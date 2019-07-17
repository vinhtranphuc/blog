package com.tranphucvinh.home.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tranphucvinh.mapper.ManagementMapper;

@Service
public class ManagementServiceImpl implements ManagementService{
	
	@Resource
	private ManagementMapper managementMapper;
	
	@Override
	public void addCategory(Map<String, Object> param) {
		managementMapper.addCategory(param);
	}

	@Override
	public void deleteCategory(String categoryId) {
		managementMapper.deleteCategory(categoryId);
	}
}
