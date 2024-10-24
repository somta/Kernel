package net.somta.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * String工具
 * Blog: https://www.somta.net/
 * Date: 2021/3/4
 * @author 明天的地平线
 * @version 1.0.0
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


}
