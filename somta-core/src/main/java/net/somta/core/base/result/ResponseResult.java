package net.somta.core.base.result;


public class ResponseResult {

	/**
	 * 返回是否成功
	 */
	private boolean success;
	/**
	 * 返回码
	 */
	private String errorCode;
	/**
	 * 返回消息
	 */
	private String errorMessage;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
