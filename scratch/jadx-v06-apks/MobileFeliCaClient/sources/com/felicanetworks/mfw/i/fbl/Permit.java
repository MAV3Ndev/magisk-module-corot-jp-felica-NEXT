package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.ArrayList;
import com.felicanetworks.mfw.i.cmn.StringUtil;

/* JADX INFO: loaded from: classes.dex */
public class Permit {
    private ArrayList mExtensionList = new ArrayList();
    private String mKeyId;
    private String mNotAfter;
    private String mNotBefore;
    private String mPermitIssuerId;
    private String mPermitType;
    private String mRvctionDistributionPoint;
    private String mSerialNum;
    private String mServiceId;
    private byte[] mSignature;
    private String mVersion;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Permit version = " + this.mVersion);
        stringBuffer.append(", permitType = " + this.mPermitType);
        stringBuffer.append(", permitIssuerId = " + this.mPermitIssuerId);
        stringBuffer.append(", serialNum = " + this.mSerialNum);
        stringBuffer.append(", keyId = " + this.mKeyId);
        stringBuffer.append(", notBefore = " + this.mNotBefore);
        stringBuffer.append(", notAfter = " + this.mNotAfter);
        stringBuffer.append(", serviceId = " + this.mServiceId);
        stringBuffer.append(", rvctionDistributionPoint = " + this.mRvctionDistributionPoint);
        stringBuffer.append(", signature = [" + StringUtil.toHexString(this.mSignature) + "]");
        for (int i = 0; i < this.mExtensionList.size(); i++) {
            stringBuffer.append(", extentionList[" + i + "] = {" + this.mExtensionList.get(i) + "}");
        }
        return stringBuffer.toString();
    }

    public String getVersion() {
        return this.mVersion;
    }

    public void setVersion(String str) {
        this.mVersion = str;
    }

    public String getPermitType() {
        return this.mPermitType;
    }

    public void setPermitType(String str) {
        this.mPermitType = str;
    }

    public String getPermitIssuerId() {
        return this.mPermitIssuerId;
    }

    public void setPermitIssuerId(String str) {
        this.mPermitIssuerId = str;
    }

    public String getSerialNum() {
        return this.mSerialNum;
    }

    public void setSerialNum(String str) {
        this.mSerialNum = str;
    }

    public String getKeyId() {
        return this.mKeyId;
    }

    public void setKeyId(String str) {
        this.mKeyId = str;
    }

    public String getNotBefore() {
        return this.mNotBefore;
    }

    public void setNotBefore(String str) {
        this.mNotBefore = str;
    }

    public String getNotAfter() {
        return this.mNotAfter;
    }

    public void setNotAfter(String str) {
        this.mNotAfter = str;
    }

    public String getServiceId() {
        return this.mServiceId;
    }

    public void setServiceId(String str) {
        this.mServiceId = str;
    }

    public String getRvctionDistributionPoint() {
        return this.mRvctionDistributionPoint;
    }

    public void setRvctionDistributionPoint(String str) {
        this.mRvctionDistributionPoint = str;
    }

    public byte[] getSignature() {
        return this.mSignature;
    }

    public void setSignature(byte[] bArr) {
        this.mSignature = bArr;
    }

    public ExtensionParameter getExtension(String str) {
        for (int i = 0; i < this.mExtensionList.size(); i++) {
            ExtensionParameter extensionParameter = (ExtensionParameter) this.mExtensionList.get(i);
            if (extensionParameter.getId().equals(str)) {
                return extensionParameter;
            }
        }
        return null;
    }

    public ExtensionParameter getExtension(int i) {
        return (ExtensionParameter) this.mExtensionList.get(i);
    }

    public int extensionSize() {
        return this.mExtensionList.size();
    }

    public void addExtension(ExtensionParameter extensionParameter) {
        this.mExtensionList.add(extensionParameter);
    }
}
