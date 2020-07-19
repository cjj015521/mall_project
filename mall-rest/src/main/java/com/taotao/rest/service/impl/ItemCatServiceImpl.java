package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.ItemCat;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;


@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ITEM_CAT_REDIS_KEY}")
	private String ITEM_CAT_REDIS_KEY;

	@Override
	public ItemCatResult queryAllCategory() throws Exception {
		
		//（缓存的添加不能影响正常的业务逻辑），从缓存中取内容
		try {
			String result = jedisClient.hget(ITEM_CAT_REDIS_KEY, "item_cat");
			if (!StringUtils.isBlank(result)) {
				//将字符串转换成itemCatResult对象
				ItemCatResult itemCatResult = JsonUtils.jsonToPojo(result, ItemCatResult.class);
				return itemCatResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ItemCatResult result = new ItemCatResult();
		result.setData(getItemCatList(0l));
		
		//向缓存中添加内容
		try {
			//先把ItemCatResult对象转换成Josn子符串
			String json = JsonUtils.objectToJson(result);
			jedisClient.hset(ITEM_CAT_REDIS_KEY, "itme_cat", json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;

	}
	
	/**
	 * 查询分类列表
	 * <p>Title: getItemCatList</p>
	 * <p>Description: </p>
	 * @param parentid
	 * @return
	 */
	private List<?> getItemCatList(long parentid) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		
		//查询parentid为0的分类信息
		criteria.andParentIdEqualTo(parentid);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List dataList = new ArrayList();
		
		int count = 0;
		for (TbItemCat tbItemCat : list) {
			//判断是否为父节点
			if (tbItemCat.getIsParent()) {
				ItemCat itemCat = new ItemCat();
				itemCat.setUrl("/category/" + tbItemCat.getId() + ".html");
				itemCat.setName(tbItemCat.getName());
				//递归调用
				itemCat.setItem(getItemCatList(tbItemCat.getId()));
				//添加到列表
				dataList.add(itemCat);
				
				count++;
				if (parentid == 0 && count >= 14) {
					break;
				}
				
			} else {
				String catItem = "/item/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
				dataList.add(catItem);
			}
		}
		return dataList;
	}


}
