package com.felicanetworks.mfm.main.presenter.structure;

import android.content.Intent;
import com.felicanetworks.mfm.main.policy.analysis.AnalysisManager;
import com.felicanetworks.mfm.main.policy.analysis.MfmStamp;
import com.felicanetworks.mfm.main.policy.fix.MfiLoginResultCode;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.action.IActionAppResult;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes.dex */
public class MfiLoginDrawStructure extends DrawStructure implements IActionAppResult {
    private final Intent intent;
    private LoginErrorResultListener listener;
    private int resultCode;

    public interface LoginErrorResultListener {
        void onErrorResult(MfiLoginResultCode mfiLoginResultCode, Intent intent);
    }

    public MfiLoginDrawStructure(Intent intent) {
        super(StructureType.MFI_LOGIN);
        this.resultCode = MfiLoginResultCode.NONE.getCode();
        this.listener = null;
        this.intent = intent;
    }

    public Intent getDefaultIntent() {
        return this.intent;
    }

    @Override // com.felicanetworks.mfm.main.presenter.action.IActionAppResult
    public void actResult(int i, Intent intent) {
        this.resultCode = i;
        if (ServicePreference.getInstance().loadTutorial(PresenterData.getInstance().getContext())) {
            AnalysisManager.stamp(MfmStamp.Event.AUTO_DUMP_RESULT_FIRST_LOGIN, Integer.valueOf(i));
        }
        int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.resolve(i).ordinal()];
        if (i2 == 1) {
            if (intent == null || intent.getExtras() == null) {
                StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_LOGIN_FAILURE, this, Integer.valueOf(i));
                return;
            } else {
                StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_LOGIN_SUCCESS, this, Integer.valueOf(i), Integer.valueOf(intent.getIntExtra("code", 0)));
                return;
            }
        }
        if (i2 == 2 || i2 == 3 || i2 == 4) {
            StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_LOGIN_CANCEL, this, Integer.valueOf(i));
            return;
        }
        LoginErrorResultListener loginErrorResultListener = this.listener;
        if (loginErrorResultListener != null) {
            loginErrorResultListener.onErrorResult(MfiLoginResultCode.resolve(i), intent);
        } else {
            StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_LOGIN_FAILURE, this, Integer.valueOf(i));
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.structure.MfiLoginDrawStructure$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode;

        static {
            int[] iArr = new int[MfiLoginResultCode.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode = iArr;
            try {
                iArr[MfiLoginResultCode.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_ANDROID.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_USER_DISAGREE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$fix$MfiLoginResultCode[MfiLoginResultCode.CANCELLED_BY_USER_SKIPPED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void actCloseErrorDialog() {
        StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_LOGIN_FAILURE, this, Integer.valueOf(this.resultCode));
    }

    public void actPressBackKey() {
        StateController.postStateEvent(StateMachine.Event.EV_RESULT_MFI_LOGIN_CANCEL, this, Integer.valueOf(MfiLoginResultCode.CANCELLED_BY_USER_SKIPPED.getCode()));
    }

    public void setLoginErrorResultListener(LoginErrorResultListener loginErrorResultListener) {
        this.listener = loginErrorResultListener;
    }
}
