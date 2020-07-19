package com.taotao.httpclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.taotao.common.utils.HttpClientUtil;

public class HttpClientTest {
	
	@Test
	public void doGet() throws Exception {
		// 创建一个httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建一个GET对象
		HttpGet get = new HttpGet("http://www.baidu.com");
		// 执行请求
		CloseableHttpResponse response = httpclient.execute(get);
		// 获取响应结果
		int statusCode = response.getStatusLine().getStatusCode();
		System.out.println(statusCode);
		
		HttpEntity entity = response.getEntity();
		String string = EntityUtils.toString(entity, "utf-8");
		System.out.println(string);
		
		// 关闭httpclient
		response.close();
		httpclient.close();
	}
	
	/**
	 * @throws Exception
	 */
	@Test
	public void doPost() throws Exception {
		// 创建一个httpclient对象
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建一个Post对象
		HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
		List<NameValuePair> kvList = new ArrayList<NameValuePair>();
		kvList.add(new BasicNameValuePair("username", "张三"));
		kvList.add(new BasicNameValuePair("password", "123"));
		
		StringEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
		
		// 设置请求内容
		post.setEntity(entity);
		
		// 执行post请求
		CloseableHttpResponse response = httpclient.execute(post);
		String string = EntityUtils.toString(response.getEntity(),"utf-8");
		System.out.println(string);
		
		// 关闭httpclient
		response.close();
		httpclient.close();
		
	}
	
	@Test
	public void getContentListTest() {
		String string = HttpClientUtil.doGet("http://localhost:8081/rest/content/list/89");
		
		System.out.println(string);
	}

}










