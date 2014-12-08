package org.skyim.snrpc.client;

import java.net.InetSocketAddress;

import org.skyim.snrpc.SnRpcConnection;
import org.skyim.snrpc.SnRpcConnectionFactory;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public class SnNettyRpcConnectionFactory implements SnRpcConnectionFactory{
	
	private InetSocketAddress serverAddr;
	
	public SnNettyRpcConnectionFactory(String host,int port){
		this.serverAddr = new InetSocketAddress(host,port);
	}
	
	@Override
	public SnRpcConnection getConnection() throws Throwable {
		// TODO Auto-generated method stub
		return new SnNettyRpcConnection(this.serverAddr.getHostName(),
				this.serverAddr.getPort());
	}

	@Override
	public void recycle(SnRpcConnection connection) throws Throwable {
		// TODO Auto-generated method stub
		
	}

}
