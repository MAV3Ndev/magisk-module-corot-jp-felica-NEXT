package com.felicanetworks.mfc;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFelicaRf;
import com.felicanetworks.mfc.felica.FelicaRf;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class IFelicaRfImpl extends IFelicaRf.Stub {
    private static IFelicaRfImpl sMe = new IFelicaRfImpl();
    protected FelicaRf mFelicaEntity;

    public static IFelicaRfImpl getInstance() {
        return sMe;
    }

    protected IFelicaRfImpl() {
    }

    public synchronized void init(Context context, MfcListener mfcListener) throws IllegalArgumentException {
        if (context == null || mfcListener == null) {
            throw new IllegalArgumentException();
        }
        this.mFelicaEntity = FelicaRf.getInstance();
        this.mFelicaEntity.setContext(context);
        this.mFelicaEntity.setMfcListener(mfcListener);
    }

    public FelicaRf getFelica() {
        return this.mFelicaEntity;
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo close() throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.close();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo reset() throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.reset();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfo doClose(boolean z) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.doClose(z);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] iArr, int i, int i2) throws RemoteException {
        try {
            return new FelicaResultInfoBlockCountInformationArray(this.mFelicaEntity != null ? this.mFelicaEntity.getBlockCountInformation(iArr, i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoBlockCountInformationArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoBlockCountInformationArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoByteArray getContainerId(int i, int i2) throws RemoteException {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getContainerId(i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoByteArray getContainerIssueInformation(int i, int i2) throws RemoteException {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getContainerIssueInformation(i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoByteArray getIcCode() throws RemoteException {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getIcCode() : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoByteArray getIdm() throws RemoteException {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getIdm() : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoInt getKeyVersion(int i, int i2, int i3) throws RemoteException {
        try {
            return new FelicaResultInfoInt(Integer.valueOf(this.mFelicaEntity != null ? this.mFelicaEntity.getKeyVersion(i, i2, i3) : 0));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoInt(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] iArr, int i, int i2) throws RemoteException {
        try {
            return new FelicaResultInfoKeyInformationArray(this.mFelicaEntity != null ? this.mFelicaEntity.getKeyVersionV2(iArr, i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoKeyInformationArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoKeyInformationArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoNodeInformation getNodeInformation(int i, int i2, int i3) throws RemoteException {
        try {
            return new FelicaResultInfoNodeInformation(this.mFelicaEntity != null ? this.mFelicaEntity.getNodeInformation(i, i2, i3) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoNodeInformation(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoNodeInformation(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoBoolean getRfsState() throws RemoteException {
        try {
            return new FelicaResultInfoBoolean(Boolean.valueOf(this.mFelicaEntity != null ? this.mFelicaEntity.getRfsState() : false));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoBoolean(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoInt getSystemCode() throws RemoteException {
        try {
            return new FelicaResultInfoInt(Integer.valueOf(this.mFelicaEntity != null ? this.mFelicaEntity.getSystemCode() : 0));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoIntArray getSystemCodeList(int i, int i2) throws RemoteException {
        try {
            return new FelicaResultInfoIntArray(this.mFelicaEntity != null ? this.mFelicaEntity.getSystemCodeList(i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoIntArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo open(IBinder iBinder) throws RemoteException {
        try {
            if (iBinder == null) {
                throw new FelicaException(1, 47);
            }
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.open(iBinder);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoDataArray read(BlockList blockList, int i, int i2) throws RemoteException {
        try {
            return new FelicaResultInfoDataArray(this.mFelicaEntity != null ? this.mFelicaEntity.read(blockList, i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoDataArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoDataArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo select(int i) throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.select(i);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo setNodeCodeSize(int i, int i2, int i3) throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.setNodeCodeSize(i, i2, i3);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo write(BlockDataList blockDataList, int i, int i2) throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.write(blockDataList, i, i2);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                if (pushSegmentParcelableWrapper == null) {
                    throw new IllegalArgumentException();
                }
                this.mFelicaEntity.push(pushSegmentParcelableWrapper.getPushSegment());
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.setPushNotificationListener(iFelicaPushAppNotificationListener, str);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo setSelectTimeout(int i) throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.setSelectTimeout(i);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo doSetSelectTimeout(int i, boolean z) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.doSetSelectTimeout(i, z);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfoInt getSelectTimeout() throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                return new FelicaResultInfoInt(Integer.valueOf(this.mFelicaEntity.getSelectTimeout()));
            }
            return null;
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public FelicaResultInfo cancelOffline() throws RemoteException {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.cancelOffline();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo doCancelOffline(boolean z) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.doCancelOffline(z);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfoByteArray executeFelicaCommandInner(byte[] bArr, int i, int i2) {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.executeFelicaCommandInner(bArr, i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo connectInner(int i) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.connectInner(i);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.IFelicaRf
    public boolean isConnected() throws RemoteException {
        FelicaRf felicaRf = this.mFelicaEntity;
        if (felicaRf != null) {
            return felicaRf.isConnected();
        }
        return false;
    }

    public FelicaResultInfoBoolean isOpen(boolean z) {
        boolean zIsOpen;
        try {
            if (this.mFelicaEntity != null) {
                if (z) {
                    this.mFelicaEntity.checkPidUidSync();
                }
                zIsOpen = this.mFelicaEntity.isOpen();
            } else {
                zIsOpen = false;
            }
            return new FelicaResultInfoBoolean(Boolean.valueOf(zIsOpen));
        } catch (FelicaException e) {
            return new FelicaResultInfoBoolean(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo resetInner() {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.resetInner();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo cancelOfflineInner() {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.cancelOfflineInner();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo checkStatus() {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.checkStatus();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }
}
