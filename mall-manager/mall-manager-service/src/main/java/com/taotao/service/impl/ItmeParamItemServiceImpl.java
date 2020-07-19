package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;

@Service
public class ItmeParamItemServiceImpl implements ItemParamItemService{
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	/**
	 * 获取商品规格参数信息
	 */
	@Override
	public String getParamItemByItemId(long itemId) {
		
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		
		if (list == null || list.isEmpty()) {
			return "";
		}
		
		// 取出参数信息
		TbItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		
		// 将Json子符串转换成Java对象
		List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
		
		//将参数信息转换成html
		StringBuffer sb = new StringBuffer(); 
		//sb.append("<div>");
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
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
		//sb.append("</div>");

		
		return sb.toString();
	}

}
