package com.felicanetworks.mnlib.felica.internal;

/* JADX INFO: loaded from: classes.dex */
class ByteBuffer {
    private byte[] mBytes;
    private int mCount;

    public ByteBuffer(int i) throws ArrayIndexOutOfBoundsException {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.mBytes = new byte[i];
    }

    public final int getMaxLength() {
        return this.mBytes.length;
    }

    public final byte[] getBytes() {
        return this.mBytes;
    }

    public final int getLength() {
        return this.mCount;
    }

    public final void setLength(int i) throws ArrayIndexOutOfBoundsException {
        if (i < 0 || i > this.mBytes.length) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.mCount = i;
    }

    public final byte get(int i) throws ArrayIndexOutOfBoundsException {
        if (i < 0 || i >= this.mCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        return this.mBytes[i];
    }

    public final void set(int i, byte b) throws ArrayIndexOutOfBoundsException {
        if (i < 0 || i >= this.mCount) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        this.mBytes[i] = b;
    }

    public final void append(byte b) throws ArrayIndexOutOfBoundsException {
        int i = this.mCount;
        byte[] bArr = this.mBytes;
        if (i == bArr.length) {
            throw new ArrayIndexOutOfBoundsException(this.mCount);
        }
        this.mCount = i + 1;
        bArr[i] = b;
    }

    public final void append(byte[] bArr, int i, int i2) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException(i2);
        }
        int i3 = i + i2;
        if (i3 > bArr.length) {
            throw new ArrayIndexOutOfBoundsException(i3);
        }
        int i4 = this.mCount;
        int i5 = i4 + i2;
        byte[] bArr2 = this.mBytes;
        if (i5 > bArr2.length) {
            throw new ArrayIndexOutOfBoundsException(this.mCount + i2);
        }
        System.arraycopy(bArr, i, bArr2, i4, i2);
        this.mCount += i2;
    }

    public final void append(byte[] bArr) throws ArrayIndexOutOfBoundsException, NullPointerException {
        append(bArr, 0, bArr.length);
    }

    public long getInBigEndian(int i, int i2) throws ArrayIndexOutOfBoundsException {
        long j = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j += ((long) (get(i + i3) & 255)) << (((i2 - i3) - 1) * 8);
        }
        return j;
    }

    public void appendInBigEndian(long j, int i) throws ArrayIndexOutOfBoundsException {
        for (int i2 = i - 1; i2 >= 0; i2--) {
            append((byte) (j >>> (i2 * 8)));
        }
    }

    public long getInLittleEndian(int i, int i2) throws ArrayIndexOutOfBoundsException {
        long j = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j += ((long) (get(i + i3) & 255)) << (i3 * 8);
        }
        return j;
    }

    public void appendInLittleEndian(long j, int i) throws ArrayIndexOutOfBoundsException {
        for (int i2 = 0; i2 < i; i2++) {
            append((byte) (j >>> (i2 * 8)));
        }
    }

    public final boolean check(int i, byte b) {
        return i >= 0 && i < this.mCount && b == this.mBytes[i];
    }

    public final boolean check(int i, byte[] bArr) {
        if (bArr == null || i < 0 || bArr.length + i > this.mCount) {
            return false;
        }
        int length = bArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (bArr[i2] != this.mBytes[i + i2]) {
                return false;
            }
        }
        return true;
    }

    public final void copy(int i, byte[] bArr, int i2, int i3) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (i2 < 0) {
            throw new ArrayIndexOutOfBoundsException(i2);
        }
        if (i3 < 0) {
            throw new ArrayIndexOutOfBoundsException(i3);
        }
        int i4 = i2 + i3;
        if (i4 > bArr.length) {
            throw new ArrayIndexOutOfBoundsException(i4);
        }
        if (i < 0) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (i + i3 > this.mCount) {
            throw new ArrayIndexOutOfBoundsException(i3 + i3);
        }
        System.arraycopy(this.mBytes, i, bArr, i2, i3);
    }

    public final void copy(int i, byte[] bArr) throws ArrayIndexOutOfBoundsException, NullPointerException {
        copy(i, bArr, 0, bArr.length);
    }
}
