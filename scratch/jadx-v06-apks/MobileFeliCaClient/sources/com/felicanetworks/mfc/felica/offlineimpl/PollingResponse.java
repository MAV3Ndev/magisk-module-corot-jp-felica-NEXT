package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class PollingResponse extends Response {
    private static final byte CODE = 1;
    private static final int IDM_LENGTH = 8;
    private static final int PMM_LENGTH = 8;
    private static final byte RESPONSE_LENGTH = 20;
    private byte[] mIdm;
    private byte[] mPmm;
    private int mSystemCode;

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, (byte) 1)) {
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mPmm = new byte[8];
            byteBuffer.copy(10, this.mPmm);
            this.mSystemCode = (int) byteBuffer.getInBigEndian(18, 2);
            if (byteBuffer.check(0, RESPONSE_LENGTH)) {
                return this;
            }
            throw new OfflineException(4);
        } catch (OfflineException e) {
            throw e;
        } catch (Exception unused) {
            throw new OfflineException(4);
        }
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    byte[] getPmm() {
        return this.mPmm;
    }

    public int getSystemCode() {
        return this.mSystemCode;
    }
}
