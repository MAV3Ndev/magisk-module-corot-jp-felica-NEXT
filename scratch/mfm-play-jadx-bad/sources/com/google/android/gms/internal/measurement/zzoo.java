package com.google.android.gms.internal.measurement;

import com.felicanetworks.semc.fcm.CloudMessagingWorker;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import libcore.io.Memory;
import sun.misc.Unsafe;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzoo {
    static final long zza;
    static final boolean zzb;
    private static final Unsafe zzc;
    private static final Class zzd;
    private static final boolean zze;
    private static final zzon zzf;
    private static final boolean zzg;
    private static final boolean zzh;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0038  */
    static {
        boolean z;
        boolean z2;
        zzon zzonVar;
        Unsafe unsafeZzq = zzq();
        zzc = unsafeZzq;
        int i = zzku.zza;
        zzd = Memory.class;
        boolean zZzr = zzr(Long.TYPE);
        zze = zZzr;
        boolean zZzr2 = zzr(Integer.TYPE);
        zzon zzolVar = null;
        if (unsafeZzq != null) {
            if (zZzr) {
                zzolVar = new zzom(unsafeZzq);
            } else if (zZzr2) {
                zzolVar = new zzol(unsafeZzq);
            }
        }
        zzf = zzolVar;
        if (zzolVar == null) {
            z = false;
        } else {
            try {
                Class<?> cls = zzolVar.zza.getClass();
                cls.getMethod("objectFieldOffset", Field.class);
                cls.getMethod("getLong", Object.class, Long.TYPE);
                if (zzB() != null) {
                    z = true;
                }
            } catch (Throwable th) {
                zzy(th);
            }
        }
        zzg = z;
        zzon zzonVar2 = zzf;
        if (zzonVar2 == null) {
            z2 = false;
        } else {
            try {
                Class<?> cls2 = zzonVar2.zza.getClass();
                cls2.getMethod("objectFieldOffset", Field.class);
                cls2.getMethod("arrayBaseOffset", Class.class);
                cls2.getMethod("arrayIndexScale", Class.class);
                cls2.getMethod("getInt", Object.class, Long.TYPE);
                cls2.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
                cls2.getMethod("getLong", Object.class, Long.TYPE);
                Class<?> cls3 = Long.TYPE;
                cls2.getMethod("putLong", Object.class, cls3, cls3);
                cls2.getMethod("getObject", Object.class, Long.TYPE);
                cls2.getMethod("putObject", Object.class, Long.TYPE, Object.class);
                z2 = true;
            } catch (Throwable th2) {
                zzy(th2);
                z2 = false;
            }
        }
        zzh = z2;
        zza = zzz(byte[].class);
        zzz(boolean[].class);
        zzA(boolean[].class);
        zzz(int[].class);
        zzA(int[].class);
        zzz(long[].class);
        zzA(long[].class);
        zzz(float[].class);
        zzA(float[].class);
        zzz(double[].class);
        zzA(double[].class);
        zzz(Object[].class);
        zzA(Object[].class);
        Field fieldZzB = zzB();
        if (fieldZzB != null && (zzonVar = zzf) != null) {
            zzonVar.zza.objectFieldOffset(fieldZzB);
        }
        zzb = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private zzoo() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static int zzA(Class cls) {
        if (zzh) {
            return zzf.zza.arrayIndexScale(cls);
        }
        return -1;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static Field zzB() {
        int i = zzku.zza;
        Field fieldZzC = zzC(Buffer.class, "effectiveDirectAddress");
        if (fieldZzC != null) {
            return fieldZzC;
        }
        Field fieldZzC2 = zzC(Buffer.class, CloudMessagingWorker.EXT_KEY_ADDRESS);
        if (fieldZzC2 == null || fieldZzC2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZzC2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static Field zzC(Class cls, String str) {
        try {
            return cls.getDeclaredField(str);
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX INFO: Access modifiers changed from: private */
    public static void zzD(Object obj, long j, byte b) {
        Unsafe unsafe = zzf.zza;
        long j2 = (-4) & j;
        int i = unsafe.getInt(obj, j2);
        int i2 = ((~((int) j)) & 3) << 3;
        unsafe.putInt(obj, j2, ((255 & b) << i2) | (i & (~(255 << i2))));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX INFO: Access modifiers changed from: private */
    public static void zzE(Object obj, long j, byte b) {
        Unsafe unsafe = zzf.zza;
        long j2 = (-4) & j;
        int i = (((int) j) & 3) << 3;
        unsafe.putInt(obj, j2, ((255 & b) << i) | (unsafe.getInt(obj, j2) & (~(255 << i))));
    }

    static boolean zza() {
        return zzh;
    }

    static boolean zzb() {
        return zzg;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static Object zzc(Class cls) {
        try {
            return zzc.allocateInstance(cls);
        } catch (InstantiationException e) {
            throw new IllegalStateException(e);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzd(Object obj, long j) {
        return zzf.zza.getInt(obj, j);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zze(Object obj, long j, int i) {
        zzf.zza.putInt(obj, j, i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static long zzf(Object obj, long j) {
        return zzf.zza.getLong(obj, j);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzg(Object obj, long j, long j2) {
        zzf.zza.putLong(obj, j, j2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static boolean zzh(Object obj, long j) {
        return zzf.zzb(obj, j);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzi(Object obj, long j, boolean z) {
        zzf.zzc(obj, j, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static float zzj(Object obj, long j) {
        return zzf.zzd(obj, j);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzk(Object obj, long j, float f) {
        zzf.zze(obj, j, f);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static double zzl(Object obj, long j) {
        return zzf.zzf(obj, j);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzm(Object obj, long j, double d) {
        zzf.zzg(obj, j, d);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static Object zzn(Object obj, long j) {
        return zzf.zza.getObject(obj, j);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzo(Object obj, long j, Object obj2) {
        zzf.zza.putObject(obj, j, obj2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzp(byte[] bArr, long j, byte b) {
        zzf.zza(bArr, zza + j, b);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static Unsafe zzq() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzok());
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: java.lang.Class */
    /* JADX WARN: Multi-variable type inference failed */
    static boolean zzr(Class cls) {
        int i = zzku.zza;
        try {
            Class cls2 = zzd;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            Class cls3 = Integer.TYPE;
            cls2.getMethod("pokeByteArray", cls, byte[].class, cls3, cls3);
            Class cls4 = Integer.TYPE;
            cls2.getMethod("peekByteArray", cls, byte[].class, cls4, cls4);
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: ?: TERNARY null = ((wrap:byte:0x0015: CAST (byte) (wrap:int:0x0013: ARITH (wrap:int:0x0012: ARITH (wrap:int:0x0007: INVOKE 
  (wrap:sun.misc.Unsafe:0x0002: IGET 
  (wrap:com.google.android.gms.internal.measurement.zzon:0x0000: SGET  A[WRAPPED] (LINE:1) com.google.android.gms.internal.measurement.zzoo.zzf com.google.android.gms.internal.measurement.zzon)
 A[WRAPPED] (LINE:1) com.google.android.gms.internal.measurement.zzon.zza sun.misc.Unsafe)
  (r3v0 java.lang.Object)
  (wrap:long:0x0006: ARITH (-4 long) & (r4v0 long) A[WRAPPED])
 VIRTUAL call: sun.misc.Unsafe.getInt(java.lang.Object, long):int A[WRAPPED] (LINE:1)) >>> (wrap:int:0x0011: CAST (int) (wrap:long:0x0010: ARITH (wrap:long:0x000e: ARITH (wrap:long:0x000b: NOT (r4v0 long) A[WRAPPED]) & (3 long) A[WRAPPED]) << (3 long) A[WRAPPED])) A[WRAPPED] (LINE:1)) & (255 int) A[WRAPPED] (LINE:1))) != (0 byte)) ? true : false */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static /* synthetic */ boolean zzu(Object obj, long j) {
        return ((byte) ((zzf.zza.getInt(obj, (-4) & j) >>> ((int) (((~j) & 3) << 3))) & 255)) != 0;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: ?: TERNARY null = ((wrap:byte:0x0014: CAST (byte) (wrap:int:0x0012: ARITH (wrap:int:0x0011: ARITH (wrap:int:0x0007: INVOKE 
  (wrap:sun.misc.Unsafe:0x0002: IGET 
  (wrap:com.google.android.gms.internal.measurement.zzon:0x0000: SGET  A[WRAPPED] (LINE:1) com.google.android.gms.internal.measurement.zzoo.zzf com.google.android.gms.internal.measurement.zzon)
 A[WRAPPED] (LINE:1) com.google.android.gms.internal.measurement.zzon.zza sun.misc.Unsafe)
  (r3v0 java.lang.Object)
  (wrap:long:0x0006: ARITH (-4 long) & (r4v0 long) A[WRAPPED])
 VIRTUAL call: sun.misc.Unsafe.getInt(java.lang.Object, long):int A[WRAPPED] (LINE:1)) >>> (wrap:int:0x0010: CAST (int) (wrap:long:0x000f: ARITH (wrap:long:0x000d: ARITH (r4v0 long) & (3 long) A[WRAPPED]) << (3 long) A[WRAPPED])) A[WRAPPED] (LINE:1)) & (255 int) A[WRAPPED] (LINE:1))) != (0 byte)) ? true : false */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static /* synthetic */ boolean zzv(Object obj, long j) {
        return ((byte) ((zzf.zza.getInt(obj, (-4) & j) >>> ((int) ((j & 3) << 3))) & 255)) != 0;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static /* synthetic */ void zzy(Throwable th) {
        Logger.getLogger(zzoo.class.getName()).logp(Level.WARNING, "com.google.protobuf.UnsafeUtil", "logMissingMethod", "platform method missing - proto runtime falling back to safer methods: ".concat(th.toString()));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private static int zzz(Class cls) {
        if (zzh) {
            return zzf.zza.arrayBaseOffset(cls);
        }
        return -1;
    }
}
