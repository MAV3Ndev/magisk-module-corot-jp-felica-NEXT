package com.google.android.gms.pay;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzbg extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzbg> CREATOR = new zzbh();
    private zzbi zza;
    private String zzb;
    private String zzc;
    private String zzd;
    private Bitmap zze;
    private String zzf;
    private PendingIntent zzg;
    private String zzh;
    private Bitmap zzi;
    private final int zzj;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private zzbg() {
        this.zzj = 14343392;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzbg) {
            zzbg zzbgVar = (zzbg) obj;
            if (Objects.equal(this.zza, zzbgVar.zza) && Objects.equal(this.zzb, zzbgVar.zzb) && Objects.equal(this.zzc, zzbgVar.zzc) && Objects.equal(this.zzd, zzbgVar.zzd) && Objects.equal(this.zze, zzbgVar.zze) && Objects.equal(this.zzf, zzbgVar.zzf) && Objects.equal(this.zzg, zzbgVar.zzg) && Objects.equal(this.zzh, zzbgVar.zzh) && Objects.equal(this.zzi, zzbgVar.zzi) && Objects.equal(Integer.valueOf(this.zzj), Integer.valueOf(zzbgVar.zzj))) {
                return true;
            }
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi, Integer.valueOf(this.zzj));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 1, this.zza, i, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzc, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzd, false);
        SafeParcelWriter.writeParcelable(parcel, 5, this.zze, i, false);
        SafeParcelWriter.writeString(parcel, 6, this.zzf, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzg, i, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzh, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzi, i, false);
        SafeParcelWriter.writeInt(parcel, 10, this.zzj);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    zzbg(zzbi zzbiVar, String str, String str2, String str3, Bitmap bitmap, String str4, PendingIntent pendingIntent, String str5, Bitmap bitmap2, int i) {
        this.zza = zzbiVar;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = bitmap;
        this.zzf = str4;
        this.zzg = pendingIntent;
        this.zzh = str5;
        this.zzi = bitmap2;
        this.zzj = i;
    }
}
