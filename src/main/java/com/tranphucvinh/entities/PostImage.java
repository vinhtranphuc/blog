package com.tranphucvinh.entities;

import com.tranphucvinh.common.utils.Const;

public class PostImage {

	public String imageId;
	public String postId;
	private String imageName;
	
	public String imageFullPath;

	public String getImageName() {
		return imageName;
	}
	
	public void setImageName(String imageName) {
		this.imageName = imageName;
		this.imageFullPath = imageName==null?"":Const.POST_IMG_LOCATION+imageName;
	}
}
