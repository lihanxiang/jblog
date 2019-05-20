package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.CommentLikesMapper;
import com.lee.jblog.pojo.CommentLikes;
import com.lee.jblog.service.CommentLikesService;
import com.lee.jblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikesServiceImpl implements CommentLikesService {

    private final CommentLikesMapper commentLikesMapper;
    private final UserService userService;

    @Autowired
    public CommentLikesServiceImpl(CommentLikesMapper commentLikesMapper, UserService userService) {
        this.commentLikesMapper = commentLikesMapper;
        this.userService = userService;
    }

    @Override
    public boolean isLiked(long articleID, long pID, String username) {
        return commentLikesMapper.isLiked(articleID, pID, userService.getIDByUsername(username)) != null;
    }

    @Override
    public void addCommentLikes(CommentLikes commentLikes) {
        commentLikesMapper.addCommentLikes(commentLikes);
    }

    @Override
    public void deleteALLCommentLikesByArticleID(long articleID) {
        commentLikesMapper.deleteCommentLikesByArticleID(articleID);
    }
}
