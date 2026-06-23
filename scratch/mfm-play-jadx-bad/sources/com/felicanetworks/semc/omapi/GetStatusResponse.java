package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GetStatusResponse {
    private static final int APPLET_STATUS_INDEX = 1;
    private static final byte APPLICATION_AID = 79;
    private static final byte APPLICATION_RELATED_DATA = 97;
    private static final byte[] LIFECYCLE_STATE = {-97, 112};
    private static final int LIFECYCLE_STATE_LENGTH = 2;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_SUCCESS = 36864;
    private final int mSWCode;
    private final TLVResponse mTLVResponse;

    public GetStatusResponse(byte[] bArr) throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            LogMgr.log(1, "800 response == null || response.length < SW_CODE_LENGTH. ");
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        this.mSWCode = getSWCode(bArr);
        List<Byte> byteList = TLVResponse.toByteList(bArr);
        if (byteList == null) {
            LogMgr.log(1, "801 TLVResponse.toByteList == null. ");
            throw new IllegalArgumentException();
        }
        this.mTLVResponse = new TLVResponse(byteList.subList(0, byteList.size() - 2));
        LogMgr.log(6, "999");
    }

    public List<byte[]> getApplicationRelatedDataList() throws IllegalArgumentException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mTLVResponse.getValueList(APPLICATION_RELATED_DATA);
    }

    public boolean isStatusSuccess() {
        return this.mSWCode == SW_SUCCESS;
    }

    private int getSWCode(byte[] bArr) {
        LogMgr.log(6, "000");
        if (bArr == null || bArr.length < 2) {
            LogMgr.log(6, "998 response == null || response.length < SW_CODE_LENGTH");
            return 0;
        }
        int i = (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
        LogMgr.log(6, "999 swCode=[" + Integer.toHexString(i) + "]");
        return i;
    }

    public byte[] getSw() throws IllegalArgumentException {
        LogMgr.log(6, "000");
        if (this.mSWCode == 0) {
            LogMgr.log(6, "998mSWCode == 0");
            return new byte[]{0, 0};
        }
        LogMgr.log(6, "999");
        int i = this.mSWCode;
        return new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }

    public Byte getAppletStatus(byte[] bArr) {
        Byte bValueOf;
        LogMgr.log(6, "000 aid=" + StringUtil.bytesToHexString(bArr));
        Iterator<byte[]> it = this.mTLVResponse.getValueList(APPLICATION_RELATED_DATA).iterator();
        while (true) {
            if (!it.hasNext()) {
                bValueOf = null;
                break;
            }
            TLVResponse tLVResponse = new TLVResponse(TLVResponse.toByteList(it.next()));
            byte[] value = tLVResponse.getValue(APPLICATION_AID);
            LogMgr.log(8, "001 response aid=" + StringUtil.bytesToHexString(value));
            if (Arrays.equals(value, bArr)) {
                LogMgr.log(8, "002");
                byte[] bArr2 = LIFECYCLE_STATE;
                byte[] value2 = tLVResponse.getValue(bArr2[0], bArr2[1]);
                if (value2 != null && value2.length == 2) {
                    bValueOf = Byte.valueOf(value2[1]);
                    break;
                }
            }
        }
        LogMgr.log(6, "999 ret:" + bValueOf);
        return bValueOf;
    }
}
