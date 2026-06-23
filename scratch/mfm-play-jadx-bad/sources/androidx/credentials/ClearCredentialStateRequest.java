package androidx.credentials;

import android.os.Bundle;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ClearCredentialStateRequest.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0011\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Landroidx/credentials/ClearCredentialStateRequest;", "", "requestType", "", "(Ljava/lang/String;)V", "requestBundle", "Landroid/os/Bundle;", "getRequestBundle", "()Landroid/os/Bundle;", "getRequestType", "()Ljava/lang/String;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class ClearCredentialStateRequest {
    private static final String BUNDLE_KEY_CLEAR_RESTORE_CREDENTIAL_REQUEST = "androidx.credentials.BUNDLE_KEY_CLEAR_RESTORE_CREDENTIAL_REQUEST";
    public static final String TYPE_CLEAR_CREDENTIAL_STATE = "androidx.credentials.TYPE_CLEAR_CREDENTIAL_STATE";
    public static final String TYPE_CLEAR_RESTORE_CREDENTIAL = "androidx.credentials.TYPE_CLEAR_RESTORE_CREDENTIAL";
    private final Bundle requestBundle;
    private final String requestType;

    /* JADX DEBUG: Multi-variable search result rejected for r0v1, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    public ClearCredentialStateRequest() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public ClearCredentialStateRequest(String requestType) {
        Intrinsics.checkNotNullParameter(requestType, "requestType");
        this.requestType = requestType;
        Bundle bundle = new Bundle();
        this.requestBundle = bundle;
        if (!Intrinsics.areEqual(requestType, TYPE_CLEAR_CREDENTIAL_STATE) && !Intrinsics.areEqual(requestType, TYPE_CLEAR_RESTORE_CREDENTIAL)) {
            throw new IllegalArgumentException("The request type " + requestType + " is not supported.");
        }
        if (Intrinsics.areEqual(requestType, TYPE_CLEAR_RESTORE_CREDENTIAL)) {
            bundle.putBoolean(BUNDLE_KEY_CLEAR_RESTORE_CREDENTIAL_REQUEST, true);
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0006: CONSTRUCTOR 
  (wrap:java.lang.String:?: TERNARY null = ((wrap:int:0x0000: ARITH (r2v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (wrap:java.lang.String:0x0004: SGET  A[WRAPPED] (LINE:36) androidx.credentials.ClearCredentialStateRequest.TYPE_CLEAR_CREDENTIAL_STATE java.lang.String) : (r1v0 java.lang.String))
 A[MD:(java.lang.String):void (m)] (LINE:36) call: androidx.credentials.ClearCredentialStateRequest.<init>(java.lang.String):void type: THIS */
    public /* synthetic */ ClearCredentialStateRequest(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? TYPE_CLEAR_CREDENTIAL_STATE : str);
    }

    public final String getRequestType() {
        return this.requestType;
    }

    public final Bundle getRequestBundle() {
        return this.requestBundle;
    }
}
