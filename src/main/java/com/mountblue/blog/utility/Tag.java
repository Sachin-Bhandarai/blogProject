package com.mountblue.blog.utility;

import java.util.List;

public class Tag {
    List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Tag(List<String> tags) {
        this.tags = tags;
    }
}
