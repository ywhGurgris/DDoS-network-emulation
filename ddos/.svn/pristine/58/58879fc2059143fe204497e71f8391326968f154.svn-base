package edu.cust.blackhole;

import org.savarese.vserv.tcpip.IPPacket;
import org.savarese.vserv.tcpip.OctetConverter;

public class OspfPacket extends IPPacket {

	public static final int OFFSET_VERSION = 0;
	/** Offset into the ICMP packet of the type header value. */
	public static final int OFFSET_TYPE = 1;

	public static final int OFFSET_OSPF_LENGTH = 2;

	/** Offset into the ICMP packet of the code header value. */
	public static final int OFFSET_ROUTER_ID = 4;

	public static final int OFFSET_AREA_ID = 8;

	public static final int OFFSET_OSPF_CHECKSUM = 12;

	public static final int OFFSET_AU_TYPE = 14;

	public static final int OFFSET_AUTHENTICATION = 16;

	public static final int OFFSET_PACKET_DATA = 24;

	/** The byte offset into the IP packet where the ICMP packet begins. */
	int _offset;

	public OspfPacket(int size) {
		super(size);
		_offset = 0;
	}

	public OspfPacket(OspfPacket packet) {
		super(packet.size());
		copy(packet);
		_offset = packet._offset;
	}

	public void setIPHeaderLength(int length) {
		super.setIPHeaderLength(length);
		_offset = getIPHeaderByteLength();
	}

	/**
	 * @return The total number of bytes in the IP and OSPF headers.
	 */
	public final int getCombinedHeaderByteLength() {
		return _offset + OFFSET_PACKET_DATA;
	}
	
	public final int getOSPFPacketLength() {
		return (((_data_[_offset + OFFSET_OSPF_LENGTH] & 0xff) << 8)
				| (_data_[_offset + OFFSET_OSPF_LENGTH + 1] & 0xff));
	}

	/**
	 * @return The TCP header length in 32-bit words.
	 */
	public final int getOSPFDataByteLength() {
		return getIPPacketLength() - getCombinedHeaderByteLength();
	}

	public final void setOSPFDataByteLength(int length) {
		if (length < 0)
			length = 0;

		setIPPacketLength(getCombinedHeaderByteLength() + length);
		int packetLen = OFFSET_PACKET_DATA + length;
		_data_[_offset + OFFSET_OSPF_LENGTH] = (byte) (0xff & (packetLen >>> 8));
		_data_[_offset + OFFSET_OSPF_LENGTH + 1] = (byte) (0xff & packetLen);
	}

	/**
	 * Copies the contents of an ICMPPacket. If the current data array is of
	 * insufficient length to store the contents, a new array is allocated.
	 *
	 * @param packet The TCPPacket to copy.
	 */
	public final void copyData(OspfPacket packet) {
		if (_data_.length < packet._data_.length) {
			byte[] data = new byte[packet._data_.length];
			System.arraycopy(_data_, 0, data, 0, getCombinedHeaderByteLength());
			_data_ = data;
		}
		int length = packet.getOSPFDataByteLength();
		System.arraycopy(packet._data_, packet.getCombinedHeaderByteLength(), _data_, getCombinedHeaderByteLength(),
				length);
		setOSPFDataByteLength(length);
	}

	public void setData(byte[] data) {
		super.setData(data);
		_offset = getIPHeaderByteLength();
	}

	public final void setType(int type) {
		_data_[_offset + OFFSET_TYPE] = (byte) (type & 0xff);
	}

	public final int getType() {
		return (_data_[_offset + OFFSET_TYPE] & 0xff);
	}

	public final void setVersion(int version) {
		_data_[_offset + OFFSET_VERSION] = (byte) (version & 0xff);
	}

	public final int getVersion() {
		return (_data_[_offset + OFFSET_VERSION] & 0xff);
	}

	public final void setRouterId(int routerId) {
		OctetConverter.intToOctets(routerId, _data_, _offset + OFFSET_ROUTER_ID);
	}

	public final int getRouterId() {
		return (((_data_[_offset + OFFSET_ROUTER_ID] & 0xff) << 24)
				| ((_data_[_offset + OFFSET_ROUTER_ID + 1] & 0xff) << 16)
				| ((_data_[_offset + OFFSET_ROUTER_ID + 2] & 0xff) << 8)
				| (_data_[_offset + OFFSET_ROUTER_ID + 3] & 0xff));
	}
	
	public final String getRouterIdString() {
		return getAddrString(OFFSET_ROUTER_ID);
	}

	public final void setAreaId(int areaId) {
		OctetConverter.intToOctets(areaId, _data_, _offset + OFFSET_AREA_ID);
	}
	
	public final String getAreaIdString() {
		return getAddrString(OFFSET_AREA_ID);
	}

	public final int getAreaId() {
		return (((_data_[_offset + OFFSET_AREA_ID] & 0xff) << 24)
				| ((_data_[_offset + OFFSET_AREA_ID + 1] & 0xff) << 16)
				| ((_data_[_offset + OFFSET_AREA_ID + 2] & 0xff) << 8) | (_data_[_offset + OFFSET_AREA_ID + 3] & 0xff));
	}

	public final int getOSPFChecksum() {
		return (((_data_[_offset + OFFSET_OSPF_CHECKSUM] & 0xff) << 8)
				| (_data_[_offset + OFFSET_OSPF_CHECKSUM + 1] & 0xff));
	}

	public final int computeOSPFChecksum(boolean update) {
		return _computeChecksum_(_offset, _offset + OFFSET_OSPF_CHECKSUM, getIPPacketLength(), 0, update);
	}

	public final int computeOSPFChecksum() {
		return computeOSPFChecksum(true);
	}
	
	private final String getAddrString(int os) {
		StringBuffer sb = new StringBuffer();
		sb.append((_data_[_offset + os] & 0xff));
		sb.append('.');
		sb.append((_data_[_offset + os + 1] & 0xff));
		sb.append('.');
		sb.append((_data_[_offset + os + 2] & 0xff));
		sb.append('.');
		sb.append((_data_[_offset + os + 3] & 0xff));
		return sb.toString();
	}
}
