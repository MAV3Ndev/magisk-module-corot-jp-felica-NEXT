package com.felicanetworks.mfw.i.fbl;

import com.felicanetworks.mfw.i.cmn.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class OfflineAccessTarget {
    private ArrayList mNodeCodeRangeArray = new ArrayList();
    private String mSystemCode;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("OfflineAccessTarget systemCode = " + this.mSystemCode);
        for (int i = 0; i < this.mNodeCodeRangeArray.size(); i++) {
            stringBuffer.append("nodeCodeRangeArray[" + i + "] = " + this.mNodeCodeRangeArray.get(i));
        }
        return stringBuffer.toString();
    }

    public String getSystemCode() {
        return this.mSystemCode;
    }

    public void setSystemCode(String str) {
        this.mSystemCode = str;
    }

    public int nodeCodeRangeSize() {
        return this.mNodeCodeRangeArray.size();
    }

    public NodeCodeRange getNodeCodeRange(int i) {
        return (NodeCodeRange) this.mNodeCodeRangeArray.get(i);
    }

    public void addNodeCodeRange(NodeCodeRange nodeCodeRange) {
        this.mNodeCodeRangeArray.add(nodeCodeRange);
    }
}
