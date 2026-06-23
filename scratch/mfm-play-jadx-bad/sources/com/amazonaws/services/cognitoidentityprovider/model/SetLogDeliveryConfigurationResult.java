package com.amazonaws.services.cognitoidentityprovider.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SetLogDeliveryConfigurationResult implements Serializable {
    private LogDeliveryConfigurationType logDeliveryConfiguration;

    public LogDeliveryConfigurationType getLogDeliveryConfiguration() {
        return this.logDeliveryConfiguration;
    }

    public void setLogDeliveryConfiguration(LogDeliveryConfigurationType logDeliveryConfigurationType) {
        this.logDeliveryConfiguration = logDeliveryConfigurationType;
    }

    public SetLogDeliveryConfigurationResult withLogDeliveryConfiguration(LogDeliveryConfigurationType logDeliveryConfigurationType) {
        this.logDeliveryConfiguration = logDeliveryConfigurationType;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getLogDeliveryConfiguration() != null) {
            sb.append("LogDeliveryConfiguration: " + getLogDeliveryConfiguration());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getLogDeliveryConfiguration() == null ? 0 : getLogDeliveryConfiguration().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SetLogDeliveryConfigurationResult)) {
            return false;
        }
        SetLogDeliveryConfigurationResult setLogDeliveryConfigurationResult = (SetLogDeliveryConfigurationResult) obj;
        if ((setLogDeliveryConfigurationResult.getLogDeliveryConfiguration() == null) ^ (getLogDeliveryConfiguration() == null)) {
            return false;
        }
        return setLogDeliveryConfigurationResult.getLogDeliveryConfiguration() == null || setLogDeliveryConfigurationResult.getLogDeliveryConfiguration().equals(getLogDeliveryConfiguration());
    }
}
