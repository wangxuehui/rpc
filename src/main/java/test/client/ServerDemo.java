package test.client;

import org.skyim.snrpc.SnRpcServer;
import org.skyim.snrpc.server.SnNettyRpcServer;


public class ServerDemo {
	public static void main(String[] args) {
		SnRpcInterface inter = new SnRpcImpl();
		SnRpcServer server = new SnNettyRpcServer(new Object[] { inter });
		try {
			server.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
