package com.signalway.engine;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * Created by 苏建力 on 2017/12/18.
 *
 */
public class ReponseQueue {

    public volatile static ConcurrentMap<String,LinkedBlockingQueue<Object>> queues = new ConcurrentHashMap<String,LinkedBlockingQueue<Object>>();
    public volatile static ConcurrentMap<String,Long> queuesTime = new ConcurrentHashMap<String,Long>();


    /**
     * 设置当前运行id队列
     * @param id
     */
    public static void setQueue(String id){
        queues.put(id,new LinkedBlockingQueue<Object>());
        queuesTime.put(id,System.currentTimeMillis());
    }
    /**
     * 获取当前id队列
     * @param id
     * @return
     */
    public static LinkedBlockingQueue<Object>  getQueue(String id){
        return queues.get(id);
    }
    /**
     * 队列是否有内容
     * @param id
     * @return
     */
    public static boolean isQueueEmpty(String id){
        LinkedBlockingQueue<Object> queue = queues.get(id);
        return queue.isEmpty();
    }
    /**
     * 获取当前id队列的时间
     * @param id
     * @return
     */
    public static Long getQueuesTime(String id){
        return queuesTime.get(id);
    }

    public static LinkedBlockingQueue<Object> removeQueue(String id){
        LinkedBlockingQueue<Object> ret = queues.remove(id);
        if(null != ret) {
            queuesTime.remove(id);
        }
        return ret;
    }

    /**
     * 设置当前id队列中的数据
     * @param id
     * @param obj
     * @return
     */
    public static <T> boolean setQueueValue(String id, T obj){
        LinkedBlockingQueue queue =  queues.get(id);
        return null == queue ? false : queue.add(null == obj ? "" : obj);
    }

    /**
     * 判断当前运行runId队列是否存在
     * @param id
     */
    public  static boolean isExiststQueueValue(String id) {
        return queues.containsKey(id);
    }
    /**
     * 获取当前id队列中的数据
     * @param id
     * @return
     */
    public static Object takeQueueValue(String id){
        LinkedBlockingQueue<Object> queue = queues.get(id);
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 数据删除
     * @param id
     */
    public static void destroy(String id){
        queues.remove(id);
        queuesTime.remove(id);
    }

    public static ConcurrentMap<String, Long> getQueuesTime() {
        return queuesTime;
    }
}
