package com.lee.jblog.service;

import com.lee.jblog.pojo.CommentLikes;

public interface CommentLikesService {

    boolean isLiked(long articleID, long pID, String username);

    void addCommentLikes(CommentLikes commentLikes);

    void deleteALLCommentLikesByArticleID(long articleID);
}
