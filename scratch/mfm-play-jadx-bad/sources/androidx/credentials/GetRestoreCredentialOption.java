package androidx.credentials;

import android.os.Bundle;
import androidx.credentials.internal.RequestValidationHelper;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GetRestoreCredentialOption.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Landroidx/credentials/GetRestoreCredentialOption;", "Landroidx/credentials/CredentialOption;", "requestJson", "", "(Ljava/lang/String;)V", "getRequestJson", "()Ljava/lang/String;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class GetRestoreCredentialOption extends CredentialOption {
    private static final String BUNDLE_KEY_GET_RESTORE_CREDENTIAL_REQUEST = "androidx.credentials.BUNDLE_KEY_GET_RESTORE_CREDENTIAL_REQUEST";
    private static final Companion Companion = new Companion(null);
    private final String requestJson;

    public final String getRequestJson() {
        return this.requestJson;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GetRestoreCredentialOption(String requestJson) {
        super(RestoreCredential.TYPE_RESTORE_CREDENTIAL, Companion.toRequestDataBundle(requestJson), new Bundle(), false, false, SetsKt.emptySet(), 2000);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
        this.requestJson = requestJson;
        if (!RequestValidationHelper.INSTANCE.isValidJSON(requestJson)) {
            throw new IllegalArgumentException("requestJson must not be empty, and must be a valid JSON".toString());
        }
    }

    /* JADX INFO: compiled from: GetRestoreCredentialOption.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Landroidx/credentials/GetRestoreCredentialOption$Companion;", "", "()V", "BUNDLE_KEY_GET_RESTORE_CREDENTIAL_REQUEST", "", "toRequestDataBundle", "Landroid/os/Bundle;", "requestJson", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.GetRestoreCredentialOption.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bundle toRequestDataBundle(String requestJson) {
            Bundle bundle = new Bundle();
            bundle.putString(GetRestoreCredentialOption.BUNDLE_KEY_GET_RESTORE_CREDENTIAL_REQUEST, requestJson);
            return bundle;
        }
    }
}
