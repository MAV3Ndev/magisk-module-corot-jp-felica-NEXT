package com.felicanetworks.mfc.mfi.fws;

/* JADX INFO: loaded from: classes.dex */
public class FwsConst {
    public static final int AGREED_TOS_VER = 2;
    public static final String RESULT_ACCESS_FAILED = "5064";
    public static final String RESULT_CODE_CONTINUE = "0010";
    public static final String RESULT_CODE_RETRYREQUEST = "1000";
    public static final String RESULT_CODE_SUCCESS = "0000";
    public static final String RESULT_CODE_TIMEDRETRYREQUEST = "1001";
    public static final String RESULT_CODE_TYPE_PTN_SUCCESS = "^0...$";
    public static final String RESULT_CONGESTED = "9005";
    public static final String RESULT_EXIST_UNKNOWN_CARD = "5061";
    public static final String RESULT_EXPIRED_LINKAGE_DATA = "3002";
    public static final String RESULT_EXPIRED_LOGIN_TOKEN_ID = "3001";
    public static final String RESULT_FAILED = "4000";
    public static final String RESULT_ILLEGAL_ACCESS_TOKEN = "2010";
    public static final String RESULT_ILLEGAL_ARGUMENTS = "2000";
    public static final String RESULT_ILLEGAL_LINKAGE_DATA = "2011";
    public static final String RESULT_ILLEGAL_REQUEST_TOKEN = "2012";
    public static final String RESULT_ILLEGAL_URL = "2001";
    public static final String RESULT_INITIALIZED = "5071";
    public static final String RESULT_INITIALIZE_UNAVAILABLE = "5081";
    public static final String RESULT_INSUFFICIENT_CHIP_SPACE = "5067";
    public static final String RESULT_INTERRUPTED = "5041";
    public static final String RESULT_INVALID_ACCESS_TOKEN = "3000";
    public static final String RESULT_INVALID_ADDITIONAL_INFORMATION = "5007";
    public static final String RESULT_INVALID_ADDRESS_OR_SSL = "9003";
    public static final String RESULT_INVALID_AUTH_CODE = "3100";
    public static final String RESULT_INVALID_ISSUE_INFORMATION = "5003";
    public static final String RESULT_INVALID_KEY_VERSION = "5080";
    public static final String RESULT_INVALID_MESSAGE_ID = "3004";
    public static final String RESULT_INVALID_OTP = "5006";
    public static final String RESULT_INVALID_REQUEST_TOKEN = "3003";
    public static final String RESULT_ISSUE_LIMIT_EXCEEDED = "5062";
    public static final String RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED = "5065";
    public static final String RESULT_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED_BY_MFI = "5087";
    public static final String RESULT_ISSUE_LIMIT_PER_DEVICE_EXCEEDED = "5066";
    public static final String RESULT_MEMORY_CLEARING = "5070";
    public static final String RESULT_NOT_ACTIVE_CARD = "5000";
    public static final String RESULT_NOT_EXIST_CARD = "5001";
    public static final String RESULT_NOT_ISSUABLE_CHIP = "5069";
    public static final String RESULT_NOT_REISSUABLE = "5039";
    public static final String RESULT_NOT_SUPPORTED_DEVICE = "5082";
    public static final String RESULT_NOT_WHITELISTED = "5049";
    public static final String RESULT_OTHER_SP_CARD_EXIST = "5068";
    public static final String RESULT_READ_CONDITION_ERROR = "5051";
    public static final String RESULT_SYSTEM_ERROR = "9001";
    public static final String RESULT_SYSTEM_SUSPENDED = "9000";
    public static final String RESULT_UNKNOWN = "4001";
    public static final String RESULT_USED_LINKAGE_DATA = "5002";
    public static final String RESULT_USER_INFORMATION_VERIFICATION_FAILED = "5045";

    public static class ActionType {
        public static final String DELETE_CARDS = "DeleteCards";
        public static final String DELETE_CARDS_FOR_PUSH = "DeleteCardsForPush";
        public static final String MIGRATE_CARD = "MigrateCard";
        public static final String SE_ACCESS_FOR_PUSH = "SeAccessForPush";
    }
}
