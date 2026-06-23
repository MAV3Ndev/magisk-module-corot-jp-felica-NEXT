package com.felicanetworks.tis.resolver;

import android.content.Context;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.TransactionInfo;
import java.util.List;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
abstract class Resolver {
    protected Context mContext;
    protected TreeMap<Integer, TransactionInfo> mTransactionInfoMap = new TreeMap<>();

    abstract List<NotificationInfo> createNotificationInfoList(byte[] bArr);

    abstract boolean isMySp(TransactionInfo transactionInfo);

    Resolver() {
    }

    void init(Context context) {
        this.mContext = context;
    }

    void addInfo(int i, TransactionInfo transactionInfo) {
        this.mTransactionInfoMap.put(Integer.valueOf(i), transactionInfo);
    }
}
