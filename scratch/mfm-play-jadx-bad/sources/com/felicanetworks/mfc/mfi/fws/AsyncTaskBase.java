package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
abstract class AsyncTaskBase<T> extends StoppableTaskBase {
    protected final ExecutorService mExecutor;
    protected boolean mIsTaskFinished;
    private Listener mTaskListener;

    interface Listener {
        void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg);
    }

    protected abstract void run();

    protected abstract void setResult(T result);

    AsyncTaskBase(int taskId, ExecutorService executor, Listener listener) {
        super(taskId);
        this.mIsTaskFinished = false;
        this.mExecutor = executor;
        this.mTaskListener = listener;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
    public final void start() {
        this.mExecutor.submit(new AsyncRunnable());
    }

    protected void onFinished(boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(4, "%s :isSuccess=%s, errType=%s, errMsg=%s", "000", Boolean.valueOf(isSuccess), Integer.valueOf(errType), errMsg);
        if (!this.mIsTaskFinished) {
            this.mIsTaskFinished = true;
            Listener listener = this.mTaskListener;
            if (listener != null) {
                listener.onFinishTask(this, isSuccess, errType, errMsg);
                this.mTaskListener = null;
            }
        }
        LogMgr.log(4, "%s", "999");
    }

    private class AsyncRunnable implements Runnable {
        private AsyncRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            LogMgr.log(4, "000 :task=" + AsyncTaskBase.this.getClass().getSimpleName());
            try {
            } catch (Throwable th) {
                LogMgr.log(2, "701 Exception");
                LogMgr.printStackTrace(7, th);
                AsyncTaskBase.this.onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(th));
            }
            if (AsyncTaskBase.this.isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                AsyncTaskBase.this.onFinished(false, 215, null);
            } else {
                AsyncTaskBase.this.run();
                LogMgr.log(4, "999");
            }
        }
    }
}
