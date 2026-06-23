package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public class SeInfoEx {
    private String mCommonAreaIDm;
    private String mContainerId;
    private String mContainerIssueInformation;
    private String mIcCode;
    private String mManagementAreaIDm;

    public SeInfoEx(String managementAreaIDm, String commonAreaIDm, String icCode, String containerIssueInformation, String containerId) {
        this.mManagementAreaIDm = managementAreaIDm;
        this.mCommonAreaIDm = commonAreaIDm;
        this.mIcCode = icCode;
        this.mContainerIssueInformation = containerIssueInformation;
        this.mContainerId = containerId;
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
