package net.somta.common.utils.encrypt;

public final class Base64Coder implements Coder {

    private static final byte[] base64Alphabet = new byte[128];
    private static final char[] lookUpBase64Alphabet = new char[64];

    static {
        int j1;
        for(j1 = 0; j1 < 128; ++j1) {
            base64Alphabet[j1] = -1;
        }

        for(j1 = 90; j1 >= 65; --j1) {
            base64Alphabet[j1] = (byte)(j1 - 65);
        }

        for(j1 = 122; j1 >= 97; --j1) {
            base64Alphabet[j1] = (byte)(j1 - 97 + 26);
        }

        for(j1 = 57; j1 >= 48; --j1) {
            base64Alphabet[j1] = (byte)(j1 - 48 + 52);
        }

        base64Alphabet[43] = 62;
        base64Alphabet[47] = 63;

        for(j1 = 0; j1 <= 25; ++j1) {
            lookUpBase64Alphabet[j1] = (char)(65 + j1);
        }

        j1 = 26;

        int l1;
        for(l1 = 0; j1 <= 51; ++l1) {
            lookUpBase64Alphabet[j1] = (char)(97 + l1);
            ++j1;
        }

        l1 = 52;

        for(int i2 = 0; l1 <= 61; ++i2) {
            lookUpBase64Alphabet[l1] = (char)(48 + i2);
            ++l1;
        }

        lookUpBase64Alphabet[62] = 43;
        lookUpBase64Alphabet[63] = 47;
    }

    public Base64Coder() {
    }

    protected static boolean isWhiteSpace(char c) {
        return c == 32 || c == 13 || c == 10 || c == 9;
    }

    protected static boolean isPad(char c) {
        return c == 61;
    }

    protected static boolean isData(char c) {
        return c < 128 && base64Alphabet[c] != -1;
    }

    protected static boolean isBase64(char c) {
        return isWhiteSpace(c) || isPad(c) || isData(c);
    }

    @Override
    public String encode(byte[] abyte0) {
        if(abyte0 == null) {
            return null;
        } else {
            int i = abyte0.length * 8;
            if(i == 0) {
                return "";
            } else {
                int j = i % 24;
                int k = i / 24;
                int l = j == 0?k:k + 1;
                char[] ac = new char[l * 4];
                int i1 = 0;
                int j1 = 0;

                byte byte9;
                byte byte4;
                byte byte2;
                byte byte13;
                byte byte15;
                for(int k1 = 0; k1 < k; ++k1) {
                    byte9 = abyte0[j1++];
                    byte4 = abyte0[j1++];
                    byte2 = abyte0[j1++];
                    byte13 = (byte)(byte4 & 15);
                    byte15 = (byte)(byte9 & 3);
                    byte byte11 = (byte9 & -128) != 0?(byte)(byte9 >> 2 ^ 192):(byte)(byte9 >> 2);
                    byte byte14 = (byte4 & -128) != 0?(byte)(byte4 >> 4 ^ 240):(byte)(byte4 >> 4);
                    byte byte16 = (byte2 & -128) != 0?(byte)(byte2 >> 6 ^ 252):(byte)(byte2 >> 6);
                    ac[i1++] = lookUpBase64Alphabet[byte11];
                    ac[i1++] = lookUpBase64Alphabet[byte14 | byte15 << 4];
                    ac[i1++] = lookUpBase64Alphabet[byte13 << 2 | byte16];
                    ac[i1++] = lookUpBase64Alphabet[byte2 & 63];
                }

                byte byte7;
                if(j == 8) {
                    byte7 = abyte0[j1];
                    byte9 = (byte)(byte7 & 3);
                    byte4 = (byte7 & -128) != 0?(byte)(byte7 >> 2 ^ 192):(byte)(byte7 >> 2);
                    ac[i1++] = lookUpBase64Alphabet[byte4];
                    ac[i1++] = lookUpBase64Alphabet[byte9 << 4];
                    ac[i1++] = 61;
                    ac[i1++] = 61;
                } else if(j == 16) {
                    byte7 = abyte0[j1];
                    byte9 = abyte0[j1 + 1];
                    byte4 = (byte)(byte9 & 15);
                    byte2 = (byte)(byte7 & 3);
                    byte13 = (byte7 & -128) != 0?(byte)(byte7 >> 2 ^ 192):(byte)(byte7 >> 2);
                    byte15 = (byte9 & -128) != 0?(byte)(byte9 >> 4 ^ 240):(byte)(byte9 >> 4);
                    ac[i1++] = lookUpBase64Alphabet[byte13];
                    ac[i1++] = lookUpBase64Alphabet[byte15 | byte2 << 4];
                    ac[i1++] = lookUpBase64Alphabet[byte4 << 2];
                    ac[i1++] = 61;
                }

                return new String(ac);
            }
        }
    }

    @Override
    public byte[] decode(String s) {
        if(s == null) {
            return null;
        } else {
            char[] ac = s.toCharArray();
            int i = removeWhiteSpace(ac);
            if(i % 4 != 0) {
                return null;
            } else {
                int j = i / 4;
                if(j == 0) {
                    return new byte[0];
                } else {
                    /*byte[] abyte0 = null;
                    byte byte0 = false;
                    byte byte1 = false;
                    char c = false;
                    char c1 = false;
                    char c2 = false;
                    char c3 = false;*/
                    int k = 0;
                    int l = 0;
                    int i1 = 0;

                    byte byte3;
                    byte byte6;
                    byte[] abyte0;
                    byte byte0;
                    byte byte1;
                    char c;
                    char c1;
                    char c2;
                    char c3;
                    for(abyte0 = new byte[j * 3]; k < j - 1; ++k) {
                        if(!isData(c = ac[i1++]) || !isData(c1 = ac[i1++]) || !isData(c2 = ac[i1++]) || !isData(c3 = ac[i1++])) {
                            return null;
                        }

                        byte0 = base64Alphabet[c];
                        byte1 = base64Alphabet[c1];
                        byte3 = base64Alphabet[c2];
                        byte6 = base64Alphabet[c3];
                        abyte0[l++] = (byte)(byte0 << 2 | byte1 >> 4);
                        abyte0[l++] = (byte)((byte1 & 15) << 4 | byte3 >> 2 & 15);
                        abyte0[l++] = (byte)(byte3 << 6 | byte6);
                    }

                    if(isData(c = ac[i1++]) && isData(c1 = ac[i1++])) {
                        byte0 = base64Alphabet[c];
                        byte1 = base64Alphabet[c1];
                        c2 = ac[i1++];
                        c3 = ac[i1++];
                        if(isData(c2) && isData(c3)) {
                            byte3 = base64Alphabet[c2];
                            byte6 = base64Alphabet[c3];
                            abyte0[l++] = (byte)(byte0 << 2 | byte1 >> 4);
                            abyte0[l++] = (byte)((byte1 & 15) << 4 | byte3 >> 2 & 15);
                            abyte0[l++] = (byte)(byte3 << 6 | byte6);
                            return abyte0;
                        } else if(isPad(c2) && isPad(c3)) {
                            if((byte1 & 15) != 0) {
                                return null;
                            } else {
                                byte[] abyte1 = new byte[k * 3 + 1];
                                System.arraycopy(abyte0, 0, abyte1, 0, k * 3);
                                abyte1[l] = (byte)(byte0 << 2 | byte1 >> 4);
                                return abyte1;
                            }
                        } else if(isPad(c2) && isPad(c3)) {
                            return null;
                        } else {
                            byte3 = base64Alphabet[c2];
                            if((byte3 & 3) != 0) {
                                return null;
                            } else {
                                byte[] abyte2 = new byte[k * 3 + 2];
                                System.arraycopy(abyte0, 0, abyte2, 0, k * 3);
                                abyte2[l++] = (byte)(byte0 << 2 | byte1 >> 4);
                                abyte2[l] = (byte)((byte1 & 15) << 4 | byte3 >> 2 & 15);
                                return abyte2;
                            }
                        }
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    protected static int removeWhiteSpace(char[] ac) {
        if(ac == null) {
            return 0;
        } else {
            int i = 0;
            int j = ac.length;

            for(int k = 0; k < j; ++k) {
                if(!isWhiteSpace(ac[k])) {
                    ac[i++] = ac[k];
                }
            }

            return i;
        }
    }
}
