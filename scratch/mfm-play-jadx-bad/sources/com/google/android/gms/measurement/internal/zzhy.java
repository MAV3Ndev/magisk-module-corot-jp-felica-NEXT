package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzhy extends zzje {
    private static final AtomicLong zzj = new AtomicLong(Long.MIN_VALUE);
    private zzhx zza;
    private zzhx zzb;
    private final PriorityBlockingQueue zzc;
    private final BlockingQueue zzd;
    private final Thread.UncaughtExceptionHandler zze;
    private final Thread.UncaughtExceptionHandler zzf;
    private final Object zzg;
    private final Semaphore zzh;
    private volatile boolean zzi;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzhy(zzib zzibVar) {
        super(zzibVar);
        this.zzg = new Object();
        this.zzh = new Semaphore(2);
        this.zzc = new PriorityBlockingQueue();
        this.zzd = new LinkedBlockingQueue();
        this.zze = new zzhv(this, "Thread death: Uncaught exception on worker thread");
        this.zzf = new zzhv(this, "Thread death: Uncaught exception on network thread");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzz(zzhw zzhwVar) {
        synchronized (this.zzg) {
            PriorityBlockingQueue priorityBlockingQueue = this.zzc;
            priorityBlockingQueue.add(zzhwVar);
            zzhx zzhxVar = this.zza;
            if (zzhxVar == null) {
                zzhx zzhxVar2 = new zzhx(this, "Measurement Worker", priorityBlockingQueue);
                this.zza = zzhxVar2;
                zzhxVar2.setUncaughtExceptionHandler(this.zze);
                this.zza.start();
            } else {
                zzhxVar.zza();
            }
        }
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    protected final boolean zza() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjd
    public final void zzaX() {
        if (Thread.currentThread() != this.zzb) {
            throw new IllegalStateException("Call expected from network thread");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzd() {
        if (Thread.currentThread() == this.zza) {
            throw new IllegalStateException("Call not expected from worker thread");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zze() {
        return Thread.currentThread() == this.zza;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzf() {
        return Thread.currentThread() == this.zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzjd
    public final void zzg() {
        if (Thread.currentThread() != this.zza) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Future zzh(Callable callable) throws IllegalStateException {
        zzw();
        Preconditions.checkNotNull(callable);
        zzhw zzhwVar = new zzhw(this, callable, false, "Task exception on worker thread");
        if (Thread.currentThread() != this.zza) {
            zzz(zzhwVar);
            return zzhwVar;
        }
        if (!this.zzc.isEmpty()) {
            this.zzu.zzaV().zze().zza("Callable skipped the worker queue.");
        }
        zzhwVar.run();
        return zzhwVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Future zzi(Callable callable) throws IllegalStateException {
        zzw();
        Preconditions.checkNotNull(callable);
        zzhw zzhwVar = new zzhw(this, callable, true, "Task exception on worker thread");
        if (Thread.currentThread() == this.zza) {
            zzhwVar.run();
            return zzhwVar;
        }
        zzz(zzhwVar);
        return zzhwVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzj(Runnable runnable) throws IllegalStateException {
        zzw();
        Preconditions.checkNotNull(runnable);
        zzz(new zzhw(this, runnable, false, "Task exception on worker thread"));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final Object zzk(AtomicReference atomicReference, long j, String str, Runnable runnable) {
        synchronized (atomicReference) {
            this.zzu.zzaW().zzj(runnable);
            try {
                atomicReference.wait(j);
            } catch (InterruptedException unused) {
                zzgr zzgrVarZze = this.zzu.zzaV().zze();
                StringBuilder sb = new StringBuilder(str.length() + 24);
                sb.append("Interrupted waiting for ");
                sb.append(str);
                zzgrVarZze.zza(sb.toString());
                return null;
            }
        }
        Object obj = atomicReference.get();
        if (obj == null) {
            this.zzu.zzaV().zze().zza("Timed out waiting for ".concat(str));
        }
        return obj;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzl(Runnable runnable) throws IllegalStateException {
        zzw();
        Preconditions.checkNotNull(runnable);
        zzz(new zzhw(this, runnable, true, "Task exception on worker thread"));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzm(Runnable runnable) throws IllegalStateException {
        zzw();
        Preconditions.checkNotNull(runnable);
        zzhw zzhwVar = new zzhw(this, runnable, false, "Task exception on network thread");
        synchronized (this.zzg) {
            BlockingQueue blockingQueue = this.zzd;
            blockingQueue.add(zzhwVar);
            zzhx zzhxVar = this.zzb;
            if (zzhxVar == null) {
                zzhx zzhxVar2 = new zzhx(this, "Measurement Network", blockingQueue);
                this.zzb = zzhxVar2;
                zzhxVar2.setUncaughtExceptionHandler(this.zzf);
                this.zzb.start();
            } else {
                zzhxVar.zza();
            }
        }
    }

    final /* synthetic */ zzhx zzn() {
        return this.zza;
    }

    final /* synthetic */ void zzo(zzhx zzhxVar) {
        this.zza = null;
    }

    final /* synthetic */ zzhx zzp() {
        return this.zzb;
    }

    final /* synthetic */ void zzq(zzhx zzhxVar) {
        this.zzb = null;
    }

    final /* synthetic */ Object zzr() {
        return this.zzg;
    }

    final /* synthetic */ Semaphore zzs() {
        return this.zzh;
    }

    final /* synthetic */ boolean zzt() {
        return false;
    }
}
