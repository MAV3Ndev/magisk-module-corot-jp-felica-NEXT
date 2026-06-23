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
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class MfiFelicaWrapper {
    public final MfiChipHolder mChipHolder;
    private boolean mIsOpened = false;

    public MfiFelicaWrapper(MfiChipHolder mfiChipHolder) {
        this.mChipHolder = mfiChipHolder;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0071  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void open() throws com.felicanetworks.mfc.mfi.MfiFelicaException {
        /*
            r11 = this;
            r0 = 7
            r1 = 2
            r2 = 200(0xc8, float:2.8E-43)
            r3 = 1
            r4 = 0
            r5 = 0
            com.felicanetworks.mfc.mfi.MfiChipHolder r6 = r11.mChipHolder     // Catch: java.lang.Exception -> L11 com.felicanetworks.mfc.FelicaException -> L2e com.felicanetworks.mfc.mfi.MfiFelicaException -> L81
            r6.open()     // Catch: java.lang.Exception -> L11 com.felicanetworks.mfc.FelicaException -> L2e com.felicanetworks.mfc.mfi.MfiFelicaException -> L81
            r11.setOpened(r3)     // Catch: java.lang.Exception -> L11 com.felicanetworks.mfc.FelicaException -> L2e com.felicanetworks.mfc.mfi.MfiFelicaException -> L81
            goto L78
        L11:
            r3 = move-exception
            java.lang.Class r5 = r3.getClass()
            java.lang.String r5 = r5.getSimpleName()
            java.lang.String r6 = r3.getMessage()
            java.lang.String r7 = "%s %s:%s"
            java.lang.String r8 = "701"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r7, r8, r5, r6)
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r0, r3)
            java.lang.String r5 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.exExecutionPoint(r3)
        L2c:
            r3 = 0
            goto L78
        L2e:
            r6 = move-exception
            java.lang.String r7 = r6.getMessage()
            java.lang.String r8 = "%s FelicaException:%s"
            java.lang.String r9 = "700"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r8, r9, r7)
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r0, r6)
            int r0 = r6.getID()
            int r1 = r6.getType()
            r7 = 3
            r8 = 55
            r9 = 6
            r10 = 8
            if (r7 != r0) goto L55
            if (r9 != r1) goto L6d
            com.felicanetworks.mfc.mfi.MfiClientCallbackConst$Api r0 = com.felicanetworks.mfc.mfi.MfiClientCallbackConst.Api.FELICA_OPEN
            java.lang.String r5 = r0.msg
            r8 = 6
            goto L6f
        L55:
            if (r10 != r0) goto L5a
            if (r8 != r1) goto L6d
            goto L6f
        L5a:
            if (r3 != r0) goto L6d
            if (r10 != r1) goto L6d
            java.lang.String r0 = r6.getMessage()
            java.lang.String r1 = "NFC R/W function is being used."
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L6d
            r8 = 8
            goto L6f
        L6d:
            r8 = 200(0xc8, float:2.8E-43)
        L6f:
            if (r2 != r8) goto L76
            java.lang.String r0 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.felicaExExecutionPoint(r6)
            r5 = r0
        L76:
            r2 = r8
            goto L2c
        L78:
            if (r3 == 0) goto L7b
            return
        L7b:
            com.felicanetworks.mfc.mfi.MfiFelicaException r0 = new com.felicanetworks.mfc.mfi.MfiFelicaException
            r0.<init>(r2, r5)
            throw r0
        L81:
            r0 = move-exception
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiFelicaWrapper.open():void");
    }

    public void select(int i) throws MfiFelicaException {
        int i2 = 200;
        boolean z = false;
        String strExExecutionPoint = null;
        try {
            LogMgr.log(3, "001 [access-felica] Felica.select(int) in");
            this.mChipHolder.getFelica().select(i);
            LogMgr.log(3, "002 [access-felica] Felica.select(int) out");
            z = true;
        } catch (FelicaException e) {
            LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
            LogMgr.printStackTrace(7, e);
            int id = e.getID();
            int type = e.getType();
            int i3 = 6;
            if (3 == id && 6 == type) {
                strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_SELECT.msg;
            } else {
                i3 = 200;
            }
            if (200 == i3) {
                strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
            }
            i2 = i3;
        } catch (MfiFelicaException e2) {
            throw e2;
        } catch (Exception e3) {
            LogMgr.log(2, "%s %s:%s", "701", e3.getClass().getSimpleName(), e3.getMessage());
            LogMgr.printStackTrace(7, e3);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e3);
        }
        if (!z) {
            throw new MfiFelicaException(i2, strExExecutionPoint);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void selectWritableSystemWithCid(int r9, java.lang.String r10) throws com.felicanetworks.mfc.mfi.MfiFelicaException, com.felicanetworks.mfc.FelicaException {
        /*
            r8 = this;
            r0 = 200(0xc8, float:2.8E-43)
            r1 = 7
            r2 = 2
            r3 = 3
            r4 = 0
            r5 = 0
            java.lang.String r6 = "001 [access-felica] Felica.select(int, String) in"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r6)     // Catch: java.lang.Exception -> L1d com.felicanetworks.mfc.FelicaException -> L4e com.felicanetworks.mfc.mfi.MfiFelicaException -> L98
            com.felicanetworks.mfc.mfi.MfiChipHolder r6 = r8.mChipHolder     // Catch: java.lang.Exception -> L1d com.felicanetworks.mfc.FelicaException -> L4e com.felicanetworks.mfc.mfi.MfiFelicaException -> L98
            com.felicanetworks.mfc.Felica r6 = r6.getFelica()     // Catch: java.lang.Exception -> L1d com.felicanetworks.mfc.FelicaException -> L4e com.felicanetworks.mfc.mfi.MfiFelicaException -> L98
            r6.select(r9, r10)     // Catch: java.lang.Exception -> L1d com.felicanetworks.mfc.FelicaException -> L4e com.felicanetworks.mfc.mfi.MfiFelicaException -> L98
            java.lang.String r9 = "002 [access-felica] Felica.select(int, String) out"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r9)     // Catch: java.lang.Exception -> L1d com.felicanetworks.mfc.FelicaException -> L4e com.felicanetworks.mfc.mfi.MfiFelicaException -> L98
            r4 = 1
            goto L8f
        L1d:
            r9 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r3 = "701 "
            r10.append(r3)
            java.lang.Class r3 = r9.getClass()
            java.lang.String r3 = r3.getSimpleName()
            r10.append(r3)
            java.lang.String r3 = ":"
            r10.append(r3)
            java.lang.String r3 = r9.getMessage()
            r10.append(r3)
            java.lang.String r10 = r10.toString()
            com.felicanetworks.mfc.util.LogMgr.log(r2, r10)
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r1, r9)
            java.lang.String r5 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.exExecutionPoint(r9)
            goto L8f
        L4e:
            r9 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r6 = "700 FelicaException:"
            r10.append(r6)
            java.lang.String r6 = r9.getMessage()
            r10.append(r6)
            java.lang.String r10 = r10.toString()
            com.felicanetworks.mfc.util.LogMgr.log(r2, r10)
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r1, r9)
            int r10 = r9.getID()
            int r6 = r9.getType()
            r7 = 6
            if (r3 != r10) goto L85
            if (r7 != r6) goto L7c
            com.felicanetworks.mfc.mfi.MfiClientCallbackConst$Api r10 = com.felicanetworks.mfc.mfi.MfiClientCallbackConst.Api.FELICA_SELECT_CID
            java.lang.String r5 = r10.msg
            goto L87
        L7c:
            if (r1 == r6) goto L7f
            goto L85
        L7f:
            java.lang.String r10 = "702 select timeout occurred"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r10)
            throw r9
        L85:
            r7 = 200(0xc8, float:2.8E-43)
        L87:
            if (r0 != r7) goto L8e
            java.lang.String r9 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.felicaExExecutionPoint(r9)
            r5 = r9
        L8e:
            r0 = r7
        L8f:
            if (r4 == 0) goto L92
            return
        L92:
            com.felicanetworks.mfc.mfi.MfiFelicaException r9 = new com.felicanetworks.mfc.mfi.MfiFelicaException
            r9.<init>(r0, r5)
            throw r9
        L98:
            r9 = move-exception
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiFelicaWrapper.selectWritableSystemWithCid(int, java.lang.String):void");
    }

    public byte[] getIDm() throws MfiFelicaException {
        byte[] iDm;
        Exception e;
        FelicaException e2;
        String strExExecutionPoint = null;
        boolean z = false;
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
        String strExExecutionPoint = null;
        boolean z = false;
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

    public byte[] getContainerId() throws MfiFelicaException {
        byte[] containerId;
        Exception e;
        int i = 200;
        String strExExecutionPoint = null;
        boolean z = false;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getContainerId() in");
                containerId = this.mChipHolder.getFelica().getContainerId();
                try {
                    LogMgr.log(3, "002 [access-felica] Felica.getContainerId() out");
                    z = true;
                } catch (FelicaException e2) {
                    e = e2;
                    LogMgr.log(2, "700 FelicaException:" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    int id = e.getID();
                    int type = e.getType();
                    int i2 = 6;
                    if (3 == id && 6 == type) {
                        strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_CONTAINER_ID.msg;
                    } else {
                        i2 = 200;
                    }
                    if (200 == i2) {
                        strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                    }
                    i = i2;
                } catch (Exception e3) {
                    e = e3;
                    LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
                }
            } catch (MfiFelicaException e4) {
                throw e4;
            }
        } catch (FelicaException e5) {
            e = e5;
            containerId = null;
        } catch (Exception e6) {
            containerId = null;
            e = e6;
        }
        if (z) {
            return containerId;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    public int[] getSystemCodeList() throws MfiFelicaException {
        int[] systemCodeList;
        Exception e;
        int i = 200;
        String strExExecutionPoint = null;
        boolean z = false;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getSystemCodeList() in");
                systemCodeList = this.mChipHolder.getFelica().getSystemCodeList();
                try {
                    LogMgr.log(3, "002 [access-felica] Felica.getSystemCodeList() out");
                    z = true;
                } catch (FelicaException e2) {
                    e = e2;
                    LogMgr.log(2, "700 FelicaException:" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    int id = e.getID();
                    int type = e.getType();
                    int i2 = 6;
                    if (3 == id && 6 == type) {
                        strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_SYSTEM_CODE_LIST.msg;
                    } else {
                        i2 = 200;
                    }
                    if (200 == i2) {
                        strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                    }
                    i = i2;
                } catch (Exception e3) {
                    e = e3;
                    LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
                }
            } catch (MfiFelicaException e4) {
                throw e4;
            }
        } catch (FelicaException e5) {
            e = e5;
            systemCodeList = null;
        } catch (Exception e6) {
            systemCodeList = null;
            e = e6;
        }
        if (z) {
            return systemCodeList;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    public byte[] getContainerIssueInformationWithCheckIssued() throws MfiFelicaException {
        return getContainerIssueInformation(true);
    }

    public byte[] getContainerIssueInformation() throws MfiFelicaException {
        return getContainerIssueInformation(false);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private byte[] getContainerIssueInformation(boolean r11) throws com.felicanetworks.mfc.mfi.MfiFelicaException {
        /*
            r10 = this;
            r0 = 7
            r1 = 2
            r2 = 200(0xc8, float:2.8E-43)
            r3 = 3
            r4 = 0
            r5 = 0
            java.lang.String r6 = "001 [access-felica] Felica.getContainerIssueInformation() in"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r6)     // Catch: java.lang.Exception -> L3a com.felicanetworks.mfc.FelicaException -> L58 com.felicanetworks.mfc.mfi.MfiFelicaException -> L8d
            com.felicanetworks.mfc.mfi.MfiChipHolder r6 = r10.mChipHolder     // Catch: java.lang.Exception -> L3a com.felicanetworks.mfc.FelicaException -> L58 com.felicanetworks.mfc.mfi.MfiFelicaException -> L8d
            com.felicanetworks.mfc.Felica r6 = r6.getFelica()     // Catch: java.lang.Exception -> L3a com.felicanetworks.mfc.FelicaException -> L58 com.felicanetworks.mfc.mfi.MfiFelicaException -> L8d
            byte[] r6 = r6.getContainerIssueInformation()     // Catch: java.lang.Exception -> L3a com.felicanetworks.mfc.FelicaException -> L58 com.felicanetworks.mfc.mfi.MfiFelicaException -> L8d
            java.lang.String r7 = "002 [access-felica] Felica.getContainerIssueInformation() out"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r7)     // Catch: java.lang.Exception -> L35 com.felicanetworks.mfc.FelicaException -> L38 com.felicanetworks.mfc.mfi.MfiFelicaException -> L8d
            r7 = 1
            if (r11 == 0) goto L33
            r11 = 0
        L1f:
            int r8 = r6.length     // Catch: java.lang.Exception -> L35 com.felicanetworks.mfc.FelicaException -> L38 com.felicanetworks.mfc.mfi.MfiFelicaException -> L8d
            if (r11 >= r8) goto L2b
            r8 = r6[r11]     // Catch: java.lang.Exception -> L35 com.felicanetworks.mfc.FelicaException -> L38 com.felicanetworks.mfc.mfi.MfiFelicaException -> L8d
            if (r8 == 0) goto L28
            r11 = 1
            goto L2c
        L28:
            int r11 = r11 + 1
            goto L1f
        L2b:
            r11 = 0
        L2c:
            if (r11 != 0) goto L33
            r11 = 31
            r2 = 31
            goto L84
        L33:
            r5 = 1
            goto L84
        L35:
            r11 = move-exception
            r4 = r6
            goto L3b
        L38:
            r11 = move-exception
            goto L5a
        L3a:
            r11 = move-exception
        L3b:
            java.lang.Class r3 = r11.getClass()
            java.lang.String r3 = r3.getSimpleName()
            java.lang.String r6 = r11.getMessage()
            java.lang.String r7 = "%s %s:%s"
            java.lang.String r8 = "701"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r7, r8, r3, r6)
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r0, r11)
            java.lang.String r11 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.exExecutionPoint(r11)
            r6 = r4
            r4 = r11
            goto L84
        L58:
            r11 = move-exception
            r6 = r4
        L5a:
            java.lang.String r7 = r11.getMessage()
            java.lang.String r8 = "%s FelicaException:%s"
            java.lang.String r9 = "700"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r8, r9, r7)
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r0, r11)
            int r0 = r11.getID()
            int r1 = r11.getType()
            r7 = 6
            if (r3 != r0) goto L7a
            if (r7 != r1) goto L7a
            com.felicanetworks.mfc.mfi.MfiClientCallbackConst$Api r0 = com.felicanetworks.mfc.mfi.MfiClientCallbackConst.Api.FELICA_GET_CONTAINER_ISSUE_INFO
            java.lang.String r4 = r0.msg
            goto L7c
        L7a:
            r7 = 200(0xc8, float:2.8E-43)
        L7c:
            if (r2 != r7) goto L83
            java.lang.String r11 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.felicaExExecutionPoint(r11)
            r4 = r11
        L83:
            r2 = r7
        L84:
            if (r5 == 0) goto L87
            return r6
        L87:
            com.felicanetworks.mfc.mfi.MfiFelicaException r11 = new com.felicanetworks.mfc.mfi.MfiFelicaException
            r11.<init>(r2, r4)
            throw r11
        L8d:
            r11 = move-exception
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiFelicaWrapper.getContainerIssueInformation(boolean):byte[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int getKeyVersion(int r10) throws com.felicanetworks.mfc.mfi.MfiFelicaException {
        /*
            r9 = this;
            r0 = 7
            r1 = 200(0xc8, float:2.8E-43)
            r2 = 0
            r3 = 3
            r4 = 0
            java.lang.String r5 = "001 [access-felica] Felica.getKeyVersion(int) in"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r5)     // Catch: java.lang.Exception -> L20 com.felicanetworks.mfc.FelicaException -> L3e com.felicanetworks.mfc.mfi.MfiFelicaException -> L79
            com.felicanetworks.mfc.mfi.MfiChipHolder r5 = r9.mChipHolder     // Catch: java.lang.Exception -> L20 com.felicanetworks.mfc.FelicaException -> L3e com.felicanetworks.mfc.mfi.MfiFelicaException -> L79
            com.felicanetworks.mfc.Felica r5 = r5.getFelica()     // Catch: java.lang.Exception -> L20 com.felicanetworks.mfc.FelicaException -> L3e com.felicanetworks.mfc.mfi.MfiFelicaException -> L79
            int r10 = r5.getKeyVersion(r10)     // Catch: java.lang.Exception -> L20 com.felicanetworks.mfc.FelicaException -> L3e com.felicanetworks.mfc.mfi.MfiFelicaException -> L79
            java.lang.String r5 = "002 [access-felica] Felica.getKeyVersion(int) out"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r5)     // Catch: java.lang.Exception -> L1c com.felicanetworks.mfc.FelicaException -> L1e com.felicanetworks.mfc.mfi.MfiFelicaException -> L79
            r2 = 1
            goto L70
        L1c:
            r3 = move-exception
            goto L22
        L1e:
            r5 = move-exception
            goto L40
        L20:
            r3 = move-exception
            r10 = 0
        L22:
            r4 = 2
            java.lang.Class r5 = r3.getClass()
            java.lang.String r5 = r5.getSimpleName()
            java.lang.String r6 = r3.getMessage()
            java.lang.String r7 = "%s %s:%s"
            java.lang.String r8 = "701"
            com.felicanetworks.mfc.util.LogMgr.log(r4, r7, r8, r5, r6)
            com.felicanetworks.mfc.util.LogMgr.printStackTrace(r0, r3)
            java.lang.String r4 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.exExecutionPoint(r3)
            goto L70
        L3e:
            r5 = move-exception
            r10 = 0
        L40:
            java.lang.String r6 = r5.getMessage()
            java.lang.String r7 = "%s FelicaException:%s"
            java.lang.String r8 = "700"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r7, r8, r6)
            int r0 = r5.getID()
            int r6 = r5.getType()
            r7 = 11
            r8 = 6
            if (r3 != r0) goto L60
            if (r8 != r6) goto L66
            com.felicanetworks.mfc.mfi.MfiClientCallbackConst$Api r0 = com.felicanetworks.mfc.mfi.MfiClientCallbackConst.Api.FELICA_GET_KEY_VERSION
            java.lang.String r4 = r0.msg
            r7 = 6
            goto L68
        L60:
            r3 = 4
            if (r3 != r0) goto L66
            if (r7 != r6) goto L66
            goto L68
        L66:
            r7 = 200(0xc8, float:2.8E-43)
        L68:
            if (r1 != r7) goto L6f
            java.lang.String r0 = com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil.felicaExExecutionPoint(r5)
            r4 = r0
        L6f:
            r1 = r7
        L70:
            if (r2 == 0) goto L73
            return r10
        L73:
            com.felicanetworks.mfc.mfi.MfiFelicaException r10 = new com.felicanetworks.mfc.mfi.MfiFelicaException
            r10.<init>(r1, r4)
            throw r10
        L79:
            r10 = move-exception
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiFelicaWrapper.getKeyVersion(int):int");
    }

    public Data[] read(BlockList blockList) throws MfiFelicaException {
        Exception e;
        Data[] dataArr;
        int i = 200;
        String strExExecutionPoint = null;
        boolean z = false;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.read(BlockList) in");
                dataArr = this.mChipHolder.getFelica().read(blockList);
                try {
                    LogMgr.log(3, "002 [access-felica] Felica.read(BlockList) out");
                    z = true;
                } catch (FelicaException e2) {
                    e = e2;
                    LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    int id = e.getID();
                    int type = e.getType();
                    int i2 = 6;
                    if (3 == id && 6 == type) {
                        strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_READ.msg;
                    } else {
                        i2 = 200;
                    }
                    if (200 == i2) {
                        strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                    }
                    i = i2;
                } catch (Exception e3) {
                    e = e3;
                    LogMgr.log(2, "%s %s:%s", "701", e.getClass().getSimpleName(), e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
                }
            } catch (MfiFelicaException e4) {
                throw e4;
            }
        } catch (FelicaException e5) {
            e = e5;
            dataArr = null;
        } catch (Exception e6) {
            e = e6;
            dataArr = null;
        }
        if (z) {
            return dataArr;
        }
        throw new MfiFelicaException(i, strExExecutionPoint);
    }

    public Data[] readWithCheckServiceAndBlock(BlockList blockList) throws MfiFelicaException, FelicaException {
        Exception e;
        Data[] dataArr;
        int i = 200;
        String strExExecutionPoint = null;
        boolean z = false;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.read(BlockList) in");
                dataArr = this.mChipHolder.getFelica().read(blockList);
                try {
                    LogMgr.log(3, "002 [access-felica] Felica.read(BlockList) out");
                    z = true;
                } catch (FelicaException e2) {
                    e = e2;
                    LogMgr.log(2, "700 FelicaException:" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    int id = e.getID();
                    int type = e.getType();
                    int i2 = 6;
                    if (3 == id && 6 == type) {
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
                } catch (Exception e3) {
                    e = e3;
                    LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
                }
            } catch (MfiFelicaException e4) {
                throw e4;
            }
        } catch (FelicaException e5) {
            e = e5;
            dataArr = null;
        } catch (Exception e6) {
            e = e6;
            dataArr = null;
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
            z = true;
            strExExecutionPoint = null;
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
        String strExExecutionPoint = null;
        boolean z = false;
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

    private synchronized void setOpened(boolean z) {
        this.mIsOpened = z;
    }

    public synchronized boolean isOpened() {
        return this.mIsOpened;
    }

    public KeyInformation[] getKeyVersionV2(int[] iArr) throws MfiFelicaException {
        Exception e;
        KeyInformation[] keyVersionV2;
        String strExExecutionPoint = null;
        int i = 200;
        boolean z = false;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getKeyVersionV2(int[]) in");
                keyVersionV2 = this.mChipHolder.getFelica().getKeyVersionV2(iArr);
            } catch (MfiFelicaException e2) {
                throw e2;
            }
        } catch (FelicaException e3) {
            e = e3;
            keyVersionV2 = null;
        } catch (Exception e4) {
            e = e4;
            keyVersionV2 = null;
        }
        try {
            LogMgr.log(3, "002 [access-felica] Felica.getKeyVersionV2(int[]) out");
            z = true;
        } catch (FelicaException e5) {
            e = e5;
            LogMgr.log(7, "700 FelicaException:" + e.getMessage());
            int id = e.getID();
            int type = e.getType();
            int i2 = 6;
            if (3 == id && 6 == type) {
                strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_KEY_VERSION_V2.msg;
            } else {
                i2 = 200;
            }
            if (200 == i2) {
                strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
            }
            i = i2;
        } catch (Exception e6) {
            e = e6;
            LogMgr.log(2, "701" + e.getClass().getSimpleName() + e.getMessage());
            LogMgr.printStackTrace(7, e);
            strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
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
        return StringUtil.versionCompare(mFCVersion, "3.0.0") == -1;
    }

    private boolean checkNodeCodeExistsByGetKeyVersion(int i) throws MfiFelicaException {
        LogMgr.log(6, "000");
        try {
            getKeyVersion(i);
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

    private boolean[] checkNodeCodeListExistsByGetKeyVersionV2(int[] iArr) throws MfiFelicaException {
        LogMgr.log(6, "000");
        if (iArr == null) {
            return null;
        }
        boolean[] zArr = new boolean[iArr.length];
        KeyInformation[] keyVersionV2 = getKeyVersionV2(iArr);
        for (int i = 0; i < iArr.length; i++) {
            zArr[i] = isNodeCodeExists(keyVersionV2[i]);
        }
        LogMgr.log(6, "999");
        return zArr;
    }

    public boolean checkNodeCodeExists(int i) throws MfiFelicaException {
        LogMgr.log(6, "000");
        boolean[] zArrCheckNodeCodeListExists = checkNodeCodeListExists(new int[]{i});
        LogMgr.log(6, "999");
        return zArrCheckNodeCodeListExists[0];
    }

    public boolean[] checkNodeCodeListExists(int[] iArr) throws MfiFelicaException {
        boolean[] zArrCheckNodeCodeListExistsByGetKeyVersionV2;
        LogMgr.log(6, "000");
        if (iArr == null) {
            return null;
        }
        if (isFaver2()) {
            LogMgr.log(6, "001");
            zArrCheckNodeCodeListExistsByGetKeyVersionV2 = new boolean[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                zArrCheckNodeCodeListExistsByGetKeyVersionV2[i] = checkNodeCodeExistsByGetKeyVersion(iArr[i]);
            }
        } else {
            zArrCheckNodeCodeListExistsByGetKeyVersionV2 = checkNodeCodeListExistsByGetKeyVersionV2(iArr);
        }
        LogMgr.log(6, "999");
        return zArrCheckNodeCodeListExistsByGetKeyVersionV2;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfc.mfi.MfiFelicaWrapper.ResultNodeCodeListInformation checkNodeCodeListExistsWithCheckKeys(int[] r11) throws com.felicanetworks.mfc.mfi.MfiFelicaException {
        /*
            r10 = this;
            r0 = 6
            java.lang.String r1 = "000"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)
            if (r11 != 0) goto La
            r11 = 0
            return r11
        La:
            int r1 = r11.length
            int r1 = r1 + 2
            int r2 = r1 + (-2)
            int r3 = r1 + (-1)
            int[] r1 = new int[r1]
            int r4 = r11.length
            r5 = 0
            java.lang.System.arraycopy(r11, r5, r1, r5, r4)
            r4 = 65535(0xffff, float:9.1834E-41)
            r1[r2] = r4
            r1[r3] = r5
            int r6 = r11.length
            boolean[] r6 = new boolean[r6]
            boolean r7 = r10.isFaver2()
            r8 = 3
            r9 = 1
            if (r7 == 0) goto L4b
            java.lang.String r1 = "001"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r1)
            r1 = 0
        L30:
            int r2 = r11.length
            if (r1 >= r2) goto L3e
            r2 = r11[r1]
            boolean r2 = r10.checkNodeCodeExistsByGetKeyVersion(r2)
            r6[r1] = r2
            int r1 = r1 + 1
            goto L30
        L3e:
            int r11 = r10.getKeyVersionExpectToExist(r4)
            int r1 = r10.getKeyVersionExpectToExist(r5)
            if (r11 != r8) goto L99
            if (r1 != r8) goto L99
            goto L98
        L4b:
            com.felicanetworks.mfc.KeyInformation[] r1 = r10.getKeyVersionV2(r1)
            r4 = 0
        L50:
            int r7 = r11.length
            if (r4 >= r7) goto L5e
            r7 = r1[r4]
            boolean r7 = isNodeCodeExists(r7)
            r6[r4] = r7
            int r4 = r4 + 1
            goto L50
        L5e:
            r11 = r1[r2]
            java.lang.Integer r11 = r11.getDesVersion()
            r2 = r1[r2]
            java.lang.Integer r2 = r2.getAesVersion()
            r4 = r1[r3]
            java.lang.Integer r4 = r4.getDesVersion()
            r1 = r1[r3]
            java.lang.Integer r1 = r1.getAesVersion()
            if (r11 == 0) goto L99
            if (r2 == 0) goto L99
            if (r4 == 0) goto L99
            if (r1 == 0) goto L99
            int r11 = r11.intValue()
            if (r11 != r8) goto L99
            int r11 = r2.intValue()
            r2 = 4160(0x1040, float:5.83E-42)
            if (r11 != r2) goto L99
            int r11 = r4.intValue()
            if (r11 != r8) goto L99
            int r11 = r1.intValue()
            if (r11 != r2) goto L99
        L98:
            r5 = 1
        L99:
            java.lang.String r11 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r11)
            com.felicanetworks.mfc.mfi.MfiFelicaWrapper$ResultNodeCodeListInformation r11 = new com.felicanetworks.mfc.mfi.MfiFelicaWrapper$ResultNodeCodeListInformation
            r11.<init>(r5, r6)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiFelicaWrapper.checkNodeCodeListExistsWithCheckKeys(int[]):com.felicanetworks.mfc.mfi.MfiFelicaWrapper$ResultNodeCodeListInformation");
    }

    private int getKeyVersionExpectToExist(int i) throws MfiFelicaException {
        LogMgr.log(6, "000");
        try {
            int keyVersion = getKeyVersion(i);
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

        ResultNodeCodeListInformation(boolean z, boolean[] zArr) {
            this.mNodeCodeExists = null;
            this.mIsServiceProvider1 = z;
            if (zArr != null) {
                this.mNodeCodeExists = (boolean[]) zArr.clone();
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

    public NodeInformation getNodeInformation(int i) throws MfiFelicaException {
        Exception e;
        NodeInformation nodeInformation;
        String strExExecutionPoint = null;
        int i2 = 200;
        boolean z = false;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getNodeInformation(int) in");
                nodeInformation = this.mChipHolder.getFelica().getNodeInformation(i);
                try {
                    LogMgr.log(3, "002 [access-felica] Felica.getNodeInformation(int) out");
                    z = true;
                } catch (FelicaException e2) {
                    e = e2;
                    LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    int id = e.getID();
                    int type = e.getType();
                    int i3 = 6;
                    if (3 == id && 6 == type) {
                        strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_NODE_INFORMATION.msg;
                    } else {
                        i3 = 200;
                    }
                    if (200 == i3) {
                        strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                    }
                    i2 = i3;
                } catch (Exception e3) {
                    e = e3;
                    LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
                }
            } catch (MfiFelicaException e4) {
                throw e4;
            }
        } catch (FelicaException e5) {
            e = e5;
            nodeInformation = null;
        } catch (Exception e6) {
            e = e6;
            nodeInformation = null;
        }
        if (z) {
            return nodeInformation;
        }
        throw new MfiFelicaException(i2, strExExecutionPoint);
    }

    public BlockCountInformation[] getBlockCountInformation(int[] iArr) throws MfiFelicaException {
        Exception e;
        BlockCountInformation[] blockCountInformation;
        String strExExecutionPoint = null;
        int i = 200;
        boolean z = false;
        try {
            try {
                LogMgr.log(3, "001 [access-felica] Felica.getBlockCountInformation(int[]) in");
                blockCountInformation = this.mChipHolder.getFelica().getBlockCountInformation(iArr);
                try {
                    LogMgr.log(3, "002 [access-felica] Felica.getBlockCountInformation(int[]) out");
                    z = true;
                } catch (FelicaException e2) {
                    e = e2;
                    LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    int id = e.getID();
                    int type = e.getType();
                    int i2 = 6;
                    if (3 == id && 6 == type) {
                        strExExecutionPoint = MfiClientCallbackConst.Api.FELICA_GET_BLOCK_COUNT_INFORMATION.msg;
                    } else {
                        i2 = 200;
                    }
                    if (200 == i2) {
                        strExExecutionPoint = ObfuscatedMsgUtil.felicaExExecutionPoint(e);
                    }
                    i = i2;
                } catch (Exception e3) {
                    e = e3;
                    LogMgr.log(2, "701 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    strExExecutionPoint = ObfuscatedMsgUtil.exExecutionPoint(e);
                }
            } catch (MfiFelicaException e4) {
                throw e4;
            }
        } catch (FelicaException e5) {
            e = e5;
            blockCountInformation = null;
        } catch (Exception e6) {
            e = e6;
            blockCountInformation = null;
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
        return ((aesVersion == null || aesVersion.intValue() == 65535) && (desVersion == null || desVersion.intValue() == 65535)) ? false : true;
    }
}
