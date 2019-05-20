package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class CommentLikes {

    private long ID;

    //文章 ID
    private long articleID;

    //评论者 ID
    private long commenterID;

    //点赞者 ID
    private int likerID;

    //点赞时间
    private String likeDate;

    public CommentLikes(){}

    public CommentLikes(long articleID, long commenterID, int likerID, String likeDate) {
        this.articleID = articleID;
        this.commenterID = commenterID;
        this.likerID = likerID;
        this.likeDate = likeDate;
    }
}
