package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    public List<Post> findByAuthor(String author);

}
