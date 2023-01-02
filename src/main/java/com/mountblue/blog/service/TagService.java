package com.mountblue.blog.service;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
public interface TagService {
    List<Tag> getAllTags();

    List<String>getTags();
    public Page<Post> tagFilter(@Param("tagNames") List<String> tagNames, Pageable pageable);
    public Page<Post> tagAuthorFilter(@Param("authors") List<String> authors,
                                      @Param("tagNames") List<String> tags, Pageable pageable);
    public Page<Post> authorFilter(@Param("authors") List<String> authors, Pageable pageable);

    Page<Post> filterByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);


}
