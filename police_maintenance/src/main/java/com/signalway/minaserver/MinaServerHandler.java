package com.signalway.minaserver;

import com.signalway.cachemanage.CacheManageCentre;
import com.signalway.engine.ReponseQueue;
import com.signalway.entity.minacode.MsgPack;
import com.signalway.util.manage.CarIDSessionIDMap;
import com.signalway.util.manage.SessionIDListMap;
import com.signalway.util.manage.SocketSessionMap;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ZhangGang on 2018/6/29.
 */
public class MinaServerHandler extends IoHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(MinaServerHandler.class);
    private CacheManageCentre cacheManageCentre = new CacheManageCentre();
    /**
     * Mina框架中消息处理的Handler
     */
    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        SocketSessionMap.instance().remove(session.getId()+"");
        SessionIDListMap.instance().remove(session.getId()+"");
        for (Map.Entry<String,String> one:CarIDSessionIDMap.createCarIDSessionIDMap.entrySet()){
            if(one.getValue().equals(session.getId()+"")){
                CarIDSessionIDMap.instance().remove(one.getKey());
                break;
            }
        }
        ReponseQueue.setQueueValue(session.getId()+"",cause.getMessage());
        logger.warn("session occured exception, so close it."+ cause.getMessage());
        session.closeOnFlush();
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        if(message instanceof MsgPack){
        logger.info("当前sessionID:"+session.getId() +"当前线程id:"+Thread.currentThread().getId());
        MsgPack info = (MsgPack) message;
        if(info.getDwInfoLen()>0 && info.getDwDataLen()==0){
            //登录判断
            String msgJsonPack = info.getMsgJsonPack();
            JSONObject jsonObject=JSONObject.fromObject(msgJsonPack);
            String cmdType = jsonObject.get("CmdType").toString();
            if(StringUtils.isNotBlank(cmdType) && "OBCLogin".equals(cmdType)){
            //登录信息
                String content = jsonObject.get("Content").toString();
                JSONObject contentObject=JSONObject.fromObject(content);
                String carid = contentObject.get("CarID").toString();
                CarIDSessionIDMap.instance().put(carid,session.getId()+"");
                logger.info("警车id:"+carid+" 登录成功！");
            }else {
            //其他命令，直接发送到阻塞队列。
                ReponseQueue.setQueueValue(session.getId()+"",info);
            }
        }
        //文件上传
         if(info.getDwInfoLen()>0 && info.getDwDataLen()>0) {
             //将文件片段往下传递
             cacheManageCentre.accumulateCutPart(session,info);
         }
        }
    }
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
      //  logger.info("MinaServerHandler---messageSent:"+message.toString());
    }
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        logger.info("客户端" + ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress() + "连接成功！");
        if(SocketSessionMap.instance().get(session.getId()+"") == null){
            SocketSessionMap.instance().put(session.getId()+"",session);
        }
        if(SessionIDListMap.instance().get(session.getId()+"") == null){
            SessionIDListMap.instance().put(session.getId()+"",new ArrayList<>());
        }
    }
    @Override
    public void sessionClosed(IoSession session) throws Exception {
      //  logger.warn("客户端" + ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress() + "断开连接！");
        //关闭时 移除
        SocketSessionMap.instance().remove(session.getId()+"");
        SessionIDListMap.instance().remove(session.getId()+"");
        for (Map.Entry<String,String> one:CarIDSessionIDMap.createCarIDSessionIDMap.entrySet()){
            if(one.getValue().equals(session.getId()+"")){
                CarIDSessionIDMap.instance().remove(one.getKey());
                logger.warn("客户端警车id:"+one.getKey()+" 断开连接！sessionClosedID:"+session.getId());
                break;
            }
        }
        session.closeOnFlush();

    }
    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
     //   logger.info("MinaServerHandler---sessionIdle."+"sessionID:"+session.getId());
    }
    @Override
    public void sessionOpened(IoSession session) throws Exception {
        logger.info("sessionOpened."+"sessionID:"+session.getId());
    }

}
