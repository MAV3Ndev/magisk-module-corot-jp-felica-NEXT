package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessException;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes.dex */
public class FelicaAccessExceptionInfo extends CausedErrorInfo {
    private final FelicaAccessException exception;

    public FelicaAccessExceptionInfo(FelicaAccessException felicaAccessException) {
        super(CauseLocation.CAUGHT_EXCEPTION);
        this.exception = felicaAccessException;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseId() {
        if (this.exception.getOriginalFelicaException() == null) {
            return 0;
        }
        return this.exception.getOriginalFelicaException().getID();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseType() {
        if (this.exception.getOriginalFelicaException() == null) {
            return 0;
        }
        return this.exception.getOriginalFelicaException().getType();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public Exception getException() {
        if (this.exception.getOriginalFelicaException() != null) {
            return this.exception.getOriginalFelicaException();
        }
        if (this.exception.getCause() instanceof Exception) {
            return (Exception) this.exception.getCause();
        }
        return this.exception;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public String getMessage() {
        return this.exception.getOriginalFelicaException() == null ? "" : this.exception.getOriginalFelicaException().getMessage();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public int getExtra() {
        if (this.exception.getOriginalFelicaException() == null) {
            return 0;
        }
        return (this.exception.getOriginalFelicaException().getID() * 256) + this.exception.getOriginalFelicaException().getType();
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.em.FelicaAccessExceptionInfo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id;

        static {
            int[] iArr = new int[FelicaAccessException.Id.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id = iArr;
            try {
                iArr[FelicaAccessException.Id.FELICA_TIMEOUT_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.MFC_OTHER_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.OTHER_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.FELICA_LOCK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.USED_BY_OTHER_APP.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.MFC_PERM_INSPECT_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.FELICA_OPEN_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.FELICA_INVALID_RESPONSE_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[FelicaAccessException.Id.FELICA_HTTP_ERROR.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public MfcException.Type getType() {
        switch (AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$Id[this.exception.getErrorId().ordinal()]) {
            case 1:
                return MfcException.Type.FELICA_TIMEOUT_ERROR;
            case 2:
                return MfcException.Type.MFC_OTHER_ERROR;
            case 3:
                return MfcException.Type.OTHER_ERROR;
            case 4:
                return MfcException.Type.LOCKED_FELICA;
            case 5:
                return MfcException.Type.MFC_USED_BY_OTHER_APP;
            case 6:
                return MfcException.Type.MFC_INVALID_PERMISSION;
            case 7:
                return MfcException.Type.FELICA_OPEN_ERROR;
            case 8:
                return MfcException.Type.FELICA_INVALID_RESPONSE_ERROR;
            case 9:
                return MfcException.Type.FELICA_HTTP_ERROR;
            default:
                return MfcException.Type.MFC_OTHER_ERROR;
        }
    }
}
