package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.AreaInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class RequestMaskedCodeListResponse extends Response {
    private static final byte CODE = -51;
    private static final byte FCODE = 2;
    private static final int IDM_LENGTH = 8;
    private static final int MAX_LIST_NUM = 24;
    private byte mContinueFlag;
    private byte[] mIdm;
    private NodeInformation mNodeInformation;
    private byte mStatusFlag1;
    private byte mStatusFlag2;

    RequestMaskedCodeListResponse() {
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
            if (!byteBuffer.check(2, (byte) 2)) {
                LogMgr.log(1, "%s", "702");
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(3, this.mIdm);
            this.mStatusFlag1 = byteBuffer.get(11);
            this.mStatusFlag2 = byteBuffer.get(12);
            int length = 13;
            if (this.mStatusFlag1 == 0) {
                this.mContinueFlag = byteBuffer.get(13);
                this.mNodeInformation = DataUtil.getInstance().createNodeInformation(byteBuffer, 4, 14);
                length = 15 + (this.mNodeInformation.getAreaInformationList().length * 8) + 1 + (this.mNodeInformation.getServiceCodeList().length * 4);
                int length2 = this.mNodeInformation.getAreaInformationList().length + this.mNodeInformation.getServiceCodeList().length;
                if (length2 > 24) {
                    LogMgr.log(1, "%s : List over(%d)", "806", Integer.valueOf(length2));
                    throw new OfflineException(4);
                }
            } else {
                this.mContinueFlag = (byte) 0;
                this.mNodeInformation = new NodeInformation(new AreaInformation[0], new int[0]);
            }
            if (length == byteBuffer.getLength()) {
                return this;
            }
            LogMgr.log(1, "%s", "704");
            throw new OfflineException(4);
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "705");
            throw e;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "706");
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

    int getContinueFlag() {
        return this.mContinueFlag & 255;
    }

    NodeInformation getNodeInformation() {
        return this.mNodeInformation;
    }
}
