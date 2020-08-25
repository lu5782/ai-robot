package com.cyp.robot.mina;

import com.cyp.robot.mina.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Jun on 2020/8/2 10:05.
 */
@Slf4j
public class MinaServerHandler implements IoHandler {

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {

    }

    @Override
    public void sessionOpened(IoSession ioSession) throws Exception {

    }

    @Override
    public void sessionClosed(IoSession ioSession) throws Exception {

    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {
//        int idleCount = ioSession.getIdleCount(idleStatus);
//        log.info("【时间】=" + new Timestamp(System.currentTimeMillis()) + ",心跳机制=" + idleCount);
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {

    }

    @Override
    public void messageReceived(IoSession ioSession, Object o) throws Exception {
        log.info("service接受=" + o.toString());
        Thread.sleep(1000);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        ioSession.write(timestamp.toString());
//        log.info("发送到client message=" + date.toString());
        MessageHandler.Message message = new MessageHandler.Message();
        message.id = 2;
        message.message = timestamp.toString();
        MessageHandler.messageQueue.add(message);
    }

    @Override
    public void messageSent(IoSession ioSession, Object o) throws Exception {
        System.out.println("service发送=" + o.toString());
    }

    @Override
    public void inputClosed(IoSession ioSession) throws Exception {

    }
}
