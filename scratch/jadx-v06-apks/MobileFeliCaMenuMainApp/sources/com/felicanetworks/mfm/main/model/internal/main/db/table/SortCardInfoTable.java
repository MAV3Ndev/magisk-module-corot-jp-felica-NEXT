package com.felicanetworks.mfm.main.model.internal.main.db.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.felicanetworks.mfm.main.policy.helper.db.table.ColumnProperty;
import com.felicanetworks.mfm.main.policy.helper.db.table.ColumnType;
import com.felicanetworks.mfm.main.policy.helper.db.table.StatementUtils;
import com.felicanetworks.mfm.main.policy.service.ServiceGroupType;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class SortCardInfoTable {
    public static final int SINCE_MFM_DB_VERSION = 9;
    static final String TABLE_NAME = "SortCardInfo";

    public static class Statement {
        public static final String DROP = String.format("DROP TABLE IF EXISTS %s", SortCardInfoTable.TABLE_NAME);
        static final String SELECT_ALL_ORDERED = String.format("SELECT * FROM %s ORDER BY %s ASC LIMIT 1000", SortCardInfoTable.TABLE_NAME, Column.SORT_ORDER.title);
    }

    public enum Column implements ColumnProperty {
        SERVICE_ID("ServiceId", ColumnType.TEXT, "NOT NULL"),
        SORT_ORDER("SortOrder", ColumnType.INTEGER, "NOT NULL"),
        DISPLAY("Display", ColumnType.INTEGER, "NOT NULL");

        private final List<String> constraints;
        private final String title;
        private final ColumnType type;

        Column(String str, ColumnType columnType, String... strArr) {
            this.title = str;
            this.type = columnType;
            this.constraints = Arrays.asList(strArr);
        }

        @Override // com.felicanetworks.mfm.main.policy.helper.db.table.ColumnProperty
        public String title() {
            return this.title;
        }

        @Override // com.felicanetworks.mfm.main.policy.helper.db.table.ColumnProperty
        public ColumnType type() {
            return this.type;
        }

        @Override // com.felicanetworks.mfm.main.policy.helper.db.table.ColumnProperty
        public List<String> constraints() {
            return this.constraints;
        }
    }

    static class Selector {
        Selector() {
        }

        static String findServiceId(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.SERVICE_ID);
        }

        static Integer findDisplay(Cursor cursor) {
            return (Integer) StatementUtils.findValue(cursor, Column.DISPLAY);
        }
    }

    public static void migrate(SQLiteDatabase sQLiteDatabase) {
        try {
            Cursor cursorRawQuery = sQLiteDatabase.rawQuery(Statement.SELECT_ALL_ORDERED, null);
            try {
                ArrayList arrayList = new ArrayList();
                cursorRawQuery.moveToFirst();
                while (!cursorRawQuery.isAfterLast()) {
                    String strFindServiceId = Selector.findServiceId(cursorRawQuery);
                    if (ServiceIdPolicy.isAllowed(strFindServiceId)) {
                        if (Selector.findDisplay(cursorRawQuery).intValue() == 1) {
                            MyServiceDatabaseAccess.getInstance().addHiddenServiceId(strFindServiceId);
                        }
                        String strGroupId = ServiceGroupType.resolve(strFindServiceId).groupId(strFindServiceId);
                        if (!arrayList.contains(strGroupId)) {
                            arrayList.add(strGroupId);
                        }
                    }
                    cursorRawQuery.moveToNext();
                }
                MyServiceDatabaseAccess.getInstance().setSortOrderGroupIds(arrayList);
                if (cursorRawQuery != null) {
                    cursorRawQuery.close();
                }
            } finally {
            }
        } catch (Exception unused) {
        }
    }
}
