package com.felicanetworks.tis.resolver;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.NotificationInfoConst;
import com.felicanetworks.tis.datatype.TransactionInfo;
import com.felicanetworks.tis.util.LogMgr;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes3.dex */
abstract class Resolver {
    private static final String AUTHORITY = "content://com.felicanetworks.mfm.main.model.internal.main.db";
    private static final String TABLE_CACHE_ASSET_LIST = "CacheAssetList";
    protected Context mContext;
    protected TreeMap<Integer, TransactionInfo> mTransactionInfoMap = new TreeMap<>();

    abstract List<NotificationInfo> createNotificationInfoList(byte[] bArr, byte[] bArr2);

    abstract String getMenuAppServiceId();

    abstract boolean isMySp(TransactionInfo transactionInfo);

    protected boolean isUpdateAssetCache() {
        return false;
    }

    Resolver() {
    }

    private static final class TableColumns_CacheAsset {
        private static final String BALANCE_VAL = "balanceValue";
        private static final String CARD_ID = "cardId";
        private static final String SERVICE_ID = "serviceId";

        private TableColumns_CacheAsset() {
        }
    }

    void init(Context context) {
        this.mContext = context;
    }

    void addInfo(int i, TransactionInfo transactionInfo) {
        this.mTransactionInfoMap.put(Integer.valueOf(i), transactionInfo);
    }

    protected byte[] getCardNo(byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, i, i3);
            if (i3 <= bArr.length) {
                return bArrCopyOfRange;
            }
            throw new IllegalArgumentException();
        } catch (Exception e) {
            LogMgr.log(6, "700 Invalid byte data. " + e);
            return null;
        }
    }

    protected String getCurrencyCode() {
        return NotificationInfoConst.CurrencyCode.YEN.getString();
    }

    protected void updateAssetCache(String str, String str2, int i) {
        Uri uri;
        String[] strArr;
        LogMgr.log(5, "000");
        LogMgr.log(6, "serviceId = " + str);
        LogMgr.log(6, "cardId = " + str2);
        LogMgr.log(6, "balance = " + i);
        try {
            uri = Uri.parse("content://com.felicanetworks.mfm.main.model.internal.main.db/CacheAssetList");
        } catch (Exception e) {
            LogMgr.log(2, "702 : Catch Exception. " + e);
        }
        if (TextUtils.isEmpty(str)) {
            LogMgr.log(2, "700 : ServiceID is Empty.");
            return;
        }
        String str3 = "serviceId = ? AND cardId = ?";
        if (TextUtils.isEmpty(str2)) {
            if ("SV000027".equals(str)) {
                strArr = new String[]{str, "D00000000000000000000000000000000000000000000000000000000000001"};
            } else {
                str3 = "serviceId = ?";
                strArr = new String[]{str};
            }
        } else {
            strArr = new String[]{str, str2};
        }
        if (i <= -1) {
            LogMgr.log(2, "701 : Balance Error.");
            return;
        }
        LogMgr.log(6, "selection = ".concat(str3));
        for (String str4 : strArr) {
            LogMgr.log(6, "selectionArgs = " + str4);
        }
        LogMgr.log(6, "balance = " + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("balanceValue", Long.valueOf((long) i));
        this.mContext.getContentResolver().update(uri, contentValues, str3, strArr);
        LogMgr.log(5, "999");
    }
}
