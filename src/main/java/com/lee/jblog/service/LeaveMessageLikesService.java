package com.lee.jblog.service;

import com.lee.jblog.pojo.LeaveMessageLikes;

public interface LeaveMessageLikesService {

    boolean isLiked(String pageName, int pID, int likeID);

    void addLeaveMessageLikes(LeaveMessageLikes leaveMessageLikes);
}
