package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AttributesResource implements Serializable {
    private String applicationId;
    private String attributeType;
    private List<String> attributes;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public AttributesResource withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getAttributeType() {
        return this.attributeType;
    }

    public void setAttributeType(String str) {
        this.attributeType = str;
    }

    public AttributesResource withAttributeType(String str) {
        this.attributeType = str;
        return this;
    }

    public List<String> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(Collection<String> collection) {
        if (collection == null) {
            this.attributes = null;
        } else {
            this.attributes = new ArrayList(collection);
        }
    }

    public AttributesResource withAttributes(String... strArr) {
        if (getAttributes() == null) {
            this.attributes = new ArrayList(strArr.length);
        }
        for (String str : strArr) {
            this.attributes.add(str);
        }
        return this;
    }

    public AttributesResource withAttributes(Collection<String> collection) {
        setAttributes(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getAttributeType() != null) {
            sb.append("AttributeType: " + getAttributeType() + ",");
        }
        if (getAttributes() != null) {
            sb.append("Attributes: " + getAttributes());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getAttributeType() == null ? 0 : getAttributeType().hashCode())) * 31) + (getAttributes() != null ? getAttributes().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AttributesResource)) {
            return false;
        }
        AttributesResource attributesResource = (AttributesResource) obj;
        if ((attributesResource.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (attributesResource.getApplicationId() != null && !attributesResource.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((attributesResource.getAttributeType() == null) ^ (getAttributeType() == null)) {
            return false;
        }
        if (attributesResource.getAttributeType() != null && !attributesResource.getAttributeType().equals(getAttributeType())) {
            return false;
        }
        if ((attributesResource.getAttributes() == null) ^ (getAttributes() == null)) {
            return false;
        }
        return attributesResource.getAttributes() == null || attributesResource.getAttributes().equals(getAttributes());
    }
}
