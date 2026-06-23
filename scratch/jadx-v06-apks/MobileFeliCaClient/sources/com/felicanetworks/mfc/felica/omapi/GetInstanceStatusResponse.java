package com.felicanetworks.mfc.felica.omapi;

import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GetInstanceStatusResponse {
    private static final byte[] LIFECYCLE_ACTIVATION_STATE = {-97, 127};
    private byte mP2;
    private int mSWCode;
    private TLVResponse mTLVResponse;
    private final int SW_SUCCESS = 36864;
    private final int SW_NOT_FOUND = 27266;
    private final byte P2_MORE_DATA_AVAILABLE = 0;
    private final byte RELATED_DATA = 97;
    private final byte SYSTEM_CODE = IllegalStateErrorException.TYPE_ILLEGAL_STATE;
    private final byte IDM = -127;
    private final byte AID = -124;
    private final byte CID = -105;
    private final byte LONG_LENGTH_IDENTIFIER = IllegalStateErrorException.TYPE_ILLEGAL_STATE;
    private final byte LENGTH_MASK = 127;
    private final int P2_INDEX = 1;
    private final int LENGTH_INDEX = 2;
    private final int COMMAND_TAG_LENGTH = 2;
    private final int SW_CODE_LENGTH = 2;
    private final int DEFAULT_LENGTH_SIZE = 1;
    private final int MAX_BYTE_LENGTH = 2;

    public GetInstanceStatusResponse(byte[] bArr) throws IllegalArgumentException {
        int i;
        int i2;
        this.mTLVResponse = null;
        this.mSWCode = 0;
        this.mP2 = (byte) 1;
        this.mSWCode = getSWCode(bArr);
        if (isStatusSuccess()) {
            if (bArr == null || bArr.length < 5) {
                throw new IllegalArgumentException();
            }
            this.mP2 = bArr[1];
            if ((bArr[2] & IllegalStateErrorException.TYPE_ILLEGAL_STATE) == -128) {
                i2 = ((byte) (bArr[2] & 127)) + 1;
                if (i2 != 2) {
                    throw new IllegalArgumentException();
                }
                i = bArr[(i2 + 2) - 1] & 255;
            } else {
                i = bArr[2] & 255;
                i2 = 1;
            }
            if (bArr.length == i2 + 4 + i) {
                int i3 = i2 + 2;
                this.mTLVResponse = new TLVResponse(TLVResponse.toByteList(bArr).subList(i3, i + i3));
            }
        }
    }

    public List<InstanceStatus> getInstanceStatusList() {
        ArrayList arrayList = new ArrayList();
        List<byte[]> arrayList2 = new ArrayList<>();
        try {
            if (this.mTLVResponse != null) {
                arrayList2 = this.mTLVResponse.getValueList(97);
            }
        } catch (IllegalArgumentException unused) {
        }
        for (byte[] bArr : arrayList2) {
            try {
                InstanceStatus instanceStatus = new InstanceStatus();
                TLVResponse tLVResponse = new TLVResponse(TLVResponse.toByteList(bArr));
                instanceStatus.setSystemCode(tLVResponse.getValue(IllegalStateErrorException.TYPE_ILLEGAL_STATE));
                instanceStatus.setIDm(tLVResponse.getValue(-127));
                instanceStatus.setAid(tLVResponse.getValue(-124));
                instanceStatus.setCid(tLVResponse.getValue(-105));
                instanceStatus.setLifeCycleActivateState(tLVResponse.getValue(LIFECYCLE_ACTIVATION_STATE));
                arrayList.add(instanceStatus);
            } catch (IllegalArgumentException unused2) {
            }
        }
        return arrayList;
    }

    public boolean isMoreDataAvailable() {
        return this.mP2 == 0;
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == 36864;
    }

    public boolean isInstanceNotFound() throws IllegalArgumentException {
        return this.mSWCode == 27266;
    }

    private int getSWCode(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        return ((bArr[bArr.length - 2] & 255) << 8) | (bArr[bArr.length - 1] & 255);
    }
}
