package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	
	// 获取内容列表
	public EUDateGridResult getContentList(long categoryId, int page, int rows);
	
	// 插入内容
	public TaotaoResult insertContent(TbContent tbContent);
	
}
