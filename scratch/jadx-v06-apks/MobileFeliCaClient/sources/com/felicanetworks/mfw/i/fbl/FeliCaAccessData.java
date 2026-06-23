package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.ArrayList;
import com.felicanetworks.mfw.i.cmn.StringUtil;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaAccessData implements ExtensionParameter {
    private ArrayList mOfflineAccessTargetArray = new ArrayList();
    private byte[] mOrdinaryCommandCategory;
    private byte[] mPrivilegedCommandCategory;

    @Override // com.felicanetworks.mfw.i.fbl.ExtensionParameter
    public String getId() {
        return ExtensionParameter.FELICA_ACCESS;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("FeliCaAccessData privilegedCommandCategory = [" + StringUtil.toHexString(this.mPrivilegedCommandCategory) + "]");
        stringBuffer.append(", ordinaryCommandCategory = [" + StringUtil.toHexString(this.mOrdinaryCommandCategory) + "]");
        for (int i = 0; i < this.mOfflineAccessTargetArray.size(); i++) {
            stringBuffer.append(", offlineAccessTargetArray[" + i + "] = " + this.mOfflineAccessTargetArray.get(i));
        }
        return stringBuffer.toString();
    }

    public byte[] getPrivilegedCommandCategory() {
        return this.mPrivilegedCommandCategory;
    }

    public void setPrivilegedCommandCategory(byte[] bArr) {
        this.mPrivilegedCommandCategory = bArr;
    }

    public byte[] getOrdinaryCommandCategory() {
        return this.mOrdinaryCommandCategory;
    }

    public void setOrdinaryCommandCategory(byte[] bArr) {
        this.mOrdinaryCommandCategory = bArr;
    }

    public int offlineAccessTargetSize() {
        return this.mOfflineAccessTargetArray.size();
    }

    public OfflineAccessTarget getOfflineAccessTarget(int i) {
        return (OfflineAccessTarget) this.mOfflineAccessTargetArray.get(i);
    }

    public void addOfflineAccessTarget(OfflineAccessTarget offlineAccessTarget) {
        this.mOfflineAccessTargetArray.add(offlineAccessTarget);
    }
}
