package com.lee.jblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lee.jblog.component.JavaScriptCheck;
import com.lee.jblog.mapper.LeaveMessageMapper;
import com.lee.jblog.pojo.LeaveMessage;
import com.lee.jblog.pojo.UnreadNews;
import com.lee.jblog.redis.HashService;
import com.lee.jblog.role.Owner;
import com.lee.jblog.service.LeaveMessageLikesService;
import com.lee.jblog.service.LeaveMessageService;
import com.lee.jblog.service.UserService;
import com.lee.jblog.util.TimeUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    private final LeaveMessageMapper leaveMessageMapper;
    private final LeaveMessageLikesService leaveMessageLikesService;
    private final UserService userService;
    private final HashService hashService;

    @Autowired
    public LeaveMessageServiceImpl(LeaveMessageMapper leaveMessageMapper, LeaveMessageLikesService leaveMessageLikesService, UserService userService, HashService hashService) {
        this.leaveMessageMapper = leaveMessageMapper;
        this.leaveMessageLikesService = leaveMessageLikesService;
        this.userService = userService;
        this.hashService = hashService;
    }

    private void addUnReadMessage(LeaveMessage leaveMessage){
        if (leaveMessage.getRespondentID() != leaveMessage.getCommenterID()){
            boolean isKeyExit = hashService.hasKey(String.valueOf(leaveMessage.getRespondentID()));
            if (!isKeyExit) {
                UnreadNews messages = new UnreadNews(1, 0, 1);
                hashService.put(String.valueOf(leaveMessage.getRespondentID()), messages, UnreadNews.class);
            } else {
                hashService.hashIncrement(String.valueOf(leaveMessage.getRespondentID()),
                        "allNewsCount", 1);
                hashService.hashIncrement(String.valueOf(leaveMessage.getRespondentID()),
                        "leaveMessagesCount", 1);
            }
        }
    }

    @Override
    public void publishLeaveMessage(String leaveMessageContent, String pageName, String commenter) {
        TimeUtil timeUtil = new TimeUtil();
        String now = timeUtil.getFormatDateForFive();
        leaveMessageContent = JavaScriptCheck.javaScriptCheck(leaveMessageContent);
        LeaveMessage leaveMessage = new LeaveMessage(pageName, userService.getIDByUsername(commenter),
                userService.getIDByUsername(Owner.OWNER), now, leaveMessageContent);
        if (leaveMessage.getCommenterID() == leaveMessage.getRespondentID()){
            leaveMessage.setIsRead(0);
        }
        leaveMessageMapper.publishMessage(leaveMessage);
        addUnReadMessage(leaveMessage);
    }

    @Override
    public LeaveMessage replyLeaveMessage(LeaveMessage leaveMessage, String respondent) {
        TimeUtil timeUtil = new TimeUtil();
        String now = timeUtil.getFormatDateForFive();
        leaveMessage.setLeaveMessageDate(now);
        leaveMessage.setRespondentID(userService.getIDByUsername(respondent));
        if (leaveMessage.getCommenterID() != leaveMessage.getRespondentID()){
            leaveMessage.setIsRead(0);
        }
        leaveMessageMapper.publishMessage(leaveMessage);
        addUnReadMessage(leaveMessage);
        return leaveMessage;
    }

    @Override
    public JSONObject leaveMessageNewestReply(LeaveMessage leaveMessage, String commenter, String respondent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        JSONObject result = new JSONObject();
        result.put("commenter", commenter);
        result.put("respondent", respondent);
        result.put("leaveMessageContent", leaveMessage.getMessageContent());
        result.put("leaveMessageDate", leaveMessage.getLeaveMessageDate());
        jsonObject.put("result", result);
        return jsonObject;
    }

    @Override
    public JSONObject getAllMessages(String pageName, int pID, String username) {
        List<LeaveMessage> leaveMessages = leaveMessageMapper.getAllLeaveMessageDESC(pageName, pID);
        JSONObject resultJson = new JSONObject();
        JSONObject replyJson = new JSONObject();
        JSONObject leaveMessageJson;
        JSONArray replyJsonArray = new JSONArray();
        JSONArray leaveMessageJsonArray = new JSONArray();
        List<LeaveMessage> leaveMessagesReplies;

        resultJson.put("status", 200);
        for (LeaveMessage message :
                leaveMessages) {
            leaveMessageJson = new JSONObject();
            leaveMessageJson.put("ID", message.getID());
            leaveMessageJson.put("commenterID", message.getCommenterID());
            leaveMessageJson.put("leaveMessageDate", message.getLeaveMessageDate());
            leaveMessageJson.put("likes", message.getLikes());
            leaveMessageJson.put("messageContent", message.getMessageContent());
            if (username == null){
                leaveMessageJson.put("isLiked", 0);
            } else {
                if (leaveMessageLikesService.isLiked(pageName, message.getIsRead(),
                        userService.getIDByUsername(username))){
                    leaveMessageJson.put("isLiked", 0);
                } else {
                    leaveMessageJson.put("isLiked", 1);
                }
            }

            leaveMessagesReplies = leaveMessageMapper.getAllLeaveMessage(pageName, message.getID());
            replyJsonArray = new JSONArray();
            for (LeaveMessage reply :
                    leaveMessagesReplies) {
                replyJson = new JSONObject();
                replyJson.put("ID", reply.getID());
                replyJson.put("commenterID", userService.getUsernameByID(reply.getCommenterID()));
                replyJson.put("respondent", userService.getUsernameByID(reply.getRespondentID()));
                replyJson.put("leaveMessageDate", reply.getLeaveMessageDate());
                replyJson.put("messageContent", reply.getMessageContent());
                replyJsonArray.add(replyJson);
            }
            leaveMessageJson.put("replies", replyJsonArray);
            leaveMessageJsonArray.add(leaveMessageJson);
        }
        resultJson.put("result", leaveMessageJsonArray);
        return resultJson;
    }

    @Override
    public int updateLikesByPageNameAndID(String pageName, int ID) {
        leaveMessageMapper.updateLikesByPageNameAndID(pageName, ID);
        return leaveMessageMapper.getLikesByPageNameAndID(pageName, ID);
    }

    @Override
    public JSONObject getUserLeaveMessage(int rows, int pageNum, String username) {
        int respondentID = userService.getIDByUsername(username);
        PageHelper.startPage(pageNum, rows);
        List<LeaveMessage> leaveMessages = leaveMessageMapper.getLeaveMessages(respondentID);
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(leaveMessages);
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", 200);
        JSONObject leaveMessageJsopn;
        JSONArray leaveMessageJsopnArray = new JSONArray();
        for (LeaveMessage message :
                leaveMessages) {
            leaveMessageJsopn = new JSONObject();
            leaveMessageJsopn.put("ID", message.getID());
            leaveMessageJsopn.put("pID", message.getPID());
            leaveMessageJsopn.put("pageName", message.getPageName());
            leaveMessageJsopn.put("commenterID", message.getCommenterID());
            leaveMessageJsopn.put("leaveMessageDate", message.getLeaveMessageDate());
            leaveMessageJsopn.put("isRead", message.getIsRead());
            leaveMessageJsopnArray.add(leaveMessageJsopn);
        }

        resultJson.put("result", leaveMessageJsopnArray);
        resultJson.put("unreadCount", leaveMessageMapper.unreadCountByID(respondentID));

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        resultJson.put("pageInfo", pageJson);
        return resultJson;
    }

    @Override
    public JSONObject getFiveNewestComment(int rows, int pageNum) {
        JSONObject resultJson = new JSONObject();
        PageHelper.startPage(pageNum, rows);
        List<LeaveMessage> fiveMessages = leaveMessageMapper.getFiveNewestMessages();
        PageInfo<LeaveMessage> pageInfo = new PageInfo<>(fiveMessages);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (LeaveMessage message :
                fiveMessages) {
            jsonObject = new JSONObject();
            if (message.getPID() != 0){
                message.setMessageContent("@" + userService.getUsernameByID(message.getRespondentID())
                        + " " + message.getMessageContent());
            }
            jsonObject.put("ID", message.getID());
            jsonObject.put("pagePath", message.getPageName());
            jsonObject.put("commenterID", message.getCommenterID());
            jsonObject.put("leaveMessageDate", message.getLeaveMessageDate());
            jsonObject.put("messageContent", message.getMessageContent());
            jsonArray.add(jsonObject);
        }
        resultJson.put("status", 200);
        resultJson.put("result", jsonArray);
        JSONObject pageJson = new JSONObject();
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("total", pageInfo.getTotal());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());
        resultJson.put("pageInfo", pageJson);
        return resultJson;
    }

    @Override
    public int getMessagesCount() {
        return leaveMessageMapper.messagesCount();
    }

    @Override
    public int readOneMessage(int ID) {
        try {
            leaveMessageMapper.readOneMessage(ID);
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public JSONObject readAllMessage(String username) {
        int respondentID = userService.getIDByUsername(username);
        leaveMessageMapper.readAllMessageByRespondentID(respondentID);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        jsonObject.put("result", "success");
        return jsonObject;
    }
}
