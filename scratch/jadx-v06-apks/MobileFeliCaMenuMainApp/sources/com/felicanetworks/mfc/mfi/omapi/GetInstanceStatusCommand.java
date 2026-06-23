package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class GetInstanceStatusCommand extends Command {
    private static final byte CLA = -128;
    private static final byte INS = -54;
    private static final byte LE = 0;
    private static final byte LIFE_CYCLE_STATE_LENGTH = 1;
    private static final byte MAX_INDEX = 7;
    private static final byte P1 = 4;
    private static final byte PERSONALIZED = 15;
    private static final byte SELECTABLE = 7;
    private static final byte SYSTEM_CODE_LENGTH = 2;
    private static final byte TAG_LIFE_CYCLE_STATE = -127;
    private static final byte TAG_SYSTEM_CODE = -128;
    private ByteBuffer mByteBuffer;
    private byte mP2 = LE;
    private byte mLc = P1;
    private byte[] mSearchCriteriaSystemCode = {-128, LE};
    private byte[] mSearchCriteriaLifeCycleState = {TAG_LIFE_CYCLE_STATE, LE};

    GetInstanceStatusCommand() {
    }

    void setSearchCriteriaSystemCode(int i) {
        if (i < 0 || i >= 65536) {
            return;
        }
        this.mSearchCriteriaSystemCode = new byte[]{-128, SYSTEM_CODE_LENGTH, (byte) ((i >> 8) & 255), (byte) (i & 255)};
    }

    void setSearchCriteriaSelectable() {
        this.mSearchCriteriaLifeCycleState = new byte[]{TAG_LIFE_CYCLE_STATE, LIFE_CYCLE_STATE_LENGTH, 7};
    }

    void setSearchCriteriaPersonalized() {
        this.mSearchCriteriaLifeCycleState = new byte[]{TAG_LIFE_CYCLE_STATE, LIFE_CYCLE_STATE_LENGTH, PERSONALIZED};
    }

    boolean increaseIndex() {
        byte b = this.mP2;
        if (b >= 7) {
            return false;
        }
        this.mP2 = (byte) (b + LIFE_CYCLE_STATE_LENGTH);
        return true;
    }

    void set(ByteBuffer byteBuffer) throws GpException {
        if (byteBuffer == null) {
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        this.mByteBuffer = byteBuffer;
        byteBuffer.setLength(0);
        this.mByteBuffer.append(new byte[]{-128, INS, P1, this.mP2});
        byte length = (byte) (this.mSearchCriteriaSystemCode.length + this.mSearchCriteriaLifeCycleState.length);
        this.mLc = length;
        this.mByteBuffer.append(length);
        this.mByteBuffer.append(this.mSearchCriteriaSystemCode);
        this.mByteBuffer.append(this.mSearchCriteriaLifeCycleState);
        this.mByteBuffer.append(LE);
    }

    @Override // com.felicanetworks.mfc.mfi.omapi.Command
    Response execute() throws InterruptedException, GpException {
        LogMgr.log(5, "000");
        Response response = new Response();
        try {
            this.mGpController.select(this.mAid);
            LogMgr.log(6, "001");
            int length = this.mByteBuffer.getLength();
            byte[] bArr = new byte[length];
            this.mByteBuffer.copy(0, bArr, 0, length);
            LogMgr.log(6, "002");
            LogMgr.logArray(6, bArr);
            try {
                response.setResponse(this.mGpController.transmit(bArr));
                LogMgr.log(5, "999");
                return response.get();
            } catch (GpException e) {
                LogMgr.printStackTrace(2, e);
                throw e;
            } catch (InterruptedException e2) {
                LogMgr.printStackTrace(2, e2);
                throw e2;
            }
        } catch (GpException e3) {
            LogMgr.printStackTrace(2, e3);
            throw e3;
        } catch (InterruptedException e4) {
            LogMgr.printStackTrace(2, e4);
            throw e4;
        }
    }
}
