package androidx.credentials;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CredentialProviderFactory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\b\u0000\u0018\u0000 !2\u00020\u0001:\u0001!B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u001b\u001a\u00020\u00012\b\b\u0002\u0010\u001c\u001a\u00020\bJ\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u001c\u001a\u00020\bJ \u0010\u001d\u001a\u0004\u0018\u00010\u000e2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00190\u00182\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\n\u0010\u001f\u001a\u0004\u0018\u00010\u000eH\u0003J\n\u0010 \u001a\u0004\u0018\u00010\u000eH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R&\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR*\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0007\u001a\u0004\u0018\u00010\u000e8G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R*\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\b\u0010\u0007\u001a\u0004\u0018\u00010\u000e8G@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0011\"\u0004\b\u0016\u0010\u0013¨\u0006\""}, d2 = {"Landroidx/credentials/CredentialProviderFactory;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "<set-?>", "", "testMode", "getTestMode", "()Z", "setTestMode", "(Z)V", "Landroidx/credentials/CredentialProvider;", "testPostUProvider", "getTestPostUProvider", "()Landroidx/credentials/CredentialProvider;", "setTestPostUProvider", "(Landroidx/credentials/CredentialProvider;)V", "testPreUProvider", "getTestPreUProvider", "setTestPreUProvider", "getAllowedProvidersFromManifest", "", "", "getBestAvailableProvider", "request", "shouldFallbackToPreU", "instantiatePreUProvider", "classNames", "tryCreatePostUProvider", "tryCreatePreUOemProvider", "Companion", "credentials_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
public final class CredentialProviderFactory {
    private static final String CREDENTIAL_PROVIDER_KEY = "androidx.credentials.CREDENTIAL_PROVIDER_KEY";
    private static final int MAX_CRED_MAN_PRE_FRAMEWORK_API_LEVEL = 33;
    private static final String TAG = "CredProviderFactory";
    private final Context context;
    private boolean testMode;
    private CredentialProvider testPostUProvider;
    private CredentialProvider testPreUProvider;

    public CredentialProviderFactory(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final Context getContext() {
        return this.context;
    }

    public final boolean getTestMode() {
        return this.testMode;
    }

    public final void setTestMode(boolean z) {
        this.testMode = z;
    }

    public final CredentialProvider getTestPostUProvider() {
        return this.testPostUProvider;
    }

    public final void setTestPostUProvider(CredentialProvider credentialProvider) {
        this.testPostUProvider = credentialProvider;
    }

    public final CredentialProvider getTestPreUProvider() {
        return this.testPreUProvider;
    }

    public final void setTestPreUProvider(CredentialProvider credentialProvider) {
        this.testPreUProvider = credentialProvider;
    }

    public static /* synthetic */ CredentialProvider getBestAvailableProvider$default(CredentialProviderFactory credentialProviderFactory, Object obj, boolean z, int i, Object obj2) {
        if ((i & 2) != 0) {
            z = true;
        }
        return credentialProviderFactory.getBestAvailableProvider(obj, z);
    }

    public final CredentialProvider getBestAvailableProvider(Object request, boolean shouldFallbackToPreU) {
        Intrinsics.checkNotNullParameter(request, "request");
        if ((request instanceof CreateRestoreCredentialRequest) || Intrinsics.areEqual(request, ClearCredentialStateRequest.TYPE_CLEAR_RESTORE_CREDENTIAL)) {
            return tryCreatePreUOemProvider();
        }
        if (request instanceof GetCredentialRequest) {
            for (CredentialOption credentialOption : ((GetCredentialRequest) request).getCredentialOptions()) {
                if ((credentialOption instanceof GetRestoreCredentialOption) || (credentialOption instanceof GetDigitalCredentialOption)) {
                    return tryCreatePreUOemProvider();
                }
            }
        }
        return getBestAvailableProvider(shouldFallbackToPreU);
    }

    public static /* synthetic */ CredentialProvider getBestAvailableProvider$default(CredentialProviderFactory credentialProviderFactory, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return credentialProviderFactory.getBestAvailableProvider(z);
    }

    public final CredentialProvider getBestAvailableProvider(boolean shouldFallbackToPreU) {
        if (Build.VERSION.SDK_INT >= 34) {
            CredentialProvider credentialProviderTryCreatePostUProvider = tryCreatePostUProvider();
            return (credentialProviderTryCreatePostUProvider == null && shouldFallbackToPreU) ? tryCreatePreUOemProvider() : credentialProviderTryCreatePostUProvider;
        }
        if (Build.VERSION.SDK_INT <= 33) {
            return tryCreatePreUOemProvider();
        }
        return null;
    }

    private final CredentialProvider tryCreatePreUOemProvider() throws PackageManager.NameNotFoundException {
        if (this.testMode) {
            CredentialProvider credentialProvider = this.testPreUProvider;
            if (credentialProvider == null) {
                return null;
            }
            Intrinsics.checkNotNull(credentialProvider);
            if (credentialProvider.isAvailableOnDevice()) {
                return this.testPreUProvider;
            }
            return null;
        }
        List<String> allowedProvidersFromManifest = getAllowedProvidersFromManifest(this.context);
        if (allowedProvidersFromManifest.isEmpty()) {
            return null;
        }
        return instantiatePreUProvider(allowedProvidersFromManifest, this.context);
    }

    private final CredentialProvider tryCreatePostUProvider() {
        if (this.testMode) {
            CredentialProvider credentialProvider = this.testPostUProvider;
            if (credentialProvider == null) {
                return null;
            }
            Intrinsics.checkNotNull(credentialProvider);
            if (credentialProvider.isAvailableOnDevice()) {
                return this.testPostUProvider;
            }
            return null;
        }
        CredentialProviderFrameworkImpl credentialProviderFrameworkImpl = new CredentialProviderFrameworkImpl(this.context);
        if (credentialProviderFrameworkImpl.isAvailableOnDevice()) {
            return credentialProviderFrameworkImpl;
        }
        return null;
    }

    private final CredentialProvider instantiatePreUProvider(List<String> classNames, Context context) {
        Iterator<String> it = classNames.iterator();
        CredentialProvider credentialProvider = null;
        while (it.hasNext()) {
            try {
                Object objNewInstance = Class.forName(it.next()).getConstructor(Context.class).newInstance(context);
                Intrinsics.checkNotNull(objNewInstance, "null cannot be cast to non-null type androidx.credentials.CredentialProvider");
                CredentialProvider credentialProvider2 = (CredentialProvider) objNewInstance;
                if (!credentialProvider2.isAvailableOnDevice()) {
                    continue;
                } else {
                    if (credentialProvider != null) {
                        Log.i(TAG, "Only one active OEM CredentialProvider allowed");
                        return null;
                    }
                    credentialProvider = credentialProvider2;
                }
            } catch (Throwable unused) {
            }
        }
        return credentialProvider;
    }

    private final List<String> getAllowedProvidersFromManifest(Context context) throws PackageManager.NameNotFoundException {
        String string;
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 132);
        ArrayList arrayList = new ArrayList();
        if (packageInfo.services != null) {
            ServiceInfo[] serviceInfoArr = packageInfo.services;
            Intrinsics.checkNotNull(serviceInfoArr);
            for (ServiceInfo serviceInfo : serviceInfoArr) {
                if (serviceInfo.metaData != null && (string = serviceInfo.metaData.getString(CREDENTIAL_PROVIDER_KEY)) != null) {
                    arrayList.add(string);
                }
            }
        }
        return CollectionsKt.toList(arrayList);
    }
}
