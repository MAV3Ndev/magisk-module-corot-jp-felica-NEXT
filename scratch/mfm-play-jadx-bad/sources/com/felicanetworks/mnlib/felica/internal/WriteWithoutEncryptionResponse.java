package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
class WriteWithoutEncryptionResponse extends Response {
    private static final byte CODE = 9;
    private static final int IDM_LENGTH = 8;
    private static final WriteWithoutEncryptionResponse INSTANCE = new WriteWithoutEncryptionResponse();
    private static final byte RESPONSE_LENGTH = 12;
    private byte[] mIdm;
    private int mStatusFlag1;
    private int mStatusFlag2;

    private WriteWithoutEncryptionResponse() {
    }

    static WriteWithoutEncryptionResponse getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Response
    Response get(Command command, ByteBuffer byteBuffer) throws FelicaInternalException {
        if (command == null || byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        if (!byteBuffer.check(0, (byte) 12)) {
            throw new FelicaInternalException(3);
        }
        if (!byteBuffer.check(1, (byte) 9)) {
            throw new FelicaInternalException(3);
        }
        if (!byteBuffer.check(2, ((WriteWithoutEncryptionCommand) command).getIdm())) {
            throw new FelicaInternalException(3);
        }
        byte[] bArr = new byte[8];
        this.mIdm = bArr;
        byteBuffer.copy(2, bArr);
        this.mStatusFlag1 = byteBuffer.get(10) & 255;
        this.mStatusFlag2 = byteBuffer.get(11) & 255;
        return this;
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    int getStatusFlag1() {
        return this.mStatusFlag1;
    }

    int getStatusFlag2() {
        return this.mStatusFlag2;
    }
}
