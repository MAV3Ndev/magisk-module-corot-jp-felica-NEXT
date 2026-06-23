package com.felicanetworks.mfc.mfi.openidconnect;

import androidx.fragment.app.Fragment;
import java.util.EventListener;

/* JADX INFO: loaded from: classes.dex */
public abstract class OpenIdConnectFragment extends Fragment {
    public abstract void onBackPressed();

    public abstract void setListener(EventListener eventListener);
}
