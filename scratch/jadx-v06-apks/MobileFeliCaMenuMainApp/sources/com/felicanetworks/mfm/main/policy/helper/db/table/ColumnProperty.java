package com.felicanetworks.mfm.main.policy.helper.db.table;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public interface ColumnProperty {
    List<String> constraints();

    String title();

    ColumnType type();
}
