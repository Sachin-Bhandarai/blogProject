package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.impl.PostServiceImpl;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller

public class CheckController {

    @Autowired
    private PostServiceImpl postServiceImpl;
    @Autowired
    private PostService postService;
    @Autowired
    private TagService tagService;
//    @GetMapping("/home")
//    public String home(Model model){
//        System.out.println("**** in / controller");
//        String  keyword=null;
//
//      return postsByPage(model,1,"author","asc",keyword);
//    }
//    @GetMapping("/posts/page/{pageNumber}")
//    public String postsByPage(Model model,
//                              @PathVariable("pageNumber") int currentPage,
//                              @PathParam("sortField") String sortField,
//                              @PathParam("sortDirection") String sortDirection,
//                              @Param("keyword") String keyword
//                              ){
//        System.out.println("**** in /page/no controller");
//
//        Page<Post> pages = postService.paginatedPost(currentPage,sortField,sortDirection,keyword);
//        List<Post> posts =  pages.getContent();
//        model.addAttribute("posts",posts);
//        model.addAttribute("totalPages",pages.getTotalPages());
//        model.addAttribute("totalRecords",pages.getTotalElements());
//        model.addAttribute("recordsPerPage",pages.getNumberOfElements());
//        model.addAttribute("currentPage",currentPage);
//        model.addAttribute("sortDirection",sortDirection);
//        model.addAttribute("sortField",sortField);
//        String reverseSortDirection = sortDirection.equalsIgnoreCase("asc")?"desc":"asc";
//        model.addAttribute("reverseSortDirection",reverseSortDirection);
//        model.addAttribute("keyword",keyword);
//
//        return "home";
//    }
    @GetMapping("/s")
    @ResponseBody
    public String s(){
        List<String> authors =postService.getAuthors();
        System.out.println("*****************search result is*********");
        for (String author : authors) {
            System.out.println(author);
        }
        List<String>tags=tagService.getTags();
        for (String tag : tags) {
            System.out.println(tag);
        }
        return "ok";
    }
    @GetMapping("/filters")
    @ResponseBody
    public String filter(
            @Param("start") String start,@Param("last") String last,@Param("tags") String tags,@Param("authors") String authors
    ){
        System.out.println("start ="+start);
        System.out.println("end ="+last);
        System.out.println(" tags="+tags);
        System.out.println("authors= "+authors);
        return  "ok";
    }
}
