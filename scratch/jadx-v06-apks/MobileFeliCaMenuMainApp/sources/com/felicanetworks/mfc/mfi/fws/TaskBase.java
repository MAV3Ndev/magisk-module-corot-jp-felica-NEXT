package com.felicanetworks.mfc.mfi.fws;

/* JADX INFO: loaded from: classes.dex */
abstract class TaskBase<T> {
    private final int mTaskId;

    abstract T getResult();

    protected abstract void start();

    protected TaskBase(int i) {
        this.mTaskId = i;
    }

    int getTaskId() {
        return this.mTaskId;
    }
}
