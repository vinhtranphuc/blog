package com.tranphucvinh.entities;

import java.util.List;

public class Comment {
	
	public String userFullName;
	public String userAvatarPath;
	private String commentId;
	public String cmtParrentId;
	public String postId;
	public String commentedAt;
	public String content;
	
	private List<Comment> childComments;

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCommentId() {
		return commentId;
	}

	public List<Comment> getChildComments() {
		return childComments;
	}
	public void setChildComments(List<Comment> childComments) {
		this.childComments = childComments;
	}
}
