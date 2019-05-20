package com.lee.jblog.pojo;

import lombok.Data;

@Data
public class LeaveMessage {

    private int ID;

    //留言页
    private String pageName;

    /**
     * 回复的父亲 ID，0 表示每层楼的第一个评论，
     * 其他数字则表示对这个评论的回复
     */
    private int pID = 0;

    //留言者
    private int commenterID;

    //被回复者 ID
    private int respondentID;

    //留言日期
    private String leaveMessageDate;

    //点赞数
    private int likes;

    //留言内容
    private String messageContent;

    //1 - 已读    0 - 未读
    private int isRead = 1;

    public LeaveMessage(String pageName, int commenterID, int respondentID,
                        String leaveMessageDate, String messageContent) {
        this.pageName = pageName;
        this.commenterID = commenterID;
        this.respondentID = respondentID;
        this.leaveMessageDate = leaveMessageDate;
        this.messageContent = messageContent;
    }
}
