package com.felicanetworks.mfc.tcap.impl;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class ErrorMessage extends Message {
    private static final String MSG_CHARSET = "ISO-8859-1";

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return true;
    }

    public ErrorMessage(Message message) {
        super(message);
    }

    public ErrorMessage(byte b, String str) {
        int length;
        byte[] bArr = null;
        if (str != null) {
            try {
                ByteBuffer byteBufferEncode = StandardCharsets.ISO_8859_1.newEncoder().encode(CharBuffer.wrap(str));
                bArr = new byte[byteBufferEncode.limit()];
                byteBufferEncode.get(bArr);
                length = bArr.length;
            } catch (Exception unused) {
                length = 0;
            }
        } else {
            length = 0;
        }
        init((byte) 0, 0, b, length);
        if (length > 0) {
            System.arraycopy(bArr, 0, this.mData, 6, length);
        }
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        int i = this.mOffset + 6;
        for (int i2 = 0; i2 < this.mLength; i2++) {
            int i3 = i + i2;
            if (this.mData[i3] < 32 || this.mData[i3] > 126) {
                return false;
            }
        }
        return true;
    }

    public String getMessage() {
        if (this.mLength > 0) {
            try {
                return new String(this.mData, this.mOffset + 6, this.mLength, MSG_CHARSET);
            } catch (UnsupportedEncodingException unused) {
            }
        }
        return null;
    }
}
