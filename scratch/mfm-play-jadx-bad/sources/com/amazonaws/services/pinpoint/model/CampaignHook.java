package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CampaignHook implements Serializable {
    private String lambdaFunctionName;
    private String mode;
    private String webUrl;

    public String getLambdaFunctionName() {
        return this.lambdaFunctionName;
    }

    public void setLambdaFunctionName(String str) {
        this.lambdaFunctionName = str;
    }

    public CampaignHook withLambdaFunctionName(String str) {
        this.lambdaFunctionName = str;
        return this;
    }

    public String getMode() {
        return this.mode;
    }

    public void setMode(String str) {
        this.mode = str;
    }

    public CampaignHook withMode(String str) {
        this.mode = str;
        return this;
    }

    public void setMode(Mode mode) {
        this.mode = mode.toString();
    }

    public CampaignHook withMode(Mode mode) {
        this.mode = mode.toString();
        return this;
    }

    public String getWebUrl() {
        return this.webUrl;
    }

    public void setWebUrl(String str) {
        this.webUrl = str;
    }

    public CampaignHook withWebUrl(String str) {
        this.webUrl = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getLambdaFunctionName() != null) {
            sb.append("LambdaFunctionName: " + getLambdaFunctionName() + ",");
        }
        if (getMode() != null) {
            sb.append("Mode: " + getMode() + ",");
        }
        if (getWebUrl() != null) {
            sb.append("WebUrl: " + getWebUrl());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getLambdaFunctionName() == null ? 0 : getLambdaFunctionName().hashCode()) + 31) * 31) + (getMode() == null ? 0 : getMode().hashCode())) * 31) + (getWebUrl() != null ? getWebUrl().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CampaignHook)) {
            return false;
        }
        CampaignHook campaignHook = (CampaignHook) obj;
        if ((campaignHook.getLambdaFunctionName() == null) ^ (getLambdaFunctionName() == null)) {
            return false;
        }
        if (campaignHook.getLambdaFunctionName() != null && !campaignHook.getLambdaFunctionName().equals(getLambdaFunctionName())) {
            return false;
        }
        if ((campaignHook.getMode() == null) ^ (getMode() == null)) {
            return false;
        }
        if (campaignHook.getMode() != null && !campaignHook.getMode().equals(getMode())) {
            return false;
        }
        if ((campaignHook.getWebUrl() == null) ^ (getWebUrl() == null)) {
            return false;
        }
        return campaignHook.getWebUrl() == null || campaignHook.getWebUrl().equals(getWebUrl());
    }
}
