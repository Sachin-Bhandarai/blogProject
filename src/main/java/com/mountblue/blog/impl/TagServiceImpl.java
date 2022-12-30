package com.mountblue.blog.impl;

import com.mountblue.blog.entity.Tag;
import com.mountblue.blog.repository.TagRepository;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TagServiceImpl implements TagService {
   @Autowired
    private TagRepository tagRepository;

    @Override
    public List<String> findByDistinctName() {
       return  tagRepository.getEntireColumn();
    }

    @Override
    public List<Tag> getAllTags() {
       return tagRepository.findAll();
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
