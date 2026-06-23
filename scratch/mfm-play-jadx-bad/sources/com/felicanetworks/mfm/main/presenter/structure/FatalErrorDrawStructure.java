package com.felicanetworks.mfm.main.presenter.structure;

import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.policy.err.MfmException;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class FatalErrorDrawStructure extends CloseDrawStructure {
    private ErrorType _errorType;
    private String _mfcErrorCode;
    private MfmException _mfmExp;

    public enum ErrorType {
        UNSUPPORTED_DEVICE(false),
        EXHAUSTION_OF_DATA_DRIVE(false),
        FAILED_TO_CONFIRM_APP_UPGRADE(false),
        UNKNOWN_ERROR(true),
        NO_PERMISSION_TO_ACTIVATE_MFC(true),
        FELICA_CHIP_TIME_OUT(true),
        UNKNOWN_MFC_ERROR(true),
        UNKNOWN_DATABASE_ERROR(false),
        LOCKED_FELICA_CHIP(false),
        USED_MFC_BY_OTHER_APP(false),
        RUNNING_BY_MFIC(false),
        FELICA_OPEN_ERROR(false),
        FELICA_INVALID_RESPONSE_ERROR(false),
        SG_LOAD_ERROR(false),
        FELICA_HTTP_ERROR(false),
        MFC_DISABLED_ERROR(false),
        MFS_DISABLED_ERROR(false),
        MFS_SIGNATURE_UNMATCHED_ERROR(false),
        INBOUND_INCOMPATIBLE_ERROR(false),
        TERMS_OF_SERVICE_LOADING_ERROR(false),
        TERMS_OF_SERVICE_SERVER_ERROR(false),
        INVALID_REQUEST_TOKEN_ERROR(false),
        MFIC_SERVER_MAINTENANCE_ERROR(false),
        NETWORK_FAILED(true),
        PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE(false),
        PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE(false),
        PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED(false),
        MFIC_UNSUPPORTED_DEVICE_ERROR(false),
        MEMORY_CLEAR_NONSUPPORT_ERROR(false),
        MEMORY_CLEAR_RUNNING_ERROR(false);

        boolean _withCode;

        ErrorType(boolean withCode) {
            this._withCode = withCode;
        }

        boolean withCode() {
            return this._withCode;
        }
    }

    public FatalErrorDrawStructure(MfmException mfmExp) {
        super(StructureType.FATAL_ERROR);
        this._mfmExp = mfmExp;
        this._errorType = ErrorType.UNKNOWN_ERROR;
        this._mfcErrorCode = null;
    }

    public FatalErrorDrawStructure(MfmException mfmExp, ErrorType errorType) {
        super(StructureType.FATAL_ERROR);
        this._mfmExp = mfmExp;
        this._errorType = errorType;
        this._mfcErrorCode = null;
    }

    public FatalErrorDrawStructure(ModelErrorInfo modelErrorInfo) {
        super(StructureType.FATAL_ERROR);
        this._mfmExp = modelErrorInfo.getException();
        this._mfcErrorCode = modelErrorInfo.getMfcErrorCode();
        resolveErrorType(modelErrorInfo.getType());
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.structure.FatalErrorDrawStructure$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type;

        static {
            int[] iArr = new int[ModelErrorInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type = iArr;
            try {
                iArr[ModelErrorInfo.Type.NONSUPPORT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.PERM_INSPECT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_TIMEOUT_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFC_OTHER_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.DB_ACCESS_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.LOCKED_FELICA.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.USED_BY_OTHER_APP.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_RUNNING_BY_ITSELF.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_OPEN_ERROR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.FELICA_HTTP_ERROR.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_INVALID_REQUEST_TOKEN_ERROR.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.NETWORK_FAILED.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_RECOVERABLE_FAILED.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_UNSUPPORTED_DEVICE_ERROR.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.EXT_MEMORY_CLEAR_NONSUPPORT_ERROR.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
        }
    }

    private void resolveErrorType(ModelErrorInfo.Type type) {
        switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[type.ordinal()]) {
            case 1:
                this._errorType = ErrorType.UNSUPPORTED_DEVICE;
                break;
            case 2:
                this._errorType = ErrorType.NO_PERMISSION_TO_ACTIVATE_MFC;
                break;
            case 3:
                this._errorType = ErrorType.FELICA_CHIP_TIME_OUT;
                break;
            case 4:
                this._errorType = ErrorType.UNKNOWN_MFC_ERROR;
                break;
            case 5:
                this._errorType = ErrorType.UNKNOWN_DATABASE_ERROR;
                break;
            case 6:
                this._errorType = ErrorType.LOCKED_FELICA_CHIP;
                break;
            case 7:
                this._errorType = ErrorType.USED_MFC_BY_OTHER_APP;
                break;
            case 8:
                this._errorType = ErrorType.RUNNING_BY_MFIC;
                break;
            case 9:
                this._errorType = ErrorType.FELICA_OPEN_ERROR;
                break;
            case 10:
                this._errorType = ErrorType.FELICA_INVALID_RESPONSE_ERROR;
                break;
            case 11:
                this._errorType = ErrorType.FELICA_HTTP_ERROR;
                break;
            case 12:
                this._errorType = ErrorType.INVALID_REQUEST_TOKEN_ERROR;
                break;
            case 13:
                this._errorType = ErrorType.MFIC_SERVER_MAINTENANCE_ERROR;
                break;
            case 14:
                this._errorType = ErrorType.NETWORK_FAILED;
                break;
            case 15:
                this._errorType = ErrorType.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_SERVICE;
                break;
            case 16:
                this._errorType = ErrorType.PIA_PLAY_INTEGRITY_REQUEST_UPDATE_PLAY_STORE;
                break;
            case 17:
                this._errorType = ErrorType.MFIC_SERVER_MAINTENANCE_ERROR;
                break;
            case 18:
                this._errorType = ErrorType.PIA_PLAY_INTEGRITY_NONRECOVERABLE_FAILED;
                break;
            case 19:
                this._errorType = ErrorType.MFIC_UNSUPPORTED_DEVICE_ERROR;
                break;
            case 20:
                this._errorType = ErrorType.MEMORY_CLEAR_NONSUPPORT_ERROR;
                break;
            default:
                this._errorType = ErrorType.UNKNOWN_ERROR;
                break;
        }
    }

    public ErrorType getErrorType() {
        return this._errorType;
    }

    public boolean withErrorCode() {
        return getErrorType().withCode();
    }

    public String getErrorCode() {
        return this._mfmExp.getErrorCode();
    }

    public String getMfcErrorCode() {
        if (hasMfcErrorCode()) {
            return this._mfcErrorCode;
        }
        return null;
    }

    public boolean hasMfcErrorCode() {
        return this._errorType == ErrorType.UNKNOWN_MFC_ERROR;
    }

    public MfmException getMfmException() {
        return this._mfmExp;
    }

    public boolean isRetry() {
        int iOrdinal = this._errorType.ordinal();
        return iOrdinal == 11 || iOrdinal == 12;
    }

    public void actRetry() {
        StateController.postStateEvent(StateMachine.Event.EV_RETRY, this, new Object[0]);
    }
}
