package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
public class ModelErrorInfo {
    private MfmException _exception;
    private String _mfcErrCode;
    private Type _type;

    public enum Type {
        OTHER_ERROR,
        OTHER_RUNTIME_ERROR,
        BAD_SG,
        FELICA_TIMEOUT_ERROR,
        MFC_OTHER_ERROR,
        NONSUPPORT_ERROR,
        LOCKED_FELICA,
        FELICA_OPEN_ERROR,
        FELICA_INVALID_RESPONSE_ERROR,
        NFC_IO_ERROR,
        NFC_ILLEGALSTATE_ERROR,
        USED_BY_OTHER_APP,
        PERM_INSPECT,
        FELICA_HTTP_ERROR,
        MFIC_VERSION_ERROR,
        MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE,
        NO_CONTENT_DATA,
        DB_ACCESS_ERROR,
        DB_CACHE_ERROR,
        NET_OTHER_ERROR,
        NETWORK_FAILED,
        NET_JSON_ERROR_NO_CASHE,
        NET_JSON_ERROR_USE_CACHE,
        MFIC_NETWORK_ERROR,
        MFIC_NETWORK_RETRY_ERROR,
        MFIC_LOCK_ERROR,
        MFIC_OPEN_ERROR,
        MFIC_OTHER_ERROR,
        MFIC_LIB_UNAVAILABLE,
        MFIC_JSON_ERROR_NO_CASHE,
        MFIC_JSON_ERROR_USE_CACHE,
        MFIC_LOGIN_ERROR,
        MFIC_USED_BY_OTHER_APP,
        MFIC_RUNNING_BY_ITSELF,
        MFIC_INVALID_REQUEST_TOKEN_ERROR,
        MFIC_UNSUPPORTED_CHIP_ERROR,
        MFIC_SERVER_MAINTENANCE_ERROR,
        MFIC_UNSUPPORTED_DEVICE_ERROR,
        MFIC_ISSUE_LIMIT_EXCEEDED,
        MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED,
        MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED,
        MFIC_INSUFFICIENT_CHIP_SPACE,
        MFIC_OTHER_SP_CARD_EXIST,
        MFIC_INSTANCE_NOT_VACANT,
        MFIC_TYPE_EXIST_UNKNOWN_CARD,
        MFIC_INSUFFICIENT_ALLOCATED_FREE_SPACE,
        MFIC_INSIDE_TRANSIT_GATE_ERROR,
        MFIC_WARNING,
        MFIC_NO_WARNING,
        MFIC_INTERRUPTED_ERROR,
        MFIC_CARD_NOT_CACHED,
        PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE,
        PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE,
        PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED,
        PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED,
        EXT_CARD_OPERATION_FATAL_ERROR,
        EXT_MEMORY_CLEAR_RUNNING,
        EXT_MFC_DISABLED,
        EXT_MEMORY_CLEAR_NONSUPPORT_ERROR
    }

    public String toString() {
        return "ModelErrorInfo{_type=" + this._type + ", _exception=" + this._exception + ", _mfcErrCode='" + this._mfcErrCode + "'}";
    }

    public ModelErrorInfo(Type type, MfmException exception) {
        this._type = type;
        this._exception = exception;
        if (exception instanceof MfcException) {
            this._mfcErrCode = ((MfcException) exception).getMfcErrorCode();
        }
    }

    public String getErrorCode() {
        return this._exception.getErrorCode();
    }

    public String getMfcErrorCode() {
        return this._mfcErrCode;
    }

    public Type getType() {
        return this._type;
    }

    public MfmException getException() {
        return this._exception;
    }
}
