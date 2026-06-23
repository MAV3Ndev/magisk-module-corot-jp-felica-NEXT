package com.felicanetworks.mfc.mfi;

import android.content.Context;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class MfiFelicaWrapper {
    public final MfiChipHolder mChipHolder;
    private boolean mIsOpened = false;

    public MfiFelicaWrapper(MfiChipHolder chipHolder) {
        this.mChipHolder = chipHolder;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void open() throws MfiFelicaException {
        int i;
        boolean z = true;
        int i2 = 200;
        String strExExecutionPoint = null;
        try {
            this.mChipHolder.open();
            setOpened(true);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                i = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_OPEN.msg;
                } else {
                    i = 200;
                }
                if (200 == i) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i2 = i;
                z = false;
            } else if (8 == id) {
                i = 55;
                if (55 != type) {
                }
                if (200 == i) {
                }
                i2 = i;
                z = false;
            } else {
                if (1 == id && 8 == type && FelicaException.NFC_RW_USED_MESSAGE.equals(e.getMessage())) {
                    i = 8;
                }
                if (200 == i) {
                }
                i2 = i;
                z = false;
            }
        } catch (MfiFelicaException e2) {
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s:%s", "701", e3.getClass().getSimpleName(), e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e3);
            z = false;
        }
        if (!z) {
            throw new MfiFelicaException(i2, strExExecutionPoint);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0059  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void select(int systemCode) throws MfiFelicaException {
        boolean z = false;
        int i = 200;
        String strExExecutionPoint = null;
        try {
            LogMgr.log(3, "001 [access-felica] Felica.select(int) in");
            this.mChipHolder.getFelica().select(systemCode);
            LogMgr.log(3, "002 [access-felica] Felica.select(int) out");
            z = true;
        } catch (FelicaException e) {
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i2 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_SELECT.msg;
                } else {
                    i2 = 200;
                }
                if (200 == i2) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i = i2;
            }
        } catch (MfiFelicaException e2) {
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s:%s", "701", e3.getClass().getSimpleName(), e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e3);
        }
        if (!z) {
            throw new MfiFelicaException(i, strExExecutionPoint);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void selectWritableSystemWithCid(int systemCode, String cid) throws MfiFelicaException, FelicaException {
        int i;
        boolean z = false;
        int i2 = 200;
        String strExExecutionPoint = null;
        try {
            LogMgr.log(3, "001 [access-felica] Felica.select(int, String) in");
            this.mChipHolder.getFelica().select(systemCode, cid);
            LogMgr.log(3, "002 [access-felica] Felica.select(int, String) out");
            z = true;
        } catch (FelicaException e) {
            LogMgr.log(2, "700 FelicaException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                i = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_SELECT_CID.msg;
                    if (200 == i) {
                        strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                    }
                    i2 = i;
                } else {
                    if (7 == type) {
                        LogMgr.log(2, "702 select timeout occurred");
                        throw e;
                    }
                    i = 200;
                    if (200 == i) {
                    }
                    i2 = i;
                }
            } else {
                i = 200;
                if (200 == i) {
                }
                i2 = i;
            }
        } catch (MfiFelicaException e2) {
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "701 " + e3.getClass().getSimpleName() + ":" + e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e3);
        }
        if (!z) {
            throw new MfiFelicaException(i2, strExExecutionPoint);
        }
    }

    public byte[] getIDm() throws MfiFelicaException {
        byte[] iDm;
        Exception e;
        FelicaException e2;
        boolean z = false;
        String strExExecutionPoint = null;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getIDm() in");
                iDm = this.mChipHolder.getFelica().getIDm();
            } catch (MfiFelicaException e3) {
                throw e3;
            }
        } catch (FelicaException e4) {
            iDm = null;
            e2 = e4;
        } catch (Exception e5) {
            iDm = null;
            e = e5;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getIDm() out");
            z = true;
        } catch (FelicaException e6) {
            e2 = e6;
            LogMgr.log(2, "%s FelicaException:%s", "700", e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e2);
        } catch (Exception e7) {
            e = e7;
            LogMgr.log(2, "%s %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
        }
        if (z) {
            return iDm;
        }
        throw new MfiFelicaException(200, strExExecutionPoint);
    }

    public byte[] getICCode() throws MfiFelicaException {
        byte[] iCCode;
        Exception e;
        FelicaException e2;
        boolean z = false;
        String strExExecutionPoint = null;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getICCode() in");
                iCCode = this.mChipHolder.getFelica().getICCode();
            } catch (MfiFelicaException e3) {
                throw e3;
            }
        } catch (FelicaException e4) {
            iCCode = null;
            e2 = e4;
        } catch (Exception e5) {
            iCCode = null;
            e = e5;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getICCode() out");
            z = true;
        } catch (FelicaException e6) {
            e2 = e6;
            LogMgr.log(2, "%s FelicaException:%s", "700", e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e2);
        } catch (Exception e7) {
            e = e7;
            LogMgr.log(2, "%s %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
        }
        if (z) {
            return iCCode;
        }
        throw new MfiFelicaException(200, strExExecutionPoint);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] getContainerId() throws MfiFelicaException {
        byte[] containerId;
        boolean z = false;
        byte[] bArr = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        int i = 200;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getContainerId() in");
                containerId = this.mChipHolder.getFelica().getContainerId();
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            containerId = null;
        } catch (Exception e3) {
            e = e3;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getContainerId() out");
            z = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(2, "700 FelicaException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i2 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_CONTAINER_ID.msg;
                } else {
                    i2 = 200;
                }
                if (200 == i2) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i = i2;
            }
        } catch (Exception e5) {
            e = e5;
            bArr = containerId;
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            containerId = bArr;
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
        }
        if (z) {
            return containerId;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int[] getSystemCodeList() throws MfiFelicaException {
        int[] systemCodeList;
        boolean z = false;
        int[] iArr = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        int i = 200;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getSystemCodeList() in");
                systemCodeList = this.mChipHolder.getFelica().getSystemCodeList();
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            systemCodeList = null;
        } catch (Exception e3) {
            e = e3;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getSystemCodeList() out");
            z = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(2, "700 FelicaException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i2 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_SYSTEM_CODE_LIST.msg;
                } else {
                    i2 = 200;
                }
                if (200 == i2) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i = i2;
            }
        } catch (Exception e5) {
            e = e5;
            iArr = systemCodeList;
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            systemCodeList = iArr;
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
        }
        if (z) {
            return systemCodeList;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    public byte[] getContainerIssueInformationWithCheckIssued() throws MfiFelicaException {
        return getContainerIssueInformation(true, false);
    }

    public byte[] getContainerIssueInformationWithCheckIssuedDetail() throws MfiFelicaException {
        return getContainerIssueInformation(true, true);
    }

    public byte[] getContainerIssueInformation() throws MfiFelicaException {
        return getContainerIssueInformation(false, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0067  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] getContainerIssueInformation(boolean z, boolean z2) throws MfiFelicaException {
        byte[] containerIssueInformation;
        boolean z3 = false;
        byte[] bArr = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        int i = 200;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getContainerIssueInformation() in");
                containerIssueInformation = this.mChipHolder.getFelica().getContainerIssueInformation();
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            containerIssueInformation = null;
        } catch (Exception e3) {
            e = e3;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getContainerIssueInformation() out");
            if (z) {
                checkContainerIssued(containerIssueInformation, z2);
            }
            z3 = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i2 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_CONTAINER_ISSUE_INFO.msg;
                } else {
                    i2 = 200;
                }
                if (200 == i2) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i = i2;
            }
        } catch (Exception e5) {
            e = e5;
            bArr = containerIssueInformation;
            LogMgr.log(2, "%s %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            containerIssueInformation = bArr;
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
        }
        if (z3) {
            return containerIssueInformation;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    private void checkContainerIssued(byte[] issueInfo, boolean reqDetailErr) throws MfiFelicaException {
        boolean z;
        int containerIssuedStatus = AccessConfig.getContainerIssuedStatus(issueInfo);
        int i = 31;
        if (containerIssuedStatus == AccessConfig.STATUS_EXACTLY_MATCH_WITH_CAREER_IDENTIFY_CODE) {
            z = false;
        } else {
            if (reqDetailErr) {
                if (containerIssuedStatus == AccessConfig.STATUS_MATCHING_OTHER_THAN_UPPER_1_BIT) {
                    i = 177;
                } else if (containerIssuedStatus != AccessConfig.STATUS_CAREER_IDENTIFY_CODE_ALL_ZERO) {
                    i = 178;
                }
            }
            z = true;
        }
        if (z) {
            throw new MfiFelicaException(i, null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int getKeyVersion(int serviceCode) throws MfiFelicaException {
        int keyVersion;
        int i;
        boolean z = false;
        int i2 = 200;
        String strExExecutionPoint = null;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getKeyVersion(int) in");
                keyVersion = this.mChipHolder.getFelica().getKeyVersion(serviceCode);
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            keyVersion = 0;
        } catch (Exception e3) {
            e = e3;
            keyVersion = 0;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getKeyVersion(int) out");
            z = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(7, "%s FelicaException:%s", "700", e.getMessage());
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                i = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_KEY_VERSION.msg;
                } else {
                    i = 200;
                }
                if (200 == i) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i2 = i;
            } else if (4 == id) {
                i = 11;
                if (11 != type) {
                }
                if (200 == i) {
                }
                i2 = i;
            }
        } catch (Exception e5) {
            e = e5;
            LogMgr.log(2, "%s %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
        }
        if (z) {
            return keyVersion;
        }
        throw new MfiFelicaException(i2, strExExecutionPoint);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Data[] read(BlockList blockList) throws MfiFelicaException {
        Data[] dataArr;
        boolean z = false;
        Data[] dataArr2 = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        int i = 200;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.read(BlockList) in");
                dataArr = this.mChipHolder.getFelica().read(blockList);
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            dataArr = null;
        } catch (Exception e3) {
            e = e3;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.read(BlockList) out");
            z = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i2 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_READ.msg;
                } else {
                    i2 = 200;
                }
                if (200 == i2) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i = i2;
            }
        } catch (Exception e5) {
            e = e5;
            dataArr2 = dataArr;
            LogMgr.log(2, "%s %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
            LogMgr.printStackTrace(7, e);
            Data[] dataArr3 = dataArr2;
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
            dataArr = dataArr3;
        }
        if (z) {
            return dataArr;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Data[] readWithCheckServiceAndBlock(BlockList blockList) throws MfiFelicaException, FelicaException {
        Data[] dataArr;
        boolean z = false;
        Data[] dataArr2 = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        int i = 200;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.read(BlockList) in");
                dataArr = this.mChipHolder.getFelica().read(blockList);
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            dataArr = null;
        } catch (Exception e3) {
            e = e3;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.read(BlockList) out");
            z = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(2, "700 FelicaException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i2 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_READ.msg;
                } else {
                    i2 = 200;
                }
                if (5 == id && (11 == type || 12 == type)) {
                    throw e;
                }
                if (200 == i2) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i = i2;
            }
        } catch (Exception e5) {
            e = e5;
            dataArr2 = dataArr;
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            Data[] dataArr3 = dataArr2;
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
            dataArr = dataArr3;
        }
        if (z) {
            return dataArr;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    public void close() throws MfiFelicaException {
        String strExExecutionPoint;
        boolean z = false;
        try {
            LogMgr.log(3, "001 [access-felica] Felica.close() in");
            this.mChipHolder.getFelica().close();
            LogMgr.log(3, "002 [access-felica] Felica.close() out");
            setOpened(false);
            strExExecutionPoint = null;
            z = true;
        } catch (FelicaException e) {
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
        } catch (MfiFelicaException e2) {
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s:%s", "701", e3.getClass().getSimpleName(), e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e3);
        }
        if (!z) {
            throw new MfiFelicaException(200, strExExecutionPoint);
        }
    }

    public String getMFCVersion(Context context) throws MfiFelicaException {
        Exception e;
        String mFCVersion;
        FelicaException e2;
        boolean z = false;
        String strExExecutionPoint = null;
        try {
            LogMgr.log(3, "001 [access-felica] Felica.getMFCVersion(Context) in");
            mFCVersion = Felica.getMFCVersion(context);
        } catch (FelicaException e3) {
            e2 = e3;
            mFCVersion = null;
        } catch (Exception e4) {
            e = e4;
            mFCVersion = null;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getMFCVersion(Context) out");
            z = true;
        } catch (FelicaException e5) {
            e2 = e5;
            LogMgr.log(2, "700 FelicaException:" + e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e2);
        } catch (Exception e6) {
            e = e6;
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
        }
        if (z) {
            return mFCVersion;
        }
        throw new MfiFelicaException(200, strExExecutionPoint);
    }

    public void closeSilently() {
        if (isOpened()) {
            try {
                close();
            } catch (MfiFelicaException unused) {
            }
        }
    }

    private synchronized void setOpened(boolean isOpened) {
        this.mIsOpened = isOpened;
    }

    public synchronized boolean isOpened() {
        return this.mIsOpened;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public KeyInformation[] getKeyVersionV2(int[] iArr) throws MfiFelicaException {
        KeyInformation[] keyVersionV2;
        boolean z = false;
        int i = 200;
        KeyInformation[] keyInformationArr = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getKeyVersionV2(int[]) in");
                keyVersionV2 = this.mChipHolder.getFelica().getKeyVersionV2(iArr);
                try {
                    LogMgr.log(3, "002 [access-felica] Felica.getKeyVersionV2(int[]) out");
                    z = true;
                } catch (FelicaException e) {
                    e = e;
                    LogMgr.log(7, "700 FelicaException:" + e.getMessage());
                    int id = e.getID();
                    int type = e.getType();
                    if (3 == id) {
                        int i2 = 6;
                        if (6 == type) {
                            strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_KEY_VERSION_V2.msg;
                        } else {
                            i2 = 200;
                        }
                        if (200 == i2) {
                            strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                        }
                        i = i2;
                    }
                } catch (Exception e2) {
                    e = e2;
                    keyInformationArr = keyVersionV2;
                    LogMgr.log(2, "701" + e.getClass().getSimpleName() + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    KeyInformation[] keyInformationArr2 = keyInformationArr;
                    strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
                    keyVersionV2 = keyInformationArr2;
                }
            } catch (MfiFelicaException e3) {
                throw e3;
            }
        } catch (FelicaException e4) {
            e = e4;
            keyVersionV2 = null;
        } catch (Exception e5) {
            e = e5;
        }
        if (z) {
            return keyVersionV2;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    public boolean isFaver2() throws MfiFelicaException {
        LogMgr.log(6, "000");
        String mFCVersion = getMFCVersion(FelicaAdapter.getInstance());
        LogMgr.log(6, "999");
        return StringUtil.versionCompare(mFCVersion, MfiClientConst.MFC_VERSION_FAVER3) == -1;
    }

    private boolean checkNodeCodeExistsByGetKeyVersion(int nodeCode) throws MfiFelicaException {
        LogMgr.log(6, "000");
        try {
            getKeyVersion(nodeCode);
            LogMgr.log(6, "999");
            return true;
        } catch (MfiFelicaException e) {
            if (11 == e.getType()) {
                LogMgr.log(7, "001 : Felica#getKeyVersion FelicaException is caused by Not exist area.");
                return false;
            }
            throw e;
        }
    }

    private boolean[] checkNodeCodeListExistsByGetKeyVersionV2(int[] nodeCodeList) throws MfiFelicaException {
        LogMgr.log(6, "000");
        if (nodeCodeList == null) {
            return null;
        }
        boolean[] zArr = new boolean[nodeCodeList.length];
        KeyInformation[] keyVersionV2 = getKeyVersionV2(nodeCodeList);
        for (int i = 0; i < nodeCodeList.length; i++) {
            zArr[i] = isNodeCodeExists(keyVersionV2[i]);
        }
        LogMgr.log(6, "999");
        return zArr;
    }

    public boolean checkNodeCodeExists(int nodeCode) throws MfiFelicaException {
        LogMgr.log(6, "000");
        boolean[] zArrCheckNodeCodeListExists = checkNodeCodeListExists(new int[]{nodeCode});
        LogMgr.log(6, "999");
        return zArrCheckNodeCodeListExists[0];
    }

    public boolean[] checkNodeCodeListExists(int[] nodeCodeList) throws MfiFelicaException {
        boolean[] zArrCheckNodeCodeListExistsByGetKeyVersionV2;
        LogMgr.log(6, "000");
        if (nodeCodeList == null) {
            return null;
        }
        if (isFaver2()) {
            LogMgr.log(6, "001");
            zArrCheckNodeCodeListExistsByGetKeyVersionV2 = new boolean[nodeCodeList.length];
            for (int i = 0; i < nodeCodeList.length; i++) {
                zArrCheckNodeCodeListExistsByGetKeyVersionV2[i] = checkNodeCodeExistsByGetKeyVersion(nodeCodeList[i]);
            }
        } else {
            zArrCheckNodeCodeListExistsByGetKeyVersionV2 = checkNodeCodeListExistsByGetKeyVersionV2(nodeCodeList);
        }
        LogMgr.log(6, "999");
        return zArrCheckNodeCodeListExistsByGetKeyVersionV2;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0096  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ResultNodeCodeListInformation checkNodeCodeListExistsWithCheckKeys(int[] nodeCodeList) throws MfiFelicaException {
        LogMgr.log(6, "000");
        if (nodeCodeList == null) {
            return null;
        }
        int length = nodeCodeList.length;
        int i = length + 1;
        int[] iArr = new int[length + 2];
        boolean z = false;
        System.arraycopy(nodeCodeList, 0, iArr, 0, nodeCodeList.length);
        iArr[length] = 65535;
        iArr[i] = 0;
        boolean[] zArr = new boolean[nodeCodeList.length];
        if (isFaver2()) {
            LogMgr.log(6, "001");
            for (int i2 = 0; i2 < nodeCodeList.length; i2++) {
                zArr[i2] = checkNodeCodeExistsByGetKeyVersion(nodeCodeList[i2]);
            }
            int keyVersionExpectToExist = getKeyVersionExpectToExist(65535);
            int keyVersionExpectToExist2 = getKeyVersionExpectToExist(0);
            if (keyVersionExpectToExist == 3 && keyVersionExpectToExist2 == 3) {
                z = true;
            }
        } else {
            KeyInformation[] keyVersionV2 = getKeyVersionV2(iArr);
            for (int i3 = 0; i3 < nodeCodeList.length; i3++) {
                zArr[i3] = isNodeCodeExists(keyVersionV2[i3]);
            }
            Integer desVersion = keyVersionV2[length].getDesVersion();
            Integer aesVersion = keyVersionV2[length].getAesVersion();
            Integer desVersion2 = keyVersionV2[i].getDesVersion();
            Integer aesVersion2 = keyVersionV2[i].getAesVersion();
            if (desVersion != null && aesVersion != null && desVersion2 != null && aesVersion2 != null && desVersion.intValue() == 3 && aesVersion.intValue() == 4160 && desVersion2.intValue() == 3 && aesVersion2.intValue() == 4160) {
            }
        }
        LogMgr.log(6, "999");
        return new ResultNodeCodeListInformation(z, zArr);
    }

    private int getKeyVersionExpectToExist(int serviceCode) throws MfiFelicaException {
        LogMgr.log(6, "000");
        try {
            int keyVersion = getKeyVersion(serviceCode);
            LogMgr.log(6, "999");
            return keyVersion;
        } catch (MfiFelicaException e) {
            if (11 == e.getType()) {
                LogMgr.log(7, "001 : Felica#getKeyVersion FelicaException is caused by Not exist area.");
                throw new MfiFelicaException(200, MfiClientCallbackConst.Api.FELICA_GET_KEY_VERSION.msg);
            }
            throw e;
        }
    }

    public class ResultNodeCodeListInformation {
        private boolean mIsServiceProvider1;
        private boolean[] mNodeCodeExists;

        ResultNodeCodeListInformation(boolean isServiceProvider1, boolean[] nodeCodeExists) {
            this.mNodeCodeExists = null;
            this.mIsServiceProvider1 = isServiceProvider1;
            if (nodeCodeExists != null) {
                this.mNodeCodeExists = (boolean[]) nodeCodeExists.clone();
            }
        }

        public boolean isServiceProvider1() {
            return this.mIsServiceProvider1;
        }

        public boolean[] getNodeCodeExists() {
            boolean[] zArr = this.mNodeCodeExists;
            if (zArr != null) {
                return (boolean[]) zArr.clone();
            }
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NodeInformation getNodeInformation(int i) throws MfiFelicaException {
        NodeInformation nodeInformation;
        boolean z = false;
        int i2 = 200;
        NodeInformation nodeInformation2 = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getNodeInformation(int) in");
                nodeInformation = this.mChipHolder.getFelica().getNodeInformation(i);
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            nodeInformation = null;
        } catch (Exception e3) {
            e = e3;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getNodeInformation(int) out");
            z = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i3 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_NODE_INFORMATION.msg;
                } else {
                    i3 = 200;
                }
                if (200 == i3) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i2 = i3;
            }
        } catch (Exception e5) {
            e = e5;
            nodeInformation2 = nodeInformation;
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            NodeInformation nodeInformation3 = nodeInformation2;
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
            nodeInformation = nodeInformation3;
        }
        if (z) {
            return nodeInformation;
        }
        throw new MfiFelicaException(i2, strExExecutionPoint);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BlockCountInformation[] getBlockCountInformation(int[] iArr) throws MfiFelicaException {
        BlockCountInformation[] blockCountInformation;
        boolean z = false;
        int i = 200;
        BlockCountInformation[] blockCountInformationArr = null;
        strExExecutionPoint = null;
        String strExExecutionPoint = null;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getBlockCountInformation(int[]) in");
                blockCountInformation = this.mChipHolder.getFelica().getBlockCountInformation(iArr);
            } catch (MfiFelicaException e) {
                throw e;
            }
        } catch (FelicaException e2) {
            e = e2;
            blockCountInformation = null;
        } catch (Exception e3) {
            e = e3;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getBlockCountInformation(int[]) out");
            z = true;
        } catch (FelicaException e4) {
            e = e4;
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            if (3 == id) {
                int i2 = 6;
                if (6 == type) {
                    strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_BLOCK_COUNT_INFORMATION.msg;
                } else {
                    i2 = 200;
                }
                if (200 == i2) {
                    strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                }
                i = i2;
            }
        } catch (Exception e5) {
            e = e5;
            blockCountInformationArr = blockCountInformation;
            LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            BlockCountInformation[] blockCountInformationArr2 = blockCountInformationArr;
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
            blockCountInformation = blockCountInformationArr2;
        }
        if (z) {
            return blockCountInformation;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    private static boolean isNodeCodeExists(KeyInformation keyInformation) {
        LogMgr.log(6, "000");
        Integer aesVersion = keyInformation.getAesVersion();
        Integer desVersion = keyInformation.getDesVersion();
        LogMgr.log(6, "999");
        if (aesVersion == null || aesVersion.intValue() == 65535) {
            return (desVersion == null || desVersion.intValue() == 65535) ? false : true;
        }
        return true;
    }
}
