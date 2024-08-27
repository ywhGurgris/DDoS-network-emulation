package edu.cust.ccsds;


import edu.cust.a.IPPacket;
import edu.cust.a.IPQHandle;
import edu.cust.a.NetfilterPacket;
import edu.cust.a.NetlinkMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.cust.util.IPUtil;

public class InputMonitor extends IPQHandle implements Runnable {
	private Logger log = LoggerFactory.getLogger("CCSDS_I_Monitor");

	

	StringBuffer buffer = new StringBuffer();

	void printError() {
		buffer.setLength(0);
		buffer.append("Error: ");
		getErrorMessage(buffer);
		log.warn(buffer.toString());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			NetlinkMessage msg = new NetlinkMessage(4096);
			int status = -1;

			boolean open = open(IPQHandle.PF_INET, 0);
			log.info("open: {}", open);
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

	@Override
	protected void callback(NetfilterPacket packet) {
		// TODO Auto-generated method stub
		byte[] data = new byte[2048];
		IPPacket ipPacket = new IPPacket(1);
		int payloadLen = packet.getPacketData(data);
		
		ipPacket.setData(data);
		int len = ipPacket.getIPPacketLength();
		int headLen = ipPacket.getIPHeaderByteLength();
		byte[] ndata = new byte[len - headLen];
		System.arraycopy(data, headLen, ndata, 0, ndata.length);
		int destIp = ipPacket.getDestinationAsWord();
		log.info("len: {}, payloadLen: {}, dest: {}", len, payloadLen, IPUtil.longToString(destIp));
		reinject(packet, IPQHandle.VERDICT_ACCEPT);
	}
}
