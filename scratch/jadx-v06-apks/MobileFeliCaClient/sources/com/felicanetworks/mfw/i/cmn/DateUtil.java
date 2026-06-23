package com.felicanetworks.mfw.i.cmn;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
public class DateUtil {
    private static final Calendar CALENDAR = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    private static StringBuffer sBuf = new StringBuffer();
    private static StringBuffer sTimeBuff = new StringBuffer();
    private static final int[] MONTH_TABLE = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private static boolean isValidHour(int i) {
        return i >= 0 && i <= 23;
    }

    private static boolean isValidMinite(int i) {
        return i >= 0 && i <= 59;
    }

    private static boolean isValidMonth(int i) {
        return 1 <= i && i <= 12;
    }

    private static boolean isValidSecond(int i) {
        return i >= 0 && i <= 59;
    }

    private static boolean isValidYear(int i) {
        return i >= 0 && i <= 9999;
    }

    public static String getCurrentJSTString() {
        return toJST(System.currentTimeMillis());
    }

    public static synchronized String toJST(long j) {
        if (j < 0) {
            sBuf.setLength(0);
            sBuf.append("Illegal argument.");
            sBuf.append(" [targetTime = " + j + "]");
            throw new SysException((Class<?>) DateUtil.class, "convertMillisToJST", sBuf.toString());
        }
        CALENDAR.setTime(new Date(j + 32400000));
        int i = CALENDAR.get(1);
        int i2 = CALENDAR.get(2) + 1;
        int i3 = CALENDAR.get(5);
        int i4 = CALENDAR.get(11);
        int i5 = CALENDAR.get(12);
        sTimeBuff.setLength(0);
        sTimeBuff.append(StringUtil.zeroPadding(String.valueOf(i), 4));
        sTimeBuff.append(StringUtil.zeroPadding(String.valueOf(i2), 2));
        sTimeBuff.append(StringUtil.zeroPadding(String.valueOf(i3), 2));
        sTimeBuff.append(StringUtil.zeroPadding(String.valueOf(i4), 2));
        sTimeBuff.append(StringUtil.zeroPadding(String.valueOf(i5), 2));
        return sTimeBuff.toString();
    }

    public static long toMillis(String str) {
        if (str == null || ((str.length() != 12 && str.length() != 14) || !isValidDateFormat(str))) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [targetTime = " + str + "]");
            throw new SysException((Class<?>) DateUtil.class, "convertJSTtoMillis", stringBuffer.toString());
        }
        return convertToMillis(str) - 32400000;
    }

    public static long toMillisWithTZD(String str) {
        if (str == null || str.length() != 19 || !isValidDateFormat(str)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [targetTime = " + str + "]");
            throw new SysException((Class<?>) DateUtil.class, "convertToMillisWithTZD", stringBuffer.toString());
        }
        String strSubstring = str.substring(15, 17);
        String strSubstring2 = str.substring(17, 19);
        long jConvertToMillis = convertToMillis(str);
        long j = Long.parseLong(strSubstring) * 60 * 60 * 1000;
        long j2 = Long.parseLong(strSubstring2) * 60 * 1000;
        return str.indexOf(43) != -1 ? (jConvertToMillis - j) - j2 : jConvertToMillis + j + j2;
    }

    public static boolean isValidDateFormat(String str) {
        if (str == null || (str.length() != 12 && str.length() != 14 && str.length() != 19)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [targetTime = " + str + "]");
            throw new SysException((Class<?>) DateUtil.class, "isValidDateFormat", stringBuffer.toString());
        }
        return isValidDateFormat(str.substring(0, 4), str.substring(4, 6), str.substring(6, 8), str.substring(8, 10), str.substring(10, 12), str.length() > 12 ? str.substring(12, 14) : "00", str.length() > 14 ? str.substring(14, 15) : "+", str.length() > 14 ? str.substring(15, 17) : "00", str.length() > 14 ? str.substring(17, 19) : "00");
    }

    private static boolean isValidDateFormat(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (StringUtil.isDecString(str) && StringUtil.isDecString(str2) && StringUtil.isDecString(str3) && StringUtil.isDecString(str4) && StringUtil.isDecString(str5) && StringUtil.isDecString(str6) && StringUtil.isDecString(str8) && StringUtil.isDecString(str9) && (str7.equals("+") || str7.equals("-"))) {
            int i = Integer.parseInt(str);
            int i2 = Integer.parseInt(str2);
            int i3 = Integer.parseInt(str3);
            int i4 = Integer.parseInt(str4);
            int i5 = Integer.parseInt(str5);
            int i6 = Integer.parseInt(str6);
            if (isValidYear(i) && isValidMonth(i2) && isValidDate(i, i2, i3) && isValidHour(i4) && isValidMinite(i5) && isValidSecond(i6)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidDate(int i, int i2, int i3) {
        return 1 <= i3 && i3 <= getMaxDate(i, i2);
    }

    private static int getMaxDate(int i, int i2) {
        if (i2 != 2) {
            return MONTH_TABLE[i2 - 1];
        }
        return isLeapYear(i) ? 29 : 28;
    }

    private static boolean isLeapYear(int i) {
        return i % 400 == 0 || (i % 100 != 0 && i % 4 == 0);
    }

    private static long convertToMillis(String str) {
        return convertToMillis(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4, 6)) - 1, Integer.parseInt(str.substring(6, 8)), Integer.parseInt(str.substring(8, 10)), Integer.parseInt(str.substring(10, 12)), str.length() != 12 ? Integer.parseInt(str.substring(12, 14)) : 0);
    }

    private static synchronized long convertToMillis(int i, int i2, int i3, int i4, int i5, int i6) {
        CALENDAR.set(1, i);
        CALENDAR.set(2, i2);
        CALENDAR.set(5, i3);
        CALENDAR.set(11, i4);
        CALENDAR.set(12, i5);
        CALENDAR.set(13, i6);
        return CALENDAR.getTime().getTime();
    }
}
