package org.skyim.snrpc;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月4日 下午2:42:00
 * 类说明
 */
public interface SnRpcConnectionFactory {
	SnRpcConnection getConnection() throws Throwable;
	void recycle(SnRpcConnection connection) throws Throwable;
}
