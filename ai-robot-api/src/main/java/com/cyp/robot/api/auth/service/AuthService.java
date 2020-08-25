package com.cyp.robot.api.auth.service;


import com.cyp.po.UserInfo;

public interface AuthService {

    Boolean insert(UserInfo userInfo);

    Boolean login(UserInfo userInfo);

}
