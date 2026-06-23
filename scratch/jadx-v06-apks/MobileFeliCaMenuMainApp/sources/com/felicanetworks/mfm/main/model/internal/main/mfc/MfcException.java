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

/* JADX INFO: loaded from: classes.dex */
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
        INSIDE_TRANSIT_GATE_ERROR
    }

    public enum CognitiveType {
        ILLEGAL_STATE(1),
        DATA_CHECK_ERROR(2),
        ILLEGAL_JSON_FORMAT(3),
        BIND_ERROR(4),
        TIMEOUT(5),
        NOT_LOGIN(6),
        RUNNING_BY_MFIC(7),
        FATAL_ERROR(FelicaConst.MAX_PACKET_DATA_LENGTH);

        public final int value;

        CognitiveType(int i) {
            this.value = i;
        }
    }

    public MfcException(Class cls, int i, CausedErrorInfo causedErrorInfo) {
        super(cls, i, causedErrorInfo.getException(), causedErrorInfo.getExtra(), causedErrorInfo.getMessage());
        this.errorInfo = causedErrorInfo;
    }

    public MfcException(Class cls, int i, CognitiveType cognitiveType) {
        this(cls, i, new CognitiveErrorInfo(cognitiveType));
    }

    public MfcException(Class cls, int i, FelicaAccessException felicaAccessException) {
        this(cls, i, new FelicaAccessExceptionInfo(felicaAccessException));
    }

    public MfcException(Class cls, int i, MfiClientException mfiClientException) {
        this(cls, i, new MfiClientExceptionInfo(mfiClientException));
    }

    public MfcException(Class cls, int i, IllegalArgumentException illegalArgumentException) {
        super(cls, i, illegalArgumentException);
    }

    @Deprecated
    protected MfcException(Class cls, int i, Exception exc) {
        super(cls, i, exc);
    }

    public boolean isRequiredUpgrade() {
        return getErrorType() == Type.MFIC_VERSION_ERROR;
    }

    public boolean isRunningMfic() {
        return getErrorType() == Type.MFC_USED_BY_OTHER_APP && TextUtils.equals(getMessage(), "com.felicanetworks.mfm.main");
    }

    public Type getErrorType() {
        CausedErrorInfo causedErrorInfo = this.errorInfo;
        return causedErrorInfo != null ? causedErrorInfo.getType() : Type.OTHER_ERROR;
    }

    public String getMfcErrorCode() {
        CausedErrorInfo causedErrorInfo = this.errorInfo;
        if (causedErrorInfo == null) {
            return null;
        }
        return causedErrorInfo.getCode();
    }
}
