package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ImportJobResource implements Serializable {
    private Boolean defineSegment;
    private String externalId;
    private String format;
    private Boolean registerEndpoints;
    private String roleArn;
    private String s3Url;
    private String segmentId;
    private String segmentName;

    public Boolean isDefineSegment() {
        return this.defineSegment;
    }

    public Boolean getDefineSegment() {
        return this.defineSegment;
    }

    public void setDefineSegment(Boolean bool) {
        this.defineSegment = bool;
    }

    public ImportJobResource withDefineSegment(Boolean bool) {
        this.defineSegment = bool;
        return this;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String str) {
        this.externalId = str;
    }

    public ImportJobResource withExternalId(String str) {
        this.externalId = str;
        return this;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public ImportJobResource withFormat(String str) {
        this.format = str;
        return this;
    }

    public void setFormat(Format format) {
        this.format = format.toString();
    }

    public ImportJobResource withFormat(Format format) {
        this.format = format.toString();
        return this;
    }

    public Boolean isRegisterEndpoints() {
        return this.registerEndpoints;
    }

    public Boolean getRegisterEndpoints() {
        return this.registerEndpoints;
    }

    public void setRegisterEndpoints(Boolean bool) {
        this.registerEndpoints = bool;
    }

    public ImportJobResource withRegisterEndpoints(Boolean bool) {
        this.registerEndpoints = bool;
        return this;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public ImportJobResource withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public String getS3Url() {
        return this.s3Url;
    }

    public void setS3Url(String str) {
        this.s3Url = str;
    }

    public ImportJobResource withS3Url(String str) {
        this.s3Url = str;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public ImportJobResource withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public String getSegmentName() {
        return this.segmentName;
    }

    public void setSegmentName(String str) {
        this.segmentName = str;
    }

    public ImportJobResource withSegmentName(String str) {
        this.segmentName = str;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getDefineSegment() != null) {
            sb.append("DefineSegment: " + getDefineSegment() + ",");
        }
        if (getExternalId() != null) {
            sb.append("ExternalId: " + getExternalId() + ",");
        }
        if (getFormat() != null) {
            sb.append("Format: " + getFormat() + ",");
        }
        if (getRegisterEndpoints() != null) {
            sb.append("RegisterEndpoints: " + getRegisterEndpoints() + ",");
        }
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn() + ",");
        }
        if (getS3Url() != null) {
            sb.append("S3Url: " + getS3Url() + ",");
        }
        if (getSegmentId() != null) {
            sb.append("SegmentId: " + getSegmentId() + ",");
        }
        if (getSegmentName() != null) {
            sb.append("SegmentName: " + getSegmentName());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((((((getDefineSegment() == null ? 0 : getDefineSegment().hashCode()) + 31) * 31) + (getExternalId() == null ? 0 : getExternalId().hashCode())) * 31) + (getFormat() == null ? 0 : getFormat().hashCode())) * 31) + (getRegisterEndpoints() == null ? 0 : getRegisterEndpoints().hashCode())) * 31) + (getRoleArn() == null ? 0 : getRoleArn().hashCode())) * 31) + (getS3Url() == null ? 0 : getS3Url().hashCode())) * 31) + (getSegmentId() == null ? 0 : getSegmentId().hashCode())) * 31) + (getSegmentName() != null ? getSegmentName().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ImportJobResource)) {
            return false;
        }
        ImportJobResource importJobResource = (ImportJobResource) obj;
        if ((importJobResource.getDefineSegment() == null) ^ (getDefineSegment() == null)) {
            return false;
        }
        if (importJobResource.getDefineSegment() != null && !importJobResource.getDefineSegment().equals(getDefineSegment())) {
            return false;
        }
        if ((importJobResource.getExternalId() == null) ^ (getExternalId() == null)) {
            return false;
        }
        if (importJobResource.getExternalId() != null && !importJobResource.getExternalId().equals(getExternalId())) {
            return false;
        }
        if ((importJobResource.getFormat() == null) ^ (getFormat() == null)) {
            return false;
        }
        if (importJobResource.getFormat() != null && !importJobResource.getFormat().equals(getFormat())) {
            return false;
        }
        if ((importJobResource.getRegisterEndpoints() == null) ^ (getRegisterEndpoints() == null)) {
            return false;
        }
        if (importJobResource.getRegisterEndpoints() != null && !importJobResource.getRegisterEndpoints().equals(getRegisterEndpoints())) {
            return false;
        }
        if ((importJobResource.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (importJobResource.getRoleArn() != null && !importJobResource.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if ((importJobResource.getS3Url() == null) ^ (getS3Url() == null)) {
            return false;
        }
        if (importJobResource.getS3Url() != null && !importJobResource.getS3Url().equals(getS3Url())) {
            return false;
        }
        if ((importJobResource.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (importJobResource.getSegmentId() != null && !importJobResource.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((importJobResource.getSegmentName() == null) ^ (getSegmentName() == null)) {
            return false;
        }
        return importJobResource.getSegmentName() == null || importJobResource.getSegmentName().equals(getSegmentName());
    }
}
