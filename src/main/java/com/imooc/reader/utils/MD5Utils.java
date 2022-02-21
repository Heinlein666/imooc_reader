package com.imooc.reader.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source, Integer salt) {
        char[] ch = source.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            ch[i] = (char) (ch[i] + salt);
        }
        String target = new String(ch);
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }
}
