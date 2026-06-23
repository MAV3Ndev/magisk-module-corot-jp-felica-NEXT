package com.felicanetworks.semc.sws;

import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes3.dex */
abstract class AsyncTaskBase<T> extends StoppableTaskBase {
    protected final ExecutorService mExecutor;
    protected boolean mIsTaskFinished;
    private Listener mTaskListener;

    interface Listener {
        void onFinishTask(TaskBase taskBase, boolean z, int i, String str, String str2);
    }

    protected abstract void run();

    protected abstract void setResult(T t);

    AsyncTaskBase(int i, ExecutorService executorService, Listener listener) {
        super(i);
        this.mIsTaskFinished = false;
        LogMgr.log(6, "000 taskId=[" + i + "]");
        this.mExecutor = executorService;
        this.mTaskListener = listener;
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.semc.sws.StoppableTaskBase, com.felicanetworks.semc.sws.TaskBase
    public final void start() {
        LogMgr.log(6, "000");
        this.mExecutor.submit(new AsyncRunnable());
        LogMgr.log(6, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onFinished(boolean z, int i, String str, String str2) {
        LogMgr.log(8, "000 isSuccess=[" + z + "] errType=[" + i + "]additionalErrorInfo = [" + str + "]errMsg=[" + str2 + "]");
        if (!this.mIsTaskFinished) {
            this.mIsTaskFinished = true;
            Listener listener = this.mTaskListener;
            if (listener != null) {
                listener.onFinishTask(this, z, i, str, str2);
                this.mTaskListener = null;
            }
        }
        LogMgr.log(8, "999");
    }

    private class AsyncRunnable implements Runnable {
        private AsyncRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            LogMgr.log(6, "000 task=" + AsyncTaskBase.this.getClass().getSimpleName());
            try {
            } catch (Throwable th) {
                LogMgr.log(2, "701 Exception");
                LogMgr.printStackTrace(9, th);
                AsyncTaskBase.this.onFinished(false, 900, "", ObfuscatedMsgUtil.executionPoint(th));
            }
            if (AsyncTaskBase.this.isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                AsyncTaskBase.this.onFinished(false, 901, "", ObfuscatedMsgUtil.executionPoint());
            } else {
                AsyncTaskBase.this.run();
                LogMgr.log(6, "999");
            }
        }
    }
}
