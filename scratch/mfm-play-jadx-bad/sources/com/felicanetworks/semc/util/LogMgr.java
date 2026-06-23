package com.felicanetworks.semc.util;

import android.util.Log;
import androidx.credentials.provider.CredentialEntry;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.SemClientExternalLogConst;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class LogMgr {
    public static final int API_OMAPI = 3;
    public static final int API_OTHER = 5;
    public static final int API_SERVER = 4;
    private static final String APP_LOG_TAG = "SemClient";
    private static final int ARGS_LENGTH_MAX = 8;
    public static final int CLS = 7;
    public static final int CMD = -1;
    public static final int DBG = 8;
    public static final int ERR = 1;
    public static final String PERFORMANCE_API = "API";
    private static final String PERFORMANCE_MSG_IN = "[in ]";
    private static final String PERFORMANCE_MSG_OUT = "[out]";
    public static final String PERFORMANCE_SERVER = "SERVER";
    private static final String PERFORMANCE_TAG = "[PERFORMANCE]";
    public static final int PKG = 6;
    private static final String S_API_OMAPI = "[API_OMAPI]";
    private static final String S_API_OTHER = "[API_OTHER]";
    private static final String S_API_SERVER = "[API_SERVER]";
    private static final String S_CLS = "[CLS]";
    private static final String S_CMD = "[APDU]";
    private static final String S_DBG = "[DBG]";
    private static final String S_ERR = "[ERR]";
    private static final String S_PKG = "[PKG]";
    private static final String S_TRC = "[TRC]";
    private static final String S_UNK = "[UNK]";
    private static final String S_WAR = "[WAR]";
    public static final int TRC = 9;
    public static final int WAR = 2;
    private static final boolean enableClass = false;
    private static final boolean isNeedObfuscation = true;
    private static boolean sEnable = false;
    private static int sLevel = 1;
    private static boolean sPerformanceEnable = false;
    protected static boolean sUseHomeBrewingFormat = true;
    static Map<String, Integer> sEnableClassName = new HashMap();
    private static final Object[] ARG_DUMMY = new Object[0];
    private static Object[] sNewArgs = new Object[8];
    private static StringBuffer sb = new StringBuffer(256);
    private static StringBuffer sbTag = new StringBuffer(128);
    private static StringBuffer sbConv = new StringBuffer(64);
    private static StringBuilder sBuilder = new StringBuilder(128);

    public static void printStackTrace(int i, Throwable th) {
    }

    protected static String getLevel(int i) {
        HashMap map = new HashMap();
        map.put(-1, S_CMD);
        map.put(1, S_ERR);
        map.put(2, S_WAR);
        map.put(3, S_API_OMAPI);
        map.put(4, S_API_SERVER);
        map.put(5, S_API_OTHER);
        map.put(6, S_PKG);
        map.put(7, S_CLS);
        map.put(8, S_DBG);
        map.put(9, S_TRC);
        String str = (String) map.get(Integer.valueOf(i));
        return str == null ? S_UNK : str;
    }

    public static boolean checkEnableClass(StackTraceElement stackTraceElement, int i) {
        Integer num;
        String className = stackTraceElement.getClassName();
        return (className == null || (num = sEnableClassName.get(className)) == null || i > num.intValue()) ? false : true;
    }

    public static void log(int i, String str) {
        log(i, str, ARG_DUMMY, 0L);
    }

    public static void setup(int i, boolean z, boolean z2) {
        sLevel = i;
        sEnable = z;
        sPerformanceEnable = z2;
    }

    private static void log(int i, String str, Object[] objArr, long j) {
        if (!sEnable || j < 0 || j > 8 || sLevel < i) {
            return;
        }
        try {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace != null && stackTrace.length > 2) {
                output(i, stackTrace[2], str, objArr, j);
            }
        } catch (Exception e) {
            Log.e("LogMgr", Log.getStackTraceString(e));
        }
    }

    protected static void output(int i, StackTraceElement stackTraceElement, String str, Object[] objArr, long j) {
        Object obj;
        int iLastIndexOf;
        StringBuilder sb2 = new StringBuilder();
        sbTag.setLength(0);
        sbTag.append(APP_LOG_TAG);
        String string = sbTag.toString();
        sb2.append(getLevel(i));
        String fileName = stackTraceElement.getFileName();
        String strSubstring = "";
        if (fileName != null && (iLastIndexOf = fileName.lastIndexOf(".")) != -1) {
            strSubstring = fileName.substring(0, iLastIndexOf);
        }
        String hexString = Integer.toHexString(strSubstring.hashCode());
        String hexString2 = Integer.toHexString(stackTraceElement.getMethodName().hashCode());
        sb2.append('[');
        sb2.append(hexString);
        sb2.append("][");
        sb2.append(hexString2);
        sb2.append("(");
        sb2.append(stackTraceElement.getLineNumber());
        sb2.append(")]");
        String string2 = sb2.toString();
        for (int i2 = 0; i2 < j; i2++) {
            Object obj2 = objArr[i2];
            if ((obj2 instanceof byte[]) || (obj2 instanceof int[])) {
                for (int i3 = 0; i3 < j; i3++) {
                    Object obj3 = objArr[i3];
                    if (obj3 instanceof byte[]) {
                        sNewArgs[i3] = ByteArray2String((byte[]) obj3);
                    } else if (obj3 instanceof int[]) {
                        sNewArgs[i3] = IntegerArray2String((int[]) obj3);
                    } else {
                        sNewArgs[i3] = obj3;
                    }
                }
                String hexString3 = Integer.toHexString(format(str, sNewArgs, j).hashCode());
                if (i == 1) {
                    Log.e(string, string2 + hexString3);
                    return;
                }
                if (i == 2) {
                    Log.w(string, string2 + hexString3);
                    return;
                }
                Log.i(string, string2 + hexString3);
                return;
            }
        }
        if (str.equals("%s") && j == 1 && (obj = objArr[0]) != null && (obj instanceof String)) {
            String hexString4 = Integer.toHexString(((String) obj).hashCode());
            if (i == 1) {
                Log.e(string, string2 + hexString4);
                return;
            }
            if (i == 2) {
                Log.w(string, string2 + hexString4);
                return;
            }
            Log.i(string, string2 + hexString4);
            return;
        }
        String hexString5 = Integer.toHexString(format(str, objArr, j).hashCode());
        if (i == 1) {
            Log.e(string, string2 + hexString5);
            return;
        }
        if (i == 2) {
            Log.w(string, string2 + hexString5);
            return;
        }
        Log.i(string, string2 + hexString5);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static String format(String str, Object[] objArr, long j) {
        if (!sUseHomeBrewingFormat) {
            return String.format(str, objArr);
        }
        sb.setLength(0);
        int length = str.length();
        int i = (int) j;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '%' && i2 <= length - 1) {
                int i4 = i2 + 1;
                char cCharAt2 = str.charAt(i4);
                if (cCharAt2 != 'f' && cCharAt2 != 's' && cCharAt2 != 'x') {
                    switch (cCharAt2) {
                        case 'b':
                        case 'c':
                        case 'd':
                            break;
                        default:
                            sb.append(cCharAt2);
                            i2 = i4;
                            break;
                    }
                } else {
                    Object obj = i3 < i ? objArr[i3] : null;
                    int i5 = i3 + 1;
                    if (cCharAt2 != 'f') {
                        if (cCharAt2 != 's') {
                            if (cCharAt2 != 'x') {
                                switch (cCharAt2) {
                                    case 'b':
                                        if (obj == null) {
                                            sb.append("null");
                                        } else {
                                            boolean z = obj instanceof Boolean;
                                            String str2 = CredentialEntry.TRUE_STRING;
                                            if (z) {
                                                StringBuffer stringBuffer = sb;
                                                if (!((Boolean) obj).booleanValue()) {
                                                    str2 = CredentialEntry.FALSE_STRING;
                                                }
                                                stringBuffer.append(str2);
                                            } else {
                                                StringBuffer stringBuffer2 = sb;
                                                if (!Boolean.valueOf(obj.toString()).booleanValue()) {
                                                    str2 = CredentialEntry.FALSE_STRING;
                                                }
                                                stringBuffer2.append(str2);
                                            }
                                        }
                                        break;
                                    case 'c':
                                        if (obj == null) {
                                            sb.append("null");
                                        } else if (obj instanceof Character) {
                                            sb.append((Character) obj);
                                        } else {
                                            sb.append(obj.toString().charAt(0));
                                        }
                                        break;
                                    case 'd':
                                        if (obj == null) {
                                            sb.append("null");
                                        } else if (obj instanceof Integer) {
                                            sb.append((Integer) obj);
                                        } else if (obj instanceof Long) {
                                            sb.append((Long) obj);
                                        } else if (obj instanceof Byte) {
                                            sb.append((Byte) obj);
                                        } else {
                                            try {
                                                sb.append(Integer.valueOf(obj.toString()));
                                            } catch (Exception e) {
                                                Log.w("LogMgr", "args[" + i3 + "]: fmt = \"" + str + "\", obj = " + obj + ", msg = " + e.getMessage());
                                                sb.append(obj.toString());
                                            }
                                        }
                                        break;
                                }
                            } else if (obj == null) {
                                sb.append("null");
                            } else if (obj instanceof Integer) {
                                sb.append(Integer.toHexString(((Integer) obj).intValue()));
                            } else if (obj instanceof Long) {
                                sb.append(Long.toHexString(((Long) obj).longValue()));
                            } else if (obj instanceof Byte) {
                                sb.append(Integer.toHexString(((Byte) obj).byteValue() & 255));
                            } else {
                                try {
                                    sb.append(Integer.toHexString(Integer.valueOf(obj.toString()).intValue()));
                                } catch (Exception e2) {
                                    Log.w("LogMgr", "args[" + i3 + "]: fmt = \"" + str + "\", obj = " + obj + ", msg = " + e2.getMessage());
                                    sb.append(obj.toString());
                                }
                            }
                        } else if (obj == null) {
                            sb.append("null");
                        } else if (obj instanceof String) {
                            sb.append((String) obj);
                        } else {
                            sb.append(obj.toString());
                        }
                    } else if (obj == null) {
                        sb.append("null");
                    } else if (obj instanceof Double) {
                        sb.append((Double) obj);
                    } else if (obj instanceof Float) {
                        sb.append((Float) obj);
                    } else {
                        sb.append(Double.parseDouble(obj.toString()));
                    }
                    i2 = i4;
                    i3 = i5;
                }
            } else {
                sb.append(cCharAt);
            }
            i2++;
        }
        return sb.toString();
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
            return "0" + hexString;
        }
        if (length <= 7) {
            return String.valueOf(cArr, 0, length) + hexString;
        }
        StringBuilder sb2 = new StringBuilder("");
        for (int i3 = 0; i3 < length; i3++) {
            sb2.append('0');
        }
        return sb2.toString() + hexString;
    }

    public static void logArray(int i, byte[] bArr) {
        StackTraceElement[] stackTrace;
        if (!sEnable || sLevel < i || (stackTrace = new Throwable().getStackTrace()) == null || stackTrace.length <= 1) {
            return;
        }
        logArray(i, bArr, 0, -1, stackTrace[1]);
    }

    private static synchronized void logArray(int i, byte[] bArr, int i2, int i3, StackTraceElement stackTraceElement) {
        if (sEnable && sLevel >= i) {
            try {
                sBuilder.setLength(0);
                if (bArr == null) {
                    output(i, stackTraceElement, "ary is null", ARG_DUMMY, 0L);
                } else if (i3 == 0 || bArr.length == 0) {
                    output(i, stackTraceElement, "length is " + i3 + ". The length of ary is " + bArr.length, ARG_DUMMY, 0L);
                } else {
                    if (i2 < 0) {
                        i2 = 0;
                    }
                    if (i3 < 0) {
                        i3 = bArr.length;
                    } else if (i2 + i3 > bArr.length) {
                        i3 = bArr.length - i2;
                    }
                    boolean z = true;
                    int i4 = 0;
                    while (i4 < i3) {
                        int i5 = i2 + i4;
                        if (z) {
                            sBuilder.append("[" + toHexString(i4, 3) + "] ");
                            z = false;
                        }
                        int i6 = i4 + 4;
                        if (i6 <= i3) {
                            sBuilder.append(toHexString(bArr[i5] & 255, 2) + ' ' + toHexString(bArr[i5 + 1] & 255, 2) + ' ' + toHexString(bArr[i5 + 2] & 255, 2) + ' ' + toHexString(bArr[i5 + 3] & 255, 2) + ' ');
                            i4 = i6;
                        } else {
                            sBuilder.append(toHexString(bArr[i5] & 255, 2) + ' ');
                            i4++;
                        }
                        if (i4 % 16 == 0) {
                            sBuilder.append('\n');
                            z = true;
                        } else if (i4 % 8 == 0) {
                            sBuilder.append("- ");
                        }
                        if (i4 % 1024 == 0) {
                            output(i, stackTraceElement, sBuilder.toString(), ARG_DUMMY, 0L);
                            sBuilder.setLength(0);
                        }
                    }
                    output(i, stackTraceElement, sBuilder.toString(), ARG_DUMMY, 0L);
                }
            } catch (RuntimeException | Exception unused) {
            }
        }
    }

    public static void performanceIn(String str, String str2, String str3) {
        performanceIn(str, str2, str3, null);
    }

    public static void performanceIn(String str, String str2, String str3, String str4) {
        if (sPerformanceEnable) {
            if (str != null) {
                str = Integer.toHexString(str.hashCode());
            }
            if (str2 != null) {
                str2 = Integer.toHexString(str2.hashCode());
            }
            if (str3 != null) {
                str3 = Integer.toHexString(str3.hashCode());
            }
            if (str4 != null) {
                str4 = Integer.toHexString(str4.hashCode());
            }
            sbTag.setLength(0);
            sbTag.append(APP_LOG_TAG);
            sb.setLength(0);
            sb.append(PERFORMANCE_TAG);
            sb.append("[");
            sb.append(str);
            sb.append("]");
            sb.append(PERFORMANCE_MSG_IN);
            sb.append(str2);
            sb.append("#");
            sb.append(str3);
            sb.append("()");
            if (str4 != null) {
                sb.append(" ");
                sb.append(str4);
            }
            Log.d(sbTag.toString(), sb.toString());
        }
    }

    public static void performanceOut(String str, String str2, String str3) {
        performanceOut(str, str2, str3, null);
    }

    public static void performanceOut(String str, String str2, String str3, String str4) {
        if (sPerformanceEnable) {
            if (str != null) {
                str = Integer.toHexString(str.hashCode());
            }
            if (str2 != null) {
                str2 = Integer.toHexString(str2.hashCode());
            }
            if (str3 != null) {
                str3 = Integer.toHexString(str3.hashCode());
            }
            if (str4 != null) {
                str4 = Integer.toHexString(str4.hashCode());
            }
            sbTag.setLength(0);
            sbTag.append(APP_LOG_TAG);
            sb.setLength(0);
            sb.append(PERFORMANCE_TAG);
            sb.append("[");
            sb.append(str);
            sb.append("]");
            sb.append(PERFORMANCE_MSG_OUT);
            sb.append(str2);
            sb.append("#");
            sb.append(str3);
            sb.append("()");
            if (str4 != null) {
                sb.append(" ");
                sb.append(str4);
            }
            Log.d(sbTag.toString(), sb.toString());
        }
    }

    public static void exLogSemClientException(SemClientExternalLogConst.SemcApi semcApi, SemClientException semClientException) {
        exLog(String.format(Locale.US, SemClientExternalLogConst.MSG_FORMAT_SEM_CLIENT_EXCEPTION, semcApi.msg, "SemClientException", Integer.valueOf(semClientException.getErrorCode())));
    }

    public static void exLogSemClientException(SemClientExternalLogConst.SemcApi semcApi, int i) {
        exLog(String.format(Locale.US, SemClientExternalLogConst.MSG_FORMAT_SEM_CLIENT_EXCEPTION, semcApi.msg, "SemClientException", Integer.valueOf(i)));
    }

    public static void exLogException(SemClientExternalLogConst.SemcApi semcApi, Exception exc) {
        exLog(String.format(Locale.US, "%s: %s", semcApi.msg, exc.getClass().getSimpleName()));
    }

    public static void exLogException(SemClientExternalLogConst.SemcApi semcApi, String str) {
        exLog(String.format(Locale.US, "%s: %s", semcApi.msg, str));
    }

    public static void exLogOnError(SemClientExternalLogConst.SemcApi semcApi, int i) {
        exLog(String.format(Locale.US, SemClientExternalLogConst.MSG_FORMAT_ON_ERROR, semcApi.msg, Integer.valueOf(i)));
    }

    private static void exLog(String str) {
        Log.println(5, SemClientExternalLogConst.TAG_WARN, str);
    }
}
