package com.taotao.portal.controller;

import javax.jws.WebParam.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.service.ContentService;

@Controller
public class IndexController {
	
	@Autowired
	private ContentService contenteService;
	
	@RequestMapping("/index")
	public String showIndex(Model model) {
		
		String contentList = contenteService.getContentList();
		model.addAttribute("ad1", contentList);
		
		return "index";
	}
	
	@RequestMapping(value="/httpclient/post",
			method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult testPost(String username, String password) {
		String string = "username" + username + "\tpassword:" + password;
		return TaotaoResult.ok(string);
	}

}
