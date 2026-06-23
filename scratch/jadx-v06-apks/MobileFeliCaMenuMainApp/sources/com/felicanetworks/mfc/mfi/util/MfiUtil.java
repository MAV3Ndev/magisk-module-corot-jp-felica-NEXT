package com.felicanetworks.mfc.mfi.util;

/* JADX INFO: loaded from: classes.dex */
public class MfiUtil {
    public static String getServiceIdFromCid(String str) {
        if (str == null || str.length() != 63) {
            return null;
        }
        return str.substring(7, 15);
    }
}
