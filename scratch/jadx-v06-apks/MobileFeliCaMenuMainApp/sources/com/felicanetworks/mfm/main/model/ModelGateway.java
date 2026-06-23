package com.felicanetworks.mfm.main.model;

import android.content.Context;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.internal.main.Initializer;

/* JADX INFO: loaded from: classes.dex */
public class ModelGateway {
    public static final boolean IS_DUMMY_FUNCTION = false;
    private Context _context;
    private Initializer _initializer;

    public interface FunctionProvider {
        CardDetailFunc getCardDetailFunc();

        CentralFunc getCentralFunc();

        DeleteCardListFunc getDeleteCardListFunc();

        ExtIcCardFunc getExtIcCardFunc();

        MemoryUsageFunc getMemoryUsageFunc();

        MfiCardFunc getMfiCardFunc();

        NoticeFunc getNoticeFunc();

        TermsOfServiceFunc getTermsOfServiceFunc();
    }

    public interface InitializeListener {
        void onComplete(FunctionProvider functionProvider, InitializedState initializedState);

        void onFailure(ModelErrorInfo modelErrorInfo);
    }

    public String toString() {
        return "ModelGateway{_initializer=" + this._initializer + '}';
    }

    public ModelGateway(Context context) {
        this._context = null;
        this._context = context;
    }

    public void initialize(InitializeListener initializeListener) {
        Initializer initializer = new Initializer(this._context);
        this._initializer = initializer;
        initializer.initialize(initializeListener);
    }

    public void deinitialize() {
        Initializer initializer = this._initializer;
        if (initializer != null) {
            initializer.deinitialize();
        }
    }

    public static class InitializedState {
        private UpgradeType _upgradeType;

        public enum UpgradeType {
            NONE,
            REQUIRED_UPGRADE,
            OPTIONAL_UPGRADE
        }

        public String toString() {
            return "InitializedState{_upgradeType=" + this._upgradeType + '}';
        }

        public InitializedState(UpgradeType upgradeType) {
            this._upgradeType = upgradeType;
        }

        public UpgradeType getUpgradeType() {
            return this._upgradeType;
        }
    }
}
