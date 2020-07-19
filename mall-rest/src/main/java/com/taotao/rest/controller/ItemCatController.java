package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.ItemCatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 方法一
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	/*
	@RequestMapping(value="/itemcat/list",
			produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback) throws Exception {
		
		ItemCatResult catResult = itemCatService.queryAllCategory();
		
		//把pojo转换成Json字符串
		String json = JsonUtils.objectToJson(catResult);
		// 拼装返回值
		String result = callback + "(" + json + ");";
		
		return result;
	}*/
	
	/**
	 * 方法二， 对taotoa-protal系统提供对外的接口，供jsonp调用。
	 * @param callback
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/itemcat/list")
	@ResponseBody
	public Object getItemCatList(String callback) throws Exception {
		
		ItemCatResult catResult = itemCatService.queryAllCategory();
		
		//包装jsonp
		MappingJacksonValue jacksonValue = new MappingJacksonValue(catResult);
		//设置包装的回调方法名
		jacksonValue.setJsonpFunction(callback);
		
		return jacksonValue;
	}
	

}
