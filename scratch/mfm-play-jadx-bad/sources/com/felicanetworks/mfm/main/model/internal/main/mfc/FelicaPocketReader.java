package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.RandomData;
import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcExpert;
import com.felicanetworks.mfm.mfcutil.mfc.Felica;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaPocketReader {
    private static final int BLOCK_SIZE = 16;
    private static final int INDEX_INFO_SIZE = 11;
    private static final int STANDARD_CARD_IDS_BLOCK_CODE = 0;
    private static final int STANDARD_CARD_IDS_SERVICE_CODE = 14667;
    private static final int STANDARD_INDEX_SERVICE_CODE = 14793;
    private static final int STANDARD_POCKET_MAX_SIZE = 20;
    private static final int STANDARD_POCKET_MIN_SIZE = 1;
    private Felica felica;
    private long fpNum;
    private int pocketCount = 0;

    public FelicaPocketReader(Felica felica) {
        this.felica = felica;
    }

    public Data[] readCardIdInfo() throws FelicaException {
        BlockList blockList = new BlockList();
        blockList.add(0, new Block(14667, 0));
        try {
            return this.felica.read(blockList);
        } catch (FelicaException e) {
            if (e.getType() == 11 || e.getType() == 12 || e.getType() == 13) {
                return null;
            }
            throw e;
        }
    }

    public BlockList parseCardInfo(Data[] datas) throws FelicaReaderException {
        if (datas == null) {
            return null;
        }
        BlockList blockList = new BlockList();
        byte[] bArrConvertDataListToByteList = convertDataListToByteList(datas);
        this.pocketCount = bArrConvertDataListToByteList[4] & 255;
        byte[] bArr = new byte[8];
        System.arraycopy(bArrConvertDataListToByteList, 0, bArr, 4, 4);
        this.fpNum = ByteBuffer.wrap(bArr).getLong();
        int i = this.pocketCount;
        if (i < 1 || 20 < i) {
            return null;
        }
        int i2 = ((i * 11) + 15) / 16;
        for (int i3 = 0; i3 < i2; i3++) {
            Block block = new Block(14793);
            block.setBlockNo(i3);
            blockList.add(block);
        }
        return blockList;
    }

    public Data[] readIndexInfo(BlockList blockList) throws FelicaException, IllegalArgumentException {
        if (blockList == null || 14 < blockList.size()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (blockList.size() > 12) {
            BlockList blockList2 = new BlockList();
            for (int i = 0; i < 12; i++) {
                blockList2.add(blockList.get(i));
            }
            arrayList.addAll(Collections.singletonList(blockList2));
            BlockList blockList3 = new BlockList();
            for (int i2 = 12; i2 < blockList.size(); i2++) {
                blockList3.add(blockList.get(i2));
            }
            arrayList.addAll(Collections.singletonList(blockList3));
        } else {
            arrayList.add(blockList);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                arrayList2.addAll(Arrays.asList(this.felica.read((BlockList) it.next())));
            } catch (FelicaException e) {
                if (e.getType() == 11 || e.getType() == 12 || e.getType() == 13) {
                    return null;
                }
                throw e;
            }
        }
        return (Data[]) arrayList2.toArray(new Data[arrayList2.size()]);
    }

    public MfcExpert.FpArea parseIndexInfo(Data[] dataList) throws FelicaReaderException {
        if (dataList == null) {
            return null;
        }
        char c = 2;
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, this.pocketCount, 11);
        byte[] bArrConvertDataListToByteList = convertDataListToByteList(dataList);
        for (int i = 0; i < this.pocketCount; i++) {
            System.arraycopy(bArrConvertDataListToByteList, i * 11, bArr[i], 0, 11);
        }
        ArrayList arrayList = new ArrayList();
        int length = bArr.length;
        int i2 = 0;
        while (i2 < length) {
            byte[] bArr2 = bArr[i2];
            byte b = bArr2[0];
            byte b2 = bArr2[1];
            byte b3 = bArr2[c];
            byte[] bArr3 = new byte[3];
            bArr3[0] = b;
            bArr3[1] = b2;
            bArr3[c] = b3;
            byte b4 = bArr2[3];
            byte b5 = bArr2[4];
            byte b6 = bArr2[5];
            byte b7 = bArr2[6];
            byte b8 = bArr2[7];
            char c2 = c;
            byte b9 = bArr2[8];
            byte b10 = bArr2[9];
            byte b11 = bArr2[10];
            byte[] bArr4 = new byte[8];
            bArr4[0] = b4;
            bArr4[1] = b5;
            bArr4[c2] = b6;
            bArr4[3] = b7;
            bArr4[4] = b8;
            bArr4[5] = b9;
            bArr4[6] = b10;
            bArr4[7] = b11;
            arrayList.add(new MfcExpert.FpArea.FpService(bArr3, bArr4));
            i2++;
            c = c2;
        }
        return new MfcExpert.FpArea(this.fpNum, this.pocketCount, arrayList);
    }

    private byte[] convertDataListToByteList(Data[] dataList) throws FelicaReaderException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(dataList.length * 16);
        for (Data data : dataList) {
            if (1 != data.getType()) {
                throw new FelicaReaderException(getClass(), 257);
            }
            byteBufferAllocate.put(((RandomData) data).getBytes());
        }
        return byteBufferAllocate.array();
    }
}
