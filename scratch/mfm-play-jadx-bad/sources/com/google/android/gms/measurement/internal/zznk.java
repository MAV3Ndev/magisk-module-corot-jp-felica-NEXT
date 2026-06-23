package com.google.android.gms.measurement.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.stats.ConnectionTracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zznk extends zzg {
    private final zzne zza;
    private zzga zzb;
    private volatile Boolean zzc;
    private final zzay zzd;
    private ScheduledExecutorService zze;
    private final zzof zzf;
    private final List zzg;
    private final zzay zzh;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected zznk(zzib zzibVar) {
        super(zzibVar);
        this.zzg = new ArrayList();
        this.zzf = new zzof(zzibVar.zzaZ());
        this.zza = new zzne(this);
        this.zzd = new zzml(this, zzibVar);
        this.zzh = new zzmp(this, zzibVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final boolean zzad() {
        this.zzu.zzaU();
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Method merged with bridge method: zzV()V */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: zzae, reason: merged with bridge method [inline-methods] */
    public final void zzV() {
        zzg();
        this.zzf.zza();
        this.zzu.zzc();
        this.zzd.zzb(((Long) zzfx.zzY.zzb(null)).longValue());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzaf(Runnable runnable) throws IllegalStateException {
        zzg();
        if (zzh()) {
            runnable.run();
            return;
        }
        List list = this.zzg;
        long size = list.size();
        zzib zzibVar = this.zzu;
        zzibVar.zzc();
        if (size >= 1000) {
            zzibVar.zzaV().zzb().zza("Discarding data. Max runnable queue size reached");
            return;
        }
        list.add(runnable);
        this.zzh.zzb(60000L);
        zzI();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Method merged with bridge method: zzX()V */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: zzag, reason: merged with bridge method [inline-methods] */
    public final void zzX() {
        zzg();
        zzgr zzgrVarZzk = this.zzu.zzaV().zzk();
        List list = this.zzg;
        zzgrVarZzk.zzb("Processing queued up service tasks", Integer.valueOf(list.size()));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            try {
                ((Runnable) it.next()).run();
            } catch (RuntimeException e) {
                this.zzu.zzaV().zzb().zzb("Task exception while flushing queue", e);
            }
        }
        this.zzg.clear();
        this.zzh.zzd();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final zzr zzah(boolean z) {
        Pair pairZzb;
        zzib zzibVar = this.zzu;
        zzibVar.zzaU();
        zzgh zzghVarZzv = this.zzu.zzv();
        String string = null;
        if (z) {
            zzib zzibVar2 = zzibVar.zzaV().zzu;
            if (zzibVar2.zzd().zzb != null && (pairZzb = zzibVar2.zzd().zzb.zzb()) != null && pairZzb != zzhg.zza) {
                String strValueOf = String.valueOf(pairZzb.second);
                String str = (String) pairZzb.first;
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(str).length());
                sb.append(strValueOf);
                sb.append(":");
                sb.append(str);
                string = sb.toString();
            }
        }
        return zzghVarZzv.zzh(string);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzA(zzpk zzpkVar) {
        zzg();
        zzb();
        zzad();
        zzaf(new zzmf(this, zzah(true), this.zzu.zzm().zzj(zzpkVar), zzpkVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzB() {
        zzg();
        zzb();
        zzr zzrVarZzah = zzah(false);
        zzad();
        this.zzu.zzm().zzh();
        zzaf(new zzmg(this, zzrVarZzah));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzC(AtomicReference atomicReference) {
        zzg();
        zzb();
        zzaf(new zzmh(this, atomicReference, zzah(false)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzD(com.google.android.gms.internal.measurement.zzcu zzcuVar) {
        zzg();
        zzb();
        zzaf(new zzmi(this, zzah(false), zzcuVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzE() {
        zzg();
        zzb();
        zzr zzrVarZzah = zzah(true);
        zzad();
        this.zzu.zzc().zzp(null, zzfx.zzbc);
        this.zzu.zzm().zzn();
        zzaf(new zzmj(this, zzrVarZzah, true));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzF() {
        zzg();
        zzb();
        zzaf(new zzmk(this, zzah(true)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzG(zzlt zzltVar) {
        zzg();
        zzb();
        zzaf(new zzmm(this, zzltVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzH(Bundle bundle) {
        zzg();
        zzb();
        zzbe zzbeVar = new zzbe(bundle);
        zzad();
        zzaf(new zzmn(this, true, zzah(false), this.zzu.zzc().zzp(null, zzfx.zzbc) && this.zzu.zzm().zzl(zzbeVar), zzbeVar, bundle));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzI() {
        zzg();
        zzb();
        if (zzh()) {
            return;
        }
        if (zzK()) {
            this.zza.zzc();
            return;
        }
        zzib zzibVar = this.zzu;
        if (zzibVar.zzc().zzE()) {
            return;
        }
        zzibVar.zzaU();
        List<ResolveInfo> listQueryIntentServices = zzibVar.zzaY().getPackageManager().queryIntentServices(new Intent().setClassName(zzibVar.zzaY(), "com.google.android.gms.measurement.AppMeasurementService"), 65536);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            zzibVar.zzaV().zzb().zza("Unable to use remote or local measurement implementation. Please register the AppMeasurementService service in the app manifest");
            return;
        }
        Intent intent = new Intent("com.google.android.gms.measurement.START");
        Context contextZzaY = zzibVar.zzaY();
        zzibVar.zzaU();
        intent.setComponent(new ComponentName(contextZzaY, "com.google.android.gms.measurement.AppMeasurementService"));
        this.zza.zza(intent);
    }

    final Boolean zzJ() {
        return this.zzc;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0113  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final boolean zzK() {
        zzg();
        zzb();
        if (this.zzc == null) {
            zzg();
            zzb();
            zzib zzibVar = this.zzu;
            zzhg zzhgVarZzd = zzibVar.zzd();
            zzhgVarZzd.zzg();
            boolean z = false;
            Boolean boolValueOf = !zzhgVarZzd.zzd().contains("use_service") ? null : Boolean.valueOf(zzhgVarZzd.zzd().getBoolean("use_service", false));
            if (boolValueOf == null || !boolValueOf.booleanValue()) {
                zzibVar.zzaU();
                if (this.zzu.zzv().zzo() == 1) {
                    z = true;
                    if (!z && zzibVar.zzc().zzE()) {
                        zzibVar.zzaV().zzb().zza("No way to upload. Consider using the full version of Analytics");
                    } else if (z) {
                        zzhg zzhgVarZzd2 = zzibVar.zzd();
                        zzhgVarZzd2.zzg();
                        SharedPreferences.Editor editorEdit = zzhgVarZzd2.zzd().edit();
                        editorEdit.putBoolean("use_service", z);
                        editorEdit.apply();
                    }
                    z = z;
                } else {
                    zzibVar.zzaV().zzk().zza("Checking service availability");
                    int iZzai = zzibVar.zzk().zzai(12451000);
                    if (iZzai != 0) {
                        if (iZzai == 1) {
                            zzibVar.zzaV().zzk().zza("Service missing");
                        } else if (iZzai != 2) {
                            if (iZzai == 3) {
                                zzibVar.zzaV().zze().zza("Service disabled");
                            } else if (iZzai == 9) {
                                zzibVar.zzaV().zze().zza("Service invalid");
                            } else if (iZzai != 18) {
                                zzibVar.zzaV().zze().zzb("Unexpected service status", Integer.valueOf(iZzai));
                            } else {
                                zzibVar.zzaV().zze().zza("Service updating");
                            }
                            z = false;
                        } else {
                            zzibVar.zzaV().zzj().zza("Service container out of date");
                            if (zzibVar.zzk().zzah() >= 17443) {
                                z = boolValueOf == null;
                                z = false;
                            }
                        }
                        if (!z) {
                            if (z) {
                            }
                            z = z;
                        }
                    } else {
                        zzibVar.zzaV().zzk().zza("Service available");
                    }
                    z = true;
                    if (!z) {
                    }
                }
            }
            this.zzc = Boolean.valueOf(z);
        }
        return this.zzc.booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzL(zzga zzgaVar) {
        zzg();
        Preconditions.checkNotNull(zzgaVar);
        this.zzb = zzgaVar;
        zzV();
        zzX();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzM() {
        zzg();
        zzb();
        zzne zzneVar = this.zza;
        zzneVar.zzb();
        try {
            ConnectionTracker.getInstance().unbindService(this.zzu.zzaY(), zzneVar);
        } catch (IllegalArgumentException | IllegalStateException unused) {
        }
        this.zzb = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzN(com.google.android.gms.internal.measurement.zzcu zzcuVar, zzbg zzbgVar, String str) {
        zzg();
        zzb();
        zzib zzibVar = this.zzu;
        if (zzibVar.zzk().zzai(12451000) == 0) {
            zzaf(new zzmo(this, zzbgVar, str, zzcuVar));
        } else {
            zzibVar.zzaV().zze().zza("Not bundling data. Service unavailable or out of date");
            zzibVar.zzk().zzao(zzcuVar, new byte[0]);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzO() {
        zzg();
        zzb();
        return !zzK() || this.zzu.zzk().zzah() >= ((Integer) zzfx.zzaJ.zzb(null)).intValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzP() {
        zzg();
        zzb();
        return !zzK() || this.zzu.zzk().zzah() >= 241200;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzQ() {
        zzga zzgaVar = this.zzb;
        if (zzgaVar == null) {
            this.zzu.zzaV().zzb().zza("Failed to send storage consent settings to service");
            return;
        }
        try {
            zzr zzrVarZzah = zzah(false);
            Preconditions.checkNotNull(zzrVarZzah);
            zzgaVar.zzy(zzrVarZzah);
            zzV();
        } catch (RemoteException e) {
            this.zzu.zzaV().zzb().zzb("Failed to send storage consent settings to the service", e);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzR() {
        zzga zzgaVar = this.zzb;
        if (zzgaVar == null) {
            this.zzu.zzaV().zzb().zza("Failed to send Dma consent settings to service");
            return;
        }
        try {
            zzr zzrVarZzah = zzah(false);
            Preconditions.checkNotNull(zzrVarZzah);
            zzgaVar.zzz(zzrVarZzah);
            zzV();
        } catch (RemoteException e) {
            this.zzu.zzaV().zzb().zzb("Failed to send Dma consent settings to the service", e);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzS(AtomicReference atomicReference, zzr zzrVar, Bundle bundle) {
        zzga zzgaVar;
        synchronized (atomicReference) {
            try {
                zzgaVar = this.zzb;
            } catch (RemoteException e) {
                this.zzu.zzaV().zzb().zzb("Failed to request trigger URIs; remote exception", e);
                atomicReference.notifyAll();
            }
            if (zzgaVar == null) {
                this.zzu.zzaV().zzb().zza("Failed to request trigger URIs; not connected to service");
                return;
            }
            Preconditions.checkNotNull(zzrVar);
            zzgaVar.zzD(zzrVar, bundle, new zzmd(this, atomicReference));
            zzV();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzT(AtomicReference atomicReference, zzr zzrVar, zzon zzonVar) {
        zzga zzgaVar;
        synchronized (atomicReference) {
            try {
                zzgaVar = this.zzb;
            } catch (RemoteException e) {
                this.zzu.zzaV().zzb().zzb("[sgtm] Failed to get upload batches; remote exception", e);
                atomicReference.notifyAll();
            }
            if (zzgaVar == null) {
                this.zzu.zzaV().zzb().zza("[sgtm] Failed to get upload batches; not connected to service");
                return;
            }
            Preconditions.checkNotNull(zzrVar);
            zzgaVar.zzB(zzrVar, zzonVar, new zzme(this, atomicReference));
            zzV();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzU(zzr zzrVar, zzaf zzafVar) {
        zzga zzgaVar = this.zzb;
        if (zzgaVar == null) {
            this.zzu.zzaV().zzb().zza("[sgtm] Discarding data. Failed to update batch upload status.");
            return;
        }
        try {
            zzgaVar.zzC(zzrVar, zzafVar);
            zzV();
        } catch (RemoteException e) {
            this.zzu.zzaV().zzb().zzc("[sgtm] Failed to update batch upload status, rowId, exception", Long.valueOf(zzafVar.zza), e);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzW(ComponentName componentName) {
        zzg();
        if (this.zzb != null) {
            this.zzb = null;
            this.zzu.zzaV().zzk().zzb("Disconnected from device MeasurementService", componentName);
            zzg();
            zzI();
        }
    }

    final /* synthetic */ zzne zzY() {
        return this.zza;
    }

    final /* synthetic */ zzga zzZ() {
        return this.zzb;
    }

    final /* synthetic */ void zzaa(zzga zzgaVar) {
        this.zzb = null;
    }

    final /* synthetic */ ScheduledExecutorService zzab() {
        return this.zze;
    }

    final /* synthetic */ void zzac(ScheduledExecutorService scheduledExecutorService) {
        this.zze = scheduledExecutorService;
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zze() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzh() {
        zzg();
        zzb();
        return this.zzb != null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzi() {
        zzg();
        zzb();
        zzaf(new zzmq(this, zzah(true)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzj(boolean z) {
        zzg();
        zzb();
        if (zzO()) {
            zzaf(new zzmr(this, zzah(false)));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzk(boolean z) {
        zzg();
        zzb();
        zzaf(new Runnable() { // from class: com.google.android.gms.measurement.internal.zznj
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzQ();
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzl() {
        zzg();
        zzb();
        zzaf(new Runnable() { // from class: com.google.android.gms.measurement.internal.zznf
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzR();
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00fd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final void zzm(zzga zzgaVar, AbstractSafeParcelable abstractSafeParcelable, zzr zzrVar) {
        zzib zzibVar;
        int i;
        zzr zzrVar2;
        long jElapsedRealtime;
        long j;
        AbstractSafeParcelable abstractSafeParcelable2 = abstractSafeParcelable;
        zzg();
        zzb();
        zzad();
        zzib zzibVar2 = this.zzu;
        zzibVar2.zzc();
        zzr zzrVar3 = zzrVar;
        int size = 100;
        int i2 = 0;
        for (int i3 = 100; i2 < 1001 && size == i3; i3 = 100) {
            zzib zzibVar3 = this.zzu;
            ArrayList arrayList = new ArrayList();
            List listZzm = zzibVar3.zzm().zzm(i3);
            if (listZzm != null) {
                arrayList.addAll(listZzm);
                size = listZzm.size();
            } else {
                size = 0;
            }
            if (abstractSafeParcelable2 != null && size < i3) {
                arrayList.add(new zzgj(abstractSafeParcelable2, zzrVar3.zzc, zzrVar3.zzj));
            }
            String str = null;
            boolean zZzp = zzibVar2.zzc().zzp(null, zzfx.zzaO);
            int size2 = arrayList.size();
            int i4 = 0;
            while (i4 < size2) {
                zzgj zzgjVar = (zzgj) arrayList.get(i4);
                AbstractSafeParcelable abstractSafeParcelable3 = zzgjVar.zza;
                if (zzibVar2.zzc().zzp(str, zzfx.zzbc)) {
                    String str2 = zzgjVar.zzb;
                    if (TextUtils.isEmpty(str2)) {
                        zzibVar = zzibVar2;
                        i = i4;
                        zzrVar2 = zzrVar3;
                    } else {
                        i = i4;
                        zzibVar = zzibVar2;
                        zzrVar2 = new zzr(zzrVar3.zza, zzrVar3.zzb, str2, zzgjVar.zzc, zzrVar3.zzd, zzrVar3.zze, zzrVar3.zzf, zzrVar3.zzg, zzrVar3.zzh, zzrVar3.zzi, zzrVar3.zzk, zzrVar3.zzl, zzrVar3.zzm, zzrVar3.zzn, zzrVar3.zzo, zzrVar3.zzp, zzrVar3.zzq, zzrVar3.zzr, zzrVar3.zzs, zzrVar3.zzt, zzrVar3.zzu, zzrVar3.zzv, zzrVar3.zzw, zzrVar3.zzx, zzrVar3.zzy, zzrVar3.zzz, zzrVar3.zzA, zzrVar3.zzB, zzrVar3.zzC, zzrVar3.zzD, zzrVar3.zzE);
                    }
                }
                if (abstractSafeParcelable3 instanceof zzbg) {
                    if (zZzp) {
                        try {
                            zzib zzibVar4 = this.zzu;
                            long jCurrentTimeMillis = zzibVar4.zzaZ().currentTimeMillis();
                            try {
                                j = jCurrentTimeMillis;
                                jElapsedRealtime = zzibVar4.zzaZ().elapsedRealtime();
                            } catch (RemoteException e) {
                                e = e;
                                j = jCurrentTimeMillis;
                                jElapsedRealtime = 0;
                                this.zzu.zzaV().zzb().zzb("Failed to send event to the service", e);
                                if (zZzp && j != 0) {
                                    zzib zzibVar5 = this.zzu;
                                    zzgp.zza(zzibVar5).zzb(36301, 13, j, zzibVar5.zzaZ().currentTimeMillis(), (int) (zzibVar5.zzaZ().elapsedRealtime() - jElapsedRealtime));
                                }
                                str = null;
                                i4 = i + 1;
                                zzrVar3 = zzrVar2;
                                zzibVar2 = zzibVar;
                            }
                        } catch (RemoteException e2) {
                            e = e2;
                            jElapsedRealtime = 0;
                            j = 0;
                        }
                    } else {
                        jElapsedRealtime = 0;
                        j = 0;
                    }
                    try {
                        zzgaVar.zze((zzbg) abstractSafeParcelable3, zzrVar2);
                        if (zZzp) {
                            zzibVar.zzaV().zzk().zza("Logging telemetry for logEvent from database");
                            zzib zzibVar6 = this.zzu;
                            zzgp.zza(zzibVar6).zzb(36301, 0, j, zzibVar6.zzaZ().currentTimeMillis(), (int) (zzibVar6.zzaZ().elapsedRealtime() - jElapsedRealtime));
                        }
                    } catch (RemoteException e3) {
                        e = e3;
                        this.zzu.zzaV().zzb().zzb("Failed to send event to the service", e);
                        if (zZzp) {
                            zzib zzibVar52 = this.zzu;
                            zzgp.zza(zzibVar52).zzb(36301, 13, j, zzibVar52.zzaZ().currentTimeMillis(), (int) (zzibVar52.zzaZ().elapsedRealtime() - jElapsedRealtime));
                        }
                    }
                } else if (abstractSafeParcelable3 instanceof zzpk) {
                    try {
                        zzgaVar.zzf((zzpk) abstractSafeParcelable3, zzrVar2);
                    } catch (RemoteException e4) {
                        this.zzu.zzaV().zzb().zzb("Failed to send user property to the service", e4);
                    }
                } else if (abstractSafeParcelable3 instanceof zzah) {
                    try {
                        zzgaVar.zzn((zzah) abstractSafeParcelable3, zzrVar2);
                    } catch (RemoteException e5) {
                        this.zzu.zzaV().zzb().zzb("Failed to send conditional user property to the service", e5);
                    }
                } else {
                    zzib zzibVar7 = this.zzu;
                    str = null;
                    if (zzibVar7.zzc().zzp(null, zzfx.zzbc) && (abstractSafeParcelable3 instanceof zzbe)) {
                        try {
                            zzgaVar.zzu(((zzbe) abstractSafeParcelable3).zzf(), zzrVar2);
                        } catch (RemoteException e6) {
                            this.zzu.zzaV().zzb().zzb("Failed to send default event parameters to the service", e6);
                        }
                    } else {
                        zzibVar7.zzaV().zzb().zza("Discarding data. Unrecognized parcel type.");
                    }
                    i4 = i + 1;
                    zzrVar3 = zzrVar2;
                    zzibVar2 = zzibVar;
                }
                str = null;
                i4 = i + 1;
                zzrVar3 = zzrVar2;
                zzibVar2 = zzibVar;
            }
            i2++;
            abstractSafeParcelable2 = abstractSafeParcelable;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzn(zzbg zzbgVar, String str) {
        Preconditions.checkNotNull(zzbgVar);
        zzg();
        zzb();
        zzad();
        zzaf(new zzms(this, true, zzah(true), this.zzu.zzm().zzi(zzbgVar), zzbgVar, str));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzp(zzah zzahVar) {
        Preconditions.checkNotNull(zzahVar);
        zzg();
        zzb();
        this.zzu.zzaU();
        zzaf(new zzmt(this, true, zzah(true), this.zzu.zzm().zzk(zzahVar), new zzah(zzahVar), zzahVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzq(AtomicReference atomicReference, String str, String str2, String str3) {
        zzg();
        zzb();
        zzaf(new zzmu(this, atomicReference, null, str2, str3, zzah(false)));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzs(com.google.android.gms.internal.measurement.zzcu zzcuVar, String str, String str2) {
        zzg();
        zzb();
        zzaf(new zzmv(this, str, str2, zzah(false), zzcuVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzt(AtomicReference atomicReference, String str, String str2, String str3, boolean z) {
        zzg();
        zzb();
        zzaf(new zzmw(this, atomicReference, null, str2, str3, zzah(false), z));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzu(com.google.android.gms.internal.measurement.zzcu zzcuVar, String str, String str2, boolean z) {
        zzg();
        zzb();
        zzaf(new zzmb(this, str, str2, zzah(false), z, zzcuVar));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzv(AtomicReference atomicReference, boolean z) {
        zzg();
        zzb();
        zzaf(new zzmc(this, atomicReference, zzah(false), z));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzw(final AtomicReference atomicReference, final Bundle bundle) {
        zzg();
        zzb();
        final zzr zzrVarZzah = zzah(false);
        zzaf(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzng
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzS(atomicReference, zzrVarZzah, bundle);
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzx(final AtomicReference atomicReference, final zzon zzonVar) {
        zzg();
        zzb();
        final zzr zzrVarZzah = zzah(false);
        zzaf(new Runnable() { // from class: com.google.android.gms.measurement.internal.zznh
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzT(atomicReference, zzrVarZzah, zzonVar);
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzy(final zzaf zzafVar) {
        zzg();
        zzb();
        final zzr zzrVarZzah = zzah(true);
        Preconditions.checkNotNull(zzrVarZzah);
        zzaf(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzni
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                this.zza.zzU(zzrVarZzah, zzafVar);
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final zzao zzz() {
        zzg();
        zzb();
        zzga zzgaVar = this.zzb;
        if (zzgaVar == null) {
            zzI();
            this.zzu.zzaV().zzj().zza("Failed to get consents; not connected to service yet.");
            return null;
        }
        zzr zzrVarZzah = zzah(false);
        Preconditions.checkNotNull(zzrVarZzah);
        try {
            zzao zzaoVarZzw = zzgaVar.zzw(zzrVarZzah);
            zzV();
            return zzaoVarZzw;
        } catch (RemoteException e) {
            this.zzu.zzaV().zzb().zzb("Failed to get consents; remote exception", e);
            return null;
        }
    }
}
