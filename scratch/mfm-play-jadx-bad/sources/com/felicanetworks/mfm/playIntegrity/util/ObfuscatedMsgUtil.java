package com.felicanetworks.mfm.playIntegrity.util;

/* JADX INFO: loaded from: classes3.dex */
public class ObfuscatedMsgUtil {
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
            LogMgr.log(1, "800 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        try {
            return Integer.toHexString(fileName.hashCode()) + ":" + lineNumber;
        } catch (Exception unused) {
            return ":0";
        }
    }
}
