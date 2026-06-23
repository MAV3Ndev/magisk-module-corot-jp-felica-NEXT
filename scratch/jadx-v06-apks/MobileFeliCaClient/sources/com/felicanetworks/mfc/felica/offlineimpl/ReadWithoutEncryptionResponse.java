package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class ReadWithoutEncryptionResponse extends Response {
    private static final byte CODE = 7;
    private static final int IDM_LENGTH = 8;
    private static final int STATUS_FLAG1_OK = 0;
    private Data[] mDataList;
    private byte[] mIdm;
    private int mStatusFlag1;
    private int mStatusFlag2;

    ReadWithoutEncryptionResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, (byte) 7)) {
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mStatusFlag1 = byteBuffer.get(10) & 255;
            this.mStatusFlag2 = byteBuffer.get(11) & 255;
            if (this.mStatusFlag1 == 0) {
                this.mDataList = DataUtil.getInstance().createDataList(byteBuffer, ((ReadWithoutEncryptionCommand) command).getBlockList());
                if ((byteBuffer.get(12) * 16) + 12 + 1 != byteBuffer.getLength()) {
                    throw new OfflineException(4);
                }
            } else {
                this.mDataList = new Data[0];
                if (12 != byteBuffer.getLength()) {
                    throw new OfflineException(4);
                }
            }
            return this;
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

    Data[] getDataList() {
        return this.mDataList;
    }
}
