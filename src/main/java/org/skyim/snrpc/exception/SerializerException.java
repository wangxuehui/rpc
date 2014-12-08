package org.skyim.snrpc.exception;
/**
 * @author skyim E-mail:wxh64788665@gmail.com
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
