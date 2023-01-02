package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Date;
import java.util.List;
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("SELECT DISTINCT t.name FROM Tag t")
    List<String> getTags();
    Tag findByName(String tagName);
    @Query("select distinct p from Post p join p.tags t where t.name IN :tagNames")
    public Page<Post> tagFilter(@Param("tagNames") List<String> tagNames, Pageable pageable);
    @Query("select distinct p from Post p join p.tags t where (t.name in :tagNames) and (p.author in :authors)")
    public Page<Post> tagAuthorFilter(@Param("authors") List<String> authors,
                                      @Param("tagNames") List<String> tags, Pageable pageable);
    @Query("select distinct p from Post p where p.author IN :authors")
    public Page<Post> authorFilter(@Param("authors") List<String> authors, Pageable pageable);

    @Query("select distinct p from Post p where published_at between :startDate and :endDate")
    Page<Post> filterByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);



}
