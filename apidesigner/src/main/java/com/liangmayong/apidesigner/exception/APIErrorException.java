package com.liangmayong.apidesigner.exception;

/**
 * APIErrorException
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class APIErrorException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * if doing a HTTP authentication, this error may occur.
	 */
	public static final int AUTH_FAILURE_ERROR = 0xA1;
	/**
	 * Socket closed, servers, DNS error will produce the error.
	 */
	public static final int NETWORK_ERROR = 0xA2;
	/**
	 * Similar to NetworkError, this is the client without a network connection.
	 */
	public static final int NO_CONNECTION_ERROR = 0xA3;
	/**
	 * When using JsonObjectRequest or JsonArrayRequest, if received JSON is
	 * abnormal, can produce abnormal.
	 */
	public static final int PARSE_ERROR = 0xA4;
	/**
	 * A mistake of the server's response, most likely 4 or 5 xx xx HTTP status
	 * code.
	 */
	public static final int SERVER_ERROR = 0xA5;
	/**
	 * The Socket timeout, the server is too busy or network latency can produce
	 * this exception. By default, a Volley of timeout time of 2.5 seconds. If
	 * you get this error RetryPolicy may be used.
	 */
	public static final int TIMEOUT_ERROR = 0xA6;
	/**
	 * Other unknown error
	 */
	public static final int UNKOWN_ERROR = 0xA7;

	private int errorCode = 0;
	private Object object = null;
	private Exception exception = null;

	public Object getObj() {
		return object;
	}

	public Exception getException() {
		return exception;
	}

	public APIErrorException(int errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

	public APIErrorException(int errorCode, Exception exception) {
		super();
		this.exception = exception;
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
