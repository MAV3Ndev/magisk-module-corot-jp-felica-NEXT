package com.felicanetworks.common.cmnlib.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class DateFormatter {
    public static final String DATE_MINUTE = "yyyyMMddHHmm";
    public static final String DATE_TIME = "yyyyMMddHHmmss";
    public static final String DATE_TIME_MSEC = "yyyyMMddHHmmssSSS";
    protected SimpleDateFormat sdf;

    public DateFormatter(String str) {
        this.sdf = new SimpleDateFormat(str, Locale.US);
    }

    public DateFormatter(String str, String str2) {
        this(str);
        setTimeZone(str2);
    }

    public String getSystemTime() {
        return this.sdf.format(CommonUtil.getSystemTime());
    }

    public String format(Date date) {
        return this.sdf.format(date);
    }

    public void setTimeZone(String str) {
        this.sdf.setTimeZone(TimeZone.getTimeZone(str));
    }

    public Date parse(String str) throws ParseException {
        return this.sdf.parse(str);
    }

    public Date parse(String str, ParsePosition parsePosition) {
        return this.sdf.parse(str, parsePosition);
    }

    public String toString() {
        return "DateFormatter[" + this.sdf.toLocalizedPattern() + "," + this.sdf.getTimeZone().getID() + "]";
    }
}
