package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.sws.AsyncTaskBase;
import com.felicanetworks.semc.util.LogMgr;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
abstract class AsyncParentTaskBase<T> extends AsyncTaskBase<T> {
    private StoppableTaskBase mChildTask;

    public abstract T getResult();

    AsyncParentTaskBase(int i, ExecutorService executorService, AsyncTaskBase.Listener listener) {
        super(i, executorService, listener);
        this.mChildTask = null;
        LogMgr.log(6, "000 taskId=[" + i + "]");
        LogMgr.log(6, "999");
    }

    protected void setStoppableSubTask(StoppableTaskBase stoppableTaskBase) {
        LogMgr.log(8, "000");
        synchronized (this) {
            this.mChildTask = stoppableTaskBase;
            if (isStopped()) {
                this.mChildTask.stop();
            }
        }
        LogMgr.log(8, "999");
    }

    @Override // com.felicanetworks.semc.sws.StoppableTaskBase
    public synchronized void stop() {
        super.stop();
        LogMgr.log(6, "000");
        if (this.mChildTask != null) {
            LogMgr.log(6, "001 mChildTask is not null.");
            this.mChildTask.stop();
        }
        LogMgr.log(6, "999");
    }
}
