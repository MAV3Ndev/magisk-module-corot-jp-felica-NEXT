package com.felicanetworks.tis.resolver;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.datatype.LogInfo;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.TransactionInfo;
import com.felicanetworks.tis.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class Q16Resolver extends Resolver {
    private static final int BLOCK_NUM = 4;
    private static final String SERVICE_ID = "q16";

    private boolean isBlockNumberValid(int i) {
        return i == 4;
    }

    private boolean isServiceCodeValid(int i) {
        return i == 13771;
    }

    Q16Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        return transactionInfo.isReadingInfo() && isServiceCodeValid(transactionInfo.getServiceCode()) && isBlockNumberValid(transactionInfo.getBlockNumber());
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        Resources resources = this.mContext.getResources();
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: q16");
            NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_q16), resources.getString(R.string.tis_common_tap), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_q16_icon), bArr, SERVICE_ID, entry.getKey().intValue());
            notificationInfo.setIntent(this.mContext, 3, 0, 0);
            LogInfo logInfo = new LogInfo();
            logInfo.setPaymentLogInfo(3, 0, 0);
            notificationInfo.setLogInfo(logInfo);
            arrayList.add(notificationInfo);
            LogMgr.log(6, "999 end data analysing: q16");
        }
        return arrayList;
    }
}
