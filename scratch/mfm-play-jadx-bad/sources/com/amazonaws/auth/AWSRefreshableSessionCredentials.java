package com.amazonaws.auth;

/* JADX INFO: loaded from: classes.dex */
public interface AWSRefreshableSessionCredentials extends AWSSessionCredentials {
    void refreshCredentials();
}
