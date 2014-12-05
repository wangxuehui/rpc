package test.client;

import org.skyim.snrpc.SnRpcClient;
import org.skyim.snrpc.SnRpcConnectionFactory;
import org.skyim.snrpc.client.CommonSnRpcClient;
import org.skyim.snrpc.client.SnNettyRpcConnectionFactory;
import org.skyim.snrpc.conf.SnRpcConfig;



public class ClientDemo {
		public static void main(String[] args) {
			SnRpcConfig snRpcConfig = SnRpcConfig.getInstance();
			snRpcConfig.loadProperties("snrpcserver.properties");
			SnRpcConnectionFactory factory = new SnNettyRpcConnectionFactory(
					snRpcConfig.getProperty("snrpc.http.host", "localhost"), Integer.parseInt(snRpcConfig.getProperty("snrpc.http.port", "8080")));
		    SnRpcClient client = new CommonSnRpcClient(factory);
		    try {
		        SnRpcInterface clazz = client.proxy(SnRpcInterface.class);
		        String message = clazz.getMessage("come on");
		        System.out.println("client receive message .... : " + message);
		    } catch (Throwable e) {
		        e.printStackTrace();
		    }
		}
	
}

