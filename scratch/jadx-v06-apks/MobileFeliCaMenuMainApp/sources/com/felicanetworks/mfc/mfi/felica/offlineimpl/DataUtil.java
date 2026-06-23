package com.felicanetworks.mfc.mfi.felica.offlineimpl;

import com.felicanetworks.mfc.CyclicData;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.PINChangeData;
import com.felicanetworks.mfc.PINCheckData;
import com.felicanetworks.mfc.PINEnableData;
import com.felicanetworks.mfc.PurseCashBackData;
import com.felicanetworks.mfc.PurseData;
import com.felicanetworks.mfc.PurseDecrementData;
import com.felicanetworks.mfc.RandomData;
import com.felicanetworks.mfc.mfi.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public class DataUtil {
    public static final int BLOCK_DATA_LENGTH = 16;
    private static final int USERDATA_LENGTH = 6;
    private static DataUtil sInstance;

    private DataUtil() {
    }

    public static DataUtil getInstance() {
        if (sInstance == null) {
            sInstance = new DataUtil();
        }
        return sInstance;
    }

    public void append(ByteBuffer byteBuffer, Data data) throws ArrayIndexOutOfBoundsException, NullPointerException {
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
}
