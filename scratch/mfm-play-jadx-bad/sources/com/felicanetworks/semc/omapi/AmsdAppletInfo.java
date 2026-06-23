package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class AmsdAppletInfo {
    private static final int GET_VSEID_DEFAULT_DATA_INDEX1 = 26;
    private static final int GET_VSEID_DEFAULT_DATA_INDEX2 = 34;
    private static final int GET_VSEID_DEFAULT_DATA_INDEX3 = 10;
    private static final int GET_VSEID_DEFAULT_DATA_INDEX4 = 18;
    private static final int GET_VSEID_DEFAULT_DATA_LENGTH = 34;
    private static final int GET_VSEID_OBLATION_DATA_INDEX1 = 2;
    private static final int GET_VSEID_OBLATION_DATA_INDEX2 = 4;
    private static final int GET_VSEID_OBLATION_DATA_LENGTH = 4;
    private static final int INDEX_0 = 0;
    private static final int SHIPMENT_KEY_DATA_INDEX1 = 5;
    private static final int SHIPMENT_KEY_DATA_INDEX2 = 7;
    private static final int SHIPMENT_KEY_DATA_LENGTH = 7;
    private GetDataResponse mGetDataCommandResponse;
    private GetDataResponse mGetVseIdDataCommandResponse;
    private static final byte[] SD_MANAGEMENT_DATA_COMMAND_P1P2 = {0, 102};
    public static final byte[] VSEID_OBTAIN_METHOD_01 = {0, 1};
    public static final byte[] VSEID_OBTAIN_METHOD_02 = {0, 2};

    public void setGetDataResponse(Response response) throws GpException {
        LogMgr.log(6, "000");
        byte[] response2 = response.getResponse();
        byte[] bArr = SD_MANAGEMENT_DATA_COMMAND_P1P2;
        GetDataResponse getDataResponse = new GetDataResponse(response2, bArr[0], bArr[1]);
        this.mGetDataCommandResponse = getDataResponse;
        if (!getDataResponse.isStatusSuccess()) {
            LogMgr.log(1, "800 : Failed GetData command.");
            throw new GpException(200, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(this.mGetDataCommandResponse.getSw())), null);
        }
        LogMgr.log(6, "999");
    }

    public void setGetVseIdDataResponse(Response response, byte[] bArr) throws GpException {
        LogMgr.log(6, "000");
        GetDataResponse getDataResponse = new GetDataResponse(response.getResponse(), bArr[0], bArr[1]);
        this.mGetVseIdDataCommandResponse = getDataResponse;
        if (!getDataResponse.isStatusSuccess()) {
            LogMgr.log(1, "800 : Failed GetData command.");
            throw new GpException(200, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(this.mGetVseIdDataCommandResponse.getSw())), null);
        }
        LogMgr.log(6, "999");
    }

    public byte[] getShipmentKeyDerivationVendor() throws GpException {
        LogMgr.log(6, "000");
        try {
            byte[] managementData = this.mGetDataCommandResponse.getManagementData();
            if (managementData != null && managementData.length > 7) {
                LogMgr.log(6, "998");
                return Arrays.copyOfRange(managementData, 5, 7);
            }
            LogMgr.log(6, "999");
            return null;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(9, e);
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getVseId() throws GpException {
        LogMgr.log(6, "000");
        if (this.mGetVseIdDataCommandResponse == null) {
            LogMgr.log(1, "800 : Failed to get VSEID");
            throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), null);
        }
        try {
            byte[] vseIdOblationMethod = getVseIdOblationMethod();
            if (Arrays.equals(vseIdOblationMethod, VSEID_OBTAIN_METHOD_01)) {
                byte[] parameterData = this.mGetVseIdDataCommandResponse.getParameterData();
                if (parameterData != null && parameterData.length > 34) {
                    byte[] bArrCopyOfRange = Arrays.copyOfRange(parameterData, 26, 34);
                    byte[] bArrCopyOfRange2 = Arrays.copyOfRange(parameterData, 10, 18);
                    byte[] bArr = new byte[bArrCopyOfRange.length + bArrCopyOfRange2.length];
                    System.arraycopy(bArrCopyOfRange, 0, bArr, 0, bArrCopyOfRange.length);
                    System.arraycopy(bArrCopyOfRange2, 0, bArr, bArrCopyOfRange.length, bArrCopyOfRange2.length);
                    LogMgr.log(6, "997");
                    return bArr;
                }
                LogMgr.log(6, "998");
                return null;
            }
            if (Arrays.equals(vseIdOblationMethod, VSEID_OBTAIN_METHOD_02)) {
                LogMgr.log(6, "999");
                return this.mGetVseIdDataCommandResponse.getParameterData();
            }
            LogMgr.log(1, "801 : Failed to get VSEID");
            throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), null);
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "802 : Response format error");
            LogMgr.printStackTrace(9, e);
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getVseIdOblationMethod() throws IllegalArgumentException {
        LogMgr.log(6, "000");
        byte[] managementData = this.mGetDataCommandResponse.getManagementData();
        if (managementData != null && managementData.length > 4) {
            LogMgr.log(6, "998");
            return Arrays.copyOfRange(managementData, 2, 4);
        }
        LogMgr.log(6, "999");
        return null;
    }
}
