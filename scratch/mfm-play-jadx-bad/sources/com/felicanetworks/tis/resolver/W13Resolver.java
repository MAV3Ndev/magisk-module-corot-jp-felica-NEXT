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
import com.felicanetworks.tis.resolver.ResolverConst;
import com.felicanetworks.tis.util.ByteBufferMgr;
import com.felicanetworks.tis.util.LogMgr;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
class W13Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int CARD_NO_BLOCK_DATA_LENGTH = 8;
    private static final int CARD_NO_BLOCK_DATA_PART_LENGTH = 4;
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
    private static final Integer[] CARD_NO_BLOCK_NUM = {0, 1};
    private static final int[][] CARD_NO_BLOCK_DATA = {new int[]{12, 4}, new int[]{0, 4}};
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_w13;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_w13_icon;
    private static final int CARD_IMAGE_RESOURCE_ID = R.drawable.tis_w13_card;

    private enum FIELD_TYPE {
        CHARGE,
        PAYMENT,
        BALANCE
    }

    private boolean isAccessModeValid(int i) {
        return i == 0;
    }

    private boolean isServiceCodeValid(int i) {
        return i == 26632;
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    protected boolean isUpdateAssetCache() {
        return true;
    }

    W13Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    String getMenuAppServiceId() {
        return "SV000011";
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    boolean isMySp(TransactionInfo transactionInfo) {
        if (transactionInfo.isWritingInfo() && isServiceCodeValid(transactionInfo.getServiceCode()) && isAccessModeValid(transactionInfo.getAccessMode()) && isBlockNumberValid(transactionInfo.getBlockNumber())) {
            return true;
        }
        return transactionInfo.isReadingInfo() && isCardNoServiceCodeValid(transactionInfo.getServiceCode()) && isCardNoBlockNumberValid(transactionInfo.getBlockNumber());
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    List<NotificationInfo> createNotificationInfoList(byte[] bArr, byte[] bArr2) {
        LogMgr.log(7, "[PFM]W13Resolver#createNotificationInfoList()");
        ArrayList arrayList = new ArrayList();
        Resources resources = this.mContext.getResources();
        byte[][] bArr3 = new byte[2][];
        bArr3[0] = null;
        bArr3[1] = null;
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: w13");
            TransactionInfo value = entry.getValue();
            byte[] blockData = value.getBlockData();
            if (value.isReadingInfo() && isCardNoServiceCodeValid(value.getServiceCode())) {
                int blockNumber = value.getBlockNumber();
                int[] iArr = CARD_NO_BLOCK_DATA[blockNumber];
                bArr3[blockNumber] = getCardNo(blockData, iArr[0], iArr[1]);
            } else {
                int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, Arrays.copyOfRange(blockData, 1, 2)) & CHECK_DATA_MASK;
                if (isPaymentCheckDataValid(valueFromByteArray)) {
                    int fieldValue = getFieldValue(FIELD_TYPE.PAYMENT, blockData);
                    int fieldValue2 = getFieldValue(FIELD_TYPE.BALANCE, blockData);
                    String strCreateMessage = createMessage(resources, NotificationInfoConst.TapType.PAY, fieldValue, fieldValue2);
                    Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), ICON_RESOURCE_ID);
                    int i = TITLE_RESOURCE_ID;
                    NotificationInfo notificationInfo = new NotificationInfo(resources.getString(i), strCreateMessage, R.mipmap.ic_osaifu_touka, bitmapDecodeResource, bArr, SERVICE_ID, entry.getKey().intValue());
                    notificationInfo.setIntent(this.mContext, 1, fieldValue, fieldValue2, i, CARD_IMAGE_RESOURCE_ID);
                    notificationInfo.setNotificationJsonInfo(new NotificationJsonInfo("SV000011", NotificationInfoConst.TapType.PAY, fieldValue, fieldValue2, getCurrencyCode()));
                    LogInfo logInfo = new LogInfo();
                    logInfo.setPaymentLogInfo(1, fieldValue, fieldValue2);
                    logInfo.addUsageData(value);
                    notificationInfo.setLogInfo(logInfo);
                    arrayList.add(notificationInfo);
                } else if (isChargeCheckDataValid(valueFromByteArray)) {
                    int fieldValue3 = getFieldValue(FIELD_TYPE.CHARGE, blockData);
                    int fieldValue4 = getFieldValue(FIELD_TYPE.BALANCE, blockData);
                    String strCreateMessage2 = createMessage(resources, NotificationInfoConst.TapType.CHARGE, fieldValue3, fieldValue4);
                    Bitmap bitmapDecodeResource2 = BitmapFactory.decodeResource(this.mContext.getResources(), ICON_RESOURCE_ID);
                    int i2 = TITLE_RESOURCE_ID;
                    NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(i2), strCreateMessage2, R.mipmap.ic_osaifu_touka, bitmapDecodeResource2, bArr, SERVICE_ID, entry.getKey().intValue());
                    notificationInfo2.setIntent(this.mContext, 2, fieldValue3, fieldValue4, i2, CARD_IMAGE_RESOURCE_ID);
                    notificationInfo2.setNotificationJsonInfo(new NotificationJsonInfo("SV000011", NotificationInfoConst.TapType.CHARGE, fieldValue3, fieldValue4, getCurrencyCode()));
                    LogInfo logInfo2 = new LogInfo();
                    logInfo2.setPaymentLogInfo(2, fieldValue3, fieldValue4);
                    logInfo2.addUsageData(value);
                    notificationInfo2.setLogInfo(logInfo2);
                    arrayList.add(notificationInfo2);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((NotificationInfo) it.next()).setCardNoJsonItem(concatCardNoInfo(bArr3));
        }
        LogMgr.log(6, "999 end data analysing: w13");
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

    private boolean isCardNoServiceCodeValid(int i) {
        return Arrays.asList(ResolverConst.W13.CARD_NO_SERVICE_CODE).contains(Integer.valueOf(i));
    }

    private boolean isCardNoBlockNumberValid(int i) {
        return Arrays.asList(CARD_NO_BLOCK_NUM).contains(Integer.valueOf(i));
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

    private String createMessage(Resources resources, NotificationInfoConst.TapType tapType, int i, int i2) {
        String str = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i));
        String str2 = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i2));
        int i3 = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[tapType.ordinal()];
        return i3 != 1 ? i3 != 2 ? "" : String.format(resources.getString(R.string.tis_common_pay_balance), str, str2) : String.format(resources.getString(R.string.tis_common_charge_balance), str, str2);
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.resolver.W13Resolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$W13Resolver$FIELD_TYPE;

        static {
            int[] iArr = new int[NotificationInfoConst.TapType.values().length];
            $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType = iArr;
            try {
                iArr[NotificationInfoConst.TapType.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.PAY.ordinal()] = 2;
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

    private byte[] concatCardNoInfo(byte[][] bArr) {
        if (bArr == null) {
            LogMgr.log(6, "700 cardNoInfo is null.");
            return null;
        }
        byte[] bArr2 = bArr[0];
        if (bArr2 == null) {
            LogMgr.log(6, "701 cardNoInfo[0] is null.");
            return null;
        }
        if (bArr[1] == null) {
            LogMgr.log(6, "702 cardNoInfo[1] is null.");
            return null;
        }
        byte[] bArr3 = new byte[8];
        try {
            System.arraycopy(bArr2, 0, bArr3, 0, 4);
            System.arraycopy(bArr[1], 0, bArr3, 4, 4);
            return bArr3;
        } catch (Exception e) {
            LogMgr.log(6, "703 Invalid byte data. " + e);
            return null;
        }
    }
}
