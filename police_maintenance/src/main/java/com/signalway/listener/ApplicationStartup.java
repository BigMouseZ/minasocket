package com.signalway.listener;

import com.signalway.thread.QueueTimeOutManage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Created by ZhangGang on 2018/7/17.
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private  static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);
    @Value("${mina.socket.interval}")
    private int interval;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {

        logger.info("系统参数初始化中....");
        new Thread(new QueueTimeOutManage(interval)).start();
        logger.info("系统参数初始化完毕....");
    }

}

