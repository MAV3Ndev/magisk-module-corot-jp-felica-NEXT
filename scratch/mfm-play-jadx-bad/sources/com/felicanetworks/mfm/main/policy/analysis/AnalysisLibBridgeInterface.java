package com.felicanetworks.mfm.main.policy.analysis;

import android.content.Context;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
interface AnalysisLibBridgeInterface {
    int getMaxDataSize();

    void initialize(Context context) throws Exception;

    void sendLogs() throws Exception;

    void stampAutoDump(String event, Map<String, String> recodes) throws Exception;

    void stampScreen(String event, Map<String, String> recodes) throws Exception;

    void stampUserAction(String event, Map<String, String> recodes) throws Exception;
}
