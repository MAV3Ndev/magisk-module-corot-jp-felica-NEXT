package com.felicanetworks.tis.resolver;

import android.content.res.Resources;
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
import com.felicanetworks.tis.util.StringUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
abstract class TrafficServiceResolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int BLOCK_DATA_MONEY_LENGTH = 3;
    private static final int BLOCK_DATA_MONEY_OFFSET = 10;
    private static final int BLOCK_IN_OUT_INFO_LENGTH = 1;
    private static final int BLOCK_IN_OUT_INFO_OFFSET = 0;
    private static final int BLOCK_NUM = 0;
    private static final int IN_OUT_INFO_MASK = 128;
    private final int mCardImageResourceId;
    private final int mIconResourceId;
    private final String mServiceId;
    private final String mServiceIdJson;
    private final int mTitleResourceId;

    private enum IN_OUT_TYPE {
        IN,
        OUT,
        NONE
    }

    private boolean isAccessModeValid(int i) {
        return i == 0;
    }

    private boolean isBlockNumberValid(int i) {
        return i == 0;
    }

    private boolean isSfWriteServiceCodeValid(int i) {
        return i == 2316;
    }

    private boolean isTicketWriteServiceCodeValid(int i) {
        return i == 4236;
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    protected boolean isUpdateAssetCache() {
        return true;
    }

    public TrafficServiceResolver(String str, String str2, int i, int i2, int i3) {
        this.mServiceId = str;
        this.mServiceIdJson = str2;
        this.mTitleResourceId = i;
        this.mIconResourceId = i2;
        this.mCardImageResourceId = i3;
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    protected boolean isMySp(TransactionInfo transactionInfo) {
        int serviceCode = transactionInfo.getServiceCode();
        if (transactionInfo.isReadingInfo() && isSfReadServiceCodeValid(serviceCode)) {
            return true;
        }
        if (transactionInfo.isWritingInfo()) {
            return isSfWriteServiceCodeValid(serviceCode) || isTicketWriteServiceCodeValid(serviceCode);
        }
        return false;
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    protected List<NotificationInfo> createNotificationInfoList(byte[] bArr, byte[] bArr2) {
        LogMgr.log(7, "[PFM]TrafficServiceResolver#createNotificationInfoList()");
        ArrayList arrayList = new ArrayList();
        LogInfo logInfo = new LogInfo();
        Resources resources = this.mContext.getResources();
        IN_OUT_TYPE inOutType = IN_OUT_TYPE.NONE;
        int money = -1;
        int money2 = -1;
        int i = -1;
        int money3 = -1;
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: " + this.mServiceId);
            TransactionInfo value = entry.getValue();
            if (isBlockNumberValid(value.getBlockNumber()) && isAccessModeValid(value.getAccessMode())) {
                int serviceCode = value.getServiceCode();
                if (value.isReadingInfo() && isSfReadServiceCodeValid(serviceCode)) {
                    money2 = getMoney(value.getBlockData());
                    logInfo.addUsageData(value);
                } else if (value.isWritingInfo()) {
                    if (isSfWriteServiceCodeValid(serviceCode)) {
                        byte[] blockData = value.getBlockData();
                        if (money < 0) {
                            money = getMoney(blockData);
                        } else {
                            money3 = getMoney(blockData);
                        }
                        logInfo.addUsageData(value);
                        i = money2;
                    } else if (isTicketWriteServiceCodeValid(serviceCode)) {
                        inOutType = getInOutType(value.getBlockData());
                        logInfo.addUsageData(value);
                    }
                }
            }
            LogMgr.log(6, "999 end data analysing: " + this.mServiceId);
        }
        if (money2 == -1 || i != -1) {
            money2 = i;
        }
        NotificationInfo notificationInfoCreateInNotificationInfo = null;
        String uTF8String = bArr2 != null ? StringUtil.toUTF8String(bArr2) : null;
        int i2 = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$TrafficServiceResolver$IN_OUT_TYPE[inOutType.ordinal()];
        if (i2 == 1) {
            notificationInfoCreateInNotificationInfo = createInNotificationInfo(money2, money, money3, resources, bArr, uTF8String, logInfo);
        } else if (i2 == 2) {
            notificationInfoCreateInNotificationInfo = createOutNotificationInfo(money2, money, money3, resources, bArr, uTF8String, logInfo);
        } else if (i2 == 3) {
            notificationInfoCreateInNotificationInfo = createNotificationInfo(money2, money, money3, resources, bArr, uTF8String, logInfo);
        }
        if (notificationInfoCreateInNotificationInfo != null) {
            notificationInfoCreateInNotificationInfo.setCidJsonItem(bArr2);
            arrayList.add(notificationInfoCreateInNotificationInfo);
        }
        return arrayList;
    }

    protected NotificationInfo createInNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, String str, LogInfo logInfo) {
        int i4;
        int i5;
        int i6;
        int i7 = 7;
        LogMgr.log(7, "[PFM]TrafficServiceResolver#createInNotificationInfo()");
        NotificationInfoConst.TapType tapType = NotificationInfoConst.TapType.UNDEFINED;
        int i8 = 0;
        if (i >= 0) {
            if (i2 < 0) {
                tapType = NotificationInfoConst.TapType.IN_ONLY;
                i7 = 5;
                i6 = i;
            } else if (i3 >= 0) {
                int i9 = i2 - i3;
                if (i9 > 0) {
                    tapType = NotificationInfoConst.TapType.IN_PAY;
                    i8 = i3;
                } else if (i9 < 0) {
                    tapType = NotificationInfoConst.TapType.IN_CHARGE;
                    i8 = i3;
                    i9 = -i9;
                    i7 = 8;
                } else {
                    i7 = 0;
                    i9 = 0;
                }
                i4 = i7;
                i6 = i8;
                i5 = i9;
            } else {
                int i10 = i - i2;
                if (i10 > 0) {
                    i8 = i10;
                    tapType = NotificationInfoConst.TapType.IN_PAY;
                } else if (i10 < 0) {
                    i8 = -i10;
                    tapType = NotificationInfoConst.TapType.IN_CHARGE;
                    i7 = 8;
                } else {
                    tapType = NotificationInfoConst.TapType.IN_NO_PAY;
                    i7 = 6;
                }
                i6 = i2;
            }
            i4 = i7;
            i5 = i8;
        } else {
            i4 = 0;
            i5 = 0;
            i6 = 0;
        }
        if (tapType == NotificationInfoConst.TapType.UNDEFINED) {
            return null;
        }
        NotificationInfo notificationInfo = new NotificationInfo(resources.getString(this.mTitleResourceId), createMessage(resources, tapType, i5, i6), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), this.mIconResourceId), bArr, this.mServiceId, 0);
        notificationInfo.setIntent(this.mContext, i4, i5, i6, this.mTitleResourceId, this.mCardImageResourceId, true, str);
        notificationInfo.setNotificationJsonInfo(new NotificationJsonInfo(this.mServiceIdJson, tapType, i5, i6, getCurrencyCode()));
        logInfo.setPaymentLogInfo(i4, i5, i6);
        notificationInfo.setLogInfo(logInfo);
        return notificationInfo;
    }

    protected NotificationInfo createOutNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, String str, LogInfo logInfo) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        LogMgr.log(7, "[PFM]TrafficServiceResolver#createOutNotificationInfo()");
        NotificationInfoConst.TapType tapType = NotificationInfoConst.TapType.UNDEFINED;
        int i10 = 0;
        if (i < 0) {
            i4 = 0;
            i5 = 0;
            i6 = 0;
        } else if (i2 >= 0) {
            int i11 = 10;
            if (i3 >= 0) {
                int i12 = i2 - i3;
                if (i12 > 0) {
                    tapType = NotificationInfoConst.TapType.OUT_PAY;
                    i10 = i3;
                } else {
                    i12 = 0;
                    i11 = 0;
                }
                i6 = i10;
                i5 = i12;
                i4 = i11;
            } else {
                int i13 = i - i2;
                if (i13 > 0) {
                    i8 = i13;
                    tapType = NotificationInfoConst.TapType.OUT_PAY;
                    i9 = 10;
                } else {
                    if (i13 < 0) {
                        i7 = 11;
                        i8 = -i13;
                        tapType = NotificationInfoConst.TapType.OUT_CHARGE;
                    } else {
                        tapType = NotificationInfoConst.TapType.OUT_NO_PAY;
                        i7 = 9;
                        i8 = 0;
                    }
                    i9 = i7;
                }
                i6 = i2;
                i4 = i9;
                i5 = i8;
            }
        } else {
            tapType = NotificationInfoConst.TapType.OUT_NO_PAY;
            i6 = i;
            i5 = 0;
            i4 = 9;
        }
        if (tapType == NotificationInfoConst.TapType.UNDEFINED) {
            return null;
        }
        NotificationInfo notificationInfo = new NotificationInfo(resources.getString(this.mTitleResourceId), createMessage(resources, tapType, i5, i6), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), this.mIconResourceId), bArr, this.mServiceId, 0);
        notificationInfo.setIntent(this.mContext, i4, i5, i6, this.mTitleResourceId, this.mCardImageResourceId, true, str);
        notificationInfo.setNotificationJsonInfo(new NotificationJsonInfo(this.mServiceIdJson, tapType, i5, i6, getCurrencyCode()));
        logInfo.setPaymentLogInfo(i4, i5, i6);
        notificationInfo.setLogInfo(logInfo);
        return notificationInfo;
    }

    protected NotificationInfo createNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, String str, LogInfo logInfo) {
        LogMgr.log(7, "[PFM]TrafficServiceResolver#createNotificationInfo()");
        if (i < 0 || i2 < 0 || i3 >= 0) {
            return null;
        }
        int i4 = i - i2;
        if (i4 > 0) {
            NotificationInfo notificationInfo = new NotificationInfo(resources.getString(this.mTitleResourceId), createMessage(resources, NotificationInfoConst.TapType.PAY, i4, i2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), this.mIconResourceId), bArr, this.mServiceId, 0);
            notificationInfo.setIntent(this.mContext, 1, i4, i2, this.mTitleResourceId, this.mCardImageResourceId, true, str);
            notificationInfo.setNotificationJsonInfo(new NotificationJsonInfo(this.mServiceIdJson, NotificationInfoConst.TapType.PAY, i4, i2, getCurrencyCode()));
            logInfo.setPaymentLogInfo(1, i4, i2);
            notificationInfo.setLogInfo(logInfo);
            return notificationInfo;
        }
        if (i4 >= 0) {
            return null;
        }
        int i5 = -i4;
        NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(this.mTitleResourceId), createMessage(resources, NotificationInfoConst.TapType.CHARGE, i5, i2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), this.mIconResourceId), bArr, this.mServiceId, 0);
        notificationInfo2.setIntent(this.mContext, 2, i5, i2, this.mTitleResourceId, this.mCardImageResourceId, true, str);
        notificationInfo2.setNotificationJsonInfo(new NotificationJsonInfo(this.mServiceIdJson, NotificationInfoConst.TapType.CHARGE, i5, i2, getCurrencyCode()));
        logInfo.setPaymentLogInfo(2, i5, i2);
        notificationInfo2.setLogInfo(logInfo);
        return notificationInfo2;
    }

    private boolean isSfReadServiceCodeValid(int i) {
        return Arrays.asList(ResolverConst.TrafficService.SF_READ_SERVICE_CODE).contains(Integer.valueOf(i));
    }

    private int getMoney(byte[] bArr) {
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 10, 13);
            if (13 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return ByteBufferMgr.getValueFromByteArray(true, bArrCopyOfRange);
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    private IN_OUT_TYPE getInOutType(byte[] bArr) {
        try {
            byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, 1);
            if (1 > bArr.length) {
                throw new IllegalArgumentException();
            }
            if ((ByteBufferMgr.getValueFromByteArray(false, bArrCopyOfRange) & 128) == 128) {
                return IN_OUT_TYPE.IN;
            }
            return IN_OUT_TYPE.OUT;
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    protected String createMessage(Resources resources, NotificationInfoConst.TapType tapType, int i, int i2) {
        String str = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i));
        String str2 = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i2));
        switch (AnonymousClass1.$SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[tapType.ordinal()]) {
            case 1:
            case 2:
            case 3:
                return String.format(resources.getString(R.string.tis_common_no_pay_balance), str2);
            case 4:
            case 5:
            case 6:
                return String.format(resources.getString(R.string.tis_common_minus_balance), str, str2);
            case 7:
            case 8:
            case 9:
                return String.format(resources.getString(R.string.tis_common_plus_balance), str, str2);
            default:
                return "";
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.resolver.TrafficServiceResolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$TrafficServiceResolver$IN_OUT_TYPE;

        static {
            int[] iArr = new int[NotificationInfoConst.TapType.values().length];
            $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType = iArr;
            try {
                iArr[NotificationInfoConst.TapType.IN_ONLY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.IN_NO_PAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.OUT_NO_PAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.PAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.IN_PAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.OUT_PAY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.CHARGE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.IN_CHARGE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$datatype$NotificationInfoConst$TapType[NotificationInfoConst.TapType.OUT_CHARGE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            int[] iArr2 = new int[IN_OUT_TYPE.values().length];
            $SwitchMap$com$felicanetworks$tis$resolver$TrafficServiceResolver$IN_OUT_TYPE = iArr2;
            try {
                iArr2[IN_OUT_TYPE.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$TrafficServiceResolver$IN_OUT_TYPE[IN_OUT_TYPE.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$TrafficServiceResolver$IN_OUT_TYPE[IN_OUT_TYPE.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }
}
