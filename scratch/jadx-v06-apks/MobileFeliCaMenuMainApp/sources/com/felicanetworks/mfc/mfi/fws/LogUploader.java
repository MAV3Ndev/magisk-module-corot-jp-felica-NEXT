package com.felicanetworks.mfc.mfi.fws;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.amazonaws.http.HttpHeader;
import com.felicanetworks.mfc.mfi.ClientIdCache;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.fws.json.NotifyDeviceMessagePayloadJson;
import com.felicanetworks.mfc.mfi.fws.json.NotifyDeviceMessageRequestJson;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class LogUploader {
    private static final String FWS_VERSION = "v1";
    private final OnUploadFinishListener mListener;
    private Handler mThreadHandler;

    public interface OnUploadFinishListener {
        void onFinished(int i, String str);
    }

    public static class Request {
        final String clientId;
        final LogInfoContent logInfoContent;
        final String mfiClientVersion;
        final String operationId;
        public final int requestId;
        final SeInfo seInfo;

        public static Request build(Context context, int i, SeInfo seInfo, String str, LogInfoContent logInfoContent) throws Exception {
            if (context == null) {
                throw new Exception("context passed was null.");
            }
            String clientId = ClientIdCache.getClientId(context);
            if (clientId == null) {
                clientId = FwsParamCreator.createClientId();
                ClientIdCache.saveClientId(context, clientId);
            }
            return new Request(i, seInfo, clientId, str, context.getString(R.string.mfi_client_version), logInfoContent);
        }

        public Request(int i, SeInfo seInfo, String str, String str2, String str3, LogInfoContent logInfoContent) {
            this.requestId = i;
            this.seInfo = seInfo;
            this.clientId = str;
            this.operationId = str2;
            this.mfiClientVersion = str3;
            this.logInfoContent = logInfoContent;
        }

        public static final class LogInfoContent {
            private final String[] messageIds;
            private final String[] messages;
            private final String[] operationIds;

            public LogInfoContent(String[] strArr, String[] strArr2, String[] strArr3) {
                this.operationIds = strArr;
                this.messageIds = strArr2;
                this.messages = strArr3;
            }
        }
    }

    public LogUploader(OnUploadFinishListener onUploadFinishListener) {
        this.mListener = onUploadFinishListener;
    }

    public void start() {
        LogMgr.log(6, "000");
        if (this.mThreadHandler != null) {
            LogMgr.log(6, "998 already started.");
            return;
        }
        HandlerThread handlerThread = new HandlerThread("log-uploader-thread");
        handlerThread.start();
        this.mThreadHandler = new Handler(handlerThread.getLooper());
        LogMgr.log(6, "999");
    }

    public void request(Request request) {
        LogMgr.log(6, "000");
        Handler handler = this.mThreadHandler;
        if (handler == null) {
            LogMgr.log(6, "998 not started.");
        } else {
            handler.post(new LogUploadTask(request));
            LogMgr.log(6, "999");
        }
    }

    public void shutdown() {
        LogMgr.log(6, "000");
        Handler handler = this.mThreadHandler;
        if (handler == null) {
            LogMgr.log(6, "998 not started.");
            return;
        }
        handler.getLooper().quitSafely();
        this.mThreadHandler = null;
        LogMgr.log(6, "999");
    }

    private class LogUploadTask implements Runnable {
        private static final String CURRENT_TIME_FORMAT = "yyyyMMddHHmmssSSS";
        private static final String DUMMY_SEP_ID = "FFFFFF";
        private static final String DUMMY_SE_ID = "FFFFFFFFFFFFFFFF";
        private static final String DUMMY_SE_TYPE = "FF";
        private static final String DUMMY_WALLET_APP_ID = "FFFFFFFF";
        private static final int FWS_SEQUENCE_NUMBER = 1;
        private static final int OPERATION_ID_LENGTH = 37;
        private static final String TIME_ZONE_UTC = "UTC";
        private static final String URL_API_NOTIFY_DEVICE_MESSAGE = "notifyDeviceMessage";
        private static final String URL_SERVICE_ID_GENERAL = "general";
        private final Request mRequest;

        LogUploadTask(Request request) {
            this.mRequest = request;
        }

        @Override // java.lang.Runnable
        public void run() {
            String strRunFwsPost;
            LogMgr.log(6, "000");
            try {
                NotifyDeviceMessagePayloadJson notifyDeviceMessagePayloadJson = new NotifyDeviceMessagePayloadJson();
                notifyDeviceMessagePayloadJson.setCnonce(FwsParamCreator.createRandomNumber());
                notifyDeviceMessagePayloadJson.setClientId(this.mRequest.clientId);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_UTC));
                notifyDeviceMessagePayloadJson.setCurrentTime(simpleDateFormat.format(new Date()));
                notifyDeviceMessagePayloadJson.setPlatformType("02");
                notifyDeviceMessagePayloadJson.setDeviceName(Build.MODEL);
                notifyDeviceMessagePayloadJson.setOsVersion(Build.VERSION.RELEASE);
                Request.LogInfoContent logInfoContent = this.mRequest.logInfoContent;
                notifyDeviceMessagePayloadJson.setLogInfoList(logInfoContent.operationIds, logInfoContent.messageIds, logInfoContent.messages);
                String strA = new b().a(notifyDeviceMessagePayloadJson);
                NotifyDeviceMessageRequestJson notifyDeviceMessageRequestJson = new NotifyDeviceMessageRequestJson();
                notifyDeviceMessageRequestJson.setRequestId(FwsParamCreator.createRequestId());
                String strCreateOperationId = this.mRequest.operationId;
                if (strCreateOperationId == null || strCreateOperationId.length() != 37) {
                    strCreateOperationId = FwsParamCreator.createOperationId();
                }
                notifyDeviceMessageRequestJson.setOperationId(strCreateOperationId);
                notifyDeviceMessageRequestJson.setDeviceMessageToken(strA);
                strRunFwsPost = runFwsPost(createPostUri(URL_SERVICE_ID_GENERAL, URL_API_NOTIFY_DEVICE_MESSAGE, 1), notifyDeviceMessageRequestJson.toString(), 0);
            } catch (JSONException e) {
                LogMgr.log(6, "700 JSONException: " + e.getMessage());
                strRunFwsPost = null;
            } catch (Exception e2) {
                LogMgr.log(6, "701 Exception: " + e2.getMessage());
                strRunFwsPost = null;
            }
            if (LogUploader.this.mListener != null) {
                LogUploader.this.mListener.onFinished(this.mRequest.requestId, strRunFwsPost);
            }
            LogMgr.log(6, "999");
        }

        private String createPostUri(String str, String str2, int i) {
            SeInfo seInfo = this.mRequest.seInfo;
            Object[] objArr = new Object[9];
            objArr[0] = FlavorConst.FWS_HOST;
            objArr[1] = LogUploader.FWS_VERSION;
            objArr[2] = seInfo != null ? seInfo.getSepId() : DUMMY_SEP_ID;
            objArr[3] = seInfo != null ? seInfo.getSeType() : DUMMY_SE_TYPE;
            objArr[4] = seInfo != null ? seInfo.getSeId() : DUMMY_SE_ID;
            objArr[5] = seInfo != null ? seInfo.getPlatformType() : "02";
            objArr[6] = str;
            objArr[7] = str2;
            objArr[8] = Integer.valueOf(i);
            return String.format("https://%s/fws/%s/%s/%s/%s/%s/%s/%s/%d", objArr);
        }

        private String runFwsPost(String str, String str2, int i) throws ProtocolException, HttpException {
            String strCreateUserAgent = createUserAgent();
            FwsPostRequest fwsPostRequest = new FwsPostRequest();
            fwsPostRequest.setPostData(str2);
            fwsPostRequest.setRetryCount(i);
            fwsPostRequest.setWalletAppId(DUMMY_WALLET_APP_ID);
            fwsPostRequest.setMfiClientVersion(this.mRequest.mfiClientVersion);
            return run(str, strCreateUserAgent, fwsPostRequest, new FwsResponse());
        }

        private String createUserAgent() {
            SeInfo seInfo = this.mRequest.seInfo;
            Object[] objArr = new Object[7];
            objArr[0] = MfiClientConst.CLIENT_APP_NAME;
            objArr[1] = this.mRequest.mfiClientVersion;
            objArr[2] = MfiClientConst.DEVICE_PLATFFORM_NAME;
            objArr[3] = Build.VERSION.RELEASE;
            objArr[4] = seInfo != null ? seInfo.getSeType() : DUMMY_SE_TYPE;
            objArr[5] = seInfo != null ? seInfo.getSepId() : DUMMY_SEP_ID;
            objArr[6] = Build.MODEL;
            return String.format("%s/%s (%s %s; %s; %s; %s)", objArr);
        }

        private String run(String str, String str2, IRequest iRequest, IResponse iResponse) throws ProtocolException, HttpException {
            runCommon(str, str2, iRequest, iResponse);
            return iResponse.getData();
        }

        private void runCommon(String str, String str2, IRequest iRequest, IResponse iResponse) throws ProtocolException, HttpException {
            HttpCommunicationAgent httpCommunicationAgent;
            LogMgr.log(4, "000");
            LogMgr.log(6, "001 uri= " + str);
            synchronized (this) {
                LogMgr.log(7, "001");
                httpCommunicationAgent = new HttpCommunicationAgent();
            }
            try {
                try {
                    httpCommunicationAgent.setUserAgent(str2);
                    httpCommunicationAgent.doTransaction(str, iRequest, iResponse);
                    LogMgr.log(7, "002");
                    LogMgr.log(4, "999");
                } catch (Exception e) {
                    throw e;
                }
            } catch (Throwable th) {
                LogMgr.log(7, "002");
                throw th;
            }
        }
    }

    static final class FwsPostRequest implements IRequest {
        private String mData;
        private String mMficVersion;
        private int mRetryCount;
        private String mWalletAppId;

        FwsPostRequest() {
        }

        @Override // com.felicanetworks.mfc.mfi.http.IRequest
        public void send(HttpURLConnection httpURLConnection) throws Throwable {
            BufferedOutputStream bufferedOutputStream = null;
            try {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestProperty(HttpHeader.CONTENT_TYPE, HttpCommunicationAgent.FWS_CONTENT_TYPE_REQ);
                httpURLConnection.setRequestProperty("X-MFIC-Version", this.mMficVersion);
                httpURLConnection.setRequestProperty("X-MFIC-Retry", String.valueOf(this.mRetryCount));
                httpURLConnection.setRequestProperty("X-MFIC-WalletAppId", this.mWalletAppId);
                httpURLConnection.setRequestProperty("X-MFIC-TimeZone", TimeZone.getDefault().getID());
                httpURLConnection.setRequestProperty("X-MFIC-UTC", String.valueOf(Calendar.getInstance().getTimeInMillis()));
                if (this.mData != null) {
                    byte[] uTF8ByteArrays = StringUtil.toUTF8ByteArrays(this.mData);
                    httpURLConnection.setFixedLengthStreamingMode(uTF8ByteArrays.length);
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    try {
                        bufferedOutputStream2.write(uTF8ByteArrays, 0, uTF8ByteArrays.length);
                        bufferedOutputStream = bufferedOutputStream2;
                    } catch (Throwable th) {
                        th = th;
                        bufferedOutputStream = bufferedOutputStream2;
                        if (bufferedOutputStream != null) {
                            try {
                                bufferedOutputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        throw th;
                    }
                }
                httpURLConnection.connect();
                if (bufferedOutputStream != null) {
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        public void setPostData(String str) {
            this.mData = str;
        }

        public void setRetryCount(int i) {
            this.mRetryCount = i;
        }

        public void setWalletAppId(String str) {
            this.mWalletAppId = str;
        }

        public void setMfiClientVersion(String str) {
            this.mMficVersion = str;
        }
    }
}
