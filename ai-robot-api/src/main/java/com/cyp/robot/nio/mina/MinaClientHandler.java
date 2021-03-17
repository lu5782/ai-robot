package com.cyp.robot.nio.mina;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.sql.Timestamp;

/**
 * Created by Jun on 2020/8/2 10:01.
 */

public class MinaClientHandler implements IoHandler {
    private int count = 0;

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        System.out.println("client与:" + ioSession.getRemoteAddress().toString() + "建立连接");
    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
        count++;
        System.out.println("第 " + count + " 个 client 登陆！address： : " + ioSession.getRemoteAddress());

    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
        System.out.println("client与:" + ioSession.getRemoteAddress().toString() + "断开连接");

    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        int idleCount = ioSession.getIdleCount(idleStatus);
        System.out.println("【时间】=" + new Timestamp(System.currentTimeMillis()) + ",心跳机制=" + idleCount);
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {

    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        System.out.println("client接受信息=" + o.toString());
//        MessageHandler.messageQueue.add(message);
//        if (str.trim().equalsIgnoreCase("quit")) {
//            ioSession.close();
//            return;
//        }
//        Date date = new Date();
//        // 向输出流中写东西
//        ioSession.write(date.toString());
//        System.out.println("Message written...");
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println("client发送信息=" + o.toString());

    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {

    }
}
