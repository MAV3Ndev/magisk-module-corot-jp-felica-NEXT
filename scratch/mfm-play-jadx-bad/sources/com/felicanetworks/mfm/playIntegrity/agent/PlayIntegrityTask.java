package com.felicanetworks.mfm.playIntegrity.agent;

import android.content.Context;
import com.felicanetworks.mfm.playIntegrity.agent.IPlayIntegrityWrapper;
import com.felicanetworks.mfm.playIntegrity.util.Base64Util;
import com.felicanetworks.mfm.playIntegrity.util.LogMgr;
import com.felicanetworks.mfm.playIntegrity.util.ObfuscatedMsgUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.IntegrityTokenResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes3.dex */
class PlayIntegrityTask implements Runnable {
    static final double BASE_RETRY_INTERVAL = 5000.0d;
    static final double EXPONENTIAL_BASE = 2.0d;
    static final int RETRY_COUNT_LIMIT = 3;
    boolean mAlreadyCallListenerFlag = true;
    final Context mContext;
    int mExecuteCounter;
    final RelayListener mListener;
    final String mNonce;
    final String mNonceSource;
    final StoppableHandler mStoppableHandler;

    interface RelayListener {
        void onError(StoppableHandler stoppableHandler, int i, PlayIntegrityTask playIntegrityTask);

        void onError(StoppableHandler stoppableHandler, String str);

        void onSuccess(StoppableHandler stoppableHandler, String str);
    }

    PlayIntegrityTask(Context context, StoppableHandler stoppableHandler, String str, RelayListener relayListener) {
        LogMgr.log(6, "000 nonceSource = " + str);
        this.mContext = context;
        this.mStoppableHandler = stoppableHandler;
        this.mNonceSource = str;
        this.mListener = relayListener;
        this.mNonce = createNonce(str);
        this.mExecuteCounter = 0;
        LogMgr.log(6, "999");
    }

    boolean isNeedRetry(int i) {
        LogMgr.log(6, "000");
        if (this.mExecuteCounter <= 3) {
            LogMgr.log(6, "998");
            return PlayIntegrityErrorMapping.REQUEST_RETRY_ERROR_LIST.contains(Integer.valueOf(i));
        }
        LogMgr.log(6, "999");
        return false;
    }

    @Override // java.lang.Runnable
    public void run() {
        LogMgr.log(6, "000");
        this.mExecuteCounter++;
        this.mAlreadyCallListenerFlag = false;
        try {
            if (this.mStoppableHandler.isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
                return;
            }
            try {
                waitForRetry();
                LogMgr.log(7, "001");
                if (this.mStoppableHandler.isStopped()) {
                    LogMgr.log(2, "701 Already has stopped.");
                    this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
                Task<IntegrityTokenResponse> taskCreatePlayIntegrityTask = PlayIntegrityWrapper.getInstance().createPlayIntegrityTask(this.mContext, this.mNonce);
                if (taskCreatePlayIntegrityTask == null) {
                    LogMgr.log(2, "702 Failed to Request IntegrityTokenResponse, Response is null.");
                    this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
                    LogMgr.log(6, "998");
                    return;
                }
                LogMgr.log(7, "002");
                if (this.mStoppableHandler.isStopped()) {
                    LogMgr.log(2, "703 Already has stopped.");
                    this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                PlayIntegrityWrapper.getInstance().getToken(taskCreatePlayIntegrityTask, this.mContext, new IPlayIntegrityWrapper.TokenListener() { // from class: com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityTask.1
                    @Override // com.felicanetworks.mfm.playIntegrity.agent.IPlayIntegrityWrapper.TokenListener
                    public void onSuccess(String str) {
                        LogMgr.log(6, "000");
                        if (PlayIntegrityTask.this.checkFirstTimeCallListener()) {
                            LogMgr.log(6, "001");
                            PlayIntegrityTask.this.mListener.onSuccess(PlayIntegrityTask.this.mStoppableHandler, str);
                        }
                        countDownLatch.countDown();
                        LogMgr.log(6, "999");
                    }

                    @Override // com.felicanetworks.mfm.playIntegrity.agent.IPlayIntegrityWrapper.TokenListener
                    public void onError(int i, String str) {
                        LogMgr.log(6, "000");
                        if (PlayIntegrityTask.this.checkFirstTimeCallListener()) {
                            LogMgr.log(6, "001");
                            PlayIntegrityTask.this.mListener.onError(PlayIntegrityTask.this.mStoppableHandler, i, PlayIntegrityTask.this);
                        }
                        countDownLatch.countDown();
                        LogMgr.log(6, "999");
                    }

                    @Override // com.felicanetworks.mfm.playIntegrity.agent.IPlayIntegrityWrapper.TokenListener
                    public void onError(String str) {
                        LogMgr.log(6, "000");
                        if (PlayIntegrityTask.this.checkFirstTimeCallListener()) {
                            LogMgr.log(6, "001");
                            PlayIntegrityTask.this.mListener.onError(PlayIntegrityTask.this.mStoppableHandler, str);
                        }
                        countDownLatch.countDown();
                        LogMgr.log(6, "999");
                    }
                });
                LogMgr.log(7, "003");
                if (this.mStoppableHandler.isStopped()) {
                    LogMgr.log(2, "704 Already has stopped.");
                    if (checkFirstTimeCallListener()) {
                        LogMgr.log(7, "004");
                        this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
                        return;
                    }
                    return;
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException unused) {
                    LogMgr.log(1, "801 Thread is interrupted.");
                    if (checkFirstTimeCallListener()) {
                        LogMgr.log(7, "005");
                        this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
                    }
                }
            } catch (InterruptedException unused2) {
                LogMgr.log(1, "800 Thread is interrupted.");
                this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
                return;
            }
        } catch (Exception unused3) {
            LogMgr.log(1, "802 Unknown error occurred.");
            this.mListener.onError(this.mStoppableHandler, ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
    }

    private String createNonce(String str) {
        String strEncode;
        LogMgr.log(6, "000 : nonceSource = " + str);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bArrDigest = messageDigest.digest();
            LogMgr.log(7, "001 SHA-256 hash : " + Arrays.toString(bArrDigest));
            strEncode = Base64Util.encode(bArrDigest);
        } catch (Exception unused) {
            strEncode = "";
        }
        LogMgr.log(7, "002 : nonce = " + strEncode);
        LogMgr.log(6, "999");
        return strEncode;
    }

    private void waitForRetry() throws InterruptedException {
        LogMgr.log(6, "000");
        if (this.mExecuteCounter > 1) {
            LogMgr.log(7, "001");
            sleepRetryBackoff();
        }
        LogMgr.log(6, "999");
    }

    private void sleepRetryBackoff() throws InterruptedException {
        LogMgr.log(6, "000");
        double d = this.mExecuteCounter - 1;
        LogMgr.log(7, "001 retry count = " + d);
        int iPow = (int) (Math.pow(EXPONENTIAL_BASE, d - 1.0d) * BASE_RETRY_INTERVAL);
        LogMgr.log(7, "002 sleep time = " + iPow);
        Thread.sleep((long) iPow);
        LogMgr.log(6, "999");
    }

    synchronized boolean checkFirstTimeCallListener() {
        LogMgr.log(6, "000");
        if (!this.mAlreadyCallListenerFlag) {
            LogMgr.log(7, "001");
            this.mAlreadyCallListenerFlag = true;
            LogMgr.log(6, "998 ret = true");
            return true;
        }
        LogMgr.log(6, "999 ret = false");
        return false;
    }
}
