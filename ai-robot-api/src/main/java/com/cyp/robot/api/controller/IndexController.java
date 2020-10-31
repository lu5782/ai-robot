package com.cyp.robot.api.controller;

import lombok.extern.slf4j.Slf4j;;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/websocket")
    public String websocket() {
        return "webSocket";
    }

}
