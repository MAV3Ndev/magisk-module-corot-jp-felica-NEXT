package com.felicanetworks.mfc.mfi.felica.util;

/* JADX INFO: loaded from: classes3.dex */
public class ByteBuffer {
    private byte[] mBytes;
    private int mCount;

    public ByteBuffer(int maxLength) throws ArrayIndexOutOfBoundsException {
        if (maxLength < 0) {
            throw new ArrayIndexOutOfBoundsException(maxLength);
        }
        this.mBytes = new byte[maxLength];
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

    public final void setLength(int length) throws ArrayIndexOutOfBoundsException {
        if (length < 0 || length > this.mBytes.length) {
            throw new ArrayIndexOutOfBoundsException(length);
        }
        this.mCount = length;
    }

    public final byte get(int index) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= this.mCount) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return this.mBytes[index];
    }

    public final void set(int index, byte b) throws ArrayIndexOutOfBoundsException {
        if (index < 0 || index >= this.mCount) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        this.mBytes[index] = b;
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

    public final void append(byte[] bytes, int offset, int length) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException(length);
        }
        int i = offset + length;
        if (i > bytes.length) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        int i2 = this.mCount;
        int i3 = i2 + length;
        byte[] bArr = this.mBytes;
        if (i3 > bArr.length) {
            throw new ArrayIndexOutOfBoundsException(this.mCount + length);
        }
        System.arraycopy(bytes, offset, bArr, i2, length);
        this.mCount += length;
    }

    public final void append(byte[] bytes) throws ArrayIndexOutOfBoundsException, NullPointerException {
        append(bytes, 0, bytes.length);
    }

    public long getInBigEndian(int index, int digit) throws ArrayIndexOutOfBoundsException {
        long j = 0;
        for (int i = 0; i < digit; i++) {
            j += ((long) (get(index + i) & 255)) << (((digit - i) - 1) * 8);
        }
        return j;
    }

    public void appendInBigEndian(long val, int digit) throws ArrayIndexOutOfBoundsException {
        for (int i = digit - 1; i >= 0; i--) {
            append((byte) (val >>> (i * 8)));
        }
    }

    public long getInLittleEndian(int index, int digit) throws ArrayIndexOutOfBoundsException {
        long j = 0;
        for (int i = 0; i < digit; i++) {
            j += ((long) (get(index + i) & 255)) << (i * 8);
        }
        return j;
    }

    public void appendInLittleEndian(long val, int digit) throws ArrayIndexOutOfBoundsException {
        for (int i = 0; i < digit; i++) {
            append((byte) (val >>> (i * 8)));
        }
    }

    public final boolean check(int index, byte b) {
        return index >= 0 && index < this.mCount && b == this.mBytes[index];
    }

    public final boolean check(int index, byte[] bytes) {
        if (bytes == null || index < 0 || bytes.length + index > this.mCount) {
            return false;
        }
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            if (bytes[i] != this.mBytes[index + i]) {
                return false;
            }
        }
        return true;
    }

    public final void copy(int index, byte[] bytes, int offset, int length) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException(length);
        }
        int i = offset + length;
        if (i > bytes.length) {
            throw new ArrayIndexOutOfBoundsException(i);
        }
        if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        if (index + length > this.mCount) {
            throw new ArrayIndexOutOfBoundsException(length + length);
        }
        System.arraycopy(this.mBytes, index, bytes, offset, length);
    }

    public final void copy(int index, byte[] bytes) throws ArrayIndexOutOfBoundsException, NullPointerException {
        copy(index, bytes, 0, bytes.length);
    }
}
