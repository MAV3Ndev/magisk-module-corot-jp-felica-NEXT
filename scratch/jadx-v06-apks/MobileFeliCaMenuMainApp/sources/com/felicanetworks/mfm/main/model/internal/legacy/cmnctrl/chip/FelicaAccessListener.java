package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;

/* JADX INFO: loaded from: classes.dex */
public interface FelicaAccessListener {
    void errorResult(MfcException mfcException);

    void finishResult();
}
