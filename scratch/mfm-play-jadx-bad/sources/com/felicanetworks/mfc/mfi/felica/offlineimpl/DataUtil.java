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

/* JADX INFO: loaded from: classes3.dex */
public class DataUtil {
    public static final int BLOCK_DATA_LENGTH = 16;
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

    private void append(ByteBuffer byteBuffer, RandomData data) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.append(data.getBytes());
    }

    private void append(ByteBuffer byteBuffer, CyclicData data) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.append(data.getBytes());
    }

    private void append(ByteBuffer byteBuffer, PurseData data) throws ArrayIndexOutOfBoundsException, NullPointerException {
        byteBuffer.appendInLittleEndian(data.getPurseData(), 4);
        byteBuffer.appendInLittleEndian(data.getCashBackData(), 4);
        byteBuffer.append(data.getUserData());
        byteBuffer.appendInBigEndian(data.getExecID(), 2);
    }

    private void append(ByteBuffer byteBuffer, PurseCashBackData data) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInLittleEndian(data.getCashBackData(), 4);
        for (int i = 0; i < 10; i++) {
            byteBuffer.append((byte) 0);
        }
        byteBuffer.appendInBigEndian(data.getExecID(), 2);
    }

    private void append(ByteBuffer byteBuffer, PurseDecrementData data) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInLittleEndian(data.getDecrementData(), 4);
        for (int i = 0; i < 10; i++) {
            byteBuffer.append((byte) 0);
        }
        byteBuffer.appendInBigEndian(data.getExecID(), 2);
    }

    private void append(ByteBuffer byteBuffer, PINCheckData data) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInBigEndian(data.getPIN(), 4);
        for (int i = 0; i < 12; i++) {
            byteBuffer.append((byte) 0);
        }
    }

    private void append(ByteBuffer byteBuffer, PINChangeData data) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInBigEndian(data.getPIN(), 4);
        byteBuffer.appendInBigEndian(data.getNewPIN(), 4);
        for (int i = 0; i < 8; i++) {
            byteBuffer.append((byte) 0);
        }
    }

    private void append(ByteBuffer byteBuffer, PINEnableData data) throws ArrayIndexOutOfBoundsException {
        byteBuffer.appendInBigEndian(data.getPIN(), 4);
        for (int i = 0; i < 4; i++) {
            byteBuffer.append((byte) 0);
        }
        if (data.isEnabling()) {
            byteBuffer.append((byte) -68);
        } else {
            byteBuffer.append((byte) 60);
        }
        for (int i2 = 0; i2 < 7; i2++) {
            byteBuffer.append((byte) 0);
        }
    }
}
