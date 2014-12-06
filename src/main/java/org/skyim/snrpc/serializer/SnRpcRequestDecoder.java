package org.skyim.snrpc.serializer;

import java.util.List;

import org.skyim.serializer.fasterxml.FasterxmlSerializer;
import org.skyim.serializer.jackson.JacksonSerializer;
import org.skyim.serializer.jdk.JdkObjectSerializer;
import org.skyim.serializer.kryo.KryoSerializer;
import org.skyim.serializer.protobuf.ProtobufSerializer;
import org.skyim.serializer.protostuff.ProtostuffSerializer;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.util.Const;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月2日 下午2:14:18
 * 类说明
 */
public class SnRpcRequestDecoder extends ByteToMessageDecoder {

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
		}
		byte[] body = new byte[dataLength];
		in.readBytes(body);
	   
		
	   SnRpcRequest snRpcRequest  = null;
       SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
       String type = snRpcConfig.getProperty("snrpc.serializataion.type", "5");
       if(Const.SERIALIZATION_PROTOBUF.equals(type)){
    	    final ProtobufSerializer protobuf = new ProtobufSerializer();
    	    snRpcRequest  =  protobuf.deserialize(body, SnRpcRequest.class);
       }else if(Const.SERIALIZATION_KRYO.equals(type)){
    	    final KryoSerializer kryo = new KryoSerializer();
    	    snRpcRequest  =  kryo.deserialize(body, SnRpcRequest.class);
       }else if(Const.SERIALIZATION_PROTOSTUFF.equals(type)){
   	    final ProtostuffSerializer protostuff = new ProtostuffSerializer();
   	    snRpcRequest  =  protostuff.deserialize(body, SnRpcRequest.class);
       }else if(Const.SERIALIZATION_FASTERXML.equals(type)){
     	    final FasterxmlSerializer fastxml = new FasterxmlSerializer();
       	    snRpcRequest  =  fastxml.deserialize(body, SnRpcRequest.class);
       }else if(Const.SERIALIZATION_JACKSON.equals(type)){
    	    final JacksonSerializer jackson = new JacksonSerializer();
      	    snRpcRequest  =  jackson.deserialize(body, SnRpcRequest.class);
       }else if(Const.SERIALIZATION_JDK.equals(type)){
   	        final JdkObjectSerializer jdk = new JdkObjectSerializer();
     	    snRpcRequest  =  jdk.deserialize(body, SnRpcRequest.class);
       }else {
    	   final ProtobufSerializer protobuf = new ProtobufSerializer();
   	       snRpcRequest  =  protobuf.deserialize(body, SnRpcRequest.class);
       }


	   out.add(snRpcRequest);
	}



}
