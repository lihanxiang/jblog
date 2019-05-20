package com.lee.jblog.service;

import com.lee.jblog.pojo.LeaveMessage;
import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

public interface LeaveMessageService {

    @Transactional
    void publishLeaveMessage(String leaveMessageContent, String pageName, String commenter);

    @Transactional
    LeaveMessage replyLeaveMessage(LeaveMessage leaveMessage, String respondent);

    JSONObject leaveMessageNewestReply(LeaveMessage leaveMessage, String commenter, String respondent);

    JSONObject getAllMessages(String pageName, int pID, String username);

    int updateLikesByPageNameAndID(String pageName, int ID);

    JSONObject getUserLeaveMessage(int rows, int pageNum, String username);

    JSONObject getFiveNewestComment(int rows, int pageNum);

    int getMessagesCount();

    int readOneMessage(int ID);

    JSONObject readAllMessage(String username);
}
