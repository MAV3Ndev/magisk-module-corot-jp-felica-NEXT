package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.net;

import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes3.dex */
public class BinaryBlock {
    protected String charCode;
    protected byte[] data;
    protected int pos;

    public BinaryBlock(byte[] b, String cc) {
        this.data = b != null ? (byte[]) b.clone() : new byte[0];
        this.charCode = cc;
        this.pos = 0;
    }

    public int getDigitValue(int size) throws DataCheckerException, UnsupportedEncodingException {
        String str = new String(this.data, this.pos, size, this.charCode);
        this.pos += size;
        DataCheckerUtil.checkDecNumberFormat(str);
        return Integer.parseInt(str);
    }

    public String getStringValue(int size) throws UnsupportedEncodingException {
        String str = new String(this.data, this.pos, size, this.charCode);
        this.pos += size;
        return str;
    }

    public byte[] getByteArray(int size) {
        byte[] bArr = new byte[size];
        System.arraycopy(this.data, this.pos, bArr, 0, size);
        this.pos += size;
        return bArr;
    }

    public String toString() {
        byte[] bArr = this.data;
        return "BinaryBlock[" + this.pos + "," + this.charCode + "," + (bArr != null ? CommonUtil.binToHexString(bArr) : null) + "]";
    }
}
