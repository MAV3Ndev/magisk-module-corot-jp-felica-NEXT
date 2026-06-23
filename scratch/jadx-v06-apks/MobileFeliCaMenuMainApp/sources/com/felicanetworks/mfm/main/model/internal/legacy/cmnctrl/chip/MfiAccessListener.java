package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.chip;

import android.content.Intent;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcException;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiAdmin;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.User;

/* JADX INFO: loaded from: classes.dex */
public interface MfiAccessListener {
    void errorResult(MfcException mfcException);

    void onRequestActivity(Intent intent);

    void onSuccess(boolean z, User user, MfiAdmin mfiAdmin);

    void onSuccessNoLogin(MfiAdmin mfiAdmin);
}
