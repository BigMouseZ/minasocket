package com.signalway.rabbitmqsend;

/**
 * 消息发送Model
 * @author Weign
 *
 */
public class MessageSendModel extends BaseModel {
	public MessageSendModel(){}
	
	private String command = null; //命令标识
	private String data = null;//数据对象
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
