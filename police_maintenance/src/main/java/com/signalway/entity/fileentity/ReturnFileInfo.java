package com.signalway.entity.fileentity;

/**
 * Created by ZhangGang on 2018/7/10.
 * 发送MQ消息给警用后端。
 */
public class ReturnFileInfo {
    private String fileName; //文件名,
    private String filePath;//图片fastdfs路径

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "ReturnFileInfo{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
