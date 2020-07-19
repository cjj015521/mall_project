package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.ItemCartService;

/**
 * 购物车Controller
 */
@Controller
@RequestMapping("/cart")
public class ItemCartController {
	
	@Autowired
	private ItemCartService cartService;
	
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable("itemId") Long itemId,
					@RequestParam(defaultValue = "1") Integer num,
					HttpServletRequest request, HttpServletResponse response) {
		
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		
		return "redirect:/cart/success.html";
				
	}
	
	@RequestMapping("/success")
	public String showSuccess() {
		return "cartSuccess";
	}
	
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> itemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", itemList);
		return "cart";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCartItem(@PathVariable("itemId") Long itemId,
								HttpServletRequest request, HttpServletResponse response) {
		
		cartService.deleteCartItem(itemId, request, response);
		
		return "redirect:/cart/cart.html";
	}
	
}









