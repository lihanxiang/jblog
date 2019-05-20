package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class Comment {

    private long ID;

    //文章 ID
    private long articleID;

    /**
     * 回复的父亲 ID，0 表示每层楼的第一个评论，
     * 其他数字则表示对这个评论的回复
     */
    private long pID = 0;

    //评论者
    private int commenterID;;

    //被评论者 ID
    private int respondentID;

    //评论日期
    private String commentDate;

    //点赞数
    private int likes;

    //评论内容
    private String commentContent;

    //1 - 已读    0 - 未读
    private int isRead = 1;
}
