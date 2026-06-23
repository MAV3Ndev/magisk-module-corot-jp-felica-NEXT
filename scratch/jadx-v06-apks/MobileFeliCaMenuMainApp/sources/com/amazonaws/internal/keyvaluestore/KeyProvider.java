package com.amazonaws.internal.keyvaluestore;

import java.security.Key;

/* JADX INFO: loaded from: classes.dex */
interface KeyProvider {
    void deleteKey(String str);

    Key generateKey(String str) throws KeyNotGeneratedException;

    Key retrieveKey(String str) throws KeyNotFoundException;
}
