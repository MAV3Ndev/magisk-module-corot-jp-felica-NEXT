package com.felicanetworks.mfc.mfi.openidconnect;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.fragment.app.Fragment;
import java.util.EventListener;

/* JADX INFO: loaded from: classes3.dex */
public abstract class OpenIdConnectFragment extends Fragment {
    public abstract void onBackPressed();

    public void setActivityResultLauncher(ActivityResultLauncher<IntentSenderRequest> launcher) {
    }

    public abstract void setListener(EventListener listener);
}
