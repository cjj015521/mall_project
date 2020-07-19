package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	/**
	 * 获取相应的内容列表
	 */
	@Override
	public EUDateGridResult getContentList(long categoryId, int page, int rows) {
		
		TbContentExample example = new TbContentExample();
		
		if (categoryId != 0) {
			Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);
		}
		
		// 分页
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		//获取记录总条数
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		
		EUDateGridResult result = new EUDateGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		
		return result;

	}

	
	/**
	 * 插入TbContent
	 */
	@Override
	public TaotaoResult insertContent(TbContent tbContent) {
		// 完善TbContent
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		
		// 把内容信息保存到数据库
		contentMapper.insert(tbContent);
		
		// 添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + tbContent.getCategoryId()); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}

}
