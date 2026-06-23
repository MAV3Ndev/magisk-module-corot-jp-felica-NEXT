package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class WriteEventStream implements Serializable {
    private String destinationStreamArn;
    private String roleArn;

    public String getDestinationStreamArn() {
        return this.destinationStreamArn;
    }

    public void setDestinationStreamArn(String str) {
        this.destinationStreamArn = str;
    }

    public WriteEventStream withDestinationStreamArn(String str) {
        this.destinationStreamArn = str;
        return this;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public WriteEventStream withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getDestinationStreamArn() != null) {
            sb.append("DestinationStreamArn: " + getDestinationStreamArn() + ",");
        }
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getDestinationStreamArn() == null ? 0 : getDestinationStreamArn().hashCode()) + 31) * 31) + (getRoleArn() != null ? getRoleArn().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof WriteEventStream)) {
            return false;
        }
        WriteEventStream writeEventStream = (WriteEventStream) obj;
        if ((writeEventStream.getDestinationStreamArn() == null) ^ (getDestinationStreamArn() == null)) {
            return false;
        }
        if (writeEventStream.getDestinationStreamArn() != null && !writeEventStream.getDestinationStreamArn().equals(getDestinationStreamArn())) {
            return false;
        }
        if ((writeEventStream.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        return writeEventStream.getRoleArn() == null || writeEventStream.getRoleArn().equals(getRoleArn());
    }
}
