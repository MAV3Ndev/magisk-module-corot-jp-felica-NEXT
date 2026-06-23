package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.ClsdAppletInfo;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.common.base.Ascii;

/* JADX INFO: loaded from: classes3.dex */
public class DataManager {
    private static final byte[] SEID_PREFIX = {-16};
    private SeInfo mSeInfo;
    private String mWalletAppId;

    public synchronized SeInfo createSeInfo(MfiFelicaWrapper felica) throws InterruptedException, MfiFelicaException, GpException {
        String strBytesToHexString;
        LogMgr.log(6, "000");
        if (this.mSeInfo == null) {
            LogMgr.log(6, "001");
            if (Property.isChipGP()) {
                LogMgr.log(6, "002");
                felica.close();
                strBytesToHexString = getGpSeId(felica.mChipHolder);
            } else {
                LogMgr.log(6, "003");
                if (!felica.isOpened()) {
                    felica.open();
                }
                felica.select(65039);
                byte[] iDm = felica.getIDm();
                iDm[0] = (byte) (iDm[0] & Ascii.SI);
                strBytesToHexString = StringUtil.bytesToHexString(iDm);
            }
            this.mSeInfo = new SeInfo(strBytesToHexString, Property.sChipIssuerId, "02", Property.getSeType());
        }
        LogMgr.log(6, "999");
        return this.mSeInfo;
    }

    public synchronized SeInfo getSeInfo() {
        return this.mSeInfo;
    }

    public synchronized void clearSeInfo() {
        LogMgr.log(6, "000");
        this.mSeInfo = null;
        LogMgr.log(6, "999");
    }

    public synchronized SeInfoEx createSeInfoEx(MfiFelicaWrapper openedFelica) throws MfiFelicaException {
        SeInfoEx seInfoEx;
        LogMgr.log(6, "000");
        openedFelica.select(65039);
        byte[] iCCode = openedFelica.getICCode();
        byte[] bArr = new byte[iCCode.length];
        for (int i = 0; i < iCCode.length; i++) {
            bArr[(iCCode.length - 1) - i] = iCCode[i];
        }
        byte[] containerId = openedFelica.getContainerId();
        byte[] containerIssueInformation = openedFelica.getContainerIssueInformation();
        byte[] iDm = openedFelica.getIDm();
        String strBytesToHexString = StringUtil.bytesToHexString(iDm);
        iDm[0] = (byte) (iDm[0] & Ascii.SI);
        seInfoEx = new SeInfoEx(strBytesToHexString, StringUtil.bytesToHexString(iDm), StringUtil.bytesToHexString(bArr), StringUtil.bytesToHexString(containerIssueInformation), StringUtil.bytesToHexString(containerId));
        LogMgr.log(6, "999");
        return seInfoEx;
    }

    private String getGpSeId(MfiChipHolder chipHolder) throws Throwable {
        GpController gpController;
        LogMgr.log(6, "000");
        GpController gpController2 = null;
        try {
            try {
                gpController = chipHolder.getGpController();
            } catch (Throwable th) {
                th = th;
            }
        } catch (GpException e) {
            throw e;
        } catch (InterruptedException e2) {
            e = e2;
        }
        try {
            ClsdAppletInfo clsdAppletInfo = (ClsdAppletInfo) new AppletManager(gpController).getAppletInfo(4);
            if (gpController != null) {
                gpController.closeChannel();
            }
            byte[] shipmentKeyDerivationVendor = clsdAppletInfo.getShipmentKeyDerivationVendor();
            byte[] vseId = clsdAppletInfo.getVseId();
            if (shipmentKeyDerivationVendor == null || vseId == null) {
                LogMgr.log(1, "800 : Failed to get SE Information.");
                throw new GpException(200, null, null);
            }
            byte[] bArr = new byte[shipmentKeyDerivationVendor.length + 1 + vseId.length];
            byte[] bArr2 = SEID_PREFIX;
            System.arraycopy(bArr2, 0, bArr, 0, bArr2.length);
            int length = bArr2.length;
            System.arraycopy(shipmentKeyDerivationVendor, 0, bArr, length, shipmentKeyDerivationVendor.length);
            System.arraycopy(vseId, 0, bArr, length + shipmentKeyDerivationVendor.length, vseId.length);
            LogMgr.log(6, "999");
            return StringUtil.bytesToHexString(bArr);
        } catch (GpException e3) {
            throw e3;
        } catch (InterruptedException e4) {
            e = e4;
            LogMgr.printStackTrace(1, e);
            throw e;
        } catch (Throwable th2) {
            th = th2;
            gpController2 = gpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
            throw th;
        }
    }

    public int getInitStatus(MfiFelicaWrapper openedFelica) throws MfiFelicaException {
        LogMgr.log(6, "000");
        openedFelica.select(65039);
        int iCheckInitStatus = checkInitStatus(openedFelica.getContainerIssueInformation());
        LogMgr.log(6, "999 initStatus=" + iCheckInitStatus);
        return iCheckInitStatus;
    }

    private int checkInitStatus(byte[] issueInfo) {
        LogMgr.log(6, "000");
        int i = !isCarrierIdentifyCodeValid(issueInfo) ? 2 : 1;
        LogMgr.log(6, "999 initStatus=" + i);
        return i;
    }

    private boolean isContainerIssueInfoAllZero(byte[] issueInfo) {
        LogMgr.log(6, "000");
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= issueInfo.length) {
                z = true;
                break;
            }
            if (issueInfo[i] != 0) {
                LogMgr.log(6, "001");
                break;
            }
            i++;
        }
        LogMgr.log(6, "999 ret=" + z);
        return z;
    }

    private boolean isCarrierIdentifyCodeValid(byte[] issueInfo) {
        LogMgr.log(6, "000");
        boolean zIsValidContainerIssueInfo = AccessConfig.isValidContainerIssueInfo(issueInfo);
        LogMgr.log(6, "999 ret=" + zIsValidContainerIssueInfo);
        return zIsValidContainerIssueInfo;
    }

    public synchronized void onStartMfiClient(String walletAppId) {
        this.mWalletAppId = walletAppId;
    }

    public synchronized void onStopMfiClient() {
        this.mWalletAppId = null;
    }

    public synchronized String getWalletAppId() {
        return this.mWalletAppId;
    }
}
