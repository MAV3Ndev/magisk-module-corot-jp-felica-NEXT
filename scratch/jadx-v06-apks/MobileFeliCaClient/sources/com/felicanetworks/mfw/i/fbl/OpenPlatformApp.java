package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.StringUtil;

/* JADX INFO: loaded from: classes.dex */
public class OpenPlatformApp implements ExtensionParameter {
    private byte[] mApplicationSignerCertificationHash;

    @Override // com.felicanetworks.mfw.i.fbl.ExtensionParameter
    public String getId() {
        return ExtensionParameter.OPEN_PLATFORM;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("applicationSignerCertificationHash = [" + StringUtil.toHexString(this.mApplicationSignerCertificationHash) + "]");
        return stringBuffer.toString();
    }

    public byte[] getHash() {
        return this.mApplicationSignerCertificationHash;
    }

    public void setHash(byte[] bArr) {
        this.mApplicationSignerCertificationHash = bArr;
    }
}
