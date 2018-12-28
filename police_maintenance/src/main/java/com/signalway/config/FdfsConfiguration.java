package com.signalway.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * Created by ZhangGang on 2018/6/22.
 * 增加配置类（此类里面什么都不需要做，也可以把@Import和@EnableMBeanExport放在启动类上，我是感觉启动类上放太多东西不好看就增加了一个配置类）
 */
@Configuration
@Import(FdfsClientConfig.class) //注解，就可以拥有带有连接池的FastDFS Java客户端了
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)// 解决jmx重复注册bean的问题

public class FdfsConfiguration {



}
