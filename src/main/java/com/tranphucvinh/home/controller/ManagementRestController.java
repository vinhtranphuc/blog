package com.tranphucvinh.home.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tranphucvinh.common.utils.Const;
import com.tranphucvinh.entities.Category;
import com.tranphucvinh.home.service.HomeService;
import com.tranphucvinh.home.service.ManagementService;


@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping(value = "home/api")
public class ManagementRestController {

	@Autowired
	private HomeService homeService;
	
	@Autowired
	private ManagementService managementService;
	
	protected Logger logger = LoggerFactory.getLogger(ManagementRestController.class);
	
	@RequestMapping(path = "/categories")
	public ResponseEntity<List<Category>> getCategories() {

		try {
			List<Category> result = homeService.getCategories();
			return ResponseEntity.ok().header(Const.MS_KEY, "Get categories : "+result.size()).body(result);
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		
		return ResponseEntity.badRequest().header(Const.MS_KEY, "An error occurred when get categories !").build();
	}
	
	@RequestMapping(path = "/add-category", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Category>> addCategory(@RequestBody(required = true) Map<String,Object> param) {

		try {
			managementService.addCategory(param);
			
			List<Category> result = homeService.getCategories();
			
			return ResponseEntity.ok().header(Const.MS_KEY, "add categories successfully ").body(result);
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		
		return ResponseEntity.badRequest().header(Const.MS_KEY, "An error occurred when add category !").build();
	}
	
	
	@RequestMapping(path = "/delete-category/{categoryId}", method = RequestMethod.DELETE, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Category>> deleteCategory(@PathVariable("categoryId") String categoryId) {

		try {
			managementService.deleteCategory(categoryId);
			List<Category> result = homeService.getCategories();
			
			return ResponseEntity.ok().header(Const.MS_KEY, "add categories successfully ").body(result);
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		
		return ResponseEntity.badRequest().header(Const.MS_KEY, "An error occurred when add category !").build();
	}
}
