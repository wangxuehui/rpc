package org.skyim.snrpc.serializer;

import java.util.List;

import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.util.Const;

import in.srid.serializer.fasterxml.FasterxmlSerializer;
import in.srid.serializer.jackson.JacksonSerializer;
import in.srid.serializer.jdk.JdkObjectSerializer;
import in.srid.serializer.kryo.KryoSerializer;
import in.srid.serializer.protobuf.ProtobufSerializer;
import in.srid.serializer.protostuff.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月3日 下午2:55:40
 * 类说明
 */
public class SnRpcResponseDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		if(in.readableBytes() < 4) {
			return;
		}
		in.markReaderIndex();
		int dataLength = in.readInt();
		if(dataLength<0) {
			ctx.close();
		}
		if(in.readableBytes() < dataLength){
			in.resetReaderIndex();
			return ;
		}
		byte[] body = new byte[dataLength];
		in.readBytes(body);
		

	   SnRpcResponse snRpcResponse  = null;
       SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
       String type = snRpcConfig.getProperty("snrpc.serializataion.type", "5");
       if(Const.SERIALIZATION_PROTOBUF.equals(type)){
    	    final ProtobufSerializer protobuf = new ProtobufSerializer();
    	    snRpcResponse  =  protobuf.deserialize(body, SnRpcResponse.class);
       }else if(Const.SERIALIZATION_KRYO.equals(type)){
    	    final KryoSerializer kryo = new KryoSerializer();
    	    snRpcResponse  =  kryo.deserialize(body, SnRpcResponse.class);
       }else if(Const.SERIALIZATION_PROTOSTUFF.equals(type)){
   	    final ProtostuffSerializer protostuff = new ProtostuffSerializer();
   	 snRpcResponse  =  protostuff.deserialize(body, SnRpcResponse.class);
       }else if(Const.SERIALIZATION_FASTERXML.equals(type)){
     	    final FasterxmlSerializer fastxml = new FasterxmlSerializer();
     	   snRpcResponse  =  fastxml.deserialize(body, SnRpcResponse.class);
       }else if(Const.SERIALIZATION_JACKSON.equals(type)){
    	    final JacksonSerializer jackson = new JacksonSerializer();
    	    snRpcResponse  =  jackson.deserialize(body, SnRpcResponse.class);
       }else if(Const.SERIALIZATION_JDK.equals(type)){
   	        final JdkObjectSerializer jdk = new JdkObjectSerializer();
   	     snRpcResponse  =  jdk.deserialize(body, SnRpcResponse.class);
       }else {
    	   final ProtobufSerializer protobuf = new ProtobufSerializer();
    	   snRpcResponse  =  protobuf.deserialize(body, SnRpcResponse.class);
       }
		
		
	   out.add(snRpcResponse);
	}

}
