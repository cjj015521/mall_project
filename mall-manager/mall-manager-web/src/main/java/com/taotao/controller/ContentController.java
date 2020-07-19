package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;


/**
 * 分类内容管理Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * 根据分类ID查询分类列表内容
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDateGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		
		EUDateGridResult result = contentService.getContentList(categoryId, page, rows);
		
		return result;
	}
	
	/**
	 * 将内容信息保存到数据库
	 * @param tbContent
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent tbContent) {
		
		TaotaoResult result = contentService.insertContent(tbContent);
		
		return result;
	}

}
