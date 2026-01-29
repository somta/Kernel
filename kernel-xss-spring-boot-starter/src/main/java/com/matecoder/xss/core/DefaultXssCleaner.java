package com.matecoder.xss.core;

import com.matecoder.xss.properties.XssProperties;
import com.matecoder.xss.utils.XssUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Cleaner;

import java.nio.charset.StandardCharsets;

/**
 * 默认的 xss 清理器
 */
public class DefaultXssCleaner implements IXssCleaner {

	private final XssProperties properties;

	public DefaultXssCleaner(XssProperties properties) {
		this.properties = properties;
	}

	@Override
	public String clean(String bodyHtml) {
		// 为空直接返回
		if (StringUtils.isBlank(bodyHtml)) {
			return bodyHtml;
		}
		XssProperties.XssMode mode = properties.getMode();
		if (XssProperties.XssMode.escape == mode) {
			// html 转义
			return XssUtil.escape(bodyHtml, StandardCharsets.UTF_8.name());
		} else {
			// jsoup html 清理
			Document.OutputSettings outputSettings = new Document.OutputSettings()
				// 转义，没找到关闭的方法，目前这个规则最少
				.escapeMode(Entities.EscapeMode.xhtml)
				// 保留换行
				.prettyPrint(false);
			Document dirty = Jsoup.parseBodyFragment(bodyHtml, "");
			Cleaner cleaner = new Cleaner(XssUtil.WHITE_LIST);
			Document clean = cleaner.clean(dirty);
			clean.outputSettings(outputSettings);
			// 获得清理后的html
			String escapedHtml = clean.body().html();
			// 反转义
			return Entities.unescape(escapedHtml);
		}
	}

}