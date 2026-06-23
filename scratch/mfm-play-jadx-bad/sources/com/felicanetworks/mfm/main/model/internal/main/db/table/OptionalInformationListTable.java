package com.felicanetworks.mfm.main.model.internal.main.db.table;

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
public class OptionalInformationListTable {
    public static final int MAX_RECORD_PAR_TRANSIT_ID = 10;
    public static final int SINCE_MFM_DB_VERSION = 12;
    public static final String TABLE_NAME = "OptionalInformationList";

    public static class Statement {
        public static final String CREATE = StatementUtils.create(OptionalInformationListTable.TABLE_NAME, Column.values(), new String[0]);
        public static final String DELETE_WITH_CARD_ID = String.format("DELETE FROM %s WHERE %s=?", OptionalInformationListTable.TABLE_NAME, Column.CARD_ID.title);
    }

    public enum Column implements ColumnProperty {
        CARD_ID("CardId", ColumnType.TEXT, "NOT NULL"),
        TRANSIT_ID("TransitId", ColumnType.TEXT, "NOT NULL"),
        KEY("Key1", ColumnType.TEXT, new String[0]),
        VALUE("Value", ColumnType.TEXT, new String[0]);

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

        public RecordBuilder setCardId(String card) {
            this.record.put(Column.CARD_ID, card);
            return this;
        }

        public RecordBuilder setTransitId(String transitId) {
            this.record.put(Column.TRANSIT_ID, transitId);
            return this;
        }

        public RecordBuilder setKey(String key) {
            this.record.put(Column.KEY, key);
            return this;
        }

        public RecordBuilder setValue(String value) {
            this.record.put(Column.VALUE, value);
            return this;
        }
    }

    public static class Selector {
        public static String findKey(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.KEY);
        }

        public static String findValue(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.VALUE);
        }
    }
}
