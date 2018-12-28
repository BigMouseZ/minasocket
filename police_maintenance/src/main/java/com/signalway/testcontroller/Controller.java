package com.signalway.testcontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by ZhangGang on 2018/8/20.
 */
@RestController
public class Controller {
    private  static final Logger logger = LoggerFactory.getLogger(Controller.class);
    /**
     * @param reqString 接口json穿串
     * @throws IOException IO异常
     * @Description 功能：
     * @return strung。
     **/
    @RequestMapping(value = "/hello",method = RequestMethod.POST)
    public String helloController(String reqString) throws IOException {
       /*   级别依次为【从高到低】：FATAL > ERROR > WARN > INFO > DEBUG > TRACE  */
        logger.debug("debug 日志测试！helloController");
        logger.info("info 日志测试！helloController");
        logger.warn("warn 日志测试！helloController");
        logger.error("error 日志测试！helloController");
        return  "测试成功";
    }
}
