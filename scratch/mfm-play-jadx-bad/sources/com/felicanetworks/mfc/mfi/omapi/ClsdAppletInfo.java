package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class ClsdAppletInfo extends SdAppletInfo {
    private static final int GETSDKEYCHANGEDMODEL_DATA_LENGTH = 4;
    private static final int GETVSEIDOBLATION_DATA_INDEX1 = 2;
    private static final int GETVSEIDOBLATION_DATA_INDEX2 = 4;
    private static final int GETVSEIDOBLATION_DATA_LENGTH = 4;
    private static final int GETVSEID_DEFAULT_DATA_INDEX1 = 26;
    private static final int GETVSEID_DEFAULT_DATA_INDEX2 = 34;
    private static final int GETVSEID_DEFAULT_DATA_INDEX3 = 10;
    private static final int GETVSEID_DEFAULT_DATA_INDEX4 = 18;
    private static final int GETVSEID_DEFAULT_DATA_LENGTH = 34;
    private static final int INDEX_0 = 0;
    private static final int SHIPMENTKEY_DATA_INDEX1 = 5;
    private static final int SHIPMENTKEY_DATA_INDEX2 = 7;
    private static final int SHIPMENTKEY_DATA_LENGTH = 7;
    private GetDataResponse mGetDataCommandResponse;
    private GetDataResponse mGetVseIdDataCommandResponse;
    private static final byte[] SDMANAGEMEMT_DATA_COMMAND_P1P2 = {0, 102};
    public static final byte[] VSEID_OBTAIN_METHOD_01 = {0, 1};
    public static final byte[] VSEID_OBTAIN_METHOD_02 = {0, 2};

    public void setGetDataResponse(Response response) throws GpException {
        LogMgr.log(4, "000");
        byte[] response2 = response.getResponse();
        byte[] bArr = SDMANAGEMEMT_DATA_COMMAND_P1P2;
        GetDataResponse getDataResponse = new GetDataResponse(response2, bArr[0], bArr[1]);
        this.mGetDataCommandResponse = getDataResponse;
        if (!getDataResponse.isStatusSuccess()) {
            LogMgr.log(1, "800 : Failed GetData command.");
            throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(this.mGetDataCommandResponse.getSw())), null);
        }
        LogMgr.log(4, "999");
    }

    public void setGetVseIdDataResponse(Response response, byte[] p1p2) throws GpException {
        LogMgr.log(4, "000");
        GetDataResponse getDataResponse = new GetDataResponse(response.getResponse(), p1p2[0], p1p2[1]);
        this.mGetVseIdDataCommandResponse = getDataResponse;
        if (!getDataResponse.isStatusSuccess()) {
            LogMgr.log(1, "800 : Failed GetData command.");
            throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(this.mGetVseIdDataCommandResponse.getSw())), null);
        }
        LogMgr.log(4, "999");
    }

    public byte[] getShipmentKeyDerivationVendor() throws GpException {
        LogMgr.log(4, "000");
        try {
            byte[] managementData = this.mGetDataCommandResponse.getManagementData();
            if (managementData != null && managementData.length > 7) {
                return Arrays.copyOfRange(managementData, 5, 7);
            }
            LogMgr.log(4, "999");
            return null;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getVseId() throws GpException {
        LogMgr.log(4, "000");
        if (this.mGetVseIdDataCommandResponse == null) {
            LogMgr.log(1, "800 : Failed to get VSEID");
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        }
        try {
            byte[] vseIdOblationMethod = getVseIdOblationMethod();
            if (Arrays.equals(vseIdOblationMethod, VSEID_OBTAIN_METHOD_01)) {
                byte[] parameterData = this.mGetVseIdDataCommandResponse.getParameterData();
                if (parameterData == null || parameterData.length <= 34) {
                    return null;
                }
                byte[] bArrCopyOfRange = Arrays.copyOfRange(parameterData, 26, 34);
                byte[] bArrCopyOfRange2 = Arrays.copyOfRange(parameterData, 10, 18);
                byte[] bArr = new byte[bArrCopyOfRange.length + bArrCopyOfRange2.length];
                System.arraycopy(bArrCopyOfRange, 0, bArr, 0, bArrCopyOfRange.length);
                System.arraycopy(bArrCopyOfRange2, 0, bArr, bArrCopyOfRange.length, bArrCopyOfRange2.length);
                return bArr;
            }
            if (Arrays.equals(vseIdOblationMethod, VSEID_OBTAIN_METHOD_02)) {
                return this.mGetVseIdDataCommandResponse.getParameterData();
            }
            LogMgr.log(1, "800 : Failed to get VSEID");
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "801 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getVseIdOblationMethod() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        byte[] managementData = this.mGetDataCommandResponse.getManagementData();
        if (managementData == null || managementData.length <= 4) {
            return null;
        }
        return Arrays.copyOfRange(managementData, 2, 4);
    }

    public byte[] getSdManagementDataTsmSetting() throws GpException {
        LogMgr.log(4, "000");
        try {
            byte[] managementData = this.mGetDataCommandResponse.getManagementData();
            if (managementData != null) {
                return managementData;
            }
            return null;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte getSdKeyChangedModel() throws GpException {
        LogMgr.log(4, "000");
        try {
            byte[] managementData = this.mGetDataCommandResponse.getManagementData();
            if (managementData != null && managementData.length > 4) {
                return managementData[4];
            }
            LogMgr.log(4, "999");
            return (byte) 0;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }
}
