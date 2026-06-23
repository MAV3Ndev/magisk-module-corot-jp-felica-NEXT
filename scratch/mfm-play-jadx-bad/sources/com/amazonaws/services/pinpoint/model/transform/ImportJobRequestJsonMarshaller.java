package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ImportJobRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ImportJobRequestJsonMarshaller {
    private static ImportJobRequestJsonMarshaller instance;

    ImportJobRequestJsonMarshaller() {
    }

    public void marshall(ImportJobRequest importJobRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (importJobRequest.getDefineSegment() != null) {
            Boolean defineSegment = importJobRequest.getDefineSegment();
            awsJsonWriter.name("DefineSegment");
            awsJsonWriter.value(defineSegment.booleanValue());
        }
        if (importJobRequest.getExternalId() != null) {
            String externalId = importJobRequest.getExternalId();
            awsJsonWriter.name("ExternalId");
            awsJsonWriter.value(externalId);
        }
        if (importJobRequest.getFormat() != null) {
            String format = importJobRequest.getFormat();
            awsJsonWriter.name("Format");
            awsJsonWriter.value(format);
        }
        if (importJobRequest.getRegisterEndpoints() != null) {
            Boolean registerEndpoints = importJobRequest.getRegisterEndpoints();
            awsJsonWriter.name("RegisterEndpoints");
            awsJsonWriter.value(registerEndpoints.booleanValue());
        }
        if (importJobRequest.getRoleArn() != null) {
            String roleArn = importJobRequest.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        if (importJobRequest.getS3Url() != null) {
            String s3Url = importJobRequest.getS3Url();
            awsJsonWriter.name("S3Url");
            awsJsonWriter.value(s3Url);
        }
        if (importJobRequest.getSegmentId() != null) {
            String segmentId = importJobRequest.getSegmentId();
            awsJsonWriter.name("SegmentId");
            awsJsonWriter.value(segmentId);
        }
        if (importJobRequest.getSegmentName() != null) {
            String segmentName = importJobRequest.getSegmentName();
            awsJsonWriter.name("SegmentName");
            awsJsonWriter.value(segmentName);
        }
        awsJsonWriter.endObject();
    }

    public static ImportJobRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ImportJobRequestJsonMarshaller();
        }
        return instance;
    }
}
