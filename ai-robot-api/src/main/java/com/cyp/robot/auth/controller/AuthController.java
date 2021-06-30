package com.cyp.robot.auth.controller;


import com.cyp.po.UserInfo;
import com.cyp.robot.auth.service.AuthService;
import com.cyp.robot.api.common.dto.ResultDto;
import com.cyp.robot.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    private AuthService authService;

    @RequestMapping(value = "/token")
    public ResultDto<String> token(HttpServletResponse response) {
        log.info("获取token");
        ResultDto<String> restResult = new ResultDto<>();
        try {
            JwtUtils.createJwt(response);
            restResult.setData("get token success");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restResult;
    }


    @RequestMapping("/register")
    public ResultDto<Object> register(@RequestBody UserInfo userInfo) {
        log.info("注册账号");
        Boolean flag = authService.insert(userInfo);
        if (flag) {
            return new ResultDto<>(ResultDto.SUCCESS_CODE, ResultDto.SUCCESS_MESSAGE, null);
        } else {
            return new ResultDto<>(ResultDto.ERROR_CODE, "用户名、邮箱或手机号已经存在", null);
        }
    }

    @RequestMapping("/login")
    public ResultDto<Object> login(@RequestBody UserInfo userInfo, HttpServletResponse response) {
        log.info("登录账号");
        Boolean flag = authService.login(userInfo);
        if (flag) {
            log.info("登录成功，返回token");
            JwtUtils.createJwt(response);
            return new ResultDto<>(ResultDto.SUCCESS_CODE, ResultDto.SUCCESS_MESSAGE, null);
        } else {
            return new ResultDto<>(ResultDto.ERROR_CODE, "用户名或密码错误", null);
        }
    }


}
