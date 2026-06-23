package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mnlib.felica.Block;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ExtNanacoBalanceReader extends ExtFelicaReader {
    private static final int EXPIRATION_DATE_1_READ_NUM = 2;
    private static final int EXPIRATION_DATE_1_START_INDEX = 3;
    private static final int EXPIRATION_DATE_2_READ_NUM = 2;
    private static final int EXPIRATION_DATE_2_START_INDEX = 8;
    private static final int MASK_VALUE_DAY = 31;
    private static final int MASK_VALUE_MONTH1 = 8;
    private static final int MASK_VALUE_MONTH2 = 7;
    private static final int MASK_VALUE_YEAR = 127;
    private static final int POINT_1_READ_NUM = 3;
    private static final int POINT_1_READ_START_INDEX = 0;
    private static final int POINT_2_READ_NUM = 3;
    private static final int POINT_2_READ_START_INDEX = 5;
    private static final int READ_INDEX_POINT = 1;
    private static final int[][] READ_INFO_LIST = {new int[]{21911, 0}, new int[]{22027, 1}};
    public static final String SERVICE_ID = "SV000075";
    private static final int SHIFT_BIT_NUM_MONTH1 = 3;
    private static final int SHIFT_BIT_NUM_MONTH2 = 5;
    private static final int SHIFT_BIT_NUM_YEAR = 1;

    public ExtNanacoBalanceReader() {
        super(65024, new BlockList());
        for (int[] iArr : READ_INFO_LIST) {
            this.blocklist.add(new Block(iArr[0], iArr[1]));
        }
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.ExtFelicaReader
    public MfcAdapterExpert.Asset getBalance(Data[] dataArr) {
        MfcAdapterExpert.Asset asset = new MfcAdapterExpert.Asset("SV000075", -1, -1, -1, null, null);
        try {
            asset.balanceValue = getPurseData(dataArr[0]);
            byte[] byteData = getByteData(dataArr[1]);
            asset.point1 = new BigInteger(1, getTargetByte(byteData, 0, 3)).intValue();
            byte[] targetByte = getTargetByte(byteData, 3, 2);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            int i = (targetByte[0] >>> 1) & MASK_VALUE_YEAR;
            int i2 = targetByte[1] & 31;
            asset.date1 = simpleDateFormat.parse((i + 2000) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(((targetByte[0] << 3) & 8) + ((targetByte[1] >>> 5) & 7))) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(i2)));
            asset.point2 = new BigInteger(1, getTargetByte(byteData, 5, 3)).intValue();
            byte[] targetByte2 = getTargetByte(byteData, 8, 2);
            int i3 = (targetByte2[0] >>> 1) & MASK_VALUE_YEAR;
            int i4 = targetByte2[1] & 31;
            asset.date2 = simpleDateFormat.parse((i3 + 2000) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(((targetByte2[0] << 3) & 8) + ((targetByte2[1] >>> 5) & 7))) + "/" + String.format(Locale.US, "%02d", Integer.valueOf(i4)));
            return asset;
        } catch (FelicaReaderException | ParseException unused) {
            return null;
        }
    }
}
