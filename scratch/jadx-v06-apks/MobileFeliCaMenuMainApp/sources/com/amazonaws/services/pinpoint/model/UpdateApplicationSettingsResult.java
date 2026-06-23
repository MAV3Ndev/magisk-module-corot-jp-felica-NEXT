package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class UpdateApplicationSettingsResult implements Serializable {
    private ApplicationSettingsResource applicationSettingsResource;

    public ApplicationSettingsResource getApplicationSettingsResource() {
        return this.applicationSettingsResource;
    }

    public void setApplicationSettingsResource(ApplicationSettingsResource applicationSettingsResource) {
        this.applicationSettingsResource = applicationSettingsResource;
    }

    public UpdateApplicationSettingsResult withApplicationSettingsResource(ApplicationSettingsResource applicationSettingsResource) {
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
        if (obj == null || !(obj instanceof UpdateApplicationSettingsResult)) {
            return false;
        }
        UpdateApplicationSettingsResult updateApplicationSettingsResult = (UpdateApplicationSettingsResult) obj;
        if ((updateApplicationSettingsResult.getApplicationSettingsResource() == null) ^ (getApplicationSettingsResource() == null)) {
            return false;
        }
        return updateApplicationSettingsResult.getApplicationSettingsResource() == null || updateApplicationSettingsResult.getApplicationSettingsResource().equals(getApplicationSettingsResource());
    }
}
