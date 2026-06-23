package com.felicanetworks.mfc.mfi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MfiClientConst {
    public static final String ACCOUNT_ISSUER_GOOGLE = "Google";
    public static final List<String> ACCOUNT_ISSUER_LIST;
    public static final int ACTION_TYPE_DELETE_CARDS = 2;
    public static final int ACTION_TYPE_DELETE_SYSTEM = 4;
    public static final int ACTION_TYPE_INITIALIZE_CHIP = 3;
    public static final int ACTION_TYPE_INVALID = -99;
    public static final int ACTION_TYPE_PERMANENT_DELETE_CARDS = 5;
    public static final int ACTION_TYPE_REISSUE_CARDS = 1;
    public static final int ACTION_TYPE_RESET_CHIP = 6;
    public static final String ALL_FF_CID_INSTANCE_KEY = "ALL_FF";
    public static final String CLIENT_APP_NAME = "MFIClient";
    public static final String DEVICE_PLATFFORM_NAME = "Android";
    public static final String EVENT_MSG_EMPTY_PERMIT_LIST = "The permitList in server response is empty.";
    public static final String EVENT_MSG_INVALID_PERMIT_LIST = "The permitList in server response is invalid.";
    public static final String EVENT_MSG_PERMIT_LIST_PARSE_ERROR = "Failed to parse permitList.";
    public static final int EVENT_TYPE_INTERRUPTED_ERROR = 104;
    public static final int EVENT_TYPE_PROTOCOL_ERROR = 103;
    public static final int EVENT_TYPE_UNKNOWN_ERROR = 100;
    public static final String EXC_INVALID_ACCOUNT_ISSUER = "The specified AccountIssuer is invalid.";
    public static final String EXC_INVALID_ACCOUNT_NAME = "The specified AccountName is null.";
    public static final String EXC_INVALID_ACCOUNT_NAME_EMPTY = "The specified AccountName is empty.";
    public static final String EXC_INVALID_ACTION_TYPE = "The specified actionType is null or invalid.";
    public static final String EXC_INVALID_BLOCK_DATA_LIST = "The specified BlockDataList is null or empty.";
    public static final String EXC_INVALID_BLOCK_LIST = "The specified BlockList is null or empty.";
    public static final String EXC_INVALID_CALLBACK = "The specified Callback is null.";
    public static final String EXC_INVALID_CALLER_PACKAGE_NAME = "The specified parameter is invalid.";
    public static final String EXC_INVALID_CARD = "The specified Card is null or invalid.";
    public static final String EXC_INVALID_CID_LIST = "The specified cidList is null or invalid.";
    public static final String EXC_INVALID_COMMAND = "The specified Command is null or invalid size.";
    public static final String EXC_INVALID_LAYOUT_TYPE = "The specified LayoutType is unexpected value.";
    public static final String EXC_INVALID_LINKAGE_DATA = "The specified LinkageData is null or invalid.";
    public static final String EXC_INVALID_LISTENER = "The specified parameter is invalid.";
    public static final String EXC_INVALID_MESSAGE_ID = "The specified messageId is null or invalid.";
    public static final String EXC_INVALID_NODECODESIZE = "The specified NodeCodeSize is invalid value.";
    public static final String EXC_INVALID_NODE_CODE_LIST = "The specified parameter is invalid.";
    public static final String EXC_INVALID_OPERATION_ID = "The specified operationId is null or invalid.";
    public static final String EXC_INVALID_OTP = "The specified Otp is null or invalid.";
    public static final String EXC_INVALID_PRIVACY_SETTING_DATA_LIST = "The specified parameter is invalid.";
    public static final String EXC_INVALID_SERVICE_ID = "The specified ServiceId is null or invalid.";
    public static final String EXC_INVALID_SYSTEM_CODE = "The specified System Code is out of range.";
    public static final String EXC_INVALID_TARGET = "The specified Target is invalid value.";
    public static final String EXC_RUNTIME_IN_FSC = "java.lang.RuntimeException: A runtime exception was thrown within FSC.";
    public static final int IDENTIFIED_SERVICE_1 = 1;
    public static final int IDENTIFIED_SERVICE_2 = 2;
    public static final int IDENTIFIED_SERVICE_NONE = 0;
    public static final int IDENTIFIED_SERVICE_UNKNOWN = -1;
    public static final int INDEX_SERVICE_ID_IN_CID = 7;
    public static final String INDIVIDUAL_SP_INNER_SERVICE_TYPE = "MFPLU100";
    public static final int INIT_STATUS_INITIALIZED = 1;
    public static final int INIT_STATUS_NEED_TO_CLEAR_MEMORY = 3;
    public static final int INIT_STATUS_NEED_TO_INITIALIZE = 2;
    public static final int INTERFACE_WIRED = 0;
    public static final int INTERFACE_WIRELESS = 1;
    public static final int LAYOUT_TYPE_SIGN_IN_ONLY = 3;
    public static final int LAYOUT_TYPE_SIGN_IN_WITH_TERMS = 1;
    public static final int LAYOUT_TYPE_SKIPPABLE_SIGN_IN = 2;
    public static final int LEN_CID = 63;
    public static final int LEN_MIGRATE_CARD_IDM = 16;
    public static final int LEN_SERVICE_ID = 8;
    public static final String MFC_VERSION_FAVER3 = "3.0.0";
    public static final int NODECODE_NOT_EXISTS = 65535;
    public static final int NODE_CODE_AREA0 = 0;
    public static final int NODE_CODE_SYSTEM = 65535;
    public static final int NO_AREA_CODE = -1;
    public static final String NO_CID_INSTANCE_KEY = "ALL_00";
    public static final String PIPE_IO_EXCEPTION_MESSAGE_INTERRUPTED = "MFI_INTERRUPTED";
    public static final String PIPE_IO_EXCEPTION_MESSAGE_STOPPED = "MFI_STOPPED";
    public static final String REGEX_ALPHANUMERIC = "[0-9a-zA-Z]*";
    public static final int SLOT_STATUS_EMPTY = 0;
    public static final int SLOT_STATUS_INSUFFICIENT_ALLOCATED_FREE_SPACE = 2;
    public static final int SLOT_STATUS_NOT_EMPTY = 1;

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("Google");
        ACCOUNT_ISSUER_LIST = Collections.unmodifiableList(arrayList);
    }
}
