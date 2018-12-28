package com.signalway.minaserver;

import com.signalway.entity.minacode.HeaderInfo;
import com.signalway.entity.minacode.MsgPack;
import com.signalway.entity.minacode.NetInfoType;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * Created by ZhangGang on 2018/7/2.
 */
/**
 * 被动型心跳机制，服务器在接受到客户端连接以后被动接受心跳请求，当在规定时间内没有收到客户端心跳请求时将客户端连接关闭
 * @ClassName KeepAliveMessageFactoryImpl
 * @Description 内部类，实现 KeepAliveMessageFactory(心跳工厂)
 */
public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {
    /* 判断是否心跳请求包，是的话返回true */
    @Override
    public boolean isRequest(IoSession session, Object message) {
        if(message instanceof MsgPack){
            MsgPack info = (MsgPack) message;
            if(info.getDwType() ==NetInfoType.NET_THROB.getIndex() ){
            //   System.out.println("服务端接收到一个 心跳："+info.getDwType()+"sessionid:"+session.getId());
                return true;
            }
        }
        return false;
    }
    /* 由于被动型心跳机制，没有请求当然也就不关注反馈，因此直接返回 false */
    @Override
    public boolean isResponse(IoSession session, Object message) {
        return false;
    }

    /* 被动型心跳机制无请求，因此直接返回 null */
    @Override
    public Object getRequest(IoSession session) {
        return null;
    }
    /* 根据心跳请求 request，反回一个心跳反馈消息 non-null  */
    @Override
    public Object getResponse(IoSession session, Object request) {
        HeaderInfo send = new HeaderInfo(NetInfoType.NET_THROB_RESPONSE.getIndex(),0,0);
        return send;
    }
}
