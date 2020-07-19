package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITME_INFO_URL}")
	private String ITME_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	@Override
	public ItemInfo getItemInfo(long itemId) {
		
		try {
			// 调用taotao-rest的服务获取商品信息
			String string = HttpClientUtil.doGet(REST_BASE_URL + ITME_INFO_URL + itemId);
			if (!StringUtils.isBlank(string)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(string, ItemInfo.class);
				if (taotaoResult.getStatus() == 200) {
					ItemInfo item = (ItemInfo) taotaoResult.getData();
					return item;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public String getItemDesc(long itemId) {
		// 调用taotao-rest的服务获取商品描述信息
		try {
			String string = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			if (!StringUtils.isBlank(string)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(string, TbItemDesc.class);
				if (taotaoResult.getStatus() == 200) {
					TbItemDesc itemDesc = (TbItemDesc) taotaoResult.getData();
					return itemDesc.getItemDesc();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String getItemParam(long itemId) {
		// 调用taotao-rest的服务获取商品规格参数
		try {
			String string = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			if (!StringUtils.isBlank(string)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(string, TbItemParamItem.class);
				if (taotaoResult.getStatus() == 200) {
					// 取出参数信息
					TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
					String paramData = itemParamItem.getParamData();
					
					// 将json字符串转换成Java对象
					List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
					
					//将参数信息转换成html
					StringBuffer sb = new StringBuffer(); 
					//sb.append("<div>");
					sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
					sb.append("    <tbody>\n");
					for (Map map : paramList) {
						sb.append("        <tr>\n");
						sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+map.get("group")+"</th>\n");
						sb.append("        </tr>\n");
						List<Map> params = (List<Map>) map.get("params");
						for (Map map2 : params) {
							sb.append("        <tr>\n");
							sb.append("            <td class=\"tdTitle\">"+map2.get("k")+"</td>\n");
							sb.append("            <td>"+map2.get("v")+"</td>\n");
							sb.append("        </tr>\n");
						}
					}
					sb.append("    </tbody>\n");
					sb.append("</table>");

					// 返回html片段
					return sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
