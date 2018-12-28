package com.signalway.entity.fileentity;

/**
 * Created by ZhangGang on 2018/7/10.
 */
public class FileByteInfo {
    private String AllFrameNum; //文件被拆成的帧总数,
    private String FrameSN; //该帧序号,
    byte[] data; //该帧二进制数组

    public String getAllFrameNum() {
        return AllFrameNum;
    }

    public void setAllFrameNum(String allFrameNum) {
        AllFrameNum = allFrameNum;
    }

    public String getFrameSN() {
        return FrameSN;
    }

    public void setFrameSN(String frameSN) {
        FrameSN = frameSN;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
