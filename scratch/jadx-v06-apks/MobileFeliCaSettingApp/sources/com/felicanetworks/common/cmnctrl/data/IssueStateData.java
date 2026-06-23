package com.felicanetworks.common.cmnctrl.data;

/* JADX INFO: loaded from: classes.dex */
public class IssueStateData {
    public String apiCodeBeta;
    public String apiCodeVersion;
    public String icCode;
    public String idmData;
    public boolean issueStateResult;

    public IssueStateData(boolean z, String str, String str2, String str3, String str4) {
        this.issueStateResult = z;
        this.idmData = str;
        this.icCode = str2;
        this.apiCodeBeta = str3;
        this.apiCodeVersion = str4;
    }

    public String toString() {
        return "IssueStateData[" + this.issueStateResult + ", " + this.idmData + ", " + this.icCode + ", " + this.apiCodeBeta + ", " + this.apiCodeVersion + "]";
    }
}
