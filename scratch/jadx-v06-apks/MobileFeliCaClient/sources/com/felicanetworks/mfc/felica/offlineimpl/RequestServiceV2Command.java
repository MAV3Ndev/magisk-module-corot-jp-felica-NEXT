package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RequestServiceV2Command extends Command {
    private static final byte CODE = 50;
    private byte[] mIdm;
    private int[] mNodeCodeList;
    private int[] mNonExistNodeCodeMask;
    private RequestServiceV2Response mResponse = new RequestServiceV2Response();

    RequestServiceV2Command() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append(CODE);
            byteBuffer.append(this.mIdm);
            DataUtil.getInstance().append(byteBuffer, this.mNodeCodeList, 2);
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

    int[] getNodeCodeList() {
        return this.mNodeCodeList;
    }

    void setNodeCodeList(int[] iArr) {
        this.mNodeCodeList = iArr;
        checkNonExistNodeCode(iArr);
    }

    int[] getNonExistNodeCodeMask() {
        return this.mNonExistNodeCodeMask;
    }

    private void checkNonExistNodeCode(int[] iArr) {
        if (iArr == null) {
            this.mNonExistNodeCodeMask = null;
            return;
        }
        this.mNonExistNodeCodeMask = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            if (iArr[i] == -1) {
                this.mNonExistNodeCodeMask[i] = 0;
            } else if ((iArr[i] & (-65536)) == 0) {
                this.mNonExistNodeCodeMask[i] = 0;
            } else {
                this.mNonExistNodeCodeMask[i] = 65535;
            }
        }
    }
}
