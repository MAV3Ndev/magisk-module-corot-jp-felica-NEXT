package com.felicanetworks.mfw.i.fbl;

/* JADX INFO: loaded from: classes.dex */
public class NodeCodeRange {
    private String mLowerNode;
    private String mUpperNode;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NodeCodeRange lowerNode = " + this.mLowerNode);
        stringBuffer.append(", upperNode = " + this.mUpperNode);
        return stringBuffer.toString();
    }

    public String getLowerNode() {
        return this.mLowerNode;
    }

    public void setLowerNode(String str) {
        this.mLowerNode = str;
    }

    public String getUpperNode() {
        return this.mUpperNode;
    }

    public void setUpperNode(String str) {
        this.mUpperNode = str;
    }
}
