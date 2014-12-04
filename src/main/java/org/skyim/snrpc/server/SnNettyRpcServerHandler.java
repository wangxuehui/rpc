package org.skyim.snrpc.server;

import java.util.HashMap;
import java.util.Map;

import org.skyim.snrpc.conf.RpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月3日 下午3:17:26
 * 类说明
 */
public class SnNettyRpcServerHandler extends ChannelInboundHandlerAdapter {
	   private static final Logger LOGGER = LoggerFactory.getLogger(SnNettyRpcServer.class);

	   private final static Map<String,RpcService> serviceMap = new HashMap<String,RpcService>();
	
	   
	   public static void putService(RpcService service) {
		   if(null!=service) {
			   serviceMap.put(service.getName(), service);
		   }
	   }
	   
	   public static RpcService getServiceMap(String serviceName){
		   return serviceMap.get(serviceName);
	   }
	   
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
