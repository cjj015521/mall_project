package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryService implements com.taotao.service.ContentCategoryService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getContenCategoryList(long parentId) {
		
		//根据parentid查询内容分类列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		ArrayList<EUTreeNode> resultList = new ArrayList<>();
		
		for (TbContentCategory contentCategory : list) {
			// 新建一个EUTreeNode
			EUTreeNode node = new EUTreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent()? "closed" : "open");
			
			resultList.add(node);
		}
		
		
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		// 创建一个pojo
		TbContentCategory category = new TbContentCategory();
		
		category.setParentId(parentId);
		category.setName(name);
		// status : 1(正常)  0:(删除)
		category.setStatus(1);
		category.setSortOrder(1);
		category.setIsParent(false);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		
		//插入新节点。需要返回主键
		contentCategoryMapper.insert(category);
		
		//判断如果父节点的isparent不是true修改为true
		//取父节点的内容
		TbContentCategory primaryKey = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!primaryKey.getIsParent()) {
			primaryKey.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(primaryKey);
		}
		
		// 把新节点返回
		return TaotaoResult.ok(category);
	}

	@Override
	public TaotaoResult deleteContentCategory(long parentId, long id) {
		// 删除id的记录
		contentCategoryMapper.deleteByPrimaryKey(id);
		
		// 判断parentId对应记录下是否有子子节点。如果没有子节点。需要把parentid对应的记录的isparent改成false
		// 查询为 parentId 的记录
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if (list == null) { // 没有 parentid 的项
			
			// 将id 为parentid 的项的 isparentid 更新为false
			TbContentCategory primaryKey = contentCategoryMapper.selectByPrimaryKey(parentId);
			primaryKey.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(primaryKey);
		}
		
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		// 	获取id对应的记录
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		category.setUpdated(new Date());
		
		// 更新i对应记录
		contentCategoryMapper.updateByPrimaryKey(category);
		
		return TaotaoResult.ok();
	}

}
