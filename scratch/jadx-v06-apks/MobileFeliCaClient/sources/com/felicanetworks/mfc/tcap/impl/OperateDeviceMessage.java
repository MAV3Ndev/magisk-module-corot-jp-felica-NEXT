package com.felicanetworks.mfc.tcap.impl;

import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class OperateDeviceMessage extends Message {
    private static final int MIN_LENGTH = 5;
    private static final String PARAM_NAME_CHARSET = "ISO-8859-1";

    public OperateDeviceMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 5;
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        int i = this.mOffset + 6;
        int i2 = this.mData[i] & 255;
        int i3 = i + 1;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i3 + i4;
            if (this.mData[i5] < 32 || this.mData[i5] > 126) {
                return false;
            }
        }
        int i6 = i3 + i2;
        if (this.mData[i6] != 0 || this.mData[i6 + 1] != 0) {
            return false;
        }
        int i7 = i6 + 2;
        return this.mLength == (((i2 + 1) + 2) + 2) + ((this.mData[i7 + 1] & 255) | ((this.mData[i7] & 255) << 8));
    }

    public String getParamName() {
        int paramNameLength = getParamNameLength();
        if (paramNameLength > 0) {
            try {
                return new String(this.mData, this.mOffset + 6 + 1, paramNameLength, PARAM_NAME_CHARSET);
            } catch (UnsupportedEncodingException unused) {
            }
        }
        return null;
    }

    public byte[] getParam() {
        int paramSize = getParamSize();
        if (paramSize <= 0) {
            return null;
        }
        byte[] bArr = new byte[paramSize];
        System.arraycopy(this.mData, this.mOffset + 6 + 1 + getParamNameLength() + 2 + 2, bArr, 0, paramSize);
        return bArr;
    }

    private int getParamNameLength() {
        return this.mData[this.mOffset + 6] & 255;
    }

    private int getParamSize() {
        int paramNameLength = this.mOffset + 6 + 1 + getParamNameLength() + 2;
        return (this.mData[paramNameLength + 1] & 255) | ((this.mData[paramNameLength] & 255) << 8);
    }
}
