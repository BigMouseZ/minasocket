package com.signalway.minaserver;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * Created by ZhangGang on 2018/7/2.
 */
public class MessageProtocolCodecFactory  implements ProtocolCodecFactory {
    private final ProtocolEncoder encoder = new MessageEncoder();
    private final ProtocolDecoder decoder = new MessageDecoder();

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }
}
