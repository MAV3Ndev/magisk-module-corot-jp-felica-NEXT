package com.felicanetworks.mnlib.felica.internal;

import com.felicanetworks.mnlib.felica.BlockList;

/* JADX INFO: loaded from: classes.dex */
class ReadWithoutEncryptionCommand extends Command {
    private static final byte CODE = 6;
    private static final ReadWithoutEncryptionCommand INSTANCE = new ReadWithoutEncryptionCommand();
    private BlockList mBlockList;
    private byte[] mIdm;
    private int mNodeCodeSize;

    private ReadWithoutEncryptionCommand() {
    }

    static ReadWithoutEncryptionCommand getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    void doSet(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        try {
            byteBuffer.append(CODE);
            byteBuffer.append(this.mIdm);
            DataUtil.append(byteBuffer, this.mBlockList, this.mNodeCodeSize);
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
        return ReadWithoutEncryptionResponse.getInstance().get(this, byteBuffer);
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

    BlockList getBlockList() {
        return this.mBlockList;
    }

    void setBlockList(BlockList blockList) {
        this.mBlockList = blockList;
    }
}
