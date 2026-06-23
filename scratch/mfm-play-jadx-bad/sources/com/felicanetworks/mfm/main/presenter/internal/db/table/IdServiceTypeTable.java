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
public class IdServiceTypeTable {
    private static final int MAX_RECORD_PAR_TRANSIT_ID = 1000;
    public static final int SINCE_MYSERVICE_DB_VERSION = 1;
    public static final String TABLE_NAME = "IdServiceType";

    public static class Statement {
        public static final String CREATE = StatementUtils.create(IdServiceTypeTable.TABLE_NAME, Column.values(), String.format("CONSTRAINT pky PRIMARY KEY(%s)", Column.CARD_ID.title));
    }

    public enum Column implements ColumnProperty {
        CARD_ID("CardId", ColumnType.TEXT, "NOT NULL"),
        SERVICE_TYPE("ServiceType", ColumnType.INTEGER, "NOT NULL");

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

        public RecordBuilder setCardId(String value) {
            this.record.put(Column.CARD_ID, value);
            return this;
        }

        public RecordBuilder setServiceType(int value) {
            this.record.put(Column.SERVICE_TYPE, Integer.valueOf(value));
            return this;
        }
    }

    public static class Selector {
        public static Integer findServiceType(Cursor cursor) {
            return (Integer) StatementUtils.findValue(cursor, Column.SERVICE_TYPE);
        }
    }
}
