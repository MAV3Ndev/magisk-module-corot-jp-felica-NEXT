package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class APNSVoipSandboxChannelRequest implements Serializable {
    private String bundleId;
    private String certificate;
    private String defaultAuthenticationMethod;
    private Boolean enabled;
    private String privateKey;
    private String teamId;
    private String tokenKey;
    private String tokenKeyId;

    public String getBundleId() {
        return this.bundleId;
    }

    public void setBundleId(String str) {
        this.bundleId = str;
    }

    public APNSVoipSandboxChannelRequest withBundleId(String str) {
        this.bundleId = str;
        return this;
    }

    public String getCertificate() {
        return this.certificate;
    }

    public void setCertificate(String str) {
        this.certificate = str;
    }

    public APNSVoipSandboxChannelRequest withCertificate(String str) {
        this.certificate = str;
        return this;
    }

    public String getDefaultAuthenticationMethod() {
        return this.defaultAuthenticationMethod;
    }

    public void setDefaultAuthenticationMethod(String str) {
        this.defaultAuthenticationMethod = str;
    }

    public APNSVoipSandboxChannelRequest withDefaultAuthenticationMethod(String str) {
        this.defaultAuthenticationMethod = str;
        return this;
    }

    public Boolean isEnabled() {
        return this.enabled;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean bool) {
        this.enabled = bool;
    }

    public APNSVoipSandboxChannelRequest withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public void setPrivateKey(String str) {
        this.privateKey = str;
    }

    public APNSVoipSandboxChannelRequest withPrivateKey(String str) {
        this.privateKey = str;
        return this;
    }

    public String getTeamId() {
        return this.teamId;
    }

    public void setTeamId(String str) {
        this.teamId = str;
    }

    public APNSVoipSandboxChannelRequest withTeamId(String str) {
        this.teamId = str;
        return this;
    }

    public String getTokenKey() {
        return this.tokenKey;
    }

    public void setTokenKey(String str) {
        this.tokenKey = str;
    }

    public APNSVoipSandboxChannelRequest withTokenKey(String str) {
        this.tokenKey = str;
        return this;
    }

    public String getTokenKeyId() {
        return this.tokenKeyId;
    }

    public void setTokenKeyId(String str) {
        this.tokenKeyId = str;
    }

    public APNSVoipSandboxChannelRequest withTokenKeyId(String str) {
        this.tokenKeyId = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getBundleId() != null) {
            sb.append("BundleId: " + getBundleId() + ",");
        }
        if (getCertificate() != null) {
            sb.append("Certificate: " + getCertificate() + ",");
        }
        if (getDefaultAuthenticationMethod() != null) {
            sb.append("DefaultAuthenticationMethod: " + getDefaultAuthenticationMethod() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled() + ",");
        }
        if (getPrivateKey() != null) {
            sb.append("PrivateKey: " + getPrivateKey() + ",");
        }
        if (getTeamId() != null) {
            sb.append("TeamId: " + getTeamId() + ",");
        }
        if (getTokenKey() != null) {
            sb.append("TokenKey: " + getTokenKey() + ",");
        }
        if (getTokenKeyId() != null) {
            sb.append("TokenKeyId: " + getTokenKeyId());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((getBundleId() == null ? 0 : getBundleId().hashCode()) + 31) * 31) + (getCertificate() == null ? 0 : getCertificate().hashCode())) * 31) + (getDefaultAuthenticationMethod() == null ? 0 : getDefaultAuthenticationMethod().hashCode())) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getPrivateKey() == null ? 0 : getPrivateKey().hashCode())) * 31) + (getTeamId() == null ? 0 : getTeamId().hashCode())) * 31) + (getTokenKey() == null ? 0 : getTokenKey().hashCode())) * 31) + (getTokenKeyId() != null ? getTokenKeyId().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof APNSVoipSandboxChannelRequest)) {
            return false;
        }
        APNSVoipSandboxChannelRequest aPNSVoipSandboxChannelRequest = (APNSVoipSandboxChannelRequest) obj;
        if ((aPNSVoipSandboxChannelRequest.getBundleId() == null) ^ (getBundleId() == null)) {
            return false;
        }
        if (aPNSVoipSandboxChannelRequest.getBundleId() != null && !aPNSVoipSandboxChannelRequest.getBundleId().equals(getBundleId())) {
            return false;
        }
        if ((aPNSVoipSandboxChannelRequest.getCertificate() == null) ^ (getCertificate() == null)) {
            return false;
        }
        if (aPNSVoipSandboxChannelRequest.getCertificate() != null && !aPNSVoipSandboxChannelRequest.getCertificate().equals(getCertificate())) {
            return false;
        }
        if ((aPNSVoipSandboxChannelRequest.getDefaultAuthenticationMethod() == null) ^ (getDefaultAuthenticationMethod() == null)) {
            return false;
        }
        if (aPNSVoipSandboxChannelRequest.getDefaultAuthenticationMethod() != null && !aPNSVoipSandboxChannelRequest.getDefaultAuthenticationMethod().equals(getDefaultAuthenticationMethod())) {
            return false;
        }
        if ((aPNSVoipSandboxChannelRequest.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (aPNSVoipSandboxChannelRequest.getEnabled() != null && !aPNSVoipSandboxChannelRequest.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((aPNSVoipSandboxChannelRequest.getPrivateKey() == null) ^ (getPrivateKey() == null)) {
            return false;
        }
        if (aPNSVoipSandboxChannelRequest.getPrivateKey() != null && !aPNSVoipSandboxChannelRequest.getPrivateKey().equals(getPrivateKey())) {
            return false;
        }
        if ((aPNSVoipSandboxChannelRequest.getTeamId() == null) ^ (getTeamId() == null)) {
            return false;
        }
        if (aPNSVoipSandboxChannelRequest.getTeamId() != null && !aPNSVoipSandboxChannelRequest.getTeamId().equals(getTeamId())) {
            return false;
        }
        if ((aPNSVoipSandboxChannelRequest.getTokenKey() == null) ^ (getTokenKey() == null)) {
            return false;
        }
        if (aPNSVoipSandboxChannelRequest.getTokenKey() != null && !aPNSVoipSandboxChannelRequest.getTokenKey().equals(getTokenKey())) {
            return false;
        }
        if ((aPNSVoipSandboxChannelRequest.getTokenKeyId() == null) ^ (getTokenKeyId() == null)) {
            return false;
        }
        return aPNSVoipSandboxChannelRequest.getTokenKeyId() == null || aPNSVoipSandboxChannelRequest.getTokenKeyId().equals(getTokenKeyId());
    }
}
