package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class SegmentResponse implements Serializable {
    private String applicationId;
    private String creationDate;
    private SegmentDimensions dimensions;
    private String id;
    private SegmentImportResource importDefinition;
    private String lastModifiedDate;
    private String name;
    private SegmentGroupList segmentGroups;
    private String segmentType;
    private Integer version;

    public String getApplicationId() {
        return this.applicationId;
    }

    public void setApplicationId(String str) {
        this.applicationId = str;
    }

    public SegmentResponse withApplicationId(String str) {
        this.applicationId = str;
        return this;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(String str) {
        this.creationDate = str;
    }

    public SegmentResponse withCreationDate(String str) {
        this.creationDate = str;
        return this;
    }

    public SegmentDimensions getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(SegmentDimensions segmentDimensions) {
        this.dimensions = segmentDimensions;
    }

    public SegmentResponse withDimensions(SegmentDimensions segmentDimensions) {
        this.dimensions = segmentDimensions;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public SegmentResponse withId(String str) {
        this.id = str;
        return this;
    }

    public SegmentImportResource getImportDefinition() {
        return this.importDefinition;
    }

    public void setImportDefinition(SegmentImportResource segmentImportResource) {
        this.importDefinition = segmentImportResource;
    }

    public SegmentResponse withImportDefinition(SegmentImportResource segmentImportResource) {
        this.importDefinition = segmentImportResource;
        return this;
    }

    public String getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(String str) {
        this.lastModifiedDate = str;
    }

    public SegmentResponse withLastModifiedDate(String str) {
        this.lastModifiedDate = str;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public SegmentResponse withName(String str) {
        this.name = str;
        return this;
    }

    public SegmentGroupList getSegmentGroups() {
        return this.segmentGroups;
    }

    public void setSegmentGroups(SegmentGroupList segmentGroupList) {
        this.segmentGroups = segmentGroupList;
    }

    public SegmentResponse withSegmentGroups(SegmentGroupList segmentGroupList) {
        this.segmentGroups = segmentGroupList;
        return this;
    }

    public String getSegmentType() {
        return this.segmentType;
    }

    public void setSegmentType(String str) {
        this.segmentType = str;
    }

    public SegmentResponse withSegmentType(String str) {
        this.segmentType = str;
        return this;
    }

    public void setSegmentType(SegmentType segmentType) {
        this.segmentType = segmentType.toString();
    }

    public SegmentResponse withSegmentType(SegmentType segmentType) {
        this.segmentType = segmentType.toString();
        return this;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer num) {
        this.version = num;
    }

    public SegmentResponse withVersion(Integer num) {
        this.version = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getApplicationId() != null) {
            sb.append("ApplicationId: " + getApplicationId() + ",");
        }
        if (getCreationDate() != null) {
            sb.append("CreationDate: " + getCreationDate() + ",");
        }
        if (getDimensions() != null) {
            sb.append("Dimensions: " + getDimensions() + ",");
        }
        if (getId() != null) {
            sb.append("Id: " + getId() + ",");
        }
        if (getImportDefinition() != null) {
            sb.append("ImportDefinition: " + getImportDefinition() + ",");
        }
        if (getLastModifiedDate() != null) {
            sb.append("LastModifiedDate: " + getLastModifiedDate() + ",");
        }
        if (getName() != null) {
            sb.append("Name: " + getName() + ",");
        }
        if (getSegmentGroups() != null) {
            sb.append("SegmentGroups: " + getSegmentGroups() + ",");
        }
        if (getSegmentType() != null) {
            sb.append("SegmentType: " + getSegmentType() + ",");
        }
        if (getVersion() != null) {
            sb.append("Version: " + getVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((((((getApplicationId() == null ? 0 : getApplicationId().hashCode()) + 31) * 31) + (getCreationDate() == null ? 0 : getCreationDate().hashCode())) * 31) + (getDimensions() == null ? 0 : getDimensions().hashCode())) * 31) + (getId() == null ? 0 : getId().hashCode())) * 31) + (getImportDefinition() == null ? 0 : getImportDefinition().hashCode())) * 31) + (getLastModifiedDate() == null ? 0 : getLastModifiedDate().hashCode())) * 31) + (getName() == null ? 0 : getName().hashCode())) * 31) + (getSegmentGroups() == null ? 0 : getSegmentGroups().hashCode())) * 31) + (getSegmentType() == null ? 0 : getSegmentType().hashCode())) * 31) + (getVersion() != null ? getVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentResponse)) {
            return false;
        }
        SegmentResponse segmentResponse = (SegmentResponse) obj;
        if ((segmentResponse.getApplicationId() == null) ^ (getApplicationId() == null)) {
            return false;
        }
        if (segmentResponse.getApplicationId() != null && !segmentResponse.getApplicationId().equals(getApplicationId())) {
            return false;
        }
        if ((segmentResponse.getCreationDate() == null) ^ (getCreationDate() == null)) {
            return false;
        }
        if (segmentResponse.getCreationDate() != null && !segmentResponse.getCreationDate().equals(getCreationDate())) {
            return false;
        }
        if ((segmentResponse.getDimensions() == null) ^ (getDimensions() == null)) {
            return false;
        }
        if (segmentResponse.getDimensions() != null && !segmentResponse.getDimensions().equals(getDimensions())) {
            return false;
        }
        if ((segmentResponse.getId() == null) ^ (getId() == null)) {
            return false;
        }
        if (segmentResponse.getId() != null && !segmentResponse.getId().equals(getId())) {
            return false;
        }
        if ((segmentResponse.getImportDefinition() == null) ^ (getImportDefinition() == null)) {
            return false;
        }
        if (segmentResponse.getImportDefinition() != null && !segmentResponse.getImportDefinition().equals(getImportDefinition())) {
            return false;
        }
        if ((segmentResponse.getLastModifiedDate() == null) ^ (getLastModifiedDate() == null)) {
            return false;
        }
        if (segmentResponse.getLastModifiedDate() != null && !segmentResponse.getLastModifiedDate().equals(getLastModifiedDate())) {
            return false;
        }
        if ((segmentResponse.getName() == null) ^ (getName() == null)) {
            return false;
        }
        if (segmentResponse.getName() != null && !segmentResponse.getName().equals(getName())) {
            return false;
        }
        if ((segmentResponse.getSegmentGroups() == null) ^ (getSegmentGroups() == null)) {
            return false;
        }
        if (segmentResponse.getSegmentGroups() != null && !segmentResponse.getSegmentGroups().equals(getSegmentGroups())) {
            return false;
        }
        if ((segmentResponse.getSegmentType() == null) ^ (getSegmentType() == null)) {
            return false;
        }
        if (segmentResponse.getSegmentType() != null && !segmentResponse.getSegmentType().equals(getSegmentType())) {
            return false;
        }
        if ((segmentResponse.getVersion() == null) ^ (getVersion() == null)) {
            return false;
        }
        return segmentResponse.getVersion() == null || segmentResponse.getVersion().equals(getVersion());
    }
}
