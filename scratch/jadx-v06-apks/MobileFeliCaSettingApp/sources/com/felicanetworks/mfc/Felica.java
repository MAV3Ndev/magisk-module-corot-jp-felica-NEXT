package com.felicanetworks.mfc;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFelica;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiClient;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class Felica extends Service {
    private static final int APP_CODE_LENGTH = 6;
    private static final String AUTHORITY = "com.felicanetworks.mfc.provider.FelicaContentProvider";
    private static final int CID_LENGTH = 63;
    private static final String COMMON_FILE_NAME = "common.cfg";
    private static final String[] COMMON_FILE_PATHS = {"/product/etc/felica/", "/vendor/etc/felica/", "/system/etc/felica/"};
    static final int DEFAULT_BIND_TIMEOUT = 10000;
    public static final int DEFAULT_RETRY_COUNT = 0;
    public static final int DEFAULT_TIMEOUT = 1000;
    private static final String EXC_INVALID_BLOCK_DATA_LIST = "The specified BlockDataList is null or empty.";
    private static final String EXC_INVALID_BLOCK_LIST = "The specified BlockList is null or empty.";
    private static final String EXC_INVALID_CID = "The specified CID is null or invalid value.";
    private static final String EXC_INVALID_COMMAND = "The specified Command is null or invalid size.";
    private static final String EXC_INVALID_LISTENER = "The specified Listener is null.";
    private static final String EXC_INVALID_NODECODESIZE = "The specified NodeCodeSize is invalid value.";
    private static final String EXC_INVALID_NODE_CODE_LIST = "The specified parameter is invalid.";
    private static final String EXC_INVALID_PRIVACY_SETTING_DATA_LIST = "The specified parameter is invalid.";
    private static final String EXC_INVALID_PUSH_SEGMENT_DATA_LIST = "The specified parameter is invalid.";
    private static final String EXC_INVALID_SET_PUSH_LISTENER_PRM = "The specified parameter is invalid.";
    private static final String EXC_INVALID_SYSTEM_CODE = "The specified System Code is out of range.";
    private static final String EXC_INVALID_TARGET = "The specified Target is invalid value.";
    private static final String EXC_MAX_SIZE_PERMIT_LIST = "The size of permit list exceeds the maximum value.";
    private static final int GET_SYSTEM_CODE_WILD1 = 65535;
    private static final int GET_SYSTEM_CODE_WILD2 = 65280;
    private static final int GET_SYSTEM_CODE_WILD3 = 255;
    public static final int INTERFACE_WIRED = 0;
    public static final int INTERFACE_WIRELESS = 1;
    private static final String INVALID_CID_0 = "000000000000000000000000000000000000000000000000000000000000000";
    private static final String INVALID_CID_F = "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
    private static final int MAJOR_VERSION = 0;
    private static final int MAX_PACKET_DATA_LENGTH = 254;
    public static final int MAX_PERMIT_LIST_SIZE = 50;
    public static final int MAX_RETRY_COUNT = 10;
    private static final int MAX_SYSTEM_CODE = 65535;
    public static final int MAX_TIMEOUT = 60000;
    private static final String MENU_APP_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    private static final String MFC_ADAPTER_CLASS_NAME = "com.felicanetworks.mfc.FelicaAdapter";
    private static final String MFC_PACKAGE_NAME = "com.felicanetworks.mfc";
    private static final String MFI_CLIENT_SERVICE_CLASS_NAME = "com.felicanetworks.mfc.mfi.FelicaAdapter";
    private static final String MFI_CLIENT_VERSION_RESOURCE_NAME = "mfi_client_version";
    private static final String MFI_CLIENT_VERSION_RESOURCE_TYPE = "string";
    public static final String MFI_PERMIT = "Y29tLmZlbGljYW5ldHdvcmtzLm1mYy5tZmkuTWZpQ2xpZW50";
    public static final int MIN_RETRY_COUNT = 0;
    private static final int MIN_SYSTEM_CODE = 0;
    public static final int MIN_TIMEOUT = 0;
    public static final int NODECODESIZE_2 = 2;
    public static final int NODECODESIZE_4 = 4;
    private static final int SUPPORTED_VERSION_MIN = 5;
    private static final String VERSION_DELIMITER = "\\.";
    static int bindTimeout = 10000;
    private FelicaEventListener felicaEventListener;
    private int mRetryCount;
    private int mTimeout;
    private String[] permitList;
    private PushAppNotificationListenerStub pushAppNotificationHooker;
    private BindTimerHandler bindTimerHandler = new BindTimerHandler(Looper.myLooper());
    private MfcConnection connectionHooker = new MfcConnection();
    private IFelicaEventListener iFelicaEventListener = new FelicaEventListenerStub();
    private IFelica felica = null;
    private final IBinder mBinder = new LocalBinder();
    private MfiClientAccess mMfiClientAccess = null;
    private MfiClient mfiClient = null;

    private interface Contents {
        public static final Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfc.provider.FelicaContentProvider/Mfc");
        public static final String PATH = "Mfc";

        public interface Columns {
            public static final String IS_RW_SUPPORTED = "IS_RW_SUPPORTED";
            public static final String READER_NAME = "READER_NAME";
        }
    }

    public Felica() {
        LogMgr.log(3, "%s", "000");
        this.mTimeout = 1000;
        this.mRetryCount = 0;
        LogMgr.log(3, "%s", "999");
    }

    public synchronized int getTimeout() {
        LogMgr.log(3, "%s", "000");
        LogMgr.log(3, "%s timeout:%d", "999", Integer.valueOf(this.mTimeout));
        return this.mTimeout;
    }

    public synchronized void setTimeout(int i) {
        LogMgr.log(3, "%s timeout:%d", "000", Integer.valueOf(i));
        if (i < 0) {
            this.mTimeout = 0;
            LogMgr.log(7, "%s", "001");
        } else if (i > 60000) {
            this.mTimeout = MAX_TIMEOUT;
            LogMgr.log(7, "%s", "002");
        } else {
            this.mTimeout = i;
            LogMgr.log(7, "%s", "003");
        }
        LogMgr.log(3, "%s timeout:%d is set", "999", Integer.valueOf(this.mTimeout));
    }

    public synchronized int getRetryCount() {
        LogMgr.log(3, "%s", "000");
        LogMgr.log(3, "%s %d", "999", "retryCount");
        return this.mRetryCount;
    }

    public synchronized void setRetryCount(int i) {
        LogMgr.log(3, "%s retryCount:%d", "000", Integer.valueOf(i));
        if (i < 0) {
            this.mRetryCount = 0;
            LogMgr.log(7, "%s", "001");
        } else if (i > 10) {
            this.mRetryCount = 10;
            LogMgr.log(7, "%s", "002");
        } else {
            this.mRetryCount = i;
            LogMgr.log(7, "%s", "003");
        }
        LogMgr.log(3, "%s retryCount:%d is set", "999", Integer.valueOf(this.mRetryCount));
    }

    public synchronized void activateFelica(String[] strArr, FelicaEventListener felicaEventListener) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        if (felicaEventListener == null) {
            LogMgr.log(2, "%s %s", "710", "Parameter Error");
            throw new IllegalArgumentException(EXC_INVALID_LISTENER);
        }
        if (strArr != null && strArr.length > 50) {
            LogMgr.log(2, "%s permitList.length > MAX_PERMIT_LIST_SIZE", "711");
            throw new IllegalArgumentException(EXC_MAX_SIZE_PERMIT_LIST);
        }
        checkNotActivated();
        if (strArr != null && strArr.length >= 1 && MFI_PERMIT.equals(strArr[0])) {
            activateMfiFelica(felicaEventListener);
            return;
        }
        this.permitList = strArr;
        this.felicaEventListener = felicaEventListener;
        try {
            bindMfc();
            LogMgr.log(3, "%s", "999");
        } catch (Exception unused) {
            LogMgr.log(2, "%s", "712");
            this.permitList = null;
            this.felicaEventListener = null;
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void inactivateFelica() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        if (isMfiAfterActivating()) {
            this.mMfiClientAccess.inactivate();
            return;
        }
        if (checkAfterActivating()) {
            if (checkConnecting()) {
                unbindMfc();
                return;
            }
            try {
                MfcUtil.checkMfcResult(this.felica.inactivateFelica());
                unbindMfc();
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                if (e.getID() == 2 && e.getType() == 5) {
                    LogMgr.log(7, "%s", "001");
                    return;
                }
                throw e;
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        }
    }

    public synchronized void open() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                MfcUtil.checkMfcResult(this.felica.open());
            } else {
                MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().open());
            }
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void close() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (this.felica != null) {
                    MfcUtil.checkMfcResult(this.felica.close());
                } else {
                    MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().close());
                }
                if (this.pushAppNotificationHooker != null) {
                    this.pushAppNotificationHooker.setListener(null);
                }
                this.pushAppNotificationHooker = null;
                this.mTimeout = 1000;
                this.mRetryCount = 0;
                LogMgr.log(3, "%s", "999");
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        }
    }

    public synchronized void select(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (i < 0 || i > 65535) {
                    LogMgr.log(2, "%s systemCode:%d", "710", Integer.valueOf(i));
                    throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
                }
                if (i == 65535 || (i & 65280) == 65280 || (i & 255) == 255) {
                    LogMgr.log(2, "%s systemCode:%d", "711", Integer.valueOf(i));
                    throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
                }
                if (this.felica != null) {
                    MfcUtil.checkMfcResult(this.felica.select(i));
                } else {
                    MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().select(i));
                }
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s systemCode:%d", "702", Integer.valueOf(i));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void select(int i, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                try {
                    checkMfcOrMfiActivated();
                    if (i < 0 || i > 65535) {
                        LogMgr.log(2, "%s systemCode:%d", "710", Integer.valueOf(i));
                        throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
                    }
                    if (i == 65535 || (i & 65280) == 65280 || (i & 255) == 255) {
                        LogMgr.log(2, "%s systemCode:%d", "711", Integer.valueOf(i));
                        throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
                    }
                    if (str == null || str.length() != 63 || str.equals(INVALID_CID_0) || str.equalsIgnoreCase(INVALID_CID_F)) {
                        LogMgr.log(1, "%s", "800");
                        throw new IllegalArgumentException(EXC_INVALID_CID);
                    }
                    if (this.felica != null) {
                        MfcUtil.checkMfcResult(this.felica.selectWithCid(i, str));
                    } else {
                        MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().selectWithCid(i, str));
                    }
                    LogMgr.log(3, "%s", "999");
                } catch (Exception unused) {
                    LogMgr.log(2, "%s %s", "701", "Other Exception");
                    throw new FelicaException(1, 47);
                }
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s systemCode:%d", "702", Integer.valueOf(i));
            throw e2;
        }
    }

    public synchronized void select(int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                try {
                    checkMfcOrMfiActivated();
                    if (i != 0 && i != 1) {
                        LogMgr.log(2, "%s target:%d", "710", Integer.valueOf(i));
                        throw new IllegalArgumentException(EXC_INVALID_TARGET);
                    }
                    if (i2 < 0 || i2 > 65535) {
                        LogMgr.log(2, "%s systemCode:%d", "711", Integer.valueOf(i2));
                        throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
                    }
                    if (i == 0 && (i2 == 65535 || (i2 & 65280) == 65280 || (i2 & 255) == 255)) {
                        LogMgr.log(2, "%s systemCode:%d", "712", Integer.valueOf(i2));
                        throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
                    }
                    if (this.felica != null) {
                        MfcUtil.checkMfcResult(this.felica.selectWithTarget(i, i2));
                    } else {
                        MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().selectWithTarget(i, i2));
                    }
                    LogMgr.log(3, "%s", "999");
                } catch (Exception unused) {
                    LogMgr.log(2, "%s %s", "701", "Other Exception");
                    throw new FelicaException(1, 47);
                }
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s systemCode:%d", "702", Integer.valueOf(i2));
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            throw e2;
        }
    }

    public synchronized int getSystemCode() throws FelicaException {
        FelicaResultInfoInt systemCode;
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                systemCode = this.felica.getSystemCode();
            } else {
                systemCode = this.mMfiClientAccess.getIMfiFelica().getSystemCode();
            }
            MfcUtil.checkMfcResult(systemCode);
            iIntValue = systemCode.getValue().intValue();
            LogMgr.log(3, "%s returned %d", "999", Integer.valueOf(iIntValue));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return iIntValue;
    }

    public synchronized int getInterface() throws FelicaException {
        FelicaResultInfoInt felicaResultInfoInt;
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                felicaResultInfoInt = this.felica.getInterface();
            } else {
                felicaResultInfoInt = this.mMfiClientAccess.getIMfiFelica().getInterface();
            }
            MfcUtil.checkMfcResult(felicaResultInfoInt);
            iIntValue = felicaResultInfoInt.getValue().intValue();
            LogMgr.log(3, "%s returned %d", "999", Integer.valueOf(iIntValue));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return iIntValue;
    }

    public synchronized byte[] getIDm() throws FelicaException {
        FelicaResultInfoByteArray iDm;
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (this.felica != null) {
                    iDm = this.felica.getIDm();
                } else {
                    iDm = this.mMfiClientAccess.getIMfiFelica().getIDm();
                }
                MfcUtil.checkMfcResult(iDm);
                value = iDm.getValue();
                LogMgr.log(3, "%s returned %s", "999", value);
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        }
        return value;
    }

    public synchronized byte[] getICCode() throws FelicaException {
        FelicaResultInfoByteArray iCCode;
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (this.felica != null) {
                    iCCode = this.felica.getICCode();
                } else {
                    iCCode = this.mMfiClientAccess.getIMfiFelica().getICCode();
                }
                MfcUtil.checkMfcResult(iCCode);
                value = iCCode.getValue();
                LogMgr.log(3, "%s returned %s", "999", value);
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        }
        return value;
    }

    public synchronized int getKeyVersion(int i) throws FelicaException, IllegalArgumentException {
        FelicaResultInfoInt keyVersion;
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                keyVersion = this.felica.getKeyVersion(i, this.mTimeout, this.mRetryCount);
            } else {
                keyVersion = this.mMfiClientAccess.getIMfiFelica().getKeyVersion(i, this.mTimeout, this.mRetryCount);
            }
            MfcUtil.checkMfcResult(keyVersion);
            iIntValue = keyVersion.getValue().intValue();
            LogMgr.log(3, "%s returned %d", "999", Integer.valueOf(iIntValue));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s serviceCode:%d", "702", Integer.valueOf(i));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return iIntValue;
    }

    public synchronized byte[] getContainerIssueInformation() throws FelicaException {
        FelicaResultInfoByteArray containerIssueInformation;
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                containerIssueInformation = this.felica.getContainerIssueInformation(this.mTimeout, this.mRetryCount);
            } else {
                containerIssueInformation = this.mMfiClientAccess.getIMfiFelica().getContainerIssueInformation(this.mTimeout, this.mRetryCount);
            }
            MfcUtil.checkMfcResult(containerIssueInformation);
            value = containerIssueInformation.getValue();
            LogMgr.log(3, "%s returned %s", "999", value);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized void setNodeCodeSize(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (i != 2 && i != 4) {
                LogMgr.log(2, "%s %s nodeCodeSize:%d", "710", "Parameter Error", Integer.valueOf(i));
                throw new IllegalArgumentException(EXC_INVALID_NODECODESIZE);
            }
            if (this.felica != null) {
                MfcUtil.checkMfcResult(this.felica.setNodeCodeSize(i, this.mTimeout, this.mRetryCount));
            } else {
                MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().setNodeCodeSize(i, this.mTimeout, this.mRetryCount));
            }
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s IllegalArgumentException nodeCodeSize:%d", "702", Integer.valueOf(i));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized Data[] read(BlockList blockList) throws FelicaException, IllegalArgumentException {
        FelicaResultInfoDataArray felicaResultInfoDataArray;
        Data[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (blockList == null || blockList.size() == 0) {
                    LogMgr.log(2, "%s %s", "710", "Parameter Error");
                    throw new IllegalArgumentException(EXC_INVALID_BLOCK_LIST);
                }
                if (this.felica != null) {
                    felicaResultInfoDataArray = this.felica.read(blockList, this.mTimeout, this.mRetryCount);
                } else {
                    felicaResultInfoDataArray = this.mMfiClientAccess.getIMfiFelica().read(blockList, this.mTimeout, this.mRetryCount);
                }
                MfcUtil.checkMfcResult(felicaResultInfoDataArray);
                value = felicaResultInfoDataArray.getValue();
                LogMgr.log(3, "%s returned %d", "999", value);
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s IllegalArgumentException", "702");
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized void write(BlockDataList blockDataList) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (blockDataList == null || blockDataList.size() == 0) {
                    LogMgr.log(2, "%s %s", "710", "Parameter Error");
                    throw new IllegalArgumentException(EXC_INVALID_BLOCK_DATA_LIST);
                }
                if (this.felica != null) {
                    MfcUtil.checkMfcResult(this.felica.write(blockDataList, this.mTimeout, this.mRetryCount));
                } else {
                    MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().write(blockDataList, this.mTimeout, this.mRetryCount));
                }
                LogMgr.log(3, "%s", "999");
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s IllegalArgumentException", "702");
            throw e2;
        }
    }

    public synchronized void reset() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                MfcUtil.checkMfcResult(this.felica.reset());
            } else {
                MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().reset());
            }
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized int[] getSystemCodeList() throws FelicaException {
        FelicaResultInfoIntArray systemCodeList;
        int[] value;
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                systemCodeList = this.felica.getSystemCodeList(this.mTimeout, this.mRetryCount);
            } else {
                systemCodeList = this.mMfiClientAccess.getIMfiFelica().getSystemCodeList(this.mTimeout, this.mRetryCount);
            }
            MfcUtil.checkMfcResult(systemCodeList);
            value = systemCodeList.getValue();
            LogMgr.log(3, "%s %s", "999", value);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized NodeInformation getNodeInformation(int i) throws FelicaException, IllegalArgumentException {
        FelicaResultInfoNodeInformation nodeInformation;
        NodeInformation value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                ServiceUtil.getInstance().checkAreaCode(i);
                if (this.felica != null) {
                    nodeInformation = this.felica.getNodeInformation(i, this.mTimeout, this.mRetryCount);
                } else {
                    nodeInformation = this.mMfiClientAccess.getIMfiFelica().getNodeInformation(i, this.mTimeout, this.mRetryCount);
                }
                MfcUtil.checkMfcResult(nodeInformation);
                value = nodeInformation.getValue();
                LogMgr.log(3, "%s", "999");
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s IllegalArgumentException", "702");
            throw e2;
        }
        return value;
    }

    public synchronized NodeInformation getPrivacyNodeInformation(int i) throws FelicaException, IllegalArgumentException {
        FelicaResultInfoNodeInformation privacyNodeInformation;
        NodeInformation value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                ServiceUtil.getInstance().checkAreaCode(i);
                if (this.felica != null) {
                    privacyNodeInformation = this.felica.getPrivacyNodeInformation(i, this.mTimeout, this.mRetryCount);
                } else {
                    privacyNodeInformation = this.mMfiClientAccess.getIMfiFelica().getPrivacyNodeInformation(i, this.mTimeout, this.mRetryCount);
                }
                MfcUtil.checkMfcResult(privacyNodeInformation);
                value = privacyNodeInformation.getValue();
                LogMgr.log(3, "%s", "999");
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s IllegalArgumentException", "702");
            throw e2;
        }
        return value;
    }

    public synchronized BlockCountInformation[] getBlockCountInformation(int[] iArr) throws FelicaException, IllegalArgumentException {
        FelicaResultInfoBlockCountInformationArray blockCountInformation;
        BlockCountInformation[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (iArr == null || iArr.length < 1 || iArr.length > 32) {
                    LogMgr.log(1, "%s invalid NodeCodeList", "800");
                    throw new IllegalArgumentException("The specified parameter is invalid.");
                }
                if (this.felica != null) {
                    blockCountInformation = this.felica.getBlockCountInformation(iArr, this.mTimeout, this.mRetryCount);
                } else {
                    blockCountInformation = this.mMfiClientAccess.getIMfiFelica().getBlockCountInformation(iArr, this.mTimeout, this.mRetryCount);
                }
                MfcUtil.checkMfcResult(blockCountInformation);
                value = blockCountInformation.getValue();
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s IllegalArgumentException", "702");
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized boolean getRFSState() throws FelicaException {
        FelicaResultInfoBoolean rFSState;
        boolean zBooleanValue;
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                rFSState = this.felica.getRFSState();
            } else {
                rFSState = this.mMfiClientAccess.getIMfiFelica().getRFSState();
            }
            MfcUtil.checkMfcResult(rFSState);
            zBooleanValue = rFSState.getValue().booleanValue();
            LogMgr.log(3, "%s returned %d", "999", Boolean.valueOf(zBooleanValue));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return zBooleanValue;
    }

    public synchronized void setPrivacy(PrivacySettingData[] privacySettingDataArr) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (privacySettingDataArr == null || privacySettingDataArr.length <= 0) {
                LogMgr.log(2, "%s %s", "710", "Parameter Error");
                throw new IllegalArgumentException("The specified parameter is invalid.");
            }
            if (this.felica != null) {
                MfcUtil.checkMfcResult(this.felica.setPrivacy(privacySettingDataArr, this.mTimeout, this.mRetryCount));
            } else {
                MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().setPrivacy(privacySettingDataArr, this.mTimeout, this.mRetryCount));
            }
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", e2.getMessage());
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized byte[] getContainerId() throws FelicaException {
        FelicaResultInfoByteArray containerId;
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                containerId = this.felica.getContainerId(this.mTimeout, this.mRetryCount);
            } else {
                containerId = this.mMfiClientAccess.getIMfiFelica().getContainerId(this.mTimeout, this.mRetryCount);
            }
            MfcUtil.checkMfcResult(containerId);
            value = containerId.getValue();
            LogMgr.log(3, "%s returned %s", "999", value);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized void push(PushSegment pushSegment) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (pushSegment == null) {
                LogMgr.log(2, "%s %s", "710", "Parameter Error");
                throw new IllegalArgumentException("The specified parameter is invalid.");
            }
            if (this.felica != null) {
                MfcUtil.checkMfcResult(this.felica.push(new PushSegmentParcelableWrapper(pushSegment)));
            } else {
                MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().push(new PushSegmentParcelableWrapper(pushSegment)));
            }
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", e2.getMessage());
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void setPushNotificationListener(PushAppNotificationListener pushAppNotificationListener, String str) throws FelicaException, IllegalArgumentException {
        PushAppNotificationListenerStub pushAppNotificationListenerStub;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                try {
                    checkMfcOrMfiActivated();
                    if (pushAppNotificationListener == null && str != null) {
                        LogMgr.log(2, "%s", "001");
                        throw new IllegalArgumentException("The specified parameter is invalid.");
                    }
                    if (str != null) {
                        LogMgr.log(7, "%s", "002");
                        if (str.length() != 0 && str.length() != 6) {
                            LogMgr.log(1, "%s invalid parameter", "800");
                            throw new IllegalArgumentException("The specified parameter is invalid.");
                        }
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) < ' ' || str.charAt(i) > '~') {
                                LogMgr.log(1, "%s not ASCII code", "801");
                                throw new IllegalArgumentException("The specified parameter is invalid.");
                            }
                        }
                    }
                    if (pushAppNotificationListener != null) {
                        LogMgr.log(7, "%s", "003");
                        pushAppNotificationListenerStub = new PushAppNotificationListenerStub(pushAppNotificationListener);
                    } else {
                        pushAppNotificationListenerStub = null;
                    }
                    try {
                        if (this.felica != null) {
                            MfcUtil.checkMfcResult(this.felica.setPushNotificationListener(pushAppNotificationListenerStub, str));
                        } else {
                            MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().setPushNotificationListener(pushAppNotificationListenerStub, str));
                        }
                        if (this.pushAppNotificationHooker != null) {
                            LogMgr.log(7, "%s", "004");
                            this.pushAppNotificationHooker.setListener(null);
                        }
                        this.pushAppNotificationHooker = pushAppNotificationListenerStub;
                        LogMgr.log(3, "%s", "999");
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "710", "Error was happend in returening procedure");
                        pushAppNotificationListenerStub.setListener(null);
                        this.pushAppNotificationHooker = null;
                        throw e;
                    }
                } catch (Exception unused) {
                    LogMgr.log(2, "%s %s", "701", "Other Exception");
                    throw new FelicaException(1, 47);
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", e2.getMessage());
                throw e2;
            }
        } catch (FelicaException e3) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e3.getID()), Integer.valueOf(e3.getType()));
            throw e3;
        }
    }

    synchronized void checkOnlineAccess() throws NumberFormatException, FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                MfcUtil.checkMfcResult(this.felica.checkOnlineAccess());
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (NumberFormatException e2) {
            LogMgr.log(2, "%s %s", "701", "NumberFormatException");
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "702", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    class PushAppNotificationListenerStub extends IFelicaPushAppNotificationListener.Stub {
        PushAppNotificationListener listener;

        PushAppNotificationListenerStub(PushAppNotificationListener pushAppNotificationListener) {
            LogMgr.log(3, "%s", "000");
            this.listener = pushAppNotificationListener;
            LogMgr.log(3, "%s", "999");
        }

        public synchronized void setListener(PushAppNotificationListener pushAppNotificationListener) {
            LogMgr.log(3, "%s", "000");
            this.listener = pushAppNotificationListener;
            LogMgr.log(3, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.IFelicaPushAppNotificationListener
        public void pushAppNotified(PushNotifyAppSegment pushNotifyAppSegment) throws RemoteException {
            try {
                LogMgr.log(3, "%s", "000");
                synchronized (this) {
                    if (this.listener != null) {
                        LogMgr.log(3, "%s %s", "001", "pushAppNotified");
                        if (pushNotifyAppSegment != null) {
                            LogMgr.log(3, "%s %s %s", "002", pushNotifyAppSegment.getAppIdentificationCode(), pushNotifyAppSegment.getAppNotificationParam());
                        }
                        this.listener.pushAppNotified(pushNotifyAppSegment);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(2, "%s %s", "700", e.getMessage());
            }
            LogMgr.log(3, "%s", "999");
        }
    }

    class FelicaEventListenerStub extends IFelicaEventListener.Stub {
        FelicaEventListenerStub() {
        }

        @Override // com.felicanetworks.mfc.IFelicaEventListener
        public void errorOccurred(int i, String str, AppInfo appInfo) throws RemoteException {
            FelicaEventListener felicaEventListener;
            LogMgr.log(3, "%s", "000");
            synchronized (Felica.this) {
                LogMgr.log(7, "%s", "001");
                felicaEventListener = Felica.this.felicaEventListener;
                Felica.this.felicaEventListener = null;
                try {
                    Felica.this.unbindMfc();
                } catch (Exception e) {
                    LogMgr.log(1, "%s %s", "900", e.getMessage());
                }
            }
            if (felicaEventListener != null) {
                try {
                    LogMgr.log(7, "%s %s %d %s", "002", "FelicaEventListener#errorOccurred", Integer.valueOf(i), str);
                    if (appInfo != null) {
                        LogMgr.log(3, "%s %s %d", "003", "FelicaEventListener#errorOccurred", Integer.valueOf(appInfo.getPid()));
                    }
                    felicaEventListener.errorOccurred(i, str, appInfo);
                } catch (Exception e2) {
                    LogMgr.log(2, "%s %s", "700", e2.getMessage());
                }
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.IFelicaEventListener
        public void finished() throws RemoteException {
            FelicaEventListener felicaEventListener;
            LogMgr.log(3, "%s %s", "000", "FelicaEventListener#finished");
            try {
                synchronized (Felica.this) {
                    felicaEventListener = null;
                    if (Felica.this.felicaEventListener != null) {
                        LogMgr.log(7, "%s", "001");
                        FelicaEventListener felicaEventListener2 = Felica.this.felicaEventListener;
                        Felica.this.felicaEventListener = null;
                        felicaEventListener = felicaEventListener2;
                    } else {
                        LogMgr.log(7, "%s", "002");
                        Felica.this.unbindMfc();
                    }
                }
                if (felicaEventListener != null) {
                    try {
                        LogMgr.log(3, "%s", "003");
                        felicaEventListener.finished();
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "700", e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s", "900", e2.getMessage());
            }
            LogMgr.log(3, "%s", "999");
        }
    }

    protected void cleanUp() {
        LogMgr.log(7, "%s", "000");
        this.felica = null;
        this.felicaEventListener = null;
        this.permitList = null;
        if (this.pushAppNotificationHooker != null) {
            LogMgr.log(7, "%s", "001");
            this.pushAppNotificationHooker.setListener(null);
        }
        this.pushAppNotificationHooker = null;
        this.mTimeout = 1000;
        this.mRetryCount = 0;
        this.bindTimerHandler.stopTimer();
        LogMgr.log(7, "%s timeout = %d, retryCount = %d", "001", Integer.valueOf(this.mTimeout), Integer.valueOf(this.mRetryCount));
        LogMgr.log(7, "%s", "999");
    }

    protected void checkActivated() throws FelicaException {
        LogMgr.log(7, "%s", "000");
        if (this.felica == null || this.felicaEventListener != null) {
            LogMgr.log(7, "%s", "001");
            throw new FelicaException(2, 5);
        }
        LogMgr.log(7, "%s", "999");
    }

    protected void checkMfcOrMfiActivated() throws FelicaException {
        boolean z;
        LogMgr.log(7, "%s", "000");
        try {
            checkActivated();
            z = true;
        } catch (FelicaException unused) {
            LogMgr.log(7, "%s", "001");
            z = false;
        }
        boolean zIsMfiActivated = isMfiActivated();
        if (!z && !zIsMfiActivated) {
            LogMgr.log(7, "%s", "003");
            throw new FelicaException(2, 5);
        }
        LogMgr.log(7, "%s", "999");
    }

    boolean isMfiActivated() {
        try {
            if (this.mMfiClientAccess == null) {
                return false;
            }
            this.mMfiClientAccess.checkActivated();
            return true;
        } catch (FelicaException unused) {
            LogMgr.log(7, "%s", "002");
            return false;
        }
    }

    boolean isMfiAfterActivating() {
        MfiClientAccess mfiClientAccess = this.mMfiClientAccess;
        if (mfiClientAccess == null) {
            return false;
        }
        return mfiClientAccess.checkAfterActivating();
    }

    protected void checkNotActivated() throws FelicaException {
        LogMgr.log(7, "%s", "000");
        if (this.felicaEventListener != null) {
            LogMgr.log(3, "%s %s id:%d type:%d", "700", "FelicaException", 2, 49);
            throw new FelicaException(2, 49);
        }
        if (this.felica != null) {
            LogMgr.log(3, "%s %s id:%d type:%d", "701", "FelicaException", 2, 42);
            throw new FelicaException(2, 42);
        }
        MfiClientAccess mfiClientAccess = this.mMfiClientAccess;
        if (mfiClientAccess != null) {
            mfiClientAccess.checkNotActivated();
        }
        LogMgr.log(7, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkConnecting() {
        return this.felica == null && this.felicaEventListener != null;
    }

    private boolean checkAfterActivating() {
        return (this.felica == null && this.felicaEventListener == null) ? false : true;
    }

    protected void bindMfc() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        if (!SignatureUtil.checkAppCertHash(this, FlavorConst.MFC_SIGNATURE_HASH, "com.felicanetworks.mfc")) {
            LogMgr.log(3, "700 Failed to connect for MFC Service. AppCertHash check failed.");
            throw new FelicaException(1, 47);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.felicanetworks.mfc", MFC_ADAPTER_CLASS_NAME));
        if (!bindService(intent, this.connectionHooker, 1)) {
            LogMgr.log(3, "701 Failed to connect for MFC Service");
            unbindService(this.connectionHooker);
            throw new FelicaException(1, 47);
        }
        this.bindTimerHandler.startTimer(bindTimeout);
        LogMgr.log(3, "%s", "999");
    }

    protected void unbindMfc() {
        LogMgr.log(7, "%s", "000");
        try {
            try {
                LogMgr.log(3, "%s", "001");
                unbindService(this.connectionHooker);
                LogMgr.log(3, "%s", "002");
            } catch (Exception unused) {
                LogMgr.log(7, "%s %s", "004", "Unbind failed");
            }
            LogMgr.log(7, "%s", "003");
            cleanUp();
            LogMgr.log(7, "%s", "999");
        } catch (Throwable th) {
            LogMgr.log(7, "%s", "003");
            cleanUp();
            throw th;
        }
    }

    class BindTimerHandler extends Handler {
        static final int MSG_BIND_TIMER = 1;

        BindTimerHandler(Looper looper) {
            super(looper);
        }

        void startTimer(int i) {
            LogMgr.log(3, "%s timeout=%d", "000", Integer.valueOf(i));
            if (i > 0) {
                LogMgr.log(7, "%s", "001");
                sendMessageDelayed(Felica.this.bindTimerHandler.obtainMessage(1), i);
            }
            LogMgr.log(3, "%s", "999");
        }

        void stopTimer() {
            LogMgr.log(3, "%s", "000");
            removeMessages(1);
            LogMgr.log(3, "%s", "999");
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            FelicaEventListener felicaEventListener;
            LogMgr.log(3, "%s what=%d", "000", Integer.valueOf(message.what));
            if (message.what == 1) {
                LogMgr.log(2, "%s bind timeout connecting=%b", "800", Boolean.valueOf(Felica.this.checkConnecting()));
                synchronized (Felica.this) {
                    if (Felica.this.checkConnecting()) {
                        LogMgr.log(7, "%s", "001");
                        felicaEventListener = Felica.this.felicaEventListener;
                        Felica.this.felicaEventListener = null;
                        Felica.this.unbindMfc();
                    } else {
                        felicaEventListener = null;
                    }
                }
                if (felicaEventListener != null) {
                    LogMgr.log(3, "%s Do the callback", "010");
                    felicaEventListener.errorOccurred(1, "Bind timeout.", null);
                }
            }
            super.handleMessage(message);
            LogMgr.log(3, "%s", "999");
        }
    }

    class MfcConnection implements ServiceConnection {
        MfcConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            int i;
            FelicaEventListener felicaEventListener;
            AppInfo appInfo;
            AppInfo appInfo2;
            LogMgr.log(3, "%s %s", "000", componentName.getClassName());
            synchronized (Felica.this) {
                Felica.this.felica = IFelica.Stub.asInterface(iBinder);
                Felica.this.bindTimerHandler.stopTimer();
                i = 1;
                if (Felica.this.felicaEventListener != null) {
                    LogMgr.log(7, "%s", "001");
                    try {
                        try {
                            MfcUtil.checkMfcResult(Felica.this.felica.activateFelica(Felica.this.permitList, Felica.this.iFelicaEventListener));
                        } finally {
                            LogMgr.log(7, "%s", "010");
                            Felica.this.permitList = null;
                        }
                    } catch (FelicaException e) {
                        LogMgr.log(7, "%s", "002");
                        int type = e.getType();
                        if (type != 39) {
                            if (type != 42) {
                                LogMgr.log(2, "%s FelicaException id:%d type:%d", "702", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                            } else {
                                LogMgr.log(2, "%s FelicaException id:%d type:%d", "701", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                            }
                            appInfo2 = null;
                        } else {
                            AppInfo otherAppInfo = e.getOtherAppInfo();
                            LogMgr.log(2, "%s FelicaException id:%d type:%d pid%d", "700", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()), null, Integer.valueOf(otherAppInfo.getPid()));
                            appInfo2 = otherAppInfo;
                            i = 7;
                        }
                        LogMgr.log(7, "%s", "010");
                        Felica.this.permitList = null;
                        LogMgr.log(7, "%s", "011");
                        FelicaEventListener felicaEventListener2 = Felica.this.felicaEventListener;
                        Felica.this.felicaEventListener = null;
                        Felica.this.unbindMfc();
                        appInfo = appInfo2;
                        felicaEventListener = felicaEventListener2;
                    } catch (Exception e2) {
                        LogMgr.log(2, "%s Exception %s", "703", e2.getMessage());
                        LogMgr.log(7, "%s", "010");
                        Felica.this.permitList = null;
                        LogMgr.log(7, "%s", "011");
                        felicaEventListener = Felica.this.felicaEventListener;
                        Felica.this.felicaEventListener = null;
                        Felica.this.unbindMfc();
                        appInfo = null;
                    }
                } else {
                    LogMgr.log(2, "%s", "704");
                    Felica.this.unbindMfc();
                }
                appInfo = null;
                felicaEventListener = null;
            }
            LogMgr.log(7, "%s", "700");
            if (felicaEventListener != null) {
                LogMgr.log(3, "%s Do the callback", "020");
                felicaEventListener.errorOccurred(i, null, appInfo);
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            FelicaEventListener felicaEventListener;
            LogMgr.log(3, "%s %s", "000", componentName);
            synchronized (Felica.this) {
                if (Felica.this.felicaEventListener != null) {
                    LogMgr.log(7, "%s", "001");
                    felicaEventListener = Felica.this.felicaEventListener;
                    Felica.this.felicaEventListener = null;
                } else {
                    felicaEventListener = null;
                }
                Felica.this.unbindMfc();
            }
            if (felicaEventListener != null) {
                LogMgr.log(7, "%s", "002");
                felicaEventListener.errorOccurred(1, "Unknown error.", null);
            }
            LogMgr.log(3, "%s", "999");
        }
    }

    synchronized IFelica getIFelica() {
        LogMgr.log(7, "%s", "000");
        LogMgr.log(7, "%s", "999");
        return this.felica;
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public Felica getInstance() {
            LogMgr.log(3, "%s", "000");
            LogMgr.log(3, "%s", "999");
            return Felica.this;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        ServiceInfo serviceInfo;
        LogMgr.log(3, "%s", "000");
        try {
            ServiceInfo[] serviceInfoArr = getPackageManager().getPackageInfo(getPackageName(), 4).services;
            int length = serviceInfoArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    serviceInfo = null;
                    break;
                }
                serviceInfo = serviceInfoArr[i];
                if (serviceInfo.name.equals(getClass().getName())) {
                    LogMgr.log(7, "%s", "001");
                    break;
                }
                i++;
            }
            if (serviceInfo == null) {
                LogMgr.log(1, "%s", "800 service tag is not found.");
                return null;
            }
            if (serviceInfo.exported) {
                LogMgr.log(1, "%s", "801 exported tag is enable.");
                return null;
            }
            this.mMfiClientAccess = new MfiClientAccess(this);
            this.mfiClient = new MfiClient(this.mMfiClientAccess);
            LogMgr.log(3, "%s", "999");
            return this.mBinder;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "802");
            return null;
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(3, "%s", "000");
        try {
            synchronized (this) {
                LogMgr.log(7, "%s", "001");
                if (this.mMfiClientAccess != null) {
                    LogMgr.log(7, "%s", "005");
                    this.mMfiClientAccess.finish();
                    this.mMfiClientAccess = null;
                }
                if (this.mfiClient != null) {
                    LogMgr.log(7, "%s", "006");
                    this.mfiClient.finish();
                    this.mfiClient = null;
                }
                try {
                    if (this.felica != null) {
                        LogMgr.log(7, "%s", "002");
                        this.felica.close();
                        this.felica.inactivateFelica();
                    }
                } catch (Exception e) {
                    LogMgr.log(6, "%s %s", "003", e.getMessage());
                }
                unbindMfc();
            }
        } catch (Exception e2) {
            LogMgr.log(6, "%s %s", "004", e2.getMessage());
        }
        super.onDestroy();
        LogMgr.log(3, "%s", "999");
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(3, "%s", "000");
        try {
            synchronized (this) {
                LogMgr.log(7, "%s", "001");
                if (this.mMfiClientAccess != null) {
                    LogMgr.log(7, "%s", "005");
                    this.mMfiClientAccess.finish();
                    this.mMfiClientAccess = null;
                }
                if (this.mfiClient != null) {
                    LogMgr.log(7, "%s", "006");
                    this.mfiClient.finish();
                    this.mfiClient = null;
                }
                try {
                    if (this.felica != null) {
                        LogMgr.log(7, "%s", "002");
                        this.felica.close();
                        this.felica.inactivateFelica();
                    }
                } catch (Exception e) {
                    LogMgr.log(6, "%s %s", "003", e.getMessage());
                }
                unbindMfc();
            }
        } catch (Exception e2) {
            LogMgr.log(6, "%s %s", "004", e2.getMessage());
        }
        LogMgr.log(3, "%s", "999");
        return super.onUnbind(intent);
    }

    public synchronized void setSelectTimeout(int i) throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                MfcUtil.checkMfcResult(this.felica.setSelectTimeout(i));
            } else {
                MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().setSelectTimeout(i));
            }
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized int getSelectTimeout() throws FelicaException {
        FelicaResultInfoInt selectTimeout;
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (this.felica != null) {
                    selectTimeout = this.felica.getSelectTimeout();
                } else {
                    selectTimeout = this.mMfiClientAccess.getIMfiFelica().getSelectTimeout();
                }
                MfcUtil.checkMfcResult(selectTimeout);
                iIntValue = selectTimeout.getValue().intValue();
                LogMgr.log(3, "%s", "999");
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        }
        return iIntValue;
    }

    public void cancelOffline() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            checkMfcOrMfiActivated();
            if (this.felica != null) {
                MfcUtil.checkMfcResult(this.felica.cancelOffline());
            } else {
                MfcUtil.checkMfcResult(this.mMfiClientAccess.getIMfiFelica().cancelOffline());
            }
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public static String getMFCVersion(Context context) throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("com.felicanetworks.mfc", 0);
            LogMgr.log(3, "%s", "999");
            return packageInfo.versionName;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "800", "package not found");
            throw new FelicaException(3, 60);
        }
    }

    public synchronized byte[] executeFelicaCommand(byte[] bArr) throws FelicaException, IllegalArgumentException {
        FelicaResultInfoByteArray felicaResultInfoByteArrayExecuteFelicaCommand;
        byte[] value;
        LogMgr.log(3, "%s", "START");
        try {
            checkMfcOrMfiActivated();
            if (bArr == null || bArr.length <= 0 || bArr.length > MAX_PACKET_DATA_LENGTH) {
                LogMgr.log(2, "%s", "Parameter Error");
                throw new IllegalArgumentException(EXC_INVALID_COMMAND);
            }
            if (this.felica != null) {
                felicaResultInfoByteArrayExecuteFelicaCommand = this.felica.executeFelicaCommand(bArr, this.mTimeout, this.mRetryCount);
            } else {
                felicaResultInfoByteArrayExecuteFelicaCommand = this.mMfiClientAccess.getIMfiFelica().executeFelicaCommand(bArr, this.mTimeout, this.mRetryCount);
            }
            MfcUtil.checkMfcResult(felicaResultInfoByteArrayExecuteFelicaCommand);
            value = felicaResultInfoByteArrayExecuteFelicaCommand.getValue();
            LogMgr.log(3, "%s", "END");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s id:%d type:%d", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s %s", "IllegalArgumentException", e2.getMessage());
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized KeyInformation[] getKeyVersionV2(int[] iArr) throws FelicaException, IllegalArgumentException {
        FelicaResultInfoKeyInformationArray keyVersionV2;
        KeyInformation[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkMfcOrMfiActivated();
                if (iArr == null || iArr.length < 1 || iArr.length > 32) {
                    LogMgr.log(1, "%s invalid NodeCodeList", "800");
                    throw new IllegalArgumentException("The specified parameter is invalid.");
                }
                if (this.felica != null) {
                    keyVersionV2 = this.felica.getKeyVersionV2(iArr, this.mTimeout, this.mRetryCount);
                } else {
                    keyVersionV2 = this.mMfiClientAccess.getIMfiFelica().getKeyVersionV2(iArr, this.mTimeout, this.mRetryCount);
                }
                MfcUtil.checkMfcResult(keyVersionV2);
                value = keyVersionV2.getValue();
                LogMgr.log(3, "%s returned %d", "999", value);
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s nodeCode:%d", "702", iArr);
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public MfiClient getMfiClient() throws MfiClientException {
        MfiClient mfiClient = this.mfiClient;
        if (mfiClient != null) {
            return mfiClient;
        }
        LogMgr.log(7, "%s", "001");
        throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_ACTIVATED, null);
    }

    MfiClientAccess getMfiClientAccess() {
        return this.mMfiClientAccess;
    }

    private synchronized void activateMfiFelica(FelicaEventListener felicaEventListener) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        if (felicaEventListener == null) {
            throw new IllegalArgumentException(EXC_INVALID_LISTENER);
        }
        checkNotActivated();
        this.mMfiClientAccess.activate(getPackageName(), felicaEventListener);
        LogMgr.log(3, "%s", "999");
    }

    public static String getMfiClientVersion(Context context) throws MfiClientException {
        ServiceInfo serviceInfo;
        LogMgr.log(3, "%s", "000");
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.felicanetworks.mfm.main", 4);
            if (packageInfo.services != null) {
                ServiceInfo[] serviceInfoArr = packageInfo.services;
                int length = serviceInfoArr.length;
                for (int i = 0; i < length; i++) {
                    serviceInfo = serviceInfoArr[i];
                    if (MFI_CLIENT_SERVICE_CLASS_NAME.equals(serviceInfo.name)) {
                        break;
                    }
                }
                serviceInfo = null;
            } else {
                serviceInfo = null;
            }
            if (serviceInfo == null) {
                LogMgr.log(2, "%s %s", "800", "service not found");
                throw new MfiClientException(3, MfiClientException.TYPE_MFICLIENT_NOT_FOUND, null);
            }
            Resources resourcesForApplication = packageManager.getResourcesForApplication("com.felicanetworks.mfm.main");
            String string = resourcesForApplication.getString(resourcesForApplication.getIdentifier(MFI_CLIENT_VERSION_RESOURCE_NAME, MFI_CLIENT_VERSION_RESOURCE_TYPE, "com.felicanetworks.mfm.main"));
            LogMgr.log(3, "%s", "999");
            return string;
        } catch (Exception e) {
            LogMgr.log(2, "%s %s", "801", e.toString());
            throw new MfiClientException(3, MfiClientException.TYPE_MFICLIENT_NOT_FOUND, null);
        }
    }

    public static boolean isReaderWriterSupported(Context context) throws Throwable {
        LogMgr.log(3, "%s", "000");
        if (!isSupportedVersion(getMFCVersion(context))) {
            return true;
        }
        Cursor cursor = null;
        string = null;
        String string = null;
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(Contents.CONTENT_URI, null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex(Contents.Columns.IS_RW_SUPPORTED));
                        }
                    } catch (SecurityException unused) {
                        LogMgr.log(2, "%s", "702");
                        throw new FelicaException(1, 47);
                    } catch (Exception unused2) {
                        LogMgr.log(2, "%s %s", "701", "Other Exception");
                        throw new FelicaException(1, 47);
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (string == null) {
                    throw new FelicaException(3, 60);
                }
                LogMgr.log(3, "%s", "999");
                return Boolean.valueOf(string).booleanValue();
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (SecurityException unused3) {
        } catch (Exception unused4) {
        }
    }

    private static boolean isSupportedVersion(String str) throws FelicaException {
        if (str == null) {
            LogMgr.log(2, "800", "Could not get MFC Version");
            throw new FelicaException(3, 60);
        }
        try {
            return Integer.parseInt(str.split(VERSION_DELIMITER)[0]) >= 5;
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "801", "Invalid MFC Version format");
            throw new FelicaException(3, 60);
        }
    }

    public static boolean isFelicaSupported() {
        LogMgr.log(3, "000");
        String[] strArr = COMMON_FILE_PATHS;
        int length = strArr.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str = strArr[i] + COMMON_FILE_NAME;
            try {
                if (new File(str).exists()) {
                    LogMgr.log(7, "001 exist file path = " + str);
                    z = true;
                    break;
                }
                continue;
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            i++;
        }
        LogMgr.log(3, "999 result = " + z);
        return z;
    }
}
