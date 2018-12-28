package com.signalway.util;

import com.signalway.entity.ErrorCode;
import com.signalway.rabbitmqsend.BaseResult;
import com.signalway.rabbitmqsend.MessageSendModel;
import com.signalway.util.json.JsonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by ZhangGang on 2018/7/10.
 */
@Component
public class RabbitMQUtils {
    private static final String CHARSET = "UTF-8";
    @Autowired
    private AmqpTemplate rabbitTemplate;


    /**
     * @param routingKey 路由键
     * @param commandKey 命令键
     * @param data       发送的数据
     * @return 直接返回
     * @Description 功能：
     **/
    public String sendMQ(String routingKey, String commandKey, Object data) {
        MessageSendModel sendModel = new MessageSendModel();
        sendModel.setCommand(commandKey);
        sendModel.setData(data instanceof String ? (String) data : JsonUtils.ConvertToString(data));
        String sendJson = JsonUtils.ConvertToString(sendModel);
        MessageProperties prop = new MessageProperties();
        Message message = null;
        try {
            message = new Message(sendJson.getBytes(CHARSET), prop);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        rabbitTemplate.send(routingKey, message);
        return JsonUtils.ConvertToString(new BaseResult(ErrorCode.Success, "执行成功"));
    }

    /**
     * @param routingKey 路由键
     * @param commandKey 命令键
     * @param data       发送的数据
     * @return 等待返回
     * @Description 功能：
     **/
    public String sendAndReceive(String routingKey, String commandKey, Object data) {
        MessageSendModel sendModel = new MessageSendModel();
        sendModel.setCommand(commandKey);
        sendModel.setData(data instanceof String ? (String) data : JsonUtils.ConvertToString(data));
        String sendJson = JsonUtils.ConvertToString(sendModel);
        MessageProperties prop = new MessageProperties();
        Message message = null;
        try {
            message = new Message(sendJson.getBytes(CHARSET), prop);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Message receiveMessage = rabbitTemplate.sendAndReceive(routingKey, message);
        String receiveJson = null;
        if (receiveMessage != null && receiveMessage.getBody() != null) {
            try {
                receiveJson = new String(receiveMessage.getBody(), CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return receiveJson;
    }
}
