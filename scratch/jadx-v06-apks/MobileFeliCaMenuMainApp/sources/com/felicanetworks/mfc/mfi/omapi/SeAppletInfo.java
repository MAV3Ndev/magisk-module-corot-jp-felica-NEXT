package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public class SeAppletInfo extends AppletInfo {
    private GetAppletKeyInfoResponse mGetAppletKeyResponse;
    private SelectResponse mSelectResponse;

    public void setGetAppletKeyInfoResponse(Response response) throws IllegalArgumentException, GpException {
        LogMgr.log(5, "000");
        GetAppletKeyInfoResponse getAppletKeyInfoResponse = new GetAppletKeyInfoResponse(response.getResponse());
        this.mGetAppletKeyResponse = getAppletKeyInfoResponse;
        if (getAppletKeyInfoResponse.isStatusSuccess()) {
            return;
        }
        LogMgr.log(1, "800 : Failed Send GetAppletKeyInfo command.");
        throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(this.mGetAppletKeyResponse.getSw())), null);
    }

    public void setSelectResponse(SelectResponse selectResponse) {
        this.mSelectResponse = selectResponse;
    }

    public byte[] getBinaryVersion() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return null;
        }
        try {
            byte[] binaryVersion = selectResponse.getBinaryVersion();
            LogMgr.log(4, "999");
            return binaryVersion;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getAppletVersion() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return null;
        }
        try {
            byte[] appletVersion = selectResponse.getAppletVersion();
            LogMgr.log(4, "999");
            return appletVersion;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getApplicationLifeCycleState() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return null;
        }
        try {
            byte[] applicationLifeCycleState = selectResponse.getApplicationLifeCycleState();
            LogMgr.log(4, "999");
            return applicationLifeCycleState;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getContactlessActivationLifeState() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return null;
        }
        try {
            byte[] contactlessActivationLifeState = selectResponse.getContactlessActivationLifeState();
            LogMgr.log(4, "999");
            return contactlessActivationLifeState;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public boolean isPersonalized() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return false;
        }
        try {
            boolean zIsPersonalized = selectResponse.isPersonalized();
            LogMgr.log(4, "999");
            return zIsPersonalized;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public boolean isActivated() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return false;
        }
        try {
            boolean zIsActivated = selectResponse.isActivated();
            LogMgr.log(4, "999");
            return zIsActivated;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getPackageKeyVersion() throws GpException {
        LogMgr.log(4, "000");
        GetAppletKeyInfoResponse getAppletKeyInfoResponse = this.mGetAppletKeyResponse;
        if (getAppletKeyInfoResponse == null) {
            return null;
        }
        try {
            byte[] keyVersion = getAppletKeyInfoResponse.getKeyVersion();
            LogMgr.log(4, "999");
            return keyVersion;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getAid() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return null;
        }
        try {
            byte[] aid = selectResponse.getAid();
            LogMgr.log(4, "999");
            return aid;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public byte[] getIdm() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return null;
        }
        try {
            byte[] idm = selectResponse.getIdm();
            LogMgr.log(4, "999");
            return idm;
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }

    public String getCidString() throws GpException {
        LogMgr.log(4, "000");
        SelectResponse selectResponse = this.mSelectResponse;
        if (selectResponse == null) {
            return null;
        }
        boolean z = true;
        try {
            byte[] cid = selectResponse.getCid();
            LogMgr.log(6, "001");
            LogMgr.logArray(6, cid);
            if (cid == null || cid.length != 63) {
                return null;
            }
            boolean z2 = true;
            for (byte b : cid) {
                if (b != 0) {
                    z = false;
                }
                if (b != -1) {
                    z2 = false;
                }
            }
            return z ? MfiClientConst.NO_CID_INSTANCE_KEY : z2 ? MfiClientConst.ALL_FF_CID_INSTANCE_KEY : new String(cid, Charset.forName("US-ASCII"));
        } catch (IllegalArgumentException e) {
            LogMgr.log(1, "800 : Response format error");
            LogMgr.printStackTrace(7, e);
            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), null);
        }
    }
}
