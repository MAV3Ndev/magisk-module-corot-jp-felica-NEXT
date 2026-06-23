package com.google.android.gms.measurement.internal;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzaw {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0084 A[Catch: SQLiteException -> 0x00ec, TryCatch #4 {SQLiteException -> 0x00ec, blocks: (B:27:0x0046, B:29:0x0076, B:31:0x0084, B:33:0x008c, B:34:0x008f, B:35:0x00b8, B:37:0x00bb, B:39:0x00be, B:41:0x00c6, B:42:0x00cd, B:43:0x00d0, B:45:0x00d6, B:48:0x00e7, B:49:0x00eb, B:28:0x006f), top: B:66:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00bb A[Catch: SQLiteException -> 0x00ec, LOOP:1: B:37:0x00bb->B:42:0x00cd, LOOP_START, PHI: r1
  0x00bb: PHI (r1v5 int) = (r1v4 int), (r1v6 int) binds: [B:36:0x00b9, B:42:0x00cd] A[DONT_GENERATE, DONT_INLINE], TryCatch #4 {SQLiteException -> 0x00ec, blocks: (B:27:0x0046, B:29:0x0076, B:31:0x0084, B:33:0x008c, B:34:0x008f, B:35:0x00b8, B:37:0x00bb, B:39:0x00be, B:41:0x00c6, B:42:0x00cd, B:43:0x00d0, B:45:0x00d6, B:48:0x00e7, B:49:0x00eb, B:28:0x006f), top: B:66:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d6 A[Catch: SQLiteException -> 0x00ec, TryCatch #4 {SQLiteException -> 0x00ec, blocks: (B:27:0x0046, B:29:0x0076, B:31:0x0084, B:33:0x008c, B:34:0x008f, B:35:0x00b8, B:37:0x00bb, B:39:0x00be, B:41:0x00c6, B:42:0x00cd, B:43:0x00d0, B:45:0x00d6, B:48:0x00e7, B:49:0x00eb, B:28:0x006f), top: B:66:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static void zza(zzgt zzgtVar, SQLiteDatabase sQLiteDatabase, String str, String str2, String str3, String[] strArr) throws Throwable {
        Throwable th;
        SQLiteDatabase sQLiteDatabase2;
        Cursor cursorQuery;
        HashSet hashSet;
        Cursor cursorRawQuery;
        boolean zMoveToFirst;
        if (zzgtVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        Cursor cursor = null;
        try {
            try {
                sQLiteDatabase2 = sQLiteDatabase;
            } catch (SQLiteException e) {
                e = e;
                sQLiteDatabase2 = sQLiteDatabase;
            }
            try {
                try {
                    try {
                        cursorQuery = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                        try {
                            try {
                                zMoveToFirst = cursorQuery.moveToFirst();
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                            } catch (SQLiteException e2) {
                                e = e2;
                                zzgtVar.zze().zzc("Error querying for table", str, e);
                                if (cursorQuery != null) {
                                    cursorQuery.close();
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            cursor = cursorQuery;
                            if (cursor != null) {
                                throw th;
                            }
                            cursor.close();
                            throw th;
                        }
                    } catch (SQLiteException e3) {
                        e = e3;
                        cursorQuery = null;
                        zzgtVar.zze().zzc("Error querying for table", str, e);
                        if (cursorQuery != null) {
                        }
                        sQLiteDatabase2.execSQL(str2);
                        hashSet = new HashSet();
                        StringBuilder sb = new StringBuilder(str.length() + 22);
                        sb.append("SELECT * FROM ");
                        sb.append(str);
                        sb.append(" LIMIT 0");
                        cursorRawQuery = sQLiteDatabase2.rawQuery(sb.toString(), null);
                        Collections.addAll(hashSet, cursorRawQuery.getColumnNames());
                        cursorRawQuery.close();
                        while (i < r0) {
                        }
                        if (strArr != null) {
                        }
                        if (hashSet.isEmpty()) {
                        }
                    }
                    Collections.addAll(hashSet, cursorRawQuery.getColumnNames());
                    cursorRawQuery.close();
                    for (String str4 : str3.split(",")) {
                        if (!hashSet.remove(str4)) {
                            StringBuilder sb2 = new StringBuilder(str.length() + 35 + String.valueOf(str4).length());
                            sb2.append("Table ");
                            sb2.append(str);
                            sb2.append(" is missing required column: ");
                            sb2.append(str4);
                            throw new SQLiteException(sb2.toString());
                        }
                    }
                    if (strArr != null) {
                        for (int i = 0; i < strArr.length; i += 2) {
                            if (!hashSet.remove(strArr[i])) {
                                sQLiteDatabase2.execSQL(strArr[i + 1]);
                            }
                        }
                    }
                    if (hashSet.isEmpty()) {
                        zzgtVar.zze().zzc("Table has extra columns. table, columns", str, TextUtils.join(", ", hashSet));
                        return;
                    }
                    return;
                } catch (Throwable th3) {
                    cursorRawQuery.close();
                    throw th3;
                }
                hashSet = new HashSet();
                StringBuilder sb3 = new StringBuilder(str.length() + 22);
                sb3.append("SELECT * FROM ");
                sb3.append(str);
                sb3.append(" LIMIT 0");
                cursorRawQuery = sQLiteDatabase2.rawQuery(sb3.toString(), null);
            } catch (SQLiteException e4) {
                zzgtVar.zzb().zzb("Failed to verify columns on table that was just created", str);
                throw e4;
            }
            if (!zMoveToFirst) {
                sQLiteDatabase2.execSQL(str2);
            }
        } catch (Throwable th4) {
            th = th4;
            if (cursor != null) {
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzb(zzgt zzgtVar, SQLiteDatabase sQLiteDatabase) {
        if (zzgtVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        com.google.android.gms.internal.measurement.zzbv.zza();
        String path = sQLiteDatabase.getPath();
        int i = com.google.android.gms.internal.measurement.zzca.zzb;
        File file = new File(path);
        if (!file.setReadable(false, false)) {
            zzgtVar.zze().zza("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzgtVar.zze().zza("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzgtVar.zze().zza("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzgtVar.zze().zza("Failed to turn on database write permission for owner");
    }
}
