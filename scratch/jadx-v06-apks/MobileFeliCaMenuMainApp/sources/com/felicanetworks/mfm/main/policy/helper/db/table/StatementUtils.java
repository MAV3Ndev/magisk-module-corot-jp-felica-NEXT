package com.felicanetworks.mfm.main.policy.helper.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class StatementUtils {
    public static String create(String str, ColumnProperty[] columnPropertyArr, String... strArr) {
        StringBuilder sb = new StringBuilder();
        for (ColumnProperty columnProperty : columnPropertyArr) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append(", ");
            }
            sb.append(columnProperty.title());
            sb.append(" ");
            sb.append(columnProperty.type().value);
            for (String str2 : columnProperty.constraints()) {
                sb.append(" ");
                sb.append(str2);
            }
        }
        for (String str3 : strArr) {
            sb.append(", ");
            sb.append(str3);
        }
        return String.format("CREATE TABLE %s(%s)", str, sb);
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.policy.helper.db.table.StatementUtils$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType;

        static {
            int[] iArr = new int[ColumnType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType = iArr;
            try {
                iArr[ColumnType.TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType[ColumnType.INTEGER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType[ColumnType.BOOLEAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType[ColumnType.BLOB.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static void applyValue(ContentValues contentValues, ColumnProperty columnProperty, Object obj) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType[columnProperty.type().ordinal()];
        if (i == 1) {
            contentValues.put(columnProperty.title(), (String) obj);
            return;
        }
        if (i == 2) {
            contentValues.put(columnProperty.title(), (Integer) obj);
        } else if (i == 3) {
            contentValues.put(columnProperty.title(), (Boolean) obj);
        } else {
            if (i != 4) {
                return;
            }
            contentValues.put(columnProperty.title(), (Byte) obj);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T findValue(Cursor cursor, ColumnProperty columnProperty) {
        int columnIndex = cursor.getColumnIndex(columnProperty.title());
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType[columnProperty.type().ordinal()];
        if (i == 1) {
            return (T) cursor.getString(columnIndex);
        }
        if (i == 2) {
            return (T) Integer.valueOf(cursor.getInt(columnIndex));
        }
        if (i == 3) {
            return (T) Boolean.valueOf(1 == cursor.getInt(columnIndex));
        }
        if (i != 4) {
            return null;
        }
        return (T) cursor.getBlob(columnIndex);
    }

    public static String getWhere(String str, List<String> list) {
        if (list.size() <= 0 || TextUtils.isEmpty(str)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(str + " =");
        int i = 0;
        while (i < list.size()) {
            i++;
            if (i == list.size()) {
                sb.append(" ?");
            } else {
                sb.append(" ? OR ");
                sb.append(str);
                sb.append(" =");
            }
        }
        return sb.toString();
    }
}
