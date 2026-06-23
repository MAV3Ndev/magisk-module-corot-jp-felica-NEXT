package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
class MfiUtil {
    MfiUtil() {
    }

    public static void checkMfcResult(FelicaResultInfo result) throws Exception {
        LogMgr.log(7, "%s", "000");
        if (result == null) {
            LogMgr.log(2, "%s %s", "704", "Result is null!");
            throw new Exception("Illegal Response");
        }
        LogMgr.log(7, "%s %d", "002", Integer.valueOf(result.getExceptionType()));
        int exceptionType = result.getExceptionType();
        if (exceptionType == 0) {
            LogMgr.log(7, "%s", "999");
            return;
        }
        if (exceptionType == 1) {
            LogMgr.log(2, "%s %s", "701", "FelicaException");
            LogMgr.log(2, "id:%d type:%d appId:%d flg1:%d flg2:%d ", Integer.valueOf(result.getId()), Integer.valueOf(result.getType()), result.getOtherAppInfo(), Integer.valueOf(result.getStatusFlag1()), Integer.valueOf(result.getStatusFlag2()));
            throw new FelicaException(result.getId(), result.getType(), result.getOtherAppInfo(), result.getStatusFlag1(), result.getStatusFlag2(), result.getMessage());
        }
        if (exceptionType == 32) {
            LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", result.getMessage());
            throw new IllegalArgumentException(result.getMessage());
        }
        if (exceptionType == 34) {
            LogMgr.log(2, "%s %s %s", "704", "NumberFormatException", result.getMessage());
            throw new NumberFormatException();
        }
        LogMgr.log(2, "%s %s", "703", "Illegal ExceptionType");
        throw new Exception("Unknown error.");
    }
}
