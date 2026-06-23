package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class SetPrivacyFlagResponse extends Response {
    private static final byte CODE = -51;
    private static final byte FCODE = 1;
    private static final int IDM_LENGTH = 8;
    private byte[] mIdm;
    private byte mStatusFlag1;
    private byte mStatusFlag2;

    SetPrivacyFlagResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, CODE)) {
                throw new OfflineException(4);
            }
            if (!byteBuffer.check(2, (byte) 1)) {
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(3, this.mIdm);
            this.mStatusFlag1 = byteBuffer.get(11);
            this.mStatusFlag2 = byteBuffer.get(12);
            if (13 == byteBuffer.getLength()) {
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

    int getStatusFlag1() {
        return this.mStatusFlag1 & 255;
    }

    int getStatusFlag2() {
        return this.mStatusFlag2 & 255;
    }
}
