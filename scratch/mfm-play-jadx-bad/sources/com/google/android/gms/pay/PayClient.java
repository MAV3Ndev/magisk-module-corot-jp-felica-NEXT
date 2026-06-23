package com.google.android.gms.pay;

import android.app.Activity;
import android.app.PendingIntent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.HasApiKey;
import com.google.android.gms.tasks.Task;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public interface PayClient extends HasApiKey<Api.ApiOptions.NotRequiredOptions> {
    public static final String EXTRA_API_ERROR_MESSAGE = "extra_api_error_message";

    /* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
    public enum ProductName {
        GOOGLE_PAY,
        GOOGLE_WALLET
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
    public @interface RequestType {
        public static final int CARD_PROVISIONING_DEEP_LINK = 1;
        public static final int SAVE_PASSES = 2;
        public static final int SAVE_PASSES_JWT = 3;
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
    public @interface SavePassesResult {
        public static final int API_UNAVAILABLE = 1;
        public static final int INTERNAL_ERROR = 3;
        public static final int SAVE_ERROR = 2;
    }

    /* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
    public @interface WearWalletIntentSource {
        public static final int OOBE = 20;
        public static final int SETTINGS = 21;
    }

    Task<EmoneyReadiness> checkReadinessForEmoney(String str, String str2);

    Task<Integer> getPayApiAvailabilityStatus(int i);

    Task<PendingIntent> getPendingIntentForWalletOnWear(String str, int i);

    ProductName getProductName();

    Task<Void> notifyCardTapEvent(String str);

    Task<Void> notifyEmoneyCardStatusUpdate(String str);

    Task<Void> pushEmoneyCard(String str, ActivityResultLauncher<IntentSenderRequest> activityResultLauncher);

    void savePasses(String str, Activity activity, int i);

    void savePassesJwt(String str, Activity activity, int i);
}
