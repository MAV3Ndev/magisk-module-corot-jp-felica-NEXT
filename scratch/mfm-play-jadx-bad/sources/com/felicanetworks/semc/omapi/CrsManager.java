package com.felicanetworks.semc.omapi;

import android.os.Build;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class CrsManager {
    private static final byte ACTIVATED_MASK = 1;
    private static final int APDU_HEADER_LENGTH = 5;
    private static final byte APPLICATION_AID_TAG = 79;
    private static final int LE_LENGTH = 1;
    private static final int MAX_AID_LENGTH = 16;
    private static final int MAX_DATA_LENGTH = 255;
    private static final int MIN_AID_LENGTH = 5;
    private static final byte NON_ACTIVATABLE_MASK = -128;
    private final byte[] TAGLIST = {92, 3, APPLICATION_AID_TAG, -97, 112};
    private final ByteBuffer mByteBuffer;
    private final GpController mGpController;

    public CrsManager(GpController gpController) throws UnsupportedOperationException, IllegalArgumentException {
        LogMgr.log(6, "000");
        if (gpController == null) {
            LogMgr.log(1, "800 Parameter(s) must not be null.");
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        if (Build.VERSION.SDK_INT < 28) {
            LogMgr.log(1, "801 API is insufficient. API 28+ is needed.");
            throw new UnsupportedOperationException(ObfuscatedMsgUtil.executionPoint());
        }
        this.mGpController = gpController;
        this.mByteBuffer = new ByteBuffer(261);
        LogMgr.log(6, "999");
    }

    public int getAppletStatus(byte[] bArr) throws GpException, InterruptedException {
        LogMgr.log(6, "000");
        GetStatusCommand getStatusCommand = new GetStatusCommand();
        try {
            getStatusCommand.setGpController(this.mGpController);
            ByteBuffer byteBuffer = new ByteBuffer(261);
            byteBuffer.setLength(0);
            byte[] aidDataToken = getAidDataToken(bArr);
            if (aidDataToken != null) {
                byteBuffer.append(aidDataToken);
            }
            byteBuffer.append(this.TAGLIST);
            int length = byteBuffer.getLength();
            byte[] bArr2 = new byte[length];
            byteBuffer.copy(0, bArr2, 0, byteBuffer.getLength());
            getStatusCommand.setParameters(GetStatusCommand.GET_FIRST_OCCURRENCE_P1P2[0], GetStatusCommand.GET_FIRST_OCCURRENCE_P1P2[1], (byte) length, bArr2);
            getStatusCommand.setAid(GpController.CRS_AID);
            getStatusCommand.set(this.mByteBuffer);
            GetStatusResponse getStatusResponse = new GetStatusResponse(getStatusCommand.execute().getResponse());
            if (!getStatusResponse.isStatusSuccess()) {
                LogMgr.log(1, "800 : Failed Send SetStatus command.");
                throw new GpException(200, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getStatusResponse.getSw())), null);
            }
            List<byte[]> applicationRelatedDataList = getStatusResponse.getApplicationRelatedDataList();
            if (applicationRelatedDataList.size() > 1) {
                LogMgr.log(1, "801 : appDataList length(=" + applicationRelatedDataList.size() + ") is unexpected, not 1.");
                throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), null);
            }
            Byte appletStatus = getStatusResponse.getAppletStatus(bArr);
            if (appletStatus == null) {
                LogMgr.log(1, "802 : Fail to get Contactless Activation State.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
            }
            int intContactlessActivationState = toIntContactlessActivationState(appletStatus);
            LogMgr.log(6, "999 ret:" + intContactlessActivationState);
            return intContactlessActivationState;
        } catch (GpException e) {
            LogMgr.log(6, "803 Failed to transmit 1st GET STATUS command.");
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(1, "804 : Response format error");
            LogMgr.printStackTrace(9, e2);
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public void activateApplet(byte[] bArr) throws GpException, InterruptedException {
        LogMgr.log(6, "000");
        updateActiveStateOnApplet(bArr, true);
        LogMgr.log(6, "999");
    }

    public void deactivateApplet(byte[] bArr) throws GpException, InterruptedException {
        LogMgr.log(6, "000");
        updateActiveStateOnApplet(bArr, false);
        LogMgr.log(6, "999");
    }

    private byte[] getAidDataToken(byte[] bArr) {
        byte[] bArr2;
        LogMgr.log(6, "000");
        ByteBuffer byteBuffer = new ByteBuffer(261);
        if (bArr == null || bArr.length <= 5 || bArr.length > 16) {
            bArr2 = null;
        } else {
            LogMgr.log(8, "001");
            byteBuffer.setLength(0);
            byteBuffer.append(APPLICATION_AID_TAG);
            byteBuffer.append((byte) bArr.length);
            byteBuffer.append(bArr);
            int length = byteBuffer.getLength();
            bArr2 = new byte[length];
            byteBuffer.copy(0, bArr2, 0, length);
        }
        LogMgr.log(6, "999");
        return bArr2;
    }

    private void updateActiveStateOnApplet(byte[] bArr, boolean z) throws GpException, InterruptedException {
        int i;
        LogMgr.log(6, "000 isActive:" + z);
        if (bArr == null) {
            LogMgr.log(1, "800 : AID is null.");
            throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), null);
        }
        SetStatusCommand setStatusCommand = new SetStatusCommand();
        setStatusCommand.setGpController(this.mGpController);
        setStatusCommand.setAid(GpController.CRS_AID);
        if (z) {
            setStatusCommand.setParameters(SetStatusCommand.ACTIVATE_P1P2[0], SetStatusCommand.ACTIVATE_P1P2[1]);
        } else {
            setStatusCommand.setParameters(SetStatusCommand.DEACTIVATE_P1P2[0], SetStatusCommand.DEACTIVATE_P1P2[1]);
        }
        byte[] aidDataToken = getAidDataToken(bArr);
        if (aidDataToken != null) {
            setStatusCommand.setDataToken(aidDataToken);
        }
        setStatusCommand.set(this.mByteBuffer);
        try {
            SetStatusResponse setStatusResponse = new SetStatusResponse(setStatusCommand.execute().getResponse());
            if (!setStatusResponse.isStatusSuccess()) {
                LogMgr.log(1, "801 : Failed Send SetStatus command.");
                if (z && setStatusResponse.isStatusActivationConflict()) {
                    LogMgr.log(1, "802 : Operation failed due to Activate Conflict.");
                    i = 204;
                } else {
                    i = 200;
                }
                throw new GpException(i, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(setStatusResponse.getSw())), null);
            }
            LogMgr.log(6, "999");
        } catch (GpException e) {
            LogMgr.log(1, "806 : GpException");
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(1, "804 : Response format error");
            LogMgr.printStackTrace(9, e2);
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        } catch (InterruptedException e3) {
            LogMgr.log(1, "805 : InterruptedException");
            throw e3;
        }
    }

    private int toIntContactlessActivationState(Byte b) {
        LogMgr.log(8, "000 status:" + b);
        int i = 1;
        if ((b.byteValue() & (-128)) != 0) {
            i = (b.byteValue() & 1) != 0 ? -1 : 128;
        } else if ((b.byteValue() & 1) == 0) {
            i = 0;
        }
        LogMgr.log(8, "999 ret:" + i);
        return i;
    }
}
