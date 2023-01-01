package com.mountblue.blog.service;

import com.mountblue.blog.entity.Comment;
import com.mountblue.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void savePost(Post post,String postTags);
    void addCommentByPostId(Long postId, String name, String email, String commentData);
    void deleteCommentByCommentId(Long postId,Long commentId);
    void updateCommentByCommentId(Long postId,Long commentId,String commentData);
    Comment getCommentByPostId(Long postId,Long commentId);
    void deletePostById(Long id);
    void updatePost(Long postId ,String newPost);
     List<Post>findAll(String field);
     //pageable = currentPage+recordPerPage
//     Page<Post>findAll(Pageable pageable);
     Page<Post> findPaginated(int pageNo, int  pageSize,String sortField,String sortDirection);

//     List<Post> findByTitleOrExcerptOrAuthor(String title,String excerpt,String author);
    Page<Post> findPaginated(int pageNo,int pageSize);
    List<Post> search1(  String keyword,
                         String sortField,
                        String sortDirection);


    Page<Post> paginatedPost(int pageNumber,String sortField, String sortDirection,String keyword);
    List<Post> search(String keyword);

}
