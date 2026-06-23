package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RequestServiceCommand extends Command {
    private static final byte CODE = 2;
    private byte[] mIdm;
    private int mNodeCodeSize;
    private RequestServiceResponse mResponse = new RequestServiceResponse();
    private int[] mServiceCodeList;

    RequestServiceCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append((byte) 2);
            byteBuffer.append(this.mIdm);
            DataUtil.getInstance().append(byteBuffer, this.mServiceCodeList, this.mNodeCodeSize);
        } catch (OfflineException e) {
            throw e;
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

    int[] getServiceCodeList() {
        return this.mServiceCodeList;
    }

    void setServiceCodeList(int[] iArr) {
        this.mServiceCodeList = iArr;
    }
}
