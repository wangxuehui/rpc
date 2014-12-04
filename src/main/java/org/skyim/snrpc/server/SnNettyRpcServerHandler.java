package org.skyim.snrpc.server;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.skyim.snrpc.conf.RpcService;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.serializer.SnRpcRequest;
import org.skyim.snrpc.serializer.SnRpcResponse;
import org.skyim.snrpc.util.ReflectionCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月3日 下午3:17:26 类说明
 */
public class SnNettyRpcServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SnNettyRpcServer.class);

	private final static Map<String, RpcService> serviceMap = new HashMap<String, RpcService>();
	private Map<String, Object> handlersMap;

	public SnNettyRpcServerHandler(Map<String, Object> handlersMap) {
		super();
		this.handlersMap = handlersMap;
	}

	public static void putService(RpcService service) {
		if (null != service) {
			serviceMap.put(service.getName(), service);
		}
	}

	public static RpcService getServiceMap(String serviceName) {
		return serviceMap.get(serviceName);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
		try {
			// Do something with msg
			if (!(msg instanceof SnRpcRequest)) {
				return;
			}

			SnRpcRequest request = (SnRpcRequest) msg;

			SnRpcResponse response = new SnRpcResponse(request.getRequestID());

			try {
				Object result = handler(request);
				response.setResult(result);
			} catch (Throwable t) {
				LOGGER.warn("handler rpc request fail! request:<{}>",
						new Object[] { request }, t);
				response.setException(t);
			}

		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	private Object handler(SnRpcRequest request) throws Throwable {
		// TODO Auto-generated method stub
		if (SnRpcConfig.getInstance().getDevMod()) {
			StatisticsService.reportBeforeInvoke(request);
		}
		String className = request.getClassName();
		String[] classNameSplits = className.split("\\.");
		String serviceName = classNameSplits[classNameSplits.length - 1];
		RpcService rpcService = getServiceMap(serviceName);
		if (null == rpcService) {
			throw new NullPointerException("server interface config is null");
		}
		Class<?> clazz = rpcService.getRpcImplementor().getProcessorClass();
		Method method = ReflectionCache.getMethod(clazz.getName(),
				request.getMethodName(), request.getParameterTypes());
		Object[] parameters = request.getParameters();
		// get handler
		Object handler = handlersMap.get(request.getClassName());
		// invoke
		Object result = method.invoke(handler, parameters);

		return result;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
		// Close the connection when an exception is raised.
		cause.printStackTrace();
		ctx.close();
	}
}
