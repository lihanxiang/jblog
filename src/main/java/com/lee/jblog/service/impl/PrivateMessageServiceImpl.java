package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.PrivateMessageMapper;
import com.lee.jblog.pojo.PrivateMessage;
import com.lee.jblog.service.PrivateMessageService;
import com.lee.jblog.service.UserService;
import com.lee.jblog.util.TimeUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

    private final PrivateMessageMapper privateMessageMapper;

    private final UserService userService;

    @Autowired
    public PrivateMessageServiceImpl(PrivateMessageMapper privateMessageMapper, UserService userService) {
        this.privateMessageMapper = privateMessageMapper;
        this.userService = userService;
    }


    @Override
    public JSONObject publishPrivateMessage(String privateMessageContent, String username) {
        TimeUtil timeUtil = new TimeUtil();
        PrivateMessage privateMessage = new PrivateMessage(privateMessageContent,
                userService.getIDByUsername(username), timeUtil.getFormatDateForSix());
        privateMessageMapper.publishPrivateMessage(privateMessage);
        JSONObject resultJson = new JSONObject();
        resultJson.put("status", 200);
        return resultJson;
    }

    @Override
    public JSONObject getPrivateMessageByPublisher(String publisher, int rows, int pageNum) {
        return null;
    }

    @Override
    public JSONObject getAllMessage() {
        return null;
    }

    @Override
    public JSONObject relpyPrivateMessage(String replyContent, String username, int replierID) {
        return null;
    }
}
