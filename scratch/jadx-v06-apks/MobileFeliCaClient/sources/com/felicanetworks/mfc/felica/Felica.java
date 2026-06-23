package com.felicanetworks.mfc.felica;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Binder;
import android.os.Process;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FSCAdapter;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.MfcListener;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.felica.access_control.AccessControllerException;
import com.felicanetworks.mfc.felica.offlineimpl.ChipController;
import com.felicanetworks.mfc.felica.offlineimpl.OfflineException;
import com.felicanetworks.mfc.felica.offlineimpl.SystemInfo;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class Felica {
    public static final int DEFAULT_RETRY_COUNT = 0;
    public static final int DEFAULT_TIMEOUT = 1000;
    protected static final String EXC_INVALID_BLOCK_DATA_LIST = "The specified BlockDataList is null or empty.";
    protected static final String EXC_INVALID_BLOCK_LIST = "The specified BlockList is null or empty.";
    protected static final String EXC_INVALID_COMMAND = "The specified Command is null or invalid size.";
    public static final String EXC_INVALID_LISTENER = "The specified parameter is invalid.";
    protected static final String EXC_INVALID_NODECODESIZE = "The specified NodeCodeSize is invalid value.";
    protected static final String EXC_INVALID_NODE_CODE_LIST = "The specified parameter is invalid.";
    protected static final String EXC_INVALID_PRIVACY_SETTING_DATA_LIST = "The specified parameter is invalid.";
    protected static final String EXC_INVALID_SET_PUSH_LISTENER_PRM = "The specified parameter is invalid.";
    protected static final String EXC_INVALID_SYSTEM_CODE = "The specified System Code is out of range.";
    protected static final String EXC_INVALID_TARGET = "The specified Target is invalid value.";
    protected static final String EXC_MAX_SIZE_PERMIT_LIST = "The size of permit list exceeds the maximum value.";
    public static final int MAX_PERMIT_LIST_SIZE = 50;
    public static final int MAX_RETRY_COUNT = 10;
    public static final int MAX_TIMEOUT = 60000;
    public static final int MIN_RETRY_COUNT = 0;
    public static final int MIN_TIMEOUT = 0;
    protected ChipController mChipController;
    protected Context mContext;
    protected boolean mFelicaCloseInFscStarting;
    protected FSCAdapter mFscAdapter;
    protected boolean mFscStarting;
    protected MfcListener mMfcListener;
    protected boolean mOpened;
    protected boolean mSelected;
    protected SystemInfo mSystemInfo;
    protected int mSelectTimeout = 1000;
    protected int mTimeout = 1000;
    protected int mRetryCount = 0;
    protected int mNodeCodeSize = 2;

    public abstract void close() throws FelicaException;

    protected abstract FelicaException convException(OfflineException offlineException, int i, int i2, int[] iArr);

    public abstract void select(int i) throws FelicaException, IllegalArgumentException;

    public abstract void setContext(Context context);

    public abstract void write(BlockDataList blockDataList, int i, int i2) throws FelicaException, IllegalArgumentException;

    public class FelicaAppInfo {
        int mPid;
        int mUid;

        public FelicaAppInfo() {
        }

        public int getUid() {
            return this.mUid;
        }

        public void setUid(int i) {
            this.mUid = i;
        }

        public int getPid() {
            return this.mPid;
        }

        public void setPid(int i) {
            this.mPid = i;
        }
    }

    Felica() {
    }

    protected int getTimeout() {
        return this.mTimeout;
    }

    protected void setTimeout(int i) {
        if (i < 0) {
            this.mTimeout = 0;
        } else if (i > 60000) {
            this.mTimeout = 60000;
        } else {
            this.mTimeout = i;
        }
    }

    protected int getRetryCount() {
        return this.mRetryCount;
    }

    protected void setRetryCount(int i) {
        if (i < 0) {
            this.mRetryCount = 0;
        } else if (i > 10) {
            this.mRetryCount = 10;
        } else {
            this.mRetryCount = i;
        }
    }

    protected void doOpen() throws FelicaException {
        this.mFscAdapter = null;
        this.mSelected = false;
        try {
            this.mChipController.open();
        } catch (OfflineException e) {
            this.mChipController.close();
            if (e.getType() == 8) {
                LogMgr.log(2, "%s OfflineException.TYPE_OFFLINE_CANCELED_OCCURRED", "700");
                throw new FelicaException(1, 8);
            }
            LogMgr.log(1, "%s FelicaException failed open == true", "800");
            throw convException(e, 8);
        } catch (Exception unused) {
            this.mChipController.close();
            LogMgr.log(1, "%s Exception failed open == true", "801");
            throw new FelicaException(1, 8);
        }
    }

    protected void doClose() throws FelicaException {
        this.mOpened = false;
        this.mFscAdapter = null;
        this.mSelected = false;
        this.mNodeCodeSize = 2;
        this.mTimeout = 1000;
        this.mRetryCount = 0;
        this.mSelectTimeout = 1000;
    }

    protected int getSystemCode() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        return this.mSystemInfo.getSystemCode();
    }

    protected byte[] getIdm() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        return this.mSystemInfo.getIdm();
    }

    protected byte[] getIcCode() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        return new byte[]{this.mSystemInfo.getPmm()[0], this.mSystemInfo.getPmm()[1]};
    }

    protected int getKeyVersion(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In serviceCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        try {
            int i4 = this.mChipController.requestService(this.mNodeCodeSize, this.mSystemInfo.getIdm(), new int[]{i}, getTimeout(), getRetryCount())[0];
            if (i4 != 65535) {
                return i4;
            }
            LogMgr.log(1, "%s FelicaException.TYPE_SERVICE_NOT_FOUND", "800");
            throw new FelicaException(4, 11);
        } catch (FelicaException e) {
            LogMgr.log(1, "%s FelicaException", "800");
            throw e;
        } catch (OfflineException e2) {
            LogMgr.log(1, "%s OfflineException.TYPE_GET_KEY_VERSION_FAILED", "801");
            throw convException(e2, 10);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "804");
            throw new FelicaException(1, 10);
        }
    }

    protected KeyInformation[] getKeyVersionV2(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCode = %d timeout = %d retryCount = %d", "000", iArr, Integer.valueOf(i), Integer.valueOf(i2));
        try {
            return this.mChipController.requestServiceV2(this.mSystemInfo.getIdm(), iArr, getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException.TYPE_GET_KEY_VERSION_V2_FAILED", "801");
            throw this.convException(e, 28, 64, new int[0]);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "804");
            throw new FelicaException(1, 64);
        }
    }

    protected byte[] getContainerIssueInformation(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        try {
            return this.mChipController.getContainerIssueInfo(this.mSystemInfo.getIdm(), getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException.TYPE_GET_CONTAINER_ISSUE_INFORMATION_FAILED", "800");
            throw this.convException(e, 29);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "803");
            throw new FelicaException(1, 29);
        }
    }

    protected void setNodeCodeSize(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCodeSize = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        try {
            this.mChipController.setParameter(this.mSystemInfo.getIdm(), i, getTimeout(), getRetryCount());
            this.mNodeCodeSize = i;
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException.TYPE_SET_NODECODESIZE_FAILED", "801");
            throw convException(e, 7, 28, new int[0]);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "805");
            throw new FelicaException(1, 28);
        }
    }

    protected Data[] read(BlockList blockList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockList = %s timeout = %d retryCount = %d", "000", blockList, Integer.valueOf(i), Integer.valueOf(i2));
        try {
            return this.mChipController.readWithoutEncryption(this.mNodeCodeSize, this.mSystemInfo.getIdm(), blockList, getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            if (e.getType() == 2) {
                throw new IllegalArgumentException();
            }
            LogMgr.log(1, "%s OfflineException", "801");
            throw this.convException(e, 5, 14, new int[]{83, 13, 166, 11, 168, 12});
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "809");
            throw new FelicaException(1, 14);
        }
    }

    public synchronized void setTcapClient(FSCAdapter fSCAdapter) {
        this.mFscAdapter = fSCAdapter;
        this.mFscStarting = false;
        this.mFelicaCloseInFscStarting = false;
    }

    public synchronized void checkOpened() throws FelicaException {
        checkOpenedNosync();
    }

    protected void checkOpenedNosync() throws FelicaException {
        if (!this.mOpened) {
            throw new FelicaException(2, 1);
        }
    }

    public synchronized void checkClosedinStarting() throws FelicaException {
        if (this.mFelicaCloseInFscStarting) {
            throw new FelicaException(2, 1);
        }
    }

    public void checkSelected() throws FelicaException {
        if (!this.mSelected) {
            throw new FelicaException(2, 3);
        }
    }

    protected int[] getSystemCodeList(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        try {
            return this.mChipController.requestSystemCode(this.mSystemInfo.getIdm(), getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException", "800");
            throw this.convException(e, 45);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "801");
            throw new FelicaException(1, 45);
        }
    }

    protected NodeInformation getNodeInformation(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        try {
            return this.mChipController.requestCodeList(this.mNodeCodeSize, this.mSystemInfo.getIdm(), i, getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException", "800");
            throw this.convException(e, 9, 34, new int[]{166, 11});
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "801");
            throw new FelicaException(1, 34);
        }
    }

    protected BlockCountInformation[] getBlockCountInformation(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        try {
            return this.mChipController.requestBlockInformationEx(this.mNodeCodeSize, this.mSystemInfo.getIdm(), iArr, getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException", "801");
            throw this.convException(e, 13, 43, new int[0]);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 43);
        }
    }

    protected byte[] getContainerId(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        try {
            return this.mChipController.getContainerId(getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException", "800");
            throw this.convException(e, 46);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "801");
            throw new FelicaException(1, 46);
        }
    }

    protected FelicaException convException(OfflineException offlineException, int i) {
        return convException(offlineException, 1, i, null);
    }

    protected FelicaException convException(AccessControllerException accessControllerException, int i) {
        int type = accessControllerException.getType();
        if (type == 0) {
            return new FelicaException(12, 32);
        }
        if (type == 1) {
            return new FelicaException(12, 38);
        }
        if (type == 2) {
            return new FelicaException(12, 50);
        }
        return new FelicaException(12, i);
    }

    public synchronized void setMfcListener(MfcListener mfcListener) {
        if (mfcListener != null) {
            this.mMfcListener = mfcListener;
        }
    }

    public synchronized Context getContext() {
        return this.mContext;
    }

    protected boolean isServiceExisted(int i, int i2) {
        return isServiceExisted(getContext(), i, i2);
    }

    public boolean isServiceExisted(Context context, int i, int i2) {
        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) context.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i3 = 0; i3 < runningServices.size(); i3++) {
            ActivityManager.RunningServiceInfo runningServiceInfo = runningServices.get(i3);
            if (runningServiceInfo.pid == i && runningServiceInfo.uid == i2) {
                return true;
            }
        }
        return false;
    }

    protected boolean isAppExisted(int i, int i2) {
        return isAppExisted(getContext(), i, i2);
    }

    public boolean isAppExisted(Context context, int i, int i2) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses.size() <= 0) {
            return false;
        }
        for (int i3 = 0; i3 < runningAppProcesses.size(); i3++) {
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i3);
            if (runningAppProcessInfo.pid == i && runningAppProcessInfo.uid == i2) {
                return true;
            }
        }
        return false;
    }

    protected void setSelectTimeout(int i) throws FelicaException {
        if (i < 0) {
            this.mSelectTimeout = 0;
        } else if (i > 60000) {
            this.mSelectTimeout = 60000;
        } else {
            this.mSelectTimeout = i;
        }
    }

    protected int getSelectTimeout() throws FelicaException {
        return this.mSelectTimeout;
    }

    public void cancelOffline() throws FelicaException {
        ChipController chipController = this.mChipController;
        if (chipController == null) {
            return;
        }
        chipController.cancelOffline();
        synchronized (this) {
            this.mChipController.finishCancel();
        }
    }

    protected byte[] executeFelicaCommand(byte[] bArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        try {
            return this.mChipController.executeFelicaCommand(bArr, getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "OfflineException");
            throw this.convException(e, 63);
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "Exception");
            throw new FelicaException(1, 63);
        }
    }

    public synchronized void connectInner(int i) throws FelicaException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        checkOpened();
        try {
            this.mChipController.connect(i);
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException", "802");
            throw convException(e, 9);
        }
    }

    public synchronized byte[] executeFelicaCommandInner(byte[] bArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        if (bArr == null || bArr.length <= 0 || bArr.length > 255) {
            LogMgr.log(1, "invalid Command");
            throw new FelicaException(1, 63);
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        try {
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "OfflineException");
            throw convException(e, 63);
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "Exception");
            throw new FelicaException(1, 63);
        }
        return this.mChipController.executeFelicaCommandInner(bArr, getTimeout(), getRetryCount());
    }

    protected void resetInner(boolean z, boolean z2, boolean z3) throws FelicaException {
        try {
            this.mChipController.reset(z, z2, z3);
            this.mNodeCodeSize = 2;
            this.mSelected = false;
            this.mSystemInfo = null;
        } catch (OfflineException e) {
            if (e.getType() == 8) {
                throw new FelicaException(1, 44);
            }
            LogMgr.log(1, "%s OfflineException", "801");
            throw convException(e, 44);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 44);
        }
    }

    public synchronized boolean isConnected() {
        return this.mChipController.isConnected();
    }
}
