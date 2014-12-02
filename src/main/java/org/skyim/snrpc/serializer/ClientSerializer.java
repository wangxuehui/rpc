package org.skyim.snrpc.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.skyim.snrpc.exception.SerializerException;

/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年12月2日 下午2:03:03
 * 类说明
 */
public interface ClientSerializer {
	
	SnRpcResponse decodeResponse(InputStream inputStream)
	throws SerializerException,IOException;
	
	void encodeRequest(OutputStream outputStream,SnRpcRequest request)
	throws SerializerException,IOException;
}
