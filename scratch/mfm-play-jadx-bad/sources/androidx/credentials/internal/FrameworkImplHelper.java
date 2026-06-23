package androidx.credentials.internal;

import android.credentials.Credential;
import android.credentials.GetCredentialRequest;
import android.os.Bundle;
import androidx.credentials.Credential;
import androidx.credentials.Credential$Companion$$ExternalSyntheticApiModelOutline0;
import androidx.credentials.CredentialOption;
import androidx.credentials.GetCredentialResponse;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: FrameworkImplHelper.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Landroidx/credentials/internal/FrameworkImplHelper;", "", "()V", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class FrameworkImplHelper {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @JvmStatic
    public static final GetCredentialRequest convertGetRequestToFrameworkClass(androidx.credentials.GetCredentialRequest getCredentialRequest) {
        return INSTANCE.convertGetRequestToFrameworkClass(getCredentialRequest);
    }

    @JvmStatic
    public static final GetCredentialResponse convertGetResponseToJetpackClass(android.credentials.GetCredentialResponse getCredentialResponse) {
        return INSTANCE.convertGetResponseToJetpackClass(getCredentialResponse);
    }

    @JvmStatic
    public static final void setOriginForGetRequest(androidx.credentials.GetCredentialRequest getCredentialRequest, GetCredentialRequest.Builder builder) {
        INSTANCE.setOriginForGetRequest(getCredentialRequest, builder);
    }

    /* JADX INFO: compiled from: FrameworkImplHelper.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0007¨\u0006\u000f"}, d2 = {"Landroidx/credentials/internal/FrameworkImplHelper$Companion;", "", "()V", "convertGetRequestToFrameworkClass", "Landroid/credentials/GetCredentialRequest;", "request", "Landroidx/credentials/GetCredentialRequest;", "convertGetResponseToJetpackClass", "Landroidx/credentials/GetCredentialResponse;", "response", "Landroid/credentials/GetCredentialResponse;", "setOriginForGetRequest", "", "builder", "Landroid/credentials/GetCredentialRequest$Builder;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR  A[MD:():void (m)] call: androidx.credentials.internal.FrameworkImplHelper.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final GetCredentialResponse convertGetResponseToJetpackClass(android.credentials.GetCredentialResponse response) {
            Intrinsics.checkNotNullParameter(response, "response");
            Credential credential = response.getCredential();
            Intrinsics.checkNotNullExpressionValue(credential, "response.credential");
            Credential.Companion companion = androidx.credentials.Credential.INSTANCE;
            String type = credential.getType();
            Intrinsics.checkNotNullExpressionValue(type, "credential.type");
            Bundle data = credential.getData();
            Intrinsics.checkNotNullExpressionValue(data, "credential.data");
            return new GetCredentialResponse(companion.createFrom(type, data));
        }

        @JvmStatic
        public final GetCredentialRequest convertGetRequestToFrameworkClass(androidx.credentials.GetCredentialRequest request) {
            Intrinsics.checkNotNullParameter(request, "request");
            Credential$Companion$$ExternalSyntheticApiModelOutline0.m190m();
            GetCredentialRequest.Builder builderM177m = Credential$Companion$$ExternalSyntheticApiModelOutline0.m177m(androidx.credentials.GetCredentialRequest.INSTANCE.getRequestMetadataBundle(request));
            for (CredentialOption credentialOption : request.getCredentialOptions()) {
                Credential$Companion$$ExternalSyntheticApiModelOutline0.m196m$1();
                builderM177m.addCredentialOption(Credential$Companion$$ExternalSyntheticApiModelOutline0.m174m(credentialOption.getType(), credentialOption.getRequestData(), credentialOption.getCandidateQueryData()).setIsSystemProviderRequired(credentialOption.getIsSystemProviderRequired()).setAllowedProviders(credentialOption.getAllowedProviders()).build());
            }
            setOriginForGetRequest(request, builderM177m);
            GetCredentialRequest getCredentialRequestBuild = builderM177m.build();
            Intrinsics.checkNotNullExpressionValue(getCredentialRequestBuild, "builder.build()");
            return getCredentialRequestBuild;
        }

        @JvmStatic
        public final void setOriginForGetRequest(androidx.credentials.GetCredentialRequest request, GetCredentialRequest.Builder builder) {
            Intrinsics.checkNotNullParameter(request, "request");
            Intrinsics.checkNotNullParameter(builder, "builder");
            if (request.getOrigin() != null) {
                builder.setOrigin(request.getOrigin());
            }
        }
    }
}
