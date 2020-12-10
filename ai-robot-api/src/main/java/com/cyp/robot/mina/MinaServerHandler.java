package com.cyp.robot.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.sql.Timestamp;

/**
 * Created by Jun on 2020/8/2 10:05.
 */
@Slf4j
public class MinaServerHandler implements IoHandler {

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        log.info("mina 服务器-sessionCreated");
        ioSession.write("sessionCreated");
    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {
        log.info("mina 服务器-sessionOpened-服务器第一次连接");
        ioSession.write("sessionOpened");
    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {
        log.info("mina 服务器-sessionClosed");
        ioSession.closeNow();
    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
        int idleCount = ioSession.getIdleCount(idleStatus);
        log.info("mina 服务器-sessionIdle,心跳机制=" + idleCount);
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {
        log.info("mina 服务器-exceptionCaught");
        ioSession.write("exceptionCaught");
    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        log.info("mina 服务器-接受到信息=" + o.toString());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String responseMessage = "服务器" + timestamp.toString() + "接受到信息:" + o.toString();
        ioSession.write(responseMessage);
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println("mina 服务器-发送消息到客户端=" + o.toString());
        ioSession.write(o.toString());
    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {
        log.info("mina 服务器-inputClosed");
        ioSession.write("inputClosed");
        Thread.sleep(10 * 1000);
    }
}
