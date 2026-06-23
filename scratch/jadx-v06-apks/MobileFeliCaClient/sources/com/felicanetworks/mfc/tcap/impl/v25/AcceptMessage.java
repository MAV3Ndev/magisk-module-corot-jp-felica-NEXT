package com.felicanetworks.mfc.tcap.impl.v25;

import com.felicanetworks.mfc.tcap.impl.Message;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
class AcceptMessage extends Message {
    private static final int MIN_LENGTH = 3;
    private static final String OPTION_CHARSET = "ISO-8859-1";

    AcceptMessage(Message message) {
        super(message);
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateFormat() {
        return this.mLength >= 3;
    }

    @Override // com.felicanetworks.mfc.tcap.impl.Message
    public boolean validateData() {
        int i = this.mOffset + 6 + 2;
        int i2 = i + 1;
        int i3 = this.mData[i] & 255;
        if (i3 > 0) {
            int i4 = i2;
            int i5 = 0;
            while (i5 < i3) {
                if (i4 >= this.mOffset + this.mSize) {
                    return false;
                }
                int i6 = i4 + 1;
                int i7 = this.mData[i4] & 255;
                if (i7 < 1 || i6 + i7 > this.mOffset + this.mSize) {
                    return false;
                }
                while (i7 > 0) {
                    if (this.mData[i6] < 32 || this.mData[i6] > 126) {
                        return false;
                    }
                    i6++;
                    i7--;
                }
                i5++;
                i4 = i6;
            }
            i2 = i4;
        }
        return i2 == this.mOffset + this.mSize;
    }

    int getVersion() {
        int i = this.mOffset + 6;
        return (this.mData[i + 1] & 255) | (this.mData[i] << 8);
    }

    int getOptionNum() {
        return this.mData[this.mOffset + 6 + 2] & 255;
    }

    Vector<String> getOptionList() {
        Vector<String> vector = new Vector<>();
        int i = this.mOffset + 6 + 2;
        int i2 = this.mData[i] & 255;
        int i3 = i + 1;
        String str = null;
        for (int i4 = 0; i4 < i2; i4++) {
            int i5 = i3 + 1;
            int i6 = this.mData[i3] & 255;
            try {
                str = new String(this.mData, i5, i6, OPTION_CHARSET);
            } catch (UnsupportedEncodingException unused) {
            }
            vector.addElement(str);
            i3 = i6 + i5;
        }
        return vector;
    }
}
