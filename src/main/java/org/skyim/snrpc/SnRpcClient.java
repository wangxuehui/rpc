package org.skyim.snrpc;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public interface SnRpcClient {
	public <T> T proxy(Class<T> interfaceClass) throws Throwable;
}
