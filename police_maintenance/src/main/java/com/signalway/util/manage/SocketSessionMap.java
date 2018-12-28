package com.signalway.util.manage;

import org.apache.mina.core.session.IoSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZhangGang on 2018/7/2.
 * 管理sessionid
 */
public class SocketSessionMap {
    private static SocketSessionMap socketSessionMap = null;
    private static Object LOCK = new Object();

    public static Map<String,IoSession> createSocketSessionMap =new ConcurrentHashMap<String,IoSession>();

    public static SocketSessionMap instance(){
        synchronized (LOCK){
            if(socketSessionMap == null){
                return new SocketSessionMap();
            }
            return socketSessionMap;
        }
    }
    private SocketSessionMap(){

    }

    public void put(String key ,IoSession value){
        createSocketSessionMap.put(key,value);
    }
    public IoSession get(String key){
        return createSocketSessionMap.get(key);
    }
    public void clear(){
        createSocketSessionMap.clear();
    }
    public boolean isEmpty(){
        return createSocketSessionMap.isEmpty();
    }

    public IoSession remove(String id) {
        return createSocketSessionMap.remove(id);
    }

    public Collection<IoSession> sessions() {
        return createSocketSessionMap.values();
    }
}
