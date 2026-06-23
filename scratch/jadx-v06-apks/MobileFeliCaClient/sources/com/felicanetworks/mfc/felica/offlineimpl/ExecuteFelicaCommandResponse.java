package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class ExecuteFelicaCommandResponse extends Response {
    private byte[] mResponse = null;

    ExecuteFelicaCommandResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        if (byteBuffer.getLength() <= 1 || byteBuffer.getInBigEndian(0, 1) != byteBuffer.getLength()) {
            throw new OfflineException(4);
        }
        this.mResponse = new byte[byteBuffer.getLength() - 1];
        byteBuffer.copy(1, this.mResponse);
        return this;
    }

    byte[] getResponse() {
        return this.mResponse;
    }
}
