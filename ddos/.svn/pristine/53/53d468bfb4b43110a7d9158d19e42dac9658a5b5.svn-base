package edu.cust.blackhole;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class Main {
	
	private static Logger log = LoggerFactory.getLogger("OSPF_Main");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = args.length;
		if(n != 4) {
			System.out.println("Must enter 4 parameters: server IP, server port, local IP and local dev");
			return;
		}
		new StatisticsThread(args[0], args[1]).start();
		
		try (DatagramSocket ds = new DatagramSocket(8888)) {
			Map<Integer, DropCountQueue> queueArr = new ConcurrentHashMap<>();
			OspfMonitor om = new OspfMonitor(queueArr, args[2], args[3]);
			new Thread(om).start();
			DropCountReport dcr = null;
			for (;;) { // 无限循环
				// 数据缓冲区:
				byte[] buffer = new byte[4096];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				try {
					ds.receive(packet); // 收取一个UDP数据包
					
					//InetAddress addr = packet.getAddress();
					//int port = 8080;
					// 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
					// 将其按UTF-8编码转换为String:
					String data = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
					Map<?,?> obj = new Gson().fromJson(data, Map.class);
					String event = (String) obj.get("event");
					String targetIp = (String) obj.get("targetIp");
					log.info(targetIp);
					
					if(dcr == null) {
						InetAddress addr = packet.getAddress();
						int port = 8080;
						dcr = new DropCountReport(queueArr, addr, port, args[2]);
						dcr.start();
					}
					//int targetPort = ((Double) obj.get("targetPort")).intValue();
					if("protect".equals(event)) {
						om.updateAttackers(targetIp);
					}else {
						om.updateAddrList(targetIp);
					}
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
