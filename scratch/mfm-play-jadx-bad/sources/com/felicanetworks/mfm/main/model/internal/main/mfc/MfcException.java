package com.felicanetworks.mfm.main.model.internal.main.mfc;

import android.text.TextUtils;
import com.felicanetworks.mfc.mfi.FelicaConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.em.CognitiveErrorInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.em.FelicaAccessExceptionInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.em.MfiClientExceptionInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
public class MfcException extends MfmException {
    private static final long serialVersionUID = -410721103973217394L;
    private CausedErrorInfo errorInfo;

    public enum Type {
        OTHER_ERROR,
        FELICA_TIMEOUT_ERROR,
        MFC_OTHER_ERROR,
        LOCKED_FELICA,
        FELICA_OPEN_ERROR,
        FELICA_INVALID_RESPONSE_ERROR,
        MFC_USED_BY_OTHER_APP,
        MFIC_RUNNING_BY_ITSELF,
        MFC_INVALID_PERMISSION,
        MFC_READ_FAILED,
        NFC_TRANSCEIVE_IO_ERROR,
        NFC_ILLEGALSTATE_ERROR,
        MFI_OPSRV_ACCOUNT_ERROR,
        FELICA_HTTP_ERROR,
        MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE,
        MFIC_VERSION_ERROR,
        MFIC_JSON_ERROR,
        MFIC_NO_ACCOUNT_INFO,
        INVALID_REQUEST_TOKEN,
        UNSUPPORTED_CHIP_ERROR,
        MFI_SERVER_MAINTENANCE_ERROR,
        FELICA_NETWORK_FAILED,
        UNSUPPORTED_DEVICE_ERROR,
        MFI_ISSUE_LIMIT_EXCEEDED,
        MFI_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED,
        MFI_ISSUE_LIMIT_PER_DEVICE_EXCEEDED,
        MFI_INSUFFICIENT_CHIP_SPACE,
        MFI_OTHER_SP_CARD_EXIST,
        MFI_INSTANCE_NOT_VACANT,
        TYPE_EXIST_UNKNOWN_CARD,
        MFI_INSUFFICIENT_ALLOCATED_FREE_SPACE,
        MFC_WARNING,
        INSIDE_TRANSIT_GATE_ERROR,
        MFC_CANNOT_MEMORY_CLEAR_BY_NOT_INITIALIZE,
        MEMORY_CLEAR_IN_PROGRESS,
        UNKNOWN_CHIP_STATE,
        NOT_IC_CHIP_FORMATTING,
        PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE,
        PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE,
        PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED,
        PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED,
        MFI_INTERRUPTED_ERROR,
        MFI_CARD_NOT_CACHED
    }

    public enum CognitiveType {
        ILLEGAL_STATE(1),
        DATA_CHECK_ERROR(2),
        ILLEGAL_JSON_FORMAT(3),
        BIND_ERROR(4),
        TIMEOUT(5),
        NOT_LOGIN(6),
        RUNNING_BY_MFIC(7),
        INTERRUPT(8),
        NETWORK_ERROR(9),
        FATAL_ERROR(FelicaConst.MAX_PACKET_DATA_LENGTH);

        public final int value;

        CognitiveType(int value) {
            this.value = value;
        }
    }

    public MfcException(Class cls, int index, CausedErrorInfo errorInfo) {
        super(cls, index, errorInfo.getException(), errorInfo.getExtra(), errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }

    public MfcException(Class cls, int index, CognitiveType type) {
        this(cls, index, new CognitiveErrorInfo(type));
    }

    public MfcException(Class cls, int index, FelicaAccessException e) {
        this(cls, index, new FelicaAccessExceptionInfo(e));
    }

    public MfcException(Class cls, int index, MfiClientException e) {
        this(cls, index, new MfiClientExceptionInfo(e));
    }

    public MfcException(Class cls, int index, IllegalArgumentException e) {
        super(cls, index, e);
    }

    @Deprecated
    protected MfcException(Class cls, int index, Exception e) {
        super(cls, index, e);
    }

    public boolean isRequiredUpgrade() {
        return getErrorType() == Type.MFIC_VERSION_ERROR;
    }

    public boolean isRunningMfic() {
        return getErrorType() == Type.MFC_USED_BY_OTHER_APP && TextUtils.equals(getMessage(), "com.felicanetworks.mfm.main");
    }

    public Type getErrorType() {
        CausedErrorInfo causedErrorInfo = this.errorInfo;
        if (causedErrorInfo != null) {
            return causedErrorInfo.getType();
        }
        return Type.OTHER_ERROR;
    }

    public String getMfcErrorCode() {
        CausedErrorInfo causedErrorInfo = this.errorInfo;
        if (causedErrorInfo == null) {
            return null;
        }
        return causedErrorInfo.getCode();
    }
}
