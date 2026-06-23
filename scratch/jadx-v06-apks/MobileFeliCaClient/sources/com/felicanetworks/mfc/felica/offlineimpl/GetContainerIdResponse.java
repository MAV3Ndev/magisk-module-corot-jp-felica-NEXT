package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class GetContainerIdResponse extends Response {
    private static final byte CODE = 113;
    private static final int CONTAINERID_LENGTH = 8;
    private byte[] mCotainerId;

    GetContainerIdResponse() {
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
            this.mCotainerId = new byte[8];
            byteBuffer.copy(2, this.mCotainerId);
            if (10 == byteBuffer.getLength()) {
                return this;
            }
            throw new OfflineException(4);
        } catch (OfflineException e) {
            throw e;
        } catch (Exception unused) {
            throw new OfflineException(4);
        }
    }

    byte[] getCotainerId() {
        return this.mCotainerId;
    }
}
