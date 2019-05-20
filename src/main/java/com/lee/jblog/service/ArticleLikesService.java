package com.lee.jblog.service;

import com.lee.jblog.pojo.ArticleLikes;

public interface ArticleLikesService {

    boolean isLiked(long articleID, String username);

    void addArticleLikes(ArticleLikes articleLikes);

    void deleteArticleLikesByArticleID(long articleID);
}
