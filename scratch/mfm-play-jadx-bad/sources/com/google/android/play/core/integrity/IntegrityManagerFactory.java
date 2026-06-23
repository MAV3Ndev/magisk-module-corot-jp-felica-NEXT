package com.google.android.play.core.integrity;

import android.content.Context;

/* JADX INFO: compiled from: com.google.android.play:integrity@@1.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public class IntegrityManagerFactory {
    private IntegrityManagerFactory() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static IntegrityManager create(Context context) {
        return z.a(context).a();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static StandardIntegrityManager createStandard(Context context) {
        return ax.a(context).a();
    }
}
