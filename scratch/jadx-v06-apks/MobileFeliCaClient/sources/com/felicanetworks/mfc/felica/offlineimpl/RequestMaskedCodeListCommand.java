package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RequestMaskedCodeListCommand extends Command {
    private static final byte CODE = -52;
    private static final byte FCODE = 2;
    private byte[] mIdm;
    private int mPacketIndex;
    private int mParentAreaCode;
    private RequestMaskedCodeListResponse mResponse = new RequestMaskedCodeListResponse();

    RequestMaskedCodeListCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append(CODE);
            byteBuffer.append((byte) 2);
            byteBuffer.append(this.mIdm);
            byteBuffer.appendInLittleEndian(this.mParentAreaCode, 4);
            byteBuffer.appendInLittleEndian(this.mPacketIndex, 2);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new OfflineException(2);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    Response get(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        return this.mResponse.get(this, byteBuffer);
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    void setIdm(byte[] bArr) {
        this.mIdm = bArr;
    }

    int getParentAreaCode() {
        return this.mParentAreaCode;
    }

    void setParentAreaCode(int i) {
        this.mParentAreaCode = i;
    }

    int getPacketIndex() {
        return this.mPacketIndex;
    }

    void setPacketIndex(int i) {
        this.mPacketIndex = i;
    }
}
