package test.client;

import org.skyim.snrpc.SnRpcClient;
import org.skyim.snrpc.SnRpcConnectionFactory;
import org.skyim.snrpc.client.CommonSnRpcClient;
import org.skyim.snrpc.client.SnNettyRpcConnectionFactory;



public class ClientDemo {
		public static void main(String[] args) {
			SnRpcConnectionFactory factory = new SnNettyRpcConnectionFactory(
		                "localhost", 8080);
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

