package com.signalway.entity.questentity;

/**
 * Created by ZhangGang on 2018/7/18.
 */
public class SendFilePojo  {
    private String policecarid;
    private String filePath;
    private String fileName;
    private String destpath;
    private String fileMD5;

    public String getFileMD5() {
        return fileMD5;
    }

    public void setFileMD5(String fileMD5) {
        this.fileMD5 = fileMD5;
    }

    public String getDestpath() {
        return destpath;
    }

    public void setDestpath(String destpath) {
        this.destpath = destpath;
    }

    public String getPolicecarid() {
        return policecarid;
    }

    public void setPolicecarid(String policecarid) {
        this.policecarid = policecarid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
