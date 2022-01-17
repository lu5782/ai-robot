package com.cyp.robot.springcloud.ribbon;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by luyijun on 2020/3/9 11:09.
 */
@RestController
public class RibbonController {

    @Resource
    RestTemplate restTemplate;

    @RequestMapping("/ribbon")
    public String client() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://EUREKA-PROVIDER/hello", String.class);
        String body = forEntity.getBody();
        System.out.println(body);
        return body;
    }

//    @RequestMapping("/offLine")
//    public String offLine(){
//        DiscoveryManager.getInstance().shutdownComponent();
//        return "eureka offLine success";
//    }

}
