package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class Article {

    private int ID;
    private long articleID;
    private String author;
    private String articleTitle;
    private String articleContent;
    private String articleTags;
    private String articleType;
    private String articleCategories;
    private String publishDate;
    private String updateDate;
    private String articleURL;
    private String articleTabloid;
    private int likes;
    private long lastArticleID;
    private long nextArticleID;

}
