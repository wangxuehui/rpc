package org.skyim.snrpc.client;

import java.net.InetSocketAddress;

import org.skyim.snrpc.SnRpcConnection;
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
 * @version 创建时间：2014年12月4日 下午1:47:02 类说明
 */
public class SnNettyRpcConnection extends
		SimpleChannelInboundHandler<SnRpcResponse> implements SnRpcConnection {

	private InetSocketAddress inetAddr;
	private SnRpcResponse response;
	private Object obj = new Object();


	public SnNettyRpcConnection(String host, int port) {
		// TODO Auto-generated constructor stub
		this.inetAddr = new InetSocketAddress(host, port);
	}

	public SnRpcResponse sendRequest(final SnRpcRequest request)
			throws Throwable {
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
					ch.pipeline().addLast(SnNettyRpcConnection.this);
				}
			});

			Channel ch = b.connect(inetAddr).sync().channel();
			ch.writeAndFlush(request);
			waitForResponse();
			SnRpcResponse resp = this.response;
			if (resp != null) {
				ch.closeFuture().sync();
			}
			return resp;
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	public void waitForResponse() {
		synchronized (obj) {
			try {
				obj.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, SnRpcResponse msg)
			throws Exception {
		// TODO Auto-generated method stub
		response = msg;
		synchronized (obj) {
			obj.notifyAll();
		}
	}

}
