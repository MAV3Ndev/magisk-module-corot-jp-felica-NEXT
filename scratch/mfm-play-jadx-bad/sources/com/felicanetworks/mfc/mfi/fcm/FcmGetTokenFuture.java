package com.felicanetworks.mfc.mfi.fcm;

import android.content.Context;
import com.felicanetworks.mfc.mfi.async.AsyncFuture;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfm.messenger.Messenger;

/* JADX INFO: loaded from: classes3.dex */
public class FcmGetTokenFuture extends AsyncFuture<String> implements Messenger.FetchTokenListener {
    private Context mContext;

    public FcmGetTokenFuture(Context context) {
        this.mContext = context;
    }

    @Override // com.felicanetworks.mfc.mfi.async.AsyncFuture
    protected boolean start() {
        LogMgr.log(4, "000");
        Messenger.fetchToken(this.mContext, this);
        LogMgr.log(4, "999");
        return true;
    }

    @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
    public void onFetchedToken(String token) {
        LogMgr.log(4, "000");
        LogMgr.log(6, "001 token=" + token);
        done(token);
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfm.messenger.Messenger.FetchTokenListener
    public void onFailed(Exception e) {
        LogMgr.log(4, "000");
        LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
        done(null);
        LogMgr.log(4, "999");
    }
}
