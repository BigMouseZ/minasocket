package com.signalway.util.manage;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZhangGang on 2018/7/2.
 * 管理carid与sessionid 的绑定
 */
public class CarIDSessionIDMap {
    private static CarIDSessionIDMap carIDSessionIDMap = null;
    private static Object LOCK = new Object();

    public static Map<String,String> createCarIDSessionIDMap = new ConcurrentHashMap<>();
    public static CarIDSessionIDMap instance(){
        synchronized (LOCK){
            if(carIDSessionIDMap == null){
                return new CarIDSessionIDMap();
            }
            return carIDSessionIDMap;
        }
    }
    private CarIDSessionIDMap(){

    }

    public void put(String key ,String value){
        createCarIDSessionIDMap.put(key,value);
    }
    
    public String get(String key){
        return createCarIDSessionIDMap.get(key);
    }
    public void clear(){
        createCarIDSessionIDMap.clear();
    }
    public boolean isEmpty(){
        return createCarIDSessionIDMap.isEmpty();
    }

    public String remove(String id) {
        return createCarIDSessionIDMap.remove(id);
    }

    public Collection<String> sessions() {
        return createCarIDSessionIDMap.values();
    }
}
