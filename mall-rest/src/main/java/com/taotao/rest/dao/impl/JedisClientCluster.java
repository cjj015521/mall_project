package com.taotao.rest.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.rest.dao.JedisClient;

import redis.clients.jedis.JedisCluster;

/**
 * JedisClient集群实现类
 * @author Administrator
 *
 */
public class JedisClientCluster implements JedisClient {

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String hkey, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long incr(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long expire(String key, int second) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long ttl(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long del(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long hdel(String hkey, String key) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//@Autowired JedisCluster jedisCluster;

//	@Override
//	public String get(String key) {
//		
//		return jedisCluster.get(key);
//	}
//
//	@Override
//	public String set(String key, String value) {
//		return jedisCluster.getSet(key, value);
//	}
//
//	@Override
//	public String hget(String hkey, String key) {
//		return jedisCluster.hget(hkey, key);
//	}
//
//	@Override
//	public long hset(String hkey, String key, String value) {
//		return jedisCluster.hset(hkey, key, value);
//	}
//
//	@Override
//	public long incr(String key) {
//		return jedisCluster.incr(key);
//	}
//
//	@Override
//	public long expire(String key, int second) {
//		return jedisCluster.expire(key, second);
//	}
//
//	@Override
//	public long ttl(String key) {
//		return jedisCluster.ttl(key);
//	}

}
