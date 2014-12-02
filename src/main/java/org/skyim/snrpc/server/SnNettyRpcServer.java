package org.skyim.snrpc.server;

import java.util.Map;

import org.skyim.snrpc.SnRpcServer;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.util.HandlerMapper;


/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年11月29日 下午3:48:53
 * 类说明
 */
public class SnNettyRpcServer implements SnRpcServer{
//	private static final Logger LOGGER = LoggerFactory.getLogger(SnNettyRpcServer.class);
	
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
