package com.felicanetworks.mfc.mfi.async;

import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public abstract class AsyncFuture<V> {
    private static final int STATE_DONE = 1;
    private static final int STATE_INIT = 0;
    private int mState = 0;
    private V mResult = null;

    protected abstract boolean start() throws Exception;

    public synchronized V get() {
        try {
            if (start()) {
                while (this.mState == 0) {
                    wait();
                }
            } else {
                this.mState = 1;
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            this.mState = 1;
        }
        return this.mResult;
    }

    public synchronized V getResult() {
        return this.mResult;
    }

    protected synchronized void done(V v) {
        this.mState = 1;
        this.mResult = v;
        notifyAll();
    }
}
