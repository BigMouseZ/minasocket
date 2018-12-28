package com.signalway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author ZhangGang
 * @createTime 2018/8/20
 **/
@SpringBootApplication
public class PolicemaintenanceApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PolicemaintenanceApplication.class);
    }
    /**
     * @param args 入参
     * @Description 功能：
     **/
    public static void main(String[] args) {
        SpringApplication.run(PolicemaintenanceApplication.class, args);
        SpringApplication springApplication = new SpringApplication(PolicemaintenanceApplication.class);
        springApplication.run(args);
    }

}
