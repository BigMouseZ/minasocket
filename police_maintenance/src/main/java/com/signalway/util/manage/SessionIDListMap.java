package com.signalway.util.manage;


import com.signalway.entity.fileentity.FileByteInfo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZhangGang on 2018/7/9.
 * 管理每个session下的List缓存。
 */
public class SessionIDListMap {
    private static SessionIDListMap sessionIDListMap = null;
    private static Object LOCK = new Object();

    public static Map<String,List<FileByteInfo>> createSessionIDListMap = new ConcurrentHashMap<>();
    public static SessionIDListMap instance(){
        synchronized (LOCK){
            if(sessionIDListMap == null){
                return new SessionIDListMap();
            }
            return sessionIDListMap;
        }
    }
    private SessionIDListMap(){

    }

    public void put(String key ,List<FileByteInfo> value){
        createSessionIDListMap.put(key,value);
    }

    public List<FileByteInfo> get(String key){
        return createSessionIDListMap.get(key);
    }
    public void clear(){
        createSessionIDListMap.clear();
    }
    public boolean isEmpty(){
        return createSessionIDListMap.isEmpty();
    }

    public List<FileByteInfo> remove(String id) {
        return createSessionIDListMap.remove(id);
    }

    public Collection<List<FileByteInfo>> sessions() {
        return createSessionIDListMap.values();
    }
}
