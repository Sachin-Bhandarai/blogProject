package com.mountblue.blog.impl;

import com.mountblue.blog.entity.Post;
import com.mountblue.blog.entity.Tag;
import com.mountblue.blog.repository.TagRepository;
import com.mountblue.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
   @Autowired
    private TagRepository tagRepository;



    @Override
    public List<Tag> getAllTags() {
       return tagRepository.findAll();
    }



    @Override
    public List<String> getTags() {
        return tagRepository.getTags();
    }

    @Override
    public Page<Post> tagFilter(List<String> tagNames, Pageable pageable) {
        System.out.println("***************in TAG SERVICE IMPL**********");
        System.out.println("tags are "+tagNames.toString());;
        return tagRepository.tagFilter(tagNames,pageable);
    }

    @Override
    public Page<Post> tagAuthorFilter(List<String> authors, List<String> tags, Pageable pageable) {
        return tagRepository.tagAuthorFilter(authors,tags,pageable);
    }

    @Override
    public Page<Post> authorFilter(List<String> authors, Pageable pageable) {
        return tagRepository.authorFilter(authors,pageable);
    }

    @Override
    public Page<Post> filterByDate(Date startDate, Date endDate, Pageable pageable) {
        return tagRepository.filterByDate(startDate,endDate,pageable);
    }
}
