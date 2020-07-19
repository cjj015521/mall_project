package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryString, int page) {
		// 调用taotao-search的服务获取查询结果
		// 查询参数
		Map<String, String> param = new HashMap<String, String>();
		param.put("q", queryString);
		param.put("page", page + "");
		
		// 调用服务
		try {
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
			
			//将json字符串转换成TaotaoResult对象
			TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
			if (result.getStatus() == 200) {
				SearchResult searchResult = (SearchResult) result.getData();
				return searchResult;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
