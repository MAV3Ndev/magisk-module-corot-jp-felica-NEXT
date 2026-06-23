package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mnlib.felica.Block;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes3.dex */
public class ExtWaonBalanceReader extends ExtFelicaReader {
    private static final int MASK_VALUE_BLANDAPLSTATUS = 1;
    private static final int MASK_VALUE_ENABLEFLG = 3;
    private static final int MASK_VALUE_VALUEISSUERAPLSTATUS = 1;
    private static final int NOT_PERMIT_BLANDAPLSTATUS = 1;
    private static final int NOT_PERMIT_ENABLEFLG = 0;
    private static final int NOT_PERMIT_VALUEISSUERAPLSTATUS = 1;
    private static final int READ_INDEX_BALANCE = 0;
    private static final int READ_INDEX_BLANDAPLSTATUS = 1;
    private static final int READ_INDEX_ENABLEFLG_VALUEISSUERAPLSTATUS = 2;
    private static final int READ_INDEX_POINT = 3;
    private static final int[][] READ_INFO_LIST = {new int[]{26647, 0}, new int[]{26571, 1}, new int[]{26635, 7}, new int[]{26699, 0}};
    private static final int READ_NUM = 3;
    private static final int READ_START_INDEX = 0;
    public static final String SERVICE_ID = "SV000011";
    private static final int SHIFT_BIT_NUM_BLANDAPLSTATUS = 7;
    private static final int SHIFT_BIT_NUM_ENABLEFLG = 6;
    private static final int SHIFT_BIT_NUM_VALUEISSUERAPLSTATUS = 7;
    private static final int TARGET_INDEX_BLANDAPLSTATUS = 12;
    private static final int TARGET_INDEX_ENABLEFLG = 4;
    private static final int TARGET_INDEX_VALUEISSUERAPLSTATUS = 0;

    public ExtWaonBalanceReader() {
        super(65024, new BlockList());
        for (int[] iArr : READ_INFO_LIST) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.ExtFelicaReader
    public MfcAdapterExpert.Asset getBalance(Data[] data) {
        try {
            MfcAdapterExpert.Asset asset = new MfcAdapterExpert.Asset("SV000011", -1, -1, -1, null, null);
            byte[] byteData = getByteData(data[2]);
            if (((byteData[4] >>> 6) & 3) != 0 && ((byteData[0] >>> 7) & 1) != 1 && ((getByteData(data[1])[12] >>> 7) & 1) != 1) {
                asset.balanceValue = getPurseData(data[0]);
                asset.point1 = new BigInteger(1, getTargetByte(getByteData(data[3]), 0, 3)).intValue();
                return asset;
            }
            return asset;
        } catch (FelicaReaderException unused) {
            return null;
        }
    }
}
