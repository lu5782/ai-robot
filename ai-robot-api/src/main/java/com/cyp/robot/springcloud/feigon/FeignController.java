package com.cyp.robot.springcloud.feigon;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by luyijun on 2020/3/9 17:55.
 */
@RestController
public class FeignController {

    @Resource
    private StudentService studentService;

    @RequestMapping("/feign")
    public String getAllStudent() {
        String allStudent = studentService.hello();
        System.out.println("allStudent=" + allStudent);
        return allStudent;
    }


    @RequestMapping("/offLine")
    public String offLine() {
        studentService.offLine();
        return "eureka offLine success";
    }
}
