package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
abstract class AsyncParentTaskBase<T> extends AsyncTaskBase<T> {
    private StoppableTaskBase<T> mChildTask;

    AsyncParentTaskBase(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener) {
        super(taskId, executor, listener);
        this.mChildTask = null;
    }

    protected void setStoppableSubTask(StoppableTaskBase childTask) {
        synchronized (this) {
            this.mChildTask = childTask;
            if (isStopped()) {
                this.mChildTask.stop();
            }
        }
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    public synchronized void stop() {
        super.stop();
        StoppableTaskBase<T> stoppableTaskBase = this.mChildTask;
        if (stoppableTaskBase != null) {
            stoppableTaskBase.stop();
        }
    }
}
