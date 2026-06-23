package com.amazonaws.auth;

/* JADX INFO: loaded from: classes.dex */
public interface AWSIdentityProvider {
    String getToken();

    String refresh();
}
