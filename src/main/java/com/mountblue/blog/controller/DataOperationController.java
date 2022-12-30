package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.impl.PostServiceImpl;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DataOperationController {
    @Autowired
    PostServiceImpl postServiceImpl;
    @Autowired
    private PostRepository postRepository;

    @CrossOrigin
    @GetMapping("/post/sort")
    public String sortBy(@RequestParam("search") String field){
        System.out.println("**********search()=*********"+field);
        return "redirect:/post/sort/"+field;
    }
    @GetMapping("/post/sort/{field}")
    public String showSortedPosts(@PathVariable("field") String field, Model model){
        List<Post>posts =new ArrayList<>();
        posts.addAll(postRepository.findByContentContainingIgnoreCase(field));
        posts.addAll(postRepository.findByTitleContainingIgnoreCase(field));

        posts.addAll(postRepository.findByAuthorContainingIgnoreCase(field));
        model.addAttribute("posts",posts);
        System.out.println("*************");
        System.out.println(postRepository.findByContentContainingIgnoreCase(field));
        return "sortedPosts";
    }
    //get all tags and show it to home page

}
