package com.signalway.entity.minacode;

/**
 * Created by ZhangGang on 2018/7/3.
 * 头文件
 */

public class HeaderInfo {
    private int dwType;
    private int dwInfoLen;
    private int dwDataLen;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HeaderInfo() {
    }

    public HeaderInfo(int dwType, int dwInfoLen, int dwDataLen) {
        this.dwType = dwType;
        this.dwInfoLen = dwInfoLen;
        this.dwDataLen = dwDataLen;
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

    @Override
    public String toString() {
        return "HeaderInfo{" +
                "dwType=" + dwType +
                ", dwInfoLen=" + dwInfoLen +
                ", dwDataLen=" + dwDataLen +
                '}';
    }
}
