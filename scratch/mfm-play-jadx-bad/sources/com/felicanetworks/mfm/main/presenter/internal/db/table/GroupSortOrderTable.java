package com.felicanetworks.mfm.main.presenter.internal.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import com.felicanetworks.mfm.main.policy.helper.db.table.ColumnProperty;
import com.felicanetworks.mfm.main.policy.helper.db.table.ColumnType;
import com.felicanetworks.mfm.main.policy.helper.db.table.StatementUtils;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class GroupSortOrderTable {
    public static final String LIMIT = "1000";
    public static final int SINCE_MYSERVICE_DB_VERSION = 1;
    public static final String TABLE_NAME = "GroupSortOrder";

    public static class Statement {
        public static final String CREATE = StatementUtils.create(GroupSortOrderTable.TABLE_NAME, Column.values(), String.format("CONSTRAINT pky PRIMARY KEY(%s)", Column.GROUP_ID.title));
    }

    public enum Column implements ColumnProperty {
        GROUP_ID("GroupId", ColumnType.TEXT, "NOT NULL"),
        SORT_PRIORITY("SortPriority", ColumnType.INTEGER, "NOT NULL");

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

    public static class RecordBuilder {
        private final LinkedHashMap<Column, Object> record = new LinkedHashMap<>();

        public RecordBuilder() {
            for (Column column : Column.values()) {
                this.record.put(column, null);
            }
        }

        public ContentValues build() {
            ContentValues contentValues = new ContentValues();
            for (Map.Entry<Column, Object> entry : this.record.entrySet()) {
                StatementUtils.applyValue(contentValues, entry.getKey(), entry.getValue());
            }
            return contentValues;
        }

        public RecordBuilder setGroupId(String value) {
            this.record.put(Column.GROUP_ID, value);
            return this;
        }

        public RecordBuilder setSortPriority(int value) {
            this.record.put(Column.SORT_PRIORITY, Integer.valueOf(value));
            return this;
        }
    }

    public static class Selector {
        public static String findGroupId(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.GROUP_ID);
        }
    }
}
