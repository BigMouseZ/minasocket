package com.signalway.rabbitmqsend;

import com.signalway.entity.ErrorCode;
import com.signalway.util.json.JsonUtils;

public class BaseResult {
	private String errorcode = ErrorCode.Success;//错误码
	private String message =  "执行成功";//执行提示消息
	private String data = null ;//结果数据
	
	public BaseResult() {
	}
	
	public BaseResult(String data) {

		this.data = data ;

	}
	
	public BaseResult(Object data) {

		this.data = JsonUtils.ConvertToString(data);

	}
	
	public BaseResult(String errorcode, String message) {
		this.errorcode = errorcode ;
		this.message = message ;
	}

	public BaseResult(String errorcode, String message, String data) {
		this.errorcode = errorcode ;
		this.message = message ;
		this.data = data;
	}
	
//------------------------------------------------------------------
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
