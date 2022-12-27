package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    public List<Post> findByAuthor(String author);
    List<Post>findByTitleOrExcerptOrAuthor(String title,String excerpt,String author);

    List<Post> findByTitle(String title);

    List<Post> findByExcerpt(String excerpt);
    List<Post> findByContentContainingIgnoreCase(String infix);
    List<Post> findByContentContaining(String infix);
    List<Post> findByAuthorContaining(String infix);

    List<Post> findByTitleContaining(String infix);
    List<Post> findByAuthorContainingIgnoreCase(String infix);

    List<Post> findByTitleContainingIgnoreCase(String infix);




}
