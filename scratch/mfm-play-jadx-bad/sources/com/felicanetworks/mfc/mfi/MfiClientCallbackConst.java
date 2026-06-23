package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public class MfiClientCallbackConst {
    public static final String LOCAL_ENGLISH = "eng";
    public static final String LOCAL_JAPANESE = "jpn";
    public static final String MSG_COMMUNICATION_ERROR = "Communication initiation error.";
    public static final String MSG_DEVICE_IDENTIFICATION_DATA_NULL = "device identification data is null.";
    public static final String MSG_FMT_FWS_ERROR = "%s:i%s:s%s";
    public static final String MSG_FMT_FWS_ERROR_WITH_RESULT_CODE = "%s:i%s:s%s:r%s:m%s";
    public static final String MSG_FMT_OP_ERROR = "OpenID Provider: %s";
    public static final String MSG_FMT_OP_ERROR_MSG_ONLY = "Result: %s";
    public static final String MSG_FMT_OP_ERROR_WITH_MSG = "OpenID Provider: %s ,Result: %s";
    public static final String MSG_FORMAT_ERROR = "Packet format error.";
    public static final String MSG_INITIALIZED_ERROR = "Already initialized error.";
    public static final String MSG_INVALID_CONTENT_TYPE = "Invalid content-type: ";
    public static final String MSG_INVALID_CONTENT_TYPE_NULL = "Invalid content-type: null";
    public static final String MSG_INVALID_RESPONSE_CODE = "Invalid response code:";
    public static final String MSG_NOT_SUPPORTED_CHIP_ERROR = "Not supported chip error.";
    public static final String MSG_UNKNOWN_ERROR = "Unknown error.";
    public static final int TYPE_ACCESS_FAILED = 228;
    public static final int TYPE_AGREEMENT_NOT_ACCEPT = 218;
    public static final int TYPE_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE = 247;
    public static final int TYPE_CARD_NOT_CACHED = 157;
    public static final int TYPE_CARD_NOT_EXIST = 208;
    public static final int TYPE_CARD_NOT_UNIQUE = 214;
    public static final int TYPE_EXIST_UNKNOWN_CARD = 212;
    public static final int TYPE_EXPIRED_MFI = 207;
    public static final int TYPE_FELICA_NOT_AVAILABLE = 55;
    public static final int TYPE_GET_DEVICE_IDENTIFICATION_DATA_ERROR = 238;
    public static final int TYPE_HTTP_COMMUNICATION_ERROR = 205;
    public static final int TYPE_HTTP_ERROR = 203;
    public static final int TYPE_IDM_NOT_EXIST = 241;
    public static final int TYPE_ILLEGAL_CARD_OPERATION = 158;
    public static final int TYPE_ILLEGAL_LINKAGE_DATA = 209;
    public static final int TYPE_INITIALIZED_ERROR = 224;
    public static final int TYPE_INITIALIZE_UNAVAILABLE = 239;
    public static final int TYPE_INSIDE_TRANSIT_GATE_ERROR = 245;
    public static final int TYPE_INSTANCE_NOT_VACANT = 237;
    public static final int TYPE_INSUFFICIENT_ALLOCATED_FREE_SPACE = 242;
    public static final int TYPE_INSUFFICIENT_CHIP_SPACE = 227;
    public static final int TYPE_INTERRUPTED_ERROR = 215;
    public static final String TYPE_INTERRUPTED_ERROR_NAME = "TYPE_INTERRUPTED_ERROR";
    public static final int TYPE_INVALID_CODE_ERROR = 221;
    public static final int TYPE_INVALID_ISSUE_INFORMATION = 231;
    public static final int TYPE_INVALID_LINKAGE_DATA = 210;
    public static final int TYPE_INVALID_MESSAGE_ID = -1;
    public static final int TYPE_INVALID_OTP = 232;
    public static final int TYPE_INVALID_REQUEST_TOKEN = 226;
    public static final int TYPE_INVALID_RESPONSE = 6;
    public static final int TYPE_ISSUE_LIMIT_EXCEEDED = 213;
    public static final int TYPE_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED = 234;
    public static final int TYPE_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED_BY_MFI = 243;
    public static final int TYPE_ISSUE_LIMIT_PER_DEVICE_EXCEEDED = 235;
    public static final int TYPE_MEMORY_CLEAR_IN_PROGRESS = 177;
    public static final int TYPE_MFI_TOS_NOT_AGREED = 246;
    public static final int TYPE_NOT_IC_CHIP_FORMATTING = 31;
    public static final int TYPE_NOT_REISSUABLE = 233;
    public static final int TYPE_NOT_SUPPORTED_CARD_OPERATION_ERROR = 244;
    public static final int TYPE_NOT_SUPPORTED_CHIP_ERROR = 223;
    public static final int TYPE_NOT_SUPPORTED_DEVICE_ERROR = 240;
    public static final int TYPE_OPEN_FAILED = 8;
    public static final int TYPE_OPSRV_ACCOUNT_ERROR = 216;
    public static final int TYPE_OPSRV_REQUIRED_LIB_UNAVAILABLE = 219;
    public static final int TYPE_OPSRV_RESULT_ERROR = 217;
    public static final int TYPE_OTHER_SP_CARD_EXIST = 236;
    public static final int TYPE_PLAY_INTEGRITY_NONRECOVERABLE_FAILED = 251;
    public static final int TYPE_PLAY_INTEGRITY_RECOVERABLE_FAILED = 250;
    public static final int TYPE_PLAY_INTEGRITY_REQUEST_UPDATE_PLAYSERVICE = 248;
    public static final int TYPE_PLAY_INTEGRITY_REQUEST_UPDATE_PLAYSTORE = 249;
    public static final int TYPE_PROTOCOL_ERROR = 202;
    public static final String TYPE_PROTOCOL_ERROR_NAME = "TYPE_PROTOCOL_ERROR";
    public static final int TYPE_READ_CONDITION_ERROR = 229;
    public static final int TYPE_SERVER_CANNOT_RESPOND_ERROR = 204;
    public static final int TYPE_SERVER_GENERAL_ERROR = 206;
    public static final int TYPE_SE_ACCESS_ERROR = 225;
    public static final int TYPE_SIGN_IN_REQUIRED = 222;
    public static final int TYPE_UNACCEPTABLE_CARD_STATUS = 211;
    public static final int TYPE_UNKNOWN_CHIP_STATE = 178;
    public static final int TYPE_UNKNOWN_ERROR = 200;

    public enum Api {
        FWS_LOGIN("FWS::login"),
        FWS_LOGOUT("FWS::logout"),
        FWS_GET_PERMIT_LIST("FWS::getPermitList"),
        FWS_GET_CARD_LIST("FWS::getCardList"),
        FWS_CHECK_CARD_INFORMATION("FWS::checkCardInformation"),
        FWS_CREATE_CARD("FWS::createCard"),
        FWS_GET_ISSUE_SCRIPT("FWS::getIssueScript"),
        FWS_GET_DELETE_SCRIPT("FWS::getDeleteScript"),
        FWS_GET_ENABLE_SCRIPT("FWS::getEnableScript"),
        FWS_GET_DISABLE_SCRIPT("FWS::getDisableScript"),
        FWS_INITIALIZE_SCRIPT("FWS::getInitializeScript"),
        FWS_GET_ACCESS_SCRIPT("FWS::getAccessScript"),
        FWS_GET_LINKAGEDATA_SCRIPT("FWS::getLinkageDataList"),
        FWS_GET_CONTENT("FWS::getContent"),
        FWS_UPDATE_DEVICE_REGISTRATION_TOKEN("FWS::updateDeviceRegistrationToken"),
        FWS_REQUEST_PUSHED_OPERATION("FWS::requestPushedOperation"),
        FWS_CHECK_EXISTS_MANAGEMENT_CARD("FWS::checkExistsManagementCard"),
        FWS_GET_UPLOAD_TARGET("FWS::getUploadTarget"),
        FWS_GET_UNIQUE_VALUE("FWS::getUniqueValue"),
        FWS_GET_RESET_SCRIPT("FWS::getResetScript"),
        FELICA_OPEN("Felica#open()"),
        FELICA_SELECT("Felica#select(int)"),
        FELICA_GET_CONTAINER_ISSUE_INFO("Felica#getContainerIssueInformation()"),
        FELICA_GET_KEY_VERSION("Felica#getKeyVersion(int)"),
        FELICA_READ("Felica#read(BlockList)"),
        FELICA_GET_CONTAINER_ID("Felica#getContainerId()"),
        FELICA_GET_KEY_VERSION_V2("Felica#getKeyVersionV2(int[])"),
        FELICA_GET_SYSTEM_CODE_LIST("Felica#getSystemCodeList()"),
        FELICA_GET_NODE_INFORMATION("Felica#getNodeInformation"),
        FELICA_GET_BLOCK_COUNT_INFORMATION("Felica#getBlockCountInformation"),
        FELICA_SELECT_CID("Felica#select(int, String)");

        public final String msg;

        Api(String msg) {
            this.msg = Integer.toHexString(msg.hashCode());
        }
    }
}
