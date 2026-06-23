package androidx.credentials.provider;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.biometric.BiometricPrompt;
import androidx.credentials.provider.utils.CryptoObjectUtils;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BiometricPromptData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u0000 \u00112\u00020\u0001:\u0004\u000e\u000f\u0010\u0011B\u001d\b\u0017\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B'\b\u0000\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Landroidx/credentials/provider/BiometricPromptData;", "", "cryptoObject", "Landroidx/biometric/BiometricPrompt$CryptoObject;", "allowedAuthenticators", "", "(Landroidx/biometric/BiometricPrompt$CryptoObject;I)V", "isCreatedFromBundle", "", "(Landroidx/biometric/BiometricPrompt$CryptoObject;IZ)V", "getAllowedAuthenticators", "()I", "getCryptoObject", "()Landroidx/biometric/BiometricPrompt$CryptoObject;", "Api35Impl", "ApiMinImpl", "Builder", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class BiometricPromptData {
    public static final String BUNDLE_HINT_ALLOWED_AUTHENTICATORS = "androidx.credentials.provider.BUNDLE_HINT_ALLOWED_AUTHENTICATORS";
    public static final String BUNDLE_HINT_CRYPTO_OP_ID = "androidx.credentials.provider.BUNDLE_HINT_CRYPTO_OP_ID";
    private static final String TAG = "BiometricPromptData";
    private final int allowedAuthenticators;
    private final BiometricPrompt.CryptoObject cryptoObject;
    private boolean isCreatedFromBundle;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Set<Integer> ALLOWED_AUTHENTICATOR_VALUES = SetsKt.setOf((Object[]) new Integer[]{15, 255, 32768, 32783, 33023});

    /* JADX DEBUG: Multi-variable search result rejected for r2v1, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    public BiometricPromptData() {
        this(null, 0, 3, 0 == true ? 1 : 0);
    }

    public BiometricPromptData(BiometricPrompt.CryptoObject cryptoObject) {
        this(cryptoObject, 0, 2, null);
    }

    @JvmStatic
    public static final BiometricPromptData fromBundle(Bundle bundle) {
        return INSTANCE.fromBundle(bundle);
    }

    @JvmStatic
    public static final Bundle toBundle(BiometricPromptData biometricPromptData) {
        return INSTANCE.toBundle(biometricPromptData);
    }

    public BiometricPromptData(BiometricPrompt.CryptoObject cryptoObject, int i, boolean z) {
        this.cryptoObject = cryptoObject;
        this.allowedAuthenticators = i;
        this.isCreatedFromBundle = z;
        if (!z && !ALLOWED_AUTHENTICATOR_VALUES.contains(Integer.valueOf(i))) {
            throw new IllegalArgumentException("The allowed authenticator must be specified according to the BiometricPrompt spec.".toString());
        }
        if (cryptoObject != null && !INSTANCE.isStrongAuthenticationType(Integer.valueOf(i))) {
            throw new IllegalArgumentException("If the cryptoObject is non-null, the allowedAuthenticator value must be Authenticators.BIOMETRIC_STRONG.".toString());
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0010: CONSTRUCTOR 
  (wrap:androidx.biometric.BiometricPrompt$CryptoObject:?: TERNARY null = ((wrap:int:0x0000: ARITH (r4v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (null androidx.biometric.BiometricPrompt$CryptoObject) : (r1v0 androidx.biometric.BiometricPrompt$CryptoObject))
  (wrap:int:?: TERNARY null = ((wrap:int:0x0005: ARITH (r4v0 int) & (2 int) A[WRAPPED]) != (0 int)) ? (255 int) : (r2v0 int))
  (wrap:boolean:?: TERNARY null = ((wrap:int:0x000b: ARITH (r4v0 int) & (4 int) A[WRAPPED]) != (0 int)) ? false : (r3v0 boolean))
 A[MD:(androidx.biometric.BiometricPrompt$CryptoObject, int, boolean):void (m)] (LINE:66) call: androidx.credentials.provider.BiometricPromptData.<init>(androidx.biometric.BiometricPrompt$CryptoObject, int, boolean):void type: THIS */
    public /* synthetic */ BiometricPromptData(BiometricPrompt.CryptoObject cryptoObject, int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : cryptoObject, (i2 & 2) != 0 ? 255 : i, (i2 & 4) != 0 ? false : z);
    }

    public final BiometricPrompt.CryptoObject getCryptoObject() {
        return this.cryptoObject;
    }

    public final int getAllowedAuthenticators() {
        return this.allowedAuthenticators;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x000b: CONSTRUCTOR 
  (wrap:androidx.biometric.BiometricPrompt$CryptoObject:?: TERNARY null = ((wrap:int:0x0000: ARITH (r3v0 int) & (1 int) A[WRAPPED]) != (0 int)) ? (null androidx.biometric.BiometricPrompt$CryptoObject) : (r1v0 androidx.biometric.BiometricPrompt$CryptoObject))
  (wrap:int:?: TERNARY null = ((wrap:int:0x0005: ARITH (r3v0 int) & (2 int) A[WRAPPED]) != (0 int)) ? (255 int) : (r2v0 int))
 A[MD:(androidx.biometric.BiometricPrompt$CryptoObject, int):void (m)] (LINE:105) call: androidx.credentials.provider.BiometricPromptData.<init>(androidx.biometric.BiometricPrompt$CryptoObject, int):void type: THIS */
    public /* synthetic */ BiometricPromptData(BiometricPrompt.CryptoObject cryptoObject, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : cryptoObject, (i2 & 2) != 0 ? 255 : i);
    }

    public BiometricPromptData(BiometricPrompt.CryptoObject cryptoObject, int i) {
        this(cryptoObject, i, false);
    }

    /* JADX INFO: compiled from: BiometricPromptData.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0007J\u0017\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0002\u0010\u0011J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u000bH\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0080T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Landroidx/credentials/provider/BiometricPromptData$Companion;", "", "()V", "ALLOWED_AUTHENTICATOR_VALUES", "", "", "BUNDLE_HINT_ALLOWED_AUTHENTICATORS", "", "BUNDLE_HINT_CRYPTO_OP_ID", "TAG", "fromBundle", "Landroidx/credentials/provider/BiometricPromptData;", "bundle", "Landroid/os/Bundle;", "isStrongAuthenticationType", "", "authenticationTypes", "(Ljava/lang/Integer;)Z", "toBundle", "biometricPromptData", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.provider.BiometricPromptData.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final BiometricPromptData fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            try {
                if (!bundle.containsKey(BiometricPromptData.BUNDLE_HINT_ALLOWED_AUTHENTICATORS)) {
                    throw new IllegalArgumentException("Bundle lacks allowed authenticator key.");
                }
                if (Build.VERSION.SDK_INT >= 35) {
                    return Api35Impl.fromBundle(bundle);
                }
                return ApiMinImpl.fromBundle(bundle);
            } catch (Exception e) {
                Log.i(BiometricPromptData.TAG, "fromSlice failed with: " + e.getMessage());
                return null;
            }
        }

        @JvmStatic
        public final Bundle toBundle(BiometricPromptData biometricPromptData) {
            Intrinsics.checkNotNullParameter(biometricPromptData, "biometricPromptData");
            if (Build.VERSION.SDK_INT >= 35) {
                return Api35Impl.toBundle(biometricPromptData);
            }
            return ApiMinImpl.toBundle(biometricPromptData);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isStrongAuthenticationType(Integer authenticationTypes) {
            return authenticationTypes != null && (authenticationTypes.intValue() & 240) == 0;
        }
    }

    /* JADX INFO: compiled from: BiometricPromptData.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u0010\u0010\u000b\u001a\u00020\u00002\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007R\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0005R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Landroidx/credentials/provider/BiometricPromptData$Builder;", "", "()V", "allowedAuthenticators", "", "Ljava/lang/Integer;", "cryptoObject", "Landroidx/biometric/BiometricPrompt$CryptoObject;", "build", "Landroidx/credentials/provider/BiometricPromptData;", "setAllowedAuthenticators", "setCryptoObject", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder {
        private Integer allowedAuthenticators;
        private BiometricPrompt.CryptoObject cryptoObject;

        public final Builder setCryptoObject(BiometricPrompt.CryptoObject cryptoObject) {
            this.cryptoObject = cryptoObject;
            return this;
        }

        public final Builder setAllowedAuthenticators(int allowedAuthenticators) {
            this.allowedAuthenticators = Integer.valueOf(allowedAuthenticators);
            return this;
        }

        public final BiometricPromptData build() {
            Integer num = this.allowedAuthenticators;
            return new BiometricPromptData(this.cryptoObject, num != null ? num.intValue() : 255);
        }
    }

    /* JADX INFO: compiled from: BiometricPromptData.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0007¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/BiometricPromptData$ApiMinImpl;", "", "()V", "fromBundle", "Landroidx/credentials/provider/BiometricPromptData;", "bundle", "Landroid/os/Bundle;", "toBundle", "biometricPromptData", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class ApiMinImpl {
        public static final ApiMinImpl INSTANCE = new ApiMinImpl();

        private ApiMinImpl() {
        }

        @JvmStatic
        public static final Bundle toBundle(BiometricPromptData biometricPromptData) {
            Intrinsics.checkNotNullParameter(biometricPromptData, "biometricPromptData");
            Bundle bundle = new Bundle();
            bundle.putInt(BiometricPromptData.BUNDLE_HINT_ALLOWED_AUTHENTICATORS, biometricPromptData.getAllowedAuthenticators());
            return bundle;
        }

        @JvmStatic
        public static final BiometricPromptData fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            return new BiometricPromptData(null, bundle.getInt(BiometricPromptData.BUNDLE_HINT_ALLOWED_AUTHENTICATORS), true, 1, null);
        }
    }

    /* JADX INFO: compiled from: BiometricPromptData.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0004H\u0007¨\u0006\t"}, d2 = {"Landroidx/credentials/provider/BiometricPromptData$Api35Impl;", "", "()V", "fromBundle", "Landroidx/credentials/provider/BiometricPromptData;", "bundle", "Landroid/os/Bundle;", "toBundle", "biometricPromptData", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    private static final class Api35Impl {
        public static final Api35Impl INSTANCE = new Api35Impl();

        private Api35Impl() {
        }

        @JvmStatic
        public static final Bundle toBundle(BiometricPromptData biometricPromptData) {
            Intrinsics.checkNotNullParameter(biometricPromptData, "biometricPromptData");
            Bundle bundle = new Bundle();
            bundle.putInt(BiometricPromptData.BUNDLE_HINT_ALLOWED_AUTHENTICATORS, biometricPromptData.getAllowedAuthenticators());
            if (biometricPromptData.getCryptoObject() != null) {
                bundle.putLong(BiometricPromptData.BUNDLE_HINT_CRYPTO_OP_ID, CryptoObjectUtils.INSTANCE.getOperationHandle(biometricPromptData.getCryptoObject()));
            }
            return bundle;
        }

        @JvmStatic
        public static final BiometricPromptData fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            return new BiometricPromptData(null, bundle.getInt(BiometricPromptData.BUNDLE_HINT_ALLOWED_AUTHENTICATORS), true);
        }
    }
}
