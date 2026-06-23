package com.felicanetworks.common.cmnctrl.net;

import com.felicanetworks.common.cmnlib.util.CommonUtil;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class NetworkAccessRequestData {
    private static final int CUT_SIZE = 1024;
    public int connectTimeout;
    public byte[] data;
    public Map<String, String> header;
    public NetworkAccessListener listener;
    public String method;
    public int noticeCnt = 1;
    public int readTimeout;
    public String url;

    public NetworkAccessRequestData(String str, String str2, Map<String, String> map, int i, int i2, byte[] bArr) {
        this.url = str;
        this.method = str2;
        this.header = map;
        this.connectTimeout = i;
        this.readTimeout = i2;
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
        return "NetworkAccessRequestData[" + this.url + ", " + this.method + ", " + this.header + ", " + strBinToHexString + "]";
    }
}
