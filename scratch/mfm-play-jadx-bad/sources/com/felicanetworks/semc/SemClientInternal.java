package com.felicanetworks.semc;

import android.content.Context;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgentErrorMessage;
import com.felicanetworks.semc.SemClient;
import com.felicanetworks.semc.util.LogMgrUtil;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SemClientUtil;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientInternal extends SemClient {
    private static final String EXC_INVALID_PACKAGENAME = "The packageName is null or invalid.";
    private static SemClientInternal sInstance;
    private OnNotifyClientEventListener mOnNotifyClientEventListener;
    private OnTsmSequenceListener mOnTsmSequenceListener;
    private final ISemClientEventListener mSemClientEventListener = new SemClientEventInternalListenerStub();

    public interface OnNotifyClientEventListener {
        void onError(int i, String str, String str2);

        void onNotified();
    }

    public interface OnTsmSequenceListener extends SemClient.OnTsmSequenceListener {
        void onErrorAndDoWorkManagerRetry(int i, String str, String str2);
    }

    private SemClientInternal() {
        LogMgrUtil.log(5, "000");
        LogMgrUtil.log(5, "999");
    }

    public static synchronized SemClientInternal getInstance() {
        LogMgrUtil.log(5, "000");
        if (sInstance == null) {
            LogMgrUtil.log(9, "001");
            sInstance = new SemClientInternal();
        }
        LogMgrUtil.log(5, "999");
        return sInstance;
    }

    public synchronized void connect(Context context, boolean z, SemClient.OnConnectedListener onConnectedListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder("context[");
        sb.append(context != null);
        sb.append("] isCheckSystemUser[");
        sb.append(z);
        sb.append("] listener[");
        sb.append(onConnectedListener != null);
        sb.append("]");
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_CONNECT, sb.toString());
        LogMgrUtil.log(5, "000");
        SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_CONNECT);
        semClientApiInfo.setIsCheckSystemUser(z);
        semClientApiInfo.setIsCalledFromInternal(true);
        semClientApiInfo.setIsModeExists(false);
        super.connect(context, semClientApiInfo, onConnectedListener);
        LogMgrUtil.log(5, "999");
        LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_CONNECT);
    }

    public synchronized void connect(Context context, boolean z, SemClient.OnConnectedListener onConnectedListener, int i) throws IllegalStateException, SemClientException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder("context[");
        sb.append(context != null);
        sb.append("] isCheckSystemUser[");
        sb.append(z);
        sb.append("] listener[");
        sb.append(onConnectedListener != null);
        sb.append("]");
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_CONNECT, sb.toString());
        LogMgrUtil.log(5, "000");
        SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_CONNECT);
        semClientApiInfo.setIsCheckSystemUser(z);
        semClientApiInfo.setIsCalledFromInternal(true);
        semClientApiInfo.setIsModeExists(true);
        semClientApiInfo.setMode(i);
        super.connect(context, semClientApiInfo, onConnectedListener);
        LogMgrUtil.log(5, "999");
        LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_CONNECT);
    }

    @Override // com.felicanetworks.semc.SemClient
    public synchronized void disconnect() throws SemClientException {
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_DISCONNECT);
        LogMgrUtil.log(5, "000");
        SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_DISCONNECT);
        semClientApiInfo.setIsCalledFromInternal(true);
        super.disconnect(semClientApiInfo);
        LogMgrUtil.log(5, "999");
        LogMgrUtil.performanceOut("API", "SemClient", SemClientApiInfo.METHOD_NAME_DISCONNECT);
    }

    public synchronized void notifyClientEvent(String str, String str2, OnNotifyClientEventListener onNotifyClientEventListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder("listener[");
        sb.append(onNotifyClientEventListener != null);
        sb.append("] packageName[");
        sb.append(str);
        sb.append("] eventType[");
        sb.append(str2);
        sb.append("]");
        LogMgrUtil.performanceIn("API", "SemClientInternal", SemClientApiInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT, sb.toString());
        LogMgrUtil.log(5, "000");
        if (str == null) {
            LogMgrUtil.log(2, "700 packageName is null.");
            throw new IllegalArgumentException(EXC_INVALID_PACKAGENAME);
        }
        if (str.isEmpty()) {
            LogMgrUtil.log(2, "701 packageName is empty.");
            throw new IllegalArgumentException(EXC_INVALID_PACKAGENAME);
        }
        if (onNotifyClientEventListener == null) {
            LogMgrUtil.log(2, "702 listener is null.");
            throw new IllegalArgumentException(PlayIntegrityAgentErrorMessage.EXC_INVALID_LISTENER);
        }
        checkConnected(true);
        try {
            try {
                try {
                    SemClientApiInfo semClientApiInfo = new SemClientApiInfo(SemClientApiInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT);
                    semClientApiInfo.setPackageName(str);
                    semClientApiInfo.setEventType(str2);
                    this.mOnNotifyClientEventListener = onNotifyClientEventListener;
                    SemClientUtil.checkSemClientResult(this.mSemClientService.callSemClientApi(semClientApiInfo, this.mSemClientEventListener, 2));
                    LogMgrUtil.log(5, "999");
                    LogMgrUtil.performanceOut("API", "SemClientInternal", SemClientApiInfo.METHOD_NAME_NOTIFY_CLIENT_EVENT);
                } catch (Exception e) {
                    LogMgrUtil.log(2, "705 Other Exception");
                    LogMgrUtil.printStackTrace(9, e);
                    throw new SemClientException(901, ObfuscatedMsgUtil.executionPoint());
                }
            } catch (SemClientException e2) {
                LogMgrUtil.log(2, "703 " + e2.toString() + " id:" + e2.getErrorCode() + " message:" + e2.getMessage());
                throw e2;
            } catch (IllegalArgumentException e3) {
                e = e3;
                LogMgrUtil.log(2, "704 " + e.toString() + " message:" + e.getMessage());
                throw e;
            } catch (IllegalStateException e4) {
                e = e4;
                LogMgrUtil.log(2, "704 " + e.toString() + " message:" + e.getMessage());
                throw e;
            }
        } catch (Throwable th) {
            LogMgrUtil.log(5, "999");
            throw th;
        }
    }

    public synchronized void startTsmSequence(String str, OnTsmSequenceListener onTsmSequenceListener) throws IllegalStateException, SemClientException, IllegalArgumentException {
        StringBuilder sb = new StringBuilder("listener[");
        sb.append(onTsmSequenceListener != null);
        sb.append("] linkageData[");
        sb.append(str);
        sb.append("]");
        LogMgrUtil.performanceIn("API", "SemClient", SemClientApiInfo.METHOD_NAME_START_TSM_SEQUENCE, sb.toString());
        LogMgrUtil.log(5, "000");
        checkStartTsmArguments(str, onTsmSequenceListener);
        checkConnected(true);
        startOnline(onTsmSequenceListener);
        try {
            try {
                try {
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
                }
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
            stopOnline();
            throw th;
        }
    }

    private synchronized void startOnline(OnTsmSequenceListener onTsmSequenceListener) throws IllegalStateException {
        LogMgrUtil.log(9, "000");
        onlineCheck();
        this.mOnline = true;
        this.mOnTsmSequenceListener = onTsmSequenceListener;
        LogMgrUtil.log(9, "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized OnTsmSequenceListener stopOnline() {
        OnTsmSequenceListener onTsmSequenceListener;
        LogMgrUtil.log(9, "000");
        onTsmSequenceListener = this.mOnTsmSequenceListener;
        this.mOnline = false;
        this.mOnTsmSequenceListener = null;
        LogMgrUtil.log(9, "999");
        return onTsmSequenceListener;
    }

    class SemClientEventInternalListenerStub extends SemClient.SemClientEventListenerStub {
        SemClientEventInternalListenerStub() {
            super();
        }

        @Override // com.felicanetworks.semc.SemClient.SemClientEventListenerStub, com.felicanetworks.semc.ISemClientEventListener
        public void onEventNotify(SemClientNotifyEventInfo semClientNotifyEventInfo) {
            String callbackName;
            int errorCode;
            String errorMessage;
            boolean z;
            String str;
            LogMgrUtil.log(5, "000");
            if (semClientNotifyEventInfo == null) {
                LogMgrUtil.log(2, "700 notifyEventInfo is null");
                SemClientInternal.this.unbindSemClient();
            }
            try {
                callbackName = semClientNotifyEventInfo.getCallbackName();
                if (callbackName.endsWith(SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR) || callbackName.endsWith(SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR_AND_DO_WORK_MANAGER_RETRY)) {
                    errorCode = semClientNotifyEventInfo.getErrorCode();
                    String errorAdditionalInformation = semClientNotifyEventInfo.getErrorAdditionalInformation();
                    errorMessage = semClientNotifyEventInfo.getErrorMessage();
                    boolean isNeedBindForSEMCApp = semClientNotifyEventInfo.getIsNeedBindForSEMCApp();
                    LogMgrUtil.log(8, "001 errorCode :" + errorCode + ", additionalErrorInformation :" + errorAdditionalInformation + ", message ; " + errorMessage);
                    z = isNeedBindForSEMCApp;
                    str = errorAdditionalInformation;
                } else {
                    str = null;
                    errorMessage = null;
                    z = false;
                    errorCode = 0;
                }
                callbackName.hashCode();
                switch (callbackName) {
                    case "OnTsmSequenceListener#onError":
                        onTsmSequenceOnError(errorCode, str, errorMessage);
                        break;
                    case "OnNotifyClientEventListener#onError":
                        notificationError(errorCode, str, errorMessage);
                        break;
                    case "OnTsmSequenceListener#onErrorAndDoWorkManagerRetry":
                        onTsmSequenceOnErrorAndDoWorkManagerRetry(errorCode, str, errorMessage);
                        break;
                    case "OnNotifyClientEventListener#onNotified":
                        notified();
                        break;
                    case "OnConnectedListener#onConnected":
                        connectedOnConnect();
                        break;
                    case "OnConnectedListener#onError":
                        connectedOnError(errorCode, str, errorMessage, z);
                        break;
                    case "OnTsmSequenceListener#onFinished":
                        onTsmSequenceOnFinished();
                        break;
                    default:
                        LogMgrUtil.log(2, "702 Unknown method : " + callbackName);
                        SemClientInternal.this.unbindSemClient();
                        break;
                }
            } catch (SemClientException unused) {
                LogMgrUtil.log(2, "701 notifyEventInfo is incorrect data");
            }
        }

        private void notified() {
            OnNotifyClientEventListener onNotifyClientEventListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClientInternal.this) {
                onNotifyClientEventListener = null;
                if (SemClientInternal.this.mOnNotifyClientEventListener != null) {
                    LogMgrUtil.log(9, "001");
                    OnNotifyClientEventListener onNotifyClientEventListener2 = SemClientInternal.this.mOnNotifyClientEventListener;
                    SemClientInternal.this.mOnNotifyClientEventListener = null;
                    onNotifyClientEventListener = onNotifyClientEventListener2;
                } else {
                    LogMgrUtil.log(9, "002");
                    SemClientInternal.this.unbindSemClient();
                }
            }
            if (onNotifyClientEventListener != null) {
                LogMgrUtil.log(5, "003");
                LogMgrUtil.performanceIn("API", "OnNotifyClientEventListener", "onNotified");
                onNotifyClientEventListener.onNotified();
                LogMgrUtil.performanceOut("API", "OnConnectedListener", "onNotified");
            }
            LogMgrUtil.log(5, "999");
        }

        protected void notificationError(int i, String str, String str2) {
            OnNotifyClientEventListener onNotifyClientEventListener;
            LogMgrUtil.log(5, "000");
            synchronized (SemClientInternal.this) {
                LogMgrUtil.log(9, "001");
                onNotifyClientEventListener = SemClientInternal.this.mOnNotifyClientEventListener;
                SemClientInternal.this.mOnNotifyClientEventListener = null;
            }
            if (onNotifyClientEventListener != null) {
                LogMgrUtil.performanceIn("API", "OnNotifyClientEventListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onNotifyClientEventListener.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnNotifyClientEventListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            }
            LogMgrUtil.log(5, "999");
        }

        private void onTsmSequenceOnFinished() {
            LogMgrUtil.log(5, "000");
            OnTsmSequenceListener onTsmSequenceListenerStopOnline = SemClientInternal.this.stopOnline();
            if (onTsmSequenceListenerStopOnline != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnTsmSequenceListener", "onFinished");
                onTsmSequenceListenerStopOnline.onFinished();
                LogMgrUtil.performanceOut("API", "OnTsmSequenceListener", "onFinished");
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onTsmSequenceOnErrorAndDoWorkManagerRetry(int i, String str, String str2) {
            LogMgrUtil.log(5, "000");
            OnTsmSequenceListener onTsmSequenceListenerStopOnline = SemClientInternal.this.stopOnline();
            if (onTsmSequenceListenerStopOnline != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR_AND_DO_WORK_MANAGER_RETRY, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onTsmSequenceListenerStopOnline.onErrorAndDoWorkManagerRetry(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR_AND_DO_WORK_MANAGER_RETRY);
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }

        private void onTsmSequenceOnError(int i, String str, String str2) {
            LogMgrUtil.log(5, "000");
            OnTsmSequenceListener onTsmSequenceListenerStopOnline = SemClientInternal.this.stopOnline();
            if (onTsmSequenceListenerStopOnline != null) {
                LogMgrUtil.log(8, "001");
                LogMgrUtil.performanceIn("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR, "errorCode[" + i + "] additionalErrorInformation[" + str + "] message[" + str2 + "]");
                onTsmSequenceListenerStopOnline.onError(i, str, str2);
                LogMgrUtil.performanceOut("API", "OnTsmSequenceListener", SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR);
            } else {
                LogMgrUtil.log(8, "002");
            }
            LogMgrUtil.log(5, "999");
        }
    }
}
