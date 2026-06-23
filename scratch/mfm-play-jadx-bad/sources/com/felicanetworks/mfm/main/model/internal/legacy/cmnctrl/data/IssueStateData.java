package com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.data;

import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class IssueStateData {
    public byte[] containerIssueInfo;
    public String icCode;
    public String idmData;

    public IssueStateData(byte[] contIssueInfo, String idmData, String icCode) {
        this.containerIssueInfo = contIssueInfo;
        this.idmData = idmData;
        this.icCode = icCode;
    }

    public String toString() {
        return "IssueStateData{containerIssueInfo=" + Arrays.toString(this.containerIssueInfo) + ", idmData='" + this.idmData + "', icCode='" + this.icCode + "'}";
    }
}
