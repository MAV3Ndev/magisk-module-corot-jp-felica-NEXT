package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes.dex */
public class CognitiveErrorInfo extends CausedErrorInfo {
    private final MfcException.CognitiveType type;

    public CognitiveErrorInfo(MfcException.CognitiveType cognitiveType) {
        super(CauseLocation.COGNITIVE);
        this.type = cognitiveType;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    protected int getCauseType() {
        return this.type.value;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.em.CognitiveErrorInfo$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType;

        static {
            int[] iArr = new int[MfcException.CognitiveType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType = iArr;
            try {
                iArr[MfcException.CognitiveType.ILLEGAL_JSON_FORMAT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[MfcException.CognitiveType.NOT_LOGIN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[MfcException.CognitiveType.RUNNING_BY_MFIC.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[MfcException.CognitiveType.ILLEGAL_STATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[MfcException.CognitiveType.DATA_CHECK_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[MfcException.CognitiveType.BIND_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[MfcException.CognitiveType.TIMEOUT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[MfcException.CognitiveType.FATAL_ERROR.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public MfcException.Type getType() {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$mfc$MfcException$CognitiveType[this.type.ordinal()];
        if (i == 1) {
            return MfcException.Type.MFIC_JSON_ERROR;
        }
        if (i == 2) {
            return MfcException.Type.MFC_OTHER_ERROR;
        }
        if (i == 3) {
            return MfcException.Type.MFIC_RUNNING_BY_ITSELF;
        }
        return MfcException.Type.OTHER_ERROR;
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public String getMessage() {
        return this.type.name();
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.em.CausedErrorInfo
    public int getExtra() {
        return this.type.value + 52736;
    }
}
