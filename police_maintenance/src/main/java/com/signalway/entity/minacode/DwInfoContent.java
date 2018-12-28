package com.signalway.entity.minacode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ZhangGang on 2018/7/3.
 */
public class DwInfoContent {
    @JsonProperty
    private String CMD; //命令实体，可为CMD命令或者是程序等,
    @JsonProperty
    private String Parameter; //命令参数，可为空,
    @JsonProperty
    private String Report; //命令执行返回内容,
    @JsonProperty
    private String CmdType; //0为阻塞式运行，可接收CMD等命令的执行返回内容，超时或执行完成后返回；1为独立运行，可运行程序,
    @JsonProperty
    private String RetCode; //0成功，-1失败,
    @JsonProperty
    private String RunTime; //命令运行时间，ms

    public DwInfoContent() {
    }

    public DwInfoContent(String CMD, String parameter, String report, String cmdType, String retCode, String runTime) {
        this.CMD = CMD;
        Parameter = parameter;
        Report = report;
        CmdType = cmdType;
        RetCode = retCode;
        RunTime = runTime;
    }
    @JsonIgnore
    public String getCMD() {
        return CMD;
    }
    @JsonIgnore
    public void setCMD(String CMD) {
        this.CMD = CMD;
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
    public String getCmdType() {
        return CmdType;
    }
    @JsonIgnore
    public void setCmdType(String cmdType) {
        CmdType = cmdType;
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

    @Override
    public String toString() {
        return "DwInfoContent{" +
                "CMD='" + CMD + '\'' +
                ", Parameter='" + Parameter + '\'' +
                ", Report='" + Report + '\'' +
                ", CmdType='" + CmdType + '\'' +
                ", RetCode='" + RetCode + '\'' +
                ", RunTime='" + RunTime + '\'' +
                '}';
    }
}
