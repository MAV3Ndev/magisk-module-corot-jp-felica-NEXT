package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public abstract class SdAppletInfo extends AppletInfo {
    private Response mGetAppletKeyInfoResponse;
    private GetDataResponse mGetSequenceCountResponse;

    void setGetAppletKeyInfoResponse(Response response) throws GpException {
        LogMgr.log(5, "000");
        this.mGetAppletKeyInfoResponse = response;
        GetDataResponse getDataResponse = new GetDataResponse(response.getResponse(), (byte) 0, (byte) -32);
        if (!getDataResponse.isStatusSuccess()) {
            LogMgr.log(1, "Failed send GetData Key Information Template Command");
            throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(getDataResponse.getSw())), null);
        }
        LogMgr.log(5, "999");
    }

    void setSequenceCounteResponse(Response response) throws GpException {
        LogMgr.log(5, "000");
        GetDataResponse getDataResponse = new GetDataResponse(response.getResponse(), (byte) 0, (byte) -63);
        this.mGetSequenceCountResponse = getDataResponse;
        if (!getDataResponse.isStatusSuccess()) {
            LogMgr.log(1, "Failed send GetData Command");
            throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(this.mGetSequenceCountResponse.getSw())), null);
        }
        LogMgr.log(5, "999");
    }

    public byte[] getKeyVersion() {
        LogMgr.log(4, "000");
        return this.mGetAppletKeyInfoResponse.getResponse();
    }

    public byte[] getSequenceCount() throws GpException {
        LogMgr.log(4, "000");
        try {
            return this.mGetSequenceCountResponse.getParameterData();
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "801 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }
}
