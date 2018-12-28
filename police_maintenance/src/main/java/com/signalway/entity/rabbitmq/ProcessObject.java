package com.signalway.entity.rabbitmq;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Admin on 2016/12/16.
 */
public class ProcessObject {

    private String strReturn;
    private boolean isReturn;

    public String getStrReturn() {
        return strReturn;
    }

    public void setStrReturn(String strReturn) {
        this.strReturn = strReturn;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean aReturn) {
        isReturn = aReturn;
    }
    public void setReturnOK() {
        setReturn("0", "执行成功",null);
    }
    public void setReturnOK(String data) {
        setReturn("0", "执行成功",data);
    }
    public void setReturnTooOften() {
        setReturn("-3", "访问太频繁,本次请求被拒绝！");
    }
    public void setReturnTooBusy() {
        setReturn("-2", "服务器繁忙,请稍候重试！");
    }
    public void setReturnError() {
        setReturn("3", "执行失败");
    }
    public void setReturnError(String data) {
        setReturn("3", "执行失败",data);
    }
    public void setReturn(String code, String msg) {
        setReturn(code, msg,null);
    }
    public void setReturn(String code, String msg,String data) {
        ReturnQueuingObject retObj = new ReturnQueuingObject();
        retObj.setErrorcode(code);
        retObj.setMessage(msg);
        if(data != null) {
            retObj.setData(data);
        }
        this.setReturn(true);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        this.setStrReturn(gson.toJson(retObj));
    }

    @Override
    public String toString() {
        return "ProcessObject{" +
                "strReturn='" + strReturn + '\'' +
                ", isReturn=" + isReturn +
                '}';
    }

}
