package com.felicanetworks.mfm.main.model.internal.main.mfc;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import com.felicanetworks.mnlib.felica.PurseData;
import com.felicanetworks.mnlib.felica.RandomData;

/* JADX INFO: loaded from: classes.dex */
public abstract class ExtFelicaReader {
    public static final int COMMON_SYSTEM_CODE = 65024;
    public static final int CYBERNE_SYSTEM_CODE = 3;
    private static final int TYPE_PURSEDATA = 3;
    private static final int TYPE_RANDOMDATA = 1;
    public BlockList blocklist;
    public int systemCode;

    public abstract MfcAdapterExpert.Asset getBalance(Data[] dataArr);

    protected ExtFelicaReader(int i, BlockList blockList) {
        this.blocklist = null;
        this.systemCode = 0;
        this.systemCode = i;
        this.blocklist = blockList;
    }

    protected int getPurseData(Data data) throws FelicaReaderException {
        if (3 != data.getType()) {
            throw new FelicaReaderException(getClass(), 593);
        }
        return (int) ((PurseData) data).getPurseData();
    }

    protected byte[] getByteData(Data data) throws FelicaReaderException {
        if (1 != data.getType()) {
            throw new FelicaReaderException(getClass(), TypedValues.Motion.TYPE_POLAR_RELATIVETO);
        }
        return ((RandomData) data).getBytes();
    }

    protected byte[] getTargetByte(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }
}
