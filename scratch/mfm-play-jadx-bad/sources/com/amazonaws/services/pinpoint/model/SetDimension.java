package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class SetDimension implements Serializable {
    private String dimensionType;
    private List<String> values;

    public String getDimensionType() {
        return this.dimensionType;
    }

    public void setDimensionType(String str) {
        this.dimensionType = str;
    }

    public SetDimension withDimensionType(String str) {
        this.dimensionType = str;
        return this;
    }

    public void setDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType.toString();
    }

    public SetDimension withDimensionType(DimensionType dimensionType) {
        this.dimensionType = dimensionType.toString();
        return this;
    }

    public List<String> getValues() {
        return this.values;
    }

    public void setValues(Collection<String> collection) {
        if (collection == null) {
            this.values = null;
        } else {
            this.values = new ArrayList(collection);
        }
    }

    public SetDimension withValues(String... strArr) {
        if (getValues() == null) {
            this.values = new ArrayList(strArr.length);
        }
        for (String str : strArr) {
            this.values.add(str);
        }
        return this;
    }

    public SetDimension withValues(Collection<String> collection) {
        setValues(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getDimensionType() != null) {
            sb.append("DimensionType: " + getDimensionType() + ",");
        }
        if (getValues() != null) {
            sb.append("Values: " + getValues());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getDimensionType() == null ? 0 : getDimensionType().hashCode()) + 31) * 31) + (getValues() != null ? getValues().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SetDimension)) {
            return false;
        }
        SetDimension setDimension = (SetDimension) obj;
        if ((setDimension.getDimensionType() == null) ^ (getDimensionType() == null)) {
            return false;
        }
        if (setDimension.getDimensionType() != null && !setDimension.getDimensionType().equals(getDimensionType())) {
            return false;
        }
        if ((setDimension.getValues() == null) ^ (getValues() == null)) {
            return false;
        }
        return setDimension.getValues() == null || setDimension.getValues().equals(getValues());
    }
}
