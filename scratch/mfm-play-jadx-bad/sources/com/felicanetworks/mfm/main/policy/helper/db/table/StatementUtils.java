package com.felicanetworks.mfm.main.policy.helper.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class StatementUtils {
    public static String create(String tableName, ColumnProperty[] columns, String... tableConstraints) {
        StringBuilder sb = new StringBuilder();
        for (ColumnProperty columnProperty : columns) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append(", ");
            }
            sb.append(columnProperty.title());
            sb.append(" ");
            sb.append(columnProperty.type().value);
            for (String str : columnProperty.constraints()) {
                sb.append(" ");
                sb.append(str);
            }
        }
        for (String str2 : tableConstraints) {
            sb.append(", ");
            sb.append(str2);
        }
        return String.format("CREATE TABLE %s(%s)", tableName, sb);
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

    public static void applyValue(ContentValues target, ColumnProperty column, Object value) {
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$policy$helper$db$table$ColumnType[column.type().ordinal()];
        if (i == 1) {
            target.put(column.title(), (String) value);
            return;
        }
        if (i == 2) {
            target.put(column.title(), (Integer) value);
        } else if (i == 3) {
            target.put(column.title(), (Boolean) value);
        } else {
            if (i != 4) {
                return;
            }
            target.put(column.title(), (Byte) value);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v10, resolved type: byte[] */
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

    public static String getWhere(String column, List<String> whereParam) {
        if (whereParam.size() <= 0 || TextUtils.isEmpty(column)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(column + " =");
        int i = 0;
        while (i < whereParam.size()) {
            i++;
            if (i == whereParam.size()) {
                sb.append(" ?");
            } else {
                sb.append(" ? OR ");
                sb.append(column);
                sb.append(" =");
            }
        }
        return sb.toString();
    }
}
