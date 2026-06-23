package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
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
    private byte mP2 = 0;
    private byte mLc = 4;
    private byte[] mSearchCriteriaSystemCode = {-128, 0};
    private byte[] mSearchCriteriaLifeCycleState = {TAG_LIFE_CYCLE_STATE, 0};

    GetInstanceStatusCommand() {
    }

    void setSearchCriteriaSystemCode(int systemCode) {
        if (systemCode < 0 || systemCode >= 65536) {
            return;
        }
        this.mSearchCriteriaSystemCode = new byte[]{-128, 2, (byte) ((systemCode >> 8) & 255), (byte) (systemCode & 255)};
    }

    void setSearchCriteriaSelectable() {
        this.mSearchCriteriaLifeCycleState = new byte[]{TAG_LIFE_CYCLE_STATE, 1, 7};
    }

    void setSearchCriteriaPersonalized() {
        this.mSearchCriteriaLifeCycleState = new byte[]{TAG_LIFE_CYCLE_STATE, 1, 15};
    }

    boolean increaseIndex() {
        byte b = this.mP2;
        if (b >= 7) {
            return false;
        }
        this.mP2 = (byte) (b + 1);
        return true;
    }

    void set(ByteBuffer byteBuffer) throws GpException {
        if (byteBuffer == null) {
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        this.mByteBuffer = byteBuffer;
        byteBuffer.setLength(0);
        this.mByteBuffer.append(new byte[]{-128, INS, 4, this.mP2});
        byte length = (byte) (this.mSearchCriteriaSystemCode.length + this.mSearchCriteriaLifeCycleState.length);
        this.mLc = length;
        this.mByteBuffer.append(length);
        this.mByteBuffer.append(this.mSearchCriteriaSystemCode);
        this.mByteBuffer.append(this.mSearchCriteriaLifeCycleState);
        this.mByteBuffer.append((byte) 0);
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
