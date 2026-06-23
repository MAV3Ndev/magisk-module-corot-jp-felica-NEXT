package com.google.firebase.messaging;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.credentials.CredentialManager$$ExternalSyntheticLambda0;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes4.dex */
public class FcmBroadcastProcessor {
    private static final String EXTRA_BINARY_DATA = "rawData";
    private static final String EXTRA_BINARY_DATA_BASE_64 = "gcm.rawData64";
    private static WithinAppServiceConnection fcmServiceConn;
    private static final Object lock = new Object();
    private final Context context;
    private final Executor executor;

    public FcmBroadcastProcessor(Context context) {
        this.context = context;
        this.executor = new CredentialManager$$ExternalSyntheticLambda0();
    }

    public FcmBroadcastProcessor(Context context, ExecutorService executorService) {
        this.context = context;
        this.executor = executorService;
    }

    public Task<Integer> process(Intent intent) {
        String stringExtra = intent.getStringExtra(EXTRA_BINARY_DATA_BASE_64);
        if (stringExtra != null) {
            intent.putExtra("rawData", Base64.decode(stringExtra, 0));
            intent.removeExtra(EXTRA_BINARY_DATA_BASE_64);
        }
        return startMessagingService(this.context, intent);
    }

    public Task<Integer> startMessagingService(final Context context, final Intent intent) {
        boolean z = PlatformVersion.isAtLeastO() && context.getApplicationInfo().targetSdkVersion >= 26;
        final boolean z2 = (intent.getFlags() & 268435456) != 0;
        if (z && !z2) {
            return bindToMessagingService(context, intent, z2);
        }
        return Tasks.call(this.executor, new Callable() { // from class: com.google.firebase.messaging.FcmBroadcastProcessor$$ExternalSyntheticLambda1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return Integer.valueOf(ServiceStarter.getInstance().startMessagingService(context, intent));
            }
        }).continueWithTask(this.executor, new Continuation() { // from class: com.google.firebase.messaging.FcmBroadcastProcessor$$ExternalSyntheticLambda2
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task) {
                return FcmBroadcastProcessor.lambda$startMessagingService$2(context, intent, z2, task);
            }
        });
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: ?: TERNARY null = (((wrap:boolean:0x0000: INVOKE  STATIC call: com.google.android.gms.common.util.PlatformVersion.isAtLeastO():boolean A[MD:():boolean (m), WRAPPED] (LINE:101)) == true && (wrap:int:0x000c: INVOKE 
  (wrap:java.lang.Integer:0x000a: CHECK_CAST (java.lang.Integer) (wrap:java.lang.Object:0x0006: INVOKE (r5v0 com.google.android.gms.tasks.Task) VIRTUAL call: com.google.android.gms.tasks.Task.getResult():java.lang.Object A[MD:():TResult (m), WRAPPED] (LINE:102)))
 VIRTUAL call: java.lang.Integer.intValue():int A[MD:():int (c), WRAPPED] (LINE:102)) == (402 int))) ? (wrap:com.google.android.gms.tasks.Task<TContinuationResult>:0x0023: INVOKE 
  (wrap:com.google.android.gms.tasks.Task<java.lang.Integer>:0x0015: INVOKE (r2v0 android.content.Context), (r3v0 android.content.Intent), (r4v0 boolean) STATIC call: com.google.firebase.messaging.FcmBroadcastProcessor.bindToMessagingService(android.content.Context, android.content.Intent, boolean):com.google.android.gms.tasks.Task A[MD:(android.content.Context, android.content.Intent, boolean):com.google.android.gms.tasks.Task<java.lang.Integer> (m), WRAPPED] (LINE:109))
  (wrap:androidx.credentials.CredentialManager$$ExternalSyntheticLambda0:0x001b: CONSTRUCTOR  A[MD:():void (m), WRAPPED] call: androidx.credentials.CredentialManager$$ExternalSyntheticLambda0.<init>():void type: CONSTRUCTOR)
  (wrap:com.google.android.gms.tasks.Continuation<java.lang.Integer, TContinuationResult>:0x0020: CONSTRUCTOR  A[MD:():void (m), WRAPPED] call: com.google.firebase.messaging.FcmBroadcastProcessor$$ExternalSyntheticLambda0.<init>():void type: CONSTRUCTOR)
 VIRTUAL call: com.google.android.gms.tasks.Task.continueWith(java.util.concurrent.Executor, com.google.android.gms.tasks.Continuation):com.google.android.gms.tasks.Task A[MD:<TContinuationResult>:(java.util.concurrent.Executor, com.google.android.gms.tasks.Continuation<TResult, TContinuationResult>):com.google.android.gms.tasks.Task<TContinuationResult> (m), WRAPPED] (LINE:110)) : (r5v0 com.google.android.gms.tasks.Task) */
    static /* synthetic */ Task lambda$startMessagingService$2(Context context, Intent intent, boolean z, Task task) throws Exception {
        return (PlatformVersion.isAtLeastO() && ((Integer) task.getResult()).intValue() == 402) ? bindToMessagingService(context, intent, z).continueWith(new CredentialManager$$ExternalSyntheticLambda0(), new Continuation() { // from class: com.google.firebase.messaging.FcmBroadcastProcessor$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task2) {
                return Integer.valueOf(TypedValues.CycleType.TYPE_ALPHA);
            }
        }) : task;
    }

    /* JADX DEBUG: Type inference failed for r2v2. Raw type applied. Possible types: com.google.android.gms.tasks.Task<TContinuationResult>, com.google.android.gms.tasks.Task<java.lang.Integer> */
    private static Task<Integer> bindToMessagingService(Context context, Intent intent, boolean z) {
        if (Log.isLoggable(Constants.TAG, 3)) {
            Log.d(Constants.TAG, "Binding to service");
        }
        WithinAppServiceConnection serviceConnection = getServiceConnection(context, "com.google.firebase.MESSAGING_EVENT");
        if (z) {
            if (ServiceStarter.getInstance().hasWakeLockPermission(context)) {
                WakeLockHolder.sendWakefulServiceIntent(context, serviceConnection, intent);
            } else {
                serviceConnection.sendIntent(intent);
            }
            return Tasks.forResult(-1);
        }
        return serviceConnection.sendIntent(intent).continueWith(new CredentialManager$$ExternalSyntheticLambda0(), new Continuation() { // from class: com.google.firebase.messaging.FcmBroadcastProcessor$$ExternalSyntheticLambda3
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // com.google.android.gms.tasks.Continuation
            public final Object then(Task task) {
                return FcmBroadcastProcessor.lambda$bindToMessagingService$3(task);
            }
        });
    }

    static /* synthetic */ Integer lambda$bindToMessagingService$3(Task task) throws Exception {
        return -1;
    }

    private static WithinAppServiceConnection getServiceConnection(Context context, String str) {
        WithinAppServiceConnection withinAppServiceConnection;
        synchronized (lock) {
            if (fcmServiceConn == null) {
                fcmServiceConn = new WithinAppServiceConnection(context, str);
            }
            withinAppServiceConnection = fcmServiceConn;
        }
        return withinAppServiceConnection;
    }

    public static void reset() {
        synchronized (lock) {
            fcmServiceConn = null;
        }
    }

    public static void setServiceConnection(WithinAppServiceConnection withinAppServiceConnection) {
        synchronized (lock) {
            fcmServiceConn = withinAppServiceConnection;
        }
    }
}
