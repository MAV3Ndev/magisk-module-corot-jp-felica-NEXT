package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
class PollingResponse extends Response {
    private static final byte CODE = 1;
    private static final int IDM_LENGTH = 8;
    private static final PollingResponse INSTANCE = new PollingResponse();
    private static final int PMM_LENGTH = 8;
    private static final byte RESPONSE_LENGTH = 20;
    private static final int SYSTEM_CODE_LENGTH = 2;
    private byte[] mIdm;
    private byte[] mPmm;
    private int mSystemCode;

    private PollingResponse() {
    }

    public static PollingResponse getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Response
    Response get(Command command, ByteBuffer byteBuffer) throws FelicaInternalException {
        if (command == null || byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        if (!byteBuffer.check(0, (byte) 20)) {
            throw new FelicaInternalException(3);
        }
        if (!byteBuffer.check(1, (byte) 1)) {
            throw new FelicaInternalException(3);
        }
        byte[] bArr = new byte[8];
        this.mIdm = bArr;
        byteBuffer.copy(2, bArr);
        byte[] bArr2 = new byte[8];
        this.mPmm = bArr2;
        byteBuffer.copy(10, bArr2);
        this.mSystemCode = (int) byteBuffer.getInBigEndian(18, 2);
        return this;
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    byte[] getPmm() {
        return this.mPmm;
    }

    public int getSystemCode() {
        return this.mSystemCode;
    }
}
