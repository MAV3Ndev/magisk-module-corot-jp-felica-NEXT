package com.felicanetworks.mfc.mfi.fws;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import com.felicanetworks.mfc.mfi.ClientIdCache;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.fws.json.NotifyDeviceMessagePayloadJson;
import com.felicanetworks.mfc.mfi.fws.json.NotifyDeviceMessageRequestJson;
import com.felicanetworks.mfc.mfi.http.AbstractHttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpCommunicationAgent;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.IRequest;
import com.felicanetworks.mfc.mfi.http.IResponse;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.util.SettingInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.semc.sws.SwsRequest;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class LogUploader {
    private static final String FWS_VERSION = "v1";
    private final OnUploadFinishListener mListener;
    private Handler mThreadHandler;

    public interface OnUploadFinishListener {
        void onFinished(int requestId, String result);
    }

    public static class Request {
        final String clientId;
        final LogInfoContent logInfoContent;
        final String mfiClientVersion;
        final String operationId;
        public final int requestId;
        final SeInfo seInfo;

        public static Request build(final Context context, final int requestId, final SeInfo seInfo, final String operationId, final LogInfoContent logInfoContent) throws Exception {
            if (context == null) {
                throw new Exception("context passed was null.");
            }
            String clientId = ClientIdCache.getClientId(context);
            if (clientId == null) {
                clientId = FwsParamCreator.createClientId();
                ClientIdCache.saveClientId(context, clientId);
            }
            return new Request(requestId, seInfo, clientId, operationId, context.getString(R.string.mfi_client_version), logInfoContent);
        }

        public Request(final int requestId, final SeInfo seInfo, final String clientId, final String operationId, final String mfiClientVersion, final LogInfoContent logInfoContent) {
            this.requestId = requestId;
            this.seInfo = seInfo;
            this.clientId = clientId;
            this.operationId = operationId;
            this.mfiClientVersion = mfiClientVersion;
            this.logInfoContent = logInfoContent;
        }

        public static final class LogInfoContent {
            private final String[] messageIds;
            private final String[] messages;
            private final String[] operationIds;

            public LogInfoContent(String[] operationIds, String[] messageIds, String[] messages) {
                this.operationIds = operationIds;
                this.messageIds = messageIds;
                this.messages = messages;
            }
        }
    }

    public LogUploader(final OnUploadFinishListener listener) {
        this.mListener = listener;
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

    public void request(final Request request) {
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

        private String createPostUri(String serviceId, String apiName, int seqNum) {
            SeInfo seInfo = this.mRequest.seInfo;
            return String.format("https://%s/fws/%s/%s/%s/%s/%s/%s/%s/%d", SettingInfo.getFwsServerHost(), LogUploader.FWS_VERSION, seInfo != null ? seInfo.getSepId() : "FFFFFF", seInfo != null ? seInfo.getSeType() : DUMMY_SE_TYPE, seInfo != null ? seInfo.getSeId() : "FFFFFFFFFFFFFFFF", seInfo != null ? seInfo.getPlatformType() : "02", serviceId, apiName, Integer.valueOf(seqNum));
        }

        private String runFwsPost(String uri, String requestData, int retryCount) throws ProtocolException, HttpException {
            String strCreateUserAgent = createUserAgent();
            FwsPostRequest fwsPostRequest = new FwsPostRequest();
            fwsPostRequest.setPostData(requestData);
            fwsPostRequest.setRetryCount(retryCount);
            fwsPostRequest.setWalletAppId(DUMMY_WALLET_APP_ID);
            fwsPostRequest.setMfiClientVersion(this.mRequest.mfiClientVersion);
            return run(uri, strCreateUserAgent, fwsPostRequest, new FwsResponse());
        }

        private String createUserAgent() {
            SeInfo seInfo = this.mRequest.seInfo;
            return String.format("%s/%s (%s %s; %s; %s; %s)", MfiClientConst.CLIENT_APP_NAME, this.mRequest.mfiClientVersion, MfiClientConst.DEVICE_PLATFFORM_NAME, Build.VERSION.RELEASE, seInfo != null ? seInfo.getSeType() : DUMMY_SE_TYPE, seInfo != null ? seInfo.getSepId() : "FFFFFF", Build.MODEL);
        }

        private String run(String uri, String userAgent, IRequest request, IResponse response) throws ProtocolException, HttpException {
            runCommon(uri, userAgent, request, response);
            return response.getData();
        }

        private void runCommon(String uri, String userAgent, IRequest request, IResponse response) throws ProtocolException, HttpException {
            HttpCommunicationAgent httpCommunicationAgent;
            LogMgr.log(4, "000");
            LogMgr.log(6, "001 uri= " + uri);
            synchronized (this) {
                LogMgr.log(7, "001");
                httpCommunicationAgent = new HttpCommunicationAgent();
                try {
                } catch (Throwable th) {
                    LogMgr.log(7, "002");
                    throw th;
                }
            }
            try {
                httpCommunicationAgent.setUserAgent(userAgent);
                httpCommunicationAgent.doTransaction(uri, request, response);
                LogMgr.log(7, "002");
                LogMgr.log(4, "999");
            } catch (Exception e) {
                throw e;
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
        public void send(HttpURLConnection urlConnection) throws Throwable {
            BufferedOutputStream bufferedOutputStream = null;
            try {
                urlConnection.setRequestMethod(SwsRequest.HTTP_METHOD_POST);
                urlConnection.setDoOutput(true);
                urlConnection.setRequestProperty("Content-Type", AbstractHttpCommunicationAgent.FWS_CONTENT_TYPE_REQ);
                urlConnection.setRequestProperty("X-MFIC-Version", this.mMficVersion);
                urlConnection.setRequestProperty("X-MFIC-Retry", String.valueOf(this.mRetryCount));
                urlConnection.setRequestProperty("X-MFIC-WalletAppId", this.mWalletAppId);
                urlConnection.setRequestProperty("X-MFIC-TimeZone", TimeZone.getDefault().getID());
                urlConnection.setRequestProperty("X-MFIC-UTC", String.valueOf(Calendar.getInstance().getTimeInMillis()));
                String profileId = SettingInfo.getProfileId();
                if (!profileId.isEmpty()) {
                    urlConnection.setRequestProperty("X-MFIC-ProfileId", profileId);
                }
                String str = this.mData;
                if (str != null) {
                    byte[] uTF8ByteArrays = StringUtil.toUTF8ByteArrays(str);
                    urlConnection.setFixedLengthStreamingMode(uTF8ByteArrays.length);
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(urlConnection.getOutputStream());
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
                urlConnection.connect();
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

        public void setPostData(String data) {
            this.mData = data;
        }

        public void setRetryCount(int retryCount) {
            this.mRetryCount = retryCount;
        }

        public void setWalletAppId(String walletAppId) {
            this.mWalletAppId = walletAppId;
        }

        public void setMfiClientVersion(String mficVersion) {
            this.mMficVersion = mficVersion;
        }
    }
}
