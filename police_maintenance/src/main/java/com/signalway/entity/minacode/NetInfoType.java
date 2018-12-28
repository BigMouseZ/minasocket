package com.signalway.entity.minacode;

/**
 * Created by ZhangGang on 2018/7/4.
 */
public enum NetInfoType {
    NET_THROB("心跳包头请求",0x0001),
    NET_THROB_RESPONSE("心跳回复",0x0002),
    NET_FILE("文件类型请求",0x1001),
    NET_STRING("json字符请求",0x1002);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private NetInfoType(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getName(int index) {
        for (NetInfoType c : NetInfoType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
