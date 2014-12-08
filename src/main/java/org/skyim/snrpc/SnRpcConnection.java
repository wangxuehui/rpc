package org.skyim.snrpc;

import org.skyim.snrpc.serializer.SnRpcRequest;
import org.skyim.snrpc.serializer.SnRpcResponse;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public interface SnRpcConnection {

	public SnRpcResponse sendRequest(final SnRpcRequest request) throws Throwable ;
}
