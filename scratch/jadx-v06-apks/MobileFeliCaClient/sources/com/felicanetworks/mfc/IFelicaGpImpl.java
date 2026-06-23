package com.felicanetworks.mfc;

import android.content.Context;
import android.os.IBinder;
import com.felicanetworks.mfc.felica.FelicaGp;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class IFelicaGpImpl {
    private static IFelicaGpImpl sMe = new IFelicaGpImpl();
    private FelicaGp mFelicaEntity;

    public static IFelicaGpImpl getInstance() {
        return sMe;
    }

    private IFelicaGpImpl() {
    }

    public synchronized void init(Context context, MfcListener mfcListener) throws IllegalArgumentException {
        if (context == null || mfcListener == null) {
            throw new IllegalArgumentException();
        }
        this.mFelicaEntity = FelicaGp.getInstance();
        this.mFelicaEntity.setContext(context);
        this.mFelicaEntity.setMfcListener(mfcListener);
    }

    public FelicaGp getFelica() {
        return this.mFelicaEntity;
    }

    public FelicaResultInfo close() {
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

    public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] iArr, int i, int i2) {
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

    public FelicaResultInfoByteArray getContainerId(int i, int i2) {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getContainerId(i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfoByteArray getContainerIssueInformation(int i, int i2) {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getContainerIssueInformation(i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfoByteArray getIcCode() {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getIcCode() : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfoByteArray getIdm() {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.getIdm() : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfoInt getKeyVersion(int i, int i2, int i3) {
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

    public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] iArr, int i, int i2) {
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

    public FelicaResultInfoNodeInformation getNodeInformation(int i, int i2, int i3) {
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

    public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int i, int i2, int i3) {
        try {
            return new FelicaResultInfoNodeInformation(this.mFelicaEntity != null ? this.mFelicaEntity.getPrivacyNodeInformation(i, i2, i3) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoNodeInformation(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoNodeInformation(32, e2.getMessage());
        }
    }

    public FelicaResultInfoInt getSystemCode() {
        try {
            return new FelicaResultInfoInt(Integer.valueOf(this.mFelicaEntity != null ? this.mFelicaEntity.getSystemCode() : 0));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfoIntArray getSystemCodeList(int i, int i2) {
        try {
            return new FelicaResultInfoIntArray(this.mFelicaEntity != null ? this.mFelicaEntity.getSystemCodeList(i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoIntArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfo inactivateFelica() {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.inactivateFelica();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo doInactivateFelica(boolean z) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.doInactivateFelica(z);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfo open() {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.open();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfoDataArray read(BlockList blockList, int i, int i2) {
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

    public FelicaResultInfo reset() {
        return reset(true, false);
    }

    FelicaResultInfo reset(boolean z, boolean z2) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.reset(z, z2);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfo select(int i) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.select(i);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            FelicaGp felicaGp = this.mFelicaEntity;
            if (felicaGp != null) {
                felicaGp.openPreviousChannel();
            }
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    public FelicaResultInfo select(int i, String str) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.select(i, str);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            FelicaGp felicaGp = this.mFelicaEntity;
            if (felicaGp != null) {
                felicaGp.openPreviousChannel();
            }
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    public FelicaResultInfo selectInner(int i, int i2) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.selectInner(i, i2);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            FelicaGp felicaGp = this.mFelicaEntity;
            if (felicaGp != null) {
                felicaGp.openPreviousChannel();
            }
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    public FelicaResultInfo setNodeCodeSize(int i, int i2, int i3) {
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

    public FelicaResultInfo write(BlockDataList blockDataList, int i, int i2) {
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

    public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.setPrivacy(privacySettingDataArr, i, i2);
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

    public FelicaResultInfo activateFelica(String[] strArr, IFelicaEventListener iFelicaEventListener, IBinder iBinder) {
        try {
            if (iBinder == null) {
                throw new FelicaException(1, 47);
            }
            if (iFelicaEventListener == null) {
                throw new IllegalArgumentException("The specified parameter is invalid.");
            }
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.activateFelica(strArr, iFelicaEventListener, iBinder);
            }
            LogMgr.log(3, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            if (e.getType() == 39) {
                LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d otherAppPID = %s", "801", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), e.getOtherAppInfo());
                return new FelicaResultInfo(1, e.getMessage(), e.getID(), 39, e.getOtherAppInfo());
            }
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "802", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), e.getOtherAppInfo());
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    public FelicaResultInfo checkOnlineAccess() {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.checkOnlineAccess();
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfo setSelectTimeout(int i) {
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

    public FelicaResultInfoInt getSelectTimeout() {
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

    public FelicaResultInfo cancelOffline() {
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

    public FelicaResultInfoByteArray executeFelicaCommand(byte[] bArr, int i, int i2) {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.executeFelicaCommand(bArr, i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "catch FelicaException id = %d type = %d", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
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

    public boolean isConnected() {
        FelicaGp felicaGp = this.mFelicaEntity;
        if (felicaGp != null) {
            return felicaGp.isConnected();
        }
        return false;
    }

    FelicaResultInfoByteArray executeFelicaCommandInner(byte[] bArr, int i, int i2) {
        try {
            return new FelicaResultInfoByteArray(this.mFelicaEntity != null ? this.mFelicaEntity.executeFelicaCommandInner(bArr, i, i2) : null);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo resetInner(boolean z, boolean z2, boolean z3) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.resetInner(z, z2, z3);
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

    boolean isSelected() {
        try {
            if (this.mFelicaEntity == null) {
                return false;
            }
            this.mFelicaEntity.checkSelected();
            return true;
        } catch (FelicaException unused) {
            return false;
        }
    }

    FelicaResultInfo reconnect(boolean z, int i) {
        try {
            if (this.mFelicaEntity != null) {
                this.mFelicaEntity.reconnect(z, i);
            }
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }
}
