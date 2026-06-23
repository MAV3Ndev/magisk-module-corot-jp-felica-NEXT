package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class GetStatusCommand extends Command {
    private static final byte CLA = -128;
    public static final byte[] GET_FIRST_OCCURRENCE_P1P2 = {64, 0};
    public static final byte[] GET_NEXT_OCCURRENCE_P1P2 = {64, 1};
    private static final byte INS = -14;
    private static final int MAX_RESPONSE_LENGTH = 2048;
    private static final int SW_LENGTH = 2;
    private static final int SW_MORE_DATA = 25360;
    private ByteBuffer mByteBuffer;
    private byte[] mFirstCommand;
    private byte[] mNextCommand;
    private byte mP1 = 64;
    private byte mP2 = 0;
    private byte mLc = 0;
    private byte[] mToken = null;
    private byte mLe = 0;

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
        if (byteBuffer == null) {
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        this.mByteBuffer = byteBuffer;
        createFirstCommand();
        createNextCommand();
    }

    private void createFirstCommand() {
        this.mByteBuffer.setLength(0);
        this.mByteBuffer.append(new byte[]{CLA, INS, this.mP1, this.mP2});
        byte b = this.mLc;
        if (b != 0 && this.mToken != null) {
            this.mByteBuffer.append(b);
            this.mByteBuffer.append(this.mToken);
        }
        this.mByteBuffer.append(this.mLe);
        byte[] bArr = new byte[this.mByteBuffer.getLength()];
        this.mFirstCommand = bArr;
        this.mByteBuffer.copy(0, bArr, 0, bArr.length);
    }

    private void createNextCommand() {
        this.mByteBuffer.setLength(0);
        byte[] bArr = GET_NEXT_OCCURRENCE_P1P2;
        this.mByteBuffer.append(new byte[]{CLA, INS, bArr[0], bArr[1]});
        byte b = this.mLc;
        if (b != 0 && this.mToken != null) {
            this.mByteBuffer.append(b);
            this.mByteBuffer.append(this.mToken);
        }
        this.mByteBuffer.append(this.mLe);
        byte[] bArr2 = new byte[this.mByteBuffer.getLength()];
        this.mNextCommand = bArr2;
        this.mByteBuffer.copy(0, bArr2, 0, bArr2.length);
    }

    @Override // com.felicanetworks.mfc.mfi.omapi.Command
    Response execute() throws InterruptedException, GpException {
        LogMgr.log(5, "000");
        ArrayList arrayList = new ArrayList();
        Response response = new Response();
        try {
            this.mGpController.select(this.mAid);
            LogMgr.log(6, "001");
            this.mByteBuffer.setLength(0);
            LogMgr.log(6, "002");
            try {
                byte[] bArrTransmit = this.mGpController.transmit(this.mFirstCommand);
                ByteBuffer byteBuffer = new ByteBuffer(bArrTransmit.length);
                byteBuffer.append(bArrTransmit);
                LogMgr.log(6, "003");
                int statusWord = getStatusWord(byteBuffer);
                if (statusWord == SW_MORE_DATA) {
                    while (statusWord == SW_MORE_DATA) {
                        LogMgr.log(6, "004");
                        int length = byteBuffer.getLength() - 2;
                        byte[] bArr = new byte[length];
                        byteBuffer.copy(0, bArr, 0, length);
                        arrayList.add(bArr);
                        try {
                            byte[] bArrTransmit2 = this.mGpController.transmit(this.mNextCommand);
                            byteBuffer = new ByteBuffer(bArrTransmit2.length);
                            byteBuffer.append(bArrTransmit2);
                            statusWord = getStatusWord(byteBuffer);
                        } catch (GpException e) {
                            LogMgr.printStackTrace(2, e);
                            throw e;
                        } catch (InterruptedException e2) {
                            LogMgr.printStackTrace(2, e2);
                            throw e2;
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
                LogMgr.log(5, "999");
                return response.get();
            } catch (GpException e3) {
                LogMgr.printStackTrace(1, e3);
                throw e3;
            } catch (InterruptedException e4) {
                LogMgr.printStackTrace(1, e4);
                throw e4;
            }
        } catch (GpException e5) {
            LogMgr.printStackTrace(1, e5);
            throw e5;
        } catch (InterruptedException e6) {
            LogMgr.printStackTrace(1, e6);
            throw e6;
        }
    }

    private int getStatusWord(ByteBuffer byteBuffer) {
        if (byteBuffer == null || byteBuffer.getLength() < 2) {
            return 0;
        }
        return (byteBuffer.get(byteBuffer.getLength() - 1) & 255) | ((byteBuffer.get(byteBuffer.getLength() - 2) & 255) << 8);
    }
}
