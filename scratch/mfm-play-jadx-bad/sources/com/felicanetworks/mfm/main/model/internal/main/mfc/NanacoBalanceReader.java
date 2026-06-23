package com.felicanetworks.mfm.main.model.internal.main.mfc;

import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.google.android.material.timepicker.TimeModel;
import com.google.common.base.Ascii;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import kotlinx.coroutines.scheduling.WorkQueueKt;

/* JADX INFO: loaded from: classes3.dex */
public class NanacoBalanceReader extends BalanceReader {
    private static final int BALANCE_READ_NUM = 4;
    private static final int BALANCE_READ_START_INDEX = 5;
    private static final int BASE_USE_YEAR = 2000;
    private static final int EXPIRATION_DATE_1_READ_NUM = 2;
    private static final int EXPIRATION_DATE_1_START_INDEX = 3;
    private static final int EXPIRATION_DATE_2_READ_NUM = 2;
    private static final int EXPIRATION_DATE_2_START_INDEX = 8;
    private static final int MASK_VALUE_DAY = 31;
    private static final int MASK_VALUE_MONTH1 = 8;
    private static final int MASK_VALUE_MONTH2 = 7;
    private static final int MASK_VALUE_USE_DAY = 31;
    private static final int MASK_VALUE_USE_MONTH = 15;
    private static final int MASK_VALUE_USE_YEAR = 127;
    private static final int MASK_VALUE_YEAR = 127;
    private static final int POINT_1_READ_NUM = 3;
    private static final int POINT_1_READ_START_INDEX = 0;
    private static final int POINT_2_READ_NUM = 3;
    private static final int POINT_2_READ_START_INDEX = 5;
    private static final int READ_HISTORY_START_INDEX = 2;
    private static final int READ_INDEX_POINT = 1;
    public static final String SERVICE_ID = "SV000075";
    private static final int SHIFT_BIT_NUM_MONTH1 = 3;
    private static final int SHIFT_BIT_NUM_MONTH2 = 5;
    private static final int SHIFT_BIT_NUM_USE_DAY = 12;
    private static final int SHIFT_BIT_NUM_USE_MONTH = 17;
    private static final int SHIFT_BIT_NUM_USE_YEAR = 21;
    private static final int SHIFT_BIT_NUM_YEAR = 1;
    private static final int USE_DATE_READ_NUM = 4;
    private static final int USE_DATE_READ_START_INDEX = 9;
    private static final int USE_MONEY_READ_NUM = 4;
    private static final int USE_MONEY_READ_START_INDEX = 1;
    private static final int USE_TYPE_OTHER = 53;
    private static final int USE_TYPE_PAYMENT = 71;
    private static final int USE_TYPE_READ_NUM = 1;
    private static final int USE_TYPE_READ_START_INDEX = 0;
    private static final int[][] READ_BALANCE_INFO_LIST = FeliCaParams.READ_BALANCE_INFO_LIST_NANACO;
    private static final int[][] READ_HISTORY_INFO_LIST = FeliCaParams.READ_HISTORY_INFO_LIST_NANACO;
    private static final Integer[] USE_TYPE_CHARGE_CODE_LIST = {92, 94, 111, 112, 115, 116, 119, 131, 133};

    NanacoBalanceReader(Boolean isHistory) {
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

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    public MfcExpert.Asset getBalance(String serviceId) throws FelicaReaderException {
        ServiceInfo.History.HistoryData.UseType useType;
        boolean z;
        checkDataExist(getClass(), 801);
        MfcExpert.Asset asset = new MfcExpert.Asset(serviceId, "", -1, -1, -1, -1, null, null, false);
        asset.balanceValue = getPurseData(this.data[0]);
        byte[] byteData = getByteData(this.data[1]);
        asset.point1 = new BigInteger(1, getTargetByte(byteData, 0, 3)).intValue();
        int i = 2;
        byte[] targetByte = getTargetByte(byteData, 3, 2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        byte b = targetByte[0];
        int i2 = (b >>> 1) & WorkQueueKt.MASK;
        byte b2 = targetByte[1];
        int i3 = b2 & Ascii.US;
        int i4 = ((b << 3) & 8) + ((b2 >>> 5) & 7);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(i2 + 2000);
            sb.append(DomExceptionUtils.SEPARATOR);
            int i5 = 0;
            sb.append(String.format(Locale.US, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i4)));
            sb.append(DomExceptionUtils.SEPARATOR);
            sb.append(String.format(Locale.US, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i3)));
            asset.date1 = simpleDateFormat.parse(sb.toString());
            asset.point2 = new BigInteger(1, getTargetByte(byteData, 5, 3)).intValue();
            byte[] targetByte2 = getTargetByte(byteData, 8, 2);
            byte b3 = targetByte2[0];
            int i6 = (b3 >>> 1) & WorkQueueKt.MASK;
            byte b4 = targetByte2[1];
            try {
                asset.date2 = simpleDateFormat.parse((i6 + 2000) + DomExceptionUtils.SEPARATOR + String.format(Locale.US, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(((b3 << 3) & 8) + ((b4 >>> 5) & 7))) + DomExceptionUtils.SEPARATOR + String.format(Locale.US, TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(b4 & Ascii.US)));
                if (this.isHistory.booleanValue()) {
                    ArrayList arrayList = new ArrayList();
                    int i7 = 2;
                    while (i7 < this.data.length) {
                        ServiceInfo.History.HistoryData.UseType useType2 = ServiceInfo.History.HistoryData.UseType.OTHER;
                        byte[] cyclicData = getCyclicData(this.data[i7]);
                        int iIntValue = new BigInteger(getTargetByte(cyclicData, i5, 1)).intValue() & 255;
                        if (iIntValue == 71) {
                            useType = ServiceInfo.History.HistoryData.UseType.PAYMENT;
                            z = false;
                        } else {
                            if (iIntValue == 53) {
                                useType = ServiceInfo.History.HistoryData.UseType.OTHER;
                            } else {
                                if (Arrays.asList(USE_TYPE_CHARGE_CODE_LIST).contains(Integer.valueOf(iIntValue))) {
                                    useType = ServiceInfo.History.HistoryData.UseType.CHARGE;
                                }
                                i5 = 0;
                                i7++;
                                i = 2;
                            }
                            z = true;
                        }
                        int iIntValue2 = new BigInteger(getTargetByte(cyclicData, 1, 4)).intValue();
                        if (99999 < iIntValue2 || iIntValue2 < 0) {
                            i5 = 0;
                        } else {
                            int iIntValue3 = new BigInteger(getTargetByte(cyclicData, 9, 4)).intValue();
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(((iIntValue3 >>> 21) & WorkQueueKt.MASK) + 2000, ((iIntValue3 >>> 17) & 15) - 1, (iIntValue3 >>> 12) & 31);
                            int i8 = calendar.get(1);
                            int i9 = calendar.get(i) + 1;
                            int i10 = calendar.get(5);
                            StringBuffer stringBuffer = new StringBuffer();
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(i8);
                            sb2.append(".");
                            i5 = 0;
                            sb2.append(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i9)));
                            sb2.append(".");
                            sb2.append(String.format(TimeModel.ZERO_LEADING_NUMBER_FORMAT, Integer.valueOf(i10)));
                            stringBuffer.append(sb2.toString());
                            arrayList.add(new ServiceInfo.History.HistoryData(stringBuffer.toString(), useType, iIntValue2, z));
                        }
                        i7++;
                        i = 2;
                    }
                    asset.historyDataList = arrayList;
                }
                return asset;
            } catch (ParseException e) {
                throw new FelicaReaderException(getClass(), 71, e);
            }
        } catch (ParseException e2) {
            throw new FelicaReaderException(getClass(), 71, e2);
        }
    }
}
