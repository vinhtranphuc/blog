package com.tranphucvinh.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tranphucvinh.entities.Category;
import com.tranphucvinh.entities.Comment;
import com.tranphucvinh.entities.PostDetail;
import com.tranphucvinh.entities.PostImage;
import com.tranphucvinh.entities.PostInfo;

@Mapper
public interface HomeMapper {
	
	public List<Category> getCategoriesByType(int type);

	public List<Category> getCategories();

	public List<PostInfo> getCodePostInfoByLevel(Map<String,Integer> param);

	public List<PostImage> getPostImagesByPostId(String postId);

	public List<PostInfo> getOtherPosts();
	
	public List<PostInfo> getLatestPosts();

	public List<PostInfo> getPostsByCategoryId(Map<String,String> param);

	public Category getCategoryById(String categoryId);

	public PostDetail getPostDetailById(String postId);

	public List<Category> getCategoriesByPostId(String postId);

	public List<Comment> getCommentsByPostId(String postId);

	public List<Comment> getChildCommentsByCmtId(String commentId);

	public int getNumberOfCommentsByPost(String postId);

	public List<PostInfo> getRelatedPostsByPostId(Map<String,String> param);
}
