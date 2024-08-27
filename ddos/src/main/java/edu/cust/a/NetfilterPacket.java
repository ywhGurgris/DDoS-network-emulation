/*
 * $Id: NetfilterPacket.java 5264 2005-05-15 18:17:18Z dfs $
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
 * NetfilterPacket encapsulates a data packet received via the
 * netfilter user space queue.  It works in conjunction with {@link
 * NetlinkMessage#getPacket}.
 *
 * @author <a href="http://www.savarese.org/">Daniel F. Savarese</a>
 */

public class NetfilterPacket {

  public static final int HOOK_PRE_ROUTING   = 0;
  public static final int HOOK_LOCAL_IN      = 1;
  public static final int HOOK_LOCAL_FORWARD = 2;
  public static final int HOOK_LOCAL_OUT     = 3;
  public static final int HOOK_POST_ROUTING  = 4;

  static {
    System.loadLibrary("vservipq");
  }

  private long __packet;
  
  /**
   * Creates an empty packet that can be used and reused for calls to
   * {@link NetlinkMessage#getPacket}.
   */
  public NetfilterPacket() {
    __packet = 0;
  }

  /**
   * @return The netfilter id of the packet.  No special support is
   * provided to interpret this value.
   */
  public native long getID();

  /**
   * @return The netfilter mark value.  No special support is provided
   * to interpret this value.
   */
  public native long getMark();

  /**
   * @return The time of arrival of the packet in seconds since 1970.
   */
  public native long getTimestampSeconds();

  /**
   * @return The number of microseconds to add to the seconds in the
   * timestamp to get the full time of arrival of the packet.
   */
  public native long getTimestampMicroseconds();

  /**
   * @return An integer, corresponding to one of the
   * <code>HOOK_</code> constants, indicating the netfilter hook that
   * placed this packet on the user space queue.
   */
  public native int getHook();

  /**
   * Writes in a StringBuffer the name of the network interface over
   * which the packet arrived.  For example, <code>eth0</code> or
   * <code>lo</code>.
   *
   * @param buffer The StringBuffer to which the incoming network
   * interface name should be appened.
   */
  public native void getIncomingInterface(StringBuffer buffer);

  /**
   * Writes in a StringBuffer the name of the network interface over
   * which the packet is exiting.  For example, <code>eth0</code> or
   * <code>lo</code>.
   *
   * @param buffer The StringBuffer to which the outgoing network
   * interface name should be appened.
   */
  public native void getOutgoingInterface(StringBuffer buffer);

  /**
   * @return The hardware protocol.  No special support is provided to
   * interpret t this value.
   */
  public native int getHardwareProtocol();

  /**
   * @return The hardware type.  No special support is provided to
   * interpret this value.
   */
  public native int getHardwareType();

  /**
   * Returns the number of bytes in the hardware address.  You'd call
   * this method first to determine how large of an array to allocate
   * before calling {@link #getHardwareAddress}.
   *
   * @return The number of bytes in the hardware address.
   */
  public native int getHardwareAddressLength();

  /**
   * Fetches the hardware address as an array of bytes.  If you were
   * to print each byte as a hexadecimal number, you'd get the
   * familiar Ethernet hardware address.
   *
   * @param address The array in which to store the address.
   */
  public native void getHardwareAddress(byte[] address);

  /**
   * @deprecated Do not use this method!
   */
  public native long getPacketDataLength();

  /**
   * Fetches the packet data.
   *
   * @param data The array in which to store the packet data.
   */
  public native int getPacketData(byte[] data);

}
