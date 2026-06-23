package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ExportJobRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ExportJobRequestJsonMarshaller {
    private static ExportJobRequestJsonMarshaller instance;

    ExportJobRequestJsonMarshaller() {
    }

    public void marshall(ExportJobRequest exportJobRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (exportJobRequest.getRoleArn() != null) {
            String roleArn = exportJobRequest.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        if (exportJobRequest.getS3UrlPrefix() != null) {
            String s3UrlPrefix = exportJobRequest.getS3UrlPrefix();
            awsJsonWriter.name("S3UrlPrefix");
            awsJsonWriter.value(s3UrlPrefix);
        }
        if (exportJobRequest.getSegmentId() != null) {
            String segmentId = exportJobRequest.getSegmentId();
            awsJsonWriter.name("SegmentId");
            awsJsonWriter.value(segmentId);
        }
        if (exportJobRequest.getSegmentVersion() != null) {
            Integer segmentVersion = exportJobRequest.getSegmentVersion();
            awsJsonWriter.name("SegmentVersion");
            awsJsonWriter.value(segmentVersion);
        }
        awsJsonWriter.endObject();
    }

    public static ExportJobRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ExportJobRequestJsonMarshaller();
        }
        return instance;
    }
}
