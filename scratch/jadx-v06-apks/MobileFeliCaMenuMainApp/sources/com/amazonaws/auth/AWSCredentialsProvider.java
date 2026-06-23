package com.amazonaws.auth;

/* JADX INFO: loaded from: classes.dex */
public interface AWSCredentialsProvider {
    AWSCredentials getCredentials();

    void refresh();
}
