package com.felicanetworks.mfc;

import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class MfcUtil {
    MfcUtil() {
    }

    public static void checkMfcResult(FelicaResultInfo felicaResultInfo) throws Exception {
        LogMgr.log(7, "%s", "000");
        if (felicaResultInfo == null) {
            LogMgr.log(2, "%s %s", "704", "Result is null!");
            throw new Exception("Illegal Response");
        }
        LogMgr.log(7, "%s %d", "002", Integer.valueOf(felicaResultInfo.getExceptionType()));
        int exceptionType = felicaResultInfo.getExceptionType();
        if (exceptionType == 0) {
            LogMgr.log(7, "%s", "999");
            return;
        }
        if (exceptionType == 1) {
            LogMgr.log(2, "%s %s", "701", "FelicaException");
            LogMgr.log(2, "id:%d type:%d appId:%d flg1:%d flg2:%d ", Integer.valueOf(felicaResultInfo.getId()), Integer.valueOf(felicaResultInfo.getType()), felicaResultInfo.getOtherAppInfo(), Integer.valueOf(felicaResultInfo.getStatusFlag1()), Integer.valueOf(felicaResultInfo.getStatusFlag2()));
            throw new FelicaException(felicaResultInfo.getId(), felicaResultInfo.getType(), felicaResultInfo.getOtherAppInfo(), felicaResultInfo.getStatusFlag1(), felicaResultInfo.getStatusFlag2(), felicaResultInfo.getMessage());
        }
        if (exceptionType == 32) {
            LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", felicaResultInfo.getMessage());
            throw new IllegalArgumentException(felicaResultInfo.getMessage());
        }
        if (exceptionType == 34) {
            LogMgr.log(2, "%s %s %s", "704", "NumberFormatException", felicaResultInfo.getMessage());
            throw new NumberFormatException();
        }
        LogMgr.log(2, "%s %s", "703", "Illegal ExceptionType");
        throw new Exception("Unknown error.");
    }
}
