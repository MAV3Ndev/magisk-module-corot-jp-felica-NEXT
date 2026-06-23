package com.amazonaws.mobile.client;

import com.amazonaws.mobile.auth.core.IdentityManager;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class AWSStartupResult {
    private IdentityManager identityManager;

    public AWSStartupResult(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public boolean isIdentityIdAvailable() {
        return this.identityManager.getCachedUserID() != null;
    }
}
