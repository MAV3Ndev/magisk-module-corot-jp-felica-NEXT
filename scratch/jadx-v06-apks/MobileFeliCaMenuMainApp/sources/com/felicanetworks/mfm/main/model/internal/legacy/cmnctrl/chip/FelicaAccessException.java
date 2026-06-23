package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes.dex */
public class FelicaAccessException extends MfmException {
    private static final long serialVersionUID = -410721103973217394L;
    private Id errorId;
    private FelicaException felicaException;

    public enum CognitiveType {
        NOT_SYSTEM_ERROR,
        NOT_EXIST_AREA_ZERO
    }

    public enum Id {
        NOT_SYSTEM_ERROR,
        FELICA_TIMEOUT_ERROR,
        FELICA_LOCK,
        FELICA_OPEN_ERROR,
        FELICA_INVALID_RESPONSE_ERROR,
        USED_BY_OTHER_APP,
        MFC_PERM_INSPECT_ERROR,
        MFC_OTHER_ERROR,
        OTHER_ERROR,
        FELICA_HTTP_ERROR
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip.FelicaAccessException$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$CognitiveType;

        static {
            int[] iArr = new int[CognitiveType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$CognitiveType = iArr;
            try {
                iArr[CognitiveType.NOT_SYSTEM_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$CognitiveType[CognitiveType.NOT_EXIST_AREA_ZERO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public FelicaAccessException(Class cls, int i, CognitiveType cognitiveType) {
        super(cls, i);
        this.errorId = Id.OTHER_ERROR;
        this.felicaException = null;
        int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$legacy$cmnctrl$chip$FelicaAccessException$CognitiveType[cognitiveType.ordinal()];
        if (i2 == 1) {
            this.errorId = Id.NOT_SYSTEM_ERROR;
        } else {
            if (i2 != 2) {
                return;
            }
            this.errorId = Id.OTHER_ERROR;
        }
    }

    public FelicaAccessException(Class cls, int i, FelicaException felicaException) {
        super(cls, i, felicaException, (felicaException.getID() * 256) + felicaException.getType());
        this.errorId = Id.OTHER_ERROR;
        this.felicaException = null;
        this.felicaException = felicaException;
        Id id = Id.MFC_OTHER_ERROR;
        int type = felicaException.getType();
        if (type == 6) {
            id = Id.FELICA_INVALID_RESPONSE_ERROR;
        } else if (type == 7) {
            id = Id.FELICA_TIMEOUT_ERROR;
        } else if (type == 8) {
            id = Id.FELICA_OPEN_ERROR;
        } else if (type == 39) {
            id = Id.USED_BY_OTHER_APP;
        } else if (type == 55) {
            id = Id.FELICA_LOCK;
        }
        this.errorId = id;
    }

    public FelicaAccessException(Class cls, int i, Exception exc) {
        super(cls, i, exc);
        this.errorId = Id.OTHER_ERROR;
        this.felicaException = null;
    }

    public Id getErrorId() {
        return this.errorId;
    }

    public FelicaException getOriginalFelicaException() {
        return this.felicaException;
    }
}
