package test.client;

import org.skyim.snrpc.SnRpcClient;
import org.skyim.snrpc.SnRpcConnectionFactory;
import org.skyim.snrpc.client.CommonSnRpcClient;
import org.skyim.snrpc.client.SnNettyRpcConnectionFactory;
import org.skyim.snrpc.zookeeper.consumer.ServiceConsumer;




public class ClientDemo {
		public static void main(String[] args) {
	        ServiceConsumer consumer = new ServiceConsumer();
	        String provider = consumer.lookup();
			String[] providers = provider.split(":");
			
			if(providers.length == 3) {
				System.out.println(providers);
				SnRpcConnectionFactory factory = new SnNettyRpcConnectionFactory(
						providers[1], Integer.parseInt(providers[2]));
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
	
}

