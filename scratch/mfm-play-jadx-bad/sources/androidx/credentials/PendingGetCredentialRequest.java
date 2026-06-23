package androidx.credentials;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PendingGetCredentialRequest.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\u0002\u0010\bR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Landroidx/credentials/PendingGetCredentialRequest;", "", "request", "Landroidx/credentials/GetCredentialRequest;", "callback", "Lkotlin/Function1;", "Landroidx/credentials/GetCredentialResponse;", "", "(Landroidx/credentials/GetCredentialRequest;Lkotlin/jvm/functions/Function1;)V", "getCallback", "()Lkotlin/jvm/functions/Function1;", "getRequest", "()Landroidx/credentials/GetCredentialRequest;", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class PendingGetCredentialRequest {
    private final Function1<GetCredentialResponse, Unit> callback;
    private final GetCredentialRequest request;

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: kotlin.jvm.functions.Function1<? super androidx.credentials.GetCredentialResponse, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public PendingGetCredentialRequest(GetCredentialRequest request, Function1<? super GetCredentialResponse, Unit> callback) {
        Intrinsics.checkNotNullParameter(request, "request");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.request = request;
        this.callback = callback;
    }

    public final GetCredentialRequest getRequest() {
        return this.request;
    }

    public final Function1<GetCredentialResponse, Unit> getCallback() {
        return this.callback;
    }
}
