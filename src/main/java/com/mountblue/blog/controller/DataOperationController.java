package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.impl.PostServiceImpl;
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

import java.util.List;

@Controller
public class DataOperationController {
    @Autowired
    PostServiceImpl postServiceImpl;
    @CrossOrigin
    @GetMapping("/post/sort")
    public String sortBy(@RequestParam("search") String field){
        System.out.println("**********1st*********");
        return "redirect:/post/sort/"+field;
    }
    @GetMapping("/post/sort/{field}")
    public String showSortedPosts(@PathVariable("field") String field, Model model){
        model.addAttribute("posts",postServiceImpl.findAll(field));
        return "posts";
    }

}
