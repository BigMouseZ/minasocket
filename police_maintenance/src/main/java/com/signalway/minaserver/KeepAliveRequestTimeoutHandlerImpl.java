package com.signalway.minaserver;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ZhangGang on 2018/7/2.
 */
public class KeepAliveRequestTimeoutHandlerImpl implements KeepAliveRequestTimeoutHandler {
    Logger logger = LoggerFactory.getLogger(KeepAliveRequestTimeoutHandlerImpl.class);
    @Override
    public void keepAliveRequestTimedOut(KeepAliveFilter filter, IoSession session) throws Exception {
        logger.warn("客户端挂了  服务端我把session给干掉了！");
        session.closeNow();
    }
}
