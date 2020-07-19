package com.taotao.portal.service;

import com.taotao.pojo.TbItemDesc;
import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
	ItemInfo getItemInfo(long itemId);
	String getItemDesc(long itemId);
	String getItemParam(long itemId);
}
