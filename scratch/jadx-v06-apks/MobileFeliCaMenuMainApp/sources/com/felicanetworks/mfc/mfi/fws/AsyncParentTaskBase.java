package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
abstract class AsyncParentTaskBase<T> extends AsyncTaskBase<T> {
    private StoppableTaskBase<T> mChildTask;

    AsyncParentTaskBase(int i, ExecutorService executorService, AsyncTaskBase.Listener listener) {
        super(i, executorService, listener);
        this.mChildTask = null;
    }

    protected void setStoppableSubTask(StoppableTaskBase stoppableTaskBase) {
        synchronized (this) {
            this.mChildTask = stoppableTaskBase;
            if (isStopped()) {
                this.mChildTask.stop();
            }
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public synchronized void stop() {
        super.stop();
        if (this.mChildTask != null) {
            this.mChildTask.stop();
        }
    }
}
