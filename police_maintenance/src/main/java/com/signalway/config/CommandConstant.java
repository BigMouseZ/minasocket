package com.signalway.config;

/**
 * Created by ZhangGang on 2018/7/24.
 */
public class CommandConstant {
    public static String JsonProgram = "OBCUpgrade";     // 命令来源
    public static String JsonTargetIP = "0.0.0.0";
    public static String JsonTargetPort = "23";
    public static String JsonSourceIP = "0.0.0.0";
    public static String JsonSourcePort = "23";
    public static String JsonNetworkType = "FTP";    // 协议格式，以后会用作标识车辆ID
    public static String JsonCmdType = "OBCCMD";
    public static String FileCmdType = "OBCRemoteFile";
}
