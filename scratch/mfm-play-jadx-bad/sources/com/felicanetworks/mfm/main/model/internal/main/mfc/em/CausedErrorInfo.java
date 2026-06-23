package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public abstract class CausedErrorInfo {
    private final CauseLocation location;

    protected int getCauseId() {
        return 0;
    }

    protected int getCauseType() {
        return 0;
    }

    public Exception getException() {
        return null;
    }

    public int getExtra() {
        return 0;
    }

    public String getMessage() {
        return null;
    }

    CausedErrorInfo(CauseLocation location) {
        this.location = location;
    }

    public MfcException.Type getType() {
        return MfcException.Type.OTHER_ERROR;
    }

    public String getCode() {
        return String.format(Locale.US, "%02d.%02d.%03d", Integer.valueOf(this.location.value), Integer.valueOf(getCauseId()), Integer.valueOf(getCauseType()));
    }
}
