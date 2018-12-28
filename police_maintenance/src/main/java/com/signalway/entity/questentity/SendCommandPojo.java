package com.signalway.entity.questentity;

/**
 * Created by ZhangGang on 2018/7/18.
 * 命令下发OBC
 */
public class SendCommandPojo  {
    private String policecarid;
    private String policecarcode;
    private String command;
    private String commandtype;
    private String filetype;
    private String configtype;
    private String issync; //0：阻塞，1：非阻塞

    public String getIssync() {
        return issync;
    }

    public void setIssync(String issync) {
        this.issync = issync;
    }

    public String getConfigtype() {
        return configtype;
    }

    public void setConfigtype(String configtype) {
        this.configtype = configtype;
    }

    public String getPolicecarid() {
        return policecarid;
    }

    public void setPolicecarid(String policecarid) {
        this.policecarid = policecarid;
    }

    public String getPolicecarcode() {
        return policecarcode;
    }

    public void setPolicecarcode(String policecarcode) {
        this.policecarcode = policecarcode;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommandtype() {
        return commandtype;
    }

    public void setCommandtype(String commandtype) {
        this.commandtype = commandtype;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }
}
