package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mnlib.Felica;
import com.felicanetworks.mnlib.felica.Block;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import com.felicanetworks.mnlib.felica.FelicaException;
import com.felicanetworks.mnlib.felica.RandomData;
import com.google.common.base.Ascii;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes3.dex */
public class ExtFelicaPocketReader {
    private static final int BLOCK_SIZE = 16;
    private static final int INDEX_INFO_SIZE = 11;
    private static final int[] LITE_CARD_DFC_BLOCK_CODE_LIST = {13, 12, 130};
    private static final int LITE_CARD_IDS_BLOCK = 13;
    private static final int LITE_DATA_FORMAT_CODE_BLOCK = 130;
    private static final int LITE_INDEX_A_BLOCK = 12;
    private static final int LITE_INDEX_A_BLOCK_NO = 1;
    private static final int LITE_POCKET_MAX_SIZE = 4;
    private static final int LITE_POCKET_MIN_SIZE = 1;
    private static final int LITE_SERVICE_CODE = 11;
    private static final int STANDARD_CARD_IDS_BLOCK_CODE = 0;
    private static final int STANDARD_CARD_IDS_SERVICE_CODE = 14667;
    private static final int STANDARD_INDEX_SERVICE_CODE = 14793;
    private static final int STANDARD_POCKET_MAX_SIZE = 20;
    private static final int STANDARD_POCKET_MIN_SIZE = 1;
    private Felica felica;
    private int pocketCount = 0;
    private long fpNum = 0;
    private boolean isReadWrite = false;

    public ExtFelicaPocketReader(Felica felica) {
        this.felica = felica;
    }

    public Data[] readCardIdInfoStandard() throws FelicaException, IOException {
        BlockList blockList = new BlockList();
        blockList.add(0, new Block(14667, 0));
        return this.felica.read(blockList);
    }

    public BlockList parseCardInfoStandard(Data[] cardIds) throws FelicaReaderException {
        if (cardIds == null) {
            return null;
        }
        BlockList blockList = new BlockList();
        byte[] bArrConvertDataListToByteList = convertDataListToByteList(cardIds);
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

    public Data[] readIndexInfoStandard(BlockList blockList) throws IOException, FelicaException {
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
            arrayList2.addAll(Arrays.asList(this.felica.read((BlockList) it.next())));
        }
        return (Data[]) arrayList2.toArray(new Data[arrayList2.size()]);
    }

    public MfcAdapterExpert.FpArea parseIndexInfoStandard(Data[] datas) throws FelicaReaderException {
        if (datas == null) {
            return null;
        }
        char c = 2;
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, this.pocketCount, 11);
        byte[] bArrConvertDataListToByteList = convertDataListToByteList(datas);
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
            arrayList.add(new MfcAdapterExpert.FpArea.FpService(bArr3, bArr4));
            i2++;
            c = c2;
        }
        return new MfcAdapterExpert.FpArea(this.fpNum, this.pocketCount, arrayList, false);
    }

    public Data[] readCardIdInfoLite() throws FelicaException, IOException {
        BlockList blockList = new BlockList();
        int i = 0;
        while (true) {
            int[] iArr = LITE_CARD_DFC_BLOCK_CODE_LIST;
            if (i < iArr.length) {
                blockList.add(i, new Block(11, iArr[i]));
                i++;
            } else {
                return this.felica.read(blockList);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x003a, code lost:
    
        if (4 >= r3) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public BlockList parseCardInfoLite(Data[] cardIds) throws FelicaReaderException {
        BlockList blockList = new BlockList();
        byte[] bArrConvertDataListToByteList = convertDataListToByteList(cardIds);
        if (bArrConvertDataListToByteList[40] == 0 && bArrConvertDataListToByteList[41] == 48) {
            int i = bArrConvertDataListToByteList[4] & 255;
            this.pocketCount = i;
            int i2 = bArrConvertDataListToByteList[8] & Ascii.SI;
            if (i2 != 2) {
                if (i2 != 3) {
                    return null;
                }
                this.isReadWrite = true;
                if (i - 1 < 1 || 3 < i - 1) {
                    return null;
                }
            } else if (i >= 1) {
            }
            byte[] bArr = new byte[8];
            System.arraycopy(bArrConvertDataListToByteList, 0, bArr, 4, 4);
            this.fpNum = ByteBuffer.wrap(bArr).getLong();
            for (int i3 = 0; i3 < this.pocketCount; i3++) {
                Block block = new Block(11);
                block.setBlockNo((byte) ((12 - this.pocketCount) + i3));
                blockList.add(block);
            }
            return blockList;
        }
        return null;
    }

    public Data parseIndexInfoA(Data[] cardIds) {
        return cardIds[1];
    }

    public Data[] readIndexInfoBLite(BlockList blockList) throws IOException, FelicaException {
        return this.felica.read(blockList);
    }

    public MfcAdapterExpert.FpArea parseIndexInfoLite(Data indexA, Data[] indexB) throws FelicaReaderException {
        try {
            byte[] bArrConvertDataListToByteList = convertDataListToByteList(new Data[]{indexA});
            byte[] bArrConvertDataListToByteList2 = convertDataListToByteList(indexB);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.pocketCount; i++) {
                byte[] bArr = new byte[3];
                System.arraycopy(bArrConvertDataListToByteList, i * 4, bArr, 0, 3);
                byte[] bArr2 = new byte[8];
                System.arraycopy(bArrConvertDataListToByteList2, i * 16, bArr2, 0, 8);
                arrayList.add(new MfcAdapterExpert.FpArea.FpService(bArr, bArr2));
            }
            return new MfcAdapterExpert.FpArea(this.fpNum, this.pocketCount, arrayList, this.isReadWrite);
        } catch (Exception e) {
            throw new FelicaReaderException(getClass(), 3, e);
        }
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
