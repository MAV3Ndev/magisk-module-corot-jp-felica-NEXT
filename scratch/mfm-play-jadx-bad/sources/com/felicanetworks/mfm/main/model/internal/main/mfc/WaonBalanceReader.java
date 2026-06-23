package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.google.android.material.timepicker.TimeModel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/* JADX INFO: loaded from: classes3.dex */
public class WaonBalanceReader extends BalanceReader {
    private static final int BALANCE_READ_NUM = 3;
    private static final int BALANCE_READ_START_INDEX = 5;
    private static final int BASE_USE_YEAR = 2005;
    private static final int CHARGE_MONEY_READ_NUM = 3;
    private static final int CHARGE_MONEY_READ_START_INDEX = 9;
    private static final int HISTORY_READ_BLOCK_NO_1 = 1;
    private static final int HISTORY_READ_BLOCK_NO_3 = 3;
    private static final int HISTORY_READ_BLOCK_NO_5 = 5;
    private static final int MASK_VALUE_BALANCE = 131071;
    private static final int MASK_VALUE_BLANDAPLSTATUS = 1;
    private static final int MASK_VALUE_CHARGE_MONEY = 65535;
    private static final int MASK_VALUE_ENABLEFLG = 3;
    private static final int MASK_VALUE_USE_DAY = 31;
    private static final int MASK_VALUE_USE_HOUR = 31;
    private static final int MASK_VALUE_USE_MINUTES = 63;
    private static final int MASK_VALUE_USE_MONEY = 131071;
    private static final int MASK_VALUE_USE_MONTH = 15;
    private static final int MASK_VALUE_USE_TYPE = 63;
    private static final int MASK_VALUE_USE_YEAR = 31;
    private static final int MASK_VALUE_VALUEISSUERAPLSTATUS = 1;
    private static final int NOT_PERMIT_BLANDAPLSTATUS = 1;
    private static final int NOT_PERMIT_ENABLEFLG = 0;
    private static final int NOT_PERMIT_VALUEISSUERAPLSTATUS = 1;
    private static final int READ_HISTORY_START_INDEX = 4;
    private static final int READ_INDEX_BALANCE = 0;
    private static final int READ_INDEX_BLANDAPLSTATUS = 1;
    private static final int READ_INDEX_ENABLEFLG_VALUEISSUERAPLSTATUS = 2;
    private static final int READ_INDEX_POINT = 3;
    private static final int READ_NUM = 3;
    private static final int READ_START_INDEX = 0;
    public static final String SERVICE_ID = "SV000011";
    private static final int SHIFT_BIT_NUM_BALANCE = 5;
    private static final int SHIFT_BIT_NUM_BLANDAPLSTATUS = 7;
    private static final int SHIFT_BIT_NUM_CHARGE_MONEY = 2;
    private static final int SHIFT_BIT_NUM_ENABLEFLG = 6;
    private static final int SHIFT_BIT_NUM_USE_DAY = 2;
    private static final int SHIFT_BIT_NUM_USE_HOUR = 5;
    private static final int SHIFT_BIT_NUM_USE_MINUTES = 7;
    private static final int SHIFT_BIT_NUM_USE_MONEY = 3;
    private static final int SHIFT_BIT_NUM_USE_MONTH = 7;
    private static final int SHIFT_BIT_NUM_USE_TYPE = 2;
    private static final int SHIFT_BIT_NUM_USE_YEAR = 3;
    private static final int SHIFT_BIT_NUM_VALUEISSUERAPLSTATUS = 7;
    private static final int TARGET_INDEX_BLANDAPLSTATUS = 12;
    private static final int TARGET_INDEX_ENABLEFLG = 4;
    private static final int TARGET_INDEX_VALUEISSUERAPLSTATUS = 0;
    private static final int USE_DAY_READ_NUM = 1;
    private static final int USE_DAY_READ_START_INDEX = 3;
    private static final int USE_HOUR_READ_NUM = 2;
    private static final int USE_HOUR_READ_START_INDEX = 3;
    private static final int USE_MINUTES_READ_NUM = 2;
    private static final int USE_MINUTES_READ_START_INDEX = 4;
    private static final int USE_MONEY_READ_NUM = 3;
    private static final int USE_MONEY_READ_START_INDEX = 7;
    private static final int USE_MONTH_READ_NUM = 2;
    private static final int USE_MONTH_READ_START_INDEX = 2;
    private static final int USE_TYPE_PAYMENT = 1;
    private static final int USE_TYPE_READ_NUM = 1;
    private static final int USE_TYPE_READ_START_INDEX = 1;
    private static final int USE_YEAR_READ_NUM = 1;
    private static final int USE_YEAR_READ_START_INDEX = 2;
    private static final int[][] READ_BALANCE_INFO_LIST = FeliCaParams.READ_BALANCE_INFO_LIST_WAON;
    private static final int[][] READ_HISTORY_INFO_LIST = FeliCaParams.READ_HISTORY_INFO_LIST_WAON;
    private static final Integer[] USE_TYPE_CHARGE_CODE_LIST = {3, 4, 12, 13};
    private static final Integer[] USE_TYPE_CHARGE_AND_PAYMENT_CODE_LIST = {7, 8};
    private static final Integer[] USE_TYPE_OTHER_CODE_LIST = {2, 10, 5};

    WaonBalanceReader(Boolean isHistory) {
        super(new BlockList(), isHistory);
        for (int[] iArr : READ_BALANCE_INFO_LIST) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
        if (isHistory.booleanValue()) {
            for (int[] iArr2 : READ_HISTORY_INFO_LIST) {
                this.blocklist.add(new Block(iArr2[0], iArr2[1]));
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r19v0, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r19v1, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r19v2, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r19v3, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    public MfcExpert.Asset getBalance(String serviceId) throws FelicaReaderException {
        int i;
        ServiceInfo.History.HistoryData.UseType useType;
        boolean z;
        int i2;
        int i3;
        int i4;
        int i5;
        checkDataExist(getClass(), 834);
        MfcExpert.Asset asset = new MfcExpert.Asset(serviceId, "", -1, -1, -1, -1, null, null, false);
        byte[] byteData = getByteData(this.data[2]);
        int i6 = 4;
        int i7 = 6;
        if (((byteData[4] >>> 6) & 3) != 0) {
            int i8 = 0;
            int i9 = 7;
            if (((byteData[0] >>> 7) & 1) != 1 && ((getByteData(this.data[1])[12] >>> 7) & 1) != 1) {
                asset.balanceValue = getPurseData(this.data[0]);
                asset.point1 = new BigInteger(1, getTargetByte(getByteData(this.data[3]), 0, 3)).intValue();
                if (this.isHistory.booleanValue()) {
                    ArrayList arrayList = new ArrayList();
                    int i10 = 4;
                    while (i10 < this.data.length) {
                        ServiceInfo.History.HistoryData.UseType useType2 = ServiceInfo.History.HistoryData.UseType.OTHER;
                        byte[] byteData2 = getByteData(this.data[i10]);
                        if (i10 == i6) {
                            i = 1;
                        } else if (i10 != 5) {
                            if (i10 == i7) {
                                i = 5;
                            }
                            i2 = i8;
                            i5 = i9;
                            i10++;
                            i8 = i2;
                            i9 = i5;
                            i6 = 4;
                            i7 = 6;
                        } else {
                            i = 3;
                        }
                        int iIntValue = (new BigInteger(getTargetByte(byteData2, 1, 1)).intValue() >>> 2) & 63;
                        if (iIntValue == 1) {
                            useType = ServiceInfo.History.HistoryData.UseType.PAYMENT;
                            z = i8;
                        } else {
                            if (Arrays.asList(USE_TYPE_CHARGE_CODE_LIST).contains(Integer.valueOf(iIntValue))) {
                                useType = ServiceInfo.History.HistoryData.UseType.CHARGE;
                            } else if (Arrays.asList(USE_TYPE_CHARGE_AND_PAYMENT_CODE_LIST).contains(Integer.valueOf(iIntValue))) {
                                useType = ServiceInfo.History.HistoryData.UseType.CHARGE_AND_PAYMENT;
                            } else {
                                if (Arrays.asList(USE_TYPE_OTHER_CODE_LIST).contains(Integer.valueOf(iIntValue))) {
                                    int i11 = (iIntValue == 2 || iIntValue == 10) ? 1 : i8;
                                    useType = ServiceInfo.History.HistoryData.UseType.OTHER;
                                    z = i11;
                                }
                                i2 = i8;
                                i5 = i9;
                                i10++;
                                i8 = i2;
                                i9 = i5;
                                i6 = 4;
                                i7 = 6;
                            }
                            z = 1;
                        }
                        i2 = i8;
                        if (useType == ServiceInfo.History.HistoryData.UseType.PAYMENT || useType == ServiceInfo.History.HistoryData.UseType.CHARGE_AND_PAYMENT || iIntValue == 2 || iIntValue == 10) {
                            int iIntValue2 = (new BigInteger(getTargetByte(byteData2, i9, 3)).intValue() >>> 3) & 131071;
                            if (99999 >= iIntValue2 && iIntValue2 >= 0) {
                                i3 = iIntValue2;
                            }
                            i5 = i9;
                            i10++;
                            i8 = i2;
                            i9 = i5;
                            i6 = 4;
                            i7 = 6;
                        } else {
                            i3 = i2;
                        }
                        if (useType == ServiceInfo.History.HistoryData.UseType.CHARGE || useType == ServiceInfo.History.HistoryData.UseType.CHARGE_AND_PAYMENT || iIntValue == 5) {
                            int iIntValue3 = (new BigInteger(getTargetByte(byteData2, 9, 3)).intValue() >>> 2) & 65535;
                            if (99999 >= iIntValue3 && iIntValue3 >= 0) {
                                i4 = iIntValue3;
                            }
                            i5 = i9;
                            i10++;
                            i8 = i2;
                            i9 = i5;
                            i6 = 4;
                            i7 = 6;
                        } else {
                            i4 = i2;
                        }
                        int iIntValue4 = ((new BigInteger(getTargetByte(byteData2, 2, 1)).intValue() >>> 3) & 31) + BASE_USE_YEAR;
                        int iIntValue5 = (new BigInteger(getTargetByte(byteData2, 2, 2)).intValue() >>> i9) & 15;
                        int iIntValue6 = (new BigInteger(getTargetByte(byteData2, 3, 1)).intValue() >>> 2) & 31;
                        i5 = i9;
                        int iIntValue7 = (new BigInteger(getTargetByte(byteData2, 3, 2)).intValue() >>> 5) & 31;
                        int iIntValue8 = (new BigInteger(getTargetByte(byteData2, i6, 2)).intValue() >>> 7) & 63;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(iIntValue4, iIntValue5 - 1, iIntValue6);
                        int i12 = calendar.get(1);
                        int i13 = calendar.get(2) + 1;
                        int i14 = calendar.get(5);
                        StringBuffer stringBuffer = new StringBuffer();
                        StringBuilder sb = new StringBuilder();
                        sb.append(i12);
                        sb.append(".");
                        Object[] objArr = new Object[1];
                        objArr[i2] = Integer.valueOf(i13);
                        sb.append(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, objArr));
                        sb.append(".");
                        Object[] objArr2 = new Object[1];
                        objArr2[i2] = Integer.valueOf(i14);
                        sb.append(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, objArr2));
                        sb.append(" ");
                        Object[] objArr3 = new Object[1];
                        objArr3[i2] = Integer.valueOf(iIntValue7);
                        sb.append(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, objArr3));
                        sb.append(":");
                        Object[] objArr4 = new Object[1];
                        objArr4[i2] = Integer.valueOf(iIntValue8);
                        sb.append(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, objArr4));
                        stringBuffer.append(sb.toString());
                        arrayList.add(new ServiceInfo.History.HistoryData(stringBuffer.toString(), useType, i3, i4, z, i));
                        i10++;
                        i8 = i2;
                        i9 = i5;
                        i6 = 4;
                        i7 = 6;
                    }
                    int i15 = i8;
                    if (arrayList.size() == 3 && ((ServiceInfo.History.HistoryData) arrayList.get(i15)).getDate().equals(((ServiceInfo.History.HistoryData) arrayList.get(1)).getDate()) && ((ServiceInfo.History.HistoryData) arrayList.get(1)).getDate().equals(((ServiceInfo.History.HistoryData) arrayList.get(2)).getDate())) {
                        asset.historyDataList = new ArrayList();
                        return asset;
                    }
                    for (int i16 = 0; i16 < arrayList.size(); i16++) {
                        int i17 = 0;
                        while (i17 < (arrayList.size() - i16) - 1) {
                            int i18 = i17 + 1;
                            if (((ServiceInfo.History.HistoryData) arrayList.get(i17)).getDate().compareTo(((ServiceInfo.History.HistoryData) arrayList.get(i18)).getDate()) < 0) {
                                ServiceInfo.History.HistoryData historyData = (ServiceInfo.History.HistoryData) arrayList.get(i17);
                                arrayList.set(i17, (ServiceInfo.History.HistoryData) arrayList.get(i18));
                                arrayList.set(i18, historyData);
                            }
                            i17 = i18;
                        }
                    }
                    ArrayList arrayList2 = new ArrayList();
                    int i19 = 0;
                    int i20 = 0;
                    while (true) {
                        if (i19 >= arrayList.size()) {
                            break;
                        }
                        int i21 = i19 + 1;
                        if (i19 == arrayList.size() - 1) {
                            arrayList2.add((ServiceInfo.History.HistoryData) arrayList.get(i19));
                            break;
                        }
                        if (!((ServiceInfo.History.HistoryData) arrayList.get(i19)).getDate().equals(((ServiceInfo.History.HistoryData) arrayList.get(i21)).getDate())) {
                            arrayList2.add((ServiceInfo.History.HistoryData) arrayList.get(i19));
                            i20 = i19;
                        } else {
                            if ((((ServiceInfo.History.HistoryData) arrayList.get(i19)).getBlock() == 1 && ((ServiceInfo.History.HistoryData) arrayList.get(i21)).getBlock() == 3) || ((((ServiceInfo.History.HistoryData) arrayList.get(i19)).getBlock() == 3 && ((ServiceInfo.History.HistoryData) arrayList.get(i21)).getBlock() == 5) || (((ServiceInfo.History.HistoryData) arrayList.get(i19)).getBlock() == 5 && ((ServiceInfo.History.HistoryData) arrayList.get(i21)).getBlock() == 1))) {
                                arrayList2.add((ServiceInfo.History.HistoryData) arrayList.get(i19));
                                arrayList2.add((ServiceInfo.History.HistoryData) arrayList.get(i21));
                            } else if ((((ServiceInfo.History.HistoryData) arrayList.get(i19)).getBlock() == 3 && ((ServiceInfo.History.HistoryData) arrayList.get(i21)).getBlock() == 1) || ((((ServiceInfo.History.HistoryData) arrayList.get(i19)).getBlock() == 5 && ((ServiceInfo.History.HistoryData) arrayList.get(i21)).getBlock() == 3) || (((ServiceInfo.History.HistoryData) arrayList.get(i19)).getBlock() == 1 && ((ServiceInfo.History.HistoryData) arrayList.get(i21)).getBlock() == 5))) {
                                arrayList2.add((ServiceInfo.History.HistoryData) arrayList.get(i21));
                                arrayList2.add((ServiceInfo.History.HistoryData) arrayList.get(i19));
                            }
                            i20 = i21;
                        }
                        i19 = i20 + 1;
                    }
                    ArrayList arrayList3 = new ArrayList();
                    for (int i22 = 0; i22 < arrayList2.size(); i22++) {
                        ServiceInfo.History.HistoryData historyData2 = (ServiceInfo.History.HistoryData) arrayList2.get(i22);
                        String strSubstring = historyData2.getDate().substring(0, 10);
                        int i23 = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType[historyData2.getUseType().ordinal()];
                        if (i23 == 1) {
                            arrayList3.add(new ServiceInfo.History.HistoryData(strSubstring, historyData2.getUseType(), historyData2.getMoney(), historyData2.getIsPlus()));
                        } else if (i23 == 2) {
                            arrayList3.add(new ServiceInfo.History.HistoryData(strSubstring, historyData2.getUseType(), historyData2.getChargeMoney(), historyData2.getIsPlus()));
                        } else if (i23 == 3) {
                            arrayList3.add(new ServiceInfo.History.HistoryData(strSubstring, ServiceInfo.History.HistoryData.UseType.CHARGE, historyData2.getMoney(), historyData2.getIsPlus()));
                            arrayList3.add(new ServiceInfo.History.HistoryData(strSubstring, ServiceInfo.History.HistoryData.UseType.PAYMENT, historyData2.getChargeMoney(), false));
                        } else if (i23 == 4) {
                            if (historyData2.getIsPlus()) {
                                arrayList3.add(new ServiceInfo.History.HistoryData(strSubstring, historyData2.getUseType(), historyData2.getMoney(), historyData2.getIsPlus()));
                            } else {
                                arrayList3.add(new ServiceInfo.History.HistoryData(strSubstring, historyData2.getUseType(), historyData2.getChargeMoney(), historyData2.getIsPlus()));
                            }
                        }
                    }
                    asset.historyDataList = arrayList3;
                }
            }
        }
        return asset;
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.mfc.WaonBalanceReader$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType;

        static {
            int[] iArr = new int[ServiceInfo.History.HistoryData.UseType.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType = iArr;
            try {
                iArr[ServiceInfo.History.HistoryData.UseType.PAYMENT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType[ServiceInfo.History.HistoryData.UseType.CHARGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType[ServiceInfo.History.HistoryData.UseType.CHARGE_AND_PAYMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$info$ServiceInfo$History$HistoryData$UseType[ServiceInfo.History.HistoryData.UseType.OTHER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
