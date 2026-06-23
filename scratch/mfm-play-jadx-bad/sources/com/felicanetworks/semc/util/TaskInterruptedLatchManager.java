package com.felicanetworks.semc.util;

import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
public class TaskInterruptedLatchManager {
    private CountDownLatch mTaskInterruptedLatch = null;

    public CountDownLatch getAndSetTaskInterruptedLatch() {
        LogMgr.log(8, "000");
        if (this.mTaskInterruptedLatch == null) {
            LogMgr.log(8, "001 Create taskInterruptedLatch");
            this.mTaskInterruptedLatch = new CountDownLatch(1);
        }
        LogMgr.log(8, "999");
        return this.mTaskInterruptedLatch;
    }

    public boolean countDownTaskInterruptedLatch() {
        LogMgr.log(8, "000");
        try {
            if (this.mTaskInterruptedLatch != null) {
                LogMgr.log(8, "001 Exec taskInterruptedLatch.countDown()");
                this.mTaskInterruptedLatch.countDown();
                LogMgr.log(8, "998");
                return true;
            }
        } catch (Exception e) {
            LogMgr.log(1, "800 Exception occurred. e=" + e);
        }
        LogMgr.log(8, "999");
        return false;
    }

    public void clearTaskInterruptedLatch() {
        LogMgr.log(8, "000");
        this.mTaskInterruptedLatch = null;
        LogMgr.log(8, "999");
    }
}
