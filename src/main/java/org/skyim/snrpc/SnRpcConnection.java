package org.skyim.snrpc;

import org.skyim.snrpc.serializer.SnRpcRequest;
import org.skyim.snrpc.serializer.SnRpcResponse;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月4日 下午2:44:03
 * 类说明
 */
public interface SnRpcConnection {

	public SnRpcResponse connect(final SnRpcRequest request) throws Throwable ;
}
