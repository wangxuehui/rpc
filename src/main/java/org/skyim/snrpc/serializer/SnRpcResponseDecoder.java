package org.skyim.snrpc.serializer;

import java.util.List;

import in.srid.serializer.jackson.JacksonSerializer;
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
		

	   final JacksonSerializer jackson = new JacksonSerializer();
	   SnRpcResponse snRpcResponse  = jackson.deserialize(body, SnRpcResponse.class);
	   out.add(snRpcResponse);
	}

}
