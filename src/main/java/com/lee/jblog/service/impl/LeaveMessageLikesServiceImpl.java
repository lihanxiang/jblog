package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.LeaveMessageLikesMapper;
import com.lee.jblog.pojo.LeaveMessageLikes;
import com.lee.jblog.service.LeaveMessageLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveMessageLikesServiceImpl implements LeaveMessageLikesService {

    private final LeaveMessageLikesMapper leaveMessageLikesMapper;

    @Autowired
    public LeaveMessageLikesServiceImpl(LeaveMessageLikesMapper leaveMessageLikesMapper) {
        this.leaveMessageLikesMapper = leaveMessageLikesMapper;
    }

    @Override
    public boolean isLiked(String pageName, int pID, int likeID) {
        return leaveMessageLikesMapper.isLiked(pageName, pID, likeID) != null;
    }

    @Override
    public void addLeaveMessageLikes(LeaveMessageLikes leaveMessageLikes) {
        leaveMessageLikesMapper.addLeaveMessageLikes(leaveMessageLikes);
    }
}
