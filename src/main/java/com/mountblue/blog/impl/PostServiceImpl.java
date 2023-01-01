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

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//         throw new RuntimeException("not found");
        return  null;
    }

    @Override
    public void savePost(Post post, String postTags) {
        System.out.println("**************in save post post is****");
        System.out.println(post);
        System.out.println("***********tagss are ***");
        System.out.println(postTags);
//        List<Tag> tagsObject = tagService.getAllTags();
        List<String>userGivenTagNames= Arrays.asList(postTags.split(","));
        List<Tag> tagslist = new ArrayList<>();
        List<Tag> tagsToAdd=new ArrayList<>();
        for (String tagName : userGivenTagNames) {
            if(!checkTag(tagName))  {
                Tag newTag = new Tag();
                newTag.setName(tagName);
                newTag.getPosts().add(post);
                System.out.println("********saving new tag  tag is *****");
                System.out.println(newTag);
                tagRepository.save(newTag);
                tagsToAdd.add(newTag);
            }
            else {
                Tag oldTag= tagRepository.findByName(tagName);
                System.out.println("preent tag is "+oldTag.getName());
//                oldTag.setName(tagName);
                oldTag.getPosts().add(post);
                System.out.println("********saving  old tag  tag is *****");
                System.out.println(oldTag);
                tagsToAdd.add(oldTag);
            }
            post.setTags(tagsToAdd);
        }
        post.setTags(tagsToAdd);
        postRepository.save(post);







//        comments.removeIf(comment->comment.getId().equals(commentId));






//        tagsObject.removeIf(tag->tag.getName().equals(tag.getName()));
//        for (String tagName : userGivenTagNames) {
//            Tag tag = tagService.getTagByName(tagName);
//            if (tag == null){
//                tag = new Tag();
//                tag.setName(tagName);
//            }
//            //associate post to tag
//            tag.addPost(post);
//            tagService.saveTag(tag);
//
//        }
//

//
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
                                    String keyword){
        System.out.println("in postServIpl");
        Sort sort = Sort.by(sortField).ascending();
        sort= sortDirection.equalsIgnoreCase("asc")? sort.ascending():sort.descending() ;
        Pageable pageable = PageRequest.of(pageNumber-1,5,sort);
        System.out.println("returning form postServIpl");
        if(keyword!=null){
            return postRepository.findAllSearches(keyword,pageable);
        }
       // will execute in run time,we don't even write in postRepo
        return postRepository.findAll(pageable);
    }

    @Override
    public List<Post> search(String keyword) {
        if(keyword!=null){
            return postRepository.search(keyword);
        }
        return postRepository.search(keyword);
    }
}
