package com.felicanetworks.mfc.mfi.fws;

import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public abstract class StoppableTaskBase<T> extends TaskBase<T> {
    private AtomicBoolean mStopped;

    public abstract T getResult();

    @Override // com.felicanetworks.mfc.mfi.fws.TaskBase
    public abstract void start();

    public StoppableTaskBase(int taskId) {
        super(taskId);
        this.mStopped = new AtomicBoolean(false);
    }

    public void stop() {
        this.mStopped.set(true);
    }

    protected boolean isStopped() {
        return this.mStopped.get();
    }
}
