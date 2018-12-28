package com.signalway.thread;

import com.signalway.engine.ReponseQueue;

import java.util.Map;

/**
 * Created by ZhangGang on 2018/7/17.
 */
public class QueueTimeOutManage implements Runnable {

    private Integer interval;
    public QueueTimeOutManage(int interval) {
        this.interval=interval;
    }
    /*超时管理*/
    @Override
    public void run() {
        while(true) {
            try {
                Map<String, Long> queuesTime = ReponseQueue.getQueuesTime();
                if(!queuesTime.isEmpty()){
                    int intervalTime = interval*1000;//毫秒
                    Long now = System.currentTimeMillis();
                    for (Map.Entry<String, Long> entry : queuesTime.entrySet()) {
                        String socketSessionID = entry.getKey();
                        Long milliseconds = entry.getValue();
                        if((now-milliseconds) > intervalTime){
                            String error = "timeout failure";
                            ReponseQueue.setQueueValue(socketSessionID,error);
                        }
                    }
                }else {
                    Thread.sleep(500);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
