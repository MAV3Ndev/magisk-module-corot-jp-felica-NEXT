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
class E12Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int BLOCK_DATA_BALANCE_LENGTH = 4;
    private static final int BLOCK_DATA_BALANCE_OFFSET = 12;
    private static final int BLOCK_DATA_CHARGE = 2;
    private static final int BLOCK_DATA_GIFT = 4;
    private static final int BLOCK_DATA_MONEY_LENGTH = 4;
    private static final int BLOCK_DATA_MONEY_OFFSET = 8;
    private static final int BLOCK_DATA_PAY = 32;
    private static final int BLOCK_DATA_PAY_TYPE_LENGTH = 1;
    private static final int BLOCK_DATA_PAY_TYPE_OFFSET = 0;
    private static final int BLOCK_NUM = 0;
    private static final String SERVICE_ID = "e12";

    private enum PAY_TYPE {
        CHARGE,
        GIFT,
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
        return i == 5900;
    }

    E12Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        return transactionInfo.isWritingInfo() && isServiceCodeValid(transactionInfo.getServiceCode()) && isAccessModeValid(transactionInfo.getAccessMode()) && isBlockNumberValid(transactionInfo.getBlockNumber());
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr) {
        ArrayList arrayList = new ArrayList();
        Resources resources = this.mContext.getResources();
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: e12");
            TransactionInfo value = entry.getValue();
            byte[] blockData = value.getBlockData();
            int i = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$E12Resolver$PAY_TYPE[getPayType(blockData).ordinal()];
            if (i == 1 || i == 2) {
                int money = getMoney(blockData);
                int balance = getBalance(blockData);
                NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_e12), createMessage(resources, PAY_TYPE.CHARGE, money, balance), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_e12_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo.setIntent(this.mContext, 2, money, balance);
                LogInfo logInfo = new LogInfo();
                logInfo.setPaymentLogInfo(2, money, balance);
                logInfo.addUsageData(value);
                notificationInfo.setLogInfo(logInfo);
                arrayList.add(notificationInfo);
            } else if (i == 3) {
                int money2 = getMoney(blockData);
                int balance2 = getBalance(blockData);
                NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(R.string.tis_title_e12), createMessage(resources, PAY_TYPE.PAY, money2, balance2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_e12_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo2.setIntent(this.mContext, 1, money2, balance2);
                LogInfo logInfo2 = new LogInfo();
                logInfo2.setPaymentLogInfo(1, money2, balance2);
                logInfo2.addUsageData(value);
                notificationInfo2.setLogInfo(logInfo2);
                arrayList.add(notificationInfo2);
            }
            LogMgr.log(6, "999 end data analysing: e12");
        }
        return arrayList;
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.resolver.E12Resolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$E12Resolver$PAY_TYPE;

        static {
            int[] iArr = new int[PAY_TYPE.values().length];
            $SwitchMap$com$felicanetworks$tis$resolver$E12Resolver$PAY_TYPE = iArr;
            try {
                iArr[PAY_TYPE.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$E12Resolver$PAY_TYPE[PAY_TYPE.GIFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$E12Resolver$PAY_TYPE[PAY_TYPE.PAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private PAY_TYPE getPayType(byte[] bArr) {
        try {
            if (1 > bArr.length) {
                throw new IllegalArgumentException();
            }
            int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, Arrays.copyOfRange(bArr, 0, 1));
            if (valueFromByteArray == 2) {
                return PAY_TYPE.CHARGE;
            }
            if (valueFromByteArray == 4) {
                return PAY_TYPE.GIFT;
            }
            if (valueFromByteArray == 32) {
                return PAY_TYPE.PAY;
            }
            return PAY_TYPE.UNDEFINED;
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private int getMoney(byte[] bArr) {
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 8, 12);
            if (12 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return ByteBufferMgr.getValueFromByteArray(false, bArrCopyOfRange);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private int getBalance(byte[] bArr) {
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 12, 16);
            if (16 > bArr.length) {
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
        int i3 = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$E12Resolver$PAY_TYPE[pay_type.ordinal()];
        return (i3 == 1 || i3 == 2) ? String.format(resources.getString(R.string.tis_common_charge_balance), str, str2) : i3 != 3 ? "" : String.format(resources.getString(R.string.tis_common_pay_balance), str, str2);
    }
}
