package com.google.android.gms.measurement.internal;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzns {
    private final Context zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzns(Context context) {
        Preconditions.checkNotNull(context);
        this.zza = context;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static final void zzi(Intent intent) {
        if (intent == null) {
            Log.e("FA", "onRebind called with null intent");
            return;
        }
        String action = intent.getAction();
        String.valueOf(action);
        Log.v("FA", "onRebind called. action: ".concat(String.valueOf(action)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static final boolean zzj(Intent intent) {
        if (intent == null) {
            Log.e("FA", "onUnbind called with null intent");
            return true;
        }
        String action = intent.getAction();
        String.valueOf(action);
        Log.v("FA", "onUnbind called for intent. action: ".concat(String.valueOf(action)));
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzk(zzpf zzpfVar, Runnable runnable) {
        zzpfVar.zzaW().zzj(new zznn(this, zzpfVar, runnable));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zza() {
        String simpleName = this.zza.getClass().getSimpleName();
        String.valueOf(simpleName);
        Log.v("FA", String.valueOf(simpleName).concat(" is starting up."));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzb() {
        String simpleName = this.zza.getClass().getSimpleName();
        String.valueOf(simpleName);
        Log.v("FA", String.valueOf(simpleName).concat(" is shutting down."));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzc(final Intent intent, int i, final int i2) {
        if (intent == null) {
            Log.w("FA", "AppMeasurementService started with null intent");
            return 2;
        }
        Context context = this.zza;
        zzib zzibVarZzy = zzib.zzy(context, null, null);
        final zzgt zzgtVarZzaV = zzibVarZzy.zzaV();
        String action = intent.getAction();
        zzibVarZzy.zzaU();
        zzgtVarZzaV.zzk().zzc("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zzk(zzpf.zza(context), new Runnable() { // from class: com.google.android.gms.measurement.internal.zznr
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    this.zza.zzf(i2, zzgtVarZzaV, intent);
                }
            });
        }
        return 2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final IBinder zzd(Intent intent) {
        if (intent == null) {
            Log.e("FA", "onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzjc(zzpf.zza(this.zza), null);
        }
        String.valueOf(action);
        Log.w("FA", "onBind received unknown action: ".concat(String.valueOf(action)));
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zze(final JobParameters jobParameters) {
        String string = jobParameters.getExtras().getString("action");
        String.valueOf(string);
        Log.v("FA", "onStartJob received action: ".concat(String.valueOf(string)));
        if (Objects.equals(string, "com.google.android.gms.measurement.UPLOAD")) {
            String str = (String) Preconditions.checkNotNull(string);
            zzpf zzpfVarZza = zzpf.zza(this.zza);
            final zzgt zzgtVarZzaV = zzpfVarZza.zzaV();
            zzpfVarZza.zzaU();
            zzgtVarZzaV.zzk().zzb("Local AppMeasurementJobService called. action", str);
            zzk(zzpfVarZza, new Runnable() { // from class: com.google.android.gms.measurement.internal.zznp
                @Override // java.lang.Runnable
                public final /* synthetic */ void run() {
                    this.zza.zzg(zzgtVarZzaV, jobParameters);
                }
            });
        }
        if (!Objects.equals(string, "com.google.android.gms.measurement.SCION_UPLOAD")) {
            return true;
        }
        com.google.android.gms.internal.measurement.zzfb.zza(this.zza, null).zzw(new Runnable() { // from class: com.google.android.gms.measurement.internal.zznq
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzh(jobParameters);
            }
        });
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: android.content.Context */
    /* JADX WARN: Multi-variable type inference failed */
    final /* synthetic */ void zzf(int i, zzgt zzgtVar, Intent intent) {
        Context context = this.zza;
        zzno zznoVar = (zzno) context;
        if (zznoVar.zza(i)) {
            zzgtVar.zzk().zzb("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzib.zzy(context, null, null).zzaV().zzk().zza("Completed wakeful intent.");
            zznoVar.zzc(intent);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzg(zzgt zzgtVar, JobParameters jobParameters) {
        zzgtVar.zzk().zza("AppMeasurementJobService processed last upload request.");
        ((zzno) this.zza).zzb(jobParameters, false);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzh(JobParameters jobParameters) {
        Log.v("FA", "[sgtm] AppMeasurementJobService processed last Scion upload request.");
        ((zzno) this.zza).zzb(jobParameters, false);
    }
}
