package com.felicanetworks.mfm.messenger;

import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
class FirebaseApi {
    private final Wrapper wrapper;

    private static class Wrapper {
        private final IFirebaseApi binder;

        Wrapper(IBinder iBinder) {
            this.binder = OneTimeInitializer.init(iBinder);
        }

        public IFirebaseApi api() {
            if (!OneTimeInitializer.initialized) {
                throw new IllegalStateException("Failed to initialize FCM.");
            }
            IFirebaseApi iFirebaseApi = this.binder;
            if (iFirebaseApi != null) {
                return iFirebaseApi;
            }
            throw new IllegalStateException("Failed to bind Firebase API Service.");
        }
    }

    private static class OneTimeInitializer {
        private static boolean initialized = false;

        private OneTimeInitializer() {
        }

        static IFirebaseApi init(IBinder iBinder) {
            IFirebaseApi iFirebaseApiAsInterface = FirebaseApiBinder.asInterface(iBinder);
            if (initialized) {
                return iFirebaseApiAsInterface;
            }
            try {
                iFirebaseApiAsInterface.initializeApp();
                if (!iFirebaseApiAsInterface.isAutoInitEnabled()) {
                    iFirebaseApiAsInterface.setAutoInitEnabled(true);
                }
                initialized = true;
                return iFirebaseApiAsInterface;
            } catch (Exception e) {
                Logger.warning(e);
                return null;
            }
        }
    }

    FirebaseApi(IBinder iBinder) {
        this.wrapper = new Wrapper(iBinder);
    }

    void setAutoInitEnabled(boolean z) {
        try {
            this.wrapper.api().setAutoInitEnabled(z);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    GetTokenResult getToken() {
        try {
            return new GetTokenResult(this.wrapper.api().getToken());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    void deleteInstallId() {
        try {
            this.wrapper.api().deleteInstallId();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
