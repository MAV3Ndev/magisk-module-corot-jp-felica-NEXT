package com.felicanetworks.semc.sws;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.SemClientConst;
import com.felicanetworks.semc.SemClientNotifyEventInfo;
import com.felicanetworks.semc.config.AccessConfig;
import com.felicanetworks.semc.http.HttpCommunicationAgent;
import com.felicanetworks.semc.http.HttpException;
import com.felicanetworks.semc.http.IRequest;
import com.felicanetworks.semc.http.IResponse;
import com.felicanetworks.semc.sws.json.ClientControlInfoJsonArray;
import com.felicanetworks.semc.sws.json.NotifyClientLogRequestJson;
import com.felicanetworks.semc.sws.json.NotifyClientLogResponseJson;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.SettingInfo;
import com.felicanetworks.semc.util.SharedPrefsProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class LogUploader {
    public static final String DUMMY_SEP_ID = "FFFFFF";
    public static final String DUMMY_SE_ID = "FFFFFFFFFFFFFFFF";
    private static final Integer[] LOG_UPLOAD_LIST;
    private static final List<String> MSG_CODE_LIST_OF_SENDING_LOG_REDUCTION;
    private static final long SENDING_LOG_REDUCTION_FREQUENCY = 100;
    private final OnUploadFinishListener mListener;
    private Handler mThreadHandler;

    public interface OnUploadFinishListener {
        void onFinished(String str, String str2);
    }

    static {
        ArrayList arrayList = new ArrayList();
        MSG_CODE_LIST_OF_SENDING_LOG_REDUCTION = arrayList;
        arrayList.add("03030700");
        arrayList.add("03030501");
        arrayList.add("03030200");
        arrayList.add("04030700");
        arrayList.add("04040700");
        arrayList.add("04030200");
        arrayList.add("04030501");
        LOG_UPLOAD_LIST = new Integer[]{900, 102, 100, 202, 200, 203, 300, 101, 103};
    }

    public static class Request {
        private static final String CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL = "retry.notifyClientLog";
        private static final String CURRENT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
        private static final String TIME_ZONE_JST = "Asia/Tokyo";
        final String clientId;
        final String datetime;
        final LogInfoContent logInfoContent;
        final String packageName;
        final String profileId;
        final String requestId;
        final String retryIntervalString;
        final String seInfo;
        final String semClientVersion;
        final String semClientVersionAdditionalInfo;
        final String sepId;
        final String urlInProfile;

        public static Request build(Context context, String str, LogInfoContent logInfoContent) throws Exception {
            Cursor cursorQuery;
            String strCreateClientId;
            String string;
            String str2;
            String str3;
            String str4;
            if (context == null) {
                throw new Exception("context passed was null.");
            }
            String sepId = LogUploader.getSepId();
            Cursor cursor = null;
            String packageName = null;
            cursor = null;
            try {
                try {
                    LogMgr.log(9, "001 mContext.getContentResolver.query() in.");
                    cursorQuery = context.getContentResolver().query(SharedPrefsProvider.getNotifyClientLogContentsUri(context), null, null, null, null);
                } catch (Exception e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                LogMgr.log(9, "002 mContext.getContentResolver.query() out.");
                if (cursorQuery == null || !cursorQuery.moveToFirst()) {
                    strCreateClientId = null;
                    string = null;
                    str2 = null;
                    str3 = null;
                } else {
                    int columnIndex = cursorQuery.getColumnIndex(SharedPrefsProvider.NotifyClientLogContents.Setting.CONTROL_INFO);
                    if (columnIndex >= 0) {
                        string = cursorQuery.getString(columnIndex);
                        LogMgr.log(9, "003 : controlInfo = " + string);
                        int columnIndex2 = cursorQuery.getColumnIndex(SharedPrefsProvider.NotifyClientLogContents.Setting.CLIENT_ID);
                        if (columnIndex2 >= 0) {
                            strCreateClientId = cursorQuery.getString(columnIndex2);
                            LogMgr.log(9, "004 : clientId = " + strCreateClientId);
                            int columnIndex3 = cursorQuery.getColumnIndex(SharedPrefsProvider.NotifyClientLogContents.Setting.PROFILE_ID);
                            if (columnIndex3 >= 0) {
                                String string2 = cursorQuery.getString(columnIndex3);
                                LogMgr.log(9, "005 : profileId = " + string2);
                                int columnIndex4 = cursorQuery.getColumnIndex(SharedPrefsProvider.NotifyClientLogContents.Setting.URL_IN_PROFILE);
                                String string3 = columnIndex4 >= 0 ? cursorQuery.getString(columnIndex4) : null;
                                LogMgr.log(9, "006 : urlInProfile = " + string3);
                                str3 = string3;
                                str2 = string2;
                            } else {
                                throw new Exception("failed to get profileId");
                            }
                        } else {
                            throw new Exception("failed to get clientId");
                        }
                    } else {
                        throw new Exception("failed to get controlInfo");
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                if (string != null) {
                    try {
                        str4 = new ClientControlInfoJsonArray(string).getClientControlInfo().get(CLIENT_CONTROL_INFO_MASTER_KEY_RETRY_INTERVAL);
                    } catch (JSONException unused) {
                        throw new Exception("failed get client control information ");
                    }
                } else {
                    str4 = null;
                }
                String strCreateRequestId = SwsParamCreator.createRequestId();
                String strCreateDateTimeVal = createDateTimeVal();
                if (strCreateClientId == null) {
                    strCreateClientId = SwsParamCreator.createClientId();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SharedPrefsProvider.NotifyClientLogContents.Setting.CLIENT_ID, strCreateClientId);
                    LogMgr.log(9, "007 mContext.getContentResolver.insert() in.");
                    context.getContentResolver().insert(SharedPrefsProvider.getNotifyClientLogContentsUri(context), contentValues);
                    LogMgr.log(9, "008 mContext.getContentResolver.insert() out.");
                }
                String str5 = strCreateClientId;
                String clientVersion = LogUploader.getClientVersion(context);
                if (clientVersion.equals("")) {
                    throw new Exception("failed get semClientVersion ");
                }
                String semClientVersionAdditionalInfo = LogUploader.getSemClientVersionAdditionalInfo(context);
                try {
                    packageName = context.getApplicationContext().getPackageName();
                } catch (Exception unused2) {
                    LogMgr.log(1, "801 Exception occurred. Failed to get package name.");
                }
                return new Request(str4, strCreateRequestId, sepId, str, str5, clientVersion, semClientVersionAdditionalInfo, str2, str3, logInfoContent, packageName, strCreateDateTimeVal);
            } catch (Exception e2) {
                e = e2;
                cursor = cursorQuery;
                LogMgr.log(1, "800 Exception occurred.Exception.getMessage()=" + e.getMessage());
                throw e;
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }

        private static String createDateTimeVal() {
            String str;
            LogMgr.log(8, "000");
            try {
                FSC$$ExternalSyntheticApiModelOutline0.m$3();
                SimpleDateFormat simpleDateFormatM = FSC$$ExternalSyntheticApiModelOutline0.m(CURRENT_TIME_FORMAT, Locale.JAPAN);
                simpleDateFormatM.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_JST));
                str = simpleDateFormatM.format(new Date());
            } catch (Exception unused) {
                LogMgr.log(1, "800 Exception occurred. Failed to create datetime val.");
                str = "";
            }
            LogMgr.log(8, "999 datetime=" + str);
            return str;
        }

        public Request(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, LogInfoContent logInfoContent, String str10, String str11) {
            this.retryIntervalString = str;
            this.requestId = str2;
            this.sepId = str3;
            this.seInfo = str4;
            this.clientId = str5;
            this.semClientVersion = str6;
            this.semClientVersionAdditionalInfo = str7;
            this.profileId = str8;
            this.urlInProfile = str9;
            this.logInfoContent = logInfoContent;
            this.packageName = str10;
            this.datetime = str11;
        }

        public static final class LogInfoContent {
            public final String mAdditionalInfo;
            public final String mMessage;
            public final String mMessageCode;

            public LogInfoContent(String str, String str2, String str3) {
                this.mMessageCode = str;
                this.mMessage = str2;
                this.mAdditionalInfo = str3;
            }
        }
    }

    public LogUploader(OnUploadFinishListener onUploadFinishListener) {
        this.mListener = onUploadFinishListener;
    }

    public void start() {
        LogMgr.log(8, "000");
        if (this.mThreadHandler != null) {
            LogMgr.log(8, "998 already started.");
            return;
        }
        HandlerThread handlerThread = new HandlerThread("log-uploader-thread");
        handlerThread.start();
        this.mThreadHandler = new Handler(handlerThread.getLooper() != null ? handlerThread.getLooper() : Looper.getMainLooper());
        LogMgr.log(8, "999");
    }

    public void request(Request request) {
        LogMgr.log(8, "000");
        Handler handler = this.mThreadHandler;
        if (handler == null) {
            LogMgr.log(8, "998 not started.");
        } else {
            handler.post(new LogUploadTask(request));
            LogMgr.log(8, "999");
        }
    }

    public void shutdown() {
        LogMgr.log(8, "000");
        Handler handler = this.mThreadHandler;
        if (handler == null) {
            LogMgr.log(8, "998 not started.");
            return;
        }
        handler.getLooper().quitSafely();
        this.mThreadHandler = null;
        LogMgr.log(8, "999");
    }

    private class LogUploadTask implements Runnable {
        private static final String API_NAME = "notifyClientLog";
        private static final int MAX_CLIENT_CONTROL_INFO_VALUE_LENGTH = 1024;
        public static final String SWS_CONTENT_TYPE_REQ = "application/jose; charset=UTF-8";
        private static final String URL_API_NOTIFY_DEVICE_MESSAGE = "notifyClientLog";
        private static final String URL_PROTOCOL = "https://";
        private final Request mRequest;
        private final List<Integer> mRetryInterval = new ArrayList();

        LogUploadTask(Request request) {
            this.mRequest = request;
            setRetryInterval(request.retryIntervalString, FlavorConst.DEFAULT_RETRY_NOTIFY_CLIENT_LOG_INTERVAL);
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x004e A[LOOP:1: B:18:0x004c->B:19:0x004e, LOOP_END] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private void setRetryInterval(String str, int[] iArr) {
            int i;
            LogMgr.log(7, "000");
            this.mRetryInterval.clear();
            if (str != null && str.length() <= 1024) {
                StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    try {
                        i = Integer.parseInt(stringTokenizer.nextToken());
                    } catch (NumberFormatException unused) {
                        LogMgr.log(8, "001 Failed to parse int");
                        this.mRetryInterval.clear();
                    }
                    if (i >= 0) {
                        this.mRetryInterval.add(Integer.valueOf(i));
                    } else {
                        this.mRetryInterval.clear();
                        for (int i2 : iArr) {
                            this.mRetryInterval.add(Integer.valueOf(i2));
                        }
                    }
                }
            } else {
                while (i < r5) {
                }
            }
            LogMgr.log(7, "999 retryCount=" + this.mRetryInterval.size());
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        @Override // java.lang.Runnable
        public void run() {
            String str;
            String strProcess;
            boolean zEquals;
            String str2;
            String strRunSwsPost;
            NotifyClientLogResponseJson notifyClientLogResponseJson;
            LogMgr.log(8, "000");
            try {
                NotifyClientLogRequestJson notifyClientLogRequestJson = new NotifyClientLogRequestJson();
                notifyClientLogRequestJson.setRequestId(this.mRequest.requestId);
                notifyClientLogRequestJson.setClientId(this.mRequest.clientId);
                notifyClientLogRequestJson.setCurrentTime(this.mRequest.datetime);
                notifyClientLogRequestJson.setMessageCode(this.mRequest.logInfoContent.mMessageCode);
                notifyClientLogRequestJson.setMessage(this.mRequest.logInfoContent.mMessage);
                notifyClientLogRequestJson.setAdditionalInfo(this.mRequest.logInfoContent.mAdditionalInfo);
                LogProcessor logProcessor = new LogProcessor();
                logProcessor.loadWithCheck();
                strProcess = logProcessor.process(notifyClientLogRequestJson);
            } catch (JSONException e) {
                LogMgr.log(2, "702 JSONException: " + e.getMessage());
                str = "JSONException:" + e.getMessage();
            } catch (Exception e2) {
                LogMgr.log(2, "703 Exception: " + e2.getMessage());
                str = "Exception:" + e2.getMessage();
            }
            if (strProcess == null) {
                LogMgr.log(2, "700 processedLinageData is null");
                throw new JSONException("processedLinageData is null");
            }
            String strCreateLogUploadUri = createLogUploadUri(this.mRequest.urlInProfile);
            int size = this.mRetryInterval.size();
            Date date = new Date();
            if (LogUploader.MSG_CODE_LIST_OF_SENDING_LOG_REDUCTION.contains(this.mRequest.logInfoContent.mMessageCode) && date.getTime() % LogUploader.SENDING_LOG_REDUCTION_FREQUENCY != 0) {
                String str3 = "Not sending log, due to target of reduction. msgCode[" + this.mRequest.logInfoContent.mMessageCode + "]";
                LogMgr.log(8, str3);
                if (LogUploader.this.mListener != null) {
                    LogMgr.log(8, "Call onFinished callback.");
                    LogUploader.this.mListener.onFinished(this.mRequest.requestId, str3);
                }
                LogMgr.log(8, "999");
                return;
            }
            str = null;
            int i = 0;
            while (i <= size) {
                try {
                    strRunSwsPost = runSwsPost(strCreateLogUploadUri, strProcess, i);
                    notifyClientLogResponseJson = new NotifyClientLogResponseJson(strRunSwsPost);
                    notifyClientLogResponseJson.checkMembers();
                    zEquals = notifyClientLogResponseJson.getResultCode().equals("1000");
                } catch (HttpException e3) {
                    e = e3;
                    zEquals = false;
                }
                try {
                    if (!notifyClientLogResponseJson.isSuccess()) {
                        LogMgr.log(2, "700 ResponseJson:" + strRunSwsPost);
                    }
                    str2 = strRunSwsPost;
                } catch (HttpException e4) {
                    e = e4;
                    LogMgr.log(2, "701 : HttpException:" + e.getMessage());
                    str2 = "HttpException:" + e.getMessage();
                    if (e.getType() == 500) {
                        zEquals = true;
                    }
                }
                if (!zEquals) {
                    LogMgr.log(8, "001 there was no retry request");
                } else if (size < 1) {
                    LogMgr.log(8, "002 there was no retry settings");
                } else {
                    int i2 = i + 1;
                    if (i2 > size) {
                        LogMgr.log(8, "003 retry count was over");
                    } else {
                        SystemClock.sleep(this.mRetryInterval.get(i).intValue());
                        LogMgr.log(8, "004 (retry count / max retry count)=(" + i2 + DomExceptionUtils.SEPARATOR + size + ")");
                        i = i2;
                        str = str2;
                    }
                }
                str = str2;
                break;
            }
            if (LogUploader.this.mListener != null) {
                LogUploader.this.mListener.onFinished(this.mRequest.requestId, str);
            }
            LogMgr.log(8, "999");
        }

        private String createLogUploadUri(String str) {
            String str2 = this.mRequest.seInfo;
            StringBuilder sb = new StringBuilder(URL_PROTOCOL);
            if (str == null) {
                str = SettingInfo.getServerConnectionUrl();
            }
            sb.append(str);
            String string = sb.toString();
            if (str2 == null) {
                str2 = LogUploader.DUMMY_SE_ID;
            }
            return String.format("%s/cl/v1/%s/%s/%s", string, str2, SwsClient.URL_SERVICE_ID, "notifyClientLog");
        }

        private String runSwsPost(String str, String str2, int i) throws HttpException {
            SwsRequest swsRequest = new SwsRequest(SwsRequest.HTTP_METHOD_POST);
            swsRequest.setRequestData(str2);
            swsRequest.setRequestHeader(createRequestHeader(i));
            return run(str, swsRequest, new SwsResponse("notifyClientLog"));
        }

        private String createUserAgent() {
            String str = this.mRequest.sepId;
            if (this.mRequest.semClientVersionAdditionalInfo != null && !this.mRequest.semClientVersionAdditionalInfo.isEmpty()) {
                String str2 = this.mRequest.semClientVersion;
                String str3 = this.mRequest.semClientVersionAdditionalInfo;
                if (str == null) {
                    str = LogUploader.DUMMY_SEP_ID;
                }
                return String.format("SemClient/%s.%s (Android %s; %s; %s)", str2, str3, Build.VERSION.RELEASE, str, Build.MODEL);
            }
            String str4 = this.mRequest.semClientVersion;
            if (str == null) {
                str = LogUploader.DUMMY_SEP_ID;
            }
            return String.format("SemClient/%s (Android %s; %s; %s)", str4, Build.VERSION.RELEASE, str, Build.MODEL);
        }

        private SwsRequestHeader createRequestHeader(int i) {
            LogMgr.log(8, "000");
            SwsRequestHeader swsRequestHeader = new SwsRequestHeader();
            swsRequestHeader.setUserAgent(createUserAgent());
            swsRequestHeader.setContentType("application/jose; charset=UTF-8");
            swsRequestHeader.setClientId(this.mRequest.clientId);
            swsRequestHeader.setClientVersion(this.mRequest.semClientVersion);
            swsRequestHeader.setSemClientVersionAdditionalInfo(this.mRequest.semClientVersionAdditionalInfo);
            swsRequestHeader.setProfileId(this.mRequest.profileId);
            swsRequestHeader.setRetryCount(i);
            swsRequestHeader.setRemotelyStarted(false);
            swsRequestHeader.setPackageName(this.mRequest.packageName);
            LogMgr.log(8, "999");
            return swsRequestHeader;
        }

        private String run(String str, IRequest iRequest, IResponse iResponse) throws HttpException {
            runCommon(str, iRequest, iResponse);
            return iResponse.getData();
        }

        private void runCommon(String str, IRequest iRequest, IResponse iResponse) throws HttpException {
            HttpCommunicationAgent httpCommunicationAgent;
            LogMgr.log(6, "000");
            LogMgr.log(8, "001 uri= " + str);
            synchronized (this) {
                LogMgr.log(9, "001");
                httpCommunicationAgent = new HttpCommunicationAgent();
                try {
                } catch (Throwable th) {
                    LogMgr.log(9, "003");
                    throw th;
                }
            }
            try {
                httpCommunicationAgent.doTransaction(str, iRequest, iResponse);
                LogMgr.log(9, "003");
                LogMgr.log(6, "999");
            } catch (Exception e) {
                LogMgr.log(9, "002");
                throw e;
            }
        }
    }

    public boolean isLogUpload(int i) {
        LogMgr.log(8, "000 errorCode:" + i);
        boolean zContains = Arrays.asList(LOG_UPLOAD_LIST).contains(Integer.valueOf(i));
        LogMgr.log(8, "999 ret:" + zContains);
        return zContains;
    }

    public Request.LogInfoContent getLogInfoContent(Message.Api api, int i, String str, MessageCode.SendTiming sendTiming, MessageCode.Process process, Context context, String str2) {
        LogMgr.log(8, "000 message:" + str + " errorCode:" + i + " seId:" + str2);
        Request.LogInfoContent logInfoContentCreateLogInfoContent = createLogInfoContent(api, Message.ErrorType.ON_ERROR, Integer.toString(i), str == null ? "" : str, sendTiming, process, MessageCode.ErrorInfo.ISEM_CLIENT_ERROR_NOTIFICATION, context, str2);
        LogMgr.log(8, "999");
        return logInfoContentCreateLogInfoContent;
    }

    public static class Message {

        public enum Api {
            SEM_CONNECT("SemClient#connect"),
            SEM_STARTTSMSEQUENCE("SemClient#startTsmSequence"),
            SEM_GETINSTANCE("SemClient#getInstance"),
            SEM_NOTIFY_CLIENT_EVENT("SemClient#notifyClientEvent"),
            UNKNOWN("UNKNOWN");

            public final String name;

            Api(String str) {
                this.name = str;
            }
        }

        public enum ErrorType {
            ILLEGAL_ARGUMENT_EXCEPTION("IllegalArgumentException"),
            ILLEGAL_STATE_EXCEPTION("IllegalStateException"),
            SEM_CLIENT_EXCEPTION("SemClientException"),
            ON_ERROR(SemClientNotifyEventInfo.METHOD_NAME_ON_ERROR),
            OTHER_ERROR("OTHER_ERROR");

            public final String type;

            ErrorType(String str) {
                this.type = str;
            }
        }
    }

    public static class MessageCode {

        public enum SendTiming {
            ON_MESSAGE_RECEIVED("01"),
            ON_NEW_TOKEN_RECEIVED("02"),
            ROUTINE_WORK_SERVICE("03"),
            NOTIFY_CLIENT_EVENT_SERVICE("04");

            public final String code;

            SendTiming(String str) {
                this.code = str;
            }
        }

        public enum Process {
            SERVER_PROCESS("01"),
            GET_SEM_CLIENT_PROCESS("02"),
            CONNECT_PROCESS("03"),
            BUSINESS_PROCESS("04");

            public final String code;

            Process(String str) {
                this.code = str;
            }
        }

        public enum ErrorInfo {
            INTENT_IS_NULL("01"),
            BUNDLE_IS_EMPTY("02"),
            LINKAGE_DATA_IS_EMPTY("03"),
            EVENT_TYPE_IS_NOT_SP_APP_UNINSTALLED("04"),
            ACTION_IS_NOT_ON_MESSAGE_RECEIVED_NOR_ON_NEW_TOKEN_RECEIVED("05"),
            SEM_CLIENT_IS_NULL("01"),
            DUPLICATED_PACKAGE_NAME("02"),
            ILLEGAL_ARGUMENT_EXCEPTION("01"),
            ILLEGAL_STATE_EXCEPTION("02"),
            SEM_CLIENT_EXCEPTION("03"),
            ISEM_CLIENT_RESULT_IS_NULL("04"),
            ISEM_CLIENT_ERROR_NOTIFICATION("05"),
            ON_SERVICE_DISCONNECTED("06"),
            TIMEOUT("07");

            public final String code;

            ErrorInfo(String str) {
                this.code = str;
            }
        }
    }

    public static Request.LogInfoContent createLogInfoContent(Message.Api api, Message.ErrorType errorType, String str, String str2, MessageCode.SendTiming sendTiming, MessageCode.Process process, MessageCode.ErrorInfo errorInfo, Context context, String str3) {
        LogMgr.log(8, "000");
        String strCreateMessage = createMessage(api, errorType, str, str2);
        String strCreateMessageCode = createMessageCode(sendTiming, process, errorInfo, str);
        String strCreateAdditionalInfo = createAdditionalInfo(context, str3);
        LogMgr.log(8, "999");
        return new Request.LogInfoContent(strCreateMessageCode, strCreateMessage, strCreateAdditionalInfo);
    }

    private static String createMessage(Message.Api api, Message.ErrorType errorType, String str, String str2) {
        String str3;
        LogMgr.log(8, "000");
        String str4 = "";
        if (errorType != Message.ErrorType.OTHER_ERROR) {
            LogMgr.log(9, "001");
            str3 = errorType.type;
        } else {
            str3 = "";
        }
        if (errorType == Message.ErrorType.SEM_CLIENT_EXCEPTION || errorType == Message.ErrorType.ON_ERROR) {
            LogMgr.log(9, "002");
            str4 = "EC=" + str + ", ";
        }
        String hexString = api.name;
        if (!Message.Api.UNKNOWN.name.equals(hexString)) {
            hexString = Integer.toHexString(api.name.hashCode());
        }
        LogMgr.log(9, "003 name=" + hexString);
        LogMgr.log(8, "999");
        return hexString + ": " + str3 + "(" + str4 + "MSG=" + str2 + ")";
    }

    private static String createMessageCode(MessageCode.SendTiming sendTiming, MessageCode.Process process, MessageCode.ErrorInfo errorInfo, String str) {
        LogMgr.log(8, "000 errorCode=" + str);
        String str2 = sendTiming.code + process.code + errorInfo.code;
        if (str == null || str.isEmpty()) {
            LogMgr.log(8, "998 ret=" + str2 + SeInfo.SE_TYPE_00);
            return str2 + SeInfo.SE_TYPE_00;
        }
        try {
            if (errorInfo == MessageCode.ErrorInfo.ISEM_CLIENT_ERROR_NOTIFICATION) {
                str2 = str2 + convertErrorCode(Integer.parseInt(str));
            } else if (errorInfo == MessageCode.ErrorInfo.SEM_CLIENT_EXCEPTION) {
                str2 = str2 + convertSemClientExceptionErrorCode(Integer.parseInt(str));
            } else {
                str2 = str2 + SeInfo.SE_TYPE_00;
            }
        } catch (NumberFormatException unused) {
            str2 = str2 + SeInfo.SE_TYPE_00;
        }
        LogMgr.log(8, "999 messageCode=" + str2);
        return str2;
    }

    private static String createAdditionalInfo(Context context, String str) {
        LogMgr.log(8, "000");
        String clientVersion = getClientVersion(context);
        String chipType = AccessConfig.getChipType();
        LogMgr.log(8, "999");
        return Build.VERSION.RELEASE + ", " + clientVersion + ", " + chipType + ", " + str;
    }

    public static String convertErrorCode(int i) {
        String str;
        LogMgr.log(8, "000 errorCode=" + i);
        if (i == 100) {
            str = "04";
        } else if (i == 102) {
            str = "02";
        } else if (i == 300) {
            str = "08";
        } else if (i == 900) {
            str = "01";
        } else {
            switch (i) {
                case 200:
                    str = "06";
                    break;
                case 201:
                    str = "03";
                    break;
                case 202:
                    str = "05";
                    break;
                case 203:
                    str = "07";
                    break;
                default:
                    str = SeInfo.SE_TYPE_00;
                    break;
            }
        }
        LogMgr.log(8, "999 result=".concat(str));
        return str;
    }

    public static String convertSemClientExceptionErrorCode(int i) {
        if (i == 500) {
            return "06";
        }
        if (i == 501) {
            return "07";
        }
        if (i == 503) {
            return "08";
        }
        if (i == 900) {
            return "03";
        }
        if (i != 901) {
            switch (i) {
                case 101:
                    return "01";
                case 102:
                    return "04";
                case 103:
                    return "02";
                default:
                    return SeInfo.SE_TYPE_00;
            }
        }
        return "05";
    }

    public static String getClientVersion(Context context) {
        LogMgr.log(8, "000");
        String string = context.getResources().getString(context.getResources().getIdentifier(SemClientConst.SEM_CLIENT_VERSION_RESOURCE_NAME, "string", context.getPackageName()));
        LogMgr.log(8, "999 clientVersion=" + string);
        return string;
    }

    public static String getSemClientVersionAdditionalInfo(Context context) {
        LogMgr.log(8, "000");
        String string = context.getResources().getString(context.getResources().getIdentifier(SemClientConst.SEM_CLIENT_VERSION_ADD_INFO_RESOURCE_NAME, "string", context.getPackageName()));
        LogMgr.log(8, "999 semClientVersionAdditionalInfo=" + string);
        return string;
    }

    public static String getSepId() {
        LogMgr.log(8, "000");
        String chipIssuerId = AccessConfig.getChipIssuerId();
        LogMgr.log(8, "999 sepId=" + chipIssuerId);
        return chipIssuerId;
    }
}
