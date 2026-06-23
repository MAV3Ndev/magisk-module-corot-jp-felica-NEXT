package com.google.android.gms.identitycredentials;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0012\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u0018H\u0007J\u0018\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0017\u001a\u00020\u001aH\u0007J\"\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u001c\u001a\u00020\u00052\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0007J\u0018\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0007J\u0018\u0010#\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0007J\u0014\u0010$\u001a\u00020%2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0002J\u0014\u0010&\u001a\u00020'2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u0005H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u0010\u0010(\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010)\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010*\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010+\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010,\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010-\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000R\u0010\u0010/\u001a\u00020\u00058\u0006X\u0087T¢\u0006\u0002\n\u0000¨\u00060"}, d2 = {"Lcom/google/android/gms/identitycredentials/IntentHelper;", "", "<init>", "()V", "ACTION_GET_CREDENTIAL", "", "ACTION_IMPORT_CREDENTIAL", "EXTRA_GET_CREDENTIAL_REQUEST", "EXTRA_IMPORT_CREDENTIALS_REQUEST", "EXTRA_IMPORT_CREDENTIALS_REQUEST_JSON", "EXTRA_CALLING_PACKAGE_NAME", "EXTRA_SIGNATURE_COUNT", "EXTRA_SIGNATURE_PREFIX", "EXTRA_ORIGIN", "EXTRA_CREDENTIAL_ID", "extractGetCredentialRequest", "Lcom/google/android/gms/identitycredentials/GetCredentialRequest;", "intent", "Landroid/content/Intent;", "extractCallingAppInfo", "Lcom/google/android/gms/identitycredentials/CallingAppInfo;", "setImportCredentialsResponse", "", "response", "Lcom/google/android/gms/identitycredentials/ImportCredentialsResponse;", "setGetCredentialResponse", "Lcom/google/android/gms/identitycredentials/GetCredentialResponse;", "setGetCredentialException", "errorType", "errorMessage", "extractImportCredentialsResponse", "resultCode", "", "resultData", "Landroid/os/Bundle;", "extractGetCredentialResponse", "newUnknownException", "Lcom/google/android/gms/identitycredentials/GetCredentialException;", "newUnknownImportCredentialsException", "Lcom/google/android/gms/identitycredentials/ImportCredentialsException;", "EXTRA_EXCEPTION_TYPE", "EXTRA_IMPORT_CREDENTIALS_RESPONSE", "EXTRA_EXCEPTION_MESSAGE", "EXTRA_CREDENTIAL_TYPE", "EXTRA_CREDENTIAL_DATA", "BUNDLE_KEY_PROVIDER_DATA", "BUNDLE_KEY_EXCEPTION_TYPE", "BUNDLE_KEY_EXCEPTION_MESSAGE", "java.com.google.android.gmscore.integ.client.identity_credentials_identity_credentials"}, k = 1, mv = {2, 0, 0}, xi = 48)
public final class IntentHelper {
    public static final String ACTION_GET_CREDENTIAL = "androidx.identitycredentials.action.GET_CREDENTIALS";
    public static final String EXTRA_CREDENTIAL_ID = "androidx.identitycredentials.extra.CREDENTIAL_ID";
    public static final IntentHelper INSTANCE = new IntentHelper();

    private IntentHelper() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmStatic
    public static final CallingAppInfo extractCallingAppInfo(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        intent.setExtrasClassLoader(null);
        String stringExtra = intent.getStringExtra("androidx.identitycredentials.extra.CALLING_PACKAGE_NAME");
        if (stringExtra == null) {
            return null;
        }
        String stringExtra2 = intent.getStringExtra("androidx.identitycredentials.extra.ORIGIN");
        int intExtra = intent.getIntExtra("androidx.identitycredentials.extra.SIGNATURE_COUNT", 0);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < intExtra; i++) {
            byte[] byteArrayExtra = intent.getByteArrayExtra("androidx.identitycredentials.extra.SIGNATURE_" + i);
            if (byteArrayExtra == null) {
                Log.w("IntentHelper", "cannot find expected signature at count " + i);
                return null;
            }
            arrayList.add(byteArrayExtra);
        }
        return new CallingAppInfo(stringExtra, arrayList, stringExtra2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmStatic
    public static final GetCredentialRequest extractGetCredentialRequest(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        intent.setExtrasClassLoader(GetCredentialRequest.class.getClassLoader());
        return (GetCredentialRequest) intent.getParcelableExtra("androidx.identitycredentials.extra.GET_CREDENTIAL_REQUEST");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmStatic
    public static final GetCredentialResponse extractGetCredentialResponse(int resultCode, Bundle resultData) throws GetCredentialException {
        Intrinsics.checkNotNullParameter(resultData, "resultData");
        if (resultCode != -1) {
            if (resultCode == 0) {
                CharSequence charSequence = resultData.getCharSequence("androidx.identitycredentials.BUNDLE_KEY_EXCEPTION_TYPE");
                String string = charSequence != null ? charSequence.toString() : "android.credentials.GetCredentialException.TYPE_USER_CANCELED";
                CharSequence charSequence2 = resultData.getCharSequence("androidx.identitycredentials.BUNDLE_KEY_EXCEPTION_MESSAGE");
                throw new GetCredentialException(string, charSequence2 != null ? charSequence2.toString() : null);
            }
            throw INSTANCE.newUnknownException("Activity result has unexpected resultCode: " + resultCode);
        }
        Intent intent = Build.VERSION.SDK_INT >= 33 ? (Intent) resultData.getParcelable("androidx.identitycredentials.BUNDLE_KEY_PROVIDER_DATA", Intent.class) : (Intent) resultData.getParcelable("androidx.identitycredentials.BUNDLE_KEY_PROVIDER_DATA");
        if (intent == null) {
            throw INSTANCE.newUnknownException("Provider result is empty");
        }
        if (intent.hasExtra("androidx.identitycredentials.EXTRA_CREDENTIAL_TYPE")) {
            String stringExtra = intent.getStringExtra("androidx.identitycredentials.EXTRA_CREDENTIAL_TYPE");
            Bundle bundleExtra = intent.getBundleExtra("androidx.identitycredentials.EXTRA_CREDENTIAL_DATA");
            if (stringExtra == null || bundleExtra == null) {
                throw INSTANCE.newUnknownException("Credential result is empty");
            }
            return new GetCredentialResponse(new Credential(stringExtra, bundleExtra));
        }
        if (!intent.hasExtra("androidx.identitycredentials.EXTRA_EXCEPTION_TYPE")) {
            throw INSTANCE.newUnknownException("Unknown provider error");
        }
        String stringExtra2 = intent.getStringExtra("androidx.identitycredentials.EXTRA_EXCEPTION_TYPE");
        if (stringExtra2 == null) {
            throw INSTANCE.newUnknownException("Unknown provider error");
        }
        throw new GetCredentialException(stringExtra2, intent.getStringExtra("androidx.identitycredentials.EXTRA_EXCEPTION_MESSAGE"));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final GetCredentialException newUnknownException(String errorMessage) {
        return new GetCredentialException("android.credentials.GetCredentialException.TYPE_UNKNOWN", errorMessage);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmStatic
    public static final void setGetCredentialException(Intent intent, String errorType, String errorMessage) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(errorType, "errorType");
        intent.putExtra("androidx.identitycredentials.EXTRA_EXCEPTION_TYPE", errorType);
        intent.putExtra("androidx.identitycredentials.EXTRA_EXCEPTION_MESSAGE", errorMessage);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @JvmStatic
    public static final void setGetCredentialResponse(Intent intent, GetCredentialResponse response) {
        Intrinsics.checkNotNullParameter(intent, "intent");
        Intrinsics.checkNotNullParameter(response, "response");
        intent.putExtra("androidx.identitycredentials.EXTRA_CREDENTIAL_TYPE", response.getCredential().getType());
        intent.putExtra("androidx.identitycredentials.EXTRA_CREDENTIAL_DATA", response.getCredential().getData());
    }
}
