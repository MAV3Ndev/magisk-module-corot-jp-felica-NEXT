package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class SetParameterResponse extends Response {
    private static final byte CODE = 33;
    private static final int IDM_LENGTH = 8;
    private static final byte RESPONSE_LENGTH = 12;
    private byte[] mIdm;
    private int mStatusFlag1;
    private int mStatusFlag2;

    SetParameterResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, (byte) 33)) {
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mStatusFlag1 = byteBuffer.get(10) & 255;
            this.mStatusFlag2 = byteBuffer.get(11) & 255;
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

    int getStatusFlag1() {
        return this.mStatusFlag1;
    }

    int getStatusFlag2() {
        return this.mStatusFlag2;
    }
}
