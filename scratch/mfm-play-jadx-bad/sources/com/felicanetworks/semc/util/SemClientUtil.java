package com.felicanetworks.semc.util;

import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.SemClientResultInfo;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientUtil {
    public static void checkSemClientResult(SemClientResultInfo semClientResultInfo) throws Exception {
        LogMgrUtil.log(9, "000");
        if (semClientResultInfo == null) {
            LogMgrUtil.log(2, "700 Result is null");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgrUtil.log(9, "001 " + semClientResultInfo.getExceptionType());
        int exceptionType = semClientResultInfo.getExceptionType();
        if (exceptionType == 0) {
            LogMgrUtil.log(9, "999");
            return;
        }
        if (exceptionType == 1) {
            LogMgrUtil.log(2, "701 SemClientException id:" + semClientResultInfo.getErrorCode());
            throw new SemClientException(semClientResultInfo.getErrorCode(), semClientResultInfo.getMessage());
        }
        if (exceptionType == 32) {
            LogMgrUtil.log(2, "702 IllegalArgumentException " + semClientResultInfo.getMessage());
            throw new IllegalArgumentException(semClientResultInfo.getMessage());
        }
        if (exceptionType == 33) {
            LogMgrUtil.log(2, "703 SemClientException " + semClientResultInfo.getMessage());
            throw new IllegalStateException(semClientResultInfo.getMessage());
        }
        LogMgrUtil.log(2, "704 Illegal ExceptionType");
        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
    }
}
