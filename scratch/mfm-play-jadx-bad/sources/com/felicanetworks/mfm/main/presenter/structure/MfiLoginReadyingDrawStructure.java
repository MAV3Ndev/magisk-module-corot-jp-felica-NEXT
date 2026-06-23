package com.felicanetworks.mfm.main.presenter.structure;

import android.os.Handler;
import android.os.Looper;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.presenter.internal.StateController;
import com.felicanetworks.mfm.main.presenter.internal.StateMachine;

/* JADX INFO: loaded from: classes3.dex */
public class MfiLoginReadyingDrawStructure extends CloseDrawStructure {
    private FailureObserver observer;

    public MfiLoginReadyingDrawStructure() {
        super(StructureType.MFI_LOGIN_READYING);
    }

    public interface FailureObserver {
        void onMfiLoginReadyingFailure(MfiLoginReadyingDrawStructure structure, Type type);

        void onMfiLoginReadyingFailureDetail(MfiLoginReadyingDrawStructure structure, Type type, ModelErrorInfo errorInfo);

        public enum Type {
            SERVER_MAINTENANCE(R.string.dlg_text_warning_mfi_server_maintenance),
            NETWORK_ERROR(R.string.dlg_warning_mfiservices_network_failed),
            FAILURE(R.string.dlg_text_warning_login_failed);

            final int stringRes;

            Type(int stringRes) {
                this.stringRes = stringRes;
            }

            public int getStringRes() {
                return this.stringRes;
            }
        }
    }

    public void actShownFailure() {
        StateController.postStateEvent(StateMachine.Event.EM_MFI_LOGIN_RESULT, this, new Object[0]);
    }

    public void setFailureObserver(FailureObserver observer) {
        this.observer = observer;
    }

    public void notifyFailure(final ModelErrorInfo errorInfo) {
        new Handler(Looper.getMainLooper()).post(new Runnable() { // from class: com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure.1
            @Override // java.lang.Runnable
            public void run() {
                FailureObserver.Type type;
                int i = AnonymousClass2.$SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[errorInfo.getType().ordinal()];
                if (i == 1) {
                    type = FailureObserver.Type.SERVER_MAINTENANCE;
                } else if (i == 2) {
                    type = FailureObserver.Type.NETWORK_ERROR;
                } else {
                    type = FailureObserver.Type.FAILURE;
                }
                if (MfiLoginReadyingDrawStructure.this.observer != null) {
                    if (type != FailureObserver.Type.FAILURE) {
                        MfiLoginReadyingDrawStructure.this.observer.onMfiLoginReadyingFailure(MfiLoginReadyingDrawStructure.this, type);
                        return;
                    } else {
                        MfiLoginReadyingDrawStructure.this.observer.onMfiLoginReadyingFailureDetail(MfiLoginReadyingDrawStructure.this, type, errorInfo);
                        return;
                    }
                }
                MfiLoginReadyingDrawStructure.this.actShownFailure();
            }
        });
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.presenter.structure.MfiLoginReadyingDrawStructure$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type;

        static {
            int[] iArr = new int[ModelErrorInfo.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type = iArr;
            try {
                iArr[ModelErrorInfo.Type.MFIC_SERVER_MAINTENANCE_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ModelErrorInfo$Type[ModelErrorInfo.Type.MFIC_NETWORK_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
