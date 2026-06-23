package com.google.android.gms.measurement.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import androidx.core.app.NotificationCompat;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzoj extends zzor {
    private final AlarmManager zza;
    private zzay zzb;
    private Integer zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected zzoj(zzpf zzpfVar) {
        super(zzpfVar);
        this.zza = (AlarmManager) this.zzu.zzaY().getSystemService(NotificationCompat.CATEGORY_ALARM);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final zzay zzf() {
        if (this.zzb == null) {
            this.zzb = new zzoi(this, this.zzg.zzaf());
        }
        return this.zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzh() {
        JobScheduler jobScheduler = (JobScheduler) this.zzu.zzaY().getSystemService("jobscheduler");
        if (jobScheduler != null) {
            jobScheduler.cancel(zzi());
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final int zzi() {
        if (this.zzc == null) {
            String packageName = this.zzu.zzaY().getPackageName();
            String.valueOf(packageName);
            this.zzc = Integer.valueOf("measurement".concat(String.valueOf(packageName)).hashCode());
        }
        return this.zzc.intValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final PendingIntent zzj() {
        Context contextZzaY = this.zzu.zzaY();
        return PendingIntent.getBroadcast(contextZzaY, 0, new Intent().setClassName(contextZzaY, "com.google.android.gms.measurement.AppMeasurementReceiver").setAction("com.google.android.gms.measurement.UPLOAD"), com.google.android.gms.internal.measurement.zzcg.zza);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzor
    protected final boolean zzbb() {
        AlarmManager alarmManager = this.zza;
        if (alarmManager != null) {
            alarmManager.cancel(zzj());
        }
        if (Build.VERSION.SDK_INT < 24) {
            return false;
        }
        zzh();
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzc(long j) {
        zzay();
        zzib zzibVar = this.zzu;
        zzibVar.zzaU();
        Context contextZzaY = zzibVar.zzaY();
        if (!zzpo.zzau(contextZzaY)) {
            zzibVar.zzaV().zzj().zza("Receiver not registered/enabled");
        }
        if (!zzpo.zzQ(contextZzaY, false)) {
            zzibVar.zzaV().zzj().zza("Service not registered/enabled");
        }
        zzd();
        zzibVar.zzaV().zzk().zzb("Scheduling upload, millis", Long.valueOf(j));
        long jElapsedRealtime = zzibVar.zzaZ().elapsedRealtime() + j;
        zzibVar.zzc();
        if (j < Math.max(0L, ((Long) zzfx.zzL.zzb(null)).longValue()) && !zzf().zzc()) {
            zzf().zzb(j);
        }
        zzibVar.zzaU();
        if (Build.VERSION.SDK_INT < 24) {
            AlarmManager alarmManager = this.zza;
            if (alarmManager != null) {
                zzibVar.zzc();
                alarmManager.setInexactRepeating(2, jElapsedRealtime, Math.max(((Long) zzfx.zzG.zzb(null)).longValue(), j), zzj());
                return;
            }
            return;
        }
        Context contextZzaY2 = zzibVar.zzaY();
        ComponentName componentName = new ComponentName(contextZzaY2, "com.google.android.gms.measurement.AppMeasurementJobService");
        int iZzi = zzi();
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString("action", "com.google.android.gms.measurement.UPLOAD");
        com.google.android.gms.internal.measurement.zzch.zza(contextZzaY2, new JobInfo.Builder(iZzi, componentName).setMinimumLatency(j).setOverrideDeadline(j + j).setExtras(persistableBundle).build(), "com.google.android.gms", "UploadAlarm");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzd() {
        zzay();
        this.zzu.zzaV().zzk().zza("Unscheduling upload");
        AlarmManager alarmManager = this.zza;
        if (alarmManager != null) {
            alarmManager.cancel(zzj());
        }
        zzf().zzd();
        if (Build.VERSION.SDK_INT >= 24) {
            zzh();
        }
    }
}
