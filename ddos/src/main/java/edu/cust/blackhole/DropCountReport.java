package edu.cust.blackhole;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

import edu.cust.util.IPUtil;

public class DropCountReport extends Thread {
	
	private Map<Integer, DropCountQueue> queueArr;
	
	private InetAddress serverAddr;

	private int serverPort;
	
	private String ip;
	
	public DropCountReport(Map<Integer, DropCountQueue> queueArr, InetAddress serverAddr, int serverPort, String ip) {
		this.queueArr = queueArr;
		this.serverAddr = serverAddr;
		this.serverPort = serverPort;
		this.ip = ip;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		CloseableHttpClient client = HttpClients.createDefault();
		while(true) {
			try {
				Set<Entry<Integer, DropCountQueue>> set = queueArr.entrySet();
				StringBuffer ips = new StringBuffer();
				StringBuffer sums = new StringBuffer();
				long t = System.currentTimeMillis() / 1000 - 10;
				for (Entry<Integer, DropCountQueue> entry : set) {
					long ip = entry.getKey();
					long sum = entry.getValue().sum(t);
					ips.append(IPUtil.longToString(ip));
					ips.append(',');
					sums.append(sum);
					sums.append(',');
				}
				if(!set.isEmpty()) {
					ips.deleteCharAt(ips.length() - 1);
					sums.deleteCharAt(sums.length() - 1);
				}
				
				HttpPost post = new HttpPost(String.format("http://%s:%d/ddos/adm/bhrNodeStatusNotifyAjax", serverAddr.getHostAddress(), serverPort));
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("midIp", ip));
				params.add(new BasicNameValuePair("srcIps", ips.toString()));
				params.add(new BasicNameValuePair("sums", sums.toString()));
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
//						post.releaseConnection();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Thread.sleep(1000);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
