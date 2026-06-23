package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class GetApplicationSettingsResult implements Serializable {
    private ApplicationSettingsResource applicationSettingsResource;

    public ApplicationSettingsResource getApplicationSettingsResource() {
        return this.applicationSettingsResource;
    }

    public void setApplicationSettingsResource(ApplicationSettingsResource applicationSettingsResource) {
        this.applicationSettingsResource = applicationSettingsResource;
    }

    public GetApplicationSettingsResult withApplicationSettingsResource(ApplicationSettingsResource applicationSettingsResource) {
        this.applicationSettingsResource = applicationSettingsResource;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationSettingsResource() != null) {
            sb.append("ApplicationSettingsResource: " + getApplicationSettingsResource());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getApplicationSettingsResource() == null ? 0 : getApplicationSettingsResource().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof GetApplicationSettingsResult)) {
            return false;
        }
        GetApplicationSettingsResult getApplicationSettingsResult = (GetApplicationSettingsResult) obj;
        if ((getApplicationSettingsResult.getApplicationSettingsResource() == null) ^ (getApplicationSettingsResource() == null)) {
            return false;
        }
        return getApplicationSettingsResult.getApplicationSettingsResource() == null || getApplicationSettingsResult.getApplicationSettingsResource().equals(getApplicationSettingsResource());
    }
}
