package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.util.LogMgr;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public abstract class StoppableTaskBase extends TaskBase {
    private AtomicBoolean mStopped;

    @Override // com.felicanetworks.semc.sws.TaskBase
    public abstract void start();

    public StoppableTaskBase(int i) {
        super(i);
        LogMgr.log(6, "000");
        this.mStopped = new AtomicBoolean(false);
        LogMgr.log(6, "999");
    }

    public void stop() {
        LogMgr.log(6, "000");
        this.mStopped.set(true);
        LogMgr.log(6, "999");
    }

    protected boolean isStopped() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return this.mStopped.get();
    }
}
