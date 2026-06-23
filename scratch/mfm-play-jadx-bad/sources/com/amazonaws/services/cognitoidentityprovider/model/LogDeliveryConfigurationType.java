package com.amazonaws.services.cognitoidentityprovider.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class LogDeliveryConfigurationType implements Serializable {
    private List<LogConfigurationType> logConfigurations;
    private String userPoolId;

    public String getUserPoolId() {
        return this.userPoolId;
    }

    public void setUserPoolId(String str) {
        this.userPoolId = str;
    }

    public LogDeliveryConfigurationType withUserPoolId(String str) {
        this.userPoolId = str;
        return this;
    }

    public List<LogConfigurationType> getLogConfigurations() {
        return this.logConfigurations;
    }

    public void setLogConfigurations(Collection<LogConfigurationType> collection) {
        if (collection == null) {
            this.logConfigurations = null;
        } else {
            this.logConfigurations = new ArrayList(collection);
        }
    }

    public LogDeliveryConfigurationType withLogConfigurations(LogConfigurationType... logConfigurationTypeArr) {
        if (getLogConfigurations() == null) {
            this.logConfigurations = new ArrayList(logConfigurationTypeArr.length);
        }
        for (LogConfigurationType logConfigurationType : logConfigurationTypeArr) {
            this.logConfigurations.add(logConfigurationType);
        }
        return this;
    }

    public LogDeliveryConfigurationType withLogConfigurations(Collection<LogConfigurationType> collection) {
        setLogConfigurations(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getUserPoolId() != null) {
            sb.append("UserPoolId: " + getUserPoolId() + ",");
        }
        if (getLogConfigurations() != null) {
            sb.append("LogConfigurations: " + getLogConfigurations());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getUserPoolId() == null ? 0 : getUserPoolId().hashCode()) + 31) * 31) + (getLogConfigurations() != null ? getLogConfigurations().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof LogDeliveryConfigurationType)) {
            return false;
        }
        LogDeliveryConfigurationType logDeliveryConfigurationType = (LogDeliveryConfigurationType) obj;
        if ((logDeliveryConfigurationType.getUserPoolId() == null) ^ (getUserPoolId() == null)) {
            return false;
        }
        if (logDeliveryConfigurationType.getUserPoolId() != null && !logDeliveryConfigurationType.getUserPoolId().equals(getUserPoolId())) {
            return false;
        }
        if ((logDeliveryConfigurationType.getLogConfigurations() == null) ^ (getLogConfigurations() == null)) {
            return false;
        }
        return logDeliveryConfigurationType.getLogConfigurations() == null || logDeliveryConfigurationType.getLogConfigurations().equals(getLogConfigurations());
    }
}
