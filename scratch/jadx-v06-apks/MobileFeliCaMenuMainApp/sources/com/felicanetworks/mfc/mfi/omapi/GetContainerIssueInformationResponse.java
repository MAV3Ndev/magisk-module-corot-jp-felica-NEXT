package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class GetContainerIssueInformationResponse {
    private static final int CONTAINER_ISSUE_INFORMAITON_LENGTH = 16;
    private static final int IDM_LENGTH = 8;
    private static final int MAX_DATA_LENGTH = 255;
    private static final byte RES_CODE = 35;
    private static final int RES_CODE_LENGTH = 1;
    private static final byte RES_LENGTH = 26;
    private static final int RES_LENGTH_SIZE = 1;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private byte[] mContainerIssueInfo;
    private byte[] mIdm;
    private int mSWCode = 0;
    private ByteBuffer mByteBuffer = new ByteBuffer(257);

    public void set(byte[] bArr) throws IllegalArgumentException, GpException {
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(bArr);
        if (isStatusSuccess()) {
            this.mByteBuffer.setLength(0);
            this.mByteBuffer.append(bArr);
            if (!this.mByteBuffer.check(1, RES_CODE)) {
                LogMgr.log(1, "801 Response code incorrect.");
                throw new IllegalArgumentException();
            }
            byte[] bArr2 = new byte[8];
            this.mIdm = bArr2;
            this.mByteBuffer.copy(2, bArr2);
            byte[] bArr3 = new byte[16];
            this.mContainerIssueInfo = bArr3;
            this.mByteBuffer.copy(10, bArr3);
            if (this.mByteBuffer.check(0, RES_LENGTH) && bArr.length == 28) {
                return;
            }
            LogMgr.log(1, "801 Response length incorrect.");
            throw new IllegalArgumentException();
        }
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    byte[] getContainerIssueInfo() {
        return this.mContainerIssueInfo;
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == SW_SUCCESS;
    }

    private int getSWCode(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        return (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
    }

    public byte[] getSw() throws IllegalArgumentException {
        int i = this.mSWCode;
        return i == 0 ? new byte[]{0, 0} : new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }
}
