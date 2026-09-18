package com.matecoder.core.protocol;

import com.matecoder.core.base.IBaseError;

/**
 * 响应类父类
 * @author husong
 **/
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

	/**
	 * 构建错误信息
	 * @return 真实错误信息
	 */
	protected static String buildErrorMsg(IBaseError baseError,Object... args){
		if(args != null && args.length > 0){
			return String.format(baseError.getErrorMsg(), args);
		}else{
			return baseError.getErrorMsg();
		}
	}
}
