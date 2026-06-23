package com.felicanetworks.mnlib.felica.internal;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
class RequestSystemCodeResponse extends Response {
    private static final byte CODE = 13;
    private static final int IDM_LENGTH = 8;
    private static final RequestSystemCodeResponse INSTANCE = new RequestSystemCodeResponse();
    private static final byte MINIMUM_RESPONSE_LENGTH = 13;
    private static final String TAG = "NFC";
    private byte[] mIdm;
    private int[] mSystemCodeList;

    private RequestSystemCodeResponse() {
    }

    static RequestSystemCodeResponse getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Response
    Response get(Command command, ByteBuffer byteBuffer) throws FelicaInternalException {
        if (command == null || byteBuffer == null) {
            Log.w(TAG, "get  : command or byteBuffer is null");
            throw new FelicaInternalException(0);
        }
        try {
            if ((byteBuffer.get(0) & 255) < 13) {
                throw new FelicaInternalException(3);
            }
            if (!byteBuffer.check(1, (byte) 13)) {
                Log.w(TAG, "get  : response code is invalid");
                throw new FelicaInternalException(3);
            }
            if (!byteBuffer.check(2, ((RequestSystemCodeCommand) command).getIdm())) {
                Log.w(TAG, "get  : command idm is invalid");
                throw new FelicaInternalException(3);
            }
            byte[] bArr = new byte[8];
            this.mIdm = bArr;
            byteBuffer.copy(2, bArr);
            int i = byteBuffer.get(10) & 255;
            if (i < 1 || 16 < i) {
                throw new FelicaInternalException(3);
            }
            if (!byteBuffer.check(0, (byte) (((i - 1) * 2) + 13))) {
                throw new FelicaInternalException(3);
            }
            this.mSystemCodeList = DataUtil.createSystemCode(byteBuffer);
            return this;
        } catch (FelicaInternalException e) {
            Log.w(TAG, "get  : catch and throw FelicaInternalException");
            throw e;
        }
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    int[] getSystemCodeList() {
        return this.mSystemCodeList;
    }
}
