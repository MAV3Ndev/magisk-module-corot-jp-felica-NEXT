package com.felicanetworks.semc;

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
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.felicanetworks.semc.ISemClient;
import com.felicanetworks.semc.ISemClientEventListener;
import com.felicanetworks.semc.util.LogMgrUtil;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SemClientUtil;
import com.felicanetworks.semc.util.SignatureUtil;
import com.felicanetworks.tis.LogSender$$ExternalSyntheticBackport0;
import com.google.common.base.Ascii;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class SemClient {
    private static final String COLUMN_CONNECTION_APP_INFO = "connectionAppInfo";
    private static final int CONNECTION_APP_INFO_ERROR = -1;
    private static final int CONNECTION_APP_INFO_MENU_APP = 1;
    private static final int CONNECTION_APP_INFO_SEMC_APP = 2;
    public static final int CONTACTLESS_INTERFACE_STATUS_ACTIVATED = 1;
    public static final int CONTACTLESS_INTERFACE_STATUS_DEACTIVATED = 0;
    public static final int CONTACTLESS_INTERFACE_STATUS_NON_ACTIVATABLE = 128;
    public static final int CONTACTLESS_INTERFACE_STATUS_UNKNOWN = -1;
    static final int DEFAULT_BIND_TIMEOUT = 10000;
    private static final String EXC_ILLEGAL_STATE_ALREADY_CONNECTED = "Illegal state already connected.";
    private static final String EXC_ILLEGAL_STATE_CURRENTLY_CONNECTING = "Illegal state currently connecting.";
    private static final String EXC_ILLEGAL_STATE_NOT_CONNECTED = "Illegal state not connected.";
    private static final String EXC_ILLEGAL_STATE_SEM_SEQUENCE_STARTED = "Illegal state Sem Sequence started.";
    private static final String EXC_INVALID_AID = "The specified AID is null or invalid.";
    private static final String EXC_INVALID_ARGUMENT_VALUE = "The specified ArgumentValue is invalid.";
    private static final String EXC_INVALID_CONTEXT = "The specified Context is null.";
    private static final String EXC_INVALID_LINKAGE_DATA = "The specified LinkageData is null or invalid.";
    protected static final String EXC_INVALID_LISTENER = "The specified Listener is null.";
    protected static final String EXC_INVALID_MODE = "The specified parameter mode is invalid.";
    private static final String FILE_NAME = "common.cfg";
    private static final int LENGTH_ARRAY_VERSION = 3;
    private static final String MENU_APP_PACKAGE_NAME = "com.felicanetworks.mfm.main";

    @Deprecated
    public static final int MODE_FLAG_AVOID_BACKGROUND_RESTRICTION = 1;
    public static final int MODE_FLAG_FOR_GLOBAL_DEVICE = 4;
    private static final String PATH_CONNECTION_APP_INFO = "ConnectionAppInfo";
    protected static final int SEMC_API_VERSION = 2;
    private static final String SEM_CLIENT_ADAPTER_CLASS_NAME = "com.felicanetworks.semc.SemClientAdapter";
    private static final String SEM_CLIENT_APP_PACKAGE_NAME = "com.felicanetworks.semcapp";
    private static final String SEM_CLIENT_VERSION_ADD_INFO_RESOURCE_NAME = "additionalInformation";
    private static final String SEM_CLIENT_VERSION_ADD_INFO_RESOURCE_TYPE = "string";
    private static final String SEM_CLIENT_VERSION_RESOURCE_NAME = "sem_client_version";
    private static final String SEM_CLIENT_VERSION_RESOURCE_TYPE = "string";
    private static final int SW_6330 = 25392;
    private static final int SW_9000 = 36864;
    private static final String URI_CONNECTION_APP_INFO_MENU_APP = "content://com.felicanetworks.mfm.main.util.ConnectionAppInfoContentProvider/ConnectionAppInfo";
    private static final String URI_CONNECTION_APP_INFO_SEMC_APP = "content://com.felicanetworks.semcapp.util.ConnectionAppInfoContentProvider/ConnectionAppInfo";
    private static final String VERSION_DELIMITER = ".";
    private static final String VERSION_DELIMITER_REGEX = "\\.";
    static final int bindTimeout = 10000;
    private static SemClientVersion mSemClientVersion;
    private static SemClient sInstance;
    private SemClientApiInfo mConnectApiInfo;
    private String mConnectSemcPackageName;
    private WeakReference<Context> mContext;
    protected OnConnectedListener mOnConnectedListener;
    private OnDeleteAppletListener mOnDeleteAppletListener;
    private OnGetAppletStatusListener mOnGetAppletStatusListener;
    private OnInstallAppletListener mOnInstallAppletListener;
    private OnTsmSequenceListener mOnTsmSequenceListener;
    private OnUpgradeAppletListener mOnUpgradeAppletListener;
    private String mPackageName = "";
    protected boolean mOnline = false;
    private final BindTimerHandler mBindTimerHandler = new BindTimerHandler(Looper.getMainLooper());
    private final SemClientConnection mConnectionHooker = new SemClientConnection();
    protected final ISemClientEventListener mSemClientEventListener = new SemClientEventListenerStub();
    protected ISemClient mSemClientService = null;

    public interface OnConnectedListener {
        void onConnected();

        void onError(int i, String str, String str2);
    }

    public interface OnDeleteAppletListener extends OnlineProcessStatusValue {
        void onError(int i, String str, String str2);

        void onFinished(String str, String str2, String str3, String str4);
    }

    public interface OnGetAppletStatusListener extends OnlineProcessStatusValue {
        void onError(int i, String str, String str2);

        void onFinished(String str, String str2, String str3, String str4, String[] strArr, AppletStatus[] appletStatusArr, String[] strArr2);
    }

    public interface OnInstallAppletListener extends OnlineProcessStatusValue {
        void onError(int i, String str, String str2);

        void onFinished(String str, String str2, String str3, String str4, SdKeyDerivationData[] sdKeyDerivationDataArr);
    }

    public interface OnTsmSequenceListener {
        void onError(int i, String str, String str2);

        void onFinished();
    }

    public interface OnUpgradeAppletListener extends OnlineProcessStatusValue {
        void onError(int i, String str, String str2);

        void onFinished(String str, String str2, String str3, String str4);
    }

    public interface OnlineProcessStatusValue {
        public static final String PROCESS_STATUS_FINISHED = "Finished";
        public static final String PROCESS_STATUS_NOT_STARTED = "NotStarted";
        public static final String PROCESS_STATUS_PROCESSING = "Processing";
    }

    protected SemClient() {
        LogMgrUtil.log(5, "000");
        LogMgrUtil.log(5, "999");
    }

    public static synchronized SemClient getInstance() {
        LogMgrUtil.log(5, "000");
        if (sInstance == null) {
            LogMgrUtil.log(9, "001");
            sInstance = new SemClient();
        }
        LogMgrUtil.log(5, "999");
        return sInstance;
    }

    public synchronized void connect(Context context, OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        LogMgrUtil.log(5, "000");
        SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_CONNECT);
        semClientApiInfo.setIsModeExists(false);
        connect(context, semClientApiInfo, onConnectedListener);
        LogMgrUtil.log(5, "999");
    }

    public synchronized void connect(Context context, OnConnectedListener onConnectedListener, int i) throws IllegalStateException, SemClientException, IllegalArgumentException {
        LogMgrUtil.log(5, "000");
        if (i == 0) {
            LogMgrUtil.log(2, "700 Parameter Error. mode was zero");
            throw new IllegalArgumentException("The specified parameter mode is invalid.");
        }
        SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_CONNECT);
        semClientApiInfo.setIsModeExists(true);
        semClientApiInfo.setMode(i);
        connect(context, semClientApiInfo, onConnectedListener);
        LogMgrUtil.log(5, "999");
    }

    void connect(Context context, SemClientApiInfo semClientApiInfo, OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder("context[");
        sb.append(context != null);
        sb.append("] apiInfo[");
        sb.append(semClientApiInfo != null);
        sb.append("] listener[");
        sb.append(onConnectedListener != null);
        sb.append("]");
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_CONNECT, sb.toString());
        LogMgrUtil.log(5, "000");
        if (onConnectedListener == null) {
            LogMgrUtil.log(2, "700 Parameter Error. listener is null");
            throw new IllegalArgumentException("The specified Listener is null.");
        }
        if (context == null) {
            LogMgrUtil.log(2, "701 Parameter Error. context is null");
            throw new IllegalArgumentException("The specified Context is null.");
        }
        checkNotConnected();
        synchronized (this) {
            this.mConnectApiInfo = semClientApiInfo;
        }
        init(context);
        try {
            try {
                this.mOnConnectedListener = onConnectedListener;
                bindSemClient(false, this.mContext.get(), this.mConnectApiInfo, this.mOnConnectedListener);
                LogMgrUtil.log(5, "999");
                LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_CONNECT);
            } catch (SemClientException e) {
                LogMgrUtil.log(2, "702 SemClientException id:" + e.getErrorCode() + "message:" + e.getMessage());
                throw e;
            } catch (IllegalArgumentException e2) {
                LogMgrUtil.log(2, "703 IllegalArgumentException message:" + e2.getMessage());
                throw e2;
            }
        } catch (Throwable th) {
            this.mOnConnectedListener = null;
            throw th;
        }
    }

    public synchronized void disconnect() throws SemClientException {
        LogMgrUtil.log(5, "000");
        disconnect(new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_DISCONNECT));
        LogMgrUtil.log(5, "999");
    }

    void disconnect(SemClientApiInfo semClientApiInfo) throws SemClientException {
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_DISCONNECT);
        LogMgrUtil.log(5, "000");
        if (checkAfterConnecting()) {
            if (checkConnecting()) {
                unbindSemClient();
                return;
            }
            try {
                SemClientUtil.checkSemClientResult(this.mSemClientService.callSemClientApi(semClientApiInfo, null, 2));
                unbindSemClient();
                LogMgrUtil.log(5, "999");
                LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_DISCONNECT);
            } catch (SemClientException e) {
                LogMgrUtil.log(2, "700 SemClientException id:" + e.getErrorCode() + "message:" + e.getMessage());
                throw e;
            } catch (IllegalStateException e2) {
                LogMgrUtil.log(2, "701 " + e2.toString() + " message:" + e2.getMessage());
                if ("Illegal state not connected.".equals(e2.getMessage())) {
                    LogMgrUtil.log(9, "998");
                    return;
                }
                throw e2;
            } catch (Exception unused) {
                LogMgrUtil.log(2, "702 Other Exception");
                throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
            }
        }
    }

    public synchronized String getSeid() throws IllegalStateException, SemClientException {
        String stringReturnValue;
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_SEID);
        LogMgrUtil.log(5, "000");
        checkConnected(true);
        try {
            try {
                SemClientResultInfo semClientResultInfoCallSemClientApi = this.mSemClientService.callSemClientApi(new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_GET_SEID), null, 2);
                SemClientUtil.checkSemClientResult(semClientResultInfoCallSemClientApi);
                stringReturnValue = semClientResultInfoCallSemClientApi.getStringReturnValue();
                LogMgrUtil.log(5, "999 returned " + stringReturnValue);
                LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_SEID, "seid[" + stringReturnValue + "]");
            } catch (SemClientException e) {
                LogMgrUtil.log(2, "700 SemClientException id:" + e.getErrorCode() + "message:" + e.getMessage());
                throw e;
            }
        } catch (IllegalStateException e2) {
            LogMgrUtil.log(2, "701 " + e2.toString() + " message:" + e2.getMessage());
            throw e2;
        } catch (Exception unused) {
            LogMgrUtil.log(2, "702 Other Exception");
            throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
        }
        return stringReturnValue;
    }

    public static SemClientVersion getSemClientVersion(Context context) throws SemClientException, IllegalArgumentException {
        LogMgrUtil.log(5, "000");
        if (context == null) {
            LogMgrUtil.log(2, "700 context is null.");
            throw new IllegalArgumentException("The specified Context is null.");
        }
        try {
            if (getSemClientService(context, "com.felicanetworks.mfm.main") == null) {
                LogMgrUtil.log(2, "701 service not found");
                throw new SemClientException(501, ObfuscatedMsgUtil.executionPoint());
            }
            SemClientVersion semClientVersionResource = getSemClientVersionResource(context, "com.felicanetworks.mfm.main");
            LogMgrUtil.log(5, "999 version : " + semClientVersionResource.version + " majorVersionCode : " + semClientVersionResource.majorVersionCode + " minorVersionCode : " + semClientVersionResource.minorVersionCode + " revisionVersionCode : " + semClientVersionResource.revisionVersionCode + " additionalInformation : " + semClientVersionResource.additionalInformation);
            return semClientVersionResource;
        } catch (Exception e) {
            LogMgrUtil.log(2, "703 " + e.toString());
            throw new SemClientException(501, ObfuscatedMsgUtil.executionPoint(e));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static SemClientVersion getSemClientVersionForGlobal(Context context) throws Throwable {
        Cursor cursor;
        String str;
        int i;
        StringBuilder sb = new StringBuilder("context[");
        sb.append(context != null);
        sb.append("]");
        LogMgrUtil.performanceIn("API", "SemClient", "getSemClientVersionForGlobal", sb.toString());
        LogMgrUtil.log(5, "000");
        String str2 = "com.felicanetworks.mfm.main";
        if (context == null) {
            LogMgrUtil.log(2, "700 context is null.");
            throw new IllegalArgumentException("The specified Context is null.");
        }
        try {
            try {
                boolean z = getSemClientService(context, "com.felicanetworks.mfm.main") != null;
                boolean z2 = getSemClientService(context, "com.felicanetworks.semcapp") != null;
                if (!z && !z2) {
                    LogMgrUtil.log(2, "701 service not found in both app.");
                    throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
                }
                if (z) {
                    str = URI_CONNECTION_APP_INFO_MENU_APP;
                    LogMgrUtil.log(8, "001 set URI for Menu app.");
                } else {
                    str = URI_CONNECTION_APP_INFO_SEMC_APP;
                    LogMgrUtil.log(8, "002 set URI for SEMC app.");
                }
                Cursor cursorQuery = context.getContentResolver().query(Uri.parse(str), null, null, null, null);
                try {
                    LogMgrUtil.log(9, "003 context.getContentResolver.query() out.");
                    if (cursorQuery == null || !cursorQuery.moveToFirst()) {
                        i = 0;
                    } else {
                        int columnIndex = cursorQuery.getColumnIndex(COLUMN_CONNECTION_APP_INFO);
                        i = columnIndex >= 0 ? cursorQuery.getInt(columnIndex) : 0;
                        LogMgrUtil.log(8, "004 : connectionAppInfo = " + i);
                    }
                    if (i == -1) {
                        LogMgrUtil.log(2, "703 Error occurred in SEMC content provider.");
                        throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                    }
                    if (i != 1) {
                        if (i != 2) {
                            LogMgrUtil.log(2, "704 invalid connection app info. val=" + i);
                            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                        }
                        if (!z2) {
                            LogMgrUtil.log(2, "702 service not found in SEMC app.");
                            throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
                        }
                        LogMgrUtil.log(8, "006 : get res app is SEMC app.");
                        str2 = "com.felicanetworks.semcapp";
                    } else {
                        if (!z) {
                            LogMgrUtil.log(2, "702 service not found in Menu app.");
                            throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
                        }
                        LogMgrUtil.log(8, "005 : get res app is Menu app.");
                    }
                    SemClientVersion semClientVersionResourceForGlobal = getSemClientVersionResourceForGlobal(context, str2);
                    if (cursorQuery != null) {
                        cursorQuery.close();
                        LogMgrUtil.log(8, "005 close cursor.");
                    }
                    LogMgrUtil.log(5, "999 version : " + semClientVersionResourceForGlobal.version + " majorVersionCode : " + semClientVersionResourceForGlobal.majorVersionCode + " minorVersionCode : " + semClientVersionResourceForGlobal.minorVersionCode + " revisionVersionCode : " + semClientVersionResourceForGlobal.revisionVersionCode + " additionalInformation : " + semClientVersionResourceForGlobal.additionalInformation);
                    LogMgrUtil.performanceOut("API", "SemClient", "getSemClientVersionForGlobal");
                    return semClientVersionResourceForGlobal;
                } catch (SemClientException e) {
                    throw e;
                } catch (Exception unused) {
                    LogMgrUtil.log(2, "704 Exception occurred.");
                    throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                    LogMgrUtil.log(8, "005 close cursor.");
                }
                throw th;
            }
        } catch (SemClientException e2) {
            throw e2;
        } catch (Exception unused2) {
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
            if (0 != 0) {
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SemClientVersion getSemClientVersionResource(Context context, String str) throws PackageManager.NameNotFoundException, SemClientException {
        LogMgrUtil.log(5, "000");
        Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(str);
        String string = resourcesForApplication.getString(resourcesForApplication.getIdentifier("sem_client_version", "string", str));
        int[] iArrSplitSemClientVersion = splitSemClientVersion(string);
        if (iArrSplitSemClientVersion == null) {
            LogMgrUtil.log(2, "702 SemClient version format error");
            throw new SemClientException(501, ObfuscatedMsgUtil.executionPoint());
        }
        String string2 = resourcesForApplication.getString(resourcesForApplication.getIdentifier("additionalInformation", "string", str));
        if (string2 != null) {
            string = LogSender$$ExternalSyntheticBackport0.m(VERSION_DELIMITER, new CharSequence[]{string, string2});
        }
        return new SemClientVersion(string, iArrSplitSemClientVersion[0], iArrSplitSemClientVersion[1], iArrSplitSemClientVersion[2], string2);
    }

    private static SemClientVersion getSemClientVersionResourceForGlobal(Context context, String str) throws PackageManager.NameNotFoundException, SemClientException {
        LogMgrUtil.log(5, "000");
        Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(str);
        String string = resourcesForApplication.getString(resourcesForApplication.getIdentifier("sem_client_version", "string", str));
        int[] iArrSplitSemClientVersion = splitSemClientVersion(string);
        if (iArrSplitSemClientVersion == null) {
            LogMgrUtil.log(2, "702 SemClient version format error");
            throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
        }
        String string2 = resourcesForApplication.getString(resourcesForApplication.getIdentifier("additionalInformation", "string", str));
        if (string2 != null) {
            string = LogSender$$ExternalSyntheticBackport0.m(VERSION_DELIMITER, new CharSequence[]{string, string2});
        }
        return new SemClientVersion(string, iArrSplitSemClientVersion[0], iArrSplitSemClientVersion[1], iArrSplitSemClientVersion[2], string2);
    }

    public synchronized SemClientStatus getSemClientStatus() {
        SemClientStatus semClientStatus;
        LogMgrUtil.log(5, "000");
        boolean zIsConnected = isConnected();
        semClientStatus = new SemClientStatus(zIsConnected, zIsConnected && isTsmSequenceStarted());
        LogMgrUtil.log(5, "999 connection:" + semClientStatus.isConnected + "started:" + semClientStatus.isTsmSequenceStarted);
        return semClientStatus;
    }

    public synchronized String getSeReaderName() throws IllegalStateException, SemClientException {
        String stringReturnValue;
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_SE_READER_NAME);
        LogMgrUtil.log(5, "000");
        checkConnected(true);
        try {
            try {
                SemClientResultInfo semClientResultInfoCallSemClientApi = this.mSemClientService.callSemClientApi(new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_GET_SE_READER_NAME), null, 2);
                SemClientUtil.checkSemClientResult(semClientResultInfoCallSemClientApi);
                stringReturnValue = semClientResultInfoCallSemClientApi.getStringReturnValue();
                LogMgrUtil.log(5, "999 returned " + stringReturnValue);
                LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_SE_READER_NAME, "seReaderName[" + stringReturnValue + "]");
            } catch (SemClientException e) {
                LogMgrUtil.log(2, "700 SemClientException id:" + e.getErrorCode() + "message:" + e.getMessage());
                throw e;
            }
        } catch (IllegalStateException e2) {
            LogMgrUtil.log(2, "701 " + e2.toString() + " message:" + e2.getMessage());
            throw e2;
        } catch (Exception unused) {
            LogMgrUtil.log(2, "702 Other Exception");
            throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
        }
        return stringReturnValue;
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public synchronized void startTsmSequence(String str, OnTsmSequenceListener onTsmSequenceListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        try {
            try {
                synchronized (this) {
                    StringBuilder sb = new StringBuilder("listener[");
                    sb.append(onTsmSequenceListener != null);
                    sb.append("] linkageData[");
                    sb.append(str);
                    sb.append("]");
                    LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE, sb.toString());
                    LogMgrUtil.log(5, "000");
                    checkStartTsmArguments(str, onTsmSequenceListener);
                    checkConnected(true);
                    synchronized (this) {
                        startOnline();
                        this.mOnTsmSequenceListener = onTsmSequenceListener;
                    }
                }
                SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
                semClientApiInfo.setLinkageData(str);
                SemClientUtil.checkSemClientResult(this.mSemClientService.callSemClientApi(semClientApiInfo, this.mSemClientEventListener, 2));
                LogMgrUtil.log(5, "999");
                LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE);
            } catch (SemClientException e) {
                LogMgrUtil.log(2, "703 " + e.toString() + " id:" + e.getErrorCode() + " message:" + e.getMessage());
                throw e;
            } catch (IllegalArgumentException e2) {
                e = e2;
                LogMgrUtil.log(2, "704 " + e.toString() + " message:" + e.getMessage());
                throw e;
            } catch (IllegalStateException e3) {
                e = e3;
                LogMgrUtil.log(2, "704 " + e.toString() + " message:" + e.getMessage());
                throw e;
            } catch (Exception e4) {
                LogMgrUtil.log(2, "705 Other Exception");
                LogMgrUtil.printStackTrace(9, e4);
                throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (Throwable th) {
            synchronized (this) {
                stopOnline();
                this.mOnTsmSequenceListener = null;
                throw th;
            }
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public synchronized void installApplet(String str, String str2, String str3, String str4, String[] strArr, String str5, String str6, OnInstallAppletListener onInstallAppletListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        try {
            try {
                synchronized (this) {
                    StringBuilder sb = new StringBuilder("serviceId[");
                    sb.append(str != null);
                    sb.append("] spAppletId[");
                    sb.append(str2 != null);
                    sb.append("] spAppletVersion[");
                    sb.append(str3 != null);
                    sb.append("] spSdKeyVersion[");
                    sb.append(str4 != null);
                    sb.append("] accessAllowedSpAppIdList[");
                    sb.append(strArr != null);
                    sb.append("] operationType[");
                    sb.append(str5 != null);
                    sb.append("] operationId[");
                    sb.append(str6 != null);
                    sb.append("] listener[");
                    sb.append(onInstallAppletListener != null);
                    sb.append("]");
                    LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_INSTALL_APPLET, sb.toString());
                    LogMgrUtil.log(5, "000");
                    checkAppletMethodArguments(str, str2, str5, str6);
                    if (str3 == null) {
                        LogMgrUtil.log(2, "705 spAppletVersion is null.");
                        throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
                    }
                    if (strArr == null) {
                        LogMgrUtil.log(2, "706 accessAllowedSpAppIdList is null.");
                        throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
                    }
                    if (onInstallAppletListener == null) {
                        LogMgrUtil.log(2, "707 listener is null.");
                        throw new IllegalArgumentException("The specified Listener is null.");
                    }
                    checkConnected(true);
                    synchronized (this) {
                        startOnline();
                        this.mOnInstallAppletListener = onInstallAppletListener;
                    }
                    return;
                }
                SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_INSTALL_APPLET);
                semClientApiInfo.setServiceId(str);
                semClientApiInfo.setSpAppletId(str2);
                semClientApiInfo.setSpAppletVersion(str3);
                semClientApiInfo.setSpSdKeyVersion(str4);
                semClientApiInfo.setAccessAllowedSpAppIdList(strArr);
                semClientApiInfo.setOperationType(str5);
                semClientApiInfo.setOperationId(str6);
                SemClientUtil.checkSemClientResult(this.mSemClientService.callSemClientApi(semClientApiInfo, this.mSemClientEventListener, 2));
                LogMgrUtil.log(5, "999");
                LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_INSTALL_APPLET);
                return;
            } catch (SemClientException e) {
                LogMgrUtil.log(2, "703 " + e + " id:" + e.getErrorCode() + " message:" + e.getMessage());
                throw e;
            } catch (IllegalArgumentException e2) {
                e = e2;
                LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                throw e;
            } catch (IllegalStateException e3) {
                e = e3;
                LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                throw e;
            } catch (Exception e4) {
                LogMgrUtil.log(2, "705 Other Exception");
                LogMgrUtil.printStackTrace(9, e4);
                throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (Throwable th) {
            synchronized (this) {
                stopOnline();
                this.mOnInstallAppletListener = null;
                throw th;
            }
        }
        checkSemClientVersion();
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public synchronized void deleteApplet(String str, String str2, String str3, String str4, String str5, OnDeleteAppletListener onDeleteAppletListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        try {
            try {
                try {
                    synchronized (this) {
                        StringBuilder sb = new StringBuilder("serviceId[");
                        sb.append(str != null);
                        sb.append("] spAppletId[");
                        sb.append(str2 != null);
                        sb.append("] spAppletVersion[");
                        sb.append(str3 != null);
                        sb.append("] operationType[");
                        sb.append(str4 != null);
                        sb.append("] operationId[");
                        sb.append(str5 != null);
                        sb.append("] listener[");
                        sb.append(onDeleteAppletListener != null);
                        sb.append("]");
                        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_DELETE_APPLET, sb.toString());
                        LogMgrUtil.log(5, "000");
                        checkAppletMethodArguments(str, str2, str4, str5);
                        if (onDeleteAppletListener == null) {
                            LogMgrUtil.log(2, "705 listener is null.");
                            throw new IllegalArgumentException("The specified Listener is null.");
                        }
                        checkConnected(true);
                        synchronized (this) {
                            startOnline();
                            this.mOnDeleteAppletListener = onDeleteAppletListener;
                        }
                        return;
                    }
                    SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
                    semClientApiInfo.setServiceId(str);
                    semClientApiInfo.setSpAppletId(str2);
                    if (str3 == null) {
                        str3 = "";
                    }
                    semClientApiInfo.setSpAppletVersion(str3);
                    semClientApiInfo.setOperationType(str4);
                    semClientApiInfo.setOperationId(str5);
                    SemClientUtil.checkSemClientResult(this.mSemClientService.callSemClientApi(semClientApiInfo, this.mSemClientEventListener, 2));
                    LogMgrUtil.log(5, "999");
                    LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_DELETE_APPLET);
                    return;
                } catch (SemClientException e) {
                    LogMgrUtil.log(2, "703 " + e + " id:" + e.getErrorCode() + " message:" + e.getMessage());
                    throw e;
                } catch (IllegalStateException e2) {
                    e = e2;
                    LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                    throw e;
                }
            } catch (IllegalArgumentException e3) {
                e = e3;
                LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                throw e;
            } catch (Exception e4) {
                LogMgrUtil.log(2, "705 Other Exception");
                LogMgrUtil.printStackTrace(9, e4);
                throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (Throwable th) {
            synchronized (this) {
                stopOnline();
                this.mOnDeleteAppletListener = null;
                throw th;
            }
        }
        checkSemClientVersion();
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public synchronized void upgradeApplet(String str, String str2, String str3, String str4, String str5, String str6, OnUpgradeAppletListener onUpgradeAppletListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        try {
            try {
                try {
                    synchronized (this) {
                        StringBuilder sb = new StringBuilder("serviceId[");
                        sb.append(str != null);
                        sb.append("] spAppletId[");
                        sb.append(str2 != null);
                        sb.append("] oldSpAppletVersion[");
                        sb.append(str3 != null);
                        sb.append("] newSpAppletVersion[");
                        sb.append(str4 != null);
                        sb.append("] operationType[");
                        sb.append(str5 != null);
                        sb.append("] operationId[");
                        sb.append(str6 != null);
                        sb.append("] listener[");
                        sb.append(onUpgradeAppletListener != null);
                        sb.append("]");
                        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET, sb.toString());
                        LogMgrUtil.log(5, "000");
                        checkAppletMethodArguments(str, str2, str5, str6);
                        if (str3 == null) {
                            LogMgrUtil.log(2, "705 oldSpAppletVersion is null.");
                            throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
                        }
                        if (str4 == null) {
                            LogMgrUtil.log(2, "706 newSpAppletVersion is null.");
                            throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
                        }
                        if (onUpgradeAppletListener == null) {
                            LogMgrUtil.log(2, "707 listener is null.");
                            throw new IllegalArgumentException("The specified Listener is null.");
                        }
                        checkConnected(true);
                        synchronized (this) {
                            startOnline();
                            this.mOnUpgradeAppletListener = onUpgradeAppletListener;
                        }
                        return;
                    }
                    SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
                    semClientApiInfo.setServiceId(str);
                    semClientApiInfo.setSpAppletId(str2);
                    semClientApiInfo.setOldAppletVersion(str3);
                    semClientApiInfo.setNewAppletVersion(str4);
                    semClientApiInfo.setOperationType(str5);
                    semClientApiInfo.setOperationId(str6);
                    SemClientUtil.checkSemClientResult(this.mSemClientService.callSemClientApi(semClientApiInfo, this.mSemClientEventListener, 2));
                    LogMgrUtil.log(5, "999");
                    LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_UPGRADE_APPLET);
                    return;
                } catch (SemClientException e) {
                    LogMgrUtil.log(2, "703 " + e + " id:" + e.getErrorCode() + " message:" + e.getMessage());
                    throw e;
                } catch (IllegalStateException e2) {
                    e = e2;
                    LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                    throw e;
                }
            } catch (IllegalArgumentException e3) {
                e = e3;
                LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                throw e;
            } catch (Exception e4) {
                LogMgrUtil.log(2, "705 Other Exception");
                LogMgrUtil.printStackTrace(9, e4);
                throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (Throwable th) {
            synchronized (this) {
                stopOnline();
                this.mOnUpgradeAppletListener = null;
                throw th;
            }
        }
        checkSemClientVersion();
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public synchronized void getAppletStatus(String str, String str2, String str3, String str4, OnGetAppletStatusListener onGetAppletStatusListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        try {
            try {
                try {
                    synchronized (this) {
                        StringBuilder sb = new StringBuilder("serviceId[");
                        sb.append(str != null);
                        sb.append("] spAppletId[");
                        sb.append(str2 != null);
                        sb.append("] operationType[");
                        sb.append(str3 != null);
                        sb.append("] operationId[");
                        sb.append(str4 != null);
                        sb.append("] listener[");
                        sb.append(onGetAppletStatusListener != null);
                        sb.append("]");
                        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS, sb.toString());
                        LogMgrUtil.log(5, "000");
                        checkAppletMethodArguments(str, str2, str3, str4);
                        if (onGetAppletStatusListener == null) {
                            LogMgrUtil.log(2, "705 listener is null.");
                            throw new IllegalArgumentException("The specified Listener is null.");
                        }
                        checkConnected(true);
                        synchronized (this) {
                            startOnline();
                            this.mOnGetAppletStatusListener = onGetAppletStatusListener;
                        }
                        return;
                    }
                    SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
                    semClientApiInfo.setServiceId(str);
                    semClientApiInfo.setSpAppletId(str2);
                    semClientApiInfo.setOperationType(str3);
                    semClientApiInfo.setOperationId(str4);
                    SemClientUtil.checkSemClientResult(this.mSemClientService.callSemClientApi(semClientApiInfo, this.mSemClientEventListener, 2));
                    LogMgrUtil.log(5, "999");
                    LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_APPLET_STATUS);
                    return;
                } catch (SemClientException e) {
                    LogMgrUtil.log(2, "703 " + e + " id:" + e.getErrorCode() + " message:" + e.getMessage());
                    throw e;
                }
            } catch (IllegalArgumentException e2) {
                e = e2;
                LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                throw e;
            } catch (IllegalStateException e3) {
                e = e3;
                LogMgrUtil.log(2, "704 " + e + " message:" + e.getMessage());
                throw e;
            } catch (Exception e4) {
                LogMgrUtil.log(2, "705 Other Exception");
                LogMgrUtil.printStackTrace(9, e4);
                throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
            }
        } catch (Throwable th) {
            synchronized (this) {
                stopOnline();
                this.mOnGetAppletStatusListener = null;
                throw th;
            }
        }
        checkSemClientVersion();
    }

    protected void checkStartTsmArguments(String str, OnTsmSequenceListener onTsmSequenceListener) throws IllegalArgumentException {
        LogMgrUtil.log(8, "000");
        if (str == null) {
            LogMgrUtil.log(2, "700 linkageData is null.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (str.isEmpty()) {
            LogMgrUtil.log(2, "701 linkageData is empty.");
            throw new IllegalArgumentException("The specified LinkageData is null or invalid.");
        }
        if (onTsmSequenceListener == null) {
            LogMgrUtil.log(2, "702 listener is null.");
            throw new IllegalArgumentException("The specified Listener is null.");
        }
        LogMgrUtil.log(8, "999");
    }

    private void checkAppletMethodArguments(String str, String str2, String str3, String str4) throws IllegalArgumentException {
        LogMgrUtil.log(8, "000");
        if (str == null) {
            LogMgrUtil.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
        }
        if (str2 == null) {
            LogMgrUtil.log(2, "701 spAppletId is null.");
            throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
        }
        if (str3 == null) {
            LogMgrUtil.log(2, "702 operationType is null.");
            throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
        }
        if (str4 == null) {
            LogMgrUtil.log(2, "703 operationId is null.");
            throw new IllegalArgumentException("The specified ArgumentValue is invalid.");
        }
        LogMgrUtil.log(8, "999");
    }

    public synchronized int getContactlessInterfaceStatus(String str) throws IllegalStateException, SemClientException, IllegalArgumentException {
        int intReturnValue;
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_CONTACTLESS_INTERFACE_STATUS, "aid[" + str + "]");
        intReturnValue = operateContactlessInterface(str, SemClientApiInfo.METHOD_NAME_GET_CONTACTLESS_INTERFACE_STATUS).getIntReturnValue();
        LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_GET_CONTACTLESS_INTERFACE_STATUS);
        return intReturnValue;
    }

    public synchronized void activateContactlessInterface(String str) throws IllegalStateException, SemClientException, IllegalArgumentException {
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_ACTIVATE_CONTACTLESS_INTERFACE, "aid[" + str + "]");
        operateContactlessInterface(str, SemClientApiInfo.METHOD_NAME_ACTIVATE_CONTACTLESS_INTERFACE);
        LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_ACTIVATE_CONTACTLESS_INTERFACE);
    }

    public synchronized void deactivateContactlessInterface(String str) throws IllegalStateException, SemClientException, IllegalArgumentException {
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_DEACTIVATE_CONTACTLESS_INTERFACE, "aid[" + str + "]");
        operateContactlessInterface(str, SemClientApiInfo.METHOD_NAME_DEACTIVATE_CONTACTLESS_INTERFACE);
        LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_DEACTIVATE_CONTACTLESS_INTERFACE);
    }

    private SemClientResultInfo operateContactlessInterface(String str, String str2) throws IllegalStateException, SemClientException, IllegalArgumentException {
        LogMgrUtil.log(5, "000");
        preCheckContactlessInterface(str);
        try {
            SemClientApiInfo semClientApiInfo = new SemClientApiInfo(str2);
            semClientApiInfo.setAid(str);
            SemClientResultInfo semClientResultInfoCallSemClientApi = this.mSemClientService.callSemClientApi(semClientApiInfo, null, 2);
            SemClientUtil.checkSemClientResult(semClientResultInfoCallSemClientApi);
            LogMgrUtil.log(5, "999");
            return semClientResultInfoCallSemClientApi;
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "700 SemClientException id:" + e.getErrorCode() + "message:" + e.getMessage());
            throw e;
        } catch (IllegalArgumentException e2) {
            e = e2;
            LogMgrUtil.log(2, "701 " + e.toString() + " message:" + e.getMessage());
            throw e;
        } catch (IllegalStateException e3) {
            e = e3;
            LogMgrUtil.log(2, "701 " + e.toString() + " message:" + e.getMessage());
            throw e;
        } catch (Exception e4) {
            LogMgrUtil.log(2, "702 Other Exception");
            throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint(e4));
        }
    }

    protected class SemClientEventListenerStub extends ISemClientEventListener.Stub {
        protected SemClientEventListenerStub() {
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX DEBUG: Multi-variable search result rejected for r0v48, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r0v49, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r2v1, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: java.lang.String */
        /* JADX DEBUG: Multi-variable search result rejected for r6v2, resolved type: java.lang.String[] */
        /* JADX DEBUG: Multi-variable search result rejected for r7v1, resolved type: com.felicanetworks.semc.SemClient$AppletStatus[] */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
        java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:116)
        	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:71)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:101:0x01a8  */
        /* JADX WARN: Removed duplicated region for block: B:105:0x01b2  */
        /* JADX WARN: Removed duplicated region for block: B:109:0x01bc  */
        /* JADX WARN: Removed duplicated region for block: B:115:0x01ce  */
        /* JADX WARN: Removed duplicated region for block: B:116:0x01e2  */
        /* JADX WARN: Removed duplicated region for block: B:118:0x0207  */
        /* JADX WARN: Removed duplicated region for block: B:119:0x0221  */
        /* JADX WARN: Removed duplicated region for block: B:120:0x023b  */
        /* JADX WARN: Removed duplicated region for block: B:121:0x0255  */
        /* JADX WARN: Removed duplicated region for block: B:122:0x026f  */
        /* JADX WARN: Removed duplicated region for block: B:126:0x0296  */
        /* JADX WARN: Removed duplicated region for block: B:130:0x02a3  */
        /* JADX WARN: Removed duplicated region for block: B:134:0x02ae  */
        /* JADX WARN: Removed duplicated region for block: B:138:0x02b9  */
        /* JADX WARN: Removed duplicated region for block: B:142:0x02c6  */
        /* JADX WARN: Removed duplicated region for block: B:147:0x02d1  */
        /* JADX WARN: Removed duplicated region for block: B:151:0x02db  */
        /* JADX WARN: Removed duplicated region for block: B:155:0x02e5  */
        /* JADX WARN: Removed duplicated region for block: B:159:0x02ef  */
        /* JADX WARN: Removed duplicated region for block: B:163:0x02f9  */
        /* JADX WARN: Removed duplicated region for block: B:167:0x0303  */
        /* JADX WARN: Removed duplicated region for block: B:171:0x030d  */
        /* JADX WARN: Removed duplicated region for block: B:176:0x0319  */
        /* JADX WARN: Removed duplicated region for block: B:177:0x0333  */
        /* JADX WARN: Removed duplicated region for block: B:178:0x0339  */
        /* JADX WARN: Removed duplicated region for block: B:179:0x033f  */
        /* JADX WARN: Removed duplicated region for block: B:180:0x0345  */
        /* JADX WARN: Removed duplicated region for block: B:182:0x034c  */
        /* JADX WARN: Removed duplicated region for block: B:183:0x0358  */
        /* JADX WARN: Removed duplicated region for block: B:184:0x0364  */
        /* JADX WARN: Removed duplicated region for block: B:185:0x036a  */
        /* JADX WARN: Removed duplicated region for block: B:186:0x037d  */
        /* JADX WARN: Removed duplicated region for block: B:187:0x0383  */
        /* JADX WARN: Removed duplicated region for block: B:188:0x0392  */
        /* JADX WARN: Removed duplicated region for block: B:189:0x0398  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x0145  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0149  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0155  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x015f  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x0169  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0175  */
        /* JADX WARN: Removed duplicated region for block: B:85:0x0180  */
        /* JADX WARN: Removed duplicated region for block: B:89:0x018a  */
        /* JADX WARN: Removed duplicated region for block: B:93:0x0194  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x019e  */
        @Override // com.felicanetworks.semc.ISemClientEventListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onEventNotify(SemClientNotifyEventInfo semClientNotifyEventInfo) throws SemClientException {
            String callbackName;
            boolean isNeedBindForSEMCApp;
            String processStatus;
            String processResultCode;
            String processResultDetailCode;
            String processResultMessageString;
            SdKeyDerivationData[] sdKeyDerivationDataList;
            Object registeredSpAppletVersionList;
            Object appletStatusList;
            String additionalErrorInfo;
            String message;
            byte b;
            byte b2;
            int i;
            String str;
            boolean z;
            String str2;
            String str3;
            String[] strArr;
            Object obj;
            byte b3;
            String[] strArr2;
            int errorCode;
            String[] accessAllowedSpAppIdList;
            LogMgrUtil.log(5, "000");
            if (semClientNotifyEventInfo == null) {
                LogMgrUtil.log(2, "700 notifyEventInfo is null");
                SemClient.this.unbindSemClient();
                LogMgrUtil.log(5, "997");
                return;
            }
            String[] strArr3 = {SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_FINISHED, SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_FINISHED, SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_FINISHED, SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED};
            byte b4 = -1;
            String[] strArr4 = null;
            try {
                callbackName = semClientNotifyEventInfo.getCallbackName();
                try {
                    if (callbackName.endsWith(SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR)) {
                        errorCode = semClientNotifyEventInfo.getErrorCode();
                        additionalErrorInfo = semClientNotifyEventInfo.getErrorAdditionalInformation();
                        strArr2 = strArr3;
                        message = semClientNotifyEventInfo.getErrorMessage();
                        isNeedBindForSEMCApp = semClientNotifyEventInfo.getIsNeedBindForSEMCApp();
                        try {
                            LogMgrUtil.log(8, "001 errorCode :" + errorCode + ", additionalErrorInformation :" + additionalErrorInfo + ", message ; " + message);
                        } catch (SemClientException e) {
                            e = e;
                            processStatus = null;
                            processResultCode = null;
                            processResultDetailCode = processResultCode;
                            processResultMessageString = processResultDetailCode;
                            sdKeyDerivationDataList = processResultMessageString;
                            registeredSpAppletVersionList = sdKeyDerivationDataList;
                            appletStatusList = registeredSpAppletVersionList;
                            LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                            LogMgrUtil.log(5, "998");
                            int errorCode2 = e.getErrorCode();
                            additionalErrorInfo = e.getAdditionalErrorInfo();
                            message = e.getMessage();
                            callbackName.hashCode();
                            switch (callbackName.hashCode()) {
                                case -2043468054:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_ERROR) ? (byte) -1 : (byte) 0;
                                    break;
                                case -739530544:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_ERROR) ? (byte) -1 : (byte) 1;
                                    break;
                                case -241888086:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_FINISHED) ? (byte) -1 : (byte) 2;
                                    break;
                                case -1944547:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_ERROR) ? (byte) -1 : (byte) 3;
                                    break;
                                case 129862205:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED) ? (byte) -1 : (byte) 4;
                                    break;
                                case 174413457:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_ERROR) ? (byte) -1 : (byte) 5;
                                    break;
                                case 1163592316:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_FINISHED) ? (byte) -1 : (byte) 6;
                                    break;
                                case 1266156361:
                                    if (!callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_FINISHED)) {
                                        b = -1;
                                    } else {
                                        b2 = 7;
                                        b = b2;
                                    }
                                    break;
                                case 1836606853:
                                    b = !callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_CONNECT) ? (byte) -1 : (byte) 8;
                                    break;
                                case 2040978494:
                                    if (!callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_ERROR)) {
                                        b = -1;
                                    } else {
                                        b2 = 9;
                                        b = b2;
                                    }
                                    break;
                                case 2130174596:
                                    if (!callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_ERROR)) {
                                        b = -1;
                                    } else {
                                        b2 = 10;
                                        b = b2;
                                    }
                                    break;
                                case 2134943824:
                                    if (!callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_FINISHED)) {
                                        b = -1;
                                    } else {
                                        b2 = Ascii.VT;
                                        b = b2;
                                    }
                                    break;
                                default:
                                    b = -1;
                                    break;
                            }
                            String str4 = processStatus;
                            switch (b) {
                                case 0:
                                case 11:
                                    LogMgrUtil.log(8, "Convert [" + callbackName + "] to error notify.");
                                    i = errorCode2;
                                    callbackName = SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_ERROR;
                                    break;
                                case 1:
                                case 2:
                                    LogMgrUtil.log(8, "Convert [" + callbackName + "] to error notify.");
                                    i = errorCode2;
                                    callbackName = SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_ERROR;
                                    break;
                                case 3:
                                case 4:
                                    LogMgrUtil.log(8, "Convert [" + callbackName + "] to error notify.");
                                    i = errorCode2;
                                    callbackName = SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_ERROR;
                                    break;
                                case 5:
                                case 7:
                                    LogMgrUtil.log(8, "Convert [" + callbackName + "] to error notify.");
                                    i = errorCode2;
                                    callbackName = SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_ERROR;
                                    break;
                                case 6:
                                case 9:
                                    LogMgrUtil.log(8, "Convert [" + callbackName + "] to error notify.");
                                    i = errorCode2;
                                    callbackName = SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_ERROR;
                                    break;
                                case 8:
                                case 10:
                                    LogMgrUtil.log(8, "Convert [" + callbackName + "] to error notify.");
                                    i = errorCode2;
                                    callbackName = SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_ERROR;
                                    break;
                                default:
                                    LogMgrUtil.log(2, "702 Unknown method : " + callbackName);
                                    i = errorCode2;
                                    break;
                            }
                            str = processResultCode;
                            z = isNeedBindForSEMCApp;
                            str2 = processResultDetailCode;
                            str3 = processResultMessageString;
                            strArr = str4;
                            callbackName.hashCode();
                            switch (callbackName.hashCode()) {
                                case -2043468054:
                                    break;
                                case -739530544:
                                    break;
                                case -241888086:
                                    break;
                                case -1944547:
                                    break;
                                case 129862205:
                                    break;
                                case 174413457:
                                    break;
                                case 1163592316:
                                    break;
                                case 1266156361:
                                    break;
                                case 1836606853:
                                    break;
                                case 2040978494:
                                    break;
                                case 2130174596:
                                    break;
                                case 2134943824:
                                    break;
                            }
                            switch (b4) {
                            }
                            LogMgrUtil.log(5, "999");
                        }
                    } else {
                        strArr2 = strArr3;
                        errorCode = 0;
                        isNeedBindForSEMCApp = false;
                        message = null;
                        additionalErrorInfo = null;
                    }
                    if (Arrays.asList(strArr2).contains(callbackName)) {
                        processStatus = semClientNotifyEventInfo.getProcessStatus();
                        try {
                            processResultCode = semClientNotifyEventInfo.getProcessResultCode();
                            try {
                                processResultDetailCode = semClientNotifyEventInfo.getProcessResultDetailCode();
                                try {
                                    processResultMessageString = semClientNotifyEventInfo.getProcessResultMessageString();
                                    try {
                                        sdKeyDerivationDataList = callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_FINISHED) ? semClientNotifyEventInfo.getSdKeyDerivationDataList() : null;
                                    } catch (SemClientException e2) {
                                        e = e2;
                                        sdKeyDerivationDataList = null;
                                        registeredSpAppletVersionList = sdKeyDerivationDataList;
                                        appletStatusList = registeredSpAppletVersionList;
                                        LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                                        LogMgrUtil.log(5, "998");
                                        int errorCode22 = e.getErrorCode();
                                        additionalErrorInfo = e.getAdditionalErrorInfo();
                                        message = e.getMessage();
                                        callbackName.hashCode();
                                        switch (callbackName.hashCode()) {
                                            case -2043468054:
                                                break;
                                            case -739530544:
                                                break;
                                            case -241888086:
                                                break;
                                            case -1944547:
                                                break;
                                            case 129862205:
                                                break;
                                            case 174413457:
                                                break;
                                            case 1163592316:
                                                break;
                                            case 1266156361:
                                                break;
                                            case 1836606853:
                                                break;
                                            case 2040978494:
                                                break;
                                            case 2130174596:
                                                break;
                                            case 2134943824:
                                                break;
                                        }
                                        String str42 = processStatus;
                                        switch (b) {
                                        }
                                        str = processResultCode;
                                        z = isNeedBindForSEMCApp;
                                        str2 = processResultDetailCode;
                                        str3 = processResultMessageString;
                                        strArr = str42;
                                        callbackName.hashCode();
                                        switch (callbackName.hashCode()) {
                                            case -2043468054:
                                                break;
                                            case -739530544:
                                                break;
                                            case -241888086:
                                                break;
                                            case -1944547:
                                                break;
                                            case 129862205:
                                                break;
                                            case 174413457:
                                                break;
                                            case 1163592316:
                                                break;
                                            case 1266156361:
                                                break;
                                            case 1836606853:
                                                break;
                                            case 2040978494:
                                                break;
                                            case 2130174596:
                                                break;
                                            case 2134943824:
                                                break;
                                        }
                                        switch (b4) {
                                        }
                                        LogMgrUtil.log(5, "999");
                                    }
                                } catch (SemClientException e3) {
                                    e = e3;
                                    processResultMessageString = null;
                                    sdKeyDerivationDataList = processResultMessageString;
                                    registeredSpAppletVersionList = sdKeyDerivationDataList;
                                    appletStatusList = registeredSpAppletVersionList;
                                    LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                                    LogMgrUtil.log(5, "998");
                                    int errorCode222 = e.getErrorCode();
                                    additionalErrorInfo = e.getAdditionalErrorInfo();
                                    message = e.getMessage();
                                    callbackName.hashCode();
                                    switch (callbackName.hashCode()) {
                                        case -2043468054:
                                            break;
                                        case -739530544:
                                            break;
                                        case -241888086:
                                            break;
                                        case -1944547:
                                            break;
                                        case 129862205:
                                            break;
                                        case 174413457:
                                            break;
                                        case 1163592316:
                                            break;
                                        case 1266156361:
                                            break;
                                        case 1836606853:
                                            break;
                                        case 2040978494:
                                            break;
                                        case 2130174596:
                                            break;
                                        case 2134943824:
                                            break;
                                    }
                                    String str422 = processStatus;
                                    switch (b) {
                                    }
                                    str = processResultCode;
                                    z = isNeedBindForSEMCApp;
                                    str2 = processResultDetailCode;
                                    str3 = processResultMessageString;
                                    strArr = str422;
                                    callbackName.hashCode();
                                    switch (callbackName.hashCode()) {
                                        case -2043468054:
                                            break;
                                        case -739530544:
                                            break;
                                        case -241888086:
                                            break;
                                        case -1944547:
                                            break;
                                        case 129862205:
                                            break;
                                        case 174413457:
                                            break;
                                        case 1163592316:
                                            break;
                                        case 1266156361:
                                            break;
                                        case 1836606853:
                                            break;
                                        case 2040978494:
                                            break;
                                        case 2130174596:
                                            break;
                                        case 2134943824:
                                            break;
                                    }
                                    switch (b4) {
                                    }
                                    LogMgrUtil.log(5, "999");
                                }
                            } catch (SemClientException e4) {
                                e = e4;
                                processResultDetailCode = null;
                                processResultMessageString = processResultDetailCode;
                                sdKeyDerivationDataList = processResultMessageString;
                                registeredSpAppletVersionList = sdKeyDerivationDataList;
                                appletStatusList = registeredSpAppletVersionList;
                                LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                                LogMgrUtil.log(5, "998");
                                int errorCode2222 = e.getErrorCode();
                                additionalErrorInfo = e.getAdditionalErrorInfo();
                                message = e.getMessage();
                                callbackName.hashCode();
                                switch (callbackName.hashCode()) {
                                    case -2043468054:
                                        break;
                                    case -739530544:
                                        break;
                                    case -241888086:
                                        break;
                                    case -1944547:
                                        break;
                                    case 129862205:
                                        break;
                                    case 174413457:
                                        break;
                                    case 1163592316:
                                        break;
                                    case 1266156361:
                                        break;
                                    case 1836606853:
                                        break;
                                    case 2040978494:
                                        break;
                                    case 2130174596:
                                        break;
                                    case 2134943824:
                                        break;
                                }
                                String str4222 = processStatus;
                                switch (b) {
                                }
                                str = processResultCode;
                                z = isNeedBindForSEMCApp;
                                str2 = processResultDetailCode;
                                str3 = processResultMessageString;
                                strArr = str4222;
                                callbackName.hashCode();
                                switch (callbackName.hashCode()) {
                                    case -2043468054:
                                        break;
                                    case -739530544:
                                        break;
                                    case -241888086:
                                        break;
                                    case -1944547:
                                        break;
                                    case 129862205:
                                        break;
                                    case 174413457:
                                        break;
                                    case 1163592316:
                                        break;
                                    case 1266156361:
                                        break;
                                    case 1836606853:
                                        break;
                                    case 2040978494:
                                        break;
                                    case 2130174596:
                                        break;
                                    case 2134943824:
                                        break;
                                }
                                switch (b4) {
                                }
                                LogMgrUtil.log(5, "999");
                            }
                            try {
                                if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED)) {
                                    registeredSpAppletVersionList = semClientNotifyEventInfo.getRegisteredSpAppletVersionList();
                                    try {
                                        appletStatusList = semClientNotifyEventInfo.getAppletStatusList();
                                    } catch (SemClientException e5) {
                                        e = e5;
                                        appletStatusList = null;
                                    }
                                    try {
                                        accessAllowedSpAppIdList = semClientNotifyEventInfo.getAccessAllowedSpAppIdList();
                                    } catch (SemClientException e6) {
                                        e = e6;
                                        LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                                        LogMgrUtil.log(5, "998");
                                        int errorCode22222 = e.getErrorCode();
                                        additionalErrorInfo = e.getAdditionalErrorInfo();
                                        message = e.getMessage();
                                        callbackName.hashCode();
                                        switch (callbackName.hashCode()) {
                                            case -2043468054:
                                                break;
                                            case -739530544:
                                                break;
                                            case -241888086:
                                                break;
                                            case -1944547:
                                                break;
                                            case 129862205:
                                                break;
                                            case 174413457:
                                                break;
                                            case 1163592316:
                                                break;
                                            case 1266156361:
                                                break;
                                            case 1836606853:
                                                break;
                                            case 2040978494:
                                                break;
                                            case 2130174596:
                                                break;
                                            case 2134943824:
                                                break;
                                        }
                                        String str42222 = processStatus;
                                        switch (b) {
                                        }
                                        str = processResultCode;
                                        z = isNeedBindForSEMCApp;
                                        str2 = processResultDetailCode;
                                        str3 = processResultMessageString;
                                        strArr = str42222;
                                    }
                                } else {
                                    accessAllowedSpAppIdList = null;
                                    registeredSpAppletVersionList = null;
                                    appletStatusList = null;
                                }
                                strArr4 = processStatus;
                            } catch (SemClientException e7) {
                                e = e7;
                                registeredSpAppletVersionList = null;
                                appletStatusList = registeredSpAppletVersionList;
                                LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                                LogMgrUtil.log(5, "998");
                                int errorCode222222 = e.getErrorCode();
                                additionalErrorInfo = e.getAdditionalErrorInfo();
                                message = e.getMessage();
                                callbackName.hashCode();
                                switch (callbackName.hashCode()) {
                                    case -2043468054:
                                        break;
                                    case -739530544:
                                        break;
                                    case -241888086:
                                        break;
                                    case -1944547:
                                        break;
                                    case 129862205:
                                        break;
                                    case 174413457:
                                        break;
                                    case 1163592316:
                                        break;
                                    case 1266156361:
                                        break;
                                    case 1836606853:
                                        break;
                                    case 2040978494:
                                        break;
                                    case 2130174596:
                                        break;
                                    case 2134943824:
                                        break;
                                }
                                String str422222 = processStatus;
                                switch (b) {
                                }
                                str = processResultCode;
                                z = isNeedBindForSEMCApp;
                                str2 = processResultDetailCode;
                                str3 = processResultMessageString;
                                strArr = str422222;
                                callbackName.hashCode();
                                switch (callbackName.hashCode()) {
                                    case -2043468054:
                                        break;
                                    case -739530544:
                                        break;
                                    case -241888086:
                                        break;
                                    case -1944547:
                                        break;
                                    case 129862205:
                                        break;
                                    case 174413457:
                                        break;
                                    case 1163592316:
                                        break;
                                    case 1266156361:
                                        break;
                                    case 1836606853:
                                        break;
                                    case 2040978494:
                                        break;
                                    case 2130174596:
                                        break;
                                    case 2134943824:
                                        break;
                                }
                                switch (b4) {
                                }
                                LogMgrUtil.log(5, "999");
                            }
                        } catch (SemClientException e8) {
                            e = e8;
                            processResultCode = null;
                            processResultDetailCode = processResultCode;
                            processResultMessageString = processResultDetailCode;
                            sdKeyDerivationDataList = processResultMessageString;
                            registeredSpAppletVersionList = sdKeyDerivationDataList;
                            appletStatusList = registeredSpAppletVersionList;
                            LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                            LogMgrUtil.log(5, "998");
                            int errorCode2222222 = e.getErrorCode();
                            additionalErrorInfo = e.getAdditionalErrorInfo();
                            message = e.getMessage();
                            callbackName.hashCode();
                            switch (callbackName.hashCode()) {
                                case -2043468054:
                                    break;
                                case -739530544:
                                    break;
                                case -241888086:
                                    break;
                                case -1944547:
                                    break;
                                case 129862205:
                                    break;
                                case 174413457:
                                    break;
                                case 1163592316:
                                    break;
                                case 1266156361:
                                    break;
                                case 1836606853:
                                    break;
                                case 2040978494:
                                    break;
                                case 2130174596:
                                    break;
                                case 2134943824:
                                    break;
                            }
                            String str4222222 = processStatus;
                            switch (b) {
                            }
                            str = processResultCode;
                            z = isNeedBindForSEMCApp;
                            str2 = processResultDetailCode;
                            str3 = processResultMessageString;
                            strArr = str4222222;
                            callbackName.hashCode();
                            switch (callbackName.hashCode()) {
                                case -2043468054:
                                    break;
                                case -739530544:
                                    break;
                                case -241888086:
                                    break;
                                case -1944547:
                                    break;
                                case 129862205:
                                    break;
                                case 174413457:
                                    break;
                                case 1163592316:
                                    break;
                                case 1266156361:
                                    break;
                                case 1836606853:
                                    break;
                                case 2040978494:
                                    break;
                                case 2130174596:
                                    break;
                                case 2134943824:
                                    break;
                            }
                            switch (b4) {
                            }
                            LogMgrUtil.log(5, "999");
                        }
                    } else {
                        accessAllowedSpAppIdList = null;
                        processResultCode = null;
                        processResultDetailCode = null;
                        processResultMessageString = null;
                        sdKeyDerivationDataList = null;
                        registeredSpAppletVersionList = null;
                        appletStatusList = null;
                    }
                    String[] strArr5 = strArr4;
                    strArr4 = accessAllowedSpAppIdList;
                    i = errorCode;
                    str = processResultCode;
                    strArr = strArr5;
                    z = isNeedBindForSEMCApp;
                    str2 = processResultDetailCode;
                    str3 = processResultMessageString;
                } catch (SemClientException e9) {
                    e = e9;
                    isNeedBindForSEMCApp = false;
                    processStatus = null;
                    processResultCode = null;
                    processResultDetailCode = processResultCode;
                    processResultMessageString = processResultDetailCode;
                    sdKeyDerivationDataList = processResultMessageString;
                    registeredSpAppletVersionList = sdKeyDerivationDataList;
                    appletStatusList = registeredSpAppletVersionList;
                    LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
                    LogMgrUtil.log(5, "998");
                    int errorCode22222222 = e.getErrorCode();
                    additionalErrorInfo = e.getAdditionalErrorInfo();
                    message = e.getMessage();
                    callbackName.hashCode();
                    switch (callbackName.hashCode()) {
                        case -2043468054:
                            break;
                        case -739530544:
                            break;
                        case -241888086:
                            break;
                        case -1944547:
                            break;
                        case 129862205:
                            break;
                        case 174413457:
                            break;
                        case 1163592316:
                            break;
                        case 1266156361:
                            break;
                        case 1836606853:
                            break;
                        case 2040978494:
                            break;
                        case 2130174596:
                            break;
                        case 2134943824:
                            break;
                    }
                    String str42222222 = processStatus;
                    switch (b) {
                    }
                    str = processResultCode;
                    z = isNeedBindForSEMCApp;
                    str2 = processResultDetailCode;
                    str3 = processResultMessageString;
                    strArr = str42222222;
                    callbackName.hashCode();
                    switch (callbackName.hashCode()) {
                        case -2043468054:
                            break;
                        case -739530544:
                            break;
                        case -241888086:
                            break;
                        case -1944547:
                            break;
                        case 129862205:
                            break;
                        case 174413457:
                            break;
                        case 1163592316:
                            break;
                        case 1266156361:
                            break;
                        case 1836606853:
                            break;
                        case 2040978494:
                            break;
                        case 2130174596:
                            break;
                        case 2134943824:
                            break;
                    }
                    switch (b4) {
                    }
                    LogMgrUtil.log(5, "999");
                }
            } catch (SemClientException e10) {
                e = e10;
                callbackName = "";
            }
            callbackName.hashCode();
            switch (callbackName.hashCode()) {
                case -2043468054:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_ERROR)) {
                        b4 = 0;
                    }
                    break;
                case -739530544:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_ERROR)) {
                        b4 = 1;
                    }
                    break;
                case -241888086:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_INSTALL_APPLET_ON_FINISHED)) {
                        b4 = 2;
                    }
                    break;
                case -1944547:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_ERROR)) {
                        b4 = 3;
                    }
                    break;
                case 129862205:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_GET_APPLET_STATUS_ON_FINISHED)) {
                        b4 = 4;
                    }
                    break;
                case 174413457:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_ERROR)) {
                        b4 = 5;
                    }
                    break;
                case 1163592316:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_FINISHED)) {
                        b4 = 6;
                    }
                    break;
                case 1266156361:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_UPGRADE_APPLET_ON_FINISHED)) {
                        b3 = 7;
                        b4 = b3;
                    }
                    break;
                case 1836606853:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_CONNECT)) {
                        b4 = 8;
                    }
                    break;
                case 2040978494:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_DELETE_APPLET_ON_ERROR)) {
                        b3 = 9;
                        b4 = b3;
                    }
                    break;
                case 2130174596:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_CONNECT_LISTENER_ON_ERROR)) {
                        b3 = 10;
                        b4 = b3;
                    }
                    break;
                case 2134943824:
                    if (callbackName.equals(SemClientNotifyEventInfo.METHOD_NAME_TSM_SEQUENCE_ON_FINISHED)) {
                        b3 = Ascii.VT;
                        b4 = b3;
                    }
                    break;
            }
            switch (b4) {
                case 0:
                    onTsmSequenceOnError(i, additionalErrorInfo, message);
                    break;
                case 1:
                    onInstallAppletOnError(i, additionalErrorInfo, message);
                    break;
                case 2:
                    onInstallAppletOnFinished(strArr, str, str2, str3, sdKeyDerivationDataList);
                    break;
                case 3:
                    onGetAppletStatusOnError(i, additionalErrorInfo, message);
                    break;
                case 4:
                    onGetAppletStatusOnFinished(strArr, str, str2, str3, registeredSpAppletVersionList, appletStatusList, strArr4);
                    break;
                case 5:
                    SemClientEventListenerStub semClientEventListenerStub = this;
                    semClientEventListenerStub.onUpgradeAppletOnError(i, additionalErrorInfo, message);
                    obj = semClientEventListenerStub;
                    break;
                case 6:
                    SemClientEventListenerStub semClientEventListenerStub2 = this;
                    semClientEventListenerStub2.onDeleteAppletOnFinished(strArr, str, str2, str3);
                    obj = semClientEventListenerStub2;
                    break;
                case 7:
                    SemClientEventListenerStub semClientEventListenerStub3 = this;
                    semClientEventListenerStub3.onUpgradeAppletOnFinished(strArr, str, str2, str3);
                    obj = semClientEventListenerStub3;
                    break;
                case 8:
                    SemClientEventListenerStub semClientEventListenerStub4 = this;
                    semClientEventListenerStub4.connectedOnConnect();
                    obj = semClientEventListenerStub4;
                    break;
                case 9:
                    SemClientEventListenerStub semClientEventListenerStub5 = this;
                    semClientEventListenerStub5.onDeleteAppletOnError(i, additionalErrorInfo, message);
                    obj = semClientEventListenerStub5;
                    break;
                case 10:
                    SemClientEventListenerStub semClientEventListenerStub6 = this;
                    semClientEventListenerStub6.connectedOnError(i, additionalErrorInfo, message, z);
                    obj = semClientEventListenerStub6;
                    break;
                case 11:
                    SemClientEventListenerStub semClientEventListenerStub7 = this;
                    semClientEventListenerStub7.onTsmSequenceOnFinished();
                    obj = semClientEventListenerStub7;
                    break;
                default:
                    LogMgrUtil.log(2, "703 Unknown method : " + callbackName);
                    SemClientEventListenerStub semClientEventListenerStub8 = this;
                    SemClient.this.unbindSemClient();
                    obj = semClientEventListenerStub8;
                    break;
            }
            LogMgrUtil.log(5, "999");
        }

        protected void connectedOnConnect() {
            OnConnectedListener onConnectedListener;
            LogMgrUtil.log(5, "000");
            try {
                SemClientVersion unused = SemClient.mSemClientVersion = SemClient.getSemClientVersionResource((Context) SemClient.this.mContext.get(), SemClient.this.mConnectSemcPackageName);
            } catch (PackageManager.NameNotFoundException e) {
                LogMgrUtil.log(2, "702 catch NameNotFoundException message=[" + e.getMessage());
            } catch (SemClientException e2) {
                LogMgrUtil.log(2, "700 cannot get SemClientVersion message=[" + e2.getMessage());
            } catch (IllegalArgumentException e3) {
                LogMgrUtil.log(2, "701 catch IllegalArgumentException message=[" + e3.getMessage());
            }
            synchronized (SemClient.this) {
                onConnectedListener = SemClient.this.mOnConnectedListener;
                SemClient.this.mOnConnectedListener = null;
                if (onConnectedListener != null) {
                    LogMgrUtil.log(9, "001");
                } else {
                    LogMgrUtil.log(9, "002");
                    SemClient.this.unbindSemClient();
                }
            }
            if (onConnectedListener != null) {
                LogMgrUtil.log(5, "003");
                LogMgrUtil.performanceIn("API", "OnConnectedListener", "onConnected");
                onConnectedListener.onConnected();
                LogMgrUtil.performanceOut("API", "OnConnectedListener", "onConnected");
            }
            LogMgrUtil.log(5, "999");
        }

        protected void connectedOnError(int i, String str, String str2, boolean z) {
            OnConnectedListener onConnectedListener;
            Context context;
            String str3;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                LogMgrUtil.log(9, "001");
                onConnectedListener = SemClient.this.mOnConnectedListener;
                SemClient.this.mOnConnectedListener = null;
                context = (Context) SemClient.this.mContext.get();
                str3 = SemClient.this.mConnectSemcPackageName;
                SemClient.this.unbindSemClient();
            }
            try {
                if (SemClient.this.mConnectApiInfo.getMode() == 4 && "com.felicanetworks.mfm.main".equals(str3) && z) {
                    SemClient semClient = SemClient.this;
                    semClient.bindSemClient(true, context, semClient.mConnectApiInfo, onConnectedListener);
                    return;
                }
            } catch (SemClientException e) {
                int errorCode = e.getErrorCode();
                str2 = e.getMessage();
                str = e.getAdditionalErrorInfo();
                i = errorCode;
            } catch (IllegalArgumentException e2) {
                str2 = e2.getMessage();
                str = "";
                i = 900;
            }
            if (onConnectedListener != null) {
                LogMgrUtil.performanceIn("API", "OnConnectedListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onConnectedListener.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnConnectedListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            }
            LogMgrUtil.log(5, "999");
        }

        private void onTsmSequenceOnError(int i, String str, String str2) {
            OnTsmSequenceListener onTsmSequenceListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onTsmSequenceListener = SemClient.this.mOnTsmSequenceListener;
                SemClient.this.mOnTsmSequenceListener = null;
            }
            if (onTsmSequenceListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onTsmSequenceListener.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onTsmSequenceOnFinished() {
            OnTsmSequenceListener onTsmSequenceListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onTsmSequenceListener = SemClient.this.mOnTsmSequenceListener;
                SemClient.this.mOnTsmSequenceListener = null;
            }
            if (onTsmSequenceListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnTsmSequenceListener", "onFinished");
                onTsmSequenceListener.onFinished();
                LogMgrUtil.performanceOut("API", "OnTsmSequenceListener", "onFinished");
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onInstallAppletOnError(int i, String str, String str2) {
            OnInstallAppletListener onInstallAppletListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onInstallAppletListener = SemClient.this.mOnInstallAppletListener;
                SemClient.this.mOnInstallAppletListener = null;
            }
            if (onInstallAppletListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnInstallAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onInstallAppletListener.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnInstallAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onInstallAppletOnFinished(String str, String str2, String str3, String str4, SdKeyDerivationData[] sdKeyDerivationDataArr) {
            OnInstallAppletListener onInstallAppletListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onInstallAppletListener = SemClient.this.mOnInstallAppletListener;
                SemClient.this.mOnInstallAppletListener = null;
            }
            if (onInstallAppletListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnInstallAppletListener", "onFinished", "processStatus[" + str + "]processResultCode[" + str2 + "]processResultDetailCode[" + str3 + "]processResultMessage[" + str4 + "]sdKeyDerivationDataList[" + Arrays.toString(sdKeyDerivationDataArr) + "]");
                onInstallAppletListener.onFinished(str, str2, str3, str4, sdKeyDerivationDataArr);
                LogMgrUtil.performanceOut("API", "OnInstallAppletListener", "onFinished");
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onUpgradeAppletOnError(int i, String str, String str2) {
            OnUpgradeAppletListener onUpgradeAppletListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onUpgradeAppletListener = SemClient.this.mOnUpgradeAppletListener;
                SemClient.this.mOnUpgradeAppletListener = null;
            }
            if (onUpgradeAppletListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onUpgradeAppletListener.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnUpgradeAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onUpgradeAppletOnFinished(String str, String str2, String str3, String str4) {
            OnUpgradeAppletListener onUpgradeAppletListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onUpgradeAppletListener = SemClient.this.mOnUpgradeAppletListener;
                SemClient.this.mOnUpgradeAppletListener = null;
            }
            if (onUpgradeAppletListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnUpgradeAppletListener", "onFinished", "processStatus[" + str + "]processResultCode[" + str2 + "]processResultDetailCode[" + str3 + "]processResultMessage[" + str4 + "]");
                onUpgradeAppletListener.onFinished(str, str2, str3, str4);
                LogMgrUtil.performanceOut("API", "OnUpgradeAppletListener", "onFinished");
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onDeleteAppletOnError(int i, String str, String str2) {
            OnDeleteAppletListener onDeleteAppletListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onDeleteAppletListener = SemClient.this.mOnDeleteAppletListener;
                SemClient.this.mOnDeleteAppletListener = null;
            }
            if (onDeleteAppletListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onDeleteAppletListener.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnDeleteAppletListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onDeleteAppletOnFinished(String str, String str2, String str3, String str4) {
            OnDeleteAppletListener onDeleteAppletListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onDeleteAppletListener = SemClient.this.mOnDeleteAppletListener;
                SemClient.this.mOnDeleteAppletListener = null;
            }
            if (onDeleteAppletListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnDeleteAppletListener", "onFinished", "processStatus[" + str + "]processResultCode[" + str2 + "]processResultDetailCode[" + str3 + "]processResultMessage[" + str4 + "]");
                onDeleteAppletListener.onFinished(str, str2, str3, str4);
                LogMgrUtil.performanceOut("API", "OnDeleteAppletListener", "onFinished");
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onGetAppletStatusOnError(int i, String str, String str2) {
            OnGetAppletStatusListener onGetAppletStatusListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onGetAppletStatusListener = SemClient.this.mOnGetAppletStatusListener;
                SemClient.this.mOnGetAppletStatusListener = null;
            }
            if (onGetAppletStatusListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnGetAppletStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onGetAppletStatusListener.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnGetAppletStatusListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onGetAppletStatusOnFinished(String str, String str2, String str3, String str4, String[] strArr, AppletStatus[] appletStatusArr, String[] strArr2) {
            OnGetAppletStatusListener onGetAppletStatusListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClient.this) {
                SemClient.this.stopOnline();
                onGetAppletStatusListener = SemClient.this.mOnGetAppletStatusListener;
                SemClient.this.mOnGetAppletStatusListener = null;
            }
            if (onGetAppletStatusListener != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnGetAppletStatusListener", "onFinished", "processStatus[" + str + "]processResultCode[" + str2 + "]processResultDetailCode[" + str3 + "]processResultMessage[" + str4 + "]registeredSpAppletVersionList[" + Arrays.toString(strArr) + "]appletStatusList[" + Arrays.toString(appletStatusArr) + "]");
                onGetAppletStatusListener.onFinished(str, str2, str3, str4, strArr, appletStatusArr, strArr2);
                LogMgrUtil.performanceOut("API", "OnGetAppletStatusListener", "onFinished");
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }
    }

    protected void cleanUp() {
        LogMgrUtil.log(9, "000");
        this.mSemClientService = null;
        this.mOnConnectedListener = null;
        synchronized (this) {
            this.mOnTsmSequenceListener = null;
            this.mOnInstallAppletListener = null;
            this.mOnUpgradeAppletListener = null;
            this.mOnDeleteAppletListener = null;
            this.mOnGetAppletStatusListener = null;
        }
        this.mBindTimerHandler.stopTimer();
        LogMgrUtil.log(9, "001");
        synchronized (this) {
            this.mOnline = false;
        }
        this.mContext = null;
        this.mConnectSemcPackageName = null;
        mSemClientVersion = null;
        LogMgrUtil.log(9, "999");
    }

    protected void checkConnected(boolean z) throws IllegalStateException {
        LogMgrUtil.log(9, "000");
        if (this.mSemClientService == null || this.mOnConnectedListener != null) {
            if (z) {
                LogMgrUtil.log(2, "700");
            }
            throw new IllegalStateException("Illegal state not connected.");
        }
        LogMgrUtil.log(9, "999");
    }

    protected void checkNotConnected() throws IllegalStateException {
        LogMgrUtil.log(9, "000");
        if (this.mOnConnectedListener != null) {
            LogMgrUtil.log(2, "700 IllegalStateException msg:Illegal state currently connecting.");
            throw new IllegalStateException("Illegal state currently connecting.");
        }
        if (this.mSemClientService != null) {
            LogMgrUtil.log(2, "701 IllegalStateException msg:Illegal state already connected.");
            throw new IllegalStateException("Illegal state already connected.");
        }
        LogMgrUtil.log(9, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkConnecting() {
        LogMgrUtil.log(9, "000");
        boolean z = this.mSemClientService == null && this.mOnConnectedListener != null;
        LogMgrUtil.log(9, "999 value:" + z);
        return z;
    }

    private boolean checkAfterConnecting() {
        LogMgrUtil.log(9, "000");
        boolean z = (this.mSemClientService == null && this.mOnConnectedListener == null) ? false : true;
        LogMgrUtil.log(9, "999 value:" + z);
        return z;
    }

    private void startOnline() throws IllegalStateException {
        LogMgrUtil.log(9, "000");
        onlineCheck();
        this.mOnline = true;
        LogMgrUtil.log(9, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void stopOnline() {
        LogMgrUtil.log(9, "000");
        this.mOnline = false;
        LogMgrUtil.log(9, "999");
    }

    protected synchronized void onlineCheck() throws IllegalStateException {
        LogMgrUtil.log(9, "000");
        if (this.mOnline) {
            LogMgrUtil.log(2, "700 online processing");
            throw new IllegalStateException("Illegal state Sem Sequence started.");
        }
        LogMgrUtil.log(9, "999");
    }

    private synchronized boolean isConnected() {
        boolean z;
        LogMgrUtil.performanceIn("API", "SemClient", "isConnected");
        LogMgrUtil.log(5, "000");
        z = false;
        try {
            checkConnected(false);
            LogMgrUtil.log(8, "connected");
            z = true;
        } catch (IllegalStateException unused) {
            LogMgrUtil.log(8, "Not connected");
        }
        LogMgrUtil.log(5, "999 isConnected " + z);
        LogMgrUtil.performanceOut("API", "SemClient", "isConnected", "isConnected[" + z + "]");
        return z;
    }

    private synchronized boolean isTsmSequenceStarted() {
        boolean z;
        LogMgrUtil.performanceIn("API", "SemClient", "isTsmSequenceStarted");
        LogMgrUtil.log(5, "000");
        z = false;
        try {
            checkConnected(false);
            try {
                onlineCheck();
            } catch (IllegalStateException e) {
                LogMgrUtil.log(2, "701 " + e.toString() + " message:" + e.getMessage());
                z = true;
            }
        } catch (IllegalStateException e2) {
            LogMgrUtil.log(8, "700 " + e2.toString() + " message:" + e2.getMessage());
        }
        LogMgrUtil.log(5, "999 returned " + z);
        LogMgrUtil.performanceOut("API", "SemClient", "isTsmSequenceStarted", String.valueOf(z));
        return z;
    }

    private static int[] splitSemClientVersion(String str) {
        LogMgrUtil.log(5, "000");
        if (str == null) {
            LogMgrUtil.log(2, "700 SemClient Version is null.");
            return null;
        }
        String[] strArrSplit = str.split(VERSION_DELIMITER_REGEX);
        if (strArrSplit.length != 3) {
            LogMgrUtil.log(2, "701 Incorrect SemClient version array length .");
            return null;
        }
        int[] iArr = new int[3];
        for (int i = 0; i < 3; i++) {
            try {
                iArr[i] = Integer.parseInt(strArrSplit[i]);
            } catch (NumberFormatException unused) {
                LogMgrUtil.log(2, "702 Invalid SemClient Version format.");
                return null;
            }
        }
        LogMgrUtil.log(5, "999 version[0]:" + iArr[0] + " version[1]:" + iArr[1] + " version[2]:" + iArr[2]);
        return iArr;
    }

    private void preCheckContactlessInterface(String str) throws IllegalStateException, IllegalArgumentException {
        LogMgrUtil.log(5, "000");
        if (str == null) {
            LogMgrUtil.log(2, "700 aid is null.");
            throw new IllegalArgumentException(EXC_INVALID_AID);
        }
        if (str.isEmpty()) {
            LogMgrUtil.log(2, "701 aid is empty.");
            throw new IllegalArgumentException(EXC_INVALID_AID);
        }
        checkConnected(true);
        onlineCheck();
        LogMgrUtil.log(5, "999");
    }

    private void checkSemClientVersion() throws SemClientException {
        LogMgrUtil.log(9, "000");
        SemClientVersion semClientVersion = mSemClientVersion;
        if (semClientVersion == null) {
            LogMgrUtil.log(2, "700 mSemClientVersion is null.");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        if (semClientVersion.majorVersionCode < 33 || (mSemClientVersion.majorVersionCode == 33 && mSemClientVersion.minorVersionCode <= 23)) {
            LogMgrUtil.log(2, "701 DCK API not support : " + mSemClientVersion.version + " majorVersionCode : " + mSemClientVersion.majorVersionCode + " minorVersionCode : " + mSemClientVersion.minorVersionCode + " revisionVersionCode : " + mSemClientVersion.revisionVersionCode + " additionalInformation : " + mSemClientVersion.additionalInformation);
            throw new SemClientException(502, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgrUtil.log(9, "999");
    }

    private static ServiceInfo getSemClientService(Context context, String str) {
        PackageInfo packageInfo;
        LogMgrUtil.log(5, "000");
        ServiceInfo serviceInfo = null;
        if (context == null) {
            LogMgrUtil.log(2, "700 context is null");
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            LogMgrUtil.log(9, "001 PackageManager.getPackageInfo");
            if (Build.VERSION.SDK_INT >= 33) {
                LogMgrUtil.log(9, "002 called PackageManager.getPackageInfo for 33 and over");
                packageInfo = packageManager.getPackageInfo(str, PackageManager.PackageInfoFlags.of(4L));
            } else {
                LogMgrUtil.log(9, "003 called PackageManager.getPackageInfo for less than 33");
                packageInfo = packageManager.getPackageInfo(str, 4);
            }
            if (packageInfo.services != null) {
                ServiceInfo[] serviceInfoArr = packageInfo.services;
                int length = serviceInfoArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    ServiceInfo serviceInfo2 = serviceInfoArr[i];
                    if (SEM_CLIENT_ADAPTER_CLASS_NAME.equals(serviceInfo2.name)) {
                        LogMgrUtil.log(8, "004 service:" + serviceInfo2);
                        serviceInfo = serviceInfo2;
                        break;
                    }
                    i++;
                }
            }
            LogMgrUtil.log(5, "999");
            return serviceInfo;
        } catch (PackageManager.NameNotFoundException unused) {
            LogMgrUtil.log(2, "701 " + str + " cannot be found");
            return null;
        }
    }

    private PackageInfo getPackageInfo(Context context, String str) {
        PackageInfo packageInfo;
        LogMgrUtil.log(9, "000");
        if (context == null) {
            LogMgrUtil.log(2, "700 context is null");
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            LogMgrUtil.log(9, "001 PackageManager.getPackageInfo");
            if (Build.VERSION.SDK_INT >= 33) {
                LogMgrUtil.log(9, "002 called PackageManager.getPackageInfo for 33 and over");
                packageInfo = packageManager.getPackageInfo(str, PackageManager.PackageInfoFlags.of(4L));
            } else {
                LogMgrUtil.log(9, "003 called PackageManager.getPackageInfo for less than 33");
                packageInfo = packageManager.getPackageInfo(str, 4);
            }
            LogMgrUtil.log(9, "999 packageInfo:" + packageInfo);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException unused) {
            LogMgrUtil.log(2, "701 " + str + " cannot be found");
            return null;
        } catch (UnsupportedOperationException unused2) {
            LogMgrUtil.log(2, "702 getPackageInfo is not supported.");
            return null;
        }
    }

    private void checkAndSetSemcApp(boolean z, boolean z2, OnConnectedListener onConnectedListener) throws SemClientException {
        LogMgrUtil.log(7, "000");
        if (!z2) {
            if (z) {
                LogMgrUtil.log(9, "001");
                onConnectedListener.onError(506, "", ObfuscatedMsgUtil.executionPoint());
            } else {
                LogMgrUtil.log(2, "701 SEMC app cannot be found.");
                throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
            }
        }
        this.mConnectSemcPackageName = "com.felicanetworks.semcapp";
        LogMgrUtil.log(7, "999");
    }

    protected void bindSemClient(boolean z, final Context context, final SemClientApiInfo semClientApiInfo, final OnConnectedListener onConnectedListener) throws SemClientException, IllegalArgumentException {
        String str;
        LogMgrUtil.log(5, "000");
        if (context == null) {
            LogMgrUtil.log(2, "700 Failed to get context");
            throw new IllegalArgumentException("The specified Context is null.");
        }
        if (semClientApiInfo == null) {
            LogMgrUtil.log(2, "701 connectApiInfo is null");
            throw new SemClientException(900, ObfuscatedMsgUtil.executionPoint());
        }
        int mode = semClientApiInfo.getMode();
        if (semClientApiInfo.getIsCalledFromInternal()) {
            synchronized (this) {
                this.mConnectSemcPackageName = this.mPackageName;
            }
        } else if (mode != 4) {
            if (getSemClientService(context, "com.felicanetworks.mfm.main") == null) {
                LogMgrUtil.log(2, "702 MENUApp SemClient Service cannot be found.");
                throw new SemClientException(501, ObfuscatedMsgUtil.executionPoint());
            }
            this.mConnectSemcPackageName = "com.felicanetworks.mfm.main";
        } else {
            PackageInfo packageInfo = getPackageInfo(context, "com.felicanetworks.mfm.main");
            PackageInfo packageInfo2 = getPackageInfo(context, "com.felicanetworks.semcapp");
            boolean z2 = packageInfo != null;
            boolean z3 = packageInfo2 != null;
            boolean zHasConfigFile = hasConfigFile();
            if (!z2 && !z3) {
                if (zHasConfigFile) {
                    LogMgrUtil.log(2, "703 MENUApp and SEMCApp cannot be found. common.cfg can be found.");
                    throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
                }
                LogMgrUtil.log(2, "704 MENUApp and SEMCApp and common.cfg cannot be found.");
                throw new SemClientException(506, ObfuscatedMsgUtil.executionPoint());
            }
            if (zHasConfigFile && getSemClientService(context, "com.felicanetworks.mfm.main") != null) {
                this.mConnectSemcPackageName = "com.felicanetworks.mfm.main";
                if (z) {
                    checkAndSetSemcApp(true, z3, onConnectedListener);
                    this.mConnectApiInfo.setIsReconnectSEMCApp(true);
                }
            } else {
                checkAndSetSemcApp(false, z3, onConnectedListener);
            }
        }
        if ("com.felicanetworks.mfm.main".equals(this.mConnectSemcPackageName)) {
            str = "BE:51:DB:F4:FE:C8:9B:D3:28:46:45:7B:13:B7:30:08:76:AF:55:94:D2:87:4D:EE:02:69:04:96:5A:E4:A6:CB";
        } else {
            str = "BE:51:DB:F4:FE:C8:9B:D3:28:46:45:7B:13:B7:30:08:76:AF:55:94:D2:87:4D:EE:02:69:04:96:5A:E4:A6:CB";
        }
        if (!SignatureUtil.checkAppCertHash(context, str, this.mConnectSemcPackageName)) {
            LogMgrUtil.log(2, "705 Failed to connect for SemClient Service. AppCertHash check failed.");
            throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(this.mConnectSemcPackageName, SEM_CLIENT_ADAPTER_CLASS_NAME));
        if (z) {
            this.mOnConnectedListener = onConnectedListener;
            this.mContext = new WeakReference<>(context.getApplicationContext());
        }
        this.mBindTimerHandler.startTimer(10000);
        LogMgrUtil.log(8, "001 Context.bindService");
        if (!context.bindService(intent, this.mConnectionHooker, 1)) {
            LogMgrUtil.log(2, "706 Failed to connect for SemClient Service");
            this.mBindTimerHandler.stopTimer();
            context.unbindService(this.mConnectionHooker);
            if (mode == 4 && "com.felicanetworks.mfm.main".equals(this.mConnectSemcPackageName)) {
                new Thread(new Runnable() { // from class: com.felicanetworks.semc.SemClient$$ExternalSyntheticLambda1
                    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m424lambda$bindSemClient$0$comfelicanetworkssemcSemClient(context, semClientApiInfo, onConnectedListener);
                    }
                }).start();
                return;
            }
            throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgrUtil.log(5, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX INFO: renamed from: lambda$bindSemClient$0$com-felicanetworks-semc-SemClient, reason: not valid java name */
    /* synthetic */ void m424lambda$bindSemClient$0$comfelicanetworkssemcSemClient(Context context, SemClientApiInfo semClientApiInfo, OnConnectedListener onConnectedListener) {
        try {
            bindSemClient(true, context, semClientApiInfo, onConnectedListener);
        } catch (SemClientException e) {
            LogMgrUtil.log(2, "707 SemClientException id:" + e.getErrorCode() + "message:" + e.getMessage());
            onConnectedListener.onError(e.getErrorCode(), e.getAdditionalErrorInfo(), e.getMessage());
        } catch (IllegalArgumentException e2) {
            LogMgrUtil.log(2, "708 IllegalArgumentException message:" + e2.getMessage());
            onConnectedListener.onError(900, "", e2.getMessage());
        }
    }

    private boolean hasConfigFile() {
        LogMgrUtil.log(7, "000");
        String[] strArr = FlavorConstForUtility.COMMON_CONFIG_FILE_PATH;
        int length = strArr.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            try {
            } catch (Exception e) {
                LogMgrUtil.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            }
            if (new File(strArr[i] + "common.cfg").exists()) {
                z = true;
                break;
            }
            i++;
        }
        LogMgrUtil.log(7, "999");
        return z;
    }

    protected void unbindSemClient() {
        String str = "003";
        LogMgrUtil.log(9, "000");
        try {
            try {
                LogMgrUtil.log(5, "001");
                this.mContext.get().unbindService(this.mConnectionHooker);
                LogMgrUtil.log(5, "002");
            } catch (Exception unused) {
                LogMgrUtil.log(2, "700 Unbind failed");
            }
            LogMgrUtil.log(9, "003");
            cleanUp();
            str = "999";
            LogMgrUtil.log(9, "999");
        } catch (Throwable th) {
            LogMgrUtil.log(9, str);
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
            LogMgrUtil.log(5, "000 timeout=" + i);
            if (i > 0) {
                LogMgrUtil.log(9, "001");
                sendMessageDelayed(SemClient.this.mBindTimerHandler.obtainMessage(1), i);
            }
            LogMgrUtil.log(5, "999");
        }

        void stopTimer() {
            LogMgrUtil.log(5, "000");
            removeMessages(1);
            LogMgrUtil.log(5, "999");
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int mode;
            LogMgrUtil.log(5, "000 what=" + message.what);
            if (message.what == 1) {
                LogMgrUtil.log(2, "800 bind timeout connecting=" + SemClient.this.checkConnecting());
                synchronized (SemClient.this) {
                    OnConnectedListener onConnectedListener = null;
                    if (SemClient.this.checkConnecting()) {
                        LogMgrUtil.log(9, "001");
                        final OnConnectedListener onConnectedListener2 = SemClient.this.mOnConnectedListener;
                        SemClient.this.mOnConnectedListener = null;
                        final Context context = (Context) SemClient.this.mContext.get();
                        String str = SemClient.this.mConnectSemcPackageName;
                        SemClient.this.unbindSemClient();
                        try {
                            mode = SemClient.this.mConnectApiInfo.getMode();
                        } catch (SemClientException e) {
                            LogMgrUtil.log(2, "701 SemClientException message:" + e.getMessage());
                            mode = 0;
                        }
                        if (mode == 4 && "com.felicanetworks.mfm.main".equals(str)) {
                            new Thread(new Runnable() { // from class: com.felicanetworks.semc.SemClient$BindTimerHandler$$ExternalSyntheticLambda0
                                /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.m425x86c28359(context, onConnectedListener2);
                                }
                            }).start();
                            return;
                        }
                        onConnectedListener = onConnectedListener2;
                    }
                    if (onConnectedListener != null) {
                        LogMgrUtil.log(5, "002 Do the callback");
                        onConnectedListener.onError(900, "", ObfuscatedMsgUtil.executionPoint());
                    }
                }
            }
            super.handleMessage(message);
            LogMgrUtil.log(5, "999");
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX INFO: renamed from: lambda$handleMessage$0$com-felicanetworks-semc-SemClient$BindTimerHandler, reason: not valid java name */
        /* synthetic */ void m425x86c28359(Context context, OnConnectedListener onConnectedListener) {
            try {
                SemClient semClient = SemClient.this;
                semClient.bindSemClient(true, context, semClient.mConnectApiInfo, onConnectedListener);
            } catch (SemClientException e) {
                LogMgrUtil.log(2, "702 SemClientException id:" + e.getErrorCode() + "message:" + e.getMessage());
                onConnectedListener.onError(e.getErrorCode(), e.getAdditionalErrorInfo(), e.getMessage());
            } catch (IllegalArgumentException e2) {
                LogMgrUtil.log(2, "703 IllegalArgumentException message:" + e2.getMessage());
                onConnectedListener.onError(900, "", e2.getMessage());
            }
        }
    }

    class SemClientConnection implements ServiceConnection {
        SemClientConnection() {
        }

        /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [3523=8, 3526=7, 3528=7, 3529=7, 3530=8] */
        /* JADX WARN: Removed duplicated region for block: B:80:0x0259  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0265  */
        @Override // android.content.ServiceConnection
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            String strExecutionPoint;
            int errorCode;
            int i;
            OnConnectedListener onConnectedListener;
            SemClient semClient;
            LogMgrUtil.log(5, "000 ComponentName:" + componentName.getClassName() + " IBinder:" + iBinder);
            synchronized (SemClient.this) {
                SemClient.this.mSemClientService = ISemClient.Stub.asInterface(iBinder);
                SemClient.this.mBindTimerHandler.stopTimer();
                OnConnectedListener onConnectedListener2 = null;
                if (SemClient.this.mOnConnectedListener != null) {
                    LogMgrUtil.log(9, "001");
                    boolean z = true;
                    try {
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                if (SemClient.this.mSemClientService != null) {
                                                    SemClient.this.mConnectApiInfo.setPackageName(SemClient.this.mPackageName);
                                                    SemClientResultInfo semClientResultInfoCallSemClientApi = SemClient.this.mSemClientService.callSemClientApi(SemClient.this.mConnectApiInfo, SemClient.this.mSemClientEventListener, 2);
                                                    SemClientUtil.checkSemClientResult(semClientResultInfoCallSemClientApi);
                                                    if (semClientResultInfoCallSemClientApi.getIntReturnValue() == 2) {
                                                        OnConnectedListener onConnectedListener3 = SemClient.this.mOnConnectedListener;
                                                        SemClient.this.mOnConnectedListener = null;
                                                        Context context = (Context) SemClient.this.mContext.get();
                                                        SemClient.this.unbindSemClient();
                                                        SemClient semClient2 = SemClient.this;
                                                        semClient2.bindSemClient(true, context, semClient2.mConnectApiInfo, onConnectedListener3);
                                                        LogMgrUtil.log(9, "003");
                                                        return;
                                                    }
                                                    z = false;
                                                } else {
                                                    LogMgrUtil.log(2, "700 SemClientService is null");
                                                }
                                                LogMgrUtil.log(9, "003");
                                                if (z) {
                                                    LogMgrUtil.log(9, "004");
                                                    OnConnectedListener onConnectedListener4 = SemClient.this.mOnConnectedListener;
                                                    SemClient.this.mOnConnectedListener = null;
                                                    SemClient.this.unbindSemClient();
                                                    onConnectedListener2 = onConnectedListener4;
                                                    strExecutionPoint = null;
                                                }
                                            } catch (SemClientException e) {
                                                LogMgrUtil.log(9, "002");
                                                errorCode = (e.getErrorCode() == 101 || e.getErrorCode() == 103 || e.getErrorCode() == 503 || e.getErrorCode() == 201 || e.getErrorCode() == 506) ? e.getErrorCode() : 900;
                                                LogMgrUtil.log(2, "701 SemClientException errorCode:" + e.getErrorCode() + " msg:" + e.getMessage());
                                                strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e);
                                                LogMgrUtil.log(9, "003");
                                                LogMgrUtil.log(9, "004");
                                                onConnectedListener = SemClient.this.mOnConnectedListener;
                                                SemClient.this.mOnConnectedListener = null;
                                                semClient = SemClient.this;
                                                semClient.unbindSemClient();
                                                onConnectedListener2 = onConnectedListener;
                                                LogMgrUtil.log(9, "005");
                                                if (onConnectedListener2 != null) {
                                                }
                                                LogMgrUtil.log(i, "999");
                                            }
                                        } catch (IllegalArgumentException e2) {
                                            LogMgrUtil.log(2, "703 IllegalArgumentException msg:" + e2.getMessage());
                                            errorCode = "The specified parameter mode is invalid.".equals(e2.getMessage()) ? 104 : 900;
                                            strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e2);
                                            LogMgrUtil.log(9, "003");
                                            LogMgrUtil.log(9, "004");
                                            onConnectedListener = SemClient.this.mOnConnectedListener;
                                            SemClient.this.mOnConnectedListener = null;
                                            semClient = SemClient.this;
                                            semClient.unbindSemClient();
                                            onConnectedListener2 = onConnectedListener;
                                            LogMgrUtil.log(9, "005");
                                            if (onConnectedListener2 != null) {
                                            }
                                            LogMgrUtil.log(i, "999");
                                        }
                                    } catch (Exception e3) {
                                        LogMgrUtil.log(2, "705 Exception " + e3.getMessage());
                                        strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e3);
                                        LogMgrUtil.log(9, "003");
                                        LogMgrUtil.log(9, "004");
                                        OnConnectedListener onConnectedListener5 = SemClient.this.mOnConnectedListener;
                                        SemClient.this.mOnConnectedListener = null;
                                        SemClient.this.unbindSemClient();
                                        onConnectedListener2 = onConnectedListener5;
                                    }
                                    errorCode = 900;
                                } catch (IllegalStateException e4) {
                                    LogMgrUtil.log(2, "702 IllegalStateException msg:" + e4.getMessage());
                                    errorCode = "Illegal state currently connecting.".equals(e4.getMessage()) ? 101 : 900;
                                    strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e4);
                                    LogMgrUtil.log(9, "003");
                                    LogMgrUtil.log(9, "004");
                                    onConnectedListener = SemClient.this.mOnConnectedListener;
                                    SemClient.this.mOnConnectedListener = null;
                                    semClient = SemClient.this;
                                    semClient.unbindSemClient();
                                    onConnectedListener2 = onConnectedListener;
                                    LogMgrUtil.log(9, "005");
                                    if (onConnectedListener2 != null) {
                                    }
                                    LogMgrUtil.log(i, "999");
                                }
                            } catch (RemoteException e5) {
                                LogMgrUtil.log(2, "704 RemoteException msg:" + e5.getMessage());
                                strExecutionPoint = ObfuscatedMsgUtil.executionPoint(e5);
                                LogMgrUtil.log(9, "003");
                                LogMgrUtil.log(9, "004");
                                OnConnectedListener onConnectedListener6 = SemClient.this.mOnConnectedListener;
                                SemClient.this.mOnConnectedListener = null;
                                SemClient.this.unbindSemClient();
                                onConnectedListener2 = onConnectedListener6;
                                errorCode = 901;
                            }
                            LogMgrUtil.log(9, "005");
                            if (onConnectedListener2 != null) {
                                i = 5;
                                LogMgrUtil.log(5, "006 Do the callback");
                                onConnectedListener2.onError(errorCode, "", strExecutionPoint);
                            } else {
                                i = 5;
                            }
                            LogMgrUtil.log(i, "999");
                        } catch (Throwable th) {
                            th = th;
                            z = false;
                            LogMgrUtil.log(9, "003");
                            if (z) {
                                LogMgrUtil.log(9, "004");
                                OnConnectedListener onConnectedListener7 = SemClient.this.mOnConnectedListener;
                                SemClient.this.mOnConnectedListener = null;
                                SemClient.this.unbindSemClient();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } else {
                    LogMgrUtil.log(2, "706");
                    SemClient.this.unbindSemClient();
                }
                strExecutionPoint = null;
                errorCode = 900;
                LogMgrUtil.log(9, "005");
                if (onConnectedListener2 != null) {
                }
                LogMgrUtil.log(i, "999");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            OnGetAppletStatusListener onGetAppletStatusListener;
            OnConnectedListener onConnectedListener;
            OnTsmSequenceListener onTsmSequenceListener;
            OnInstallAppletListener onInstallAppletListener;
            OnUpgradeAppletListener onUpgradeAppletListener;
            OnDeleteAppletListener onDeleteAppletListener;
            LogMgrUtil.log(5, "000 ComponentName:" + componentName.getClassName());
            LogMgrUtil.log(2, "700 SemClient Service was disconnected.");
            synchronized (SemClient.this) {
                onGetAppletStatusListener = null;
                if (SemClient.this.mOnConnectedListener != null) {
                    LogMgrUtil.log(9, "001");
                    onConnectedListener = SemClient.this.mOnConnectedListener;
                    SemClient.this.mOnConnectedListener = null;
                } else {
                    onConnectedListener = null;
                }
                if (SemClient.this.mOnTsmSequenceListener != null) {
                    LogMgrUtil.log(9, "002");
                    onTsmSequenceListener = SemClient.this.mOnTsmSequenceListener;
                    SemClient.this.mOnTsmSequenceListener = null;
                } else {
                    onTsmSequenceListener = null;
                }
                if (SemClient.this.mOnInstallAppletListener != null) {
                    LogMgrUtil.log(9, "003");
                    onInstallAppletListener = SemClient.this.mOnInstallAppletListener;
                    SemClient.this.mOnInstallAppletListener = null;
                } else {
                    onInstallAppletListener = null;
                }
                if (SemClient.this.mOnUpgradeAppletListener != null) {
                    LogMgrUtil.log(9, "004");
                    onUpgradeAppletListener = SemClient.this.mOnUpgradeAppletListener;
                    SemClient.this.mOnUpgradeAppletListener = null;
                } else {
                    onUpgradeAppletListener = null;
                }
                if (SemClient.this.mOnDeleteAppletListener != null) {
                    LogMgrUtil.log(9, "005");
                    onDeleteAppletListener = SemClient.this.mOnDeleteAppletListener;
                    SemClient.this.mOnDeleteAppletListener = null;
                } else {
                    onDeleteAppletListener = null;
                }
                if (SemClient.this.mOnGetAppletStatusListener != null) {
                    LogMgrUtil.log(9, "006");
                    OnGetAppletStatusListener onGetAppletStatusListener2 = SemClient.this.mOnGetAppletStatusListener;
                    SemClient.this.mOnGetAppletStatusListener = null;
                    onGetAppletStatusListener = onGetAppletStatusListener2;
                }
                SemClient.this.unbindSemClient();
            }
            if (onConnectedListener != null) {
                LogMgrUtil.log(9, "003");
                onConnectedListener.onError(900, "", ObfuscatedMsgUtil.executionPoint());
            }
            if (onTsmSequenceListener != null) {
                LogMgrUtil.log(9, "004");
                onTsmSequenceListener.onError(900, "", ObfuscatedMsgUtil.executionPoint());
            }
            if (onInstallAppletListener != null) {
                LogMgrUtil.log(9, "004");
                onInstallAppletListener.onError(900, "", ObfuscatedMsgUtil.executionPoint());
            }
            if (onUpgradeAppletListener != null) {
                LogMgrUtil.log(9, "004");
                onUpgradeAppletListener.onError(900, "", ObfuscatedMsgUtil.executionPoint());
            }
            if (onDeleteAppletListener != null) {
                LogMgrUtil.log(9, "004");
                onDeleteAppletListener.onError(900, "", ObfuscatedMsgUtil.executionPoint());
            }
            if (onGetAppletStatusListener != null) {
                LogMgrUtil.log(9, "004");
                onGetAppletStatusListener.onError(900, "", ObfuscatedMsgUtil.executionPoint());
            }
            LogMgrUtil.log(5, "999");
        }
    }

    private void init(Context context) throws IllegalArgumentException {
        LogMgrUtil.log(5, "000");
        if (context == null) {
            LogMgrUtil.log(2, "700 context is null");
            throw new IllegalArgumentException("The specified Context is null.");
        }
        synchronized (this) {
            this.mContext = new WeakReference<>(context.getApplicationContext());
            this.mPackageName = context.getApplicationContext().getPackageName();
        }
        LogMgrUtil.log(5, "999");
    }

    public static class SdKeyDerivationData {
        public final String casdCertificate;
        public final String crt;
        public final String dr;
        public final String receipt;
        public final String sdAid;

        SdKeyDerivationData(String str, String str2, String str3, String str4, String str5) {
            this.sdAid = str;
            this.dr = str2;
            this.crt = str3;
            this.receipt = str4;
            this.casdCertificate = str5;
        }

        public String getSdAid() {
            return this.sdAid;
        }

        public String getDr() {
            return this.dr;
        }

        public String getCrt() {
            return this.crt;
        }

        public String getReceipt() {
            return this.receipt;
        }

        public String getCasdCertificate() {
            return this.casdCertificate;
        }
    }

    public static class AppletStatus {
        public static final String APPLET_STATUS_INSTALLED = "Installed";
        public static final String APPLET_STATUS_INSTALLING_OR_DELETING = "InstallingOrDeleting";
        public static final String APPLET_STATUS_UPGRADING = "Upgrading";
        public static final String APPLET_STATUS_UPGRADING_WAITING_FOR_DELETION = "Upgrading-WaitingForDeletion";
        public static final String APPLET_STATUS_UPGRADING_WAITING_FOR_RECOVERY = "Upgrading-WaitingForRecovery";
        public final String newSpAppletVersion;
        public final String oldSpAppletVersion;
        public final String spAppletVersion;
        public final String status;

        AppletStatus(String str, String str2, String str3, String str4) {
            this.spAppletVersion = str;
            this.oldSpAppletVersion = str2;
            this.newSpAppletVersion = str3;
            this.status = str4;
        }

        public String getSpAppletVersion() {
            return this.spAppletVersion;
        }

        public String getOldSpAppletVersion() {
            return this.oldSpAppletVersion;
        }

        public String getNewSpAppletVersion() {
            return this.newSpAppletVersion;
        }

        public String getStatus() {
            return this.status;
        }
    }
}
