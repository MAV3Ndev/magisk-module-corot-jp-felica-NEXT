package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.AreaInformation;
import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockData;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.CyclicData;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PINChangeData;
import com.felicanetworks.mfc.PINCheckData;
import com.felicanetworks.mfc.PINEnableData;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PurseCashBackData;
import com.felicanetworks.mfc.PurseData;
import com.felicanetworks.mfc.PurseDecrementData;
import com.felicanetworks.mfc.RandomData;
import com.felicanetworks.mfc.ServiceUtil;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class DataUtil {
    private static final int BLOCK_DATA_LENGTH = 16;
    private static final int USERDATA_LENGTH = 6;
    private static DataUtil sInstance;

    private DataUtil() {
    }

    static DataUtil getInstance() {
        if (sInstance == null) {
            sInstance = new DataUtil();
        }
        return sInstance;
    }

    void append(ByteBuffer byteBuffer, int[] iArr, int i) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        if (iArr == null || iArr.length == 0) {
            throw new OfflineException(1);
        }
        if (i != 2 && i != 4) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append((byte) iArr.length);
            int i2 = 0;
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
        } catch (Exception unused) {
            throw new OfflineException(2);
        }
    }

    void append(ByteBuffer byteBuffer, BlockList blockList, int i) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        if (blockList == null || blockList.size() == 0) {
            throw new OfflineException(1);
        }
        if (i != 2 && i != 4) {
            throw new OfflineException(1);
        }
        try {
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
        } catch (Exception unused) {
            throw new OfflineException(2);
        }
    }

    void append(ByteBuffer byteBuffer, BlockDataList blockDataList, int i) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        if (blockDataList == null || blockDataList.size() == 0) {
            throw new OfflineException(1);
        }
        if (i != 2 && i != 4) {
            throw new OfflineException(1);
        }
        try {
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
        } catch (Exception unused) {
            throw new OfflineException(2);
        }
    }

    void append(ByteBuffer byteBuffer, PrivacySettingData[] privacySettingDataArr) throws OfflineException {
        if (byteBuffer == null) {
            LogMgr.log(1, "%s byteBuffer == null", "800");
            throw new OfflineException(1);
        }
        if (privacySettingDataArr == null) {
            LogMgr.log(1, "%s privacySettingDataList == null", "801");
            throw new OfflineException(1);
        }
        int length = privacySettingDataArr.length;
        if (length < 1 || length > 15) {
            LogMgr.log(1, "%s privacySettingDataList.length == %d", "802", Integer.valueOf(length));
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append((byte) length);
            for (PrivacySettingData privacySettingData : privacySettingDataArr) {
                byteBuffer.appendInLittleEndian(privacySettingData.getNodeCode(), 4);
            }
            for (PrivacySettingData privacySettingData2 : privacySettingDataArr) {
                byteBuffer.append(privacySettingData2.getPrivacySetting() ? (byte) 1 : (byte) 0);
            }
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", (Object) 800, (Object) e.toString());
            throw new OfflineException(2);
        }
    }

    private void append(ByteBuffer byteBuffer, Block block, int i) throws ArrayIndexOutOfBoundsException {
        if (i == 2) {
            byteBuffer.appendInLittleEndian(block.getServiceCode(), 2);
        } else {
            byteBuffer.appendInLittleEndian(block.getServiceCode(), 4);
        }
    }

    private void append(ByteBuffer byteBuffer, int i, Block block) throws ArrayIndexOutOfBoundsException {
        int i2 = (byte) (i & 15);
        switch (block.getType()) {
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
                i2 |= 32;
                break;
        }
        int blockNo = block.getBlockNo();
        if (blockNo < 256) {
            byteBuffer.append((byte) (i2 | 128));
            byteBuffer.append((byte) blockNo);
        } else {
            byteBuffer.append((byte) i2);
            byteBuffer.appendInLittleEndian(blockNo, 2);
        }
    }

    private void append(ByteBuffer byteBuffer, int i, BlockData blockData) throws ArrayIndexOutOfBoundsException {
        int i2 = (byte) (i & 15);
        int type = blockData.getData().getType();
        if (type == 4 || type == 7) {
            i2 |= 16;
        } else if (type == 8) {
            i2 |= 32;
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

    private void append(ByteBuffer byteBuffer, Data data) throws ArrayIndexOutOfBoundsException, NullPointerException {
        switch (data.getType()) {
            case 2:
                append(byteBuffer, (CyclicData) data);
                break;
            case 3:
                append(byteBuffer, (PurseData) data);
                break;
            case 4:
                append(byteBuffer, (PurseCashBackData) data);
                break;
            case 5:
                append(byteBuffer, (PurseDecrementData) data);
                break;
            case 6:
                append(byteBuffer, (PINCheckData) data);
                break;
            case 7:
                append(byteBuffer, (PINChangeData) data);
                break;
            case 8:
                append(byteBuffer, (PINEnableData) data);
                break;
            default:
                append(byteBuffer, (RandomData) data);
                break;
        }
    }

    private void append(ByteBuffer byteBuffer, RandomData randomData) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.append(randomData.getBytes());
    }

    private void append(ByteBuffer byteBuffer, CyclicData cyclicData) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.append(cyclicData.getBytes());
    }

    private void append(ByteBuffer byteBuffer, PurseData purseData) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.appendInLittleEndian(purseData.getPurseData(), 4);
        byteBuffer.appendInLittleEndian(purseData.getCashBackData(), 4);
        byteBuffer.append(purseData.getUserData());
        byteBuffer.appendInBigEndian(purseData.getExecID(), 2);
    }

    private void append(ByteBuffer byteBuffer, PurseCashBackData purseCashBackData) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInLittleEndian(purseCashBackData.getCashBackData(), 4);
        for (int i = 0; i < 10; i++) {
            byteBuffer.append((byte) 0);
        }
        byteBuffer.appendInBigEndian(purseCashBackData.getExecID(), 2);
    }

    private void append(ByteBuffer byteBuffer, PurseDecrementData purseDecrementData) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInLittleEndian(purseDecrementData.getDecrementData(), 4);
        for (int i = 0; i < 10; i++) {
            byteBuffer.append((byte) 0);
        }
        byteBuffer.appendInBigEndian(purseDecrementData.getExecID(), 2);
    }

    private void append(ByteBuffer byteBuffer, PINCheckData pINCheckData) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInBigEndian(pINCheckData.getPIN(), 4);
        for (int i = 0; i < 12; i++) {
            byteBuffer.append((byte) 0);
        }
    }

    private void append(ByteBuffer byteBuffer, PINChangeData pINChangeData) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInBigEndian(pINChangeData.getPIN(), 4);
        byteBuffer.appendInBigEndian(pINChangeData.getNewPIN(), 4);
        for (int i = 0; i < 8; i++) {
            byteBuffer.append((byte) 0);
        }
    }

    private void append(ByteBuffer byteBuffer, PINEnableData pINEnableData) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInBigEndian(pINEnableData.getPIN(), 4);
        for (int i = 0; i < 4; i++) {
            byteBuffer.append((byte) 0);
        }
        if (pINEnableData.isEnabling()) {
            byteBuffer.append((byte) -68);
        } else {
            byteBuffer.append((byte) 60);
        }
        for (int i2 = 0; i2 < 7; i2++) {
            byteBuffer.append((byte) 0);
        }
    }

    int[] createKeyVersionList(ByteBuffer byteBuffer, int[] iArr) throws OfflineException {
        try {
            int i = byteBuffer.get(10);
            if (i != iArr.length) {
                throw new OfflineException(4);
            }
            int[] iArr2 = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                iArr2[i2] = (int) byteBuffer.getInLittleEndian(11 + (i2 * 2), 2);
            }
            return iArr2;
        } catch (OfflineException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new OfflineException(4);
        }
    }

    int[] createKeyVersionV2FirstList(ByteBuffer byteBuffer, int[] iArr) throws OfflineException {
        try {
            int i = byteBuffer.get(13);
            if (i != iArr.length) {
                throw new OfflineException(4);
            }
            int[] iArr2 = new int[i];
            for (int i2 = 0; i2 < i; i2++) {
                iArr2[i2] = (int) byteBuffer.getInLittleEndian((i2 * 2) + 14, 2);
            }
            return iArr2;
        } catch (OfflineException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new OfflineException(4);
        }
    }

    int[] createKeyVersionV2SecondList(ByteBuffer byteBuffer, int[] iArr) throws OfflineException {
        try {
            int i = byteBuffer.get(13);
            if (i != iArr.length) {
                throw new OfflineException(4);
            }
            int[] iArr2 = new int[i];
            int i2 = 14 + (i * 2);
            for (int i3 = 0; i3 < i; i3++) {
                iArr2[i3] = (int) byteBuffer.getInLittleEndian((i3 * 2) + i2, 2);
            }
            return iArr2;
        } catch (OfflineException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new OfflineException(4);
        }
    }

    Data[] createDataList(ByteBuffer byteBuffer, BlockList blockList) throws OfflineException {
        try {
            int i = byteBuffer.get(12);
            if (i != blockList.size()) {
                LogMgr.log(1, "%s %s", "800", "INVALID block size");
                throw new OfflineException(4);
            }
            Data[] dataArr = new Data[i];
            int i2 = 13;
            for (int i3 = 0; i3 < i; i3++) {
                switch (ServiceUtil.getInstance().getBlockType(blockList.get(i3).getServiceCode())) {
                    case 1:
                    case 2:
                        byte[] bArr = new byte[16];
                        byteBuffer.copy(i2, bArr);
                        dataArr[i3] = new RandomData(bArr);
                        break;
                    case 3:
                    case 4:
                        byte[] bArr2 = new byte[16];
                        byteBuffer.copy(i2, bArr2);
                        dataArr[i3] = new CyclicData(bArr2);
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        long inLittleEndian = byteBuffer.getInLittleEndian(i2, 4);
                        int i4 = i2 + 4;
                        long inLittleEndian2 = byteBuffer.getInLittleEndian(i4, 4);
                        int i5 = i4 + 4;
                        byte[] bArr3 = new byte[6];
                        byteBuffer.copy(i5, bArr3);
                        int i6 = i5 + 6;
                        int inBigEndian = (int) byteBuffer.getInBigEndian(i6, 2);
                        i2 = i6 + 2;
                        dataArr[i3] = new PurseData(inLittleEndian, inLittleEndian2, bArr3, inBigEndian);
                        continue;
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                        byte[] bArr4 = new byte[16];
                        byteBuffer.copy(i2, bArr4);
                        i2 += 16;
                        if ((bArr4[8] & IllegalStateErrorException.TYPE_ILLEGAL_STATE) == 0) {
                            dataArr[i3] = new PINEnableData(0L, false);
                            continue;
                        } else {
                            dataArr[i3] = new PINEnableData(0L, true);
                        }
                        break;
                    default:
                        LogMgr.log(1, "%s %s", "801", "INVALID block type");
                        throw new OfflineException(0);
                }
                i2 += 16;
            }
            return dataArr;
        } catch (OfflineException e) {
            LogMgr.log(1, "%s %s", "802", e.toString());
            throw e;
        } catch (Exception e2) {
            LogMgr.log(1, "%s %s", "803", e2.toString());
            throw new OfflineException(4);
        }
    }

    int[] createSystemCode(ByteBuffer byteBuffer) throws OfflineException {
        try {
            int i = byteBuffer.get(10) & 255;
            int i2 = 11;
            int[] iArr = new int[i];
            for (int i3 = 0; i3 < i; i3++) {
                iArr[i3] = (int) (byteBuffer.getInBigEndian(i2, 2) & 65535);
                i2 += 2;
            }
            return iArr;
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", "800", e.toString());
            throw new OfflineException(4);
        }
    }

    BlockCountInformation[] createBlockCountInformation(ByteBuffer byteBuffer, int[] iArr) throws OfflineException {
        try {
            int i = byteBuffer.get(12) & 255;
            if (i != iArr.length) {
                LogMgr.log(1, "%s INVALID node length", "800");
                throw new OfflineException(4);
            }
            int i2 = 13;
            BlockCountInformation[] blockCountInformationArr = new BlockCountInformation[i];
            for (int i3 = 0; i3 < i; i3++) {
                int inLittleEndian = (int) (byteBuffer.getInLittleEndian(i2, 2) & 65535);
                int i4 = i2 + 2;
                int inLittleEndian2 = (int) (byteBuffer.getInLittleEndian(i4, 2) & 65535);
                i2 = i4 + 2;
                blockCountInformationArr[i3] = new BlockCountInformation(inLittleEndian, inLittleEndian2);
            }
            return blockCountInformationArr;
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", "801", e.toString());
            throw new OfflineException(4);
        }
    }

    NodeInformation createNodeInformation(ByteBuffer byteBuffer, int i, int i2) throws OfflineException {
        try {
            int i3 = byteBuffer.get(i2) & 255;
            AreaInformation[] areaInformationArr = new AreaInformation[i3];
            int i4 = i2 + 1;
            for (int i5 = 0; i5 < i3; i5++) {
                int inLittleEndian = (int) byteBuffer.getInLittleEndian(i4, i);
                int i6 = i4 + i;
                int inLittleEndian2 = (int) byteBuffer.getInLittleEndian(i6, i);
                i4 = i6 + i;
                areaInformationArr[i5] = new AreaInformation(inLittleEndian, inLittleEndian2);
            }
            int i7 = byteBuffer.get(i4) & 255;
            int i8 = i4 + 1;
            int[] iArr = new int[i7];
            for (int i9 = 0; i9 < i7; i9++) {
                iArr[i9] = (int) byteBuffer.getInLittleEndian(i8, i);
                i8 += i;
            }
            return new NodeInformation(areaInformationArr, iArr);
        } catch (Exception e) {
            LogMgr.log(1, "%s %s", "800", e.toString());
            throw new OfflineException(4);
        }
    }
}
