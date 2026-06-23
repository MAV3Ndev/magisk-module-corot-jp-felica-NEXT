package com.felicanetworks.mfm.main.presenter;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class PresenterData {
    public static final String EXTRA_KEY_SPECIFIC_RECOMMEND = "SpecificFirstPageRecommend";
    public static final int REQUEST_CODE_NFC = 2000;
    public static final int REQUEST_CODE_NOTIFICATION = 1000;
    private static PresenterData _instance = null;
    private static boolean isPossibleInterruptAppStart = true;
    Context _context;
    private boolean _hasNfc = true;

    private PresenterData() {
    }

    public static boolean getPossibleInterruptAppStart() {
        return isPossibleInterruptAppStart;
    }

    public static void setPossibleInterruptAppStart(boolean z) {
        isPossibleInterruptAppStart = z;
    }

    public static PresenterData getInstance() {
        if (_instance == null) {
            _instance = new PresenterData();
        }
        return _instance;
    }

    public void setContext(Context context) {
        this._context = context;
    }

    public Context getContext() {
        return this._context;
    }

    public void setHasNfc(boolean z) {
        this._hasNfc = z;
    }

    public boolean hasNFC() {
        return this._hasNfc;
    }
}
