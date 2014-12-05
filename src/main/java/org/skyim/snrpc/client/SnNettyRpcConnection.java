package org.skyim.snrpc.client;

import java.net.InetSocketAddress;

import org.skyim.snrpc.SnRpcConnection;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.serializer.SnRpcRequest;
import org.skyim.snrpc.serializer.SnRpcRequestEncoder;
import org.skyim.snrpc.serializer.SnRpcResponse;
import org.skyim.snrpc.serializer.SnRpcResponseDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月4日 下午1:47:02
 * 类说明
 */
public class SnNettyRpcConnection  extends SimpleChannelInboundHandler<SnRpcResponse> implements SnRpcConnection{

	private InetSocketAddress inetAddr ;
	private SnRpcResponse response;
//	private final SnRpcRequest request;
	private volatile Channel ch  ;
	
	private SnRpcConfig snRpcConfig =SnRpcConfig.getInstance();
   
	{
		inetAddr = new InetSocketAddress(snRpcConfig.getProperty("snrpc.http.host", "localhost"), Integer.parseInt(snRpcConfig.getProperty("snrpc.http.port", "8080")));
	}
	
	public SnNettyRpcConnection(String host, int port,final SnRpcRequest request) {
		// TODO Auto-generated constructor stub
		this.inetAddr = new InetSocketAddress(host, port);
//		this.request = request;
	}

	
	

	public SnRpcResponse connect(final SnRpcRequest request) throws Throwable {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			
//			final SnNettyRpcConnection snNettyRpcConnection=new SnNettyRpcConnection(snRpcConfig.getProperty("snrpc.http.host", "localhost"),
//					Integer.parseInt(snRpcConfig.getProperty("snrpc.http.port", "8080")
//					),request) ;
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new SnRpcResponseDecoder());
					ch.pipeline().addLast(new SnRpcRequestEncoder());
					ch.pipeline().addLast(SnNettyRpcConnection.this);
				}
			});
			
		   ch = b.connect(inetAddr).sync().channel();
	       ch.writeAndFlush(request);    
	       waitForResponse();   
	       SnRpcResponse resp = this.response;
	       if(resp!=null) {
		       ch.closeFuture().sync();
	       }
	       return resp;
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	
	

	public void waitForResponse() {
		synchronized (ch) {
			try {
				ch.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SnRpcResponse msg)
			throws Exception {
		// TODO Auto-generated method stub
		response =  msg;
		synchronized (ch) {
			ch.notifyAll();
		}
	}
	
}
