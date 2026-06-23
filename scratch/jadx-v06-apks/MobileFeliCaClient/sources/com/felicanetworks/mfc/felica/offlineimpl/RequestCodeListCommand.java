package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class RequestCodeListCommand extends Command {
    private static final byte CODE = 26;
    private byte[] mIdm;
    private int mNodeCodeSize;
    private int mPacketIndex;
    private int mParentAreaCode;
    private RequestCodeListResponse mResponse = new RequestCodeListResponse();

    RequestCodeListCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            LogMgr.log(2, "%s : Throw OfflineException = %d", (Object) "700", (Object) 1);
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append(CODE);
            byteBuffer.append(this.mIdm);
            int i = this.mNodeCodeSize;
            if (i == 2) {
                byteBuffer.appendInLittleEndian(this.mParentAreaCode, 2);
            } else if (i == 4) {
                byteBuffer.appendInLittleEndian(this.mParentAreaCode, 4);
            }
            byteBuffer.appendInLittleEndian(this.mPacketIndex, 2);
        } catch (ArrayIndexOutOfBoundsException unused) {
            LogMgr.log(2, "%s", "701");
            throw new OfflineException(2);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    Response get(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            LogMgr.log(2, "%s : Throw OfflineException = %d", (Object) "700", (Object) 1);
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

    int getNodeCodeSize() {
        return this.mNodeCodeSize;
    }

    void setNodeCodeSize(int i) {
        this.mNodeCodeSize = i;
    }

    int getPacketIndex() {
        return this.mPacketIndex;
    }

    void setPacketIndex(int i) {
        this.mPacketIndex = i;
    }
}
