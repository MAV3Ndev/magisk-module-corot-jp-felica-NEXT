package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class CheckKeyCombinationCommand extends Command {
    private static final byte CMD_CODE = 50;
    private static final byte FELICA_COMMAND_APDU_LE = 0;
    private static final byte[] NODE_CODE_LIST = {-1, -1, FELICA_COMMAND_APDU_LE, FELICA_COMMAND_APDU_LE};
    private static final byte NODE_NUM = 2;
    private final byte[] FELICA_COMMAND_APDU_HEADER = {-127, -62, FELICA_COMMAND_APDU_LE, FELICA_COMMAND_APDU_LE};
    private ByteBuffer mByteBuffer;
    private byte[] mIdm;
    private byte mLen;

    CheckKeyCombinationCommand() {
    }

    void setIdm(byte[] bArr) {
        this.mIdm = bArr;
        this.mLen = (byte) (bArr.length + 2 + 1 + NODE_CODE_LIST.length);
    }

    void set(ByteBuffer byteBuffer) throws GpException {
        if (byteBuffer == null) {
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        this.mByteBuffer = byteBuffer;
        byteBuffer.setLength(0);
        this.mByteBuffer.append(this.FELICA_COMMAND_APDU_HEADER);
        ByteBuffer byteBuffer2 = this.mByteBuffer;
        byte b = this.mLen;
        byteBuffer2.append(new byte[]{b, b});
        this.mByteBuffer.append(CMD_CODE);
        this.mByteBuffer.append(this.mIdm);
        this.mByteBuffer.append(NODE_NUM);
        this.mByteBuffer.append(NODE_CODE_LIST);
        this.mByteBuffer.append(FELICA_COMMAND_APDU_LE);
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
            try {
                response.setResponse(this.mGpController.transmit(bArr));
                LogMgr.log(5, "999");
                return response.get();
            } catch (GpException e) {
                LogMgr.log(1, "801 : GpException");
                throw e;
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : InterruptedException");
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
