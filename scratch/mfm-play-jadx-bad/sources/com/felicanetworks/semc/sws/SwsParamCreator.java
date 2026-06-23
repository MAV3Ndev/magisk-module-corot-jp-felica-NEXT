package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.StringUtil;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class SwsParamCreator {
    private static final String ID_TAG_CLIENT = "CL";
    private static final String ID_TAG_REQUEST = "RQ";
    private static final int RANDOM_BYTE_LEN_1 = 4;
    private static final int RANDOM_BYTE_LEN_2 = 5;
    private static final int TIMESTAMP_BYTE_LEN = 6;

    static String createRequestId() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return ID_TAG_REQUEST + createRandomNumber(4) + createTimeStamp() + createRandomNumber(5);
    }

    public static String createClientId() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
        return ID_TAG_CLIENT + createRandomNumber(4) + createTimeStamp() + createRandomNumber(5);
    }

    private static String createTimeStamp() {
        LogMgr.log(8, "000");
        long jCurrentTimeMillis = System.currentTimeMillis();
        LogMgr.log(8, "001 time=" + jCurrentTimeMillis);
        byte[] bArrCopyOfRange = Arrays.copyOfRange(ByteBuffer.allocate(8).putLong(jCurrentTimeMillis).array(), 2, 8);
        LogMgr.log(8, "002 bytes.length=" + bArrCopyOfRange.length);
        LogMgr.log(8, "999");
        return StringUtil.bytesToHexString(bArrCopyOfRange);
    }

    static String createRandomNumber(int i) {
        LogMgr.log(8, "000");
        byte[] bArr = new byte[i];
        new SecureRandom().nextBytes(bArr);
        LogMgr.log(8, "001 bytes.length=" + i);
        LogMgr.log(8, "999");
        return StringUtil.bytesToHexString(bArr);
    }
}
