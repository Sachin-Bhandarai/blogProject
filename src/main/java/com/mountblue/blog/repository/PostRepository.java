package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("SELECT DISTINCT p.author FROM Post p")
    List<String> getAuthors();
//    @Query("select distinct p from Post p join p.tags t where ((t.name IN :tag) and (p.name IN :author) and" +
//            "(published_at between :startDate and :endDate))")
//    List<Post> findByColumn1Column2In(@Param("column1") List<String> tags, @Param("column2") List<String> authors,
//                                              @Param("startDate") LocalDateTime startDate, @Param("endtDate") LocalDateTime endDate);
//




    @Query("SELECT p FROM Post p WHERE "+
            "CONCAT(p.title, ' ',p.author, ' ', p.content)"+
            "LIKE %?1%"  )
    Page<Post> findAllSearches(String keyword,Pageable pageable);



}
