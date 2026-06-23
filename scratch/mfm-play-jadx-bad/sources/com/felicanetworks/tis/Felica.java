package com.felicanetworks.tis;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaEventListener;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfoBlockCountInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoBoolean;
import com.felicanetworks.mfc.FelicaResultInfoByteArray;
import com.felicanetworks.mfc.FelicaResultInfoDataArray;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.FelicaResultInfoIntArray;
import com.felicanetworks.mfc.FelicaResultInfoKeyInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoNodeInformation;
import com.felicanetworks.mfc.IFelica;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PushAppNotificationListener;
import com.felicanetworks.mfc.PushNotifyAppSegment;
import com.felicanetworks.mfc.PushSegment;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;
import com.felicanetworks.mfc.ServiceUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class Felica extends Service {
    private static final int APP_CODE_LENGTH = 6;
    private static final int CID_LENGTH = 63;
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
    private static final int MAX_PACKET_DATA_LENGTH = 254;
    public static final int MAX_PERMIT_LIST_SIZE = 50;
    public static final int MAX_RETRY_COUNT = 10;
    private static final int MAX_SYSTEM_CODE = 65535;
    public static final int MAX_TIMEOUT = 60000;
    private static final String MFC_ADAPTER_CLASS_NAME = "com.felicanetworks.mfc.FelicaAdapter";
    private static final String MFC_PACKAGE_NAME = "com.felicanetworks.mfc";
    public static final int MIN_RETRY_COUNT = 0;
    private static final int MIN_SYSTEM_CODE = 0;
    public static final int MIN_TIMEOUT = 0;
    public static final int NODECODESIZE_2 = 2;
    public static final int NODECODESIZE_4 = 4;
    static int bindTimeout = 10000;
    private FelicaEventListener felicaEventListener;
    private String[] permitList;
    private PushAppNotificationListenerStub pushAppNotificationHooker;
    private int retryCount;
    private int timeout;
    private BindTimerHandler bindTimerHandler = new BindTimerHandler(Looper.myLooper());
    private MfcConnection connectionHooker = new MfcConnection();
    private IFelicaEventListener iFelicaEventListener = new FelicaEventListenerStub();
    private IFelica felica = null;
    private final IBinder mBinder = new LocalBinder();

    public Felica() {
        LogMgr.log(3, "%s", "000");
        this.timeout = 1000;
        this.retryCount = 0;
        LogMgr.log(3, "%s", "999");
    }

    public synchronized int getTimeout() {
        LogMgr.log(3, "%s", "000");
        LogMgr.log(3, "%s timeout:%d", "999", Integer.valueOf(this.timeout));
        return this.timeout;
    }

    public synchronized void setTimeout(int i) {
        LogMgr.log(3, "%s timeout:%d", "000", Integer.valueOf(i));
        if (i < 0) {
            this.timeout = 0;
            LogMgr.log(7, "%s", "001");
        } else if (i > 60000) {
            this.timeout = 60000;
            LogMgr.log(7, "%s", "002");
        } else {
            this.timeout = i;
            LogMgr.log(7, "%s", "003");
        }
        LogMgr.log(3, "%s timeout:%d is set", "999", Integer.valueOf(this.timeout));
    }

    public synchronized int getRetryCount() {
        LogMgr.log(3, "%s", "000");
        LogMgr.log(3, "%s %d", "999", "retryCount");
        return this.retryCount;
    }

    public synchronized void setRetryCount(int i) {
        LogMgr.log(3, "%s retryCount:%d", "000", Integer.valueOf(i));
        if (i < 0) {
            this.retryCount = 0;
            LogMgr.log(7, "%s", "001");
        } else if (i > 10) {
            this.retryCount = 10;
            LogMgr.log(7, "%s", "002");
        } else {
            this.retryCount = i;
            LogMgr.log(7, "%s", "003");
        }
        LogMgr.log(3, "%s retryCount:%d is set", "999", Integer.valueOf(this.retryCount));
    }

    public synchronized void activateFelica(String[] strArr, FelicaEventListener felicaEventListener) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        if (felicaEventListener == null) {
            LogMgr.log(2, "%s %s", "710", "Parameter Error");
            throw new IllegalArgumentException("The specified Listener is null.");
        }
        if (strArr != null && strArr.length > 50) {
            LogMgr.log(2, "%s permitList.length > MAX_PERMIT_LIST_SIZE", "711");
            throw new IllegalArgumentException(EXC_MAX_SIZE_PERMIT_LIST);
        }
        checkNotActivated();
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
            checkActivated();
            MfcUtil.checkMfcResult(this.felica.open());
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
            checkActivated();
            MfcUtil.checkMfcResult(this.felica.close());
            PushAppNotificationListenerStub pushAppNotificationListenerStub = this.pushAppNotificationHooker;
            if (pushAppNotificationListenerStub != null) {
                pushAppNotificationListenerStub.setListener(null);
            }
            this.pushAppNotificationHooker = null;
            this.timeout = 1000;
            this.retryCount = 0;
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void select(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                if (i < 0 || i > 65535) {
                    LogMgr.log(2, "%s systemCode:%d", "710", Integer.valueOf(i));
                    throw new IllegalArgumentException("The specified System Code is out of range.");
                }
                if (i == 65535 || (i & 65280) == 65280 || (i & 255) == 255) {
                    LogMgr.log(2, "%s systemCode:%d", "711", Integer.valueOf(i));
                    throw new IllegalArgumentException("The specified System Code is out of range.");
                }
                MfcUtil.checkMfcResult(this.felica.select(i));
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s systemCode:%d", "702", Integer.valueOf(i));
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void select(int i, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "000");
        try {
            try {
                try {
                    checkActivated();
                    if (i < 0 || i > 65535) {
                        LogMgr.log(2, "710 systemCode:" + i);
                        throw new IllegalArgumentException("The specified System Code is out of range.");
                    }
                    if (i == 65535 || (i & 65280) == 65280 || (i & 255) == 255) {
                        LogMgr.log(2, "711 systemCode:" + i);
                        throw new IllegalArgumentException("The specified System Code is out of range.");
                    }
                    if (str == null || str.length() != 63 || str.equals("000000000000000000000000000000000000000000000000000000000000000") || str.equalsIgnoreCase("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff")) {
                        LogMgr.log(1, "%s", "800");
                        throw new IllegalArgumentException("The specified CID is null or invalid value.");
                    }
                    MfcUtil.checkMfcResult(this.felica.selectWithCid(i, str));
                    LogMgr.log(3, "999");
                } catch (FelicaException e) {
                    LogMgr.log(2, "700 FelicaException id:" + e.getID() + " type:" + e.getType());
                    throw e;
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(2, "702 systemCode:" + i);
                throw e2;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "701 Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void select(int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                try {
                    checkActivated();
                    if (i != 0 && i != 1) {
                        LogMgr.log(2, "%s target:%d", "710", Integer.valueOf(i));
                        throw new IllegalArgumentException("The specified Target is invalid value.");
                    }
                    if (i2 < 0 || i2 > 65535) {
                        LogMgr.log(2, "%s systemCode:%d", "711", Integer.valueOf(i2));
                        throw new IllegalArgumentException("The specified System Code is out of range.");
                    }
                    if (i == 0 && (i2 == 65535 || (i2 & 65280) == 65280 || (i2 & 255) == 255)) {
                        LogMgr.log(2, "%s systemCode:%d", "712", Integer.valueOf(i2));
                        throw new IllegalArgumentException("The specified System Code is out of range.");
                    }
                    MfcUtil.checkMfcResult(this.felica.selectWithTarget(i, i2));
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
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoInt systemCode = this.felica.getSystemCode();
                MfcUtil.checkMfcResult(systemCode);
                Integer value = systemCode.getValue();
                iIntValue = value.intValue();
                LogMgr.log(3, "%s returned %d", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return iIntValue;
    }

    public synchronized int getInterface() throws FelicaException {
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoInt felicaResultInfoInt = this.felica.getInterface();
                MfcUtil.checkMfcResult(felicaResultInfoInt);
                Integer value = felicaResultInfoInt.getValue();
                iIntValue = value.intValue();
                LogMgr.log(3, "%s returned %d", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return iIntValue;
    }

    public synchronized byte[] getIDm() throws FelicaException {
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoByteArray iDm = this.felica.getIDm();
                MfcUtil.checkMfcResult(iDm);
                value = iDm.getValue();
                LogMgr.log(3, "%s returned %s", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized byte[] getICCode() throws FelicaException {
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoByteArray iCCode = this.felica.getICCode();
                MfcUtil.checkMfcResult(iCCode);
                value = iCCode.getValue();
                LogMgr.log(3, "%s returned %s", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized int getKeyVersion(int i) throws FelicaException, IllegalArgumentException {
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            checkActivated();
            FelicaResultInfoInt keyVersion = this.felica.getKeyVersion(i, this.timeout, this.retryCount);
            MfcUtil.checkMfcResult(keyVersion);
            Integer value = keyVersion.getValue();
            iIntValue = value.intValue();
            LogMgr.log(3, "%s returned %d", "999", value);
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
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoByteArray containerIssueInformation = this.felica.getContainerIssueInformation(this.timeout, this.retryCount);
                MfcUtil.checkMfcResult(containerIssueInformation);
                value = containerIssueInformation.getValue();
                LogMgr.log(3, "%s returned %s", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized void setNodeCodeSize(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            checkActivated();
            if (i != 2 && i != 4) {
                LogMgr.log(2, "%s %s nodeCodeSize:%d", "710", "Parameter Error", Integer.valueOf(i));
                throw new IllegalArgumentException("The specified NodeCodeSize is invalid value.");
            }
            MfcUtil.checkMfcResult(this.felica.setNodeCodeSize(i, this.timeout, this.retryCount));
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
        Data[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                if (blockList == null || blockList.size() == 0) {
                    LogMgr.log(2, "%s %s", "710", "Parameter Error");
                    throw new IllegalArgumentException("The specified BlockList is null or empty.");
                }
                FelicaResultInfoDataArray felicaResultInfoDataArray = this.felica.read(blockList, this.timeout, this.retryCount);
                MfcUtil.checkMfcResult(felicaResultInfoDataArray);
                value = felicaResultInfoDataArray.getValue();
                LogMgr.log(3, "%s returned %s", "999", value);
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

    public synchronized void write(BlockDataList blockDataList) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                if (blockDataList == null || blockDataList.size() == 0) {
                    LogMgr.log(2, "%s %s", "710", "Parameter Error");
                    throw new IllegalArgumentException("The specified BlockDataList is null or empty.");
                }
                MfcUtil.checkMfcResult(this.felica.write(blockDataList, this.timeout, this.retryCount));
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s IllegalArgumentException", "702");
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void reset() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            checkActivated();
            MfcUtil.checkMfcResult(this.felica.reset());
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
        int[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoIntArray systemCodeList = this.felica.getSystemCodeList(this.timeout, this.retryCount);
                MfcUtil.checkMfcResult(systemCodeList);
                value = systemCodeList.getValue();
                LogMgr.log(3, "%s %s", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized NodeInformation getNodeInformation(int i) throws FelicaException, IllegalArgumentException {
        NodeInformation value;
        LogMgr.log(3, "%s", "000");
        try {
            checkActivated();
            ServiceUtil.getInstance().checkAreaCode(i);
            FelicaResultInfoNodeInformation nodeInformation = this.felica.getNodeInformation(i, this.timeout, this.retryCount);
            MfcUtil.checkMfcResult(nodeInformation);
            value = nodeInformation.getValue();
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s IllegalArgumentException", "702");
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized NodeInformation getPrivacyNodeInformation(int i) throws FelicaException, IllegalArgumentException {
        NodeInformation value;
        LogMgr.log(3, "%s", "000");
        try {
            checkActivated();
            ServiceUtil.getInstance().checkAreaCode(i);
            FelicaResultInfoNodeInformation privacyNodeInformation = this.felica.getPrivacyNodeInformation(i, this.timeout, this.retryCount);
            MfcUtil.checkMfcResult(privacyNodeInformation);
            value = privacyNodeInformation.getValue();
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s IllegalArgumentException", "702");
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized BlockCountInformation[] getBlockCountInformation(int[] iArr) throws FelicaException, IllegalArgumentException {
        BlockCountInformation[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                if (iArr == null || iArr.length < 1 || iArr.length > 32) {
                    LogMgr.log(1, "%s invalid NodeCodeList", "800");
                    throw new IllegalArgumentException("The specified parameter is invalid.");
                }
                FelicaResultInfoBlockCountInformationArray blockCountInformation = this.felica.getBlockCountInformation(iArr, this.timeout, this.retryCount);
                MfcUtil.checkMfcResult(blockCountInformation);
                value = blockCountInformation.getValue();
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

    public synchronized boolean getRFSState() throws FelicaException {
        boolean zBooleanValue;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoBoolean rFSState = this.felica.getRFSState();
                MfcUtil.checkMfcResult(rFSState);
                Boolean value = rFSState.getValue();
                zBooleanValue = value.booleanValue();
                LogMgr.log(3, "%s returned %d", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return zBooleanValue;
    }

    public synchronized void setPrivacy(PrivacySettingData[] privacySettingDataArr) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                if (privacySettingDataArr == null || privacySettingDataArr.length <= 0) {
                    LogMgr.log(2, "%s %s", "710", "Parameter Error");
                    throw new IllegalArgumentException("The specified parameter is invalid.");
                }
                MfcUtil.checkMfcResult(this.felica.setPrivacy(privacySettingDataArr, this.timeout, this.retryCount));
                LogMgr.log(3, "%s", "999");
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", e.getMessage());
                throw e;
            }
        } catch (FelicaException e2) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
            throw e2;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized byte[] getContainerId() throws FelicaException {
        byte[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoByteArray containerId = this.felica.getContainerId(this.timeout, this.retryCount);
                MfcUtil.checkMfcResult(containerId);
                value = containerId.getValue();
                LogMgr.log(3, "%s returned %s", "999", value);
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return value;
    }

    public synchronized void push(PushSegment pushSegment) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                try {
                    checkActivated();
                    if (pushSegment == null) {
                        LogMgr.log(2, "%s %s", "710", "Parameter Error");
                        throw new IllegalArgumentException("The specified parameter is invalid.");
                    }
                    MfcUtil.checkMfcResult(this.felica.push(new PushSegmentParcelableWrapper(pushSegment)));
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
            LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", e2.getMessage());
            throw e2;
        }
    }

    public synchronized void setPushNotificationListener(PushAppNotificationListener pushAppNotificationListener, String str) throws FelicaException, IllegalArgumentException {
        PushAppNotificationListenerStub pushAppNotificationListenerStub;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                try {
                    checkActivated();
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
                        MfcUtil.checkMfcResult(this.felica.setPushNotificationListener(pushAppNotificationListenerStub, str));
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
            } catch (FelicaException e2) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e2.getID()), Integer.valueOf(e2.getType()));
                throw e2;
            }
        } catch (IllegalArgumentException e3) {
            LogMgr.log(2, "%s %s %s", "702", "IllegalArgumentException", e3.getMessage());
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
        this.timeout = 1000;
        this.retryCount = 0;
        this.bindTimerHandler.stopTimer();
        LogMgr.log(7, "%s timeout = %d, retryCount = %d", "001", Integer.valueOf(this.timeout), Integer.valueOf(this.retryCount));
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
        if (!SignatureUtil.checkAppCertHash(this, "E0:72:31:7C:30:CE:10:BE:A7:10:D7:94:D6:F0:AB:F6:5C:BE:DF:F9:DB:1E:2A:9E:9C:63:BB:C2:BE:69:9B:60", MFC_PACKAGE_NAME)) {
            LogMgr.log(3, "700 Failed to connect for MFC Service. AppCertHash check failed.");
            throw new FelicaException(1, 47);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(MFC_PACKAGE_NAME, MFC_ADAPTER_CLASS_NAME));
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

        /* JADX DEBUG: Another duplicated slice has different insns count: {[]}, finally: {[CONST_STR, CONST_STR, INVOKE, IGET, INVOKE, CONST_STR, CONST_STR, INVOKE, IGET, INVOKE] complete} */
        /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [3653=4, 3655=4] */
        /* JADX DEBUG: Incorrect finally slice size: {[CONST_STR, CONST_STR, INVOKE, IGET, INVOKE, CONST_STR, CONST_STR, INVOKE, IGET, INVOKE, CONST_STR, CONST_STR, INVOKE, IGET, INVOKE, IGET, INVOKE, IGET, INVOKE, GOTO] complete}, expected: {[CONST_STR, CONST_STR, INVOKE, IGET, INVOKE, CONST_STR, CONST_STR, INVOKE, IGET, INVOKE] complete} */
        /* JADX WARN: Finally extract failed */
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            FelicaEventListener felicaEventListener;
            int i;
            AppInfo otherAppInfo;
            FelicaEventListener felicaEventListener2;
            LogMgr.log(3, "%s %s", "000", componentName.getClassName());
            synchronized (Felica.this) {
                Felica.this.felica = IFelica.Stub.asInterface(iBinder);
                Felica.this.bindTimerHandler.stopTimer();
                felicaEventListener = null;
                i = 1;
                if (Felica.this.felicaEventListener != null) {
                    LogMgr.log(7, "%s", "001");
                    try {
                        try {
                            MfcUtil.checkMfcResult(Felica.this.felica.activateFelica(Felica.this.permitList, Felica.this.iFelicaEventListener));
                            LogMgr.log(7, "%s", "010");
                            Felica.this.permitList = null;
                        } catch (Throwable th) {
                            LogMgr.log(7, "%s", "010");
                            Felica.this.permitList = null;
                            throw th;
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
                            otherAppInfo = null;
                        } else {
                            otherAppInfo = e.getOtherAppInfo();
                            LogMgr.log(2, "%s FelicaException id:%d type:%d pid%d", "700", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()), null, Integer.valueOf(otherAppInfo.getPid()));
                            i = 7;
                        }
                        LogMgr.log(7, "%s", "010");
                        Felica.this.permitList = null;
                        LogMgr.log(7, "%s", "011");
                        felicaEventListener2 = Felica.this.felicaEventListener;
                        Felica.this.felicaEventListener = null;
                        Felica.this.unbindMfc();
                        felicaEventListener = felicaEventListener2;
                    } catch (Exception e2) {
                        LogMgr.log(2, "%s Exception %s", "703", e2.getMessage());
                        LogMgr.log(7, "%s", "010");
                        Felica.this.permitList = null;
                        LogMgr.log(7, "%s", "011");
                        felicaEventListener2 = Felica.this.felicaEventListener;
                        Felica.this.felicaEventListener = null;
                        Felica.this.unbindMfc();
                        otherAppInfo = null;
                        felicaEventListener = felicaEventListener2;
                    }
                } else {
                    LogMgr.log(2, "%s", "704");
                    Felica.this.unbindMfc();
                }
                otherAppInfo = null;
            }
            LogMgr.log(7, "%s", "700");
            if (felicaEventListener != null) {
                LogMgr.log(3, "%s Do the callback", "020");
                felicaEventListener.errorOccurred(i, null, otherAppInfo);
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
            Process.killProcess(Process.myPid());
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
        PackageInfo packageInfo;
        ServiceInfo serviceInfo;
        LogMgr.log(3, "%s", "000");
        try {
            PackageManager packageManager = getPackageManager();
            String packageName = getPackageName();
            if (Build.VERSION.SDK_INT >= 33) {
                packageInfo = packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(4L));
            } else {
                packageInfo = packageManager.getPackageInfo(packageName, 4);
            }
            ServiceInfo[] serviceInfoArr = packageInfo.services;
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
            checkActivated();
            MfcUtil.checkMfcResult(this.felica.setSelectTimeout(i));
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
        int iIntValue;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                FelicaResultInfoInt selectTimeout = this.felica.getSelectTimeout();
                MfcUtil.checkMfcResult(selectTimeout);
                iIntValue = selectTimeout.getValue().intValue();
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                throw e;
            }
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "701", "Other Exception");
            throw new FelicaException(1, 47);
        }
        return iIntValue;
    }

    public void cancelOffline() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        try {
            checkActivated();
            MfcUtil.checkMfcResult(this.felica.cancelOffline());
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
        PackageInfo packageInfo;
        LogMgr.log(3, "%s", "000");
        try {
            PackageManager packageManager = context.getPackageManager();
            if (Build.VERSION.SDK_INT >= 33) {
                packageInfo = packageManager.getPackageInfo(MFC_PACKAGE_NAME, PackageManager.PackageInfoFlags.of(0L));
            } else {
                packageInfo = packageManager.getPackageInfo(MFC_PACKAGE_NAME, 0);
            }
            LogMgr.log(3, "%s", "999");
            return packageInfo.versionName;
        } catch (Exception unused) {
            LogMgr.log(2, "%s %s", "800", "package not found");
            throw new FelicaException(3, 60);
        }
    }

    public synchronized byte[] executeFelicaCommand(byte[] bArr) throws FelicaException, IllegalArgumentException {
        byte[] value;
        LogMgr.log(3, "%s", "START");
        try {
            try {
                checkActivated();
                if (bArr == null || bArr.length <= 0 || bArr.length > 254) {
                    LogMgr.log(2, "%s", "Parameter Error");
                    throw new IllegalArgumentException("The specified Command is null or invalid size.");
                }
                FelicaResultInfoByteArray felicaResultInfoByteArrayExecuteFelicaCommand = this.felica.executeFelicaCommand(bArr, this.timeout, this.retryCount);
                MfcUtil.checkMfcResult(felicaResultInfoByteArrayExecuteFelicaCommand);
                value = felicaResultInfoByteArrayExecuteFelicaCommand.getValue();
                LogMgr.log(3, "%s", "END");
            } catch (Exception unused) {
                LogMgr.log(2, "%s", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s id:%d type:%d", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s %s", "IllegalArgumentException", e2.getMessage());
            throw e2;
        }
        return value;
    }

    public synchronized KeyInformation[] getKeyVersionV2(int[] iArr) throws FelicaException, IllegalArgumentException {
        KeyInformation[] value;
        LogMgr.log(3, "%s", "000");
        try {
            try {
                checkActivated();
                if (iArr == null || iArr.length < 1 || iArr.length > 32) {
                    LogMgr.log(1, "%s invalid NodeCodeList", "800");
                    throw new IllegalArgumentException("The specified parameter is invalid.");
                }
                FelicaResultInfoKeyInformationArray keyVersionV2 = this.felica.getKeyVersionV2(iArr, this.timeout, this.retryCount);
                MfcUtil.checkMfcResult(keyVersionV2);
                value = keyVersionV2.getValue();
                LogMgr.log(3, "%s returned %d", "999", value);
            } catch (Exception unused) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e) {
            LogMgr.log(2, "%s %s id:%d type:%d", "700", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            throw e;
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s nodeCode:%d", "702", iArr);
            throw e2;
        }
        return value;
    }
}
