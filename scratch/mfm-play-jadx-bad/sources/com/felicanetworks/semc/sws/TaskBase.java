package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public abstract class TaskBase {
    private final int mTaskId;

    protected abstract void start();

    protected TaskBase(int i) {
        LogMgr.log(6, "000");
        this.mTaskId = i;
        LogMgr.log(6, "999");
    }

    protected int getTaskId() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mTaskId;
    }
}
