package com.felicanetworks.tis.resolver;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.datatype.LogInfo;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.TransactionInfo;
import com.felicanetworks.tis.resolver.ResolverConst;
import com.felicanetworks.tis.util.ByteBufferMgr;
import com.felicanetworks.tis.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class I15Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int BLOCK_DATA_PAYMENT_LENGTH = 3;
    private static final int BLOCK_DATA_PAYMENT_OFFSET = 9;
    private static final int BLOCK_DATA_PAY_TYPE_LENGTH = 1;
    private static final int BLOCK_DATA_PAY_TYPE_OFFSET = 12;
    private static final int BLOCK_NUM = 0;
    private static final int PAY_TYPE_EARNINGS = 16;
    private static final String SERVICE_ID = "i15";
    private boolean mFirstRead = false;
    private boolean mSecondRead = false;

    private boolean isAccessModeValid(int i) {
        return i == 0;
    }

    private boolean isBlockNumberValid(int i) {
        return i == 0;
    }

    private boolean isWriteServiceCodeValid(int i) {
        return i == 16524;
    }

    I15Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        return isAccessModeValid(transactionInfo.getAccessMode()) && ((transactionInfo.isReadingInfo() && isReadServiceCodeValid(transactionInfo.getServiceCode())) || (transactionInfo.isWritingInfo() && isWriteServiceCodeValid(transactionInfo.getServiceCode())));
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        Resources resources = this.mContext.getResources();
        NotificationInfo notificationInfo = null;
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: i15");
            TransactionInfo value = entry.getValue();
            byte[] blockData = value.getBlockData();
            if (value.isReadingInfo()) {
                if (isReadServiceCodeValid(value.getServiceCode(), 0)) {
                    this.mFirstRead = true;
                } else if (this.mFirstRead && isReadServiceCodeValid(value.getServiceCode(), 1)) {
                    this.mSecondRead = true;
                    notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_i15), resources.getString(R.string.tis_common_tap), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_i15_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                    notificationInfo.setIntent(this.mContext, 3, 0, 0);
                    LogInfo logInfo = new LogInfo();
                    logInfo.setPaymentLogInfo(3, 0, 0);
                    notificationInfo.setLogInfo(logInfo);
                }
            } else if (this.mSecondRead && value.isWritingInfo() && isWriteServiceCodeValid(value.getServiceCode()) && isBlockNumberValid(value.getBlockNumber())) {
                if (getPayType(blockData) == 16) {
                    int payment = getPayment(blockData);
                    NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(R.string.tis_title_i15), createMessage(resources, payment), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_i15_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                    notificationInfo2.setIntent(this.mContext, 4, payment, 0);
                    LogInfo logInfo2 = new LogInfo();
                    logInfo2.setPaymentLogInfo(4, payment, 0);
                    logInfo2.addUsageData(value);
                    notificationInfo2.setLogInfo(logInfo2);
                    arrayList.add(notificationInfo2);
                }
                this.mFirstRead = false;
                this.mSecondRead = false;
                notificationInfo = null;
            }
            LogMgr.log(6, "999 end data analysing: i15");
        }
        if (notificationInfo != null) {
            arrayList.add(notificationInfo);
        }
        return arrayList;
    }

    private boolean isReadServiceCodeValid(int i) {
        return Arrays.asList(ResolverConst.I15.READ_SERVICE_CODE).contains(Integer.valueOf(i));
    }

    private boolean isReadServiceCodeValid(int i, int i2) {
        return i == ResolverConst.I15.READ_SERVICE_CODE[i2].intValue();
    }

    private int getPayment(byte[] bArr) {
        try {
            if (12 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return ByteBufferMgr.getBCDValueFromByteArray(true, Arrays.copyOfRange(bArr, 9, 12));
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private int getPayType(byte[] bArr) {
        try {
            if (13 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return ByteBufferMgr.getValueFromByteArray(true, Arrays.copyOfRange(bArr, 12, 13));
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private String createMessage(Resources resources, int i) {
        return String.format(resources.getString(R.string.tis_common_pay), String.format(Locale.JAPAN, "%,d", Integer.valueOf(i)));
    }
}
