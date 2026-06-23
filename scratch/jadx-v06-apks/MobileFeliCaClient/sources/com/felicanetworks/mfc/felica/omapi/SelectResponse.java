package com.felicanetworks.mfc.felica.omapi;

import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import com.felicanetworks.mfc.tcap.impl.Message;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SelectResponse {
    private static final byte ACTIVATE_MASK = 1;
    private static final int ACTIVATION_STATE = 1;
    private static final int LIFE_CYCLE_STATE = 0;
    private static final byte PERSONALIZED = 15;
    private static final int SW_CODE_LENGTH = 2;
    private int mSWCode;
    private TLVResponse mTLVResponse;
    private static final byte[] LIFECYCLE_ACTIVATION_STATE = {-97, 112};
    private static final byte[] SYSTEM0_INSTANCE = {0};
    private final byte FILE_CONTROL_INFORMATION = 111;
    private final byte APPLICATION_ID = -124;
    private final byte PROPRIETARY_DATA = -91;
    private final byte SYSTEM_CODE = IllegalStateErrorException.TYPE_ILLEGAL_STATE;
    private final byte IDM = -127;
    private final byte PMM = Message.MT_SET_RETRY_COUNT;
    private final byte ENCRYPTION_IDENTIFIER = -125;
    private final byte BINARY_VERSION = -124;
    private final byte APPLET_VERSION = -112;
    private final byte BUILD_OPTION = -111;
    private final byte INSTANCE_TYPE = -110;
    private final byte SUB_INSTANCE_SP_ID = -109;
    private final byte SUB_INSTANCE_ACTIVATE_STATE = -108;
    private final byte SUB_INSTANCE_MULTI_ID = -107;
    private final byte CID = -105;
    private final int SW_SUCCESS = 36864;
    private final int SW_MORE_DATA = 25360;
    private final int SW_DATA_NOT_FOUND = 27272;
    private final int SW_INCORRECT_CMD_DATA = 27264;
    private final int SW_INVALID_CLA = 28160;
    private final int SW_INVALID_INS = 27904;
    private final int SW_INVALID_P1P2 = 27270;
    private final int SW_NO_SPECIFIC_DIAG = 25600;
    private final int SW_WRONG_LC = 26368;
    private final int SW_LOGICAL_CH_NOT_SUPPORTED = 26753;
    private final int SW_SECURITY_STATUS_ERROR = 27010;
    private final int SW_CONDITION_ERROR = 27013;

    public SelectResponse(byte[] bArr) throws IllegalArgumentException {
        this.mTLVResponse = null;
        this.mSWCode = 0;
        this.mSWCode = getSWCode(bArr);
        if (bArr == null || bArr.length <= 2) {
            return;
        }
        List<Byte> byteList = TLVResponse.toByteList(bArr);
        this.mTLVResponse = new TLVResponse(byteList.subList(0, byteList.size() - 2));
    }

    public byte[] getAid() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        return tLVResponse.getValue(111, -124);
    }

    public int getSystemCode() throws IllegalArgumentException {
        byte[] value;
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null || (value = tLVResponse.getValue(111, -91, IllegalStateErrorException.TYPE_ILLEGAL_STATE)) == null || value.length < 2) {
            return 65536;
        }
        return (value[1] & 255) | ((value[0] & 255) << 8);
    }

    public byte[] getIdm() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        return tLVResponse.getValue(111, -91, -127);
    }

    public byte[] getPmm() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        return tLVResponse.getValue(111, -91, Message.MT_SET_RETRY_COUNT);
    }

    public byte[] getApplicationLifeCycleState() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        byte[] bArr = LIFECYCLE_ACTIVATION_STATE;
        return new byte[]{tLVResponse.getValue(111, -91, bArr[0], bArr[1])[0]};
    }

    public byte[] getContactlessActivationLifeState() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        byte[] bArr = LIFECYCLE_ACTIVATION_STATE;
        return new byte[]{tLVResponse.getValue(111, -91, bArr[0], bArr[1])[1]};
    }

    public byte[] getInstanceType() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        return tLVResponse.getValue(111, -91, -110);
    }

    public boolean isStatusSuccess() throws IllegalArgumentException {
        return this.mSWCode == 36864;
    }

    public boolean isPersonalized() throws IllegalArgumentException {
        byte[] applicationLifeCycleState = getApplicationLifeCycleState();
        return applicationLifeCycleState != null && applicationLifeCycleState.length >= 1 && applicationLifeCycleState[0] == 15;
    }

    public boolean isActivated() throws IllegalArgumentException {
        byte[] contactlessActivationLifeState = getContactlessActivationLifeState();
        return (contactlessActivationLifeState == null || contactlessActivationLifeState.length != 1 || (contactlessActivationLifeState[0] & 1) == 0) ? false : true;
    }

    public boolean isSystem0Instance() throws IllegalArgumentException {
        return Arrays.equals(getInstanceType(), SYSTEM0_INSTANCE);
    }

    public int getSWCode(byte[] bArr) {
        if (bArr == null || bArr.length < 2) {
            return 0;
        }
        return ((bArr[bArr.length - 2] & 255) << 8) | (bArr[bArr.length - 1] & 255);
    }

    public byte[] getCid() throws IllegalArgumentException {
        TLVResponse tLVResponse = this.mTLVResponse;
        if (tLVResponse == null) {
            return null;
        }
        return tLVResponse.getValue(111, -91, -105);
    }
}
