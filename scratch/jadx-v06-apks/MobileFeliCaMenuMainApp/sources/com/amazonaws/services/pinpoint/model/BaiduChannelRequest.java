package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class BaiduChannelRequest implements Serializable {
    private String apiKey;
    private Boolean enabled;
    private String secretKey;

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String str) {
        this.apiKey = str;
    }

    public BaiduChannelRequest withApiKey(String str) {
        this.apiKey = str;
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

    public BaiduChannelRequest withEnabled(Boolean bool) {
        this.enabled = bool;
        return this;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String str) {
        this.secretKey = str;
    }

    public BaiduChannelRequest withSecretKey(String str) {
        this.secretKey = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApiKey() != null) {
            sb.append("ApiKey: " + getApiKey() + ",");
        }
        if (getEnabled() != null) {
            sb.append("Enabled: " + getEnabled() + ",");
        }
        if (getSecretKey() != null) {
            sb.append("SecretKey: " + getSecretKey());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApiKey() == null ? 0 : getApiKey().hashCode()) + 31) * 31) + (getEnabled() == null ? 0 : getEnabled().hashCode())) * 31) + (getSecretKey() != null ? getSecretKey().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof BaiduChannelRequest)) {
            return false;
        }
        BaiduChannelRequest baiduChannelRequest = (BaiduChannelRequest) obj;
        if ((baiduChannelRequest.getApiKey() == null) ^ (getApiKey() == null)) {
            return false;
        }
        if (baiduChannelRequest.getApiKey() != null && !baiduChannelRequest.getApiKey().equals(getApiKey())) {
            return false;
        }
        if ((baiduChannelRequest.getEnabled() == null) ^ (getEnabled() == null)) {
            return false;
        }
        if (baiduChannelRequest.getEnabled() != null && !baiduChannelRequest.getEnabled().equals(getEnabled())) {
            return false;
        }
        if ((baiduChannelRequest.getSecretKey() == null) ^ (getSecretKey() == null)) {
            return false;
        }
        return baiduChannelRequest.getSecretKey() == null || baiduChannelRequest.getSecretKey().equals(getSecretKey());
    }
}
