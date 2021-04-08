package com.cyp.robot.nio.socket;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by luyijun on 2020/9/19 21:14.
 */
@Component
@ServerEndpoint("/websocket")
@Slf4j
public class MyWebSocket01 {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static final Map<String, MyWebSocket01> webSockets = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.put(session.getId(), this);
        onlineCount++;
        log.info("当前websocket连接数：" + onlineCount);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSockets.remove(this);
        onlineCount--;
        log.info("当前websocket连接数：" + onlineCount);
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        sendMessage(message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("websocket发生错误：" + error);
    }


    public void sendMessage(String message) {
        //同步发送 发送第二条时，必须等第一条发送完成
        try {
            this.session.getBasicRemote().sendText(set(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //异步发送
        //webSocket.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }


    public String set(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        return jsonObject.toString();
    }

}
