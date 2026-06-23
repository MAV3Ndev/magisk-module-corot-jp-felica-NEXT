package com.felicanetworks.mfc.mfi.util;

/* JADX INFO: loaded from: classes3.dex */
public class MfiUtil {
    public static String getServiceIdFromCid(String cid) {
        if (cid == null || cid.length() != 63) {
            return null;
        }
        return cid.substring(7, 15);
    }
}
