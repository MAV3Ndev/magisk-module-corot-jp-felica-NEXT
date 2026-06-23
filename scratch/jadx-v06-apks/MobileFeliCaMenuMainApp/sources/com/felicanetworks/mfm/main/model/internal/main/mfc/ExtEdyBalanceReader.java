package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mnlib.felica.Block;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;

/* JADX INFO: loaded from: classes.dex */
public class ExtEdyBalanceReader extends ExtFelicaReader {
    private static final int BLOCK_NO = 0;
    private static final int SERVICE_CODE = 4887;
    public static final String SERVICE_ID = "SV000013";

    public ExtEdyBalanceReader() {
        super(65024, new BlockList());
        this.blocklist.add(new Block(4887, 0));
    }

    @Override // com.felicanetworks.mfm.main.model.internal.main.mfc.ExtFelicaReader
    public MfcAdapterExpert.Asset getBalance(Data[] dataArr) {
        try {
            MfcAdapterExpert.Asset asset = new MfcAdapterExpert.Asset("SV000013", -1, -1, -1, null, null);
            asset.balanceValue = getPurseData(dataArr[0]);
            return asset;
        } catch (FelicaReaderException unused) {
            return null;
        }
    }
}
