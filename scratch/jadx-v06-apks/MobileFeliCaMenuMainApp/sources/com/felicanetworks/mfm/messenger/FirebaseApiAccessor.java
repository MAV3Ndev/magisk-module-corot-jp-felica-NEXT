package com.felicanetworks.mfm.messenger;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public class FirebaseApiAccessor extends Service {
    private FirebaseApiBinder binder;

    interface AccessTransaction {
        void transaction(FirebaseApi firebaseApi);
    }

    @Override // android.app.Service
    public void onCreate() {
        Logger.debug(this);
        this.binder = new FirebaseApiBinder(this);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Logger.debug(this, intent);
        return this.binder;
    }

    @Override // android.app.Service
    public void onDestroy() {
        Logger.debug(this);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Logger.debug(this, intent);
        return true;
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        Logger.debug(this, intent);
    }

    static void access(Context context, AccessTransaction accessTransaction) {
        Logger.debug(context);
        new Connector().connect(context.getApplicationContext(), accessTransaction);
    }

    private static final class Connector implements ServiceConnection {
        private static final long WAIT_COUNT = 1000;
        private static final long WAIT_INTERVAL = 10;
        private IBinder binder;

        private Connector() {
        }

        public void connect(final Context context, final AccessTransaction accessTransaction) {
            Logger.debug(new Object[0]);
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.1
                /* JADX WARN: Code restructure failed: missing block: B:18:0x003d, code lost:
                
                    com.felicanetworks.mfm.messenger.Logger.stop(r0);
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public void run() {
                    /*
                        r7 = this;
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r0 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this     // Catch: java.lang.Throwable -> L68
                        android.content.Context r1 = r2     // Catch: java.lang.Throwable -> L68
                        boolean r0 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$100(r0, r1)     // Catch: java.lang.Throwable -> L68
                        if (r0 != 0) goto L29
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r0 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this
                        monitor-enter(r0)
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$AccessTransaction r1 = r3     // Catch: java.lang.Throwable -> L26
                        com.felicanetworks.mfm.messenger.FirebaseApi r2 = new com.felicanetworks.mfm.messenger.FirebaseApi     // Catch: java.lang.Throwable -> L26
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r3 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this     // Catch: java.lang.Throwable -> L26
                        android.os.IBinder r3 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$200(r3)     // Catch: java.lang.Throwable -> L26
                        r2.<init>(r3)     // Catch: java.lang.Throwable -> L26
                        r1.transaction(r2)     // Catch: java.lang.Throwable -> L26
                        monitor-exit(r0)     // Catch: java.lang.Throwable -> L26
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r0 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this
                        android.content.Context r1 = r2
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$300(r0, r1)
                        return
                    L26:
                        r1 = move-exception
                        monitor-exit(r0)     // Catch: java.lang.Throwable -> L26
                        throw r1
                    L29:
                        com.felicanetworks.mfm.messenger.Logger$Stopwatch r0 = com.felicanetworks.mfm.messenger.Logger.start()     // Catch: java.lang.Throwable -> L68
                        r1 = 0
                    L2e:
                        long r2 = (long) r1     // Catch: java.lang.Throwable -> L68
                        r4 = 1000(0x3e8, double:4.94E-321)
                        int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                        if (r6 >= 0) goto L49
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r2 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this     // Catch: java.lang.Throwable -> L68
                        android.os.IBinder r2 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$200(r2)     // Catch: java.lang.Throwable -> L68
                        if (r2 == 0) goto L41
                        com.felicanetworks.mfm.messenger.Logger.stop(r0)     // Catch: java.lang.Throwable -> L68
                        goto L49
                    L41:
                        r2 = 10
                        android.os.SystemClock.sleep(r2)     // Catch: java.lang.Throwable -> L68
                        int r1 = r1 + 1
                        goto L2e
                    L49:
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r0 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this
                        monitor-enter(r0)
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$AccessTransaction r1 = r3     // Catch: java.lang.Throwable -> L65
                        com.felicanetworks.mfm.messenger.FirebaseApi r2 = new com.felicanetworks.mfm.messenger.FirebaseApi     // Catch: java.lang.Throwable -> L65
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r3 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this     // Catch: java.lang.Throwable -> L65
                        android.os.IBinder r3 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$200(r3)     // Catch: java.lang.Throwable -> L65
                        r2.<init>(r3)     // Catch: java.lang.Throwable -> L65
                        r1.transaction(r2)     // Catch: java.lang.Throwable -> L65
                        monitor-exit(r0)     // Catch: java.lang.Throwable -> L65
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r0 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this
                        android.content.Context r1 = r2
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$300(r0, r1)
                        return
                    L65:
                        r1 = move-exception
                        monitor-exit(r0)     // Catch: java.lang.Throwable -> L65
                        throw r1
                    L68:
                        r0 = move-exception
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r1 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this
                        monitor-enter(r1)
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$AccessTransaction r2 = r3     // Catch: java.lang.Throwable -> L85
                        com.felicanetworks.mfm.messenger.FirebaseApi r3 = new com.felicanetworks.mfm.messenger.FirebaseApi     // Catch: java.lang.Throwable -> L85
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r4 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this     // Catch: java.lang.Throwable -> L85
                        android.os.IBinder r4 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$200(r4)     // Catch: java.lang.Throwable -> L85
                        r3.<init>(r4)     // Catch: java.lang.Throwable -> L85
                        r2.transaction(r3)     // Catch: java.lang.Throwable -> L85
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> L85
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor$Connector r1 = com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.this
                        android.content.Context r2 = r2
                        com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.access$300(r1, r2)
                        throw r0
                    L85:
                        r0 = move-exception
                        monitor-exit(r1)     // Catch: java.lang.Throwable -> L85
                        throw r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.AnonymousClass1.run():void");
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean bind(Context context) {
            Logger.debug(new Object[0]);
            try {
                return context.bindService(new Intent(context, (Class<?>) FirebaseApiAccessor.class), this, 1);
            } catch (Exception e) {
                Logger.warning(e);
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unbind(Context context) {
            Logger.debug(new Object[0]);
            try {
                context.unbindService(this);
            } catch (Exception e) {
                Logger.warning(e);
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.debug(componentName, iBinder);
            this.binder = iBinder;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.debug(componentName);
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            Logger.debug(componentName);
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Logger.debug(componentName);
        }
    }
}
