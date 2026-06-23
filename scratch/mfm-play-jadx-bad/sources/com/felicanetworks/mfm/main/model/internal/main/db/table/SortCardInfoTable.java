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

/* JADX INFO: loaded from: classes3.dex */
@Deprecated
public class SortCardInfoTable {
    public static final int SINCE_MFM_DB_VERSION = 9;
    static final String TABLE_NAME = "SortCardInfo";

    public static class Statement {
        public static final String DROP = String.format("DROP TABLE IF EXISTS %s", SortCardInfoTable.TABLE_NAME);
    }

    public enum Column implements ColumnProperty {
        SERVICE_ID("ServiceId", ColumnType.TEXT, "NOT NULL"),
        SORT_ORDER("SortOrder", ColumnType.INTEGER, "NOT NULL"),
        DISPLAY("Display", ColumnType.INTEGER, "NOT NULL");

        private final List<String> constraints;
        private final String title;
        private final ColumnType type;

        Column(String title, ColumnType type, String... constraints) {
            this.title = title;
            this.type = type;
            this.constraints = Arrays.asList(constraints);
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

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public static void migrate(SQLiteDatabase db) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = db.query(TABLE_NAME, null, null, null, null, null, Column.SORT_ORDER.title + " ASC", "1000");
            cursorQuery.moveToFirst();
            ArrayList arrayList = new ArrayList();
            while (!cursorQuery.isAfterLast()) {
                String strFindServiceId = Selector.findServiceId(cursorQuery);
                if (ServiceIdPolicy.isAllowed(strFindServiceId)) {
                    if (Selector.findDisplay(cursorQuery).intValue() == 1) {
                        MyServiceDatabaseAccess.getInstance().addHiddenServiceId(strFindServiceId);
                    }
                    String strGroupId = ServiceGroupType.resolve(strFindServiceId).groupId(strFindServiceId);
                    if (!arrayList.contains(strGroupId)) {
                        arrayList.add(strGroupId);
                    }
                }
                cursorQuery.moveToNext();
            }
            MyServiceDatabaseAccess.getInstance().setSortOrderGroupIds(arrayList);
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception unused) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
                throw th;
            }
            throw th;
        }
    }
}
