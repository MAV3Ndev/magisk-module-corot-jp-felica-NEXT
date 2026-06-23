package com.felicanetworks.mfm.main.policy;

import android.content.Context;
import com.felicanetworks.mfm.main.policy.err.ErrorManager;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes.dex */
public class PolicyManager {
    public static void setup(Context context) {
        if (!Sg.load(context)) {
            throw new IllegalStateException();
        }
        LogUtil.setup(context);
        ErrorManager.setup(context);
    }
}
