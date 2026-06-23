package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public class MfiClientExternalLogConst {
    public static final String MSG_FORMAT_FELICA_EXCEPTION = "%s: %s(ID=%d, TYPE=%d)";
    public static final String MSG_FORMAT_ON_ACTIVITY_RESULT = "Activity: onActivityResult(ResultCode=%d)";
    public static final String MSG_FORMAT_ON_ERROR = "%s: onError(TYPE=%d)";
    public static final String MSG_FORMAT_ON_REQUEST_ACTIVITY = "%s: Request activity";
    public static final String MSG_FORMAT_OTHER_EXCEPTION = "%s: %s";
    public static final String TAG_INFO = "[MFIC_I]";
    public static final String TAG_WARN = "[MFIC_W]";

    public enum Keys {
        NAME_MFIC_CONFIG("mficConfig"),
        NAME_INDIVIDUAL_CARDS("individualCards"),
        NAME_SERVICE_ID("serviceId");

        public final String keyName;

        Keys(String keyName) {
            this.keyName = keyName;
        }

        public String getKeyHash() {
            return Integer.toHexString(this.keyName.hashCode());
        }
    }

    public enum MficApi {
        CARD_ACCESS("Card", "access"),
        CARD_DELETE("Card", "delete"),
        CARD_DISABLE("Card", "disable"),
        CARD_ENABLE("Card", "enable"),
        MFI_ADMIN_GET_LINKAGE_DATA_LIST("MfiAdmin", "getLinkageDataList"),
        MFI_ADMIN_INITIALIZE("MfiAdmin", "initialize"),
        MFI_ADMIN_CHECK_CHIP_FORMATTING("MfiAdmin", "checkChipFormatting"),
        MFI_ADMIN_GET_LOCAL_CID_LIST("MfiAdmin", "getLocalCidList"),
        MFI_ADMIN_GET_REMAINED_CARDS("MfiAdmin", "getRemainedCards"),
        MFI_ADMIN_CLEAR_MEMORY("MfiAdmin", "clearMemory"),
        MFI_ADMIN_CHECK_AND_RECOVER_CRS_STATE("MfiAdmin", "checkAndRecoverCrsState"),
        MFI_CLIENT_CANCEL_CARD_OPERATION("MfiClient", "cancelCardOperation"),
        MFI_CLIENT_CANCEL_MFI_OFFLINE("MfiClient", "cancelMfiOffline"),
        MFI_CLIENT_CLEAR_MFI_ACCOUNT("MfiClient", "clearMfiAccount"),
        MFI_CLIENT_GET_CURRENT_ACCOUNT_HASH("MfiClient", "getCurrentAccountHash"),
        MFI_CLIENT_GET_LOCAL_CID_LIST("MfiClient", "getLocalCidList"),
        MFI_CLIENT_GET_LOCAL_PARTIAL_CARD_INFO_LIST("MfiClient", "getLocalPartialCardInfoList"),
        MFI_CLIENT_GET_SE_INFORMATION("MfiClient", "getSeInformation"),
        MFI_CLIENT_GET_UNSUPPORT_MFI_SERVICE1_CARD_POSITION("MfiClient", "getUnsupportMfiService1CardPosition"),
        MFI_CLIENT_IDENTIFY_SERVICE("MfiClient", "identifyService"),
        MFI_CLIENT_SILENT_START("MfiClient", LogSender.EXTRA_VALUE_EVENT_NAME_SILENT_START),
        MFI_CLIENT_START("MfiClient", LogSender.EXTRA_VALUE_EVENT_NAME_START),
        MFI_CLIENT_STOP("MfiClient", "stop"),
        MFI_CLIENT_PROVISION_SERVER_OPERATION("MfiClient", "provisionServerOperation"),
        MFI_CLIENT_EXIST_UNSUPPORT_MFI_SERVICE1_CARD("MfiClient", "existUnsupportMfiService1Card"),
        MFI_CLIENT_EXIST_EMPTY_SLOT("MfiClient", "existEmptySlot"),
        MFI_CLIENT_DELETE_UNSUPPORT_MFI_SERVICE1_CARD("MfiClient", "deleteUnsupportMfiService1Card"),
        MFI_CLIENT_GET_CACHED_CARD_LIST("MfiClient", "getCachedCardList"),
        MFI_CLIENT_IS_LOGINED_ACCOUNT("MfiClient", "isLoginedAccount"),
        MFI_CLIENT_EXIST_SERVICE("MfiClient", "existService"),
        MFI_CLIENT_GET_MFI_TOS_DATA("MfiClient", "getMfiTosData"),
        MFI_CLIENT_SET_MFI_TOS_AGREEMENT("MfiClient", "setMfiTosAgreement"),
        MFI_CLIENT_GET_GOOGLE_TOS("MfiClient", "getGoogleTos"),
        CACHED_CARD_ENABLE("CacheCard", "enable"),
        USER_GET_CARD_ADDITIONAL_INFO_LIST("User", "getCardAdditionalInfoList"),
        USER_GET_CARD_INFO_LIST_WITH_SP_STATUS("User", "getCardInfoListWithSpStatus"),
        USER_GET_CARD_LIST("User", "getCardList"),
        USER_ISSUE_CARD("User", "issueCard"),
        MFI_ADMIN_IS_CHIP_INITIALIZED("MfiAdmin", "isChipInitialized"),
        REMAINED_CARDS_UPLOAD_CARDS_TO_DELETE("RemainedCards", "uploadCardsToDelete"),
        REMAINED_CARDS_UPLOAD_CARDS_TO_DISABLE("RemainedCards", "uploadCardsToDisable"),
        REMAINED_CARDS_UPLOAD_CARDS_TO_PERMANENT_DELETE("RemainedCards", "uploadCardsToPermanentDelete"),
        MFI_CLIENT_GET_CACHED_CARD_LIST_WITH_INTEGRITY_CHECK("MfiClient", "getCachedCardListWithIntegrityCheck"),
        USER_MIGRATE_CARD_KEY("User", "migrateCardKey");

        public final String className;
        public final String methodName;
        public final String msg;

        MficApi(String className, String methodName) {
            this.className = className;
            this.methodName = methodName;
            this.msg = Integer.toHexString(className.hashCode()) + "#" + Integer.toHexString(methodName.hashCode());
        }
    }
}
