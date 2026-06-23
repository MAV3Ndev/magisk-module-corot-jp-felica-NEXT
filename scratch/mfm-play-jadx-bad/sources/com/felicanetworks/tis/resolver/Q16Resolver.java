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
import com.felicanetworks.tis.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
class Q16Resolver extends Resolver {
    private static final int BLOCK_NUM = 4;
    private static final int CARD_NO_BLOCK_DATA_LENGTH = 16;
    private static final int CARD_NO_BLOCK_DATA_OFFSET = 0;
    private static final String SERVICE_ID = "q16";
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_q16;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_q16_icon;
    private static final int CARD_IMAGE_RESOURCE_ID = R.drawable.tis_q16_card;

    private boolean isBlockNumberValid(int i) {
        return i == 4;
    }

    private boolean isServiceCodeValid(int i) {
        return i == 13771;
    }

    Q16Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    String getMenuAppServiceId() {
        return "SV000024";
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        return transactionInfo.isReadingInfo() && isServiceCodeValid(transactionInfo.getServiceCode()) && isBlockNumberValid(transactionInfo.getBlockNumber());
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr, byte[] bArr2) {
        LogMgr.log(7, "[PFM]Q16Resolver#createNotificationInfoList()");
        ArrayList arrayList = new ArrayList();
        Resources resources = this.mContext.getResources();
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: q16");
            String string = resources.getString(R.string.tis_common_tap);
            Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), ICON_RESOURCE_ID);
            int i = TITLE_RESOURCE_ID;
            NotificationInfo notificationInfo = new NotificationInfo(resources.getString(i), string, R.mipmap.ic_osaifu_touka, bitmapDecodeResource, bArr, SERVICE_ID, entry.getKey().intValue());
            notificationInfo.setIntent(this.mContext, 3, 0, 0, i, CARD_IMAGE_RESOURCE_ID);
            notificationInfo.setNotificationJsonInfo(new NotificationJsonInfo("SV000024", NotificationInfoConst.TapType.TAPPED, 0, 0, getCurrencyCode()));
            notificationInfo.setCardNoJsonItem(getCardNo(entry.getValue().getBlockData(), 0, 16));
            LogInfo logInfo = new LogInfo();
            logInfo.setPaymentLogInfo(3, 0, 0);
            notificationInfo.setLogInfo(logInfo);
            arrayList.add(notificationInfo);
            LogMgr.log(6, "999 end data analysing: q16");
        }
        return arrayList;
    }
}
