package com.felicanetworks.semc.omapi;

import com.felicanetworks.semc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class GpException extends Exception {
    private static final long serialVersionUID = -1689480856745547994L;
    private String mApduResultCode;
    private String mErrorMessage;
    private boolean mIsSwCodeError;
    private byte[] mResponse;
    private String mSwsMessage;
    private int mType;

    public interface ErrorApduResultCode {
        public static final String ILLEGAL_PARAM_ERROR = "2000";
        public static final String SECURITY_ERROR = "3001";
        public static final String SEND_AND_RECEIVE_FAILED = "4001";
        public static final String SEND_CANCEL = "4000";
        public static final String STATUS_ERROR = "3000";
        public static final String SYSTEM_ERROR = "9001";
    }

    public interface OmapiName {
        public static final String CHANNEL_GET_SELECT_RESPONSE = "Channel#getSelectResponse";
        public static final String CHANNEL_SELECT_NEXT = "Channel#selectNext";
        public static final String CHANNEL_TRANSMIT = "Channel#transmit";
        public static final String READER_IS_SECURE_ELEMENT_PRESENT = "Reader#isSecureElementPresent";
        public static final String READER_OPEN_SESSION = "Reader#openSession";
        public static final String SESSION_OPEN_BASIC_CHANNEL = "Session#openBasicChannel";
        public static final String SESSION_OPEN_LOGICAL_CHANNEL = "Session#openLogicalChannel";
        public static final String SE_SERVICE_GET_READERS = "SEService#getReaders";
        public static final String SE_SERVICE_SE_SERVICE = "SEService#SEService";
    }

    public interface SwsErrorMsg {
        public static final String FORMAT_ERROR = "FormatError";
        public static final String NO_READER = "NoReader";
        public static final String NO_SESSION = "NoSession";
        public static final String NULL = "Null";
        public static final String RESPONSE_TOO_LONG = "ResponseTooLong";
        public static final String SW = "SW=";
        public static final String TIMEOUT = "TimeOut";
        public static final String UNKNOWN_ERROR = "UnknownError";
    }

    public GpException(int i, String str, String str2) {
        this.mIsSwCodeError = false;
        this.mResponse = null;
        LogMgr.log(7, "000");
        LogMgr.log(8, "001 : type = " + i);
        LogMgr.log(8, "002 : message = " + str);
        LogMgr.log(8, "003 : swsMessage = " + str2);
        this.mType = i;
        this.mErrorMessage = str;
        this.mSwsMessage = str2;
        LogMgr.log(7, "999");
    }

    public GpException(int i, String str, String str2, String str3) {
        this.mIsSwCodeError = false;
        this.mResponse = null;
        LogMgr.log(7, "000");
        LogMgr.log(8, "001 : type = " + i);
        LogMgr.log(8, "002 : message = " + str);
        LogMgr.log(8, "003 : swsMessage = " + str2);
        LogMgr.log(8, "004 : apduResultCode = " + str3);
        this.mType = i;
        this.mErrorMessage = str;
        this.mSwsMessage = str2;
        this.mApduResultCode = str3;
        LogMgr.log(7, "999");
    }

    public GpException(int i, String str, String str2, byte[] bArr) {
        this.mIsSwCodeError = false;
        this.mResponse = null;
        LogMgr.log(7, "000");
        LogMgr.log(8, "001 : type = " + i);
        LogMgr.log(8, "002 : message = " + str);
        LogMgr.log(8, "003 : swsMessage = " + str2);
        LogMgr.logArray(8, this.mResponse);
        this.mType = i;
        this.mErrorMessage = str;
        this.mSwsMessage = str2;
        this.mResponse = bArr;
        LogMgr.log(7, "999");
    }

    public GpException(int i, String str, String str2, boolean z, byte[] bArr) {
        this.mIsSwCodeError = false;
        this.mResponse = null;
        LogMgr.log(7, "000");
        LogMgr.log(8, "001 : type = " + i);
        LogMgr.log(8, "002 : message = " + str);
        LogMgr.log(8, "003 : swsMessage = " + str2);
        LogMgr.log(8, "004 : isSwCodeErr = " + z);
        LogMgr.logArray(8, this.mResponse);
        this.mType = i;
        this.mErrorMessage = str;
        this.mSwsMessage = str2;
        this.mIsSwCodeError = z;
        this.mResponse = bArr;
        LogMgr.log(7, "999");
    }

    public int getType() {
        return this.mType;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.mErrorMessage;
    }

    public String getSwsMessage() {
        return this.mSwsMessage;
    }

    public boolean getIsSwCodeError() {
        return this.mIsSwCodeError;
    }

    public byte[] getResponse() {
        return this.mResponse;
    }

    public String getApduResultCode() {
        return this.mApduResultCode;
    }
}
