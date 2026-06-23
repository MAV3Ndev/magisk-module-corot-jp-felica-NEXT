package com.felicanetworks.mfm.playIntegrity.agent;

import android.content.Context;
import android.os.Build;
import com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgentErrorType;
import com.felicanetworks.mfm.playIntegrity.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PlayIntegrityAgent {

    public interface PlayIntegrityTokenEventListener {
        void onError(PlayIntegrityAgentErrorType.Error error, String str);

        void onSuccess(String str);
    }

    private PlayIntegrityAgent() {
    }

    static {
        LogMgr.log(6, "<PlayIntegrityAgent static method is called.>");
    }

    public static void getPlayIntegrityToken(Context context, String str, final PlayIntegrityTokenEventListener playIntegrityTokenEventListener) throws IllegalArgumentException {
        LogMgr.performanceIn("API", "PlayIntegrityAgent", "getPlayIntegrityToken");
        LogMgr.log(3, "000");
        if (context == null) {
            LogMgr.log(2, "700 context is null.");
            throw new IllegalArgumentException(PlayIntegrityAgentErrorMessage.EXC_INVALID_CONTEXT);
        }
        if (str == null) {
            LogMgr.log(2, "701 json is null.");
            throw new IllegalArgumentException(PlayIntegrityAgentErrorMessage.EXC_INVALID_NONCESOURCE);
        }
        if (playIntegrityTokenEventListener == null) {
            LogMgr.log(2, "702 listener is null.");
            throw new IllegalArgumentException(PlayIntegrityAgentErrorMessage.EXC_INVALID_LISTENER);
        }
        if (Build.VERSION.SDK_INT < 28) {
            LogMgr.log(2, "703 " + Build.VERSION.SDK_INT + " is not suppoerted OS version.");
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.1
                @Override // java.lang.Runnable
                public void run() {
                    LogMgr.log(6, "000");
                    try {
                        playIntegrityTokenEventListener.onError(PlayIntegrityAgentErrorType.Error.TYPE_UNSUPPORTED_OS_VERSION, null);
                    } catch (Exception unused) {
                        LogMgr.log(1, "800 Failed to call listener.");
                    }
                    LogMgr.log(6, "999");
                }
            }).start();
            return;
        }
        if (PlayIntegrityHandler.getInstance().checkPiaTaskHandlerIsStopped()) {
            LogMgr.log(2, "704 Stop task is being executed.");
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.playIntegrity.agent.PlayIntegrityAgent.2
                @Override // java.lang.Runnable
                public void run() {
                    LogMgr.log(6, "000");
                    try {
                        playIntegrityTokenEventListener.onError(PlayIntegrityAgentErrorType.Error.TYPE_INTERRUPTED_ERROR, null);
                    } catch (Exception unused) {
                        LogMgr.log(1, "800 Failed to call listener.");
                    }
                    LogMgr.log(6, "999");
                }
            }).start();
        } else {
            PlayIntegrityHandler.getInstance().post(context, str, playIntegrityTokenEventListener);
            LogMgr.log(3, "999");
            LogMgr.performanceOut("API", "PlayIntegrityAgent", "getPlayIntegrityToken");
        }
    }

    public static void stop() {
        LogMgr.performanceIn("API", "PlayIntegrityAgent", "stop");
        LogMgr.log(3, "000");
        if (PlayIntegrityHandler.checkInstance() && !PlayIntegrityHandler.getInstance().checkPiaTaskHandlerIsStopped()) {
            LogMgr.log(7, "001");
            PlayIntegrityHandler.getInstance().stop();
        }
        LogMgr.log(3, "999");
        LogMgr.performanceOut("API", "PlayIntegrityAgent", "stop");
    }
}
