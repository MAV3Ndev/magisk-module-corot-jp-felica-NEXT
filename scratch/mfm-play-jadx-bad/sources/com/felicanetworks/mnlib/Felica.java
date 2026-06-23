package com.felicanetworks.mnlib;

import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.NfcF;
import android.os.RemoteException;
import android.util.Log;
import com.felicanetworks.mnlib.felica.BlockDataList;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import com.felicanetworks.mnlib.felica.FelicaException;
import com.felicanetworks.mnlib.felica.ServiceUtil;
import com.felicanetworks.mnlib.felica.internal.FelicaInternalException;
import com.felicanetworks.mnlib.felica.internal.FelicaPacketTransceiver;
import com.felicanetworks.mnlib.felica.internal.SystemInfo;
import com.felicanetworks.mnlib.felica.internal.TransceiveAccessor;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public final class Felica {
    public static final int DEFAULT_RETRY_COUNT = 0;
    public static final int DEFAULT_TIMEOUT = 1000;
    private static final String EXC_INVALID_BLOCK_DATA_LIST = "The specified BlockDataList is null or empty.";
    private static final String EXC_INVALID_BLOCK_LIST = "The specified BlockList is null or empty.";
    private static final String EXC_INVALID_COMMAND_PACKET = "The size of the command packet exceeds 255 bytes.";
    private static final String EXC_INVALID_FELICA_STATE = "The state of Felica is invalid.";
    private static final String EXC_INVALID_SYSTEM_CODE = "The specified System Code is null or out of range.";
    private static final int IDM_LENGTH = 8;
    public static final int MAX_RETRY_COUNT = 10;
    private static final int MAX_SYSTEM_CODE = 65535;
    public static final int MAX_TIMEOUT = 60000;
    public static final int MIN_RETRY_COUNT = 0;
    private static final int MIN_SYSTEM_CODE = 0;
    public static final int MIN_TIMEOUT = 0;
    public static final int NODE_CODE_SIZE_2 = 2;
    public static final int NODE_CODE_SIZE_4 = 4;
    private static final int NONEXISTENT_SERVICE = 65535;
    private static final byte OPTION_SYSTEM = 1;
    private static final int PMM_LENGTH = 8;
    private static final String TAG = "FeliCa";
    private static final Object TRANSCEIVE_LOCK_OBJ = new Object();
    private final FelicaPacketTransceiver mFelicaPacketTransceiver;
    private NfcF mNfcF;
    private final int mNodeCodeSize;
    private int mRetryCount;
    private int mTimeout;
    private final TransceiveAccessor mTransceiveAccessor;
    private byte[] mObtainedManufacturer = null;
    private byte[] mObtainedIdm = null;
    private final Object mParamLockObj = new Object();

    public static Felica getInstance(Tag tag) {
        Log.d(TAG, "Felica#getInstance(tag)");
        try {
            return new Felica(tag);
        } catch (RemoteException unused) {
            return null;
        }
    }

    private Felica(Tag tag) throws RemoteException {
        Log.d(TAG, "Felica(tag)");
        NfcF nfcF = NfcF.get(tag);
        this.mNfcF = nfcF;
        if (nfcF == null) {
            throw new RemoteException("The tag does not support NFC-F");
        }
        this.mTransceiveAccessor = new TransceiveAccessor() { // from class: com.felicanetworks.mnlib.Felica.1
            @Override // com.felicanetworks.mnlib.felica.internal.TransceiveAccessor
            public byte[] transceive(byte[] bArr) throws IOException {
                return Felica.this.mNfcF.transceive(bArr);
            }
        };
        this.mFelicaPacketTransceiver = FelicaPacketTransceiver.getInstance();
        this.mNodeCodeSize = 2;
        this.mTimeout = 1000;
        this.mRetryCount = 0;
    }

    public void open() throws IOException {
        Log.d(TAG, "Felica#open()");
        this.mNfcF.connect();
    }

    public void close() throws IOException {
        Log.d(TAG, "Felica#close()");
        if (this.mNfcF.isConnected()) {
            this.mNfcF.close();
        }
        synchronized (this.mParamLockObj) {
            this.mObtainedManufacturer = null;
            this.mObtainedIdm = null;
        }
        this.mTimeout = 1000;
        this.mRetryCount = 0;
    }

    void checkConnected() {
        Log.d(TAG, "Felica#checkConnected()");
        if (!this.mNfcF.isConnected()) {
            throw new IllegalStateException("Call open() first!");
        }
    }

    public byte[] transceive(byte[] bArr) throws IOException {
        Log.d(TAG, "Felica#transceive(data)");
        return this.mNfcF.transceive(bArr);
    }

    public void setTimeout(int i) {
        Log.d(TAG, "Felica#setTimeout(timeout)");
        if (i < 0) {
            this.mTimeout = 0;
        } else if (i > 60000) {
            this.mTimeout = 60000;
        } else {
            this.mTimeout = i;
        }
    }

    public int getTimeout() {
        Log.d(TAG, "Felica#getTimeout()");
        return this.mTimeout;
    }

    public void setRetryCount(int i) {
        Log.d(TAG, "Felica#setRetryCount(retryCount)");
        if (i < 0) {
            this.mRetryCount = 0;
        } else if (i > 10) {
            this.mRetryCount = 10;
        } else {
            this.mRetryCount = i;
        }
    }

    public int getRetryCount() {
        Log.d(TAG, "Felica#getRetryCount()");
        return this.mRetryCount;
    }

    public void select(int i) throws FelicaException, IOException, IllegalArgumentException {
        SystemInfo systemInfoPolling;
        Log.d(TAG, "Felica#select(systemCode)");
        if (i < 0 || i > 65535) {
            throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
        }
        checkConnected();
        try {
            synchronized (TRANSCEIVE_LOCK_OBJ) {
                this.mNfcF.setTimeout(this.mTimeout);
                systemInfoPolling = this.mFelicaPacketTransceiver.polling(i, this.mRetryCount, (byte) 1, this.mTransceiveAccessor);
            }
            synchronized (this.mParamLockObj) {
                this.mObtainedIdm = systemInfoPolling.getIdm();
                this.mObtainedManufacturer = systemInfoPolling.getPmm();
            }
        } catch (TagLostException unused) {
            throw new FelicaException(2, 7);
        } catch (FelicaInternalException e) {
            if (e.getType() == 3) {
                throw new FelicaException(2, 1);
            }
            throw new RuntimeException(EXC_INVALID_FELICA_STATE);
        }
    }

    public byte[] getIDm() throws FelicaException {
        byte[] bArr;
        Log.d(TAG, "Felica#getIDm()");
        checkIdm(this.mObtainedIdm);
        synchronized (this.mParamLockObj) {
            bArr = (byte[]) this.mObtainedIdm.clone();
        }
        return bArr;
    }

    public byte[] getICCode() throws FelicaException {
        Log.d(TAG, "Felica#getICCode()");
        checkIdm(this.mObtainedIdm);
        synchronized (this.mParamLockObj) {
            byte[] bArr = this.mObtainedManufacturer;
            if (bArr == null || bArr.length != 8) {
                return null;
            }
            return new byte[]{bArr[0], bArr[1]};
        }
    }

    public int getKeyVersion(int i) throws FelicaException, IOException, IllegalArgumentException {
        int i2;
        Log.d(TAG, "Felica#getKeyVersion(nodeCode)");
        ServiceUtil.checkServiceCode(i, this.mNodeCodeSize);
        checkConnected();
        try {
            byte[] iDm = getIDm();
            synchronized (TRANSCEIVE_LOCK_OBJ) {
                this.mNfcF.setTimeout(this.mTimeout);
                i2 = this.mFelicaPacketTransceiver.requestService(this.mNodeCodeSize, iDm, new int[]{i}, this.mRetryCount, this.mTransceiveAccessor)[0];
            }
            if (i2 != 65535) {
                return i2;
            }
            Log.w(TAG, "getKeyVersion  : node not found");
            throw new FelicaException(4, 11);
        } catch (TagLostException unused) {
            throw new FelicaException(2, 7);
        } catch (FelicaInternalException e) {
            if (e.getType() == 3) {
                throw new FelicaException(2, 1);
            }
            throw new RuntimeException(EXC_INVALID_FELICA_STATE);
        }
    }

    public Data[] read(BlockList blockList) throws FelicaException, IOException, IllegalArgumentException {
        Data[] withoutEncryption;
        Log.d(TAG, "Felica#read(blockList)");
        if (blockList == null || blockList.size() == 0) {
            throw new IllegalArgumentException("The specified BlockList is null or empty.");
        }
        blockList.checkFormat();
        checkConnected();
        try {
            byte[] iDm = getIDm();
            synchronized (TRANSCEIVE_LOCK_OBJ) {
                this.mNfcF.setTimeout(this.mTimeout);
                withoutEncryption = this.mFelicaPacketTransceiver.readWithoutEncryption(this.mNodeCodeSize, iDm, blockList, this.mRetryCount, this.mTransceiveAccessor);
            }
            return withoutEncryption;
        } catch (TagLostException unused) {
            throw new FelicaException(2, 7);
        } catch (FelicaInternalException e) {
            int type = e.getType();
            if (type == 1) {
                throw new IllegalArgumentException(EXC_INVALID_COMMAND_PACKET);
            }
            if (type == 3) {
                throw new FelicaException(2, 1);
            }
            if (type == 4) {
                throw new FelicaException(3, 4, e.getStatusFlag1(), e.getStatusFlag2());
            }
            throw new RuntimeException(EXC_INVALID_FELICA_STATE);
        }
    }

    public void write(BlockDataList blockDataList) throws FelicaException, IOException, IllegalArgumentException {
        Log.d(TAG, "Felica#write(blockDataList)");
        if (blockDataList == null || blockDataList.size() == 0) {
            throw new IllegalArgumentException("The specified BlockDataList is null or empty.");
        }
        blockDataList.checkFormat();
        checkConnected();
        try {
            byte[] iDm = getIDm();
            synchronized (TRANSCEIVE_LOCK_OBJ) {
                this.mNfcF.setTimeout(this.mTimeout);
                this.mFelicaPacketTransceiver.writeWithoutEncryption(this.mNodeCodeSize, iDm, blockDataList, this.mRetryCount, this.mTransceiveAccessor);
            }
        } catch (TagLostException unused) {
            throw new FelicaException(2, 7);
        } catch (FelicaInternalException e) {
            int type = e.getType();
            if (type == 1) {
                throw new IllegalArgumentException(EXC_INVALID_COMMAND_PACKET);
            }
            if (type == 3) {
                throw new FelicaException(2, 1);
            }
            if (type == 4) {
                if (e.getStatusFlag1() != 255 || e.getStatusFlag2() != 113) {
                    throw new FelicaException(5, 5, e.getStatusFlag1(), e.getStatusFlag2());
                }
                return;
            }
            throw new RuntimeException(EXC_INVALID_FELICA_STATE);
        }
    }

    public int[] getSystemCodeList() throws FelicaException, IOException {
        int[] iArrRequestSystemCode;
        Log.d(TAG, "Felica#getSystemCodeList()");
        checkConnected();
        try {
            byte[] iDm = getIDm();
            synchronized (TRANSCEIVE_LOCK_OBJ) {
                this.mNfcF.setTimeout(this.mTimeout);
                iArrRequestSystemCode = this.mFelicaPacketTransceiver.requestSystemCode(iDm, this.mRetryCount, this.mTransceiveAccessor);
            }
            return iArrRequestSystemCode;
        } catch (TagLostException unused) {
            throw new FelicaException(2, 7);
        } catch (FelicaInternalException e) {
            if (e.getType() == 3) {
                throw new FelicaException(2, 1);
            }
            throw new RuntimeException(EXC_INVALID_FELICA_STATE);
        }
    }

    private void checkIdm(byte[] bArr) throws FelicaException {
        Log.d(TAG, "Felica#checkIdm()");
        if (bArr == null || bArr.length != 8) {
            throw new FelicaException(1, 3);
        }
    }
}
