package com.felicanetworks.semc.sws;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import androidx.credentials.provider.CredentialEntry;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.semc.SemClient;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.SemClientInternal;
import com.felicanetworks.semc.permit.SpAppInfo;
import com.felicanetworks.semc.sws.LogUploader;
import com.felicanetworks.semc.sws.json.SpAppInfoJsonArray;
import com.felicanetworks.semc.util.DateUtil;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SharedPrefsProvider;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyClientEventWorker extends Worker {
    protected static final HashMap<Integer, String> EVENT_TYPE_MAP;
    public static final String EXT_KEY_EVENT_TYPE = "eventType";
    public static final String EXT_KEY_PACKAGE_NAME = "packageName";
    public static final String EXT_KEY_RETRY_COUNT = "retryCount";
    public static final int EXT_VALUE_EVENT_TYPE_SP_APP_UNINSTALLED = 0;
    public static final int EXT_VALUE_EVENT_TYPE_UNKNOWN = 1;
    private static final String SP_APP_UNINSTALLED = "SP_APP_UNINSTALLED";
    private static final String UNKNOWN = "UNKNOWN";
    private LogUploader mLogUploader;

    static {
        HashMap<Integer, String> map = new HashMap<>();
        EVENT_TYPE_MAP = map;
        map.put(0, SP_APP_UNINSTALLED);
        map.put(1, UNKNOWN);
    }

    public NotifyClientEventWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
        LogMgr.log(7, "000");
        LogMgr.log(7, "999");
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        LogMgr.log(7, "000");
        int i = getInputData().getInt(EXT_KEY_EVENT_TYPE, 1);
        String string = getInputData().getString(EXT_KEY_PACKAGE_NAME);
        if (string == null) {
            LogMgr.log(7, "998");
            return ListenableWorker.Result.failure();
        }
        int i2 = getInputData().getInt("retryCount", 0);
        LogMgr.log(8, "001 eventType=" + i + " packageName=" + string + " retryCount=" + i2);
        Context applicationContext = getApplicationContext();
        if (i == 0) {
            LogMgr.log(9, "001");
            onNotifySpAppUnInstalledReceived(applicationContext, EVENT_TYPE_MAP.get(0), string, i2);
        } else {
            LogMgr.log(8, "700 eventType : " + i);
            try {
                LogUploader.Request requestBuild = LogUploader.Request.build(applicationContext, LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.UNKNOWN, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.SERVER_PROCESS, LogUploader.MessageCode.ErrorInfo.EVENT_TYPE_IS_NOT_SP_APP_UNINSTALLED, getApplicationContext(), LogUploader.DUMMY_SE_ID));
                LogUploader logUploader = new LogUploader(new OnUploadFinishListenerImpl());
                this.mLogUploader = logUploader;
                logUploader.start();
                this.mLogUploader.request(requestBuild);
            } catch (Exception e) {
                LogMgr.log(7, "998 " + e.getMessage());
                return ListenableWorker.Result.success();
            }
        }
        LogMgr.log(7, "999");
        return ListenableWorker.Result.success();
    }

    private void onNotifySpAppUnInstalledReceived(Context context, String str, String str2, int i) {
        LogMgr.log(8, "000 eventType=" + str + " packageName=" + str2 + " retryCount=" + i);
        SessionHandler.getInstance().post(new NotifySpAppUninstalledRequest(context, str, str2, i));
        LogMgr.log(8, "999");
    }

    private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
        private OnUploadFinishListenerImpl() {
        }

        @Override // com.felicanetworks.semc.sws.LogUploader.OnUploadFinishListener
        public void onFinished(String str, String str2) {
            LogMgr.log(8, "000 requestId=" + str + " result=" + str2);
            if (NotifyClientEventWorker.this.mLogUploader != null) {
                NotifyClientEventWorker.this.mLogUploader.shutdown();
                NotifyClientEventWorker.this.mLogUploader = null;
            }
            LogMgr.log(8, "999");
        }
    }

    static final class NotifySpAppUninstalledRequest extends SessionHandler.SemClientSessionRequest {
        private final String mEventType;

        NotifySpAppUninstalledRequest(Context context, String str, String str2, int i) {
            super(context, str2, i);
            this.mEventType = str;
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:61:0x015d */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:70:0x01bf */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:78:0x01ce */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:80:0x01d0 */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:84:0x01ec */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:97:0x0015 */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:51:0x011b A[PHI: r9
  0x011b: PHI (r9v19 ??) = (r9v43 ??), (r9v44 ??) binds: [B:30:0x00ce, B:32:0x00d4] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:90:0x0214  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x022f  */
        /* JADX WARN: Type inference failed for: r9v0 */
        /* JADX WARN: Type inference failed for: r9v1 */
        /* JADX WARN: Type inference failed for: r9v10 */
        /* JADX WARN: Type inference failed for: r9v11, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r9v12 */
        /* JADX WARN: Type inference failed for: r9v16 */
        /* JADX WARN: Type inference failed for: r9v19 */
        /* JADX WARN: Type inference failed for: r9v2, types: [android.database.Cursor] */
        /* JADX WARN: Type inference failed for: r9v3 */
        /* JADX WARN: Type inference failed for: r9v33 */
        /* JADX WARN: Type inference failed for: r9v34 */
        /* JADX WARN: Type inference failed for: r9v35 */
        /* JADX WARN: Type inference failed for: r9v36 */
        /* JADX WARN: Type inference failed for: r9v37 */
        /* JADX WARN: Type inference failed for: r9v38 */
        /* JADX WARN: Type inference failed for: r9v39 */
        /* JADX WARN: Type inference failed for: r9v4, types: [android.database.Cursor] */
        /* JADX WARN: Type inference failed for: r9v40 */
        /* JADX WARN: Type inference failed for: r9v43 */
        /* JADX WARN: Type inference failed for: r9v44 */
        /* JADX WARN: Type inference failed for: r9v47 */
        /* JADX WARN: Type inference failed for: r9v48 */
        /* JADX WARN: Type inference failed for: r9v5 */
        /* JADX WARN: Type inference failed for: r9v6 */
        /* JADX WARN: Type inference failed for: r9v8 */
        /* JADX WARN: Type inference failed for: r9v9 */
        @Override // com.felicanetworks.semc.sws.NotifyClientEventWorker.SessionHandler.SemClientLifecycleAdapter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public boolean onPrepareStarting() throws Throwable {
            Cursor cursorQuery;
            String string;
            long j;
            String str;
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean needsNotifyUninstallation;
            LogMgr.log(7, "000");
            ?? r9 = 0;
            r9 = 0;
            r9 = 0;
            r9 = 0;
            try {
                try {
                    LogMgr.log(9, "001 mContext.getContentResolver.query() in.");
                    cursorQuery = this.mContext.getContentResolver().query(SharedPrefsProvider.getNotifyClientEventContentsUri(this.mContext), null, null, null, null);
                } catch (Throwable th) {
                    th = th;
                }
            } catch (RuntimeException e) {
                e = e;
            } catch (Exception e2) {
                e = e2;
            }
            try {
                LogMgr.log(9, "002 mContext.getContentResolver.query() out.");
                string = CredentialEntry.FALSE_STRING;
                if (cursorQuery == null || !cursorQuery.moveToFirst()) {
                    j = 0;
                } else {
                    int columnIndex = cursorQuery.getColumnIndex(SharedPrefsProvider.NotifyClientEventContents.Setting.IS_EXISTS);
                    if (columnIndex < 0) {
                        throw new Exception("failed to get isExists");
                    }
                    string = cursorQuery.getString(columnIndex);
                    LogMgr.log(9, "003 : isExists = " + string);
                    int columnIndex2 = cursorQuery.getColumnIndex(SharedPrefsProvider.NotifyClientEventContents.Setting.SP_APP_INFO_LIST_STR);
                    if (columnIndex2 < 0) {
                        throw new Exception("failed to get spAppInfoListStr");
                    }
                    String string2 = cursorQuery.getString(columnIndex2);
                    LogMgr.log(9, "004 : spAppInfoListStr = " + string2);
                    int columnIndex3 = cursorQuery.getColumnIndex(SharedPrefsProvider.NotifyClientEventContents.Setting.SAVE_DATE);
                    if (columnIndex3 < 0) {
                        throw new Exception("failed to get saveDate");
                    }
                    j = cursorQuery.getLong(columnIndex3);
                    LogMgr.log(9, "005 : saveDate = " + j);
                    r9 = string2;
                }
            } catch (RuntimeException e3) {
                e = e3;
                r9 = cursorQuery;
                LogMgr.log(9, "011 Exception.getMessage=" + e.getMessage());
                r9 = r9;
                if (r9 != 0) {
                    r9.close();
                }
            } catch (Exception e4) {
                e = e4;
                r9 = cursorQuery;
                LogMgr.log(9, "012 Exception.getMessage=" + e.getMessage());
                if (r9 != 0) {
                    r9 = r9;
                    r9.close();
                }
            } catch (Throwable th2) {
                th = th2;
                r9 = cursorQuery;
                if (r9 != 0) {
                    r9.close();
                }
                throw th;
            }
            if (Boolean.parseBoolean(string)) {
                LogMgr.log(9, "006");
                if (DateUtil.isTodayInJapan(j)) {
                    LogMgr.log(9, "007");
                    try {
                    } catch (SemClientException e5) {
                        LogMgr.log(2, "701SemClientException:" + e5.getMessage());
                        str = "701SemClientException:";
                        z = false;
                        z2 = true;
                        r9 = str;
                    } catch (JSONException e6) {
                        LogMgr.log(2, "700JSONException:" + e6.getMessage());
                        str = "700JSONException:";
                        z = false;
                        z2 = true;
                        r9 = str;
                    }
                    if (r9 == 0) {
                        throw new JSONException("spAppInfoList is null");
                    }
                    SpAppInfoJsonArray spAppInfoJsonArray = new SpAppInfoJsonArray(r9);
                    ArrayList<SpAppInfo> spAppInfoList = spAppInfoJsonArray.getSpAppInfoList(this.mPackageName);
                    ?? r92 = r9;
                    if (spAppInfoList != null) {
                        int size = spAppInfoList.size();
                        r92 = size;
                        if (size != 0) {
                            String str2 = spAppInfoList.get(0).mSpAppId;
                            int i = 1;
                            while (true) {
                                if (i >= spAppInfoList.size()) {
                                    int i2 = 0;
                                    while (true) {
                                        int length = spAppInfoJsonArray.length();
                                        if (i2 >= length) {
                                            z3 = false;
                                            z4 = length;
                                            break;
                                        }
                                        if (spAppInfoJsonArray.getPackageName(i2).equals(this.mPackageName) && (needsNotifyUninstallation = spAppInfoJsonArray.getNeedsNotifyUninstallation(i2))) {
                                            z3 = true;
                                            z4 = needsNotifyUninstallation;
                                            break;
                                        }
                                        i2++;
                                    }
                                    z = false;
                                    z2 = z3;
                                    r9 = z4;
                                } else {
                                    if (!spAppInfoList.get(i).mSpAppId.equals(str2)) {
                                        z2 = false;
                                        z = true;
                                        r9 = str2;
                                        break;
                                    }
                                    i++;
                                }
                            }
                        } else {
                            z2 = false;
                            z = false;
                            r9 = r92;
                        }
                        if (!z2) {
                            LogMgr.log(9, "008 spAppInfoList don't include packageName:" + this.mPackageName);
                            LogMgr.log(9, "998");
                            if (z) {
                                LogMgr.log(2, "702 spAppInfoList has duplicated packageName:" + this.mPackageName);
                                requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_GETINSTANCE, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.GET_SEM_CLIENT_PROCESS, LogUploader.MessageCode.ErrorInfo.DUPLICATED_PACKAGE_NAME, this.mContext, LogUploader.DUMMY_SE_ID));
                            }
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            return false;
                        }
                    }
                    this.mSemClient = SemClientInternal.getInstance();
                    if (this.mSemClient != null) {
                        requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_GETINSTANCE, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.GET_SEM_CLIENT_PROCESS, LogUploader.MessageCode.ErrorInfo.SEM_CLIENT_IS_NULL, this.mContext, LogUploader.DUMMY_SE_ID));
                        return false;
                    }
                    LogMgr.log(7, "999");
                    return true;
                }
                LogMgr.log(9, "009");
            } else {
                LogMgr.log(9, "010");
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            this.mSemClient = SemClientInternal.getInstance();
            if (this.mSemClient != null) {
            }
        }

        @Override // com.felicanetworks.semc.sws.NotifyClientEventWorker.SessionHandler.SemClientLifecycleAdapter
        public void onPreparedAndCallConnect(SemClient.OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
            LogMgr.log(7, "000");
            this.mSemClient.connect(this.mContext, false, onConnectedListener);
            LogMgr.log(7, "999");
        }

        @Override // com.felicanetworks.semc.sws.NotifyClientEventWorker.SessionHandler.SemClientLifecycleAdapter
        public void onConnectedAndNotifyClientEvent(SemClientInternal.OnNotifyClientEventListener onNotifyClientEventListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
            LogMgr.log(7, "000");
            this.mSemClient.notifyClientEvent(this.mPackageName, this.mEventType, onNotifyClientEventListener);
            LogMgr.log(7, "999");
        }

        @Override // com.felicanetworks.semc.sws.NotifyClientEventWorker.SessionHandler.SemClientLifecycleAdapter
        public void onFinished() {
            LogMgr.log(8, "000");
            try {
                if (this.mSemClient == null) {
                    this.mSemClient = SemClientInternal.getInstance();
                }
                if (this.mSemClient != null) {
                    this.mSemClient.disconnect();
                }
            } catch (SemClientException e) {
                LogMgr.log(2, "700 ErrorCode:" + e.getErrorCode() + " MSG:" + e.getMessage());
            } catch (IllegalStateException e2) {
                LogMgr.log(2, "701 MSG:" + e2.getMessage());
            }
            LogMgr.log(8, "999");
        }

        private void requestLogUpload(String str, LogUploader.Request.LogInfoContent logInfoContent) {
            LogMgr.log(8, "000");
            try {
                LogUploader.Request requestBuild = LogUploader.Request.build(this.mContext, str, logInfoContent);
                this.mLogUploader = new LogUploader(new OnUploadFinishListenerImpl());
                this.mLogUploader.start();
                this.mLogUploader.request(requestBuild);
                LogMgr.log(8, "999");
            } catch (Exception e) {
                LogMgr.log(8, "998 " + e.getMessage());
            }
        }

        private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
            private OnUploadFinishListenerImpl() {
            }

            @Override // com.felicanetworks.semc.sws.LogUploader.OnUploadFinishListener
            public void onFinished(String str, String str2) {
                LogMgr.log(8, "000 requestId=" + str + " result=" + str2);
                if (NotifySpAppUninstalledRequest.this.mLogUploader != null) {
                    NotifySpAppUninstalledRequest.this.mLogUploader.shutdown();
                    NotifySpAppUninstalledRequest.this.mLogUploader = null;
                }
                LogMgr.log(8, "999");
            }
        }
    }

    private static final class SessionHandler {
        private static final Object lock = new Object();
        private static volatile SessionHandler sInstance;
        private final Deque<SemClientSessionRequest> mQueue = new LinkedList();
        private final Handler mThreadHandler;

        interface SemClientLifecycleAdapter {
            void onConnectedAndNotifyClientEvent(SemClientInternal.OnNotifyClientEventListener onNotifyClientEventListener) throws IllegalStateException, SemClientException, IllegalArgumentException;

            void onFinished();

            boolean onPrepareStarting();

            void onPreparedAndCallConnect(SemClient.OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException;
        }

        protected static abstract class SemClientSessionRequest implements SemClientLifecycleAdapter {
            protected final Context mContext;
            protected LogUploader mLogUploader;
            protected final String mPackageName;
            private final int mRetryCount;
            protected SemClientInternal mSemClient;

            SemClientLifecycleAdapter getAdapter() {
                return this;
            }

            SemClientSessionRequest(Context context, String str, int i) {
                this.mContext = context;
                this.mPackageName = str;
                this.mRetryCount = i;
            }
        }

        SessionHandler() {
            HandlerThread handlerThread = new HandlerThread("semClient-session-handler-thread", 0);
            handlerThread.start();
            this.mThreadHandler = new Handler(handlerThread.getLooper() != null ? handlerThread.getLooper() : Looper.getMainLooper());
        }

        public static SessionHandler getInstance() {
            LogMgr.log(6, "000");
            if (sInstance == null) {
                synchronized (lock) {
                    if (sInstance == null) {
                        sInstance = new SessionHandler();
                    }
                }
            }
            LogMgr.log(6, "999");
            return sInstance;
        }

        void post(SemClientSessionRequest semClientSessionRequest) {
            LogMgr.log(9, "000.");
            if (semClientSessionRequest == null) {
                LogMgr.log(2, "700 Request is null.");
                return;
            }
            try {
                synchronized (this.mQueue) {
                    if (!this.mQueue.contains(semClientSessionRequest)) {
                        LogMgr.log(9, "001 add request.");
                        this.mQueue.add(semClientSessionRequest);
                    }
                    if (this.mQueue.getFirst() == semClientSessionRequest) {
                        LogMgr.log(9, "002 start doSemClientServiceConnection.");
                        doSemClientServiceConnection(semClientSessionRequest);
                    }
                }
            } catch (ClassCastException e) {
                LogMgr.log(2, "701 Argument is not SemClientSessionRequest." + e.getMessage());
            } catch (Exception e2) {
                LogMgr.log(2, "702 Request execution failed." + e2.getMessage());
            }
            LogMgr.log(9, "999.");
        }

        void onSessionFinished(SemClientSessionRequest semClientSessionRequest) {
            if (semClientSessionRequest == null) {
                LogMgr.log(2, "700 Request is null.");
                return;
            }
            synchronized (this.mQueue) {
                try {
                    try {
                        this.mQueue.remove(semClientSessionRequest);
                    } catch (ClassCastException unused) {
                        LogMgr.log(2, "701 Argument is not SemClientSessionRequest.");
                    }
                } catch (Exception unused2) {
                    LogMgr.log(2, "702 Failed to remove from queue.");
                }
                runNextRequest();
            }
        }

        void runNextRequest() {
            if (!this.mQueue.isEmpty()) {
                try {
                    post(this.mQueue.getFirst());
                } catch (NoSuchElementException unused) {
                    LogMgr.log(2, "700 runNextRequest is failed.");
                }
            } else {
                this.mThreadHandler.getLooper().quitSafely();
                sInstance = null;
            }
        }

        void doSemClientServiceConnection(SemClientSessionRequest semClientSessionRequest) {
            LogMgr.log(8, "000");
            this.mThreadHandler.post(new SemClientConnectionTask(semClientSessionRequest));
            LogMgr.log(8, "999");
        }

        void doSemClientFinish(SemClientSessionRequest semClientSessionRequest, TimeManager timeManager, boolean z) {
            LogMgr.log(8, "000");
            if (timeManager != null) {
                timeManager.stopConnectTimer();
                timeManager.stopProcessTimer();
            }
            if (z) {
                semClientSessionRequest.getAdapter().onFinished();
            }
            onSessionFinished(semClientSessionRequest);
            LogMgr.log(8, "999");
        }

        static abstract class SemClientAccessTask implements Runnable {
            final SemClientSessionRequest mRequest;
            protected TimeManager mTimeManager;

            abstract void doSemClientAccess();

            SemClientAccessTask(SemClientSessionRequest semClientSessionRequest) {
                this.mRequest = semClientSessionRequest;
            }

            /* JADX DEBUG: Finally have unexpected throw blocks count: 0, expect 1 */
            @Override // java.lang.Runnable
            public void run() {
                LogMgr.log(8, "000 " + getClass().getSimpleName());
                synchronized (this.mRequest) {
                    try {
                        doSemClientAccess();
                    } finally {
                    }
                }
                LogMgr.log(8, "999 " + getClass().getSimpleName());
            }

            final SemClientLifecycleAdapter getLifecycleAdapter() {
                return this.mRequest.getAdapter();
            }
        }

        class SemClientConnectionTask extends SemClientAccessTask {
            private AtomicBoolean mIsLogUploadExecuted;
            private LogUploader mLogUploader;
            private final RegisterRetryWorkManager mRegisterRetryWorkManager;

            SemClientConnectionTask(SemClientSessionRequest semClientSessionRequest) {
                super(semClientSessionRequest);
                this.mRegisterRetryWorkManager = new RegisterRetryWorkManager(this.mRequest.mContext, this.mRequest.mPackageName, this.mRequest.mRetryCount);
                this.mTimeManager = new TimeManager();
                LogUploader logUploader = new LogUploader(new OnUploadFinishListenerImpl());
                this.mLogUploader = logUploader;
                logUploader.start();
                this.mIsLogUploadExecuted = new AtomicBoolean(false);
            }

            @Override // com.felicanetworks.semc.sws.NotifyClientEventWorker.SessionHandler.SemClientAccessTask
            public void doSemClientAccess() {
                LogUploader.Message.ErrorType errorType;
                LogUploader.MessageCode.ErrorInfo errorInfo;
                LogMgr.log(8, "000");
                SemClientLifecycleAdapter lifecycleAdapter = getLifecycleAdapter();
                this.mTimeManager.startConnectTimer(new TimerTask() { // from class: com.felicanetworks.semc.sws.NotifyClientEventWorker.SessionHandler.SemClientConnectionTask.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                        SemClientConnectionTask.this.mRegisterRetryWorkManager.registerWorkManagerForRetry();
                        SemClientConnectionTask.this.requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, LogUploader.MessageCode.ErrorInfo.TIMEOUT, SemClientConnectionTask.this.mRequest.mContext, LogUploader.DUMMY_SE_ID));
                    }
                });
                this.mTimeManager.startProcessTimer(new TimerTask() { // from class: com.felicanetworks.semc.sws.NotifyClientEventWorker.SessionHandler.SemClientConnectionTask.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        String seid = SemClientConnectionTask.this.getSeid();
                        SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                        SemClientConnectionTask.this.requestLogUpload(seid, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.BUSINESS_PROCESS, LogUploader.MessageCode.ErrorInfo.TIMEOUT, SemClientConnectionTask.this.mRequest.mContext, seid));
                    }
                });
                if (!lifecycleAdapter.onPrepareStarting()) {
                    SessionHandler.this.doSemClientFinish(this.mRequest, this.mTimeManager, true);
                    LogMgr.log(8, "998");
                    return;
                }
                try {
                    lifecycleAdapter.onPreparedAndCallConnect(new OnConnectedListener());
                } catch (SemClientException | IllegalArgumentException | IllegalStateException e) {
                    String string = null;
                    if (e instanceof IllegalArgumentException) {
                        errorType = LogUploader.Message.ErrorType.ILLEGAL_ARGUMENT_EXCEPTION;
                        errorInfo = LogUploader.MessageCode.ErrorInfo.ILLEGAL_ARGUMENT_EXCEPTION;
                    } else if (e instanceof IllegalStateException) {
                        errorType = LogUploader.Message.ErrorType.ILLEGAL_STATE_EXCEPTION;
                        errorInfo = LogUploader.MessageCode.ErrorInfo.ILLEGAL_STATE_EXCEPTION;
                    } else {
                        errorType = LogUploader.Message.ErrorType.SEM_CLIENT_EXCEPTION;
                        errorInfo = LogUploader.MessageCode.ErrorInfo.SEM_CLIENT_EXCEPTION;
                        string = Integer.toString(((SemClientException) e).getErrorCode());
                    }
                    requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, errorType, string, e.getMessage() == null ? "" : e.getMessage(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, errorInfo, this.mRequest.mContext, LogUploader.DUMMY_SE_ID));
                    SessionHandler.this.doSemClientFinish(this.mRequest, this.mTimeManager, false);
                }
                LogMgr.log(8, "999");
            }

            class OnConnectedListener implements SemClient.OnConnectedListener {
                OnConnectedListener() {
                }

                @Override // com.felicanetworks.semc.SemClient.OnConnectedListener
                public void onConnected() {
                    LogUploader.Message.ErrorType errorType;
                    LogUploader.MessageCode.ErrorInfo errorInfo;
                    LogMgr.log(8, "000");
                    SemClientConnectionTask.this.mTimeManager.stopConnectTimer();
                    try {
                        SemClientConnectionTask.this.getLifecycleAdapter().onConnectedAndNotifyClientEvent(SemClientConnectionTask.this.new OnNotifyListener());
                    } catch (SemClientException | IllegalArgumentException | IllegalStateException e) {
                        String string = null;
                        if (e instanceof IllegalArgumentException) {
                            errorType = LogUploader.Message.ErrorType.ILLEGAL_ARGUMENT_EXCEPTION;
                            errorInfo = LogUploader.MessageCode.ErrorInfo.ILLEGAL_ARGUMENT_EXCEPTION;
                        } else if (e instanceof IllegalStateException) {
                            errorType = LogUploader.Message.ErrorType.ILLEGAL_STATE_EXCEPTION;
                            errorInfo = LogUploader.MessageCode.ErrorInfo.ILLEGAL_STATE_EXCEPTION;
                        } else {
                            errorType = LogUploader.Message.ErrorType.SEM_CLIENT_EXCEPTION;
                            errorInfo = LogUploader.MessageCode.ErrorInfo.SEM_CLIENT_EXCEPTION;
                            string = Integer.toString(((SemClientException) e).getErrorCode());
                        }
                        LogUploader.Message.ErrorType errorType2 = errorType;
                        String str = string;
                        LogUploader.MessageCode.ErrorInfo errorInfo2 = errorInfo;
                        String seid = SemClientConnectionTask.this.getSeid();
                        SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                        SemClientConnectionTask.this.requestLogUpload(seid, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_NOTIFY_CLIENT_EVENT, errorType2, str, e.getMessage() == null ? "" : e.getMessage(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.BUSINESS_PROCESS, errorInfo2, SemClientConnectionTask.this.mRequest.mContext, seid));
                    }
                    LogMgr.log(8, "999");
                }

                @Override // com.felicanetworks.semc.SemClient.OnConnectedListener
                public void onError(int i, String str, String str2) {
                    LogMgr.log(8, "000");
                    SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, false);
                    if (needRetryError(i)) {
                        SemClientConnectionTask.this.mRegisterRetryWorkManager.registerWorkManagerForRetry();
                    }
                    synchronized (SemClientConnectionTask.this) {
                        if (!SemClientConnectionTask.this.mIsLogUploadExecuted.get()) {
                            if (SemClientConnectionTask.this.mLogUploader.isLogUpload(i)) {
                                SemClientConnectionTask semClientConnectionTask = SemClientConnectionTask.this;
                                semClientConnectionTask.requestLogUpload(LogUploader.DUMMY_SE_ID, semClientConnectionTask.mLogUploader.getLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, i, str2, LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.CONNECT_PROCESS, SemClientConnectionTask.this.mRequest.mContext, LogUploader.DUMMY_SE_ID));
                            }
                            LogMgr.log(8, "999");
                            return;
                        }
                        LogMgr.log(8, "998");
                    }
                }

                boolean needRetryError(int i) {
                    LogMgr.log(8, "000");
                    boolean z = i == 101 || i == 103 || i == 202 || i == 300 || i == 400;
                    LogMgr.log(8, "999 ret=" + z);
                    return z;
                }
            }

            class OnNotifyListener implements SemClientInternal.OnNotifyClientEventListener {
                OnNotifyListener() {
                }

                @Override // com.felicanetworks.semc.SemClientInternal.OnNotifyClientEventListener
                public void onNotified() {
                    LogMgr.log(8, "000");
                    SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                    LogMgr.log(8, "999");
                }

                @Override // com.felicanetworks.semc.SemClientInternal.OnNotifyClientEventListener
                public void onError(int i, String str, String str2) {
                    LogMgr.log(8, "000");
                    synchronized (SemClientConnectionTask.this) {
                        if (!SemClientConnectionTask.this.mIsLogUploadExecuted.get()) {
                            if (SemClientConnectionTask.this.mLogUploader.isLogUpload(i)) {
                                String seid = SemClientConnectionTask.this.getSeid();
                                SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                                SemClientConnectionTask semClientConnectionTask = SemClientConnectionTask.this;
                                semClientConnectionTask.requestLogUpload(seid, semClientConnectionTask.mLogUploader.getLogInfoContent(LogUploader.Message.Api.SEM_NOTIFY_CLIENT_EVENT, i, str2, LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.BUSINESS_PROCESS, SemClientConnectionTask.this.mRequest.mContext, seid));
                            } else {
                                SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                            }
                            LogMgr.log(8, "999");
                            return;
                        }
                        LogMgr.log(8, "998");
                    }
                }

                boolean needRetryError(int i) {
                    LogMgr.log(8, "000");
                    boolean z = i == 300 || i == 400;
                    LogMgr.log(8, "999 ret=" + z);
                    return z;
                }
            }

            private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
                private OnUploadFinishListenerImpl() {
                }

                @Override // com.felicanetworks.semc.sws.LogUploader.OnUploadFinishListener
                public void onFinished(String str, String str2) {
                    LogMgr.log(8, "000 requestId=" + str + " result=" + str2);
                    synchronized (SemClientConnectionTask.this) {
                        if (SemClientConnectionTask.this.mLogUploader != null) {
                            SemClientConnectionTask.this.mLogUploader.shutdown();
                            SemClientConnectionTask.this.mLogUploader = null;
                        }
                    }
                    LogMgr.log(8, "999");
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public String getSeid() {
                String seid;
                LogMgr.log(8, "000");
                try {
                    seid = this.mRequest.mSemClient.getSeid();
                } catch (SemClientException | IllegalArgumentException | IllegalStateException e) {
                    LogMgr.log(2, "700 MSG:" + e.getMessage());
                    seid = LogUploader.DUMMY_SE_ID;
                }
                LogMgr.log(8, "999");
                return seid;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void requestLogUpload(String str, LogUploader.Request.LogInfoContent logInfoContent) {
                LogMgr.log(8, "000");
                try {
                    LogUploader.Request requestBuild = LogUploader.Request.build(this.mRequest.mContext, str, logInfoContent);
                    synchronized (this) {
                        if (this.mIsLogUploadExecuted.get()) {
                            LogMgr.log(8, "998");
                            return;
                        }
                        this.mLogUploader.request(requestBuild);
                        this.mIsLogUploadExecuted.compareAndSet(false, true);
                        LogMgr.log(8, "999");
                    }
                } catch (Exception e) {
                    LogMgr.log(8, "998 " + e.getMessage());
                }
            }
        }

        static class TimeManager {
            private static final long CONNECT_TIMEOUT = 30000;
            private static final long PROCESS_TIMEOUT = 60000;
            private Timer mConnectTimer;
            private Timer mProcessTimer;

            TimeManager() {
                LogMgr.log(7, "000");
                LogMgr.log(7, "999");
            }

            public void startConnectTimer(TimerTask timerTask) {
                LogMgr.log(7, "000");
                Timer timer = new Timer();
                this.mConnectTimer = timer;
                timer.schedule(timerTask, 30000L);
                LogMgr.log(7, "999");
            }

            public void stopConnectTimer() {
                LogMgr.log(7, "000");
                Timer timer = this.mConnectTimer;
                if (timer != null) {
                    timer.cancel();
                    this.mConnectTimer = null;
                }
                LogMgr.log(7, "999");
            }

            public void startProcessTimer(TimerTask timerTask) {
                LogMgr.log(7, "000");
                Timer timer = new Timer();
                this.mProcessTimer = timer;
                timer.schedule(timerTask, PROCESS_TIMEOUT);
                LogMgr.log(7, "999");
            }

            public void stopProcessTimer() {
                LogMgr.log(7, "000");
                Timer timer = this.mProcessTimer;
                if (timer != null) {
                    timer.cancel();
                    this.mProcessTimer = null;
                }
                LogMgr.log(7, "999");
            }
        }

        static class RegisterRetryWorkManager {
            private final Context mContext;
            private final String mPackageName;
            int mRetryCntForRegisterWorkManager;
            private boolean mSetFlg;

            RegisterRetryWorkManager(Context context, String str, int i) {
                LogMgr.log(7, "000");
                this.mContext = context;
                this.mRetryCntForRegisterWorkManager = i;
                this.mPackageName = str;
                this.mSetFlg = false;
                LogMgr.log(7, "999");
            }

            public void registerWorkManagerForRetry() {
                LogMgr.log(8, "000");
                if (this.mRetryCntForRegisterWorkManager < 3 && !this.mSetFlg) {
                    LogMgr.log(8, "001 doOneTimeWorkRequest; mRetryCntForRegisterWorkManager=" + this.mRetryCntForRegisterWorkManager);
                    this.mSetFlg = true;
                    this.mRetryCntForRegisterWorkManager = this.mRetryCntForRegisterWorkManager + 1;
                    OneTimeWorkRequest oneTimeWorkRequestBuild = new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) NotifyClientEventWorker.class).setInputData(new Data.Builder().putInt(NotifyClientEventWorker.EXT_KEY_EVENT_TYPE, 0).putString(NotifyClientEventWorker.EXT_KEY_PACKAGE_NAME, this.mPackageName).putInt("retryCount", this.mRetryCntForRegisterWorkManager).build()).setInitialDelay(15L, TimeUnit.MINUTES).build();
                    LogMgr.log(9, "002 OneTimeWorkRequest(CloudMessagingWorker) ID=" + oneTimeWorkRequestBuild.getId());
                    WorkManager.getInstance(this.mContext).enqueue(oneTimeWorkRequestBuild);
                }
                LogMgr.log(8, "999");
            }
        }
    }
}
