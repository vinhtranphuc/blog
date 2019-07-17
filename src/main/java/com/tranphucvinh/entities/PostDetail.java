package com.tranphucvinh.entities;

import java.util.List;
import java.util.stream.Collectors;

public class PostDetail extends PostInfo {
	
	public String content;

	private List<Category> categoryList;
	
	private List<Comment> commentList;

	public void setCategories(List<Category> categoryList) {
		this.categories = categoryList.stream().map(e -> e.category).collect(Collectors.joining(", "));
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
		this.setCategories(categoryList);
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
}
