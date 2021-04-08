package com.cyp.robot.api.controller;

import lombok.extern.slf4j.Slf4j;;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author :        luyijun
 * @Date :          2020/10/29 22:23
 * @Description :   首页
 */
@Slf4j
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "clock";
    }

    @RequestMapping("/nas")
    public String nas() {
        return "nas";
    }

    @RequestMapping("/websocket01")
    public String websocket01() {
        return "webSocket01";
    }

    @RequestMapping("/websocket02")
    public String websocket02() {
        return "webSocket02";
    }


}
