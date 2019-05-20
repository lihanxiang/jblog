package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class ArticleLikes {

    private long id;

    private long articleID;

    private int likerID;

    private String likeDate;

    public ArticleLikes(){}

    public ArticleLikes(long articleID, int likerID, String likeDate) {
        this.articleID = articleID;
        this.likerID = likerID;
        this.likeDate = likeDate;
    }
}
