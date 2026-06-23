package com.felicanetworks.mfm.main.model.internal.main.mfc.em;

/* JADX INFO: loaded from: classes3.dex */
public enum CauseLocation {
    CAUGHT_EXCEPTION(1),
    ACTIVATE_CALLBACK(2),
    MFIC_API_CALLBACK(3),
    MFIC_LOGIN_RESULT_CODE(4),
    COGNITIVE(0);

    final int value;

    CauseLocation(int value) {
        this.value = value;
    }
}
