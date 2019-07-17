package com.tranphucvinh.entities;

import java.util.List;

public class PostInfo {
	
	public String categories;
	public String categoryType;
	public String publishedAt;
	public String title;
	public String summary;
	public String postId;
	public String numberOfComments;
	
	private List<PostImage> postImageList;

	public List<PostImage> getPostImages() {
		return postImageList;
	}
	public void setPostImages(List<PostImage> postImageList) {
		this.postImageList = postImageList;
	}
}
