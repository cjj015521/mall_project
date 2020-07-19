package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
/**
 * 用户管理Service
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN}")
	private String SSO_USER_TOKEN;
	@Value("${SSO_USER_LOGIN}")
	public String SSO_USER_LOGIN;

	@Override
	public TbUser getUserByToken(String token) {
		
		try {
			// 调用taotao-sso的服务获取用户信息
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
			
			//把json转换成TaotaoREsult
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbUser.class);
			if (taotaoResult.getStatus() == 200) {
				TbUser user = (TbUser) taotaoResult.getData();
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
