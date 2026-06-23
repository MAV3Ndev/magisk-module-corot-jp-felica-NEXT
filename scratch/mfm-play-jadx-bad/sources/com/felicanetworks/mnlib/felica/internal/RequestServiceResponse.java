package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
class RequestServiceResponse extends Response {
    private static final byte CODE = 3;
    private static final int IDM_LENGTH = 8;
    private static final RequestServiceResponse INSTANCE = new RequestServiceResponse();
    private static final byte MINIMUM_RESPONSE_LENGTH = 13;
    private byte[] mIdm;
    private int[] mServiceKeyVersionList;

    private RequestServiceResponse() {
    }

    static RequestServiceResponse getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Response
    Response get(Command command, ByteBuffer byteBuffer) throws FelicaInternalException {
        if (command == null || byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        if ((byteBuffer.get(0) & 255) < 13) {
            throw new FelicaInternalException(3);
        }
        if (!byteBuffer.check(1, (byte) 3)) {
            throw new FelicaInternalException(3);
        }
        RequestServiceCommand requestServiceCommand = (RequestServiceCommand) command;
        if (!byteBuffer.check(2, requestServiceCommand.getIdm())) {
            throw new FelicaInternalException(3);
        }
        byte[] bArr = new byte[8];
        this.mIdm = bArr;
        byteBuffer.copy(2, bArr);
        int i = byteBuffer.get(10) & 255;
        if (i < 1 || 32 < i) {
            throw new FelicaInternalException(3);
        }
        if (!byteBuffer.check(0, (byte) (((i - 1) * 2) + 13))) {
            throw new FelicaInternalException(3);
        }
        this.mServiceKeyVersionList = DataUtil.createKeyVersionList(byteBuffer, requestServiceCommand.getServiceCodeList());
        return this;
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    int[] getServiceKeyVersionList() {
        return this.mServiceKeyVersionList;
    }
}
