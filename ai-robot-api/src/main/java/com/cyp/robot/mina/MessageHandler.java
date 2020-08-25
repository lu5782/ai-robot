package com.cyp.robot.mina;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jun on 2020/8/5 21:29.
 */
@Slf4j
@Component
public class MessageHandler {

    //    public static LinkedList<Message> messageQueue = new LinkedList<>();
//    public static LinkedBlockingDeque<Message> messageQueue = new LinkedBlockingDeque<>();
    public static LinkedBlockingQueue<Message> messageQueue = new LinkedBlockingQueue<>();


    @PostConstruct
    public void init() {
        log.info("MessageHandler init");
        MinaServer.connect();
        MinaClient.connect();

//        new Thread(MessageHandler::handleMessage).start();

//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.println("请输入：");
//            String message = sc.next();
//            if (message != null) {
//                MinaClient.sendMsg2Service(message);
//            }
//        }
    }


    private static void handleMessage() {
        while (true) {
//            System.out.println("messageQueue.size() = " + messageQueue.size());
            Message message = messageQueue.poll();
            if (message != null) {
                handle(message);
//                new Thread(() -> handle(message)).start();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handle(Message message) {
        switch (message.id) {
            case 1:
                handleClient(message);
                break;
            case 2:
                handleService(message);
                break;
            default:
                break;
        }
    }

    static void handleClient(Message message) {
        System.out.println("接受到服务端的message = " + message.message);
    }

    static void handleService(Message message) {
        System.out.println("接受到客户端的message = " + message.message);
        Message message1 = new Message();
        message1.id = 1;
        message1.message = "服务端发送的消息";
        messageQueue.add(message1);
    }

    public static class Message {
        public int id;
        public int type;
        public String message;
    }

}
