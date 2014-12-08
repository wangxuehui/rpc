package org.skyim.snrpc;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public interface SnRpcConnectionFactory {
	SnRpcConnection getConnection() throws Throwable;
	void recycle(SnRpcConnection connection) throws Throwable;
}
