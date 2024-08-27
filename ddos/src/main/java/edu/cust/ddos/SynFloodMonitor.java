package edu.cust.ddos;

import edu.cust.a.IPQHandle;
import edu.cust.a.NetfilterPacket;
import edu.cust.a.NetlinkMessage;
import edu.cust.a.TCPPacket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;



public class SynFloodMonitor extends IPQHandle implements Runnable {
	
	StringBuffer buffer = new StringBuffer();
	
	ConcurrentHashMap<String, Object> chm = new ConcurrentHashMap<>();
	
	public void putChm(String ip, int port) {
		chm.put(String.format("%s:%d", ip, port), new Object());
	}
	
	void printError() {
		buffer.setLength(0);
		buffer.append("Error: ");
		getErrorMessage(buffer);
		System.err.println(buffer.toString());
	}

	@Override
	protected void callback(NetfilterPacket packet) {
		// TODO Auto-generated method stub
		byte[] data = new byte[4096];
		TCPPacket tcpPacket = new TCPPacket(1);
		packet.getPacketData(data);

		tcpPacket.setData(data);

		
		if (tcpPacket.getProtocol() == TCPPacket.PROTOCOL_TCP &&
				tcpPacket.isSet(TCPPacket.MASK_SYN) &&
				tcpPacket.isSet(TCPPacket.MASK_ACK)) {
			//printTCPPacket(tcpPacket);
			try {
				String sourceIp = tcpPacket.getSourceAsInetAddress().getHostAddress();
				int sourcePort = tcpPacket.getSourcePort();
				if(chm.containsKey(String.format("%s:%d", sourceIp, sourcePort))) {
					reinject(packet, IPQHandle.VERDICT_DROP);
					return;
				}
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		reinject(packet, IPQHandle.VERDICT_ACCEPT);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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

}
