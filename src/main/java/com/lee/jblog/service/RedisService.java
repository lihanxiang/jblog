package com.lee.jblog.service;

import net.sf.json.JSONObject;

public interface RedisService {

    JSONObject getNews(String username);

    void readOneMessageOnRedis(int userID, int messageType);

    void readAllMessage(int userID, int messageType);
}
