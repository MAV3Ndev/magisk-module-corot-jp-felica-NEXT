package com.felicanetworks.semc;

import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserManager;
import android.widget.Toast;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import com.felicanetworks.semc.ISemClient;
import com.felicanetworks.semc.SemChipHolder;
import com.felicanetworks.semc.SemClient;
import com.felicanetworks.semc.SemClientExternalLogConst;
import com.felicanetworks.semc.config.AccessConfig;
import com.felicanetworks.semc.fcm.FcmGetTokenFuture;
import com.felicanetworks.semc.omapi.CrsManager;
import com.felicanetworks.semc.omapi.GpController;
import com.felicanetworks.semc.omapi.GpException;
import com.felicanetworks.semc.permit.SpAppInfo;
import com.felicanetworks.semc.sws.RoutineWorker;
import com.felicanetworks.semc.sws.SwsClientFacade;
import com.felicanetworks.semc.sws.SwsParamCreator;
import com.felicanetworks.semc.sws.json.ClientConfigurationJson;
import com.felicanetworks.semc.sws.json.ClientControlInfoJsonArray;
import com.felicanetworks.semc.sws.json.JwsException;
import com.felicanetworks.semc.sws.json.JwsObject;
import com.felicanetworks.semc.sws.json.ProfileDataJson;
import com.felicanetworks.semc.sws.json.SpAppInfoJsonArray;
import com.felicanetworks.semc.util.CommonConfig;
import com.felicanetworks.semc.util.DateUtil;
import com.felicanetworks.semc.util.ErrorCodeConverter;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SettingInfo;
import com.felicanetworks.semc.util.SharedPrefsUtil;
import com.felicanetworks.semc.util.SignatureUtil;
import com.felicanetworks.semc.util.StringUtil;
import com.felicanetworks.semc.util.TaskInterruptedLatchManager;
import com.google.common.base.Ascii;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ISemClientImpl extends ISemClient.Stub {
    private static final int CARRIER_ID_LENGTH = 6;
    private static final String DEEMED_CAREER_ID = "100008";
    private static final String DEEMED_CHIP_TYPE = "1";
    private static final String DEEMED_SEP_ID = "000068";
    private static final String KEY_ACCESS_ALLOWED_SP_APP_ID_LIST = "accessAllowedSpAppIdList";
    private static final String KEY_APPLET_STATUS = "appletStatus";
    private static final String KEY_APPLET_STATUS_LIST = "appletStatusList";
    private static final String KEY_SERVICE_ID = "serviceId";
    private static final String KEY_SP_APPLET_ID = "spAppletId";
    private static final String KEY_SP_APPLET_VERSION = "spAppletVersion";
    private static final int MAJOR_VERSION_INDEX = 0;
    private static final int MINOR_VERSION_INDEX = 1;
    private static final String PROCESS_RESULT_CODE_SUCCESS = "0000";
    private static final int REVISION_VERSION_INDEX = 2;
    private static final int SUPPORTED_SEMC_API_VERSION = 1;
    private static final int TASK_INTERRUPTED_WAIT_TIMEOUT = 10;
    private static final String USE_CASE_DELETE_APPLET = "AppletDeletion";
    private static final String USE_CASE_GET_APPLET_STATUS = "GetAppletStatus";
    private static final String USE_CASE_INSTALL_APPLET = "AppletInstallation";
    private static final String USE_CASE_UPGRADE_APPLET = "AppletUpgrade";
    private static final int VERSION_ELEMENT_NUMBER = 3;
    private static final String VERSION_SEPARATOR = ".";
    private static volatile ISemClientImpl sISemClientImpl;
    private JSONObject mAppletStatus;
    private CountDownLatch mConnectGetClientConfigLatch;
    private CountDownLatch mConnectRegisterDeviceTokenLatch;
    private ConnectThread mConnectWorker;
    private LocalDeathRecipient mDeathRecipient;
    private CountDownLatch mDeleteAppletLatch;
    private DeleteAppletThread mDeleteAppletThread;
    private CountDownLatch mDisConnectChipHolderCancelLatch;
    private DisConnectThread mDisConnectWorker;
    private CountDownLatch mGetAppletStatusLatch;
    private GetAppletStatusThread mGetAppletStatusThread;
    private CountDownLatch mGetProcessStatusLatch;
    private CountDownLatch mGetUniqueValueLatch;
    private final Handler mHandlerForToast;
    private CountDownLatch mInstallAppletLatch;
    private InstallAppletThread mInstallAppletThread;
    private AtomicBoolean mIsConnectInterrupted;
    private AtomicBoolean mIsConnecting;
    private AtomicBoolean mIsRunningConnectThread;
    private AtomicBoolean mIsRunningStartTsmSequence;
    private String mLinkageData;
    public int mPeriodicWorkStartTimeMax;
    public int mPeriodicWorkStartTimeMin;
    private String mProcessId;
    private String mProcessResultCode;
    private String mProcessResultDetailCode;
    private String mProcessResultMessageString;
    private String mProcessStatus;
    private JSONArray mSdKeyDerivationDataList;
    private SemListener mSemListener;
    private CountDownLatch mStartTsmSequenceLatch;
    private String mUniqueValue;
    private CountDownLatch mUpgradeAppletLatch;
    private UpgradeAppletThread mUpgradeAppletThread;
    private CountDownLatch mWaitDisconnectFinishedLatch;
    private static final Object sLock = new Object();
    private static final byte[] SEID_PREFIX = {-16};
    private final TaskInterruptedLatchManager mTaskInterruptedLatchManager = new TaskInterruptedLatchManager();
    private SwsClientFacade mSwsClientFacade = new SwsClientFacade();
    private Context mContext = null;
    private SemClientAppInfo mConnectedApp = new SemClientAppInfo();
    private SemChipHolder mSemChipHolder = null;
    private DataManager mDataManager = null;
    private SharedPrefsUtil mSharedPrefsUtil = null;

    public ISemClientImpl() {
        this.mHandlerForToast = new Handler(Looper.myLooper() != null ? Looper.myLooper() : Looper.getMainLooper());
        this.mConnectGetClientConfigLatch = null;
        this.mConnectRegisterDeviceTokenLatch = null;
        this.mDisConnectChipHolderCancelLatch = null;
        this.mWaitDisconnectFinishedLatch = null;
        this.mGetUniqueValueLatch = null;
        this.mUniqueValue = null;
        this.mGetProcessStatusLatch = null;
        this.mProcessStatus = null;
        this.mProcessResultCode = null;
        this.mProcessResultDetailCode = null;
        this.mProcessResultMessageString = null;
        this.mAppletStatus = null;
        this.mIsConnecting = new AtomicBoolean(false);
        this.mIsRunningConnectThread = new AtomicBoolean(false);
        this.mIsConnectInterrupted = new AtomicBoolean(false);
        this.mIsRunningStartTsmSequence = new AtomicBoolean(false);
        this.mPeriodicWorkStartTimeMin = 0;
        this.mPeriodicWorkStartTimeMax = 6;
        this.mInstallAppletLatch = null;
        this.mUpgradeAppletLatch = null;
        this.mDeleteAppletLatch = null;
        this.mGetAppletStatusLatch = null;
        this.mStartTsmSequenceLatch = null;
    }

    public static ISemClientImpl getInstance() {
        LogMgr.log(5, "000");
        if (sISemClientImpl == null) {
            synchronized (sLock) {
                if (sISemClientImpl == null) {
                    sISemClientImpl = new ISemClientImpl();
                }
            }
        }
        LogMgr.log(5, "999");
        return sISemClientImpl;
    }

    public synchronized void init(Context context) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(1, "800 context is null.");
            throw new IllegalArgumentException(ObfuscatedMsgUtil.executionPoint());
        }
        this.mContext = context;
        this.mSharedPrefsUtil = new SharedPrefsUtil(context);
        this.mDataManager = new DataManager();
        this.mSemListener = new SemListener() { // from class: com.felicanetworks.semc.ISemClientImpl$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.felicanetworks.semc.SemListener
            public final void semCancel() {
                ISemClientImpl.lambda$init$0();
            }
        };
        LogMgr.log(5, "999");
    }

    static /* synthetic */ void lambda$init$0() {
        LogMgr.log(9, "000");
        if (sISemClientImpl != null) {
            sISemClientImpl.disconnect(false);
        }
        LogMgr.log(9, "999");
    }

    public synchronized SemClientResultInfo disconnect(boolean z) {
        CountDownLatch countDownLatch;
        String str;
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DISCONNECT, "isCheckProcess[" + z + "]");
        LogMgr.log(5, "000");
        SemClientResultInfo semClientResultInfo = new SemClientResultInfo();
        boolean z2 = false;
        try {
            try {
                try {
                    try {
                    } catch (Throwable th) {
                        th = th;
                        if (z2 && (countDownLatch = this.mWaitDisconnectFinishedLatch) != null) {
                            countDownLatch.countDown();
                            this.mWaitDisconnectFinishedLatch = null;
                            LogMgr.log(8, "Canceled WaitDisconnectFinishedLatch.");
                        }
                        throw th;
                    }
                } catch (IllegalStateException e) {
                    this.mConnectedApp = null;
                    semClientResultInfo = new SemClientResultInfo(33, e.getMessage());
                    LogMgr.log(2, "800 IllegalStateException occurred.");
                    LogMgr.exLogException(SemClientExternalLogConst.SemcApi.DISCONNECT, e);
                    CountDownLatch countDownLatch2 = this.mWaitDisconnectFinishedLatch;
                    if (countDownLatch2 != null) {
                        countDownLatch2.countDown();
                        this.mWaitDisconnectFinishedLatch = null;
                        str = "Canceled WaitDisconnectFinishedLatch.";
                        LogMgr.log(8, str);
                    }
                    LogMgr.log(5, "999");
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DISCONNECT);
                    return semClientResultInfo;
                }
            } catch (Exception e2) {
                this.mConnectedApp = null;
                semClientResultInfo = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
                LogMgr.log(1, "801 Exception occurred. e[" + e2 + "]");
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.DISCONNECT, 900);
                CountDownLatch countDownLatch3 = this.mWaitDisconnectFinishedLatch;
                if (countDownLatch3 != null) {
                    countDownLatch3.countDown();
                    this.mWaitDisconnectFinishedLatch = null;
                    str = "Canceled WaitDisconnectFinishedLatch.";
                    LogMgr.log(8, str);
                }
                LogMgr.log(5, "999");
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DISCONNECT);
                return semClientResultInfo;
            }
            if (this.mConnectedApp == null) {
                LogMgr.log(8, "001mConnectedApp is null");
                LogMgr.log(5, "998");
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DISCONNECT);
                return semClientResultInfo;
            }
            if (z) {
                checkPidUid();
            }
            if (this.mConnectWorker != null) {
                LogMgr.log(8, "002mConnectWorker is not null");
                try {
                    this.mIsConnectInterrupted.compareAndSet(false, true);
                } catch (Exception e3) {
                    LogMgr.log(2, "700" + e3.getClass().getSimpleName());
                    LogMgr.printStackTrace(8, e3);
                }
            }
            if (this.mWaitDisconnectFinishedLatch == null) {
                this.mWaitDisconnectFinishedLatch = new CountDownLatch(1);
            }
            DisConnectThread disConnectThread = new DisConnectThread();
            this.mDisConnectWorker = disConnectThread;
            disConnectThread.start();
            LogMgr.log(5, "999");
            LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DISCONNECT);
            return semClientResultInfo;
        } catch (Throwable th2) {
            th = th2;
            z2 = true;
        }
    }

    public synchronized boolean isConnecting() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mIsConnecting.get();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00fd  */
    @Override // com.felicanetworks.semc.ISemClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SemClientResultInfo callSemClientApi(SemClientApiInfo semClientApiInfo, ISemClientEventListener iSemClientEventListener, int i) throws Throwable {
        String serviceId;
        String spAppletId;
        String operationType;
        String operationId;
        SemClientResultInfo semClientResultInfoConnect;
        LogMgr.log(5, "000");
        SemClientResultInfo semClientResultInfo = null;
        if (semClientApiInfo == null) {
            LogMgr.log(1, "800 apiInfo is null");
            return null;
        }
        byte b = 9;
        LogMgr.log(9, "001 SEMC API version:" + i + " (supported:1)");
        try {
            String methodName = semClientApiInfo.getMethodName();
            String[] strArr = {SemClientApiInfo.METHOD_NAME_INSTALL_APPLET, SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET, SemClientApiInfo.METHOD_NAME_DELETE_APPLET, SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS};
            LogMgr.log(8, "002 methodName[" + methodName + "]");
            if (Arrays.asList(strArr).contains(methodName)) {
                serviceId = semClientApiInfo.getServiceId();
                spAppletId = semClientApiInfo.getSpAppletId();
                operationType = semClientApiInfo.getOperationType();
                operationId = semClientApiInfo.getOperationId();
            } else {
                serviceId = null;
                spAppletId = null;
                operationType = null;
                operationId = null;
            }
            switch (methodName.hashCode()) {
                case -1279119899:
                    b = !methodName.equals(SemClientApiInfo.METHOD_NAME_DELETE_APPLET) ? (byte) -1 : Ascii.VT;
                    break;
                case -714207610:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT)) {
                        b = 8;
                        break;
                    }
                    break;
                case -397719277:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_ACTIVATE_CONTACTLESS_INTERFACE)) {
                        b = 6;
                        break;
                    }
                    break;
                case -75155613:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_GET_SEID)) {
                        b = 2;
                        break;
                    }
                    break;
                case 193358934:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_GET_SE_READER_NAME)) {
                        b = 3;
                        break;
                    }
                    break;
                case 530405532:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_DISCONNECT)) {
                        b = 1;
                        break;
                    }
                    break;
                case 643131798:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET)) {
                        b = 10;
                        break;
                    }
                    break;
                case 926179208:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_GET_CONTACTLESS_INTERFACE_STATUS)) {
                        b = 5;
                        break;
                    }
                    break;
                case 951351530:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_CONNECT)) {
                        b = 0;
                        break;
                    }
                    break;
                case 1632281069:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE)) {
                        b = 4;
                        break;
                    }
                    break;
                case 1689062242:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS)) {
                        b = Ascii.FF;
                        break;
                    }
                    break;
                case 2017688084:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_DEACTIVATE_CONTACTLESS_INTERFACE)) {
                        b = 7;
                        break;
                    }
                    break;
                case 2104367573:
                    if (methodName.equals(SemClientApiInfo.METHOD_NAME_INSTALL_APPLET)) {
                        break;
                    }
                    break;
                default:
                    break;
            }
            switch (b) {
                case 0:
                    semClientResultInfoConnect = connect(iSemClientEventListener, semClientApiInfo.getIsCheckSystemUser(), semClientApiInfo.getIsCalledFromInternal(), semClientApiInfo.getPackageName(), semClientApiInfo.getIsModeExists(), semClientApiInfo.getMode(), semClientApiInfo.getIsReConnectSEMCApp());
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 1:
                    semClientResultInfoConnect = disconnect(!semClientApiInfo.getIsCalledFromInternal());
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 2:
                    semClientResultInfoConnect = getSeid();
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 3:
                    semClientResultInfoConnect = getSeReaderName();
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 4:
                    semClientResultInfoConnect = startTsmSequence(semClientApiInfo.getLinkageData(), iSemClientEventListener, false);
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 5:
                    semClientResultInfoConnect = getContactlessInterfaceStatus(semClientApiInfo.getAid());
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 6:
                    semClientResultInfoConnect = activateContactlessInterface(semClientApiInfo.getAid());
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 7:
                    semClientResultInfoConnect = deactivateContactlessInterface(semClientApiInfo.getAid());
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 8:
                    semClientResultInfoConnect = notifyClientEvent(semClientApiInfo.getPackageName(), semClientApiInfo.getEventType(), iSemClientEventListener);
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 9:
                    try {
                        semClientResultInfoConnect = installApplet(serviceId, spAppletId, semClientApiInfo.getSpAppletVersion(), semClientApiInfo.getSpSdKeyVersion(), semClientApiInfo.getAccessAllowedSpAppIdList(), operationType, operationId, iSemClientEventListener);
                        semClientResultInfo = semClientResultInfoConnect;
                    } catch (SemClientException e) {
                        e = e;
                        LogMgr.log(1, "803 SemClientException occurred. error code:" + e.getErrorCode() + " error message:" + e.getMessage());
                    }
                    break;
                case 10:
                    semClientResultInfoConnect = upgradeApplet(serviceId, spAppletId, semClientApiInfo.getOldSpAppletVersion(), semClientApiInfo.getNewSpAppletVersion(), operationType, operationId, iSemClientEventListener);
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 11:
                    semClientResultInfoConnect = deleteApplet(serviceId, spAppletId, semClientApiInfo.getSpAppletVersion(), operationType, operationId, iSemClientEventListener);
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                case 12:
                    semClientResultInfoConnect = getAppletStatus(serviceId, spAppletId, operationType, operationId, iSemClientEventListener);
                    semClientResultInfo = semClientResultInfoConnect;
                    break;
                default:
                    LogMgr.log(1, "802 Unsupported method. method:" + methodName);
                    break;
            }
        } catch (SemClientException e2) {
            e = e2;
        }
        LogMgr.log(5, "999 semClientResultInfo:" + semClientResultInfo);
        return semClientResultInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerDeviceToken() throws InterruptedException, SemClientException {
        LogMgr.log(8, "000");
        this.mConnectRegisterDeviceTokenLatch = new CountDownLatch(1);
        InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
        internalSemClientEventListener.setCountDownLatch(this.mConnectRegisterDeviceTokenLatch);
        SemClientResultInfo semClientResultInfoDoRegisterDeviceToken = doRegisterDeviceToken(internalSemClientEventListener);
        if (semClientResultInfoDoRegisterDeviceToken.mExceptionType != 0) {
            LogMgr.log(2, "700 Failed to exec registerDeviceToken. message:" + semClientResultInfoDoRegisterDeviceToken.getMessage() + " code:" + semClientResultInfoDoRegisterDeviceToken.getErrorCode());
            return;
        }
        LogMgr.log(9, "001 ConnectRegisterDeviceTokenLatch.await()");
        this.mConnectRegisterDeviceTokenLatch.await();
        if (!internalSemClientEventListener.isSuccessToRegisterDeviceToken()) {
            LogMgr.log(2, "701 Failed to exec registerDeviceToken. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
        }
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    private SemClientResultInfo doRegisterDeviceToken(ISemClientEventListener iSemClientEventListener) {
        LogMgr.performanceIn("API", "ISemClientImpl", "registerDeviceToken");
        LogMgr.log(5, "000");
        try {
            String str = new FcmGetTokenFuture(this.mContext).get();
            if (str == null) {
                LogMgr.log(1, "800 newDeviceToken is null.");
                CountDownLatch countDownLatch = this.mConnectRegisterDeviceTokenLatch;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
                LogMgr.log(5, "998");
                return new SemClientResultInfo();
            }
            String deviceToken = this.mSharedPrefsUtil.readDeviceToken();
            if (deviceToken == null || !deviceToken.equals(str)) {
                ClientControlInfoJsonArray clientControlInfoJSONArray = getClientControlInfoJSONArray();
                if (clientControlInfoJSONArray != null) {
                    this.mDataManager.setClientControlInfo(clientControlInfoJSONArray.getClientControlInfo());
                }
                String clientId = this.mSharedPrefsUtil.readClientId();
                if (clientId == null) {
                    clientId = SwsParamCreator.createClientId();
                    this.mSharedPrefsUtil.writeClientId(clientId);
                }
                this.mDataManager.setClientId(clientId);
                this.mDataManager.setPackageName(this.mContext.getPackageName());
                this.mSwsClientFacade.start(new OnFinishListener(iSemClientEventListener), this.mDataManager);
                this.mSwsClientFacade.registerDeviceToken(str);
                LogMgr.log(5, "999");
            } else {
                iSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_REGISTER_DEVICE_TOKEN_LISTENER_ON_FINISHED, 0, "", ""));
            }
            return new SemClientResultInfo();
        } catch (IllegalArgumentException e) {
            this.mConnectRegisterDeviceTokenLatch.countDown();
            LogMgr.log(2, "704 : catch IllegalArgumentException exceptionType=[32] message=[" + e.getMessage());
            return new SemClientResultInfo(32, e.getMessage());
        } catch (SemClientException e2) {
            this.mConnectRegisterDeviceTokenLatch.countDown();
            LogMgr.log(2, "705 : catch SemClientException exceptionType=[1] message=[" + e2.getMessage());
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), e2.getErrorCode());
        } catch (Exception e3) {
            this.mConnectRegisterDeviceTokenLatch.countDown();
            LogMgr.log(2, "706 : Exception occurred. e[" + e3 + "]");
            LogMgr.printStackTrace(8, e3);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e3), 900);
        } finally {
            LogMgr.performanceOut("API", "ISemClientImpl", "registerDeviceToken");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [973=7] */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00c7, code lost:
    
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00cc, code lost:
    
        if (r11 >= r10.length()) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00d6, code lost:
    
        if (r10.getPackageName(r11).equals(r21) == false) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00dc, code lost:
    
        if (r10.getNeedsNotifyUninstallation(r11) == false) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00e0, code lost:
    
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e1, code lost:
    
        r20.mDataManager.setSpAppIdForNotify(r0.get(0).mSpAppId);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00ec, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ee, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00f0, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f2, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00f3, code lost:
    
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00f4, code lost:
    
        r19 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f7, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00f8, code lost:
    
        r12 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f9, code lost:
    
        r19 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00fc, code lost:
    
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0100, code lost:
    
        r12 = false;
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0102, code lost:
    
        r11 = r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:117:0x019d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x014d A[Catch: all -> 0x024f, Exception -> 0x0252, SemClientException -> 0x0285, IllegalArgumentException -> 0x02ba, Merged into TryCatch #8 {all -> 0x024f, SemClientException -> 0x0285, IllegalArgumentException -> 0x02ba, Exception -> 0x0252, blocks: (B:4:0x0050, B:7:0x0058, B:9:0x005e, B:12:0x0072, B:14:0x007e, B:16:0x0085, B:18:0x0090, B:20:0x0096, B:22:0x00a1, B:24:0x00a7, B:71:0x014d, B:73:0x016c, B:76:0x017c, B:77:0x019c, B:78:0x019d, B:80:0x01a5, B:81:0x01b3, B:83:0x01bb, B:84:0x01c4, B:87:0x01ed, B:88:0x01f8, B:89:0x01f9, B:90:0x0204, B:33:0x00c8, B:35:0x00ce, B:37:0x00d8, B:39:0x00de, B:41:0x00e1, B:64:0x0111, B:69:0x0131, B:91:0x0205, B:94:0x021c, B:97:0x0233, B:98:0x0240, B:99:0x0241, B:100:0x024e, B:104:0x0253, B:108:0x0286, B:112:0x02bb), top: B:118:0x004e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public SemClientResultInfo notifyClientEvent(String str, String str2, ISemClientEventListener iSemClientEventListener) {
        boolean z;
        boolean z2;
        boolean z3;
        ArrayList<SpAppInfo> spAppInfoList;
        SemClientResultInfo semClientResultInfo = new SemClientResultInfo();
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT, "packageName[" + str + "] eventType[" + str2 + "]");
        LogMgr.log(5, "000 packageName=[" + str + "] eventType=[" + str2 + "]");
        try {
            if (str != null) {
                if (!str.isEmpty()) {
                    if (str2 == null || str2.isEmpty()) {
                        LogMgr.log(2, "701 eventType is null or empty.");
                        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_EVENT_TYPE);
                    }
                    checkConnected();
                    this.mSwsClientFacade.checkNotRunningTask();
                    if (!this.mSharedPrefsUtil.hasSpAppInfoListData()) {
                        LogMgr.log(8, "004 there are no any spAppInfoListData.");
                        LogMgr.log(5, "999");
                        iSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_NOTIFIED));
                        return semClientResultInfo;
                    }
                    if (!DateUtil.isTodayInJapan(this.mSharedPrefsUtil.readSaveDate())) {
                        LogMgr.log(8, "003 todayDate and syncUnixDate are different.");
                        LogMgr.log(5, "999");
                        iSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_NOTIFIED));
                        return semClientResultInfo;
                    }
                    try {
                        SpAppInfoJsonArray spAppInfoJsonArray = new SpAppInfoJsonArray(this.mSharedPrefsUtil.readSpAppInfoList());
                        spAppInfoList = spAppInfoJsonArray.getSpAppInfoList(str);
                    } catch (SemClientException e) {
                        e = e;
                        z = false;
                    } catch (JSONException e2) {
                        e = e2;
                        z = false;
                    }
                    if (spAppInfoList == null || spAppInfoList.size() == 0) {
                        z = false;
                        z3 = false;
                    } else {
                        String str3 = spAppInfoList.get(0).mSpAppId;
                        int i = 1;
                        while (true) {
                            try {
                                if (i >= spAppInfoList.size()) {
                                    break;
                                }
                                try {
                                    if (!spAppInfoList.get(i).mSpAppId.equals(str3)) {
                                        z3 = false;
                                        z = true;
                                        break;
                                    }
                                    i++;
                                } catch (SemClientException e3) {
                                    e = e3;
                                    z = false;
                                    z2 = false;
                                    LogMgr.log(2, "705SemClientException:" + e.getMessage());
                                    z3 = z2;
                                } catch (JSONException e4) {
                                    e = e4;
                                    z = false;
                                    z2 = false;
                                    LogMgr.log(2, "704JSONException:" + e.getMessage());
                                    z3 = z2;
                                }
                            } catch (SemClientException e5) {
                                e = e5;
                                z = false;
                                z2 = z;
                                LogMgr.log(2, "705SemClientException:" + e.getMessage());
                                z3 = z2;
                                if (!z3) {
                                }
                            } catch (JSONException e6) {
                                e = e6;
                                z = false;
                                z2 = z;
                                LogMgr.log(2, "704JSONException:" + e.getMessage());
                                z3 = z2;
                                if (!z3) {
                                }
                            }
                            z3 = z2;
                        }
                    }
                    if (!z3) {
                        LogMgr.log(8, "001 spAppInfoList don't include packageName which has valid needsNotifyUninstallation");
                        LogMgr.log(8, "002 packageName:" + str);
                        if (!z) {
                            LogMgr.log(5, "999");
                            iSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_NOTIFIED));
                            return semClientResultInfo;
                        }
                        LogMgr.log(2, "702 spAppInfoList has duplicated packageName:" + str);
                        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                    }
                    try {
                        String controlInfo = this.mSharedPrefsUtil.readControlInfo();
                        if (controlInfo == null) {
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                        this.mDataManager.setClientControlInfo(new ClientControlInfoJsonArray(controlInfo).getClientControlInfo());
                        String clientId = this.mSharedPrefsUtil.readClientId();
                        if (clientId == null) {
                            clientId = SwsParamCreator.createClientId();
                            this.mSharedPrefsUtil.writeClientId(clientId);
                        }
                        this.mDataManager.setClientId(clientId);
                        this.mDataManager.setPackageName(this.mContext.getPackageName());
                        this.mSwsClientFacade.start(new OnFinishListener(iSemClientEventListener), this.mDataManager);
                        this.mSwsClientFacade.notifyClientEvent(str2);
                        LogMgr.log(5, "999");
                        return semClientResultInfo;
                    } catch (JSONException unused) {
                        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                    }
                }
            }
            LogMgr.log(2, "700 packageName is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_PACKAGE_NAME);
        } catch (SemClientException e7) {
            LogMgr.log(2, "706 : catch SemClientException exceptionType=[1] message=[" + e7.getMessage());
            LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.NOTIFY_CLIENT_EVENT, e7.getErrorCode());
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e7), e7.getErrorCode());
        } catch (IllegalArgumentException e8) {
            LogMgr.log(2, "705 : catch IllegalArgumentException exceptionType=[32] message=[" + e8.getMessage());
            LogMgr.exLogException(SemClientExternalLogConst.SemcApi.NOTIFY_CLIENT_EVENT, e8);
            return new SemClientResultInfo(32, e8.getMessage());
        } catch (Exception e9) {
            LogMgr.log(2, "707 : Exception occurred. e[" + e9 + "]");
            LogMgr.printStackTrace(8, e9);
            LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.NOTIFY_CLIENT_EVENT, 900);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e9), 900);
        } finally {
            LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getClientConfiguration() throws InterruptedException, SemClientException {
        LogMgr.log(8, "000");
        this.mConnectGetClientConfigLatch = new CountDownLatch(1);
        InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
        internalSemClientEventListener.setCountDownLatch(this.mConnectGetClientConfigLatch);
        SemClientResultInfo semClientResultInfoDoGetClientConfiguration = doGetClientConfiguration(internalSemClientEventListener);
        if (semClientResultInfoDoGetClientConfiguration.mExceptionType != 0) {
            LogMgr.log(2, "700 Failed to exec getClientConfiguration.");
            throw new SemClientException(semClientResultInfoDoGetClientConfiguration.getErrorCode(), semClientResultInfoDoGetClientConfiguration.getMessage());
        }
        LogMgr.log(9, "001 ConnectGetClientConfigLatch.await()");
        this.mConnectGetClientConfigLatch.await();
        if (!internalSemClientEventListener.isSuccessToGetConfiguration()) {
            LogMgr.log(2, "701 Failed to exec getClientConfiguration.");
            throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
        }
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    private SemClientResultInfo doGetClientConfiguration(ISemClientEventListener iSemClientEventListener) {
        LogMgr.log(5, "000");
        SemClientResultInfo semClientResultInfo = new SemClientResultInfo();
        try {
            if (this.mSharedPrefsUtil.hasSaveDateData() && DateUtil.isTodayInJapan(this.mSharedPrefsUtil.readSaveDate())) {
                try {
                    iSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_CLIENT_CONFIGURATION_LISTENER_ON_FINISHED, 0, "", ""));
                } catch (Exception e) {
                    LogMgr.log(1, "801 Exception occurred. e=" + e);
                }
            } else {
                try {
                    String controlInfo = this.mSharedPrefsUtil.readControlInfo();
                    if (controlInfo != null) {
                        this.mDataManager.setClientControlInfo(new ClientControlInfoJsonArray(controlInfo).getClientControlInfo());
                    }
                } catch (JSONException unused) {
                }
                String clientId = this.mSharedPrefsUtil.readClientId();
                if (clientId == null) {
                    clientId = SwsParamCreator.createClientId();
                    this.mSharedPrefsUtil.writeClientId(clientId);
                }
                this.mDataManager.setClientId(clientId);
                this.mDataManager.setPackageName(this.mContext.getPackageName());
                this.mSwsClientFacade.start(new OnFinishListener(iSemClientEventListener), this.mDataManager);
                this.mSwsClientFacade.getClientConfiguration();
            }
            return semClientResultInfo;
        } catch (SemClientException e2) {
            this.mConnectGetClientConfigLatch.countDown();
            LogMgr.log(2, "700 : Exception occurred. e[" + e2 + "]");
            LogMgr.printStackTrace(8, e2);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), e2.getErrorCode());
        } finally {
            LogMgr.log(5, "999");
        }
    }

    private synchronized SemClientResultInfo connect(ISemClientEventListener iSemClientEventListener, boolean z, boolean z2, String str, boolean z3, int i, boolean z4) {
        SemClientResultInfo semClientResultInfo;
        boolean z5;
        String deviceIdentificationData;
        StringBuilder sb = new StringBuilder("listener[");
        sb.append(iSemClientEventListener != null);
        sb.append("] isCheckSystemUser[");
        sb.append(z);
        sb.append("]  isCalledFromInternal[");
        sb.append(z2);
        sb.append("packageName[");
        sb.append(str);
        sb.append("] existMode=[");
        sb.append(z3);
        sb.append("] mode=[");
        sb.append(i);
        sb.append("]");
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_CONNECT, sb.toString());
        LogMgr.log(5, "000 listener[" + iSemClientEventListener + "] isCheckSystemUser[" + z + "] isCalledFromInternal[" + z2 + "]] packageName[" + str + "] existMode=[" + z3 + "] mode=[" + i + "]");
        try {
            try {
                try {
                    try {
                        this.mIsConnecting.compareAndSet(false, true);
                        if (this.mConnectedApp == null) {
                            LogMgr.log(9, "001 new SemClientAppInfo.");
                            this.mConnectedApp = new SemClientAppInfo();
                            z5 = true;
                        } else {
                            z5 = false;
                        }
                        semClientResultInfo = new SemClientResultInfo();
                        LogMgr.log(9, "002 Arguments check.");
                    } catch (SemClientException e) {
                        LogMgr.log(2, "707 Catch SemClientException errorCode[" + e.getErrorCode() + "]");
                        LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.CONNECT, e);
                        this.mIsConnecting.compareAndSet(true, false);
                        semClientResultInfo = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), e.getErrorCode());
                    }
                } catch (IllegalArgumentException e2) {
                    LogMgr.log(2, "709 Catch IllegalArgumentException errorCode[" + e2.getClass().getSimpleName() + "]");
                    LogMgr.exLogException(SemClientExternalLogConst.SemcApi.CONNECT, e2);
                    this.mIsConnecting.compareAndSet(true, false);
                    semClientResultInfo = new SemClientResultInfo(32, e2.getMessage());
                }
            } catch (IllegalStateException e3) {
                LogMgr.log(2, "708 Catch IllegalSateException errorCode[" + e3.getClass().getSimpleName() + "]");
                LogMgr.exLogException(SemClientExternalLogConst.SemcApi.CONNECT, e3);
                this.mIsConnecting.compareAndSet(true, false);
                semClientResultInfo = new SemClientResultInfo(33, e3.getMessage());
            }
        } catch (Exception e4) {
            LogMgr.log(1, "802 Catch Exception e[" + e4.getClass().getSimpleName() + "]");
            LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.CONNECT, 900);
            this.mIsConnecting.compareAndSet(true, false);
            semClientResultInfo = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 900);
        }
        if (iSemClientEventListener == null) {
            LogMgr.log(2, "700 Parameter Error listener is null");
            if (z5) {
                LogMgr.log(9, "003 delete SemClientAppInfo.");
                this.mConnectedApp = null;
            }
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_LISTENER);
        }
        if (z3) {
            if (i == 0) {
                LogMgr.log(2, "701 mode was 0");
                if (z5) {
                    LogMgr.log(9, "004 delete SemClientAppInfo.");
                    this.mConnectedApp = null;
                }
                throw new IllegalArgumentException(SemClientConst.EXC_INVALID_MODE);
            }
            if ((i & (-5)) != 0) {
                LogMgr.log(2, "702 Invalid bit was on");
                if (z5) {
                    LogMgr.log(9, "005 delete SemClientAppInfo.");
                    this.mConnectedApp = null;
                }
                throw new IllegalArgumentException(SemClientConst.EXC_INVALID_MODE);
            }
            if ((i & 4) == 4) {
                LogMgr.log(8, "006 MODE_FLAG_FOR_GLOBAL_DEVICE is ON.");
                String strFindConfigFile = findConfigFile();
                String packageName = this.mContext.getPackageName();
                try {
                    deviceIdentificationData = AccessConfig.getDeviceIdentificationData(this.mContext);
                } catch (SemClientException unused) {
                    if ("com.felicanetworks.semcapp".equals(packageName)) {
                        if (z4) {
                            throw new SemClientException(201, ObfuscatedMsgUtil.executionPoint());
                        }
                        throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
                    }
                    deviceIdentificationData = null;
                }
                if (strFindConfigFile != null && deviceIdentificationData == null) {
                    deviceIdentificationData = "1";
                }
                if ("com.felicanetworks.mfm.main".equals(packageName)) {
                    if (strFindConfigFile == null || !"1".equals(deviceIdentificationData)) {
                        this.mIsConnecting.compareAndSet(true, false);
                        return new SemClientResultInfo(2);
                    }
                } else if ("com.felicanetworks.semcapp".equals(packageName)) {
                    if (strFindConfigFile != null && "1".equals(deviceIdentificationData)) {
                        throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
                    }
                } else {
                    LogMgr.log(1, "804 SEMC inner app package name is " + packageName + VERSION_SEPARATOR);
                    throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                }
            }
        }
        if (Build.VERSION.SDK_INT < 28) {
            LogMgr.log(1, "803 Not supported device.");
            throw new SemClientException(201, ObfuscatedMsgUtil.executionPoint());
        }
        checkCallerPackageName(str);
        if (!z && z2) {
            LogMgr.log(8, "007 Skip SystemUser check.");
        } else {
            LogMgr.log(8, "008 Do SystemUser check.");
            UserManager userManager = (UserManager) this.mContext.getSystemService("user");
            if (userManager == null) {
                LogMgr.log(1, "800 Unable to get UserManager Service.");
                if (z5) {
                    LogMgr.log(9, "009 delete SemClientAppInfo.");
                    this.mConnectedApp = null;
                }
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (!userManager.isSystemUser()) {
                LogMgr.log(2, "702 Not SystemUser Error.");
                if (z5) {
                    LogMgr.log(9, "010 delete SemClientAppInfo.");
                    this.mConnectedApp = null;
                }
                throw new SemClientException(503, ObfuscatedMsgUtil.executionPoint());
            }
        }
        checkConnecting();
        if (this.mConnectedApp.isAppInfoExist()) {
            LogMgr.log(9, "011 Connected.");
            LogMgr.log(9, "012 Check connected by calling app.");
            if (this.mConnectedApp.getPid() == Binder.getCallingPid() && this.mConnectedApp.getUid() == Binder.getCallingUid()) {
                LogMgr.log(2, "704 Already connected Error.");
                throw new IllegalStateException(SemClientConst.EXC_ILLEGAL_STATE_ALREADY_CONNECTED);
            }
            LogMgr.log(9, "013 Check connected by background service.");
            if (this.mConnectedApp.getIsCalledFromInternal()) {
                LogMgr.log(2, "705 SemClient background service connected Error.");
                throw new SemClientException(103, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(2, "706 An other app connected Error.");
            throw new SemClientException(101, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(9, "014 Not Connected.");
        IBinder iBinderAsBinder = iSemClientEventListener.asBinder();
        if (iBinderAsBinder == null) {
            LogMgr.log(1, "801 binder is null");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        registerBinder(iBinderAsBinder);
        this.mConnectedApp.setAppInfoExist();
        this.mConnectedApp.setPid(Binder.getCallingPid());
        this.mConnectedApp.setUid(Binder.getCallingUid());
        this.mConnectedApp.setCallerPackageName(str);
        this.mConnectedApp.setIsCalledFromInternal(z2);
        this.mConnectWorker = new ConnectThread(z2, i, iSemClientEventListener);
        this.mIsConnectInterrupted = new AtomicBoolean(false);
        this.mConnectWorker.start();
        LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_CONNECT);
        return semClientResultInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String findConfigFile() {
        String str;
        LogMgr.log(7, "000");
        String[] strArr = FlavorConst.COMMON_CONFIG_FILE_PATH;
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                str = null;
                break;
            }
            str = strArr[i] + CommonConfig.FILE_NAME;
            try {
                if (new File(str).exists()) {
                    LogMgr.log(9, "001 tempPath = " + str);
                    break;
                }
                continue;
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            i++;
        }
        LogMgr.log(7, "999");
        return str;
    }

    private SemClientResultInfo getSeid() {
        LogMgr.log(8, "000");
        try {
            checkConnected();
            String seid = this.mDataManager.getSeid();
            if (seid == null || seid.isEmpty()) {
                LogMgr.log(2, "700 : Failed to get SEID");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(8, "999");
            return new SemClientResultInfo(seid);
        } catch (SemClientException e) {
            LogMgr.log(2, "702 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
            LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.GET_SEID, e);
            return new SemClientResultInfo(1, e.getMessage(), e.getErrorCode());
        } catch (IllegalStateException e2) {
            LogMgr.log(2, "701 : catch IllegalStateException exceptionType=[33] message=[" + e2.getMessage());
            LogMgr.exLogException(SemClientExternalLogConst.SemcApi.GET_SEID, e2);
            return new SemClientResultInfo(33, e2.getMessage());
        }
    }

    private SemClientResultInfo getSeReaderName() {
        LogMgr.log(8, "000");
        try {
            checkConnected();
            String seReaderName = this.mDataManager.getSeReaderName();
            if (seReaderName == null || seReaderName.isEmpty()) {
                LogMgr.log(2, "700 : Failed to get SeReaderName");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(8, "999");
            return new SemClientResultInfo(seReaderName);
        } catch (SemClientException e) {
            LogMgr.log(2, "702 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
            LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.GET_SE_READER_NAME, e);
            return new SemClientResultInfo(1, e.getMessage(), e.getErrorCode());
        } catch (IllegalStateException e2) {
            LogMgr.log(2, "701 : catch IllegalStateException exceptionType=[33] message=[" + e2.getMessage());
            LogMgr.exLogException(SemClientExternalLogConst.SemcApi.GET_SE_READER_NAME, e2);
            return new SemClientResultInfo(33, e2.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startTsmSequence(String str) throws InterruptedException, SemClientException {
        LogMgr.log(8, "000");
        this.mStartTsmSequenceLatch = new CountDownLatch(1);
        InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
        internalSemClientEventListener.setCountDownLatch(this.mStartTsmSequenceLatch);
        SemClientResultInfo semClientResultInfoStartTsmSequence = startTsmSequence(str, internalSemClientEventListener, true);
        if (semClientResultInfoStartTsmSequence.mExceptionType != 0) {
            LogMgr.log(2, "700 Failed to exec startTsmSequence. message:" + semClientResultInfoStartTsmSequence.getMessage() + " code:" + semClientResultInfoStartTsmSequence.getErrorCode());
            throw new SemClientException(900, semClientResultInfoStartTsmSequence.getMessage());
        }
        LogMgr.log(9, "001 StartTsmSequenceLatch.await()");
        this.mStartTsmSequenceLatch.await();
        if (!internalSemClientEventListener.isSuccessToStartTsmSequence()) {
            LogMgr.log(2, "701 Failed to exec startTsmSequence. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
            throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
        }
        LogMgr.log(8, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [1618=6] */
    private SemClientResultInfo startTsmSequence(String str, ISemClientEventListener iSemClientEventListener, boolean z) {
        StringBuilder sb = new StringBuilder("listener[");
        sb.append(iSemClientEventListener != null);
        sb.append("] linkageData[");
        sb.append(str);
        sb.append("] skipCheckConnected[");
        sb.append(z);
        sb.append("]");
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE, sb.toString());
        LogMgr.log(8, "000 linkageData=[" + str + "]");
        try {
            try {
                try {
                    if (str == null) {
                        LogMgr.log(2, "700 linkageData is null. ");
                        throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
                    }
                    if (str.isEmpty()) {
                        LogMgr.log(2, "701 linkageData is empty. ");
                        throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
                    }
                    if (iSemClientEventListener == null) {
                        LogMgr.log(2, "702 Param Error. listener is null.");
                        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_LISTENER);
                    }
                    if (!z) {
                        checkConnected();
                    }
                    this.mSwsClientFacade.checkNotRunningTask();
                    String payloadWithVerify = new JwsObject(str).getPayloadWithVerify(SettingInfo.getServerPublicKeysForLinkageData());
                    if (payloadWithVerify != null && !payloadWithVerify.isEmpty()) {
                        StartTsmSequenceThread startTsmSequenceThread = new StartTsmSequenceThread(payloadWithVerify, iSemClientEventListener, ("com.felicanetworks.mfm.main".equals(this.mConnectedApp.getCallerPackageName()) || "com.felicanetworks.semcapp".equals(this.mConnectedApp.getCallerPackageName())) ? false : true);
                        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                        this.mIsRunningStartTsmSequence = atomicBoolean;
                        if (!z) {
                            atomicBoolean.compareAndSet(false, true);
                        }
                        startTsmSequenceThread.start();
                        LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
                        LogMgr.log(8, "999");
                        return new SemClientResultInfo();
                    }
                    LogMgr.log(2, "703JWS Object payload data is null or empty. jsonPayload[" + payloadWithVerify + "]");
                    LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, 102);
                    SemClientResultInfo semClientResultInfo = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 102);
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
                    return semClientResultInfo;
                } catch (IllegalArgumentException e) {
                    LogMgr.log(2, "704 : catch IllegalArgumentException exceptionType=[32] message=[" + e.getMessage());
                    LogMgr.exLogException(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, e);
                    SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(32, e.getMessage());
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
                    return semClientResultInfo2;
                } catch (IllegalStateException e2) {
                    LogMgr.log(2, "705 : catch IllegalStateException exceptionType=[33] message=[" + e2.getMessage());
                    LogMgr.exLogException(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, e2);
                    SemClientResultInfo semClientResultInfo3 = new SemClientResultInfo(33, e2.getMessage());
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
                    return semClientResultInfo3;
                }
            } catch (JwsException e3) {
                LogMgr.log(2, "707Failed to read JWS Object. type[" + e3.getType() + "] message[" + e3.getMessage() + "]");
                LogMgr.printStackTrace(8, e3);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, 102);
                SemClientResultInfo semClientResultInfo4 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e3), 102);
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
                return semClientResultInfo4;
            } catch (Exception e4) {
                LogMgr.log(2, "708Exception occurred. e[" + e4 + "]");
                LogMgr.printStackTrace(9, e4);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, 900);
                SemClientResultInfo semClientResultInfo5 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e4), 900);
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
                return semClientResultInfo5;
            }
        } catch (Throwable th) {
            LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
            throw th;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private SemClientResultInfo getContactlessInterfaceStatus(String str) {
        LogMgr.log(9, "000 aid:" + str);
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 Param Error. aid is null.");
            LogMgr.exLogException(SemClientExternalLogConst.SemcApi.GET_CONTACTLESS_INTERFACE_STATUS, "IllegalArgumentException");
            return new SemClientResultInfo(32, SemClientConst.EXC_INVALID_AID);
        }
        SemClientResultInfo semClientResultInfoPreCheckToAccessContactlessInterface = preCheckToAccessContactlessInterface(str, SemClientExternalLogConst.SemcApi.GET_CONTACTLESS_INTERFACE_STATUS);
        if (semClientResultInfoPreCheckToAccessContactlessInterface.getExceptionType() != 0) {
            LogMgr.log(2, "701 Cannot access Contactless Interface. Exception type:" + semClientResultInfoPreCheckToAccessContactlessInterface.getExceptionType() + " message:" + semClientResultInfoPreCheckToAccessContactlessInterface.getMessage() + " code:" + semClientResultInfoPreCheckToAccessContactlessInterface.getErrorCode());
            return semClientResultInfoPreCheckToAccessContactlessInterface;
        }
        GpController gpController = null;
        try {
            try {
                SemChipHolder semChipHolder = this.mSemChipHolder;
                if (semChipHolder == null) {
                    LogMgr.log(1, "800 mTsmChipHolder is null.");
                    LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.GET_CONTACTLESS_INTERFACE_STATUS, 900);
                    return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 900);
                }
                GpController gpController2 = semChipHolder.getGpController();
                int appletStatus = new CrsManager(gpController2).getAppletStatus(StringUtil.hexToByteArray(str));
                if (gpController2 != null) {
                    gpController2.closeChannel();
                }
                LogMgr.log(9, "999 contactlessInterfaceStatus:" + appletStatus);
                return new SemClientResultInfo(appletStatus);
            } catch (GpException e) {
                LogMgr.log(2, "703 GpException occurred. e[" + e + "]");
                LogMgr.printStackTrace(8, e);
                int iConvertException = ErrorCodeConverter.convertException(1, e.getType());
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.GET_CONTACTLESS_INTERFACE_STATUS, iConvertException);
                SemClientResultInfo semClientResultInfo = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), iConvertException);
                if (0 != 0) {
                    gpController.closeChannel();
                }
                return semClientResultInfo;
            } catch (Exception e2) {
                LogMgr.log(2, "701 Exception occurred. message=" + e2.getMessage());
                LogMgr.printStackTrace(8, e2);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.GET_CONTACTLESS_INTERFACE_STATUS, 900);
                SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
                if (0 != 0) {
                    gpController.closeChannel();
                }
                return semClientResultInfo2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                gpController.closeChannel();
            }
            throw th;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private SemClientResultInfo activateContactlessInterface(String str) {
        LogMgr.log(9, "000");
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 Param Error. aid is null.");
            LogMgr.exLogException(SemClientExternalLogConst.SemcApi.ACTIVATE_CONTACTLESS_INTERFACE, "IllegalArgumentException");
            return new SemClientResultInfo(32, SemClientConst.EXC_INVALID_AID);
        }
        SemClientResultInfo semClientResultInfoPreCheckToAccessContactlessInterface = preCheckToAccessContactlessInterface(str, SemClientExternalLogConst.SemcApi.ACTIVATE_CONTACTLESS_INTERFACE);
        if (semClientResultInfoPreCheckToAccessContactlessInterface.getExceptionType() != 0) {
            LogMgr.log(2, "701 Cannot access Contactless Interface. Exception type:" + semClientResultInfoPreCheckToAccessContactlessInterface.getExceptionType() + " message:" + semClientResultInfoPreCheckToAccessContactlessInterface.getMessage() + " code:" + semClientResultInfoPreCheckToAccessContactlessInterface.getErrorCode());
            return semClientResultInfoPreCheckToAccessContactlessInterface;
        }
        GpController gpController = null;
        try {
            try {
                SemChipHolder semChipHolder = this.mSemChipHolder;
                if (semChipHolder == null) {
                    LogMgr.log(1, "800 mTsmChipHolder is null.");
                    LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.ACTIVATE_CONTACTLESS_INTERFACE, 900);
                    return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 900);
                }
                GpController gpController2 = semChipHolder.getGpController();
                new CrsManager(gpController2).activateApplet(StringUtil.hexToByteArray(str));
                if (gpController2 != null) {
                    gpController2.closeChannel();
                }
                LogMgr.log(9, "999");
                return new SemClientResultInfo();
            } catch (GpException e) {
                LogMgr.log(2, "702 GpException occurred. e[" + e + "]");
                LogMgr.printStackTrace(8, e);
                int iConvertException = ErrorCodeConverter.convertException(1, e.getType());
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.ACTIVATE_CONTACTLESS_INTERFACE, iConvertException);
                SemClientResultInfo semClientResultInfo = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), iConvertException);
                if (0 != 0) {
                    gpController.closeChannel();
                }
                return semClientResultInfo;
            } catch (Exception e2) {
                LogMgr.log(2, "704 Exception occurred. message=" + e2.getMessage());
                LogMgr.printStackTrace(8, e2);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.ACTIVATE_CONTACTLESS_INTERFACE, 900);
                SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
                if (0 != 0) {
                    gpController.closeChannel();
                }
                return semClientResultInfo2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                gpController.closeChannel();
            }
            throw th;
        }
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private SemClientResultInfo deactivateContactlessInterface(String str) {
        LogMgr.log(9, "000");
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 Param Error. aid is null.");
            LogMgr.exLogException(SemClientExternalLogConst.SemcApi.DEACTIVATE_CONTACTLESS_INTERFACE, "IllegalArgumentException");
            return new SemClientResultInfo(32, SemClientConst.EXC_INVALID_AID);
        }
        SemClientResultInfo semClientResultInfoPreCheckToAccessContactlessInterface = preCheckToAccessContactlessInterface(str, SemClientExternalLogConst.SemcApi.DEACTIVATE_CONTACTLESS_INTERFACE);
        if (semClientResultInfoPreCheckToAccessContactlessInterface.getExceptionType() != 0) {
            LogMgr.log(2, "701 Cannot access Contactless Interface. Exception type:" + semClientResultInfoPreCheckToAccessContactlessInterface.getExceptionType() + " message:" + semClientResultInfoPreCheckToAccessContactlessInterface.getMessage() + " code:" + semClientResultInfoPreCheckToAccessContactlessInterface.getErrorCode());
            return semClientResultInfoPreCheckToAccessContactlessInterface;
        }
        GpController gpController = null;
        try {
            try {
                SemChipHolder semChipHolder = this.mSemChipHolder;
                if (semChipHolder == null) {
                    LogMgr.log(1, "800 mTsmChipHolder is null.");
                    LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.DEACTIVATE_CONTACTLESS_INTERFACE, 900);
                    return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 900);
                }
                GpController gpController2 = semChipHolder.getGpController();
                new CrsManager(gpController2).deactivateApplet(StringUtil.hexToByteArray(str));
                if (gpController2 != null) {
                    gpController2.closeChannel();
                }
                LogMgr.log(9, "999");
                return new SemClientResultInfo();
            } catch (GpException e) {
                LogMgr.log(2, "702 GpException occurred. e[" + e + "]");
                LogMgr.printStackTrace(8, e);
                int iConvertException = ErrorCodeConverter.convertException(1, e.getType());
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.DEACTIVATE_CONTACTLESS_INTERFACE, iConvertException);
                SemClientResultInfo semClientResultInfo = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), iConvertException);
                if (0 != 0) {
                    gpController.closeChannel();
                }
                return semClientResultInfo;
            } catch (Exception e2) {
                LogMgr.log(2, "704 Exception occurred. message=" + e2.getMessage());
                LogMgr.printStackTrace(8, e2);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.DEACTIVATE_CONTACTLESS_INTERFACE, 900);
                SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
                if (0 != 0) {
                    gpController.closeChannel();
                }
                return semClientResultInfo2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                gpController.closeChannel();
            }
            throw th;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [1932=4] */
    public SemClientResultInfo installApplet(String str, String str2, String str3, String str4, String[] strArr, String str5, String str6, ISemClientEventListener iSemClientEventListener) throws Throwable {
        String str7 = "701 : catch IllegalArgumentException exceptionType=[32] message=[";
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_INSTALL_APPLET, "serviceId[" + str + "] spAppletId[" + str2 + "] spAppletVersion[" + str3 + "] spSdKeyVersion[" + str4 + "] accessAllowedSpAppIdList[" + Arrays.toString(strArr) + "] operationType[" + str5 + "] operationId[" + str6 + "] listener[" + iSemClientEventListener + "]");
        String str8 = SemClientApiInfo.METHOD_NAME_INSTALL_APPLET;
        LogMgr.log(5, "000 serviceId=[" + str + "] spAppletId=[" + str2 + "] spAppletVersion=[" + str3 + "] spSdKeyVersion=[" + str4 + "] accessAllowedSpAppIdList=[" + Arrays.toString(strArr) + "] operationType=[" + str5 + "] operationId=[" + str6 + "] listener=[" + iSemClientEventListener + "]");
        try {
            try {
                checkParameterServiceId(str);
                checkParameterSpAppletId(str2);
                checkParameterSpAppletVersion(str3);
                if (str4 != null) {
                    checkParameterSpSdKeyVersion(str4);
                }
                checkParameterAccessAllowedSpAppIdList(strArr);
                checkParameterOperationType(str5);
                checkParameterOperationId(str6);
                try {
                    if (iSemClientEventListener == null) {
                        LogMgr.log(2, "700 listener is null.");
                        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_LISTENER);
                    }
                    checkConnected();
                    this.mSwsClientFacade.checkNotRunningTask();
                    InstallAppletThread installAppletThread = new InstallAppletThread(str, str2, str3, str4, strArr, str5, str6, iSemClientEventListener);
                    this.mInstallAppletThread = installAppletThread;
                    installAppletThread.start();
                    LogMgr.performanceOut("API", "ISemClientImpl", str8);
                    LogMgr.log(5, "999");
                    return new SemClientResultInfo();
                } catch (IllegalArgumentException e) {
                    e = e;
                } catch (IllegalStateException e2) {
                    e = e2;
                    LogMgr.log(2, str8 + e.getMessage());
                    LogMgr.exLogException(SemClientExternalLogConst.SemcApi.INSTALL_APPLET, e);
                    SemClientResultInfo semClientResultInfo = new SemClientResultInfo(33, e.getMessage());
                    LogMgr.performanceOut("API", "ISemClientImpl", str7);
                    return semClientResultInfo;
                } catch (Exception e3) {
                    e = e3;
                    LogMgr.log(2, "703Exception occurred. e[" + e + "]");
                    LogMgr.printStackTrace(9, e);
                    LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.INSTALL_APPLET, 900);
                    SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), 900);
                    LogMgr.performanceOut("API", "ISemClientImpl", str7);
                    return semClientResultInfo2;
                }
            } catch (Throwable th) {
                th = th;
                LogMgr.performanceOut("API", "ISemClientImpl", str7);
                throw th;
            }
        } catch (IllegalArgumentException e4) {
            e = e4;
            str7 = str8;
        } catch (IllegalStateException e5) {
            e = e5;
            str7 = str8;
            str8 = "702 : catch IllegalStateException exceptionType=[33] message=[";
        } catch (Exception e6) {
            e = e6;
            str7 = str8;
        } catch (Throwable th2) {
            th = th2;
            str7 = str8;
            LogMgr.performanceOut("API", "ISemClientImpl", str7);
            throw th;
        }
        LogMgr.log(2, "701 : catch IllegalArgumentException exceptionType=[32] message=[" + e.getMessage());
        LogMgr.exLogException(SemClientExternalLogConst.SemcApi.INSTALL_APPLET, e);
        SemClientResultInfo semClientResultInfo3 = new SemClientResultInfo(32, e.getMessage());
        LogMgr.performanceOut("API", "ISemClientImpl", str7);
        return semClientResultInfo3;
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [2018=4] */
    public SemClientResultInfo upgradeApplet(String str, String str2, String str3, String str4, String str5, String str6, ISemClientEventListener iSemClientEventListener) {
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET, "serviceId[" + str + "] spAppletId[" + str2 + "] oldSpAppletVersion[" + str3 + "] newSpAppletVersion[" + str4 + "] operationType[" + str5 + "] operationId[" + str6 + "] listener[" + iSemClientEventListener + "]");
        LogMgr.log(5, "000 serviceId=[" + str + "] spAppletId=[" + str2 + "] oldSpAppletVersion[" + str3 + "] newSpAppletVersion[" + str4 + "] operationType=[" + str5 + "] operationId=[" + str6 + "] listener=[" + iSemClientEventListener + "]");
        try {
            try {
                try {
                    checkParameterServiceId(str);
                    checkParameterSpAppletId(str2);
                    checkParameterOldSpAppletVersion(str3);
                    checkParameterNewSpAppletVersion(str4);
                    checkParameterOperationType(str5);
                    checkParameterOperationId(str6);
                    if (iSemClientEventListener == null) {
                        LogMgr.log(2, "700 listener is null.");
                        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_LISTENER);
                    }
                    checkConnected();
                    this.mSwsClientFacade.checkNotRunningTask();
                    UpgradeAppletThread upgradeAppletThread = new UpgradeAppletThread(str, str2, str3, str4, str5, str6, iSemClientEventListener);
                    this.mUpgradeAppletThread = upgradeAppletThread;
                    upgradeAppletThread.start();
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
                    LogMgr.log(5, "999");
                    return new SemClientResultInfo();
                } catch (IllegalStateException e) {
                    LogMgr.log(2, "702 : catch IllegalStateException exceptionType=[33] message=[" + e.getMessage());
                    LogMgr.exLogException(SemClientExternalLogConst.SemcApi.UPGRADE_APPLET, e);
                    SemClientResultInfo semClientResultInfo = new SemClientResultInfo(33, e.getMessage());
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
                    return semClientResultInfo;
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(2, "701 : catch IllegalArgumentException exceptionType=[32] message=[" + e2.getMessage());
                LogMgr.exLogException(SemClientExternalLogConst.SemcApi.UPGRADE_APPLET, e2);
                SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(32, e2.getMessage());
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
                return semClientResultInfo2;
            } catch (Exception e3) {
                LogMgr.log(2, "703Exception occurred. e[" + e3 + "]");
                LogMgr.printStackTrace(9, e3);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.UPGRADE_APPLET, 900);
                SemClientResultInfo semClientResultInfo3 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e3), 900);
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
                return semClientResultInfo3;
            }
        } catch (Throwable th) {
            LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
            throw th;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [2102=4] */
    public SemClientResultInfo deleteApplet(String str, String str2, String str3, String str4, String str5, ISemClientEventListener iSemClientEventListener) {
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET, "serviceId[" + str + "] spAppletId[" + str2 + "] spAppletVersion[" + str3 + "] operationType[" + str4 + "] operationId[" + str5 + "] listener[" + iSemClientEventListener + "]");
        LogMgr.log(5, "000 serviceId=[" + str + "] spAppletId=[" + str2 + "] spAppletVersion=[" + str3 + "] operationType=[" + str4 + "] operationId=[" + str5 + "] listener=[" + iSemClientEventListener + "]");
        try {
            try {
                try {
                    checkParameterServiceId(str);
                    checkParameterSpAppletId(str2);
                    if (str3 != null) {
                        checkParameterSpAppletVersion(str3);
                    }
                    checkParameterOperationType(str4);
                    checkParameterOperationId(str5);
                    if (iSemClientEventListener == null) {
                        LogMgr.log(2, "700 listener is null.");
                        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_LISTENER);
                    }
                    checkConnected();
                    this.mSwsClientFacade.checkNotRunningTask();
                    DeleteAppletThread deleteAppletThread = new DeleteAppletThread(str, str2, str3, str4, str5, iSemClientEventListener);
                    this.mDeleteAppletThread = deleteAppletThread;
                    deleteAppletThread.start();
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
                    LogMgr.log(5, "999");
                    return new SemClientResultInfo();
                } catch (IllegalStateException e) {
                    LogMgr.log(2, "702 : catch IllegalStateException exceptionType=[33] message=[" + e.getMessage());
                    LogMgr.exLogException(SemClientExternalLogConst.SemcApi.DELETE_APPLET, e);
                    SemClientResultInfo semClientResultInfo = new SemClientResultInfo(33, e.getMessage());
                    LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
                    return semClientResultInfo;
                }
            } catch (IllegalArgumentException e2) {
                LogMgr.log(2, "701 : catch IllegalArgumentException exceptionType=[32] message=[" + e2.getMessage());
                LogMgr.exLogException(SemClientExternalLogConst.SemcApi.DELETE_APPLET, e2);
                SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(32, e2.getMessage());
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
                return semClientResultInfo2;
            } catch (Exception e3) {
                LogMgr.log(2, "703Exception occurred. e[" + e3 + "]");
                LogMgr.printStackTrace(9, e3);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.DELETE_APPLET, 900);
                SemClientResultInfo semClientResultInfo3 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e3), 900);
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
                return semClientResultInfo3;
            }
        } catch (Throwable th) {
            LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
            throw th;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [2179=4] */
    public SemClientResultInfo getAppletStatus(String str, String str2, String str3, String str4, ISemClientEventListener iSemClientEventListener) {
        LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS, "serviceId[" + str + "] spAppletId[" + str2 + "] operationType[" + str3 + "] operationId[" + str4 + "] listener[" + iSemClientEventListener + "]");
        LogMgr.log(5, "000 serviceId=[" + str + "] spAppletId=[" + str2 + "] operationType=[" + str3 + "] operationId=[" + str4 + "] listener=[" + iSemClientEventListener + "]");
        try {
            try {
                checkParameterServiceId(str);
                checkParameterSpAppletId(str2);
                checkParameterOperationType(str3);
                checkParameterOperationId(str4);
                if (iSemClientEventListener == null) {
                    LogMgr.log(2, "700 listener is null.");
                    throw new IllegalArgumentException(SemClientConst.EXC_INVALID_LISTENER);
                }
                checkConnected();
                this.mSwsClientFacade.checkNotRunningTask();
                GetAppletStatusThread getAppletStatusThread = new GetAppletStatusThread(str, str2, str3, str4, iSemClientEventListener);
                this.mGetAppletStatusThread = getAppletStatusThread;
                getAppletStatusThread.start();
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
                LogMgr.log(5, "999");
                return new SemClientResultInfo();
            } catch (IllegalArgumentException e) {
                LogMgr.log(2, "701 : catch IllegalArgumentException exceptionType=[32] message=[" + e.getMessage());
                LogMgr.exLogException(SemClientExternalLogConst.SemcApi.GET_APPLET_STATUS, e);
                SemClientResultInfo semClientResultInfo = new SemClientResultInfo(32, e.getMessage());
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
                return semClientResultInfo;
            } catch (IllegalStateException e2) {
                LogMgr.log(2, "702 : catch IllegalStateException exceptionType=[33] message=[" + e2.getMessage());
                LogMgr.exLogException(SemClientExternalLogConst.SemcApi.GET_APPLET_STATUS, e2);
                SemClientResultInfo semClientResultInfo2 = new SemClientResultInfo(33, e2.getMessage());
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
                return semClientResultInfo2;
            } catch (Exception e3) {
                LogMgr.log(2, "703Exception occurred. e[" + e3 + "]");
                LogMgr.printStackTrace(9, e3);
                LogMgr.exLogSemClientException(SemClientExternalLogConst.SemcApi.GET_APPLET_STATUS, 900);
                SemClientResultInfo semClientResultInfo3 = new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e3), 900);
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
                return semClientResultInfo3;
            }
        } catch (Throwable th) {
            LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
            throw th;
        }
    }

    private void checkParameterServiceId(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 serviceId is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        if (!str.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        if (str.length() == 8) {
            return;
        }
        LogMgr.log(2, "702 serviceId length is invalid.");
        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
    }

    private void checkParameterSpAppletId(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 spAppletId is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        if (!str.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 spAppletId involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        if (str.length() == 8) {
            return;
        }
        LogMgr.log(2, "702 spAppletId length is invalid.");
        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
    }

    private void checkParameterSpAppletVersion(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 spAppletVersion is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        try {
            hexToByteArray(str);
            if (str.length() == 4) {
                return;
            }
            LogMgr.log(2, "702 spAppletVersion length is invalid.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "701 spAppletVersion involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
    }

    private void checkParameterOldSpAppletVersion(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 oldSpAppletVersion is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        try {
            hexToByteArray(str);
            if (str.length() == 4) {
                return;
            }
            LogMgr.log(2, "702 oldSpAppletVersion length is invalid.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "701 oldSpAppletVersion involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
    }

    private void checkParameterNewSpAppletVersion(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 newSpAppletVersion is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        try {
            hexToByteArray(str);
            if (str.length() == 4) {
                return;
            }
            LogMgr.log(2, "702 newSpAppletVersion length is invalid.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "701 newSpAppletVersion involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
    }

    private void checkParameterSpSdKeyVersion(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 spSdKeyVersion is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        try {
            hexToByteArray(str);
            if (str.length() == 2) {
                return;
            }
            LogMgr.log(2, "702 spSdKeyVersion length is invalid.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "701 spSdKeyVersion involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
    }

    private void checkParameterAccessAllowedSpAppIdList(String[] strArr) throws IllegalArgumentException {
        if (strArr == null) {
            LogMgr.log(2, "700 accessAllowedSpAppIdList is null.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        for (String str : strArr) {
            if (str == null || str.isEmpty()) {
                LogMgr.log(2, "701 accessAllowedSpAppId is null or empty.");
                throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
            }
            if (!str.matches("[0-9a-zA-Z]*")) {
                LogMgr.log(2, "702 accessAllowedSpAppId involves invalid character.");
                throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
            }
            if (str.length() != 8) {
                LogMgr.log(2, "703 accessAllowedSpAppId length is invalid.");
                throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
            }
        }
        if (strArr.length < 1 || strArr.length > 10) {
            LogMgr.log(2, "704 accessAllowedSpAppIdList length is invalid.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
    }

    private void checkParameterOperationType(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 operationType is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        try {
            hexToByteArray(str);
            if (str.length() == 8) {
                return;
            }
            LogMgr.log(2, "702 operationType length is invalid.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "701 operationType involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
    }

    private void checkParameterOperationId(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 operationId is null or empty.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        if (!str.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 operationId involves invalid character.");
            throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
        }
        if (str.length() == 32) {
            return;
        }
        LogMgr.log(2, "702 operationId length is invalid.");
        throw new IllegalArgumentException(SemClientConst.EXC_INVALID_ARGUMENT_VALUE);
    }

    public static byte[] hexToByteArray(String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return new byte[0];
        }
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Long.parseLong(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getUniqueValue(String str) throws InterruptedException, SemClientException {
        LogMgr.log(8, "000");
        this.mGetUniqueValueLatch = new CountDownLatch(1);
        InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
        internalSemClientEventListener.setCountDownLatch(this.mGetUniqueValueLatch);
        SemClientResultInfo semClientResultInfoDoGetUniqueValue = doGetUniqueValue(str, internalSemClientEventListener);
        if (semClientResultInfoDoGetUniqueValue.mExceptionType != 0) {
            LogMgr.log(2, "700 Failed to exec getUniqueValue. message:" + semClientResultInfoDoGetUniqueValue.getMessage() + " code:" + semClientResultInfoDoGetUniqueValue.getErrorCode());
            throw new SemClientException(900, semClientResultInfoDoGetUniqueValue.getMessage());
        }
        LogMgr.log(9, "001 GetUniqueValueLatch.await()");
        this.mGetUniqueValueLatch.await();
        if (!internalSemClientEventListener.isSuccessToGetUniqueValue()) {
            LogMgr.log(2, "701 Failed to exec getUniqueValue. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
            throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
        }
        LogMgr.log(8, "999");
        return this.mUniqueValue;
    }

    private SemClientResultInfo doGetUniqueValue(String str, ISemClientEventListener iSemClientEventListener) {
        LogMgr.performanceIn("API", "ISemClientImpl", "getUniqueValue");
        LogMgr.log(5, "000");
        try {
            String clientId = this.mSharedPrefsUtil.readClientId();
            if (clientId == null) {
                clientId = SwsParamCreator.createClientId();
                this.mSharedPrefsUtil.writeClientId(clientId);
            }
            this.mDataManager.setClientId(clientId);
            this.mDataManager.setPackageName(this.mContext.getPackageName());
            this.mSwsClientFacade.start(new OnFinishListener(iSemClientEventListener), this.mDataManager);
            this.mSwsClientFacade.getUniqueValue(str);
            LogMgr.log(5, "999");
            return new SemClientResultInfo();
        } catch (SemClientException e) {
            LogMgr.log(2, "700 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), e.getErrorCode());
        } catch (Exception e2) {
            LogMgr.log(2, "701 : Exception occurred. e[" + e2 + "]");
            LogMgr.printStackTrace(8, e2);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
        } finally {
            LogMgr.performanceOut("API", "ISemClientImpl", "getUniqueValue");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getProcessStatus(String str, String str2, String str3, String str4) throws InterruptedException, SemClientException {
        LogMgr.log(8, "000");
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 serviceId is null or empty.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        if (str2 == null || str2.isEmpty()) {
            LogMgr.log(2, "701 processId is null or empty.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        if (str3 == null || str3.isEmpty()) {
            LogMgr.log(2, "702 operationType is null or empty.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        if (str4 == null || str4.isEmpty()) {
            LogMgr.log(2, "703 operationId is null or empty.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        this.mGetProcessStatusLatch = new CountDownLatch(1);
        InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
        internalSemClientEventListener.setCountDownLatch(this.mGetProcessStatusLatch);
        SemClientResultInfo semClientResultInfoDoGetProcessStatus = doGetProcessStatus(str, str2, str3, str4, internalSemClientEventListener);
        if (semClientResultInfoDoGetProcessStatus.mExceptionType != 0) {
            LogMgr.log(2, "704 Failed to exec getProcessStatus. message:" + semClientResultInfoDoGetProcessStatus.getMessage() + " code:" + semClientResultInfoDoGetProcessStatus.getErrorCode());
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(9, "001 GetProcessStatusLatch.await()");
        this.mGetProcessStatusLatch.await();
        if (!internalSemClientEventListener.isSuccessToGetProcessStatus()) {
            LogMgr.log(2, "705 Failed to exec getProcessStatus. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
            throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
        }
        LogMgr.log(8, "999");
        return this.mProcessStatus;
    }

    private SemClientResultInfo doGetProcessStatus(String str, String str2, String str3, String str4, ISemClientEventListener iSemClientEventListener) {
        LogMgr.performanceIn("API", "ISemClientImpl", "getProcessStatus");
        LogMgr.log(5, "000");
        try {
            this.mSwsClientFacade.start(new OnFinishListener(iSemClientEventListener), this.mDataManager);
            this.mSwsClientFacade.getProcessStatus(str, str2, str3, str4);
            LogMgr.log(5, "999");
            return new SemClientResultInfo();
        } catch (SemClientException e) {
            LogMgr.log(2, "700 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), e.getErrorCode());
        } catch (Exception e2) {
            LogMgr.log(2, "701 : Exception occurred. e[" + e2 + "]");
            LogMgr.printStackTrace(8, e2);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
        } finally {
            LogMgr.performanceOut("API", "ISemClientImpl", "getProcessStatus");
        }
    }

    private class StartTsmSequenceThread extends Thread {
        private final boolean mIsCalledFromSpApp;
        private final String mJsonPayload;
        private ISemClientEventListener mOnSemClientEventListener;

        StartTsmSequenceThread(String str, ISemClientEventListener iSemClientEventListener, boolean z) {
            LogMgr.log(9, "000");
            this.mJsonPayload = str;
            this.mOnSemClientEventListener = ISemClientImpl.this.new SemClientEventListenerWrapper(iSemClientEventListener);
            this.mIsCalledFromSpApp = z;
            LogMgr.log(9, "999");
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            String controlInfo;
            LogMgr.log(9, "000");
            try {
                try {
                    controlInfo = ISemClientImpl.this.mSharedPrefsUtil.readControlInfo();
                } catch (SemClientException e) {
                    try {
                        try {
                            int errorCode = e.getErrorCode();
                            String additionalErrorInfo = e.getAdditionalErrorInfo();
                            String strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                            LogMgr.performanceIn("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, " errorCode[" + errorCode + "] additionalErrorInfo [" + additionalErrorInfo + "] message[" + strExecutionPoint + "]");
                            LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, errorCode);
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_ERROR, errorCode, additionalErrorInfo, strExecutionPoint));
                            this.mOnSemClientEventListener = null;
                        } catch (Exception e2) {
                            LogMgr.log(1, "800 Exception occurred. e=" + e2);
                        }
                    } finally {
                        LogMgr.performanceOut("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                    }
                }
                if (controlInfo == null) {
                    throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                }
                ISemClientImpl.this.mDataManager.setClientControlInfo(new ClientControlInfoJsonArray(controlInfo).getClientControlInfo());
                String clientId = ISemClientImpl.this.mSharedPrefsUtil.readClientId();
                if (clientId == null) {
                    clientId = SwsParamCreator.createClientId();
                    ISemClientImpl.this.mSharedPrefsUtil.writeClientId(clientId);
                }
                ISemClientImpl.this.mDataManager.setClientId(clientId);
                ISemClientImpl.this.mDataManager.setPackageName(ISemClientImpl.this.mContext.getPackageName());
                ISemClientImpl.this.mDataManager.setDeviceManufacturer(Build.MANUFACTURER);
                if (CommonConfig.findConfigFile(CommonConfig.FILE_NAME) != null) {
                    ISemClientImpl.this.mDataManager.setDeviceIdentificationData(AccessConfig.getDeviceIdentificationData(ISemClientImpl.this.mContext));
                } else {
                    LogMgr.log(9, "001 SKIP getDeviceIdentificationData");
                }
                ISemClientImpl.this.mSwsClientFacade.start(ISemClientImpl.this.new OnFinishListener(this.mOnSemClientEventListener), ISemClientImpl.this.mDataManager);
                ISemClientImpl.this.mSwsClientFacade.startOnlineSequence(this.mJsonPayload, ISemClientImpl.this.mSemChipHolder, this.mIsCalledFromSpApp, ISemClientImpl.this.mContext);
                LogMgr.log(9, "999");
            } catch (JSONException unused) {
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
        }
    }

    private class InstallAppletThread extends Thread {
        private final String[] mAccessAllowedSpAppIdList;
        private ISemClientEventListener mOnSemClientEventListener;
        private final String mOperationId;
        private final String mOperationType;
        private final String mServiceId;
        private final String mSpAppletId;
        private final String mSpAppletVersion;
        private final String mSpSdKeyVersion;

        InstallAppletThread(String str, String str2, String str3, String str4, String[] strArr, String str5, String str6, ISemClientEventListener iSemClientEventListener) {
            LogMgr.log(9, "000");
            this.mServiceId = str;
            this.mSpAppletId = str2;
            this.mSpAppletVersion = str3;
            this.mSpSdKeyVersion = str4;
            this.mAccessAllowedSpAppIdList = strArr;
            this.mOperationType = str5;
            this.mOperationId = str6;
            this.mOnSemClientEventListener = ISemClientImpl.this.new SemClientEventListenerWrapper(iSemClientEventListener);
            LogMgr.log(9, "999");
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:113:0x01cf */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:114:0x01eb */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:88:0x01e8 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:92:0x01f8 */
        /* JADX WARN: Can't wrap try/catch for region: R(16:0|2|120|(2:118|3)|(3:5|6|(2:73|74)(4:10|140|11|(2:13|(25:15|16|135|17|18|132|19|20|126|21|22|124|23|24|121|25|26|123|27|130|28|29|91|106|107)(4:54|55|56|57))(4:58|142|59|60)))(4:75|128|76|77)|84|116|93|94|137|95|96|91|106|107|(2:(0)|(1:117))) */
        /* JADX WARN: Can't wrap try/catch for region: R(17:0|2|120|118|3|(3:5|6|(2:73|74)(4:10|140|11|(2:13|(25:15|16|135|17|18|132|19|20|126|21|22|124|23|24|121|25|26|123|27|130|28|29|91|106|107)(4:54|55|56|57))(4:58|142|59|60)))(4:75|128|76|77)|84|116|93|94|137|95|96|91|106|107|(2:(0)|(1:117))) */
        /* JADX WARN: Code restructure failed: missing block: B:100:0x0244, code lost:
        
            r0 = th;
         */
        /* JADX WARN: Code restructure failed: missing block: B:101:0x0245, code lost:
        
            r5 = r16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:102:0x0248, code lost:
        
            r0 = e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:103:0x0249, code lost:
        
            r5 = r16;
         */
        /* JADX WARN: Code restructure failed: missing block: B:104:0x024b, code lost:
        
            com.felicanetworks.semc.util.LogMgr.log(1, r19 + r0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:98:0x0242, code lost:
        
            r0 = e;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r11v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r11v1, types: [com.felicanetworks.semc.ISemClientImpl$InstallAppletThread] */
        /* JADX WARN: Type inference failed for: r11v16 */
        /* JADX WARN: Type inference failed for: r11v17 */
        /* JADX WARN: Type inference failed for: r11v18 */
        /* JADX WARN: Type inference failed for: r11v19 */
        /* JADX WARN: Type inference failed for: r11v2 */
        /* JADX WARN: Type inference failed for: r11v20 */
        /* JADX WARN: Type inference failed for: r11v21 */
        /* JADX WARN: Type inference failed for: r11v22 */
        /* JADX WARN: Type inference failed for: r11v23 */
        /* JADX WARN: Type inference failed for: r11v24 */
        /* JADX WARN: Type inference failed for: r11v25 */
        /* JADX WARN: Type inference failed for: r11v26 */
        /* JADX WARN: Type inference failed for: r11v3 */
        /* JADX WARN: Type inference failed for: r11v5 */
        /* JADX WARN: Type inference failed for: r11v6, types: [com.felicanetworks.semc.ISemClientEventListener] */
        /* JADX WARN: Type inference failed for: r11v7, types: [com.felicanetworks.semc.ISemClientEventListener] */
        /* JADX WARN: Type inference failed for: r11v8 */
        /* JADX WARN: Type inference failed for: r11v9, types: [com.felicanetworks.semc.ISemClientImpl$InstallAppletThread] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() throws Throwable {
            String str;
            String str2;
            String str3;
            ?? r11;
            String controlInfo;
            int i;
            ?? r112 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR;
            int i2 = 9;
            LogMgr.log(9, "000");
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    controlInfo = ISemClientImpl.this.mSharedPrefsUtil.readControlInfo();
                                } catch (SemClientException e) {
                                    e = e;
                                    str = "801 Exception occurred. e=";
                                }
                            } catch (InterruptedException unused) {
                                LogMgr.log(i2, "704 InterruptedException");
                                this.mOnSemClientEventListener = r112;
                                r11 = r112;
                                ISemClientImpl.this.mInstallAppletThread = r11;
                                LogMgr.log(9, "999");
                                return;
                            }
                        } catch (SemClientException e2) {
                            e = e2;
                        }
                    } catch (JSONException unused2) {
                    }
                } catch (InterruptedException unused3) {
                    r112 = 0;
                } catch (Throwable th) {
                    th = th;
                    r112 = 0;
                }
                try {
                    if (controlInfo == null) {
                        try {
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        } catch (JSONException unused4) {
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                    }
                    ISemClientImpl.this.mDataManager.setClientControlInfo(new ClientControlInfoJsonArray(controlInfo).getClientControlInfo());
                    String spAppId = ISemClientImpl.this.mConnectedApp.getSpAppId();
                    if (spAppId == null || spAppId.isEmpty()) {
                        LogMgr.log(2, "700 spAppId is null.");
                        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                    }
                    try {
                        String chipIssuerId = AccessConfig.getChipIssuerId();
                        ISemClientImpl iSemClientImpl = ISemClientImpl.this;
                        String str4 = this.mServiceId;
                        str3 = ISemClientImpl.USE_CASE_INSTALL_APPLET;
                        if (!iSemClientImpl.checkAllowedService(spAppId, str4, chipIssuerId, ISemClientImpl.USE_CASE_INSTALL_APPLET)) {
                            i = 2;
                            try {
                                LogMgr.log(2, "701 checkAllowedService is false.");
                                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
                            } catch (SemClientException e3) {
                                e = e3;
                                throw new SemClientException(e.getErrorCode(), ObfuscatedMsgUtil.executionPoint(e));
                            } catch (JSONException e4) {
                                e = e4;
                                LogMgr.log(i, "702 JSONException");
                                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint(e));
                            }
                        }
                        if (ISemClientImpl.this.mContext == null) {
                            LogMgr.log(2, "703 Context is null.");
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                        ISemClientImpl.this.mDataManager.setPackageName(ISemClientImpl.this.mContext.getPackageName());
                        ISemClientImpl.this.mDataManager.setIntegrityVerificationUniqueValue(ISemClientImpl.this.getUniqueValue(this.mServiceId));
                        try {
                            String str5 = this.mServiceId;
                            String str6 = this.mSpAppletId;
                            str3 = this.mSpAppletVersion;
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    r112 = 0;
                                                    try {
                                                        installApplet(str5, spAppId, str6, str3, this.mSpSdKeyVersion, this.mAccessAllowedSpAppIdList, this.mOperationType, this.mOperationId);
                                                        ISemClientImpl.this.mDataManager.setIntegrityVerificationUniqueValue(null);
                                                        ISemClientImpl.this.mDataManager.setPlayIntegrityToken(null);
                                                        ISemClientImpl iSemClientImpl2 = ISemClientImpl.this;
                                                        iSemClientImpl2.startTsmSequence(iSemClientImpl2.mLinkageData);
                                                        ISemClientImpl iSemClientImpl3 = ISemClientImpl.this;
                                                        String processStatus = iSemClientImpl3.getProcessStatus(this.mServiceId, iSemClientImpl3.mProcessId, this.mOperationType, this.mOperationId);
                                                        try {
                                                            try {
                                                                LogMgr.performanceIn("API", "OnInstallAppletListener", "onFinished");
                                                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_FINISHED, processStatus, ISemClientImpl.this.mProcessResultCode, ISemClientImpl.this.mProcessResultDetailCode, ISemClientImpl.this.mProcessResultMessageString, ISemClientImpl.this.mSdKeyDerivationDataList));
                                                                this.mOnSemClientEventListener = null;
                                                            } finally {
                                                                LogMgr.performanceOut("API", "OnInstallAppletListener", "onFinished");
                                                            }
                                                        } catch (Exception e5) {
                                                            LogMgr.log(1, "800 Exception occurred. e=" + e5);
                                                        }
                                                    } catch (InterruptedException unused5) {
                                                        i2 = 2;
                                                        LogMgr.log(i2, "704 InterruptedException");
                                                        this.mOnSemClientEventListener = r112;
                                                        r11 = r112;
                                                    }
                                                } catch (SemClientException e6) {
                                                    e = e6;
                                                    str2 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR;
                                                    r112 = 0;
                                                    str = "801 Exception occurred. e=";
                                                    int errorCode = e.getErrorCode();
                                                    String additionalErrorInfo = e.getAdditionalErrorInfo();
                                                    String message = e.getMessage();
                                                    str3 = str2;
                                                    LogMgr.performanceIn("API", "OnInstallAppletListener", str3, " errorCode[" + errorCode + "] additionalErrorInfo[" + additionalErrorInfo + "] message[" + message + "]");
                                                    LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.INSTALL_APPLET, errorCode);
                                                    this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_ERROR, errorCode, additionalErrorInfo, message));
                                                    this.mOnSemClientEventListener = r112;
                                                    LogMgr.performanceOut("API", "OnInstallAppletListener", str3);
                                                    r11 = r112;
                                                }
                                            } catch (SemClientException e7) {
                                                e = e7;
                                                str = "801 Exception occurred. e=";
                                                str2 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR;
                                                r112 = 0;
                                            }
                                        } catch (InterruptedException unused6) {
                                            r112 = 0;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            r112 = 0;
                                            ISemClientImpl.this.mInstallAppletThread = r112;
                                            throw th;
                                        }
                                    } catch (SemClientException e8) {
                                        e = e8;
                                        str = "801 Exception occurred. e=";
                                        str2 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR;
                                        r112 = 0;
                                    }
                                } catch (SemClientException e9) {
                                    e = e9;
                                    str = "801 Exception occurred. e=";
                                }
                            } catch (InterruptedException unused7) {
                                r112 = 0;
                            } catch (Throwable th3) {
                                th = th3;
                                r112 = 0;
                            }
                        } catch (SemClientException e10) {
                            e = e10;
                            str = "801 Exception occurred. e=";
                            str2 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR;
                            r112 = 0;
                        }
                        ISemClientImpl.this.mInstallAppletThread = r11;
                        LogMgr.log(9, "999");
                        return;
                    } catch (SemClientException e11) {
                        e = e11;
                    } catch (JSONException e12) {
                        e = e12;
                        i = 2;
                    }
                    int errorCode2 = e.getErrorCode();
                    String additionalErrorInfo2 = e.getAdditionalErrorInfo();
                    String message2 = e.getMessage();
                    str3 = str2;
                    LogMgr.performanceIn("API", "OnInstallAppletListener", str3, " errorCode[" + errorCode2 + "] additionalErrorInfo[" + additionalErrorInfo2 + "] message[" + message2 + "]");
                    LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.INSTALL_APPLET, errorCode2);
                    this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_ERROR, errorCode2, additionalErrorInfo2, message2));
                    this.mOnSemClientEventListener = r112;
                    LogMgr.performanceOut("API", "OnInstallAppletListener", str3);
                    r11 = r112;
                    ISemClientImpl.this.mInstallAppletThread = r11;
                    LogMgr.log(9, "999");
                    return;
                } catch (Throwable th4) {
                    th = th4;
                    LogMgr.performanceOut("API", "OnInstallAppletListener", str3);
                    throw th;
                }
                str2 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR;
                r112 = 0;
            } catch (Throwable th5) {
                th = th5;
            }
        }

        private void installApplet(String str, String str2, String str3, String str4, String str5, String[] strArr, String str6, String str7) throws InterruptedException, SemClientException {
            LogMgr.log(8, "000");
            ISemClientImpl.this.mInstallAppletLatch = new CountDownLatch(1);
            InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
            internalSemClientEventListener.setCountDownLatch(ISemClientImpl.this.mInstallAppletLatch);
            SemClientResultInfo semClientResultInfoDoInstallApplet = doInstallApplet(str, str2, str3, str4, str5, strArr, str6, str7, internalSemClientEventListener);
            if (semClientResultInfoDoInstallApplet.mExceptionType != 0) {
                LogMgr.log(2, "700 Failed to exec install Applet. message:" + semClientResultInfoDoInstallApplet.getMessage() + " code:" + semClientResultInfoDoInstallApplet.getErrorCode());
                throw new SemClientException(900, semClientResultInfoDoInstallApplet.getMessage());
            }
            LogMgr.log(9, "001 InstallAppletLatch.await()");
            ISemClientImpl.this.mInstallAppletLatch.await();
            if (!internalSemClientEventListener.isSuccessToInstallApplet()) {
                LogMgr.log(2, "701 Failed to exec install Applet. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
                throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
            }
            LogMgr.log(8, "999");
        }

        private SemClientResultInfo doInstallApplet(String str, String str2, String str3, String str4, String str5, String[] strArr, String str6, String str7, ISemClientEventListener iSemClientEventListener) {
            LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_INSTALL_APPLET);
            LogMgr.log(5, "000");
            try {
                ISemClientImpl.this.mSwsClientFacade.start(ISemClientImpl.this.new OnFinishListener(iSemClientEventListener), ISemClientImpl.this.mDataManager);
                ISemClientImpl.this.mSwsClientFacade.installApplet(str, str2, str3, str4, str5, strArr, str6, str7);
                LogMgr.log(5, "999");
                return new SemClientResultInfo();
            } catch (SemClientException e) {
                LogMgr.log(2, "700 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), e.getErrorCode());
            } catch (Exception e2) {
                LogMgr.log(2, "701 : Exception occurred. e[" + e2 + "]");
                LogMgr.printStackTrace(8, e2);
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
            } finally {
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_INSTALL_APPLET);
            }
        }
    }

    private class UpgradeAppletThread extends Thread {
        private final String mNewSpAppletVersion;
        private final String mOldSpAppletVersion;
        private ISemClientEventListener mOnSemClientEventListener;
        private final String mOperationId;
        private final String mOperationType;
        private final String mServiceId;
        private final String mSpAppletId;

        UpgradeAppletThread(String str, String str2, String str3, String str4, String str5, String str6, ISemClientEventListener iSemClientEventListener) {
            LogMgr.log(9, "000");
            this.mServiceId = str;
            this.mSpAppletId = str2;
            this.mOldSpAppletVersion = str3;
            this.mNewSpAppletVersion = str4;
            this.mOperationType = str5;
            this.mOperationId = str6;
            this.mOnSemClientEventListener = ISemClientImpl.this.new SemClientEventListenerWrapper(iSemClientEventListener);
            LogMgr.log(9, "999");
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:107:0x01c1 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:108:0x01d9 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:110:0x01e7 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:88:0x01d6 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:92:0x01e6 */
        /* JADX WARN: Can't wrap try/catch for region: R(7:(2:74|75)(4:10|123|11|(2:13|(22:15|16|133|17|18|129|19|20|127|21|22|131|23|24|112|25|117|26|27|91|101|102)(4:55|56|57|58))(4:59|125|60|61))|110|93|94|91|101|102) */
        /* JADX WARN: Code restructure failed: missing block: B:98:0x0230, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:99:0x0231, code lost:
        
            com.felicanetworks.semc.util.LogMgr.log(1, r17 + r0);
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r15v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r15v1 */
        /* JADX WARN: Type inference failed for: r15v10 */
        /* JADX WARN: Type inference failed for: r15v17 */
        /* JADX WARN: Type inference failed for: r15v18 */
        /* JADX WARN: Type inference failed for: r15v19 */
        /* JADX WARN: Type inference failed for: r15v2, types: [com.felicanetworks.semc.ISemClientImpl$UpgradeAppletThread] */
        /* JADX WARN: Type inference failed for: r15v20 */
        /* JADX WARN: Type inference failed for: r15v21 */
        /* JADX WARN: Type inference failed for: r15v22 */
        /* JADX WARN: Type inference failed for: r15v23 */
        /* JADX WARN: Type inference failed for: r15v24 */
        /* JADX WARN: Type inference failed for: r15v25 */
        /* JADX WARN: Type inference failed for: r15v26 */
        /* JADX WARN: Type inference failed for: r15v27 */
        /* JADX WARN: Type inference failed for: r15v28 */
        /* JADX WARN: Type inference failed for: r15v29 */
        /* JADX WARN: Type inference failed for: r15v3, types: [com.felicanetworks.semc.ISemClientEventListener] */
        /* JADX WARN: Type inference failed for: r15v30 */
        /* JADX WARN: Type inference failed for: r15v31 */
        /* JADX WARN: Type inference failed for: r15v32 */
        /* JADX WARN: Type inference failed for: r15v4, types: [com.felicanetworks.semc.ISemClientImpl$UpgradeAppletThread] */
        /* JADX WARN: Type inference failed for: r15v5 */
        /* JADX WARN: Type inference failed for: r15v6 */
        /* JADX WARN: Type inference failed for: r15v7 */
        /* JADX WARN: Type inference failed for: r15v9, types: [com.felicanetworks.semc.ISemClientEventListener] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() throws Throwable {
            String str;
            String controlInfo;
            int i;
            ?? r15 = "801 Exception occurred. e=";
            LogMgr.log(9, "000");
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    controlInfo = ISemClientImpl.this.mSharedPrefsUtil.readControlInfo();
                                } catch (JSONException unused) {
                                }
                            } catch (InterruptedException unused2) {
                                LogMgr.log(2, "704 InterruptedException");
                                this.mOnSemClientEventListener = r15;
                                ?? r152 = r15;
                                ISemClientImpl.this.mUpgradeAppletThread = r152;
                                LogMgr.log(9, "999");
                                return;
                            }
                        } catch (Throwable th) {
                            th = th;
                            ISemClientImpl.this.mUpgradeAppletThread = r15;
                            throw th;
                        }
                    } catch (SemClientException e) {
                        e = e;
                        str = "801 Exception occurred. e=";
                        r15 = 0;
                    }
                } catch (InterruptedException unused3) {
                    r15 = 0;
                } catch (Throwable th2) {
                    th = th2;
                    r15 = 0;
                }
            } catch (SemClientException e2) {
                e = e2;
            }
            if (controlInfo != null) {
                ISemClientImpl.this.mDataManager.setClientControlInfo(new ClientControlInfoJsonArray(controlInfo).getClientControlInfo());
                String spAppId = ISemClientImpl.this.mConnectedApp.getSpAppId();
                try {
                    if (spAppId == null || spAppId.isEmpty()) {
                        LogMgr.log(2, "700 spAppId is null.");
                        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                    }
                    try {
                        if (!ISemClientImpl.this.checkAllowedService(spAppId, this.mServiceId, AccessConfig.getChipIssuerId(), ISemClientImpl.USE_CASE_UPGRADE_APPLET)) {
                            i = 2;
                            try {
                                LogMgr.log(2, "701 checkAllowedService is false.");
                                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
                            } catch (SemClientException e3) {
                                e = e3;
                                throw new SemClientException(e.getErrorCode(), ObfuscatedMsgUtil.executionPoint(e));
                            } catch (JSONException e4) {
                                e = e4;
                                LogMgr.log(i, "702 JSONException");
                                throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint(e));
                            }
                        }
                        if (ISemClientImpl.this.mContext == null) {
                            LogMgr.log(2, "703 Context is null.");
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                        ISemClientImpl.this.mDataManager.setPackageName(ISemClientImpl.this.mContext.getPackageName());
                        ISemClientImpl.this.mDataManager.setIntegrityVerificationUniqueValue(ISemClientImpl.this.getUniqueValue(this.mServiceId));
                        try {
                            try {
                                try {
                                } catch (SemClientException e5) {
                                    e = e5;
                                    str = "801 Exception occurred. e=";
                                    r15 = 0;
                                } catch (InterruptedException unused4) {
                                    r15 = 0;
                                } catch (Throwable th3) {
                                    th = th3;
                                    r15 = 0;
                                }
                            } catch (SemClientException e6) {
                                e = e6;
                                str = "801 Exception occurred. e=";
                                r15 = 0;
                            } catch (InterruptedException unused5) {
                                r15 = 0;
                            } catch (Throwable th4) {
                                th = th4;
                                r15 = 0;
                            }
                            try {
                                r15 = 0;
                                try {
                                    upgradeApplet(this.mServiceId, spAppId, this.mSpAppletId, this.mOldSpAppletVersion, this.mNewSpAppletVersion, this.mOperationType, this.mOperationId);
                                    ISemClientImpl.this.mDataManager.setIntegrityVerificationUniqueValue(null);
                                    ISemClientImpl.this.mDataManager.setPlayIntegrityToken(null);
                                    ISemClientImpl iSemClientImpl = ISemClientImpl.this;
                                    iSemClientImpl.startTsmSequence(iSemClientImpl.mLinkageData);
                                    ISemClientImpl iSemClientImpl2 = ISemClientImpl.this;
                                    String processStatus = iSemClientImpl2.getProcessStatus(this.mServiceId, iSemClientImpl2.mProcessId, this.mOperationType, this.mOperationId);
                                    try {
                                        try {
                                            LogMgr.performanceIn("API", "OnUpgradeAppletListener", "onFinished");
                                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_FINISHED, processStatus, ISemClientImpl.this.mProcessResultCode, ISemClientImpl.this.mProcessResultDetailCode, ISemClientImpl.this.mProcessResultMessageString, ISemClientImpl.this.mSdKeyDerivationDataList));
                                            this.mOnSemClientEventListener = null;
                                        } catch (Exception e7) {
                                            LogMgr.log(1, "800 Exception occurred. e=" + e7);
                                        }
                                    } finally {
                                        LogMgr.performanceOut("API", "OnUpgradeAppletListener", "onFinished");
                                    }
                                } catch (InterruptedException unused6) {
                                    LogMgr.log(2, "704 InterruptedException");
                                    this.mOnSemClientEventListener = r15;
                                    ?? r1522 = r15;
                                }
                            } catch (SemClientException e8) {
                                e = e8;
                                str = "801 Exception occurred. e=";
                                r15 = 0;
                                int errorCode = e.getErrorCode();
                                String additionalErrorInfo = e.getAdditionalErrorInfo();
                                String message = e.getMessage();
                                LogMgr.performanceIn("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, " errorCode[" + errorCode + "] additionalErrorInfo[" + additionalErrorInfo + "] message[" + message + "]");
                                LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.UPGRADE_APPLET, errorCode);
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_ERROR, errorCode, additionalErrorInfo, message));
                                this.mOnSemClientEventListener = r15;
                            } catch (InterruptedException unused7) {
                                r15 = 0;
                            } catch (Throwable th5) {
                                th = th5;
                                r15 = 0;
                                ISemClientImpl.this.mUpgradeAppletThread = r15;
                                throw th;
                            }
                        } catch (SemClientException e9) {
                            e = e9;
                            str = "801 Exception occurred. e=";
                            r15 = 0;
                        } catch (InterruptedException unused8) {
                            r15 = 0;
                        } catch (Throwable th6) {
                            th = th6;
                            r15 = 0;
                        }
                        ISemClientImpl.this.mUpgradeAppletThread = r1522;
                        LogMgr.log(9, "999");
                        return;
                    } catch (SemClientException e10) {
                        e = e10;
                    } catch (JSONException e11) {
                        e = e11;
                        i = 2;
                    }
                    int errorCode2 = e.getErrorCode();
                    String additionalErrorInfo2 = e.getAdditionalErrorInfo();
                    String message2 = e.getMessage();
                    LogMgr.performanceIn("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, " errorCode[" + errorCode2 + "] additionalErrorInfo[" + additionalErrorInfo2 + "] message[" + message2 + "]");
                    LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.UPGRADE_APPLET, errorCode2);
                    this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_ERROR, errorCode2, additionalErrorInfo2, message2));
                    this.mOnSemClientEventListener = r15;
                    ISemClientImpl.this.mUpgradeAppletThread = r1522;
                    LogMgr.log(9, "999");
                    return;
                } finally {
                    LogMgr.performanceOut("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                }
            }
            try {
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            } catch (JSONException unused9) {
            }
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }

        private void upgradeApplet(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws InterruptedException, SemClientException {
            LogMgr.log(8, "000");
            ISemClientImpl.this.mUpgradeAppletLatch = new CountDownLatch(1);
            InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
            internalSemClientEventListener.setCountDownLatch(ISemClientImpl.this.mUpgradeAppletLatch);
            SemClientResultInfo semClientResultInfoDoUpgradeApplet = doUpgradeApplet(str, str2, str3, str4, str5, str6, str7, internalSemClientEventListener);
            if (semClientResultInfoDoUpgradeApplet.mExceptionType != 0) {
                LogMgr.log(2, "700 Failed to exec upgrade Applet. message:" + semClientResultInfoDoUpgradeApplet.getMessage() + " code:" + semClientResultInfoDoUpgradeApplet.getErrorCode());
                throw new SemClientException(900, semClientResultInfoDoUpgradeApplet.getMessage());
            }
            LogMgr.log(9, "001 UpgradeAppletLatch.await()");
            ISemClientImpl.this.mUpgradeAppletLatch.await();
            if (!internalSemClientEventListener.isSuccessToUpgradeApplet()) {
                LogMgr.log(2, "701 Failed to exec upgrade Applet. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
                throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
            }
            LogMgr.log(8, "999");
        }

        private SemClientResultInfo doUpgradeApplet(String str, String str2, String str3, String str4, String str5, String str6, String str7, ISemClientEventListener iSemClientEventListener) {
            LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
            LogMgr.log(5, "000");
            try {
                ISemClientImpl.this.mSwsClientFacade.start(ISemClientImpl.this.new OnFinishListener(iSemClientEventListener), ISemClientImpl.this.mDataManager);
                ISemClientImpl.this.mSwsClientFacade.upgrade(str, str2, str3, str4, str5, str6, str7);
                LogMgr.log(5, "999");
                return new SemClientResultInfo();
            } catch (SemClientException e) {
                LogMgr.log(2, "700 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), e.getErrorCode());
            } catch (Exception e2) {
                LogMgr.log(2, "701 : Exception occurred. e[" + e2 + "]");
                LogMgr.printStackTrace(8, e2);
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
            } finally {
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
            }
        }
    }

    private class DeleteAppletThread extends Thread {
        private ISemClientEventListener mOnSemClientEventListener;
        private final String mOperationId;
        private final String mOperationType;
        private final String mServiceId;
        private final String mSpAppletId;
        private final String mSpAppletVersion;

        DeleteAppletThread(String str, String str2, String str3, String str4, String str5, ISemClientEventListener iSemClientEventListener) {
            LogMgr.log(9, "000");
            this.mServiceId = str;
            this.mSpAppletId = str2;
            this.mSpAppletVersion = str3;
            this.mOperationType = str4;
            this.mOperationId = str5;
            this.mOnSemClientEventListener = ISemClientImpl.this.new SemClientEventListenerWrapper(iSemClientEventListener);
            LogMgr.log(9, "999");
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:61:0x0188 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:65:0x0198 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:80:0x0109 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:81:0x018b */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:87:0x0019 */
        /* JADX WARN: Can't wrap try/catch for region: R(9:5|6|(2:48|49)(4:10|101|11|(2:13|(11:94|15|16|92|17|85|18|19|64|74|75)(4:29|30|31|32))(4:33|99|34|35))|83|66|67|64|74|75) */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x01e2, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x01e3, code lost:
        
            com.felicanetworks.semc.util.LogMgr.log(1, "801 Exception occurred. e=" + r0);
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r15v0 */
        /* JADX WARN: Type inference failed for: r15v1, types: [com.felicanetworks.semc.ISemClientEventListener] */
        /* JADX WARN: Type inference failed for: r15v15 */
        /* JADX WARN: Type inference failed for: r15v16 */
        /* JADX WARN: Type inference failed for: r15v17 */
        /* JADX WARN: Type inference failed for: r15v18 */
        /* JADX WARN: Type inference failed for: r15v19 */
        /* JADX WARN: Type inference failed for: r15v2, types: [com.felicanetworks.semc.ISemClientImpl$DeleteAppletThread] */
        /* JADX WARN: Type inference failed for: r15v20 */
        /* JADX WARN: Type inference failed for: r15v21 */
        /* JADX WARN: Type inference failed for: r15v22 */
        /* JADX WARN: Type inference failed for: r15v3 */
        /* JADX WARN: Type inference failed for: r15v4 */
        /* JADX WARN: Type inference failed for: r15v5 */
        /* JADX WARN: Type inference failed for: r15v6, types: [com.felicanetworks.semc.ISemClientEventListener] */
        /* JADX WARN: Type inference failed for: r15v8, types: [com.felicanetworks.semc.ISemClientImpl$DeleteAppletThread] */
        /* JADX WARN: Type inference failed for: r15v9 */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() throws Throwable {
            int i;
            ?? r15;
            String controlInfo;
            int i2;
            ?? r152 = 9;
             = 9;
            ?? r153 = 9;
            LogMgr.log(9, "000");
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    controlInfo = ISemClientImpl.this.mSharedPrefsUtil.readControlInfo();
                                } catch (Throwable th) {
                                    th = th;
                                    ISemClientImpl.this.mDeleteAppletThread = r153;
                                    throw th;
                                }
                            } catch (JSONException unused) {
                            }
                        } catch (SemClientException e) {
                            e = e;
                        }
                    } catch (SemClientException e2) {
                        e = e2;
                        r153 = 0;
                    }
                } catch (InterruptedException unused2) {
                    i = 2;
                    r152 = 0;
                }
                if (controlInfo != null) {
                    ISemClientImpl.this.mDataManager.setClientControlInfo(new ClientControlInfoJsonArray(controlInfo).getClientControlInfo());
                    String spAppId = ISemClientImpl.this.mConnectedApp.getSpAppId();
                    try {
                        if (spAppId == null || spAppId.isEmpty()) {
                            LogMgr.log(2, "700 spAppId is null.");
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                        try {
                            if (!ISemClientImpl.this.checkAllowedService(spAppId, this.mServiceId, AccessConfig.getChipIssuerId(), ISemClientImpl.USE_CASE_DELETE_APPLET)) {
                                i2 = 2;
                                try {
                                    LogMgr.log(2, "701 checkAllowedService is false.");
                                    throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
                                } catch (SemClientException e3) {
                                    e = e3;
                                    throw new SemClientException(e.getErrorCode(), ObfuscatedMsgUtil.executionPoint(e));
                                } catch (JSONException e4) {
                                    e = e4;
                                    LogMgr.log(i2, "702 JSONException");
                                    throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint(e));
                                }
                            }
                            if (ISemClientImpl.this.mContext == null) {
                                LogMgr.log(2, "703 Context is null.");
                                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                            }
                            try {
                                ISemClientImpl.this.mDataManager.setPackageName(ISemClientImpl.this.mContext.getPackageName());
                                ISemClientImpl.this.mDataManager.setIntegrityVerificationUniqueValue(ISemClientImpl.this.getUniqueValue(this.mServiceId));
                                r152 = 0;
                                r15 = 0;
                                try {
                                    deleteApplet(this.mServiceId, spAppId, this.mSpAppletId, this.mSpAppletVersion, this.mOperationType, this.mOperationId);
                                    ISemClientImpl.this.mDataManager.setIntegrityVerificationUniqueValue(null);
                                    ISemClientImpl.this.mDataManager.setPlayIntegrityToken(null);
                                    ISemClientImpl iSemClientImpl = ISemClientImpl.this;
                                    iSemClientImpl.startTsmSequence(iSemClientImpl.mLinkageData);
                                    ISemClientImpl iSemClientImpl2 = ISemClientImpl.this;
                                    String processStatus = iSemClientImpl2.getProcessStatus(this.mServiceId, iSemClientImpl2.mProcessId, this.mOperationType, this.mOperationId);
                                    try {
                                        try {
                                            LogMgr.performanceIn("API", "OnDeleteAppletListener", "onFinished");
                                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_FINISHED, processStatus, ISemClientImpl.this.mProcessResultCode, ISemClientImpl.this.mProcessResultDetailCode, ISemClientImpl.this.mProcessResultMessageString, ISemClientImpl.this.mSdKeyDerivationDataList));
                                            this.mOnSemClientEventListener = null;
                                        } finally {
                                            LogMgr.performanceOut("API", "OnDeleteAppletListener", "onFinished");
                                        }
                                    } catch (Exception e5) {
                                        LogMgr.log(1, "800 Exception occurred. e=" + e5);
                                    }
                                } catch (InterruptedException unused3) {
                                    i = 2;
                                    LogMgr.log(i, "704 InterruptedException");
                                    this.mOnSemClientEventListener = r152;
                                    r15 = r152;
                                }
                            } catch (InterruptedException unused4) {
                                r152 = 0;
                            }
                            ISemClientImpl.this.mDeleteAppletThread = r15;
                            LogMgr.log(9, "999");
                            return;
                        } catch (SemClientException e6) {
                            e = e6;
                        } catch (JSONException e7) {
                            e = e7;
                            i2 = 2;
                        }
                        int errorCode = e.getErrorCode();
                        String additionalErrorInfo = e.getAdditionalErrorInfo();
                        String message = e.getMessage();
                        LogMgr.performanceIn("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, " errorCode[" + errorCode + "] additionalErrorInfo[" + additionalErrorInfo + "] message[" + message + "]");
                        LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.DELETE_APPLET, errorCode);
                        this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_ERROR, errorCode, additionalErrorInfo, message));
                        this.mOnSemClientEventListener = r153;
                        ISemClientImpl.this.mDeleteAppletThread = r15;
                        LogMgr.log(9, "999");
                        return;
                    } finally {
                        LogMgr.performanceOut("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                    }
                }
                try {
                    throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                } catch (JSONException unused5) {
                }
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            } catch (Throwable th2) {
                th = th2;
                r153 = 0;
                ISemClientImpl.this.mDeleteAppletThread = r153;
                throw th;
            }
        }

        private void deleteApplet(String str, String str2, String str3, String str4, String str5, String str6) throws InterruptedException, SemClientException {
            LogMgr.log(8, "000");
            ISemClientImpl.this.mDeleteAppletLatch = new CountDownLatch(1);
            InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
            internalSemClientEventListener.setCountDownLatch(ISemClientImpl.this.mDeleteAppletLatch);
            SemClientResultInfo semClientResultInfoDoDeleteApplet = doDeleteApplet(str, str2, str3, str4, str5, str6, internalSemClientEventListener);
            if (semClientResultInfoDoDeleteApplet.mExceptionType != 0) {
                LogMgr.log(2, "700 Failed to exec delete Applet. message:" + semClientResultInfoDoDeleteApplet.getMessage() + " code:" + semClientResultInfoDoDeleteApplet.getErrorCode());
                throw new SemClientException(900, semClientResultInfoDoDeleteApplet.getMessage());
            }
            LogMgr.log(9, "001 DeleteAppletLatch.await()");
            ISemClientImpl.this.mDeleteAppletLatch.await();
            if (!internalSemClientEventListener.isSuccessToDeleteApplet()) {
                LogMgr.log(2, "701 Failed to exec delete Applet. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
                throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
            }
            LogMgr.log(8, "999");
        }

        private SemClientResultInfo doDeleteApplet(String str, String str2, String str3, String str4, String str5, String str6, ISemClientEventListener iSemClientEventListener) {
            LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
            LogMgr.log(5, "000");
            try {
                ISemClientImpl.this.mSwsClientFacade.start(ISemClientImpl.this.new OnFinishListener(iSemClientEventListener), ISemClientImpl.this.mDataManager);
                ISemClientImpl.this.mSwsClientFacade.delete(str, str2, str3, str4, str5, str6);
                LogMgr.log(5, "999");
                return new SemClientResultInfo();
            } catch (SemClientException e) {
                LogMgr.log(2, "700 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), e.getErrorCode());
            } catch (Exception e2) {
                LogMgr.log(2, "701 : Exception occurred. e[" + e2 + "]");
                LogMgr.printStackTrace(8, e2);
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
            } finally {
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
            }
        }
    }

    private class GetAppletStatusThread extends Thread {
        private ISemClientEventListener mOnSemClientEventListener;
        private final String mOperationId;
        private final String mOperationType;
        private final String mServiceId;
        private final String mSpAppletId;

        GetAppletStatusThread(String str, String str2, String str3, String str4, ISemClientEventListener iSemClientEventListener) {
            LogMgr.log(9, "000");
            this.mServiceId = str;
            this.mSpAppletId = str2;
            this.mOperationType = str3;
            this.mOperationId = str4;
            this.mOnSemClientEventListener = ISemClientImpl.this.new SemClientEventListenerWrapper(iSemClientEventListener);
            LogMgr.log(9, "999");
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:106:0x029d */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:110:0x02ac */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:125:0x028a */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:126:0x029f */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:127:0x0204 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:135:0x02ad */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:58:0x01e7 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v10 */
        /* JADX WARN: Type inference failed for: r2v17 */
        /* JADX WARN: Type inference failed for: r2v18 */
        /* JADX WARN: Type inference failed for: r2v19 */
        /* JADX WARN: Type inference failed for: r2v2, types: [com.felicanetworks.semc.ISemClientImpl$GetAppletStatusThread] */
        /* JADX WARN: Type inference failed for: r2v25 */
        /* JADX WARN: Type inference failed for: r2v3, types: [com.felicanetworks.semc.ISemClientEventListener] */
        /* JADX WARN: Type inference failed for: r2v4, types: [com.felicanetworks.semc.ISemClientImpl$GetAppletStatusThread] */
        /* JADX WARN: Type inference failed for: r2v57 */
        /* JADX WARN: Type inference failed for: r2v59 */
        /* JADX WARN: Type inference failed for: r2v6 */
        /* JADX WARN: Type inference failed for: r2v60 */
        /* JADX WARN: Type inference failed for: r2v61 */
        /* JADX WARN: Type inference failed for: r2v7 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9, types: [com.felicanetworks.semc.ISemClientEventListener] */
        /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r3v19, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r3v20 */
        /* JADX WARN: Type inference failed for: r3v21 */
        /* JADX WARN: Type inference failed for: r3v5, types: [int] */
        /* JADX WARN: Type inference failed for: r3v6 */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws Throwable {
            ?? r2;
            int i;
            String controlInfo;
            int i2;
            JSONArray jSONArray;
            JSONArray jSONArrayOptJSONArray;
            boolean z;
            boolean z2;
            ?? r22 = "800 Exception occurred. e=";
            ?? r3 = "000";
            LogMgr.log(9, "000");
            try {
            } catch (SemClientException e) {
                e = e;
            }
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    controlInfo = ISemClientImpl.this.mSharedPrefsUtil.readControlInfo();
                                } catch (SemClientException e2) {
                                    e = e2;
                                    r22 = 0;
                                }
                            } catch (InterruptedException unused) {
                                r22 = 0;
                            }
                        } catch (Throwable th) {
                            th = th;
                            r22 = 0;
                        }
                    } catch (InterruptedException unused2) {
                    }
                    if (controlInfo != null) {
                        ISemClientImpl.this.mDataManager.setClientControlInfo(new ClientControlInfoJsonArray(controlInfo).getClientControlInfo());
                        String spAppId = ISemClientImpl.this.mConnectedApp.getSpAppId();
                        if (spAppId == null || spAppId.isEmpty()) {
                            LogMgr.log(2, "700 spAppId is null.");
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                        try {
                            if (!ISemClientImpl.this.checkAllowedService(spAppId, this.mServiceId, AccessConfig.getChipIssuerId(), ISemClientImpl.USE_CASE_GET_APPLET_STATUS)) {
                                i2 = 2;
                                try {
                                    LogMgr.log(2, "701 checkAllowedService is false.");
                                    throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint());
                                } catch (SemClientException e3) {
                                    e = e3;
                                    throw new SemClientException(e.getErrorCode(), ObfuscatedMsgUtil.executionPoint(e));
                                } catch (JSONException e4) {
                                    e = e4;
                                    LogMgr.log(i2, "702 JSONException");
                                    throw new SemClientException(100, ObfuscatedMsgUtil.executionPoint(e));
                                }
                            }
                            if (ISemClientImpl.this.mContext == null) {
                                LogMgr.log(2, "703 Context is null.");
                                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                            }
                            ISemClientImpl.this.mDataManager.setPackageName(ISemClientImpl.this.mContext.getPackageName());
                            if (ISemClientImpl.this.mSharedPrefsUtil.hasAppletStatusCacheList(this.mSpAppletId)) {
                                LogMgr.log(9, "001 AppletStatusCache exists spAppletId=[" + this.mSpAppletId + "]");
                                JSONArray jSONArrayCreateSpAppletVersionList = createSpAppletVersionList(this.mServiceId, this.mSpAppletId);
                                JSONArray appletStatusList = getAppletStatusList(this.mSpAppletId);
                                JSONArray accessAllowedSpAppIdList = getAccessAllowedSpAppIdList(this.mSpAppletId);
                                try {
                                    try {
                                        LogMgr.performanceIn("API", "OnGetAppletStatusListener", "onFinished");
                                        this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED, "", "", "", "", jSONArrayCreateSpAppletVersionList, appletStatusList, accessAllowedSpAppIdList));
                                        this.mOnSemClientEventListener = null;
                                    } catch (Throwable th2) {
                                        r22 = 0;
                                        try {
                                            LogMgr.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
                                            throw th2;
                                        } catch (InterruptedException unused3) {
                                        }
                                    }
                                } catch (Exception e5) {
                                    LogMgr.log(1, "800 Exception occurred. e=" + e5);
                                }
                                LogMgr.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
                                ISemClientImpl.this.mGetAppletStatusThread = null;
                                return;
                            }
                            try {
                                getAppletStatus(this.mServiceId, spAppId, this.mSpAppletId, this.mOperationType, this.mOperationId);
                                ISemClientImpl iSemClientImpl = ISemClientImpl.this;
                                iSemClientImpl.startTsmSequence(iSemClientImpl.mLinkageData);
                                ISemClientImpl iSemClientImpl2 = ISemClientImpl.this;
                                String str = this.mServiceId;
                                r3 = iSemClientImpl2.mProcessId;
                                String processStatus = iSemClientImpl2.getProcessStatus(str, r3, this.mOperationType, this.mOperationId);
                                try {
                                    if (processStatus.equals(SemClient.OnlineProcessStatusValue.PROCESS_STATUS_FINISHED)) {
                                        try {
                                            if (ISemClientImpl.this.mProcessResultCode.equals("0000")) {
                                                LogMgr.log(9, "002");
                                                if (ISemClientImpl.this.mAppletStatus == null) {
                                                    LogMgr.log(2, "704 AppletStatus is not set.");
                                                    throw new SemClientException(300, ObfuscatedMsgUtil.executionPoint());
                                                }
                                                try {
                                                    JSONObject jSONObject = new JSONObject();
                                                    jSONObject.put(ISemClientImpl.KEY_APPLET_STATUS, ISemClientImpl.this.mAppletStatus);
                                                    ISemClientImpl.this.mSharedPrefsUtil.writeAppletStatusCache(this.mSpAppletId, jSONObject.toString());
                                                    LogMgr.log(9, "003 saved AppletStatusCache");
                                                } catch (JSONException e6) {
                                                    LogMgr.log(2, "705 Failed to create spAppletVersionList.");
                                                    throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e6));
                                                }
                                            }
                                        } catch (InterruptedException unused4) {
                                            r3 = 2;
                                            r22 = 0;
                                            LogMgr.log(r3, "706 InterruptedException");
                                            this.mOnSemClientEventListener = r22;
                                            r2 = r22;
                                        }
                                    }
                                    JSONArray jSONArrayCreateSpAppletVersionList2 = createSpAppletVersionList(this.mServiceId, this.mSpAppletId);
                                    if (ISemClientImpl.this.mAppletStatus != null) {
                                        LogMgr.log(9, "004");
                                        JSONArray jSONArrayOptJSONArray2 = ISemClientImpl.this.mAppletStatus.optJSONArray(ISemClientImpl.KEY_APPLET_STATUS_LIST);
                                        jSONArrayOptJSONArray = ISemClientImpl.this.mAppletStatus.optJSONArray("accessAllowedSpAppIdList");
                                        jSONArray = jSONArrayOptJSONArray2;
                                    } else {
                                        jSONArray = null;
                                        jSONArrayOptJSONArray = null;
                                    }
                                    try {
                                        LogMgr.performanceIn("API", "OnGetAppletStatusListener", "onFinished");
                                        this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED, processStatus, ISemClientImpl.this.mProcessResultCode, ISemClientImpl.this.mProcessResultDetailCode, ISemClientImpl.this.mProcessResultMessageString, jSONArrayCreateSpAppletVersionList2, jSONArray, jSONArrayOptJSONArray));
                                        z = false;
                                        z2 = false;
                                        try {
                                            try {
                                                this.mOnSemClientEventListener = null;
                                            } catch (Exception e7) {
                                                e = e7;
                                                LogMgr.log(1, "801 Exception occurred. e=" + e);
                                                z2 = z;
                                            }
                                        } catch (Throwable th3) {
                                            th = th3;
                                            LogMgr.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
                                            throw th;
                                        }
                                    } catch (Exception e8) {
                                        e = e8;
                                        z = false;
                                    } catch (Throwable th4) {
                                        th = th4;
                                        LogMgr.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
                                        throw th;
                                    }
                                    LogMgr.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
                                    r2 = z2;
                                } catch (InterruptedException unused5) {
                                }
                            } catch (SemClientException e9) {
                                e = e9;
                                r22 = 0;
                                try {
                                    try {
                                        int errorCode = e.getErrorCode();
                                        String additionalErrorInfo = e.getAdditionalErrorInfo();
                                        String message = e.getMessage();
                                        LogMgr.performanceIn("API", "OnGetAppletStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, " errorCode[" + errorCode + "] additionalErrorInfo[" + additionalErrorInfo + "] message[" + message + "]");
                                        LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.GET_APPLET_STATUS, errorCode);
                                        this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_ERROR, errorCode, additionalErrorInfo, message));
                                        this.mOnSemClientEventListener = r22;
                                    } catch (Exception e10) {
                                        LogMgr.log(1, "802 Exception occurred. e=" + e10);
                                    }
                                } finally {
                                    LogMgr.performanceOut("API", "OnGetAppletStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                                }
                            } catch (InterruptedException unused6) {
                                r22 = 0;
                                r3 = 2;
                            } catch (Throwable th5) {
                                th = th5;
                                r22 = 0;
                                ISemClientImpl.this.mGetAppletStatusThread = r22;
                                throw th;
                            }
                            ISemClientImpl.this.mGetAppletStatusThread = r2;
                            LogMgr.log(9, "999");
                            return;
                            r3 = 2;
                            LogMgr.log(r3, "706 InterruptedException");
                            this.mOnSemClientEventListener = r22;
                            r2 = r22;
                            ISemClientImpl.this.mGetAppletStatusThread = r2;
                            LogMgr.log(9, "999");
                            return;
                        } catch (SemClientException e11) {
                            e = e11;
                        } catch (JSONException e12) {
                            e = e12;
                            i2 = 2;
                        }
                    } else {
                        i = 900;
                        try {
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        } catch (JSONException unused7) {
                        }
                    }
                } catch (Throwable th6) {
                    th = th6;
                    ISemClientImpl.this.mGetAppletStatusThread = r22;
                    throw th;
                }
            } catch (JSONException unused8) {
                i = 900;
            }
            throw new SemClientException(i, ObfuscatedMsgUtil.executionPoint());
        }

        private JSONArray createSpAppletVersionList(String str, String str2) throws SemClientException {
            LogMgr.log(8, "000");
            try {
                JSONArray jSONArray = new JSONArray(ISemClientImpl.this.mSharedPrefsUtil.readSpAppletInfoList());
                JSONArray jSONArray2 = new JSONArray();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String string = jSONObject.getString("serviceId");
                    String string2 = jSONObject.getString("spAppletId");
                    if (string.equals(str) && string2.equals(str2)) {
                        LogMgr.log(9, "001 match Service ID and SP Applet ID at [" + i + "]");
                        jSONArray2.put(jSONObject.getString("spAppletVersion"));
                    }
                }
                LogMgr.log(8, "999");
                return jSONArray2;
            } catch (Exception e) {
                LogMgr.log(2, "700 Failed to create spAppletVersionList.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
            }
        }

        private JSONArray getAppletStatusList(String str) throws SemClientException {
            LogMgr.log(8, "000");
            String appletStatusCache = ISemClientImpl.this.mSharedPrefsUtil.readAppletStatusCache(str);
            if (appletStatusCache == null) {
                LogMgr.log(2, "700 Failed to read appletStatusCache.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            try {
                JSONArray jSONArrayOptJSONArray = new JSONObject(appletStatusCache).getJSONObject(ISemClientImpl.KEY_APPLET_STATUS).optJSONArray(ISemClientImpl.KEY_APPLET_STATUS_LIST);
                LogMgr.log(8, "999");
                return jSONArrayOptJSONArray;
            } catch (Exception e) {
                LogMgr.log(2, "701 Failed to get appletStatusList.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
            }
        }

        private JSONArray getAccessAllowedSpAppIdList(String str) throws SemClientException {
            LogMgr.log(8, "000");
            String appletStatusCache = ISemClientImpl.this.mSharedPrefsUtil.readAppletStatusCache(str);
            if (appletStatusCache == null) {
                LogMgr.log(2, "700 Failed to read appletStatusCache.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            try {
                JSONArray jSONArrayOptJSONArray = new JSONObject(appletStatusCache).getJSONObject(ISemClientImpl.KEY_APPLET_STATUS).optJSONArray("accessAllowedSpAppIdList");
                LogMgr.log(8, "999");
                return jSONArrayOptJSONArray;
            } catch (Exception e) {
                LogMgr.log(2, "701 Failed to get accessAllowedSpAppIdList.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint(e));
            }
        }

        private void getAppletStatus(String str, String str2, String str3, String str4, String str5) throws InterruptedException, SemClientException {
            LogMgr.log(8, "000");
            ISemClientImpl.this.mGetAppletStatusLatch = new CountDownLatch(1);
            InternalSemClientEventListener internalSemClientEventListener = new InternalSemClientEventListener();
            internalSemClientEventListener.setCountDownLatch(ISemClientImpl.this.mGetAppletStatusLatch);
            SemClientResultInfo semClientResultInfoDoGetAppletStatus = doGetAppletStatus(str, str2, str3, str4, str5, internalSemClientEventListener);
            if (semClientResultInfoDoGetAppletStatus.mExceptionType != 0) {
                LogMgr.log(2, "700 Failed to exec getAppletStatus. message:" + semClientResultInfoDoGetAppletStatus.getMessage() + " code:" + semClientResultInfoDoGetAppletStatus.getErrorCode());
                throw new SemClientException(900, semClientResultInfoDoGetAppletStatus.getMessage());
            }
            LogMgr.log(9, "001 GetAppletStatusLatch.await()");
            ISemClientImpl.this.mGetAppletStatusLatch.await();
            if (!internalSemClientEventListener.isSuccessToGetAppletStatus()) {
                LogMgr.log(2, "701 Failed to exec getAppletStatus. message:" + internalSemClientEventListener.getMsg() + " code:" + internalSemClientEventListener.getErrCode());
                throw new SemClientException(internalSemClientEventListener.getErrCode(), internalSemClientEventListener.getMsg());
            }
            LogMgr.log(8, "999");
        }

        private SemClientResultInfo doGetAppletStatus(String str, String str2, String str3, String str4, String str5, ISemClientEventListener iSemClientEventListener) {
            LogMgr.performanceIn("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
            LogMgr.log(5, "000");
            try {
                String clientId = ISemClientImpl.this.mSharedPrefsUtil.readClientId();
                if (clientId == null) {
                    clientId = SwsParamCreator.createClientId();
                    ISemClientImpl.this.mSharedPrefsUtil.writeClientId(clientId);
                }
                ISemClientImpl.this.mDataManager.setClientId(clientId);
                ISemClientImpl.this.mSwsClientFacade.start(ISemClientImpl.this.new OnFinishListener(iSemClientEventListener), ISemClientImpl.this.mDataManager);
                ISemClientImpl.this.mSwsClientFacade.getAppletStatus(str, str2, str3, str4, str5);
                LogMgr.log(5, "999");
                return new SemClientResultInfo();
            } catch (SemClientException e) {
                LogMgr.log(2, "700 : catch SemClientException exceptionType=[1] message=[" + e.getMessage());
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e), e.getErrorCode());
            } catch (Exception e2) {
                LogMgr.log(2, "701 : Exception occurred. e[" + e2 + "]");
                LogMgr.printStackTrace(8, e2);
                return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(e2), 900);
            } finally {
                LogMgr.performanceOut("API", "ISemClientImpl", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
            }
        }
    }

    public void checkConnected() throws IllegalStateException {
        LogMgr.log(5, "000");
        try {
            checkPidUid();
            checkConnecting();
            LogMgr.log(5, "999");
        } catch (IllegalStateException unused) {
            LogMgr.log(2, "700 IllegalStateException Error.");
            throw new IllegalStateException(SemClientConst.EXC_ILLEGAL_STATE_NOT_CONNECTED);
        }
    }

    public void checkPidUid() throws IllegalStateException {
        LogMgr.log(6, "000");
        checkPidUid(Binder.getCallingPid(), Binder.getCallingUid());
        LogMgr.log(6, "999");
    }

    public void checkPidUid(int i, int i2) throws IllegalStateException {
        LogMgr.log(9, "000 pid=[" + i + "] uid=%[" + i2 + "]");
        SemClientAppInfo semClientAppInfo = this.mConnectedApp;
        if (semClientAppInfo == null) {
            LogMgr.log(1, "800 mConnectedApp == null");
            throw new IllegalStateException(SemClientConst.EXC_ILLEGAL_STATE_NOT_CONNECTED);
        }
        if (semClientAppInfo.getPid() != i || this.mConnectedApp.getUid() != i2) {
            LogMgr.log(1, "801 pid=[" + i + "] uid=[" + i2 + "]mConnectedApp.getPID()=[" + this.mConnectedApp.getPid() + "]mConnectedApp.getUID()=[" + this.mConnectedApp.getUid() + "]");
            throw new IllegalStateException(SemClientConst.EXC_ILLEGAL_STATE_NOT_CONNECTED);
        }
        LogMgr.log(9, "999");
    }

    private void checkConnecting() throws IllegalStateException {
        boolean z;
        LogMgr.log(9, "000");
        synchronized (this) {
            z = this.mConnectWorker != null;
        }
        if (z) {
            LogMgr.log(2, "700 activateWorker is not null.");
            throw new IllegalStateException(SemClientConst.EXC_ILLEGAL_STATE_CURRENTLY_CONNECTING);
        }
        LogMgr.log(9, "999");
    }

    private SemClientResultInfo preCheckToAccessContactlessInterface(String str, SemClientExternalLogConst.SemcApi semcApi) {
        LogMgr.log(9, "000");
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 Param Error. aid is null.");
            LogMgr.exLogSemClientException(semcApi, 900);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 900);
        }
        try {
            checkConnected();
            this.mSwsClientFacade.checkNotRunningTask();
            SemClientResultInfo semClientResultInfoCheckPermissionWithAid = checkPermissionWithAid(str, semcApi);
            if (semClientResultInfoCheckPermissionWithAid.getExceptionType() != 0) {
                LogMgr.log(2, "AID(=" + str + ") is not allowed ");
                return semClientResultInfoCheckPermissionWithAid;
            }
            LogMgr.log(9, "999");
            return new SemClientResultInfo();
        } catch (IllegalStateException e) {
            LogMgr.log(2, "703 catch IllegalStateException exceptionType=[33] message=[" + e.getMessage());
            LogMgr.exLogException(semcApi, e);
            return new SemClientResultInfo(33, e.getMessage());
        }
    }

    private SemClientResultInfo checkPermissionWithAid(String str, SemClientExternalLogConst.SemcApi semcApi) {
        LogMgr.log(9, "000");
        if (str == null || str.isEmpty()) {
            LogMgr.log(2, "700 aid is null.");
            LogMgr.exLogSemClientException(semcApi, 900);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 900);
        }
        try {
            Iterator<String> it = this.mDataManager.getCallerSpAppInfo().mAidListForCrsOperation.iterator();
            while (it.hasNext()) {
                if (str.equalsIgnoreCase(it.next())) {
                    LogMgr.log(9, "999");
                    return new SemClientResultInfo();
                }
            }
            LogMgr.log(1, "802 Not permitted aid.");
            LogMgr.exLogSemClientException(semcApi, 100);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 100);
        } catch (Exception e) {
            LogMgr.log(1, "801 " + e.getClass().getSimpleName());
            LogMgr.printStackTrace(9, e);
            LogMgr.exLogSemClientException(semcApi, 900);
            return new SemClientResultInfo(1, ObfuscatedMsgUtil.executionPoint(), 900);
        }
    }

    private static class SemClientAppInfo {
        static final int INVALID_PID_UID_VAL = -1;
        String mCallerPackageName;
        private boolean mIsAppInfoExist;
        boolean mIsCalledFromInternal;
        int mPid;
        String mSpAppId;
        int mUid;

        private SemClientAppInfo() {
            this.mIsAppInfoExist = false;
            this.mIsCalledFromInternal = false;
        }

        synchronized int getUid() {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            return this.mUid;
        }

        synchronized boolean isAppInfoExist() {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999 mIsAppInfoExist[" + this.mIsAppInfoExist + "]");
            return this.mIsAppInfoExist;
        }

        synchronized void setAppInfoExist() {
            LogMgr.log(9, "000");
            this.mIsAppInfoExist = true;
            LogMgr.log(9, "999");
        }

        synchronized void clearConnectedAppInfo() {
            LogMgr.log(9, "000");
            this.mIsAppInfoExist = false;
            this.mPid = -1;
            this.mUid = -1;
            this.mIsCalledFromInternal = false;
            LogMgr.log(9, "999");
        }

        synchronized void setUid(int i) {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            this.mUid = i;
        }

        synchronized int getPid() {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            return this.mPid;
        }

        synchronized void setPid(int i) {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            this.mPid = i;
        }

        String getCallerPackageName() {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            return this.mCallerPackageName;
        }

        void setCallerPackageName(String str) {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            this.mCallerPackageName = str;
        }

        void setIsCalledFromInternal(boolean z) {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            this.mIsCalledFromInternal = z;
        }

        boolean getIsCalledFromInternal() {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            return this.mIsCalledFromInternal;
        }

        String getSpAppId() {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            return this.mSpAppId;
        }

        void setSpAppId(String str) {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            this.mSpAppId = str;
        }
    }

    private void checkCallerPackageName(String str) throws SemClientException {
        LogMgr.log(9, "000");
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(Binder.getCallingUid());
        if (packagesForUid == null || packagesForUid.length == 0) {
            LogMgr.log(2, "700 packageNameList is null or empty.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        for (String str2 : packagesForUid) {
            if (str.equals(str2)) {
                LogMgr.log(9, "999");
                return;
            }
        }
        LogMgr.log(2, "701 packageNameList does not contain packageName.");
        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
    }

    private synchronized boolean registerBinder(IBinder iBinder) {
        LogMgr.log(5, "000");
        if (iBinder == null) {
            return false;
        }
        try {
            this.mDeathRecipient = new LocalDeathRecipient(iBinder);
            LogMgr.log(5, "999");
            return true;
        } catch (RemoteException e) {
            LogMgr.log(2, "700binder.linkToDeath Failed. " + e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void unregisterBinder() {
        IBinder binder;
        LogMgr.log(5, "000");
        LocalDeathRecipient localDeathRecipient = this.mDeathRecipient;
        if (localDeathRecipient != null && (binder = localDeathRecipient.getBinder()) != null) {
            binder.unlinkToDeath(this.mDeathRecipient, 0);
        }
        this.mDeathRecipient = null;
        LogMgr.log(5, "999");
    }

    private class LocalDeathRecipient implements IBinder.DeathRecipient {
        IBinder mBinder;

        LocalDeathRecipient(IBinder iBinder) throws RemoteException {
            LogMgr.log(5, "000");
            iBinder.linkToDeath(this, 0);
            this.mBinder = iBinder;
            LogMgr.log(5, "999");
        }

        IBinder getBinder() {
            LogMgr.log(9, "000");
            LogMgr.log(9, "999");
            return this.mBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this) {
                ISemClientImpl.this.mDeathRecipient = null;
            }
            ISemClientImpl.this.mSemListener.semCancel();
            LogMgr.log(5, "999");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class DisConnectThread extends Thread {
        private DisConnectThread() {
        }

        /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET, INVOKE]}, finally: {[IGET] complete} */
        /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [4775=4, 4776=4, 4777=4, 4779=4, 4780=4, 4781=4, 4783=6] */
        /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            CountDownLatch andSetTaskInterruptedLatch;
            LogMgr.log(9, "000");
            try {
                try {
                    try {
                        if (ISemClientImpl.this.mSemChipHolder != null && ISemClientImpl.this.mSemChipHolder.isConnected()) {
                            ISemClientImpl.this.mDisConnectChipHolderCancelLatch = new CountDownLatch(1);
                            ISemClientImpl.this.mSemChipHolder.cancel(new SemChipHolder.OnCanceledListener() { // from class: com.felicanetworks.semc.ISemClientImpl$DisConnectThread$$ExternalSyntheticLambda0
                                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
                                @Override // com.felicanetworks.semc.SemChipHolder.OnCanceledListener
                                public final void onCanceled() {
                                    this.f$0.m423xa4ecff20();
                                }
                            });
                            LogMgr.log(9, "002 DisConnectChipHolderCancelLatch.await()");
                            ISemClientImpl.this.mDisConnectChipHolderCancelLatch.await();
                            ISemClientImpl.this.mDisConnectChipHolderCancelLatch = null;
                            ISemClientImpl.this.mSemChipHolder.removeOnCanceledListener();
                        }
                        ISemClientImpl.this.mSwsClientFacade.stop();
                        synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                            andSetTaskInterruptedLatch = (!ISemClientImpl.this.mIsRunningConnectThread.get() && !ISemClientImpl.this.mIsRunningStartTsmSequence.get() && ISemClientImpl.this.mInstallAppletThread == null && ISemClientImpl.this.mUpgradeAppletThread == null && ISemClientImpl.this.mDeleteAppletThread == null && ISemClientImpl.this.mGetAppletStatusThread == null) ? null : ISemClientImpl.this.mTaskInterruptedLatchManager.getAndSetTaskInterruptedLatch();
                        }
                        if (andSetTaskInterruptedLatch != null) {
                            try {
                                LogMgr.log(9, "003 TaskInterruptedLatch.await()");
                                andSetTaskInterruptedLatch.await(10L, TimeUnit.SECONDS);
                            } catch (Exception unused) {
                                LogMgr.log(2, "701 Exception occurred.");
                            }
                        }
                        ISemClientImpl.this.mSwsClientFacade.finish();
                        synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                            ISemClientImpl.this.mTaskInterruptedLatchManager.clearTaskInterruptedLatch();
                            ISemClientImpl.this.mIsRunningConnectThread.compareAndSet(true, false);
                            ISemClientImpl.this.mIsRunningStartTsmSequence.compareAndSet(true, false);
                        }
                        if (ISemClientImpl.this.mConnectedApp != null) {
                            try {
                                ISemClientImpl.this.mConnectedApp.clearConnectedAppInfo();
                            } catch (Exception e) {
                                LogMgr.log(2, "702" + e.getClass().getSimpleName());
                                LogMgr.printStackTrace(8, e);
                            }
                            ISemClientImpl.this.mConnectedApp = null;
                        }
                        if (ISemClientImpl.this.mSemChipHolder != null) {
                            try {
                                ISemClientImpl.this.mSemChipHolder.discard();
                            } catch (Exception e2) {
                                LogMgr.log(2, "704" + e2.getClass().getSimpleName());
                                LogMgr.printStackTrace(8, e2);
                            }
                            ISemClientImpl.this.mSemChipHolder = null;
                        }
                        try {
                            if (ISemClientImpl.this.mConnectWorker != null) {
                                LogMgr.log(8, "003 mConnectWorker is not null");
                                ISemClientImpl.this.mConnectWorker.interrupt();
                                ISemClientImpl.this.mConnectWorker = null;
                            }
                            if (ISemClientImpl.this.mInstallAppletThread != null) {
                                LogMgr.log(8, "004mInstallAppletThread is not null");
                                ISemClientImpl.this.mInstallAppletThread.interrupt();
                            }
                            if (ISemClientImpl.this.mUpgradeAppletThread != null) {
                                LogMgr.log(8, "005mUpgradeAppletThread is not null");
                                ISemClientImpl.this.mUpgradeAppletThread.interrupt();
                            }
                            if (ISemClientImpl.this.mDeleteAppletThread != null) {
                                LogMgr.log(8, "006mDeleteAppletThread is not null");
                                ISemClientImpl.this.mDeleteAppletThread.interrupt();
                            }
                            if (ISemClientImpl.this.mGetAppletStatusThread != null) {
                                LogMgr.log(8, "007mGetAppletStatusThread is not null");
                                ISemClientImpl.this.mGetAppletStatusThread.interrupt();
                            }
                        } catch (Exception e3) {
                            LogMgr.log(2, "703" + e3.getClass().getSimpleName());
                            LogMgr.printStackTrace(8, e3);
                        }
                        ISemClientImpl.this.unregisterBinder();
                        SettingInfo.initializeValue();
                        LogMgr.log(9, "004 LogMgr.setup()");
                        LogMgr.setup(1, false, false);
                        synchronized (ISemClientImpl.this) {
                            ISemClientImpl.this.mDisConnectWorker = null;
                            if (ISemClientImpl.this.mWaitDisconnectFinishedLatch != null) {
                                ISemClientImpl.this.mWaitDisconnectFinishedLatch.countDown();
                                ISemClientImpl.this.mWaitDisconnectFinishedLatch = null;
                                LogMgr.log(8, "005 Disconnect thread finished.");
                            }
                        }
                    } catch (Throwable th) {
                        synchronized (ISemClientImpl.this) {
                            ISemClientImpl.this.mDisConnectWorker = null;
                            if (ISemClientImpl.this.mWaitDisconnectFinishedLatch != null) {
                                ISemClientImpl.this.mWaitDisconnectFinishedLatch.countDown();
                                ISemClientImpl.this.mWaitDisconnectFinishedLatch = null;
                                LogMgr.log(8, "005 Disconnect thread finished.");
                            }
                            throw th;
                        }
                    }
                } catch (InterruptedException unused2) {
                    LogMgr.log(2, "705 InterruptedException occurred.");
                    synchronized (ISemClientImpl.this) {
                        ISemClientImpl.this.mDisConnectWorker = null;
                        if (ISemClientImpl.this.mWaitDisconnectFinishedLatch != null) {
                            ISemClientImpl.this.mWaitDisconnectFinishedLatch.countDown();
                            ISemClientImpl.this.mWaitDisconnectFinishedLatch = null;
                            LogMgr.log(8, "005 Disconnect thread finished.");
                        }
                    }
                }
            } catch (Exception unused3) {
                LogMgr.log(2, "706 Exception occurred.");
                synchronized (ISemClientImpl.this) {
                    ISemClientImpl.this.mDisConnectWorker = null;
                    if (ISemClientImpl.this.mWaitDisconnectFinishedLatch != null) {
                        ISemClientImpl.this.mWaitDisconnectFinishedLatch.countDown();
                        ISemClientImpl.this.mWaitDisconnectFinishedLatch = null;
                        LogMgr.log(8, "005 Disconnect thread finished.");
                    }
                }
            }
            LogMgr.log(9, "999");
        }

        /* JADX INFO: renamed from: lambda$run$0$com-felicanetworks-semc-ISemClientImpl$DisConnectThread, reason: not valid java name */
        /* synthetic */ void m423xa4ecff20() {
            LogMgr.log(9, "001");
            try {
                if (ISemClientImpl.this.mDisConnectChipHolderCancelLatch != null) {
                    ISemClientImpl.this.mDisConnectChipHolderCancelLatch.countDown();
                    LogMgr.log(8, "Exec mDisConnectChipHolderCancelLatch.countDown()");
                } else {
                    LogMgr.log(1, "800 IllegalState mDisConnectChipHolderCancelLatch is null.");
                }
            } catch (Exception unused) {
                LogMgr.log(1, "801 Failed to exec countDown().");
            }
            LogMgr.log(9, "999");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ConnectThread extends Thread {
        private static final int PROFILE_ID_ELEMENT_NUMBER = 5;
        private static final String PROFILE_ID_ENV_NAME_PATTERN = "^[\\w]{4}$";
        private static final String PROFILE_ID_HEADER = "SCPF";
        private static final int PROFILE_ID_INDEX_ENVIRONMENT = 1;
        private static final int PROFILE_ID_INDEX_HEADER = 0;
        private static final int PROFILE_ID_INDEX_RANDOM = 4;
        private static final int PROFILE_ID_INDEX_SEQUENCE = 3;
        private static final int PROFILE_ID_INDEX_SP_APP_ID = 2;
        private static final String PROFILE_ID_RANDOM_PATTERN = "^[0-9A-F]{32}$";
        private static final String PROFILE_ID_SEPARATOR = "_";
        private static final String PROFILE_ID_SEQ_PATTERN = "^[0-9]{8}$";
        private static final String PROFILE_ID_SP_APP_ID_PATTERN = "SPA.....";
        private static final String PROFILE_NAME_PATTERN = "^SCPF_.*\\.jws$";
        private FilenameFilter filter = new FilenameFilter() { // from class: com.felicanetworks.semc.ISemClientImpl.ConnectThread.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str) {
                LogMgr.log(9, "000");
                if (Pattern.compile(ConnectThread.PROFILE_NAME_PATTERN).matcher(str).find()) {
                    LogMgr.log(9, "998 ret=true");
                    return true;
                }
                LogMgr.log(9, "999 ret=false");
                return false;
            }
        };
        public boolean mCalledFromSpApp;
        public int mMode;
        private ISemClientEventListener mOnConnectedListener;

        ConnectThread(boolean z, int i, ISemClientEventListener iSemClientEventListener) {
            LogMgr.log(9, "000");
            this.mCalledFromSpApp = !z;
            this.mMode = i;
            this.mOnConnectedListener = ISemClientImpl.this.new SemClientEventListenerWrapper(iSemClientEventListener);
            LogMgr.log(9, "999");
        }

        void checkInterrupted() throws InterruptedException {
            LogMgr.log(9, "000");
            if (ISemClientImpl.this.mIsConnectInterrupted.get()) {
                throw new InterruptedException(ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(9, "999");
        }

        /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [5348=5, 5349=5, 5350=5, 5351=5, 5352=5, 5354=5, 5355=10, 5358=5, 5359=5, 5360=10, 5361=5, 5362=5, 5364=5, 5365=5, 5366=10, 5367=5, 5368=5, 5369=5, 5371=5, 5372=5, 5373=5, 5376=10, 5377=5, 5381=5, 5383=5, 5385=5, 5387=10, 5388=5, 5392=5, 5393=5, 5394=5, 5398=5, 5400=5, 5404=5, 5406=5, 5407=7] */
        /* JADX DEBUG: Duplicate block (B:285:0x07ff) to fix multi-entry loop: BACK_EDGE: B:285:0x07ff -> B:286:0x0800 */
        /* JADX DEBUG: Duplicate block (B:334:0x094c) to fix multi-entry loop: BACK_EDGE: B:334:0x094c -> B:335:0x094d */
        /* JADX WARN: Removed duplicated region for block: B:145:0x047e  */
        /* JADX WARN: Removed duplicated region for block: B:156:0x0494  */
        /* JADX WARN: Removed duplicated region for block: B:436:0x07d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:285:0x07ff
            	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
            	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
            */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            /*
                r16 = this;
                r1 = r16
                java.lang.String r0 = "000"
                r2 = 9
                com.felicanetworks.semc.util.LogMgr.log(r2, r0)
                r4 = 201(0xc9, float:2.82E-43)
                r5 = 2
                r6 = 0
                r7 = 1
                r8 = 0
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
                java.util.concurrent.CountDownLatch r0 = com.felicanetworks.semc.ISemClientImpl.access$3700(r0)     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
                if (r0 == 0) goto L25
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L1e java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.SemClientException -> L925
                java.util.concurrent.CountDownLatch r0 = com.felicanetworks.semc.ISemClientImpl.access$3700(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L1e java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.SemClientException -> L925
                goto L26
            L1e:
                r0 = move-exception
                r6 = r4
                r9 = r8
            L21:
                r4 = 200(0xc8, float:2.8E-43)
                goto L7bd
            L25:
                r0 = r8
            L26:
                if (r0 == 0) goto L31
                java.lang.String r9 = "001 WaitDisconnectFinishedLatch.await()"
                com.felicanetworks.semc.util.LogMgr.log(r2, r9)     // Catch: com.felicanetworks.semc.omapi.GpException -> L1e java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.SemClientException -> L925
                r0.await()     // Catch: com.felicanetworks.semc.omapi.GpException -> L1e java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.SemClientException -> L925
                goto L36
            L31:
                java.lang.String r0 = "002 Not wait for disconnect."
                com.felicanetworks.semc.util.LogMgr.log(r2, r0)     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
            L36:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
                java.util.concurrent.atomic.AtomicBoolean r0 = com.felicanetworks.semc.ISemClientImpl.access$3200(r0)     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
                r0.compareAndSet(r6, r7)     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
                com.felicanetworks.semc.SemClientNotifyEventInfo r9 = new com.felicanetworks.semc.SemClientNotifyEventInfo     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
                java.lang.String r0 = "OnConnectedListener#onConnected"
                r9.<init>(r0)     // Catch: java.lang.Throwable -> L69d java.lang.InterruptedException -> L6a2 com.felicanetworks.semc.omapi.GpException -> L7b8 com.felicanetworks.semc.SemClientException -> L925
                r1.checkInterrupted()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r0 = "003 Settings.Secure.getString(ANDROID_ID)"
                r10 = 8
                com.felicanetworks.semc.util.LogMgr.log(r10, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r0 = com.felicanetworks.semc.ISemClientImpl.access$500(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.ContentResolver r0 = r0.getContentResolver()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r10 = "android_id"
                java.lang.String r0 = android.provider.Settings.Secure.getString(r0, r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r10 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r10 = com.felicanetworks.semc.ISemClientImpl.access$300(r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r10 = r10.readAndroidId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r1.checkInterrupted()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r11 = 900(0x384, float:1.261E-42)
                if (r0 == 0) goto L681
                boolean r12 = r0.isEmpty()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r12 != 0) goto L681
                if (r10 == 0) goto L86
                boolean r10 = r0.equals(r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r10 != 0) goto L80
                goto L86
            L80:
                java.lang.String r0 = "005 saved ANDROID_ID and ANDROID_ID are equals."
                com.felicanetworks.semc.util.LogMgr.log(r2, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L9d
            L86:
                java.lang.String r10 = "004 saved ANDROID_ID is null or ANDROID_ID is changed."
                com.felicanetworks.semc.util.LogMgr.log(r2, r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r10 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r10 = com.felicanetworks.semc.ISemClientImpl.access$300(r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r10.clearAllData()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r10 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r10 = com.felicanetworks.semc.ISemClientImpl.access$300(r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r10.writeAndroidId(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L9d:
                r1.loadProfileData()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r1.checkInterrupted()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r0 = com.felicanetworks.semc.ISemClientImpl.access$3900(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r10 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r10 = com.felicanetworks.semc.ISemClientImpl.access$500(r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r10 = r10.getPackageName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r12 = 4
                if (r0 == 0) goto Lfc
                java.lang.String r13 = com.felicanetworks.semc.config.AccessConfig.getGpEseReaderName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto Led
                com.felicanetworks.semc.ISemClientImpl r14 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r14 = com.felicanetworks.semc.ISemClientImpl.access$400(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r14.setSeReaderName(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = com.felicanetworks.semc.config.AccessConfig.getChipType()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r13.setChipType(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = com.felicanetworks.semc.config.AccessConfig.getChipIssuerId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r13.setSepId(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = com.felicanetworks.semc.config.AccessConfig.getCareerIdentifyCode()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r13.setCarrierId(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L15c
            Led:
                java.lang.String r0 = "801 Failed to get GpEseReaderName from common.cfg."
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r10 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r4, r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            Lfc:
                if (r0 != 0) goto L15c
                int r13 = r1.mMode     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r13 = r13 & r12
                if (r13 != r12) goto L15c
                java.lang.String r13 = "com.felicanetworks.semcapp"
                boolean r13 = r13.equals(r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto L15c
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = "1"
                r13.setChipType(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = "000068"
                r13.setSepId(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = "100008"
                r13.setCarrierId(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r13 = com.felicanetworks.semc.ISemClientImpl.access$300(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r13 = r13.readReaderName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r14 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r14 = com.felicanetworks.semc.ISemClientImpl.access$400(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r14.setSeReaderName(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r14 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r14 = com.felicanetworks.semc.ISemClientImpl.access$300(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                long r14 = r14.readSeIdSaveDate()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto L151
                boolean r13 = com.felicanetworks.semc.util.DateUtil.isTodayInJapan(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 != 0) goto L15c
            L151:
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = ""
                r13.setSeReaderName(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L15c:
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r13 = r13.getSeReaderName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L696 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto L66c
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r13 = r13.getChipType()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto L652
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r13 = r13.getSepId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto L63f
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r13 = r13.getCarrierId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto L62c
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r13 = r13.getChipType()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = "1"
                boolean r13 = r13.equals(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r13 == 0) goto L619
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = android.os.Build.VERSION.RELEASE     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r13.setAndroidOsVersion(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r14 = android.os.Build.MODEL     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r13.setDeviceName(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r13 = com.felicanetworks.semc.ISemClientImpl.access$400(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r14 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r14 = com.felicanetworks.semc.ISemClientImpl.access$500(r14)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.res.Resources r14 = r14.getResources()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r15 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r15 = com.felicanetworks.semc.ISemClientImpl.access$500(r15)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.res.Resources r15 = r15.getResources()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r4 = "sem_client_version"
                java.lang.String r3 = "string"
                com.felicanetworks.semc.ISemClientImpl r6 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r6 = com.felicanetworks.semc.ISemClientImpl.access$500(r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r6 = r6.getPackageName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                int r3 = r15.getIdentifier(r4, r3, r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = r14.getString(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r13.setSemClientVersion(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r3 = com.felicanetworks.semc.ISemClientImpl.access$400(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r4 = com.felicanetworks.semc.ISemClientImpl.access$500(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.res.Resources r4 = r4.getResources()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r6 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r6 = com.felicanetworks.semc.ISemClientImpl.access$500(r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.res.Resources r6 = r6.getResources()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r13 = "additionalInformation"
                java.lang.String r14 = "string"
                com.felicanetworks.semc.ISemClientImpl r15 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r15 = com.felicanetworks.semc.ISemClientImpl.access$500(r15)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r15 = r15.getPackageName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                int r6 = r6.getIdentifier(r13, r14, r15)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r4 = r4.getString(r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r3.setSemClientVersionAdditionalInfo(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r3 = com.felicanetworks.semc.ISemClientImpl.access$400(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                boolean r4 = r1.mCalledFromSpApp     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4 = r4 ^ r7
                r3.setIsCalledFromSemcBackground(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r1.checkInterrupted()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$4000(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.sws.json.ClientControlInfoJsonArray r3 = com.felicanetworks.semc.ISemClientImpl.access$4100(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L23b
                java.lang.String r3 = r3.getMinimumRequiredVersion()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L23c
            L23b:
                r3 = r8
            L23c:
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r6 = com.felicanetworks.semc.ISemClientImpl.access$400(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r6 = r6.getSemClientVersion()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$4200(r4, r3, r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                boolean r3 = r1.mCalledFromSpApp     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L2f3
                java.lang.String r3 = "006"
                com.felicanetworks.semc.util.LogMgr.log(r2, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r3 = 0
            L253:
                if (r3 > r7) goto L28a
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: org.json.JSONException -> L268 com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r4 = com.felicanetworks.semc.ISemClientImpl.access$500(r4)     // Catch: org.json.JSONException -> L268 com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r6 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: org.json.JSONException -> L268 com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl$SemClientAppInfo r6 = com.felicanetworks.semc.ISemClientImpl.access$800(r6)     // Catch: org.json.JSONException -> L268 com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r6 = r6.mCallerPackageName     // Catch: org.json.JSONException -> L268 com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.permit.SignerHashCheckResult r3 = com.felicanetworks.semc.permit.SignerHashChecker.checkSignerHash(r4, r6)     // Catch: org.json.JSONException -> L268 com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L28b
            L268:
                if (r3 >= r7) goto L27b
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r4 = com.felicanetworks.semc.ISemClientImpl.access$300(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4.clearClientConfigInfo()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$4000(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                int r3 = r3 + 1
                goto L253
            L27b:
                java.lang.String r0 = "808 CacheFile was corrupted."
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r11, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L28a:
                r3 = r8
            L28b:
                if (r3 == 0) goto L2e4
                boolean r4 = r3.mResult     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r4 != 0) goto L2b7
                com.felicanetworks.semc.permit.ErrorInfo r0 = r3.mErrorInfo     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 != 0) goto L2a4
                java.lang.String r0 = "701 Failed to exec checkSignerHash."
                com.felicanetworks.semc.util.LogMgr.log(r5, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r11, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L2a4:
                java.lang.String r0 = "702 Failed to exec checkSignerHash."
                com.felicanetworks.semc.util.LogMgr.log(r5, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.permit.ErrorInfo r4 = r3.mErrorInfo     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                int r4 = r4.mErrorType     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.permit.ErrorInfo r3 = r3.mErrorInfo     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = r3.mErrorMessage     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r4, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L2b7:
                com.felicanetworks.semc.permit.SpAppInfo r4 = r3.mSpAppInfo     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r4 == 0) goto L2d5
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r4 = com.felicanetworks.semc.ISemClientImpl.access$400(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.permit.SpAppInfo r6 = r3.mSpAppInfo     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4.setCallerSpAppInfo(r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl$SemClientAppInfo r4 = com.felicanetworks.semc.ISemClientImpl.access$800(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.permit.SpAppInfo r3 = r3.mSpAppInfo     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = r3.mSpAppId     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4.setSpAppId(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L34d
            L2d5:
                java.lang.String r0 = "703 Failed to exec checkSignerHash."
                com.felicanetworks.semc.util.LogMgr.log(r5, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r11, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L2e4:
                java.lang.String r0 = "700 checkSignerHash is null."
                com.felicanetworks.semc.util.LogMgr.log(r5, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r11, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L2f3:
                java.lang.String r3 = "007"
                com.felicanetworks.semc.util.LogMgr.log(r2, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl$SemClientAppInfo r3 = com.felicanetworks.semc.ISemClientImpl.access$800(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = r3.getCallerPackageName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r4 = "com.felicanetworks.mfm.main"
                boolean r3 = r3.equals(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L323
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r3 = com.felicanetworks.semc.ISemClientImpl.access$500(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r4 = "BE:51:DB:F4:FE:C8:9B:D3:28:46:45:7B:13:B7:30:08:76:AF:55:94:D2:87:4D:EE:02:69:04:96:5A:E4:A6:CB"
                com.felicanetworks.semc.ISemClientImpl r6 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl$SemClientAppInfo r6 = com.felicanetworks.semc.ISemClientImpl.access$800(r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r6 = r6.getCallerPackageName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                boolean r3 = com.felicanetworks.semc.util.SignatureUtil.checkAppCertHash(r3, r4, r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L606
                goto L34d
            L323:
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl$SemClientAppInfo r3 = com.felicanetworks.semc.ISemClientImpl.access$800(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = r3.getCallerPackageName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r4 = "com.felicanetworks.semcapp"
                boolean r3 = r3.equals(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L606
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r3 = com.felicanetworks.semc.ISemClientImpl.access$500(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r4 = "BE:51:DB:F4:FE:C8:9B:D3:28:46:45:7B:13:B7:30:08:76:AF:55:94:D2:87:4D:EE:02:69:04:96:5A:E4:A6:CB"
                com.felicanetworks.semc.ISemClientImpl r6 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl$SemClientAppInfo r6 = com.felicanetworks.semc.ISemClientImpl.access$800(r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r6 = r6.getCallerPackageName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                boolean r3 = com.felicanetworks.semc.util.SignatureUtil.checkAppCertHash(r3, r4, r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L606
            L34d:
                r1.checkInterrupted()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.sws.json.ClientControlInfoJsonArray r3 = com.felicanetworks.semc.ISemClientImpl.access$4100(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L35d
                java.lang.String r3 = r3.getMinimumRequiredSdkVersion()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L35e
            L35d:
                r3 = r8
            L35e:
                r4 = 28
                if (r3 == 0) goto L36a
                int r3 = java.lang.Integer.parseInt(r3)     // Catch: java.lang.NumberFormatException -> L36a com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 >= r4) goto L369
                goto L36a
            L369:
                r4 = r3
            L36a:
                int r3 = android.os.Build.VERSION.SDK_INT     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 < r4) goto L5f3
                java.lang.String r3 = "008 OS Version is supported version."
                com.felicanetworks.semc.util.LogMgr.log(r2, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemChipHolder r3 = com.felicanetworks.semc.ISemClientImpl.access$700(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 != 0) goto L395
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemChipHolder r4 = new com.felicanetworks.semc.SemChipHolder     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r6 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r6 = com.felicanetworks.semc.ISemClientImpl.access$400(r6)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r6 = r6.getSeReaderName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r13 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                android.content.Context r13 = com.felicanetworks.semc.ISemClientImpl.access$500(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4.<init>(r6, r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$702(r3, r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L395:
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemChipHolder r3 = com.felicanetworks.semc.ISemClientImpl.access$700(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r3.init()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r1.checkInterrupted()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r3 = com.felicanetworks.semc.ISemClientImpl.access$300(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = r3.readSeId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r4 = com.felicanetworks.semc.ISemClientImpl.access$300(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                long r13 = r4.readSeIdSaveDate()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L3c3
                boolean r3 = r3.isEmpty()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 != 0) goto L3c3
                boolean r3 = com.felicanetworks.semc.util.DateUtil.isTodayInJapan(r13)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 != 0) goto L438
            L3c3:
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemChipHolder r3 = com.felicanetworks.semc.ISemClientImpl.access$700(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = r3.doGetSeId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 == 0) goto L3e8
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r4 = com.felicanetworks.semc.ISemClientImpl.access$300(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r4 = r4.readSeId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                boolean r4 = r3.equals(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r4 != 0) goto L3e8
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r4 = com.felicanetworks.semc.ISemClientImpl.access$300(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4.removeDeviceToken()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L3e8:
                if (r3 == 0) goto L5e2
                boolean r4 = r3.isEmpty()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r4 != 0) goto L5e2
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r4 = com.felicanetworks.semc.ISemClientImpl.access$300(r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4.writeSeId(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 != 0) goto L428
                int r0 = r1.mMode     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0 = r0 & r12
                if (r0 != r12) goto L428
                java.lang.String r0 = "com.felicanetworks.semcapp"
                boolean r0 = r0.equals(r10)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 == 0) goto L428
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.omapi.GpController r0 = r0.getGpController()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r0 = r0.getReaderName()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r3 = com.felicanetworks.semc.ISemClientImpl.access$300(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r3.writeReaderName(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r3 = com.felicanetworks.semc.ISemClientImpl.access$400(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r3.setSeReaderName(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L428:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r0 = com.felicanetworks.semc.ISemClientImpl.access$300(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                long r3 = java.lang.System.currentTimeMillis()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r12 = 1000(0x3e8, double:4.94E-321)
                long r3 = r3 / r12
                r0.writeSeIdSaveDate(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L438:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r0 = com.felicanetworks.semc.ISemClientImpl.access$300(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r0 = r0.readSeId()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 == 0) goto L5d3
                boolean r3 = r0.isEmpty()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 != 0) goto L5d3
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.DataManager r3 = com.felicanetworks.semc.ISemClientImpl.access$400(r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r3.setSeid(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.util.SharedPrefsUtil r0 = com.felicanetworks.semc.ISemClientImpl.access$300(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.deleteOtherSeIdDataFiles()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r1.checkInterrupted()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$4300(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.sws.json.ClientControlInfoJsonArray r0 = com.felicanetworks.semc.ISemClientImpl.access$4100(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 == 0) goto L471
                java.lang.String r3 = r0.getPeriodicWorkStartTimeMin()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L472
            L471:
                r3 = r8
            L472:
                if (r3 == 0) goto L47e
                int r3 = java.lang.Integer.parseInt(r3)     // Catch: java.lang.NumberFormatException -> L47e com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r3 < 0) goto L47e
                r4 = 23
                if (r4 >= r3) goto L47f
            L47e:
                r3 = 0
            L47f:
                if (r0 == 0) goto L486
                java.lang.String r0 = r0.getPeriodicWorkStartTimeMax()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L487
            L486:
                r0 = r8
            L487:
                r4 = 6
                if (r0 == 0) goto L494
                int r0 = java.lang.Integer.parseInt(r0)     // Catch: java.lang.NumberFormatException -> L494 com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 < r7) goto L494
                r6 = 24
                if (r6 >= r0) goto L495
            L494:
                r0 = r4
            L495:
                if (r0 >= r3) goto L499
                r3 = 0
                goto L49a
            L499:
                r4 = r0
            L49a:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                boolean r0 = com.felicanetworks.semc.ISemClientImpl.access$4400(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 == 0) goto L4bd
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                boolean r0 = com.felicanetworks.semc.ISemClientImpl.access$4500(r0, r3, r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                if (r0 == 0) goto L4ca
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$4600(r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.mPeriodicWorkStartTimeMin = r3     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.mPeriodicWorkStartTimeMax = r4     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$4700(r0, r3, r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                goto L4ca
            L4bd:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.mPeriodicWorkStartTimeMin = r3     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.mPeriodicWorkStartTimeMax = r4     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl.access$4700(r0, r3, r4)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L4ca:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4802(r0, r8)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4902(r0, r8)
                com.felicanetworks.semc.ISemClientEventListener r0 = r1.mOnConnectedListener
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this
                monitor-enter(r3)
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L5d0
                com.felicanetworks.semc.ISemClientImpl.access$3402(r4, r8)     // Catch: java.lang.Throwable -> L5d0
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L5d0
                java.util.concurrent.atomic.AtomicBoolean r4 = com.felicanetworks.semc.ISemClientImpl.access$5000(r4)     // Catch: java.lang.Throwable -> L5d0
                r5 = 0
                r4.compareAndSet(r7, r5)     // Catch: java.lang.Throwable -> L5d0
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L5d0
                if (r0 == 0) goto L587
                java.lang.String r3 = r9.getCallbackName()     // Catch: java.lang.Exception -> L59e
                java.lang.String r4 = "OnConnectedListener#onError"
                boolean r3 = r3.equals(r4)     // Catch: java.lang.Exception -> L59e
                if (r3 == 0) goto L55c
                java.lang.String r3 = "API"
                java.lang.String r4 = "OnConnectedListener"
                java.lang.String r5 = r9.getCallbackName()     // Catch: java.lang.Exception -> L59e
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L59e
                r6.<init>()     // Catch: java.lang.Exception -> L59e
                java.lang.String r10 = " errorCode["
                r6.append(r10)     // Catch: java.lang.Exception -> L59e
                int r10 = r9.getErrorCode()     // Catch: java.lang.Exception -> L59e
                r6.append(r10)     // Catch: java.lang.Exception -> L59e
                java.lang.String r10 = "] additionalErrorInfo ["
                r6.append(r10)     // Catch: java.lang.Exception -> L59e
                java.lang.String r10 = r9.getErrorAdditionalInformation()     // Catch: java.lang.Exception -> L59e
                r6.append(r10)     // Catch: java.lang.Exception -> L59e
                java.lang.String r10 = "] message["
                r6.append(r10)     // Catch: java.lang.Exception -> L59e
                java.lang.String r10 = r9.getErrorMessage()     // Catch: java.lang.Exception -> L59e
                r6.append(r10)     // Catch: java.lang.Exception -> L59e
                java.lang.String r10 = "]"
                r6.append(r10)     // Catch: java.lang.Exception -> L59e
                java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r4, r5, r6)     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.SemClientExternalLogConst$SemcApi r3 = com.felicanetworks.semc.SemClientExternalLogConst.SemcApi.CONNECT     // Catch: java.lang.Exception -> L59e
                int r4 = r9.getErrorCode()     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.util.LogMgr.exLogOnError(r3, r4)     // Catch: java.lang.Exception -> L59e
                r0.onEventNotify(r9)     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl.access$3500(r0)     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L59e
                if (r0 == 0) goto L57b
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L59e
                r0.discard()     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> L59e
                goto L57b
            L55c:
                java.lang.String r3 = "API"
                java.lang.String r4 = "OnConnectedListener"
                java.lang.String r5 = r9.getCallbackName()     // Catch: java.lang.Exception -> L59e
                java.lang.String r6 = " succeeded"
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r4, r5, r6)     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.SemChipHolder r3 = com.felicanetworks.semc.ISemClientImpl.access$700(r3)     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.omapi.GpController r3 = r3.getGpController()     // Catch: java.lang.Exception -> L59e
                if (r3 == 0) goto L578
                r3.closeChannel()     // Catch: java.lang.Exception -> L59e
            L578:
                r0.onEventNotify(r9)     // Catch: java.lang.Exception -> L59e
            L57b:
                java.lang.String r0 = "API"
                java.lang.String r3 = "OnConnectedListener"
                java.lang.String r4 = r9.getCallbackName()     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.util.LogMgr.performanceOut(r0, r3, r4)     // Catch: java.lang.Exception -> L59e
                goto L5b0
            L587:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L59e
                if (r0 == 0) goto L5b0
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L59e
                r0.discard()     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L59e
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> L59e
                goto L5b0
            L59e:
                r0 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = "816 Exception occurred. e="
                r3.<init>(r4)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)
            L5b0:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r4 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)
                monitor-enter(r4)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L5cd
                java.util.concurrent.atomic.AtomicBoolean r0 = com.felicanetworks.semc.ISemClientImpl.access$3200(r0)     // Catch: java.lang.Throwable -> L5cd
                r5 = 0
                r0.compareAndSet(r7, r5)     // Catch: java.lang.Throwable -> L5cd
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L5cd
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r0 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)     // Catch: java.lang.Throwable -> L5cd
                r0.countDownTaskInterruptedLatch()     // Catch: java.lang.Throwable -> L5cd
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L5cd
                goto L7ab
            L5cd:
                r0 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L5cd
                throw r0
            L5d0:
                r0 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L5d0
                throw r0
            L5d3:
                java.lang.String r0 = "811 Failed to get SEID"
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r11, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L5e2:
                java.lang.String r0 = "810 Failed to get SEID"
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L665 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r4 = 200(0xc8, float:2.8E-43)
                r0.<init>(r4, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L5f3:
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "809 OS Version is not supported version"
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r6 = 500(0x1f4, float:7.0E-43)
                r0.<init>(r6, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L606:
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "704 checkAppCertHash NG."
                com.felicanetworks.semc.util.LogMgr.log(r5, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r6 = 100
                r0.<init>(r6, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L619:
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "806 ChipType is not GP4.1."
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r6 = 201(0xc9, float:2.82E-43)
                r0.<init>(r6, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L62c:
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "805 Failed to get CareerIdentifyCode from common.cfg."
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r6 = 201(0xc9, float:2.82E-43)
                r0.<init>(r6, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L63f:
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "804 Failed to get ChipIssuerId from common.cfg."
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r6 = 201(0xc9, float:2.82E-43)
                r0.<init>(r6, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L652:
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "803 Failed to get ChipType from common.cfg."
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r6 = 201(0xc9, float:2.82E-43)
                r0.<init>(r6, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L665:
                r0 = move-exception
                r4 = 200(0xc8, float:2.8E-43)
            L668:
                r6 = 201(0xc9, float:2.82E-43)
                goto L7bd
            L66c:
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "802 Failed to get GpEseReaderName from common.cfg."
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L67f com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r6 = 201(0xc9, float:2.82E-43)
                r0.<init>(r6, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L693 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L693 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L67f:
                r0 = move-exception
                goto L668
            L681:
                r6 = r4
                r4 = 200(0xc8, float:2.8E-43)
                java.lang.String r0 = "800 ANDROID_ID is null or Empty"
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: com.felicanetworks.semc.omapi.GpException -> L693 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                com.felicanetworks.semc.SemClientException r0 = new com.felicanetworks.semc.SemClientException     // Catch: com.felicanetworks.semc.omapi.GpException -> L693 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                java.lang.String r3 = com.felicanetworks.semc.util.ObfuscatedMsgUtil.executionPoint()     // Catch: com.felicanetworks.semc.omapi.GpException -> L693 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                r0.<init>(r11, r3)     // Catch: com.felicanetworks.semc.omapi.GpException -> L693 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
                throw r0     // Catch: com.felicanetworks.semc.omapi.GpException -> L693 com.felicanetworks.semc.SemClientException -> L69a java.lang.InterruptedException -> L6a3 java.lang.Throwable -> L921
            L693:
                r0 = move-exception
                goto L7bd
            L696:
                r0 = move-exception
                r6 = r4
                goto L21
            L69a:
                r0 = move-exception
                goto L927
            L69d:
                r0 = move-exception
                r3 = r0
                r9 = r8
                goto La6f
            L6a2:
                r9 = r8
            L6a3:
                java.lang.String r0 = "707 InterruptedException"
                com.felicanetworks.semc.util.LogMgr.log(r5, r0)     // Catch: java.lang.Throwable -> L921
                r1.mOnConnectedListener = r8     // Catch: java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4802(r0, r8)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4902(r0, r8)
                com.felicanetworks.semc.ISemClientEventListener r0 = r1.mOnConnectedListener
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this
                monitor-enter(r3)
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L7b5
                com.felicanetworks.semc.ISemClientImpl.access$3402(r4, r8)     // Catch: java.lang.Throwable -> L7b5
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L7b5
                java.util.concurrent.atomic.AtomicBoolean r4 = com.felicanetworks.semc.ISemClientImpl.access$5000(r4)     // Catch: java.lang.Throwable -> L7b5
                r5 = 0
                r4.compareAndSet(r7, r5)     // Catch: java.lang.Throwable -> L7b5
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L7b5
                if (r0 == 0) goto L767
                java.lang.String r3 = r9.getCallbackName()     // Catch: java.lang.Exception -> L77e
                java.lang.String r4 = "OnConnectedListener#onError"
                boolean r3 = r3.equals(r4)     // Catch: java.lang.Exception -> L77e
                if (r3 == 0) goto L73c
                java.lang.String r3 = "API"
                java.lang.String r4 = "OnConnectedListener"
                java.lang.String r5 = r9.getCallbackName()     // Catch: java.lang.Exception -> L77e
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L77e
                r6.<init>()     // Catch: java.lang.Exception -> L77e
                java.lang.String r10 = " errorCode["
                r6.append(r10)     // Catch: java.lang.Exception -> L77e
                int r10 = r9.getErrorCode()     // Catch: java.lang.Exception -> L77e
                r6.append(r10)     // Catch: java.lang.Exception -> L77e
                java.lang.String r10 = "] additionalErrorInfo ["
                r6.append(r10)     // Catch: java.lang.Exception -> L77e
                java.lang.String r10 = r9.getErrorAdditionalInformation()     // Catch: java.lang.Exception -> L77e
                r6.append(r10)     // Catch: java.lang.Exception -> L77e
                java.lang.String r10 = "] message["
                r6.append(r10)     // Catch: java.lang.Exception -> L77e
                java.lang.String r10 = r9.getErrorMessage()     // Catch: java.lang.Exception -> L77e
                r6.append(r10)     // Catch: java.lang.Exception -> L77e
                java.lang.String r10 = "]"
                r6.append(r10)     // Catch: java.lang.Exception -> L77e
                java.lang.String r6 = r6.toString()     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r4, r5, r6)     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.SemClientExternalLogConst$SemcApi r3 = com.felicanetworks.semc.SemClientExternalLogConst.SemcApi.CONNECT     // Catch: java.lang.Exception -> L77e
                int r4 = r9.getErrorCode()     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.util.LogMgr.exLogOnError(r3, r4)     // Catch: java.lang.Exception -> L77e
                r0.onEventNotify(r9)     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl.access$3500(r0)     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L77e
                if (r0 == 0) goto L75b
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L77e
                r0.discard()     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> L77e
                goto L75b
            L73c:
                java.lang.String r3 = "API"
                java.lang.String r4 = "OnConnectedListener"
                java.lang.String r5 = r9.getCallbackName()     // Catch: java.lang.Exception -> L77e
                java.lang.String r6 = " succeeded"
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r4, r5, r6)     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.SemChipHolder r3 = com.felicanetworks.semc.ISemClientImpl.access$700(r3)     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.omapi.GpController r3 = r3.getGpController()     // Catch: java.lang.Exception -> L77e
                if (r3 == 0) goto L758
                r3.closeChannel()     // Catch: java.lang.Exception -> L77e
            L758:
                r0.onEventNotify(r9)     // Catch: java.lang.Exception -> L77e
            L75b:
                java.lang.String r0 = "API"
                java.lang.String r3 = "OnConnectedListener"
                java.lang.String r4 = r9.getCallbackName()     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.util.LogMgr.performanceOut(r0, r3, r4)     // Catch: java.lang.Exception -> L77e
                goto L790
            L767:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L77e
                if (r0 == 0) goto L790
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L77e
                r0.discard()     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L77e
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> L77e
                goto L790
            L77e:
                r0 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = "816 Exception occurred. e="
                r3.<init>(r4)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)
            L790:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r4 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)
                monitor-enter(r4)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L7b2
                java.util.concurrent.atomic.AtomicBoolean r0 = com.felicanetworks.semc.ISemClientImpl.access$3200(r0)     // Catch: java.lang.Throwable -> L7b2
                r5 = 0
                r0.compareAndSet(r7, r5)     // Catch: java.lang.Throwable -> L7b2
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L7b2
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r0 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)     // Catch: java.lang.Throwable -> L7b2
                r0.countDownTaskInterruptedLatch()     // Catch: java.lang.Throwable -> L7b2
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L7b2
            L7ab:
                java.lang.String r0 = "999"
                com.felicanetworks.semc.util.LogMgr.log(r2, r0)
                goto La68
            L7b2:
                r0 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L7b2
                throw r0
            L7b5:
                r0 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L7b5
                throw r0
            L7b8:
                r0 = move-exception
                r6 = r4
                r4 = 200(0xc8, float:2.8E-43)
                r9 = r8
            L7bd:
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L921
                r3.<init>()     // Catch: java.lang.Throwable -> L921
                java.lang.String r10 = "814 GpException occurred. e="
                r3.append(r10)     // Catch: java.lang.Throwable -> L921
                r3.append(r0)     // Catch: java.lang.Throwable -> L921
                java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L921
                com.felicanetworks.semc.util.LogMgr.log(r7, r3)     // Catch: java.lang.Throwable -> L921
                java.lang.String r3 = "706 GpException occurred."
                com.felicanetworks.semc.util.LogMgr.log(r5, r3)     // Catch: java.lang.Exception -> L802 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L802 java.lang.Throwable -> L921
                monitor-enter(r3)     // Catch: java.lang.Exception -> L802 java.lang.Throwable -> L921
                com.felicanetworks.semc.ISemClientImpl r5 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L7ff
                com.felicanetworks.semc.ISemClientImpl.access$802(r5, r8)     // Catch: java.lang.Throwable -> L7ff
                int r5 = r0.getType()     // Catch: java.lang.Throwable -> L7ff
                switch(r5) {
                    case 201: goto L7ec;
                    case 202: goto L7e9;
                    case 203: goto L7e6;
                    default: goto L7e5;
                }     // Catch: java.lang.Throwable -> L7ff
            L7e5:
                goto L7ed
            L7e6:
                r4 = 203(0xcb, float:2.84E-43)
                goto L7ed
            L7e9:
                r4 = 202(0xca, float:2.83E-43)
                goto L7ed
            L7ec:
                r4 = r6
            L7ed:
                com.felicanetworks.semc.SemClientNotifyEventInfo r5 = new com.felicanetworks.semc.SemClientNotifyEventInfo     // Catch: java.lang.Throwable -> L7ff
                java.lang.String r6 = "OnConnectedListener#onError"
                java.lang.String r10 = ""
                java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L7ff
                r5.<init>(r6, r4, r10, r0)     // Catch: java.lang.Throwable -> L7ff
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L7fc
                goto L818
            L7fc:
                r0 = move-exception
                r9 = r5
                goto L800
            L7ff:
                r0 = move-exception
            L800:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L7ff
                throw r0     // Catch: java.lang.Exception -> L802 java.lang.Throwable -> L921
            L802:
                r0 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L921
                r3.<init>()     // Catch: java.lang.Throwable -> L921
                java.lang.String r4 = "815 Exception occurred. e="
                r3.append(r4)     // Catch: java.lang.Throwable -> L921
                r3.append(r0)     // Catch: java.lang.Throwable -> L921
                java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L921
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: java.lang.Throwable -> L921
                r5 = r9
            L818:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4802(r0, r8)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4902(r0, r8)
                com.felicanetworks.semc.ISemClientEventListener r0 = r1.mOnConnectedListener
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this
                monitor-enter(r3)
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L91e
                com.felicanetworks.semc.ISemClientImpl.access$3402(r4, r8)     // Catch: java.lang.Throwable -> L91e
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L91e
                java.util.concurrent.atomic.AtomicBoolean r4 = com.felicanetworks.semc.ISemClientImpl.access$5000(r4)     // Catch: java.lang.Throwable -> L91e
                r6 = 0
                r4.compareAndSet(r7, r6)     // Catch: java.lang.Throwable -> L91e
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L91e
                if (r0 == 0) goto L8d5
                java.lang.String r3 = r5.getCallbackName()     // Catch: java.lang.Exception -> L8ec
                java.lang.String r4 = "OnConnectedListener#onError"
                boolean r3 = r3.equals(r4)     // Catch: java.lang.Exception -> L8ec
                if (r3 == 0) goto L8aa
                java.lang.String r3 = "API"
                java.lang.String r4 = "OnConnectedListener"
                java.lang.String r6 = r5.getCallbackName()     // Catch: java.lang.Exception -> L8ec
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L8ec
                r9.<init>()     // Catch: java.lang.Exception -> L8ec
                java.lang.String r10 = " errorCode["
                r9.append(r10)     // Catch: java.lang.Exception -> L8ec
                int r10 = r5.getErrorCode()     // Catch: java.lang.Exception -> L8ec
                r9.append(r10)     // Catch: java.lang.Exception -> L8ec
                java.lang.String r10 = "] additionalErrorInfo ["
                r9.append(r10)     // Catch: java.lang.Exception -> L8ec
                java.lang.String r10 = r5.getErrorAdditionalInformation()     // Catch: java.lang.Exception -> L8ec
                r9.append(r10)     // Catch: java.lang.Exception -> L8ec
                java.lang.String r10 = "] message["
                r9.append(r10)     // Catch: java.lang.Exception -> L8ec
                java.lang.String r10 = r5.getErrorMessage()     // Catch: java.lang.Exception -> L8ec
                r9.append(r10)     // Catch: java.lang.Exception -> L8ec
                java.lang.String r10 = "]"
                r9.append(r10)     // Catch: java.lang.Exception -> L8ec
                java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r4, r6, r9)     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.SemClientExternalLogConst$SemcApi r3 = com.felicanetworks.semc.SemClientExternalLogConst.SemcApi.CONNECT     // Catch: java.lang.Exception -> L8ec
                int r4 = r5.getErrorCode()     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.util.LogMgr.exLogOnError(r3, r4)     // Catch: java.lang.Exception -> L8ec
                r0.onEventNotify(r5)     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl.access$3500(r0)     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L8ec
                if (r0 == 0) goto L8c9
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L8ec
                r0.discard()     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> L8ec
                goto L8c9
            L8aa:
                java.lang.String r3 = "API"
                java.lang.String r4 = "OnConnectedListener"
                java.lang.String r6 = r5.getCallbackName()     // Catch: java.lang.Exception -> L8ec
                java.lang.String r8 = " succeeded"
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r4, r6, r8)     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.SemChipHolder r3 = com.felicanetworks.semc.ISemClientImpl.access$700(r3)     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.omapi.GpController r3 = r3.getGpController()     // Catch: java.lang.Exception -> L8ec
                if (r3 == 0) goto L8c6
                r3.closeChannel()     // Catch: java.lang.Exception -> L8ec
            L8c6:
                r0.onEventNotify(r5)     // Catch: java.lang.Exception -> L8ec
            L8c9:
                java.lang.String r0 = "API"
                java.lang.String r3 = "OnConnectedListener"
                java.lang.String r4 = r5.getCallbackName()     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.util.LogMgr.performanceOut(r0, r3, r4)     // Catch: java.lang.Exception -> L8ec
                goto L8fe
            L8d5:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L8ec
                if (r0 == 0) goto L8fe
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> L8ec
                r0.discard()     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> L8ec
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> L8ec
                goto L8fe
            L8ec:
                r0 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = "816 Exception occurred. e="
                r3.<init>(r4)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)
            L8fe:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r4 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)
                monitor-enter(r4)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L91b
                java.util.concurrent.atomic.AtomicBoolean r0 = com.felicanetworks.semc.ISemClientImpl.access$3200(r0)     // Catch: java.lang.Throwable -> L91b
                r5 = 0
                r0.compareAndSet(r7, r5)     // Catch: java.lang.Throwable -> L91b
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L91b
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r0 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)     // Catch: java.lang.Throwable -> L91b
                r0.countDownTaskInterruptedLatch()     // Catch: java.lang.Throwable -> L91b
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L91b
                goto L7ab
            L91b:
                r0 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> L91b
                throw r0
            L91e:
                r0 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L91e
                throw r0
            L921:
                r0 = move-exception
                r3 = r0
                goto La6f
            L925:
                r0 = move-exception
                r9 = r8
            L927:
                java.lang.String r3 = "705 SemClientException occurred."
                com.felicanetworks.semc.util.LogMgr.log(r5, r3)     // Catch: java.lang.Throwable -> L921 java.lang.Exception -> L94f
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L921 java.lang.Exception -> L94f
                monitor-enter(r3)     // Catch: java.lang.Throwable -> L921 java.lang.Exception -> L94f
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> L94c
                com.felicanetworks.semc.ISemClientImpl.access$802(r4, r8)     // Catch: java.lang.Throwable -> L94c
                com.felicanetworks.semc.SemClientNotifyEventInfo r4 = new com.felicanetworks.semc.SemClientNotifyEventInfo     // Catch: java.lang.Throwable -> L94c
                java.lang.String r5 = "OnConnectedListener#onError"
                int r6 = r0.getErrorCode()     // Catch: java.lang.Throwable -> L94c
                java.lang.String r10 = r0.getAdditionalErrorInfo()     // Catch: java.lang.Throwable -> L94c
                java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L94c
                r4.<init>(r5, r6, r10, r0)     // Catch: java.lang.Throwable -> L94c
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L949
                goto L965
            L949:
                r0 = move-exception
                r9 = r4
                goto L94d
            L94c:
                r0 = move-exception
            L94d:
                monitor-exit(r3)     // Catch: java.lang.Throwable -> L94c
                throw r0     // Catch: java.lang.Throwable -> L921 java.lang.Exception -> L94f
            L94f:
                r0 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L921
                r3.<init>()     // Catch: java.lang.Throwable -> L921
                java.lang.String r4 = "813 Exception occurred. e="
                r3.append(r4)     // Catch: java.lang.Throwable -> L921
                r3.append(r0)     // Catch: java.lang.Throwable -> L921
                java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> L921
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)     // Catch: java.lang.Throwable -> L921
                r4 = r9
            L965:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4802(r0, r8)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4902(r0, r8)
                com.felicanetworks.semc.ISemClientEventListener r0 = r1.mOnConnectedListener
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this
                monitor-enter(r3)
                com.felicanetworks.semc.ISemClientImpl r5 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> La6c
                com.felicanetworks.semc.ISemClientImpl.access$3402(r5, r8)     // Catch: java.lang.Throwable -> La6c
                com.felicanetworks.semc.ISemClientImpl r5 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> La6c
                java.util.concurrent.atomic.AtomicBoolean r5 = com.felicanetworks.semc.ISemClientImpl.access$5000(r5)     // Catch: java.lang.Throwable -> La6c
                r6 = 0
                r5.compareAndSet(r7, r6)     // Catch: java.lang.Throwable -> La6c
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La6c
                if (r0 == 0) goto La22
                java.lang.String r3 = r4.getCallbackName()     // Catch: java.lang.Exception -> La39
                java.lang.String r5 = "OnConnectedListener#onError"
                boolean r3 = r3.equals(r5)     // Catch: java.lang.Exception -> La39
                if (r3 == 0) goto L9f7
                java.lang.String r3 = "API"
                java.lang.String r5 = "OnConnectedListener"
                java.lang.String r6 = r4.getCallbackName()     // Catch: java.lang.Exception -> La39
                java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> La39
                r9.<init>()     // Catch: java.lang.Exception -> La39
                java.lang.String r10 = " errorCode["
                r9.append(r10)     // Catch: java.lang.Exception -> La39
                int r10 = r4.getErrorCode()     // Catch: java.lang.Exception -> La39
                r9.append(r10)     // Catch: java.lang.Exception -> La39
                java.lang.String r10 = "] additionalErrorInfo ["
                r9.append(r10)     // Catch: java.lang.Exception -> La39
                java.lang.String r10 = r4.getErrorAdditionalInformation()     // Catch: java.lang.Exception -> La39
                r9.append(r10)     // Catch: java.lang.Exception -> La39
                java.lang.String r10 = "] message["
                r9.append(r10)     // Catch: java.lang.Exception -> La39
                java.lang.String r10 = r4.getErrorMessage()     // Catch: java.lang.Exception -> La39
                r9.append(r10)     // Catch: java.lang.Exception -> La39
                java.lang.String r10 = "]"
                r9.append(r10)     // Catch: java.lang.Exception -> La39
                java.lang.String r9 = r9.toString()     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r5, r6, r9)     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.SemClientExternalLogConst$SemcApi r3 = com.felicanetworks.semc.SemClientExternalLogConst.SemcApi.CONNECT     // Catch: java.lang.Exception -> La39
                int r5 = r4.getErrorCode()     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.util.LogMgr.exLogOnError(r3, r5)     // Catch: java.lang.Exception -> La39
                r0.onEventNotify(r4)     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl.access$3500(r0)     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> La39
                if (r0 == 0) goto La16
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> La39
                r0.discard()     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> La39
                goto La16
            L9f7:
                java.lang.String r3 = "API"
                java.lang.String r5 = "OnConnectedListener"
                java.lang.String r6 = r4.getCallbackName()     // Catch: java.lang.Exception -> La39
                java.lang.String r8 = " succeeded"
                com.felicanetworks.semc.util.LogMgr.performanceIn(r3, r5, r6, r8)     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl r3 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.SemChipHolder r3 = com.felicanetworks.semc.ISemClientImpl.access$700(r3)     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.omapi.GpController r3 = r3.getGpController()     // Catch: java.lang.Exception -> La39
                if (r3 == 0) goto La13
                r3.closeChannel()     // Catch: java.lang.Exception -> La39
            La13:
                r0.onEventNotify(r4)     // Catch: java.lang.Exception -> La39
            La16:
                java.lang.String r0 = "API"
                java.lang.String r3 = "OnConnectedListener"
                java.lang.String r4 = r4.getCallbackName()     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.util.LogMgr.performanceOut(r0, r3, r4)     // Catch: java.lang.Exception -> La39
                goto La4b
            La22:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> La39
                if (r0 == 0) goto La4b
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> La39
                r0.discard()     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> La39
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> La39
                goto La4b
            La39:
                r0 = move-exception
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                java.lang.String r4 = "816 Exception occurred. e="
                r3.<init>(r4)
                r3.append(r0)
                java.lang.String r0 = r3.toString()
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)
            La4b:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r4 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)
                monitor-enter(r4)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> La69
                java.util.concurrent.atomic.AtomicBoolean r0 = com.felicanetworks.semc.ISemClientImpl.access$3200(r0)     // Catch: java.lang.Throwable -> La69
                r5 = 0
                r0.compareAndSet(r7, r5)     // Catch: java.lang.Throwable -> La69
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> La69
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r0 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)     // Catch: java.lang.Throwable -> La69
                r0.countDownTaskInterruptedLatch()     // Catch: java.lang.Throwable -> La69
                monitor-exit(r4)     // Catch: java.lang.Throwable -> La69
                goto L7ab
            La68:
                return
            La69:
                r0 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> La69
                throw r0
            La6c:
                r0 = move-exception
                monitor-exit(r3)     // Catch: java.lang.Throwable -> La6c
                throw r0
            La6f:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4802(r0, r8)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.ISemClientImpl.access$4902(r0, r8)
                com.felicanetworks.semc.ISemClientEventListener r0 = r1.mOnConnectedListener
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this
                monitor-enter(r4)
                com.felicanetworks.semc.ISemClientImpl r5 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> Lb79
                com.felicanetworks.semc.ISemClientImpl.access$3402(r5, r8)     // Catch: java.lang.Throwable -> Lb79
                com.felicanetworks.semc.ISemClientImpl r5 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> Lb79
                java.util.concurrent.atomic.AtomicBoolean r5 = com.felicanetworks.semc.ISemClientImpl.access$5000(r5)     // Catch: java.lang.Throwable -> Lb79
                r6 = 0
                r5.compareAndSet(r7, r6)     // Catch: java.lang.Throwable -> Lb79
                monitor-exit(r4)     // Catch: java.lang.Throwable -> Lb79
                if (r0 == 0) goto Lb2c
                java.lang.String r4 = r9.getCallbackName()     // Catch: java.lang.Exception -> Lb43
                java.lang.String r5 = "OnConnectedListener#onError"
                boolean r4 = r4.equals(r5)     // Catch: java.lang.Exception -> Lb43
                if (r4 == 0) goto Lb01
                java.lang.String r4 = "API"
                java.lang.String r5 = "OnConnectedListener"
                java.lang.String r6 = r9.getCallbackName()     // Catch: java.lang.Exception -> Lb43
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Lb43
                r10.<init>()     // Catch: java.lang.Exception -> Lb43
                java.lang.String r11 = " errorCode["
                r10.append(r11)     // Catch: java.lang.Exception -> Lb43
                int r11 = r9.getErrorCode()     // Catch: java.lang.Exception -> Lb43
                r10.append(r11)     // Catch: java.lang.Exception -> Lb43
                java.lang.String r11 = "] additionalErrorInfo ["
                r10.append(r11)     // Catch: java.lang.Exception -> Lb43
                java.lang.String r11 = r9.getErrorAdditionalInformation()     // Catch: java.lang.Exception -> Lb43
                r10.append(r11)     // Catch: java.lang.Exception -> Lb43
                java.lang.String r11 = "] message["
                r10.append(r11)     // Catch: java.lang.Exception -> Lb43
                java.lang.String r11 = r9.getErrorMessage()     // Catch: java.lang.Exception -> Lb43
                r10.append(r11)     // Catch: java.lang.Exception -> Lb43
                java.lang.String r11 = "]"
                r10.append(r11)     // Catch: java.lang.Exception -> Lb43
                java.lang.String r10 = r10.toString()     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.util.LogMgr.performanceIn(r4, r5, r6, r10)     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.SemClientExternalLogConst$SemcApi r4 = com.felicanetworks.semc.SemClientExternalLogConst.SemcApi.CONNECT     // Catch: java.lang.Exception -> Lb43
                int r5 = r9.getErrorCode()     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.util.LogMgr.exLogOnError(r4, r5)     // Catch: java.lang.Exception -> Lb43
                r0.onEventNotify(r9)     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl.access$3500(r0)     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> Lb43
                if (r0 == 0) goto Lb20
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> Lb43
                r0.discard()     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> Lb43
                goto Lb20
            Lb01:
                java.lang.String r4 = "API"
                java.lang.String r5 = "OnConnectedListener"
                java.lang.String r6 = r9.getCallbackName()     // Catch: java.lang.Exception -> Lb43
                java.lang.String r8 = " succeeded"
                com.felicanetworks.semc.util.LogMgr.performanceIn(r4, r5, r6, r8)     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl r4 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.SemChipHolder r4 = com.felicanetworks.semc.ISemClientImpl.access$700(r4)     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.omapi.GpController r4 = r4.getGpController()     // Catch: java.lang.Exception -> Lb43
                if (r4 == 0) goto Lb1d
                r4.closeChannel()     // Catch: java.lang.Exception -> Lb43
            Lb1d:
                r0.onEventNotify(r9)     // Catch: java.lang.Exception -> Lb43
            Lb20:
                java.lang.String r0 = "API"
                java.lang.String r4 = "OnConnectedListener"
                java.lang.String r5 = r9.getCallbackName()     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.util.LogMgr.performanceOut(r0, r4, r5)     // Catch: java.lang.Exception -> Lb43
                goto Lb55
            Lb2c:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> Lb43
                if (r0 == 0) goto Lb55
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.SemChipHolder r0 = com.felicanetworks.semc.ISemClientImpl.access$700(r0)     // Catch: java.lang.Exception -> Lb43
                r0.discard()     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Exception -> Lb43
                com.felicanetworks.semc.ISemClientImpl.access$702(r0, r8)     // Catch: java.lang.Exception -> Lb43
                goto Lb55
            Lb43:
                r0 = move-exception
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                java.lang.String r5 = "816 Exception occurred. e="
                r4.<init>(r5)
                r4.append(r0)
                java.lang.String r0 = r4.toString()
                com.felicanetworks.semc.util.LogMgr.log(r7, r0)
            Lb55:
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r5 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)
                monitor-enter(r5)
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> Lb76
                java.util.concurrent.atomic.AtomicBoolean r0 = com.felicanetworks.semc.ISemClientImpl.access$3200(r0)     // Catch: java.lang.Throwable -> Lb76
                r6 = 0
                r0.compareAndSet(r7, r6)     // Catch: java.lang.Throwable -> Lb76
                com.felicanetworks.semc.ISemClientImpl r0 = com.felicanetworks.semc.ISemClientImpl.this     // Catch: java.lang.Throwable -> Lb76
                com.felicanetworks.semc.util.TaskInterruptedLatchManager r0 = com.felicanetworks.semc.ISemClientImpl.access$3100(r0)     // Catch: java.lang.Throwable -> Lb76
                r0.countDownTaskInterruptedLatch()     // Catch: java.lang.Throwable -> Lb76
                monitor-exit(r5)     // Catch: java.lang.Throwable -> Lb76
                java.lang.String r0 = "999"
                com.felicanetworks.semc.util.LogMgr.log(r2, r0)
                throw r3
            Lb76:
                r0 = move-exception
                monitor-exit(r5)     // Catch: java.lang.Throwable -> Lb76
                throw r0
            Lb79:
                r0 = move-exception
                monitor-exit(r4)     // Catch: java.lang.Throwable -> Lb79
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.semc.ISemClientImpl.ConnectThread.run():void");
        }

        private void loadProfileData() throws SemClientException {
            boolean zCheckAndGetEnableLog;
            LogMgr.log(9, "000");
            checkAndSaveProfileCache();
            if (!ISemClientImpl.this.mSharedPrefsUtil.readExistence()) {
                ISemClientImpl.this.mDataManager.setProfileId("");
            } else {
                try {
                    try {
                        ProfileDataJson profileDataJson = new ProfileDataJson(ISemClientImpl.this.mSharedPrefsUtil.readProfileInfo());
                        checkCaller(profileDataJson);
                        ISemClientImpl.this.mDataManager.setProfileId(profileDataJson.checkAndGetProfileId());
                        String strCheckAndGetServerConnectionUrl = profileDataJson.checkAndGetServerConnectionUrl();
                        String strCheckAndGetJwsSignatureKeyIdForLinkageData = profileDataJson.checkAndGetJwsSignatureKeyIdForLinkageData();
                        String strCheckAndGetJwsSignatureKeyForLinkageData = profileDataJson.checkAndGetJwsSignatureKeyForLinkageData();
                        HashMap map = new HashMap();
                        map.put(strCheckAndGetJwsSignatureKeyIdForLinkageData, strCheckAndGetJwsSignatureKeyForLinkageData);
                        String strCheckAndGetJwsSignatureKeyIdForClientConfig = profileDataJson.checkAndGetJwsSignatureKeyIdForClientConfig();
                        String strCheckAndGetJwsSignatureKeyForClientConfig = profileDataJson.checkAndGetJwsSignatureKeyForClientConfig();
                        HashMap map2 = new HashMap();
                        map2.put(strCheckAndGetJwsSignatureKeyIdForClientConfig, strCheckAndGetJwsSignatureKeyForClientConfig);
                        SettingInfo.setProfileValue(strCheckAndGetServerConnectionUrl, map, map2);
                        boolean zCheckAndGetEnablePerformanceLog = false;
                        try {
                            zCheckAndGetEnableLog = profileDataJson.checkAndGetEnableLog();
                        } catch (JSONException unused) {
                            LogMgr.log(9, "001 profile has no enableLog element, so enableLog will work with default value.");
                            zCheckAndGetEnableLog = false;
                        }
                        try {
                            zCheckAndGetEnablePerformanceLog = profileDataJson.checkAndGetEnablePerformanceLog();
                        } catch (JSONException unused2) {
                            LogMgr.log(9, "002 profile has no enablePerformanceLog element, so enablePerformanceLog will work with default value.");
                        }
                        LogMgr.setup(profileDataJson.checkAndGetLogLevel(), zCheckAndGetEnableLog, zCheckAndGetEnablePerformanceLog);
                        final String str = "SEMC Profile is enabled";
                        final String profileName = ISemClientImpl.this.mSharedPrefsUtil.readProfileName();
                        LogMgr.log(9, "002 succeeded to read profile(" + profileName + ").SemClient will be work with ProfileData");
                        try {
                            ISemClientImpl.this.mHandlerForToast.post(new Runnable() { // from class: com.felicanetworks.semc.ISemClientImpl$ConnectThread$$ExternalSyntheticLambda4
                                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m422x9f254cf8(str, profileName);
                                }
                            });
                        } catch (Exception unused3) {
                            LogMgr.log(1, "802 Exception occurred in Toast#makeText.");
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                    } catch (SemClientException e) {
                        LogMgr.log(1, "801 occurred verify Error. Failed to connect.");
                        throw e;
                    }
                } catch (JSONException unused4) {
                    LogMgr.log(1, "800 profileData is invalid. Failed to connect.");
                    throw new SemClientException(504, ObfuscatedMsgUtil.executionPoint());
                }
            }
            LogMgr.log(9, "999");
        }

        /* JADX INFO: renamed from: lambda$loadProfileData$0$com-felicanetworks-semc-ISemClientImpl$ConnectThread, reason: not valid java name */
        /* synthetic */ void m422x9f254cf8(String str, String str2) {
            Toast.makeText(ISemClientImpl.this.mContext, str + System.getProperty("line.separator") + str2, 1).show();
        }

        private void checkAndSaveProfileCache() throws SemClientException {
            LogMgr.log(9, "000");
            if (ISemClientImpl.this.mSharedPrefsUtil.hasExistence() || ISemClientImpl.this.mSharedPrefsUtil.hasProfileInfo()) {
                LogMgr.log(9, "997 profile cache exists.");
                return;
            }
            LogMgr.log(9, "001 profile cache is not exists.");
            String externalStoragePath = getExternalStoragePath();
            File[] fileArrListFiles = externalStoragePath != null ? new File(externalStoragePath).listFiles(this.filter) : null;
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                ISemClientImpl.this.mSharedPrefsUtil.writeExistence(false);
                LogMgr.log(9, "998 profile is not exists.");
                return;
            }
            LogMgr.log(9, "002 profile exists.");
            if (fileArrListFiles.length > 1) {
                LogMgr.log(1, "800 There are multiple profiles. Failed to connect.");
                throw new SemClientException(504, ObfuscatedMsgUtil.executionPoint());
            }
            try {
                Path path = Paths.get(fileArrListFiles[0].toString(), new String[0]);
                String payloadWithVerify = new JwsObject(StringUtil.toUTF8String(Files.readAllBytes(path))).getPayloadWithVerify(FlavorConst.SERVER_PUBLIC_KEYS_FOR_PROFILE);
                checkProfileData(payloadWithVerify);
                ISemClientImpl.this.mSharedPrefsUtil.writeExistence(true);
                ISemClientImpl.this.mSharedPrefsUtil.writeProfileName(path.getFileName().toString());
                ISemClientImpl.this.mSharedPrefsUtil.writeProfileInfo(payloadWithVerify);
                LogMgr.log(9, "003 saved cache.");
                LogMgr.log(9, "999");
            } catch (SemClientException e) {
                LogMgr.log(1, "803 invalid profile data. Failed to connect.");
                throw e;
            } catch (JwsException unused) {
                LogMgr.log(1, "802 JwsException occurred in reading profile data. Failed to connect.");
                throw new SemClientException(504, ObfuscatedMsgUtil.executionPoint());
            } catch (IOException unused2) {
                LogMgr.log(1, "801 IOException occurred in reading profile data. Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
        }

        private String getExternalStoragePath() {
            LogMgr.log(8, "000");
            String str = null;
            if ("mounted".equals(Environment.getExternalStorageState())) {
                try {
                    str = ISemClientImpl.this.mContext.getExternalFilesDir(null).getPath() + DomExceptionUtils.SEPARATOR;
                } catch (Exception unused) {
                }
            }
            LogMgr.log(8, "999 result=" + str);
            return str;
        }

        private void checkProfileData(String str) throws SemClientException {
            LogMgr.log(9, "000");
            try {
                ProfileDataJson profileDataJson = new ProfileDataJson(str);
                profileDataJson.checkProfileData();
                checkProfileId(profileDataJson.checkAndGetProfileId());
                LogMgr.log(9, "999");
            } catch (JSONException unused) {
                LogMgr.log(1, "800 profileData is invalid. Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
        }

        private void checkProfileId(String str) throws SemClientException {
            LogMgr.log(9, "000");
            String[] strArrSplit = split(str);
            if (!PROFILE_ID_HEADER.equals(strArrSplit[0])) {
                LogMgr.log(1, "800 profileData is invalid(Profile ID is invalid). Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (!Pattern.compile(PROFILE_ID_ENV_NAME_PATTERN).matcher(strArrSplit[1]).find()) {
                LogMgr.log(1, "801 profileData is invalid(Profile ID is invalid). Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (!Pattern.compile(PROFILE_ID_SP_APP_ID_PATTERN).matcher(strArrSplit[2]).find()) {
                LogMgr.log(1, "802 profileData is invalid(Profile ID is invalid). Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (!Pattern.compile(PROFILE_ID_SEQ_PATTERN).matcher(strArrSplit[3]).find()) {
                LogMgr.log(1, "803 profileData is invalid(Profile ID is invalid). Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (!Pattern.compile(PROFILE_ID_RANDOM_PATTERN).matcher(strArrSplit[4]).find()) {
                LogMgr.log(1, "804 profileData is invalid(Profile ID is invalid). Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(9, "999");
        }

        private String[] split(String str) throws SemClientException {
            LogMgr.log(9, "000");
            String[] strArrSplit = str.split("_");
            if (strArrSplit.length != 5) {
                LogMgr.log(1, "800 profileData is invalid(Profile ID is invalid). Failed to connect.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            LogMgr.log(9, "999");
            return strArrSplit;
        }

        private void checkCaller(ProfileDataJson profileDataJson) throws JSONException, SemClientException {
            LogMgr.log(9, "000");
            if (!this.mCalledFromSpApp) {
                if (!ISemClientImpl.this.mConnectedApp.getCallerPackageName().equals("com.felicanetworks.mfm.main") ? !ISemClientImpl.this.mConnectedApp.getCallerPackageName().equals("com.felicanetworks.semcapp") || !SignatureUtil.checkAppCertHash(ISemClientImpl.this.mContext, "BE:51:DB:F4:FE:C8:9B:D3:28:46:45:7B:13:B7:30:08:76:AF:55:94:D2:87:4D:EE:02:69:04:96:5A:E4:A6:CB", "com.felicanetworks.semcapp") : !SignatureUtil.checkAppCertHash(ISemClientImpl.this.mContext, "BE:51:DB:F4:FE:C8:9B:D3:28:46:45:7B:13:B7:30:08:76:AF:55:94:D2:87:4D:EE:02:69:04:96:5A:E4:A6:CB", "com.felicanetworks.mfm.main")) {
                    throw new SemClientException(504, ObfuscatedMsgUtil.executionPoint());
                }
            } else {
                String strCheckAndGetPermittedSpPackageName = profileDataJson.checkAndGetPermittedSpPackageName();
                String strCheckAndGetPermittedSigningCertHash = profileDataJson.checkAndGetPermittedSigningCertHash();
                if (!ISemClientImpl.this.mConnectedApp.getCallerPackageName().equals(strCheckAndGetPermittedSpPackageName) || !SignatureUtil.checkAppCertHash(ISemClientImpl.this.mContext, strCheckAndGetPermittedSigningCertHash, strCheckAndGetPermittedSpPackageName)) {
                    throw new SemClientException(504, ObfuscatedMsgUtil.executionPoint());
                }
            }
            LogMgr.log(9, "999");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ClientControlInfoJsonArray getClientControlInfoJSONArray() throws InterruptedException, SemClientException {
        ClientControlInfoJsonArray clientControlInfoJsonArray;
        LogMgr.log(8, "000");
        if (this.mSharedPrefsUtil != null) {
            for (int i = 0; i <= 1; i++) {
                String controlInfo = this.mSharedPrefsUtil.readControlInfo();
                try {
                    if (controlInfo == null) {
                        throw new JSONException(ObfuscatedMsgUtil.executionPoint());
                    }
                    clientControlInfoJsonArray = new ClientControlInfoJsonArray(controlInfo);
                } catch (JSONException unused) {
                    if (i < 1) {
                        this.mSharedPrefsUtil.clearClientConfigInfo();
                        getClientConfiguration();
                    } else {
                        LogMgr.log(1, "807 CacheFile was corrupted.");
                        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                    }
                }
            }
            clientControlInfoJsonArray = null;
        } else {
            clientControlInfoJsonArray = null;
        }
        LogMgr.log(8, "999");
        return clientControlInfoJsonArray;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean needsSetRoutineWorkRequest(int i, int i2) {
        LogMgr.log(8, "000");
        boolean z = (this.mPeriodicWorkStartTimeMin == i && this.mPeriodicWorkStartTimeMax == i2) ? false : true;
        LogMgr.log(8, "999 ret=" + z);
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelRoutineWorkRequest() throws SemClientException {
        LogMgr.log(8, "000");
        RoutineWorker.cancelRoutineWorkRequest(this.mContext);
        LogMgr.log(8, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRoutineWorkRequest(int i, int i2) throws SemClientException {
        LogMgr.log(8, "000");
        RoutineWorker.scheduleNext(this.mContext, i, i2);
        LogMgr.log(8, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isEnqueuedRoutineWorkReq() throws SemClientException {
        boolean z;
        LogMgr.log(8, "000");
        try {
            ListenableFuture<List<WorkInfo>> workInfosByTag = WorkManager.getInstance(this.mContext).getWorkInfosByTag(RoutineWorker.TAG);
            if (workInfosByTag.get() != null) {
                for (WorkInfo workInfo : workInfosByTag.get()) {
                    if (workInfo.getState() == WorkInfo.State.ENQUEUED || workInfo.getState() == WorkInfo.State.RUNNING) {
                        z = true;
                        break;
                    }
                }
                z = false;
            } else {
                z = false;
            }
            LogMgr.log(8, "999 ret[" + z + "]");
            return z;
        } catch (InterruptedException unused) {
            LogMgr.log(2, "701 InterruptedException occurred.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        } catch (ExecutionException unused2) {
            LogMgr.log(2, "700 ExecutionException occurred.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX INFO: Access modifiers changed from: private */
    public void checkValidVer(String str, String str2) throws SemClientException {
        LogMgr.log(9, "000 validVer[" + str + "] currentVer[" + str2 + "]");
        try {
            if (str == null) {
                LogMgr.log(1, "800 validVer is null.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (str2 == null) {
                LogMgr.log(1, "801 currentVer is null.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            String[] strArrSplit = str.split(Pattern.quote(VERSION_SEPARATOR));
            String[] strArrSplit2 = str2.split(Pattern.quote(VERSION_SEPARATOR));
            if (strArrSplit.length != 3) {
                LogMgr.log(1, "802 Invalid validVer. validVer[" + str + "]");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            if (strArrSplit2.length != 3) {
                LogMgr.log(1, "803 Invalid currentVer. currentVer[" + str2 + "]");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
            strArrSplit[1] = StringUtil.padding(strArrSplit[1], '0', 2);
            strArrSplit[2] = StringUtil.padding(strArrSplit[2], '0', 2);
            strArrSplit2[1] = StringUtil.padding(strArrSplit2[1], '0', 2);
            strArrSplit2[2] = StringUtil.padding(strArrSplit2[2], '0', 2);
            String str3 = strArrSplit[0] + strArrSplit[1] + strArrSplit[2];
            try {
                if (Integer.parseInt(strArrSplit2[0] + strArrSplit2[1] + strArrSplit2[2]) >= Integer.parseInt(str3)) {
                    LogMgr.log(9, "999");
                    return;
                }
                LogMgr.log(1, "805 currentVer is less than validVer. validVer[" + str + "] currentVer[" + str2 + "]");
                throw new SemClientException(502, ObfuscatedMsgUtil.executionPoint());
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "804 parseInt error occurred.");
                throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (SemClientException e) {
            throw e;
        } catch (Exception unused2) {
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAllowedService(String str, String str2, String str3, String str4) throws JSONException, SemClientException {
        LogMgr.log(9, "000 spAppId[" + str + "] serviceId[" + str2 + "] sepId[" + str3 + "] useCase[" + str4 + "]");
        if (str == null || str2 == null || str3 == null || str4 == null) {
            LogMgr.log(8, "700 arguments invalid.");
            return false;
        }
        ArrayList<SpAppInfo> spAppInfoListBySpAppId = new SpAppInfoJsonArray(this.mSharedPrefsUtil.readSpAppInfoList()).getSpAppInfoListBySpAppId(str);
        if (spAppInfoListBySpAppId == null) {
            LogMgr.log(2, "701 spAppId is not matched any spAppInfo.");
            return false;
        }
        boolean z = false;
        boolean z2 = false;
        for (int i = 0; i < spAppInfoListBySpAppId.size(); i++) {
            SpAppInfo spAppInfo = spAppInfoListBySpAppId.get(i);
            for (int i2 = 0; i2 < spAppInfo.mAllowedServiceLists.size(); i2++) {
                SpAppInfo.AllowedServiceList allowedServiceList = spAppInfo.mAllowedServiceLists.get(i2);
                if (str2.equals(allowedServiceList.serviceId)) {
                    if (!allowedServiceList.sepIdList.contains(str3)) {
                        z = true;
                    } else {
                        if (allowedServiceList.useCaseList.contains(str4)) {
                            return true;
                        }
                        z = true;
                        z2 = true;
                    }
                }
            }
        }
        if (!z) {
            LogMgr.log(8, "702 checkServiceId is not found.");
            return false;
        }
        if (!z2) {
            LogMgr.log(8, "703 checkSepId is not found.");
            return false;
        }
        LogMgr.log(8, "704 checkUseCase is not found.");
        LogMgr.log(9, "999");
        return false;
    }

    public class SemClientEventListenerWrapper implements ISemClientEventListener {
        private ISemClientEventListener mOnSemClientEventListener;

        SemClientEventListenerWrapper(ISemClientEventListener iSemClientEventListener) {
            LogMgr.log(5, "000");
            this.mOnSemClientEventListener = iSemClientEventListener;
            LogMgr.log(5, "999");
        }

        /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
        @Override // com.felicanetworks.semc.ISemClientEventListener
        public void onEventNotify(SemClientNotifyEventInfo semClientNotifyEventInfo) throws RemoteException {
            LogMgr.log(5, "000");
            try {
                try {
                    this.mOnSemClientEventListener.onEventNotify(semClientNotifyEventInfo);
                    if (!(this.mOnSemClientEventListener instanceof InternalSemClientEventListener)) {
                        synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                            ISemClientImpl.this.mIsRunningConnectThread.compareAndSet(true, false);
                            ISemClientImpl.this.mIsRunningStartTsmSequence.compareAndSet(true, false);
                            ISemClientImpl.this.mInstallAppletThread = null;
                            ISemClientImpl.this.mUpgradeAppletThread = null;
                            ISemClientImpl.this.mDeleteAppletThread = null;
                            ISemClientImpl.this.mGetAppletStatusThread = null;
                            ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch();
                        }
                    }
                    LogMgr.log(5, "999");
                } catch (Exception e) {
                    LogMgr.log(1, "800 Exception occurred. e=" + e);
                    throw e;
                }
            } catch (Throwable th) {
                if (!(this.mOnSemClientEventListener instanceof InternalSemClientEventListener)) {
                    synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                        ISemClientImpl.this.mIsRunningConnectThread.compareAndSet(true, false);
                        ISemClientImpl.this.mIsRunningStartTsmSequence.compareAndSet(true, false);
                        ISemClientImpl.this.mInstallAppletThread = null;
                        ISemClientImpl.this.mUpgradeAppletThread = null;
                        ISemClientImpl.this.mDeleteAppletThread = null;
                        ISemClientImpl.this.mGetAppletStatusThread = null;
                        ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch();
                    }
                }
                throw th;
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999");
            return this.mOnSemClientEventListener.asBinder();
        }
    }

    private static class InternalSemClientEventListener implements ISemClientEventListener {
        private String mAdditionalErrInfo;
        private CountDownLatch mCountDownLatch;
        private int mErrCode;
        private String mMethodName;
        private String mMsg;

        private InternalSemClientEventListener() {
            this.mErrCode = 900;
            this.mAdditionalErrInfo = "";
            this.mMsg = "";
            this.mMethodName = "";
        }

        public int getErrCode() {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999 mErrCode[" + this.mErrCode + "]");
            return this.mErrCode;
        }

        public String getMsg() {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999 mMsg[" + this.mMsg + "]");
            return this.mMsg;
        }

        public String getAdditionalErrInfo() {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999 mAdditionalErrInfo[" + this.mAdditionalErrInfo + "]");
            return this.mAdditionalErrInfo;
        }

        public String getMethodName() {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999 mMethodName[" + this.mMethodName + "]");
            return this.mMethodName;
        }

        public void setCountDownLatch(CountDownLatch countDownLatch) {
            this.mCountDownLatch = countDownLatch;
        }

        public boolean isSuccessToGetConfiguration() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_CLIENT_CONFIGURATION_LISTENER_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToRegisterDeviceToken() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_REGISTER_DEVICE_TOKEN_LISTENER_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToGetProcessStatus() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_PROCESS_STATUS_LISTENER_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToGetUniqueValue() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_UNIQUE_VALUE_LISTENER_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToInstallApplet() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToUpgradeApplet() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToDeleteApplet() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToGetAppletStatus() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        public boolean isSuccessToStartTsmSequence() {
            LogMgr.log(5, "000");
            boolean zEquals = this.mMethodName.equals(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_FINISHED);
            LogMgr.log(5, "999 isSuccess[" + zEquals + "]");
            return zEquals;
        }

        /* JADX DEBUG: Another duplicated slice has different insns count: {[IGET]}, finally: {[IGET, INVOKE, IF] complete} */
        /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [6373=4] */
        @Override // com.felicanetworks.semc.ISemClientEventListener
        public void onEventNotify(SemClientNotifyEventInfo semClientNotifyEventInfo) {
            CountDownLatch countDownLatch;
            CountDownLatch countDownLatch2;
            LogMgr.log(5, "000");
            try {
                try {
                } catch (Exception unused) {
                    LogMgr.log(1, "800 Invalid param Error. semClientNotifyEventInfo is null.");
                    this.mErrCode = 900;
                    this.mMsg = ObfuscatedMsgUtil.executionPoint();
                    this.mAdditionalErrInfo = "";
                    countDownLatch = this.mCountDownLatch;
                    if (countDownLatch != null) {
                    }
                }
                if (semClientNotifyEventInfo == null) {
                    LogMgr.log(1, "800 Invalid param Error. semClientNotifyEventInfo is null.");
                    this.mErrCode = 900;
                    this.mMsg = ObfuscatedMsgUtil.executionPoint();
                    this.mAdditionalErrInfo = "";
                    if (countDownLatch2 != null) {
                        return;
                    } else {
                        return;
                    }
                }
                this.mErrCode = semClientNotifyEventInfo.getErrorCode();
                this.mMsg = semClientNotifyEventInfo.getErrorMessage();
                this.mAdditionalErrInfo = semClientNotifyEventInfo.getErrorAdditionalInformation();
                this.mMethodName = semClientNotifyEventInfo.getCallbackName();
                countDownLatch = this.mCountDownLatch;
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
                LogMgr.log(5, "999");
            } finally {
                countDownLatch2 = this.mCountDownLatch;
                if (countDownLatch2 != null) {
                    countDownLatch2.countDown();
                }
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999");
            return null;
        }
    }

    private class OnFinishListener implements SwsClientFacade.OnFinishListener {
        private ISemClientEventListener mOnSemClientEventListener;

        OnFinishListener(ISemClientEventListener iSemClientEventListener) {
            LogMgr.log(5, "000");
            this.mOnSemClientEventListener = iSemClientEventListener;
            LogMgr.log(5, "999");
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onStartTsmSequenceFinished(boolean z, int i, String str, String str2) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            LogMgr.performanceIn("API", "OnTsmSequenceListener", "onFinished");
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_FINISHED, 0, "", ""));
                            LogMgr.performanceOut("API", "OnTsmSequenceListener", "onFinished");
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            String str3 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR;
                            String str4 = SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_ERROR;
                            if (i == 600 || i == 601) {
                                str3 = SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR_AND_DO_WORK_MANAGER_RETRY;
                                str4 = SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_ERROR_AND_DO_WORK_MANAGER_RETRY;
                            }
                            LogMgr.performanceIn("API", "OnTsmSequenceListener", str3, " errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str + "] message[" + str2 + "]");
                            LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, iConvertFromErrorTypeConst);
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(str4, iConvertFromErrorTypeConst, str, str2));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "OnTsmSequenceListener", str3);
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                LogMgr.performanceIn("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + ObfuscatedMsgUtil.executionPoint(e) + "]");
                                LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.START_TSM_SEQUENCE, 900);
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_ERROR, 900, "", str2));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                        } finally {
                            LogMgr.performanceOut("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onGetClientConfigurationFinished(boolean z, String str, int i, String str2, String str3) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    if (z) {
                        try {
                            JwsObject jwsObject = new JwsObject(str);
                            jwsObject.getPayloadWithVerify(SettingInfo.getServerPublicKeysForClientConfig());
                            ISemClientImpl.this.mSharedPrefsUtil.writeSaveDate(System.currentTimeMillis() / 1000);
                            ClientConfigurationJson clientConfigurationJson = new ClientConfigurationJson(jwsObject.getPayload());
                            clientConfigurationJson.checkMembers();
                            ISemClientImpl.this.mSharedPrefsUtil.writeSpAppInfoList(clientConfigurationJson.getSpAppInfoList());
                            ISemClientImpl.this.mSharedPrefsUtil.writeControlInfo(clientConfigurationJson.getClientControlInfo());
                            ISemClientImpl.this.mSharedPrefsUtil.writeSpAppletInfoList(clientConfigurationJson.getSpAppletInfoList());
                            ISemClientImpl.this.mSharedPrefsUtil.deleteOtherClientConfigInfoFiles();
                            try {
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_CLIENT_CONFIGURATION_LISTENER_ON_FINISHED, 0, "", ""));
                            } catch (Exception e) {
                                LogMgr.log(1, "801 Exception occurred. e=" + e);
                            }
                        } catch (JSONException e2) {
                            LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            LogMgr.printStackTrace(9, e2.getCause());
                            try {
                                LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.CONNECT, 900);
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_ERROR, 900, "", ObfuscatedMsgUtil.executionPoint(e2)));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e3) {
                                LogMgr.log(1, "803 Exception occurred. e=" + e3);
                            }
                        }
                    } else {
                        try {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.CONNECT, iConvertFromErrorTypeConst);
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_ERROR, iConvertFromErrorTypeConst, str2, str3));
                            this.mOnSemClientEventListener = null;
                        } catch (Exception e4) {
                            LogMgr.log(1, "804 Exception occurred. e=" + e4);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onRegisterDeviceTokenFinished(boolean z, String str, int i, String str2, String str3) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            ISemClientImpl.this.mSharedPrefsUtil.writeDeviceToken(str);
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_REGISTER_DEVICE_TOKEN_LISTENER_ON_FINISHED, 0, "", ""));
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.performanceIn("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str2 + "] message[" + str3 + "]");
                            LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.CONNECT, iConvertFromErrorTypeConst);
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_ERROR, iConvertFromErrorTypeConst, str2, str3));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                LogMgr.performanceIn("API", "OnConnectedListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + ObfuscatedMsgUtil.executionPoint(e) + "]");
                                LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.CONNECT, 900);
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_ERROR, 900, "", str3));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                        } finally {
                            LogMgr.performanceOut("API", "OnConnectedListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onNotifyClientEventFinished(boolean z, int i, String str, String str2) {
            LogMgr.log(5, "000");
            if (ISemClientImpl.this.mSwsClientFacade != null) {
                ISemClientImpl.this.mSwsClientFacade.finish();
            }
            try {
                if (z) {
                    LogMgr.performanceIn("API", "OnNotifyClientEventListener", "onFinished");
                    this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_NOTIFIED));
                    LogMgr.performanceOut("API", "OnNotifyClientEventListener", "onFinished");
                } else {
                    int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                    LogMgr.performanceIn("API", "OnNotifyClientEventListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, " errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str + "] message[" + str2 + "]");
                    LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.NOTIFY_CLIENT_EVENT, iConvertFromErrorTypeConst);
                    this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_ERROR, iConvertFromErrorTypeConst, str, str2));
                    this.mOnSemClientEventListener = null;
                    LogMgr.performanceOut("API", "OnNotifyClientEventListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                }
            } catch (Exception e) {
                LogMgr.log(1, "801 Exception occurred. e=" + e);
                LogMgr.printStackTrace(9, e.getCause());
                try {
                    try {
                        LogMgr.performanceIn("API", "OnNotifyClientEventListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + ObfuscatedMsgUtil.executionPoint(e) + "]");
                        LogMgr.exLogOnError(SemClientExternalLogConst.SemcApi.NOTIFY_CLIENT_EVENT, 900);
                        this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT_LISTENER_ON_ERROR, 900, "", str2));
                        this.mOnSemClientEventListener = null;
                    } finally {
                        LogMgr.performanceOut("API", "OnNotifyClientEventListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                    }
                } catch (Exception e2) {
                    LogMgr.log(1, "802 Exception occurred. e=" + e2);
                }
            }
            LogMgr.log(5, "999");
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onGetUniqueValueFinished(boolean z, String str, int i, String str2, String str3) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            LogMgr.performanceIn("API", "onGetUniqueValueListener", "onFinished");
                            ISemClientImpl.this.mUniqueValue = str;
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_UNIQUE_VALUE_LISTENER_ON_FINISHED, 0, "", ""));
                            LogMgr.performanceOut("API", "onGetUniqueValueListener", "onFinished");
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.performanceIn("API", "OnGetUniqueValueListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str2 + "] message[" + str3 + "]");
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_UNIQUE_VALUE_LISTENER_ON_ERROR, iConvertFromErrorTypeConst, str2, str3));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "OnGetUniqueValueListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                String strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                                LogMgr.performanceIn("API", "OnGetUniqueValueListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + strExecutionPoint + "]");
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_UNIQUE_VALUE_LISTENER_ON_ERROR, 900, "", strExecutionPoint));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                        } finally {
                            LogMgr.performanceOut("API", "OnGetUniqueValueListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onGetProcessStatusFinished(boolean z, String str, String str2, String str3, String str4, JSONArray jSONArray, JSONObject jSONObject, int i, String str5, String str6) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            LogMgr.performanceIn("API", "onGetProcessStatusListener", "onFinished");
                            ISemClientImpl.this.mProcessStatus = str;
                            ISemClientImpl.this.mProcessResultCode = str2;
                            ISemClientImpl.this.mProcessResultDetailCode = str3;
                            ISemClientImpl.this.mProcessResultMessageString = str4;
                            ISemClientImpl.this.mSdKeyDerivationDataList = jSONArray;
                            ISemClientImpl.this.mAppletStatus = jSONObject;
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_PROCESS_STATUS_LISTENER_ON_FINISHED, 0, "", ""));
                            LogMgr.performanceOut("API", "onGetProcessStatusListener", "onFinished");
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.performanceIn("API", "onGetProcessStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str5 + "] message[" + str6 + "]");
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_PROCESS_STATUS_LISTENER_ON_ERROR, iConvertFromErrorTypeConst, str5, str6));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "onGetProcessStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                String strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                                LogMgr.performanceIn("API", "onGetProcessStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + strExecutionPoint + "]");
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_PROCESS_STATUS_LISTENER_ON_ERROR, 900, "", strExecutionPoint));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                            LogMgr.performanceOut("API", "onGetProcessStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        } catch (Throwable th) {
                            LogMgr.performanceOut("API", "onGetProcessStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                            throw th;
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onInstallAppletFinished(boolean z, String str, String str2, int i, String str3, String str4) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            LogMgr.performanceIn("API", "OnInstallAppletListener", "onFinished");
                            ISemClientImpl.this.mLinkageData = str;
                            ISemClientImpl.this.mProcessId = str2;
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_FINISHED, 0, "", ""));
                            LogMgr.performanceOut("API", "OnInstallAppletListener", "onFinished");
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.performanceIn("API", "OnInstallAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str3 + "] message[" + str4 + "]");
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_ERROR, iConvertFromErrorTypeConst, str3, str4));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "OnInstallAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                String strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                                LogMgr.performanceIn("API", "OnInstallAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + strExecutionPoint + "]");
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_ERROR, 900, "", strExecutionPoint));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                        } finally {
                            LogMgr.performanceOut("API", "OnInstallAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onUpgradeAppletFinished(boolean z, String str, String str2, int i, String str3, String str4) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            LogMgr.performanceIn("API", "OnUpgradeAppletListener", "onFinished");
                            ISemClientImpl.this.mLinkageData = str;
                            ISemClientImpl.this.mProcessId = str2;
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_FINISHED, 0, "", ""));
                            LogMgr.performanceOut("API", "OnUpgradeAppletListener", "onFinished");
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.performanceIn("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str3 + "] message[" + str4 + "]");
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_ERROR, iConvertFromErrorTypeConst, str3, str4));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                String strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                                LogMgr.performanceIn("API", "OnUpgradeListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + strExecutionPoint + "]");
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_ERROR, 900, "", strExecutionPoint));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                        } finally {
                            LogMgr.performanceOut("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onDeleteAppletFinished(boolean z, String str, String str2, int i, String str3, String str4) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            LogMgr.performanceIn("API", "OnDeleteAppletListener", "onFinished");
                            ISemClientImpl.this.mLinkageData = str;
                            ISemClientImpl.this.mProcessId = str2;
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_FINISHED, 0, "", ""));
                            LogMgr.performanceOut("API", "OnDeleteAppletListener", "onFinished");
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.performanceIn("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str3 + "] message[" + str4 + "]");
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_ERROR, iConvertFromErrorTypeConst, str3, str4));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                String strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                                LogMgr.performanceIn("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + strExecutionPoint + "]");
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_ERROR, 900, "", strExecutionPoint));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                        } finally {
                            LogMgr.performanceOut("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }

        @Override // com.felicanetworks.semc.sws.SwsClientFacade.OnFinishListener
        public void onGetAppletStatusFinished(boolean z, String str, String str2, int i, String str3, String str4) {
            LogMgr.log(5, "000");
            synchronized (ISemClientImpl.this.mTaskInterruptedLatchManager) {
                if (!ISemClientImpl.this.mTaskInterruptedLatchManager.countDownTaskInterruptedLatch()) {
                    if (ISemClientImpl.this.mSwsClientFacade != null) {
                        ISemClientImpl.this.mSwsClientFacade.finish();
                    }
                    try {
                        if (z) {
                            LogMgr.performanceIn("API", "OnGetAppletStatusListener", "onFinished");
                            ISemClientImpl.this.mLinkageData = str;
                            ISemClientImpl.this.mProcessId = str2;
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED, 0, "", ""));
                            LogMgr.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
                        } else {
                            int iConvertFromErrorTypeConst = ErrorCodeConverter.convertFromErrorTypeConst(i);
                            LogMgr.performanceIn("API", "OnGetAppletStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + iConvertFromErrorTypeConst + "] additionalErrorInfo[ " + str3 + "] message[" + str4 + "]");
                            this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_ERROR, iConvertFromErrorTypeConst, str3, str4));
                            this.mOnSemClientEventListener = null;
                            LogMgr.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
                        }
                    } catch (Exception e) {
                        LogMgr.log(1, "801 Exception occurred. e=" + e);
                        LogMgr.printStackTrace(9, e.getCause());
                        try {
                            try {
                                String strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                                LogMgr.performanceIn("API", "OnGetAppletStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[900] message[" + strExecutionPoint + "]");
                                this.mOnSemClientEventListener.onEventNotify(new SemClientNotifyEventInfo(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_ERROR, 900, "", strExecutionPoint));
                                this.mOnSemClientEventListener = null;
                            } catch (Exception e2) {
                                LogMgr.log(1, "802 Exception occurred. e=" + e2);
                            }
                        } finally {
                            LogMgr.performanceOut("API", "OnGetAppletStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
                        }
                    }
                    LogMgr.log(5, "999");
                    return;
                }
                LogMgr.log(5, "998");
            }
        }
    }
}
