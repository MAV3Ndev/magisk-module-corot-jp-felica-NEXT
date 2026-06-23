package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data;

/* JADX INFO: loaded from: classes.dex */
public class IssueStateData {
    public String icCode;
    public String idmData;
    public boolean issueStateResult;

    public IssueStateData(boolean z, String str, String str2) {
        this.issueStateResult = z;
        this.idmData = str;
        this.icCode = str2;
    }

    public String toString() {
        return "IssueStateData{issueStateResult=" + this.issueStateResult + ", idmData='" + this.idmData + "', icCode='" + this.icCode + "'}";
    }
}
