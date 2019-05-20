package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.ArticleLikesMapper;
import com.lee.jblog.pojo.ArticleLikes;
import com.lee.jblog.service.ArticleLikesService;
import com.lee.jblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikesServiceImpl implements ArticleLikesService{

    private final ArticleLikesMapper articleLikesMapper;
    private final UserService userService;

    @Autowired
    public ArticleLikesServiceImpl(ArticleLikesMapper articleLikesMapper, UserService userService) {
        this.articleLikesMapper = articleLikesMapper;
        this.userService = userService;
    }

    @Override
    public boolean isLiked(long articleID, String username) {
        return articleLikesMapper.isLiked(articleID, userService.getIDByUsername(username)) != null;
    }

    @Override
    public void addArticleLikes(ArticleLikes articleLikes) {
        articleLikesMapper.addArticleLikes(articleLikes);
    }

    @Override
    public void deleteArticleLikesByArticleID(long articleID) {
        articleLikesMapper.deleteArticleLikesByArticleID(articleID);
    }
}
