package com.signalway.entity.minacode;

/**
 * Created by ZhangGang on 2018/7/9.
 */
public class LoginInfo {
    private String Program;     // 命令来源
    private String TargetIP;
    private String TargetPort;
    private String SourceIP;
    private String SourcePort;
    private String NetworkType;    // 协议格式，以后会用作标识车辆ID
    private String CmdType; // 协议名
    private LoginContent Content;

    public String getProgram() {
        return Program;
    }

    public void setProgram(String program) {
        Program = program;
    }

    public String getTargetIP() {
        return TargetIP;
    }

    public void setTargetIP(String targetIP) {
        TargetIP = targetIP;
    }

    public String getTargetPort() {
        return TargetPort;
    }

    public void setTargetPort(String targetPort) {
        TargetPort = targetPort;
    }

    public String getSourceIP() {
        return SourceIP;
    }

    public void setSourceIP(String sourceIP) {
        SourceIP = sourceIP;
    }

    public String getSourcePort() {
        return SourcePort;
    }

    public void setSourcePort(String sourcePort) {
        SourcePort = sourcePort;
    }

    public String getNetworkType() {
        return NetworkType;
    }

    public void setNetworkType(String networkType) {
        NetworkType = networkType;
    }

    public String getCmdType() {
        return CmdType;
    }

    public void setCmdType(String cmdType) {
        CmdType = cmdType;
    }

    public LoginContent getContent() {
        return Content;
    }

    public void setContent(LoginContent content) {
        Content = content;
    }
}
