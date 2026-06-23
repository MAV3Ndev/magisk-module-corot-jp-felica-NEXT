package com.google.android.gms.identitycredentials;

import com.google.android.gms.tasks.Task;
import kotlin.Metadata;

/* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H'J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u0006\u0010\u0005\u001a\u00020\tH'J\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\u0006\u0010\u0005\u001a\u00020\fH'J\u0016\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\u0006\u0010\u0005\u001a\u00020\u000fH'J\u0016\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\u0006\u0010\u0005\u001a\u00020\u0012H'ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0013À\u0006\u0001"}, d2 = {"Lcom/google/android/gms/identitycredentials/IdentityCredentialClient;", "", "getCredential", "Lcom/google/android/gms/tasks/Task;", "Lcom/google/android/gms/identitycredentials/PendingGetCredentialHandle;", "request", "Lcom/google/android/gms/identitycredentials/GetCredentialRequest;", "registerCredentials", "Lcom/google/android/gms/identitycredentials/RegistrationResponse;", "Lcom/google/android/gms/identitycredentials/RegistrationRequest;", "clearRegistry", "Lcom/google/android/gms/identitycredentials/ClearRegistryResponse;", "Lcom/google/android/gms/identitycredentials/ClearRegistryRequest;", "importCredentials", "Lcom/google/android/gms/identitycredentials/PendingImportCredentialsHandle;", "Lcom/google/android/gms/identitycredentials/ImportCredentialsRequest;", "registerExport", "Lcom/google/android/gms/identitycredentials/RegisterExportResponse;", "Lcom/google/android/gms/identitycredentials/RegisterExportRequest;", "java.com.google.android.gmscore.integ.client.identity_credentials_identity_credentials"}, k = 1, mv = {2, 0, 0}, xi = 48)
public interface IdentityCredentialClient {
    Task<ClearRegistryResponse> clearRegistry(ClearRegistryRequest request);

    Task<PendingGetCredentialHandle> getCredential(GetCredentialRequest request);

    Task<RegistrationResponse> registerCredentials(RegistrationRequest request);
}
