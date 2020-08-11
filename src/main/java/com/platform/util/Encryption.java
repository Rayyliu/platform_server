package com.platform.util;

import com.hc.common.config.util.Md5SHA1Util;

public class Encryption {

    public static String Md5Util(String PrimordialString){
        return Md5SHA1Util.md5(PrimordialString);
    }
}
