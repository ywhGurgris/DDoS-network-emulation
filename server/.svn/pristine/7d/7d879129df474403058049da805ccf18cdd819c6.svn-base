package edu.cust;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class AccessAttackTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(String.format("http://%s:%d/ddos/adm/receiveAccessAttackStateAjax", "127.0.0.1", 8080));
		try {
			Thread.sleep(4000);
			StringEntity entity = new StringEntity("{\"event\":\"success\",\"ip\":\"10.0.0.1\",\"mac\":\"00:FF:36:FF:6A:16\",\"status\":\"成功接入\"}", "utf-8");
			post.setEntity(entity);
			try(CloseableHttpResponse httpResponse = client.execute(post)){
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					String result = EntityUtils.toString(httpEntity);// 取出应答字符串
					System.out.println(result);
				}
//				post.releaseConnection();
			}
			Thread.sleep(5000);
			entity = new StringEntity("{\"event\":\"success\",\"ip\":\"\",\"mac\":\"0A:00:27:00:00:0B\",\"status\":\"1次认证测试\"}", "utf-8");
			post.setEntity(entity);
			try(CloseableHttpResponse httpResponse = client.execute(post)){
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					String result = EntityUtils.toString(httpEntity);// 取出应答字符串
					System.out.println(result);
				}
//				post.releaseConnection();
			}
			Thread.sleep(2000);
			entity = new StringEntity("{\"event\":\"success\",\"ip\":\"\",\"mac\":\"0A:00:27:00:00:0B\",\"status\":\"2次认证测试\"}", "utf-8");
			post.setEntity(entity);
			try(CloseableHttpResponse httpResponse = client.execute(post)){
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					String result = EntityUtils.toString(httpEntity);// 取出应答字符串
					System.out.println(result);
				}
//				post.releaseConnection();
			}
			Thread.sleep(8000);
			entity = new StringEntity("{\"event\":\"success\",\"ip\":\"\",\"mac\":\"0A:00:27:00:00:0B\",\"status\":\"3次认证测试\"}", "utf-8");
			post.setEntity(entity);
			try(CloseableHttpResponse httpResponse = client.execute(post)){
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					String result = EntityUtils.toString(httpEntity);// 取出应答字符串
					System.out.println(result);
				}
//				post.releaseConnection();
			}
			entity = new StringEntity("{\"event\":\"bl\",\"ip\":\"\",\"mac\":\"0A:00:27:00:00:0B\",\"status\":\"\"}", "utf-8");
			post.setEntity(entity);
			try(CloseableHttpResponse httpResponse = client.execute(post)){
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					String result = EntityUtils.toString(httpEntity);// 取出应答字符串
					System.out.println(result);
				}
//				post.releaseConnection();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
