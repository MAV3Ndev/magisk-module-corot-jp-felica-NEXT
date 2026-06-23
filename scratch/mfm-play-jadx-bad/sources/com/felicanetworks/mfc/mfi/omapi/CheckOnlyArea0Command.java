package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class CheckOnlyArea0Command extends Command {
    private static final byte CMD_CODE = 26;
    private static final byte FELICA_COMMAND_APDU_LE = 0;
    private final byte[] FELICA_COMMAND_APDU_HEADER = {-127, -62, 0, 0};
    private ByteBuffer mByteBuffer;
    private byte[] mIdm;
    private byte mLen;
    private static final byte[] AREA_CODE = {0, 0};
    private static final byte[] PACKET_INDEX = {1, 0};

    CheckOnlyArea0Command() {
    }

    void setIdm(byte[] idm) {
        this.mIdm = idm;
        this.mLen = (byte) (idm.length + 2 + AREA_CODE.length + PACKET_INDEX.length);
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
        this.mByteBuffer.append((byte) 26);
        this.mByteBuffer.append(this.mIdm);
        this.mByteBuffer.append(AREA_CODE);
        this.mByteBuffer.append(PACKET_INDEX);
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
