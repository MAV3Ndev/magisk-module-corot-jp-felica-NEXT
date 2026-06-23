package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.info.ServiceInfo;

/* JADX INFO: loaded from: classes.dex */
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

    WaonBalanceReader(Boolean bool) {
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

    /* JADX WARN: Removed duplicated region for block: B:110:0x0347  */
    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.BalanceReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert.Asset getBalance(java.lang.String r22) throws com.felicanetworks.mfm.main.model.internal.main.mfc.FelicaReaderException {
        /*
            Method dump skipped, instruction units count: 1132
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.mfc.WaonBalanceReader.getBalance(java.lang.String):com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert$Asset");
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
