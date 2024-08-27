/*
 * $Id: NetlinkMessage.java 5264 2005-05-15 18:17:18Z dfs $
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
 * NetlinkMessage encapsulates a Linux netlink datagram.  Linux
 * netlink provides the ability to communicate between kernel modules
 * and user space processes.  NetlinkMessage is used in conjunction
 * with {@link IPQHandle#read} to read from the user
 * space queue netlink messages containing network packets.  Only
 * NetlinkMessage instances of type {@link #TYPE_PACKET} contain
 * packets.  No support is provided for accessing other types of
 * netlink messages, such as netlink error messages.
 *
 * @author <a href="http://www.savarese.org/">Daniel F. Savarese</a>
 */

public class NetlinkMessage {

  /**
   * An unused constant from which the MODE, VERDICT, and PACKET
   * types are derived.
   */
  public static final int TYPE_BASE    = 0x10;

  /** An IPQ mode request message. */
  public static final int TYPE_MODE    = TYPE_BASE + 1;

  /** An IPQ verdict setting message. */
  public static final int TYPE_VERDICT = TYPE_BASE + 2;

  /** An IPQ packet message. */
  public static final int TYPE_PACKET  = TYPE_BASE + 3;

  /** A noop message type to be ignored. */
  public static final int TYPE_NOOP    = 0x1;

  /** An netlink error message type to be ignored. */
  public static final int TYPE_ERROR   = 0x2;

  /** End of netlink dump. */
  public static final int TYPE_DONE    = 0x3;

  /** Indication of loss of data. */
  public static final int TYPE_OVERRUN = 0x4;

  static {
    System.loadLibrary("vservipq");
  }

  private long __message;
  private int __maxLength;

  private native void __initBuffer();

  private native void __freeBuffer();

  /**
   * Frees the memory allocated for the netlink message buffer.
   * Do not override this method without calling <code>super.finalize()</code>.
   */
  protected void finalize() throws Throwable {
    __freeBuffer();
  }

  /**
   * Same as <code>NetlinkMessage(2048);</code>
   */
  public NetlinkMessage() {
    this(2048);
  }

  /**
   * Initializes a netlink message with a given maximum length.
   *
   * @param maxLength The maximum number of bytes that this netlink
   * message can store.
   */
  public NetlinkMessage(int maxLength) {
    __maxLength = maxLength;
    __initBuffer();
  }

  /**
   * @return The maximum length of this netlink message.
   */
  public int getMaxLength() {
    return __maxLength;
  }

  /**
   * @return The error number resulting from a {@link #TYPE_ERROR}
   * netlink message.  No special support is provided to interpret the
   * value, but it corresponds to platform-specific errno values.
   */
  public native int getError();

  /**
   * @return The length of the entire netlink message, including the header.
   */
  public native int getLength();

  /**
   * @return The type of the netlink message, corresponding to one of
   * the <code>TYPE_</code> constants.
   */
  public native int getType();

  /**
   * @return Additional netlink flags.  No special support is provided
   * to interpret them.
   */
  public native int getFlags();

  /**
   * @return The sequence number of the netlink message.
   */
  public native int getSequenceNumber();

  /**
   * @return The process id of the process sending the netlink message.
   */
  public native int getSendingProcessID();

  /**
   * @deprecated Do not use this method!
   */
  public native void getPacket(NetfilterPacket packet);
}
