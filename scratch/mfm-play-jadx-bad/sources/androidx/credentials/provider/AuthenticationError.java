package androidx.credentials.provider;

import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AuthenticationError.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\r\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u000e\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0010"}, d2 = {"Landroidx/credentials/provider/AuthenticationError;", "", "errorCode", "", "errorMsg", "", "(ILjava/lang/CharSequence;)V", "getErrorCode", "()I", "getErrorMsg", "()Ljava/lang/CharSequence;", "equals", "", "other", "hashCode", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class AuthenticationError {
    public static final String EXTRA_BIOMETRIC_AUTH_ERROR = "androidx.credentials.provider.BIOMETRIC_AUTH_ERROR_CODE";
    public static final String EXTRA_BIOMETRIC_AUTH_ERROR_FALLBACK = "BIOMETRIC_AUTH_ERROR_CODE";
    public static final String EXTRA_BIOMETRIC_AUTH_ERROR_MESSAGE = "androidx.credentials.provider.BIOMETRIC_AUTH_ERROR_MESSAGE";
    public static final String EXTRA_BIOMETRIC_AUTH_ERROR_MESSAGE_FALLBACK = "BIOMETRIC_AUTH_ERROR_MESSAGE";
    private final int errorCode;
    private final CharSequence errorMsg;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG = "AuthenticationError";
    private static final LinkedHashMap<Integer, Integer> biometricFrameworkToJetpackErrorMap = MapsKt.linkedMapOf(TuplesKt.to(5, 5), TuplesKt.to(12, 12), TuplesKt.to(1, 1), TuplesKt.to(7, 7), TuplesKt.to(9, 9), TuplesKt.to(11, 11), TuplesKt.to(14, 14), TuplesKt.to(4, 4), TuplesKt.to(15, 15), TuplesKt.to(3, 3), TuplesKt.to(2, 2), TuplesKt.to(10, 10), TuplesKt.to(8, 8));

    /* JADX DEBUG: Multi-variable search result rejected for r0v1, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    public AuthenticationError(int i) {
        this(i, null, 2, 0 == true ? 1 : 0);
    }

    public AuthenticationError(int i, CharSequence charSequence) {
        this.errorCode = i;
        this.errorMsg = charSequence;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0005: CONSTRUCTOR 
  (r1v0 int)
  (wrap:java.lang.CharSequence:?: TERNARY null = ((wrap:int:0x0000: ARITH (r3v0 int) & (2 int) A[WRAPPED]) != (0 int)) ? (null java.lang.CharSequence) : (r2v0 java.lang.CharSequence))
 A[MD:(int, java.lang.CharSequence):void (m)] (LINE:40) call: androidx.credentials.provider.AuthenticationError.<init>(int, java.lang.CharSequence):void type: THIS */
    public /* synthetic */ AuthenticationError(int i, CharSequence charSequence, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : charSequence);
    }

    public final int getErrorCode() {
        return this.errorCode;
    }

    public final CharSequence getErrorMsg() {
        return this.errorMsg;
    }

    /* JADX INFO: compiled from: AuthenticationError.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0015\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\rH\u0000¢\u0006\u0002\b\u0013J'\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001aH\u0001¢\u0006\u0002\b\u001bR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\u0004X\u0080D¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR2\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\fj\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r`\u000e8\u0000X\u0081\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001c"}, d2 = {"Landroidx/credentials/provider/AuthenticationError$Companion;", "", "()V", "EXTRA_BIOMETRIC_AUTH_ERROR", "", "EXTRA_BIOMETRIC_AUTH_ERROR_FALLBACK", "EXTRA_BIOMETRIC_AUTH_ERROR_MESSAGE", "EXTRA_BIOMETRIC_AUTH_ERROR_MESSAGE_FALLBACK", "TAG", "getTAG$credentials_release", "()Ljava/lang/String;", "biometricFrameworkToJetpackErrorMap", "Ljava/util/LinkedHashMap;", "", "Lkotlin/collections/LinkedHashMap;", "getBiometricFrameworkToJetpackErrorMap$credentials_release", "()Ljava/util/LinkedHashMap;", "convertFrameworkBiometricErrorToJetpack", "frameworkCode", "convertFrameworkBiometricErrorToJetpack$credentials_release", "createFrom", "Landroidx/credentials/provider/AuthenticationError;", "uiErrorCode", "uiErrorMessage", "", "isFrameworkBiometricPrompt", "", "createFrom$credentials_release", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.provider.AuthenticationError.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AuthenticationError createFrom$credentials_release(int i, CharSequence uiErrorMessage) {
            Intrinsics.checkNotNullParameter(uiErrorMessage, "uiErrorMessage");
            return createFrom$credentials_release$default(this, i, uiErrorMessage, false, 4, null);
        }

        private Companion() {
        }

        public final String getTAG$credentials_release() {
            return AuthenticationError.TAG;
        }

        public final LinkedHashMap<Integer, Integer> getBiometricFrameworkToJetpackErrorMap$credentials_release() {
            return AuthenticationError.biometricFrameworkToJetpackErrorMap;
        }

        public final int convertFrameworkBiometricErrorToJetpack$credentials_release(int frameworkCode) {
            if (getBiometricFrameworkToJetpackErrorMap$credentials_release().containsKey(Integer.valueOf(frameworkCode))) {
                Integer num = getBiometricFrameworkToJetpackErrorMap$credentials_release().get(Integer.valueOf(frameworkCode));
                Intrinsics.checkNotNull(num);
                Intrinsics.checkNotNullExpressionValue(num, "{\n                biomet…workCode]!!\n            }");
                return num.intValue();
            }
            Log.i(getTAG$credentials_release(), "Unexpected error code, " + frameworkCode + ", ");
            return frameworkCode;
        }

        public static /* synthetic */ AuthenticationError createFrom$credentials_release$default(Companion companion, int i, CharSequence charSequence, boolean z, int i2, Object obj) {
            if ((i2 & 4) != 0) {
                z = true;
            }
            return companion.createFrom$credentials_release(i, charSequence, z);
        }

        public final AuthenticationError createFrom$credentials_release(int uiErrorCode, CharSequence uiErrorMessage, boolean isFrameworkBiometricPrompt) {
            Intrinsics.checkNotNullParameter(uiErrorMessage, "uiErrorMessage");
            if (isFrameworkBiometricPrompt) {
                uiErrorCode = convertFrameworkBiometricErrorToJetpack$credentials_release(uiErrorCode);
            }
            return new AuthenticationError(uiErrorCode, uiErrorMessage);
        }
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof AuthenticationError) {
            AuthenticationError authenticationError = (AuthenticationError) other;
            if (this.errorCode == authenticationError.errorCode && Intrinsics.areEqual(this.errorMsg, authenticationError.errorMsg)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.errorCode), this.errorMsg);
    }
}
