package com.signalway.entity.minacode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ZhangGang on 2018/7/3.
 */
public class DwDataInfo {
    @JsonProperty
    private String Program;     // 命令来源
    @JsonProperty
    private String TargetIP;
    @JsonProperty
    private String TargetPort;
    @JsonProperty
    private String SourceIP;
    @JsonProperty
    private String SourcePort;
    @JsonProperty
    private String NetworkType;    // 协议格式，以后会用作标识车辆ID
    @JsonProperty
    private String CmdType; // 协议名
    @JsonProperty
    private DwDataContent Content;

    public DwDataInfo() {
    }

    public DwDataInfo(String program, String targetIP, String targetPort, String sourceIP, String sourcePort, String networkType, String cmdType, DwDataContent content) {
        Program = program;
        TargetIP = targetIP;
        TargetPort = targetPort;
        SourceIP = sourceIP;
        SourcePort = sourcePort;
        NetworkType = networkType;
        CmdType = cmdType;
        Content = content;
    }
    @JsonIgnore
    public String getProgram() {
        return Program;
    }
    @JsonIgnore
    public void setProgram(String program) {
        Program = program;
    }
    @JsonIgnore
    public String getTargetIP() {
        return TargetIP;
    }
    @JsonIgnore
    public void setTargetIP(String targetIP) {
        TargetIP = targetIP;
    }
    @JsonIgnore
    public String getTargetPort() {
        return TargetPort;
    }
    @JsonIgnore
    public void setTargetPort(String targetPort) {
        TargetPort = targetPort;
    }
    @JsonIgnore
    public String getSourceIP() {
        return SourceIP;
    }
    @JsonIgnore
    public void setSourceIP(String sourceIP) {
        SourceIP = sourceIP;
    }
    @JsonIgnore
    public String getSourcePort() {
        return SourcePort;
    }
    @JsonIgnore
    public void setSourcePort(String sourcePort) {
        SourcePort = sourcePort;
    }
    @JsonIgnore
    public String getNetworkType() {
        return NetworkType;
    }
    @JsonIgnore
    public void setNetworkType(String networkType) {
        NetworkType = networkType;
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
    public DwDataContent getContent() {
        return Content;
    }
    @JsonIgnore
    public void setContent(DwDataContent content) {
        Content = content;
    }
}
