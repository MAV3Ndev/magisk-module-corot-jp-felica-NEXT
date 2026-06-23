package com.felicanetworks.mfm.main.model.internal.main.pkg;

import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public final class SpecAppSignatures {
    public static List<String> findSignatures(String pkgName) {
        List<String> list = SignatureParams.APP_SIGNATURES.get(pkgName);
        return list != null ? list : Collections.EMPTY_LIST;
    }
}
