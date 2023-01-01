package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import com.mountblue.blog.impl.PostServiceImpl;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.repository.TagRepository;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostServiceImpl postServiceImpl;
    @Autowired
    private PostService postService;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        String keyword = null;
        return postsByPage(model, 1, "author", "asc", keyword);
    }

    @CrossOrigin
    @GetMapping("/post/new")
    public String createPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "writePost";
    }

    @CrossOrigin
    @PostMapping("/post")
    public String savePost(@ModelAttribute("post") Post post, @RequestParam("postTags") String tags) {
        postService.savePost(post, tags);
        return "redirect:/home";
    }

    @CrossOrigin
    @GetMapping("/post/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {
        Post post = postServiceImpl.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("comments", post.getComments());
        return "viewStory";
    }

    @CrossOrigin
    @GetMapping("/saveComment")
    public String saveComment(@RequestParam(name = "name") String name,
                              @RequestParam(name = "email") String email,
                              @RequestParam(name = "comment") String commentData,
                              @RequestParam(name = "id") Long postId) {
        postService.addCommentByPostId(postId, name, email, commentData);
        return "redirect:/post/" + postId;
    }

    @CrossOrigin
    @GetMapping("/post/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        postService.deleteCommentByCommentId(postId, commentId);
        return "redirect:/post/" + postId;
    }


    @CrossOrigin
    @GetMapping("/showCommentUpdate/{id}/post/{postId}")
    public String showUpdateComment(@PathVariable("postId") Long postId, @PathVariable("id") Long commentId, Model model) {
        model.addAttribute("comment", postService.getCommentByPostId(postId, commentId));
        model.addAttribute("postId", postId);
        return "updateComment";
    }

    @CrossOrigin
    @PostMapping("/updateComment")
    public String saveUpdatedComment(@RequestParam("postId") Long postId, @RequestParam("commentId") Long commentId, @ModelAttribute("comment") Comment comment) {
        postService.updateCommentByCommentId(postId, commentId, comment.getComment());
        return "redirect:/post/" + postId;
    }

    @CrossOrigin
    @PostMapping("/post/{id}")
    public String deletePost(@PathVariable("id") Long postId) {
        postService.deletePostById(postId);
        return "redirect:/home";
    }

    @CrossOrigin
    @PostMapping("/post/update")
    public String updatePost(@RequestParam("postId") Long postId, @ModelAttribute("post") Post post) {
        postService.updatePost(postId, post.getContent());
        return "redirect:/post/" + postId;
    }


    @CrossOrigin
    @GetMapping("/updatePostPage/post/{postId}")
    public String updatePostPage(@PathVariable("postId") Long postId, Model model) {
        model.addAttribute("post", postService.getPostById(postId));
        return "updatePostPage";
    }


    @GetMapping("/posts/page/{pageNumber}")
    public String postsByPage(Model model,
                              @PathVariable("pageNumber") int currentPage,
                              @PathParam("sortField") String sortField,
                              @PathParam("sortDirection") String sortDirection,
                              @Param("keyword") String keyword) {

        Page<Post> pages = postService.paginatedPost(currentPage, sortField, sortDirection, keyword);
        List<Post> posts = pages.getContent();
        model.addAttribute("posts", posts);
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalRecords", pages.getTotalElements());
        model.addAttribute("recordsPerPage", pages.getNumberOfElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortField", sortField);
        String reverseSortDirection = sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDirection", reverseSortDirection);
        model.addAttribute("keyword", keyword);

        return "home";
    }

}
