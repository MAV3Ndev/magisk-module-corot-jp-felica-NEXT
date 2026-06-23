package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ImportJobRequest implements Serializable {
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

    public ImportJobRequest withDefineSegment(Boolean bool) {
        this.defineSegment = bool;
        return this;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String str) {
        this.externalId = str;
    }

    public ImportJobRequest withExternalId(String str) {
        this.externalId = str;
        return this;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public ImportJobRequest withFormat(String str) {
        this.format = str;
        return this;
    }

    public void setFormat(Format format) {
        this.format = format.toString();
    }

    public ImportJobRequest withFormat(Format format) {
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

    public ImportJobRequest withRegisterEndpoints(Boolean bool) {
        this.registerEndpoints = bool;
        return this;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public ImportJobRequest withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public String getS3Url() {
        return this.s3Url;
    }

    public void setS3Url(String str) {
        this.s3Url = str;
    }

    public ImportJobRequest withS3Url(String str) {
        this.s3Url = str;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public ImportJobRequest withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public String getSegmentName() {
        return this.segmentName;
    }

    public void setSegmentName(String str) {
        this.segmentName = str;
    }

    public ImportJobRequest withSegmentName(String str) {
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
        if (obj == null || !(obj instanceof ImportJobRequest)) {
            return false;
        }
        ImportJobRequest importJobRequest = (ImportJobRequest) obj;
        if ((importJobRequest.getDefineSegment() == null) ^ (getDefineSegment() == null)) {
            return false;
        }
        if (importJobRequest.getDefineSegment() != null && !importJobRequest.getDefineSegment().equals(getDefineSegment())) {
            return false;
        }
        if ((importJobRequest.getExternalId() == null) ^ (getExternalId() == null)) {
            return false;
        }
        if (importJobRequest.getExternalId() != null && !importJobRequest.getExternalId().equals(getExternalId())) {
            return false;
        }
        if ((importJobRequest.getFormat() == null) ^ (getFormat() == null)) {
            return false;
        }
        if (importJobRequest.getFormat() != null && !importJobRequest.getFormat().equals(getFormat())) {
            return false;
        }
        if ((importJobRequest.getRegisterEndpoints() == null) ^ (getRegisterEndpoints() == null)) {
            return false;
        }
        if (importJobRequest.getRegisterEndpoints() != null && !importJobRequest.getRegisterEndpoints().equals(getRegisterEndpoints())) {
            return false;
        }
        if ((importJobRequest.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (importJobRequest.getRoleArn() != null && !importJobRequest.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if ((importJobRequest.getS3Url() == null) ^ (getS3Url() == null)) {
            return false;
        }
        if (importJobRequest.getS3Url() != null && !importJobRequest.getS3Url().equals(getS3Url())) {
            return false;
        }
        if ((importJobRequest.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (importJobRequest.getSegmentId() != null && !importJobRequest.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((importJobRequest.getSegmentName() == null) ^ (getSegmentName() == null)) {
            return false;
        }
        return importJobRequest.getSegmentName() == null || importJobRequest.getSegmentName().equals(getSegmentName());
    }
}
