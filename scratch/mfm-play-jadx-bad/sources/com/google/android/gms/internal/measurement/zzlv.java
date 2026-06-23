package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzlv {
    private static final zzlv zzd = new zzlv(true);
    final zzod zza = new zznz();
    private boolean zzb;
    private boolean zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private zzlv() {
    }

    public static zzlv zza() {
        return zzd;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzf(zzll zzllVar, zzos zzosVar, int i, Object obj) throws IOException {
        if (zzosVar == zzos.GROUP) {
            zznl zznlVar = (zznl) obj;
            zzmo.zzd(zznlVar);
            zzllVar.zza(i, 3);
            zznlVar.zzcB(zzllVar);
            zzllVar.zza(i, 4);
            return;
        }
        zzllVar.zza(i, zzosVar.zzb());
        zzot zzotVar = zzot.INT;
        switch (zzosVar) {
            case DOUBLE:
                zzllVar.zzu(Double.doubleToRawLongBits(((Double) obj).doubleValue()));
                break;
            case FLOAT:
                zzllVar.zzs(Float.floatToRawIntBits(((Float) obj).floatValue()));
                break;
            case INT64:
                zzllVar.zzt(((Long) obj).longValue());
                break;
            case UINT64:
                zzllVar.zzt(((Long) obj).longValue());
                break;
            case INT32:
                zzllVar.zzq(((Integer) obj).intValue());
                break;
            case FIXED64:
                zzllVar.zzu(((Long) obj).longValue());
                break;
            case FIXED32:
                zzllVar.zzs(((Integer) obj).intValue());
                break;
            case BOOL:
                zzllVar.zzp(((Boolean) obj).booleanValue() ? (byte) 1 : (byte) 0);
                break;
            case STRING:
                if (!(obj instanceof zzlg)) {
                    zzllVar.zzx((String) obj);
                } else {
                    zzllVar.zzj((zzlg) obj);
                }
                break;
            case GROUP:
                ((zznl) obj).zzcB(zzllVar);
                break;
            case MESSAGE:
                zzllVar.zzo((zznl) obj);
                break;
            case BYTES:
                if (!(obj instanceof zzlg)) {
                    byte[] bArr = (byte[]) obj;
                    zzllVar.zzk(bArr, 0, bArr.length);
                } else {
                    zzllVar.zzj((zzlg) obj);
                }
                break;
            case UINT32:
                zzllVar.zzr(((Integer) obj).intValue());
                break;
            case ENUM:
                if (!(obj instanceof zzmi)) {
                    zzllVar.zzq(((Integer) obj).intValue());
                } else {
                    zzllVar.zzq(((zzmi) obj).zza());
                }
                break;
            case SFIXED32:
                zzllVar.zzs(((Integer) obj).intValue());
                break;
            case SFIXED64:
                zzllVar.zzu(((Long) obj).longValue());
                break;
            case SINT32:
                int iIntValue = ((Integer) obj).intValue();
                zzllVar.zzr((iIntValue >> 31) ^ (iIntValue + iIntValue));
                break;
            case SINT64:
                long jLongValue = ((Long) obj).longValue();
                zzllVar.zzt((jLongValue >> 63) ^ (jLongValue + jLongValue));
                break;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzh(zzos zzosVar, int i, Object obj) {
        int iZzz = zzll.zzz(i << 3);
        if (zzosVar == zzos.GROUP) {
            zzmo.zzd((zznl) obj);
            iZzz += iZzz;
        }
        return iZzz + zzi(zzosVar, obj);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzi(zzos zzosVar, Object obj) {
        int iZzc;
        int iZzz;
        zzos zzosVar2 = zzos.DOUBLE;
        zzot zzotVar = zzot.INT;
        switch (zzosVar) {
            case DOUBLE:
                ((Double) obj).doubleValue();
                int i = zzll.zzb;
                return 8;
            case FLOAT:
                ((Float) obj).floatValue();
                int i2 = zzll.zzb;
                return 4;
            case INT64:
                return zzll.zzA(((Long) obj).longValue());
            case UINT64:
                return zzll.zzA(((Long) obj).longValue());
            case INT32:
                return zzll.zzA(((Integer) obj).intValue());
            case FIXED64:
                ((Long) obj).longValue();
                int i3 = zzll.zzb;
                return 8;
            case FIXED32:
                ((Integer) obj).intValue();
                int i4 = zzll.zzb;
                return 4;
            case BOOL:
                ((Boolean) obj).booleanValue();
                int i5 = zzll.zzb;
                return 1;
            case STRING:
                if (!(obj instanceof zzlg)) {
                    return zzll.zzB((String) obj);
                }
                int i6 = zzll.zzb;
                iZzc = ((zzlg) obj).zzc();
                iZzz = zzll.zzz(iZzc);
                break;
                break;
            case GROUP:
                return ((zznl) obj).zzcn();
            case MESSAGE:
                if (!(obj instanceof zzmu)) {
                    return zzll.zzC((zznl) obj);
                }
                int i7 = zzll.zzb;
                iZzc = ((zzmu) obj).zzb();
                iZzz = zzll.zzz(iZzc);
                break;
                break;
            case BYTES:
                if (!(obj instanceof zzlg)) {
                    int i8 = zzll.zzb;
                    iZzc = ((byte[]) obj).length;
                    iZzz = zzll.zzz(iZzc);
                } else {
                    int i9 = zzll.zzb;
                    iZzc = ((zzlg) obj).zzc();
                    iZzz = zzll.zzz(iZzc);
                }
                break;
            case UINT32:
                return zzll.zzz(((Integer) obj).intValue());
            case ENUM:
                return obj instanceof zzmi ? zzll.zzA(((zzmi) obj).zza()) : zzll.zzA(((Integer) obj).intValue());
            case SFIXED32:
                ((Integer) obj).intValue();
                int i10 = zzll.zzb;
                return 4;
            case SFIXED64:
                ((Long) obj).longValue();
                int i11 = zzll.zzb;
                return 8;
            case SINT32:
                int iIntValue = ((Integer) obj).intValue();
                return zzll.zzz((iIntValue >> 31) ^ (iIntValue + iIntValue));
            case SINT64:
                long jLongValue = ((Long) obj).longValue();
                return zzll.zzA((jLongValue >> 63) ^ (jLongValue + jLongValue));
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
        return iZzz + iZzc;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static int zzj(zzlu zzluVar, Object obj) {
        zzos zzosVarZzb = zzluVar.zzb();
        int iZza = zzluVar.zza();
        if (!zzluVar.zzd()) {
            return zzh(zzosVarZzb, iZza, obj);
        }
        List list = (List) obj;
        int size = list.size();
        int i = 0;
        if (!zzluVar.zze()) {
            int iZzh = 0;
            while (i < size) {
                iZzh += zzh(zzosVarZzb, iZza, list.get(i));
                i++;
            }
            return iZzh;
        }
        if (list.isEmpty()) {
            return 0;
        }
        int iZzi = 0;
        while (i < size) {
            iZzi += zzi(zzosVarZzb, list.get(i));
            i++;
        }
        return zzll.zzz(iZza << 3) + iZzi + zzll.zzz(iZzi);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static boolean zzk(Map.Entry entry) {
        zzlu zzluVar = (zzlu) entry.getKey();
        if (zzluVar.zzc() != zzot.MESSAGE) {
            return true;
        }
        if (!zzluVar.zzd()) {
            return zzl(entry.getValue());
        }
        List list = (List) entry.getValue();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (!zzl(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static boolean zzl(Object obj) {
        if (obj instanceof zznm) {
            return ((zznm) obj).zzcD();
        }
        if (obj instanceof zzmu) {
            return true;
        }
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static final int zzm(Map.Entry entry) {
        int i;
        int iZzz;
        int iZzz2;
        zzlu zzluVar = (zzlu) entry.getKey();
        Object value = entry.getValue();
        if (zzluVar.zzc() != zzot.MESSAGE || zzluVar.zzd() || zzluVar.zze()) {
            return zzj(zzluVar, value);
        }
        if (value instanceof zzmu) {
            int iZza = ((zzlu) entry.getKey()).zza();
            int iZzz3 = zzll.zzz(8);
            i = iZzz3 + iZzz3;
            iZzz = zzll.zzz(16) + zzll.zzz(iZza);
            int iZzz4 = zzll.zzz(24);
            int iZzb = ((zzmu) value).zzb();
            iZzz2 = iZzz4 + zzll.zzz(iZzb) + iZzb;
        } else {
            int iZza2 = ((zzlu) entry.getKey()).zza();
            int iZzz5 = zzll.zzz(8);
            i = iZzz5 + iZzz5;
            iZzz = zzll.zzz(16) + zzll.zzz(iZza2);
            iZzz2 = zzll.zzz(24) + zzll.zzC((zznl) value);
        }
        return i + iZzz + iZzz2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final void zzn(zzlu zzluVar, Object obj) {
        boolean z;
        zzluVar.zzb();
        byte[] bArr = zzmo.zzb;
        obj.getClass();
        zzos zzosVar = zzos.DOUBLE;
        zzot zzotVar = zzot.INT;
        switch (r0.zza()) {
            case INT:
                z = obj instanceof Integer;
                if (z) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case LONG:
                z = obj instanceof Long;
                if (z) {
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case FLOAT:
                z = obj instanceof Float;
                if (z) {
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case DOUBLE:
                z = obj instanceof Double;
                if (z) {
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case BOOLEAN:
                z = obj instanceof Boolean;
                if (z) {
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case STRING:
                z = obj instanceof String;
                if (z) {
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case BYTE_STRING:
                if ((obj instanceof zzlg) || (obj instanceof byte[])) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case ENUM:
                if ((obj instanceof Integer) || (obj instanceof zzmi)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            case MESSAGE:
                if ((obj instanceof zznl) || (obj instanceof zzmu)) {
                    return;
                }
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
            default:
                throw new IllegalArgumentException(String.format("Wrong object type used with protocol message reflection.\nField number: %d, field java type: %s, value type: %s\n", Integer.valueOf(zzluVar.zza()), zzluVar.zzb().zza(), obj.getClass().getName()));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final /* bridge */ /* synthetic */ Object clone() throws CloneNotSupportedException {
        zzlv zzlvVar = new zzlv();
        zzod zzodVar = this.zza;
        int iZzc = zzodVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            Map.Entry entryZzd = zzodVar.zzd(i);
            zzlvVar.zzd((zzlu) ((zzoa) entryZzd).zza(), entryZzd.getValue());
        }
        for (Map.Entry entry : zzodVar.zze()) {
            zzlvVar.zzd((zzlu) entry.getKey(), entry.getValue());
        }
        zzlvVar.zzc = this.zzc;
        return zzlvVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof zzlv) {
            return this.zza.equals(((zzlv) obj).zza);
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return this.zza.hashCode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzb() {
        if (this.zzb) {
            return;
        }
        zzod zzodVar = this.zza;
        int iZzc = zzodVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            Object value = zzodVar.zzd(i).getValue();
            if (value instanceof zzme) {
                ((zzme) value).zzcj();
            }
        }
        Iterator it = zzodVar.zze().iterator();
        while (it.hasNext()) {
            Object value2 = ((Map.Entry) it.next()).getValue();
            if (value2 instanceof zzme) {
                ((zzme) value2).zzcj();
            }
        }
        zzodVar.zza();
        this.zzb = true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Iterator zzc() {
        zzod zzodVar = this.zza;
        return zzodVar.isEmpty() ? Collections.emptyIterator() : this.zzc ? new zzmt(zzodVar.entrySet().iterator()) : zzodVar.entrySet().iterator();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzd(zzlu zzluVar, Object obj) {
        if (!zzluVar.zzd()) {
            zzn(zzluVar, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            List list = (List) obj;
            int size = list.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i = 0; i < size; i++) {
                Object obj2 = list.get(i);
                zzn(zzluVar, obj2);
                arrayList.add(obj2);
            }
            obj = arrayList;
        }
        if (obj instanceof zzmu) {
            this.zzc = true;
        }
        this.zza.put(zzluVar, obj);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zze() {
        zzod zzodVar = this.zza;
        int iZzc = zzodVar.zzc();
        for (int i = 0; i < iZzc; i++) {
            if (!zzk(zzodVar.zzd(i))) {
                return false;
            }
        }
        Iterator it = zzodVar.zze().iterator();
        while (it.hasNext()) {
            if (!zzk((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzg() {
        zzod zzodVar = this.zza;
        int iZzc = zzodVar.zzc();
        int iZzm = 0;
        for (int i = 0; i < iZzc; i++) {
            iZzm += zzm(zzodVar.zzd(i));
        }
        Iterator it = zzodVar.zze().iterator();
        while (it.hasNext()) {
            iZzm += zzm((Map.Entry) it.next());
        }
        return iZzm;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private zzlv(boolean z) {
        zzb();
        zzb();
    }
}
