package com.signalway.entity.questentity;

/**
 * Created by ZhangGang on 2018/7/18.
 */
public class SendFileQuestEntity {
    private String policecarid; //警车id,唯一标识
    private String fileName; //文件名,
    private String filePath;//图片fastdfs路径

    public String getPolicecarid() {
        return policecarid;
    }

    public void setPolicecarid(String policecarid) {
        this.policecarid = policecarid;
    }

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
}
