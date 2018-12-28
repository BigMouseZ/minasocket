package com.signalway.minaserver;

import com.signalway.entity.minacode.HeaderInfo;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * Created by ZhangGang on 2018/7/2.
 */
public class MessageEncoder  extends ProtocolEncoderAdapter {
    private final Charset charset = Charset.forName("UTF-8");
    /*
       * 服务器端编码无需处理，直接将接收到的数据向下传递
       */
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        if(message instanceof HeaderInfo){ //头文件编码
            HeaderInfo request = (HeaderInfo) message;
            if(request.getData() !=null){

                IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                buffer.putInt(request.getDwType());
                buffer.putInt(request.getDwInfoLen());
                buffer.putInt(request.getDwDataLen());
                buffer.put(request.getData().getBytes(charset));
                buffer.flip();
                out.write(buffer);
            }else {
                IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
                buffer.order(ByteOrder.LITTLE_ENDIAN);
                buffer.putInt(request.getDwType());
                buffer.putInt(request.getDwInfoLen());
                buffer.putInt(request.getDwDataLen());
                buffer.flip();
                out.write(buffer);

            }



        }else if(message instanceof String){  //字符串编码

            String request = (String) message;
            IoBuffer buffer = IoBuffer.allocate(100).setAutoExpand(true);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            buffer.put(request.getBytes(charset));
            buffer.flip();
            out.write(buffer);

        }else if(message instanceof byte[]){
            byte[] mp = (byte[]) message;
            IoBuffer buffer = IoBuffer.allocate(1024*1024).setAutoExpand(true);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            //设置消息的功能函数
            buffer.put(mp);
            buffer.flip();

            out.write(buffer);

        }
    }
}
