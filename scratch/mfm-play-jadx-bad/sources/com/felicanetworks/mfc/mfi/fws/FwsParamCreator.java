package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.util.Base64Util;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
class FwsParamCreator {
    private static final String ID_ISSUER_CLIENT = "CL";
    private static final String ID_TAG_OPERATION = "OP";
    private static final String ID_TAG_REQUEST = "RQ";
    private static final int RANDOM_BYTE_LEN = 16;
    private static final int TIMESTAMP_BYTE_LEN = 8;

    FwsParamCreator() {
    }

    static String createRequestId() {
        return ID_TAG_REQUEST + createTimeStamp() + ID_ISSUER_CLIENT + createRandomNumber();
    }

    static String createOperationId() {
        return ID_TAG_OPERATION + createTimeStamp() + ID_ISSUER_CLIENT + createRandomNumber();
    }

    private static String createTimeStamp() {
        return Base64Util.encode(ByteBuffer.allocate(8).putLong(System.currentTimeMillis()).array());
    }

    static String createRandomNumber() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return Base64Util.encode(bArr);
    }

    static String getLanguageCode() {
        return Locale.getDefault().getISO3Language();
    }

    static String createClientId() {
        return createTimeStamp() + createRandomNumber();
    }
}
