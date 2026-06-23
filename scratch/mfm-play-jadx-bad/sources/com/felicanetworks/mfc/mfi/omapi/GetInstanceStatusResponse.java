package com.felicanetworks.mfc.mfi.omapi;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class GetInstanceStatusResponse {
    private static final byte[] LIFECYCLE_ACTIVATION_STATE = {-97, 127};
    private byte mP2;
    private int mSWCode;
    private TLVResponse mTLVResponse;
    private final int SW_SUCCESS = 36864;
    private final int SW_NOT_FOUND = 27266;
    private final byte P2_MORE_DATA_AVAILABLE = 0;
    private final byte RELATED_DATA = 97;
    private final byte SYSTEM_CODE = -128;
    private final byte IDM = -127;
    private final byte AID = -124;
    private final byte CID = -105;
    private final byte LONG_LENGTH_IDENTIFIER = -128;
    private final byte LENGTH_MASK = 127;
    private final int P2_INDEX = 1;
    private final int LENGTH_INDEX = 2;
    private final int COMMAND_TAG_LENGTH = 2;
    private final int SW_CODE_LENGTH = 2;
    private final int DEFAULT_LENGTH_SIZE = 1;
    private final int MAX_BYTE_LENGTH = 2;

    public GetInstanceStatusResponse(final byte[] response) throws IllegalArgumentException {
        int i = 1;
        this.mTLVResponse = null;
        this.mSWCode = 0;
        this.mP2 = (byte) 1;
        this.mSWCode = getSWCode(response);
        if (isStatusSuccess()) {
            if (response == null || response.length < 5) {
                throw new IllegalArgumentException();
            }
            this.mP2 = response[1];
            byte b = response[2];
            if ((b & (-128)) == -128) {
                byte b2 = (byte) (b & 127);
                i = b2 + 1;
                if (i != 2) {
                    throw new IllegalArgumentException();
                }
                b = response[b2 + 2];
            }
            int i2 = b & 255;
            if (response.length == i + 4 + i2) {
                int i3 = i + 2;
                this.mTLVResponse = new TLVResponse(TLVResponse.toByteList(response).subList(i3, i2 + i3));
            }
        }
    }

    public List<InstanceStatus> getInstanceStatusList() {
        ArrayList arrayList = new ArrayList();
        List<byte[]> arrayList2 = new ArrayList<>();
        try {
            TLVResponse tLVResponse = this.mTLVResponse;
            if (tLVResponse != null) {
                arrayList2 = tLVResponse.getValueList(97);
            }
        } catch (IllegalArgumentException unused) {
        }
        for (byte[] bArr : arrayList2) {
            try {
                InstanceStatus instanceStatus = new InstanceStatus();
                TLVResponse tLVResponse2 = new TLVResponse(TLVResponse.toByteList(bArr));
                instanceStatus.setSystemCode(tLVResponse2.getValue(-128));
                instanceStatus.setIDm(tLVResponse2.getValue(-127));
                instanceStatus.setAid(tLVResponse2.getValue(-124));
                instanceStatus.setCid(tLVResponse2.getValue(-105));
                instanceStatus.setLifeCycleActivateState(tLVResponse2.getValue(LIFECYCLE_ACTIVATION_STATE));
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
