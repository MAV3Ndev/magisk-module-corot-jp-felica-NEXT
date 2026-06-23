package com.felicanetworks.mfw.a.cmn;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* JADX INFO: compiled from: DateUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class x {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final Calendar f182a = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    static Object b = new Object();

    private static long a(int i, int i2, int i3, int i4, int i5, int i6) {
        long time;
        synchronized (b) {
            f182a.set(1, i);
            f182a.set(2, i2);
            f182a.set(5, i3);
            f182a.set(11, i4);
            f182a.set(12, i5);
            f182a.set(13, i6);
            time = f182a.getTime().getTime();
        }
        return time;
    }

    private static long b(String str) {
        return a(Integer.parseInt(str.substring(0, 4)), Integer.parseInt(str.substring(4, 6)) - 1, Integer.parseInt(str.substring(6, 8)), Integer.parseInt(str.substring(8, 10)), Integer.parseInt(str.substring(10, 12)), str.length() != 12 ? Integer.parseInt(str.substring(12, 14)) : 0);
    }

    public static String c() {
        return n(System.currentTimeMillis());
    }

    private static int d(int i, int i2) {
        return i2 != 2 ? new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}[i2 - 1] : e(i) ? 29 : 28;
    }

    private static boolean e(int i) {
        return i % 400 == 0 || (i % 100 != 0 && i % 4 == 0);
    }

    private static boolean f(int i, int i2, int i3) {
        return 1 <= i3 && i3 <= d(i, i2);
    }

    public static boolean g(String str) {
        if (str != null && (str.length() == 12 || str.length() == 14 || str.length() == 19)) {
            return h(str.substring(0, 4), str.substring(4, 6), str.substring(6, 8), str.substring(8, 10), str.substring(10, 12), str.length() > 12 ? str.substring(12, 14) : "00", str.length() > 14 ? str.substring(14, 15) : "+", str.length() > 14 ? str.substring(15, 17) : "00", str.length() > 14 ? str.substring(17, 19) : "00");
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [targetTime = " + str + "]");
        throw new c1(x.class, "isValidDateFormat", stringBuffer.toString());
    }

    private static boolean h(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        if (b1.k(str) && b1.k(str2) && b1.k(str3) && b1.k(str4) && b1.k(str5) && b1.k(str6) && b1.k(str8) && b1.k(str9) && (str7.equals("+") || str7.equals("-"))) {
            int i = Integer.parseInt(str);
            int i2 = Integer.parseInt(str2);
            int i3 = Integer.parseInt(str3);
            int i4 = Integer.parseInt(str4);
            int i5 = Integer.parseInt(str5);
            int i6 = Integer.parseInt(str6);
            if (m(i) && k(i2) && f(i, i2, i3) && i(i4) && j(i5) && l(i6)) {
                return true;
            }
        }
        return false;
    }

    private static boolean i(int i) {
        return i >= 0 && i <= 23;
    }

    private static boolean j(int i) {
        return i >= 0 && i <= 59;
    }

    private static boolean k(int i) {
        return 1 <= i && i <= 12;
    }

    private static boolean l(int i) {
        return i >= 0 && i <= 59;
    }

    private static boolean m(int i) {
        return i >= 0 && i <= 9999;
    }

    public static String n(long j) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        if (j < 0) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal argument.");
            stringBuffer.append(" [targetTime = " + j + "]");
            throw new c1(x.class, "convertMillisToJST", stringBuffer.toString());
        }
        synchronized (b) {
            f182a.setTime(new Date(j + 32400000));
            i = f182a.get(1);
            i2 = f182a.get(2) + 1;
            i3 = f182a.get(5);
            i4 = f182a.get(11);
            i5 = f182a.get(12);
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append(b1.P(String.valueOf(i), 4));
        stringBuffer2.append(b1.P(String.valueOf(i2), 2));
        stringBuffer2.append(b1.P(String.valueOf(i3), 2));
        stringBuffer2.append(b1.P(String.valueOf(i4), 2));
        stringBuffer2.append(b1.P(String.valueOf(i5), 2));
        return stringBuffer2.toString();
    }

    public static long o(String str) {
        if (str != null && ((str.length() == 12 || str.length() == 14) && g(str))) {
            return b(str) - 32400000;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [targetTime = " + str + "]");
        throw new c1(x.class, "convertJSTtoMillis", stringBuffer.toString());
    }

    public static long p(String str) {
        if (str != null && str.length() == 19 && g(str)) {
            String strSubstring = str.substring(15, 17);
            String strSubstring2 = str.substring(17, 19);
            long jB = b(str);
            long j = Long.parseLong(strSubstring) * 60 * 60 * 1000;
            long j2 = Long.parseLong(strSubstring2) * 60 * 1000;
            return str.indexOf(43) != -1 ? (jB - j) - j2 : jB + j + j2;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [targetTime = " + str + "]");
        throw new c1(x.class, "convertToMillisWithTZD", stringBuffer.toString());
    }
}
