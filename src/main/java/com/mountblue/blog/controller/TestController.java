package com.mountblue.blog.controller;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Student;
import com.mountblue.blog.impl.PostServiceImpl;
import com.mountblue.blog.impl.TagServiceImpl;
import com.mountblue.blog.repository.TagRepository;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TestController {

    private final TagRepository tagRepository;
    @Autowired
     TagServiceImpl tagServiceImpl;
    @Autowired
    PostServiceImpl postServiceImpl;
    @Autowired
    private TagService tagService;


    public TestController(TagRepository tagRepository, TagService tagService) {
       super();
        this.tagRepository = tagRepository;
        this.tagService = tagService;
    }

    @CrossOrigin
    @GetMapping ("/test")
    public String test(Model model){
        Post post = new Post();
        post.setContent("Thymeleaf Easy Peasy");
        post.setId(252L);
        model.addAttribute("post",post);

        return "index";
    }
    @CrossOrigin
    @GetMapping("/TupdatePostPage/post/")
    public String updatePostPage(@RequestParam("pid") Long postId, Model model){
        System.out.println("in update");
        System.out.println("************redricting method********");
        model.addAttribute("post",postServiceImpl.getPostById(postId));
        return "updatePostPage";
    }
    @CrossOrigin
    @GetMapping("/props")
    public String updatePostPage(){
       return "message";
    }
    @CrossOrigin
    @GetMapping("/register")
    public String openRegistrationForm(@ModelAttribute("student") Student student){
        System.out.println("in controller");
        return "registration";
    }
    @CrossOrigin
    @GetMapping("/saveEmp")
    public String saveEmp(@ModelAttribute("student") Student student){
        System.out.println("in controller");
        return "registration";
    }
    @CrossOrigin
    @GetMapping("/one")
    public String handler1(Model model, RedirectAttributes re){
        System.out.println("in handler 1");
        model.addAttribute("obj1","handler1");
        re.addFlashAttribute("obj2","handler1");
        return "redirect:/two";
    }
    @CrossOrigin
    @GetMapping("/two")
    public String handler2(Model model){
        System.out.println("in handler 2");
        System.out.println("model is "+model.getAttribute("obj"));
//        System.out.println("model is "+obj2);
        return "ok";
    }
    @CrossOrigin
    @GetMapping("/view1")
    public String view1(Model model){
        System.out.println("in view1");
        model.addAttribute("posts","post1 ,post 2");
        return "view1";
    }
    @CrossOrigin
    @GetMapping("/view2")
    public String view2(Model model){
        System.out.println("in view2");
//        model.addAttribute("posts","post1 ,post 2");
        return "redirect:/view1";
    }
    @CrossOrigin
    @ResponseBody()
    @GetMapping("/data")
    public String data(){
        System.out.println("in data");
        System.out.println(tagServiceImpl.findByDistinctName());
         return "data";
    }
    @CrossOrigin
    @ResponseBody()
    @GetMapping("/tags")
    public  String showTags(){
        System.out.println("**********tags are************");
        System.out.println(tagService.getAllTags());
        return "ok";
    }

    @CrossOrigin
    @ResponseBody()
    @GetMapping("/posts")
    public  String postTags(){
        System.out.println("**********tags are************");
        System.out.println(postServiceImpl.getPostById(102L).getTags());
        System.out.println("*************post is **********");
        System.out.println(postServiceImpl.getPostById(102L));
        System.out.println(tagRepository.findById(202L).get().getName());

        return "ok";
    }

}
