package com.google.android.gms.measurement.internal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.os.Parcel;
import android.os.SystemClock;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzgk extends zzg {
    private static final String[] zza = {"app_version", "ALTER TABLE messages ADD COLUMN app_version TEXT;", "app_version_int", "ALTER TABLE messages ADD COLUMN app_version_int INTEGER;"};
    private final zzgi zzb;
    private boolean zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzgk(zzib zzibVar) {
        super(zzibVar);
        Context contextZzaY = this.zzu.zzaY();
        this.zzu.zzc();
        this.zzb = new zzgi(this, contextZzaY, "google_app_measurement_local.db");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:117:0x017c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x017c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x017c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009e A[Catch: SQLiteException -> 0x0083, SQLiteDatabaseLockedException -> 0x008a, SQLiteFullException -> 0x008e, all -> 0x015b, TRY_ENTER, TryCatch #16 {all -> 0x015b, blocks: (B:23:0x0078, B:25:0x007e, B:36:0x009e, B:38:0x00c2, B:40:0x00cc, B:42:0x00d4, B:48:0x00e9, B:69:0x0122, B:71:0x0128, B:72:0x012b, B:89:0x0162, B:79:0x014b), top: B:106:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0122 A[Catch: all -> 0x015b, TRY_ENTER, TryCatch #16 {all -> 0x015b, blocks: (B:23:0x0078, B:25:0x007e, B:36:0x009e, B:38:0x00c2, B:40:0x00cc, B:42:0x00d4, B:48:0x00e9, B:69:0x0122, B:71:0x0128, B:72:0x012b, B:89:0x0162, B:79:0x014b), top: B:106:0x0078 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0140  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0157 A[PHI: r8 r10 r17
  0x0157: PHI (r8v5 int) = (r8v3 int), (r8v3 int), (r8v6 int) binds: [B:75:0x0143, B:92:0x0179, B:83:0x0155] A[DONT_GENERATE, DONT_INLINE]
  0x0157: PHI (r10v7 android.database.sqlite.SQLiteDatabase) = 
  (r10v5 android.database.sqlite.SQLiteDatabase)
  (r10v6 android.database.sqlite.SQLiteDatabase)
  (r10v8 android.database.sqlite.SQLiteDatabase)
 binds: [B:75:0x0143, B:92:0x0179, B:83:0x0155] A[DONT_GENERATE, DONT_INLINE]
  0x0157: PHI (r17v7 boolean) = (r17v4 boolean), (r17v5 boolean), (r17v8 boolean) binds: [B:75:0x0143, B:92:0x0179, B:83:0x0155] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0176  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final boolean zzs(int i, byte[] bArr) throws Throwable {
        SQLiteDatabase sQLiteDatabaseZzp;
        boolean z;
        boolean z2;
        Cursor cursorRawQuery;
        zzg();
        boolean z3 = false;
        z3 = false;
        if (!this.zzc) {
            zzib zzibVar = this.zzu;
            zzal zzalVarZzc = zzibVar.zzc();
            zzfw zzfwVar = zzfx.zzbc;
            Cursor cursor = null;
            cursor = null;
            zzr zzrVarZzh = zzalVarZzc.zzp(null, zzfwVar) ? this.zzu.zzv().zzh(null) : null;
            ContentValues contentValues = new ContentValues();
            contentValues.put("type", Integer.valueOf(i));
            contentValues.put("entry", bArr);
            if (zzibVar.zzc().zzp(null, zzfwVar) && zzrVarZzh != null) {
                contentValues.put("app_version", zzrVarZzh.zzc);
                contentValues.put("app_version_int", Long.valueOf(zzrVarZzh.zzj));
            }
            zzibVar.zzc();
            int i2 = 0;
            int i3 = 5;
            for (int i4 = 5; i2 < i4; i4 = 5) {
                try {
                    sQLiteDatabaseZzp = zzp();
                } catch (SQLiteDatabaseLockedException unused) {
                    z = z3 ? 1 : 0;
                    sQLiteDatabaseZzp = null;
                    cursorRawQuery = null;
                } catch (SQLiteFullException e) {
                    e = e;
                    z = z3 ? 1 : 0;
                    sQLiteDatabaseZzp = null;
                    cursorRawQuery = null;
                } catch (SQLiteException e2) {
                    e = e2;
                    z = z3 ? 1 : 0;
                    z2 = true;
                    sQLiteDatabaseZzp = null;
                    cursorRawQuery = null;
                } catch (Throwable th) {
                    th = th;
                    sQLiteDatabaseZzp = null;
                }
                if (sQLiteDatabaseZzp == null) {
                    this.zzc = true;
                } else {
                    try {
                        sQLiteDatabaseZzp.beginTransaction();
                        cursorRawQuery = sQLiteDatabaseZzp.rawQuery("select count(1) from messages", null);
                        long j = 0;
                        if (cursorRawQuery == null) {
                            if (j < 100000) {
                            }
                            sQLiteDatabaseZzp.insertOrThrow("messages", null, contentValues);
                            sQLiteDatabaseZzp.setTransactionSuccessful();
                            sQLiteDatabaseZzp.endTransaction();
                            if (cursorRawQuery != null) {
                            }
                            sQLiteDatabaseZzp.close();
                            return z2;
                        }
                        try {
                            try {
                                if (cursorRawQuery.moveToFirst()) {
                                    j = cursorRawQuery.getLong(z3 ? 1 : 0);
                                }
                                if (j < 100000) {
                                    zzibVar.zzaV().zzb().zza("Data loss, local db full");
                                    long j2 = 100001 - j;
                                    long jDelete = sQLiteDatabaseZzp.delete("messages", "rowid in (select rowid from messages order by rowid asc limit ?)", new String[]{Long.toString(j2)});
                                    if (jDelete != j2) {
                                        zzgr zzgrVarZzb = zzibVar.zzaV().zzb();
                                        z = z3 ? 1 : 0;
                                        try {
                                            try {
                                                z2 = true;
                                            } catch (SQLiteDatabaseLockedException unused2) {
                                                SystemClock.sleep(i3);
                                                i3 += 20;
                                                if (cursorRawQuery != null) {
                                                    cursorRawQuery.close();
                                                }
                                                if (sQLiteDatabaseZzp == null) {
                                                    sQLiteDatabaseZzp.close();
                                                }
                                                i2++;
                                                z3 = z;
                                            }
                                        } catch (SQLiteFullException e3) {
                                            e = e3;
                                            this.zzu.zzaV().zzb().zzb("Error writing entry; local database full", e);
                                            this.zzc = true;
                                            if (cursorRawQuery != null) {
                                                cursorRawQuery.close();
                                            }
                                            if (sQLiteDatabaseZzp != null) {
                                            }
                                            i2++;
                                            z3 = z;
                                        } catch (SQLiteException e4) {
                                            e = e4;
                                            z2 = true;
                                            if (sQLiteDatabaseZzp != null && sQLiteDatabaseZzp.inTransaction()) {
                                                sQLiteDatabaseZzp.endTransaction();
                                            }
                                            this.zzu.zzaV().zzb().zzb("Error writing entry to local database", e);
                                            this.zzc = z2;
                                            if (cursorRawQuery != null) {
                                                cursorRawQuery.close();
                                            }
                                            if (sQLiteDatabaseZzp != null) {
                                            }
                                            i2++;
                                            z3 = z;
                                        }
                                        try {
                                            zzgrVarZzb.zzd("Different delete count than expected in local db. expected, received, difference", Long.valueOf(j2), Long.valueOf(jDelete), Long.valueOf(j2 - jDelete));
                                        } catch (SQLiteFullException e5) {
                                            e = e5;
                                            this.zzu.zzaV().zzb().zzb("Error writing entry; local database full", e);
                                            this.zzc = true;
                                            if (cursorRawQuery != null) {
                                            }
                                            if (sQLiteDatabaseZzp != null) {
                                            }
                                            i2++;
                                            z3 = z;
                                        } catch (SQLiteException e6) {
                                            e = e6;
                                            if (sQLiteDatabaseZzp != null) {
                                                sQLiteDatabaseZzp.endTransaction();
                                            }
                                            this.zzu.zzaV().zzb().zzb("Error writing entry to local database", e);
                                            this.zzc = z2;
                                            if (cursorRawQuery != null) {
                                            }
                                            if (sQLiteDatabaseZzp != null) {
                                            }
                                            i2++;
                                            z3 = z;
                                        }
                                    } else {
                                        z = z3 ? 1 : 0;
                                        z2 = true;
                                    }
                                }
                                sQLiteDatabaseZzp.insertOrThrow("messages", null, contentValues);
                                sQLiteDatabaseZzp.setTransactionSuccessful();
                                sQLiteDatabaseZzp.endTransaction();
                                if (cursorRawQuery != null) {
                                    cursorRawQuery.close();
                                }
                                sQLiteDatabaseZzp.close();
                                return z2;
                            } catch (Throwable th2) {
                                th = th2;
                                cursor = cursorRawQuery;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                if (sQLiteDatabaseZzp != null) {
                                    sQLiteDatabaseZzp.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteDatabaseLockedException unused3) {
                            z = z3 ? 1 : 0;
                            SystemClock.sleep(i3);
                            i3 += 20;
                            if (cursorRawQuery != null) {
                            }
                            if (sQLiteDatabaseZzp == null) {
                            }
                            i2++;
                            z3 = z;
                        } catch (SQLiteFullException e7) {
                            e = e7;
                            z = z3 ? 1 : 0;
                            this.zzu.zzaV().zzb().zzb("Error writing entry; local database full", e);
                            this.zzc = true;
                            if (cursorRawQuery != null) {
                            }
                            if (sQLiteDatabaseZzp != null) {
                            }
                            i2++;
                            z3 = z;
                        } catch (SQLiteException e8) {
                            e = e8;
                            z = z3 ? 1 : 0;
                            z2 = true;
                            if (sQLiteDatabaseZzp != null) {
                            }
                            this.zzu.zzaV().zzb().zzb("Error writing entry to local database", e);
                            this.zzc = z2;
                            if (cursorRawQuery != null) {
                            }
                            if (sQLiteDatabaseZzp != null) {
                            }
                            i2++;
                            z3 = z;
                        }
                    } catch (SQLiteDatabaseLockedException unused4) {
                        z = z3 ? 1 : 0;
                        cursorRawQuery = null;
                    } catch (SQLiteFullException e9) {
                        e = e9;
                        z = z3 ? 1 : 0;
                        cursorRawQuery = null;
                    } catch (SQLiteException e10) {
                        e = e10;
                        z = z3 ? 1 : 0;
                        z2 = true;
                        cursorRawQuery = null;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                    i2++;
                    z3 = z;
                }
            }
            boolean z4 = z3 ? 1 : 0;
            this.zzu.zzaV().zzk().zza("Failed to write entry to local database");
            return z4;
        }
        return z3;
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zze() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzh() {
        int iDelete;
        zzg();
        try {
            SQLiteDatabase sQLiteDatabaseZzp = zzp();
            if (sQLiteDatabaseZzp == null || (iDelete = sQLiteDatabaseZzp.delete("messages", null, null)) <= 0) {
                return;
            }
            this.zzu.zzaV().zzk().zzb("Reset local analytics data. records", Integer.valueOf(iDelete));
        } catch (SQLiteException e) {
            this.zzu.zzaV().zzb().zzb("Error resetting local analytics data. error", e);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzi(zzbg zzbgVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzbh.zza(zzbgVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzs(0, bArrMarshall);
        }
        this.zzu.zzaV().zzc().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzj(zzpk zzpkVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzpl.zza(zzpkVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzs(1, bArrMarshall);
        }
        this.zzu.zzaV().zzc().zza("User property too long for local database. Sending directly to service");
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzk(zzah zzahVar) {
        zzib zzibVar = this.zzu;
        byte[] bArrZzae = zzibVar.zzk().zzae(zzahVar);
        if (bArrZzae.length <= 131072) {
            return zzs(2, bArrZzae);
        }
        zzibVar.zzaV().zzc().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzl(zzbe zzbeVar) {
        zzib zzibVar = this.zzu;
        byte[] bArrZzae = zzibVar.zzk().zzae(zzbeVar);
        if (bArrZzae == null) {
            zzibVar.zzaV().zzc().zza("Null default event parameters; not writing to database");
            return false;
        }
        if (bArrZzae.length <= 131072) {
            return zzs(4, bArrZzae);
        }
        zzibVar.zzaV().zzc().zza("Default event parameters too long for local database. Sending directly to service");
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:153:0x02b6 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:155:0x02b9 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:169:0x02f1 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:171:0x02f3 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:192:0x0343 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:225:0x034f */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:229:0x034e */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0325 A[Catch: all -> 0x0360, TRY_ENTER, TryCatch #0 {all -> 0x0360, blocks: (B:32:0x00b8, B:34:0x00be, B:36:0x00d1, B:38:0x00d7, B:43:0x00f0, B:48:0x0108, B:50:0x010d, B:197:0x0350, B:186:0x0325, B:188:0x032b, B:189:0x032e, B:208:0x036c, B:61:0x0137, B:62:0x013a, B:58:0x012d, B:73:0x0156, B:75:0x016a, B:82:0x0185, B:83:0x018e, B:84:0x0191, B:80:0x017f, B:91:0x0199, B:95:0x01af, B:105:0x01d0, B:106:0x01da, B:107:0x01dd, B:103:0x01ca, B:110:0x01e3, B:114:0x01f7, B:124:0x0216, B:126:0x0220, B:127:0x0223, B:122:0x0210, B:134:0x022c, B:135:0x023c, B:145:0x027b, B:147:0x0298, B:148:0x02a7), top: B:230:0x0350 }] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0340  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x035c A[PHI: r6 r11 r13 r17 r19 r21
  0x035c: PHI (r6v13 int) = (r6v6 int), (r6v8 int), (r6v14 int) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r11v3 int) = (r11v1 int), (r11v1 int), (r11v4 int) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r13v9 ??) = (r13v4 ??), (r13v6 ??), (r13v10 ??) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r17v8 java.lang.String) = (r17v3 java.lang.String), (r17v5 java.lang.String), (r17v9 java.lang.String) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r19v8 java.lang.String) = (r19v3 java.lang.String), (r19v5 java.lang.String), (r19v9 java.lang.String) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]
  0x035c: PHI (r21v8 java.lang.String) = (r21v3 java.lang.String), (r21v5 java.lang.String), (r21v9 java.lang.String) binds: [B:192:0x0343, B:211:0x0381, B:201:0x035a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x0399  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0384 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0384 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0384 A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v10 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v12, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r13v13 */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r13v9, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r15v5, types: [com.google.android.gms.measurement.internal.zzal] */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v4 */
    /* JADX WARN: Type inference failed for: r17v15 */
    /* JADX WARN: Type inference failed for: r17v16, types: [java.lang.String[]] */
    /* JADX WARN: Type inference failed for: r17v43 */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1, types: [java.lang.String, java.util.List] */
    /* JADX WARN: Type inference failed for: r6v12 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List zzm(int i) {
        Cursor cursor;
        ?? r13;
        String str;
        String str2;
        String str3;
        int i2;
        ?? Zzp;
        Cursor cursorQuery;
        Cursor cursorQuery2;
        long j;
        long j2;
        ?? r17;
        ?? r16;
        int i3;
        zzfw zzfwVar;
        int i4;
        int i5;
        int i6;
        zzib zzibVar;
        long j3;
        String string;
        zzib zzibVar2;
        zzbg zzbgVarCreateFromParcel;
        int i7;
        int i8;
        zzbe zzbeVarCreateFromParcel;
        zzah zzahVarCreateFromParcel;
        zzpk zzpkVarCreateFromParcel;
        String str4 = "entry";
        String str5 = "type";
        String str6 = "rowid";
        zzg();
        ?? r6 = 0;
        if (this.zzc) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (!zzq()) {
            return arrayList;
        }
        int i9 = 0;
        int i10 = 5;
        int i11 = 0;
        for (int i12 = 5; i11 < i12; i12 = 5) {
            try {
                Zzp = zzp();
                try {
                } catch (Throwable th) {
                    th = th;
                    cursor = null;
                    r13 = Zzp;
                    if (cursor != null) {
                    }
                    if (r13 != 0) {
                    }
                    throw th;
                }
            } catch (SQLiteDatabaseLockedException unused) {
                str = str4;
                str2 = str5;
                str3 = str6;
                i2 = i9;
                Zzp = 0;
            } catch (SQLiteFullException e) {
                e = e;
                str = str4;
                str2 = str5;
                str3 = str6;
                i2 = i9;
                Zzp = 0;
            } catch (SQLiteException e2) {
                e = e2;
                str = str4;
                str2 = str5;
                str3 = str6;
                i2 = i9;
                Zzp = 0;
            } catch (Throwable th2) {
                th = th2;
                cursor = null;
                r13 = 0;
            }
            if (Zzp == 0) {
                this.zzc = true;
                return r6;
            }
            try {
                Zzp.beginTransaction();
                try {
                    cursorQuery2 = Zzp.query("messages", new String[]{str6}, "type=?", new String[]{"3"}, null, null, "rowid desc", "1");
                } catch (Throwable th3) {
                    th = th3;
                    str = str4;
                    str2 = str5;
                    str3 = str6;
                    i2 = i9;
                    cursorQuery2 = null;
                }
                try {
                    j = -1;
                    if (cursorQuery2.moveToFirst()) {
                        j2 = cursorQuery2.getLong(i9);
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                    } else {
                        if (cursorQuery2 != null) {
                            cursorQuery2.close();
                        }
                        j2 = -1;
                    }
                    if (j2 != -1) {
                        String[] strArr = new String[1];
                        strArr[i9] = String.valueOf(j2);
                        r16 = "rowid<?";
                        r17 = strArr;
                    } else {
                        ?? r162 = r6;
                        r17 = r162;
                        r16 = r162;
                    }
                    String[] strArr2 = {str6, str5, str4};
                    zzib zzibVar3 = this.zzu;
                    ?? Zzc = zzibVar3.zzc();
                    i3 = 1;
                    zzfwVar = zzfx.zzbc;
                    boolean zZzp = Zzc.zzp(r6, zzfwVar);
                    i4 = 4;
                    i5 = 3;
                    i6 = 2;
                    if (zZzp) {
                        strArr2 = new String[i12];
                        strArr2[i9] = str6;
                        strArr2[1] = str5;
                        strArr2[2] = str4;
                        strArr2[3] = "app_version";
                        strArr2[4] = "app_version_int";
                    }
                    String[] strArr3 = strArr2;
                    zzibVar = zzibVar3;
                    cursorQuery = Zzp.query("messages", strArr3, r16, r17, null, null, "rowid asc", Integer.toString(100));
                } catch (Throwable th4) {
                    th = th4;
                    str = str4;
                    str2 = str5;
                    str3 = str6;
                    i2 = i9;
                    if (cursorQuery2 != null) {
                        try {
                            cursorQuery2.close();
                        } catch (SQLiteDatabaseLockedException unused2) {
                            cursorQuery = null;
                            try {
                                SystemClock.sleep(i10);
                                i10 += 20;
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                                if (Zzp == 0) {
                                    Zzp.close();
                                }
                                i11++;
                                i9 = i2;
                                str4 = str;
                                str5 = str2;
                                str6 = str3;
                                r6 = 0;
                            } catch (Throwable th5) {
                                th = th5;
                                cursor = cursorQuery;
                                r13 = Zzp;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                if (r13 != 0) {
                                    r13.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteFullException e3) {
                            e = e3;
                            cursorQuery = null;
                            this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                            this.zzc = true;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (Zzp == 0) {
                            }
                            i11++;
                            i9 = i2;
                            str4 = str;
                            str5 = str2;
                            str6 = str3;
                            r6 = 0;
                        } catch (SQLiteException e4) {
                            e = e4;
                            cursorQuery = null;
                            if (Zzp != 0 && Zzp.inTransaction()) {
                                Zzp.endTransaction();
                            }
                            this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                            this.zzc = true;
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (Zzp == 0) {
                            }
                            i11++;
                            i9 = i2;
                            str4 = str;
                            str5 = str2;
                            str6 = str3;
                            r6 = 0;
                        }
                    }
                    throw th;
                }
            } catch (SQLiteDatabaseLockedException unused3) {
                str = str4;
                str2 = str5;
                str3 = str6;
                i2 = i9;
            } catch (SQLiteFullException e5) {
                e = e5;
                str = str4;
                str2 = str5;
                str3 = str6;
                i2 = i9;
            } catch (SQLiteException e6) {
                e = e6;
                str = str4;
                str2 = str5;
                str3 = str6;
                i2 = i9;
            }
            while (cursorQuery.moveToNext()) {
                try {
                    j = cursorQuery.getLong(i9);
                    int i13 = cursorQuery.getInt(i3);
                    byte[] blob = cursorQuery.getBlob(i6);
                    try {
                        if (zzibVar.zzc().zzp(null, zzfwVar)) {
                            string = cursorQuery.getString(i5);
                            j3 = cursorQuery.getLong(i4);
                        } else {
                            j3 = 0;
                            string = null;
                        }
                        String str7 = str4;
                        long j4 = j3;
                        str2 = str5;
                        if (i13 == 0) {
                            try {
                                Parcel parcelObtain = Parcel.obtain();
                                zzibVar2 = zzibVar;
                                try {
                                    str3 = str6;
                                    try {
                                        try {
                                            parcelObtain.unmarshall(blob, 0, blob.length);
                                            parcelObtain.setDataPosition(0);
                                            zzbgVarCreateFromParcel = zzbg.CREATOR.createFromParcel(parcelObtain);
                                        } catch (SafeParcelReader.ParseException unused4) {
                                            this.zzu.zzaV().zzb().zza("Failed to load event from local database");
                                        }
                                    } catch (Throwable th6) {
                                        th = th6;
                                        throw th;
                                    }
                                } catch (SafeParcelReader.ParseException unused5) {
                                    str3 = str6;
                                } catch (Throwable th7) {
                                    th = th7;
                                }
                            } catch (SQLiteDatabaseLockedException unused6) {
                                str3 = str6;
                            } catch (SQLiteFullException e7) {
                                e = e7;
                                str3 = str6;
                            } catch (SQLiteException e8) {
                                e = e8;
                                str3 = str6;
                            }
                            try {
                                if (zzbgVarCreateFromParcel != null) {
                                    arrayList.add(new zzgj(zzbgVarCreateFromParcel, string, j4));
                                }
                                str = str7;
                                i8 = 2;
                                i7 = 3;
                                i2 = 0;
                                i5 = i7;
                                i9 = i2;
                                str4 = str;
                                str5 = str2;
                                str6 = str3;
                                i3 = 1;
                                i4 = 4;
                                i6 = i8;
                                zzibVar = zzibVar2;
                            } catch (SQLiteDatabaseLockedException unused7) {
                                str = str7;
                                i2 = 0;
                                SystemClock.sleep(i10);
                                i10 += 20;
                                if (cursorQuery != null) {
                                }
                                if (Zzp == 0) {
                                }
                                i11++;
                                i9 = i2;
                                str4 = str;
                                str5 = str2;
                                str6 = str3;
                                r6 = 0;
                            } catch (SQLiteFullException e9) {
                                e = e9;
                                str = str7;
                                i2 = 0;
                                this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                                this.zzc = true;
                                if (cursorQuery != null) {
                                }
                                if (Zzp == 0) {
                                }
                                i11++;
                                i9 = i2;
                                str4 = str;
                                str5 = str2;
                                str6 = str3;
                                r6 = 0;
                            } catch (SQLiteException e10) {
                                e = e10;
                                str = str7;
                                i2 = 0;
                                if (Zzp != 0) {
                                    Zzp.endTransaction();
                                }
                                this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                                this.zzc = true;
                                if (cursorQuery != null) {
                                }
                                if (Zzp == 0) {
                                }
                                i11++;
                                i9 = i2;
                                str4 = str;
                                str5 = str2;
                                str6 = str3;
                                r6 = 0;
                            }
                        } else {
                            zzibVar2 = zzibVar;
                            str3 = str6;
                            if (i13 == 1) {
                                Parcel parcelObtain2 = Parcel.obtain();
                                try {
                                    try {
                                        parcelObtain2.unmarshall(blob, 0, blob.length);
                                        parcelObtain2.setDataPosition(0);
                                        zzpkVarCreateFromParcel = zzpk.CREATOR.createFromParcel(parcelObtain2);
                                    } finally {
                                        parcelObtain2.recycle();
                                    }
                                } catch (SafeParcelReader.ParseException unused8) {
                                    this.zzu.zzaV().zzb().zza("Failed to load user property from local database");
                                    parcelObtain2.recycle();
                                    zzpkVarCreateFromParcel = null;
                                }
                                if (zzpkVarCreateFromParcel != null) {
                                    arrayList.add(new zzgj(zzpkVarCreateFromParcel, string, j4));
                                }
                                str = str7;
                                i8 = 2;
                                i7 = 3;
                                i2 = 0;
                                i5 = i7;
                                i9 = i2;
                                str4 = str;
                                str5 = str2;
                                str6 = str3;
                                i3 = 1;
                                i4 = 4;
                                i6 = i8;
                                zzibVar = zzibVar2;
                            } else {
                                i8 = 2;
                                if (i13 == 2) {
                                    Parcel parcelObtain3 = Parcel.obtain();
                                    try {
                                        str = str7;
                                        try {
                                            try {
                                                parcelObtain3.unmarshall(blob, 0, blob.length);
                                                parcelObtain3.setDataPosition(0);
                                                zzahVarCreateFromParcel = zzah.CREATOR.createFromParcel(parcelObtain3);
                                            } catch (Throwable th8) {
                                                th = th8;
                                                throw th;
                                            }
                                        } catch (SafeParcelReader.ParseException unused9) {
                                            this.zzu.zzaV().zzb().zza("Failed to load conditional user property from local database");
                                            parcelObtain3.recycle();
                                            zzahVarCreateFromParcel = null;
                                        }
                                    } catch (SafeParcelReader.ParseException unused10) {
                                        str = str7;
                                    } catch (Throwable th9) {
                                        th = th9;
                                        str = str7;
                                    }
                                    try {
                                        if (zzahVarCreateFromParcel != null) {
                                            arrayList.add(new zzgj(zzahVarCreateFromParcel, string, j4));
                                        }
                                        i7 = 3;
                                        i2 = 0;
                                        i5 = i7;
                                        i9 = i2;
                                        str4 = str;
                                        str5 = str2;
                                        str6 = str3;
                                        i3 = 1;
                                        i4 = 4;
                                        i6 = i8;
                                        zzibVar = zzibVar2;
                                    } catch (SQLiteDatabaseLockedException unused11) {
                                        i2 = 0;
                                        SystemClock.sleep(i10);
                                        i10 += 20;
                                        if (cursorQuery != null) {
                                        }
                                        if (Zzp == 0) {
                                        }
                                        i11++;
                                        i9 = i2;
                                        str4 = str;
                                        str5 = str2;
                                        str6 = str3;
                                        r6 = 0;
                                    } catch (SQLiteFullException e11) {
                                        e = e11;
                                        i2 = 0;
                                        this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                                        this.zzc = true;
                                        if (cursorQuery != null) {
                                        }
                                        if (Zzp == 0) {
                                        }
                                        i11++;
                                        i9 = i2;
                                        str4 = str;
                                        str5 = str2;
                                        str6 = str3;
                                        r6 = 0;
                                    } catch (SQLiteException e12) {
                                        e = e12;
                                        i2 = 0;
                                        if (Zzp != 0) {
                                        }
                                        this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                                        this.zzc = true;
                                        if (cursorQuery != null) {
                                        }
                                        if (Zzp == 0) {
                                        }
                                        i11++;
                                        i9 = i2;
                                        str4 = str;
                                        str5 = str2;
                                        str6 = str3;
                                        r6 = 0;
                                    }
                                } else {
                                    str = str7;
                                    if (i13 == 4) {
                                        Parcel parcelObtain4 = Parcel.obtain();
                                        try {
                                            i2 = 0;
                                            try {
                                                try {
                                                    parcelObtain4.unmarshall(blob, 0, blob.length);
                                                    parcelObtain4.setDataPosition(0);
                                                    zzbeVarCreateFromParcel = zzbe.CREATOR.createFromParcel(parcelObtain4);
                                                } catch (Throwable th10) {
                                                    th = th10;
                                                    throw th;
                                                }
                                            } catch (SafeParcelReader.ParseException unused12) {
                                                this.zzu.zzaV().zzb().zza("Failed to load default event parameters from local database");
                                                parcelObtain4.recycle();
                                                zzbeVarCreateFromParcel = null;
                                            }
                                        } catch (SafeParcelReader.ParseException unused13) {
                                            i2 = 0;
                                        } catch (Throwable th11) {
                                            th = th11;
                                        }
                                        try {
                                            if (zzbeVarCreateFromParcel != null) {
                                                arrayList.add(new zzgj(zzbeVarCreateFromParcel, string, j4));
                                            }
                                            i7 = 3;
                                        } catch (SQLiteDatabaseLockedException unused14) {
                                            SystemClock.sleep(i10);
                                            i10 += 20;
                                            if (cursorQuery != null) {
                                            }
                                            if (Zzp == 0) {
                                            }
                                            i11++;
                                            i9 = i2;
                                            str4 = str;
                                            str5 = str2;
                                            str6 = str3;
                                            r6 = 0;
                                        } catch (SQLiteFullException e13) {
                                            e = e13;
                                            this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                                            this.zzc = true;
                                            if (cursorQuery != null) {
                                            }
                                            if (Zzp == 0) {
                                            }
                                            i11++;
                                            i9 = i2;
                                            str4 = str;
                                            str5 = str2;
                                            str6 = str3;
                                            r6 = 0;
                                        } catch (SQLiteException e14) {
                                            e = e14;
                                            if (Zzp != 0) {
                                            }
                                            this.zzu.zzaV().zzb().zzb("Error reading entries from local database", e);
                                            this.zzc = true;
                                            if (cursorQuery != null) {
                                            }
                                            if (Zzp == 0) {
                                            }
                                            i11++;
                                            i9 = i2;
                                            str4 = str;
                                            str5 = str2;
                                            str6 = str3;
                                            r6 = 0;
                                        }
                                    } else {
                                        i7 = 3;
                                        i2 = 0;
                                        if (i13 == 3) {
                                            this.zzu.zzaV().zzk().zza("Skipping app launch break");
                                        } else {
                                            this.zzu.zzaV().zzb().zza("Unknown record type in local database");
                                        }
                                    }
                                    i5 = i7;
                                    i9 = i2;
                                    str4 = str;
                                    str5 = str2;
                                    str6 = str3;
                                    i3 = 1;
                                    i4 = 4;
                                    i6 = i8;
                                    zzibVar = zzibVar2;
                                }
                            }
                        }
                    } catch (SQLiteDatabaseLockedException unused15) {
                        str = str4;
                        str2 = str5;
                        str3 = str6;
                    } catch (SQLiteFullException e15) {
                        e = e15;
                        str = str4;
                        str2 = str5;
                        str3 = str6;
                    } catch (SQLiteException e16) {
                        e = e16;
                        str = str4;
                        str2 = str5;
                        str3 = str6;
                    }
                } catch (SQLiteDatabaseLockedException unused16) {
                    str = str4;
                    str2 = str5;
                    str3 = str6;
                    i2 = i9;
                } catch (SQLiteFullException e17) {
                    e = e17;
                    str = str4;
                    str2 = str5;
                    str3 = str6;
                    i2 = i9;
                } catch (SQLiteException e18) {
                    e = e18;
                    str = str4;
                    str2 = str5;
                    str3 = str6;
                    i2 = i9;
                }
                i11++;
                i9 = i2;
                str4 = str;
                str5 = str2;
                str6 = str3;
                r6 = 0;
            }
            if (Zzp.delete("messages", "rowid <= ?", new String[]{Long.toString(j)}) < arrayList.size()) {
                this.zzu.zzaV().zzb().zza("Fewer entries removed from local database than expected");
            }
            Zzp.setTransactionSuccessful();
            Zzp.endTransaction();
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            Zzp.close();
            return arrayList;
        }
        this.zzu.zzaV().zze().zza("Failed to read events from database in reasonable time");
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzn() {
        return zzs(3, new byte[0]);
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0068 A[PHI: r4
  0x0068: PHI (r4v4 int) = (r4v2 int), (r4v1 int), (r4v1 int) binds: [B:28:0x0066, B:25:0x005f, B:32:0x007c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean zzo() {
        zzg();
        if (!this.zzc && zzq()) {
            int i = 5;
            int i2 = 0;
            while (true) {
                if (i2 >= 5) {
                    this.zzu.zzaV().zze().zza("Error deleting app launch break from local database in reasonable time");
                    break;
                }
                SQLiteDatabase sQLiteDatabase = null;
                try {
                    try {
                        try {
                            SQLiteDatabase sQLiteDatabaseZzp = zzp();
                            if (sQLiteDatabaseZzp != null) {
                                sQLiteDatabaseZzp.beginTransaction();
                                sQLiteDatabaseZzp.delete("messages", "type == ?", new String[]{Integer.toString(3)});
                                sQLiteDatabaseZzp.setTransactionSuccessful();
                                sQLiteDatabaseZzp.endTransaction();
                                sQLiteDatabaseZzp.close();
                                return true;
                            }
                            this.zzc = true;
                        } catch (SQLiteFullException e) {
                            this.zzu.zzaV().zzb().zzb("Error deleting app launch break from local database", e);
                            this.zzc = true;
                            if (0 != 0) {
                                sQLiteDatabase.close();
                            }
                            i2++;
                        }
                    } catch (SQLiteException e2) {
                        if (0 != 0) {
                            try {
                                if (sQLiteDatabase.inTransaction()) {
                                    sQLiteDatabase.endTransaction();
                                }
                            } catch (Throwable th) {
                                if (0 != 0) {
                                    sQLiteDatabase.close();
                                }
                                throw th;
                            }
                        }
                        this.zzu.zzaV().zzb().zzb("Error deleting app launch break from local database", e2);
                        this.zzc = true;
                        if (0 != 0) {
                            sQLiteDatabase.close();
                            i2++;
                        } else {
                            i2++;
                        }
                    }
                } catch (SQLiteDatabaseLockedException unused) {
                    SystemClock.sleep(i);
                    i += 20;
                    if (0 != 0) {
                    }
                    i2++;
                }
                i2++;
            }
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final SQLiteDatabase zzp() throws SQLiteException {
        if (this.zzc) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zzb.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzc = true;
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzq() {
        zzib zzibVar = this.zzu;
        Context contextZzaY = zzibVar.zzaY();
        zzibVar.zzc();
        return contextZzaY.getDatabasePath("google_app_measurement_local.db").exists();
    }
}
