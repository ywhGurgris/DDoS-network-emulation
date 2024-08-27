/*
 * $Id: IPQHandle.java 5264 2005-05-15 18:17:18Z dfs $
 *
 * Copyright 2004 by Daniel F. Savarese
 * Contact Information: http://www.savarese.org/contact.html
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 included
 * with this source distribution in the file named LICENSE.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details. 
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package edu.cust.a;



/**
 * <p>IPQHandle encapsulates netfilter libipq operations that require a
 * netfilter handle.  First you must {@link #open open} the handle,
 * then {@linkplain #setMode set the mode}, before you can 
 * {@link #read read} and {@link #reinject reinject} packets.  After you're
 * done filtering packets, you must {@link #close close} the netfilter
 * handle.</p>
 * 
 * <p>Only one IPQHandle instance may be open at a time.  The Linux
 * netfilter user space queuing allows only one user space packet
 * reading handle to be open at a time.</p>
 *
 * @author <a href="http://www.savarese.org/">Daniel F. Savarese</a>
 */

public abstract class IPQHandle {

  // TODO: Initialize all of these in native code, setting them
  // the appropriate macro values.  That way I don't have to copy
  // the values from the headers and they will stay in sync if
  // they change.

  /** A protocol constant for {@link #open} indicating IPv4 */
  public static final int PF_INET  = 2;

  /** A protocol constant for {@link #open} indicating IPv6 */
  public static final int PF_INET6 = 10 ;

  /**
   * A mode constant for {@link #setMode} indicating no packet data
   * should be copied.
   */
  public static final int MODE_COPY_NONE   = 0;

  /**
   * A mode constant for {@link #setMode} indicating only packet metadata
   * should be copied.
   */
  public static final int MODE_COPY_META   = 1;

  /**
   * A mode constant for {@link #setMode} indicating both packet metadata
   * and packet payloads should be copied.
   */
  public static final int MODE_COPY_PACKET = 2;

  /**
   * A verdict constant for {@link #reinject} indicating the packet should
   * be dropped.
   */
  public static final int VERDICT_DROP     = 0;

  /**
   * A verdict constant for {@link #reinject} indicating the packet should
   * be accepted.
   */
  public static final int VERDICT_ACCEPT   = 1;

  static {
    System.loadLibrary("vservipq");
  }

  private long __handle;
  
  private long __queue_handle;

  /**
   * Creates an unopened netfilter handle.
   */
  public IPQHandle() {
    __handle = 0;
	__queue_handle = 0;
  }


  /**
   * @return True if the object references a valid netfilter ipq handle.
   */
  public boolean isOpen() {
    return (__handle != 0 && __queue_handle != 0);
  }


  private native static void __open(IPQHandle handle, int protocol, int queueNum);
  private native static void __close(long handle, long queueHandle);
  private native static int __setMode(long handle, int mode, int range);
  private native static int __read(long handle, NetlinkMessage message,
                                   int timeout);
  private native static int
    __reinject(long handle, NetfilterPacket packet,
               int verdict, int dataLength, byte[] payload);

  /**
   * Creates a reference to a netfilter ipq handle, thereby allowing
   * packets to be read.
   *
   * @param protocol The protocol type of packets to copy; one of
   * either {@link #PF_INET} or {@link #PF_INET6}.
   * @return True if the handle is opened successfully, false if not.
   * @exception IllegalStateException If the object instance is
   * already open.
   */
  public boolean open(int protocol, int queueNum)
    throws IllegalStateException
  {
    if(isOpen())
      throw new IllegalStateException();
    __open(this, protocol, queueNum);
    return isOpen();
  }


  /**
   * Closes the ipq handle.
   */
  public void close() {
    __close(__handle, __queue_handle);
    __handle = 0;
	__queue_handle = 0;
  }


  /**
   * Sets the packet copying mode for calls to {@link #read}.
   *
   * @param mode One of either {@link #MODE_COPY_META} or {@link
   * #MODE_COPY_PACKET}.  The first indicates only packet metadata
   * should be copied to user space.  The second indicates both the
   * packet metadata and data payload should be cpied.
   * @param range The number of bytes of the packet payload to copy.
   * It does not include the bytes in the packet metadata.
   * @return A positive value on success; -1 on failure.
   */
  public int setMode(int mode, int range) {
    return __setMode(__queue_handle, mode, range);
  }


  /**
   * Reads a netlink message from the user space queue, containing
   * packet metadata and possibly packet data payload depending on the mode
   * requested with {@link #setMode}.
   *
   * @param message The message in which to store the message
   * retrieved from the queue.
   * @param timeout A timeout in microseconds specifying the maximum
   * amount of time to wait for a message to become available for
   * reading.  If set to zero, the read will block indefinitely.
   * @return -1 on failure and a positive value on success.  A value
   * of zero is returned if a timeout value is specified and no data
   * message was available to be read.
   */
  public int read(NetlinkMessage message, int timeout) {
    return __read(__handle, message, timeout);
  }


  /**
   * Same as <code>read(message, 0);</code>
   */
  public int read(NetlinkMessage message) {
    return read(message, 0);
  }


  /**
   * Reinjects a packet into the iptables chain.  The data payload of
   * the packet can be altered by passing a byte array as an argument.
   * If the payload is not altered, the dataLength should be 0 and the
   * payload should be null.
   *
   * @param packet The packet to reinject.
   * @param verdict The verdict specifying what to do with the packet.
   * A value of {@link #VERDICT_DROP} indicates the packet should be
   * dropped.  A value of {@link #VERDICT_ACCEPT} indicates the packet
   * should be accepted.
   * @param dataLength The number of bytes of the new payload,
   * starting from offset 0, to copy into the reinjected packet.  If
   * the payload is not altered, this value should be set to zero.
   * @param payload The new data payload.  If the payload is not
   * altered, this value should be set to null.
   * @return -1 on failure and a positive value on success.
   */
  public int reinject(NetfilterPacket packet, int verdict,
                      int dataLength, byte[] payload)
  {
    return __reinject(__queue_handle, packet, verdict, dataLength, payload);
  }


  /**
   * Same as <code>reinject(packet, verdict, 0, null);</code>
   */
  public int reinject(NetfilterPacket packet, int verdict) {
    return reinject(packet, verdict, 0, null);
  }


  /**
   * Writes a netfilter IPQ error message into a StringBuffer.  If one of
   * the IPQHandle methods fails, the cause of the error can be
   * determined by calling this method.  The message is appended to
   * the supplied StringBuffer argument.
   *
   * @param buffer The buffer in which to store the error message.
   */
  public native void getErrorMessage(StringBuffer buffer);

  //public native int getErrorNumber();
  protected abstract void callback(NetfilterPacket packet);
}
