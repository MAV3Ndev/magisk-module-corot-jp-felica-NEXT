package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
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

    NanacoBalanceReader(Boolean bool) {
        super(new BlockList(), bool);
        for (int[] iArr : READ_BALANCE_INFO_LIST) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
        if (bool.booleanValue()) {
            for (int[] iArr2 : READ_HISTORY_INFO_LIST) {
                this.blocklist.add(new Block(iArr2[0], iArr2[1]));
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    public MfcExpert.Asset getBalance(String str) throws FelicaReaderException {
        ServiceInfo.History.HistoryData.UseType useType;
        boolean z;
        checkDataExist(getClass(), 801);
        MfcExpert.Asset asset = new MfcExpert.Asset(str, "", -1, -1, -1, -1, null, null, false);
        asset.balanceValue = getPurseData(this.data[0]);
        byte[] byteData = getByteData(this.data[1]);
        asset.point1 = new BigInteger(1, getTargetByte(byteData, 0, 3)).intValue();
        byte[] targetByte = getTargetByte(byteData, 3, 2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        int i = (targetByte[0] >>> 1) & 127;
        int i2 = targetByte[1] & 31;
        int i3 = 5;
        try {
            asset.date1 = simpleDateFormat.parse((i + 2000) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(((targetByte[0] << 3) & 8) + ((targetByte[1] >>> 5) & 7))) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(i2)));
            asset.point2 = new BigInteger(1, getTargetByte(byteData, 5, 3)).intValue();
            byte[] targetByte2 = getTargetByte(byteData, 8, 2);
            int i4 = (targetByte2[0] >>> 1) & 127;
            int i5 = targetByte2[1] & 31;
            try {
                asset.date2 = simpleDateFormat.parse((i4 + 2000) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(((targetByte2[0] << 3) & 8) + ((targetByte2[1] >>> 5) & 7))) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(i5)));
                if (this.isHistory.booleanValue()) {
                    ArrayList arrayList = new ArrayList();
                    int i6 = 2;
                    while (i6 < this.data.length) {
                        ServiceInfo.History.HistoryData.UseType useType2 = ServiceInfo.History.HistoryData.UseType.OTHER;
                        byte[] cyclicData = getCyclicData(this.data[i6]);
                        int iIntValue = new BigInteger(getTargetByte(cyclicData, 0, 1)).intValue() & 255;
                        if (iIntValue == 71) {
                            useType = ServiceInfo.History.HistoryData.UseType.PAYMENT;
                            z = false;
                        } else {
                            if (iIntValue == 53) {
                                useType = ServiceInfo.History.HistoryData.UseType.OTHER;
                            } else if (Arrays.asList(USE_TYPE_CHARGE_CODE_LIST).contains(Integer.valueOf(iIntValue))) {
                                useType = ServiceInfo.History.HistoryData.UseType.CHARGE;
                            } else {
                                i6++;
                                i3 = 5;
                            }
                            z = true;
                        }
                        int iIntValue2 = new BigInteger(getTargetByte(cyclicData, 1, 4)).intValue();
                        if (99999 >= iIntValue2 && iIntValue2 >= 0) {
                            int iIntValue3 = new BigInteger(getTargetByte(cyclicData, 9, 4)).intValue();
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(((iIntValue3 >>> 21) & 127) + 2000, ((iIntValue3 >>> 17) & 15) - 1, (iIntValue3 >>> 12) & 31);
                            int i7 = calendar.get(1);
                            int i8 = calendar.get(2) + 1;
                            int i9 = calendar.get(i3);
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(i7 + "." + String.format("%02d", Integer.valueOf(i8)) + "." + String.format("%02d", Integer.valueOf(i9)));
                            arrayList.add(new ServiceInfo.History.HistoryData(stringBuffer.toString(), useType, iIntValue2, z));
                        }
                        i6++;
                        i3 = 5;
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
