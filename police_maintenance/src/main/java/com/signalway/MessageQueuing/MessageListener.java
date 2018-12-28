package com.signalway.MessageQueuing;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.signalway.entity.rabbitmq.ProcessObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by Admin on 2016/12/16.
 */
@Component
public class MessageListener  {
    private final static Logger logger = LoggerFactory.getLogger(MessageListener.class);
    @Autowired
    ProcessMessageQueuing processMessageQueuing;

    @RabbitListener(queues = {"PoliceMaintenance.GetOBCFile","PoliceMaintenance.SendOrder","DeviceMaintenance.SendFile"})
    @RabbitHandler
    public void onMessage(Message message, Channel channel) {
    //有返回处理
        try {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(message.getMessageProperties().getCorrelationId())
                    .build();
            byte[] bytes = message.getBody();
            String reStr = new String(bytes, "utf-8");
            //逻辑处理
            ProcessObject po = processMessageQueuing.ProcessRequest(reStr);
            if (po.isReturn()) {
                //结果返回
                //当生产者不等待回值的时候，这样写ReplyTo的话，就会出错，也没意义。
                channel.basicPublish("", message.getMessageProperties().getReplyTo(), replyProps, po.getStrReturn().getBytes("utf-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*@RabbitListener(queues = "PoliceImage")
    @RabbitHandler
    public void onMessageWithOut(Message message) {
        //无返回处理
        try {
            byte[] bytes = message.getBody();
            String reStr = new String(bytes, "utf-8");
            logger.info("日志输出测试：onMessageWithOut");
            //逻辑处理
             processMessageQueuing.ProcessRequest(reStr);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }*/
}
