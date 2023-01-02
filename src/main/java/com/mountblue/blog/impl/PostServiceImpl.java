package com.mountblue.blog.impl;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Tag;
import com.mountblue.blog.repository.PostRepository;
import com.mountblue.blog.repository.TagRepository;
import com.mountblue.blog.service.PostService;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    @Autowired
    private TagService tagService;
    private final TagRepository tagRepository;

    public PostServiceImpl(PostRepository postRepository,
                           TagRepository tagRepository) {
        super();
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        if (postRepository.findById(id).isPresent()) {
            return postRepository.findById(id).get();
        }
         throw new RuntimeException("not found");
    }

    @Override
    public void savePost(Post post, String postTags) {
        System.out.println("**************in save post post is****");
        System.out.println(post);
        System.out.println("*******************escerpt to be saved is**********");
        System.out.println(post.getContent().substring(0,5).toString());
        post.setExcerpt(post.getContent().substring(0,20));
        System.out.println("******************* new escerpt to be saved is**********");
        System.out.println(post.getContent().toString());
        System.out.println("***********tagss are ***");
        System.out.println(postTags);
        List<String>userGivenTagNames= Arrays.asList(postTags.split(","));
        List<Tag> tagsToAdd=new ArrayList<>();
        for (String tagName : userGivenTagNames) {
            if(!checkTag(tagName))  {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                newTag.getPosts().add(post);
                System.out.println(newTag);
                tagRepository.save(newTag);
                tagsToAdd.add(newTag);
            }
            else {
                Tag oldTag= tagRepository.findByName(tagName);
                oldTag.getPosts().add(post);
                tagsToAdd.add(oldTag);
            }
            post.setTags(tagsToAdd);
        }
        post.setTags(tagsToAdd);
        postRepository.save(post);
    }

    public boolean checkTag(String tagName) {
        List<Tag> tags = tagRepository.findAll();
        for (Tag tag : tags) {
            if(tag.getName().equals(tagName))
                return true;
        }
        return false;
    }

    @Override
    public void addCommentByPostId(Long postId, String name, String email, String commentData) {
        Post post = getPostById(postId);
        Comment comment = createComment(post,name, email, commentData);
        post.getComments().add(comment);
        postRepository.save(post);

    }

    @Override
    public void deleteCommentByCommentId(Long postId, Long commentId) {
        Post post = getPostById(postId);
        List<Comment> comments = post.getComments();
        comments.removeIf(comment->comment.getId().equals(commentId));
        postRepository.save(post);
    }

    @Override
    public void updateCommentByCommentId(Long postId, Long commentId,String commentData) {
        Post post = getPostById(postId);
        List<Comment> comments = post.getComments();
        for (Comment comment : comments) {
            if(comment.getId().equals(commentId)){
                comment.setComment(commentData);
                break;
            }
        }
        postRepository.save(post);

    }

    @Override
    public Comment getCommentByPostId(Long postId,Long commentId) {
        Post post = getPostById(postId);
        List<Comment> comments = post.getComments();
//        Comment comment = comments.stream().filter(a -> a.getId() == commentId).collect(Collectors.toList()).get(0);
        for(Comment comment : comments) {
            if(comment.getId().equals(commentId)) {
                return comment;
            }
        }
        throw new RuntimeException("not found");


    }

    @Override
    public void deletePostById(Long id) {
        System.out.println("***************************");
        System.out.println("deleting post with id ="+id);
        System.out.println("post is "+getPostById(id));
        postRepository.delete(getPostById(id));
    }

    @Override
    public void updatePost(Long postId,String newPost) {
        Post post = getPostById(postId);
        post.setContent(newPost);
        postRepository.save(post);
    }

    @Override
    public List<Post> findAll(String field) {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }




    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize,String sortField,String sortDirection) {
       Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                   :Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        return this.postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);

        return postRepository.findAll(pageable);
    }

    @Override
    public List<Post> search1(String keyword, String sortField, String sortDirection) {
        int pageNo=1;
        int pageSize = 5;
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page postPages ;

        List<Post> posts= postRepository.findAll();
        return posts;
    }


    private static Comment createComment(Post post, String name, String email, String commentData) {
        Comment comment = new Comment();
        comment.setName(name);
        comment.setEmail(email);
        comment.setComment(commentData);
        comment.setPost(post);
        return comment;
    }

    public void save(Post post) {
        postRepository.save(post);
    }


    @Override
    public Page<Post> paginatedPost(int pageNumber,String sortField,String sortDirection,
                                    String keyword,
                                    String firstDate,String lastDate,String tagFilters,String authorFilters

    )  {
        Sort sort = Sort.by(sortField).ascending();

        sort= sortDirection.equalsIgnoreCase("asc")? sort.ascending():sort.descending() ;
        Pageable pageable = PageRequest.of(pageNumber-1,5,sort);


        if(keyword!=null){
            return postRepository.findAllSearches(keyword,pageable);
        }

       //if only tag has value rest other has no value neither null  ///coming from filters so none has null
        if(tagFilters!=null  && authorFilters.equals("")  && firstDate.equals(",") && lastDate.equals(",")   ) {
            if ((tagFilters != "" && (firstDate.equals(",") && lastDate.equals(",") && authorFilters.equals("")))) {
                List<String> tagList = List.of(tagFilters.split(","));
                System.out.println(tagRepository.tagFilter(tagList, pageable).getContent().toString());
                return tagRepository.tagFilter(tagList, pageable);
            }
        }
        if(tagFilters!=null && authorFilters!=null &&  firstDate.equals(",") && lastDate.equals(",")  && !tagFilters.equals("") && !authorFilters.equals("")){
            System.out.println("inside  tag+author");
            System.out.println("tags are ="+tagFilters);
            System.out.println("authros are ="+authorFilters);
            List<String> tagList = List.of(tagFilters.split(","));
            List<String> authorList =List.of(authorFilters.split(","));
            Page<Post> posts=tagService.tagAuthorFilter(authorList,tagList,pageable);
            for (Post post : posts) {
                System.out.println(post.toString());
            }
            return tagService.tagAuthorFilter(authorList,tagList,pageable);
        }
        if( authorFilters!=null && authorFilters!=null && tagFilters.equals("")  && !authorFilters.equals("") && firstDate.equals(",") && lastDate.equals(",")   ){
            System.out.println("inside author only ");
            System.out.println("author is ="+authorFilters);
            System.out.println("tags are "+tagFilters);
            List<String> authorList = List.of(authorFilters.split(","));
            Page<Post> posts=tagService.authorFilter(authorList,pageable);
            for (Post post : posts) {
                System.out.println(post.toString());
            }
            return tagService.authorFilter(authorList,pageable);
        }
        if( authorFilters!=null && authorFilters!=null && !firstDate.equals(",") && !lastDate.equals(",") && tagFilters.equals("") && authorFilters.equals("")){
            System.out.println("inside date only");
            System.out.println("author is ="+authorFilters);
            System.out.println("tags are "+tagFilters);
            System.out.println("start -> end"+firstDate+" -> "+lastDate);
            System.out.println("chopped dates are");
//            str.substring(0,str.length()-1)
            firstDate= firstDate.substring(0,firstDate.length()-1);
            lastDate= lastDate.substring(0,lastDate.length()-1);
            System.out.println("chopped");
            System.out.println(firstDate);
            System.out.println(lastDate);
            System.out.println("formattde");
            System.out.println(Date.valueOf(firstDate));
            System.out.println(Date.valueOf(lastDate));
            Date startingDate =Date.valueOf(firstDate);
            Date endingDate = Date.valueOf(lastDate);
            List<Post> posts =  tagService.filterByDate(startingDate, endingDate, pageable).getContent();
            for (Post post :posts) {
                System.out.println("*****************post is**********");
                System.out.println(post.toString());
            }
            System.out.println("query result are "+ tagService.filterByDate(startingDate,endingDate,pageable));
            System.out.println("returning<>>>>>>>>>>>>>>>>");

            return tagService.filterByDate(startingDate,endingDate,pageable);


        }
        // will execute in run time,we don't even write in postRepo
        return postRepository.findAll(pageable);
    }



    @Override
    public List<String> getAuthors() {
        return postRepository.getAuthors();
    }


}
