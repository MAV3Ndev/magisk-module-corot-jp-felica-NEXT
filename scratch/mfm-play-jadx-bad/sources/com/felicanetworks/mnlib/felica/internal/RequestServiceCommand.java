package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
class RequestServiceCommand extends Command {
    private static final byte CODE = 2;
    private static final RequestServiceCommand INSTANCE = new RequestServiceCommand();
    private byte[] mIdm;
    private int mNodeCodeSize;
    private int[] mServiceCodeList;

    private RequestServiceCommand() {
    }

    static RequestServiceCommand getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    void doSet(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        try {
            byteBuffer.append((byte) 2);
            byteBuffer.append(this.mIdm);
            DataUtil.append(byteBuffer, this.mServiceCodeList, this.mNodeCodeSize);
        } catch (FelicaInternalException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new FelicaInternalException(1);
        }
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    Response get(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        return RequestServiceResponse.getInstance().get(this, byteBuffer);
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    void setIdm(byte[] bArr) {
        this.mIdm = bArr;
    }

    int getNodeCodeSize() {
        return this.mNodeCodeSize;
    }

    void setNodeCodeSize(int i) {
        this.mNodeCodeSize = i;
    }

    int[] getServiceCodeList() {
        return this.mServiceCodeList;
    }

    void setServiceCodeList(int[] iArr) {
        this.mServiceCodeList = iArr;
    }
}
