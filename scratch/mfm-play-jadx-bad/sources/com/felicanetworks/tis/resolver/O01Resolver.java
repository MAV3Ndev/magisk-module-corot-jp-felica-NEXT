package com.felicanetworks.tis.resolver;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.datatype.LogInfo;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.NotificationInfoConst;
import com.felicanetworks.tis.datatype.NotificationJsonInfo;
import com.felicanetworks.tis.datatype.TransactionInfo;
import com.felicanetworks.tis.util.ByteBufferMgr;
import com.felicanetworks.tis.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
class O01Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int BLOCK_DATA_MONEY_LENGTH = 2;
    private static final int BLOCK_DATA_MONEY_OFFSET = 14;
    private static final int BLOCK_NUM = 0;
    private static final String SERVICE_ID = "o01";
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_o01;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_o01_icon;

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
    String getMenuAppServiceId() {
        return "";
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        return transactionInfo.isWritingInfo() && isServiceCodeValid(transactionInfo.getServiceCode()) && isAccessModeValid(transactionInfo.getAccessMode()) && isBlockNumberValid(transactionInfo.getBlockNumber());
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr, byte[] bArr2) {
        LogMgr.log(7, "[PFM]O01Resolver#createNotificationInfoList()");
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
            int i = money2;
            if (money > 0 && i > 0) {
                int i2 = money - i;
                String str = String.format(resources.getString(R.string.tis_common_pay_balance), String.format(Locale.JAPAN, "%,d", Integer.valueOf(i)), String.format(Locale.JAPAN, "%,d", Integer.valueOf(i2)));
                Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), ICON_RESOURCE_ID);
                int i3 = TITLE_RESOURCE_ID;
                NotificationInfo notificationInfo = new NotificationInfo(resources.getString(i3), str, R.mipmap.ic_osaifu_touka, bitmapDecodeResource, bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo.setIntent(this.mContext, 12, i, i2, i3, 0);
                notificationInfo.setNotificationJsonInfo(new NotificationJsonInfo("", NotificationInfoConst.TapType.TEST_PAY, i, i2, getCurrencyCode()));
                logInfo.setPaymentLogInfo(12, i, i2);
                notificationInfo.setLogInfo(logInfo);
                logInfo = new LogInfo();
                arrayList.add(notificationInfo);
            }
            LogMgr.log(6, "999 end data analysing: o01");
            money2 = i;
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
