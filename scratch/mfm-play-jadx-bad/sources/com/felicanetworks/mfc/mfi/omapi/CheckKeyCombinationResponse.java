package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class CheckKeyCombinationResponse {
    private static final int EXPECTED_NODE_NUM = 2;
    private static final int IDM_LENGTH = 8;
    private static final int MAX_DATA_LENGTH = 255;
    private static final byte MIN_RES_LENGTH = 22;
    private static final int NODE_LENGTH = 2;
    private static final byte RES_CODE = 51;
    private static final int STATUS_FLAG1_OK = 0;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private int mSystemAesKeyVersion = 0;
    private int mArea0AesKeyVersion = 0;
    private int mSystemDesKeyVersion = 0;
    private int mArea0DesKeyVersion = 0;
    private int mSWCode = 0;
    private ByteBuffer mByteBuffer = new ByteBuffer(257);

    public void set(final byte[] response) throws IllegalArgumentException {
        if (response == null || response.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(response);
        if (isStatusSuccess()) {
            if (response.length < 24) {
                LogMgr.log(1, "800  Too short response");
                throw new IllegalArgumentException();
            }
            this.mByteBuffer.setLength(0);
            this.mByteBuffer.append(response);
            if (!this.mByteBuffer.check(1, RES_CODE)) {
                LogMgr.log(1, "801 Response code incorrect.");
                throw new IllegalArgumentException();
            }
            int i = this.mByteBuffer.get(10) & 255;
            int i2 = this.mByteBuffer.get(11) & 255;
            if (i == 0) {
                this.mByteBuffer.get(12);
                int i3 = this.mByteBuffer.get(13) & 255;
                if (i3 != 2) {
                    LogMgr.log(1, "804 Node num is invalid(" + i3 + ")");
                    throw new IllegalArgumentException();
                }
                this.mSystemAesKeyVersion = ((this.mByteBuffer.get(15) & 255) << 8) + (this.mByteBuffer.get(14) & 255);
                this.mArea0AesKeyVersion = ((this.mByteBuffer.get(17) & 255) << 8) + (this.mByteBuffer.get(16) & 255);
                this.mSystemDesKeyVersion = ((this.mByteBuffer.get(19) & 255) << 8) + (this.mByteBuffer.get(18) & 255);
                this.mArea0DesKeyVersion = ((this.mByteBuffer.get(21) & 255) << 8) + (this.mByteBuffer.get(20) & 255);
                if (this.mByteBuffer.getLength() == 24) {
                    return;
                }
                LogMgr.log(1, "805 invalid length response");
                throw new IllegalArgumentException();
            }
            LogMgr.log(1, "806 StatusFlag failed ( SF1 = " + i + ", SF2 = " + i2 + ")");
            throw new IllegalArgumentException();
        }
    }

    boolean hasNoOwner() {
        return (this.mSystemDesKeyVersion == 3 && this.mSystemAesKeyVersion == 4160 && this.mArea0DesKeyVersion == 3 && this.mArea0AesKeyVersion == 4160) ? false : true;
    }

    public boolean isStatusSuccess() {
        return this.mSWCode == SW_SUCCESS;
    }

    private int getSWCode(byte[] response) {
        if (response == null || response.length < 2) {
            return 0;
        }
        return (response[response.length - 1] & 255) | ((response[response.length - 2] & 255) << 8);
    }

    public byte[] getSw() throws IllegalArgumentException {
        int i = this.mSWCode;
        if (i == 0) {
            return new byte[]{0, 0};
        }
        return new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }
}
