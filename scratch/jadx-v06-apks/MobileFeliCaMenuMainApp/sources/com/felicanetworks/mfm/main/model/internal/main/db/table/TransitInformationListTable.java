package com.felicanetworks.mfm.main.model.internal.main.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import com.felicanetworks.mfm.main.policy.helper.db.table.ColumnProperty;
import com.felicanetworks.mfm.main.policy.helper.db.table.ColumnType;
import com.felicanetworks.mfm.main.policy.helper.db.table.StatementUtils;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TransitInformationListTable {
    private static final int MAX_RECORD_PAR_CARD_ID = 5;
    public static final int SINCE_MFM_DB_VERSION = 12;
    public static final String TABLE_NAME = "TransitInformationList";

    public static class Statement {
        public static final String CREATE = StatementUtils.create(TransitInformationListTable.TABLE_NAME, Column.values(), String.format("CONSTRAINT pky PRIMARY KEY(%s, %s)", Column.CARD_ID.title, Column.RECORD_NUMBER.title));
        public static final String DELETE_WITH_CARD_ID = String.format("DELETE FROM %s WHERE %s=?", TransitInformationListTable.TABLE_NAME, Column.CARD_ID.title);
        public static final String SELECT_WITH_CARD_ID = String.format(Locale.US, "SELECT * FROM %s WHERE %s=? LIMIT %d", TransitInformationListTable.TABLE_NAME, Column.CARD_ID.title, 5);
    }

    public enum Column implements ColumnProperty {
        CARD_ID("CardId", ColumnType.TEXT, "NOT NULL"),
        RECORD_NUMBER("RecordNumber", ColumnType.INTEGER, "NOT NULL"),
        TRANSIT_PASS_NAME("TransitPassName", ColumnType.TEXT, new String[0]),
        CATEGORY("Category", ColumnType.TEXT, new String[0]),
        COMMUTER_PASS_NAME("CommuterPassName", ColumnType.TEXT, new String[0]),
        FROM1("From1", ColumnType.TEXT, new String[0]),
        TO1("To1", ColumnType.TEXT, new String[0]),
        FROM2("From2", ColumnType.TEXT, new String[0]),
        TO2("To2", ColumnType.TEXT, new String[0]),
        ADDITIONAL_INFORMATION("AdditionalInformation", ColumnType.TEXT, new String[0]),
        VIA("Via", ColumnType.TEXT, new String[0]),
        ISSUER_NAME("IssuerName", ColumnType.TEXT, new String[0]),
        KEY("Key1", ColumnType.TEXT, new String[0]),
        START("Start", ColumnType.TEXT, new String[0]),
        END("End1", ColumnType.TEXT, new String[0]),
        EXTENSION("Extension", ColumnType.BOOLEAN, new String[0]);

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

        public RecordBuilder setRecordNumber(int i) {
            this.record.put(Column.RECORD_NUMBER, Integer.valueOf(i));
            return this;
        }

        public RecordBuilder setTransitPassName(String str) {
            this.record.put(Column.TRANSIT_PASS_NAME, str);
            return this;
        }

        public RecordBuilder setCategory(String str) {
            this.record.put(Column.CATEGORY, str);
            return this;
        }

        public RecordBuilder setCommuterPassName(String str) {
            this.record.put(Column.COMMUTER_PASS_NAME, str);
            return this;
        }

        public RecordBuilder setFrom1(String str) {
            this.record.put(Column.FROM1, str);
            return this;
        }

        public RecordBuilder setTo1(String str) {
            this.record.put(Column.TO1, str);
            return this;
        }

        public RecordBuilder setFrom2(String str) {
            this.record.put(Column.FROM2, str);
            return this;
        }

        public RecordBuilder setTo2(String str) {
            this.record.put(Column.TO2, str);
            return this;
        }

        public RecordBuilder setAdditionalInformation(String str) {
            this.record.put(Column.ADDITIONAL_INFORMATION, str);
            return this;
        }

        public RecordBuilder setVia(String str) {
            this.record.put(Column.VIA, str);
            return this;
        }

        public RecordBuilder setIssuerName(String str) {
            this.record.put(Column.ISSUER_NAME, str);
            return this;
        }

        public RecordBuilder setKey(String str) {
            this.record.put(Column.KEY, str);
            return this;
        }

        public RecordBuilder setStart(String str) {
            this.record.put(Column.START, str);
            return this;
        }

        public RecordBuilder setEnd(String str) {
            this.record.put(Column.END, str);
            return this;
        }

        public RecordBuilder setExtension(boolean z) {
            this.record.put(Column.EXTENSION, Boolean.valueOf(z));
            return this;
        }
    }

    public static class Selector {
        public static String findTransitId(Cursor cursor) {
            return findCardId(cursor) + findRecordNumber(cursor);
        }

        public static String findCardId(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.CARD_ID);
        }

        public static Integer findRecordNumber(Cursor cursor) {
            return (Integer) StatementUtils.findValue(cursor, Column.RECORD_NUMBER);
        }

        public static String findTransitPassName(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.TRANSIT_PASS_NAME);
        }

        public static String findCategory(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.CATEGORY);
        }

        public static String findCommuterPassName(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.COMMUTER_PASS_NAME);
        }

        public static String findFrom1(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.FROM1);
        }

        public static String findTo1(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.TO1);
        }

        public static String findFrom2(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.FROM2);
        }

        public static String findTo2(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.TO2);
        }

        public static String findAdditionalInformation(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.ADDITIONAL_INFORMATION);
        }

        public static String findVia(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.VIA);
        }

        public static String findIssuerName(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.ISSUER_NAME);
        }

        public static String findKey(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.KEY);
        }

        public static String findStart(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.START);
        }

        public static String findEnd(Cursor cursor) {
            return (String) StatementUtils.findValue(cursor, Column.END);
        }

        public static Boolean findExtension(Cursor cursor) {
            return (Boolean) StatementUtils.findValue(cursor, Column.EXTENSION);
        }
    }
}
