package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;
	
	@Override
	public String getContentList() {
		// 通过httpclient获取taotao-rest服务的Content列表
		String string = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
		
		try {
			//把json数据转换成TaotaoResult对象
			TaotaoResult taotaoResult = TaotaoResult.formatToList(string, TbContent.class);
			// 取出TaotaoResult对象中的“data”数据部分
			List<TbContent> contentList = (List<TbContent>) taotaoResult.getData();
			// 创建结果列表
			List<Map<String, Object>> itemList = new ArrayList<Map<String,Object>>();
			// 创建一个jsp页面要求的pojo列表
			for (TbContent content: contentList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("src",content.getPic());
				map.put("height", 240);
				map.put("width",670);
				map.put("srcB",content.getPic2());
				map.put("heightB", 240);
				map.put("widthB",550);
				map.put("alt",content.getSubTitle());
				map.put("url",content.getUrl());
				
				itemList.add(map);
			}
			return JsonUtils.objectToJson(itemList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	

}
