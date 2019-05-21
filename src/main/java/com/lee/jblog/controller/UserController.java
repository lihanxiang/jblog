package com.lee.jblog.controller;

import com.lee.jblog.service.*;
import com.sun.xml.internal.bind.v2.model.core.ID;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.lee.jblog.pojo.User;
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

    @PostMapping("saveUserInfo")
    public JSONObject saveUserInfo(User user, @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 403);
            jsonObject.put("result", "Unauthorized");
            return jsonObject;
        }
        return userService.updateUser(user, username);
    }

    @PostMapping("/getAllComments")
    public JSONObject getAllComments(@RequestParam("rows") String rows,
                                     @RequestParam("pageNum") String pageNum,
                                     @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 403);
            jsonObject.put("result", "Unauthorized");
            return jsonObject;
        }
        return commentService.getUserAllComment(Integer.parseInt(rows), Integer.parseInt(pageNum), username);
    }

    @PostMapping("/getAllLeaveMessage")
    public JSONObject getAllLeaveMessage(@RequestParam("rows") String rows,
                                         @RequestParam("pageNum") String pageNum,
                                         @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 403);
            jsonObject.put("result", "Unauthorized");
            return jsonObject;
        }
        return leaveMessageService.getUserLeaveMessage(Integer.parseInt(rows), Integer.parseInt(pageNum), username);
    }

    @PostMapping("/sendPrivateMessage")
    public JSONObject sendPrivateMessage(@RequestParam("privateMessage") String privateMessage,
                                         @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 403);
            jsonObject.put("result", "Unauthorized");
            return jsonObject;
        }
        return privateMessageService.publishPrivateMessage(privateMessage, username);
    }

    @PostMapping("/getPrivateMessage")
    public JSONObject getPrivateMessage(@RequestParam("rows") String rows,
                                        @RequestParam("pageNum") String pageNum,
                                        @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 403);
            jsonObject.put("result", "Unauthorized");
            return jsonObject;
        }
        return privateMessageService.getPrivateMessageByPublisher(username,
                Integer.parseInt(rows), Integer.parseInt(pageNum));
    }

    @GetMapping("/readOneMessage")
    @ResponseBody
    public int readOneMessage(@RequestParam("ID") int ID,
                              @RequestParam("messageType") int messageType,
                              @AuthenticationPrincipal Principal principal){
        redisService.readOneMessageOnRedis(userService.getIDByUsername(principal.getName()), messageType);
        if (messageType == 1){
            return commentService.readOneComment(ID);
        } else {
            return leaveMessageService.readOneMessage(ID);
        }
    }

    @GetMapping("/readAllMessage")
    @ResponseBody
    public JSONObject readAllMessage(@RequestParam("messageType") int messageType,
                              @AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 403);
            jsonObject.put("result", "Unauthorized");
            return jsonObject;
        }
        redisService.readAllMessage(userService.getIDByUsername(principal.getName()), messageType);
        if (messageType == 1){
            return commentService.readAllComment(username);
        } else {
            return leaveMessageService.readAllMessage(username);
        }
    }

    @PostMapping("/getUnreadNews")
    @ResponseBody
    public JSONObject getUnreadNews(@AuthenticationPrincipal Principal principal){
        String username;
        try {
            username = principal.getName();
        } catch (NullPointerException e){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 403);
            jsonObject.put("result", "Unauthorized");
            return jsonObject;
        }
        return redisService.getNews(username);
    }
}
