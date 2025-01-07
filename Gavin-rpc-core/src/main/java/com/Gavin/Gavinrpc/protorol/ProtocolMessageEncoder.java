package com.Gavin.Gavinrpc.protorol;

import java.io.IOException;

import com.Gavin.Gavinrpc.protorol.enums.ProtocolMessageSerializerEnum;
import com.Gavin.Gavinrpc.serializer.Serializer;
import com.Gavin.Gavinrpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

/**
 * 消息编码器，核心流程是依次向 Buffer 缓冲区写入消息对象里的字段。
 */
public class ProtocolMessageEncoder {
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws IOException {
        if (protocolMessage ==null ||protocolMessage.getHeader()==null){
            return Buffer.buffer();
        }
        ProtocolMessage.Header header = protocolMessage.getHeader();
        //依次向缓冲区写入字节
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());
        //获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if (serializerEnum == null) {
            throw new RuntimeException("序列化协议不存在");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());
        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());
        //写入body长度和数据
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);
        return buffer;
    }
}
