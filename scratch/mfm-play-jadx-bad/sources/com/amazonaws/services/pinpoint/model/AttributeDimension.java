package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AttributeDimension implements Serializable {
    private String attributeType;
    private List<String> values;

    public String getAttributeType() {
        return this.attributeType;
    }

    public void setAttributeType(String str) {
        this.attributeType = str;
    }

    public AttributeDimension withAttributeType(String str) {
        this.attributeType = str;
        return this;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType.toString();
    }

    public AttributeDimension withAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType.toString();
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

    public AttributeDimension withValues(String... strArr) {
        if (getValues() == null) {
            this.values = new ArrayList(strArr.length);
        }
        for (String str : strArr) {
            this.values.add(str);
        }
        return this;
    }

    public AttributeDimension withValues(Collection<String> collection) {
        setValues(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getAttributeType() != null) {
            sb.append("AttributeType: " + getAttributeType() + ",");
        }
        if (getValues() != null) {
            sb.append("Values: " + getValues());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((getAttributeType() == null ? 0 : getAttributeType().hashCode()) + 31) * 31) + (getValues() != null ? getValues().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AttributeDimension)) {
            return false;
        }
        AttributeDimension attributeDimension = (AttributeDimension) obj;
        if ((attributeDimension.getAttributeType() == null) ^ (getAttributeType() == null)) {
            return false;
        }
        if (attributeDimension.getAttributeType() != null && !attributeDimension.getAttributeType().equals(getAttributeType())) {
            return false;
        }
        if ((attributeDimension.getValues() == null) ^ (getValues() == null)) {
            return false;
        }
        return attributeDimension.getValues() == null || attributeDimension.getValues().equals(getValues());
    }
}
