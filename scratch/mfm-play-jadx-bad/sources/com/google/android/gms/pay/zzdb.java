package com.google.android.gms.pay;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzdb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzdb> CREATOR = new zzdc();
    private String zza;
    private String zzb;
    private String zzc;
    private PendingIntent zzd;
    private String zze;
    private PendingIntent zzf;
    private Bitmap zzg;
    private Bitmap[] zzh;
    private zzc zzi;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private zzdb() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzdb) {
            zzdb zzdbVar = (zzdb) obj;
            if (Objects.equal(this.zza, zzdbVar.zza) && Objects.equal(this.zzb, zzdbVar.zzb) && Objects.equal(this.zzc, zzdbVar.zzc) && Objects.equal(this.zzd, zzdbVar.zzd) && Objects.equal(this.zze, zzdbVar.zze) && Objects.equal(this.zzf, zzdbVar.zzf) && Objects.equal(this.zzg, zzdbVar.zzg) && Arrays.equals(this.zzh, zzdbVar.zzh) && Objects.equal(this.zzi, zzdbVar.zzi)) {
                return true;
            }
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return Objects.hashCode(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, Integer.valueOf(Arrays.hashCode(this.zzh)), this.zzi);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zza, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzb, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzc, false);
        SafeParcelWriter.writeParcelable(parcel, 4, this.zzd, i, false);
        SafeParcelWriter.writeString(parcel, 5, this.zze, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzf, i, false);
        SafeParcelWriter.writeParcelable(parcel, 7, this.zzg, i, false);
        SafeParcelWriter.writeTypedArray(parcel, 8, this.zzh, i, false);
        SafeParcelWriter.writeParcelable(parcel, 9, this.zzi, i, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    zzdb(String str, String str2, String str3, PendingIntent pendingIntent, String str4, PendingIntent pendingIntent2, Bitmap bitmap, Bitmap[] bitmapArr, zzc zzcVar) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = pendingIntent;
        this.zze = str4;
        this.zzf = pendingIntent2;
        this.zzg = bitmap;
        this.zzh = bitmapArr;
        this.zzi = zzcVar;
    }
}
