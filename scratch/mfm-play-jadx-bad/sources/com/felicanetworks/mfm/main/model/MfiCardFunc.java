package com.felicanetworks.mfm.main.model;

import android.content.Intent;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.specific.MySuicaInfo;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public interface MfiCardFunc {

    public interface CompileListener {
        void onChangedAccount(String newAccountHash, String oldAccountHash);

        void onCompleted(List<MyCardInfo> myCardInfoList, CompiledState compiledState);

        void onError(ModelErrorInfo modelErrorInfo);

        void onRequestActivity(Intent intent);
    }

    public interface MfiLoginListener {
        void onCompleted(List<MyCardInfo> myCardInfoList, CompiledState compiledState);

        void onError(ModelErrorInfo modelErrorInfo);

        void onRequestActivity(Intent intent);
    }

    public interface RecoveryListener {
        void onCompleted();
    }

    void cancel();

    void clearRecoveryErrorInfo();

    void compile(CentralFunc centralFunc, String lastAccountHash, int resultCode, int extraAccountCode, final boolean isBackground, CompileListener listener);

    void compile(String name, CentralFunc centralFunc, String lastAccountHash, int resultCode, int extraAccountCode, final boolean isBackground, CompileListener listener);

    void compileCache(CentralFunc centralFunc, String lastAccountHash, CompileListener listener);

    void executeCardRecovery(RecoveryListener listener);

    void finishBackground();

    CompiledState getCompiledState();

    List<MyCardInfo> getDeleteCardInfoList();

    Set<String> getMyCardDetectSet();

    List<MyCardInfo> getMyCardInfoList();

    ModelErrorInfo getRecoveryErrorInfo();

    MySuicaInfo.Position getSuicaPosition();

    boolean hasNeverLoggedIn();

    boolean hasUnrecoveredCard();

    boolean isChaced();

    Boolean isCompiled();

    boolean isInsideTransitGateRecoveryFailed();

    boolean isLoggedIn();

    void mfiLogin(final int extraAccountCode, final boolean isTutorial, final MfiLoginListener listener);

    void reset();

    public static class CompiledState {
        private final MfiCardState _mfiCardState;
        private final ModelErrorInfo _modelErrorInfo;

        public enum MfiCardState {
            NO_PROBLEM,
            NEED_CARD_RECOVERY,
            MFIC_SERVER_MAINTENANCE_ERROR,
            MFIC_UNSUPPORTED_DEVICE_ERROR,
            MFIC_ISSUE_LIMIT_EXCEEDED,
            MFIC_ISSUE_LIMIT_PER_ACCOUNT_EXCEEDED,
            MFIC_ISSUE_LIMIT_PER_DEVICE_EXCEEDED,
            MFIC_INSUFFICIENT_CHIP_SPACE,
            MFIC_OTHER_SP_CARD_EXIST,
            MFIC_INSTANCE_NOT_VACANT,
            MFIC_TYPE_EXIST_UNKNOWN_CARD,
            MFIC_NETWORK_ERROR,
            MFIC_LOCK_ERROR,
            MFIC_OPEN_ERROR,
            MFIC_OTHER_ERROR,
            MFIC_FELICA_HTTP_ERROR,
            MFIC_LIB_UNAVAILABLE,
            MFIC_JSON_ERROR_NO_CASHE,
            MFIC_JSON_ERROR_USE_CACHE,
            MFIC_LOGIN_ERROR,
            MFIC_USED_BY_OTHER_APP,
            MFIC_RUNNING_BY_ITSELF,
            MFIC_INVALID_REQUEST_TOKEN,
            OTHER_ERROR,
            MFIC_NO_WARNING,
            MFIC_INTERRUPTED_ERROR,
            MFIC_CARD_NOT_CACHED
        }

        public String toString() {
            return "CompiledState{_mfiCardState=" + this._mfiCardState + ", _modelErrorInfo=" + this._modelErrorInfo + '}';
        }

        public CompiledState(MfiCardState mfiCardState, ModelErrorInfo modelErrorInfo) {
            this._mfiCardState = mfiCardState;
            this._modelErrorInfo = modelErrorInfo;
        }

        public MfiCardState getMfiCardState() {
            return this._mfiCardState;
        }

        public ModelErrorInfo getMficWarningState() {
            return this._modelErrorInfo;
        }
    }
}
