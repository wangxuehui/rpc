package org.skyim.snrpc.serializer;

import in.srid.serializer.jackson.JacksonSerializer;
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
		   final JacksonSerializer jackson = new JacksonSerializer();
		   byte[] data = jackson.serialize(msg);
	       int dataLength = data.length;
			// TODO Auto-generated method stub
	       out.writeInt(dataLength);
	       out.writeBytes(data);
	}

}
