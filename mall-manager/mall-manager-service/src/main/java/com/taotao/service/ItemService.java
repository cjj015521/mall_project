package com.taotao.service;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;


public interface ItemService {
	
	TbItem getItemById(long itemId);
	
	EUDateGridResult getItemList(int page, int rows);
	
	TaotaoResult creatItem(TbItem tbItem, String desc, String itemParam) throws Exception;
	

}
