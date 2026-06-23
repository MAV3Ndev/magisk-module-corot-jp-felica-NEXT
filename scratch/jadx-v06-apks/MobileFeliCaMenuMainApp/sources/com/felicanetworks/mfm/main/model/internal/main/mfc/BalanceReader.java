package com.felicanetworks.mfm.main.model.internal.main.mfc;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;

/* JADX INFO: loaded from: classes.dex */
public abstract class BalanceReader extends FelicaReader {
    static final int USE_MONEY_VALUE_MAX = 99999;
    static final int USE_MONEY_VALUE_MIN = 0;
    protected Boolean isHistory;

    protected BalanceReader(BlockList blockList) {
        super(blockList);
        this.isHistory = false;
    }

    protected BalanceReader(BlockList blockList, Boolean bool) {
        super(blockList);
        this.isHistory = false;
        this.isHistory = bool;
    }

    public MfcExpert.Asset getBalance(String str) throws FelicaReaderException {
        return getBalance(str, "");
    }

    public MfcExpert.Asset getBalance(String str, String str2) throws FelicaReaderException {
        checkDataExist(getClass(), InputDeviceCompat.SOURCE_DPAD);
        MfcExpert.Asset asset = new MfcExpert.Asset(str, str2, -1, -1, -1, -1, null, null, false);
        asset.balanceValue = getPurseData(this.data[0]);
        return asset;
    }

    protected void checkDataExist(Class cls, int i) throws FelicaReaderException {
        if (!isDataExist()) {
            throw new FelicaReaderException(cls, i);
        }
    }
}
