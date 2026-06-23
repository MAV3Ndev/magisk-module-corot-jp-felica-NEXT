package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip;

/* JADX INFO: loaded from: classes.dex */
public interface BindFelicaListener {
    void notifyFailed(BindException bindException);

    void notifySucceeded();
}
