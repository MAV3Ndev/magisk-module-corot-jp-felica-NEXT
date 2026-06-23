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
class W13Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int CHECK_DATA_LENGTH = 1;
    private static final int CHECK_DATA_MASK = 252;
    private static final int CHECK_DATA_OFFSET = 1;
    private static final int MONEY_SIGN_LENGTH = 1;
    private static final int MONEY_SIGN_OFFSET = 0;
    private static final String SERVICE_ID = "w13";
    private static final Integer[] BLOCK_NUM = {1, 3, 5};
    private static final Integer[] PAYMENT_CHECK_DATA = {4, 28, 32};
    private static final Integer[] CHARGE_CHECK_DATA = {12, 16, 48, 52};
    private static final int[][] PAYMENT_FIELD = {new int[]{7, 3, 8}, new int[]{8, 0, 8}, new int[]{9, 0, 5}};
    private static final int[][] CHARGE_FIELD = {new int[]{9, 5, 8}, new int[]{10, 0, 8}, new int[]{11, 0, 6}};
    private static final int[][] BALANCE_FIELD = {new int[]{5, 1, 8}, new int[]{6, 0, 8}, new int[]{7, 0, 3}};

    private enum FIELD_TYPE {
        CHARGE,
        PAYMENT,
        BALANCE,
        UNDEFINED
    }

    private enum PAY_TYPE {
        CHARGE,
        PAY,
        UNDEFINED
    }

    private boolean isAccessModeValid(int i) {
        return i == 0;
    }

    private boolean isServiceCodeValid(int i) {
        return i == 26632;
    }

    W13Resolver() {
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
            LogMgr.log(6, "000 start data analysing: w13");
            TransactionInfo value = entry.getValue();
            byte[] blockData = value.getBlockData();
            int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, Arrays.copyOfRange(blockData, 1, 2)) & CHECK_DATA_MASK;
            if (isPaymentCheckDataValid(valueFromByteArray)) {
                int fieldValue = getFieldValue(FIELD_TYPE.PAYMENT, blockData);
                int fieldValue2 = getFieldValue(FIELD_TYPE.BALANCE, blockData);
                NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_w13), createMessage(resources, PAY_TYPE.PAY, fieldValue, fieldValue2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_w13_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo.setIntent(this.mContext, 1, fieldValue, fieldValue2);
                LogInfo logInfo = new LogInfo();
                logInfo.setPaymentLogInfo(1, fieldValue, fieldValue2);
                logInfo.addUsageData(value);
                notificationInfo.setLogInfo(logInfo);
                arrayList.add(notificationInfo);
            } else if (isChargeCheckDataValid(valueFromByteArray)) {
                int fieldValue3 = getFieldValue(FIELD_TYPE.CHARGE, blockData);
                int fieldValue4 = getFieldValue(FIELD_TYPE.BALANCE, blockData);
                NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(R.string.tis_title_w13), createMessage(resources, PAY_TYPE.CHARGE, fieldValue3, fieldValue4), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_w13_icon), bArr, SERVICE_ID, entry.getKey().intValue());
                notificationInfo2.setIntent(this.mContext, 2, fieldValue3, fieldValue4);
                LogInfo logInfo2 = new LogInfo();
                logInfo2.setPaymentLogInfo(2, fieldValue3, fieldValue4);
                logInfo2.addUsageData(value);
                notificationInfo2.setLogInfo(logInfo2);
                arrayList.add(notificationInfo2);
            }
            LogMgr.log(6, "999 end data analysing: w13");
        }
        return arrayList;
    }

    private boolean isBlockNumberValid(int i) {
        return Arrays.asList(BLOCK_NUM).contains(Integer.valueOf(i));
    }

    private boolean isPaymentCheckDataValid(int i) {
        return Arrays.asList(PAYMENT_CHECK_DATA).contains(Integer.valueOf(i));
    }

    private boolean isChargeCheckDataValid(int i) {
        return Arrays.asList(CHARGE_CHECK_DATA).contains(Integer.valueOf(i));
    }

    private int getFieldValue(FIELD_TYPE field_type, byte[] bArr) {
        int[][] iArr;
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$FIELD_TYPE[field_type.ordinal()];
        if (i == 1) {
            iArr = PAYMENT_FIELD;
        } else if (i == 2) {
            iArr = CHARGE_FIELD;
        } else if (i == 3) {
            iArr = BALANCE_FIELD;
        } else {
            throw new IllegalArgumentException();
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (int[] iArr2 : iArr) {
                sb.append(String.format("%8s", Integer.toBinaryString(bArr[iArr2[0]] & 255)).replace(' ', '0').substring(iArr2[1], iArr2[2]));
            }
            String string = sb.toString();
            String strSubstring = string.substring(0, 1);
            int i2 = Integer.parseInt(string.substring(1), 2);
            return strSubstring.equals("0") ? i2 : -i2;
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private String createMessage(Resources resources, PAY_TYPE pay_type, int i, int i2) {
        String str = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i));
        String str2 = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i2));
        int i3 = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$PAY_TYPE[pay_type.ordinal()];
        return i3 != 1 ? i3 != 2 ? "" : String.format(resources.getString(R.string.tis_common_pay_balance), str, str2) : String.format(resources.getString(R.string.tis_common_charge_balance), str, str2);
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.resolver.W13Resolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$FIELD_TYPE;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$PAY_TYPE;

        static {
            int[] iArr = new int[PAY_TYPE.values().length];
            $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$PAY_TYPE = iArr;
            try {
                iArr[PAY_TYPE.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$PAY_TYPE[PAY_TYPE.PAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[FIELD_TYPE.values().length];
            $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$FIELD_TYPE = iArr2;
            try {
                iArr2[FIELD_TYPE.PAYMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$FIELD_TYPE[FIELD_TYPE.CHARGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$FIELD_TYPE[FIELD_TYPE.BALANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }
}
