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
class N14Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int BLOCK_DATA_BALANCE_LENGTH = 4;
    private static final int BLOCK_DATA_BALANCE_OFFSET = 5;
    private static final int BLOCK_DATA_MONEY_LENGTH = 4;
    private static final int BLOCK_DATA_MONEY_OFFSET = 1;
    private static final int BLOCK_DATA_PAY_TYPE_LENGTH = 1;
    private static final int BLOCK_DATA_PAY_TYPE_OFFSET = 0;
    private static final int BLOCK_NUM = 0;
    private static final Integer[] PAY_TYPE_CHARGE = {111, 112, 131};
    private static final int PAY_TYPE_PAY = 71;
    private static final String SERVICE_ID = "n14";

    private enum PAY_TYPE {
        CHARGE,
        PAY,
        UNDEFINED
    }

    private boolean isAccessModeValid(int i) {
        return i == 0;
    }

    private boolean isBlockNumberValid(int i) {
        return i == 0;
    }

    private boolean isServiceCodeValid(int i) {
        return i == 22092;
    }

    N14Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        return transactionInfo.isWritingInfo() && isServiceCodeValid(transactionInfo.getServiceCode()) && isAccessModeValid(transactionInfo.getAccessMode()) && isBlockNumberValid(transactionInfo.getBlockNumber());
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        Resources resources = this.mContext.getResources();
        NotificationInfo notificationInfo = null;
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: n14");
            TransactionInfo value = entry.getValue();
            if (value.isStartOfCommand() && notificationInfo != null) {
                arrayList.add(notificationInfo);
                notificationInfo = null;
            }
            byte[] blockData = value.getBlockData();
            int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, blockData, 0, 1);
            if (valueFromByteArray == 71) {
                int money = getMoney(blockData);
                int balance = getBalance(blockData);
                NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(R.string.tis_title_n14), createMessage(resources, PAY_TYPE.PAY, money, balance), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_n14_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo2.setIntent(this.mContext, 1, money, balance);
                LogInfo logInfo = new LogInfo();
                logInfo.setPaymentLogInfo(1, money, balance);
                logInfo.addUsageData(value);
                notificationInfo2.setLogInfo(logInfo);
                arrayList.add(notificationInfo2);
                notificationInfo = null;
            } else if (Arrays.asList(PAY_TYPE_CHARGE).contains(Integer.valueOf(valueFromByteArray))) {
                int money2 = getMoney(blockData);
                int balance2 = getBalance(blockData);
                NotificationInfo notificationInfo3 = new NotificationInfo(resources.getString(R.string.tis_title_n14), createMessage(resources, PAY_TYPE.CHARGE, money2, balance2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_n14_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo3.setIntent(this.mContext, 2, money2, balance2);
                LogInfo logInfo2 = new LogInfo();
                logInfo2.setPaymentLogInfo(2, money2, balance2);
                logInfo2.addUsageData(value);
                notificationInfo3.setLogInfo(logInfo2);
                notificationInfo = notificationInfo3;
            }
            LogMgr.log(6, "999 end data analysing: n14");
        }
        if (notificationInfo != null) {
            arrayList.add(notificationInfo);
        }
        return arrayList;
    }

    private int getMoney(byte[] bArr) {
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 1, 5);
            if (5 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return ByteBufferMgr.getValueFromByteArray(false, bArrCopyOfRange);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private int getBalance(byte[] bArr) {
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 5, 9);
            if (9 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return ByteBufferMgr.getValueFromByteArray(false, bArrCopyOfRange);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private String createMessage(Resources resources, PAY_TYPE pay_type, int i, int i2) {
        String str = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i));
        String str2 = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i2));
        int i3 = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$N14Resolver$PAY_TYPE[pay_type.ordinal()];
        return i3 != 1 ? i3 != 2 ? "" : String.format(resources.getString(R.string.tis_common_pay_balance), str, str2) : String.format(resources.getString(R.string.tis_common_charge_balance), str, str2);
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.resolver.N14Resolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$N14Resolver$PAY_TYPE;

        static {
            int[] iArr = new int[PAY_TYPE.values().length];
            $SwitchMap$com$felicanetworks$tis$resolver$N14Resolver$PAY_TYPE = iArr;
            try {
                iArr[PAY_TYPE.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$N14Resolver$PAY_TYPE[PAY_TYPE.PAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
