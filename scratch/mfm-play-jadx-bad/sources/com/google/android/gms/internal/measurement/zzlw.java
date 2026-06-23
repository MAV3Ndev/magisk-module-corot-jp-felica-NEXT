package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public enum zzlw {
    DOUBLE(0, 1, zzmr.DOUBLE),
    FLOAT(1, 1, zzmr.FLOAT),
    INT64(2, 1, zzmr.LONG),
    UINT64(3, 1, zzmr.LONG),
    INT32(4, 1, zzmr.INT),
    FIXED64(5, 1, zzmr.LONG),
    FIXED32(6, 1, zzmr.INT),
    BOOL(7, 1, zzmr.BOOLEAN),
    STRING(8, 1, zzmr.STRING),
    MESSAGE(9, 1, zzmr.MESSAGE),
    BYTES(10, 1, zzmr.BYTE_STRING),
    UINT32(11, 1, zzmr.INT),
    ENUM(12, 1, zzmr.ENUM),
    SFIXED32(13, 1, zzmr.INT),
    SFIXED64(14, 1, zzmr.LONG),
    SINT32(15, 1, zzmr.INT),
    SINT64(16, 1, zzmr.LONG),
    GROUP(17, 1, zzmr.MESSAGE),
    DOUBLE_LIST(18, 2, zzmr.DOUBLE),
    FLOAT_LIST(19, 2, zzmr.FLOAT),
    INT64_LIST(20, 2, zzmr.LONG),
    UINT64_LIST(21, 2, zzmr.LONG),
    INT32_LIST(22, 2, zzmr.INT),
    FIXED64_LIST(23, 2, zzmr.LONG),
    FIXED32_LIST(24, 2, zzmr.INT),
    BOOL_LIST(25, 2, zzmr.BOOLEAN),
    STRING_LIST(26, 2, zzmr.STRING),
    MESSAGE_LIST(27, 2, zzmr.MESSAGE),
    BYTES_LIST(28, 2, zzmr.BYTE_STRING),
    UINT32_LIST(29, 2, zzmr.INT),
    ENUM_LIST(30, 2, zzmr.ENUM),
    SFIXED32_LIST(31, 2, zzmr.INT),
    SFIXED64_LIST(32, 2, zzmr.LONG),
    SINT32_LIST(33, 2, zzmr.INT),
    SINT64_LIST(34, 2, zzmr.LONG),
    DOUBLE_LIST_PACKED(35, 3, zzmr.DOUBLE),
    FLOAT_LIST_PACKED(36, 3, zzmr.FLOAT),
    INT64_LIST_PACKED(37, 3, zzmr.LONG),
    UINT64_LIST_PACKED(38, 3, zzmr.LONG),
    INT32_LIST_PACKED(39, 3, zzmr.INT),
    FIXED64_LIST_PACKED(40, 3, zzmr.LONG),
    FIXED32_LIST_PACKED(41, 3, zzmr.INT),
    BOOL_LIST_PACKED(42, 3, zzmr.BOOLEAN),
    UINT32_LIST_PACKED(43, 3, zzmr.INT),
    ENUM_LIST_PACKED(44, 3, zzmr.ENUM),
    SFIXED32_LIST_PACKED(45, 3, zzmr.INT),
    SFIXED64_LIST_PACKED(46, 3, zzmr.LONG),
    SINT32_LIST_PACKED(47, 3, zzmr.INT),
    SINT64_LIST_PACKED(48, 3, zzmr.LONG),
    GROUP_LIST(49, 2, zzmr.MESSAGE),
    MAP(50, 4, zzmr.VOID);

    private static final zzlw[] zzaa;
    private final int zzZ;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        zzlw[] zzlwVarArrValues = values();
        zzaa = new zzlw[zzlwVarArrValues.length];
        for (zzlw zzlwVar : zzlwVarArrValues) {
            zzaa[zzlwVar.zzZ] = zzlwVar;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzlw(int i, int i2, zzmr zzmrVar) {
        this.zzZ = i;
        int i3 = i2 - 1;
        if (i3 == 1 || i3 == 3) {
            zzmrVar.zza();
        }
        if (i2 == 1) {
            zzmr zzmrVar2 = zzmr.VOID;
            zzmrVar.ordinal();
        }
    }

    public final int zza() {
        return this.zzZ;
    }
}
