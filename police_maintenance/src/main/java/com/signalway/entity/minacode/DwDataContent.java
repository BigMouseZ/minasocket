package com.signalway.entity.minacode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ZhangGang on 2018/7/3.
 */
public class DwDataContent {
    @JsonProperty
    private String FileName; //文件名,
    @JsonProperty
    private String SourcePath; //文件来源路径,
    @JsonProperty
    private String DestPath; //文件存储路径,
    @JsonProperty
    private String FileMd5; //文件MD5,
    @JsonProperty
    private String FrameMd5; //该帧MD5,
    @JsonProperty
    private String AllFrameNum; //文件被拆成的帧总数,
    @JsonProperty
    private String FrameSN; //该帧序号,
    @JsonProperty
    private String Parameter; //参数，保留,
    @JsonProperty
    private String Report; //执行返回内容,
    @JsonProperty
    private String Type; //0发送，1获取，2为信息返回,
    @JsonProperty
    private String RetCode; //0成功，-1失败,
    @JsonProperty
    private String RunTime; //命令运行时间，ms

    @JsonIgnore
    public String getFileName() {
        return FileName;
    }
    @JsonIgnore
    public void setFileName(String fileName) {
        FileName = fileName;
    }
    @JsonIgnore
    public String getSourcePath() {
        return SourcePath;
    }
    @JsonIgnore
    public void setSourcePath(String sourcePath) {
        SourcePath = sourcePath;
    }
    @JsonIgnore
    public String getDestPath() {
        return DestPath;
    }
    @JsonIgnore
    public void setDestPath(String destPath) {
        DestPath = destPath;
    }
    @JsonIgnore
    public String getFileMd5() {
        return FileMd5;
    }
    @JsonIgnore
    public void setFileMd5(String fileMd5) {
        FileMd5 = fileMd5;
    }
    @JsonIgnore
    public String getFrameMd5() {
        return FrameMd5;
    }
    @JsonIgnore
    public void setFrameMd5(String frameMd5) {
        FrameMd5 = frameMd5;
    }
    @JsonIgnore
    public String getAllFrameNum() {
        return AllFrameNum;
    }
    @JsonIgnore
    public void setAllFrameNum(String allFrameNum) {
        AllFrameNum = allFrameNum;
    }
    @JsonIgnore
    public String getFrameSN() {
        return FrameSN;
    }
    @JsonIgnore
    public void setFrameSN(String frameSN) {
        FrameSN = frameSN;
    }
    @JsonIgnore
    public String getParameter() {
        return Parameter;
    }
    @JsonIgnore
    public void setParameter(String parameter) {
        Parameter = parameter;
    }
    @JsonIgnore
    public String getReport() {
        return Report;
    }
    @JsonIgnore
    public void setReport(String report) {
        Report = report;
    }
    @JsonIgnore
    public String getType() {
        return Type;
    }
    @JsonIgnore
    public void setType(String type) {
        Type = type;
    }
    @JsonIgnore
    public String getRetCode() {
        return RetCode;
    }
    @JsonIgnore
    public void setRetCode(String retCode) {
        RetCode = retCode;
    }
    @JsonIgnore
    public String getRunTime() {
        return RunTime;
    }
    @JsonIgnore
    public void setRunTime(String runTime) {
        RunTime = runTime;
    }
}
