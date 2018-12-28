package com.signalway.entity.rabbitmq;

/**
 * Created by Admin on 2016/12/16.
 */
public class ReturnQueuingObject {
//    2. 返回给mq的消息
//           数据格式：{errorcode:错误码,message:执行提示消息,data: 返回数据}
//
//           返回登录结果示例：
//           {errorcode:”0”,message:”执行成功”,
//               data:
//               {sessionid:” YDSJSDYJS”,key:” ASIKJSYHJF”,
//                   police:{警员}
//               }
//           }
//
//           返回警车车辆信息结果示例：
//           {errorcode:”0”,message:”执行成功”,
//               data:[{},{}….]
//           }

    private String errorcode;
    private String message;
    private String data;
    public ReturnQueuingObject() {
    }

    public ReturnQueuingObject(String data) {
        this.data = data ;
    }

    public ReturnQueuingObject(String errorcode, String message) {
        this.errorcode = errorcode ;
        this.message = message ;
    }
    public ReturnQueuingObject(String errorcode, String message, String data) {
        this.errorcode = errorcode ;
        this.message = message ;
        this.data = data;
    }
    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        if("null".equals(data)){
            this.data = null;
        } else {
            this.data = data;
        }
    }
	@Override
    public String toString() {
        return "ReturnObject{" +
                "errorcode='" + errorcode + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
