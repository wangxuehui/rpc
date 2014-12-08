package org.skyim.snrpc.exception;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
 * 类说明
 */
public class SnRpcException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8446010720048926785L;

	public SnRpcException() {
		super();
	}

	public SnRpcException(String msg) {
		super(msg);
	}

	public SnRpcException(Throwable t) {
		super(t);
	}

	public SnRpcException(String msg, Throwable t) {
		super(msg, t);
	}
}
