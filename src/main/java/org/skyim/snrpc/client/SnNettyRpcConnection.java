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
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月4日 下午1:47:02
 * 类说明
 */
public class SnNettyRpcConnection  extends ChannelInboundHandlerAdapter implements SnRpcConnection{

	private InetSocketAddress inetAddr;
	private volatile SnRpcResponse response;
	//org.jboss.netty.channel.Channel
	private volatile Channel channel;
    public SnNettyRpcConnection(String host, int port) {
		// TODO Auto-generated constructor stub
		this.inetAddr = new InetSocketAddress(host, port);
	}

	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Server is supposed to send nothing, but if it sends something, discard it.
		response = (SnRpcResponse) msg;
		synchronized (channel) {
			channel.notifyAll();
		}
	}

	public  void connection() throws Throwable {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new SnRpcResponseDecoder());
					ch.pipeline().addLast(new SnRpcRequestEncoder());
					ch.pipeline().addLast(this);
				}
			});
			// Start the connection attempt.
			channel = b.connect(inetAddr).sync().channel(); // (5)
			// Wait until the connection is closed.
			channel.closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}


	@Override
	public SnRpcResponse sendRequest(SnRpcRequest request) throws Throwable {
		// TODO Auto-generated method stub
		
		channel.writeAndFlush(request);
		waitForResponse();
		SnRpcResponse resp = this.response;
		
		return resp;
	}
	
	public void waitForResponse() {
		synchronized (channel) {
			try {
				channel.wait();
			} catch (InterruptedException e) {
			}
		}
	}
	
}
