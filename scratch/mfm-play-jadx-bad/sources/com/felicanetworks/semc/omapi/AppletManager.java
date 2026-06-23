package com.felicanetworks.semc.omapi;

import android.os.Build;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class AppletManager {
    private static final int APDU_HEADER_LENGTH = 5;
    private static final int LE_LENGTH = 1;
    private static final int MAX_DATA_LENGTH = 255;
    private final ByteBuffer mByteBuffer;
    private final GpController mGpController;

    public AppletManager(GpController gpController) throws UnsupportedOperationException, SemClientException {
        LogMgr.log(6, "000");
        if (gpController == null) {
            LogMgr.log(1, "800 Parameter(s) must not be null.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        if (Build.VERSION.SDK_INT < 28) {
            LogMgr.log(1, "801 API is insufficient. API 28+ is needed.");
            throw new UnsupportedOperationException(ObfuscatedMsgUtil.executionPoint());
        }
        this.mGpController = gpController;
        this.mByteBuffer = new ByteBuffer(261);
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET, INVOKE]}, finally: {[IGET, INVOKE, IF] complete} */
    public AmsdAppletInfo getAmsdAppletInfo() throws GpException, InterruptedException {
        byte[] bArr;
        LogMgr.log(6, "000");
        GetDataCommand getDataCommand = new GetDataCommand();
        getDataCommand.setGpController(this.mGpController);
        try {
            try {
                try {
                    AmsdAppletInfo amsdAppletInfo = new AmsdAppletInfo();
                    getDataCommand.setAid(GpController.AMSD_AID);
                    getDataCommand.setParameters(GetDataCommand.SD_MANAGEMEMT_DATA_P1P2[0], GetDataCommand.SD_MANAGEMEMT_DATA_P1P2[1]);
                    getDataCommand.set(this.mByteBuffer);
                    amsdAppletInfo.setGetDataResponse(getDataCommand.execute());
                    byte[] vseIdOblationMethod = amsdAppletInfo.getVseIdOblationMethod();
                    LogMgr.log(9, "001 VSEID OblationMethod = " + Arrays.toString(vseIdOblationMethod));
                    if (Arrays.equals(vseIdOblationMethod, AmsdAppletInfo.VSEID_OBTAIN_METHOD_01)) {
                        LogMgr.log(9, "001 vseIdOblationMethod = 0001");
                        bArr = GetDataCommand.VSEID01_DATA_P1P2;
                    } else if (Arrays.equals(vseIdOblationMethod, AmsdAppletInfo.VSEID_OBTAIN_METHOD_02)) {
                        LogMgr.log(9, "001 vseIdOblationMethod = 0002");
                        bArr = GetDataCommand.VSEID02_DATA_P1P2;
                    } else {
                        LogMgr.log(9, "001 vseIdOblationMethod is not 0001 nor 0002");
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
                    }
                    getDataCommand.setAid(null);
                    getDataCommand.setParameters(bArr[0], bArr[1]);
                    getDataCommand.set(this.mByteBuffer);
                    amsdAppletInfo.setGetVseIdDataResponse(getDataCommand.execute(), bArr);
                    LogMgr.log(6, "999");
                    return amsdAppletInfo;
                } catch (InterruptedException e) {
                    LogMgr.log(1, "801 InterruptedException");
                    throw e;
                }
            } catch (GpException e2) {
                LogMgr.log(1, "802 GpException");
                throw e2;
            } catch (IllegalArgumentException e3) {
                LogMgr.log(1, "800 Response format error");
                LogMgr.printStackTrace(9, e3);
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
            }
        } finally {
            GpController gpController = this.mGpController;
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }
}
