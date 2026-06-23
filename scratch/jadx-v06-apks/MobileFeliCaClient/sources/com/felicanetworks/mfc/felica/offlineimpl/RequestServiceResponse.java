package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class RequestServiceResponse extends Response {
    private static final byte CODE = 3;
    private static final int IDM_LENGTH = 8;
    private byte[] mIdm;
    private int[] mServiceKeyVersionList;

    RequestServiceResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, (byte) 3)) {
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mServiceKeyVersionList = DataUtil.getInstance().createKeyVersionList(byteBuffer, ((RequestServiceCommand) command).getServiceCodeList());
            if ((byteBuffer.get(10) * 2) + 10 + 1 == byteBuffer.getLength()) {
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

    int[] getServiceKeyVersionList() {
        return this.mServiceKeyVersionList;
    }
}
