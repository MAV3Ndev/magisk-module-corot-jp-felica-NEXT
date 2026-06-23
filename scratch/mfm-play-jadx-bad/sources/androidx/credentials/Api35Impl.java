package androidx.credentials;

import android.credentials.GetCredentialException;
import android.os.OutcomeReceiver;
import android.util.Log;
import android.view.View;
import androidx.credentials.internal.FrameworkImplHelper;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CredentialManagerViewHandler.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J*\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00040\u000b¨\u0006\r"}, d2 = {"Landroidx/credentials/Api35Impl;", "", "()V", "clearPendingGetCredentialRequest", "", "view", "Landroid/view/View;", "setPendingGetCredentialRequest", "request", "Landroidx/credentials/GetCredentialRequest;", "callback", "Lkotlin/Function1;", "Landroidx/credentials/GetCredentialResponse;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
final class Api35Impl {
    public static final Api35Impl INSTANCE = new Api35Impl();

    private Api35Impl() {
    }

    public final void setPendingGetCredentialRequest(View view, GetCredentialRequest request, final Function1<? super GetCredentialResponse, Unit> callback) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(callback, "callback");
        view.setPendingCredentialRequest(FrameworkImplHelper.INSTANCE.convertGetRequestToFrameworkClass(request), new OutcomeReceiver<android.credentials.GetCredentialResponse, GetCredentialException>() { // from class: androidx.credentials.Api35Impl$setPendingGetCredentialRequest$frameworkCallback$1
            /* JADX DEBUG: Method merged with bridge method: onResult(Ljava/lang/Object;)V */
            public void onResult(android.credentials.GetCredentialResponse response) {
                Intrinsics.checkNotNullParameter(response, "response");
                callback.invoke(FrameworkImplHelper.INSTANCE.convertGetResponseToJetpackClass(response));
            }

            /* JADX DEBUG: Method merged with bridge method: onError(Ljava/lang/Throwable;)V */
            public void onError(GetCredentialException error) {
                Intrinsics.checkNotNullParameter(error, "error");
                Log.w("ViewHandler", "Error: " + error.getType() + " , " + error.getMessage());
            }
        });
    }

    public final void clearPendingGetCredentialRequest(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        view.clearPendingCredentialRequest();
    }
}
