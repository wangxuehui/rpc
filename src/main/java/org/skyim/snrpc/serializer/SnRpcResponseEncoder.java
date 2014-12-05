package org.skyim.snrpc.serializer;

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
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月3日 下午2:56:29
 * 类说明
 */
public class SnRpcResponseEncoder  extends MessageToByteEncoder<SnRpcResponse>{

	@Override
	protected void encode(ChannelHandlerContext ctx, SnRpcResponse msg,
			ByteBuf out) throws Exception {
		// TODO Auto-generated method stub
           byte[] data =null;
	       SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
	       String type = snRpcConfig.getProperty("snrpc.serializataion.type", "5");
	       if(Const.SERIALIZATION_PROTOBUF.equals(type)){
	    	    final ProtobufSerializer protobuf = new ProtobufSerializer();
	    	    data  =  protobuf.serialize(msg);
	       }else if(Const.SERIALIZATION_KRYO.equals(type)){
	    	    final KryoSerializer kryo = new KryoSerializer();
	    	    data  =  kryo.serialize(msg);
	       }else if(Const.SERIALIZATION_PROTOSTUFF.equals(type)){
	   	    final ProtostuffSerializer protostuff = new ProtostuffSerializer();
	   	    	data  =  protostuff.serialize(msg);
	       }else if(Const.SERIALIZATION_FASTERXML.equals(type)){
	     	    final FasterxmlSerializer fastxml = new FasterxmlSerializer();
	     	   data  =  fastxml.serialize(msg);
	       }else if(Const.SERIALIZATION_JACKSON.equals(type)){
	    	    final JacksonSerializer jackson = new JacksonSerializer();
	    	    data  =  jackson.serialize(msg);
	       }else if(Const.SERIALIZATION_JDK.equals(type)){
	   	        final JdkObjectSerializer jdk = new JdkObjectSerializer();
	   	        data  =  jdk.serialize(msg);
	       }else {
	    	   	final ProtobufSerializer protobuf = new ProtobufSerializer();
	    	   	data  =  protobuf.serialize(msg);
	       }
		   
	       int dataLength = data.length;
			// TODO Auto-generated method stub
	       out.writeInt(dataLength);
	       out.writeBytes(data);
	}

}
