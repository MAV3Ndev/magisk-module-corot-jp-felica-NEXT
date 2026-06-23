package com.felicanetworks.mfm.main.model.internal.main.mfc;

import com.felicanetworks.mfm.main.model.internal.main.mfc.MfcAdapterExpert;
import com.felicanetworks.mnlib.Felica;
import com.felicanetworks.mnlib.felica.Block;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import com.felicanetworks.mnlib.felica.FelicaException;
import com.felicanetworks.mnlib.felica.RandomData;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
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

    public BlockList parseCardInfoStandard(Data[] dataArr) throws FelicaReaderException {
        if (dataArr == null) {
            return null;
        }
        BlockList blockList = new BlockList();
        byte[] bArrConvertDataListToByteList = convertDataListToByteList(dataArr);
        this.pocketCount = bArrConvertDataListToByteList[4] & 255;
        byte[] bArr = new byte[8];
        System.arraycopy(bArrConvertDataListToByteList, 0, bArr, 4, 4);
        this.fpNum = ByteBuffer.wrap(bArr).getLong();
        int i = this.pocketCount;
        if (i < 1 || 20 < i) {
            return null;
        }
        int i2 = (((i * 11) + 16) - 1) / 16;
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

    public MfcAdapterExpert.FpArea parseIndexInfoStandard(Data[] dataArr) throws FelicaReaderException {
        if (dataArr == null) {
            return null;
        }
        byte[][] bArr = (byte[][]) Array.newInstance((Class<?>) byte.class, this.pocketCount, 11);
        byte[] bArrConvertDataListToByteList = convertDataListToByteList(dataArr);
        for (int i = 0; i < this.pocketCount; i++) {
            System.arraycopy(bArrConvertDataListToByteList, i * 11, bArr[i], 0, 11);
        }
        ArrayList arrayList = new ArrayList();
        for (byte[] bArr2 : bArr) {
            arrayList.add(new MfcAdapterExpert.FpArea.FpService(new byte[]{bArr2[0], bArr2[1], bArr2[2]}, new byte[]{bArr2[3], bArr2[4], bArr2[5], bArr2[6], bArr2[7], bArr2[8], bArr2[9], bArr2[10]}));
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
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mnlib.felica.BlockList parseCardInfoLite(com.felicanetworks.mnlib.felica.Data[] r9) throws com.felicanetworks.mfm.main.model.internal.main.mfc.FelicaReaderException {
        /*
            r8 = this;
            com.felicanetworks.mnlib.felica.BlockList r0 = new com.felicanetworks.mnlib.felica.BlockList
            r0.<init>()
            byte[] r9 = r8.convertDataListToByteList(r9)
            r1 = 40
            r1 = r9[r1]
            r2 = 0
            if (r1 != 0) goto L68
            r1 = 41
            r1 = r9[r1]
            r3 = 48
            if (r1 == r3) goto L19
            goto L68
        L19:
            r1 = 4
            r3 = r9[r1]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r8.pocketCount = r3
            r4 = 8
            r5 = r9[r4]
            r5 = r5 & 15
            r6 = 2
            r7 = 1
            if (r5 == r6) goto L38
            r6 = 3
            if (r5 == r6) goto L2e
            return r2
        L2e:
            r8.isReadWrite = r7
            int r5 = r3 + (-1)
            if (r5 < r7) goto L37
            int r3 = r3 - r7
            if (r6 >= r3) goto L3d
        L37:
            return r2
        L38:
            if (r3 < r7) goto L68
            if (r1 >= r3) goto L3d
            goto L68
        L3d:
            byte[] r2 = new byte[r4]
            r3 = 0
            java.lang.System.arraycopy(r9, r3, r2, r1, r1)
            java.nio.ByteBuffer r9 = java.nio.ByteBuffer.wrap(r2)
            long r1 = r9.getLong()
            r8.fpNum = r1
        L4d:
            int r9 = r8.pocketCount
            if (r3 >= r9) goto L67
            com.felicanetworks.mnlib.felica.Block r9 = new com.felicanetworks.mnlib.felica.Block
            r1 = 11
            r9.<init>(r1)
            int r1 = r8.pocketCount
            int r1 = 12 - r1
            int r1 = r1 + r3
            byte r1 = (byte) r1
            r9.setBlockNo(r1)
            r0.add(r9)
            int r3 = r3 + 1
            goto L4d
        L67:
            return r0
        L68:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.mfc.ExtFelicaPocketReader.parseCardInfoLite(com.felicanetworks.mnlib.felica.Data[]):com.felicanetworks.mnlib.felica.BlockList");
    }

    public Data parseIndexInfoA(Data[] dataArr) {
        return dataArr[1];
    }

    public Data[] readIndexInfoBLite(BlockList blockList) throws IOException, FelicaException {
        return this.felica.read(blockList);
    }

    public MfcAdapterExpert.FpArea parseIndexInfoLite(Data data, Data[] dataArr) throws FelicaReaderException {
        try {
            byte[] bArrConvertDataListToByteList = convertDataListToByteList(new Data[]{data});
            byte[] bArrConvertDataListToByteList2 = convertDataListToByteList(dataArr);
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

    private byte[] convertDataListToByteList(Data[] dataArr) throws FelicaReaderException {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(dataArr.length * 16);
        for (Data data : dataArr) {
            if (1 != data.getType()) {
                throw new FelicaReaderException(getClass(), 257);
            }
            byteBufferAllocate.put(((RandomData) data).getBytes());
        }
        return byteBufferAllocate.array();
    }
}
