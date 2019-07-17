package com.tranphucvinh.mapper;

import java.util.Map;

public interface ManagementMapper {

	void addCategory(Map<String,Object> param);

	void deleteCategory(String categoryId);
}
