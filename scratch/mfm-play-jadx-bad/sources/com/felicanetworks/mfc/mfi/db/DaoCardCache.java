package com.felicanetworks.mfc.mfi.db;

/* JADX INFO: loaded from: classes3.dex */
public abstract class DaoCardCache {
    private static final int LIMIT_RECORDS_FROM = 30;
    private static final int LIMIT_RECORDS_TO = 3;

    public abstract void cutOffOverLimitRecords();

    public abstract void delete(String id);

    public abstract void insert(EntityCardCache cardCache);

    public abstract EntityCardCache select(String id);
}
