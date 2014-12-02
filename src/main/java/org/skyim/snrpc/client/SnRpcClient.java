package org.skyim.snrpc.client;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月1日 下午1:05:39
 * 类说明
 */
public interface SnRpcClient {
	public <T> T proxy(Class<T> interfaceClass) throws Throwable;
}
