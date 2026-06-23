package com.google.android.gms.identitycredentials;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.identitycredentials.internal.InternalIdentityCredentialClient;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0001\u0004B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0005"}, d2 = {"Lcom/google/android/gms/identitycredentials/IdentityCredentialManager;", "", "<init>", "()V", "Companion", "java.com.google.android.gmscore.integ.client.identity_credentials_identity_credentials"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class IdentityCredentialManager {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    /* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u0007¨\u0006\n"}, d2 = {"Lcom/google/android/gms/identitycredentials/IdentityCredentialManager$Companion;", "", "<init>", "()V", "getClient", "Lcom/google/android/gms/identitycredentials/IdentityCredentialClient;", "activity", "Landroid/app/Activity;", "context", "Landroid/content/Context;", "java.com.google.android.gmscore.integ.client.identity_credentials_identity_credentials"}, k = 1, mv = {2, 0, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: com.google.android.gms.identitycredentials.IdentityCredentialManager.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @JvmStatic
        public final IdentityCredentialClient getClient(Activity activity) {
            Intrinsics.checkNotNullParameter(activity, "activity");
            return new InternalIdentityCredentialClient(activity);
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
        @JvmStatic
        public final IdentityCredentialClient getClient(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            return new InternalIdentityCredentialClient(context);
        }
    }

    private IdentityCredentialManager() {
    }

    @JvmStatic
    public static final IdentityCredentialClient getClient(Activity activity) {
        return INSTANCE.getClient(activity);
    }

    @JvmStatic
    public static final IdentityCredentialClient getClient(Context context) {
        return INSTANCE.getClient(context);
    }
}
