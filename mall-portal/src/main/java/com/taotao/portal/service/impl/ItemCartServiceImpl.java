package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.ItemCartService;

@Service
public class ItemCartServiceImpl implements ItemCartService {
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;

	@Override
	public TaotaoResult addCartItem(long itemId, int num, 
			HttpServletRequest request, HttpServletResponse response) {
		
		//取商品信息
		CartItem cartItem = null;
		
		// 获取cookie中的商品列表
		List<CartItem> itemList = getCartItemList(request);
		// 判断商品列表中是否已经有该商品信息
		for (CartItem item: itemList) {
			if (item.getId() == itemId) {
				item.setNum(item.getNum() + num);
				cartItem = item;
				break;
			}
		}
		// 如果没有该商品信息
		if (null == cartItem) {
			cartItem = new CartItem();
			// 调用taotao-rest的服务获取商品信息,  根据商品id查询商品基本信息。
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItem item = (TbItem) taotaoResult.getData();
				cartItem.setId(itemId);
				cartItem.setTitle(item.getTitle());
				cartItem.setPrice(item.getPrice());
				cartItem.setNum(num);
				cartItem.setImage(item.getImage() == null ? "":item.getImage().split(",")[0]);
			}
			//添加到购物车列表
			itemList.add(cartItem);
		}
		//把购物车列表写入cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		
		return TaotaoResult.ok();
	}
	
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		// 获取cookie中的购物车信息
		String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (null == cartJson) {
			return new ArrayList<CartItem>();
		}
		
		try {
			// 把json字符串转换成商品列表
			List<CartItem> cartList = JsonUtils.jsonToList(cartJson, CartItem.class);
			return cartList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<CartItem>();
	}

	@Override
	public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> itemList = getCartItemList(request);
		return itemList;
	}

	@Override
	public TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 获取购物车列表
		List<CartItem> itemList = getCartItemList(request);
		for (CartItem item : itemList) {
			if (item.getId() == itemId) {
				itemList.remove(item);
				break;
			}
		}
		//把购物车列表写入Cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
		
		return TaotaoResult.ok();
	}
	
	

}









