package com.felicanetworks.mfc.felica.offlineimpl;

import com.felicanetworks.mfc.felica.util.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
class SetParameterCommand extends Command {
    private static final byte CODE = 32;
    static final int ENCRYPTION_TYPE_SRM_TYPE1 = 1;
    static final int ENCRYPTION_TYPE_SRM_TYPE2 = 2;
    static final int PACKET_TYPE_NODECODESIZE_2 = 3;
    static final int PACKET_TYPE_NODECODESIZE_4 = 4;
    private static final byte RESERVED = 0;
    private int mEncryptionType;
    private byte[] mIdm;
    private int mPacketType;
    private SetParameterResponse mResponse = new SetParameterResponse();

    SetParameterCommand() {
    }

    @Override // com.felicanetworks.mfc.felica.offlineimpl.Command
    void doSet(ByteBuffer byteBuffer) throws OfflineException {
        if (byteBuffer == null) {
            throw new OfflineException(1);
        }
        try {
            byteBuffer.append(CODE);
            byteBuffer.append(this.mIdm);
            byteBuffer.append((byte) 0);
            byteBuffer.append((byte) 0);
            byteBuffer.append((byte) 0);
            byteBuffer.append((byte) 0);
            if (this.mEncryptionType == 1) {
                byteBuffer.append((byte) 0);
            } else {
                byteBuffer.append((byte) 1);
            }
            if (this.mPacketType == 3) {
                byteBuffer.append((byte) 0);
            } else {
                byteBuffer.append((byte) 1);
            }
            byteBuffer.append((byte) 0);
            byteBuffer.append((byte) 0);
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

    int getEncryptionType() {
        return this.mEncryptionType;
    }

    void setEncryptionType(int i) {
        this.mEncryptionType = i;
    }

    int getPacketType() {
        return this.mPacketType;
    }

    void setPacketType(int i) {
        this.mPacketType = i;
    }
}
