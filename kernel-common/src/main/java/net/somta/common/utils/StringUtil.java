package net.somta.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * String util
 * @author husong
 */
public class StringUtil {

    /**
     * 去掉双引号
     * @param str str
     * @return str
     */
    public static String removeDoubleQuotes(String str) {
        if(StringUtils.isEmpty(str)){
           return str;
        }
        return str.replace("\"", "");
    }

    /**
     * 去掉回车符
     * @param str str
     * @return str
     */
    public static String removeEnterKey(String str) {
        if(StringUtils.isEmpty(str)){
            return str;
        }
        return str.replaceAll("\n","");
    }

    /**
     * 对象转字符串
     * @param obj obj
     * @return 返回字符串
     */
    public static String valueOf(Object obj) {
        return (obj == null) ? null : obj.toString();
    }

}
