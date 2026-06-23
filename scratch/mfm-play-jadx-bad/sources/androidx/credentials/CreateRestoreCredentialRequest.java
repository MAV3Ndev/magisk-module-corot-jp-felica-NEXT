package androidx.credentials;

import android.os.Bundle;
import androidx.credentials.CreateCredentialRequest;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* JADX INFO: compiled from: CreateRestoreCredentialRequest.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0019\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u000b"}, d2 = {"Landroidx/credentials/CreateRestoreCredentialRequest;", "Landroidx/credentials/CreateCredentialRequest;", "requestJson", "", "isCloudBackupEnabled", "", "(Ljava/lang/String;Z)V", "()Z", "getRequestJson", "()Ljava/lang/String;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class CreateRestoreCredentialRequest extends CreateCredentialRequest {
    private static final String BUNDLE_KEY_CREATE_RESTORE_CREDENTIAL_REQUEST = "androidx.credentials.BUNDLE_KEY_CREATE_RESTORE_CREDENTIAL_REQUEST";
    private static final String BUNDLE_KEY_SHOULD_BACKUP_TO_CLOUD = "androidx.credentials.BUNDLE_KEY_SHOULD_BACKUP_TO_CLOUD";

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean isCloudBackupEnabled;
    private final String requestJson;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CreateRestoreCredentialRequest(String requestJson) {
        this(requestJson, false, 2, null);
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0005: CONSTRUCTOR 
  (r1v0 java.lang.String)
  (wrap:boolean:?: TERNARY null = ((wrap:int:0x0000: ARITH (r3v0 int) & (2 int) A[WRAPPED]) != (0 int)) ? true : (r2v0 boolean))
 A[MD:(java.lang.String, boolean):void (m)] (LINE:49) call: androidx.credentials.CreateRestoreCredentialRequest.<init>(java.lang.String, boolean):void type: THIS */
    public /* synthetic */ CreateRestoreCredentialRequest(String str, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? true : z);
    }

    public final String getRequestJson() {
        return this.requestJson;
    }

    /* JADX INFO: renamed from: isCloudBackupEnabled, reason: from getter */
    public final boolean getIsCloudBackupEnabled() {
        return this.isCloudBackupEnabled;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public CreateRestoreCredentialRequest(String requestJson, boolean z) {
        Intrinsics.checkNotNullParameter(requestJson, "requestJson");
        Companion companion = INSTANCE;
        super(RestoreCredential.TYPE_RESTORE_CREDENTIAL, companion.toCredentialDataBundle(requestJson, z), new Bundle(), false, false, companion.getDisplayInfoFromJson(requestJson), null, false);
        this.requestJson = requestJson;
        this.isCloudBackupEnabled = z;
    }

    /* JADX INFO: compiled from: CreateRestoreCredentialRequest.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H\u0002J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/credentials/CreateRestoreCredentialRequest$Companion;", "", "()V", "BUNDLE_KEY_CREATE_RESTORE_CREDENTIAL_REQUEST", "", "BUNDLE_KEY_SHOULD_BACKUP_TO_CLOUD", "getDisplayInfoFromJson", "Landroidx/credentials/CreateCredentialRequest$DisplayInfo;", "requestJson", "toCredentialDataBundle", "Landroid/os/Bundle;", "isCloudBackupEnabled", "", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.CreateRestoreCredentialRequest.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX DEBUG: Multi-variable search result rejected for r2v1, resolved type: java.lang.Object[] */
        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public final CreateCredentialRequest.DisplayInfo getDisplayInfoFromJson(String requestJson) {
            try {
                String string = new JSONObject(requestJson).getJSONObject("user").getString("id");
                Intrinsics.checkNotNullExpressionValue(string, "userJson.getString(\"id\")");
                return new CreateCredentialRequest.DisplayInfo((CharSequence) string, (CharSequence) null, 2, (DefaultConstructorMarker) (0 == true ? 1 : 0));
            } catch (Exception unused) {
                throw new IllegalArgumentException("user.id must be defined in requestJson");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final Bundle toCredentialDataBundle(String requestJson, boolean isCloudBackupEnabled) {
            Bundle bundle = new Bundle();
            bundle.putString(CreateRestoreCredentialRequest.BUNDLE_KEY_CREATE_RESTORE_CREDENTIAL_REQUEST, requestJson);
            bundle.putBoolean(CreateRestoreCredentialRequest.BUNDLE_KEY_SHOULD_BACKUP_TO_CLOUD, isCloudBackupEnabled);
            return bundle;
        }
    }
}
