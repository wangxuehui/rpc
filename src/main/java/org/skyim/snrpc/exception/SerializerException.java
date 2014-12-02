package org.skyim.snrpc.exception;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * @version 创建时间：2014年11月29日 下午4:45:28
 * 类说明
 */
public class SerializerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8446010720048926785L;

	public SerializerException() {
		super();
	}

	public SerializerException(String msg) {
		super(msg);
	}

	public SerializerException(Throwable t) {
		super(t);
	}

	public SerializerException(String msg, Throwable t) {
		super(msg, t);
	}
}
