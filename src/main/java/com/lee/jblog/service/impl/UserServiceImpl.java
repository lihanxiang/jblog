package com.lee.jblog.service.impl;

import com.lee.jblog.mapper.UserMapper;
import com.lee.jblog.pojo.User;
import com.lee.jblog.role.RoleLevel;
import com.lee.jblog.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public int getIDByUsername(String username) {
        return userMapper.getIDByUsername(username);
    }

    @Override
    public String getUsernameByID(int ID) {
        return userMapper.getUsernameByID(ID);
    }

    @Override
    public int addUser(User user) {
        user.setUsername(user.getUsername().trim());
        String username = user.getUsername();

        if (username.length() > 20 || username.equals("")){
            return 3;
        }
        if (isUsernameExit(username)){
            return 2;
        }
        userMapper.addUser(user);
        int userID = user.getID();
        addRole(userID, RoleLevel.ROLE_USER);
        return 1;
    }

    @Override
    public void addRole(int userID, int roleID) {
        userMapper.addRole(userID, roleID);
    }

    @Override
    public void updatePassword(String username, String password) {
        userMapper.updatePassword(username, password);
    }

    @Override
    public void updateRecentlyLogin(String username, String recentlyLogin) {
        userMapper.updateRecentlyLogin(username, recentlyLogin);
    }

    @Override
    public boolean isUsernameExit(String username) {
        return userMapper.getUserByUsername(username) != null;
    }

    @Override
    public boolean isAdmin(String username) {
        int ID = userMapper.getIDByUsername(username);
        int roleID = userMapper.getRoleByID(ID);
        return roleID == 2;
    }

    @Override
    public JSONObject getUserInfoByUsername(String username) {
        User user = userMapper.getUserByUsername(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", 200);
        JSONObject userJson = new JSONObject();
        userJson.put("username", user.getUsername());
        userJson.put("phone", user.getPhone());
        userJson.put("gender", user.getGender());
        userJson.put("realName", user.getRealName());
        userJson.put("birthday", user.getBirthday());
        userJson.put("email", user.getEmail());
        userJson.put("personalProfile", user.getPersonalProfile());
        jsonObject.put("result", userJson);
        return jsonObject;
    }

    @Override
    public JSONObject updateUser(User user, String username) {
        JSONObject resultJson = new JSONObject();
        user.setUsername(user.getUsername().trim());
        String newName = user.getUsername();
        if (newName.length() > 20){
            resultJson.put("status", 501);
            return resultJson;
        } else if (newName.equals("")){
            resultJson.put("status", 502);
            return resultJson;
        }
        if (!newName.equals(username)){
            if (isUsernameExit(newName)){
                resultJson.put("status", 500);
                return resultJson;
            }
            resultJson.put("status", 200);
            SecurityContextHolder.getContext().setAuthentication(null);
        } else {
            resultJson.put("status", 201);
        }
        userMapper.updateUser(user, newName);
        return resultJson;
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();
    }
}
