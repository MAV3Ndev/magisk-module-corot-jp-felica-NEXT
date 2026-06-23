package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class ReadWithoutEncryptionCommand extends Command {
    private static final byte CODE = 6;
    private BlockList mBlockList;
    private byte[] mIdm;
    private int mNodeCodeSize;
    private ReadWithoutEncryptionResponse mResponse = new ReadWithoutEncryptionResponse();

    ReadWithoutEncryptionCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append((byte) 6);
            byteBuffer.append(this.mIdm);
            DataUtil.getInstance().append(byteBuffer, this.mBlockList, this.mNodeCodeSize);
        } catch (OfflineException e) {
            throw e;
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new OfflineException(2);
        }
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    Response get(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        return this.mResponse.get(this, byteBuffer);
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
