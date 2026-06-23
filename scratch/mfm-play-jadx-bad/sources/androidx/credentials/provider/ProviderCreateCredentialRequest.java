package androidx.credentials.provider;

import android.os.Bundle;
import androidx.credentials.CreateCredentialRequest;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ProviderCreateCredentialRequest.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB#\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0010"}, d2 = {"Landroidx/credentials/provider/ProviderCreateCredentialRequest;", "", "callingRequest", "Landroidx/credentials/CreateCredentialRequest;", "callingAppInfo", "Landroidx/credentials/provider/CallingAppInfo;", "biometricPromptResult", "Landroidx/credentials/provider/BiometricPromptResult;", "(Landroidx/credentials/CreateCredentialRequest;Landroidx/credentials/provider/CallingAppInfo;Landroidx/credentials/provider/BiometricPromptResult;)V", "getBiometricPromptResult", "()Landroidx/credentials/provider/BiometricPromptResult;", "getCallingAppInfo", "()Landroidx/credentials/provider/CallingAppInfo;", "getCallingRequest", "()Landroidx/credentials/CreateCredentialRequest;", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class ProviderCreateCredentialRequest {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String EXTRA_CREATE_CREDENTIAL_REQUEST_TYPE = "androidx.credentials.provider.extra.CREATE_CREDENTIAL_REQUEST_TYPE";
    private static final String EXTRA_CREATE_REQUEST_CANDIDATE_QUERY_DATA = "androidx.credentials.provider.extra.CREATE_REQUEST_CANDIDATE_QUERY_DATA";
    private static final String EXTRA_CREATE_REQUEST_CREDENTIAL_DATA = "androidx.credentials.provider.extra.CREATE_REQUEST_CREDENTIAL_DATA";
    private final BiometricPromptResult biometricPromptResult;
    private final CallingAppInfo callingAppInfo;
    private final CreateCredentialRequest callingRequest;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ProviderCreateCredentialRequest(CreateCredentialRequest callingRequest, CallingAppInfo callingAppInfo) {
        this(callingRequest, callingAppInfo, null, 4, null);
        Intrinsics.checkNotNullParameter(callingRequest, "callingRequest");
        Intrinsics.checkNotNullParameter(callingAppInfo, "callingAppInfo");
    }

    @JvmStatic
    public static final Bundle asBundle(ProviderCreateCredentialRequest providerCreateCredentialRequest) {
        return INSTANCE.asBundle(providerCreateCredentialRequest);
    }

    @JvmStatic
    public static final ProviderCreateCredentialRequest fromBundle(Bundle bundle) {
        return INSTANCE.fromBundle(bundle);
    }

    public ProviderCreateCredentialRequest(CreateCredentialRequest callingRequest, CallingAppInfo callingAppInfo, BiometricPromptResult biometricPromptResult) {
        Intrinsics.checkNotNullParameter(callingRequest, "callingRequest");
        Intrinsics.checkNotNullParameter(callingAppInfo, "callingAppInfo");
        this.callingRequest = callingRequest;
        this.callingAppInfo = callingAppInfo;
        this.biometricPromptResult = biometricPromptResult;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0005: CONSTRUCTOR 
  (r1v0 androidx.credentials.CreateCredentialRequest)
  (r2v0 androidx.credentials.provider.CallingAppInfo)
  (wrap:androidx.credentials.provider.BiometricPromptResult:?: TERNARY null = ((wrap:int:0x0000: ARITH (r4v0 int) & (4 int) A[WRAPPED]) != (0 int)) ? (null androidx.credentials.provider.BiometricPromptResult) : (r3v0 androidx.credentials.provider.BiometricPromptResult))
 A[MD:(androidx.credentials.CreateCredentialRequest, androidx.credentials.provider.CallingAppInfo, androidx.credentials.provider.BiometricPromptResult):void (m)] (LINE:43) call: androidx.credentials.provider.ProviderCreateCredentialRequest.<init>(androidx.credentials.CreateCredentialRequest, androidx.credentials.provider.CallingAppInfo, androidx.credentials.provider.BiometricPromptResult):void type: THIS */
    public /* synthetic */ ProviderCreateCredentialRequest(CreateCredentialRequest createCredentialRequest, CallingAppInfo callingAppInfo, BiometricPromptResult biometricPromptResult, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(createCredentialRequest, callingAppInfo, (i & 4) != 0 ? null : biometricPromptResult);
    }

    public final CreateCredentialRequest getCallingRequest() {
        return this.callingRequest;
    }

    public final CallingAppInfo getCallingAppInfo() {
        return this.callingAppInfo;
    }

    public final BiometricPromptResult getBiometricPromptResult() {
        return this.biometricPromptResult;
    }

    /* JADX INFO: compiled from: ProviderCreateCredentialRequest.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Landroidx/credentials/provider/ProviderCreateCredentialRequest$Companion;", "", "()V", "EXTRA_CREATE_CREDENTIAL_REQUEST_TYPE", "", "EXTRA_CREATE_REQUEST_CANDIDATE_QUERY_DATA", "EXTRA_CREATE_REQUEST_CREDENTIAL_DATA", "asBundle", "Landroid/os/Bundle;", "request", "Landroidx/credentials/provider/ProviderCreateCredentialRequest;", "fromBundle", "bundle", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.provider.ProviderCreateCredentialRequest.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Bundle asBundle(ProviderCreateCredentialRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            Bundle bundle = new Bundle();
            bundle.putString(ProviderCreateCredentialRequest.EXTRA_CREATE_CREDENTIAL_REQUEST_TYPE, request.getCallingRequest().getType());
            bundle.putBundle(ProviderCreateCredentialRequest.EXTRA_CREATE_REQUEST_CREDENTIAL_DATA, request.getCallingRequest().getCredentialData());
            bundle.putBundle(ProviderCreateCredentialRequest.EXTRA_CREATE_REQUEST_CANDIDATE_QUERY_DATA, request.getCallingRequest().getCandidateQueryData());
            CallingAppInfo.INSTANCE.setCallingAppInfo$credentials_release(bundle, request.getCallingAppInfo());
            return bundle;
        }

        @JvmStatic
        public final ProviderCreateCredentialRequest fromBundle(Bundle bundle) {
            Intrinsics.checkNotNullParameter(bundle, "bundle");
            String string = bundle.getString(ProviderCreateCredentialRequest.EXTRA_CREATE_CREDENTIAL_REQUEST_TYPE);
            if (string == null) {
                throw new IllegalArgumentException("Bundle was missing request type.");
            }
            Bundle bundle2 = bundle.getBundle(ProviderCreateCredentialRequest.EXTRA_CREATE_REQUEST_CREDENTIAL_DATA);
            if (bundle2 == null) {
                bundle2 = new Bundle();
            }
            Bundle bundle3 = bundle2;
            Bundle bundle4 = bundle.getBundle(ProviderCreateCredentialRequest.EXTRA_CREATE_REQUEST_CANDIDATE_QUERY_DATA);
            if (bundle4 == null) {
                bundle4 = new Bundle();
            }
            Bundle bundle5 = bundle4;
            String string2 = bundle.getString(CallingAppInfo.EXTRA_CREDENTIAL_REQUEST_ORIGIN);
            CallingAppInfo callingAppInfoExtractCallingAppInfo$credentials_release = CallingAppInfo.INSTANCE.extractCallingAppInfo$credentials_release(bundle);
            if (callingAppInfoExtractCallingAppInfo$credentials_release == null) {
                throw new IllegalArgumentException("Bundle was missing CallingAppInfo.");
            }
            try {
                return new ProviderCreateCredentialRequest(CreateCredentialRequest.INSTANCE.createFrom(string, bundle3, bundle5, false, string2), callingAppInfoExtractCallingAppInfo$credentials_release, null, 4, null);
            } catch (Exception e) {
                throw new IllegalArgumentException("Conversion failed with " + e);
            }
        }
    }
}
