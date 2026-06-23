package com.felicanetworks.mfc.mfi.omapi;

import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SelectResponse {
    private static final byte ACTIVATE_MASK = 1;
    private static final int ACTIVATION_STATE = 1;
    private static final byte APPLET_VERSION = -112;
    private static final byte APPLICATION_ID = -124;
    private static final byte BINARY_VERSION = -124;
    private static final byte BUILD_OPTION = -111;
    private static final byte CID = -105;
    private static final byte ENCRYPTION_IDENTIFIER = -125;
    private static final byte FILE_CONTROL_INFORMATION = 111;
    private static final byte IDM = -127;
    private static final byte INSTANCE_TYPE = -110;
    private static final int LIFE_CYCLE_STATE = 0;
    private static final byte PERSONALIZED = 15;
    private static final byte PMM = -126;
    private static final byte PROPRIETARY_DATA = -91;
    private static final byte SELECTABLE = 7;
    private static final byte SUB_INSTANCE_ACTIVATE_STATE = -108;
    private static final byte SUB_INSTANCE_MULTI_ID = -107;
    private static final byte SUB_INSTANCE_SP_ID = -109;
    private static final int SW_CODE_LENGTH = 2;
    private static final int SW_CONDITION_ERROR = 27013;
    private static final int SW_DATA_NOT_FOUND = 27272;
    private static final int SW_INCORRECT_CMD_DATA = 27264;
    private static final int SW_INVALID_CLA = 28160;
    private static final int SW_INVALID_INS = 27904;
    private static final int SW_INVALID_P1P2 = 27270;
    private static final int SW_LOGICAL_CH_NOT_SUPPORTED = 26753;
    private static final int SW_MORE_DATA = 25360;
    private static final int SW_NO_SPECIFIC_DIAG = 25600;
    private static final int SW_SECURITY_STATUS_ERROR = 27010;
    private static final int SW_SUCCESS = 36864;
    private static final int SW_WRONG_LC = 26368;
    private static final byte SYSTEM_CODE = -128;
    private int mSWCode;
    private TLVResponse mTLVResponse;
    private static final byte[] LIFECYCLE_ACTIVATION_STATE = {-97, 112};
    private static final byte[] SYSTEM0_INSTANCE = {0};

    public SelectResponse(byte[] bArr) throws IllegalArgumentException {
        this.mTLVResponse = null;
        if (bArr == null || bArr.length < 2) {
            throw new IllegalArgumentException();
        }
        this.mSWCode = getSWCode(bArr);
        List<Byte> byteList = TLVResponse.toByteList(bArr);
        this.mTLVResponse = new TLVResponse(byteList.subList(0, byteList.size() - 2));
    }

    public byte[] getAid() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, -124);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getSystemCodeInByte() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, SYSTEM_CODE);
        if (value == null || value.length < 2) {
            throw new IllegalArgumentException();
        }
        return value;
    }

    public int getSystemCode() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, SYSTEM_CODE);
        if (value == null || value.length < 2) {
            throw new IllegalArgumentException();
        }
        return (value[1] & 255) | ((value[0] & 255) << 8);
    }

    public byte[] getIdm() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, IDM);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getPmm() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, PMM);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getBinaryVersion() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, -124);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getAppletVersion() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, APPLET_VERSION);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getSw() throws IllegalArgumentException {
        int i = this.mSWCode;
        return i == 0 ? new byte[]{0, 0} : new byte[]{(byte) ((65280 & i) >> 8), (byte) (i & 255)};
    }

    public byte[] getApplicationLifeCycleState() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] bArr = LIFECYCLE_ACTIVATION_STATE;
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, bArr[0], bArr[1]);
        if (value != null) {
            return new byte[]{value[0]};
        }
        throw new IllegalArgumentException();
    }

    public byte[] getContactlessActivationLifeState() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] bArr = LIFECYCLE_ACTIVATION_STATE;
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, bArr[0], bArr[1]);
        if (value != null) {
            return new byte[]{value[1]};
        }
        throw new IllegalArgumentException();
    }

    public byte[] getInstanceType() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, INSTANCE_TYPE);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public byte[] getCid() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            throw new IllegalArgumentException();
        }
        byte[] value = tLVResponse.getValue(FILE_CONTROL_INFORMATION, PROPRIETARY_DATA, CID);
        if (value != null) {
            return value;
        }
        throw new IllegalArgumentException();
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == SW_SUCCESS;
    }

    public boolean isPersonalized() throws IllegalArgumentException {
        byte[] applicationLifeCycleState = getApplicationLifeCycleState();
        return applicationLifeCycleState != null && applicationLifeCycleState.length >= 1 && applicationLifeCycleState[0] == 15;
    }

    public boolean isSelectTable() throws IllegalArgumentException {
        byte[] applicationLifeCycleState = getApplicationLifeCycleState();
        return applicationLifeCycleState != null && applicationLifeCycleState.length >= 1 && applicationLifeCycleState[0] == 7;
    }

    public boolean isActivated() throws IllegalArgumentException {
        byte[] contactlessActivationLifeState = getContactlessActivationLifeState();
        return (contactlessActivationLifeState == null || contactlessActivationLifeState.length != 1 || (contactlessActivationLifeState[0] & ACTIVATE_MASK) == 0) ? false : true;
    }

    public boolean isSystem0Instance() throws IllegalArgumentException {
        return Arrays.equals(getInstanceType(), SYSTEM0_INSTANCE);
    }

    private int getSWCode(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        return (bArr[bArr.length - 1] & 255) | ((bArr[bArr.length - 2] & 255) << 8);
    }
}
