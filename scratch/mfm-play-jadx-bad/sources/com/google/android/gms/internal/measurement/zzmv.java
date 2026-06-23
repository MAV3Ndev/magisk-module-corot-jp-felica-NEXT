package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public class zzmv {
    protected volatile zznl zza;
    private volatile zzlg zzb;
    private volatile boolean zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzmv)) {
            return false;
        }
        zzmv zzmvVar = (zzmv) obj;
        zznl zznlVar = this.zza;
        zznl zznlVar2 = zzmvVar.zza;
        if (zznlVar == null && zznlVar2 == null) {
            return zzc().equals(zzmvVar.zzc());
        }
        if (zznlVar != null && zznlVar2 != null) {
            return zznlVar.equals(zznlVar2);
        }
        if (zznlVar != null) {
            zzmvVar.zzd(zznlVar.zzcE());
            return zznlVar.equals(zzmvVar.zza);
        }
        zzd(zznlVar2.zzcE());
        return this.zza.equals(zznlVar2);
    }

    public int hashCode() {
        return 1;
    }

    public final zznl zza(zznl zznlVar) {
        zznl zznlVar2 = this.zza;
        this.zzb = null;
        this.zza = zznlVar;
        return zznlVar2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzb() {
        if (this.zzb != null) {
            return ((zzlf) this.zzb).zza.length;
        }
        if (this.zza != null) {
            return this.zza.zzcn();
        }
        return 0;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzlg zzc() {
        if (this.zzb != null) {
            return this.zzb;
        }
        synchronized (this) {
            if (this.zzb != null) {
                return this.zzb;
            }
            if (this.zza == null) {
                this.zzb = zzlg.zzb;
            } else {
                this.zzb = this.zza.zzcb();
            }
            return this.zzb;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final void zzd(zznl zznlVar) {
        if (this.zza != null) {
            return;
        }
        synchronized (this) {
            if (this.zza != null) {
                return;
            }
            try {
                this.zza = zznlVar;
                this.zzb = zzlg.zzb;
            } catch (zzmq unused) {
                this.zzc = true;
                this.zza = zznlVar;
                this.zzb = zzlg.zzb;
            }
        }
    }
}
