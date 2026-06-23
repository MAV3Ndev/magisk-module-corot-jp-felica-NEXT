package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RequestBlockInformationExResponse extends Response {
    private static final byte CODE = 31;
    private static final int IDM_LENGTH = 8;
    BlockCountInformation[] mBlockCountInformation;
    private byte[] mIdm;
    private byte mStatusFlag1;
    private byte mStatusFlag2;

    RequestBlockInformationExResponse() {
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
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mStatusFlag1 = byteBuffer.get(10);
            this.mStatusFlag2 = byteBuffer.get(11);
            int length = 12;
            if (this.mStatusFlag1 == 0) {
                this.mBlockCountInformation = DataUtil.getInstance().createBlockCountInformation(byteBuffer, ((RequestBlockInformationExCommand) command).getNodeCodeList());
                length = (this.mBlockCountInformation.length * 4) + 13;
            } else {
                this.mBlockCountInformation = null;
            }
            if (length == byteBuffer.getLength()) {
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

    BlockCountInformation[] getBlockCountInformation() {
        return this.mBlockCountInformation;
    }
}
