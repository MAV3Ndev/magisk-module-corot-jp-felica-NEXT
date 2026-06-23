package com.felicanetworks.mfm.main.model.internal.main.mfc;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.CyclicData;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.PurseData;
import com.felicanetworks.mfc.RandomData;
import com.felicanetworks.mfm.mfcutil.mfc.Felica;

/* JADX INFO: loaded from: classes.dex */
public abstract class FelicaReader {
    private static final int MAX_READ_BLOCK_SIZE = 15;
    private static final int TYPE_CYCLICDATA = 2;
    private static final int TYPE_PURSEDATA = 3;
    private static final int TYPE_RANDOMDATA = 1;
    protected BlockList blocklist;
    protected Data[] data = null;

    FelicaReader(BlockList blockList) {
        this.blocklist = null;
        this.blocklist = blockList;
    }

    public void initializeDataList() {
        this.data = null;
    }

    public void readData(Felica felica) throws FelicaException {
        BlockList blockList = this.blocklist;
        if (blockList == null) {
            return;
        }
        try {
            if (blockList.size() > 15) {
                BlockList blockList2 = new BlockList();
                BlockList blockList3 = new BlockList();
                for (int i = 0; i < 15; i++) {
                    blockList2.add(this.blocklist.get(i));
                }
                for (int i2 = 15; i2 < this.blocklist.size(); i2++) {
                    blockList3.add(this.blocklist.get(i2));
                }
                Data[] dataArr = felica.read(blockList2);
                Data[] dataArr2 = felica.read(blockList3);
                Data[] dataArr3 = new Data[dataArr.length + dataArr2.length];
                this.data = dataArr3;
                System.arraycopy(dataArr, 0, dataArr3, 0, dataArr.length);
                System.arraycopy(dataArr2, 0, this.data, dataArr.length, dataArr2.length);
                return;
            }
            this.data = felica.read(this.blocklist);
        } catch (FelicaException e) {
            if (e.getType() != 11 && e.getType() != 12 && e.getType() != 13) {
                throw e;
            }
        }
    }

    protected boolean isDataExist() {
        Data[] dataArr = this.data;
        return dataArr != null && dataArr.length == this.blocklist.size();
    }

    protected int getPurseData(Data data) throws FelicaReaderException {
        if (3 != data.getType()) {
            throw new FelicaReaderException(getClass(), 528);
        }
        return (int) ((PurseData) data).getPurseData();
    }

    protected byte[] getByteData(Data data) throws FelicaReaderException {
        if (1 != data.getType()) {
            throw new FelicaReaderException(getClass(), 564);
        }
        return ((RandomData) data).getBytes();
    }

    protected byte[] getCyclicData(Data data) throws FelicaReaderException {
        if (2 != data.getType()) {
            throw new FelicaReaderException(getClass(), InputDeviceCompat.SOURCE_DPAD);
        }
        return ((CyclicData) data).getBytes();
    }

    protected byte[] getTargetByte(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }
}
