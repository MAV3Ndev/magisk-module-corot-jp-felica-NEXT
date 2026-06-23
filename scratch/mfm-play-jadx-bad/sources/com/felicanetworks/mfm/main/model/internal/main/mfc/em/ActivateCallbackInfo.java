package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes3.dex */
public class ActivateCallbackInfo extends CausedErrorInfo {
    private final AppInfo appInfo;
    private final String message;
    private final int type;

    public ActivateCallbackInfo(int type, String message, AppInfo appInfo) {
        super(CauseLocation.ACTIVATE_CALLBACK);
        this.type = type;
        this.message = message;
        this.appInfo = appInfo;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseType() {
        return this.type;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public String getMessage() {
        return this.message;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public int getExtra() {
        return this.type;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public MfcException.Type getType() {
        int i = this.type;
        if (i != 3) {
            if (i != 4) {
                if (i == 7) {
                    return MfcException.Type.MFC_USED_BY_OTHER_APP;
                }
                if (i == 100) {
                    return MfcException.Type.MFIC_VERSION_ERROR;
                }
                if (i != 101) {
                    return MfcException.Type.MFC_OTHER_ERROR;
                }
            }
            return MfcException.Type.MFC_INVALID_PERMISSION;
        }
        return MfcException.Type.FELICA_HTTP_ERROR;
    }
}
