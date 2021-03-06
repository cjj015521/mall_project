package com.taotao.rest.jedis;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;




public class JedisTest {

	@Test
	public void testJedisSingle() {
		// 创建一个jedis对象
		Jedis jedis = new Jedis("192.168.64.133",6379);
		
		// 调用jedis对象的方法，方法名和redis命名一致
		jedis.set("key1","jedis test");
		String string = jedis.get("key1");
		System.out.println(string);
		
		// 关闭jedis
		jedis.close();
		
	}
	
	//使用连接池
	@Test
	public void testJedisPool() {
		// 创建jedis连接池
		JedisPool pool = new JedisPool("192.168.64.133",6379);
		
		//从连接池中获取Jedis对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		
		// 关闭jedis对象
		jedis.close();
		pool.close();
	}
	
	// 连接集群
	@Test
	public void testJedisCluster() throws IOException {
		// 创建一个set存储结点
		HashSet<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.64.132", 7001));
		nodes.add(new HostAndPort("192.168.64.132", 7002));
		nodes.add(new HostAndPort("192.168.64.132", 7003));
		nodes.add(new HostAndPort("192.168.64.132", 7004));
		nodes.add(new HostAndPort("192.168.64.132", 7005));
		nodes.add(new HostAndPort("192.168.64.132", 7006));
		
		//创建一个集群对象
		JedisCluster cluster = new JedisCluster(nodes);
		
		cluster.set("key1", "1000");
		String string = cluster.get("key1");
		System.out.println(string);
		
		cluster.close();
	}
	
	//测试Spring Jedis单机版
	@Test
	public void testSpringJedisSingle() {
		ApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool pool = (JedisPool) applicationContext.getBean("redisClient");
		
		//从连接池中获取Jedis对象
		Jedis jedis = pool.getResource();
		String string = jedis.get("key1");
		System.out.println(string);
		
		// 关闭jedis对象
		jedis.close();
		pool.close();
	}
	
	// 测试Spring Jedis集群版
	@Test
	public void testSpringJedisCluster() throws IOException {
		ApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		
		JedisCluster cluster = (JedisCluster) applicationContext.getBean("redisClient");
		
		String string = cluster.get("key1");
		
		System.out.println(string);
		
		cluster.close();
	}
}







