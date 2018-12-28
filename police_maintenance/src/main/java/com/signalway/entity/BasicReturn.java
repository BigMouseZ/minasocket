package com.signalway.entity;

import com.signalway.entity.rabbitmq.ReturnQueuingObject;
import com.signalway.util.json.GsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Admin on 2016/12/17.
 */
public class BasicReturn {
    private static final Logger logger = LoggerFactory.getLogger(BasicReturn.class);

    public static String ReturnEx(Exception ex) {
        ReturnQueuingObject ro = new ReturnQueuingObject();
        ro.setErrorcode("3");
        ro.setMessage("执行失败");
        ro.setData("{\"Exception\":\"" + ex.getMessage() + "\"}");
        return GsonUtil.createGsonString(ro);
    }
    
    /**
     * 仅返回错误信息，并可设置Data数据
     * @param ex
     * @param data
     * @return
     */
    public static String ReturnEx(Exception ex,Object data) {
    	ReturnQueuingObject ro = new ReturnQueuingObject();
        ro.setErrorcode("3");
        ro.setMessage("执行失败！");
        if(data!=null){
        	ro.setData(GsonUtil.createGsonString(data));
        }
        return GsonUtil.createGsonString(ro);
    }
    
}
