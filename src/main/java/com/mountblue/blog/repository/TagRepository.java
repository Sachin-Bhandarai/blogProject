package com.mountblue.blog.repository;

import com.mountblue.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
public interface TagRepository extends JpaRepository<Tag,Long> {

    @Query("SELECT DISTINCT t.name FROM Tag t")
    List<String> getEntireColumn();
    Tag findByName(String tagName);


}
