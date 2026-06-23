package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mnlib.felica.Block;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import java.math.BigInteger;

/* JADX INFO: loaded from: classes3.dex */
public class ExtMobileSuicaBalanceReader extends ExtFelicaReader {
    private static final int BLOCK_NO = 0;
    private static final int READ_NUM = 3;
    private static final int READ_START_INDEX = 11;
    private static final int SERVICE_CODE = 139;

    public ExtMobileSuicaBalanceReader() {
        super(3, new BlockList());
        this.blocklist.add(new Block(139, 0));
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.ExtFelicaReader
    public MfcAdapterExpert.Asset getBalance(Data[] data) {
        try {
            byte[] byteData = getByteData(data[0]);
            MfcAdapterExpert.Asset asset = new MfcAdapterExpert.Asset(FeliCaParams.SERVICE_ID_SUICA, -1, -1, -1, null, null);
            asset.balanceValue = new BigInteger(CommonUtil.changeEndian(getTargetByte(byteData, 11, 3))).intValue();
            return asset;
        } catch (FelicaReaderException unused) {
            return null;
        }
    }
}
