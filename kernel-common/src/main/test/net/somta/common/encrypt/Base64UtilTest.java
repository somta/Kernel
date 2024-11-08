package net.somta.common.encrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Base64UtilTest {

    private final String srcStr = "https://somta.net";

    private final String base64Str = "aHR0cHM6Ly9zb210YS5uZXQ=";

    @Test
    public void encodeTest(){
        String tempBase64Str = Base64Util.encode(srcStr);
        Assertions.assertEquals(base64Str,tempBase64Str);
    }

    @Test
    public void encodeFromByteTest(){
        String tempBase64Str = Base64Util.encode(srcStr.getBytes());
        Assertions.assertEquals(base64Str,tempBase64Str);
    }

    @Test
    public void encodeUrlTest(){
        String tempUrl = Base64Util.encodeUrl(srcStr);
        Assertions.assertEquals(base64Str,tempUrl);
    }

    @Test
    public void decodeTest(){
        String tempSrcStr = Base64Util.decode(base64Str);
        Assertions.assertEquals(srcStr,tempSrcStr);
    }

    @Test
    public void decodeToByteTest(){
        byte[] tempStrByte = Base64Util.decodeToByte(base64Str);
        Assertions.assertEquals(srcStr,new String(tempStrByte));
    }

    @Test
    public void decodeUrlTest(){
        String tempSrcUrl = Base64Util.decodeUrl(base64Str);
        Assertions.assertEquals(srcStr,tempSrcUrl);
    }

}
