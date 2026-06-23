package androidx.credentials.provider;

import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BiometricPromptResult.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001f\b\u0000\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\bJ\u0013\u0010\u0010\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000f¨\u0006\u0014"}, d2 = {"Landroidx/credentials/provider/BiometricPromptResult;", "", "authenticationError", "Landroidx/credentials/provider/AuthenticationError;", "(Landroidx/credentials/provider/AuthenticationError;)V", "authenticationResult", "Landroidx/credentials/provider/AuthenticationResult;", "(Landroidx/credentials/provider/AuthenticationResult;)V", "(Landroidx/credentials/provider/AuthenticationResult;Landroidx/credentials/provider/AuthenticationError;)V", "getAuthenticationError", "()Landroidx/credentials/provider/AuthenticationError;", "getAuthenticationResult", "()Landroidx/credentials/provider/AuthenticationResult;", "isSuccessful", "", "()Z", "equals", "other", "hashCode", "", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class BiometricPromptResult {
    private final AuthenticationError authenticationError;
    private final AuthenticationResult authenticationResult;
    private final boolean isSuccessful;

    /* JADX DEBUG: Multi-variable search result rejected for r0v1, resolved type: java.lang.Object[] */
    /* JADX DEBUG: Multi-variable search result rejected for r0v2, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    public BiometricPromptResult() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public BiometricPromptResult(AuthenticationResult authenticationResult, AuthenticationError authenticationError) {
        this.authenticationResult = authenticationResult;
        this.authenticationError = authenticationError;
        this.isSuccessful = authenticationResult != null;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x000b: CONSTRUCTOR 
  (wrap:androidx.credentials.provider.AuthenticationResult:?: TERNARY null = ((wrap:int:0x0000: ARITH (r4v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (null androidx.credentials.provider.AuthenticationResult) : (r2v0 androidx.credentials.provider.AuthenticationResult))
  (wrap:androidx.credentials.provider.AuthenticationError:?: TERNARY null = ((wrap:int:0x0006: ARITH (r4v0 int) & (2 int) A[WRAPPED]) != (0 int)) ? (null androidx.credentials.provider.AuthenticationError) : (r3v0 androidx.credentials.provider.AuthenticationError))
 A[MD:(androidx.credentials.provider.AuthenticationResult, androidx.credentials.provider.AuthenticationError):void (m)] (LINE:37) call: androidx.credentials.provider.BiometricPromptResult.<init>(androidx.credentials.provider.AuthenticationResult, androidx.credentials.provider.AuthenticationError):void type: THIS */
    public /* synthetic */ BiometricPromptResult(AuthenticationResult authenticationResult, AuthenticationError authenticationError, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : authenticationResult, (i & 2) != 0 ? null : authenticationError);
    }

    public final AuthenticationResult getAuthenticationResult() {
        return this.authenticationResult;
    }

    public final AuthenticationError getAuthenticationError() {
        return this.authenticationError;
    }

    /* JADX INFO: renamed from: isSuccessful, reason: from getter */
    public final boolean getIsSuccessful() {
        return this.isSuccessful;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BiometricPromptResult(AuthenticationError authenticationError) {
        this(null, authenticationError);
        Intrinsics.checkNotNullParameter(authenticationError, "authenticationError");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BiometricPromptResult(AuthenticationResult authenticationResult) {
        this(authenticationResult, null);
        Intrinsics.checkNotNullParameter(authenticationResult, "authenticationResult");
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof BiometricPromptResult) {
            BiometricPromptResult biometricPromptResult = (BiometricPromptResult) other;
            if (this.isSuccessful == biometricPromptResult.isSuccessful && Intrinsics.areEqual(this.authenticationResult, biometricPromptResult.authenticationResult) && Intrinsics.areEqual(this.authenticationError, biometricPromptResult.authenticationError)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.isSuccessful), this.authenticationResult, this.authenticationError);
    }
}
