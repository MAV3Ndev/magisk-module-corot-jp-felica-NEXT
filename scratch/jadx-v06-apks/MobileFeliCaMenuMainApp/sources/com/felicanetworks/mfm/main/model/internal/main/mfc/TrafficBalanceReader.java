package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;

/* JADX INFO: loaded from: classes.dex */
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

    TrafficBalanceReader(Boolean bool) {
        super(new BlockList(), bool);
        for (int[] iArr : FeliCaParams.READ_BALANCE_INFO_LIST_TRAFFIC) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
        if (bool.booleanValue()) {
            for (int[] iArr2 : FeliCaParams.READ_HISTORY_INFO_LIST_TRAFFIC) {
                this.blocklist.add(new Block(iArr2[0], iArr2[1]));
            }
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    public MfcExpert.Asset getBalance(String str) throws FelicaReaderException {
        return getBalance(str, "");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0101  */
    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.Asset getBalance(java.lang.String r14, java.lang.String r15) throws com.felicanetworks.mfm.main.model.internal.main.mfc.FelicaReaderException {
        /*
            Method dump skipped, instruction units count: 463
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.mfc.TrafficBalanceReader.getBalance(java.lang.String, java.lang.String):com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert$Asset");
    }
}
