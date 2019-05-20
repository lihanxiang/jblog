package com.lee.jblog.service.impl;

import com.lee.jblog.pojo.UnreadNews;
import com.lee.jblog.redis.HashService;
import com.lee.jblog.service.RedisService;
import com.lee.jblog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class RedisServiceImpl implements RedisService {

    private final HashService hashService;
    private final UserService userService;

    @Autowired
    public RedisServiceImpl(HashService hashService, UserService userService) {
        this.hashService = hashService;
        this.userService = userService;
    }

    @Override
    public JSONObject getNews(String username) {
        JSONObject jsonObject = new JSONObject();
        int userID = userService.getIDByUsername(username);
        LinkedHashMap map = (LinkedHashMap)hashService.hashGetAll(String.valueOf(userID));
        if (map.size() == 0){
            jsonObject.put("result", 0);
        } else {
            int allNewsCount = (int)map.get("AllNewsCount");
            int commentsCount = (int)map.get("commentsCount");
            int leaveMessagesCount = (int)map.get("leaveMessagesCount");
            UnreadNews unreadNews = new UnreadNews(allNewsCount, commentsCount, leaveMessagesCount);
            jsonObject.put("result", unreadNews);
        }
        jsonObject.put("status", 200);
        return jsonObject;
    }

    @Override
    public void readOneMessageOnRedis(int userID, int messageType) {
        LinkedHashMap map = (LinkedHashMap)hashService.hashGetAll(String.valueOf(userID));
        int allNewsCount = (int)map.get("AllNewsCount");
        hashService.hashIncrement(String.valueOf(userID), "allNewsCount", -1);
        if (--allNewsCount == 0){
            hashService.delete(String.valueOf(userID), UnreadNews.class);
        } else if (messageType == 1){       //评论
            hashService.hashIncrement(String.valueOf(userID), "commentsCount", -1);
        } else {                            //留言
            hashService.hashIncrement(String.valueOf(userID), "leaveMessagesCount", -1);
        }
    }

    @Override
    public void readAllMessage(int userID, int messageType) {
        LinkedHashMap map = (LinkedHashMap)hashService.hashGetAll(String.valueOf(userID));
        int commentsCount = (int)map.get("commentsCount");
        int leaveMessagesCount = (int)map.get("leaveMessagesCount");
        if (commentsCount == 0 || leaveMessagesCount == 0){
            hashService.delete(String.valueOf(userID), UnreadNews.class);
        } else if (messageType == 1){       //评论
            hashService.hashIncrement(String.valueOf(userID), "allNewsCount", -commentsCount);
            hashService.hashIncrement(String.valueOf(userID), "commentsCount", -commentsCount);
        } else {                            //留言
            hashService.hashIncrement(String.valueOf(userID), "allNewsCount", -leaveMessagesCount);
            hashService.hashIncrement(String.valueOf(userID), "leaveMessagesCount", -leaveMessagesCount);
        }
    }
}
