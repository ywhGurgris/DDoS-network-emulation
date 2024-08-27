package example;

import org.savarese.vserv.ipq.IPQHandle;
import org.savarese.vserv.ipq.NetfilterPacket;
import org.savarese.vserv.ipq.NetlinkMessage;
import org.savarese.vserv.tcpip.IPPacket;
import org.savarese.vserv.tcpip.TCPPacket;

public class PacketMonitor extends IPQHandle {

	void printError() {
		buffer.setLength(0);
		buffer.append("Error: ");
		getErrorMessage(buffer);
		System.err.println(buffer.toString());
	}

	void printNetfilterPacket(NetfilterPacket packet) {
		System.out.println("Hook: " + packet.getHook());
		buffer.setLength(0);
		packet.getIncomingInterface(buffer);
		System.out.println("Input if: " + buffer.toString());
		buffer.setLength(0);
		packet.getOutgoingInterface(buffer);
		System.out.println("Output if: " + buffer.toString());
		//System.out.println("Length: " + packet.getPacketDataLength());
		System.out.println("Timestamp: " + packet.getTimestampSeconds() + "::" + packet.getTimestampMicroseconds());
	}

	void printIPPacket(IPPacket packet) {
		buffer.setLength(0);
		packet.getSource(buffer);
		System.out.println("Source: " + buffer.toString());
		buffer.setLength(0);
		packet.getDestination(buffer);
		System.out.println("Dest  : " + buffer.toString());
		System.out.println("Packet Length: " + packet.getIPPacketLength());
		System.out.println("IP Header Length: " + packet.getIPHeaderByteLength());
		System.out.println("Protocol: " + packet.getProtocol());
		System.out.println("TTL: " + packet.getTTL());
		System.out.println("Checksum: " + packet.getIPChecksum());
	}

	void printTCPPacket(TCPPacket packet) {
		System.out.println("TCP Header Length: " + packet.getTCPHeaderByteLength());
		System.out.println("Source Port: " + packet.getSourcePort());
		System.out.println("Dest Port: " + packet.getDestinationPort());
		System.out.println("Sequence: " + packet.getSequenceNumber());
		System.out.println("Ack: " + packet.getAckNumber());
		System.out.println("Window: " + packet.getWindowSize());
		System.out.println("TCP Checksum: " + packet.getTCPChecksum());
		System.out.println("Computed Checksum: " + packet.computeTCPChecksum(false));
		System.out.println("URG: " + packet.isSet(TCPPacket.MASK_URG) + " ACK: " + packet.isSet(TCPPacket.MASK_ACK)
				+ " PSH: " + packet.isSet(TCPPacket.MASK_PSH) + " RST: " + packet.isSet(TCPPacket.MASK_RST) + " SYN: "
				+ packet.isSet(TCPPacket.MASK_SYN) + " FIN: " + packet.isSet(TCPPacket.MASK_FIN));
	}

	@Override
	protected void callback(NetfilterPacket packet) {
		// TODO Auto-generated method stub
		byte[] data = new byte[4096];
		TCPPacket tcpPacket = new TCPPacket(1);
		packet.getPacketData(data);

		tcpPacket.setData(data);

		/*System.out.println();
		printNetfilterPacket(packet);
		printIPPacket(tcpPacket);
		if (tcpPacket.getProtocol() == TCPPacket.PROTOCOL_TCP) {
			printTCPPacket(tcpPacket);
		}*/

		int status = reinject(packet, IPQHandle.VERDICT_ACCEPT);

		if (status < 0) {
			printError();
		}
	}
	
	StringBuffer buffer = new StringBuffer();

	public void monitor() {
		try {
			NetlinkMessage msg = new NetlinkMessage(4096);
			int status = -1;

			boolean open = open(IPQHandle.PF_INET, 0);
			System.out.println("open: " + open);
			if (open)
				status = setMode(IPQHandle.MODE_COPY_PACKET, msg.getMaxLength());

			if (status >= 0) {
				while (true) {
					status = read(msg, 0);

					if (status < 0) {
						printError();
						break;
					}
				}
			} else
				printError();
		} finally {
			buffer.setLength(0);
			close();
		}
	}

	public static final void main(String[] args) {
		new PacketMonitor().monitor();
	}

}
