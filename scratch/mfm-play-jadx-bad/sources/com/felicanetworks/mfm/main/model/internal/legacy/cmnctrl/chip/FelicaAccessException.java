package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip;

import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfm.main.policy.err.MfmException;

/* JADX INFO: loaded from: classes3.dex */
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

    public FelicaAccessException(Class cls, int index, CognitiveType id) {
        super(cls, index);
        this.errorId = Id.OTHER_ERROR;
        this.felicaException = null;
        int iOrdinal = id.ordinal();
        if (iOrdinal == 0) {
            this.errorId = Id.NOT_SYSTEM_ERROR;
        } else {
            if (iOrdinal != 1) {
                return;
            }
            this.errorId = Id.OTHER_ERROR;
        }
    }

    public FelicaAccessException(Class cls, int index, FelicaException e) {
        super(cls, index, e, (e.getID() * 256) + e.getType());
        this.errorId = Id.OTHER_ERROR;
        this.felicaException = e;
        Id id = Id.MFC_OTHER_ERROR;
        int type = e.getType();
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

    public FelicaAccessException(Class cls, int index, Exception e) {
        super(cls, index, e);
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
