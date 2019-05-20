package com.lee.jblog.pojo;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private int ID;
    private String username;
    private String password;
    private String phone;
    private String gender;
    private String realName;
    private String birthday;
    private String email;
    private String personalProfile;
    private String recentlyLogin;
    private List<Role> roles;

    public User(){}

    public User(String username, String password, String phone, String gender) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
    }

    public User(String username, String password, String phone, String gender,
                String realName, String birthday, String email, String personalProfile,
                String recentlyLogin) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.realName = realName;
        this.birthday = birthday;
        this.email = email;
        this.personalProfile = personalProfile;
        this.recentlyLogin = recentlyLogin;
    }
}
