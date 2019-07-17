package com.tranphucvinh.home.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tranphucvinh.common.utils.Const;
import com.tranphucvinh.entities.Comment;
import com.tranphucvinh.entities.PostInfo;
import com.tranphucvinh.home.service.HomeService;

@Controller
@RequestMapping(value = "home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@RequestMapping(path = {"", "/code-posts", "/more-posts" })
	public String index(Model model,@RequestParam("page") Optional<Integer> page,HttpServletRequest request) {
		
		loadCommonData(model);

		// top posts
		model.addAttribute("topPosts", homeService.getCodePostInfoByLevel(Const.POST_LEVEL_1, 4));
		
		// get current page
		int currentPage = page.orElse(1);
		
		int codeCurrentPage = 1;
		int moreCurrentPage = 1;

		String path = request.getServletPath();
		if("/home/code-posts".equals(path)) {
			codeCurrentPage = currentPage;
		} else if("/home/more-posts".equals(path)) {
			moreCurrentPage = currentPage;
		}

		/**
		 * pagination code posts
		 */
		Page<PostInfo> codePosts = homeService.findPostPaginated(PageRequest.of(codeCurrentPage - 1, 4),homeService.getCodePostInfoByLevel(Const.POST_LEVEL_2, 9999));
		
		model.addAttribute("codePosts", codePosts);
		
		int codeTotalPages = codePosts.getTotalPages();
		if (codeTotalPages > 0) {
			List<Integer> codePageNumbers = IntStream.rangeClosed(1, codeTotalPages).boxed().collect(Collectors.toList());
			model.addAttribute("codePageNumbers", codePageNumbers);
		}

		/**
		 * pagination more posts
		 */
		Page<PostInfo> morePosts = homeService.findPostPaginated(PageRequest.of(moreCurrentPage - 1, 3),homeService.getOtherPosts());
		
		model.addAttribute("morePosts", morePosts);
		
		int moreTotalPages = morePosts.getTotalPages();
		if (moreTotalPages > 0) {
			List<Integer> morePageNumbers = IntStream.rangeClosed(1, moreTotalPages).boxed().collect(Collectors.toList());
			model.addAttribute("morePageNumbers", morePageNumbers);
		}
		
		return "home/index";
	}

	@RequestMapping(path = "/category/{categoryId}")
	public String category(@PathVariable("categoryId") String categoryId, @RequestParam("page") Optional<Integer> page, Model model) {

		loadCommonData(model);
		
		model.addAttribute("category", homeService.getCategoryById(categoryId));

		/**
		 * pagination posts by category
		 */
		int currentPage = page.orElse(1);
		Page<PostInfo> categoryPosts = homeService.findPostPaginated(PageRequest.of(currentPage - 1, 2), homeService.getPostsByCategoryId(categoryId, 99999));
		
		model.addAttribute("categoryPosts", categoryPosts);
		
		int totalPages = categoryPosts.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		return "home/category";
	}

	@RequestMapping(path = {"/post","/post/comments/{page}"})
	public String post(@RequestParam("postId") String postId, Model model, @PathVariable("page") Optional<Integer> page) {

		loadCommonData(model);
		
		model.addAttribute("postDetail", homeService.getPostDetailById(postId));
		
		model.addAttribute("numberOfCommentsByPost", homeService.getNumberOfCommentsByPost(postId));
		
		model.addAttribute("relatedPosts", homeService.getRelatedPostsByPostId(postId,3));

		/**
		 * pagination comments by postId
		 */
		int currentPage = page.orElse(1);
		Page<Comment> comments = homeService.findCommentPaginated(PageRequest.of(currentPage - 1, 3), homeService.getCommentsByPostId(postId));
		
		model.addAttribute("comments", comments);
		
		int totalPages = comments.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("cmtPageNumbers", pageNumbers);
		}
		
		return "home/post";
	}
	
	@RequestMapping(path = {"/post/like","/post/dislike","/post/reply"}, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public String commentAction(@RequestBody String commentId, Model model, HttpServletRequest request) {
		
		// check comment id exists ?
		
		String path = request.getServletPath();
		
		switch(path) {
			case "/post/like":
				// add like for commentId
				break;
			case "/post/dislike":
				// add dislike for commentId
				break;
			case "/post/reply":
				// save comment contend to db for commentId
				break;
		}

		return "home/post";
	}

	private void loadCommonData(Model model) {
		/**
		 * HEADER
		 */
		// categories
		model.addAttribute("codeShareCatetories", homeService.getCategoriesByType(Const.CODE_CATEGORY_TYPE));
		model.addAttribute("aroundMeCategories", homeService.getCategoriesByType(Const.OTHER_CATEGORY_TYPE));

		/**
		 * FOOTER
		 */
		// latest post
		model.addAttribute("latestPosts", homeService.getLatestPosts());

		/**
		 * RIGHT PANEL
		 */
		// popular posts
		model.addAttribute("popularPosts", homeService.getCodePostInfoByLevel(Const.POST_LEVEL_3, 3));
		// categories
		model.addAttribute("categories", homeService.getCategories());
	}
}
