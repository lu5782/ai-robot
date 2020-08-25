package com.cyp.robot.api.auth.controller;


import com.cyp.po.UserInfo;
import com.cyp.robot.api.auth.service.AuthService;
import com.cyp.robot.api.common.dto.AuthReq;
import com.cyp.robot.api.common.dto.ResultDto;
import com.cyp.robot.utils.JwtUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    @RequestMapping(value = "/token")
    public ResultDto token(@RequestBody(required = false) AuthReq authReq, HttpServletRequest request, HttpServletResponse response) {
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
    public ResultDto register(@RequestBody UserInfo userInfo) {

        Boolean flag = authService.insert(userInfo);
        if (flag) {
            return new ResultDto(ResultDto.SUCCESS_CODE, ResultDto.SUCCESS_MESSAGE, null);
        } else {
            return new ResultDto(ResultDto.ERROR_CODE, "用户名、邮箱或手机号已经存在", null);
        }
    }

    @RequestMapping("/login")
    public ResultDto login(@RequestBody UserInfo userInfo) {
        Boolean flag = authService.login(userInfo);
        if (flag) {
            return new ResultDto(ResultDto.SUCCESS_CODE, ResultDto.SUCCESS_MESSAGE, null);
        } else {
            return new ResultDto(ResultDto.ERROR_CODE, "用户名、邮箱或手机号已经存在", null);
        }
    }


}
