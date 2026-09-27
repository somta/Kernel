package com.matecoder.common.encrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Md5UtilTest {

    private final String srcStr = "https://matecoder.com";

    private final String md5Str = "69931075beaf4ad131d874a59c241506";

    @Test
    public void md5Test(){
        String tempMd5Str = Md5Util.encrypt(srcStr);
        Assertions.assertEquals(md5Str,tempMd5Str);
    }
}
