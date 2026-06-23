package com.felicanetworks.mfc.mfi.fws;

/* JADX INFO: loaded from: classes3.dex */
abstract class TaskBase<T> {
    private final int mTaskId;

    abstract T getResult();

    protected abstract void start();

    protected TaskBase(int taskId) {
        this.mTaskId = taskId;
    }

    int getTaskId() {
        return this.mTaskId;
    }
}
