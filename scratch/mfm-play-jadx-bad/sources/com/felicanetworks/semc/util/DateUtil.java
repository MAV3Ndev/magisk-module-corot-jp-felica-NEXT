package com.felicanetworks.semc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static boolean isTodayInJapan(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.JAPAN);
        return simpleDateFormat.format(new Date(j * 1000)).equals(simpleDateFormat.format(Long.valueOf(System.currentTimeMillis())));
    }
}
