package org.skyim.snrpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Map;

import org.skyim.snrpc.SnRpcServer;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.serializer.SnRpcRequestDecoder;
import org.skyim.snrpc.serializer.SnRpcResponseEncoder;
import org.skyim.snrpc.util.HandlerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年11月29日 下午3:48:53
 * 类说明
 */
public class SnNettyRpcServer implements SnRpcServer{
	private static final Logger LOGGER = LoggerFactory.getLogger(SnNettyRpcServer.class);
	
	private SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
	private Map<String,Object> handlersMap;
	private int httpListenPort;
	
	public SnNettyRpcServer(Object...  handlers){
		snRpcConfig.loadProperties("snrpcserver.properties");
		this.handlersMap = HandlerMapper.getHandlerMap(handlers);
	}
	
	public SnNettyRpcServer(String fileName,Object... handlers){
		snRpcConfig.loadProperties(fileName);
		this.handlersMap = HandlerMapper.getHandlerMap(handlers);
	}
	
	
	public void start() throws Throwable {
		// TODO Auto-generated method stub
		initServerInfo();
		initHttpBootstrap();
	}

	private void initHttpBootstrap() throws InterruptedException {
		// TODO Auto-generated method stub
		if(SnRpcConfig.getInstance().getDevMod()){
			StatisticsService.reportPerformance();
		}
		LOGGER.info("init HTTP Bootstrap ..........");
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class) // (3)
             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                	 ch.pipeline().addLast(new SnRpcRequestDecoder());
                	 ch.pipeline().addLast(new SnRpcResponseEncoder());
                     ch.pipeline().addLast(new SnNettyRpcServerHandler(handlersMap));
                 }
             })
             .option(ChannelOption.SO_BACKLOG, 128)          // (5)
             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            if(!checkPortConfig(httpListenPort)){
            	throw new IllegalStateException("port: " + httpListenPort + " already in use");
            }
            
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(httpListenPort).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
	}

	private boolean checkPortConfig(int listenPort) {
		// TODO Auto-generated method stub
		if(listenPort < 0 || listenPort > 65536){
			throw new IllegalArgumentException("Invalid start port: "+listenPort);
		}
		ServerSocket ss = null;
		DatagramSocket ds =null;
		try {
			ss = new ServerSocket(listenPort);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(listenPort);
			ds.setReuseAddress(true);
			return true;
		}catch(IOException e){
			
		}finally {
			if(ds!=null){
				ds.close();
			}
			if(ss!=null){
				try {
					ss.close();
				}catch(IOException e){
					
				}
			}
		}
		
		return false;
	}

	private void initServerInfo() {
		// TODO Auto-generated method stub
		httpListenPort = snRpcConfig.getHttpPort();
		new ParseXmlToService().parse();
		
	}

	public void end() throws Throwable {
		// TODO Auto-generated method stub
		
	}

}
