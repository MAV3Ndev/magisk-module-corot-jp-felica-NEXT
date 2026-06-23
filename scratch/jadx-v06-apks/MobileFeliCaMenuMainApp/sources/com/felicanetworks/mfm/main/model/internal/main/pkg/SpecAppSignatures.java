package com.felicanetworks.mfm.main.model.internal.main.pkg;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class SpecAppSignatures {
    public static List<String> findSignatures(String str) {
        List<String> list = SignatureParams.APP_SIGNATURES.get(str);
        return list != null ? list : Collections.emptyList();
    }
}
