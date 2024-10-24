package net.somta.core.protocol;


public class ResponseResult {

	/**
	 * 返回是否成功
	 */
	private boolean success;
	/**
	 * 返回码
	 */
	private long errorCode;
	/**
	 * 返回消息
	 */
	private String errorMsg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public long getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(long errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
