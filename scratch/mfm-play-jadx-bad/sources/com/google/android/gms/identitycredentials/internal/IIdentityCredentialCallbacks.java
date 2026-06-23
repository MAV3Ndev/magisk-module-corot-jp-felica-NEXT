package com.google.android.gms.identitycredentials.internal;

import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.identitycredentials.ClearRegistryResponse;
import com.google.android.gms.identitycredentials.PendingGetCredentialHandle;
import com.google.android.gms.identitycredentials.PendingImportCredentialsHandle;
import com.google.android.gms.identitycredentials.RegisterExportResponse;
import com.google.android.gms.identitycredentials.RegistrationResponse;
import com.google.android.gms.internal.identity_credentials.zzb;
import com.google.android.gms.internal.identity_credentials.zzc;

/* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
/* JADX INFO: loaded from: classes3.dex */
public interface IIdentityCredentialCallbacks extends IInterface {

    /* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
    public static abstract class Stub extends zzb implements IIdentityCredentialCallbacks {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public Stub() {
            super("com.google.android.gms.identitycredentials.internal.IIdentityCredentialCallbacks");
        }

        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        @Override // com.google.android.gms.internal.identity_credentials.zzb
        protected boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                Status status = (Status) zzc.zza(parcel, Status.CREATOR);
                PendingGetCredentialHandle pendingGetCredentialHandle = (PendingGetCredentialHandle) zzc.zza(parcel, PendingGetCredentialHandle.CREATOR);
                enforceNoDataAvail(parcel);
                onGetCredential(status, pendingGetCredentialHandle);
            } else if (i == 2) {
                Status status2 = (Status) zzc.zza(parcel, Status.CREATOR);
                RegistrationResponse registrationResponse = (RegistrationResponse) zzc.zza(parcel, RegistrationResponse.CREATOR);
                enforceNoDataAvail(parcel);
                onRegisterCredentials(status2, registrationResponse);
            } else if (i == 3) {
                Status status3 = (Status) zzc.zza(parcel, Status.CREATOR);
                ClearRegistryResponse clearRegistryResponse = (ClearRegistryResponse) zzc.zza(parcel, ClearRegistryResponse.CREATOR);
                enforceNoDataAvail(parcel);
                onClearRegistry(status3, clearRegistryResponse);
            } else if (i == 4) {
                Status status4 = (Status) zzc.zza(parcel, Status.CREATOR);
                PendingImportCredentialsHandle pendingImportCredentialsHandle = (PendingImportCredentialsHandle) zzc.zza(parcel, PendingImportCredentialsHandle.CREATOR);
                enforceNoDataAvail(parcel);
                onImportCredentials(status4, pendingImportCredentialsHandle);
            } else {
                if (i != 5) {
                    return false;
                }
                Status status5 = (Status) zzc.zza(parcel, Status.CREATOR);
                RegisterExportResponse registerExportResponse = (RegisterExportResponse) zzc.zza(parcel, RegisterExportResponse.CREATOR);
                enforceNoDataAvail(parcel);
                onRegisterExport(status5, registerExportResponse);
            }
            return true;
        }
    }

    void onClearRegistry(Status status, ClearRegistryResponse clearRegistryResponse) throws RemoteException;

    void onGetCredential(Status status, PendingGetCredentialHandle pendingGetCredentialHandle) throws RemoteException;

    void onImportCredentials(Status status, PendingImportCredentialsHandle pendingImportCredentialsHandle) throws RemoteException;

    void onRegisterCredentials(Status status, RegistrationResponse registrationResponse) throws RemoteException;

    void onRegisterExport(Status status, RegisterExportResponse registerExportResponse) throws RemoteException;
}
