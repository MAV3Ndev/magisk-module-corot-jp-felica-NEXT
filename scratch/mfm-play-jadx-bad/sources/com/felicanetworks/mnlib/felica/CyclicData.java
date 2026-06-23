package com.felicanetworks.mnlib.felica;

/* JADX INFO: loaded from: classes3.dex */
public class CyclicData extends Data {
    public static final int TYPE = 2;
    private byte[] mBytes;

    @Override // com.felicanetworks.mnlib.felica.Data
    public int getType() {
        return 2;
    }

    public CyclicData(byte[] bArr) throws IllegalArgumentException {
        setBytes(bArr);
    }

    public byte[] getBytes() {
        return this.mBytes;
    }

    public void setBytes(byte[] bArr) throws IllegalArgumentException {
        checkBytes(bArr);
        this.mBytes = bArr;
    }

    @Override // com.felicanetworks.mnlib.felica.Data
    public void checkFormat() throws IllegalArgumentException {
        checkBytes(this.mBytes);
    }
}
