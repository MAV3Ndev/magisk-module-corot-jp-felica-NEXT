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

/* JADX INFO: loaded from: classes.dex */
public class IdServiceTypeTable {
    private static final int MAX_RECORD_PAR_TRANSIT_ID = 1000;
    public static final int SINCE_MYSERVICE_DB_VERSION = 1;
    public static final String TABLE_NAME = "IdServiceType";

    public static class Statement {
        public static final String CREATE = StatementUtils.create(IdServiceTypeTable.TABLE_NAME, Column.values(), String.format("CONSTRAINT pky PRIMARY KEY(%s)", Column.CARD_ID.title));
        public static final String SELECT_SERVICE_TYPE_WITH_CID = String.format("SELECT %s FROM %s WHERE %s=? LIMIT 1", Column.SERVICE_TYPE.title, IdServiceTypeTable.TABLE_NAME, Column.CARD_ID.title);
    }

    public enum Column implements ColumnProperty {
        CARD_ID("CardId", ColumnType.TEXT, "NOT NULL"),
        SERVICE_TYPE("ServiceType", ColumnType.INTEGER, "NOT NULL");

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

        public RecordBuilder setCardId(String str) {
            this.record.put(Column.CARD_ID, str);
            return this;
        }

        public RecordBuilder setServiceType(int i) {
            this.record.put(Column.SERVICE_TYPE, Integer.valueOf(i));
            return this;
        }
    }

    public static class Selector {
        public static Integer findServiceType(Cursor cursor) {
            return (Integer) StatementUtils.findValue(cursor, Column.SERVICE_TYPE);
        }
    }
}
