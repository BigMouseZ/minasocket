package com.signalway.minaserver;

import com.signalway.entity.minacode.MsgPack;
import com.signalway.entity.minacode.NetInfoType;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * Created by ZhangGang on 2018/7/2.
 */
//消息解码器
public class MessageDecoder extends CumulativeProtocolDecoder {
    private final Charset charset = Charset.forName("UTF-8");
    @Override
    protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput out) throws Exception {
        ioBuffer.order(ByteOrder.LITTLE_ENDIAN);
        MsgPack mp = (MsgPack) session.getAttribute("nac-msg-pack"); // 从session对象中获取“xhs-upload”属性值
        if(null==mp){
            if (ioBuffer.remaining() >= 12) {
                //取消息体长度
                int dwType = ioBuffer.getInt();
                int dwInfoLen = ioBuffer.getInt();
                int dwDataLen = ioBuffer.getInt();
                mp=new MsgPack();
                mp.setDwType(dwType);
                mp.setDwInfoLen(dwInfoLen);
                mp.setDwDataLen(dwDataLen);
                if(dwType== NetInfoType.NET_THROB.getIndex() || dwType== NetInfoType.NET_THROB_RESPONSE.getIndex()){  //消息头
                    out.write(mp);
                    return true;
                }
                session.setAttribute("nac-msg-pack",mp);
                return true;
            }
            return false;
        }else if(ioBuffer.remaining() == 12){  //重新更新包头
            //取消息体长度
            int dwType = ioBuffer.getInt();
            int dwInfoLen = ioBuffer.getInt();
            int dwDataLen = ioBuffer.getInt();
            mp=new MsgPack();
            mp.setDwType(dwType);
            mp.setDwInfoLen(dwInfoLen);
            mp.setDwDataLen(dwDataLen);
            if(dwType== NetInfoType.NET_THROB.getIndex()){ //消息头
                out.write(mp);
                return true;
            }
            session.removeAttribute("nac-msg-pack");
            session.setAttribute("nac-msg-pack",mp);
            return true;

        }
        if(mp.getDwType()== NetInfoType.NET_STRING.getIndex()){ //存在消息体,不存在文件消息
            if(ioBuffer.remaining() >=mp.getDwInfoLen()){
                byte [] msgPack=new byte[mp.getDwInfoLen()];
                ioBuffer.get(msgPack);
                mp.setMsgJsonPack(new String(msgPack,charset));
                session.removeAttribute("nac-msg-pack");
                out.write(mp);
                return true;
            }
            return false;
        }
        if(mp.getDwType()== NetInfoType.NET_FILE.getIndex() && mp.getMsgJsonPack() == null ){

            if(ioBuffer.remaining() >= mp.getDwInfoLen()){
                byte [] msgPack=new byte[mp.getDwInfoLen()];
                ioBuffer.get(msgPack);
                mp.setMsgJsonPack(new String(msgPack,charset));
                session.removeAttribute("nac-msg-pack");
                session.setAttribute("nac-msg-pack",mp);
                return true;
            }
            return false;
        }
        if(mp.getDwType()== NetInfoType.NET_FILE.getIndex() && mp.getMsgJsonPack() != null ){
            if(ioBuffer.remaining() >=mp.getDwDataLen()){
                byte [] msgPack=new byte[mp.getDwDataLen()];
                ioBuffer.get(msgPack);
                mp.setData(msgPack);
                session.removeAttribute("nac-msg-pack");
                out.write(mp);
                return true;
            }
           return false;
        }
        return false;
    }
}
