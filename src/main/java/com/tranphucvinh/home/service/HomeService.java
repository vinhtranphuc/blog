package com.tranphucvinh.home.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tranphucvinh.entities.Category;
import com.tranphucvinh.entities.Comment;
import com.tranphucvinh.entities.PostDetail;
import com.tranphucvinh.entities.PostInfo;

public interface HomeService {
	
	public List<Category> getCategoriesByType(int type);
	
	public List<Category> getCategories();
	
	public Page<PostInfo> findPostPaginated(Pageable pageable, List<PostInfo> postList);

	public List<PostInfo> getCodePostInfoByLevel(int level,int limit);

	public List<PostInfo> getOtherPosts();
	
	public List<PostInfo> getLatestPosts();

	public List<PostInfo> getPostsByCategoryId(String categoryId, int limit);

	public Category getCategoryById(String categoryId);

	public PostDetail getPostDetailById(String postId);

	public List<Comment> getCommentsByPostId(String postId);

	public int getNumberOfCommentsByPost(String postId);

	public List<PostInfo> getRelatedPostsByPostId(String postId, int limit);

	public Page<Comment> findCommentPaginated(Pageable pageable, List<Comment> commentListPrm);
}
