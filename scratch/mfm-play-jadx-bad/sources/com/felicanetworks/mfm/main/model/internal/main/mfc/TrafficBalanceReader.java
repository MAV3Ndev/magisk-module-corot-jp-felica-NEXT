package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.google.android.material.timepicker.TimeModel;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* JADX INFO: loaded from: classes3.dex */
public class TrafficBalanceReader extends BalanceReader {
    private static final int BALANCE_READ_NUM = 3;
    private static final int BALANCE_READ_START_INDEX = 10;
    private static final int BASE_USE_YEAR = 2000;
    private static final int BLOCK_NO = 0;
    private static final int MASK_VALUE_USE_DAY = 31;
    private static final int MASK_VALUE_USE_MONTH = 15;
    private static final int MASK_VALUE_USE_TYPE = 127;
    private static final int MASK_VALUE_USE_YEAR = 127;
    private static final int READ_HISTORY_START_INDEX = 1;
    private static final int READ_NUM = 3;
    private static final int READ_START_INDEX = 11;
    private static final int SHIFT_BIT_NUM_USE_MONTH = 5;
    private static final int SHIFT_BIT_NUM_USE_YEAR = 1;
    private static final int USE_DAY_READ_NUM = 1;
    private static final int USE_DAY_READ_START_INDEX = 5;
    private static final int USE_MONTH_READ_NUM = 2;
    private static final int USE_MONTH_READ_START_INDEX = 4;
    private static final int USE_TYPE_READ_NUM = 1;
    private static final int USE_TYPE_READ_START_INDEX = 1;
    private static final int USE_YEAR_READ_NUM = 1;
    private static final int USE_YEAR_READ_START_INDEX = 4;
    private static final Integer[] USE_TYPE_TRAFIIC_CODE_LIST = {0, 1, 3, 4, 5, 6, 12, 13, 14, 15, 18, 19, 25, 26, 27, 29, 35};
    private static final Integer[] USE_TYPE_CHARGE_CODE_LIST = {2, 20, 31, 72, 73, 78};
    private static final Integer[] USE_TYPE_PAYMENT_CODE_LIST = {70};
    private static final Integer[] USE_TYPE_OTHER_CODE_LIST = {7, 8, 9, 10, 11, 16, 17, 21, 22, 23, 24, 28, 30, 32, 33, 34, 36, 37, 38, 39, 40, 50, 51, 71, 74, 75, 76, 77};

    TrafficBalanceReader(Boolean isHistory) {
        super(new BlockList(), isHistory);
        for (int[] iArr : FeliCaParams.READ_BALANCE_INFO_LIST_TRAFFIC) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
        if (isHistory.booleanValue()) {
            for (int[] iArr2 : FeliCaParams.READ_HISTORY_INFO_LIST_TRAFFIC) {
                this.blocklist.add(new Block(iArr2[0], iArr2[1]));
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    public MfcExpert.Asset getBalance(String serviceId) throws FelicaReaderException {
        return getBalance(serviceId, "");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0100  */
    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public MfcExpert.Asset getBalance(String serviceId, String cid) throws FelicaReaderException {
        boolean z;
        checkDataExist(getClass(), 785);
        byte[] byteData = getByteData(this.data[0]);
        MfcExpert.Asset asset = new MfcExpert.Asset(serviceId, cid, -1, -1, -1, -1, null, null, false);
        asset.balanceValue = new BigInteger(CommonUtil.changeEndian(getTargetByte(byteData, 11, 3))).intValue();
        if (this.isHistory.booleanValue()) {
            ArrayList arrayList = new ArrayList();
            int i = 1;
            while (i < this.data.length - 1) {
                ServiceInfo.History.HistoryData.UseType useType = ServiceInfo.History.HistoryData.UseType.OTHER;
                byte[] cyclicData = getCyclicData(this.data[i]);
                i++;
                int iIntValue = new BigInteger(CommonUtil.changeEndian(getTargetByte(getCyclicData(this.data[i]), 10, 3))).intValue() - new BigInteger(CommonUtil.changeEndian(getTargetByte(cyclicData, 10, 3))).intValue();
                if (99999 >= iIntValue && iIntValue != 0) {
                    int i2 = getTargetByte(cyclicData, 1, 1)[0] & 127;
                    if (iIntValue < 0) {
                        if (Arrays.asList(USE_TYPE_CHARGE_CODE_LIST).contains(Integer.valueOf(i2)) || Arrays.asList(USE_TYPE_OTHER_CODE_LIST).contains(Integer.valueOf(i2))) {
                            iIntValue *= -1;
                            z = true;
                            if (!Arrays.asList(USE_TYPE_TRAFIIC_CODE_LIST).contains(Integer.valueOf(i2))) {
                                useType = ServiceInfo.History.HistoryData.UseType.TRAFFIC;
                            } else if (Arrays.asList(USE_TYPE_CHARGE_CODE_LIST).contains(Integer.valueOf(i2))) {
                                useType = ServiceInfo.History.HistoryData.UseType.CHARGE;
                            } else if (Arrays.asList(USE_TYPE_OTHER_CODE_LIST).contains(Integer.valueOf(i2))) {
                                useType = ServiceInfo.History.HistoryData.UseType.OTHER;
                            } else if (Arrays.asList(USE_TYPE_PAYMENT_CODE_LIST).contains(Integer.valueOf(i2))) {
                                useType = ServiceInfo.History.HistoryData.UseType.PAYMENT;
                            }
                            int iIntValue2 = ((new BigInteger(getTargetByte(cyclicData, 4, 1)).intValue() >>> 1) & WorkQueueKt.MASK) + 2000;
                            int iIntValue3 = (new BigInteger(getTargetByte(cyclicData, 4, 2)).intValue() >>> 5) & 15;
                            int iIntValue4 = new BigInteger(getTargetByte(cyclicData, 5, 1)).intValue() & 31;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(iIntValue2, iIntValue3 - 1, iIntValue4);
                            int i3 = calendar.get(1);
                            int i4 = calendar.get(2) + 1;
                            int i5 = calendar.get(5);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(i3 + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i4)) + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i5)));
                            arrayList.add(new ServiceInfo.History.HistoryData(stringBuffer.toString(), useType, iIntValue, z));
                        }
                    } else if (Arrays.asList(USE_TYPE_TRAFIIC_CODE_LIST).contains(Integer.valueOf(i2)) || Arrays.asList(USE_TYPE_PAYMENT_CODE_LIST).contains(Integer.valueOf(i2)) || Arrays.asList(USE_TYPE_OTHER_CODE_LIST).contains(Integer.valueOf(i2))) {
                        z = false;
                        if (!Arrays.asList(USE_TYPE_TRAFIIC_CODE_LIST).contains(Integer.valueOf(i2))) {
                        }
                        int iIntValue22 = ((new BigInteger(getTargetByte(cyclicData, 4, 1)).intValue() >>> 1) & WorkQueueKt.MASK) + 2000;
                        int iIntValue32 = (new BigInteger(getTargetByte(cyclicData, 4, 2)).intValue() >>> 5) & 15;
                        int iIntValue42 = new BigInteger(getTargetByte(cyclicData, 5, 1)).intValue() & 31;
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.set(iIntValue22, iIntValue32 - 1, iIntValue42);
                        int i32 = calendar2.get(1);
                        int i42 = calendar2.get(2) + 1;
                        int i52 = calendar2.get(5);
                        StringBuffer stringBuffer2 = new StringBuffer();
                        stringBuffer2.append(i32 + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i42)) + "." + String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i52)));
                        arrayList.add(new ServiceInfo.History.HistoryData(stringBuffer2.toString(), useType, iIntValue, z));
                    }
                }
            }
            asset.historyDataList = arrayList;
        }
        return asset;
    }
}
