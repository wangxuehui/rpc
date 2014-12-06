package test.client;

import org.skyim.snrpc.SnRpcServer;
import org.skyim.snrpc.conf.SnRpcConfig;
import org.skyim.snrpc.server.SnNettyRpcServer;
import org.skyim.snrpc.zookeeper.provider.ServiceProvider;




public class ServerDemo {
	public static void main(String[] args) {
		SnRpcInterface inter = new SnRpcImpl();
		SnRpcServer server = new SnNettyRpcServer(new Object[] { inter });		
		SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
		ServiceProvider provider = new ServiceProvider();
        provider.publish(snRpcConfig.getProperty("snrpc.http.host", "127.0.0.1"), Integer.parseInt(snRpcConfig.getProperty("snrpc.http.port","8080")));		
		try {
			server.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
