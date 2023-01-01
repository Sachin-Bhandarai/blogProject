//package com.mountblue.blog.controller;
//
//import com.mountblue.blog.entity.Post;
//import com.mountblue.blog.impl.PostServiceImpl;
//import com.mountblue.blog.repository.PostRepository;
//import com.mountblue.blog.repository.TagRepository;
//import com.mountblue.blog.service.PostService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.net.http.HttpRequest;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//public class DataOperationController {
//    @Autowired
//    PostServiceImpl postServiceImpl;
//    @Autowired
//    private PostRepository postRepository;
//    @Autowired
//    private TagRepository tagRepository;
//    @Autowired
//    PostController postController;
//
//
//    @CrossOrigin
//    @GetMapping("/pagee/{pageNo}/search")
//    public String sortBy(@Param("search") String field, Model model) {
//        System.out.println("**********search1()=*********" + field);
//        model.addAttribute("keyword", field);
//
//        return "redirect:/post/sort/" + field;
//    }
//
//    @GetMapping("/post/sort")
//    public String showSortedPosts(@Param("field") String field, Model model) {
//        List<Post> posts = new ArrayList<>();
//        posts.addAll(postRepository.findByContentContainingIgnoreCase(field));
//        posts.addAll(postRepository.findByTitleContainingIgnoreCase(field));
//        model.addAttribute("keyword", field);
//
//        posts.addAll(postRepository.findByAuthorContainingIgnoreCase(field));
//        model.addAttribute("posts", posts);
//        System.out.println("*************");
//        System.out.println(postRepository.findByContentContainingIgnoreCase(field));
//        return "sortedPosts";
//    }
//
//    //get all tags and show it to home pag
//    // e
//    @GetMapping("/search")
//    public String search(Model model, HttpServletRequest request, @RequestParam("keyword") String keyword,
//                         @RequestParam("sortField") String sortField,
//                         @RequestParam("sortDirection") String sortDirection
//
//    ) {
//        System.out.println("****************in search keyword is " + keyword);
//        System.out.println(sortDirection+ ">" + sortField);
//
//        int pageNo=1;
//        int pageSize = 5;
//        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
//                : Sort.by(sortField).descending();
//        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
//        Page postPages = postRepository.findAllSearches(keyword);
//        List<Post> posts= postPages.getContent();
//        System.out.println("***********in search list size of post is ="+posts.size());
//        System.out.println("search result is "+posts.size());
//        System.out.println(posts);
//
//
//        HttpSession session= request.getSession();
//        session.setAttribute("posts",posts);
//        session.setAttribute("location",1);
//        model.addAttribute("posts", posts);
//
//        model.addAttribute("keyword", keyword);
//
//
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("totalPages", postPages.getTotalPages());
//        model.addAttribute("totalItems", postPages.getTotalElements());
//
//
//        model.addAttribute("sortField", sortField);
//        model.addAttribute("sortDirection", sortDirection);
//
//        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
//
//        model.addAttribute("posts", posts);
//        model.addAttribute("tags", tagRepository.getEntireColumn());
//        model.addAttribute("authors", postRepository.getEntireColumn());
//        model.addAttribute("type", 2);
//
//        System.out.println("*********************from seach going back to psots***********");
//
////        return "redirect:/pagee/"+pageNo+"/"+sortField+"/"+sortDirection;
//        boolean isSearch=true;
//        System.out.println("redirction to page/1");
//        return  postController.pageo( model,request, pageNo, sortField, sortDirection,isSearch,keyword);
//
//
//    }
//
//    //    sort BY
//    @GetMapping("/sort")
//
//    public String sort(@RequestParam("direction") String sortingDirection, RedirectAttributes model) {
//
//        System.out.println("soring is " + sortingDirection);
//        if(sortingDirection.equals("ASC")){
//            List<Post> posts= postRepository.findAllByOrderByCreatedAtAsc();
//            System.out.println("size is "+posts.size());
//        }else {
//            List<Post> posts= postRepository.findAllByOrderByCreatedAtDesc();
//            System.out.println("size is "+posts.size());
//            model.addAttribute("posts",posts);
//        }
//        return "redirect:/pageNo/1";
//
//    }
//
////    *************************************************************************************************************************
//@GetMapping("/search1/{pageNo}")
//public String search1(Model model, HttpServletRequest request, @RequestParam("keyword") String keyword,
//                     @RequestParam("sortField") String sortField,
//                     @RequestParam("sortDirection") String sortDirection) {
//    System.out.println("****************in search keyword is " + keyword);
//    System.out.println(sortDirection+ ">" + sortField);
//
//    int pageNo=1;
//    int pageSize = 5;
//    Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
//            : Sort.by(sortField).descending();
//    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
//    Page postPages = postRepository.findAllSearches(keyword);
//    List<Post> posts= postPages.getContent();
//    System.out.println("***********in search list size of post is ="+posts.size());
//    System.out.println("search result is "+posts.size());
//    System.out.println(posts);
//
//
//    HttpSession session= request.getSession();
//    session.setAttribute("posts",posts);
//    model.addAttribute("posts", posts);
//
//    model.addAttribute("keyword", keyword);
//
//
//    model.addAttribute("currentPage", pageNo);
//    model.addAttribute("totalPages", postPages.getTotalPages());
//    model.addAttribute("totalItems", postPages.getTotalElements());
//
//
//    model.addAttribute("sortField", sortField);
//    model.addAttribute("sortDirection", sortDirection);
//
//    model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
//
//    model.addAttribute("posts", posts);
//    model.addAttribute("tags", tagRepository.getEntireColumn());
//    model.addAttribute("authors", postRepository.getEntireColumn());
//    model.addAttribute("type", 2);
//
//    System.out.println("*********************from seach going back to psots***********");
//    boolean isSearch=true;
//    System.out.println("redirction to page/1");
//    return  postController.pageo( model,request, pageNo, sortField, sortDirection,isSearch,keyword);
//
//
//}
//
//}
