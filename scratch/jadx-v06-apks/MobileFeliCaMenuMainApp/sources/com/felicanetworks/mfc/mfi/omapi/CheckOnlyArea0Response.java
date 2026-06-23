package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class CheckOnlyArea0Response {
    private static final byte[] AREA0_AREA_CODE = {0, 0};
    private static final int CODE_SIZE = 2;
    private static final int IDM_LENGTH = 8;
    private static final int MAX_DATA_LENGTH = 255;
    private static final byte MIN_RES_LENGTH = 19;
    private static final int NO_CONTINUE_FLAG = 0;
    private static final int NO_SERVICE_CODE_LIST = 0;
    private static final int ONLY_AREA_LIST = 1;
    private static final byte RES_CODE = 27;
    private static final int STATUS_FLAG1_OK = 0;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private byte[] mAreaCode = null;
    private int mSWCode = 0;
    private ByteBuffer mByteBuffer = new ByteBuffer(257);

    public void set(byte[] bArr) throws IllegalArgumentException {
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(bArr);
        if (isStatusSuccess()) {
            if (bArr.length < 21) {
                LogMgr.log(1, "800  Too short response");
                throw new IllegalArgumentException();
            }
            this.mByteBuffer.setLength(0);
            this.mByteBuffer.append(bArr);
            if (!this.mByteBuffer.check(1, RES_CODE)) {
                LogMgr.log(1, "801 Response code incorrect.");
                throw new IllegalArgumentException();
            }
            int i = this.mByteBuffer.get(10) & 255;
            int i2 = this.mByteBuffer.get(11) & 255;
            if (i == 0) {
                int i3 = this.mByteBuffer.get(12) & 255;
                if (i3 != 0) {
                    LogMgr.log(1, "802 : mContinueFlag = " + i3);
                    throw new IllegalArgumentException();
                }
                int i4 = this.mByteBuffer.get(13) & 255;
                if (i4 != 1) {
                    LogMgr.log(1, "803 List size over(" + i4 + ")");
                    throw new IllegalArgumentException();
                }
                byte[] bArr2 = new byte[2];
                this.mByteBuffer.copy(14, bArr2);
                int i5 = this.mByteBuffer.get(18) & 255;
                if (i5 != 0) {
                    LogMgr.log(1, "804 ServiceCode List size over(" + i5 + ")");
                    throw new IllegalArgumentException();
                }
                if (this.mByteBuffer.getLength() != 21) {
                    LogMgr.log(1, "805 invalid length response");
                    throw new IllegalArgumentException();
                }
                this.mAreaCode = bArr2;
                return;
            }
            LogMgr.log(1, "806 StatusFlag failed ( SF1 = " + i + ", SF2 = " + i2 + ")");
            throw new IllegalArgumentException();
        }
    }

    boolean isOnlyArea0() {
        return Arrays.equals(this.mAreaCode, AREA0_AREA_CODE);
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
