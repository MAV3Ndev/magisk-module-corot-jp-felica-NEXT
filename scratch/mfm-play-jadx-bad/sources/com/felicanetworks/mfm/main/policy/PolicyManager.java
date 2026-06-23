package com.felicanetworks.mfm.main.policy;

import android.content.Context;
import com.felicanetworks.mfm.main.policy.err.ErrorManager;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.mfitest.MfiTestSettings;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class PolicyManager {
    private static boolean isMfiTestTarget() {
        return false;
    }

    public static void setup(Context context) {
        if (!Sg.load(context)) {
            throw new IllegalStateException();
        }
        LogUtil.setup(context);
        if (isMfiTestTarget() && !MfiTestSettings.load()) {
            throw new IllegalStateException(new JSONException("MfiTestSettings Read Error."));
        }
        ErrorManager.setup(context);
    }
}
