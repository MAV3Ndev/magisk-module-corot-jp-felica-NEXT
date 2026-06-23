package com.felicanetworks.mnlib.felica.internal;

import com.felicanetworks.mnlib.felica.BlockDataList;

/* JADX INFO: loaded from: classes3.dex */
class WriteWithoutEncryptionCommand extends Command {
    private static final byte CODE = 8;
    private static final WriteWithoutEncryptionCommand INSTANCE = new WriteWithoutEncryptionCommand();
    private BlockDataList mBlockDataList;
    private byte[] mIdm;
    private int mNodeCodeSize;

    private WriteWithoutEncryptionCommand() {
    }

    static WriteWithoutEncryptionCommand getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    void doSet(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        try {
            byteBuffer.append((byte) 8);
            byteBuffer.append(this.mIdm);
            DataUtil.append(byteBuffer, this.mBlockDataList, this.mNodeCodeSize);
        } catch (FelicaInternalException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new FelicaInternalException(1);
        }
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    Response get(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        return WriteWithoutEncryptionResponse.getInstance().get(this, byteBuffer);
    }

    byte[] getIdm() {
        return this.mIdm;
    }

    void setIdm(byte[] bArr) {
        this.mIdm = bArr;
    }

    int getNodeCodeSize() {
        return this.mNodeCodeSize;
    }

    void setNodeCodeSize(int i) {
        this.mNodeCodeSize = i;
    }

    BlockDataList getBlockDataList() {
        return this.mBlockDataList;
    }

    void setBlockDataList(BlockDataList blockDataList) {
        this.mBlockDataList = blockDataList;
    }
}
