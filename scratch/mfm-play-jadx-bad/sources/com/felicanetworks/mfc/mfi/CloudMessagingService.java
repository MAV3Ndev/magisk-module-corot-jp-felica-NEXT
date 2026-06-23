package com.felicanetworks.mfc.mfi;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Pair;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.FelicaResultInfoType;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.mfi.CloudMessagingEventReceiver;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.ForegroundServiceSetupProvider;
import com.felicanetworks.mfc.mfi.IMfiFelica;
import com.felicanetworks.mfc.mfi.IServerOperationEventCallback;
import com.felicanetworks.mfc.mfi.MfiClientLogUploadConst;
import com.felicanetworks.mfc.mfi.fws.LogUploader;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.semc.SemClientNotifyEventInfo;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
public class CloudMessagingService extends Service {
    private static final String EXT_KEY_RETRY_REMAINING = "retry-remaining";
    private static final Integer[] LOG_UPLOAD_NEGATIVE_LIST = {206, 209, 207, 210, 226, -1, 211, 208, 229, 228};
    private int mJobCount;
    private LogUploader mLogUploader;
    private Handler mMainThreadHandler;
    private FelicaSessionHandler mSessionHandler;

    static /* synthetic */ int access$010(CloudMessagingService cloudMessagingService) {
        int i = cloudMessagingService.mJobCount;
        cloudMessagingService.mJobCount = i - 1;
        return i;
    }

    enum Action {
        FCM_ON_MESSAGE_RECEIVED("com.felicanetworks.mfc.mfi.action.FCM_ON_MESSAGE_RECEIVED"),
        FCM_ON_NEW_TOKEN_RECEIVED("com.felicanetworks.mfc.mfi.action.FCM_ON_NEW_TOKEN_RECEIVED"),
        UNKNOWN("");

        final String value;

        Action(String value) {
            this.value = value;
        }

        static Action find(String value) {
            for (Action action : values()) {
                if (TextUtils.equals(action.value, value)) {
                    return action;
                }
            }
            return UNKNOWN;
        }
    }

    @Override // android.app.Service
    public void onCreate() {
        LogMgr.log(6, "000");
        this.mMainThreadHandler = new Handler(Looper.myLooper());
        this.mSessionHandler = new FelicaSessionHandler();
        LogMgr.log(6, "999");
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogMgr.log(6, "000 startId=" + startId);
        this.mJobCount = this.mJobCount + 1;
        try {
            ForegroundServiceSetupProvider.requestForegroundService(this, ForegroundServiceSetupProvider.Type.CLOUD_MESSAGING_SERVICE);
        } catch (Throwable unused) {
            LogMgr.log(1, "800");
            onDetectedOutOfSessionError(startId, Error.Cause.OTHER, ObfuscatedMsgUtil.executionPoint());
        }
        if (intent == null) {
            LogMgr.log(6, "998 intent=null");
            onDetectedOutOfSessionError(startId, Error.Cause.OTHER, ObfuscatedMsgUtil.executionPoint());
            return 2;
        }
        String action = intent.getAction();
        Action actionFind = Action.find(action);
        LogMgr.log(6, "001 action=" + actionFind.name());
        int iOrdinal = actionFind.ordinal();
        if (iOrdinal == 0) {
            onMessageReceived(intent.getExtras(), startId);
        } else if (iOrdinal == 1) {
            onNewTokenReceived(intent.getExtras(), startId);
        } else {
            LogMgr.log(6, "700 intentAction : " + action);
            requestStopService(startId);
        }
        LogMgr.log(6, "999");
        return 2;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return null;
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(6, "000");
        FelicaSessionHandler felicaSessionHandler = this.mSessionHandler;
        if (felicaSessionHandler != null) {
            felicaSessionHandler.shutdown();
            this.mSessionHandler = null;
        }
        LogUploader logUploader = this.mLogUploader;
        if (logUploader != null) {
            logUploader.shutdown();
            this.mLogUploader = null;
        }
        super.onDestroy();
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestStopService(final int startId) {
        LogMgr.log(6, "000");
        this.mMainThreadHandler.post(new Runnable() { // from class: com.felicanetworks.mfc.mfi.CloudMessagingService.1
            @Override // java.lang.Runnable
            public void run() {
                LogMgr.log(6, "000 mJobCount=" + CloudMessagingService.this.mJobCount + ", startId=" + startId);
                CloudMessagingService.access$010(CloudMessagingService.this);
                if (CloudMessagingService.this.mJobCount == 0) {
                    CloudMessagingService.this.stopSelf();
                }
            }
        });
        LogMgr.log(6, "999");
    }

    private void onMessageReceived(Bundle messageData, final int startId) {
        LogMgr.log(6, "000");
        this.mSessionHandler.post(new MessageOperationRequest(getApplicationContext(), startId, messageData));
        LogMgr.log(6, "999");
    }

    private void onNewTokenReceived(Bundle messageData, final int startId) {
        LogMgr.log(6, "000");
        this.mSessionHandler.post(new OnNewTokenOperationRequest(getApplicationContext(), startId, messageData));
        LogMgr.log(6, "999");
    }

    private void onDetectedOutOfSessionError(int startId, Error.Cause cause, String msg) {
        LogMgr.log(6, "000");
        Error error = new Error(cause);
        error.errorMessage = msg;
        if (!requestLogUpload(startId, error)) {
            requestStopService(startId);
            LogMgr.log(6, "998 Log upload request was rejected.");
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestFinished(final int startId, final Error error) {
        LogMgr.log(6, "000");
        if (error == null) {
            requestStopService(startId);
            LogMgr.log(6, "997 error=null");
        } else {
            if (!requestLogUpload(startId, error)) {
                requestStopService(startId);
                LogMgr.log(6, "998 Log upload request was rejected.");
            }
            LogMgr.log(6, "999");
        }
    }

    private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
        private OnUploadFinishListenerImpl() {
        }

        @Override // com.felicanetworks.mfc.mfi.fws.LogUploader.OnUploadFinishListener
        public void onFinished(final int requestId, final String result) {
            LogMgr.log(6, "000 requestId=" + requestId);
            CloudMessagingService.this.requestStopService(requestId);
            LogMgr.log(6, "999");
        }
    }

    private boolean requestLogUpload(final int startId, final Error error) {
        LogMgr.log(6, "000 startId=" + startId);
        if (Error.ContentType.EXECUTE_SERVER_OPERATION_CB_ERROR.equalsType(error.errorContentType) && Arrays.asList(LOG_UPLOAD_NEGATIVE_LIST).contains(Integer.valueOf(error.errorType))) {
            LogMgr.log(6, "997 errorType=" + error.errorType);
            return false;
        }
        try {
            LogUploader.Request requestBuild = LogUploader.Request.build(getApplicationContext(), startId, error.seInfo, error.operationId, new LogUploader.Request.LogInfoContent(new String[]{error.operationId}, new String[]{error.messageId}, new String[]{error.getMessage()}));
            if (this.mLogUploader == null) {
                LogUploader logUploader = new LogUploader(new OnUploadFinishListenerImpl());
                this.mLogUploader = logUploader;
                logUploader.start();
            }
            this.mLogUploader.request(requestBuild);
            LogMgr.log(6, "999");
            return true;
        } catch (Exception e) {
            LogMgr.log(6, "998 " + e.getMessage());
            return false;
        }
    }

    final class MessageOperationRequest extends FelicaSessionHandler.FelicaSessionRequest {
        private static final String EXT_KEY_MESSAGE_ID = "messageId";
        private static final String EXT_KEY_OPERATION_ID = "operationId";
        private final Bundle mMessageData;
        private String mMessageId;
        private String mOperationId;

        MessageOperationRequest(Context context, int startId, Bundle messageData) {
            super(context, startId);
            this.mMessageData = messageData;
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaSessionRequest, com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public boolean onPrepareStarting(FelicaSessionHandler.ResultContext resultContext) {
            if (!super.onPrepareStarting(resultContext)) {
                LogMgr.log(2, "700 : request is aborted.");
                return false;
            }
            Bundle bundle = this.mMessageData;
            if (bundle == null || bundle.isEmpty()) {
                LogMgr.log(2, "701 : mMessageData is invalid.");
                resultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                return false;
            }
            this.mOperationId = this.mMessageData.getString("operationId");
            this.mMessageId = this.mMessageData.getString(EXT_KEY_MESSAGE_ID);
            resultContext.getResultBuilder().setOperationId(this.mOperationId);
            resultContext.getResultBuilder().setMessageId(this.mMessageId);
            String str = this.mOperationId;
            if (str == null || str.isEmpty()) {
                LogMgr.log(2, "702 : mOperationId is invalid. :" + this.mOperationId);
                resultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                return false;
            }
            String str2 = this.mMessageId;
            if (str2 != null && !str2.isEmpty()) {
                return true;
            }
            LogMgr.log(2, "703 : mMessageId is invalid. :" + this.mMessageId);
            resultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
            return false;
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public void onFelicaActivated(FelicaSessionHandler.ResultContext resultContext) {
            LogMgr.log(6, "000");
            MfiServiceImpl mfiServiceImpl = FelicaAdapter.getInstance().getMfiServiceImpl();
            if (mfiServiceImpl == null) {
                LogMgr.log(1, "800");
                resultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                return;
            }
            try {
                if (resultContext.getResultChecker().checkGetSeInfoResult(mfiServiceImpl.getSeInfomation())) {
                    LogMgr.log(6, "001 getSeInfomation success.");
                    ServerOperationCompletionLatch serverOperationCompletionLatch = new ServerOperationCompletionLatch();
                    try {
                        FelicaResultInfo felicaResultInfoExecuteServerOperation = mfiServiceImpl.executeServerOperation(this.mOperationId, this.mMessageId, serverOperationCompletionLatch);
                        LogMgr.log(6, "002 executeServerOperation called.");
                        if (resultContext.getResultChecker().checkServerOperationResult(felicaResultInfoExecuteServerOperation)) {
                            LogMgr.log(6, "003");
                            try {
                                serverOperationCompletionLatch.await();
                                if (serverOperationCompletionLatch.getErrorResult() == null) {
                                    LogMgr.log(6, "004 ServerOperation successful.");
                                    resultContext.getResultCollector().onDetectSuccessful();
                                } else {
                                    LogMgr.log(6, "005 ServerOperation failed.");
                                    Pair<Integer, String> errorResult = serverOperationCompletionLatch.getErrorResult();
                                    resultContext.getResultCollector().onDetectCallbackError(Error.Cause.SERVER_OPERATION, Error.ContentType.EXECUTE_SERVER_OPERATION_CB_ERROR, ((Integer) errorResult.first).intValue(), 0, (String) errorResult.second);
                                }
                            } catch (InterruptedException e) {
                                resultContext.getResultCollector().onDetectUnknownError(e);
                                LogMgr.log(2, "801");
                                return;
                            }
                        } else {
                            if (felicaResultInfoExecuteServerOperation == null) {
                                LogMgr.log(2, "800 result=null");
                            } else {
                                LogMgr.log(6, "998 exType=" + felicaResultInfoExecuteServerOperation.getExceptionType() + " id:" + felicaResultInfoExecuteServerOperation.getId() + " type:" + felicaResultInfoExecuteServerOperation.getType() + " otherAppInfo:" + felicaResultInfoExecuteServerOperation.getOtherAppInfo() + " flg1:" + felicaResultInfoExecuteServerOperation.getStatusFlag1() + " flg2:" + felicaResultInfoExecuteServerOperation.getStatusFlag2());
                            }
                            serverOperationCompletionLatch.countDown();
                        }
                        LogMgr.log(6, "999");
                        return;
                    } catch (Exception e2) {
                        LogMgr.log(2, "703");
                        serverOperationCompletionLatch.countDown();
                        resultContext.getResultCollector().onDetectError(Error.Cause.SERVER_OPERATION, e2);
                        return;
                    }
                }
                LogMgr.log(2, "702");
            } catch (Exception e3) {
                LogMgr.log(2, "701");
                resultContext.getResultCollector().onDetectError(Error.Cause.GET_SE_INFORMATION, e3);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public void onFelicaActivationFailed(FelicaSessionHandler.ResultContext resultContext) {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaSessionRequest, com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public void onFelicaDisconnected(FelicaSessionHandler.ResultContext resultContext) {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public void onFinished(FelicaSessionHandler.FelicaSessionResult result) {
            LogMgr.log(6, "000");
            if (CloudMessagingService.this.doRetry(Action.FCM_ON_MESSAGE_RECEIVED, this.mMessageData, result.getRetryParameters())) {
                CloudMessagingService.this.onRequestFinished(this.mStartId, null);
            } else {
                CloudMessagingService.this.onRequestFinished(this.mStartId, result.getError());
            }
            LogMgr.log(6, "999");
        }

        private final class ServerOperationCompletionLatch implements ExecuteServerOperationEventCallback {
            private final CountDownLatch mCountDownLatch;
            private Pair<Integer, String> mErrorResult;
            private boolean mRetryRequired;

            private ServerOperationCompletionLatch() {
                this.mCountDownLatch = new CountDownLatch(1);
            }

            @Override // com.felicanetworks.mfc.mfi.ExecuteServerOperationEventCallback
            public void onSuccess() {
                LogMgr.log(6, "000 onSuccess");
                countDown();
            }

            @Override // com.felicanetworks.mfc.mfi.ExecuteServerOperationEventCallback, com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
            public void onError(int errType, String msg) {
                LogMgr.log(6, "000 onError errType=" + errType + " msg=" + msg);
                setError(errType, msg);
                countDown();
            }

            @Override // com.felicanetworks.mfc.mfi.ExecuteServerOperationEventCallback
            public void onRetryRequired(int errType, String msg) {
                LogMgr.log(6, "000 onRetryRequired errType=" + errType + " msg=" + msg);
                setError(errType, msg);
                setRetryRequired();
                countDown();
            }

            synchronized void setError(int errType, String msg) {
                this.mErrorResult = new Pair<>(Integer.valueOf(errType), msg);
            }

            synchronized Pair<Integer, String> getErrorResult() {
                return this.mErrorResult;
            }

            synchronized void setRetryRequired() {
                this.mRetryRequired = true;
            }

            synchronized boolean isRetryRequired() {
                return this.mRetryRequired;
            }

            void countDown() {
                LogMgr.log(6, "000 countDown");
                this.mCountDownLatch.countDown();
            }

            void await() throws InterruptedException {
                LogMgr.log(6, "000 await");
                this.mCountDownLatch.await();
            }
        }
    }

    final class OnNewTokenOperationRequest extends FelicaSessionHandler.FelicaSessionRequest {
        private final Bundle mMessageData;

        OnNewTokenOperationRequest(Context context, int startId, Bundle messageData) {
            super(context, startId);
            this.mMessageData = messageData;
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public void onFelicaActivated(FelicaSessionHandler.ResultContext resultContext) {
            LogMgr.log(6, "000");
            MfiServiceImpl mfiServiceImpl = FelicaAdapter.getInstance().getMfiServiceImpl();
            if (mfiServiceImpl == null) {
                LogMgr.log(1, "800");
                resultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                return;
            }
            try {
                if (resultContext.getResultChecker().checkGetSeInfoResult(mfiServiceImpl.getSeInfomation())) {
                    LogMgr.log(6, "001 getSeInfomation success.");
                    ServerOperationCompletionLatch serverOperationCompletionLatch = new ServerOperationCompletionLatch();
                    try {
                        FelicaResultInfo felicaResultInfoProvisionServerOperation = mfiServiceImpl.provisionServerOperation(serverOperationCompletionLatch);
                        LogMgr.log(6, "001 provisionServerOperation called.");
                        if (resultContext.getResultChecker().checkProvisionServerOperationResult(felicaResultInfoProvisionServerOperation)) {
                            LogMgr.log(6, "002");
                            try {
                                serverOperationCompletionLatch.await();
                                if (serverOperationCompletionLatch.getErrorResult() == null) {
                                    LogMgr.log(6, "003 provisionServerOperation successful.");
                                    resultContext.getResultCollector().onDetectSuccessful();
                                } else {
                                    LogMgr.log(6, "004 provisionServerOperation failed.");
                                    Pair<Integer, String> errorResult = serverOperationCompletionLatch.getErrorResult();
                                    resultContext.getResultCollector().onDetectCallbackError(Error.Cause.PROVISION_SERVER_OPERATION, Error.ContentType.EXECUTE_SERVER_OPERATION_CB_ERROR, ((Integer) errorResult.first).intValue(), 0, (String) errorResult.second);
                                    FlavorConst.ServerOperationRetryParameters serverOperationRetryParametersSelectByMfiClientCallback = RetryingStrategy.selectByMfiClientCallback(((Integer) errorResult.first).intValue());
                                    if (serverOperationRetryParametersSelectByMfiClientCallback != null) {
                                        resultContext.getResultCollector().onDetectRetryRequest(serverOperationRetryParametersSelectByMfiClientCallback);
                                    }
                                }
                            } catch (InterruptedException e) {
                                resultContext.getResultCollector().onDetectUnknownError(e);
                                LogMgr.log(2, "801");
                                return;
                            }
                        } else {
                            if (felicaResultInfoProvisionServerOperation == null) {
                                LogMgr.log(2, "800 result=null");
                            } else {
                                LogMgr.log(6, "998 exType=" + felicaResultInfoProvisionServerOperation.getExceptionType() + " id:" + felicaResultInfoProvisionServerOperation.getId() + " type:" + felicaResultInfoProvisionServerOperation.getType() + " otherAppInfo:" + felicaResultInfoProvisionServerOperation.getOtherAppInfo() + " flg1:" + felicaResultInfoProvisionServerOperation.getStatusFlag1() + " flg2:" + felicaResultInfoProvisionServerOperation.getStatusFlag2());
                            }
                            serverOperationCompletionLatch.countDown();
                        }
                        LogMgr.log(6, "999");
                        return;
                    } catch (Exception e2) {
                        LogMgr.log(2, "702");
                        serverOperationCompletionLatch.countDown();
                        resultContext.getResultCollector().onDetectError(Error.Cause.PROVISION_SERVER_OPERATION, e2);
                        return;
                    }
                }
                LogMgr.log(2, "701");
            } catch (Exception e3) {
                LogMgr.log(2, "700");
                resultContext.getResultCollector().onDetectError(Error.Cause.GET_SE_INFORMATION, e3);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public void onFelicaActivationFailed(FelicaSessionHandler.ResultContext resultContext) {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
        public void onFinished(FelicaSessionHandler.FelicaSessionResult result) {
            LogMgr.log(6, "000");
            if (CloudMessagingService.this.doRetry(Action.FCM_ON_NEW_TOKEN_RECEIVED, this.mMessageData, result.getRetryParameters())) {
                CloudMessagingService.this.onRequestFinished(this.mStartId, null);
            } else {
                CloudMessagingService.this.onRequestFinished(this.mStartId, result.getError());
            }
            LogMgr.log(6, "999");
        }

        private final class ServerOperationCompletionLatch extends IServerOperationEventCallback.Stub {
            private final CountDownLatch mCountDownLatch;
            private Pair<Integer, String> mErrorResult;

            private ServerOperationCompletionLatch() {
                this.mCountDownLatch = new CountDownLatch(1);
            }

            @Override // com.felicanetworks.mfc.mfi.IServerOperationEventCallback
            public void onSuccess() {
                LogMgr.log(6, "000 onSuccess");
                countDown();
            }

            @Override // com.felicanetworks.mfc.mfi.IServerOperationEventCallback
            public void onError(int errType, String msg) {
                LogMgr.log(6, "000 onError errType=" + errType + " msg=" + msg);
                setError(errType, msg);
                countDown();
            }

            synchronized void setError(int errType, String msg) {
                this.mErrorResult = new Pair<>(Integer.valueOf(errType), msg);
            }

            synchronized Pair<Integer, String> getErrorResult() {
                return this.mErrorResult;
            }

            void countDown() {
                LogMgr.log(6, "000 countDown");
                this.mCountDownLatch.countDown();
            }

            void await() throws InterruptedException {
                LogMgr.log(6, "000 await");
                this.mCountDownLatch.await();
            }
        }
    }

    private static final class RetryingStrategy {
        private RetryingStrategy() {
        }

        static FlavorConst.ServerOperationRetryParameters selectByMfiClientCallback(int errType) {
            if (errType == 8) {
                return FlavorConst.ServerOperationRetryParameters.TYPE_NFC_RW_USED;
            }
            if (errType != 205) {
                return null;
            }
            return FlavorConst.ServerOperationRetryParameters.TYPE_HTTP_ERROR;
        }

        static FlavorConst.ServerOperationRetryParameters selectByFelicaException(int getId, int getType, String msg) {
            if (getId != 1) {
                if (getId == 2 && getType == 39) {
                    return FlavorConst.ServerOperationRetryParameters.TYPE_USED_BY_OTHER_APP;
                }
                return null;
            }
            if (getType == 8 && TextUtils.equals(FelicaException.NFC_RW_USED_MESSAGE, msg)) {
                return FlavorConst.ServerOperationRetryParameters.TYPE_NFC_RW_USED;
            }
            return null;
        }

        static FlavorConst.ServerOperationRetryParameters selectByActivationErrorCallback(int type) {
            if (type == 3) {
                return FlavorConst.ServerOperationRetryParameters.TYPE_HTTP_ERROR;
            }
            if (type != 7) {
                return null;
            }
            return FlavorConst.ServerOperationRetryParameters.TYPE_USED_BY_OTHER_APP;
        }
    }

    static final class Error {
        final String api;
        final Cause cause;
        String errorContentType;
        int errorId;
        String errorMessage;
        int errorType;
        String messageId;
        String operationId;
        SeInfo seInfo;
        String walletAppId;

        public enum Cause {
            BIND_SERVICE(MfiClientLogUploadConst.Api.BIND_SERVICE.name),
            UNBIND_SERVICE(MfiClientLogUploadConst.Api.UNBIND_SERVICE.name),
            ACTIVATE_FELICA(MfiClientLogUploadConst.Api.ACTIVATE_FELICA.name),
            INACTIVATE_FELICA(MfiClientLogUploadConst.Api.INACTIVATE_FELICA.name),
            GET_SE_INFORMATION(MfiClientLogUploadConst.Api.GET_SE_INFORMATION.name),
            SERVER_OPERATION(MfiClientLogUploadConst.Api.SERVER_OPERATION.name),
            PROVISION_SERVER_OPERATION(MfiClientLogUploadConst.Api.PROVISION_SERVER_OPERATION.name),
            OTHER(MfiClientLogUploadConst.Api.UNKNOWN.name);

            final String api;

            Cause(String api) {
                this.api = api;
            }
        }

        public enum ContentType {
            ILLEGAL_ARGUMENT_EXCEPTION("IllegalArgumentException"),
            NUMBER_FORMAT_EXCEPTION("NumberFormatException"),
            MFI_CLIENT_EXCEPTION("MfiClientException"),
            FELICA_EXCEPTION("FelicaException"),
            EXCEPTION("Exception"),
            EXECUTE_SERVER_OPERATION_CB_ERROR(SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR),
            FELICA_EVENT_LISTENER_ERROR("errorOccurred"),
            UNKNOWN(null);

            final String type;

            ContentType(String type) {
                this.type = type;
            }

            boolean equalsType(String type) {
                return TextUtils.equals(this.type, type);
            }
        }

        Error(Cause cause) {
            this.cause = cause;
            this.api = cause.api;
        }

        public String getMessage() {
            if (this.errorContentType == null) {
                return String.format(Locale.US, "%s: (MSG=%s)", this.api, this.errorMessage);
            }
            int i = this.errorId;
            if (i == 0 && this.errorType == 0) {
                return String.format(Locale.US, "%s: %s(MSG=%s)", this.api, this.errorContentType, this.errorMessage);
            }
            if (i != 0 && this.errorType != 0) {
                return String.format(Locale.US, "%s: %s(ID=%d, TYPE=%d, MSG=%s)", this.api, this.errorContentType, Integer.valueOf(this.errorId), Integer.valueOf(this.errorType), this.errorMessage);
            }
            if (i != 0) {
                return String.format(Locale.US, "%s: %s(ID=%d, MSG=%s)", this.api, this.errorContentType, Integer.valueOf(this.errorId), this.errorMessage);
            }
            return String.format(Locale.US, "%s: %s(TYPE=%d, MSG=%s)", this.api, this.errorContentType, Integer.valueOf(this.errorType), this.errorMessage);
        }
    }

    private static final class FelicaSessionHandler {
        private static final long BIND_TIME_OUT = 10000;
        private FelicaServiceConnection mFelicaServiceConnection;
        private boolean mIsFelicaActivating;
        private final Deque<FelicaSessionRequest> mQueue = new LinkedList();
        private ResultContext mSessionResultContext;
        private final Handler mThreadHandler;

        interface FelicaLifecycleAdapter {
            void onFelicaActivated(ResultContext resultContext);

            void onFelicaActivationFailed(ResultContext resultContext);

            void onFelicaConnectionError(ResultContext resultContext);

            void onFelicaDisconnected(ResultContext resultContext);

            void onFelicaInactivated(ResultContext resultContext);

            void onFinished(FelicaSessionResult result);

            boolean onPrepareStarting(ResultContext resultContext);
        }

        protected static abstract class FelicaSessionRequest implements FelicaLifecycleAdapter {
            final Context mContext;
            long mDelayed;
            boolean mIsAborted;
            final int mStartId;

            FelicaLifecycleAdapter getAdapter() {
                return this;
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
            public void onFelicaConnectionError(ResultContext resultContext) {
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
            public void onFelicaDisconnected(ResultContext resultContext) {
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
            public void onFelicaInactivated(ResultContext resultContext) {
            }

            FelicaSessionRequest(Context context, int startId) {
                this.mContext = context;
                this.mStartId = startId;
            }

            void abort() {
                this.mIsAborted = true;
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaLifecycleAdapter
            public boolean onPrepareStarting(ResultContext resultContext) {
                return !this.mIsAborted;
            }
        }

        static class FelicaSessionResult {
            final Error mError;
            final FlavorConst.ServerOperationRetryParameters mRetryParameters;

            FelicaSessionResult(Error error, FlavorConst.ServerOperationRetryParameters retryParameters) {
                this.mError = error;
                this.mRetryParameters = retryParameters;
            }

            Error getError() {
                return this.mError;
            }

            FlavorConst.ServerOperationRetryParameters getRetryParameters() {
                return this.mRetryParameters;
            }

            static class Builder {
                Error mErrorData;
                boolean mIsSuccess;
                String mMessageId;
                String mOperationId;
                FlavorConst.ServerOperationRetryParameters mRetryParameters;
                SeInfo mSeInfo;
                String mWalletAppId;

                Builder() {
                }

                FelicaSessionResult build() {
                    Error error = this.mErrorData;
                    if (error != null) {
                        error.operationId = this.mOperationId;
                        this.mErrorData.messageId = this.mMessageId;
                        this.mErrorData.walletAppId = this.mWalletAppId;
                        this.mErrorData.seInfo = this.mSeInfo;
                    }
                    return new FelicaSessionResult(this.mErrorData, this.mRetryParameters);
                }

                void setSeInfo(SeInfo seInfo) {
                    this.mSeInfo = seInfo;
                }

                void setOperationId(String operationId) {
                    this.mOperationId = operationId;
                }

                void setMessageId(String messageId) {
                    this.mMessageId = messageId;
                }

                void setWalletAppId(String walletAppId) {
                    this.mWalletAppId = walletAppId;
                }

                void setRetryParameters(FlavorConst.ServerOperationRetryParameters retryParameters) {
                    this.mRetryParameters = retryParameters;
                }

                void createSuccessContent() {
                    this.mIsSuccess = true;
                }

                void createUnknownErrorContent(String msg) {
                    LogMgr.log(6, "000");
                    createUnknownErrorContent(Error.Cause.OTHER, msg);
                    LogMgr.log(6, "999");
                }

                void createUnknownErrorContent(Error.Cause cause, String msg) {
                    LogMgr.log(6, "000");
                    if (this.mErrorData != null) {
                        LogMgr.log(2, "700 The error has already occurred.");
                        return;
                    }
                    if (this.mIsSuccess) {
                        LogMgr.log(2, "701 already successful.");
                        return;
                    }
                    Error error = new Error(cause);
                    this.mErrorData = error;
                    error.errorMessage = msg;
                    LogMgr.log(6, "999");
                }

                void createUnknownErrorContent(Exception e) {
                    LogMgr.log(6, "000");
                    createUnknownErrorContent(e.getClass().getSimpleName(), e.getMessage());
                    LogMgr.log(6, "999");
                }

                void createUnknownErrorContent(String contentType, String msg) {
                    LogMgr.log(6, "000");
                    if (this.mErrorData != null) {
                        LogMgr.log(2, "700 The error has already occurred.");
                        return;
                    }
                    if (this.mIsSuccess) {
                        LogMgr.log(2, "701 already successful.");
                        return;
                    }
                    Error error = new Error(Error.Cause.OTHER);
                    this.mErrorData = error;
                    error.errorContentType = contentType;
                    this.mErrorData.errorMessage = msg;
                    LogMgr.log(6, "999");
                }

                void createErrorContent(Error.Cause cause, Exception e) {
                    LogMgr.log(6, "000");
                    if (this.mErrorData != null) {
                        LogMgr.log(2, "700 The error has already occurred.");
                        return;
                    }
                    if (this.mIsSuccess) {
                        LogMgr.log(2, "701 already successful.");
                        return;
                    }
                    Error error = new Error(cause);
                    Class<?> cls = e.getClass();
                    error.errorContentType = cls.getSimpleName();
                    error.errorMessage = e.getMessage();
                    if (FelicaException.class.isAssignableFrom(cls)) {
                        FelicaException felicaException = (FelicaException) e;
                        error.errorId = felicaException.getID();
                        error.errorType = felicaException.getType();
                    }
                    LogMgr.log(6, "999");
                    this.mErrorData = error;
                }

                void createErrorContent(Error.Cause cause, Error.ContentType contentType, int errorType, int errorId, String message) {
                    LogMgr.log(6, "000");
                    if (this.mErrorData != null) {
                        LogMgr.log(2, "700 The error has already occurred.");
                        return;
                    }
                    if (this.mIsSuccess) {
                        LogMgr.log(2, "701 already successful.");
                        return;
                    }
                    Error error = new Error(cause);
                    error.errorContentType = contentType.type;
                    error.errorMessage = message;
                    error.errorType = errorType;
                    error.errorId = errorId;
                    this.mErrorData = error;
                    LogMgr.log(6, "999");
                }
            }
        }

        static final class ResultContext {
            final FelicaSessionResult.Builder mResultBuilder = new FelicaSessionResult.Builder();
            final ResultChecker mResultChecker = new ResultChecker();
            final ResultCollector mResultCollector = new ResultCollector();

            ResultContext() {
            }

            FelicaSessionResult.Builder getResultBuilder() {
                return this.mResultBuilder;
            }

            FelicaSessionResult getResult() {
                return this.mResultBuilder.build();
            }

            ResultChecker getResultChecker() {
                return this.mResultChecker;
            }

            ResultCollector getResultCollector() {
                return this.mResultCollector;
            }

            final class ResultChecker {
                ResultChecker() {
                }

                boolean checkActivateFelicaResult(FelicaResultInfo resultInfo) {
                    FlavorConst.ServerOperationRetryParameters serverOperationRetryParametersSelectByFelicaException;
                    if (checkMfcResult(resultInfo, Error.Cause.ACTIVATE_FELICA)) {
                        return true;
                    }
                    if (resultInfo == null || (serverOperationRetryParametersSelectByFelicaException = RetryingStrategy.selectByFelicaException(resultInfo.getId(), resultInfo.getType(), resultInfo.getMessage())) == null) {
                        return false;
                    }
                    ResultContext.this.mResultCollector.onDetectRetryRequest(serverOperationRetryParametersSelectByFelicaException);
                    return false;
                }

                boolean checkInactivateFelicaResult(FelicaResultInfo resultInfo) {
                    return checkMfcResult(resultInfo, Error.Cause.INACTIVATE_FELICA);
                }

                boolean checkServerOperationResult(FelicaResultInfo resultInfo) {
                    return checkMfcResult(resultInfo, Error.Cause.SERVER_OPERATION);
                }

                boolean checkProvisionServerOperationResult(FelicaResultInfo resultInfo) {
                    return checkMfcResult(resultInfo, Error.Cause.PROVISION_SERVER_OPERATION);
                }

                boolean checkGetSeInfoResult(FelicaResultInfo resultInfo) {
                    SeInfo seInfo;
                    FlavorConst.ServerOperationRetryParameters serverOperationRetryParametersSelectByFelicaException;
                    if (checkMfcResult(resultInfo, Error.Cause.GET_SE_INFORMATION)) {
                        Object value = ((FelicaResultInfoType) resultInfo).getValue();
                        if (value instanceof SeInfo) {
                            seInfo = (SeInfo) value;
                        } else {
                            LogMgr.log(2, "700 SeInfo acquisition failed.");
                            ResultContext.this.mResultCollector.onDetectUnknownError(Error.Cause.GET_SE_INFORMATION, ObfuscatedMsgUtil.executionPoint());
                            seInfo = null;
                        }
                    } else {
                        seInfo = null;
                    }
                    if (seInfo != null) {
                        ResultContext.this.mResultBuilder.setSeInfo(seInfo);
                        return true;
                    }
                    if (resultInfo == null || (serverOperationRetryParametersSelectByFelicaException = RetryingStrategy.selectByFelicaException(resultInfo.getId(), resultInfo.getType(), resultInfo.getMessage())) == null) {
                        return false;
                    }
                    ResultContext.this.mResultCollector.onDetectRetryRequest(serverOperationRetryParametersSelectByFelicaException);
                    return false;
                }

                boolean checkMfcResult(FelicaResultInfo result, Error.Cause causeIfError) {
                    Error.ContentType contentType;
                    LogMgr.log(7, "000");
                    if (result == null) {
                        LogMgr.log(2, "704 " + causeIfError.api + " Result is null!");
                        ResultContext.this.mResultCollector.onDetectUnknownError(causeIfError, ObfuscatedMsgUtil.executionPoint());
                        return false;
                    }
                    LogMgr.log(7, "002 api=" + causeIfError.api + ", getExceptionType=" + result.getExceptionType());
                    int exceptionType = result.getExceptionType();
                    if (exceptionType == 0) {
                        return true;
                    }
                    if (exceptionType == 1) {
                        LogMgr.log(2, "701 FelicaException");
                        int iOrdinal = causeIfError.ordinal();
                        if (iOrdinal == 4 || iOrdinal == 5 || iOrdinal == 6) {
                            contentType = Error.ContentType.MFI_CLIENT_EXCEPTION;
                        } else {
                            contentType = Error.ContentType.FELICA_EXCEPTION;
                        }
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoError(causeIfError, result, contentType);
                    } else if (exceptionType == 32) {
                        LogMgr.log(2, "702 IllegalArgumentException " + result.getMessage());
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoErrorMessage(causeIfError, result.getMessage(), Error.ContentType.ILLEGAL_ARGUMENT_EXCEPTION);
                    } else if (exceptionType == 34) {
                        LogMgr.log(2, "704 NumberFormatException" + result.getMessage());
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoErrorMessage(causeIfError, result.getMessage(), Error.ContentType.NUMBER_FORMAT_EXCEPTION);
                    } else {
                        LogMgr.log(2, "703 Illegal ExceptionType");
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoErrorMessage(causeIfError, result.getMessage(), Error.ContentType.EXCEPTION);
                    }
                    LogMgr.log(7, "999");
                    return false;
                }
            }

            final class ResultCollector {
                ResultCollector() {
                }

                void onDetectSuccessful() {
                    ResultContext.this.mResultBuilder.createSuccessContent();
                }

                void onDetectRetryRequest(FlavorConst.ServerOperationRetryParameters retryParameters) {
                    ResultContext.this.mResultBuilder.setRetryParameters(retryParameters);
                }

                void onDetectError(Error.Cause cause, Exception e) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, e);
                }

                void onDetectUnknownError(String msg) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(msg);
                }

                void onDetectUnknownError(Error.Cause cause, String msg) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(cause, msg);
                }

                void onDetectUnknownError(Exception e) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(e);
                }

                void onDetectUnknownError(String contentType, String msg) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(contentType, msg);
                }

                void onDetectFelicaResultInfoError(Error.Cause cause, FelicaResultInfo resultInfo, Error.ContentType contentType) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, contentType, resultInfo.getType(), resultInfo.getId(), resultInfo.getMessage());
                }

                void onDetectFelicaResultInfoErrorMessage(Error.Cause cause, String message, Error.ContentType contentType) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, contentType, 0, 0, message);
                }

                void onDetectCallbackError(Error.Cause cause, Error.ContentType contentType, int errorType, int errorId, String message) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, contentType, errorType, errorId, message);
                }
            }
        }

        FelicaSessionHandler() {
            HandlerThread handlerThread = new HandlerThread("felica-session-handler-thread", 0);
            handlerThread.start();
            this.mThreadHandler = new Handler(handlerThread.getLooper());
        }

        void post(FelicaSessionRequest request) {
            if (request == null) {
                LogMgr.log(2, "700 Request is null.");
                return;
            }
            try {
                synchronized (this.mQueue) {
                    if (!this.mQueue.contains(request)) {
                        this.mQueue.add(request);
                    }
                    if (this.mQueue.getFirst() == request) {
                        this.mSessionResultContext = new ResultContext();
                        doFelicaServiceConnection(request);
                    }
                }
            } catch (ClassCastException unused) {
                LogMgr.log(2, "700 Argument is not FelicaSessionRequest.");
            } catch (Exception unused2) {
                LogMgr.log(2, "701 Request execution failed.");
            }
        }

        void onSessionFinished(FelicaSessionRequest request) {
            if (request == null) {
                LogMgr.log(2, "700 Request is null.");
                return;
            }
            synchronized (this.mQueue) {
                try {
                    try {
                        this.mQueue.remove(request);
                    } catch (ClassCastException unused) {
                        LogMgr.log(2, "701 Argument is not FelicaSessionRequest.");
                    }
                } catch (Exception unused2) {
                    LogMgr.log(2, "702 Failed to remove from queue.");
                }
                this.mSessionResultContext = null;
                runNextRequest();
            }
        }

        void runNextRequest() {
            if (this.mQueue.isEmpty()) {
                return;
            }
            try {
                post(this.mQueue.getFirst());
            } catch (NoSuchElementException unused) {
                LogMgr.log(2, "700 runNextRequest is failed.");
            }
        }

        void shutdown() {
            LogMgr.log(6, "000");
            this.mThreadHandler.getLooper().quitSafely();
            LogMgr.log(6, "999");
        }

        void doFelicaServiceConnection(FelicaSessionRequest request) {
            LogMgr.log(6, "000");
            if (request.mDelayed == 0) {
                this.mThreadHandler.post(new FelicaServiceConnectionTask(request, this.mSessionResultContext));
            } else {
                this.mThreadHandler.postDelayed(new FelicaServiceConnectionTask(request, this.mSessionResultContext), request.mDelayed);
            }
            LogMgr.log(6, "999");
        }

        void onFelicaServiceConnected(FelicaSessionRequest request, IBinder service) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaServiceConnectedTask(request, this.mSessionResultContext, service));
            LogMgr.log(6, "999");
        }

        void onFelicaActivated(FelicaSessionRequest request, IMfiFelica felica) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaActivatedTask(request, this.mSessionResultContext, felica));
            LogMgr.log(6, "999");
        }

        void onFelicaActivationFailed(FelicaSessionRequest request) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaActivationFailedTask(request, this.mSessionResultContext));
            LogMgr.log(6, "999");
        }

        void onFelicaServiceConnectionError(FelicaSessionRequest request) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaServiceConnectionErrorTask(request, this.mSessionResultContext));
            LogMgr.log(6, "999");
        }

        void doFelicaActivation(FelicaSessionRequest request, IMfiFelica felica) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaActivationTask(request, this.mSessionResultContext, felica));
            LogMgr.log(6, "999");
        }

        void doFelicaFinish(FelicaSessionRequest request) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaFinishTask(request, this.mSessionResultContext));
            LogMgr.log(6, "999");
        }

        FelicaAccessTask setServiceConnectionTimeout(FelicaSessionRequest request) {
            FelicaServiceConnectionTimeoutTask felicaServiceConnectionTimeoutTask = new FelicaServiceConnectionTimeoutTask(request, this.mSessionResultContext);
            this.mThreadHandler.postDelayed(felicaServiceConnectionTimeoutTask, 10000L);
            return felicaServiceConnectionTimeoutTask;
        }

        void cancelTask(FelicaAccessTask task) {
            task.mIsCanceled = true;
            this.mThreadHandler.removeCallbacks(task);
        }

        abstract class FelicaAccessTask implements Runnable {
            final IMfiFelica mIMfiFelica;
            boolean mIsCanceled = false;
            boolean mIsExecuted = false;
            final FelicaSessionRequest mRequest;
            final ResultContext mResultContext;

            abstract void doFelicaAccess();

            FelicaAccessTask(FelicaSessionRequest request, ResultContext resultContext, IMfiFelica felica) {
                this.mRequest = request;
                this.mResultContext = resultContext;
                this.mIMfiFelica = felica;
            }

            /* JADX DEBUG: Finally have unexpected throw blocks count: 0, expect 1 */
            @Override // java.lang.Runnable
            public void run() {
                LogMgr.log(6, "000 " + getClass().getSimpleName());
                synchronized (this.mRequest) {
                    try {
                    } finally {
                    }
                    if (!this.mIsCanceled) {
                        doFelicaAccess();
                        this.mIsExecuted = true;
                    } else {
                        this.mIsExecuted = true;
                    }
                }
                LogMgr.log(6, "999 " + getClass().getSimpleName());
            }

            final void finalizeEmergency(Error.Cause cause, String msg) {
                emergencyFinishProcedure(cause, msg, 0);
            }

            private void emergencyFinishProcedure(Error.Cause cause, String msg, int step) {
                Error.Cause cause2;
                String str;
                LogMgr.log(1, "000 step=" + step);
                try {
                    try {
                    } catch (Throwable th) {
                        th = th;
                        LogMgr.log(1, "810 " + th.getClass().getSimpleName() + ":" + th.getMessage());
                        emergencyFinishProcedure(cause2, str, step + 1);
                        return;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cause2 = cause;
                    str = msg;
                    LogMgr.log(1, "810 " + th.getClass().getSimpleName() + ":" + th.getMessage());
                    emergencyFinishProcedure(cause2, str, step + 1);
                    return;
                }
                switch (step) {
                    case 0:
                        cause2 = cause;
                        str = msg;
                        FelicaSessionHandler.this.mIsFelicaActivating = false;
                        this.mRequest.abort();
                        emergencyFinishProcedure(cause2, str, step + 1);
                        break;
                    case 1:
                        cause2 = cause;
                        str = msg;
                        if (FelicaSessionHandler.this.mFelicaServiceConnection != null) {
                            FelicaSessionHandler.this.mFelicaServiceConnection.cancelConnectionTimeout();
                        }
                        emergencyFinishProcedure(cause2, str, step + 1);
                        break;
                    case 2:
                        if (cause == Error.Cause.ACTIVATE_FELICA) {
                            cause2 = cause;
                            str = msg;
                            getResultCollector().onDetectCallbackError(cause2, Error.ContentType.UNKNOWN, 0, 0, str);
                        } else {
                            cause2 = cause;
                            str = msg;
                            getResultCollector().onDetectUnknownError(str);
                        }
                        emergencyFinishProcedure(cause2, str, step + 1);
                        break;
                    case 3:
                        IMfiFelica iMfiFelica = this.mIMfiFelica;
                        if (iMfiFelica != null) {
                            iMfiFelica.inactivateFelica();
                        }
                        cause2 = cause;
                        str = msg;
                        emergencyFinishProcedure(cause2, str, step + 1);
                        break;
                    case 4:
                        if (FelicaSessionHandler.this.mFelicaServiceConnection != null) {
                            FelicaServiceConnection felicaServiceConnection = FelicaSessionHandler.this.mFelicaServiceConnection;
                            FelicaSessionHandler.this.mFelicaServiceConnection = null;
                            felicaServiceConnection.disconnect();
                        }
                        cause2 = cause;
                        str = msg;
                        emergencyFinishProcedure(cause2, str, step + 1);
                        break;
                    case 5:
                        FelicaSessionHandler.this.onSessionFinished(this.mRequest);
                        cause2 = cause;
                        str = msg;
                        emergencyFinishProcedure(cause2, str, step + 1);
                        break;
                    case 6:
                        if (this.mResultContext != null) {
                            getLifecycleAdapter().onFinished(this.mResultContext.getResult());
                        } else {
                            ResultContext resultContext = new ResultContext();
                            resultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                            getLifecycleAdapter().onFinished(resultContext.getResult());
                        }
                        break;
                }
            }

            final boolean isRequestAborted() {
                return this.mRequest.mIsAborted;
            }

            final FelicaLifecycleAdapter getLifecycleAdapter() {
                return this.mRequest.getAdapter();
            }

            final ResultContext.ResultCollector getResultCollector() {
                return this.mResultContext.getResultCollector();
            }

            final ResultContext.ResultChecker getResultChecker() {
                return this.mResultContext.getResultChecker();
            }
        }

        class FelicaServiceConnectionTask extends FelicaAccessTask {
            FelicaServiceConnectionTask(FelicaSessionRequest request, ResultContext resultContext) {
                super(request, resultContext, null);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            public void doFelicaAccess() {
                LogMgr.log(6, "000");
                if (!getLifecycleAdapter().onPrepareStarting(this.mResultContext)) {
                    FelicaSessionHandler.this.doFelicaFinish(this.mRequest);
                    LogMgr.log(6, "998");
                    return;
                }
                FelicaSessionHandler.this.mFelicaServiceConnection = FelicaSessionHandler.this.new FelicaServiceConnection();
                try {
                    if (!FelicaSessionHandler.this.mFelicaServiceConnection.connect(this.mRequest)) {
                        LogMgr.log(1, "800");
                        getResultCollector().onDetectUnknownError(Error.Cause.BIND_SERVICE, ObfuscatedMsgUtil.executionPoint());
                        FelicaSessionHandler.this.handleOnServiceConnectionError(this.mRequest, false);
                        FelicaSessionHandler.this.mFelicaServiceConnection = null;
                    }
                } catch (Exception e) {
                    LogMgr.log(1, "801");
                    getResultCollector().onDetectError(Error.Cause.BIND_SERVICE, e);
                    FelicaSessionHandler.this.handleOnServiceConnectionError(this.mRequest, false);
                    FelicaSessionHandler.this.mFelicaServiceConnection = null;
                }
                LogMgr.log(6, "999");
            }
        }

        class FelicaServiceConnectionTimeoutTask extends FelicaAccessTask {
            FelicaServiceConnectionTimeoutTask(FelicaSessionRequest request, ResultContext resultContext) {
                super(request, resultContext, null);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            void doFelicaAccess() {
                LogMgr.log(6, "000");
                getResultCollector().onDetectUnknownError(Error.Cause.BIND_SERVICE, ObfuscatedMsgUtil.executionPoint());
                FelicaSessionHandler.this.handleOnServiceConnectionError(this.mRequest, false);
                LogMgr.log(6, "999");
            }
        }

        class FelicaServiceConnectedTask extends FelicaAccessTask {
            private final IBinder mIBinder;

            FelicaServiceConnectedTask(FelicaSessionRequest request, ResultContext resultContext, IBinder iBinder) {
                super(request, resultContext, null);
                this.mIBinder = iBinder;
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            void doFelicaAccess() {
                LogMgr.log(6, "000");
                if (FelicaSessionHandler.this.mFelicaServiceConnection != null) {
                    if (FelicaSessionHandler.this.mFelicaServiceConnection.cancelConnectionTimeout()) {
                        LogMgr.log(6, "001 canceled time out timer.");
                        if (isRequestAborted()) {
                            LogMgr.log(6, "997 request aborted.");
                            FelicaSessionHandler.this.doFelicaFinish(this.mRequest);
                            return;
                        }
                        IMfiFelica iMfiFelicaAsInterface = IMfiFelica.Stub.asInterface(this.mIBinder);
                        if (iMfiFelicaAsInterface == null) {
                            LogMgr.log(6, "998 IMfiFelica could not be obtained.");
                            getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                            FelicaSessionHandler.this.handleOnServiceConnectionError(this.mRequest, false);
                            return;
                        } else {
                            FelicaSessionHandler.this.doFelicaActivation(this.mRequest, iMfiFelicaAsInterface);
                            LogMgr.log(6, "999");
                            return;
                        }
                    }
                    LogMgr.log(6, "996 already timed out.");
                    return;
                }
                LogMgr.log(6, "995 already finished.");
            }
        }

        class FelicaActivationTask extends FelicaAccessTask {
            FelicaActivationTask(FelicaSessionRequest request, ResultContext resultContext, IMfiFelica felica) {
                super(request, resultContext, felica);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            public void doFelicaAccess() {
                LogMgr.log(6, "000");
                if (!isRequestAborted()) {
                    try {
                        FelicaSessionHandler.this.mIsFelicaActivating = true;
                        LogMgr.log(6, "001 call activateFelica.");
                        if (!getResultChecker().checkActivateFelicaResult(this.mIMfiFelica.activateFelica(this.mRequest.mContext.getPackageName(), new FelicaActivationCallback()))) {
                            FelicaSessionHandler.this.mIsFelicaActivating = false;
                            FelicaSessionHandler.this.onFelicaActivationFailed(this.mRequest);
                        } else {
                            LogMgr.log(6, "005 call activateFelica is successful.");
                        }
                    } catch (RemoteException e) {
                        LogMgr.log(1, "700");
                        getResultCollector().onDetectError(Error.Cause.ACTIVATE_FELICA, e);
                        FelicaSessionHandler.this.handleOnServiceConnectionError(this.mRequest, false);
                    } catch (Exception e2) {
                        LogMgr.log(1, "701");
                        getResultCollector().onDetectError(Error.Cause.ACTIVATE_FELICA, e2);
                        FelicaSessionHandler.this.handleOnServiceConnectionError(this.mRequest, false);
                    }
                    LogMgr.log(6, "999");
                    return;
                }
                FelicaSessionHandler.this.doFelicaFinish(this.mRequest);
                LogMgr.log(6, "998 request aborted.");
            }

            class FelicaActivationCallback extends IFelicaEventListener.Stub {
                FelicaActivationCallback() {
                }

                @Override // com.felicanetworks.mfc.IFelicaEventListener
                public void finished() throws RemoteException {
                    LogMgr.log(6, "002 finished.");
                    try {
                        synchronized (FelicaActivationTask.this.mRequest) {
                            if (FelicaSessionHandler.this.mIsFelicaActivating) {
                                FelicaSessionHandler.this.mIsFelicaActivating = false;
                                FelicaSessionHandler.this.onFelicaActivated(FelicaActivationTask.this.mRequest, FelicaActivationTask.this.mIMfiFelica);
                            }
                        }
                    } catch (Throwable unused) {
                        LogMgr.log(1, "800");
                        FelicaActivationTask.this.finalizeEmergency(Error.Cause.ACTIVATE_FELICA, ObfuscatedMsgUtil.executionPoint());
                    }
                }

                @Override // com.felicanetworks.mfc.IFelicaEventListener
                public void errorOccurred(int errorId, String msg, AppInfo otherAppInfo) throws RemoteException {
                    LogMgr.log(2, "003 errorOccurred errorId=" + errorId + " msg=" + msg);
                    try {
                        synchronized (FelicaActivationTask.this.mRequest) {
                            if (FelicaSessionHandler.this.mIsFelicaActivating) {
                                FelicaSessionHandler.this.mIsFelicaActivating = false;
                                FelicaActivationTask.this.mResultContext.getResultCollector().onDetectCallbackError(Error.Cause.ACTIVATE_FELICA, Error.ContentType.FELICA_EVENT_LISTENER_ERROR, 0, errorId, msg);
                                FlavorConst.ServerOperationRetryParameters serverOperationRetryParametersSelectByActivationErrorCallback = RetryingStrategy.selectByActivationErrorCallback(errorId);
                                if (serverOperationRetryParametersSelectByActivationErrorCallback != null) {
                                    FelicaActivationTask.this.mResultContext.getResultCollector().onDetectRetryRequest(serverOperationRetryParametersSelectByActivationErrorCallback);
                                }
                                FelicaSessionHandler.this.onFelicaActivationFailed(FelicaActivationTask.this.mRequest);
                            }
                        }
                    } catch (Throwable unused) {
                        LogMgr.log(1, "800");
                        FelicaActivationTask.this.finalizeEmergency(Error.Cause.ACTIVATE_FELICA, ObfuscatedMsgUtil.executionPoint());
                    }
                }
            }
        }

        class FelicaActivatedTask extends FelicaAccessTask {
            FelicaActivatedTask(FelicaSessionRequest request, ResultContext resultContext, IMfiFelica felica) {
                super(request, resultContext, felica);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            public void doFelicaAccess() {
                FelicaResultInfo felicaResultInfoInactivateFelica;
                LogMgr.log(6, "000");
                if (isRequestAborted()) {
                    LogMgr.log(6, "100 request aborted.");
                } else {
                    getLifecycleAdapter().onFelicaActivated(this.mResultContext);
                }
                Exception exc = null;
                try {
                    LogMgr.log(6, "001 call inactivateFelica.");
                    felicaResultInfoInactivateFelica = this.mIMfiFelica.inactivateFelica();
                } catch (RemoteException e) {
                    e = e;
                    LogMgr.log(1, "700");
                    felicaResultInfoInactivateFelica = null;
                    exc = e;
                } catch (Exception e2) {
                    e = e2;
                    LogMgr.log(1, "701");
                    felicaResultInfoInactivateFelica = null;
                    exc = e;
                }
                if (exc == null) {
                    if (getResultChecker().checkInactivateFelicaResult(felicaResultInfoInactivateFelica)) {
                        LogMgr.log(6, "002 success inactivateFelica.");
                    } else {
                        LogMgr.log(2, "003 failed inactivateFelica.");
                    }
                } else {
                    getResultCollector().onDetectError(Error.Cause.INACTIVATE_FELICA, exc);
                }
                getLifecycleAdapter().onFelicaInactivated(this.mResultContext);
                FelicaSessionHandler.this.doFelicaFinish(this.mRequest);
                LogMgr.log(6, "999");
            }
        }

        class FelicaActivationFailedTask extends FelicaAccessTask {
            FelicaActivationFailedTask(FelicaSessionRequest request, ResultContext resultContext) {
                super(request, resultContext, null);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            public void doFelicaAccess() {
                LogMgr.log(6, "000");
                getLifecycleAdapter().onFelicaActivationFailed(this.mResultContext);
                FelicaSessionHandler.this.doFelicaFinish(this.mRequest);
                LogMgr.log(6, "999");
            }
        }

        class FelicaServiceConnectionErrorTask extends FelicaAccessTask {
            FelicaServiceConnectionErrorTask(FelicaSessionRequest request, ResultContext resultContext) {
                super(request, resultContext, null);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            public void doFelicaAccess() {
                LogMgr.log(6, "000");
                getLifecycleAdapter().onFelicaConnectionError(this.mResultContext);
                FelicaSessionHandler.this.doFelicaFinish(this.mRequest);
                LogMgr.log(6, "999");
            }
        }

        class FelicaFinishTask extends FelicaAccessTask {
            FelicaFinishTask(FelicaSessionRequest request, ResultContext resultContext) {
                super(request, resultContext, null);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            public void doFelicaAccess() {
                LogMgr.log(6, "000");
                if (FelicaSessionHandler.this.mFelicaServiceConnection != null) {
                    try {
                        FelicaSessionHandler.this.mFelicaServiceConnection.disconnect();
                    } catch (Exception e) {
                        getResultCollector().onDetectError(Error.Cause.UNBIND_SERVICE, e);
                        LogMgr.log(2, "700");
                    }
                    getLifecycleAdapter().onFelicaDisconnected(this.mResultContext);
                    FelicaSessionHandler.this.mFelicaServiceConnection = null;
                }
                FelicaSessionHandler.this.onSessionFinished(this.mRequest);
                getLifecycleAdapter().onFinished(this.mResultContext.getResult());
                LogMgr.log(6, "999");
            }
        }

        class FelicaServiceConnection implements ServiceConnection {
            private FelicaAccessTask mConnectionTimeoutTask;
            private FelicaSessionRequest mRequest;

            FelicaServiceConnection() {
            }

            boolean connect(FelicaSessionRequest request) throws Exception {
                LogMgr.log(6, "000 connect.");
                this.mRequest = request;
                Intent intent = new Intent();
                intent.setClass(this.mRequest.mContext, FelicaAdapter.class);
                if (!this.mRequest.mContext.bindService(intent, this, 1)) {
                    return false;
                }
                this.mConnectionTimeoutTask = FelicaSessionHandler.this.setServiceConnectionTimeout(this.mRequest);
                LogMgr.log(6, "999 connect.");
                return true;
            }

            void disconnect() {
                LogMgr.log(6, "000 disconnect.");
                this.mConnectionTimeoutTask = null;
                this.mRequest.mContext.unbindService(this);
                LogMgr.log(6, "999 disconnect.");
            }

            boolean cancelConnectionTimeout() {
                LogMgr.log(6, "000");
                FelicaAccessTask felicaAccessTask = this.mConnectionTimeoutTask;
                if (felicaAccessTask == null) {
                    LogMgr.log(6, "001 The connection is not in process.");
                    return true;
                }
                if (felicaAccessTask.mIsExecuted) {
                    LogMgr.log(6, "002 Connection timeout has already been triggered.");
                    return false;
                }
                if (this.mConnectionTimeoutTask.mIsCanceled) {
                    LogMgr.log(6, "003 already canceled.");
                    return true;
                }
                FelicaSessionHandler.this.cancelTask(this.mConnectionTimeoutTask);
                LogMgr.log(6, "999");
                return true;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogMgr.log(6, "000 FelicaConnection#onServiceConnected called.");
                FelicaSessionHandler.this.onFelicaServiceConnected(this.mRequest, service);
                LogMgr.log(6, "999 FelicaConnection#onServiceConnected called.");
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName name) {
                LogMgr.log(6, "000 FelicaConnection#onServiceDisonnected called.");
                synchronized (this.mRequest) {
                    if (FelicaSessionHandler.this.mSessionResultContext != null) {
                        FelicaSessionHandler.this.mSessionResultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                    }
                    FelicaSessionHandler.this.handleOnServiceConnectionError(this.mRequest, true);
                }
                LogMgr.log(6, "999 FelicaConnection#onServiceDisonnected called.");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleOnServiceConnectionError(final FelicaSessionRequest request, boolean fromCallbacks) {
            LogMgr.log(6, "000");
            if (request.mIsAborted) {
                LogMgr.log(6, "995 request already aborted.");
                return;
            }
            request.abort();
            if (this.mIsFelicaActivating) {
                LogMgr.log(6, "996 felica activating.");
                this.mIsFelicaActivating = false;
                onFelicaActivationFailed(request);
            } else if (fromCallbacks) {
                LogMgr.log(6, "997");
            } else {
                if (this.mFelicaServiceConnection.cancelConnectionTimeout()) {
                    LogMgr.log(6, "001 canceled time out timer.");
                    onFelicaServiceConnectionError(request);
                    LogMgr.log(6, "999");
                    return;
                }
                LogMgr.log(6, "998 already timed out.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doRetry(Action action, Bundle extras, FlavorConst.ServerOperationRetryParameters retryType) {
        LogMgr.log(6, "000");
        if (retryType == null) {
            LogMgr.log(6, "997 do not retry.");
            return false;
        }
        LogMgr.log(6, "001 type=" + retryType.name());
        if (extras == null) {
            extras = new Bundle();
        }
        int i = extras.getInt(EXT_KEY_RETRY_REMAINING, -1);
        int i2 = i == -1 ? 2 : (-1) + i;
        LogMgr.log(6, "002 Retry remaining=" + i2);
        if (i2 <= 0) {
            LogMgr.log(6, "998 Retry limit exceeded.");
            return false;
        }
        extras.putInt(EXT_KEY_RETRY_REMAINING, i2);
        LogMgr.log(6, "999");
        return CloudMessagingEventReceiver.ChannelAlarm.setup(getApplicationContext(), extras, action.value, retryType.RETRY_INTERVAL);
    }
}
