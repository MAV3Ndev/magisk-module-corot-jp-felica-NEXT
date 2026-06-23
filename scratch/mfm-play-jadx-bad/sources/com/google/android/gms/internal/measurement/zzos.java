package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public enum zzos {
    DOUBLE(zzot.DOUBLE, 1),
    FLOAT(zzot.FLOAT, 5),
    INT64(zzot.LONG, 0),
    UINT64(zzot.LONG, 0),
    INT32(zzot.INT, 0),
    FIXED64(zzot.LONG, 1),
    FIXED32(zzot.INT, 5),
    BOOL(zzot.BOOLEAN, 0),
    STRING(zzot.STRING, 2),
    GROUP(zzot.MESSAGE, 3),
    MESSAGE(zzot.MESSAGE, 2),
    BYTES(zzot.BYTE_STRING, 2),
    UINT32(zzot.INT, 0),
    ENUM(zzot.ENUM, 0),
    SFIXED32(zzot.INT, 5),
    SFIXED64(zzot.LONG, 1),
    SINT32(zzot.INT, 0),
    SINT64(zzot.LONG, 0);

    private final zzot zzs;
    private final int zzt;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzos(zzot zzotVar, int i) {
        this.zzs = zzotVar;
        this.zzt = i;
    }

    public final zzot zza() {
        return this.zzs;
    }

    public final int zzb() {
        return this.zzt;
    }
}
