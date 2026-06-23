package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class RequestSystemCodeResponse extends Response {
    private static final byte CODE = 13;
    private static final int IDM_LENGTH = 8;
    private static final int SYSTEM_CODE_LIST_MIN = 1;
    private byte[] mIdm;
    private int[] mSystemCodeList;

    RequestSystemCodeResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            LogMgr.log(1, "%s", "700");
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, CODE)) {
                LogMgr.log(1, "%s", "701");
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mSystemCodeList = DataUtil.getInstance().createSystemCode(byteBuffer);
            int length = 10 + (this.mSystemCodeList.length * 2) + 1;
            if (this.mSystemCodeList.length < 1) {
                LogMgr.log(1, "%s", "706");
                throw new OfflineException(1);
            }
            if (length == byteBuffer.getLength()) {
                return this;
            }
            LogMgr.log(1, "%s", "703");
            throw new OfflineException(4);
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "704");
            throw e;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "705");
            throw new OfflineException(4);
        }
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    int[] getSystemCodeList() {
        return this.mSystemCodeList;
    }
}
