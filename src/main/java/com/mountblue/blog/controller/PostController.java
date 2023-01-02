package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        String keyword = null;
        String startDate=null;
        String lastDate=null;
        String tags=null;
        String authors=null;
        return postsByPage(model, 1, "author", "asc", keyword,startDate,lastDate,tags,authors);
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
    @GetMapping("/getPost/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {
        Post post = postService.getPostById(id);
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
        return "redirect:/getPost/" + postId;
    }

    @CrossOrigin
    @GetMapping("/post/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId) {
        postService.deleteCommentByCommentId(postId, commentId);
        return "redirect:/getPost/" + postId;
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
        return "redirect:/getPost/" + postId;
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
        return "redirect:/getPost/" + postId;
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
                              @Param("keyword") String keyword,
                              @Param("startDate") String startDate,
                              @Param("lastDate") String lastDate,
//                              @Param("tagFilters") String tagFilters,
                              @Param("authorFilters") String authorFilters,
//                              @PathParam("tags") String tag1
//                              @PathVariable("tags") String tag2
                              @PathParam("tagFilters") String tagFilters
                              )  {

//        System.out.println("tagFilters in controllers ="+tagFilters);
        HttpServletRequest request;
         System.out.println("***********in post controller ***");
        System.out.println("tags are = "+tagFilters);
        System.out.println("authors are "+authorFilters);
        System.out.println("start date is "+startDate);
        System.out.println("last date is "+lastDate);
        System.out.println("sort direction is "+sortDirection);
        System.out.println("sort field is "+sortField);
//        System.out.println("tag2 = "+tag2);
//        System.out.println("tag3 = "+tag3);


        Page<Post> pages = postService.paginatedPost(currentPage, sortField, sortDirection, keyword,startDate,lastDate,tagFilters,authorFilters);
        List<Post> posts = pages.getContent();
        model.addAttribute("posts", posts);
//        model.addAttribute("excerpt",p)
        model.addAttribute("totalPages", pages.getTotalPages());
        model.addAttribute("totalRecords", pages.getTotalElements());
        model.addAttribute("recordsPerPage", pages.getNumberOfElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("sortField", sortField);
        String reverseSortDirection = sortDirection.equalsIgnoreCase("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDirection", reverseSortDirection);
        model.addAttribute("keyword", keyword);
        model.addAttribute("startDate",startDate);
        model.addAttribute("lastDate",lastDate);
        model.addAttribute("tagFilters",tagFilters);
        model.addAttribute("authorFilters",authorFilters);
        List<String> authors= postService.getAuthors();
        List<String> tags =tagService.getTags();
        model.addAttribute("authors",authors);
        model.addAttribute("tags",tags);

        return "home";
    }

}
