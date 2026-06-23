package com.felicanetworks.mfm.main.model.internal.main.mfc;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;

/* JADX INFO: loaded from: classes3.dex */
public abstract class BalanceReader extends FelicaReader {
    static final int USE_MONEY_VALUE_MAX = 99999;
    static final int USE_MONEY_VALUE_MIN = 0;
    protected Boolean isHistory;

    protected BalanceReader(BlockList blocklist) {
        super(blocklist);
        this.isHistory = false;
    }

    protected BalanceReader(BlockList blocklist, Boolean isHistory) {
        super(blocklist);
        Boolean.valueOf(false);
        this.isHistory = isHistory;
    }

    public MfcExpert.Asset getBalance(String serviceId) throws FelicaReaderException {
        return getBalance(serviceId, "");
    }

    public MfcExpert.Asset getBalance(String serviceId, String cid) throws FelicaReaderException {
        checkDataExist(getClass(), InputDeviceCompat.SOURCE_DPAD);
        MfcExpert.Asset asset = new MfcExpert.Asset(serviceId, cid, -1, -1, -1, -1, null, null, false);
        asset.balanceValue = getPurseData(this.data[0]);
        return asset;
    }

    protected void checkDataExist(Class cls, int index) throws FelicaReaderException {
        if (!isDataExist()) {
            throw new FelicaReaderException(cls, index);
        }
    }
}
