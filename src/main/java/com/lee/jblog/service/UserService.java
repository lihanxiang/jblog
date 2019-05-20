package com.lee.jblog.service;

import com.lee.jblog.pojo.User;
import net.sf.json.JSONObject;

public interface UserService {

    User getUserByUsername(String username);

    User getUserByPhone(String phone);

    int getIDByUsername(String username);

    String getUsernameByID(int ID);

    int addUser(User user);

    void addRole(int userID, int roleID);

    void updatePassword(String username, String password);

    void updateRecentlyLogin(String username, String recentlyLogin);

    boolean isUsernameExit(String username);

    boolean isAdmin(String username);

    JSONObject getUserInfoByUsername(String username);

    JSONObject updateUser(User user, String username);

    int getUserCount();
}
