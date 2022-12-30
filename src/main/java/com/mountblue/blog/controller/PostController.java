package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import com.mountblue.blog.impl.PostServiceImpl;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {
    private final PostRepository postRepository;
    @Autowired
    private PostServiceImpl postServiceImpl;
    @Autowired
    private PostService postService;

    public PostController(PostService postService,
                          PostRepository postRepository) {
        super();
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @CrossOrigin
    @GetMapping("/post")
    public String getAllPosts(Model model) {
//        old
//        model.addAttribute("posts", postService.getAllPosts());
// new
//         TODO change the name and don't call from controller ,reomve model
        return post(1,"createdAt","desc",model);
    }

    @CrossOrigin
    @GetMapping("/post/new")
    public String createPost(Model model) {
        Post post = new Post();
        model.addAttribute("post",post);
        return "writePost";
    }

    @CrossOrigin
    @PostMapping("/post")
    public String savePost(@ModelAttribute("post") Post post,@RequestParam("postTags") String tags) {
        System.out.println("**************in controller********");

        System.out.println("post tags are "+tags);
        postServiceImpl.savePost(post,tags);   //change
        return "redirect:/post";
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
        postServiceImpl.addCommentByPostId(postId, name, email, commentData);
        System.out.println(postId);
        return "redirect:/post/" + postId;
    }

    ///    post (post/post/{id}
    //     delete (post/post >
    //     get (post/post/{id}
    //      (/)
    @CrossOrigin
    @GetMapping("/post/{postId}/comment/{commentId}")
    public String deleteComment(@PathVariable("postId") Long postId,@PathVariable("commentId") Long commentId) {
        postServiceImpl.deleteCommentByCommentId(postId,commentId);
        return "redirect:/post/" + postId;
    }

    // comment redirct to upate page
    @CrossOrigin
    @GetMapping("/showCommentUpdate/{id}/post/{postId}") //show comment
    public String showUpdateComment(@PathVariable("postId") Long postId,@PathVariable("id") Long commentId,Model model) {
        System.out.println("commentId="+commentId+ " postId="+postId);
        model.addAttribute("comment",postServiceImpl.getCommentByPostId(postId,commentId));
        model.addAttribute("postId",postId);
        return "updateComment";
    }
    @CrossOrigin
    @PostMapping("/updateComment")
    public String saveUpdatedComment(@RequestParam("postId") Long postId, @RequestParam("commentId") Long commentId,@ModelAttribute("comment") Comment comment){
        postServiceImpl.updateCommentByCommentId(postId,commentId,comment.getComment());
        return "redirect:/post/" + postId;
    }
    @CrossOrigin
    @PostMapping("/post/{id}")
    public String deletePost(@PathVariable("id") Long postId){
        postServiceImpl.deletePostById(postId);
        return "redirect:/post";
    }
    @CrossOrigin
    @PostMapping("/post/update")
    public String updatePost(@RequestParam("postId") Long postId,@ModelAttribute("post") Post post){
        postServiceImpl.updatePost(postId,post.getContent());
        return "redirect:/post/" + postId;
    }

    //redirect to post update page
    @CrossOrigin
    @GetMapping("/updatePostPage/post/{postId}")
    public String updatePostPage(@PathVariable("postId") Long postId,Model model){
        System.out.println("************redricting method********");
        model.addAttribute("post",postServiceImpl.getPostById(postId));
        return "updatePostPage";
    }
    @CrossOrigin
    @GetMapping("/page/{pageNo}")
    //controler can't be called from controller
    public String post(@PathVariable(value = "pageNo" ) Integer pageNo,
                       @RequestParam("sortField") String sortField,
                       @RequestParam("sortDirection") String sortDirection,
                       Model model){
        if(pageNo==null) pageNo=1;
        if(sortField==null) sortField="java";
        if(sortDirection==null) sortDirection="ASC";
        System.out.println("page no ="+pageNo);
        int pageSize=5;

        Page<Post> postPages = postServiceImpl.findPaginated(pageNo,pageSize,sortField,sortDirection);
        List<Post> posts=postPages.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",postPages.getTotalPages());
        model.addAttribute("totalItems",postPages.getTotalElements());


        model.addAttribute("sortField",sortField);
        model.addAttribute("sortDirection",sortDirection);

        model.addAttribute("reverseSortDirection",sortDirection.equals("asc") ? "desc" : "asc");

        model.addAttribute("posts",posts);


        return "posts";
    }



}
