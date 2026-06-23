package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class GetContainerIssueInformationResponse extends Response {
    private static final byte CODE = 35;
    private static final int CONTAINER_ISSUE_INFORMAITON_LENGTH = 16;
    private static final int IDM_LENGTH = 8;
    private static final byte RESPONSE_LENGTH = 26;
    private byte[] mContainerIssueInfo;
    private byte[] mIdm;

    GetContainerIssueInformationResponse() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Response
    Response get(Command command, ByteBuffer byteBuffer) throws OfflineException {
        if (command == null || byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            if (!byteBuffer.check(1, (byte) 35)) {
                throw new OfflineException(4);
            }
            this.mIdm = new byte[8];
            byteBuffer.copy(2, this.mIdm);
            this.mContainerIssueInfo = new byte[16];
            byteBuffer.copy(10, this.mContainerIssueInfo);
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

    byte[] getContainerIssueInfo() {
        return this.mContainerIssueInfo;
    }
}
