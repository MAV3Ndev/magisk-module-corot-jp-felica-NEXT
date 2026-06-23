package com.felicanetworks.mfc;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFelica;
import com.felicanetworks.mfc.felica.FelicaRf;
import com.felicanetworks.mfc.felica.FelicaSe;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class IFelicaImpl extends IFelica.Stub {
    private static final String EXC_INVALID_TARGET = "The specified Target is invalid value.";
    public static final int INTERFACE_LA = 2;
    public static final int INTERFACE_LA_PREV_WIRED = 3;
    public static final int INTERFACE_LA_PREV_WIRELESS = 4;
    public static final int INTERFACE_WIRED = 0;
    public static final int INTERFACE_WIRELESS = 1;
    private static IFelicaImpl sMe = new IFelicaImpl();
    private FelicaResultInfoByteArray mPrevIcCode;
    private FelicaResultInfoByteArray mPrevIdm;
    private int mSystemCode;
    private IBinder mBinder = null;
    private int mTarget = 0;
    private IFelicaSeImpl mFelicaSeEntity = IFelicaSeImpl.getInstance();
    private IFelicaRfImpl mFelicaRfEntity = IFelicaRfImpl.getInstance();

    public static IFelicaImpl getInstance() {
        return sMe;
    }

    private IFelicaImpl() {
    }

    public synchronized void init(Context context, MfcListener mfcListener) throws IllegalArgumentException {
        this.mFelicaSeEntity.init(context, mfcListener);
        this.mFelicaRfEntity.init(context, mfcListener);
    }

    public FelicaSe getFelicaSe() {
        return this.mFelicaSeEntity.getFelica();
    }

    public FelicaRf getFelicaRf() {
        return this.mFelicaRfEntity.getFelica();
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo close() throws RemoteException {
        FelicaResultInfo felicaResultInfoClose = this.mFelicaRfEntity.close();
        if (felicaResultInfoClose.getExceptionType() != 0) {
            return felicaResultInfoClose;
        }
        FelicaResultInfo felicaResultInfoClose2 = this.mFelicaSeEntity.close();
        if (felicaResultInfoClose2.getExceptionType() != 0) {
            return felicaResultInfoClose2;
        }
        this.mTarget = 0;
        return felicaResultInfoClose2;
    }

    FelicaResultInfo doClose(boolean z) {
        FelicaResultInfo felicaResultInfoDoClose = this.mFelicaRfEntity.doClose(z);
        if (felicaResultInfoDoClose.getExceptionType() != 0) {
            return felicaResultInfoDoClose;
        }
        FelicaResultInfo felicaResultInfoDoClose2 = this.mFelicaSeEntity.doClose(z);
        if (felicaResultInfoDoClose2.getExceptionType() != 0) {
            return felicaResultInfoDoClose2;
        }
        this.mTarget = 0;
        return felicaResultInfoDoClose2;
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] iArr, int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getBlockCountInformation(iArr, i, i2);
            }
            return this.mFelicaRfEntity.getBlockCountInformation(iArr, i, i2);
        } catch (FelicaException e) {
            return new FelicaResultInfoBlockCountInformationArray(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoByteArray getContainerId(int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getContainerId(i, i2);
            }
            return this.mFelicaRfEntity.getContainerId(i, i2);
        } catch (FelicaException unused) {
            return new FelicaResultInfoByteArray(1, null, 3, 6);
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoByteArray getContainerIssueInformation(int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getContainerIssueInformation(i, i2);
            }
            return this.mFelicaRfEntity.getContainerIssueInformation(i, i2);
        } catch (FelicaException e) {
            return new FelicaResultInfoByteArray(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoByteArray getICCode() throws RemoteException {
        int i = this.mTarget;
        if (i == 0) {
            return this.mFelicaSeEntity.getIcCode();
        }
        if (i == 1) {
            return this.mFelicaRfEntity.getIcCode();
        }
        if (i == 3 || i == 4) {
            return this.mPrevIcCode;
        }
        return this.mFelicaSeEntity.getIcCode();
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoByteArray getIDm() throws RemoteException {
        int i = this.mTarget;
        if (i == 0) {
            return this.mFelicaSeEntity.getIdm();
        }
        if (i == 1) {
            return this.mFelicaRfEntity.getIdm();
        }
        if (i == 3 || i == 4) {
            return this.mPrevIdm;
        }
        return this.mFelicaSeEntity.getIdm();
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoInt getInterface() throws RemoteException {
        FelicaResultInfo felicaResultInfoCheckStatus;
        LogMgr.log(4, "%s", "000");
        int i = this.mTarget;
        if (i == 0) {
            felicaResultInfoCheckStatus = this.mFelicaSeEntity.checkStatus();
        } else {
            if (i != 1) {
                if (i == 2) {
                    return new FelicaResultInfoInt(1, null, 2, 3, 0, 0);
                }
                if (i == 3) {
                    return new FelicaResultInfoInt((Integer) 0);
                }
                if (i == 4) {
                    return new FelicaResultInfoInt((Integer) 1);
                }
                return new FelicaResultInfoInt(1, null, 2, 3, 0, 0);
            }
            felicaResultInfoCheckStatus = this.mFelicaRfEntity.checkStatus();
        }
        if (felicaResultInfoCheckStatus.getExceptionType() != 0) {
            return new FelicaResultInfoInt(felicaResultInfoCheckStatus.getExceptionType(), felicaResultInfoCheckStatus.getMessage(), felicaResultInfoCheckStatus.getId(), felicaResultInfoCheckStatus.getType(), felicaResultInfoCheckStatus.getStatusFlag1(), felicaResultInfoCheckStatus.getStatusFlag2());
        }
        return new FelicaResultInfoInt(Integer.valueOf(this.mTarget));
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoInt getKeyVersion(int i, int i2, int i3) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getKeyVersion(i, i2, i3);
            }
            return this.mFelicaRfEntity.getKeyVersion(i, i2, i3);
        } catch (FelicaException e) {
            return new FelicaResultInfoInt(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] iArr, int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getKeyVersionV2(iArr, i, i2);
            }
            return this.mFelicaRfEntity.getKeyVersionV2(iArr, i, i2);
        } catch (FelicaException e) {
            return new FelicaResultInfoKeyInformationArray(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoNodeInformation getNodeInformation(int i, int i2, int i3) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getNodeInformation(i, i2, i3);
            }
            return this.mFelicaRfEntity.getNodeInformation(i, i2, i3);
        } catch (FelicaException e) {
            return new FelicaResultInfoNodeInformation(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int i, int i2, int i3) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getPrivacyNodeInformation(i, i2, i3);
            }
            return new FelicaResultInfoNodeInformation(1, null, 2, 54);
        } catch (FelicaException e) {
            return new FelicaResultInfoNodeInformation(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoBoolean getRFSState() throws RemoteException {
        return this.mFelicaRfEntity.getRfsState();
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoInt getSystemCode() throws RemoteException {
        int i = this.mTarget;
        if (i == 0) {
            return this.mFelicaSeEntity.getSystemCode();
        }
        if (i == 1) {
            return this.mFelicaRfEntity.getSystemCode();
        }
        if (i == 3 || i == 4) {
            FelicaResultInfoInt felicaResultInfoInt = new FelicaResultInfoInt(Integer.valueOf(this.mSystemCode));
            return felicaResultInfoInt;
        }
        return this.mFelicaSeEntity.getSystemCode();
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoIntArray getSystemCodeList(int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.getSystemCodeList(i, i2);
            }
            return this.mFelicaRfEntity.getSystemCodeList(i, i2);
        } catch (FelicaException e) {
            return new FelicaResultInfoIntArray(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public synchronized FelicaResultInfo inactivateFelica() throws RemoteException {
        FelicaResultInfo felicaResultInfoInactivateFelica = this.mFelicaSeEntity.inactivateFelica();
        if (felicaResultInfoInactivateFelica.getExceptionType() != 0) {
            return felicaResultInfoInactivateFelica;
        }
        this.mFelicaRfEntity.doSetSelectTimeout(1000, false);
        this.mBinder = null;
        return felicaResultInfoInactivateFelica;
    }

    synchronized FelicaResultInfo doInactivateFelica(boolean z) {
        FelicaResultInfo felicaResultInfoDoInactivateFelica = this.mFelicaSeEntity.doInactivateFelica(z);
        if (felicaResultInfoDoInactivateFelica.getExceptionType() != 0) {
            return felicaResultInfoDoInactivateFelica;
        }
        this.mFelicaRfEntity.doSetSelectTimeout(1000, false);
        this.mBinder = null;
        return felicaResultInfoDoInactivateFelica;
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo open() throws RemoteException {
        if (this.mBinder == null) {
            return new FelicaResultInfo(1, null, 8, 5);
        }
        FelicaResultInfo felicaResultInfoOpen = this.mFelicaSeEntity.open();
        if (felicaResultInfoOpen.getExceptionType() != 0) {
            return felicaResultInfoOpen;
        }
        try {
            FelicaResultInfo felicaResultInfoOpen2 = this.mFelicaRfEntity.open(this.mBinder);
            if (felicaResultInfoOpen2.getExceptionType() != 0) {
                this.mFelicaSeEntity.close();
            }
            return felicaResultInfoOpen2;
        } catch (RemoteException e) {
            this.mFelicaSeEntity.close();
            throw e;
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoDataArray read(BlockList blockList, int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.read(blockList, i, i2);
            }
            return this.mFelicaRfEntity.read(blockList, i, i2);
        } catch (FelicaException e) {
            return new FelicaResultInfoDataArray(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo reset() throws RemoteException {
        FelicaResultInfo felicaResultInfoReset = this.mFelicaRfEntity.reset();
        if (felicaResultInfoReset.getExceptionType() != 0) {
            return felicaResultInfoReset;
        }
        FelicaResultInfo felicaResultInfoReset2 = this.mFelicaSeEntity.reset(true, false);
        if (felicaResultInfoReset2.getExceptionType() != 0) {
            if (felicaResultInfoReset2.getExceptionType() != 1 || felicaResultInfoReset2.getId() != 3 || felicaResultInfoReset2.getType() != 6 || !"Cannot change discovery-state.".equals(felicaResultInfoReset2.getMessage())) {
                return felicaResultInfoReset2;
            }
            if (!this.mFelicaSeEntity.isConnected()) {
                this.mTarget = 4;
            }
            return new FelicaResultInfo(felicaResultInfoReset2.getExceptionType(), null, felicaResultInfoReset2.getId(), felicaResultInfoReset2.getType());
        }
        this.mTarget = 0;
        return felicaResultInfoReset2;
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo select(int i) throws RemoteException {
        return selectWithTarget(0, i);
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo selectWithTarget(int i, int i2) throws RemoteException {
        FelicaResultInfo felicaResultInfoSelect;
        boolean z = false;
        boolean z2 = this.mFelicaSeEntity.checkStatus().getExceptionType() == 0 || this.mFelicaRfEntity.checkStatus().getExceptionType() == 0;
        if (i != 0 && i != 1) {
            return new FelicaResultInfo(32, EXC_INVALID_TARGET);
        }
        if (i == 0) {
            if (this.mFelicaRfEntity.isConnected()) {
                FelicaResultInfo felicaResultInfoReset = this.mFelicaRfEntity.reset();
                if (felicaResultInfoReset.getExceptionType() != 0) {
                    return felicaResultInfoReset;
                }
                z = true;
            }
            felicaResultInfoSelect = this.mFelicaSeEntity.select(i2);
            if (felicaResultInfoSelect.getExceptionType() == 0) {
                this.mTarget = i;
                this.mSystemCode = i2;
                this.mPrevIcCode = this.mFelicaSeEntity.getIcCode();
                this.mPrevIdm = this.mFelicaSeEntity.getIdm();
            } else if (felicaResultInfoSelect.getExceptionType() == 1 && felicaResultInfoSelect.getId() == 3 && felicaResultInfoSelect.getType() == 6 && "Cannot change discovery-state.".equals(felicaResultInfoSelect.getMessage()) && z) {
                this.mTarget = 4;
            } else if (z && this.mFelicaRfEntity.select(this.mSystemCode).getExceptionType() != 0) {
                this.mTarget = 4;
            }
        } else {
            if (this.mFelicaSeEntity.isConnected()) {
                FelicaResultInfo felicaResultInfoReset2 = this.mFelicaSeEntity.reset(false, true);
                if (felicaResultInfoReset2.getExceptionType() != 0) {
                    return felicaResultInfoReset2;
                }
                z = true;
            }
            FelicaResultInfo felicaResultInfoSelect2 = this.mFelicaRfEntity.select(i2);
            if (felicaResultInfoSelect2.getExceptionType() == 0) {
                this.mTarget = i;
                this.mSystemCode = i2;
                this.mPrevIcCode = this.mFelicaRfEntity.getIcCode();
                this.mPrevIdm = this.mFelicaRfEntity.getIdm();
            } else if (felicaResultInfoSelect2.getExceptionType() == 1 && felicaResultInfoSelect2.getId() == 3 && felicaResultInfoSelect2.getType() == 6 && "Cannot change discovery-state.".equals(felicaResultInfoSelect2.getMessage()) && z) {
                if (!z2) {
                    this.mTarget = 2;
                } else {
                    this.mTarget = 3;
                }
            } else if (z && this.mFelicaSeEntity.reconnect(z2, this.mSystemCode).getExceptionType() != 0) {
                if (!z2) {
                    this.mTarget = 2;
                } else {
                    this.mTarget = 3;
                }
            }
            felicaResultInfoSelect = felicaResultInfoSelect2;
        }
        return "Cannot change discovery-state.".equals(felicaResultInfoSelect.getMessage()) ? new FelicaResultInfo(felicaResultInfoSelect.getExceptionType(), null, felicaResultInfoSelect.getId(), felicaResultInfoSelect.getType()) : felicaResultInfoSelect;
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo selectWithCid(int i, String str) throws RemoteException {
        boolean z;
        if (this.mFelicaRfEntity.isConnected()) {
            FelicaResultInfo felicaResultInfoReset = this.mFelicaRfEntity.reset();
            if (felicaResultInfoReset.getExceptionType() != 0) {
                return felicaResultInfoReset;
            }
            z = true;
        } else {
            z = false;
        }
        FelicaResultInfo felicaResultInfoSelect = this.mFelicaSeEntity.select(i, str);
        if (felicaResultInfoSelect.getExceptionType() == 0) {
            this.mTarget = 0;
            this.mSystemCode = i;
            this.mPrevIcCode = this.mFelicaSeEntity.getIcCode();
            this.mPrevIdm = this.mFelicaSeEntity.getIdm();
        } else if (felicaResultInfoSelect.getExceptionType() == 1 && felicaResultInfoSelect.getId() == 3 && felicaResultInfoSelect.getType() == 6 && "Cannot change discovery-state.".equals(felicaResultInfoSelect.getMessage()) && z) {
            this.mTarget = 4;
        } else if (z && this.mFelicaRfEntity.select(this.mSystemCode).getExceptionType() != 0) {
            this.mTarget = 4;
        }
        return "Cannot change discovery-state.".equals(felicaResultInfoSelect.getMessage()) ? new FelicaResultInfo(felicaResultInfoSelect.getExceptionType(), null, felicaResultInfoSelect.getId(), felicaResultInfoSelect.getType()) : felicaResultInfoSelect;
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo setNodeCodeSize(int i, int i2, int i3) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.setNodeCodeSize(i, i2, i3);
            }
            return this.mFelicaRfEntity.setNodeCodeSize(i, i2, i3);
        } catch (FelicaException e) {
            return new FelicaResultInfo(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo write(BlockDataList blockDataList, int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.write(blockDataList, i, i2);
            }
            return this.mFelicaRfEntity.write(blockDataList, i, i2);
        } catch (FelicaException e) {
            return new FelicaResultInfo(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException {
        try {
            checkListenActive();
            if (this.mFelicaSeEntity.isConnected()) {
                boolean zIsSelected = this.mFelicaSeEntity.isSelected();
                FelicaResultInfo felicaResultInfoReset = this.mFelicaSeEntity.reset(false, true);
                if (felicaResultInfoReset.getExceptionType() != 0) {
                    return felicaResultInfoReset;
                }
                FelicaResultInfo felicaResultInfoPush = this.mFelicaRfEntity.push(pushSegmentParcelableWrapper);
                FelicaResultInfo felicaResultInfoReconnect = this.mFelicaSeEntity.reconnect(zIsSelected, this.mSystemCode);
                if (felicaResultInfoReconnect.getExceptionType() != 1 || felicaResultInfoReconnect.getId() != 3 || felicaResultInfoReconnect.getType() != 6 || !"Cannot change discovery-state.".equals(felicaResultInfoReconnect.getMessage())) {
                    return felicaResultInfoPush;
                }
                if (zIsSelected) {
                    this.mTarget = 3;
                } else {
                    this.mTarget = 2;
                }
                return new FelicaResultInfo(felicaResultInfoReconnect.getExceptionType(), null, felicaResultInfoReconnect.getId(), felicaResultInfoReconnect.getType());
            }
            return this.mFelicaRfEntity.push(pushSegmentParcelableWrapper);
        } catch (FelicaException unused) {
            return new FelicaResultInfo(1, null, 3, 6);
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.setPrivacy(privacySettingDataArr, i, i2);
            }
            return new FelicaResultInfo(1, null, 2, 54);
        } catch (FelicaException e) {
            return new FelicaResultInfo(1, null, e.getID(), e.getType());
        }
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo activateFelica(String[] strArr, IFelicaEventListener iFelicaEventListener) throws RemoteException {
        return activateFelica(strArr, iFelicaEventListener, iFelicaEventListener.asBinder());
    }

    public synchronized FelicaResultInfo activateFelica(String[] strArr, IFelicaEventListener iFelicaEventListener, IBinder iBinder) throws RemoteException {
        FelicaResultInfo felicaResultInfoActivateFelica;
        felicaResultInfoActivateFelica = this.mFelicaSeEntity.activateFelica(strArr, iFelicaEventListener, iBinder);
        if (felicaResultInfoActivateFelica.getExceptionType() == 0) {
            this.mBinder = iBinder;
        }
        return felicaResultInfoActivateFelica;
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws RemoteException {
        return this.mFelicaRfEntity.setPushNotificationListener(iFelicaPushAppNotificationListener, str);
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo checkOnlineAccess() throws RemoteException {
        return this.mFelicaSeEntity.checkOnlineAccess();
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo setSelectTimeout(int i) throws RemoteException {
        FelicaResultInfo selectTimeout = this.mFelicaSeEntity.setSelectTimeout(i);
        return selectTimeout.getExceptionType() != 0 ? selectTimeout : this.mFelicaRfEntity.doSetSelectTimeout(i, false);
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoInt getSelectTimeout() throws RemoteException {
        if (this.mTarget == 0) {
            return this.mFelicaSeEntity.getSelectTimeout();
        }
        return this.mFelicaRfEntity.getSelectTimeout();
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfo cancelOffline() throws RemoteException {
        FelicaResultInfo felicaResultInfoCancelOffline = this.mFelicaSeEntity.cancelOffline();
        return felicaResultInfoCancelOffline.getExceptionType() != 0 ? felicaResultInfoCancelOffline : this.mFelicaRfEntity.doCancelOffline(false);
    }

    @Override // com.felicanetworks.mfc.IFelica
    public FelicaResultInfoByteArray executeFelicaCommand(byte[] bArr, int i, int i2) throws RemoteException {
        try {
            checkListenActive();
            if (this.mTarget == 0) {
                return this.mFelicaSeEntity.executeFelicaCommand(bArr, i, i2);
            }
            return new FelicaResultInfoByteArray(1, null, 2, 54);
        } catch (FelicaException unused) {
            return new FelicaResultInfoByteArray(1, null, 3, 6);
        }
    }

    void clearTarget() {
        this.mTarget = 0;
    }

    private void checkListenActive() throws FelicaException {
        int i = this.mTarget;
        if (i == 2) {
            throw new FelicaException(2, 3);
        }
        if (i == 3 || i == 4) {
            throw new FelicaException(3, 6);
        }
    }
}
