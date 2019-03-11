package com.lgh.aio.util;

import com.lgh.aio.commons.Conts;
import com.tencent.mmkv.MMKV;

public class DataUtil {
    private static MMKV mmkv;

    public static MMKV getMmkv() {
        if (mmkv != null)
            return mmkv;
        else return mmkv = MMKV.defaultMMKV();
    }


    public static void setPhone(String phone) {
        getMmkv().encode(Conts.Account.PHONE, phone);
    }

    public static String getPhone() {
        return getMmkv().decodeString(Conts.Account.PHONE);
    }

    public static void setPasswd(String passwd) {
        getMmkv().encode(Conts.Account.PASSWD, passwd);
    }

    public static String getPasswd() {
        return getMmkv().decodeString(Conts.Account.PASSWD);
    }

}
