package com.felicanetworks.mnlib.felica.internal;

import android.util.Log;
import com.felicanetworks.mnlib.felica.Block;
import com.felicanetworks.mnlib.felica.BlockData;
import com.felicanetworks.mnlib.felica.BlockDataList;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.CyclicData;
import com.felicanetworks.mnlib.felica.Data;
import com.felicanetworks.mnlib.felica.PurseCashBackData;
import com.felicanetworks.mnlib.felica.PurseData;
import com.felicanetworks.mnlib.felica.PurseDecrementData;
import com.felicanetworks.mnlib.felica.RandomData;
import com.felicanetworks.mnlib.felica.ServiceUtil;

/* JADX INFO: loaded from: classes.dex */
class DataUtil {
    private static final int BLOCK_DATA_LENGTH = 16;
    private static final int SYSTEM_CODE_LENGTH = 2;
    private static final String TAG = "NFC";
    private static final int USER_DATA_LENGTH = 6;

    private DataUtil() {
    }

    static void append(ByteBuffer byteBuffer, int[] iArr, int i) throws FelicaInternalException {
        int i2 = 0;
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        if (iArr == null || iArr.length == 0) {
            throw new FelicaInternalException(0);
        }
        if (i != 2 && i != 4) {
            throw new FelicaInternalException(0);
        }
        byteBuffer.append((byte) iArr.length);
        if (i == 4) {
            while (i2 < iArr.length) {
                byteBuffer.appendInLittleEndian(iArr[i2], 4);
                i2++;
            }
        } else {
            while (i2 < iArr.length) {
                byteBuffer.appendInLittleEndian(iArr[i2], 2);
                i2++;
            }
        }
    }

    static void append(ByteBuffer byteBuffer, BlockList blockList, int i) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        if (blockList == null || blockList.size() == 0) {
            throw new FelicaInternalException(0);
        }
        if (i != 2 && i != 4) {
            throw new FelicaInternalException(0);
        }
        int size = blockList.size();
        int i2 = size;
        for (int i3 = 1; i3 < size; i3++) {
            if (blockList.get(i3 - 1).getServiceCode() == blockList.get(i3).getServiceCode()) {
                i2--;
            }
        }
        byteBuffer.append((byte) i2);
        for (int i4 = 0; i4 < size; i4++) {
            if (i4 == 0) {
                append(byteBuffer, blockList.get(i4), i);
            } else if (blockList.get(i4 - 1).getServiceCode() != blockList.get(i4).getServiceCode()) {
                append(byteBuffer, blockList.get(i4), i);
            }
        }
        byteBuffer.append((byte) size);
        int i5 = -1;
        for (int i6 = 0; i6 < size; i6++) {
            Block block = blockList.get(i6);
            if (i6 == 0 || blockList.get(i6 - 1).getServiceCode() != block.getServiceCode()) {
                i5++;
            }
            append(byteBuffer, i5, block);
        }
    }

    static void append(ByteBuffer byteBuffer, BlockDataList blockDataList, int i) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        if (blockDataList == null || blockDataList.size() == 0) {
            throw new FelicaInternalException(0);
        }
        if (i != 2 && i != 4) {
            throw new FelicaInternalException(0);
        }
        int size = blockDataList.size();
        int i2 = size;
        for (int i3 = 1; i3 < size; i3++) {
            if (blockDataList.get(i3 - 1).getBlock().getServiceCode() == blockDataList.get(i3).getBlock().getServiceCode()) {
                i2--;
            }
        }
        byteBuffer.append((byte) i2);
        for (int i4 = 0; i4 < size; i4++) {
            if (i4 == 0) {
                append(byteBuffer, blockDataList.get(i4).getBlock(), i);
            } else if (blockDataList.get(i4 - 1).getBlock().getServiceCode() != blockDataList.get(i4).getBlock().getServiceCode()) {
                append(byteBuffer, blockDataList.get(i4).getBlock(), i);
            }
        }
        byteBuffer.append((byte) size);
        int i5 = -1;
        for (int i6 = 0; i6 < size; i6++) {
            BlockData blockData = blockDataList.get(i6);
            if (i6 == 0 || blockDataList.get(i6 - 1).getBlock().getServiceCode() != blockData.getBlock().getServiceCode()) {
                i5++;
            }
            append(byteBuffer, i5, blockData);
        }
        for (int i7 = 0; i7 < size; i7++) {
            append(byteBuffer, blockDataList.get(i7).getData());
        }
    }

    private static void append(ByteBuffer byteBuffer, Block block, int i) throws ArrayIndexOutOfBoundsException {
        if (i == 2) {
            byteBuffer.appendInLittleEndian(block.getServiceCode(), 2);
        } else {
            byteBuffer.appendInLittleEndian(block.getServiceCode(), 4);
        }
    }

    private static void append(ByteBuffer byteBuffer, int i, Block block) throws ArrayIndexOutOfBoundsException {
        byte b = (byte) (i & 15);
        int blockNo = block.getBlockNo();
        if (blockNo < 256) {
            byteBuffer.append((byte) (b | 128));
            byteBuffer.append((byte) blockNo);
        } else {
            byteBuffer.append(b);
            byteBuffer.appendInLittleEndian(blockNo, 2);
        }
    }

    private static void append(ByteBuffer byteBuffer, int i, BlockData blockData) throws ArrayIndexOutOfBoundsException {
        int i2 = (byte) (i & 15);
        if (blockData.getData().getType() == 4) {
            i2 |= 16;
        }
        int blockNo = blockData.getBlock().getBlockNo();
        if (blockNo < 256) {
            byteBuffer.append((byte) (i2 | 128));
            byteBuffer.append((byte) blockNo);
        } else {
            byteBuffer.append((byte) i2);
            byteBuffer.appendInLittleEndian(blockNo, 2);
        }
    }

    private static void append(ByteBuffer byteBuffer, Data data) throws ArrayIndexOutOfBoundsException, NullPointerException {
        int type = data.getType();
        if (type == 2) {
            append(byteBuffer, (CyclicData) data);
            return;
        }
        if (type == 3) {
            append(byteBuffer, (PurseData) data);
            return;
        }
        if (type == 4) {
            append(byteBuffer, (PurseCashBackData) data);
        } else if (type != 5) {
            append(byteBuffer, (RandomData) data);
        } else {
            append(byteBuffer, (PurseDecrementData) data);
        }
    }

    private static void append(ByteBuffer byteBuffer, RandomData randomData) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.append(randomData.getBytes());
    }

    private static void append(ByteBuffer byteBuffer, CyclicData cyclicData) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.append(cyclicData.getBytes());
    }

    private static void append(ByteBuffer byteBuffer, PurseData purseData) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.appendInLittleEndian(purseData.getPurseData(), 4);
        byteBuffer.appendInLittleEndian(purseData.getCashBackData(), 4);
        byteBuffer.append(purseData.getUserData());
        byteBuffer.appendInBigEndian(purseData.getExecID(), 2);
    }

    private static void append(ByteBuffer byteBuffer, PurseCashBackData purseCashBackData) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInLittleEndian(purseCashBackData.getCashBackData(), 4);
        for (int i = 0; i < 10; i++) {
            byteBuffer.append((byte) 0);
        }
        byteBuffer.appendInBigEndian(purseCashBackData.getExecID(), 2);
    }

    private static void append(ByteBuffer byteBuffer, PurseDecrementData purseDecrementData) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInLittleEndian(purseDecrementData.getDecrementData(), 4);
        for (int i = 0; i < 10; i++) {
            byteBuffer.append((byte) 0);
        }
        byteBuffer.appendInBigEndian(purseDecrementData.getExecID(), 2);
    }

    static int[] createKeyVersionList(ByteBuffer byteBuffer, int[] iArr) throws FelicaInternalException {
        try {
            int i = byteBuffer.get(10);
            if (i != iArr.length) {
                throw new FelicaInternalException(3);
            }
            int[] iArr2 = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                iArr2[i2] = (int) byteBuffer.getInLittleEndian(11 + (i2 * 2), 2);
            }
            return iArr2;
        } catch (FelicaInternalException e) {
            throw e;
        }
    }

    static Data[] createDataList(ByteBuffer byteBuffer, BlockList blockList) throws FelicaInternalException {
        try {
            int i = byteBuffer.get(12);
            if (i != blockList.size()) {
                Log.w(TAG, "createDataList  : INVALID block size");
                throw new FelicaInternalException(3);
            }
            int i2 = 13;
            Data[] dataArr = new Data[i];
            for (int i3 = 0; i3 < i; i3++) {
                switch (ServiceUtil.getBlockType(blockList.get(i3).getServiceCode())) {
                    case 1:
                    case 2:
                        byte[] bArr = new byte[16];
                        byteBuffer.copy(i2, bArr);
                        dataArr[i3] = new CyclicData(bArr);
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        long inLittleEndian = byteBuffer.getInLittleEndian(i2, 4);
                        int i4 = i2 + 4;
                        long inLittleEndian2 = byteBuffer.getInLittleEndian(i4, 4);
                        int i5 = i4 + 4;
                        byte[] bArr2 = new byte[6];
                        byteBuffer.copy(i5, bArr2);
                        int i6 = i5 + 6;
                        int inBigEndian = (int) byteBuffer.getInBigEndian(i6, 2);
                        i2 = i6 + 2;
                        dataArr[i3] = new PurseData(inLittleEndian, inLittleEndian2, bArr2, inBigEndian);
                        continue;
                        break;
                    case 7:
                    case 8:
                        byte[] bArr3 = new byte[16];
                        byteBuffer.copy(i2, bArr3);
                        dataArr[i3] = new RandomData(bArr3);
                        break;
                    default:
                        Log.w(TAG, "createDataList  : INVALID block type");
                        throw new RuntimeException("INVALID block type");
                }
                i2 += 16;
            }
            return dataArr;
        } catch (FelicaInternalException e) {
            Log.w(TAG, "createDataList  : catch and throw FelicaInternalException");
            throw e;
        }
    }

    static int[] createSystemCode(ByteBuffer byteBuffer) {
        int i = byteBuffer.get(10) & 255;
        int[] iArr = new int[i];
        int i2 = 11;
        for (int i3 = 0; i3 < i; i3++) {
            iArr[i3] = (int) (byteBuffer.getInBigEndian(i2, 2) & 65535);
            i2 += 2;
        }
        return iArr;
    }
}
