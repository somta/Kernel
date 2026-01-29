package com.matecoder.xss.core;

/**
 * Xss清理器
 * @author husong
 **/
public interface IXssCleaner {

	/**
	 * 清理 html
	 *
	 * @param html html
	 * @return 清理后的数据
	 */
	String clean(String html);

}