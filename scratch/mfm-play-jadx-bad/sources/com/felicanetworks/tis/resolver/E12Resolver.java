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
    private static final int CARD_NO_BLOCK_DATA_LENGTH = 8;
    private static final int CARD_NO_BLOCK_DATA_OFFSET = 2;
    private static final int CARD_NO_BLOCK_NUM = 0;
    private static final String SERVICE_ID = "e12";
    private static final int TITLE_RESOURCE_ID = R.string.tis_title_e12;
    private static final int ICON_RESOURCE_ID = R.drawable.tis_e12_icon;
    private static final int CARD_IMAGE_RESOURCE_ID = R.drawable.tis_e12_card;

    private boolean isAccessModeValid(int i) {
        return i == 0;
    }

    private boolean isBlockNumberValid(int i) {
        return i == 0;
    }

    private boolean isCardNoBlockNumberValid(int i) {
        return i == 0;
    }

    private boolean isServiceCodeValid(int i) {
        return i == 5900;
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    protected boolean isUpdateAssetCache() {
        return true;
    }

    E12Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    String getMenuAppServiceId() {
        return "SV000013";
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
        LogMgr.log(7, "[PFM]E12Resolver#createNotificationInfoList()");
        ArrayList arrayList = new ArrayList();
        Resources resources = this.mContext.getResources();
        byte[] cardNo = null;
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: e12");
            TransactionInfo value = entry.getValue();
            byte[] blockData = value.getBlockData();
            if (value.isReadingInfo() && isCardNoServiceCodeValid(value.getServiceCode())) {
                cardNo = getCardNo(blockData, 2, 8);
            } else {
                NotificationInfoConst.TapType tapType = getTapType(blockData);
                int i = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[tapType.ordinal()];
                if (i == 1 || i == 2) {
                    int money = getMoney(blockData);
                    int balance = getBalance(blockData);
                    String strCreateMessage = createMessage(resources, tapType, money, balance);
                    Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), ICON_RESOURCE_ID);
                    int i2 = TITLE_RESOURCE_ID;
                    NotificationInfo notificationInfo = new NotificationInfo(resources.getString(i2), strCreateMessage, R.mipmap.ic_osaifu_touka, bitmapDecodeResource, bArr, SERVICE_ID, entry.getKey().intValue());
                    notificationInfo.setIntent(this.mContext, 2, money, balance, i2, CARD_IMAGE_RESOURCE_ID);
                    notificationInfo.setNotificationJsonInfo(new NotificationJsonInfo("SV000013", tapType, money, balance, getCurrencyCode()));
                    LogInfo logInfo = new LogInfo();
                    logInfo.setPaymentLogInfo(2, money, balance);
                    logInfo.addUsageData(value);
                    notificationInfo.setLogInfo(logInfo);
                    arrayList.add(notificationInfo);
                } else if (i == 3) {
                    int money2 = getMoney(blockData);
                    int balance2 = getBalance(blockData);
                    String strCreateMessage2 = createMessage(resources, tapType, money2, balance2);
                    Bitmap bitmapDecodeResource2 = BitmapFactory.decodeResource(this.mContext.getResources(), ICON_RESOURCE_ID);
                    int i3 = TITLE_RESOURCE_ID;
                    NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(i3), strCreateMessage2, R.mipmap.ic_osaifu_touka, bitmapDecodeResource2, bArr, SERVICE_ID, entry.getKey().intValue());
                    notificationInfo2.setIntent(this.mContext, 1, money2, balance2, i3, CARD_IMAGE_RESOURCE_ID);
                    notificationInfo2.setNotificationJsonInfo(new NotificationJsonInfo("SV000013", tapType, money2, balance2, getCurrencyCode()));
                    LogInfo logInfo2 = new LogInfo();
                    logInfo2.setPaymentLogInfo(1, money2, balance2);
                    logInfo2.addUsageData(value);
                    notificationInfo2.setLogInfo(logInfo2);
                    arrayList.add(notificationInfo2);
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((NotificationInfo) it.next()).setCardNoJsonItem(cardNo);
        }
        LogMgr.log(6, "999 end data analysing: e12");
        return arrayList;
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.resolver.E12Resolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType;

        static {
            int[] iArr = new int[NotificationInfoConst.TapType.values().length];
            $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType = iArr;
            try {
                iArr[NotificationInfoConst.TapType.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.GIFT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.PAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private boolean isCardNoServiceCodeValid(int i) {
        return Arrays.asList(ResolverConst.E12.CARD_NO_SERVICE_CODE).contains(Integer.valueOf(i));
    }

    private NotificationInfoConst.TapType getTapType(byte[] bArr) {
        try {
            if (1 > bArr.length) {
                throw new IllegalArgumentException();
            }
            int valueFromByteArray = ByteBufferMgr.getValueFromByteArray(false, Arrays.copyOfRange(bArr, 0, 1));
            if (valueFromByteArray == 2) {
                return NotificationInfoConst.TapType.CHARGE;
            }
            if (valueFromByteArray == 4) {
                return NotificationInfoConst.TapType.GIFT;
            }
            if (valueFromByteArray == 32) {
                return NotificationInfoConst.TapType.PAY;
            }
            return NotificationInfoConst.TapType.UNDEFINED;
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

    private String createMessage(Resources resources, NotificationInfoConst.TapType tapType, int i, int i2) {
        String str = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i));
        String str2 = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i2));
        int i3 = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[tapType.ordinal()];
        return (i3 == 1 || i3 == 2) ? String.format(resources.getString(R.string.tis_common_charge_balance), str, str2) : i3 != 3 ? "" : String.format(resources.getString(R.string.tis_common_pay_balance), str, str2);
    }
}
