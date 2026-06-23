package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class GetAppletKeyInfoCommand extends Command {
    private static final byte CLA = -128;
    private static final byte INS = -54;
    private ByteBuffer mByteBuffer;
    public static final byte[] GET_UPGRADE_KEYINFO_P1P2 = {2, 0};
    public static final byte[] GET_INIT_KEY1INFO_P1P2 = {2, 1};
    public static final byte[] GET_INIT_KEY2INFO_P1P2 = {2, 2};
    private byte mP1 = 2;
    private byte mP2 = 0;
    private byte mLc = 0;
    private byte[] mToken = null;
    private byte mLe = 0;

    GetAppletKeyInfoCommand() {
    }

    void setParameters(byte b, byte b2) {
        setParameters(b, b2, (byte) 0, null);
    }

    void setParameters(byte b, byte b2, byte b3, byte[] bArr) {
        this.mP1 = b;
        this.mP2 = b2;
        this.mLc = b3;
        this.mToken = bArr;
    }

    void setDataToken(byte[] bArr) {
        if (bArr == null || bArr.length > 255) {
            return;
        }
        this.mLc = (byte) bArr.length;
        this.mToken = bArr;
    }

    void set(ByteBuffer byteBuffer) throws GpException {
        if (byteBuffer == null) {
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        this.mByteBuffer = byteBuffer;
        byteBuffer.setLength(0);
        this.mByteBuffer.append(new byte[]{CLA, INS, this.mP1, this.mP2});
        byte b = this.mLc;
        if (b != 0 && this.mToken != null) {
            this.mByteBuffer.append(b);
            this.mByteBuffer.append(this.mToken);
        }
        this.mByteBuffer.append(this.mLe);
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
