package com.felicanetworks.mnlib.felica.internal;

import android.util.Log;
import com.felicanetworks.mnlib.felica.BlockDataList;
import com.felicanetworks.mnlib.felica.BlockList;
import com.felicanetworks.mnlib.felica.Data;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class FelicaPacketTransceiver {
    private static final String EXC_INVALID_COMMAND = "Invalid Command(null).";
    private static final FelicaPacketTransceiver INSTANCE = new FelicaPacketTransceiver();
    private static final int MAX_DATA_LENGTH = 255;
    private static final String TAG = "FeliCa";
    private ByteBuffer mByteBuffer = new ByteBuffer(255);

    protected FelicaPacketTransceiver() {
    }

    public static FelicaPacketTransceiver getInstance() {
        return INSTANCE;
    }

    public SystemInfo polling(int i, int i2, byte b, TransceiveAccessor transceiveAccessor) throws IOException, FelicaInternalException {
        try {
            PollingCommand pollingCommand = PollingCommand.getInstance();
            pollingCommand.setSystemCode(i);
            pollingCommand.setOption(b);
            PollingResponse pollingResponse = (PollingResponse) execute(pollingCommand, i2, transceiveAccessor);
            return new SystemInfo(pollingResponse.getIdm(), pollingResponse.getPmm(), pollingResponse.getSystemCode());
        } catch (FelicaInternalException e) {
            Log.w(TAG, "polling  : catch and throw FelicaInternalException");
            throw e;
        }
    }

    public int[] requestService(int i, byte[] bArr, int[] iArr, int i2, TransceiveAccessor transceiveAccessor) throws IOException, FelicaInternalException {
        try {
            RequestServiceCommand requestServiceCommand = RequestServiceCommand.getInstance();
            requestServiceCommand.setNodeCodeSize(i);
            requestServiceCommand.setIdm(bArr);
            requestServiceCommand.setServiceCodeList(iArr);
            return ((RequestServiceResponse) execute(requestServiceCommand, i2, transceiveAccessor)).getServiceKeyVersionList();
        } catch (FelicaInternalException e) {
            Log.w(TAG, "requestService  : catch and throw FelicaInternalException");
            throw e;
        }
    }

    public Data[] readWithoutEncryption(int i, byte[] bArr, BlockList blockList, int i2, TransceiveAccessor transceiveAccessor) throws IOException, FelicaInternalException {
        try {
            ReadWithoutEncryptionCommand readWithoutEncryptionCommand = ReadWithoutEncryptionCommand.getInstance();
            readWithoutEncryptionCommand.setNodeCodeSize(i);
            readWithoutEncryptionCommand.setIdm(bArr);
            readWithoutEncryptionCommand.setBlockList(blockList);
            ReadWithoutEncryptionResponse readWithoutEncryptionResponse = (ReadWithoutEncryptionResponse) execute(readWithoutEncryptionCommand, i2, transceiveAccessor);
            if (readWithoutEncryptionResponse.getStatusFlag1() != 0) {
                Log.w(TAG, "readWithoutEncryption  : Status Flag error");
                throw new FelicaInternalException(4, readWithoutEncryptionResponse.getStatusFlag1(), readWithoutEncryptionResponse.getStatusFlag2());
            }
            return readWithoutEncryptionResponse.getDataList();
        } catch (FelicaInternalException e) {
            Log.w(TAG, "readWithoutEncryption  : catch and throw FelicaInternalException");
            throw e;
        }
    }

    public void writeWithoutEncryption(int i, byte[] bArr, BlockDataList blockDataList, int i2, TransceiveAccessor transceiveAccessor) throws IOException, FelicaInternalException {
        try {
            WriteWithoutEncryptionCommand writeWithoutEncryptionCommand = WriteWithoutEncryptionCommand.getInstance();
            writeWithoutEncryptionCommand.setNodeCodeSize(i);
            writeWithoutEncryptionCommand.setIdm(bArr);
            writeWithoutEncryptionCommand.setBlockDataList(blockDataList);
            WriteWithoutEncryptionResponse writeWithoutEncryptionResponse = (WriteWithoutEncryptionResponse) execute(writeWithoutEncryptionCommand, i2, transceiveAccessor);
            if (writeWithoutEncryptionResponse.getStatusFlag1() == 0) {
                return;
            }
            Log.w(TAG, "writeWithoutEncryption  : Status Flag error");
            throw new FelicaInternalException(4, writeWithoutEncryptionResponse.getStatusFlag1(), writeWithoutEncryptionResponse.getStatusFlag2());
        } catch (FelicaInternalException e) {
            Log.w(TAG, "writeWithoutEncryption  : catch and throw FelicaInternalException");
            throw e;
        }
    }

    private Response execute(Command command, int i, TransceiveAccessor transceiveAccessor) throws IOException, FelicaInternalException {
        if (command == null) {
            Log.w(TAG, "execute  : command is null");
            throw new IllegalArgumentException(EXC_INVALID_COMMAND);
        }
        Response responseDoExecution = null;
        int i2 = 0;
        while (i2 <= i) {
            try {
                responseDoExecution = doExecution(command, transceiveAccessor);
                break;
            } catch (IOException e) {
                if (i2 == i) {
                    throw e;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("doExcecution throws ");
                sb.append(e.toString());
                sb.append(". Going to retry (times=");
                i2++;
                sb.append(i2);
                sb.append(")");
                Log.i(TAG, sb.toString());
            }
        }
        return responseDoExecution;
    }

    private Response doExecution(Command command, TransceiveAccessor transceiveAccessor) throws IOException, FelicaInternalException {
        try {
            this.mByteBuffer.setLength(0);
            this.mByteBuffer.append((byte) 0);
            this.mByteBuffer.set(0, (byte) (command.set(this.mByteBuffer) + 1));
            int length = this.mByteBuffer.getLength();
            byte[] bArr = new byte[length];
            this.mByteBuffer.copy(0, bArr, 0, length);
            byte[] bArrTransceive = transceiveAccessor.transceive(bArr);
            this.mByteBuffer.setLength(0);
            this.mByteBuffer.append(bArrTransceive);
            return command.get(this.mByteBuffer);
        } catch (FelicaInternalException e) {
            Log.w(TAG, "doExecution  : catch and throw FelicaInternalException");
            throw e;
        }
    }

    public int[] requestSystemCode(byte[] bArr, int i, TransceiveAccessor transceiveAccessor) throws IOException, FelicaInternalException {
        try {
            RequestSystemCodeCommand requestSystemCodeCommand = RequestSystemCodeCommand.getInstance();
            requestSystemCodeCommand.setIdm(bArr);
            return ((RequestSystemCodeResponse) execute(requestSystemCodeCommand, i, transceiveAccessor)).getSystemCodeList();
        } catch (FelicaInternalException e) {
            Log.w(TAG, "requestSystemCode  : catch and throw FelicaInternalException");
            throw e;
        }
    }
}
