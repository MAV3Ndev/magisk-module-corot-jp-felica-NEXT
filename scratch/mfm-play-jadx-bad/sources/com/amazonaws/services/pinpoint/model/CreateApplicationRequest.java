package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CreateApplicationRequest implements Serializable {
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public CreateApplicationRequest withName(String str) {
        this.name = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getName() != null) {
            sb.append("Name: " + getName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getName() == null ? 0 : getName().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateApplicationRequest)) {
            return false;
        }
        CreateApplicationRequest createApplicationRequest = (CreateApplicationRequest) obj;
        if ((createApplicationRequest.getName() == null) ^ (getName() == null)) {
            return false;
        }
        return createApplicationRequest.getName() == null || createApplicationRequest.getName().equals(getName());
    }
}
