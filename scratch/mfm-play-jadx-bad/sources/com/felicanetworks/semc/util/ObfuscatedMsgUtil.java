package com.felicanetworks.semc.util;

/* JADX INFO: loaded from: classes3.dex */
public class ObfuscatedMsgUtil {
    private static final int LINE_DELIMITER_IS_BRACKETS = 0;
    private static final int LINE_DELIMITER_IS_COLON = 1;
    private static final String TAG_EXCEPTION = "ex=";
    public static final String TAG_OBFUSCATED_SYMBOL = "+/";
    private static final String TAG_SW = "sw=";

    public static String swExecutionPoint(String str) {
        LogMgrUtil.log(7, "000");
        String strExecutionPoint = executionPoint(2, true, 0);
        LogMgrUtil.log(7, "999");
        return strExecutionPoint + ":sw=" + str;
    }

    public static String swExecutionPoint(String str, String str2) {
        LogMgrUtil.log(7, "000");
        String strExecutionPoint = executionPoint(2, false, 0);
        LogMgrUtil.log(7, "999");
        return str + ":sw=" + str2 + ":" + strExecutionPoint;
    }

    public static String omapiExecutionPoint(String str, String str2) {
        LogMgrUtil.log(7, "000");
        String strExecutionPoint = executionPoint(2, false, 0);
        LogMgrUtil.log(7, "999");
        return TAG_OBFUSCATED_SYMBOL + str + ":" + str2 + ":" + strExecutionPoint;
    }

    public static String backgroundExecutionPoint() {
        LogMgrUtil.log(7, "000");
        String strExecutionPoint = executionPoint(2, false, 1);
        LogMgrUtil.log(7, "999");
        return strExecutionPoint;
    }

    public static String executionPoint() {
        LogMgrUtil.log(7, "000");
        String strExecutionPoint = executionPoint(2, true, 0);
        LogMgrUtil.log(7, "999");
        return strExecutionPoint;
    }

    public static String executionPoint(String str) {
        LogMgrUtil.log(7, "000");
        String str2 = executionPoint(2, true, 0) + " [" + str.hashCode() + "]";
        LogMgrUtil.log(7, "999");
        return str2;
    }

    public static String executionPoint(Throwable th) {
        String strExecutionPoint;
        LogMgrUtil.log(7, "000");
        if (th == null) {
            return executionPoint(2, true, 0);
        }
        if (isObfuscatedStr(th.getMessage())) {
            strExecutionPoint = th.getMessage();
        } else {
            strExecutionPoint = executionPoint(2, true, 0);
        }
        LogMgrUtil.log(7, "999");
        return strExecutionPoint;
    }

    public static boolean isObfuscatedStr(String str) {
        LogMgrUtil.log(7, "000");
        boolean z = str != null && str.startsWith(TAG_OBFUSCATED_SYMBOL);
        LogMgrUtil.log(7, "999");
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String executionPoint(int i, boolean z, int i2) {
        String fileName;
        String methodName;
        String str;
        StackTraceElement[] stackTrace;
        LogMgrUtil.log(7, "000");
        String str2 = "";
        int lineNumber = 0;
        try {
            stackTrace = new Throwable().getStackTrace();
        } catch (Exception e) {
            e = e;
            fileName = "";
        }
        if (stackTrace.length <= i) {
            methodName = "";
            LogMgrUtil.log(7, "999");
            if (z) {
            }
            if (i2 == 0) {
            }
        } else {
            fileName = stackTrace[i].getFileName();
            if (fileName == null) {
                fileName = "";
            } else {
                try {
                    int iLastIndexOf = fileName.lastIndexOf(".");
                    if (iLastIndexOf != -1) {
                        fileName = fileName.substring(0, iLastIndexOf);
                    }
                } catch (Exception e2) {
                    e = e2;
                    LogMgrUtil.log(1, "800" + e.getClass().getSimpleName() + e.getMessage());
                    LogMgrUtil.printStackTrace(9, e);
                    methodName = "";
                }
            }
            lineNumber = stackTrace[i].getLineNumber();
            methodName = stackTrace[i].getMethodName();
            str2 = fileName;
            LogMgrUtil.log(7, "999");
            if (z) {
                str = TAG_OBFUSCATED_SYMBOL + Integer.toHexString(str2.hashCode()) + ":" + Integer.toHexString(methodName.hashCode());
            } else {
                str = TAG_OBFUSCATED_SYMBOL + Integer.toHexString(str2.hashCode());
            }
            if (i2 == 0) {
                return str + "(" + lineNumber + ")";
            }
            return str + ":" + lineNumber;
        }
        LogMgrUtil.log(1, "800" + e.getClass().getSimpleName() + e.getMessage());
        LogMgrUtil.printStackTrace(9, e);
        methodName = "";
        str2 = fileName;
        LogMgrUtil.log(7, "999");
        if (z) {
        }
        if (i2 == 0) {
        }
    }
}
