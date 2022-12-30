package com.mountblue.blog.service;

import com.mountblue.blog.entity.Tag;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TagService {
    List<String> findByDistinctName();
    List<Tag> getAllTags();
    Tag getTagByName(String name);
}
