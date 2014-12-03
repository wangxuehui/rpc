package org.skyim.snrpc.serializer;

import in.srid.serializer.protobuf.ProtobufSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月2日 下午2:32:04
 * 类说明
 */
public class SnRpcRequestEncoder  extends MessageToByteEncoder<SnRpcRequest>{

	@Override
	protected void encode(ChannelHandlerContext ctx, SnRpcRequest msg,
			ByteBuf out) throws Exception {
		
	   final ProtobufSerializer protobuf = new ProtobufSerializer();
	   byte[] data = protobuf.serialize(msg);
       int dataLength = data.length;
		// TODO Auto-generated method stub
       out.writeInt(dataLength);
       out.writeBytes(data);
	}

	



}
