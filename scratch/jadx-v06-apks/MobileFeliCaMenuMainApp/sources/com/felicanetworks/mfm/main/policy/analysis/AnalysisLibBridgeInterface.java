package com.felicanetworks.mfm.main.policy.analysis;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
interface AnalysisLibBridgeInterface {
    int getMaxDataSize();

    void initialize(Context context) throws Exception;

    void sendLogs() throws Exception;

    void stampAutoDump(String str, Map<String, String> map) throws Exception;

    void stampScreen(String str, Map<String, String> map) throws Exception;

    void stampUserAction(String str, Map<String, String> map) throws Exception;
}
