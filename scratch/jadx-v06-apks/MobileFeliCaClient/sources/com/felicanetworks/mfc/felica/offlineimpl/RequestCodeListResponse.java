package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.AreaInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class RequestCodeListResponse extends Response {
    private static final byte CODE = 27;
    private static final int IDM_LENGTH = 8;
    private static final int MAX_LIST_NUM = 24;
    private static final int STATUS_FLAG1_OK = 0;
    private int mContinueFlag;
    private byte[] mIdm;
    private NodeInformation mNodeInformation;
    private int mStatusFlag1;
    private int mStatusFlag2;

    RequestCodeListResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            LogMgr.log(2, "%s : %s", "700", "Throw OfflineException.TYPE_INVALID_PARAMETER");
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, CODE)) {
                LogMgr.log(2, "%s : %s", "701", "Throw OfflineException.TYPE_INVALID_RESPONSE");
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mStatusFlag1 = byteBuffer.get(10) & 255;
            this.mStatusFlag2 = byteBuffer.get(11) & 255;
            if (this.mStatusFlag1 == 0) {
                this.mContinueFlag = byteBuffer.get(12) & 255;
                RequestCodeListCommand requestCodeListCommand = (RequestCodeListCommand) command;
                this.mNodeInformation = DataUtil.getInstance().createNodeInformation(byteBuffer, requestCodeListCommand.getNodeCodeSize(), 13);
                int length = this.mNodeInformation.getAreaInformationList().length + this.mNodeInformation.getServiceCodeList().length;
                if (length > 24) {
                    LogMgr.log(1, "%s : List over(%d)", "806", Integer.valueOf(length));
                    throw new OfflineException(4);
                }
                int nodeCodeSize = (byteBuffer.get(13) * 2 * requestCodeListCommand.getNodeCodeSize()) + 1 + 13;
                if (nodeCodeSize + (byteBuffer.get(nodeCodeSize) * requestCodeListCommand.getNodeCodeSize()) + 1 != byteBuffer.getLength()) {
                    LogMgr.log(1, "%s : %s", "804", "Throw OfflineException.TYPE_INVALID_RESPONSE");
                    throw new OfflineException(4);
                }
            } else {
                this.mNodeInformation = new NodeInformation(new AreaInformation[0], new int[0]);
                if (12 != byteBuffer.getLength()) {
                    LogMgr.log(1, "%s : %s", "805", "Throw OfflineException.TYPE_INVALID_RESPONSE");
                    throw new OfflineException(4);
                }
            }
            return this;
        } catch (OfflineException e) {
            LogMgr.log(2, "%s : %s = %d", "706", "catch OfflineException", Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s : %s", "707", "Throw OfflineException.TYPE_INVALID_RESPONSE");
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

    int getContinueFlag() {
        return this.mContinueFlag;
    }

    NodeInformation getNodeInformation() {
        return this.mNodeInformation;
    }
}
