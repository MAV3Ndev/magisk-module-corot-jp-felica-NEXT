package com.felicanetworks.mfm.playIntegrity.agent;

import android.os.Handler;
import android.os.Looper;
import com.felicanetworks.mfm.playIntegrity.util.LogMgr;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class StoppableHandler extends Handler {
    private final Queue<Runnable> mExecutingAndStackedPiaTask;
    private final AtomicBoolean mStopped;

    public StoppableHandler(Looper looper) {
        super(looper);
        this.mExecutingAndStackedPiaTask = new LinkedList();
        LogMgr.log(6, "000");
        this.mStopped = new AtomicBoolean(false);
        LogMgr.log(6, "999");
    }

    public synchronized boolean stop() {
        boolean z;
        LogMgr.log(6, "000");
        if (isStopped()) {
            z = false;
        } else {
            LogMgr.log(7, "001");
            z = true;
            this.mStopped.set(true);
        }
        LogMgr.log(6, "999");
        return z;
    }

    public synchronized void addTaskToQueue(Runnable runnable) {
        LogMgr.log(6, "000");
        try {
            this.mExecutingAndStackedPiaTask.add(runnable);
            LogMgr.log(7, "001 Queue size = " + this.mExecutingAndStackedPiaTask.size());
        } catch (Exception unused) {
            LogMgr.log(1, "800 Failed to add Task to queue.");
        }
        LogMgr.log(6, "999");
    }

    public synchronized void removeTaskFromQueue() {
        LogMgr.log(6, "000");
        try {
            this.mExecutingAndStackedPiaTask.remove();
            LogMgr.log(7, "001 Queue size = " + this.mExecutingAndStackedPiaTask.size());
        } catch (Exception unused) {
            LogMgr.log(1, "800 Failed to remove Task from queue.");
        }
        LogMgr.log(6, "999");
    }

    public synchronized boolean checkQueueIsEmpty() {
        return this.mExecutingAndStackedPiaTask.isEmpty();
    }

    public synchronized boolean isStopped() {
        return this.mStopped.get();
    }
}
