package edu.cust.blackhole;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

public class StatisticsThread extends Thread{
	
	private String serverIp;
	
	private String serverPort;
	
	public StatisticsThread(String serverIp, String serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}
	
	void sleep() {
		try {
			Thread.sleep(1000);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	long lastRecv = -1;
	long lastSend = -1;
	
	String[] netInfo(NetworkIF[] ifs) {
		long totalRecv = 0;
		long totalSend = 0;
		for (NetworkIF nif : ifs) {
			nif.updateNetworkStats();
			totalRecv += nif.getPacketsRecv();
			totalSend += nif.getPacketsSent();
		}
		long currentRecv = lastRecv == -1 ? 0 : totalRecv - lastRecv;
		long currentSend = lastSend == -1 ? 0 : totalSend - lastSend;
		lastRecv = totalRecv;
		lastSend = totalSend;
		return new String[] {currentRecv + "", currentSend + ""};
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String rid = null;
		try(BufferedReader in = new BufferedReader(new FileReader("/usr/local/etc/ospfd.conf"))){
			String line;
			while((line = in.readLine()) != null) {
				if(line.indexOf("router-id") != -1) {
					String[] sarr = line.trim().split(" +");
					rid = sarr[sarr.length - 1];
					break;
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		if(rid == null) {
			System.out.println("Can not obtain the router id");
			return;
		}
		System.out.println("Router id: " + rid);
		
		SystemInfo si = new SystemInfo();
		HardwareAbstractionLayer hal = si.getHardware();
		CloseableHttpClient client = HttpClients.createDefault();
		while(true) {
			String[] net = netInfo(hal.getNetworkIFs());
//			System.out.println("network");
			
			HttpPost post = new HttpPost(String.format("http://%s:%s/ddos/adm/receiveRouterStateAjax", serverIp, serverPort));
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("rid", rid));
			params.add(new BasicNameValuePair("recv", net[0]));
			params.add(new BasicNameValuePair("send", net[1]));
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
				entity.setContentType("application/x-www-form-urlencoded");
				post.setEntity(entity);
				try(CloseableHttpResponse httpResponse = client.execute(post)){
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						HttpEntity httpEntity = httpResponse.getEntity();
						String result = EntityUtils.toString(httpEntity);// 取出应答字符串
						System.out.println(String.format("Successfully notified: %s", result));
					}
//					post.releaseConnection();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			sleep();
		}
		
	}

}
