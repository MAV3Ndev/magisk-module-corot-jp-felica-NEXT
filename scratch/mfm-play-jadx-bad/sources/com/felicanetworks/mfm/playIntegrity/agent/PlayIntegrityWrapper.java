package com.felicanetworks.mfm.playIntegrity.agent;

import android.content.Context;
import com.felicanetworks.mfm.playIntegrity.agent.IPlayIntegrityWrapper;
import com.felicanetworks.mfm.playIntegrity.util.LogMgr;
import com.felicanetworks.mfm.playIntegrity.util.ObfuscatedMsgUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.integrity.IntegrityManager;
import com.google.android.play.core.integrity.IntegrityManagerFactory;
import com.google.android.play.core.integrity.IntegrityServiceException;
import com.google.android.play.core.integrity.IntegrityTokenRequest;
import com.google.android.play.core.integrity.IntegrityTokenResponse;

/* JADX INFO: loaded from: classes3.dex */
class PlayIntegrityWrapper implements IPlayIntegrityWrapper {
    private static PlayIntegrityWrapper sInstance;

    private PlayIntegrityWrapper() {
    }

    static PlayIntegrityWrapper getInstance() {
        if (sInstance == null) {
            sInstance = new PlayIntegrityWrapper();
        }
        return sInstance;
    }

    @Override // com.felicanetworks.mfm.playIntegrity.agent.IPlayIntegrityWrapper
    public Task<IntegrityTokenResponse> createPlayIntegrityTask(Context context, String str) {
        LogMgr.log(6, "000");
        IntegrityManager integrityManagerCreate = IntegrityManagerFactory.create(context);
        LogMgr.log(6, "999");
        return integrityManagerCreate.requestIntegrityToken(IntegrityTokenRequest.builder().setNonce(str).build());
    }

    @Override // com.felicanetworks.mfm.playIntegrity.agent.IPlayIntegrityWrapper
    public void getToken(Task<IntegrityTokenResponse> task, Context context, final IPlayIntegrityWrapper.TokenListener tokenListener) {
        LogMgr.log(6, "000");
        task.addOnSuccessListener(new OnSuccessListener() { // from class: com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityWrapper$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.android.gms.tasks.OnSuccessListener
            public final void onSuccess(Object obj) {
                PlayIntegrityWrapper.lambda$getToken$0(tokenListener, (IntegrityTokenResponse) obj);
            }
        });
        task.addOnFailureListener(new OnFailureListener() { // from class: com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityWrapper.1
            @Override // com.google.android.gms.tasks.OnFailureListener
            public void onFailure(Exception exc) {
                LogMgr.log(6, "000");
                try {
                    LogMgr.log(7, "001");
                    if (exc instanceof IntegrityServiceException) {
                        int errorCode = ((IntegrityServiceException) exc).getErrorCode();
                        LogMgr.log(7, "002 ErrorCode = " + errorCode);
                        tokenListener.onError(errorCode, exc.getMessage());
                    } else {
                        LogMgr.log(1, "800 Unknown error occurred.");
                        tokenListener.onError(ObfuscatedMsgUtil.executionPoint());
                    }
                } catch (Exception unused) {
                    LogMgr.log(1, "801 Failed to call listener.");
                    tokenListener.onError(ObfuscatedMsgUtil.executionPoint());
                }
                LogMgr.log(6, "999");
            }
        });
        LogMgr.log(6, "999");
    }

    static /* synthetic */ void lambda$getToken$0(IPlayIntegrityWrapper.TokenListener tokenListener, IntegrityTokenResponse integrityTokenResponse) {
        LogMgr.log(6, "000");
        try {
            LogMgr.log(7, "001");
            tokenListener.onSuccess(integrityTokenResponse.token());
        } catch (Exception unused) {
            LogMgr.log(1, "800 Failed to call listener.");
            tokenListener.onError(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(6, "999");
    }
}
