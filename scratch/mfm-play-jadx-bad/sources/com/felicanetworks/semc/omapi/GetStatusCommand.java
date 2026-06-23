package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.google.common.primitives.SignedBytes;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class GetStatusCommand extends Command {
    private static final byte CLA = -128;
    public static final byte[] GET_FIRST_OCCURRENCE_P1P2 = {SignedBytes.MAX_POWER_OF_TWO, 0};
    public static final byte[] GET_NEXT_OCCURRENCE_P1P2 = {SignedBytes.MAX_POWER_OF_TWO, 1};
    private static final byte INS = -14;
    private static final int SW_LENGTH = 2;
    private static final int SW_MORE_DATA = 25360;
    private static final byte mLe = 0;
    private ByteBuffer mByteBuffer;
    private byte[] mFirstCommand;
    private byte[] mNextCommand;
    private byte mP1 = SignedBytes.MAX_POWER_OF_TWO;
    private byte mP2 = 0;
    private byte mLc = 0;
    private byte[] mToken = null;

    GetStatusCommand() {
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

    void set(ByteBuffer byteBuffer) throws GpException {
        LogMgr.log(6, "000");
        if (byteBuffer == null) {
            throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), null);
        }
        this.mByteBuffer = byteBuffer;
        createFirstCommand();
        createNextCommand();
        LogMgr.log(6, "000");
    }

    private void createFirstCommand() {
        LogMgr.log(6, "000");
        this.mByteBuffer.setLength(0);
        LogMgr.log(6, "000");
        this.mByteBuffer.append(new byte[]{-128, INS, this.mP1, this.mP2});
        byte b = this.mLc;
        if (b != 0 && this.mToken != null) {
            this.mByteBuffer.append(b);
            this.mByteBuffer.append(this.mToken);
        }
        this.mByteBuffer.append((byte) 0);
        byte[] bArr = new byte[this.mByteBuffer.getLength()];
        this.mFirstCommand = bArr;
        this.mByteBuffer.copy(0, bArr, 0, bArr.length);
        LogMgr.log(6, "999");
    }

    private void createNextCommand() {
        LogMgr.log(6, "000");
        this.mByteBuffer.setLength(0);
        byte[] bArr = GET_NEXT_OCCURRENCE_P1P2;
        this.mByteBuffer.append(new byte[]{-128, INS, bArr[0], bArr[1]});
        byte b = this.mLc;
        if (b != 0 && this.mToken != null) {
            this.mByteBuffer.append(b);
            this.mByteBuffer.append(this.mToken);
        }
        this.mByteBuffer.append((byte) 0);
        byte[] bArr2 = new byte[this.mByteBuffer.getLength()];
        this.mNextCommand = bArr2;
        this.mByteBuffer.copy(0, bArr2, 0, bArr2.length);
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.omapi.Command
    Response execute() throws GpException, InterruptedException {
        LogMgr.log(7, "000");
        ArrayList arrayList = new ArrayList();
        Response response = new Response();
        try {
            if (new SelectResponse(this.mGpController.select(this.mAid)).isStatusFailedInNotOnline()) {
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
            }
            LogMgr.log(8, "001");
            this.mByteBuffer.setLength(0);
            LogMgr.log(8, "002");
            try {
                byte[] bArrTransmit = this.mGpController.transmit(this.mFirstCommand);
                ByteBuffer byteBuffer = new ByteBuffer(bArrTransmit.length);
                byteBuffer.append(bArrTransmit);
                LogMgr.log(8, "003");
                int statusWord = getStatusWord(byteBuffer);
                if (statusWord == SW_MORE_DATA) {
                    while (statusWord == SW_MORE_DATA) {
                        LogMgr.log(8, "004");
                        int length = byteBuffer.getLength() - 2;
                        byte[] bArr = new byte[length];
                        byteBuffer.copy(0, bArr, 0, length);
                        arrayList.add(bArr);
                        try {
                            byte[] bArrTransmit2 = this.mGpController.transmit(this.mNextCommand);
                            byteBuffer = new ByteBuffer(bArrTransmit2.length);
                            byteBuffer.append(bArrTransmit2);
                            statusWord = getStatusWord(byteBuffer);
                        } catch (GpException | InterruptedException e) {
                            LogMgr.printStackTrace(8, e);
                            throw e;
                        }
                    }
                }
                arrayList.add(byteBuffer.getBytes());
                Iterator it = arrayList.iterator();
                int length2 = 0;
                while (it.hasNext()) {
                    length2 += ((byte[]) it.next()).length;
                }
                ByteBuffer byteBuffer2 = new ByteBuffer(length2);
                Iterator it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    byteBuffer2.append((byte[]) it2.next());
                }
                byte[] bArr2 = new byte[length2];
                byteBuffer2.copy(0, bArr2, 0, length2);
                response.setResponse(bArr2);
                LogMgr.log(7, "999");
                return response.get();
            } catch (GpException | InterruptedException e2) {
                LogMgr.printStackTrace(8, e2);
                throw e2;
            }
        } catch (GpException | InterruptedException e3) {
            LogMgr.printStackTrace(8, e3);
            throw e3;
        }
    }

    private int getStatusWord(ByteBuffer byteBuffer) {
        LogMgr.log(6, "000");
        if (byteBuffer == null || byteBuffer.getLength() < 2) {
            LogMgr.log(6, "998");
            return 0;
        }
        int i = (byteBuffer.get(byteBuffer.getLength() - 1) & 255) | ((byteBuffer.get(byteBuffer.getLength() - 2) & 255) << 8);
        LogMgr.log(6, "999 swCode=[" + Integer.toHexString(i) + "]");
        return i;
    }
}
