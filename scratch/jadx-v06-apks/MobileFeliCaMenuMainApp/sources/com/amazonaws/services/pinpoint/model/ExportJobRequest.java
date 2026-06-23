package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class ExportJobRequest implements Serializable {
    private String roleArn;
    private String s3UrlPrefix;
    private String segmentId;
    private Integer segmentVersion;

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public ExportJobRequest withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public String getS3UrlPrefix() {
        return this.s3UrlPrefix;
    }

    public void setS3UrlPrefix(String str) {
        this.s3UrlPrefix = str;
    }

    public ExportJobRequest withS3UrlPrefix(String str) {
        this.s3UrlPrefix = str;
        return this;
    }

    public String getSegmentId() {
        return this.segmentId;
    }

    public void setSegmentId(String str) {
        this.segmentId = str;
    }

    public ExportJobRequest withSegmentId(String str) {
        this.segmentId = str;
        return this;
    }

    public Integer getSegmentVersion() {
        return this.segmentVersion;
    }

    public void setSegmentVersion(Integer num) {
        this.segmentVersion = num;
    }

    public ExportJobRequest withSegmentVersion(Integer num) {
        this.segmentVersion = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn() + ",");
        }
        if (getS3UrlPrefix() != null) {
            sb.append("S3UrlPrefix: " + getS3UrlPrefix() + ",");
        }
        if (getSegmentId() != null) {
            sb.append("SegmentId: " + getSegmentId() + ",");
        }
        if (getSegmentVersion() != null) {
            sb.append("SegmentVersion: " + getSegmentVersion());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((getRoleArn() == null ? 0 : getRoleArn().hashCode()) + 31) * 31) + (getS3UrlPrefix() == null ? 0 : getS3UrlPrefix().hashCode())) * 31) + (getSegmentId() == null ? 0 : getSegmentId().hashCode())) * 31) + (getSegmentVersion() != null ? getSegmentVersion().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ExportJobRequest)) {
            return false;
        }
        ExportJobRequest exportJobRequest = (ExportJobRequest) obj;
        if ((exportJobRequest.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (exportJobRequest.getRoleArn() != null && !exportJobRequest.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if ((exportJobRequest.getS3UrlPrefix() == null) ^ (getS3UrlPrefix() == null)) {
            return false;
        }
        if (exportJobRequest.getS3UrlPrefix() != null && !exportJobRequest.getS3UrlPrefix().equals(getS3UrlPrefix())) {
            return false;
        }
        if ((exportJobRequest.getSegmentId() == null) ^ (getSegmentId() == null)) {
            return false;
        }
        if (exportJobRequest.getSegmentId() != null && !exportJobRequest.getSegmentId().equals(getSegmentId())) {
            return false;
        }
        if ((exportJobRequest.getSegmentVersion() == null) ^ (getSegmentVersion() == null)) {
            return false;
        }
        return exportJobRequest.getSegmentVersion() == null || exportJobRequest.getSegmentVersion().equals(getSegmentVersion());
    }
}
