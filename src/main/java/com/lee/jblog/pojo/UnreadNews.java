package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class UnreadNews {

    private int allNewsCount;

    private int commentsCount;

    private int messagesCount;

    public UnreadNews() {}

    public UnreadNews(int allNewsCount, int commentsCount, int messagesCount) {
        this.allNewsCount = allNewsCount;
        this.commentsCount = commentsCount;
        this.messagesCount = messagesCount;
    }
}
