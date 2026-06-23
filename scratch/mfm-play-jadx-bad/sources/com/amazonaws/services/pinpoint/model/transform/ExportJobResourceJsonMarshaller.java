package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ExportJobResource;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ExportJobResourceJsonMarshaller {
    private static ExportJobResourceJsonMarshaller instance;

    ExportJobResourceJsonMarshaller() {
    }

    public void marshall(ExportJobResource exportJobResource, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (exportJobResource.getRoleArn() != null) {
            String roleArn = exportJobResource.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        if (exportJobResource.getS3UrlPrefix() != null) {
            String s3UrlPrefix = exportJobResource.getS3UrlPrefix();
            awsJsonWriter.name("S3UrlPrefix");
            awsJsonWriter.value(s3UrlPrefix);
        }
        if (exportJobResource.getSegmentId() != null) {
            String segmentId = exportJobResource.getSegmentId();
            awsJsonWriter.name("SegmentId");
            awsJsonWriter.value(segmentId);
        }
        if (exportJobResource.getSegmentVersion() != null) {
            Integer segmentVersion = exportJobResource.getSegmentVersion();
            awsJsonWriter.name("SegmentVersion");
            awsJsonWriter.value(segmentVersion);
        }
        awsJsonWriter.endObject();
    }

    public static ExportJobResourceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ExportJobResourceJsonMarshaller();
        }
        return instance;
    }
}
