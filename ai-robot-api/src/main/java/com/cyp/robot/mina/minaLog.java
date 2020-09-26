package com.cyp.robot.mina;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;

/**
 * Created by Jun on 2020/9/18 23:00.
 */
public class minaLog extends LoggingFilter {

    @Override
    public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
        System.out.println("覆写LoggingFilter的sessionIdle方法");
        nextFilter.sessionIdle(session, status);
    }
}
