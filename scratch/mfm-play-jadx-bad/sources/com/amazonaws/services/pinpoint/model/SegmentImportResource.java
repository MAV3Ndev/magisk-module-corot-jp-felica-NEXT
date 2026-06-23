package com.amazonaws.services.pinpoint.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SegmentImportResource implements Serializable {
    private Map<String, Integer> channelCounts;
    private String externalId;
    private String format;
    private String roleArn;
    private String s3Url;
    private Integer size;

    public Map<String, Integer> getChannelCounts() {
        return this.channelCounts;
    }

    public void setChannelCounts(Map<String, Integer> map) {
        this.channelCounts = map;
    }

    public SegmentImportResource withChannelCounts(Map<String, Integer> map) {
        this.channelCounts = map;
        return this;
    }

    public SegmentImportResource addChannelCountsEntry(String str, Integer num) {
        if (this.channelCounts == null) {
            this.channelCounts = new HashMap();
        }
        if (this.channelCounts.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.channelCounts.put(str, num);
        return this;
    }

    public SegmentImportResource clearChannelCountsEntries() {
        this.channelCounts = null;
        return this;
    }

    public String getExternalId() {
        return this.externalId;
    }

    public void setExternalId(String str) {
        this.externalId = str;
    }

    public SegmentImportResource withExternalId(String str) {
        this.externalId = str;
        return this;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public SegmentImportResource withFormat(String str) {
        this.format = str;
        return this;
    }

    public void setFormat(Format format) {
        this.format = format.toString();
    }

    public SegmentImportResource withFormat(Format format) {
        this.format = format.toString();
        return this;
    }

    public String getRoleArn() {
        return this.roleArn;
    }

    public void setRoleArn(String str) {
        this.roleArn = str;
    }

    public SegmentImportResource withRoleArn(String str) {
        this.roleArn = str;
        return this;
    }

    public String getS3Url() {
        return this.s3Url;
    }

    public void setS3Url(String str) {
        this.s3Url = str;
    }

    public SegmentImportResource withS3Url(String str) {
        this.s3Url = str;
        return this;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer num) {
        this.size = num;
    }

    public SegmentImportResource withSize(Integer num) {
        this.size = num;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if (getChannelCounts() != null) {
            sb.append("ChannelCounts: " + getChannelCounts() + ",");
        }
        if (getExternalId() != null) {
            sb.append("ExternalId: " + getExternalId() + ",");
        }
        if (getFormat() != null) {
            sb.append("Format: " + getFormat() + ",");
        }
        if (getRoleArn() != null) {
            sb.append("RoleArn: " + getRoleArn() + ",");
        }
        if (getS3Url() != null) {
            sb.append("S3Url: " + getS3Url() + ",");
        }
        if (getSize() != null) {
            sb.append("Size: " + getSize());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((((getChannelCounts() == null ? 0 : getChannelCounts().hashCode()) + 31) * 31) + (getExternalId() == null ? 0 : getExternalId().hashCode())) * 31) + (getFormat() == null ? 0 : getFormat().hashCode())) * 31) + (getRoleArn() == null ? 0 : getRoleArn().hashCode())) * 31) + (getS3Url() == null ? 0 : getS3Url().hashCode())) * 31) + (getSize() != null ? getSize().hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SegmentImportResource)) {
            return false;
        }
        SegmentImportResource segmentImportResource = (SegmentImportResource) obj;
        if ((segmentImportResource.getChannelCounts() == null) ^ (getChannelCounts() == null)) {
            return false;
        }
        if (segmentImportResource.getChannelCounts() != null && !segmentImportResource.getChannelCounts().equals(getChannelCounts())) {
            return false;
        }
        if ((segmentImportResource.getExternalId() == null) ^ (getExternalId() == null)) {
            return false;
        }
        if (segmentImportResource.getExternalId() != null && !segmentImportResource.getExternalId().equals(getExternalId())) {
            return false;
        }
        if ((segmentImportResource.getFormat() == null) ^ (getFormat() == null)) {
            return false;
        }
        if (segmentImportResource.getFormat() != null && !segmentImportResource.getFormat().equals(getFormat())) {
            return false;
        }
        if ((segmentImportResource.getRoleArn() == null) ^ (getRoleArn() == null)) {
            return false;
        }
        if (segmentImportResource.getRoleArn() != null && !segmentImportResource.getRoleArn().equals(getRoleArn())) {
            return false;
        }
        if ((segmentImportResource.getS3Url() == null) ^ (getS3Url() == null)) {
            return false;
        }
        if (segmentImportResource.getS3Url() != null && !segmentImportResource.getS3Url().equals(getS3Url())) {
            return false;
        }
        if ((segmentImportResource.getSize() == null) ^ (getSize() == null)) {
            return false;
        }
        return segmentImportResource.getSize() == null || segmentImportResource.getSize().equals(getSize());
    }
}
