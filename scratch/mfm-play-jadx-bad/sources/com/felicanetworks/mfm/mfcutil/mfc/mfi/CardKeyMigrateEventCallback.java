package com.felicanetworks.mfm.mfcutil.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public interface CardKeyMigrateEventCallback extends BaseMfiEventCallback {

    /* JADX INFO: renamed from: com.felicanetworks.mfm.mfcutil.mfc.mfi.CardKeyMigrateEventCallback$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
        public static void $default$onError(CardKeyMigrateEventCallback _this, int type, String msg) {
        }
    }

    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onError(int type, String msg, Card unfinishedCard);

    void onSuccess(Card migratedCard);
}
