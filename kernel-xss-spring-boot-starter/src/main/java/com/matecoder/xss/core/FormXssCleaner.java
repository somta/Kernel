package com.matecoder.xss.core;

import com.matecoder.xss.properties.XssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;

/**
 * 表单 xss 处理
 *
 */
@ControllerAdvice
@ConditionalOnProperty(
	prefix = XssProperties.PREFIX,
	name = "enabled",
	havingValue = "true",
	matchIfMissing = true
)
public class FormXssCleaner {
	private final XssProperties properties;
	private final IXssCleaner xssCleaner;

	public FormXssCleaner(XssProperties properties, IXssCleaner xssCleaner) {
		this.properties = properties;
		this.xssCleaner = xssCleaner;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 处理前端传来的表单字符串
		binder.registerCustomEditor(String.class, new StringPropertiesEditor(xssCleaner));
	}

	public static class StringPropertiesEditor extends PropertyEditorSupport {
		private final static Logger logger = LoggerFactory.getLogger(StringPropertiesEditor.class);
		private final IXssCleaner xssCleaner;

		public StringPropertiesEditor(IXssCleaner xssCleaner) {
			this.xssCleaner = xssCleaner;
		}

		@Override
		public String getAsText() {
			Object value = getValue();
			return value != null ? value.toString() : "";
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (text == null) {
				setValue(null);
			} else if (XssHolder.isEnabled()) {
				String value = xssCleaner.clean(text);
				setValue(value);
				logger.debug("Request parameter value:{} cleaned up by xss, current value is:{}.", text, value);
			} else {
				setValue(text);
			}
		}
	}

}