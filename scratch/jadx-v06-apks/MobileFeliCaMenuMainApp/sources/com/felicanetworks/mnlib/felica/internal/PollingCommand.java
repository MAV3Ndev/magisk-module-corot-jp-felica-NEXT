package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes.dex */
class PollingCommand extends Command {
    private static final byte CODE = 0;
    private static final PollingCommand INSTANCE = new PollingCommand();
    private static final byte TIME_SLOT = 0;
    private byte mOption;
    private int mSystemCode;

    private PollingCommand() {
    }

    static PollingCommand getInstance() {
        return INSTANCE;
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    void doSet(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        try {
            byteBuffer.append((byte) 0);
            byteBuffer.appendInBigEndian(this.mSystemCode, 2);
            byteBuffer.append(this.mOption);
            byteBuffer.append((byte) 0);
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new FelicaInternalException(1);
        }
    }

    @Override // com.felicanetworks.mnlib.felica.internal.Command
    Response get(ByteBuffer byteBuffer) throws FelicaInternalException {
        if (byteBuffer == null) {
            throw new FelicaInternalException(0);
        }
        return PollingResponse.getInstance().get(this, byteBuffer);
    }

    int getSystemCode() {
        return this.mSystemCode;
    }

    void setSystemCode(int i) {
        this.mSystemCode = i;
    }

    void setOption(byte b) {
        this.mOption = b;
    }
}
