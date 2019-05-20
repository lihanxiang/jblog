package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class PrivateMessage {

    private int ID;

    private String privateMessage;

    private int publisherID;

    private int replierID;

    private int replyContent;

    private String publishDate;

    public PrivateMessage(){}

    public PrivateMessage(String privateMessage, int publisherID, String publishDate) {
        this.privateMessage = privateMessage;
        this.publisherID = publisherID;
        this.publishDate = publishDate;
    }
}
