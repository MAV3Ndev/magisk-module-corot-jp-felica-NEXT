package com.felicanetworks.common.cmnctrl.net;

import com.felicanetworks.common.cmnlib.util.CommonUtil;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NetworkAccessResponseData {
    private static final int CUT_SIZE = 1024;
    public int code;
    public byte[] data;
    public Map<String, String> header;

    public NetworkAccessResponseData(int i, Map<String, String> map, byte[] bArr) {
        this.code = i;
        this.header = map;
        this.data = bArr;
    }

    public String toString() {
        String strBinToHexString;
        byte[] bArr = this.data;
        if (bArr == null) {
            strBinToHexString = null;
        } else if (bArr.length > 1024) {
            byte[] bArr2 = new byte[1024];
            System.arraycopy(bArr, 0, bArr2, 0, 1024);
            strBinToHexString = CommonUtil.binToHexString(bArr2);
        } else {
            strBinToHexString = CommonUtil.binToHexString(bArr);
        }
        return "NetworkAccessResponseData[" + this.code + ", " + this.header + ", " + strBinToHexString + "]";
    }
}
