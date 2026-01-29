package com.matecoder.xss.core;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.matecoder.xss.properties.XssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * jackson的xss处理,处理请求体为application/json的请求
 * @author husong
 **/
public class JsonXssCleaner extends JsonDeserializer<String> {
	private final static Logger logger = LoggerFactory.getLogger(JsonXssCleaner.class);

	private final XssProperties properties;
	private final IXssCleaner xssCleaner;

	public JsonXssCleaner(XssProperties properties, IXssCleaner xssCleaner) {
		this.properties = properties;
		this.xssCleaner = xssCleaner;
	}

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
		String text = p.getValueAsString();
		if (text == null) {
			return null;
		}
		if (XssHolder.isEnabled()) {
			String value = xssCleaner.clean(text);
			logger.debug("Json property value:{} cleaned up by xss, current value is:{}.", text, value);
			return value;
		} else {
			return text;
		}
	}

}