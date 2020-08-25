package com.cyp.robot.mina;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jun on 2020/8/4 22:57.
 */
@RestController
@RequestMapping("/mina")
@Slf4j
public class MinaController {

    @RequestMapping("/test")
    public String test(HttpServletRequest request) {
        String w = request.getParameter("w");
        log.info("keyWord=" + w);
        MinaClient.sendMsg2Service(w);
//        MessageHandler.Message message = null;
//        try {
//            message = MessageHandler.messageQueue.poll(1000, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        if (message != null) {
//            return message.message;
//        }
        return "调用接口启动mina";
    }


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


}
