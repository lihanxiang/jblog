package com.lee.jblog.controller;

import com.lee.jblog.service.*;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@SuppressWarnings("all")
public class UserController {

    private final UserService userService;
    private final CommentService commentService;
    private final LeaveMessageService leaveMessageService;
    private final PrivateMessageService privateMessageService;
    private final RedisService redisService;

    @Autowired
    public UserController(RedisService redisService, PrivateMessageService privateMessageService, LeaveMessageService leaveMessageService, CommentService commentService, UserService userService) {
        this.redisService = redisService;
        this.privateMessageService = privateMessageService;
        this.leaveMessageService = leaveMessageService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/getUserInfo")
    public JSONObject getUserInfo(@AuthenticationPrincipal Principal principal){
        String username = principal.getName();
        return userService.getUserInfoByUsername(username);
    }
}
