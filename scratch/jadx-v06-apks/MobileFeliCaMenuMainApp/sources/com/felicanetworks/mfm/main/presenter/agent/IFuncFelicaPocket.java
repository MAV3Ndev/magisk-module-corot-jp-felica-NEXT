package com.felicanetworks.mfm.main.presenter.agent;

import com.felicanetworks.mfm.main.presenter.agent.FelicaPocketFuncAgent;

/* JADX INFO: loaded from: classes.dex */
public interface IFuncFelicaPocket {
    FelicaPocketFuncAgent.CompiledFpState getCompiledFpState();

    FelicaPocketFuncAgent getFelicaPocketFunc();

    boolean needRecommend();
}
