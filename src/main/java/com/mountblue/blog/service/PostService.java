package com.mountblue.blog.service;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void addCommentByPostId(Long postId, String name, String email, String commentData);
    void deleteCommentByCommentId(Long postId,Long commentId);
    void updateCommentByCommentId(Long postId,Long commentId,String commentData);
    Comment getCommentByPostId(Long postId,Long commentId);
    void deletePostById(Long id);
    void updatePost(Long postId ,String newPost);
     List<Post>findAll(String field);


}
