package com.mountblue.blog.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {
    @Id
    private Long id;
    private String name;
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public void addPost(Post post) {
        if (posts.equals(null)) {
            posts = new ArrayList<>();
        }
        posts.add(post);
    }
}
