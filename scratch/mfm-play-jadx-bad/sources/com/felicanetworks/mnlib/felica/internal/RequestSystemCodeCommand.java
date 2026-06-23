package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes3.dex */
class RequestSystemCodeCommand extends Command {
    private static final byte CODE = 12;
    private static final RequestSystemCodeCommand INSTANCE = new RequestSystemCodeCommand();
    private byte[] mIdm;

    private RequestSystemCodeCommand() {
    }

    static RequestSystemCodeCommand getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    void doSet(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        try {
            byteBuffer.append((byte) 12);
            byteBuffer.append(this.mIdm);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new FelicaInternalException(1);
        }
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    Response get(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        return RequestSystemCodeResponse.getInstance().get(this, byteBuffer);
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    void setIdm(byte[] bArr) {
        this.mIdm = bArr;
    }
}
