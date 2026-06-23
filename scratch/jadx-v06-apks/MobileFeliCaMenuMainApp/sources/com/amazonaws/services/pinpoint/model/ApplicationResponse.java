package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ApplicationResponse implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public ApplicationResponse withId(String str) {
        this.id = str;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public ApplicationResponse withName(String str) {
        this.name = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getName() != null) {
            sb.append("Name: " + getName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getId() == null ? 0 : getId().hashCode()) + 31) * 31) + (getName() != null ? getName().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ApplicationResponse)) {
            return false;
        }
        ApplicationResponse applicationResponse = (ApplicationResponse) obj;
        if ((applicationResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (applicationResponse.getId() != null && !applicationResponse.getId().equals(getId())) {
            return false;
        }
        if ((applicationResponse.getName() == null) ^ (getName() == null)) {
            return false;
        }
        return applicationResponse.getName() == null || applicationResponse.getName().equals(getName());
    }
}
