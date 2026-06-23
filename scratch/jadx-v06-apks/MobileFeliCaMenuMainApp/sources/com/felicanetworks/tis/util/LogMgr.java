package com.felicanetworks.tis.util;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LogMgr {
    public static final int API = 3;
    private static final int ARGS_LENGTH_MAX = 8;
    public static final int CLS = 5;
    public static final int DBG = 6;
    public static final int ERR = 1;
    public static final int PKG = 4;
    private static final String S_API = "[API]";
    private static final String S_CLS = "[CLS]";
    private static final String S_CMP = "[MFIC]";
    private static final String S_DBG = "[DBG]";
    private static final String S_ERR = "[ERR]";
    private static final String S_PKG = "[PKG]";
    private static final String S_TRC = "[TRC]";
    private static final String S_UNK = "[UNK]";
    private static final String S_WAR = "[WAR]";
    public static final int TRC = 7;
    public static final int WAR = 2;
    private static final Object[] argDummy;
    protected static boolean bUseHomeBrewingFromat = true;
    private static final boolean enable = false;
    private static final boolean enableClass = false;
    static Map<String, Integer> enableClassName = null;
    public static int level = 4;
    private static Object[] sNew_args;
    private static volatile Object[] sObjs;
    private static StringBuffer sb;
    private static StringBuffer sbConv;
    private static StringBuffer sbTag;
    private static StringBuilder sbuilder;

    protected static void log(int i, String str, Object[] objArr, long j) {
    }

    public static void logArray(int i, byte[] bArr) {
    }

    public static void logArray(int i, byte[] bArr, int i2, int i3) {
    }

    public static void printStackTrace(int i, Throwable th) {
    }

    static {
        HashMap map = new HashMap();
        enableClassName = map;
        map.put("com.felicanetworks.mfc.Felica", 7);
        enableClassName.put("com.felicanetworks.mfc.FSC", 7);
        enableClassName.put("com.felicanetworks.mfc.PushSegmentParcelableWrapper", 7);
        enableClassName.put("com.felicanetworks.mfc.PushSegmentParcelableWrapper$1", 7);
        argDummy = new Object[0];
        sNew_args = new Object[8];
        sObjs = new Object[8];
        sb = new StringBuffer(256);
        sbTag = new StringBuffer(128);
        sbConv = new StringBuffer(64);
        sbuilder = new StringBuilder(128);
    }

    protected static String getLevel(int i) {
        HashMap map = new HashMap();
        map.put(1, S_ERR);
        map.put(2, S_WAR);
        map.put(3, S_API);
        map.put(4, S_PKG);
        map.put(5, S_CLS);
        map.put(6, S_DBG);
        map.put(7, S_TRC);
        String str = (String) map.get(Integer.valueOf(i));
        return str == null ? S_UNK : str;
    }

    public static boolean setUseHomeBrewingFromat(boolean z) {
        boolean z2 = bUseHomeBrewingFromat;
        bUseHomeBrewingFromat = z;
        return z2;
    }

    public static boolean getUseHomeBrewingFromat() {
        return bUseHomeBrewingFromat;
    }

    public static boolean checkEnableClass(StackTraceElement stackTraceElement, int i) {
        Integer num;
        String className = stackTraceElement.getClassName();
        return (className == null || (num = enableClassName.get(className)) == null || i > num.intValue()) ? false : true;
    }

    public static void log(int i, String str) {
        log(i, str, argDummy, 0L);
    }

    public static void log(int i, String str, Object obj) {
        sObjs[0] = obj;
        log(i, str, sObjs, 1L);
    }

    public static void log(int i, String str, Object obj, Object obj2) {
        sObjs[0] = obj;
        sObjs[1] = obj2;
        log(i, str, sObjs, 2L);
    }

    public static void log(int i, String str, Object obj, Object obj2, Object obj3) {
        sObjs[0] = obj;
        sObjs[1] = obj2;
        sObjs[2] = obj3;
        log(i, str, sObjs, 3L);
    }

    public static void log(int i, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        sObjs[0] = obj;
        sObjs[1] = obj2;
        sObjs[2] = obj3;
        sObjs[3] = obj4;
        log(i, str, sObjs, 4L);
    }

    public static void log(int i, String str, Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        sObjs[0] = obj;
        sObjs[1] = obj2;
        sObjs[2] = obj3;
        sObjs[3] = obj4;
        sObjs[4] = obj5;
        log(i, str, sObjs, 5L);
    }

    public static void log(int i, String str, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        sObjs[0] = obj;
        sObjs[1] = obj2;
        sObjs[2] = obj3;
        sObjs[3] = obj4;
        sObjs[4] = obj5;
        sObjs[5] = obj6;
        log(i, str, sObjs, 6L);
    }

    public static void log(int i, String str, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        sObjs[0] = obj;
        sObjs[1] = obj2;
        sObjs[2] = obj3;
        sObjs[3] = obj4;
        sObjs[4] = obj5;
        sObjs[5] = obj6;
        sObjs[6] = obj7;
        log(i, str, sObjs, 7L);
    }

    public static void log(int i, String str, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        sObjs[0] = obj;
        sObjs[1] = obj2;
        sObjs[2] = obj3;
        sObjs[3] = obj4;
        sObjs[4] = obj5;
        sObjs[5] = obj6;
        sObjs[6] = obj7;
        sObjs[7] = obj8;
        log(i, str, sObjs, 8L);
    }

    protected static void output(int i, StackTraceElement stackTraceElement, String str, Object[] objArr, long j) {
        boolean z;
        sbTag.setLength(0);
        sbTag.append(S_CMP);
        sbTag.append(getLevel(i));
        sbTag.append('[');
        sbTag.append(stackTraceElement.getFileName());
        sbTag.append("][");
        sbTag.append(stackTraceElement.getMethodName());
        sbTag.append("(L:");
        sbTag.append(stackTraceElement.getLineNumber());
        sbTag.append(")]");
        String string = sbTag.toString();
        for (int i2 = 0; i2 < j; i2++) {
            if ((objArr[i2] instanceof byte[]) || (objArr[i2] instanceof int[])) {
                z = true;
                break;
            }
        }
        z = false;
        if (z) {
            for (int i3 = 0; i3 < j; i3++) {
                if (objArr[i3] instanceof byte[]) {
                    sNew_args[i3] = ByteArray2String((byte[]) objArr[i3]);
                } else if (objArr[i3] instanceof int[]) {
                    sNew_args[i3] = IntegerArray2String((int[]) objArr[i3]);
                } else {
                    sNew_args[i3] = objArr[i3];
                }
            }
            if (i == 1) {
                Log.e(string, format(str, sNew_args, j));
                return;
            } else if (i == 2) {
                Log.w(string, format(str, sNew_args, j));
                return;
            } else {
                Log.i(string, format(str, sNew_args, j));
                return;
            }
        }
        if (str.equals("%s") && j == 1 && objArr[0] != null && (objArr[0] instanceof String)) {
            if (i == 1) {
                Log.e(string, (String) objArr[0]);
                return;
            } else if (i == 2) {
                Log.w(string, (String) objArr[0]);
                return;
            } else {
                Log.i(string, (String) objArr[0]);
                return;
            }
        }
        if (i == 1) {
            Log.e(string, format(str, objArr, j));
        } else if (i == 2) {
            Log.w(string, format(str, objArr, j));
        } else {
            Log.i(string, format(str, objArr, j));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static java.lang.String format(java.lang.String r12, java.lang.Object[] r13, long r14) {
        /*
            Method dump skipped, instruction units count: 564
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.tis.util.LogMgr.format(java.lang.String, java.lang.Object[], long):java.lang.String");
    }

    static String ByteArray2String(byte[] bArr) {
        sbConv.setLength(0);
        String[] strArr = {"0x00", "0x01", "0x02", "0x03", "0x04", "0x05", "0x06", "0x07", "0x08", "0x09", "0x0A", "0x0B", "0x0C", "0x0D", "0x0E", "0x0F", "0x10", "0x11", "0x12", "0x13", "0x14", "0x15", "0x16", "0x17", "0x18", "0x19", "0x1A", "0x1B", "0x1C", "0x1D", "0x1E", "0x1F", "0x20", "0x21", "0x22", "0x23", "0x24", "0x25", "0x26", "0x27", "0x28", "0x29", "0x2A", "0x2B", "0x2C", "0x2D", "0x2E", "0x2F", "0x30", "0x31", "0x32", "0x33", "0x34", "0x35", "0x36", "0x37", "0x38", "0x39", "0x3A", "0x3B", "0x3C", "0x3D", "0x3E", "0x3F", "0x40", "0x41", "0x42", "0x43", "0x44", "0x45", "0x46", "0x47", "0x48", "0x49", "0x4A", "0x4B", "0x4C", "0x4D", "0x4E", "0x4F", "0x50", "0x51", "0x52", "0x53", "0x54", "0x55", "0x56", "0x57", "0x58", "0x59", "0x5A", "0x5B", "0x5C", "0x5D", "0x5E", "0x5F", "0x60", "0x61", "0x62", "0x63", "0x64", "0x65", "0x66", "0x67", "0x68", "0x69", "0x6A", "0x6B", "0x6C", "0x6D", "0x6E", "0x6F", "0x70", "0x71", "0x72", "0x73", "0x74", "0x75", "0x76", "0x77", "0x78", "0x79", "0x7A", "0x7B", "0x7C", "0x7D", "0x7E", "0x7F", "0x80", "0x81", "0x82", "0x83", "0x84", "0x85", "0x86", "0x87", "0x88", "0x89", "0x8A", "0x8B", "0x8C", "0x8D", "0x8E", "0x8F", "0x90", "0x91", "0x92", "0x93", "0x94", "0x95", "0x96", "0x97", "0x98", "0x99", "0x9A", "0x9B", "0x9C", "0x9D", "0x9E", "0x9F", "0xA0", "0xA1", "0xA2", "0xA3", "0xA4", "0xA5", "0xA6", "0xA7", "0xA8", "0xA9", "0xAA", "0xAB", "0xAC", "0xAD", "0xAE", "0xAF", "0xB0", "0xB1", "0xB2", "0xB3", "0xB4", "0xB5", "0xB6", "0xB7", "0xB8", "0xB9", "0xBA", "0xBB", "0xBC", "0xBD", "0xBE", "0xBF", "0xC0", "0xC1", "0xC2", "0xC3", "0xC4", "0xC5", "0xC6", "0xC7", "0xC8", "0xC9", "0xCA", "0xCB", "0xCC", "0xCD", "0xCE", "0xCF", "0xD0", "0xD1", "0xD2", "0xD3", "0xD4", "0xD5", "0xD6", "0xD7", "0xD8", "0xD9", "0xDA", "0xDB", "0xDC", "0xDD", "0xDE", "0xDF", "0xE0", "0xE1", "0xE2", "0xE3", "0xE4", "0xE5", "0xE6", "0xE7", "0xE8", "0xE9", "0xEA", "0xEB", "0xEC", "0xED", "0xEE", "0xEF", "0xF0", "0xF1", "0xF2", "0xF3", "0xF4", "0xF5", "0xF6", "0xF7", "0xF8", "0xF9", "0xFA", "0xFB", "0xFC", "0xFD", "0xFE", "0xFF"};
        if (bArr == null) {
            return "{NULL}";
        }
        int length = bArr.length;
        if (length == 0) {
            return "{0#}";
        }
        if (length == 1) {
            sbConv.append("{1#");
            sbConv.append(strArr[bArr[0] & 255]);
            sbConv.append("}");
            return sbConv.toString();
        }
        if (length == 2) {
            sbConv.append("{2#");
            sbConv.append(strArr[bArr[0] & 255]);
            sbConv.append(',');
            sbConv.append(strArr[bArr[1] & 255]);
            sbConv.append("}");
            return sbConv.toString();
        }
        if (length == 3) {
            sb.toString();
            sbConv.append("{3#");
            sbConv.append(strArr[bArr[0] & 255]);
            sbConv.append(',');
            sbConv.append(strArr[bArr[1] & 255]);
            sbConv.append(',');
            sbConv.append(strArr[bArr[2] & 255]);
            sbConv.append("}");
            return sbConv.toString();
        }
        sbConv.append('{');
        sbConv.append(bArr.length);
        sbConv.append('#');
        sbConv.append(strArr[bArr[0] & 255]);
        sbConv.append(',');
        sbConv.append(strArr[bArr[1] & 255]);
        sbConv.append(',');
        sbConv.append(strArr[bArr[2] & 255]);
        sbConv.append("...}");
        return sbConv.toString();
    }

    static String IntegerArray2String(int[] iArr) {
        sbConv.setLength(0);
        if (iArr == null) {
            return "{NULL}";
        }
        int length = iArr.length;
        if (length == 0) {
            return "{0#}";
        }
        if (length == 1) {
            sbConv.append("{1#");
            sbConv.append(iArr[0]);
            sbConv.append("}");
            return sbConv.toString();
        }
        if (length == 2) {
            sbConv.append("{2#");
            sbConv.append(iArr[0]);
            sbConv.append(',');
            sbConv.append(iArr[1]);
            sbConv.append("}");
            return sbConv.toString();
        }
        if (length == 3) {
            sbConv.append("{3#");
            sbConv.append(iArr[0]);
            sbConv.append(',');
            sbConv.append(iArr[1]);
            sbConv.append(',');
            sbConv.append(iArr[2]);
            sbConv.append("}");
            return sbConv.toString();
        }
        sbConv.append('{');
        sbConv.append(iArr.length);
        sbConv.append('#');
        sbConv.append(iArr[0]);
        sbConv.append(',');
        sbConv.append(iArr[1]);
        sbConv.append(',');
        sbConv.append(iArr[2]);
        sbConv.append("...}");
        return sbConv.toString();
    }

    static String toHexString(int i, int i2) {
        char[] cArr = {'0', '0', '0', '0', '0', '0', '0'};
        String hexString = Integer.toHexString(i);
        int length = i2 - hexString.length();
        if (length <= 0) {
            return hexString;
        }
        if (length == 1) {
            return '0' + hexString;
        }
        if (length <= 7) {
            return String.valueOf(cArr, 0, length) + hexString;
        }
        String str = "";
        for (int i3 = 0; i3 < length; i3++) {
            str = str + '0';
        }
        return str + hexString;
    }

    private static synchronized void logArray(int i, byte[] bArr, int i2, int i3, StackTraceElement stackTraceElement) {
    }
}
