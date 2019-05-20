package com.lee.jblog.service;

import net.sf.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

public interface PrivateMessageService {

    JSONObject publishPrivateMessage(String privateMessageContent, String username);

    JSONObject getPrivateMessageByPublisher(String publisher, int rows, int pageNum);

    JSONObject getAllMessage();

    @Transactional
    JSONObject relpyPrivateMessage(String replyContent, String username, int replierID);
}
