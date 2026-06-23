package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class ObfuscatedMsgUtil {
    private static final String TAG_EXCEPTION = "ex=";
    private static final String TAG_ID = "id=";
    private static final String TAG_SW = "sw=";
    private static final String TAG_TYPE = "type=";

    public static String felicaExExecutionPoint(FelicaException fe) {
        return executionPoint(2) + ":id=" + fe.getID() + ":type=" + fe.getType();
    }

    public static String exExecutionPoint(Throwable ex) {
        return executionPoint(2) + ":ex=" + ex.getClass().getSimpleName();
    }

    public static String swExecutionPoint(String sw) {
        return executionPoint(2) + ":sw=" + sw;
    }

    public static String omapiExecutionPoint(String omapiName, String errorMessage) {
        return omapiName + ":" + errorMessage + ":" + executionPoint(2);
    }

    public static String executionPoint() {
        return executionPoint(2);
    }

    private static String executionPoint(int point) {
        String fileName = "";
        int lineNumber = 0;
        try {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            if (stackTrace != null && stackTrace.length > point) {
                fileName = stackTrace[point].getFileName();
                int iLastIndexOf = fileName.lastIndexOf(".");
                if (iLastIndexOf != -1) {
                    fileName = fileName.substring(0, iLastIndexOf);
                }
                lineNumber = stackTrace[point].getLineNumber();
            }
        } catch (Exception e) {
            LogMgr.log(1, "%s %s:%s", "800", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        return Integer.toHexString(fileName.hashCode()) + ":" + lineNumber;
    }
}
