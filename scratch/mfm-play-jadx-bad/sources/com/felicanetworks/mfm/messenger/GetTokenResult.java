package com.felicanetworks.mfm.messenger;

import android.os.RemoteException;
import com.felicanetworks.mfm.messenger.IGetTokenResult;

/* JADX INFO: loaded from: classes3.dex */
class GetTokenResult extends IGetTokenResult.Stub {
    private final String token;

    GetTokenResult(String str) {
        this.token = str;
    }

    GetTokenResult(IGetTokenResult iGetTokenResult) throws RemoteException {
        if (iGetTokenResult != null) {
            this.token = iGetTokenResult.getToken();
        } else {
            this.token = null;
        }
    }

    @Override // com.felicanetworks.mfm.messenger.IGetTokenResult
    public String getToken() {
        return this.token;
    }
}
