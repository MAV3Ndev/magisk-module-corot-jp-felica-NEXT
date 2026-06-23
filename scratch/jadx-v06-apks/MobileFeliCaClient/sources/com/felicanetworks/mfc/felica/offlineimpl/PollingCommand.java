package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class PollingCommand extends Command {
    private static final byte CODE = 0;
    private static final byte TIME_SLOT = 0;
    private byte mOption;
    private PollingResponse mResponse = new PollingResponse();
    private int mSystemCode;

    PollingCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append((byte) 0);
            byteBuffer.appendInBigEndian(this.mSystemCode, 2);
            byteBuffer.append(this.mOption);
            byteBuffer.append((byte) 0);
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

    int getSystemCode() {
        return this.mSystemCode;
    }

    void setSystemCode(int i) {
        this.mSystemCode = i;
    }

    void setOption(byte b) {
        this.mOption = b;
    }
}
