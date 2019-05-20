package com.lee.jblog.service.impl;

import com.lee.jblog.pojo.Role;
import com.lee.jblog.pojo.User;
import com.lee.jblog.service.UserService;
import com.lee.jblog.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomizeUserServiceImpl implements UserDetailsService{

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userService.getUserByPhone(phone);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        TimeUtil timeUtil = new TimeUtil();
        String recentlyLogin = timeUtil.getFormatDateForSix();
        userService.updateRecentlyLogin(user.getUsername(), recentlyLogin);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role :
                user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }
}
