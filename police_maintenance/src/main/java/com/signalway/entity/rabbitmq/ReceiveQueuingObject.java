package com.signalway.entity.rabbitmq;

import java.util.Date;

/**
 * Created by Admin on 2016/12/16.
 */
public class ReceiveQueuingObject {

//    1.从mq中接收到的消息（客户端发送消息）
//          数据格式：{ command:命名名称，data:发送数据，sessionid:”会话ID”}
//
//          登录示例：
//          { command:”LoginManage.Login”， sessionid:null ,data: { loginname:”admin”, password:”123”, key:”SDIKEWYDJ”}}
//
//          查询警车车辆信息示例：
//          { command:” PoliceCarManage.Query”， sessionid:”SDSFKISDK” ,
//              data: { policecarcode:”JC007”, policecarno:”桂A123”}

    private String command;
    private String sessionid;
    private Date apiTime;
    public String data;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public Date getApiTime() {
        return apiTime;
    }

    public void setApiTime(Date apiTime) {
        this.apiTime = apiTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ReceiveObject{" +
                "command='" + command + '\'' +
                ", sessionid='" + sessionid + '\'' +
                ", data=" + data +
                '}';
    }
}
