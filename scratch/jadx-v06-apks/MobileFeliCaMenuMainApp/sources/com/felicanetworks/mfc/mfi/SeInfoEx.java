package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes.dex */
public class SeInfoEx {
    private String mCommonAreaIDm;
    private String mContainerId;
    private String mContainerIssueInformation;
    private String mIcCode;
    private String mManagementAreaIDm;

    public SeInfoEx(String str, String str2, String str3, String str4, String str5) {
        this.mManagementAreaIDm = str;
        this.mCommonAreaIDm = str2;
        this.mIcCode = str3;
        this.mContainerIssueInformation = str4;
        this.mContainerId = str5;
    }

    public String getManagementAreaIDm() {
        return this.mManagementAreaIDm;
    }

    public String getCommonAreaIDm() {
        return this.mCommonAreaIDm;
    }

    public String getIcCode() {
        return this.mIcCode;
    }

    public String getContainerIssueInformation() {
        return this.mContainerIssueInformation;
    }

    public String getContainerId() {
        return this.mContainerId;
    }
}
