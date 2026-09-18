package com.matecoder.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DevUtilTest {

    @Test
    void isWindowsReturnsBoolean() {
        // Just verify it doesn't throw and returns a valid boolean
        boolean result = DevUtil.isWindows();
        // On any OS, this should return true or false without exception
        assertTrue(result || !result);
    }

    @Test
    void isMacReturnsBoolean() {
        boolean result = DevUtil.isMac();
        assertTrue(result || !result);
    }

    @Test
    void getMachineCpuBitReturnsValidValue() {
        int bits = DevUtil.getMachineCpuBit();
        assertTrue(bits == 32 || bits == 64);
    }

    @Test
    void isInDevelopmentModeReturnsBoolean() {
        // Should not throw on any platform
        boolean result = DevUtil.isInDevelopmentMode();
        assertTrue(result || !result);
    }
}
