package net.somta.common.encrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Md5UtilTest {

    private final String srcStr = "https://somta.net";

    private final String md5Str = "a4884b8e8a0c7a44b8d3d58a8c1e9291";

    @Test
    public void md5Test(){
        String tempMd5Str = Md5Util.encrypt(srcStr);
        Assertions.assertEquals(md5Str,tempMd5Str);
    }
}
