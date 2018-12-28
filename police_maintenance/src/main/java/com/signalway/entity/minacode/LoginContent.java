package com.signalway.entity.minacode;

/**
 * Created by ZhangGang on 2018/7/9.
 */
public class LoginContent {
    private String CarID; //警车id,
    private String Time; //登录时间
    private String Type; //暂留
    private String Report; //命令执行返回内容,
    private String RetCode; //0成功，-1失败,

    public String getCarID() {
        return CarID;
    }

    public void setCarID(String carID) {
        CarID = carID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getReport() {
        return Report;
    }

    public void setReport(String report) {
        Report = report;
    }

    public String getRetCode() {
        return RetCode;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }
}
