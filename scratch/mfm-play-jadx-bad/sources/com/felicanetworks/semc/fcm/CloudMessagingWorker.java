package com.felicanetworks.semc.fcm;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.felicanetworks.semc.SemClient;
import com.felicanetworks.semc.SemClientException;
import com.felicanetworks.semc.SemClientInternal;
import com.felicanetworks.semc.sws.LogUploader;
import com.felicanetworks.semc.sws.json.JwsException;
import com.felicanetworks.semc.sws.json.JwsObject;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class CloudMessagingWorker extends Worker {
    public static final String EXT_KEY_ACTION = "action";
    public static final String EXT_KEY_ADDRESS = "address";
    public static final String EXT_KEY_LINKAGE_DATA = "linkageData";
    public static final String EXT_KEY_MESSAGE_TYPE = "messageType";
    public static final String EXT_KEY_RETRY_COUNT = "retryCount";
    public static final int EXT_VALUE_ACTION_ON_MESSAGE_RECEIVED = 0;
    public static final int EXT_VALUE_ACTION_ON_NEW_TOKEN_RECEIVED = 1;
    public static final int EXT_VALUE_ACTION_UNKNOWN = 2;
    private LogUploader mLogUploader;

    private static class MessageData {
        private String mAddress;
        private String mLinkageData;
        private String mMessageType;
        private int mRetryCount;

        private MessageData() {
        }

        public void setAddress(String str) {
            LogMgr.log(8, "000 address=" + str);
            this.mAddress = str;
            LogMgr.log(8, "999");
        }

        public void setMessageType(String str) {
            LogMgr.log(8, "000 messageType=" + str);
            this.mMessageType = str;
            LogMgr.log(8, "999");
        }

        public void setLinkageData(String str) {
            LogMgr.log(8, "000 linkageData=" + str);
            this.mLinkageData = str;
            LogMgr.log(8, "999");
        }

        public void setRetryCount(int i) {
            LogMgr.log(8, "000 retryCount=" + i);
            this.mRetryCount = i;
            LogMgr.log(8, "999");
        }

        public String getAddress() {
            LogMgr.log(8, "000");
            LogMgr.log(8, "000 address=" + this.mAddress);
            return this.mAddress;
        }

        public String getMessageType() {
            LogMgr.log(8, "000");
            LogMgr.log(8, "999 messageType=" + this.mMessageType);
            return this.mMessageType;
        }

        public String getLinkageData() {
            LogMgr.log(8, "000");
            LogMgr.log(8, "999 linkageData=" + this.mLinkageData);
            return this.mLinkageData;
        }

        public int getRetryCount() {
            LogMgr.log(8, "000");
            LogMgr.log(8, "999 retryCount=" + this.mRetryCount);
            return this.mRetryCount;
        }
    }

    public CloudMessagingWorker(Context context, WorkerParameters workerParameters) {
        super(context, workerParameters);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    @Override // androidx.work.Worker
    public ListenableWorker.Result doWork() {
        LogMgr.log(7, "000");
        int i = getInputData().getInt("action", 2);
        LogMgr.log(8, "001 action=" + i);
        MessageData messageData = new MessageData();
        if (i == 0) {
            messageData.setAddress(getInputData().getString(EXT_KEY_ADDRESS));
            messageData.setMessageType(getInputData().getString(EXT_KEY_MESSAGE_TYPE));
            messageData.setLinkageData(getInputData().getString(EXT_KEY_LINKAGE_DATA));
            messageData.setRetryCount(getInputData().getInt("retryCount", 0));
            onMessageReceived(messageData);
        } else if (i == 1) {
            onNewTokenReceived(messageData);
        } else {
            try {
                LogUploader.Request requestBuild = LogUploader.Request.build(getApplicationContext(), LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.UNKNOWN, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.SERVER_PROCESS, LogUploader.MessageCode.ErrorInfo.ACTION_IS_NOT_ON_MESSAGE_RECEIVED_NOR_ON_NEW_TOKEN_RECEIVED, getApplicationContext(), LogUploader.DUMMY_SE_ID));
                LogUploader logUploader = new LogUploader(new OnUploadFinishListenerImpl());
                this.mLogUploader = logUploader;
                logUploader.start();
                this.mLogUploader.request(requestBuild);
                LogMgr.log(8, "700 action : " + i);
            } catch (Exception e) {
                LogMgr.log(7, "998 " + e.getMessage());
                return ListenableWorker.Result.success();
            }
        }
        LogMgr.log(7, "999");
        return ListenableWorker.Result.success();
    }

    private void onMessageReceived(MessageData messageData) {
        LogMgr.log(8, "000");
        SessionHandler.getInstance().post(new MessageOperationRequest(getApplicationContext(), messageData));
        LogMgr.log(8, "999");
    }

    private void onNewTokenReceived(MessageData messageData) {
        LogMgr.log(8, "000");
        SessionHandler.getInstance().post(new OnNewTokenOperationRequest(getApplicationContext(), messageData));
        LogMgr.log(8, "999");
    }

    static final class MessageOperationRequest extends SessionHandler.SemClientSessionRequest {
        private static final String FIXED_VALUE_MESSAGE_TYPE = "START_TSM_SEQUENCE";

        MessageOperationRequest(Context context, MessageData messageData) {
            super(context, messageData, true);
        }

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
        public boolean onPrepareStarting() {
            LogMgr.log(7, "000");
            if (this.mMessageData == null) {
                LogMgr.log(1, "800 messageData is null or empty");
                requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.UNKNOWN, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.SERVER_PROCESS, LogUploader.MessageCode.ErrorInfo.BUNDLE_IS_EMPTY, this.mContext, LogUploader.DUMMY_SE_ID));
                LogMgr.log(7, "994");
                return false;
            }
            String address = this.mMessageData.getAddress();
            if (address == null || address.equals("")) {
                LogMgr.log(1, "801 messageData is null or empty");
                LogMgr.log(7, "995");
                return false;
            }
            if (!FIXED_VALUE_MESSAGE_TYPE.equals(this.mMessageData.getMessageType())) {
                LogMgr.log(1, "802 messageType is not START_TSM_SEQUENCE");
                LogMgr.log(7, "996");
                return false;
            }
            String linkageData = this.mMessageData.getLinkageData();
            if (linkageData == null || linkageData.equals("")) {
                LogMgr.log(1, "803 linkageData is null or empty");
                requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.UNKNOWN, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.SERVER_PROCESS, LogUploader.MessageCode.ErrorInfo.LINKAGE_DATA_IS_EMPTY, this.mContext, LogUploader.DUMMY_SE_ID));
                LogMgr.log(7, "997");
                return false;
            }
            this.mSemClient = SemClientInternal.getInstance();
            if (this.mSemClient == null) {
                LogMgr.log(1, "804 instance of SemClientInternal is null or empty");
                requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_GETINSTANCE, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.GET_SEM_CLIENT_PROCESS, LogUploader.MessageCode.ErrorInfo.SEM_CLIENT_IS_NULL, this.mContext, LogUploader.DUMMY_SE_ID));
                LogMgr.log(7, "998");
                return false;
            }
            LogMgr.log(7, "999");
            return true;
        }

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
        public void onPreparedAndCallConnect(SemClient.OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
            LogMgr.log(7, "000");
            this.mSemClient.connect(this.mContext, true, onConnectedListener);
            LogMgr.log(7, "999");
        }

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
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

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
        public void onConnectedAndStartTsmSequence(SemClientInternal.OnTsmSequenceListener onTsmSequenceListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
            LogMgr.log(7, "000");
            this.mSemClient.startTsmSequence(this.mMessageData.getLinkageData(), onTsmSequenceListener);
            LogMgr.log(7, "999");
        }

        private void requestLogUpload(String str, LogUploader.Request.LogInfoContent logInfoContent) {
            LogMgr.log(8, "000");
            try {
                this.mLogUploader.request(LogUploader.Request.build(this.mContext, str, logInfoContent));
                LogMgr.log(8, "999");
            } catch (Exception e) {
                LogMgr.log(8, "998 " + e.getMessage());
            }
        }
    }

    static final class OnNewTokenOperationRequest extends SessionHandler.SemClientSessionRequest {
        OnNewTokenOperationRequest(Context context, MessageData messageData) {
            super(context, messageData, false);
        }

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
        public boolean onPrepareStarting() {
            LogMgr.log(7, "000");
            this.mSemClient = SemClientInternal.getInstance();
            if (this.mSemClient == null) {
                LogMgr.log(1, "800 instance of SemClientInternal is null or empty");
                requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_GETINSTANCE, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ON_NEW_TOKEN_RECEIVED, LogUploader.MessageCode.Process.GET_SEM_CLIENT_PROCESS, LogUploader.MessageCode.ErrorInfo.SEM_CLIENT_IS_NULL, this.mContext, LogUploader.DUMMY_SE_ID));
                LogMgr.log(7, "998");
                return false;
            }
            LogMgr.log(7, "999");
            return true;
        }

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
        public void onPreparedAndCallConnect(SemClient.OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
            LogMgr.log(7, "000");
            this.mSemClient.connect(this.mContext, true, onConnectedListener);
            LogMgr.log(7, "999");
        }

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
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

        @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientLifecycleAdapter
        public void onConnectedAndStartTsmSequence(SemClientInternal.OnTsmSequenceListener onTsmSequenceListener) throws IllegalStateException, IllegalArgumentException {
            LogMgr.log(7, "000");
            LogMgr.log(7, "999");
        }

        private void requestLogUpload(String str, LogUploader.Request.LogInfoContent logInfoContent) {
            LogMgr.log(8, "000");
            try {
                this.mLogUploader.request(LogUploader.Request.build(this.mContext, str, logInfoContent));
                LogMgr.log(8, "999");
            } catch (Exception e) {
                LogMgr.log(8, "998 " + e.getMessage());
            }
        }
    }

    private static final class SessionHandler {
        private static final Object lock = new Object();
        private static volatile SessionHandler sInstance;
        private final Deque<SemClientSessionRequest> mQueue = new LinkedList();
        private final Handler mThreadHandler;

        interface SemClientLifecycleAdapter {
            void onConnectedAndStartTsmSequence(SemClientInternal.OnTsmSequenceListener onTsmSequenceListener) throws IllegalStateException, SemClientException, IllegalArgumentException;

            void onFinished();

            boolean onPrepareStarting();

            void onPreparedAndCallConnect(SemClient.OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException;
        }

        protected static abstract class SemClientSessionRequest implements SemClientLifecycleAdapter {
            final Context mContext;
            boolean mIsOnReceivedMessage;
            LogUploader mLogUploader;
            final MessageData mMessageData;
            SemClientInternal mSemClient;

            SemClientLifecycleAdapter getAdapter() {
                return this;
            }

            SemClientSessionRequest(Context context, MessageData messageData, boolean z) {
                this.mContext = context;
                this.mMessageData = messageData;
                this.mIsOnReceivedMessage = z;
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
            LogUploader mLogUploader;
            RegisterRetryWorkManager mRegisterRetryWorkManager;

            SemClientConnectionTask(SemClientSessionRequest semClientSessionRequest) {
                super(semClientSessionRequest);
                if (this.mRequest.mIsOnReceivedMessage) {
                    this.mRegisterRetryWorkManager = new RegisterRetryWorkManager(this.mRequest.mContext, this.mRequest.mMessageData);
                }
                this.mTimeManager = new TimeManager();
                if (this.mRequest.mIsOnReceivedMessage) {
                    this.mLogUploader = new LogUploader(new OnUploadFinishListenerImpl());
                } else {
                    this.mLogUploader = new LogUploaderForRegDevToken(new OnUploadFinishListenerImplForNewToken());
                }
                this.mLogUploader.start();
                this.mRequest.mLogUploader = this.mLogUploader;
                this.mIsLogUploadExecuted = new AtomicBoolean(false);
            }

            @Override // com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientAccessTask
            public void doSemClientAccess() {
                LogUploader.Message.ErrorType errorType;
                LogUploader.MessageCode.ErrorInfo errorInfo;
                LogUploader.MessageCode.SendTiming sendTiming;
                LogMgr.log(8, "000");
                SemClientLifecycleAdapter lifecycleAdapter = getLifecycleAdapter();
                if (!lifecycleAdapter.onPrepareStarting()) {
                    SessionHandler.this.doSemClientFinish(this.mRequest, this.mTimeManager, true);
                    LogMgr.log(8, "998");
                    return;
                }
                this.mTimeManager.startConnectTimer(new TimerTask() { // from class: com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientConnectionTask.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        LogUploader.MessageCode.SendTiming sendTiming2;
                        SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                        if (SemClientConnectionTask.this.mRequest.mIsOnReceivedMessage) {
                            SemClientConnectionTask.this.mRegisterRetryWorkManager.registerWorkManagerForRetry(false);
                        }
                        if (SemClientConnectionTask.this.mRequest.mIsOnReceivedMessage) {
                            sendTiming2 = LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED;
                        } else {
                            sendTiming2 = LogUploader.MessageCode.SendTiming.ON_NEW_TOKEN_RECEIVED;
                        }
                        SemClientConnectionTask.this.requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), sendTiming2, LogUploader.MessageCode.Process.CONNECT_PROCESS, LogUploader.MessageCode.ErrorInfo.TIMEOUT, SemClientConnectionTask.this.mRequest.mContext, LogUploader.DUMMY_SE_ID));
                    }
                });
                this.mTimeManager.startProcessTimer(new TimerTask() { // from class: com.felicanetworks.semc.fcm.CloudMessagingWorker.SessionHandler.SemClientConnectionTask.2
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        String seid = SemClientConnectionTask.this.getSeid();
                        SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                        SemClientConnectionTask.this.requestLogUpload(seid, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_STARTTSMSEQUENCE, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.BUSINESS_PROCESS, LogUploader.MessageCode.ErrorInfo.TIMEOUT, SemClientConnectionTask.this.mRequest.mContext, seid));
                    }
                });
                try {
                    if (this.mRequest.mIsOnReceivedMessage) {
                        lifecycleAdapter.onPreparedAndCallConnect(new OnConnectedListener());
                    } else {
                        lifecycleAdapter.onPreparedAndCallConnect(new OnConnectedListenerForNewToken());
                    }
                } catch (SemClientException | IllegalArgumentException | IllegalStateException e) {
                    SessionHandler.this.doSemClientFinish(this.mRequest, this.mTimeManager, false);
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
                    if (this.mRequest.mIsOnReceivedMessage) {
                        sendTiming = LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED;
                    } else {
                        sendTiming = LogUploader.MessageCode.SendTiming.ON_NEW_TOKEN_RECEIVED;
                    }
                    requestLogUpload(LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, errorType2, str, e.getMessage() == null ? "" : e.getMessage(), sendTiming, LogUploader.MessageCode.Process.CONNECT_PROCESS, errorInfo2, this.mRequest.mContext, LogUploader.DUMMY_SE_ID));
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
                        SemClientConnectionTask.this.getLifecycleAdapter().onConnectedAndStartTsmSequence(SemClientConnectionTask.this.new OnTsmSequenceListener());
                    } catch (SemClientException | IllegalArgumentException | IllegalStateException e) {
                        String seid = SemClientConnectionTask.this.getSeid();
                        SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
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
                        LogUploader.MessageCode.ErrorInfo errorInfo2 = errorInfo;
                        SemClientConnectionTask.this.requestLogUpload(seid, LogUploader.createLogInfoContent(LogUploader.Message.Api.SEM_STARTTSMSEQUENCE, errorType, string, e.getMessage() == null ? "" : e.getMessage(), LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.BUSINESS_PROCESS, errorInfo2, SemClientConnectionTask.this.mRequest.mContext, seid));
                    }
                    LogMgr.log(8, "999");
                }

                @Override // com.felicanetworks.semc.SemClient.OnConnectedListener
                public void onError(int i, String str, String str2) {
                    LogMgr.log(8, "000");
                    SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, false);
                    if (SemClientConnectionTask.this.mRequest.mIsOnReceivedMessage && needRetryError(i)) {
                        SemClientConnectionTask.this.mRegisterRetryWorkManager.registerWorkManagerForRetry(false);
                    }
                    synchronized (SemClientConnectionTask.this) {
                        if (SemClientConnectionTask.this.mIsLogUploadExecuted.get()) {
                            LogMgr.log(8, "998");
                            return;
                        }
                        if (SemClientConnectionTask.this.mLogUploader.isLogUpload(i)) {
                            SemClientConnectionTask.this.requestLogUpload(LogUploader.DUMMY_SE_ID, SemClientConnectionTask.this.mLogUploader.getLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, i, str2, LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.CONNECT_PROCESS, SemClientConnectionTask.this.mRequest.mContext, LogUploader.DUMMY_SE_ID));
                        }
                        LogMgr.log(8, "999");
                    }
                }

                boolean needRetryError(int i) {
                    LogMgr.log(8, "000");
                    boolean z = i == 101 || i == 103 || i == 202 || i == 300 || i == 400;
                    LogMgr.log(8, "999 ret=" + z);
                    return z;
                }
            }

            class OnTsmSequenceListener implements SemClientInternal.OnTsmSequenceListener {
                OnTsmSequenceListener() {
                }

                @Override // com.felicanetworks.semc.SemClient.OnTsmSequenceListener
                public void onFinished() {
                    LogMgr.log(8, "000");
                    SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                    LogMgr.log(8, "999");
                }

                @Override // com.felicanetworks.semc.SemClient.OnTsmSequenceListener
                public void onError(int i, String str, String str2) {
                    LogMgr.log(8, "000");
                    doErrorProcess(i, str2);
                    LogMgr.log(8, "999");
                }

                @Override // com.felicanetworks.semc.SemClientInternal.OnTsmSequenceListener
                public void onErrorAndDoWorkManagerRetry(int i, String str, String str2) {
                    LogMgr.log(8, "000");
                    SemClientConnectionTask.this.mRegisterRetryWorkManager.registerWorkManagerForRetry(true);
                    doErrorProcess(i, str2);
                    LogMgr.log(8, "999");
                }

                private void doErrorProcess(int i, String str) {
                    LogMgr.log(8, "000");
                    synchronized (SemClientConnectionTask.this) {
                        if (SemClientConnectionTask.this.mIsLogUploadExecuted.get()) {
                            LogMgr.log(8, "998");
                            return;
                        }
                        if (SemClientConnectionTask.this.mLogUploader.isLogUpload(i)) {
                            String seid = SemClientConnectionTask.this.getSeid();
                            SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                            LogUploader.Message.Api api = LogUploader.Message.Api.SEM_STARTTSMSEQUENCE;
                            LogUploader.Message.ErrorType errorType = LogUploader.Message.ErrorType.ON_ERROR;
                            String string = Integer.toString(i);
                            if (str == null) {
                                str = "";
                            }
                            SemClientConnectionTask.this.requestLogUpload(seid, LogUploader.createLogInfoContent(api, errorType, string, str, LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.BUSINESS_PROCESS, LogUploader.MessageCode.ErrorInfo.ISEM_CLIENT_ERROR_NOTIFICATION, SemClientConnectionTask.this.mRequest.mContext, seid));
                        } else {
                            SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                        }
                        LogMgr.log(8, "999");
                    }
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

            class OnConnectedListenerForNewToken implements SemClient.OnConnectedListener {
                OnConnectedListenerForNewToken() {
                }

                @Override // com.felicanetworks.semc.SemClient.OnConnectedListener
                public void onConnected() {
                    LogMgr.log(8, "000");
                    SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, true);
                    LogMgr.log(8, "999");
                }

                @Override // com.felicanetworks.semc.SemClient.OnConnectedListener
                public void onError(int i, String str, String str2) {
                    LogMgr.log(8, "000");
                    SessionHandler.this.doSemClientFinish(SemClientConnectionTask.this.mRequest, SemClientConnectionTask.this.mTimeManager, false);
                    synchronized (SemClientConnectionTask.this) {
                        if (SemClientConnectionTask.this.mIsLogUploadExecuted.get()) {
                            LogMgr.log(8, "998");
                            return;
                        }
                        if (SemClientConnectionTask.this.mLogUploader.isLogUpload(i)) {
                            SemClientConnectionTask.this.requestLogUpload(LogUploader.DUMMY_SE_ID, SemClientConnectionTask.this.mLogUploader.getLogInfoContent(LogUploader.Message.Api.SEM_CONNECT, i, str2, LogUploader.MessageCode.SendTiming.ON_NEW_TOKEN_RECEIVED, LogUploader.MessageCode.Process.CONNECT_PROCESS, SemClientConnectionTask.this.mRequest.mContext, LogUploader.DUMMY_SE_ID));
                        }
                        LogMgr.log(8, "999");
                    }
                }
            }

            private class OnUploadFinishListenerImplForNewToken implements LogUploader.OnUploadFinishListener {
                private OnUploadFinishListenerImplForNewToken() {
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
            private static final long PROCESS_TIMEOUT = 600000;
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
            private final String mAddress;
            private final Context mContext;
            private final String mLinkageData;
            private final String mMessageType;
            private int mRetryCntForRegisterWorkManager;
            private boolean mSetFlg;

            RegisterRetryWorkManager(Context context, MessageData messageData) {
                LogMgr.log(7, "000");
                this.mContext = context;
                this.mAddress = messageData.getAddress();
                this.mMessageType = messageData.getMessageType();
                this.mLinkageData = messageData.getLinkageData();
                this.mRetryCntForRegisterWorkManager = messageData.getRetryCount();
                this.mSetFlg = false;
                LogMgr.log(7, "999");
            }

            /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void registerWorkManagerForRetry(boolean z) {
                LogMgr.log(8, "000");
                if (z) {
                    LogMgr.log(8, "001 startTsmSequence was failed.");
                    try {
                        new JwsObject(this.mLinkageData).verifyExp();
                        if (!this.mSetFlg) {
                            LogMgr.log(8, "004 doOneTimeWorkRequest; mRetryCntForRegisterWorkManager=" + this.mRetryCntForRegisterWorkManager);
                            this.mSetFlg = true;
                            this.mRetryCntForRegisterWorkManager = this.mRetryCntForRegisterWorkManager + 1;
                            OneTimeWorkRequest oneTimeWorkRequestBuild = new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) CloudMessagingWorker.class).setInputData(new Data.Builder().putInt("action", 0).putString(CloudMessagingWorker.EXT_KEY_ADDRESS, this.mAddress).putString(CloudMessagingWorker.EXT_KEY_MESSAGE_TYPE, this.mMessageType).putString(CloudMessagingWorker.EXT_KEY_LINKAGE_DATA, this.mLinkageData).putInt("retryCount", this.mRetryCntForRegisterWorkManager).build()).setInitialDelay(15L, TimeUnit.MINUTES).build();
                            LogMgr.log(9, "005 OneTimeWorkRequest(CloudMessagingWorker) ID=" + oneTimeWorkRequestBuild.getId());
                            WorkManager.getInstance(this.mContext).enqueue(oneTimeWorkRequestBuild);
                        }
                    } catch (JwsException unused) {
                        LogMgr.log(8, "002 LinkageData Expiration time is over.");
                    }
                } else {
                    LogMgr.log(8, "003 connect was failed.");
                    if (this.mRetryCntForRegisterWorkManager < 3) {
                        if (!this.mSetFlg) {
                        }
                    }
                }
                LogMgr.log(8, "999");
            }
        }
    }

    private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
        private OnUploadFinishListenerImpl() {
        }

        @Override // com.felicanetworks.semc.sws.LogUploader.OnUploadFinishListener
        public void onFinished(String str, String str2) {
            LogMgr.log(8, "000 requestId=" + str + " result=" + str2);
            if (CloudMessagingWorker.this.mLogUploader != null) {
                CloudMessagingWorker.this.mLogUploader.shutdown();
                CloudMessagingWorker.this.mLogUploader = null;
            }
            LogMgr.log(8, "999");
        }
    }

    private static class LogUploaderForRegDevToken extends LogUploader {
        private final Integer[] RESTRICTED_LOG_UPLOAD_EC_LIST;

        public LogUploaderForRegDevToken(LogUploader.OnUploadFinishListener onUploadFinishListener) {
            super(onUploadFinishListener);
            this.RESTRICTED_LOG_UPLOAD_EC_LIST = new Integer[]{101};
            LogMgr.log(8, "000");
            LogMgr.log(8, "999");
        }

        @Override // com.felicanetworks.semc.sws.LogUploader
        public boolean isLogUpload(int i) {
            LogMgr.log(8, "000 errorCode:" + i);
            if (Arrays.asList(this.RESTRICTED_LOG_UPLOAD_EC_LIST).contains(Integer.valueOf(i))) {
                LogMgr.log(8, "700 An errorCode is restricted for log uploading. code=" + i);
                return false;
            }
            boolean zIsLogUpload = super.isLogUpload(i);
            LogMgr.log(8, "999 isLogUpload:" + zIsLogUpload);
            return zIsLogUpload;
        }
    }
}
