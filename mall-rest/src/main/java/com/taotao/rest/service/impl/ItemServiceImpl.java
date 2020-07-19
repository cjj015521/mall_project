package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
/**
 * 获取商品详细信息
 * @author Administrator
 *
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper tbItemParamItemMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			
			if (!StringUtils.isBlank(string)) {
				// 把json字符串转换成Java对象
				TbItem item = JsonUtils.jsonToPojo(string, TbItem.class);
				return TaotaoResult.ok(item);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 根据商品id查询商品信息
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		
		//将java对象转换成json字符串
		String json = JsonUtils.objectToJson(item);
		try {
			//将商品信息保存到redis中
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", json);
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 使用TaotaoResult包装一下
		return TaotaoResult.ok(item);
	}
	
	@Override
	public TaotaoResult getItemDescInfo(long itemId) {
		
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String string = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			
			if (!StringUtils.isBlank(string)) {
				// 把json字符串转换成Java对象
				TbItemDesc itemDesc = JsonUtils.jsonToPojo(string, TbItemDesc.class);
				return TaotaoResult.ok(itemDesc);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 获取商品描述信息
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
		
		//将java对象转换成json字符串
		String json = JsonUtils.objectToJson(itemDesc);
		try {
			//将商品信息保存到redis中
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", json);
			//设置key的过期时间
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 使用TaotaoResult包装一下
		return TaotaoResult.ok(itemDesc);
		
	}

	@Override
	public TaotaoResult getItemParamInfo(long itemId) {
		
		try {
			//添加缓存逻辑
			//从缓存中取商品信息，商品id对应的信息
			String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			//判断是否有值
			if (!StringUtils.isBlank(json)) {
				//把json转换成java对象
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				return TaotaoResult.ok(paramItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//根据商品id查询规格参数
		//设置查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		// 获取商品规格参数
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list != null && list.size() > 0) {
			TbItemParamItem itemParm = list.get(0);
			try {
				//把商品信息写入缓存
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(itemParm));
				//设置key的有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(itemParm);
		}
		return TaotaoResult.build(400, "无此商品规格");
	}

}









