package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class ExecuteFelicaCommandCommand extends Command {
    private byte[] mFelicaCommand = null;
    private ExecuteFelicaCommandResponse mResponse = new ExecuteFelicaCommandResponse();

    ExecuteFelicaCommandCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        byte[] bArr;
        if (byteBuffer == null || (bArr = this.mFelicaCommand) == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append(bArr);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new OfflineException(2);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    Response get(ByteBuffer byteBuffer) throws OfflineException {
        return this.mResponse.get(this, byteBuffer);
    }

    void setCommand(byte[] bArr) {
        this.mFelicaCommand = bArr;
    }
}
