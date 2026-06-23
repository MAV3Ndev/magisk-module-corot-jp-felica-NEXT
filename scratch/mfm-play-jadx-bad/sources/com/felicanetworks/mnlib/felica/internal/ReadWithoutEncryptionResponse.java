package com.felicanetworks.mnlib.felica.internal;

import com.felicanetworks.mnlib.felica.Data;

/* JADX INFO: loaded from: classes3.dex */
class ReadWithoutEncryptionResponse extends Response {
    private static final byte CODE = 7;
    private static final int IDM_LENGTH = 8;
    private static final ReadWithoutEncryptionResponse INSTANCE = new ReadWithoutEncryptionResponse();
    private static final byte MINIMUM_RESPONSE_LENGTH = 12;
    private static final int STATUS_FLAG1_OK = 0;
    private Data[] mDataList;
    private byte[] mIdm;
    private int mStatusFlag1;
    private int mStatusFlag2;

    private ReadWithoutEncryptionResponse() {
    }

    static ReadWithoutEncryptionResponse getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Response
    Response get(Command command, ByteBuffer byteBuffer) throws FelicaInternalException {
        if (command == null || byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        if ((byteBuffer.get(0) & 255) < 12) {
            throw new FelicaInternalException(3);
        }
        if (!byteBuffer.check(1, (byte) 7)) {
            throw new FelicaInternalException(3);
        }
        ReadWithoutEncryptionCommand readWithoutEncryptionCommand = (ReadWithoutEncryptionCommand) command;
        if (!byteBuffer.check(2, readWithoutEncryptionCommand.getIdm())) {
            throw new FelicaInternalException(3);
        }
        byte[] bArr = new byte[8];
        this.mIdm = bArr;
        byteBuffer.copy(2, bArr);
        this.mStatusFlag1 = byteBuffer.get(10) & 255;
        this.mStatusFlag2 = byteBuffer.get(11) & 255;
        if (this.mStatusFlag1 == 0) {
            if ((byteBuffer.get(0) & 255) < 13) {
                throw new FelicaInternalException(3);
            }
            int i = byteBuffer.get(12) & 255;
            if (i < 1 || 15 < i) {
                throw new FelicaInternalException(3);
            }
            if (!byteBuffer.check(0, (byte) ((i * 16) + 13))) {
                throw new FelicaInternalException(3);
            }
            this.mDataList = DataUtil.createDataList(byteBuffer, readWithoutEncryptionCommand.getBlockList());
            return this;
        }
        if (!byteBuffer.check(0, (byte) 12)) {
            throw new FelicaInternalException(3);
        }
        this.mDataList = new Data[0];
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

    Data[] getDataList() {
        return this.mDataList;
    }
}
