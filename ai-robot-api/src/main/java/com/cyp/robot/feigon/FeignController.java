package com.cyp.robot.feigon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Jun on 2020/3/9 17:55.
 */
@RestController
public class FeignController {

    @Autowired
    private StudentService studentService;

    @RequestMapping("/feign")
    public String getAllStudent() {
        String allStudent = studentService.hello();
        System.out.println("allStudent="+allStudent);
        return allStudent;
    }

    static{
        i = 20;
        //这里的i， 是不能被用作运算的， 因为本质上 i 还未被定义
    }

    public static int i = 1;

    static{
        i = 23;
    }

    @RequestMapping("/offLine")
    public String offLine(){
        studentService.offLine();
        return "eureka offLine success";
    }
}
