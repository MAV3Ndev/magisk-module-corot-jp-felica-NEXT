package com.felicanetworks.mfm.messenger;

import android.content.Context;
import android.os.Parcel;
import com.felicanetworks.mfm.messenger.IFirebaseApi;
import com.felicanetworks.mfm.messenger.Logger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
class FirebaseApiBinder extends IFirebaseApi.Stub {
    private final Context context;

    private interface AsyncRunner<Result> {
        void run(CompleteHandler<Result> completeHandler);
    }

    private interface CompleteHandler<Result> {
        void failure(Exception exc);

        void success(Result result);
    }

    FirebaseApiBinder(Context context) {
        this.context = context;
    }

    @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
    public void initializeApp() {
        Logger.Stopwatch stopwatchStart = Logger.start();
        try {
            try {
                FirebaseApp.initializeApp(this.context);
            } catch (Exception e) {
                Logger.error(e);
                throwsParcelableException(e);
            }
        } finally {
            Logger.stop(stopwatchStart);
        }
    }

    @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
    public boolean isAutoInitEnabled() {
        Logger.Stopwatch stopwatchStart = Logger.start();
        try {
            return FirebaseMessaging.getInstance().isAutoInitEnabled();
        } catch (Exception e) {
            Logger.error(e);
            throwsParcelableException(e);
            return false;
        } finally {
            Logger.stop(stopwatchStart);
        }
    }

    @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
    public void setAutoInitEnabled(boolean z) {
        Logger.Stopwatch stopwatchStart = Logger.start();
        try {
            try {
                FirebaseMessaging.getInstance().setAutoInitEnabled(z);
            } catch (Exception e) {
                Logger.error(e);
                throwsParcelableException(e);
            }
        } finally {
            Logger.stop(stopwatchStart);
        }
    }

    @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
    public IGetTokenResult getToken() {
        Logger.Stopwatch stopwatchStart = Logger.start();
        try {
            return (IGetTokenResult) returnAsyncResult(new AsyncRunner<GetTokenResult>() { // from class: com.felicanetworks.mfm.messenger.FirebaseApiBinder.1
                @Override // com.felicanetworks.mfm.messenger.FirebaseApiBinder.AsyncRunner
                public void run(final CompleteHandler<GetTokenResult> completeHandler) {
                    FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() { // from class: com.felicanetworks.mfm.messenger.FirebaseApiBinder.1.1
                        @Override // com.google.android.gms.tasks.OnCompleteListener
                        public void onComplete(Task<String> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                completeHandler.success(new GetTokenResult(task.getResult()));
                            } else {
                                completeHandler.failure(task.getException());
                            }
                        }
                    });
                }
            }, 60000L);
        } catch (Exception e) {
            Logger.error(e);
            throwsParcelableException(e);
            return null;
        } finally {
            Logger.stop(stopwatchStart);
        }
    }

    @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
    public void deleteInstallId() {
        Logger.Stopwatch stopwatchStart = Logger.start();
        try {
            try {
                FirebaseInstallations.getInstance().delete().addOnCompleteListener(new OnCompleteListener<Void>() { // from class: com.felicanetworks.mfm.messenger.FirebaseApiBinder.2
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Logger.debug("Installation deleted");
                        } else {
                            Logger.debug("Unable to delete Installation");
                        }
                    }
                });
            } catch (Exception e) {
                Logger.error(e);
                throwsParcelableException(e);
            }
        } finally {
            Logger.stop(stopwatchStart);
        }
    }

    private void throwsParcelableException(Exception exc) throws RuntimeException {
        Parcel parcelObtain = Parcel.obtain();
        try {
            try {
                parcelObtain.writeException(exc);
                if (exc instanceof RuntimeException) {
                    throw ((RuntimeException) exc);
                }
                throw new IllegalStateException(exc);
            } catch (RuntimeException unused) {
                throw new IllegalStateException(exc);
            }
        } catch (Throwable th) {
            parcelObtain.recycle();
            throw th;
        }
    }

    private static <Result> Result returnAsyncResult(final AsyncRunner<Result> asyncRunner, long j) throws Exception {
        final OneWayTicket oneWayTicket = new OneWayTicket(j);
        final AsyncPacket asyncPacket = new AsyncPacket();
        final AsyncPacket asyncPacket2 = new AsyncPacket();
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.messenger.FirebaseApiBinder.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    asyncRunner.run(new CompleteHandler<Result>() { // from class: com.felicanetworks.mfm.messenger.FirebaseApiBinder.3.1
                        @Override // com.felicanetworks.mfm.messenger.FirebaseApiBinder.CompleteHandler
                        public void success(Result result) {
                            if (oneWayTicket.isAvailable()) {
                                asyncPacket.put(result);
                                oneWayTicket.getOff();
                            }
                        }

                        @Override // com.felicanetworks.mfm.messenger.FirebaseApiBinder.CompleteHandler
                        public void failure(Exception exc) {
                            if (oneWayTicket.isAvailable()) {
                                asyncPacket2.put(exc);
                                oneWayTicket.getOff();
                            }
                        }
                    });
                } catch (Exception e) {
                    asyncPacket2.put(e);
                    oneWayTicket.getOff();
                }
            }
        }).start();
        oneWayTicket.getOn();
        if (asyncPacket2.has()) {
            throw ((Exception) asyncPacket2.get());
        }
        if (!asyncPacket.has()) {
            throw new IllegalStateException("Result is nothing.");
        }
        return (Result) asyncPacket.get();
    }

    private static class AsyncPacket<T> extends HashMap<UUID, T> {
        private static final UUID KEY = UUID.randomUUID();

        private AsyncPacket() {
        }

        void put(T t) {
            if (t == null) {
                return;
            }
            super.put(KEY, t);
        }

        T get() {
            return (T) super.get(KEY);
        }

        boolean has() {
            return containsKey(KEY);
        }
    }

    private static class OneWayTicket {
        private final CountDownLatch latch = new CountDownLatch(1);
        private final long timeout;

        OneWayTicket(long j) {
            this.timeout = j;
        }

        synchronized boolean isAvailable() {
            return this.latch.getCount() > 0;
        }

        void getOn() throws TimeoutException {
            try {
                if (!this.latch.await(this.timeout, TimeUnit.MILLISECONDS) && isAvailable()) {
                    throw new TimeoutException("Timed is over " + this.timeout + " ms.");
                }
            } catch (InterruptedException unused) {
            } catch (Throwable th) {
                this.latch.countDown();
                throw th;
            }
            this.latch.countDown();
        }

        synchronized void getOff() {
            this.latch.countDown();
        }
    }
}
