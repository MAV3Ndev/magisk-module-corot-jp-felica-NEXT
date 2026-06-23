package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.model.FelicaPocketFunc;

/* JADX INFO: loaded from: classes.dex */
public class FelicaPocketFuncAgent {
    private FelicaPocketFunc _client;

    public FelicaPocketFuncAgent(FelicaPocketFunc felicaPocketFunc) {
        if (felicaPocketFunc == null) {
            throw new IllegalArgumentException("client is null");
        }
        this._client = felicaPocketFunc;
    }

    public FpAreaInfoAgent getFPAreaInfo() {
        if (this._client.getFpAreaInfo() == null) {
            return null;
        }
        return new FpAreaInfoAgent(this._client.getFpAreaInfo());
    }

    public CompiledFpState getCompiledFpState() {
        return new CompiledFpState(this._client.getCompiledFpState());
    }

    public static class CompiledFpState {
        private FelicaPocketFunc.CompiledFpState _client;

        public enum FpState {
            NO_PROBLEM,
            OPEN_ERROR,
            TIMEOUT_ERROR,
            USED_BY_OTHER_APP,
            RUNNING_BY_MFIC,
            OTHER_ERROR,
            UNSUPPORTED
        }

        CompiledFpState(FelicaPocketFunc.CompiledFpState compiledFpState) {
            if (compiledFpState == null) {
                throw new IllegalArgumentException("client is null");
            }
            this._client = compiledFpState;
        }

        public FpState getFpState() {
            return FpState.valueOf(this._client.getFelicaFPState().name());
        }
    }
}
