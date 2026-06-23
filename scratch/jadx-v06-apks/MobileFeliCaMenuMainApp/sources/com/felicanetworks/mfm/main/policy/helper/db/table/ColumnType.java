package com.felicanetworks.mfm.main.policy.helper.db.table;

/* JADX INFO: loaded from: classes.dex */
public enum ColumnType {
    TEXT("TEXT"),
    INTEGER("INTEGER"),
    BOOLEAN("BOOLEAN"),
    BLOB("BLOB");

    public final String value;

    ColumnType(String str) {
        this.value = str;
    }
}
