package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class EdyBalanceReader extends BalanceReader {
    private static final int BALANCE_READ_NUM = 4;
    private static final int BALANCE_READ_START_INDEX = 12;
    private static final int BASE_USE_DAY = 1;
    private static final int BASE_USE_MONTH = 1;
    private static final int BASE_USE_YEAR = 2000;
    private static final int BLOCK_NO = 0;
    private static final int[][] READ_BALANCE_INFO_LIST = FeliCaParams.READ_BALANCE_INFO_LIST_EDY;
    private static final int[][] READ_HISTORY_INFO_LIST = FeliCaParams.READ_HISTORY_INFO_LIST_EDY;
    private static final int READ_HISTORY_START_INDEX = 1;
    public static final String SERVICE_ID = "SV000013";
    private static final int SHIFT_BIT_NUM_USE_DATE = 1;
    private static final int USE_DATE_READ_NUM = 2;
    private static final int USE_DATE_READ_START_INDEX = 4;
    private static final int USE_MONEY_READ_NUM = 4;
    private static final int USE_MONEY_READ_START_INDEX = 8;
    private static final int USE_TYPE_CHARGE1 = 2;
    private static final int USE_TYPE_CHARGE2 = 4;
    private static final int USE_TYPE_OTHER = 8;
    private static final int USE_TYPE_PAYMENT = 32;
    private static final int USE_TYPE_READ_NUM = 1;
    private static final int USE_TYPE_READ_START_INDEX = 0;

    EdyBalanceReader(Boolean bool) {
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
        int iIntValue;
        MfcExpert.Asset balance = super.getBalance(str);
        if (this.isHistory.booleanValue()) {
            ArrayList arrayList = new ArrayList();
            for (int i = 1; i < this.data.length; i++) {
                byte[] cyclicData = getCyclicData(this.data[i]);
                int iIntValue2 = new BigInteger(getTargetByte(cyclicData, 0, 1)).intValue();
                if (iIntValue2 == 2 || iIntValue2 == 4) {
                    useType = ServiceInfo.History.HistoryData.UseType.CHARGE;
                } else if (iIntValue2 == 8) {
                    useType = ServiceInfo.History.HistoryData.UseType.OTHER;
                } else if (iIntValue2 == 32) {
                    useType = ServiceInfo.History.HistoryData.UseType.PAYMENT;
                    z = false;
                    iIntValue = new BigInteger(getTargetByte(cyclicData, 8, 4)).intValue();
                    if (99999 < iIntValue && iIntValue >= 0) {
                        int iIntValue3 = new BigInteger(getTargetByte(cyclicData, 4, 2)).intValue() >>> 1;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(2000, 0, 1);
                        calendar.add(5, iIntValue3);
                        int i2 = calendar.get(1);
                        int i3 = calendar.get(2) + 1;
                        int i4 = calendar.get(5);
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(i2 + "." + String.format("%02d", Integer.valueOf(i3)) + "." + String.format("%02d", Integer.valueOf(i4)));
                        arrayList.add(new ServiceInfo.History.HistoryData(stringBuffer.toString(), useType, iIntValue, z));
                    }
                }
                z = true;
                iIntValue = new BigInteger(getTargetByte(cyclicData, 8, 4)).intValue();
                if (99999 < iIntValue) {
                }
            }
            balance.historyDataList = arrayList;
        }
        return balance;
    }
}
