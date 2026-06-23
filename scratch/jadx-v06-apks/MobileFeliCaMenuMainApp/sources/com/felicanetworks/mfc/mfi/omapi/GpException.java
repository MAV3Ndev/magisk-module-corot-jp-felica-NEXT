package com.felicanetworks.mfc.mfi.omapi;

import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class GpException extends Exception {
    private static final long serialVersionUID = -1689480856745547994L;
    private String mErrorMessage;
    private String mFwsMessage;
    private byte[] mResponse;
    private int mType;

    public interface FwsErrorMsg {
        public static final String FORMAT_ERROR = "FormatError";
        public static final String NO_READER = "NoReader";
        public static final String NO_SESSION = "NoSession";
        public static final String NULL = "Null";
        public static final String RESPONSE_TOO_LONG = "ResponseTooLong";
        public static final String SW = "SW=";
        public static final String TIMEOUT = "TimeOut";
        public static final String UNKNOWN_ERROR = "UnknownError";
    }

    public interface OmapiName {
        public static final String CHANNEL_GET_SELECT_RESPONSE = "Channel#getSelectResponse";
        public static final String CHANNEL_SELECT_NEXT = "Channel#selectNext";
        public static final String CHANNEL_TRANSMIT = "Channel#transmit";
        public static final String READER_IS_SECUREELEMENT_PRESENT = "Reader#isSecureElementPresent";
        public static final String READER_OPENSESSION = "Reader#openSession";
        public static final String SESERVICE_GETREADERS = "SEService#getReaders";
        public static final String SESERVICE_SESERVICE = "SEService#SEService";
        public static final String SESSION_OPEN_BASIC_CHANNEL = "Session#openBasicChannel";
        public static final String SESSION_OPEN_LOGICAL_CHANNEL = "Session#openLogicalChannel";
    }

    public GpException(int i, String str, String str2) {
        this.mResponse = null;
        LogMgr.log(5, "000");
        LogMgr.log(6, "001 : type = " + i);
        LogMgr.log(6, "002 : message = " + str);
        LogMgr.log(6, "003 : fwsMessage = " + str2);
        this.mType = i;
        this.mErrorMessage = str;
        this.mFwsMessage = str2;
        LogMgr.log(5, "999");
    }

    public GpException(int i, String str, String str2, byte[] bArr) {
        this.mResponse = null;
        LogMgr.log(5, "000");
        LogMgr.log(6, "001 : type = " + i);
        LogMgr.log(6, "002 : message = " + str);
        LogMgr.log(6, "003 : fwsMessage = " + str2);
        LogMgr.logArray(6, this.mResponse);
        this.mType = i;
        this.mErrorMessage = str;
        this.mFwsMessage = str2;
        this.mResponse = bArr;
        LogMgr.log(5, "999");
    }

    public int getType() {
        return this.mType;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return this.mErrorMessage;
    }

    public String getFwsMessage() {
        return this.mFwsMessage;
    }

    public byte[] getResponse() {
        return this.mResponse;
    }
}
