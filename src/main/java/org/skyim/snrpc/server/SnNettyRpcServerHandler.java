package org.skyim.snrpc.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月3日 下午3:17:26
 * 类说明
 */
public class SnNettyRpcServerHandler extends ChannelInboundHandlerAdapter {
	    
	   @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
	        // Discard the received data silently.
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
	        // Close the connection when an exception is raised.
	        cause.printStackTrace();
	        ctx.close();
	    }
}
