package com.matecoder.common.encrypt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DigestUtilTest {

    @Test
    void sha1HexOfString() {
        String result = DigestUtil.sha1Hex("hello");
        assertNotNull(result);
        assertEquals(40, result.length()); // SHA-1 produces 40 hex chars
        // SHA-1 of "hello" is well-known
        assertEquals("aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d", result);
    }

    @Test
    void sha1HexOfBytes() {
        byte[] data = "hello".getBytes();
        String result = DigestUtil.sha1Hex(data);
        assertNotNull(result);
        assertEquals(40, result.length());
    }

    @Test
    void sha1HexNullStringReturnsNull() {
        assertNull(DigestUtil.sha1Hex((String) null));
    }

    @Test
    void sha1HexNullBytesReturnsNull() {
        assertNull(DigestUtil.sha1Hex((byte[]) null));
    }

    @Test
    void sha1HexConsistent() {
        String result1 = DigestUtil.sha1Hex("test");
        String result2 = DigestUtil.sha1Hex("test");
        assertEquals(result1, result2);
    }

    @Test
    void sha1HexDifferentInputs() {
        String result1 = DigestUtil.sha1Hex("hello");
        String result2 = DigestUtil.sha1Hex("world");
        assertNotEquals(result1, result2);
    }
}
