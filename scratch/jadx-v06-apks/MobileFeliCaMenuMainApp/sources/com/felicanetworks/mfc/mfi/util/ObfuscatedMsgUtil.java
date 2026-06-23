package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class ObfuscatedMsgUtil {
    private static final String TAG_EXCEPTION = "ex=";
    private static final String TAG_ID = "id=";
    private static final String TAG_SW = "sw=";
    private static final String TAG_TYPE = "type=";

    public static String felicaExExecutionPoint(FelicaException felicaException) {
        return executionPoint(2) + ":" + TAG_ID + felicaException.getID() + ":" + TAG_TYPE + felicaException.getType();
    }

    public static String exExecutionPoint(Throwable th) {
        return executionPoint(2) + ":" + TAG_EXCEPTION + th.getClass().getSimpleName();
    }

    public static String swExecutionPoint(String str) {
        return executionPoint(2) + ":" + TAG_SW + str;
    }

    public static String omapiExecutionPoint(String str, String str2) {
        return str + ":" + str2 + ":" + executionPoint(2);
    }

    public static String executionPoint() {
        return executionPoint(2);
    }

    private static String executionPoint(int i) {
        String fileName = "";
        int lineNumber = 0;
        try {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace != null && stackTrace.length > i) {
                fileName = stackTrace[i].getFileName();
                int iLastIndexOf = fileName.lastIndexOf(".");
                if (iLastIndexOf != -1) {
                    fileName = fileName.substring(0, iLastIndexOf);
                }
                lineNumber = stackTrace[i].getLineNumber();
            }
        } catch (Exception e) {
            LogMgr.log(1, "%s %s:%s", "800", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        return Integer.toHexString(fileName.hashCode()) + ":" + lineNumber;
    }
}
