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
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
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

        Action(String str) {
            this.value = str;
        }

        static Action find(String str) {
            for (Action action : values()) {
                if (TextUtils.equals(action.value, str)) {
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
    public int onStartCommand(Intent intent, int i, int i2) {
        LogMgr.log(6, "000 startId=" + i2);
        this.mJobCount = this.mJobCount + 1;
        try {
            ForegroundServiceSetupProvider.requestForegroundService(this, ForegroundServiceSetupProvider.Type.CLOUD_MESSAGING_SERVICE);
        } catch (Throwable unused) {
            LogMgr.log(1, "800");
            onDetectedOutOfSessionError(i2, Error.Cause.OTHER, ObfuscatedMsgUtil.executionPoint());
        }
        if (intent == null) {
            LogMgr.log(6, "998 intent=null");
            onDetectedOutOfSessionError(i2, Error.Cause.OTHER, ObfuscatedMsgUtil.executionPoint());
            return 2;
        }
        String action = intent.getAction();
        Action actionFind = Action.find(action);
        LogMgr.log(6, "001 action=" + actionFind.name());
        int i3 = AnonymousClass2.$SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Action[actionFind.ordinal()];
        if (i3 == 1) {
            onMessageReceived(intent.getExtras(), i2);
        } else if (i3 == 2) {
            onNewTokenReceived(intent.getExtras(), i2);
        } else {
            LogMgr.log(6, "700 intentAction : " + action);
            requestStopService(i2);
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
    public void requestStopService(final int i) {
        LogMgr.log(6, "000");
        this.mMainThreadHandler.post(new Runnable() { // from class: com.felicanetworks.mfc.mfi.CloudMessagingService.1
            @Override // java.lang.Runnable
            public void run() {
                LogMgr.log(6, "000 mJobCount=" + CloudMessagingService.this.mJobCount + ", startId=" + i);
                CloudMessagingService.access$010(CloudMessagingService.this);
                if (CloudMessagingService.this.mJobCount == 0) {
                    CloudMessagingService.this.stopSelf();
                }
            }
        });
        LogMgr.log(6, "999");
    }

    private void onMessageReceived(Bundle bundle, int i) {
        LogMgr.log(6, "000");
        this.mSessionHandler.post(new MessageOperationRequest(getApplicationContext(), i, bundle));
        LogMgr.log(6, "999");
    }

    private void onNewTokenReceived(Bundle bundle, int i) {
        LogMgr.log(6, "000");
        this.mSessionHandler.post(new OnNewTokenOperationRequest(getApplicationContext(), i, bundle));
        LogMgr.log(6, "999");
    }

    private void onDetectedOutOfSessionError(int i, Error.Cause cause, String str) {
        LogMgr.log(6, "000");
        Error error = new Error(cause);
        error.errorMessage = str;
        if (!requestLogUpload(i, error)) {
            requestStopService(i);
            LogMgr.log(6, "998 Log upload request was rejected.");
        }
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onRequestFinished(int i, Error error) {
        LogMgr.log(6, "000");
        if (error == null) {
            requestStopService(i);
            LogMgr.log(6, "997 error=null");
        } else {
            if (!requestLogUpload(i, error)) {
                requestStopService(i);
                LogMgr.log(6, "998 Log upload request was rejected.");
            }
            LogMgr.log(6, "999");
        }
    }

    private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
        private OnUploadFinishListenerImpl() {
        }

        @Override // com.felicanetworks.mfc.mfi.fws.LogUploader.OnUploadFinishListener
        public void onFinished(int i, String str) {
            LogMgr.log(6, "000 requestId=" + i);
            CloudMessagingService.this.requestStopService(i);
            LogMgr.log(6, "999");
        }
    }

    private boolean requestLogUpload(int i, Error error) {
        LogMgr.log(6, "000 startId=" + i);
        if (Error.ContentType.EXECUTE_SERVER_OPERATION_CB_ERROR.equalsType(error.errorContentType) && Arrays.asList(LOG_UPLOAD_NEGATIVE_LIST).contains(Integer.valueOf(error.errorType))) {
            LogMgr.log(6, "997 errorType=" + error.errorType);
            return false;
        }
        try {
            LogUploader.Request requestBuild = LogUploader.Request.build(getApplicationContext(), i, error.seInfo, error.operationId, new LogUploader.Request.LogInfoContent(new String[]{error.operationId}, new String[]{error.messageId}, new String[]{error.getMessage()}));
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

        MessageOperationRequest(Context context, int i, Bundle bundle) {
            super(context, i);
            this.mMessageData = bundle;
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
            this.mOperationId = this.mMessageData.getString(EXT_KEY_OPERATION_ID);
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
        public void onFinished(FelicaSessionHandler.FelicaSessionResult felicaSessionResult) {
            LogMgr.log(6, "000");
            if (CloudMessagingService.this.doRetry(Action.FCM_ON_MESSAGE_RECEIVED, this.mMessageData, felicaSessionResult.getRetryParameters())) {
                CloudMessagingService.this.onRequestFinished(this.mStartId, null);
            } else {
                CloudMessagingService.this.onRequestFinished(this.mStartId, felicaSessionResult.getError());
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
            public void onError(int i, String str) {
                LogMgr.log(6, "000 onError errType=" + i + " msg=" + str);
                setError(i, str);
                countDown();
            }

            @Override // com.felicanetworks.mfc.mfi.ExecuteServerOperationEventCallback
            public void onRetryRequired(int i, String str) {
                LogMgr.log(6, "000 onRetryRequired errType=" + i + " msg=" + str);
                setError(i, str);
                setRetryRequired();
                countDown();
            }

            synchronized void setError(int i, String str) {
                this.mErrorResult = new Pair<>(Integer.valueOf(i), str);
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

        OnNewTokenOperationRequest(Context context, int i, Bundle bundle) {
            super(context, i);
            this.mMessageData = bundle;
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
        public void onFinished(FelicaSessionHandler.FelicaSessionResult felicaSessionResult) {
            LogMgr.log(6, "000");
            if (CloudMessagingService.this.doRetry(Action.FCM_ON_NEW_TOKEN_RECEIVED, this.mMessageData, felicaSessionResult.getRetryParameters())) {
                CloudMessagingService.this.onRequestFinished(this.mStartId, null);
            } else {
                CloudMessagingService.this.onRequestFinished(this.mStartId, felicaSessionResult.getError());
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
            public void onError(int i, String str) {
                LogMgr.log(6, "000 onError errType=" + i + " msg=" + str);
                setError(i, str);
                countDown();
            }

            synchronized void setError(int i, String str) {
                this.mErrorResult = new Pair<>(Integer.valueOf(i), str);
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

        static FlavorConst.ServerOperationRetryParameters selectByMfiClientCallback(int i) {
            if (i == 8) {
                return FlavorConst.ServerOperationRetryParameters.TYPE_NFC_RW_USED;
            }
            if (i != 205) {
                return null;
            }
            return FlavorConst.ServerOperationRetryParameters.TYPE_HTTP_ERROR;
        }

        static FlavorConst.ServerOperationRetryParameters selectByFelicaException(int i, int i2, String str) {
            if (i != 1) {
                if (i == 2 && i2 == 39) {
                    return FlavorConst.ServerOperationRetryParameters.TYPE_USED_BY_OTHER_APP;
                }
                return null;
            }
            if (i2 == 8 && TextUtils.equals(FelicaException.NFC_RW_USED_MESSAGE, str)) {
                return FlavorConst.ServerOperationRetryParameters.TYPE_NFC_RW_USED;
            }
            return null;
        }

        static FlavorConst.ServerOperationRetryParameters selectByActivationErrorCallback(int i) {
            if (i == 3) {
                return FlavorConst.ServerOperationRetryParameters.TYPE_HTTP_ERROR;
            }
            if (i != 7) {
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

            Cause(String str) {
                this.api = str;
            }
        }

        public enum ContentType {
            ILLEGAL_ARGUMENT_EXCEPTION("IllegalArgumentException"),
            NUMBER_FORMAT_EXCEPTION("NumberFormatException"),
            MFI_CLIENT_EXCEPTION("MfiClientException"),
            FELICA_EXCEPTION("FelicaException"),
            EXCEPTION("Exception"),
            EXECUTE_SERVER_OPERATION_CB_ERROR("onError"),
            FELICA_EVENT_LISTENER_ERROR("errorOccurred"),
            UNKNOWN(null);

            final String type;

            ContentType(String str) {
                this.type = str;
            }

            boolean equalsType(String str) {
                return TextUtils.equals(this.type, str);
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
            if (this.errorId == 0 && this.errorType == 0) {
                return String.format(Locale.US, "%s: %s(MSG=%s)", this.api, this.errorContentType, this.errorMessage);
            }
            if (this.errorId != 0 && this.errorType != 0) {
                return String.format(Locale.US, "%s: %s(ID=%d, TYPE=%d, MSG=%s)", this.api, this.errorContentType, Integer.valueOf(this.errorId), Integer.valueOf(this.errorType), this.errorMessage);
            }
            if (this.errorId != 0) {
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

            void onFinished(FelicaSessionResult felicaSessionResult);

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

            FelicaSessionRequest(Context context, int i) {
                this.mContext = context;
                this.mStartId = i;
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

            FelicaSessionResult(Error error, FlavorConst.ServerOperationRetryParameters serverOperationRetryParameters) {
                this.mError = error;
                this.mRetryParameters = serverOperationRetryParameters;
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

                void setOperationId(String str) {
                    this.mOperationId = str;
                }

                void setMessageId(String str) {
                    this.mMessageId = str;
                }

                void setWalletAppId(String str) {
                    this.mWalletAppId = str;
                }

                void setRetryParameters(FlavorConst.ServerOperationRetryParameters serverOperationRetryParameters) {
                    this.mRetryParameters = serverOperationRetryParameters;
                }

                void createSuccessContent() {
                    this.mIsSuccess = true;
                }

                void createUnknownErrorContent(String str) {
                    LogMgr.log(6, "000");
                    createUnknownErrorContent(Error.Cause.OTHER, str);
                    LogMgr.log(6, "999");
                }

                void createUnknownErrorContent(Error.Cause cause, String str) {
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
                    error.errorMessage = str;
                    LogMgr.log(6, "999");
                }

                void createUnknownErrorContent(Exception exc) {
                    LogMgr.log(6, "000");
                    createUnknownErrorContent(exc.getClass().getSimpleName(), exc.getMessage());
                    LogMgr.log(6, "999");
                }

                void createUnknownErrorContent(String str, String str2) {
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
                    error.errorContentType = str;
                    this.mErrorData.errorMessage = str2;
                    LogMgr.log(6, "999");
                }

                void createErrorContent(Error.Cause cause, Exception exc) {
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
                    Class<?> cls = exc.getClass();
                    error.errorContentType = cls.getSimpleName();
                    error.errorMessage = exc.getMessage();
                    if (FelicaException.class.isAssignableFrom(cls)) {
                        FelicaException felicaException = (FelicaException) exc;
                        error.errorId = felicaException.getID();
                        error.errorType = felicaException.getType();
                    }
                    LogMgr.log(6, "999");
                    this.mErrorData = error;
                }

                void createErrorContent(Error.Cause cause, Error.ContentType contentType, int i, int i2, String str) {
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
                    error.errorMessage = str;
                    error.errorType = i;
                    error.errorId = i2;
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

                boolean checkActivateFelicaResult(FelicaResultInfo felicaResultInfo) {
                    FlavorConst.ServerOperationRetryParameters serverOperationRetryParametersSelectByFelicaException;
                    if (checkMfcResult(felicaResultInfo, Error.Cause.ACTIVATE_FELICA)) {
                        return true;
                    }
                    if (felicaResultInfo == null || (serverOperationRetryParametersSelectByFelicaException = RetryingStrategy.selectByFelicaException(felicaResultInfo.getId(), felicaResultInfo.getType(), felicaResultInfo.getMessage())) == null) {
                        return false;
                    }
                    ResultContext.this.mResultCollector.onDetectRetryRequest(serverOperationRetryParametersSelectByFelicaException);
                    return false;
                }

                boolean checkInactivateFelicaResult(FelicaResultInfo felicaResultInfo) {
                    return checkMfcResult(felicaResultInfo, Error.Cause.INACTIVATE_FELICA);
                }

                boolean checkServerOperationResult(FelicaResultInfo felicaResultInfo) {
                    return checkMfcResult(felicaResultInfo, Error.Cause.SERVER_OPERATION);
                }

                boolean checkProvisionServerOperationResult(FelicaResultInfo felicaResultInfo) {
                    return checkMfcResult(felicaResultInfo, Error.Cause.PROVISION_SERVER_OPERATION);
                }

                boolean checkGetSeInfoResult(FelicaResultInfo felicaResultInfo) {
                    SeInfo seInfo;
                    FlavorConst.ServerOperationRetryParameters serverOperationRetryParametersSelectByFelicaException;
                    if (checkMfcResult(felicaResultInfo, Error.Cause.GET_SE_INFORMATION)) {
                        Object value = ((FelicaResultInfoType) felicaResultInfo).getValue();
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
                    if (felicaResultInfo == null || (serverOperationRetryParametersSelectByFelicaException = RetryingStrategy.selectByFelicaException(felicaResultInfo.getId(), felicaResultInfo.getType(), felicaResultInfo.getMessage())) == null) {
                        return false;
                    }
                    ResultContext.this.mResultCollector.onDetectRetryRequest(serverOperationRetryParametersSelectByFelicaException);
                    return false;
                }

                boolean checkMfcResult(FelicaResultInfo felicaResultInfo, Error.Cause cause) {
                    Error.ContentType contentType;
                    LogMgr.log(7, "000");
                    if (felicaResultInfo == null) {
                        LogMgr.log(2, "704 " + cause.api + " Result is null!");
                        ResultContext.this.mResultCollector.onDetectUnknownError(cause, ObfuscatedMsgUtil.executionPoint());
                        return false;
                    }
                    LogMgr.log(7, "002 api=" + cause.api + ", getExceptionType=" + felicaResultInfo.getExceptionType());
                    int exceptionType = felicaResultInfo.getExceptionType();
                    if (exceptionType == 0) {
                        return true;
                    }
                    if (exceptionType == 1) {
                        LogMgr.log(2, "701 FelicaException");
                        int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Error$Cause[cause.ordinal()];
                        if (i == 1 || i == 2 || i == 3) {
                            contentType = Error.ContentType.MFI_CLIENT_EXCEPTION;
                        } else {
                            contentType = Error.ContentType.FELICA_EXCEPTION;
                        }
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoError(cause, felicaResultInfo, contentType);
                    } else if (exceptionType == 32) {
                        LogMgr.log(2, "702 IllegalArgumentException " + felicaResultInfo.getMessage());
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoErrorMessage(cause, felicaResultInfo.getMessage(), Error.ContentType.ILLEGAL_ARGUMENT_EXCEPTION);
                    } else if (exceptionType == 34) {
                        LogMgr.log(2, "704 NumberFormatException" + felicaResultInfo.getMessage());
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoErrorMessage(cause, felicaResultInfo.getMessage(), Error.ContentType.NUMBER_FORMAT_EXCEPTION);
                    } else {
                        LogMgr.log(2, "703 Illegal ExceptionType");
                        ResultContext.this.mResultCollector.onDetectFelicaResultInfoErrorMessage(cause, felicaResultInfo.getMessage(), Error.ContentType.EXCEPTION);
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

                void onDetectRetryRequest(FlavorConst.ServerOperationRetryParameters serverOperationRetryParameters) {
                    ResultContext.this.mResultBuilder.setRetryParameters(serverOperationRetryParameters);
                }

                void onDetectError(Error.Cause cause, Exception exc) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, exc);
                }

                void onDetectUnknownError(String str) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(str);
                }

                void onDetectUnknownError(Error.Cause cause, String str) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(cause, str);
                }

                void onDetectUnknownError(Exception exc) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(exc);
                }

                void onDetectUnknownError(String str, String str2) {
                    ResultContext.this.mResultBuilder.createUnknownErrorContent(str, str2);
                }

                void onDetectFelicaResultInfoError(Error.Cause cause, FelicaResultInfo felicaResultInfo, Error.ContentType contentType) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, contentType, felicaResultInfo.getType(), felicaResultInfo.getId(), felicaResultInfo.getMessage());
                }

                void onDetectFelicaResultInfoErrorMessage(Error.Cause cause, String str, Error.ContentType contentType) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, contentType, 0, 0, str);
                }

                void onDetectCallbackError(Error.Cause cause, Error.ContentType contentType, int i, int i2, String str) {
                    ResultContext.this.mResultBuilder.createErrorContent(cause, contentType, i, i2, str);
                }
            }
        }

        FelicaSessionHandler() {
            HandlerThread handlerThread = new HandlerThread("felica-session-handler-thread", 0);
            handlerThread.start();
            this.mThreadHandler = new Handler(handlerThread.getLooper());
        }

        void post(FelicaSessionRequest felicaSessionRequest) {
            if (felicaSessionRequest == null) {
                LogMgr.log(2, "700 Request is null.");
                return;
            }
            try {
                synchronized (this.mQueue) {
                    if (!this.mQueue.contains(felicaSessionRequest)) {
                        this.mQueue.add(felicaSessionRequest);
                    }
                    if (this.mQueue.getFirst() == felicaSessionRequest) {
                        this.mSessionResultContext = new ResultContext();
                        doFelicaServiceConnection(felicaSessionRequest);
                    }
                }
            } catch (ClassCastException unused) {
                LogMgr.log(2, "700 Argument is not FelicaSessionRequest.");
            } catch (Exception unused2) {
                LogMgr.log(2, "701 Request execution failed.");
            }
        }

        void onSessionFinished(FelicaSessionRequest felicaSessionRequest) {
            if (felicaSessionRequest == null) {
                LogMgr.log(2, "700 Request is null.");
                return;
            }
            synchronized (this.mQueue) {
                try {
                    try {
                        this.mQueue.remove(felicaSessionRequest);
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

        void doFelicaServiceConnection(FelicaSessionRequest felicaSessionRequest) {
            LogMgr.log(6, "000");
            if (felicaSessionRequest.mDelayed == 0) {
                this.mThreadHandler.post(new FelicaServiceConnectionTask(felicaSessionRequest, this.mSessionResultContext));
            } else {
                this.mThreadHandler.postDelayed(new FelicaServiceConnectionTask(felicaSessionRequest, this.mSessionResultContext), felicaSessionRequest.mDelayed);
            }
            LogMgr.log(6, "999");
        }

        void onFelicaServiceConnected(FelicaSessionRequest felicaSessionRequest, IBinder iBinder) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaServiceConnectedTask(felicaSessionRequest, this.mSessionResultContext, iBinder));
            LogMgr.log(6, "999");
        }

        void onFelicaActivated(FelicaSessionRequest felicaSessionRequest, IMfiFelica iMfiFelica) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaActivatedTask(felicaSessionRequest, this.mSessionResultContext, iMfiFelica));
            LogMgr.log(6, "999");
        }

        void onFelicaActivationFailed(FelicaSessionRequest felicaSessionRequest) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaActivationFailedTask(felicaSessionRequest, this.mSessionResultContext));
            LogMgr.log(6, "999");
        }

        void onFelicaServiceConnectionError(FelicaSessionRequest felicaSessionRequest) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaServiceConnectionErrorTask(felicaSessionRequest, this.mSessionResultContext));
            LogMgr.log(6, "999");
        }

        void doFelicaActivation(FelicaSessionRequest felicaSessionRequest, IMfiFelica iMfiFelica) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaActivationTask(felicaSessionRequest, this.mSessionResultContext, iMfiFelica));
            LogMgr.log(6, "999");
        }

        void doFelicaFinish(FelicaSessionRequest felicaSessionRequest) {
            LogMgr.log(6, "000");
            this.mThreadHandler.post(new FelicaFinishTask(felicaSessionRequest, this.mSessionResultContext));
            LogMgr.log(6, "999");
        }

        FelicaAccessTask setServiceConnectionTimeout(FelicaSessionRequest felicaSessionRequest) {
            FelicaServiceConnectionTimeoutTask felicaServiceConnectionTimeoutTask = new FelicaServiceConnectionTimeoutTask(felicaSessionRequest, this.mSessionResultContext);
            this.mThreadHandler.postDelayed(felicaServiceConnectionTimeoutTask, BIND_TIME_OUT);
            return felicaServiceConnectionTimeoutTask;
        }

        void cancelTask(FelicaAccessTask felicaAccessTask) {
            felicaAccessTask.mIsCanceled = true;
            this.mThreadHandler.removeCallbacks(felicaAccessTask);
        }

        abstract class FelicaAccessTask implements Runnable {
            final IMfiFelica mIMfiFelica;
            boolean mIsCanceled = false;
            boolean mIsExecuted = false;
            final FelicaSessionRequest mRequest;
            final ResultContext mResultContext;

            abstract void doFelicaAccess();

            FelicaAccessTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext, IMfiFelica iMfiFelica) {
                this.mRequest = felicaSessionRequest;
                this.mResultContext = resultContext;
                this.mIMfiFelica = iMfiFelica;
            }

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

            final void finalizeEmergency(Error.Cause cause, String str) {
                emergencyFinishProcedure(cause, str, 0);
            }

            private void emergencyFinishProcedure(Error.Cause cause, String str, int i) {
                LogMgr.log(1, "000 step=" + i);
                try {
                    switch (i) {
                        case 0:
                            FelicaSessionHandler.this.mIsFelicaActivating = false;
                            this.mRequest.abort();
                            break;
                        case 1:
                            if (FelicaSessionHandler.this.mFelicaServiceConnection != null) {
                                FelicaSessionHandler.this.mFelicaServiceConnection.cancelConnectionTimeout();
                            }
                            break;
                        case 2:
                            if (cause == Error.Cause.ACTIVATE_FELICA) {
                                getResultCollector().onDetectCallbackError(cause, Error.ContentType.UNKNOWN, 0, 0, str);
                            } else {
                                getResultCollector().onDetectUnknownError(str);
                            }
                            break;
                        case 3:
                            if (this.mIMfiFelica != null) {
                                this.mIMfiFelica.inactivateFelica();
                            }
                            break;
                        case 4:
                            if (FelicaSessionHandler.this.mFelicaServiceConnection != null) {
                                FelicaServiceConnection felicaServiceConnection = FelicaSessionHandler.this.mFelicaServiceConnection;
                                FelicaSessionHandler.this.mFelicaServiceConnection = null;
                                felicaServiceConnection.disconnect();
                            }
                            break;
                        case 5:
                            FelicaSessionHandler.this.onSessionFinished(this.mRequest);
                            break;
                        case 6:
                            if (this.mResultContext != null) {
                                getLifecycleAdapter().onFinished(this.mResultContext.getResult());
                                return;
                            }
                            ResultContext resultContext = new ResultContext();
                            resultContext.getResultCollector().onDetectUnknownError(ObfuscatedMsgUtil.executionPoint());
                            getLifecycleAdapter().onFinished(resultContext.getResult());
                            return;
                        default:
                            return;
                    }
                } catch (Throwable th) {
                    LogMgr.log(1, "810 " + th.getClass().getSimpleName() + ":" + th.getMessage());
                }
                emergencyFinishProcedure(cause, str, i + 1);
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
            FelicaServiceConnectionTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext) {
                super(felicaSessionRequest, resultContext, null);
            }

            @Override // com.felicanetworks.mfc.mfi.CloudMessagingService.FelicaSessionHandler.FelicaAccessTask
            public void doFelicaAccess() {
                LogMgr.log(6, "000");
                if (!getLifecycleAdapter().onPrepareStarting(this.mResultContext)) {
                    FelicaSessionHandler.this.doFelicaFinish(this.mRequest);
                    LogMgr.log(6, "998");
                    return;
                }
                FelicaSessionHandler felicaSessionHandler = FelicaSessionHandler.this;
                felicaSessionHandler.mFelicaServiceConnection = felicaSessionHandler.new FelicaServiceConnection();
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
            FelicaServiceConnectionTimeoutTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext) {
                super(felicaSessionRequest, resultContext, null);
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

            FelicaServiceConnectedTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext, IBinder iBinder) {
                super(felicaSessionRequest, resultContext, null);
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
            FelicaActivationTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext, IMfiFelica iMfiFelica) {
                super(felicaSessionRequest, resultContext, iMfiFelica);
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
                public void errorOccurred(int i, String str, AppInfo appInfo) throws RemoteException {
                    LogMgr.log(2, "003 errorOccurred errorId=" + i + " msg=" + str);
                    try {
                        synchronized (FelicaActivationTask.this.mRequest) {
                            if (FelicaSessionHandler.this.mIsFelicaActivating) {
                                FelicaSessionHandler.this.mIsFelicaActivating = false;
                                FelicaActivationTask.this.mResultContext.getResultCollector().onDetectCallbackError(Error.Cause.ACTIVATE_FELICA, Error.ContentType.FELICA_EVENT_LISTENER_ERROR, 0, i, str);
                                FlavorConst.ServerOperationRetryParameters serverOperationRetryParametersSelectByActivationErrorCallback = RetryingStrategy.selectByActivationErrorCallback(i);
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
            FelicaActivatedTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext, IMfiFelica iMfiFelica) {
                super(felicaSessionRequest, resultContext, iMfiFelica);
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
            FelicaActivationFailedTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext) {
                super(felicaSessionRequest, resultContext, null);
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
            FelicaServiceConnectionErrorTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext) {
                super(felicaSessionRequest, resultContext, null);
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
            FelicaFinishTask(FelicaSessionRequest felicaSessionRequest, ResultContext resultContext) {
                super(felicaSessionRequest, resultContext, null);
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

            boolean connect(FelicaSessionRequest felicaSessionRequest) throws Exception {
                LogMgr.log(6, "000 connect.");
                this.mRequest = felicaSessionRequest;
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
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                LogMgr.log(6, "000 FelicaConnection#onServiceConnected called.");
                FelicaSessionHandler.this.onFelicaServiceConnected(this.mRequest, iBinder);
                LogMgr.log(6, "999 FelicaConnection#onServiceConnected called.");
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
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
        public void handleOnServiceConnectionError(FelicaSessionRequest felicaSessionRequest, boolean z) {
            LogMgr.log(6, "000");
            if (felicaSessionRequest.mIsAborted) {
                LogMgr.log(6, "995 request already aborted.");
                return;
            }
            felicaSessionRequest.abort();
            if (this.mIsFelicaActivating) {
                LogMgr.log(6, "996 felica activating.");
                this.mIsFelicaActivating = false;
                onFelicaActivationFailed(felicaSessionRequest);
            } else if (z) {
                LogMgr.log(6, "997");
            } else {
                if (this.mFelicaServiceConnection.cancelConnectionTimeout()) {
                    LogMgr.log(6, "001 canceled time out timer.");
                    onFelicaServiceConnectionError(felicaSessionRequest);
                    LogMgr.log(6, "999");
                    return;
                }
                LogMgr.log(6, "998 already timed out.");
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfc.mfi.CloudMessagingService$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Action;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Error$Cause;

        static {
            int[] iArr = new int[Error.Cause.values().length];
            $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Error$Cause = iArr;
            try {
                iArr[Error.Cause.GET_SE_INFORMATION.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Error$Cause[Error.Cause.SERVER_OPERATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Error$Cause[Error.Cause.PROVISION_SERVER_OPERATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Error$Cause[Error.Cause.ACTIVATE_FELICA.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Error$Cause[Error.Cause.INACTIVATE_FELICA.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            int[] iArr2 = new int[Action.values().length];
            $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Action = iArr2;
            try {
                iArr2[Action.FCM_ON_MESSAGE_RECEIVED.ordinal()] = 1;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Action[Action.FCM_ON_NEW_TOKEN_RECEIVED.ordinal()] = 2;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfc$mfi$CloudMessagingService$Action[Action.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean doRetry(Action action, Bundle bundle, FlavorConst.ServerOperationRetryParameters serverOperationRetryParameters) {
        int i;
        LogMgr.log(6, "000");
        if (serverOperationRetryParameters == null) {
            LogMgr.log(6, "997 do not retry.");
            return false;
        }
        LogMgr.log(6, "001 type=" + serverOperationRetryParameters.name());
        if (bundle == null) {
            bundle = new Bundle();
        }
        int i2 = bundle.getInt(EXT_KEY_RETRY_REMAINING, -1);
        if (i2 == -1) {
            serverOperationRetryParameters.getClass();
            i = 2;
        } else {
            i = (-1) + i2;
        }
        LogMgr.log(6, "002 Retry remaining=" + i);
        if (i <= 0) {
            LogMgr.log(6, "998 Retry limit exceeded.");
            return false;
        }
        bundle.putInt(EXT_KEY_RETRY_REMAINING, i);
        LogMgr.log(6, "999");
        return CloudMessagingEventReceiver.ChannelAlarm.setup(getApplicationContext(), bundle, action.value, serverOperationRetryParameters.RETRY_INTERVAL);
    }
}
