package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class Tag {

    private int ID;

    private String tagName;

    private int tagSize;

    public Tag() {}

    public Tag(String tagName, int tagSize) {
        this.tagName = tagName;
        this.tagSize = tagSize;
    }
}
