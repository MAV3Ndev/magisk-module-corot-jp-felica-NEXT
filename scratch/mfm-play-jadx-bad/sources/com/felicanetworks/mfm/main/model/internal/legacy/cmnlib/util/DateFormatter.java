package com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes3.dex */
public class DateFormatter {
    public static final String DATE_MINUTE = "yyyyMMddHHmm";
    public static final String DATE_TIME_MSEC = "yyyyMMddHHmmssSSS";
    protected SimpleDateFormat sdf;

    public DateFormatter(String format) {
        this.sdf = new SimpleDateFormat(format, Locale.US);
    }

    public DateFormatter(String format, String timeZone) {
        this(format);
        setTimeZone(timeZone);
    }

    public String getSystemTime() {
        return this.sdf.format(CommonUtil.getSystemTime());
    }

    public String format(Date date) {
        return this.sdf.format(date);
    }

    public void setTimeZone(String timeZone) {
        this.sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
    }

    public Date parse(String sdate) throws ParseException {
        return this.sdf.parse(sdate);
    }

    public Date parse(String sdate, ParsePosition pos) {
        return this.sdf.parse(sdate, pos);
    }

    public String toString() {
        return "DateFormatter[" + this.sdf.toLocalizedPattern() + "," + this.sdf.getTimeZone().getID() + "]";
    }
}
