package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class RemoveAttributesResult implements Serializable {
    private AttributesResource attributesResource;

    public AttributesResource getAttributesResource() {
        return this.attributesResource;
    }

    public void setAttributesResource(AttributesResource attributesResource) {
        this.attributesResource = attributesResource;
    }

    public RemoveAttributesResult withAttributesResource(AttributesResource attributesResource) {
        this.attributesResource = attributesResource;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getAttributesResource() != null) {
            sb.append("AttributesResource: " + getAttributesResource());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return 31 + (getAttributesResource() == null ? 0 : getAttributesResource().hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof RemoveAttributesResult)) {
            return false;
        }
        RemoveAttributesResult removeAttributesResult = (RemoveAttributesResult) obj;
        if ((removeAttributesResult.getAttributesResource() == null) ^ (getAttributesResource() == null)) {
            return false;
        }
        return removeAttributesResult.getAttributesResource() == null || removeAttributesResult.getAttributesResource().equals(getAttributesResource());
    }
}
