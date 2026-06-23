package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.main.policy.fix.MfiLoginResultCode;

/* JADX INFO: loaded from: classes3.dex */
public class MficLoginResultCodeInfo extends CausedErrorInfo {
    private final MfiLoginResultCode code;
    private final int value;

    public MficLoginResultCodeInfo(int value) {
        super(CauseLocation.MFIC_LOGIN_RESULT_CODE);
        this.value = value;
        this.code = MfiLoginResultCode.resolve(value);
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.em.MficLoginResultCodeInfo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode;

        static {
            int[] iArr = new int[MfiLoginResultCode.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode = iArr;
            try {
                iArr[MfiLoginResultCode.USED_BY_OTHER_APP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.UNAVAILABLE_LIBRARY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.FAILED_COMMUNICATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public MfcException.Type getType() {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[this.code.ordinal()];
        if (i == 1) {
            return MfcException.Type.MFC_USED_BY_OTHER_APP;
        }
        if (i == 2) {
            return MfcException.Type.MFI_OPSRV_REQUIRED_LIB_UNAVAILABLE;
        }
        if (i == 3) {
            return MfcException.Type.FELICA_NETWORK_FAILED;
        }
        return MfcException.Type.MFI_OPSRV_ACCOUNT_ERROR;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseType() {
        return this.value;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public String getMessage() {
        return this.code.name();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public int getExtra() {
        int i = this.value;
        return i >= 0 ? i : i & 65535;
    }
}
