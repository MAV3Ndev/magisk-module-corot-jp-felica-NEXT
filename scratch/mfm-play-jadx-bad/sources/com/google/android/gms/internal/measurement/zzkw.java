package com.google.android.gms.internal.measurement;

import com.google.common.base.Ascii;
import java.io.IOException;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzkw {
    public static final /* synthetic */ int zza = 0;
    private static volatile int zzb = 100;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zza(byte[] bArr, int i, zzkv zzkvVar) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zzb(b, bArr, i2, zzkvVar);
        }
        zzkvVar.zza = b;
        return i2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzb(int i, byte[] bArr, int i2, zzkv zzkvVar) {
        byte b = bArr[i2];
        int i3 = i2 + 1;
        int i4 = i & WorkQueueKt.MASK;
        if (b >= 0) {
            zzkvVar.zza = i4 | (b << 7);
            return i3;
        }
        int i5 = i4 | ((b & 127) << 7);
        int i6 = i2 + 2;
        byte b2 = bArr[i3];
        if (b2 >= 0) {
            zzkvVar.zza = i5 | (b2 << Ascii.SO);
            return i6;
        }
        int i7 = i5 | ((b2 & 127) << 14);
        int i8 = i2 + 3;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzkvVar.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & 127) << 21);
        int i10 = i2 + 4;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzkvVar.zza = i9 | (b4 << Ascii.FS);
            return i10;
        }
        int i11 = i9 | ((b4 & 127) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzkvVar.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzc(byte[] bArr, int i, zzkv zzkvVar) {
        long j = bArr[i];
        int i2 = i + 1;
        if (j >= 0) {
            zzkvVar.zzb = j;
            return i2;
        }
        int i3 = i + 2;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & 127)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & 127)) << i4;
            b = b2;
            i3 = i5;
        }
        zzkvVar.zzb = j2;
        return i3;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzd(byte[] bArr, int i) {
        int i2 = bArr[i] & 255;
        int i3 = bArr[i + 1] & 255;
        int i4 = bArr[i + 2] & 255;
        return ((bArr[i + 3] & 255) << 24) | (i3 << 8) | i2 | (i4 << 16);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static long zze(byte[] bArr, int i) {
        return (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48) | ((((long) bArr[i + 7]) & 255) << 56);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzf(byte[] bArr, int i, zzkv zzkvVar) throws zzmq {
        int i2;
        int iZza = zza(bArr, i, zzkvVar);
        int i3 = zzkvVar.zza;
        if (i3 < 0) {
            throw new zzmq("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        if (i3 == 0) {
            zzkvVar.zzc = "";
            return iZza;
        }
        int i4 = zzor.zza;
        int length = bArr.length;
        if ((((length - iZza) - i3) | iZza | i3) < 0) {
            throw new ArrayIndexOutOfBoundsException(String.format("buffer length=%d, index=%d, size=%d", Integer.valueOf(length), Integer.valueOf(iZza), Integer.valueOf(i3)));
        }
        int i5 = iZza + i3;
        char[] cArr = new char[i3];
        int i6 = 0;
        while (iZza < i5) {
            byte b = bArr[iZza];
            if (!zzop.zza(b)) {
                break;
            }
            iZza++;
            cArr[i6] = (char) b;
            i6++;
        }
        int i7 = i6;
        while (iZza < i5) {
            int i8 = iZza + 1;
            byte b2 = bArr[iZza];
            if (zzop.zza(b2)) {
                cArr[i7] = (char) b2;
                i7++;
                iZza = i8;
                while (iZza < i5) {
                    byte b3 = bArr[iZza];
                    if (zzop.zza(b3)) {
                        iZza++;
                        cArr[i7] = (char) b3;
                        i7++;
                    }
                }
            } else {
                if (b2 < -32) {
                    if (i8 >= i5) {
                        throw new zzmq("Protocol message had invalid UTF-8.");
                    }
                    i2 = i7 + 1;
                    iZza += 2;
                    zzop.zzb(b2, bArr[i8], cArr, i7);
                } else if (b2 < -16) {
                    if (i8 >= i5 - 1) {
                        throw new zzmq("Protocol message had invalid UTF-8.");
                    }
                    i2 = i7 + 1;
                    int i9 = iZza + 2;
                    iZza += 3;
                    zzop.zzc(b2, bArr[i8], bArr[i9], cArr, i7);
                } else {
                    if (i8 >= i5 - 2) {
                        throw new zzmq("Protocol message had invalid UTF-8.");
                    }
                    byte b4 = bArr[i8];
                    int i10 = iZza + 3;
                    byte b5 = bArr[iZza + 2];
                    iZza += 4;
                    zzop.zzd(b2, b4, b5, bArr[i10], cArr, i7);
                    i7 += 2;
                }
                i7 = i2;
            }
        }
        zzkvVar.zzc = new String(cArr, 0, i7);
        return i5;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzg(byte[] bArr, int i, zzkv zzkvVar) throws zzmq {
        int iZza = zza(bArr, i, zzkvVar);
        int i2 = zzkvVar.zza;
        if (i2 < 0) {
            throw new zzmq("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
        }
        if (i2 > bArr.length - iZza) {
            throw new zzmq("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        if (i2 == 0) {
            zzkvVar.zzc = zzlg.zzb;
            return iZza;
        }
        zzkvVar.zzc = zzlg.zzh(bArr, iZza, i2);
        return iZza + i2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzh(zznw zznwVar, byte[] bArr, int i, int i2, zzkv zzkvVar) throws IOException {
        Object objZza = zznwVar.zza();
        int iZzj = zzj(objZza, zznwVar, bArr, i, i2, zzkvVar);
        zznwVar.zzj(objZza);
        zzkvVar.zzc = objZza;
        return iZzj;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzi(zznw zznwVar, byte[] bArr, int i, int i2, int i3, zzkv zzkvVar) throws IOException {
        Object objZza = zznwVar.zza();
        int iZzk = zzk(objZza, zznwVar, bArr, i, i2, i3, zzkvVar);
        zznwVar.zzj(objZza);
        zzkvVar.zzc = objZza;
        return iZzk;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzj(Object obj, zznw zznwVar, byte[] bArr, int i, int i2, zzkv zzkvVar) throws IOException {
        int iZzb = i + 1;
        int i3 = bArr[i];
        if (i3 < 0) {
            iZzb = zzb(i3, bArr, iZzb, zzkvVar);
            i3 = zzkvVar.zza;
        }
        int i4 = iZzb;
        if (i3 < 0 || i3 > i2 - i4) {
            throw new zzmq("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
        }
        int i5 = zzkvVar.zze + 1;
        zzkvVar.zze = i5;
        zzq(i5);
        int i6 = i4 + i3;
        zznwVar.zzi(obj, bArr, i4, i6, zzkvVar);
        zzkvVar.zze--;
        zzkvVar.zzc = obj;
        return i6;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzk(Object obj, zznw zznwVar, byte[] bArr, int i, int i2, int i3, zzkv zzkvVar) throws IOException {
        int i4 = zzkvVar.zze + 1;
        zzkvVar.zze = i4;
        zzq(i4);
        int iZzh = ((zzno) zznwVar).zzh(obj, bArr, i, i2, i3, zzkvVar);
        zzkvVar.zze--;
        zzkvVar.zzc = obj;
        return iZzh;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzl(int i, byte[] bArr, int i2, int i3, zzmn zzmnVar, zzkv zzkvVar) {
        zzmf zzmfVar = (zzmf) zzmnVar;
        int iZza = zza(bArr, i2, zzkvVar);
        zzmfVar.zzh(zzkvVar.zza);
        while (iZza < i3) {
            int iZza2 = zza(bArr, iZza, zzkvVar);
            if (i != zzkvVar.zza) {
                break;
            }
            iZza = zza(bArr, iZza2, zzkvVar);
            zzmfVar.zzh(zzkvVar.zza);
        }
        return iZza;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzm(byte[] bArr, int i, zzmn zzmnVar, zzkv zzkvVar) throws IOException {
        zzmf zzmfVar = (zzmf) zzmnVar;
        int iZza = zza(bArr, i, zzkvVar);
        int i2 = zzkvVar.zza + iZza;
        while (iZza < i2) {
            iZza = zza(bArr, iZza, zzkvVar);
            zzmfVar.zzh(zzkvVar.zza);
        }
        if (iZza == i2) {
            return iZza;
        }
        throw new zzmq("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzn(zznw zznwVar, int i, byte[] bArr, int i2, int i3, zzmn zzmnVar, zzkv zzkvVar) throws IOException {
        int iZzh = zzh(zznwVar, bArr, i2, i3, zzkvVar);
        zzmnVar.add(zzkvVar.zzc);
        while (iZzh < i3) {
            int iZza = zza(bArr, iZzh, zzkvVar);
            if (i != zzkvVar.zza) {
                break;
            }
            iZzh = zzh(zznwVar, bArr, iZza, i3, zzkvVar);
            zzmnVar.add(zzkvVar.zzc);
        }
        return iZzh;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzo(int i, byte[] bArr, int i2, int i3, zzoi zzoiVar, zzkv zzkvVar) throws zzmq {
        if ((i >>> 3) == 0) {
            throw new zzmq("Protocol message contained an invalid tag (zero).");
        }
        int i4 = i & 7;
        if (i4 == 0) {
            int iZzc = zzc(bArr, i2, zzkvVar);
            zzoiVar.zzk(i, Long.valueOf(zzkvVar.zzb));
            return iZzc;
        }
        if (i4 == 1) {
            zzoiVar.zzk(i, Long.valueOf(zze(bArr, i2)));
            return i2 + 8;
        }
        if (i4 == 2) {
            int iZza = zza(bArr, i2, zzkvVar);
            int i5 = zzkvVar.zza;
            if (i5 < 0) {
                throw new zzmq("CodedInputStream encountered an embedded string or message which claimed to have negative size.");
            }
            if (i5 > bArr.length - iZza) {
                throw new zzmq("While parsing a protocol message, the input ended unexpectedly in the middle of a field.  This could mean either that the input has been truncated or that an embedded message misreported its own length.");
            }
            if (i5 == 0) {
                zzoiVar.zzk(i, zzlg.zzb);
            } else {
                zzoiVar.zzk(i, zzlg.zzh(bArr, iZza, i5));
            }
            return iZza + i5;
        }
        if (i4 != 3) {
            if (i4 != 5) {
                throw new zzmq("Protocol message contained an invalid tag (zero).");
            }
            zzoiVar.zzk(i, Integer.valueOf(zzd(bArr, i2)));
            return i2 + 4;
        }
        int i6 = (i & (-8)) | 4;
        zzoi zzoiVarZzb = zzoi.zzb();
        int i7 = zzkvVar.zze + 1;
        zzkvVar.zze = i7;
        zzq(i7);
        int i8 = 0;
        while (true) {
            if (i2 >= i3) {
                break;
            }
            int iZza2 = zza(bArr, i2, zzkvVar);
            int i9 = zzkvVar.zza;
            if (i9 == i6) {
                i8 = i9;
                i2 = iZza2;
                break;
            }
            i2 = zzo(i9, bArr, iZza2, i3, zzoiVarZzb, zzkvVar);
            i8 = i9;
        }
        zzkvVar.zze--;
        if (i2 > i3 || i8 != i6) {
            throw new zzmq("Failed to parse the message.");
        }
        zzoiVar.zzk(i, zzoiVarZzb);
        return i2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzp(int i, byte[] bArr, int i2, int i3, zzkv zzkvVar) throws zzmq {
        if ((i >>> 3) == 0) {
            throw new zzmq("Protocol message contained an invalid tag (zero).");
        }
        int i4 = i & 7;
        if (i4 == 0) {
            return zzc(bArr, i2, zzkvVar);
        }
        if (i4 == 1) {
            return i2 + 8;
        }
        if (i4 == 2) {
            return zza(bArr, i2, zzkvVar) + zzkvVar.zza;
        }
        if (i4 != 3) {
            if (i4 == 5) {
                return i2 + 4;
            }
            throw new zzmq("Protocol message contained an invalid tag (zero).");
        }
        int i5 = (i & (-8)) | 4;
        int i6 = 0;
        while (i2 < i3) {
            i2 = zza(bArr, i2, zzkvVar);
            i6 = zzkvVar.zza;
            if (i6 == i5) {
                break;
            }
            i2 = zzp(i6, bArr, i2, i3, zzkvVar);
        }
        if (i2 > i3 || i6 != i5) {
            throw new zzmq("Failed to parse the message.");
        }
        return i2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static void zzq(int i) throws zzmq {
        if (i >= zzb) {
            throw new zzmq("Protocol message had too many levels of nesting.  May be malicious.  Use setRecursionLimit() to increase the recursion depth limit.");
        }
    }
}
