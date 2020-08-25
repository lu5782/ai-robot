package com.cyp.robot.api.ai.controller;

import com.cyp.robot.api.common.annotation.RequestLog;
import lombok.extern.slf4j.Slf4j;;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "index";
    }


    @RequestMapping("/console")
    @RequestLog(value = "value", name = "laowang", age = 17)
    public String console() {
        return "console";
    }


}
