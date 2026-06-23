package com.felicanetworks.tis.resolver;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.datatype.LogInfo;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.TransactionInfo;
import com.felicanetworks.tis.util.ByteBufferMgr;
import com.felicanetworks.tis.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class O01Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int BLOCK_DATA_MONEY_LENGTH = 2;
    private static final int BLOCK_DATA_MONEY_OFFSET = 14;
    private static final int BLOCK_NUM = 0;
    private static final String SERVICE_ID = "o01";

    private boolean isAccessModeValid(int i) {
        return i == 0;
    }

    private boolean isBlockNumberValid(int i) {
        return i == 0;
    }

    private boolean isServiceCodeValid(int i) {
        return i == 3277;
    }

    O01Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        return transactionInfo.isWritingInfo() && isServiceCodeValid(transactionInfo.getServiceCode()) && isAccessModeValid(transactionInfo.getAccessMode()) && isBlockNumberValid(transactionInfo.getBlockNumber());
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        LogInfo logInfo = new LogInfo();
        Resources resources = this.mContext.getResources();
        int money = -1;
        int money2 = -1;
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: o01");
            byte[] blockData = entry.getValue().getBlockData();
            if (money < 0) {
                money = getMoney(blockData);
            } else if (money2 < 0) {
                money2 = getMoney(blockData);
            }
            if (money > 0 && money2 > 0) {
                int i = money - money2;
                NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_o01), String.format(resources.getString(R.string.tis_common_pay_balance), String.format(Locale.JAPAN, "%,d", Integer.valueOf(money2)), String.format(Locale.JAPAN, "%,d", Integer.valueOf(i))), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_o01_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo.setIntent(this.mContext, 12, money2, i);
                logInfo.setPaymentLogInfo(12, money2, i);
                notificationInfo.setLogInfo(logInfo);
                logInfo = new LogInfo();
                arrayList.add(notificationInfo);
            }
            LogMgr.log(6, "999 end data analysing: o01");
        }
        return arrayList;
    }

    private int getMoney(byte[] bArr) {
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 14, 16);
            if (16 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return ByteBufferMgr.getValueFromByteArray(false, bArrCopyOfRange);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }
}
