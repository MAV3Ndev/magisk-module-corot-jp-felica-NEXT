package com.felicanetworks.mfm.playIntegrity.agent;

import android.content.Context;
import android.os.HandlerThread;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgentErrorType;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityTask;
import com.felicanetworks.mfm.playIntegrity.util.LogMgr;
import com.felicanetworks.mfm.playIntegrity.util.ObfuscatedMsgUtil;

/* JADX INFO: loaded from: classes3.dex */
class PlayIntegrityHandler {
    private static final int SLEEP_TIME_FOR_HANDLER_CHECK = 100;
    private static PlayIntegrityHandler sInstance;
    private StoppableHandler mHandler;

    private PlayIntegrityHandler() {
        LogMgr.log(6, "000");
        HandlerThread handlerThread = new HandlerThread("playIntegrity-session-handler-thread", 0);
        handlerThread.start();
        this.mHandler = new StoppableHandler(handlerThread.getLooper());
        LogMgr.log(6, "999");
    }

    static synchronized PlayIntegrityHandler getInstance() {
        LogMgr.log(6, "000");
        if (sInstance == null) {
            LogMgr.log(7, "001");
            sInstance = new PlayIntegrityHandler();
        }
        LogMgr.log(6, "999");
        return sInstance;
    }

    static synchronized boolean checkInstance() {
        return sInstance != null;
    }

    synchronized void post(Context context, String str, final PlayIntegrityAgent.PlayIntegrityTokenEventListener playIntegrityTokenEventListener) {
        LogMgr.log(6, "000");
        PlayIntegrityTask playIntegrityTask = new PlayIntegrityTask(context, this.mHandler, str, new PlayIntegrityTask.RelayListener() { // from class: com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityHandler.1
            @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityTask.RelayListener
            public void onSuccess(StoppableHandler stoppableHandler, String str2) {
                LogMgr.log(6, "000");
                try {
                    new NotifyRespAsync(true, stoppableHandler, str2, playIntegrityTokenEventListener, null, null).notifyRspAsync();
                } catch (Exception unused) {
                    LogMgr.log(1, "800 Failed to start NotifyRespAsyncTask.");
                }
                LogMgr.log(6, "999");
            }

            @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityTask.RelayListener
            public void onError(StoppableHandler stoppableHandler, int i, PlayIntegrityTask playIntegrityTask2) {
                String strExecutionPoint;
                LogMgr.log(6, "000");
                if (stoppableHandler.isStopped()) {
                    LogMgr.log(2, "700 Already has stopped.");
                    try {
                        new NotifyRespAsync(false, stoppableHandler, null, playIntegrityTokenEventListener, PlayIntegrityAgentErrorType.Error.TYPE_INTERRUPTED_ERROR, null).notifyRspAsync();
                    } catch (Exception unused) {
                        LogMgr.log(1, "800 Failed to start NotifyRespAsyncTask.");
                    }
                    LogMgr.log(6, "998");
                    return;
                }
                try {
                    if (playIntegrityTask2.isNeedRetry(i)) {
                        LogMgr.log(7, "001");
                        PlayIntegrityHandler.this.mHandler.postAtFrontOfQueue(playIntegrityTask2);
                    } else {
                        LogMgr.log(2, "701 PIA return error code.");
                        PlayIntegrityAgentErrorType.Error errorConvertErrorType = PlayIntegrityHandler.this.convertErrorType(i);
                        if (errorConvertErrorType == PlayIntegrityAgentErrorType.Error.TYPE_UNKNOWN_ERROR) {
                            LogMgr.log(1, "801 PIA return Unknown error code.");
                            strExecutionPoint = ObfuscatedMsgUtil.executionPoint();
                        } else {
                            strExecutionPoint = null;
                        }
                        try {
                            new NotifyRespAsync(false, stoppableHandler, null, playIntegrityTokenEventListener, errorConvertErrorType, strExecutionPoint).notifyRspAsync();
                        } catch (Exception unused2) {
                            LogMgr.log(1, "802 Failed to start NotifyRespAsyncTask.");
                        }
                    }
                } catch (Exception unused3) {
                    LogMgr.log(1, "803 Unknown error occurred.");
                    try {
                        new NotifyRespAsync(false, stoppableHandler, null, playIntegrityTokenEventListener, PlayIntegrityAgentErrorType.Error.TYPE_UNKNOWN_ERROR, ObfuscatedMsgUtil.executionPoint()).notifyRspAsync();
                    } catch (Exception unused4) {
                        LogMgr.log(1, "804 Failed to start NotifyRespAsyncTask.");
                    }
                }
                LogMgr.log(6, "999");
            }

            @Override // com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityTask.RelayListener
            public void onError(StoppableHandler stoppableHandler, String str2) {
                LogMgr.log(6, "000");
                LogMgr.log(2, "700 PIA is failed.");
                try {
                    new NotifyRespAsync(false, stoppableHandler, null, playIntegrityTokenEventListener, PlayIntegrityAgentErrorType.Error.TYPE_UNKNOWN_ERROR, str2).notifyRspAsync();
                } catch (Exception unused) {
                    LogMgr.log(1, "800 Failed to start NotifyRespAsyncTask.");
                }
                LogMgr.log(6, "999");
            }
        });
        this.mHandler.addTaskToQueue(playIntegrityTask);
        this.mHandler.post(playIntegrityTask);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PlayIntegrityAgentErrorType.Error convertErrorType(int i) {
        LogMgr.log(6, "000");
        PlayIntegrityAgentErrorType.Error error = PlayIntegrityErrorMapping.ERROR_MAP.get(Integer.valueOf(i));
        if (error == null) {
            error = PlayIntegrityAgentErrorType.Error.TYPE_UNKNOWN_ERROR;
        }
        LogMgr.log(6, "999");
        return error;
    }

    public void stop() {
        LogMgr.log(6, "000");
        if (!this.mHandler.checkQueueIsEmpty()) {
            LogMgr.log(7, "001");
            if (this.mHandler.stop()) {
                try {
                    try {
                        try {
                            LogMgr.log(7, "002");
                            this.mHandler.getLooper().getThread().interrupt();
                            LogMgr.log(7, "003");
                            while (!this.mHandler.checkQueueIsEmpty()) {
                                Thread.sleep(100L);
                            }
                            LogMgr.log(7, "004");
                            HandlerThread handlerThread = new HandlerThread("playIntegrity-session-handler-thread", 0);
                            handlerThread.start();
                            this.mHandler = new StoppableHandler(handlerThread.getLooper());
                        } catch (Throwable th) {
                            try {
                                LogMgr.log(7, "004");
                                HandlerThread handlerThread2 = new HandlerThread("playIntegrity-session-handler-thread", 0);
                                handlerThread2.start();
                                this.mHandler = new StoppableHandler(handlerThread2.getLooper());
                            } catch (Exception e) {
                                LogMgr.log(1, "801 Unknown Error occurred.");
                                LogMgr.printStackTrace(7, e);
                            }
                            throw th;
                        }
                    } catch (Exception e2) {
                        LogMgr.log(1, "800 Unknown Error occurred.");
                        LogMgr.printStackTrace(7, e2);
                        LogMgr.log(7, "004");
                        HandlerThread handlerThread3 = new HandlerThread("playIntegrity-session-handler-thread", 0);
                        handlerThread3.start();
                        this.mHandler = new StoppableHandler(handlerThread3.getLooper());
                    }
                } catch (Exception e3) {
                    LogMgr.log(1, "801 Unknown Error occurred.");
                    LogMgr.printStackTrace(7, e3);
                }
            }
        }
        LogMgr.log(6, "999");
    }

    public boolean checkPiaTaskHandlerIsStopped() {
        LogMgr.log(6, "000");
        boolean zIsStopped = this.mHandler.isStopped();
        LogMgr.log(6, "999 ret = " + zIsStopped);
        return zIsStopped;
    }

    private static class NotifyRespAsync {
        private final Thread mAsyncThread;
        private final NotifyRspRunnable mRunnable;

        public NotifyRespAsync(boolean z, StoppableHandler stoppableHandler, String str, PlayIntegrityAgent.PlayIntegrityTokenEventListener playIntegrityTokenEventListener, PlayIntegrityAgentErrorType.Error error, String str2) throws IllegalArgumentException {
            LogMgr.log(5, "000");
            NotifyRspRunnable notifyRspRunnable = new NotifyRspRunnable(z, stoppableHandler, str, playIntegrityTokenEventListener, error, str2);
            this.mRunnable = notifyRspRunnable;
            this.mAsyncThread = new Thread(notifyRspRunnable);
            LogMgr.log(5, "999");
        }

        private static class NotifyRspRunnable implements Runnable {
            private String mErrMsg;
            private PlayIntegrityAgentErrorType.Error mErrType;
            private boolean mIsSuccess;
            private final PlayIntegrityAgent.PlayIntegrityTokenEventListener mListener;
            private final StoppableHandler mStoppableHandler;
            private final String mToken;

            public NotifyRspRunnable(boolean z, StoppableHandler stoppableHandler, String str, PlayIntegrityAgent.PlayIntegrityTokenEventListener playIntegrityTokenEventListener, PlayIntegrityAgentErrorType.Error error, String str2) throws IllegalArgumentException {
                boolean z2;
                LogMgr.log(5, "000");
                boolean z3 = true;
                if (playIntegrityTokenEventListener == null) {
                    LogMgr.log(1, "800 Illegal argument, listener is null.");
                    throw new IllegalArgumentException("Illegal argument, listener is null.");
                }
                if (stoppableHandler == null) {
                    LogMgr.log(1, "801 Illegal argument, handler is null.");
                    throw new IllegalArgumentException("Illegal argument, handler is null.");
                }
                if (z && str == null) {
                    LogMgr.log(2, "700 Invalid result, token is null.");
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z || error != null) {
                    z3 = z2;
                } else {
                    LogMgr.log(2, "701 Invalid result, errType is null.");
                }
                if (!z3) {
                    LogMgr.log(7, "001");
                    this.mIsSuccess = z;
                    this.mStoppableHandler = stoppableHandler;
                    this.mToken = str;
                    this.mListener = playIntegrityTokenEventListener;
                    this.mErrType = error;
                    this.mErrMsg = str2;
                } else {
                    LogMgr.log(7, "002");
                    this.mIsSuccess = false;
                    this.mStoppableHandler = stoppableHandler;
                    this.mToken = null;
                    this.mListener = playIntegrityTokenEventListener;
                    this.mErrType = PlayIntegrityAgentErrorType.Error.TYPE_UNKNOWN_ERROR;
                    this.mErrMsg = ObfuscatedMsgUtil.executionPoint();
                }
                LogMgr.log(5, "999");
            }

            @Override // java.lang.Runnable
            public void run() {
                LogMgr.log(6, "000");
                try {
                    if (this.mStoppableHandler.isStopped()) {
                        LogMgr.log(2, "700 Already has stopped.");
                        this.mIsSuccess = false;
                        this.mErrType = PlayIntegrityAgentErrorType.Error.TYPE_INTERRUPTED_ERROR;
                        this.mErrMsg = null;
                    }
                    this.mStoppableHandler.removeTaskFromQueue();
                    if (this.mIsSuccess) {
                        LogMgr.log(7, "001");
                        this.mListener.onSuccess(this.mToken);
                    } else {
                        LogMgr.log(7, "002");
                        this.mListener.onError(this.mErrType, this.mErrMsg);
                    }
                    LogMgr.log(7, "003");
                } catch (Exception unused) {
                    LogMgr.log(1, "800 Failed to call listener.");
                }
                LogMgr.log(6, "999");
            }
        }

        public void notifyRspAsync() {
            LogMgr.log(6, "000");
            this.mAsyncThread.start();
            LogMgr.log(6, "999");
        }
    }
}
