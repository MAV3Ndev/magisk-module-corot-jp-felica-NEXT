package com.felicanetworks.semc.sws;

import android.content.Context;
import com.felicanetworks.semc.DataManager;
import com.felicanetworks.semc.SemChipHolder;
import com.felicanetworks.semc.SemClientConst;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SwsClientFacade implements AsyncTaskBase.Listener {
    private static final int TASK_ID_DELETE_APPLET = 144;
    private static final int TASK_ID_GET_APPLET_STATUS = 160;
    private static final int TASK_ID_GET_CLIENT_CONFIGURATION = 32;
    private static final int TASK_ID_GET_PROCESS_STATUS = 96;
    private static final int TASK_ID_GET_UNIQUE_VALUE = 80;
    private static final int TASK_ID_INSTALL_APPLET = 112;
    private static final int TASK_ID_NOTIFY_CLIENT_EVENT = 64;
    private static final int TASK_ID_REGISTER_DEVICE_TOKEN = 48;
    private static final int TASK_ID_START_ONLINE_SEQUENCE = 16;
    private static final int TASK_ID_UPGRADE_APPLET = 128;
    private AsyncTaskBase<?> mCurrentTask;
    private DataManager mDataManager;
    private ExecutorService mExecutor;
    private OnFinishListener mListener;
    private SemChipHolder mSemChipHolder;
    private SwsClient mSwsClient;

    public interface OnFinishListener {
        void onDeleteAppletFinished(boolean z, String str, String str2, int i, String str3, String str4);

        void onGetAppletStatusFinished(boolean z, String str, String str2, int i, String str3, String str4);

        void onGetClientConfigurationFinished(boolean z, String str, int i, String str2, String str3);

        void onGetProcessStatusFinished(boolean z, String str, String str2, String str3, String str4, JSONArray jSONArray, JSONObject jSONObject, int i, String str5, String str6);

        void onGetUniqueValueFinished(boolean z, String str, int i, String str2, String str3);

        void onInstallAppletFinished(boolean z, String str, String str2, int i, String str3, String str4);

        void onNotifyClientEventFinished(boolean z, int i, String str, String str2);

        void onRegisterDeviceTokenFinished(boolean z, String str, int i, String str2, String str3);

        void onStartTsmSequenceFinished(boolean z, int i, String str, String str2);

        void onUpgradeAppletFinished(boolean z, String str, String str2, int i, String str3, String str4);
    }

    public synchronized void start(OnFinishListener onFinishListener, DataManager dataManager) throws SemClientException {
        LogMgr.log(6, "000");
        checkStarted(false);
        if (onFinishListener == null) {
            LogMgr.log(1, "800 Listener is null.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        if (dataManager == null) {
            LogMgr.log(1, "801 dataManager is null.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        this.mListener = onFinishListener;
        this.mDataManager = dataManager;
        this.mSwsClient = new SwsClient(dataManager);
        generateExecutor();
        LogMgr.log(6, "999");
    }

    public synchronized void startOnlineSequence(String str, SemChipHolder semChipHolder, boolean z, Context context) throws SemClientException {
        LogMgr.log(6, "000");
        checkStarted(true);
        generateExecutor();
        this.mSemChipHolder = semChipHolder;
        StartOnlineSequenceTask startOnlineSequenceTask = new StartOnlineSequenceTask(16, this.mExecutor, this, str, this.mSwsClient, this.mSemChipHolder, this.mDataManager, z, context);
        startOnlineSequenceTask.start();
        this.mCurrentTask = startOnlineSequenceTask;
        LogMgr.log(6, "999");
    }

    public synchronized void getClientConfiguration() throws Throwable {
        try {
            try {
                LogMgr.log(6, "000");
                checkStarted(true);
                checkNotRunningTask();
                generateExecutor();
                GetClientConfigurationTask getClientConfigurationTask = new GetClientConfigurationTask(32, this.mExecutor, this, this.mDataManager, this.mSwsClient);
                getClientConfigurationTask.start();
                this.mCurrentTask = getClientConfigurationTask;
                LogMgr.log(6, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
    }

    public synchronized void registerDeviceToken(String str) throws Throwable {
        try {
            try {
                LogMgr.log(6, "000");
                checkStarted(true);
                checkNotRunningTask();
                generateExecutor();
                RegisterDeviceTokenTask registerDeviceTokenTask = new RegisterDeviceTokenTask(48, this.mExecutor, this, str, this.mSwsClient, this.mDataManager);
                registerDeviceTokenTask.start();
                this.mCurrentTask = registerDeviceTokenTask;
                LogMgr.log(6, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void notifyClientEvent(String str) throws Throwable {
        try {
            try {
                LogMgr.log(6, "000");
                checkStarted(true);
                checkNotRunningTask();
                generateExecutor();
                NotifyClientEventTask notifyClientEventTask = new NotifyClientEventTask(64, this.mExecutor, this, str, this.mSwsClient, this.mDataManager);
                notifyClientEventTask.start();
                this.mCurrentTask = notifyClientEventTask;
                LogMgr.log(6, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void installApplet(String str, String str2, String str3, String str4, String str5, String[] strArr, String str6, String str7) throws SemClientException {
        LogMgr.log(6, "000");
        checkStarted(true);
        checkNotRunningTask();
        generateExecutor();
        InstallAppletTask installAppletTask = new InstallAppletTask(112, this.mExecutor, this, str, str2, str3, str4, str5, strArr, this.mSwsClient, this.mDataManager, str6, str7);
        installAppletTask.start();
        this.mCurrentTask = installAppletTask;
        LogMgr.log(6, "999");
    }

    public synchronized void upgrade(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws SemClientException {
        LogMgr.log(6, "000");
        checkStarted(true);
        checkNotRunningTask();
        generateExecutor();
        UpgradeAppletTask upgradeAppletTask = new UpgradeAppletTask(128, this.mExecutor, this, str, str2, str3, str4, str5, this.mSwsClient, this.mDataManager, str6, str7);
        upgradeAppletTask.start();
        this.mCurrentTask = upgradeAppletTask;
        LogMgr.log(6, "999");
    }

    public synchronized void delete(String str, String str2, String str3, String str4, String str5, String str6) throws SemClientException {
        LogMgr.log(6, "000");
        checkStarted(true);
        checkNotRunningTask();
        generateExecutor();
        DeleteAppletTask deleteAppletTask = new DeleteAppletTask(TASK_ID_DELETE_APPLET, this.mExecutor, this, str, str2, str3, str4, this.mSwsClient, this.mDataManager, str5, str6);
        deleteAppletTask.start();
        this.mCurrentTask = deleteAppletTask;
        LogMgr.log(6, "999");
    }

    public synchronized void getAppletStatus(String str, String str2, String str3, String str4, String str5) throws SemClientException {
        LogMgr.log(6, "000");
        checkStarted(true);
        checkNotRunningTask();
        generateExecutor();
        GetAppletStatusTask getAppletStatusTask = new GetAppletStatusTask(160, this.mExecutor, this, str, str2, str3, this.mSwsClient, this.mDataManager, str4, str5);
        getAppletStatusTask.start();
        this.mCurrentTask = getAppletStatusTask;
        LogMgr.log(6, "999");
    }

    public synchronized void getUniqueValue(String str) throws Throwable {
        try {
            try {
                LogMgr.log(6, "000");
                checkStarted(true);
                checkNotRunningTask();
                generateExecutor();
                GetUniqueValueTask getUniqueValueTask = new GetUniqueValueTask(80, this.mExecutor, this, str, this.mSwsClient, this.mDataManager);
                getUniqueValueTask.start();
                this.mCurrentTask = getUniqueValueTask;
                LogMgr.log(6, "999");
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void getProcessStatus(String str, String str2, String str3, String str4) throws SemClientException {
        LogMgr.log(6, "000");
        checkStarted(true);
        checkNotRunningTask();
        generateExecutor();
        GetProcessStatusTask getProcessStatusTask = new GetProcessStatusTask(96, this.mExecutor, this, str, str2, this.mSwsClient, this.mDataManager, str3, str4);
        getProcessStatusTask.start();
        this.mCurrentTask = getProcessStatusTask;
        LogMgr.log(6, "999");
    }

    private synchronized void checkStarted(Boolean bool) throws SemClientException {
        LogMgr.log(8, "000");
        if ((this.mSwsClient != null) != bool.booleanValue()) {
            if (bool.booleanValue()) {
                LogMgr.log(2, "700 Not started.");
            } else {
                LogMgr.log(2, "701 Already running online task.");
            }
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(8, "999");
    }

    public synchronized void checkNotRunningTask() throws IllegalStateException {
        LogMgr.log(6, "000");
        if (this.mCurrentTask != null) {
            LogMgr.log(2, "700 Already running task.");
            throw new IllegalStateException(SemClientConst.EXC_ILLEGAL_STATE_SEM_SEQUENCE_STARTED);
        }
        LogMgr.log(6, "999");
    }

    private synchronized void generateExecutor() {
        LogMgr.log(8, "000");
        if (this.mExecutor != null) {
            LogMgr.log(8, "001 mExecutor is not null.");
            this.mExecutor.shutdown();
        }
        this.mExecutor = Executors.newSingleThreadExecutor();
        LogMgr.log(8, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00de A[PHI: r2 r3 r5
  0x00de: PHI (r2v41 boolean) = (r2v0 boolean), (r2v50 boolean) binds: [B:30:0x0097, B:44:0x00cf] A[DONT_GENERATE, DONT_INLINE]
  0x00de: PHI (r3v34 int) = (r3v0 int), (r3v40 int) binds: [B:30:0x0097, B:44:0x00cf] A[DONT_GENERATE, DONT_INLINE]
  0x00de: PHI (r5v47 java.lang.String) = (r5v46 java.lang.String), (r5v52 java.lang.String) binds: [B:30:0x0097, B:44:0x00cf] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x014d A[PHI: r2 r3 r5
  0x014d: PHI (r2v29 boolean) = (r2v0 boolean), (r2v38 boolean) binds: [B:54:0x0106, B:68:0x013e] A[DONT_GENERATE, DONT_INLINE]
  0x014d: PHI (r3v26 int) = (r3v0 int), (r3v32 int) binds: [B:54:0x0106, B:68:0x013e] A[DONT_GENERATE, DONT_INLINE]
  0x014d: PHI (r5v38 java.lang.String) = (r5v37 java.lang.String), (r5v43 java.lang.String) binds: [B:54:0x0106, B:68:0x013e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01be A[PHI: r2 r3 r5
  0x01be: PHI (r2v17 boolean) = (r2v0 boolean), (r2v26 boolean) binds: [B:78:0x0177, B:92:0x01af] A[DONT_GENERATE, DONT_INLINE]
  0x01be: PHI (r3v18 int) = (r3v0 int), (r3v24 int) binds: [B:78:0x0177, B:92:0x01af] A[DONT_GENERATE, DONT_INLINE]
  0x01be: PHI (r5v31 java.lang.String) = (r5v30 java.lang.String), (r5v36 java.lang.String) binds: [B:78:0x0177, B:92:0x01af] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01cd  */
    @Override // com.felicanetworks.semc.sws.AsyncTaskBase.Listener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onFinishTask(TaskBase taskBase, boolean z, int i, String str, String str2) throws JSONException {
        String strExecutionPoint;
        int i2;
        boolean z2;
        String strExecutionPoint2;
        boolean z3;
        int i3;
        String strExecutionPoint3;
        int i4;
        boolean z4;
        String processResultCode;
        String processResultDetailCode;
        String processResultMessage;
        JSONArray sdKeyDerivationDataList;
        String str3;
        String str4;
        JSONArray jSONArray;
        int i5;
        String strExecutionPoint4;
        String str5;
        JSONObject appletStatus;
        boolean z5;
        OnFinishListener onFinishListener;
        int i6;
        String str6;
        boolean z6;
        OnFinishListener onFinishListener2;
        String strExecutionPoint5;
        int i7;
        String strExecutionPoint6;
        boolean z7;
        OnFinishListener onFinishListener3;
        int i8;
        String strExecutionPoint7;
        boolean z8;
        OnFinishListener onFinishListener4;
        int i9;
        String strExecutionPoint8;
        boolean z9;
        OnFinishListener onFinishListener5;
        boolean z10 = z;
        int i10 = i;
        StringBuilder sb = new StringBuilder("000 task is null=[");
        sb.append(taskBase == null);
        sb.append("] isSuccess=[");
        sb.append(z10);
        sb.append("] errType=[");
        sb.append(i10);
        sb.append("] additionalErrorInfo=[");
        sb.append(str);
        sb.append("] errMsg=[");
        sb.append(str2);
        sb.append("]");
        LogMgr.log(6, sb.toString());
        int taskId = ((TaskBase) Objects.requireNonNull(taskBase)).getTaskId();
        if (taskId == 16) {
            LogMgr.log(8, "001 startOnlineSequence task finished.");
            OnFinishListener onFinishListener6 = this.mListener;
            if (onFinishListener6 != null) {
                onFinishListener6.onStartTsmSequenceFinished(z10, i10, str, str2);
            } else {
                LogMgr.log(2, "700 listener is null. Could not notify finished a task.");
            }
        } else if (taskId == 32) {
            LogMgr.log(8, "002 getClientConfiguration task finished.");
            if (taskBase instanceof GetClientConfigurationTask) {
                String clientConfiguration = ((GetClientConfigurationTask) taskBase).getClientConfiguration();
                if (z10 && (clientConfiguration == null || clientConfiguration.isEmpty())) {
                    LogMgr.log(2, "701 clientConfiguration is null or empty.");
                    strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
                    z2 = false;
                    i2 = 900;
                } else {
                    strExecutionPoint = str2;
                    i2 = i10;
                    z2 = z10;
                }
                OnFinishListener onFinishListener7 = this.mListener;
                if (onFinishListener7 != null) {
                    onFinishListener7.onGetClientConfigurationFinished(z2, clientConfiguration, i2, str, strExecutionPoint);
                } else {
                    LogMgr.log(2, "702 listener is null. Could not notify finished a task.");
                }
            }
        } else if (taskId == 48) {
            LogMgr.log(8, "003 registerDeviceToken task finished.");
            if (taskBase instanceof RegisterDeviceTokenTask) {
                String deviceToken = ((RegisterDeviceTokenTask) taskBase).getDeviceToken();
                if (deviceToken == null || deviceToken.isEmpty()) {
                    LogMgr.log(2, "703 deviceToken is null or empty.");
                    strExecutionPoint2 = ObfuscatedMsgUtil.executionPoint();
                    z3 = false;
                    i3 = 900;
                } else {
                    strExecutionPoint2 = str2;
                    i3 = i10;
                    z3 = z10;
                }
                OnFinishListener onFinishListener8 = this.mListener;
                if (onFinishListener8 != null) {
                    onFinishListener8.onRegisterDeviceTokenFinished(z3, deviceToken, i3, str, strExecutionPoint2);
                } else {
                    LogMgr.log(2, "704 listener is null. Could not notify finished a task.");
                }
            }
        } else if (taskId == 64) {
            LogMgr.log(8, "004 notifyClientEvent task finished.");
            OnFinishListener onFinishListener9 = this.mListener;
            if (onFinishListener9 != null) {
                onFinishListener9.onNotifyClientEventFinished(z10, i10, str, str2);
            } else {
                LogMgr.log(2, "705 listener is null. Could not notify finished a task.");
            }
        } else if (taskId == 80) {
            LogMgr.log(8, "005 getUniqueValue task finished.");
            if (taskBase instanceof GetUniqueValueTask) {
                String uniqueValue = ((GetUniqueValueTask) taskBase).getUniqueValue();
                if (z10 && (uniqueValue == null || uniqueValue.isEmpty())) {
                    LogMgr.log(2, "706 uniqueValue is null or empty.");
                    strExecutionPoint3 = ObfuscatedMsgUtil.executionPoint();
                    z4 = false;
                    i4 = 900;
                } else {
                    strExecutionPoint3 = str2;
                    i4 = i10;
                    z4 = z10;
                }
                OnFinishListener onFinishListener10 = this.mListener;
                if (onFinishListener10 != null) {
                    onFinishListener10.onGetUniqueValueFinished(z4, uniqueValue, i4, str, strExecutionPoint3);
                } else {
                    LogMgr.log(2, "707 listener is null. Could not notify finished a task.");
                }
            }
        } else if (taskId == 96) {
            String strExecutionPoint9 = str2;
            LogMgr.log(8, "006 getProcessStatus task finished.");
            if (taskBase instanceof GetProcessStatusTask) {
                GetProcessStatusTask getProcessStatusTask = (GetProcessStatusTask) taskBase;
                String processStatus = getProcessStatusTask.getProcessStatus();
                if (z10 && (processStatus == null || processStatus.isEmpty())) {
                    LogMgr.log(2, "708 processStatus is null or empty.");
                    strExecutionPoint9 = ObfuscatedMsgUtil.executionPoint();
                    z10 = false;
                    i10 = 900;
                }
                try {
                    processResultCode = getProcessStatusTask.getProcessResultCode();
                    try {
                        processResultDetailCode = getProcessStatusTask.getProcessResultDetailCode();
                        try {
                            processResultMessage = getProcessStatusTask.getProcessResultMessage();
                            try {
                                sdKeyDerivationDataList = getProcessStatusTask.getSdKeyDerivationDataList();
                                try {
                                    strExecutionPoint4 = strExecutionPoint9;
                                    str5 = processResultCode;
                                    appletStatus = getProcessStatusTask.getAppletStatus();
                                    str4 = processResultMessage;
                                    str3 = processResultDetailCode;
                                    jSONArray = sdKeyDerivationDataList;
                                    i5 = i10;
                                } catch (JSONException e) {
                                    e = e;
                                    LogMgr.log(1, "800 Failed to get Response Payload.");
                                    if (!z10) {
                                        z5 = false;
                                        str3 = processResultDetailCode;
                                        i5 = 900;
                                        str4 = processResultMessage;
                                        jSONArray = sdKeyDerivationDataList;
                                        strExecutionPoint4 = ObfuscatedMsgUtil.executionPoint(e);
                                        str5 = processResultCode;
                                        appletStatus = null;
                                        onFinishListener = this.mListener;
                                        if (onFinishListener == null) {
                                        }
                                        LogMgr.log(6, "999");
                                    }
                                    str3 = processResultDetailCode;
                                    str4 = processResultMessage;
                                    jSONArray = sdKeyDerivationDataList;
                                    i5 = i10;
                                    strExecutionPoint4 = strExecutionPoint9;
                                    str5 = processResultCode;
                                    appletStatus = null;
                                }
                            } catch (JSONException e2) {
                                e = e2;
                                sdKeyDerivationDataList = null;
                                LogMgr.log(1, "800 Failed to get Response Payload.");
                                if (!z10) {
                                }
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            processResultMessage = null;
                            sdKeyDerivationDataList = null;
                            LogMgr.log(1, "800 Failed to get Response Payload.");
                            if (!z10) {
                            }
                        }
                    } catch (JSONException e4) {
                        e = e4;
                        processResultDetailCode = null;
                        processResultMessage = null;
                        sdKeyDerivationDataList = null;
                        LogMgr.log(1, "800 Failed to get Response Payload.");
                        if (!z10) {
                        }
                    }
                } catch (JSONException e5) {
                    e = e5;
                    processResultCode = null;
                }
                z5 = z10;
                onFinishListener = this.mListener;
                if (onFinishListener == null) {
                    onFinishListener.onGetProcessStatusFinished(z5, processStatus, str5, str3, str4, jSONArray, appletStatus, i5, str, strExecutionPoint4);
                } else {
                    LogMgr.log(2, "709 listener is null. Could not notify finished a task.");
                }
            }
        } else if (taskId == 112) {
            LogMgr.log(8, "007 installApplet task finished.");
            if (taskBase instanceof InstallAppletTask) {
                InstallAppletTask installAppletTask = (InstallAppletTask) taskBase;
                String linkageData = installAppletTask.getLinkageData();
                String processId = installAppletTask.getProcessId();
                if (z10) {
                    if ((linkageData == null || linkageData.isEmpty()) && (processId == null || processId.isEmpty())) {
                        LogMgr.log(2, "710 linkageData and processId are null or empty.");
                        strExecutionPoint5 = ObfuscatedMsgUtil.executionPoint();
                    } else if (linkageData == null || linkageData.isEmpty()) {
                        LogMgr.log(2, "711 linkageData is null or empty.");
                        strExecutionPoint5 = ObfuscatedMsgUtil.executionPoint();
                    } else {
                        if (processId == null || processId.isEmpty()) {
                            LogMgr.log(2, "712 processId is null or empty.");
                            strExecutionPoint5 = ObfuscatedMsgUtil.executionPoint();
                        }
                        i6 = i10;
                        str6 = str2;
                        z6 = z10;
                        onFinishListener2 = this.mListener;
                        if (onFinishListener2 != null) {
                        }
                    }
                    z6 = false;
                    i6 = 900;
                    str6 = strExecutionPoint5;
                    onFinishListener2 = this.mListener;
                    if (onFinishListener2 != null) {
                    }
                } else {
                    i6 = i10;
                    str6 = str2;
                    z6 = z10;
                    onFinishListener2 = this.mListener;
                    if (onFinishListener2 != null) {
                        onFinishListener2.onInstallAppletFinished(z6, linkageData, processId, i6, str, str6);
                    } else {
                        LogMgr.log(2, "713 listener is null. Could not notify finished a task.");
                    }
                }
            }
        } else if (taskId == 128) {
            String strExecutionPoint10 = str2;
            LogMgr.log(8, "008 upgradeApplet task finished.");
            if (taskBase instanceof UpgradeAppletTask) {
                UpgradeAppletTask upgradeAppletTask = (UpgradeAppletTask) taskBase;
                String linkageData2 = upgradeAppletTask.getLinkageData();
                String processId2 = upgradeAppletTask.getProcessId();
                if (z10) {
                    if ((linkageData2 == null || linkageData2.isEmpty()) && (processId2 == null || processId2.isEmpty())) {
                        LogMgr.log(2, "714 linkageData and processId are null or empty.");
                        strExecutionPoint10 = ObfuscatedMsgUtil.executionPoint();
                        z10 = false;
                        i10 = 900;
                    }
                    if (linkageData2 == null || linkageData2.isEmpty()) {
                        LogMgr.log(2, "715 linkageData is null or empty.");
                        strExecutionPoint10 = ObfuscatedMsgUtil.executionPoint();
                        z10 = false;
                        i10 = 900;
                    }
                    if (processId2 == null || processId2.isEmpty()) {
                        LogMgr.log(2, "716 processId is null or empty.");
                        z7 = false;
                        i7 = 900;
                        strExecutionPoint6 = ObfuscatedMsgUtil.executionPoint();
                    }
                    onFinishListener3 = this.mListener;
                    if (onFinishListener3 == null) {
                    }
                } else {
                    i7 = i10;
                    strExecutionPoint6 = strExecutionPoint10;
                    z7 = z10;
                    onFinishListener3 = this.mListener;
                    if (onFinishListener3 == null) {
                        onFinishListener3.onUpgradeAppletFinished(z7, linkageData2, processId2, i7, str, strExecutionPoint6);
                    } else {
                        LogMgr.log(2, "717 listener is null. Could not notify finished a task.");
                    }
                }
            }
        } else if (taskId == TASK_ID_DELETE_APPLET) {
            String strExecutionPoint11 = str2;
            LogMgr.log(8, "009 deleteApplet task finished.");
            if (taskBase instanceof DeleteAppletTask) {
                DeleteAppletTask deleteAppletTask = (DeleteAppletTask) taskBase;
                String linkageData3 = deleteAppletTask.getLinkageData();
                String processId3 = deleteAppletTask.getProcessId();
                if (z10) {
                    if ((linkageData3 == null || linkageData3.isEmpty()) && (processId3 == null || processId3.isEmpty())) {
                        LogMgr.log(2, "718 linkageData and processId are null or empty.");
                        strExecutionPoint11 = ObfuscatedMsgUtil.executionPoint();
                        z10 = false;
                        i10 = 900;
                    }
                    if (linkageData3 == null || linkageData3.isEmpty()) {
                        LogMgr.log(2, "719 linkageData is null or empty.");
                        strExecutionPoint11 = ObfuscatedMsgUtil.executionPoint();
                        z10 = false;
                        i10 = 900;
                    }
                    if (processId3 == null || processId3.isEmpty()) {
                        LogMgr.log(2, "720 processId is null or empty.");
                        z8 = false;
                        i8 = 900;
                        strExecutionPoint7 = ObfuscatedMsgUtil.executionPoint();
                    }
                    onFinishListener4 = this.mListener;
                    if (onFinishListener4 == null) {
                    }
                } else {
                    i8 = i10;
                    strExecutionPoint7 = strExecutionPoint11;
                    z8 = z10;
                    onFinishListener4 = this.mListener;
                    if (onFinishListener4 == null) {
                        onFinishListener4.onDeleteAppletFinished(z8, linkageData3, processId3, i8, str, strExecutionPoint7);
                    } else {
                        LogMgr.log(2, "721 listener is null. Could not notify finished a task.");
                    }
                }
            }
        } else if (taskId == 160) {
            LogMgr.log(8, "010 getAppletStatus task finished.");
            if (taskBase instanceof GetAppletStatusTask) {
                GetAppletStatusTask getAppletStatusTask = (GetAppletStatusTask) taskBase;
                String strExecutionPoint12 = str2;
                String linkageData4 = getAppletStatusTask.getLinkageData();
                String processId4 = getAppletStatusTask.getProcessId();
                if (z10) {
                    if ((linkageData4 == null || linkageData4.isEmpty()) && (processId4 == null || processId4.isEmpty())) {
                        LogMgr.log(2, "722 linkageData and processId are null or empty.");
                        strExecutionPoint12 = ObfuscatedMsgUtil.executionPoint();
                        z10 = false;
                        i10 = 900;
                    }
                    if (linkageData4 == null || linkageData4.isEmpty()) {
                        LogMgr.log(2, "723 linkageData is null or empty.");
                        strExecutionPoint12 = ObfuscatedMsgUtil.executionPoint();
                        z10 = false;
                        i10 = 900;
                    }
                    if (processId4 == null || processId4.isEmpty()) {
                        LogMgr.log(2, "724 processId is null or empty.");
                        z9 = false;
                        i9 = 900;
                        strExecutionPoint8 = ObfuscatedMsgUtil.executionPoint();
                    }
                    onFinishListener5 = this.mListener;
                    if (onFinishListener5 == null) {
                    }
                } else {
                    i9 = i10;
                    strExecutionPoint8 = strExecutionPoint12;
                    z9 = z10;
                    onFinishListener5 = this.mListener;
                    if (onFinishListener5 == null) {
                        onFinishListener5.onGetAppletStatusFinished(z9, linkageData4, processId4, i9, str, strExecutionPoint8);
                    } else {
                        LogMgr.log(2, "725 listener is null. Could not notify finished a task.");
                    }
                }
            }
        }
        LogMgr.log(6, "999");
    }

    public synchronized void stop() {
        LogMgr.log(6, "000");
        AsyncTaskBase<?> asyncTaskBase = this.mCurrentTask;
        if (asyncTaskBase != null) {
            asyncTaskBase.stop();
        }
        SwsClient swsClient = this.mSwsClient;
        if (swsClient != null) {
            swsClient.stop();
        } else {
            LogMgr.log(9, "001 SwsClient is null.");
        }
        SemChipHolder semChipHolder = this.mSemChipHolder;
        if (semChipHolder != null) {
            semChipHolder.reset();
        }
        ExecutorService executorService = this.mExecutor;
        if (executorService != null) {
            executorService.shutdown();
        }
        LogMgr.log(6, "999");
    }

    public synchronized void finish() {
        LogMgr.log(6, "000");
        this.mListener = null;
        AsyncTaskBase<?> asyncTaskBase = this.mCurrentTask;
        if (asyncTaskBase != null) {
            asyncTaskBase.stop();
            this.mCurrentTask = null;
        }
        SwsClient swsClient = this.mSwsClient;
        if (swsClient != null) {
            swsClient.stop();
            this.mSwsClient = null;
        } else {
            LogMgr.log(9, "002 SwsClient is null.");
        }
        SemChipHolder semChipHolder = this.mSemChipHolder;
        if (semChipHolder != null) {
            semChipHolder.reset();
            this.mSemChipHolder = null;
        }
        ExecutorService executorService = this.mExecutor;
        if (executorService != null) {
            executorService.shutdown();
            this.mExecutor = null;
        }
        if (this.mDataManager != null) {
            this.mDataManager = null;
        }
        LogMgr.log(6, "999");
    }
}
