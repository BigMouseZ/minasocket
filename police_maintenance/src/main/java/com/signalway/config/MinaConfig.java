package com.signalway.config;

import com.signalway.minaserver.KeepAliveMessageFactoryImpl;
import com.signalway.minaserver.KeepAliveRequestTimeoutHandlerImpl;
import com.signalway.minaserver.MessageProtocolCodecFactory;
import com.signalway.minaserver.MinaServerHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Created by ZhangGang on 2018/6/29.
 */
@Configuration
public class MinaConfig {
    private static final Logger logger = LoggerFactory.getLogger(MinaConfig.class);
    /**
     * 30秒后超时
     */
    private static final int IDELTIMEOUT = 60;
    private static final int BUFFERSIZE = 1024 * 1024 * 2;//2M
    /**
     * 15秒发送一次心跳包
     */
    @Value("${mina.socket.heart}")
    private int heart;
    @Value("${mina.socket.host}")
    private String host;
    @Value("${mina.socket.port}")
    private int port;


    /**
     * @return LoggingFilter
     * @Description 功能：
     **/
    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    /**
     * @return InetSocketAddress
     * @Description 功能：
     **/
    @Bean
    public InetSocketAddress inetSocketAddress() {
        return new InetSocketAddress(host, port);
    }

    /**
     * @return acceptor
     * @throws Exception 抛出所有异常
     * @Description 功能：开启监听服务
     **/
    @Bean
    public IoAcceptor ioAcceptor() throws Exception {

        IoAcceptor acceptor = new NioSocketAcceptor();
        // acceptor.getFilterChain().addLast( "logger", loggingFilter() );
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MessageProtocolCodecFactory()));
        acceptor.getFilterChain().addLast("ThreadPool", new ExecutorFilter(Executors.newCachedThreadPool()));

        acceptor.getSessionConfig().setReadBufferSize(BUFFERSIZE);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDELTIMEOUT);
        //心跳检测开始
        KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
        KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory, IdleStatus.BOTH_IDLE);
        //设置是否forward 到下一个filter
        heartBeat.setForwardEvent(true);
        //设置心跳频率
        heartBeat.setRequestInterval(heart);
        //设置失败处理handler
        heartBeat.setRequestTimeoutHandler(new KeepAliveRequestTimeoutHandlerImpl());
        acceptor.getFilterChain().addLast("heartbeat", heartBeat);
        //心跳检测结束
        acceptor.setHandler(new MinaServerHandler());
        acceptor.bind(inetSocketAddress());
        logger.info("服务器在端口：" + host + ":" + port + "已经启动");
        return acceptor;
    }
}
