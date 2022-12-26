package com.mountblue.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)

    private Long id;
    private String title;
    private String excerpt;
    private String author;
    private String publishedAt;

    @Column(columnDefinition = "text")
    private String content;
   @CreationTimestamp
    private LocalDateTime createdAt;

   @UpdateTimestamp
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment>comments;



}
