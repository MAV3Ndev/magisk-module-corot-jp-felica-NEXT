package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.FelicaException;

/* JADX INFO: loaded from: classes.dex */
public class MfiClientException extends FelicaException {
    public static final int ID_CARD_ISSUE_ERROR = 100;
    public static final int ID_CARD_OPERATION_ERROR = 102;
    public static final int ID_GET_ACCOUNT_ERROR = 101;
    public static final int ID_GET_LOCAL_PARTIAL_CARD_INFO_LIST_ERROR = 105;
    public static final int ID_GET_UNSUPPORT_MFI_SERVICE_1_CARD_POSITION_ERROR = 104;
    public static final int ID_NOT_SUPPORTED_CHIP_ERROR = 103;
    public static final String JSON_PARSE_ERROR_MESSAGE = "Json parse error.";
    public static final int TYPE_CARD_NOT_CACHED = 157;
    public static final int TYPE_CARD_NOT_FOUND = 167;
    public static final int TYPE_EXIST_EMPTY_SLOT_FAILED = 173;
    public static final int TYPE_GET_LOCAL_CID_LIST_FAILED = 166;
    public static final int TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED = 172;
    public static final int TYPE_GET_SE_INFORMATION_FAILED = 162;
    public static final int TYPE_GET_UNSUPPORT_MFI_SERVICE_1_CARD_POSITION_FAILED = 168;
    public static final int TYPE_IDENTIFY_SERVICE_FAILED = 165;
    public static final int TYPE_ILLEGAL_CARD_OPERATION = 158;
    public static final int TYPE_ILLEGAL_LINKAGE_DATA = 155;
    public static final int TYPE_ILLEGAL_SERVICEID = 170;
    public static final int TYPE_MFICLIENT_ALREADY_STARTED = 160;
    public static final int TYPE_MFICLIENT_CURRENTLY_ONLINE = 159;
    public static final int TYPE_MFICLIENT_NOT_ACTIVATED = 152;
    public static final int TYPE_MFICLIENT_NOT_FOUND = 151;
    public static final int TYPE_MFICLIENT_NOT_STARTED = 154;
    public static final int TYPE_MFICLIENT_REMOTE_ACCESS_FAILED = 150;
    public static final int TYPE_MFICLIENT_STARTED = 153;
    public static final int TYPE_MFI_OFFLINE_CANCELED = 169;
    public static final int TYPE_NOT_SUPPORTED = 163;
    public static final int TYPE_NOT_SUPPORTED_CHIP_ERROR = 164;
    public static final int TYPE_NOT_SUPPORTED_SERVICEID_ERROR = 171;
    public static final int TYPE_NO_ACCOUNT_INFO = 156;
    public static final int TYPE_SE_ACCESS_ERROR = 161;

    public MfiClientException(FelicaException felicaException) {
        super(felicaException.getID(), felicaException.getType(), felicaException.getOtherAppInfo(), felicaException.getStatusFlag1(), felicaException.getStatusFlag2(), felicaException.getMessage());
    }

    public MfiClientException(int i, int i2, String str) {
        super(i, i2, str);
    }
}
