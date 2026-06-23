package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import androidx.collection.ArrayMap;
import com.amazonaws.mobileconnectors.pinpoint.internal.event.ClientContext;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzpq;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzad extends zzor {
    private String zza;
    private Set zzb;
    private Map zzc;
    private Long zzd;
    private Long zze;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzad(zzpf zzpfVar) {
        super(zzpfVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final zzy zzc(Integer num) {
        if (this.zzc.containsKey(num)) {
            return (zzy) this.zzc.get(num);
        }
        zzy zzyVar = new zzy(this, this.zza, null);
        this.zzc.put(num, zzyVar);
        return zzyVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final boolean zzd(int i, int i2) {
        zzy zzyVar = (zzy) this.zzc.get(Integer.valueOf(i));
        if (zzyVar == null) {
            return false;
        }
        return zzyVar.zzc().get(i2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:44:0x014f */
    /* JADX DEBUG: Type inference failed for r0v143. Raw type applied. Possible types: V */
    /* JADX DEBUG: Type inference failed for r16v22. Raw type applied. Possible types: V */
    /* JADX DEBUG: Type inference failed for r16v33. Raw type applied. Possible types: V */
    /* JADX DEBUG: Type inference failed for r17v24. Raw type applied. Possible types: V */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x0964, code lost:
    
        r0 = r13.zzaV().zze();
        r2 = com.google.android.gms.measurement.internal.zzgt.zzl(r34.zza);
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x0976, code lost:
    
        if (r12.zza() == false) goto L409;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x0978, code lost:
    
        r3 = java.lang.Integer.valueOf(r12.zzb());
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x0981, code lost:
    
        r3 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x0982, code lost:
    
        r0.zzc("Invalid property filter ID. appId, id", r2, java.lang.String.valueOf(r3));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0256  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02ba A[PHI: r0 r6
  0x02ba: PHI (r0v72 java.util.Map) = (r0v57 java.util.Map), (r0v74 java.util.Map), (r0v51 java.util.Map) binds: [B:129:0x02e0, B:120:0x02c0, B:117:0x02b8] A[DONT_GENERATE, DONT_INLINE]
  0x02ba: PHI (r6v10 android.database.Cursor) = (r6v4 android.database.Cursor), (r6v11 android.database.Cursor), (r6v11 android.database.Cursor) binds: [B:129:0x02e0, B:120:0x02c0, B:117:0x02b8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02f8  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0401  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x06c9  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x06d3  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x06eb  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x077a  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x084c A[PHI: r0 r13 r36
  0x084c: PHI (r0v120 java.util.Map) = (r0v122 java.util.Map), (r0v128 java.util.Map) binds: [B:371:0x0874, B:359:0x084a] A[DONT_GENERATE, DONT_INLINE]
  0x084c: PHI (r13v4 android.database.Cursor) = (r13v5 android.database.Cursor), (r13v6 android.database.Cursor) binds: [B:371:0x0874, B:359:0x084a] A[DONT_GENERATE, DONT_INLINE]
  0x084c: PHI (r36v4 java.util.Iterator) = (r36v5 java.util.Iterator), (r36v9 java.util.Iterator) binds: [B:371:0x0874, B:359:0x084a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0a47  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01a6 A[Catch: SQLiteException -> 0x021b, all -> 0x0a4d, TRY_LEAVE, TryCatch #10 {SQLiteException -> 0x021b, blocks: (B:61:0x01a0, B:63:0x01a6, B:67:0x01b6, B:68:0x01bb, B:69:0x01c5, B:70:0x01d5, B:72:0x01e4), top: B:450:0x01a0 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b6 A[Catch: SQLiteException -> 0x021b, all -> 0x0a4d, TRY_ENTER, TryCatch #10 {SQLiteException -> 0x021b, blocks: (B:61:0x01a0, B:63:0x01a6, B:67:0x01b6, B:68:0x01bb, B:69:0x01c5, B:70:0x01d5, B:72:0x01e4), top: B:450:0x01a0 }] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v40 */
    /* JADX WARN: Type inference failed for: r5v42, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v49 */
    /* JADX WARN: Type inference failed for: r5v50 */
    /* JADX WARN: Type inference failed for: r5v51 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v9 */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final List zzb(String str, List list, List list2, Long l, Long l2, boolean z) throws Throwable {
        int i;
        int i2;
        boolean z2;
        Map map;
        zzav zzavVarZzj;
        String str2;
        Cursor cursor;
        boolean z3;
        String str3;
        String str4;
        Cursor cursorQuery;
        Map map2;
        HashSet<Integer> hashSet;
        Map map3;
        List list3;
        boolean z4;
        ?? r5;
        Cursor cursorRawQuery;
        Map map4;
        HashSet hashSet2;
        Iterator it;
        Iterator it2;
        Map map5;
        String str5;
        ArrayMap arrayMap;
        Cursor cursor2;
        String str6;
        Cursor cursorQuery2;
        List arrayList;
        zzz zzzVar;
        Iterator it3;
        zzbc zzbcVar;
        Iterator it4;
        Map map6;
        Iterator it5;
        long j;
        String str7;
        Cursor cursor3;
        Cursor cursorQuery3;
        Cursor cursor4;
        List arrayList2;
        Cursor cursorQuery4;
        List arrayList3;
        Preconditions.checkNotEmpty(str);
        Preconditions.checkNotNull(list);
        Preconditions.checkNotNull(list2);
        this.zza = str;
        this.zzb = new HashSet();
        this.zzc = new ArrayMap();
        this.zzd = l;
        this.zze = l2;
        Iterator it6 = list.iterator();
        while (true) {
            i = 0;
            i2 = 1;
            if (!it6.hasNext()) {
                z2 = false;
                break;
            }
            if ("_s".equals(((com.google.android.gms.internal.measurement.zzhs) it6.next()).zzd())) {
                z2 = true;
                break;
            }
        }
        zzpq.zza();
        zzib zzibVar = this.zzu;
        zzal zzalVarZzc = zzibVar.zzc();
        String str8 = this.zza;
        zzfw zzfwVar = zzfx.zzaF;
        boolean zZzp = zzalVarZzc.zzp(str8, zzfwVar);
        zzpq.zza();
        boolean zZzp2 = zzibVar.zzc().zzp(this.zza, zzfx.zzaE);
        ?? r52 = zzfwVar;
        if (z2) {
            zzav zzavVarZzj2 = this.zzg.zzj();
            String str9 = this.zza;
            zzavVarZzj2.zzay();
            zzavVarZzj2.zzg();
            Preconditions.checkNotEmpty(str9);
            ContentValues contentValues = new ContentValues();
            contentValues.put("current_session_count", (Integer) 0);
            try {
                SQLiteDatabase sQLiteDatabaseZze = zzavVarZzj2.zze();
                sQLiteDatabaseZze.update("events", contentValues, "app_id = ?", new String[]{str9});
                r52 = sQLiteDatabaseZze;
            } catch (SQLiteException e) {
                zzavVarZzj2.zzu.zzaV().zzb().zzc("Error resetting session-scoped event counts. appId", zzgt.zzl(str9), e);
                r52 = "Error resetting session-scoped event counts. appId";
            }
        }
        Map map7 = Collections.EMPTY_MAP;
        String str10 = "data";
        String str11 = "audience_id";
        if (zZzp2 && zZzp) {
            zzav zzavVarZzj3 = this.zzg.zzj();
            String str12 = this.zza;
            Preconditions.checkNotEmpty(str12);
            ArrayMap arrayMap2 = new ArrayMap();
            try {
                try {
                    cursorQuery4 = zzavVarZzj3.zze().query("event_filters", new String[]{"audience_id", "data"}, "app_id=?", new String[]{str12}, null, null, null);
                    try {
                    } catch (SQLiteException e2) {
                        e = e2;
                        zzavVarZzj3.zzu.zzaV().zzb().zzc("Database error querying filters. appId", zzgt.zzl(str12), e);
                        map7 = Collections.EMPTY_MAP;
                        if (cursorQuery4 != null) {
                        }
                        map = map7;
                        zzavVarZzj = this.zzg.zzj();
                        str2 = this.zza;
                        zzavVarZzj.zzay();
                        zzavVarZzj.zzg();
                        Preconditions.checkNotEmpty(str2);
                        cursorQuery = zzavVarZzj.zze().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str2}, null, null, null);
                        try {
                            try {
                                if (cursorQuery.moveToFirst()) {
                                }
                            } catch (SQLiteException e3) {
                                e = e3;
                                z3 = z2;
                                str3 = "data";
                            }
                            if (!map2.isEmpty()) {
                            }
                            String str13 = str3;
                            String str14 = str4;
                            String str15 = "Skipping failed audience ID";
                            if (!list.isEmpty()) {
                            }
                            if (z) {
                            }
                        } catch (Throwable th) {
                            th = th;
                            cursor = cursorQuery;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (r52 != 0) {
                        r52.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e4) {
                e = e4;
                cursorQuery4 = null;
            } catch (Throwable th3) {
                th = th3;
                r52 = 0;
                if (r52 != 0) {
                }
                throw th;
            }
            if (cursorQuery4.moveToFirst()) {
                while (true) {
                    try {
                        com.google.android.gms.internal.measurement.zzff zzffVar = (com.google.android.gms.internal.measurement.zzff) ((com.google.android.gms.internal.measurement.zzfe) zzpj.zzw(com.google.android.gms.internal.measurement.zzff.zzn(), cursorQuery4.getBlob(i2))).zzbc();
                        if (zzffVar.zzg()) {
                            Integer numValueOf = Integer.valueOf(cursorQuery4.getInt(i));
                            List list4 = (List) arrayMap2.get(numValueOf);
                            if (list4 == null) {
                                arrayList3 = new ArrayList();
                                arrayMap2.put(numValueOf, arrayList3);
                            } else {
                                arrayList3 = list4;
                            }
                            arrayList3.add(zzffVar);
                        }
                    } catch (IOException e5) {
                        zzavVarZzj3.zzu.zzaV().zzb().zzc("Failed to merge filter. appId", zzgt.zzl(str12), e5);
                    }
                    if (!cursorQuery4.moveToNext()) {
                        break;
                    }
                    i = 0;
                    i2 = 1;
                }
                if (cursorQuery4 != null) {
                    cursorQuery4.close();
                }
                map = arrayMap2;
            } else {
                map7 = Collections.EMPTY_MAP;
                if (cursorQuery4 != null) {
                    cursorQuery4.close();
                }
                map = map7;
            }
        } else {
            map = map7;
        }
        zzavVarZzj = this.zzg.zzj();
        str2 = this.zza;
        zzavVarZzj.zzay();
        zzavVarZzj.zzg();
        Preconditions.checkNotEmpty(str2);
        try {
            cursorQuery = zzavVarZzj.zze().query("audience_filter_values", new String[]{"audience_id", "current_results"}, "app_id=?", new String[]{str2}, null, null, null);
            if (cursorQuery.moveToFirst()) {
                Map map8 = Collections.EMPTY_MAP;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                map2 = map8;
                z3 = z2;
                str3 = "data";
                str4 = "audience_id";
            } else {
                ArrayMap arrayMap3 = new ArrayMap();
                while (true) {
                    int i3 = cursorQuery.getInt(0);
                    try {
                        arrayMap3.put(Integer.valueOf(i3), (com.google.android.gms.internal.measurement.zzii) ((com.google.android.gms.internal.measurement.zzih) zzpj.zzw(com.google.android.gms.internal.measurement.zzii.zzi(), cursorQuery.getBlob(1))).zzbc());
                        z3 = z2;
                        str3 = str10;
                        str4 = str11;
                    } catch (IOException e6) {
                        z3 = z2;
                        str3 = str10;
                        try {
                            str4 = str11;
                        } catch (SQLiteException e7) {
                            e = e7;
                            str4 = str11;
                            zzavVarZzj.zzu.zzaV().zzb().zzc("Database error querying filter results. appId", zzgt.zzl(str2), e);
                            Map map9 = Collections.EMPTY_MAP;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            map2 = map9;
                        }
                        try {
                            zzavVarZzj.zzu.zzaV().zzb().zzd("Failed to merge filter results. appId, audienceId, error", zzgt.zzl(str2), Integer.valueOf(i3), e6);
                        } catch (SQLiteException e8) {
                            e = e8;
                            zzavVarZzj.zzu.zzaV().zzb().zzc("Database error querying filter results. appId", zzgt.zzl(str2), e);
                            Map map92 = Collections.EMPTY_MAP;
                            if (cursorQuery != null) {
                            }
                            map2 = map92;
                        }
                    }
                    if (!cursorQuery.moveToNext()) {
                        break;
                    }
                    z2 = z3;
                    str10 = str3;
                    str11 = str4;
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                map2 = arrayMap3;
            }
        } catch (SQLiteException e9) {
            e = e9;
            z3 = z2;
            str3 = "data";
            str4 = "audience_id";
            cursorQuery = null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
        }
        if (!map2.isEmpty()) {
            HashSet hashSet3 = new HashSet(map2.keySet());
            if (z3) {
                String str16 = this.zza;
                zzav zzavVarZzj4 = this.zzg.zzj();
                String str17 = this.zza;
                zzavVarZzj4.zzay();
                zzavVarZzj4.zzg();
                Preconditions.checkNotEmpty(str17);
                Map arrayMap4 = new ArrayMap();
                SQLiteDatabase sQLiteDatabaseZze2 = zzavVarZzj4.zze();
                try {
                    try {
                        cursorRawQuery = sQLiteDatabaseZze2.rawQuery("select audience_id, filter_id from event_filters where app_id = ? and session_scoped = 1 UNION select audience_id, filter_id from property_filters where app_id = ? and session_scoped = 1;", new String[]{str17, str17});
                        try {
                        } catch (SQLiteException e10) {
                            e = e10;
                            zzavVarZzj4.zzu.zzaV().zzb().zzc("Database error querying scoped filters. appId", zzgt.zzl(str17), e);
                            arrayMap4 = Collections.EMPTY_MAP;
                            if (cursorRawQuery != null) {
                            }
                            Preconditions.checkNotEmpty(str16);
                            Preconditions.checkNotNull(map2);
                            ArrayMap arrayMap5 = new ArrayMap();
                            if (!map2.isEmpty()) {
                            }
                            hashSet = hashSet3;
                            map3 = arrayMap5;
                            while (r16.hasNext()) {
                            }
                            String str132 = str3;
                            String str142 = str4;
                            String str152 = "Skipping failed audience ID";
                            if (!list.isEmpty()) {
                            }
                            if (z) {
                            }
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        r5 = sQLiteDatabaseZze2;
                        if (r5 != 0) {
                            r5.close();
                        }
                        throw th;
                    }
                } catch (SQLiteException e11) {
                    e = e11;
                    cursorRawQuery = null;
                } catch (Throwable th6) {
                    th = th6;
                    r5 = 0;
                    if (r5 != 0) {
                    }
                    throw th;
                }
                if (cursorRawQuery.moveToFirst()) {
                    do {
                        Integer numValueOf2 = Integer.valueOf(cursorRawQuery.getInt(0));
                        List arrayList4 = (List) arrayMap4.get(numValueOf2);
                        if (arrayList4 == null) {
                            arrayList4 = new ArrayList();
                            arrayMap4.put(numValueOf2, arrayList4);
                        }
                        arrayList4.add(Integer.valueOf(cursorRawQuery.getInt(1)));
                    } while (cursorRawQuery.moveToNext());
                    if (cursorRawQuery != null) {
                        cursorRawQuery.close();
                    }
                    Preconditions.checkNotEmpty(str16);
                    Preconditions.checkNotNull(map2);
                    ArrayMap arrayMap52 = new ArrayMap();
                    if (!map2.isEmpty()) {
                        Iterator it7 = map2.keySet().iterator();
                        while (it7.hasNext()) {
                            Integer num = (Integer) it7.next();
                            num.intValue();
                            com.google.android.gms.internal.measurement.zzii zziiVar = (com.google.android.gms.internal.measurement.zzii) map2.get(num);
                            List list5 = (List) arrayMap4.get(num);
                            if (list5 == null || list5.isEmpty()) {
                                map4 = arrayMap4;
                                hashSet2 = hashSet3;
                                it = it7;
                                arrayMap52.put(num, zziiVar);
                                arrayMap4 = map4;
                                hashSet3 = hashSet2;
                                it7 = it;
                            } else {
                                zzpf zzpfVar = this.zzg;
                                map4 = arrayMap4;
                                hashSet2 = hashSet3;
                                List listZzq = zzpfVar.zzp().zzq(zziiVar.zzc(), list5);
                                if (listZzq.isEmpty()) {
                                    arrayMap4 = map4;
                                    hashSet3 = hashSet2;
                                } else {
                                    com.google.android.gms.internal.measurement.zzih zzihVar = (com.google.android.gms.internal.measurement.zzih) zziiVar.zzcl();
                                    zzihVar.zzd();
                                    zzihVar.zzc(listZzq);
                                    List listZzq2 = zzpfVar.zzp().zzq(zziiVar.zza(), list5);
                                    zzihVar.zzb();
                                    zzihVar.zza(listZzq2);
                                    ArrayList arrayList5 = new ArrayList();
                                    Iterator it8 = zziiVar.zze().iterator();
                                    while (it8.hasNext()) {
                                        Iterator it9 = it7;
                                        com.google.android.gms.internal.measurement.zzhq zzhqVar = (com.google.android.gms.internal.measurement.zzhq) it8.next();
                                        Iterator it10 = it8;
                                        if (!list5.contains(Integer.valueOf(zzhqVar.zzb()))) {
                                            arrayList5.add(zzhqVar);
                                        }
                                        it7 = it9;
                                        it8 = it10;
                                    }
                                    it = it7;
                                    zzihVar.zzf();
                                    zzihVar.zze(arrayList5);
                                    ArrayList arrayList6 = new ArrayList();
                                    for (com.google.android.gms.internal.measurement.zzik zzikVar : zziiVar.zzg()) {
                                        if (!list5.contains(Integer.valueOf(zzikVar.zzb()))) {
                                            arrayList6.add(zzikVar);
                                        }
                                    }
                                    zzihVar.zzh();
                                    zzihVar.zzg(arrayList6);
                                    arrayMap52.put(num, (com.google.android.gms.internal.measurement.zzii) zzihVar.zzbc());
                                    arrayMap4 = map4;
                                    hashSet3 = hashSet2;
                                    it7 = it;
                                }
                            }
                        }
                    }
                    hashSet = hashSet3;
                    map3 = arrayMap52;
                } else {
                    arrayMap4 = Collections.EMPTY_MAP;
                    if (cursorRawQuery != null) {
                    }
                    Preconditions.checkNotEmpty(str16);
                    Preconditions.checkNotNull(map2);
                    ArrayMap arrayMap522 = new ArrayMap();
                    if (!map2.isEmpty()) {
                    }
                    hashSet = hashSet3;
                    map3 = arrayMap522;
                }
            } else {
                hashSet = hashSet3;
                map3 = map2;
            }
            for (Integer num2 : hashSet) {
                num2.intValue();
                com.google.android.gms.internal.measurement.zzii zziiVar2 = (com.google.android.gms.internal.measurement.zzii) map3.get(num2);
                BitSet bitSet = new BitSet();
                BitSet bitSet2 = new BitSet();
                ArrayMap arrayMap6 = new ArrayMap();
                if (zziiVar2 != null && zziiVar2.zzf() != 0) {
                    for (com.google.android.gms.internal.measurement.zzhq zzhqVar2 : zziiVar2.zze()) {
                        if (zzhqVar2.zza()) {
                            Map map10 = map3;
                            arrayMap6.put(Integer.valueOf(zzhqVar2.zzb()), zzhqVar2.zzc() ? Long.valueOf(zzhqVar2.zzd()) : null);
                            map3 = map10;
                        }
                    }
                }
                Map map11 = map3;
                ArrayMap arrayMap7 = new ArrayMap();
                if (zziiVar2 != null && zziiVar2.zzh() != 0) {
                    Iterator it11 = zziiVar2.zzg().iterator();
                    while (it11.hasNext()) {
                        com.google.android.gms.internal.measurement.zzik zzikVar2 = (com.google.android.gms.internal.measurement.zzik) it11.next();
                        if (zzikVar2.zza() && zzikVar2.zzd() > 0) {
                            arrayMap7.put(Integer.valueOf(zzikVar2.zzb()), Long.valueOf(zzikVar2.zze(zzikVar2.zzd() - 1)));
                            it11 = it11;
                            zziiVar2 = zziiVar2;
                        }
                    }
                }
                com.google.android.gms.internal.measurement.zzii zziiVar3 = zziiVar2;
                if (zziiVar3 != null) {
                    int i4 = 0;
                    while (i4 < zziiVar3.zzb() * 64) {
                        if (zzpj.zzn(zziiVar3.zza(), i4)) {
                            z4 = zZzp;
                            this.zzu.zzaV().zzk().zzc("Filter already evaluated. audience ID, filter ID", num2, Integer.valueOf(i4));
                            bitSet2.set(i4);
                            if (zzpj.zzn(zziiVar3.zzc(), i4)) {
                                bitSet.set(i4);
                            }
                            i4++;
                            zZzp = z4;
                        } else {
                            z4 = zZzp;
                        }
                        arrayMap6.remove(Integer.valueOf(i4));
                        i4++;
                        zZzp = z4;
                    }
                }
                boolean z5 = zZzp;
                com.google.android.gms.internal.measurement.zzii zziiVar4 = (com.google.android.gms.internal.measurement.zzii) map2.get(num2);
                if (zZzp2 && z5 && (list3 = (List) map.get(num2)) != null && this.zze != null && this.zzd != null) {
                    Iterator it12 = list3.iterator();
                    while (it12.hasNext()) {
                        com.google.android.gms.internal.measurement.zzff zzffVar2 = (com.google.android.gms.internal.measurement.zzff) it12.next();
                        int iZzb = zzffVar2.zzb();
                        Iterator it13 = it12;
                        long jLongValue = this.zze.longValue() / 1000;
                        if (zzffVar2.zzj()) {
                            jLongValue = this.zzd.longValue() / 1000;
                        }
                        Integer numValueOf3 = Integer.valueOf(iZzb);
                        if (arrayMap6.containsKey(numValueOf3)) {
                            arrayMap6.put(numValueOf3, Long.valueOf(jLongValue));
                        }
                        if (arrayMap7.containsKey(numValueOf3)) {
                            arrayMap7.put(numValueOf3, Long.valueOf(jLongValue));
                        }
                        it12 = it13;
                    }
                }
                this.zzc.put(num2, new zzy(this, this.zza, zziiVar4, bitSet, bitSet2, arrayMap6, arrayMap7, null));
                str3 = str3;
                map = map;
                str4 = str4;
                map2 = map2;
                zZzp = z5;
                map3 = map11;
            }
        }
        String str1322 = str3;
        String str1422 = str4;
        String str1522 = "Skipping failed audience ID";
        if (!list.isEmpty()) {
            zzz zzzVar2 = new zzz(this, null);
            ArrayMap arrayMap8 = new ArrayMap();
            Iterator it14 = list.iterator();
            while (it14.hasNext()) {
                com.google.android.gms.internal.measurement.zzhs zzhsVar = (com.google.android.gms.internal.measurement.zzhs) it14.next();
                com.google.android.gms.internal.measurement.zzhs zzhsVarZza = zzzVar2.zza(this.zza, zzhsVar);
                if (zzhsVarZza != null) {
                    zzpf zzpfVar2 = this.zzg;
                    zzbc zzbcVarZzah = zzpfVar2.zzj().zzah(this.zza, zzhsVar, zzhsVarZza.zzd());
                    zzpfVar2.zzj().zzh(zzbcVarZzah);
                    if (z) {
                        continue;
                    } else {
                        long j2 = zzbcVarZzah.zzc;
                        String strZzd = zzhsVarZza.zzd();
                        Map map12 = (Map) arrayMap8.get(strZzd);
                        if (map12 == null) {
                            zzav zzavVarZzj5 = zzpfVar2.zzj();
                            zzzVar = zzzVar2;
                            String str18 = this.zza;
                            zzavVarZzj5.zzay();
                            zzavVarZzj5.zzg();
                            Preconditions.checkNotEmpty(str18);
                            Preconditions.checkNotEmpty(strZzd);
                            it3 = it14;
                            ArrayMap arrayMap9 = new ArrayMap();
                            try {
                                try {
                                    str7 = str18;
                                    try {
                                        cursorQuery3 = zzavVarZzj5.zze().query("event_filters", new String[]{str1422, str1322}, "app_id=? AND event_name=?", new String[]{str18, strZzd}, null, null, null);
                                    } catch (SQLiteException e12) {
                                        e = e12;
                                        zzbcVar = zzbcVarZzah;
                                        cursor3 = null;
                                        try {
                                            zzavVarZzj5.zzu.zzaV().zzb().zzc("Database error querying filters. appId", zzgt.zzl(str7), e);
                                            map12 = Collections.EMPTY_MAP;
                                            if (cursor3 != null) {
                                                cursor3.close();
                                            }
                                            arrayMap8.put(strZzd, map12);
                                            it4 = map12.keySet().iterator();
                                            while (it4.hasNext()) {
                                            }
                                            it14 = it3;
                                            zzzVar2 = zzzVar;
                                        } catch (Throwable th7) {
                                            th = th7;
                                            if (cursor3 != null) {
                                                cursor3.close();
                                            }
                                            throw th;
                                        }
                                    }
                                } catch (Throwable th8) {
                                    th = th8;
                                    cursor3 = null;
                                }
                            } catch (SQLiteException e13) {
                                e = e13;
                                str7 = str18;
                            }
                            try {
                                try {
                                    if (cursorQuery3.moveToFirst()) {
                                        zzbcVar = zzbcVarZzah;
                                        while (true) {
                                            try {
                                                try {
                                                    com.google.android.gms.internal.measurement.zzff zzffVar3 = (com.google.android.gms.internal.measurement.zzff) ((com.google.android.gms.internal.measurement.zzfe) zzpj.zzw(com.google.android.gms.internal.measurement.zzff.zzn(), cursorQuery3.getBlob(1))).zzbc();
                                                    Integer numValueOf4 = Integer.valueOf(cursorQuery3.getInt(0));
                                                    List list6 = (List) arrayMap9.get(numValueOf4);
                                                    if (list6 == null) {
                                                        cursor4 = cursorQuery3;
                                                        try {
                                                            arrayList2 = new ArrayList();
                                                            arrayMap9.put(numValueOf4, arrayList2);
                                                        } catch (SQLiteException e14) {
                                                            e = e14;
                                                            cursor3 = cursor4;
                                                            zzavVarZzj5.zzu.zzaV().zzb().zzc("Database error querying filters. appId", zzgt.zzl(str7), e);
                                                            map12 = Collections.EMPTY_MAP;
                                                            if (cursor3 != null) {
                                                            }
                                                        } catch (Throwable th9) {
                                                            th = th9;
                                                            cursor3 = cursor4;
                                                            if (cursor3 != null) {
                                                            }
                                                            throw th;
                                                        }
                                                    } else {
                                                        cursor4 = cursorQuery3;
                                                        arrayList2 = list6;
                                                    }
                                                    arrayList2.add(zzffVar3);
                                                } catch (IOException e15) {
                                                    cursor4 = cursorQuery3;
                                                    zzavVarZzj5.zzu.zzaV().zzb().zzc("Failed to merge filter. appId", zzgt.zzl(str7), e15);
                                                }
                                                if (!cursor4.moveToNext()) {
                                                    break;
                                                }
                                                cursorQuery3 = cursor4;
                                            } catch (SQLiteException e16) {
                                                e = e16;
                                                cursor4 = cursorQuery3;
                                            }
                                        }
                                        if (cursor4 != null) {
                                            cursor4.close();
                                        }
                                        map12 = arrayMap9;
                                    } else {
                                        zzbcVar = zzbcVarZzah;
                                        map12 = Collections.EMPTY_MAP;
                                        if (cursorQuery3 != null) {
                                            cursorQuery3.close();
                                        }
                                    }
                                } catch (SQLiteException e17) {
                                    e = e17;
                                    cursor4 = cursorQuery3;
                                    zzbcVar = zzbcVarZzah;
                                }
                                arrayMap8.put(strZzd, map12);
                            } catch (Throwable th10) {
                                th = th10;
                                cursor4 = cursorQuery3;
                            }
                        } else {
                            zzzVar = zzzVar2;
                            it3 = it14;
                            zzbcVar = zzbcVarZzah;
                        }
                        it4 = map12.keySet().iterator();
                        while (it4.hasNext()) {
                            Integer num3 = (Integer) it4.next();
                            int iIntValue = num3.intValue();
                            if (this.zzb.contains(num3)) {
                                this.zzu.zzaV().zzk().zzb("Skipping failed audience ID", num3);
                            } else {
                                Iterator it15 = ((List) map12.get(num3)).iterator();
                                boolean zZzd = true;
                                while (true) {
                                    if (!it15.hasNext()) {
                                        map6 = map12;
                                        it5 = it4;
                                        j = j2;
                                        break;
                                    }
                                    com.google.android.gms.internal.measurement.zzff zzffVar4 = (com.google.android.gms.internal.measurement.zzff) it15.next();
                                    map6 = map12;
                                    it5 = it4;
                                    zzaa zzaaVar = new zzaa(this, this.zza, iIntValue, zzffVar4);
                                    j = j2;
                                    zZzd = zzaaVar.zzd(this.zzd, this.zze, zzhsVarZza, j, zzbcVar, zzd(iIntValue, zzffVar4.zzb()));
                                    if (!zZzd) {
                                        this.zzb.add(num3);
                                        break;
                                    }
                                    zzc(num3).zza(zzaaVar);
                                    j2 = j;
                                    map12 = map6;
                                    it4 = it5;
                                }
                                if (!zZzd) {
                                    this.zzb.add(num3);
                                }
                                j2 = j;
                                map12 = map6;
                                it4 = it5;
                            }
                        }
                        it14 = it3;
                        zzzVar2 = zzzVar;
                    }
                }
            }
        }
        if (z) {
            return new ArrayList();
        }
        if (!list2.isEmpty()) {
            ArrayMap arrayMap10 = new ArrayMap();
            Iterator it16 = list2.iterator();
            while (it16.hasNext()) {
                com.google.android.gms.internal.measurement.zziu zziuVar = (com.google.android.gms.internal.measurement.zziu) it16.next();
                String strZzc = zziuVar.zzc();
                Map map13 = (Map) arrayMap10.get(strZzc);
                if (map13 == null) {
                    zzav zzavVarZzj6 = this.zzg.zzj();
                    String str19 = this.zza;
                    zzavVarZzj6.zzay();
                    zzavVarZzj6.zzg();
                    Preconditions.checkNotEmpty(str19);
                    Preconditions.checkNotEmpty(strZzc);
                    ArrayMap arrayMap11 = new ArrayMap();
                    try {
                        cursorQuery2 = zzavVarZzj6.zze().query("property_filters", new String[]{str1422, str1322}, "app_id=? AND property_name=?", new String[]{str19, strZzc}, null, null, null);
                        try {
                            try {
                            } catch (Throwable th11) {
                                th = th11;
                                cursor2 = cursorQuery2;
                                if (cursor2 != null) {
                                    cursor2.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e18) {
                            e = e18;
                            it2 = it16;
                        }
                    } catch (SQLiteException e19) {
                        e = e19;
                        it2 = it16;
                        str6 = str19;
                        cursorQuery2 = null;
                    } catch (Throwable th12) {
                        th = th12;
                        cursor2 = null;
                    }
                    if (cursorQuery2.moveToFirst()) {
                        while (true) {
                            try {
                                com.google.android.gms.internal.measurement.zzfn zzfnVar = (com.google.android.gms.internal.measurement.zzfn) ((com.google.android.gms.internal.measurement.zzfm) zzpj.zzw(com.google.android.gms.internal.measurement.zzfn.zzi(), cursorQuery2.getBlob(1))).zzbc();
                                Integer numValueOf5 = Integer.valueOf(cursorQuery2.getInt(0));
                                List list7 = (List) arrayMap11.get(numValueOf5);
                                if (list7 == null) {
                                    it2 = it16;
                                    try {
                                        arrayList = new ArrayList();
                                        arrayMap11.put(numValueOf5, arrayList);
                                    } catch (SQLiteException e20) {
                                        e = e20;
                                        str6 = str19;
                                        zzavVarZzj6.zzu.zzaV().zzb().zzc("Database error querying filters. appId", zzgt.zzl(str6), e);
                                        map13 = Collections.EMPTY_MAP;
                                        if (cursorQuery2 != null) {
                                        }
                                    }
                                } else {
                                    it2 = it16;
                                    arrayList = list7;
                                }
                                arrayList.add(zzfnVar);
                                str6 = str19;
                            } catch (IOException e21) {
                                it2 = it16;
                                str6 = str19;
                                try {
                                    zzavVarZzj6.zzu.zzaV().zzb().zzc("Failed to merge filter", zzgt.zzl(str6), e21);
                                } catch (SQLiteException e22) {
                                    e = e22;
                                    zzavVarZzj6.zzu.zzaV().zzb().zzc("Database error querying filters. appId", zzgt.zzl(str6), e);
                                    map13 = Collections.EMPTY_MAP;
                                    if (cursorQuery2 != null) {
                                    }
                                }
                            }
                            if (!cursorQuery2.moveToNext()) {
                                break;
                            }
                            it16 = it2;
                            str19 = str6;
                        }
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                        map13 = arrayMap11;
                    } else {
                        it2 = it16;
                        map13 = Collections.EMPTY_MAP;
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                    }
                    arrayMap10.put(strZzc, map13);
                } else {
                    it2 = it16;
                }
                Iterator it17 = map13.keySet().iterator();
                while (true) {
                    if (it17.hasNext()) {
                        Integer num4 = (Integer) it17.next();
                        int iIntValue2 = num4.intValue();
                        if (this.zzb.contains(num4)) {
                            this.zzu.zzaV().zzk().zzb(str1522, num4);
                            break;
                        }
                        Iterator it18 = ((List) map13.get(num4)).iterator();
                        boolean zZzd2 = true;
                        while (true) {
                            if (!it18.hasNext()) {
                                map5 = map13;
                                str5 = str1522;
                                arrayMap = arrayMap10;
                                break;
                            }
                            com.google.android.gms.internal.measurement.zzfn zzfnVar2 = (com.google.android.gms.internal.measurement.zzfn) it18.next();
                            zzib zzibVar2 = this.zzu;
                            map5 = map13;
                            if (Log.isLoggable(zzibVar2.zzaV().zzn(), 2)) {
                                str5 = str1522;
                                arrayMap = arrayMap10;
                                zzibVar2.zzaV().zzk().zzd("Evaluating filter. audience, filter, property", num4, zzfnVar2.zza() ? Integer.valueOf(zzfnVar2.zzb()) : null, zzibVar2.zzl().zzc(zzfnVar2.zzc()));
                                zzibVar2.zzaV().zzk().zzb("Filter definition", this.zzg.zzp().zzk(zzfnVar2));
                            } else {
                                str5 = str1522;
                                arrayMap = arrayMap10;
                            }
                            if (!zzfnVar2.zza() || zzfnVar2.zzb() > 256) {
                                break;
                            }
                            zzac zzacVar = new zzac(this, this.zza, iIntValue2, zzfnVar2);
                            zZzd2 = zzacVar.zzd(this.zzd, this.zze, zziuVar, zzd(iIntValue2, zzfnVar2.zzb()));
                            if (!zZzd2) {
                                this.zzb.add(num4);
                                break;
                            }
                            zzc(num4).zza(zzacVar);
                            map13 = map5;
                            arrayMap10 = arrayMap;
                            str1522 = str5;
                        }
                        if (!zZzd2) {
                            this.zzb.add(num4);
                        }
                        map13 = map5;
                        arrayMap10 = arrayMap;
                        str1522 = str5;
                    }
                }
                it16 = it2;
            }
        }
        ArrayList arrayList7 = new ArrayList();
        Set<Integer> setKeySet = this.zzc.keySet();
        setKeySet.removeAll(this.zzb);
        for (Integer num5 : setKeySet) {
            int iIntValue3 = num5.intValue();
            zzy zzyVar = (zzy) this.zzc.get(num5);
            Preconditions.checkNotNull(zzyVar);
            com.google.android.gms.internal.measurement.zzhg zzhgVarZzb = zzyVar.zzb(iIntValue3);
            arrayList7.add(zzhgVarZzb);
            zzav zzavVarZzj7 = this.zzg.zzj();
            String str20 = this.zza;
            com.google.android.gms.internal.measurement.zzii zziiVarZzc = zzhgVarZzb.zzc();
            zzavVarZzj7.zzay();
            zzavVarZzj7.zzg();
            Preconditions.checkNotEmpty(str20);
            Preconditions.checkNotNull(zziiVarZzc);
            byte[] bArrZzcc = zziiVarZzc.zzcc();
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(ClientContext.APP_ID_KEY, str20);
            contentValues2.put(str1422, num5);
            contentValues2.put("current_results", bArrZzcc);
            try {
            } catch (SQLiteException e23) {
                e = e23;
            }
            try {
                if (zzavVarZzj7.zze().insertWithOnConflict("audience_filter_values", null, contentValues2, 5) == -1) {
                    zzavVarZzj7.zzu.zzaV().zzb().zzb("Failed to insert filter results (got -1). appId", zzgt.zzl(str20));
                }
            } catch (SQLiteException e24) {
                e = e24;
                zzavVarZzj7.zzu.zzaV().zzb().zzc("Error storing filter results. appId", zzgt.zzl(str20), e);
            }
        }
        return arrayList7;
    }

    @Override // com.google.android.gms.measurement.internal.zzor
    protected final boolean zzbb() {
        return false;
    }
}
