package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RequestBlockInformationExCommand extends Command {
    private static final byte CODE = 30;
    private byte[] mIdm;
    int[] mNodeCodeList;
    private int mNodeCodeSize;
    private RequestBlockInformationExResponse mResponse = new RequestBlockInformationExResponse();

    RequestBlockInformationExCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append(CODE);
            byteBuffer.append(this.mIdm);
            DataUtil.getInstance().append(byteBuffer, this.mNodeCodeList, this.mNodeCodeSize);
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

    int getNodeCodeSize() {
        return this.mNodeCodeSize;
    }

    void setNodeCodeSize(int i) {
        this.mNodeCodeSize = i;
    }

    int[] getNodeCodeList() {
        return this.mNodeCodeList;
    }

    void setNodeCodeList(int[] iArr) {
        this.mNodeCodeList = iArr;
    }
}
