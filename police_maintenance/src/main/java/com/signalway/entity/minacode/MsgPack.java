package com.signalway.entity.minacode;

import java.io.Serializable;
import java.util.Arrays;

/**
 *  自定义数据包
 * */
public class MsgPack implements Serializable{
	/**
	 * 序列化和反序列化的版本号
	 */
	private static final long serialVersionUID = 1L;
	private int dwType;
	private int dwInfoLen;
	private int dwDataLen;
	//消息包内容
	private String msgJsonPack;
	private byte[] data;
	public MsgPack() {}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getDwType() {
		return dwType;
	}

	public void setDwType(int dwType) {
		this.dwType = dwType;
	}

	public int getDwInfoLen() {
		return dwInfoLen;
	}

	public void setDwInfoLen(int dwInfoLen) {
		this.dwInfoLen = dwInfoLen;
	}

	public int getDwDataLen() {
		return dwDataLen;
	}

	public void setDwDataLen(int dwDataLen) {
		this.dwDataLen = dwDataLen;
	}

	public String getMsgJsonPack() {
		return msgJsonPack;
	}

	public void setMsgJsonPack(String msgJsonPack) {
		this.msgJsonPack = msgJsonPack;
	}

	@Override
	public String toString() {
		return "MsgPack{" +
				"dwType=" + dwType +
				", dwInfoLen=" + dwInfoLen +
				", dwDataLen=" + dwDataLen +
				", msgJsonPack='" + msgJsonPack + '\'' +
				", data=" + Arrays.toString(data) +
				'}';
	}
}
