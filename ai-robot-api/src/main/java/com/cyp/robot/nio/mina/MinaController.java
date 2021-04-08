package com.cyp.robot.nio.mina;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by luyijun on 2020/8/4 22:57.
 */
@RestController
@RequestMapping("/mina")
@Slf4j
public class MinaController {


    @RequestMapping("/client/restart")
    public void restart() {
        if (MinaClient.session != null) {
            log.info("关闭服务");
            MinaClient.close();
        }
        log.info("开启连接");
        MinaClient.connect();
    }


    @RequestMapping("/client/close")
    public void close() {
        MinaClient.close();
    }


    @RequestMapping("/service/restart")
    public void restart1() {
        log.info("调用接口启动mina service服务");
        MinaClient.close();
        MinaClient.connect();
    }

    @RequestMapping("/service/close")
    public void close1() {
        MinaClient.close();
    }


//    @PostConstruct
//    public void init() {
//        MinaServer.connect();
//    }


}
