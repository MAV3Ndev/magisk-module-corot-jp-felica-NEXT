package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.MfiCardFunc;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MfiCardFuncAgent {
    private MfiCardFunc _client;

    public MfiCardFuncAgent(MfiCardFunc mfiCardFunc) {
        if (mfiCardFunc == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = mfiCardFunc;
    }

    public List<String> getUniqueServiceIds() {
        ArrayList arrayList = new ArrayList();
        for (MyCardInfo myCardInfo : this._client.getMyCardInfoList()) {
            if (!arrayList.contains(myCardInfo.getServiceId())) {
                arrayList.add(myCardInfo.getServiceId());
            }
        }
        return arrayList;
    }

    public CompiledState getCompiledState() {
        return new CompiledState(this._client.getCompiledState());
    }

    public Boolean isCompiled() {
        return this._client.isCompiled();
    }

    public boolean isLoggedIn() {
        return this._client.isLoggedIn();
    }

    public boolean hasNeverLoggedIn() {
        return this._client.hasNeverLoggedIn();
    }

    public boolean hasUnrecoveredCard() {
        return this._client.hasUnrecoveredCard();
    }

    public ModelErrorInfo getRecoveryErrorInfo() {
        return this._client.getRecoveryErrorInfo();
    }

    public boolean isInsideTransitGateRecoveryFailed() {
        return this._client.isInsideTransitGateRecoveryFailed();
    }

    public static class CompiledState {
        private MfiCardFunc.CompiledState _client;

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
            MFIC_NO_WARNING
        }

        CompiledState(MfiCardFunc.CompiledState compiledState) {
            this._client = compiledState;
        }

        public MfiCardState getMfiCardState() {
            return MfiCardState.valueOf(this._client.getMfiCardState().name());
        }

        public ModelErrorInfo getMficWarningState() {
            return this._client.getMficWarningState();
        }
    }
}
