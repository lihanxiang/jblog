package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class LeaveMessageLikes {

    private long ID;

    private String pageName;

    private int pID;

    private int likerID;

    private String likeDate;

    public LeaveMessageLikes(){}

    public LeaveMessageLikes(String pageName, int pID, int likerID, String likeDate) {
        this.pageName = pageName;
        this.pID = pID;
        this.likerID = likerID;
        this.likeDate = likeDate;
    }
}
