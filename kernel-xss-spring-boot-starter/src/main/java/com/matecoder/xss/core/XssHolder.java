package com.matecoder.xss.core;
/**
 * xss holder
 * @author gavin
 **/
public class XssHolder {
	private static final ThreadLocal<Boolean> XSS_TL = new ThreadLocal<>();

	/**
	 * 是否开启
	 *
	 * @return boolean
	 */
	public static boolean isEnabled() {
		return Boolean.TRUE.equals(XSS_TL.get());
	}

	/**
	 * 标记为开启
	 */
	static void setEnable() {
		XSS_TL.set(Boolean.TRUE);
	}

	/**
	 * 关闭 xss 清理
	 */
	public static void remove() {
		XSS_TL.remove();
	}

}