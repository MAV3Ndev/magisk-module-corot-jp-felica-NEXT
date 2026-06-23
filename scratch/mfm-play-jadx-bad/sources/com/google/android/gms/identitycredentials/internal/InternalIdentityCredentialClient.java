package com.google.android.gms.identitycredentials.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.identitycredentials.ClearRegistryRequest;
import com.google.android.gms.identitycredentials.ClearRegistryResponse;
import com.google.android.gms.identitycredentials.GetCredentialRequest;
import com.google.android.gms.identitycredentials.IdentityCredentialClient;
import com.google.android.gms.identitycredentials.PendingGetCredentialHandle;
import com.google.android.gms.identitycredentials.RegistrationRequest;
import com.google.android.gms.identitycredentials.RegistrationResponse;
import com.google.android.gms.internal.identity_credentials.zze;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\t2\u0006\u0010\u000b\u001a\u00020\u000fH\u0016J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\t2\u0006\u0010\u000b\u001a\u00020\u0012H\u0016J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t2\u0006\u0010\u000b\u001a\u00020\u0015H\u0016J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\t2\u0006\u0010\u000b\u001a\u00020\u0018H\u0016¨\u0006\u0019"}, d2 = {"Lcom/google/android/gms/identitycredentials/internal/InternalIdentityCredentialClient;", "Lcom/google/android/gms/common/api/GoogleApi;", "Lcom/google/android/gms/common/api/Api$ApiOptions$NoOptions;", "Lcom/google/android/gms/identitycredentials/IdentityCredentialClient;", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "getCredential", "Lcom/google/android/gms/tasks/Task;", "Lcom/google/android/gms/identitycredentials/PendingGetCredentialHandle;", "request", "Lcom/google/android/gms/identitycredentials/GetCredentialRequest;", "registerCredentials", "Lcom/google/android/gms/identitycredentials/RegistrationResponse;", "Lcom/google/android/gms/identitycredentials/RegistrationRequest;", "clearRegistry", "Lcom/google/android/gms/identitycredentials/ClearRegistryResponse;", "Lcom/google/android/gms/identitycredentials/ClearRegistryRequest;", "importCredentials", "Lcom/google/android/gms/identitycredentials/PendingImportCredentialsHandle;", "Lcom/google/android/gms/identitycredentials/ImportCredentialsRequest;", "registerExport", "Lcom/google/android/gms/identitycredentials/RegisterExportResponse;", "Lcom/google/android/gms/identitycredentials/RegisterExportRequest;", "java.com.google.android.gmscore.integ.client.identity_credentials_identity_credentials"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class InternalIdentityCredentialClient extends GoogleApi<Api.ApiOptions.NoOptions> implements IdentityCredentialClient {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InternalIdentityCredentialClient(Context context) {
        super(context, (Api<Api.ApiOptions.NoOptions>) InternalIdentityCredentialClientKt.API, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX INFO: Access modifiers changed from: private */
    public static final void clearRegistry$lambda$2(ClearRegistryRequest request, IdentityCredentialClientImpl identityCredentialClientImpl, final TaskCompletionSource taskCompletionSource) throws RemoteException {
        Intrinsics.checkNotNullParameter(request, "$request");
        ((IIdentityCredentialService) identityCredentialClientImpl.getService()).clearRegistry(new IdentityCredentialBaseCallbacks() { // from class: com.google.android.gms.identitycredentials.internal.InternalIdentityCredentialClient$clearRegistry$1$callback$1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // com.google.android.gms.identitycredentials.internal.IdentityCredentialBaseCallbacks, com.google.android.gms.identitycredentials.internal.IIdentityCredentialCallbacks
            public void onClearRegistry(Status status, ClearRegistryResponse result) {
                Intrinsics.checkNotNullParameter(status, "status");
                TaskUtil.setResultOrApiException(status, result, taskCompletionSource);
            }
        }, request);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX INFO: Access modifiers changed from: private */
    public static final void getCredential$lambda$0(GetCredentialRequest request, IdentityCredentialClientImpl identityCredentialClientImpl, final TaskCompletionSource taskCompletionSource) throws RemoteException {
        Intrinsics.checkNotNullParameter(request, "$request");
        ((IIdentityCredentialService) identityCredentialClientImpl.getService()).getCredential(new IdentityCredentialBaseCallbacks() { // from class: com.google.android.gms.identitycredentials.internal.InternalIdentityCredentialClient$getCredential$1$callback$1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // com.google.android.gms.identitycredentials.internal.IdentityCredentialBaseCallbacks, com.google.android.gms.identitycredentials.internal.IIdentityCredentialCallbacks
            public void onGetCredential(Status status, PendingGetCredentialHandle result) {
                Intrinsics.checkNotNullParameter(status, "status");
                TaskUtil.setResultOrApiException(status, result, taskCompletionSource);
            }
        }, request);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX INFO: Access modifiers changed from: private */
    public static final void registerCredentials$lambda$1(RegistrationRequest request, IdentityCredentialClientImpl identityCredentialClientImpl, final TaskCompletionSource taskCompletionSource) throws RemoteException {
        Intrinsics.checkNotNullParameter(request, "$request");
        ((IIdentityCredentialService) identityCredentialClientImpl.getService()).registerCredentials(new IdentityCredentialBaseCallbacks() { // from class: com.google.android.gms.identitycredentials.internal.InternalIdentityCredentialClient$registerCredentials$1$callback$1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // com.google.android.gms.identitycredentials.internal.IdentityCredentialBaseCallbacks, com.google.android.gms.identitycredentials.internal.IIdentityCredentialCallbacks
            public void onRegisterCredentials(Status status, RegistrationResponse result) {
                Intrinsics.checkNotNullParameter(status, "status");
                TaskUtil.setResultOrApiException(status, result, taskCompletionSource);
            }
        }, request);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Type inference failed for r5v4. Raw type applied. Possible types: com.google.android.gms.tasks.Task<TResult>, java.lang.Object, com.google.android.gms.tasks.Task<com.google.android.gms.identitycredentials.ClearRegistryResponse> */
    @Override // com.google.android.gms.identitycredentials.IdentityCredentialClient
    public Task<ClearRegistryResponse> clearRegistry(final ClearRegistryRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        Task taskDoWrite = doWrite(TaskApiCall.builder().setFeatures(zze.zzc).run(new RemoteCall() { // from class: com.google.android.gms.identitycredentials.internal.InternalIdentityCredentialClient$$ExternalSyntheticLambda1
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                InternalIdentityCredentialClient.clearRegistry$lambda$2(request, (IdentityCredentialClientImpl) obj, (TaskCompletionSource) obj2);
            }
        }).setMethodKey(32703).build());
        Intrinsics.checkNotNullExpressionValue(taskDoWrite, "doWrite(...)");
        return taskDoWrite;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Type inference failed for r5v4. Raw type applied. Possible types: com.google.android.gms.tasks.Task<TResult>, java.lang.Object, com.google.android.gms.tasks.Task<com.google.android.gms.identitycredentials.PendingGetCredentialHandle> */
    @Override // com.google.android.gms.identitycredentials.IdentityCredentialClient
    public Task<PendingGetCredentialHandle> getCredential(final GetCredentialRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        Task taskDoRead = doRead(TaskApiCall.builder().setFeatures(zze.zza).run(new RemoteCall() { // from class: com.google.android.gms.identitycredentials.internal.InternalIdentityCredentialClient$$ExternalSyntheticLambda3
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                InternalIdentityCredentialClient.getCredential$lambda$0(request, (IdentityCredentialClientImpl) obj, (TaskCompletionSource) obj2);
            }
        }).setMethodKey(32701).build());
        Intrinsics.checkNotNullExpressionValue(taskDoRead, "doRead(...)");
        return taskDoRead;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Type inference failed for r5v4. Raw type applied. Possible types: com.google.android.gms.tasks.Task<TResult>, java.lang.Object, com.google.android.gms.tasks.Task<com.google.android.gms.identitycredentials.RegistrationResponse> */
    @Override // com.google.android.gms.identitycredentials.IdentityCredentialClient
    public Task<RegistrationResponse> registerCredentials(final RegistrationRequest request) {
        Intrinsics.checkNotNullParameter(request, "request");
        Task taskDoWrite = doWrite(TaskApiCall.builder().setFeatures(zze.zzb).run(new RemoteCall() { // from class: com.google.android.gms.identitycredentials.internal.InternalIdentityCredentialClient$$ExternalSyntheticLambda4
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                InternalIdentityCredentialClient.registerCredentials$lambda$1(request, (IdentityCredentialClientImpl) obj, (TaskCompletionSource) obj2);
            }
        }).setMethodKey(32702).build());
        Intrinsics.checkNotNullExpressionValue(taskDoWrite, "doWrite(...)");
        return taskDoWrite;
    }
}
