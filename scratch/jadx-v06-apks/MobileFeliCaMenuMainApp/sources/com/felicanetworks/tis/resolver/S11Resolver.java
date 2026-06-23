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
class S11Resolver extends Resolver {
    private static final int ACCESS_MODE = 0;
    private static final int BLOCK_DATA_MONEY_LENGTH = 3;
    private static final int BLOCK_DATA_MONEY_OFFSET = 10;
    private static final int BLOCK_IN_OUT_INFO_LENGTH = 1;
    private static final int BLOCK_IN_OUT_INFO_OFFSET = 0;
    private static final int BLOCK_NUM = 0;
    private static final int IN_OUT_INFO_MASK = 128;
    private static final String SERVICE_ID = "s11";

    private enum IN_OUT_TYPE {
        IN,
        OUT,
        NONE,
        UNDEFINED
    }

    protected enum PAY_TYPE {
        CHARGE,
        PAY,
        IN_ONLY,
        IN_NO_PAY,
        IN_PAY,
        IN_CHARGE,
        OUT_NO_PAY,
        OUT_PAY,
        OUT_CHARGE,
        UNDEFINED
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

    S11Resolver() {
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    protected boolean isMySp(TransactionInfo transactionInfo) {
        int serviceCode = transactionInfo.getServiceCode();
        return (transactionInfo.isReadingInfo() && isSfReadServiceCodeValid(serviceCode)) || (transactionInfo.isWritingInfo() && (isSfWriteServiceCodeValid(serviceCode) || isTicketWriteServiceCodeValid(serviceCode)));
    }

    @Override // com.felicanetworks.tis.resolver.Resolver
    protected List<NotificationInfo> createNotificationInfoList(byte[] bArr) {
        NotificationInfo notificationInfoCreateInNotificationInfo;
        ArrayList arrayList = new ArrayList();
        LogInfo logInfo = new LogInfo();
        Resources resources = this.mContext.getResources();
        IN_OUT_TYPE inOutType = IN_OUT_TYPE.NONE;
        int money = -1;
        int money2 = -1;
        int money3 = -1;
        for (Map.Entry<Integer, TransactionInfo> entry : this.mTransactionInfoMap.entrySet()) {
            LogMgr.log(6, "000 start data analysing: s11");
            TransactionInfo value = entry.getValue();
            if (isBlockNumberValid(value.getBlockNumber()) && isAccessModeValid(value.getAccessMode())) {
                int serviceCode = value.getServiceCode();
                if (value.isReadingInfo() && isSfReadServiceCodeValid(serviceCode)) {
                    money = getMoney(value.getBlockData());
                    logInfo.addUsageData(value);
                } else if (value.isWritingInfo()) {
                    if (isSfWriteServiceCodeValid(serviceCode)) {
                        byte[] blockData = value.getBlockData();
                        if (money2 < 0) {
                            money2 = getMoney(blockData);
                        } else {
                            money3 = getMoney(blockData);
                        }
                        logInfo.addUsageData(value);
                    } else if (isTicketWriteServiceCodeValid(serviceCode)) {
                        inOutType = getInOutType(value.getBlockData());
                        logInfo.addUsageData(value);
                    }
                }
            }
            LogMgr.log(6, "999 end data analysing: s11");
        }
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$IN_OUT_TYPE[inOutType.ordinal()];
        if (i == 1) {
            notificationInfoCreateInNotificationInfo = createInNotificationInfo(money, money2, money3, resources, bArr, logInfo);
        } else if (i == 2) {
            notificationInfoCreateInNotificationInfo = createOutNotificationInfo(money, money2, money3, resources, bArr, logInfo);
        } else {
            notificationInfoCreateInNotificationInfo = i != 3 ? null : createNotificationInfo(money, money2, money3, resources, bArr, logInfo);
        }
        if (notificationInfoCreateInNotificationInfo != null) {
            arrayList.add(notificationInfoCreateInNotificationInfo);
        }
        return arrayList;
    }

    protected NotificationInfo createInNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, LogInfo logInfo) {
        String strCreateMessage;
        int i4;
        int i5;
        String strCreateMessage2;
        int i6;
        String strCreateMessage3;
        int i7 = i;
        int i8 = i3;
        int i9 = 0;
        if (i7 < 0) {
            strCreateMessage = null;
            i7 = 0;
            i4 = 0;
            i5 = 0;
        } else if (i2 < 0) {
            i5 = 5;
            strCreateMessage = createMessage(resources, PAY_TYPE.IN_ONLY, 0, i7);
            i4 = 0;
        } else if (i8 >= 0) {
            int i10 = i2 - i8;
            if (i10 > 0) {
                strCreateMessage3 = createMessage(resources, PAY_TYPE.IN_PAY, i10, i8);
                i9 = 7;
            } else if (i10 < 0) {
                i10 = -i10;
                strCreateMessage3 = createMessage(resources, PAY_TYPE.IN_CHARGE, i10, i8);
                i9 = 8;
            } else {
                strCreateMessage3 = null;
                i10 = 0;
                i8 = 0;
            }
            strCreateMessage = strCreateMessage3;
            i4 = i10;
            i7 = i8;
            i5 = i9;
        } else {
            int i11 = i7 - i2;
            if (i11 > 0) {
                strCreateMessage2 = createMessage(resources, PAY_TYPE.IN_PAY, i11, i2);
                i6 = 7;
            } else if (i11 < 0) {
                i11 = -i11;
                strCreateMessage2 = createMessage(resources, PAY_TYPE.IN_CHARGE, i11, i2);
                i6 = 8;
            } else {
                strCreateMessage2 = createMessage(resources, PAY_TYPE.IN_NO_PAY, i11, i2);
                i6 = 6;
            }
            strCreateMessage = strCreateMessage2;
            i5 = i6;
            i4 = i11;
            i7 = i2;
        }
        if (strCreateMessage == null) {
            return null;
        }
        NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_s11), strCreateMessage, R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_s11_icon), bArr, SERVICE_ID, 0);
        notificationInfo.setIntent(this.mContext, i5, i4, i7);
        logInfo.setPaymentLogInfo(i5, i4, i7);
        notificationInfo.setLogInfo(logInfo);
        return notificationInfo;
    }

    protected NotificationInfo createOutNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, LogInfo logInfo) {
        String strCreateMessage;
        int i4;
        String strCreateMessage2;
        String strCreateMessage3;
        int i5 = i;
        int i6 = i3;
        int i7 = 9;
        int i8 = 0;
        if (i5 < 0) {
            strCreateMessage = null;
            i5 = 0;
            i4 = 0;
            i7 = 0;
        } else if (i2 >= 0) {
            if (i6 >= 0) {
                int i9 = i2 - i6;
                if (i9 > 0) {
                    strCreateMessage3 = createMessage(resources, PAY_TYPE.OUT_PAY, i9, i6);
                    i8 = i9;
                    i7 = 10;
                } else {
                    strCreateMessage3 = null;
                    i6 = 0;
                    i7 = 0;
                }
                strCreateMessage = strCreateMessage3;
                i5 = i6;
            } else {
                int i10 = i5 - i2;
                if (i10 > 0) {
                    strCreateMessage2 = createMessage(resources, PAY_TYPE.OUT_PAY, i10, i2);
                    i8 = i10;
                    i7 = 10;
                } else {
                    if (i10 < 0) {
                        i10 = -i10;
                        strCreateMessage2 = createMessage(resources, PAY_TYPE.OUT_CHARGE, i10, i2);
                        i7 = 11;
                    } else {
                        strCreateMessage2 = createMessage(resources, PAY_TYPE.OUT_NO_PAY, i10, i2);
                    }
                    i8 = i10;
                }
                i5 = i2;
                strCreateMessage = strCreateMessage2;
            }
            i4 = i8;
        } else {
            strCreateMessage = createMessage(resources, PAY_TYPE.OUT_NO_PAY, 0, i5);
            i4 = 0;
        }
        if (strCreateMessage == null) {
            return null;
        }
        NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_s11), strCreateMessage, R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_s11_icon), bArr, SERVICE_ID, 0);
        notificationInfo.setIntent(this.mContext, i7, i4, i5);
        logInfo.setPaymentLogInfo(i7, i4, i5);
        notificationInfo.setLogInfo(logInfo);
        return notificationInfo;
    }

    protected NotificationInfo createNotificationInfo(int i, int i2, int i3, Resources resources, byte[] bArr, LogInfo logInfo) {
        if (i >= 0 && i2 >= 0 && i3 < 0) {
            int i4 = i - i2;
            if (i4 > 0) {
                NotificationInfo notificationInfo = new NotificationInfo(resources.getString(R.string.tis_title_s11), createMessage(resources, PAY_TYPE.PAY, i4, i2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_s11_icon), bArr, SERVICE_ID, 0);
                notificationInfo.setIntent(this.mContext, 1, i4, i2);
                logInfo.setPaymentLogInfo(1, i4, i2);
                notificationInfo.setLogInfo(logInfo);
                return notificationInfo;
            }
            if (i4 < 0) {
                int i5 = -i4;
                NotificationInfo notificationInfo2 = new NotificationInfo(resources.getString(R.string.tis_title_s11), createMessage(resources, PAY_TYPE.CHARGE, i5, i2), R.mipmap.ic_osaifu_touka, BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.tis_s11_icon), bArr, SERVICE_ID, 0);
                notificationInfo2.setIntent(this.mContext, 2, i5, i2);
                logInfo.setPaymentLogInfo(2, i5, i2);
                notificationInfo2.setLogInfo(logInfo);
                return notificationInfo2;
            }
        }
        return null;
    }

    private boolean isSfReadServiceCodeValid(int i) {
        return Arrays.asList(ResolverConst.S11.SF_READ_SERVICE_CODE).contains(Integer.valueOf(i));
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

    protected String createMessage(Resources resources, PAY_TYPE pay_type, int i, int i2) {
        String str = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i));
        String str2 = String.format(Locale.JAPAN, "%,d", Integer.valueOf(i2));
        switch (AnonymousClass1.$SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[pay_type.ordinal()]) {
            case 1:
                return String.format(resources.getString(R.string.tis_common_charge_balance), str, str2);
            case 2:
                return String.format(resources.getString(R.string.tis_common_pay_balance), str, str2);
            case 3:
                return String.format(resources.getString(R.string.tis_common_in_only), str2);
            case 4:
                return String.format(resources.getString(R.string.tis_common_in_no_pay), str2);
            case 5:
                return String.format(resources.getString(R.string.tis_common_in_pay), str, str2);
            case 6:
                return String.format(resources.getString(R.string.tis_common_in_charge), str, str2);
            case 7:
                return String.format(resources.getString(R.string.tis_common_out_no_pay), str2);
            case 8:
                return String.format(resources.getString(R.string.tis_common_out_pay), str, str2);
            case 9:
                return String.format(resources.getString(R.string.tis_common_out_charge), str, str2);
            default:
                return "";
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.tis.resolver.S11Resolver$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$IN_OUT_TYPE;
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE;

        static {
            int[] iArr = new int[PAY_TYPE.values().length];
            $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE = iArr;
            try {
                iArr[PAY_TYPE.CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.PAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.IN_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.IN_NO_PAY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.IN_PAY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.IN_CHARGE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.OUT_NO_PAY.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.OUT_PAY.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$PAY_TYPE[PAY_TYPE.OUT_CHARGE.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            int[] iArr2 = new int[IN_OUT_TYPE.values().length];
            $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$IN_OUT_TYPE = iArr2;
            try {
                iArr2[IN_OUT_TYPE.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$IN_OUT_TYPE[IN_OUT_TYPE.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$felicanetworks$tis$resolver$S11Resolver$IN_OUT_TYPE[IN_OUT_TYPE.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError unused12) {
            }
        }
    }
}
