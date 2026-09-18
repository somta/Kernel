package com.matecoder.xss.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Safelist;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * xss清理工具类
 * @author husong
 **/
public class XssUtil {
	public static final HtmlSafeList WHITE_LIST = HtmlSafeList.INSTANCE;
	private static final String[] characterToEntityReferenceMap = new String[3000];

	/**
	 * trim 字符串
	 *
	 * @param text text
	 * @return 清理后的 text
	 */
	public static String trim(String text, boolean trim) {
		return trim ? StringUtils.trimWhitespace(text) : text;
	}

	/**
	 * xss 清理
	 *
	 * @param html html
	 * @return 清理后的 html
	 */
	public static String clean(String html) {
		if (StringUtils.hasText(html)) {
			return Jsoup.clean(html, WHITE_LIST);
		}
		return html;
	}

	/**
	 * xss 转义
	 * @param html html
	 * @return 清理后的 html
	 */
	public static String escape(String html, String encoding) {
		if(ObjectUtils.isEmpty(encoding)){
			return html;
		}
		if (StringUtils.hasText(html)) {
			StringBuilder escaped = new StringBuilder(html.length() * 2);
			for(int i = 0; i < html.length(); ++i) {
				char character = html.charAt(i);
				String reference = convertToReference(character, encoding);
				if (reference != null) {
					escaped.append(reference);
				} else {
					escaped.append(character);
				}
			}
			return escaped.toString();
		}
		return html;
	}

	/**
	 * 转换编码，说明：双引号不能转义，否则前端给一个Json字符串就会出现问题
	 * @param character 字节
	 * @param encoding 编码格式
	 * @return 转义后的字符串
	 */
	private static String convertToReference(char character, String encoding) {
		if (encoding.startsWith("UTF-")) {
			switch(character) {
				/*case '"':
					return "&quot;";*/
				case '&':
					return "&amp;";
				case '\'':
					return "&#39;";
				case '<':
					return "&lt;";
				case '>':
					return "&gt;";
			}
		} else if (character < 1000 || character >= 8000 && character < 10000) {
			int index = character < 1000 ? character : character - 7000;
			String entityReference = XssUtil.characterToEntityReferenceMap[index];
			if (entityReference != null) {
				return entityReference;
			}
		}
		return null;
	}

	/**
	 * 做自己的白名单，允许base64的图片通过等
	 */
	public static class HtmlSafeList extends Safelist {
		public static final HtmlSafeList INSTANCE = new HtmlSafeList();

		public HtmlSafeList() {
			addTags("a", "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", "div", "span", "dl", "dt",
				"em", "h1", "h2", "h3", "h4", "h5", "h6", "i", "img", "li", "ol", "p", "pre", "q", "small",
				"strike", "strong", "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul", "hr", "s",
				"video", "source");

			addAttributes("a", "href", "title", "target");
			addAttributes("blockquote", "cite");
			addAttributes("col", "span");
			addAttributes("colgroup", "span");
			addAttributes("img", "align", "alt", "src", "title", "data-href");
			addAttributes("ol", "start");
			addAttributes("q", "cite");
			addAttributes("table", "summary");
			addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width");
			addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width");
			addAttributes("video", "src", "loop", "muted", "poster", "preload", "controls", "width", "height");
			addAttributes("source", "src", "type");

			addAttributes(":all", "class", "style", "height", "width", "type", "id", "name");

			addProtocols("blockquote", "cite", "http", "https");
			addProtocols("cite", "cite", "http", "https");
			addProtocols("q", "cite", "http", "https");
			addProtocols("a", "href", "http", "https", "mailto", "tel");
			addProtocols("img", "src", "http", "https", "data");
		}

		@Override
		public boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
			String key = attr.getKey();
			String value = attr.getValue();
			if (!StringUtils.hasText(value)) {
				return super.isSafeAttribute(tagName, el, attr);
			}

			if ("src".equalsIgnoreCase(key) || "href".equalsIgnoreCase(key)) {
				String lowerValue = value.toLowerCase().trim();
				// 拦截 javascript: / vbscript: / data:text/html 等危险协议
				if (lowerValue.startsWith("javascript:")
						|| lowerValue.startsWith("vbscript:")
						|| lowerValue.startsWith("data:text/html")) {
					return false;
				}
				// 拦截含空白符的变体，如 "java\tscript:"、"java\nscript:"
				if (lowerValue.replaceAll("\\s+", "").startsWith("javascript:")
						|| lowerValue.replaceAll("\\s+", "").startsWith("vbscript:")) {
					return false;
				}
			}

			// 允许 img 的 data: URI（仅限图片类型）
			if ("img".equals(tagName) && "src".equals(key)) {
				String lowerValue = value.toLowerCase();
				if (lowerValue.startsWith("data:image/")) {
					return true;
				}
				return super.isSafeAttribute(tagName, el, attr);
			}

			return super.isSafeAttribute(tagName, el, attr);
		}
	}

}