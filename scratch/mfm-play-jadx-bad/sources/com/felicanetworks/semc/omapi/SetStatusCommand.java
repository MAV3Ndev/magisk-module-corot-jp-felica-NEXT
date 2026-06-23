package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SetStatusCommand extends Command {
    private static final byte CLA = -128;
    private static final byte INS = -16;
    private static final byte mLe = 0;
    private ByteBuffer mByteBuffer;
    public static final byte[] ACTIVATE_P1P2 = {1, 1};
    public static final byte[] DEACTIVATE_P1P2 = {1, 0};
    private byte mP1 = 0;
    private byte mP2 = 0;
    private byte mLc = 0;
    private byte[] mToken = null;

    SetStatusCommand() {
    }

    void setParameters(byte b, byte b2) {
        LogMgr.log(7, "000");
        this.mP1 = b;
        this.mP2 = b2;
    }

    void setParameters(byte b, byte b2, byte b3, byte[] bArr) {
        LogMgr.log(6, "000");
        setParameters(b, b2);
        this.mLc = b3;
        this.mToken = bArr;
        LogMgr.log(6, "999");
    }

    void setDataToken(byte[] bArr) {
        LogMgr.log(7, "000");
        if (bArr != null && bArr.length <= 255) {
            this.mLc = (byte) bArr.length;
            this.mToken = bArr;
        }
        LogMgr.log(7, "999");
    }

    void set(ByteBuffer byteBuffer) throws GpException {
        LogMgr.log(7, "000");
        if (byteBuffer == null) {
            throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), null);
        }
        LogMgr.log(7, "000");
        this.mByteBuffer = byteBuffer;
        byteBuffer.setLength(0);
        this.mByteBuffer.append(new byte[]{-128, INS, this.mP1, this.mP2});
        byte b = this.mLc;
        if (b != 0 && this.mToken != null) {
            this.mByteBuffer.append(b);
            this.mByteBuffer.append(this.mToken);
        }
        this.mByteBuffer.append((byte) 0);
        LogMgr.log(7, "999");
    }

    @Override // com.felicanetworks.semc.omapi.Command
    Response execute() throws GpException, InterruptedException {
        LogMgr.log(7, "000");
        Response response = new Response();
        try {
            if (new SelectResponse(this.mGpController.select(this.mAid)).isStatusFailedInNotOnline()) {
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
            }
            LogMgr.log(8, "001");
            int length = this.mByteBuffer.getLength();
            byte[] bArr = new byte[length];
            this.mByteBuffer.copy(0, bArr, 0, length);
            LogMgr.log(8, "002");
            LogMgr.logArray(8, bArr);
            try {
                response.setResponse(this.mGpController.transmit(bArr));
                LogMgr.log(7, "999");
                return response.get();
            } catch (GpException e) {
                LogMgr.log(1, "803 : GpException");
                throw e;
            } catch (InterruptedException e2) {
                LogMgr.log(1, "802 : InterruptedException");
                throw e2;
            }
        } catch (GpException e3) {
            LogMgr.log(1, "801 : GpException");
            throw e3;
        } catch (InterruptedException e4) {
            LogMgr.log(1, "800 : InterruptedException");
            throw e4;
        }
    }
}
