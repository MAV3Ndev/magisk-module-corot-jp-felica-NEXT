package com.felicanetworks.semc.fcm;

import android.content.Context;
import com.felicanetworks.mfm.messenger.Messenger;
import com.felicanetworks.semc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FcmGetTokenFuture extends AsyncFuture<String> implements Messenger.FetchTokenListener {
    private final Context mContext;

    public FcmGetTokenFuture(Context context) {
        this.mContext = context;
    }

    @Override // com.felicanetworks.semc.fcm.AsyncFuture
    protected boolean start() {
        LogMgr.log(6, "000");
        Messenger.fetchToken(this.mContext, this);
        LogMgr.log(6, "999");
        return true;
    }

    @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
    public void onFetchedToken(String str) {
        LogMgr.log(6, "000");
        LogMgr.log(8, "001 token=" + str);
        done(str);
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
    public void onFailed(Exception exc) {
        LogMgr.log(6, "000");
        LogMgr.log(2, "700 " + exc.getClass().getSimpleName() + ":" + exc.getMessage());
        done(null);
        LogMgr.log(6, "999");
    }
}
