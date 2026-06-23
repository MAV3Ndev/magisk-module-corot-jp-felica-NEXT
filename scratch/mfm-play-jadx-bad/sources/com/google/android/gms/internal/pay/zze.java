package com.google.android.gms.internal.pay;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.pay.EmoneyReadiness;
import com.google.android.gms.pay.zzac;
import com.google.android.gms.pay.zzai;
import com.google.android.gms.pay.zzak;
import com.google.android.gms.pay.zzam;
import com.google.android.gms.pay.zzao;
import com.google.android.gms.pay.zzaq;
import com.google.android.gms.pay.zzau;
import com.google.android.gms.pay.zzbm;
import com.google.android.gms.pay.zzbv;
import com.google.android.gms.pay.zzcr;
import com.google.android.gms.pay.zzct;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public abstract class zze extends zzb implements zzf {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zze() {
        super("com.google.android.gms.pay.internal.IPayServiceCallbacks");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.pay.zzb
    protected final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        switch (i) {
            case 2:
                Status status = (Status) zzc.zza(parcel, Status.CREATOR);
                zzc.zzb(parcel);
                zzt(status);
                return true;
            case 3:
                Status status2 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzac zzacVar = (zzac) zzc.zza(parcel, zzac.CREATOR);
                zzc.zzb(parcel);
                zzo(status2, zzacVar);
                return true;
            case 4:
                Status status3 = (Status) zzc.zza(parcel, Status.CREATOR);
                PendingIntent pendingIntent = (PendingIntent) zzc.zza(parcel, PendingIntent.CREATOR);
                zzc.zzb(parcel);
                zzp(status3, pendingIntent);
                return true;
            case 5:
                Status status4 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzbv zzbvVar = (zzbv) zzc.zza(parcel, zzbv.CREATOR);
                zzc.zzb(parcel);
                zzq(status4, zzbvVar);
                return true;
            case 6:
                com.google.android.gms.pay.zzi zziVar = (com.google.android.gms.pay.zzi) zzc.zza(parcel, com.google.android.gms.pay.zzi.CREATOR);
                zzc.zzb(parcel);
                zze(zziVar);
                return true;
            case 7:
                Status status5 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzak zzakVar = (zzak) zzc.zza(parcel, zzak.CREATOR);
                zzc.zzb(parcel);
                zzs(status5, zzakVar);
                return true;
            case 8:
                Status status6 = (Status) zzc.zza(parcel, Status.CREATOR);
                boolean z = parcel.readInt() != 0;
                zzc.zzb(parcel);
                zzb(status6, z);
                return true;
            case 9:
                Status status7 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzam zzamVar = (zzam) zzc.zza(parcel, zzam.CREATOR);
                zzc.zzb(parcel);
                zzv(status7, zzamVar);
                return true;
            case 10:
                zzbm zzbmVar = (zzbm) zzc.zza(parcel, zzbm.CREATOR);
                zzc.zzb(parcel);
                zzm(zzbmVar);
                return true;
            case 11:
                Status status8 = (Status) zzc.zza(parcel, Status.CREATOR);
                com.google.android.gms.pay.zzs zzsVar = (com.google.android.gms.pay.zzs) zzc.zza(parcel, com.google.android.gms.pay.zzs.CREATOR);
                zzc.zzb(parcel);
                zzk(status8, zzsVar);
                return true;
            case 12:
                Status status9 = (Status) zzc.zza(parcel, Status.CREATOR);
                com.google.android.gms.pay.zzo zzoVar = (com.google.android.gms.pay.zzo) zzc.zza(parcel, com.google.android.gms.pay.zzo.CREATOR);
                zzc.zzb(parcel);
                zzd(status9, zzoVar);
                return true;
            case 13:
                Status status10 = (Status) zzc.zza(parcel, Status.CREATOR);
                com.google.android.gms.pay.zzaa zzaaVar = (com.google.android.gms.pay.zzaa) zzc.zza(parcel, com.google.android.gms.pay.zzaa.CREATOR);
                zzc.zzb(parcel);
                zzn(status10, zzaaVar);
                return true;
            case 14:
                Status status11 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzct zzctVar = (zzct) zzc.zza(parcel, zzct.CREATOR);
                zzc.zzb(parcel);
                zzw(status11, zzctVar);
                return true;
            case 15:
                Status status12 = (Status) zzc.zza(parcel, Status.CREATOR);
                byte[] bArrCreateByteArray = parcel.createByteArray();
                zzc.zzb(parcel);
                zzc(status12, bArrCreateByteArray);
                return true;
            case 16:
                Status status13 = (Status) zzc.zza(parcel, Status.CREATOR);
                com.google.android.gms.pay.zzu zzuVar = (com.google.android.gms.pay.zzu) zzc.zza(parcel, com.google.android.gms.pay.zzu.CREATOR);
                zzc.zzb(parcel);
                zzl(status13, zzuVar);
                return true;
            case 17:
                Status status14 = (Status) zzc.zza(parcel, Status.CREATOR);
                long j = parcel.readLong();
                zzc.zzb(parcel);
                zzj(status14, j);
                return true;
            case 18:
                Status status15 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzc.zzb(parcel);
                zzh(status15);
                return true;
            case 19:
                Status status16 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzau zzauVar = (zzau) zzc.zza(parcel, zzau.CREATOR);
                zzc.zzb(parcel);
                zzg(status16, zzauVar);
                return true;
            case 20:
                Status status17 = (Status) zzc.zza(parcel, Status.CREATOR);
                int i3 = parcel.readInt();
                zzc.zzb(parcel);
                zzi(status17, i3);
                return true;
            case 21:
                Status status18 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzao zzaoVar = (zzao) zzc.zza(parcel, zzao.CREATOR);
                zzc.zzb(parcel);
                zzx(status18, zzaoVar);
                return true;
            case 22:
                Status status19 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzaq zzaqVar = (zzaq) zzc.zza(parcel, zzaq.CREATOR);
                zzc.zzb(parcel);
                zzz(status19, zzaqVar);
                return true;
            case 23:
                Status status20 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzai zzaiVar = (zzai) zzc.zza(parcel, zzai.CREATOR);
                zzc.zzb(parcel);
                zzr(status20, zzaiVar);
                return true;
            case 24:
                Status status21 = (Status) zzc.zza(parcel, Status.CREATOR);
                zzcr zzcrVar = (zzcr) zzc.zza(parcel, zzcr.CREATOR);
                zzc.zzb(parcel);
                zzu(status21, zzcrVar);
                return true;
            case 25:
                Status status22 = (Status) zzc.zza(parcel, Status.CREATOR);
                com.google.android.gms.pay.zzq zzqVar = (com.google.android.gms.pay.zzq) zzc.zza(parcel, com.google.android.gms.pay.zzq.CREATOR);
                zzc.zzb(parcel);
                zzy(status22, zzqVar);
                return true;
            case 26:
                Status status23 = (Status) zzc.zza(parcel, Status.CREATOR);
                EmoneyReadiness emoneyReadiness = (EmoneyReadiness) zzc.zza(parcel, EmoneyReadiness.CREATOR);
                zzc.zzb(parcel);
                zzf(status23, emoneyReadiness);
                return true;
            default:
                return false;
        }
    }
}
