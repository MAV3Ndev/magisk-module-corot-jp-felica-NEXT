package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.net;

import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.CommonUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import java.io.UnsupportedEncodingException;

/* JADX INFO: loaded from: classes.dex */
public class BinaryBlock {
    protected String charCode;
    protected byte[] data;
    protected int pos;

    public BinaryBlock(byte[] bArr, String str) {
        this.data = bArr != null ? (byte[]) bArr.clone() : new byte[0];
        this.charCode = str;
        this.pos = 0;
    }

    public int getDigitValue(int i) throws DataCheckerException, UnsupportedEncodingException {
        String str = new String(this.data, this.pos, i, this.charCode);
        this.pos += i;
        DataCheckerUtil.checkDecNumberFormat(str);
        return Integer.parseInt(str);
    }

    public String getStringValue(int i) throws UnsupportedEncodingException {
        String str = new String(this.data, this.pos, i, this.charCode);
        this.pos += i;
        return str;
    }

    public byte[] getByteArray(int i) {
        byte[] bArr = new byte[i];
        System.arraycopy(this.data, this.pos, bArr, 0, i);
        this.pos += i;
        return bArr;
    }

    public String toString() {
        byte[] bArr = this.data;
        return "BinaryBlock[" + this.pos + "," + this.charCode + "," + (bArr != null ? CommonUtil.binToHexString(bArr) : null) + "]";
    }
}
