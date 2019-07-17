package com.tranphucvinh.home.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tranphucvinh.entities.Category;
import com.tranphucvinh.entities.Comment;
import com.tranphucvinh.entities.PostDetail;
import com.tranphucvinh.entities.PostInfo;
import com.tranphucvinh.mapper.HomeMapper;

@Service
public class HomeServiceImpl implements HomeService {

	@Resource
	HomeMapper homeMapper;

	@Override
	public List<Category> getCategoriesByType(int type) {
		return homeMapper.getCategoriesByType(type);
	}
	
	@Override
	public List<Category> getCategories() {
		return homeMapper.getCategories();
	}

	@Override
	public List<PostInfo> getCodePostInfoByLevel(int level,int limit) {

		List<PostInfo> postInfoList = new ArrayList<PostInfo>();
		
		Map<String,Integer> param = new HashMap<String,Integer>();
		param.put("level", level);
		param.put("limit", limit);
		
		postInfoList = homeMapper.getCodePostInfoByLevel(param);
		
		for (PostInfo post : postInfoList) {
			post.setPostImages(homeMapper.getPostImagesByPostId(post.postId));
		}
		return postInfoList;
	}

	@Override
	public List<PostInfo> getOtherPosts() {

		List<PostInfo> postInfoList = new ArrayList<PostInfo>();
		postInfoList = homeMapper.getOtherPosts();
		for (PostInfo post : postInfoList) {
			post.setPostImages(homeMapper.getPostImagesByPostId(post.postId));
		}
		return postInfoList;
	}

	@Override
	public Page<PostInfo> findPostPaginated(Pageable pageable,List<PostInfo> postListPrm) {
		
		List<PostInfo> postList = postListPrm;
	
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<PostInfo> list;

		if (postList.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, postList.size());
			list = postList.subList(startItem, toIndex);
		}

		Page<PostInfo> postPage = new PageImpl<PostInfo>(list, PageRequest.of(currentPage, pageSize),
				postList.size());

		return postPage;
	}
	
	@Override
	public Page<Comment> findCommentPaginated(Pageable pageable, List<Comment> commentListPrm) {

		List<Comment> commentList = commentListPrm;
		
		int pageSize = pageable.getPageSize();
		int currentPage = pageable.getPageNumber();
		int startItem = currentPage * pageSize;
		List<Comment> list;

		if (commentList.size() < startItem) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItem + pageSize, commentList.size());
			list = commentList.subList(startItem, toIndex);
		}

		Page<Comment> postPage = new PageImpl<Comment>(list, PageRequest.of(currentPage, pageSize),
				commentList.size());

		return postPage;
	}

	@Override
	public List<PostInfo> getLatestPosts() {

		List<PostInfo> postInfoList = new ArrayList<PostInfo>();
		postInfoList = homeMapper.getLatestPosts();
		for (PostInfo post : postInfoList) {
			post.setPostImages(homeMapper.getPostImagesByPostId(post.postId));
		}
		return postInfoList;
	}

	@Override
	public List<PostInfo> getPostsByCategoryId(String categoryId, int limit) {
		
		List<PostInfo> postInfoList = new ArrayList<PostInfo>();
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("categoryId", categoryId);
		param.put("limit", limit+"");

		postInfoList = homeMapper.getPostsByCategoryId(param);
		for (PostInfo post : postInfoList) {
			post.setPostImages(homeMapper.getPostImagesByPostId(post.postId));
		}

		return postInfoList;
	}

	@Override
	public Category getCategoryById(String categoryId) {
		return homeMapper.getCategoryById(categoryId);
	}

	@Override
	public PostDetail getPostDetailById(String postId) {
		
		PostDetail postDetail = homeMapper.getPostDetailById(postId);
		
		// set categories
		List<Category> categoryList = new ArrayList<Category>();
		categoryList = homeMapper.getCategoriesByPostId(postId);
		postDetail.setCategoryList(categoryList);
		
		// set comments
		List<Comment> commentList = new ArrayList<Comment>();
		commentList = homeMapper.getCommentsByPostId(postId);
		postDetail.setCommentList(commentList);
		
		return postDetail;
	}

	@Override
	public List<Comment> getCommentsByPostId(String postId) {
		
		List<Comment> commentList = new ArrayList<Comment>();
		
		commentList = homeMapper.getCommentsByPostId(postId);
		
		for(Comment cmt:commentList) {
			cmt.setChildComments(getChildCommentsByCmtId(cmt.getCommentId()));
		}
		
		return commentList;
	}

	private List<Comment> getChildCommentsByCmtId(String commentId) {
		
		List<Comment> childCommentList = new ArrayList<Comment>();
		childCommentList = homeMapper.getChildCommentsByCmtId(commentId);
		
		for(Comment cmt:childCommentList) {
			cmt.setChildComments(getChildCommentsByCmtId(cmt.getCommentId()));
		}
				
		return childCommentList;
	}

	@Override
	public int getNumberOfCommentsByPost(String postId) {
		return homeMapper.getNumberOfCommentsByPost(postId);
	}

	@Override
	public List<PostInfo> getRelatedPostsByPostId(String postId, int limit) {
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("postId", postId);
		param.put("limit", limit+"");
		
		List<PostInfo> list = new ArrayList<PostInfo>();
		list = homeMapper.getRelatedPostsByPostId(param);

		for(PostInfo post:list) {
			post.setPostImages(homeMapper.getPostImagesByPostId(post.postId));
		}

		return list;
	}
}
