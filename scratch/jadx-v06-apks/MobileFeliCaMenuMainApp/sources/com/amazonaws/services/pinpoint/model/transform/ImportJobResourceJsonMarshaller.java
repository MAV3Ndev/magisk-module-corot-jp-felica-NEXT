package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ImportJobResource;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class ImportJobResourceJsonMarshaller {
    private static ImportJobResourceJsonMarshaller instance;

    ImportJobResourceJsonMarshaller() {
    }

    public void marshall(ImportJobResource importJobResource, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (importJobResource.getDefineSegment() != null) {
            Boolean defineSegment = importJobResource.getDefineSegment();
            awsJsonWriter.name("DefineSegment");
            awsJsonWriter.value(defineSegment.booleanValue());
        }
        if (importJobResource.getExternalId() != null) {
            String externalId = importJobResource.getExternalId();
            awsJsonWriter.name("ExternalId");
            awsJsonWriter.value(externalId);
        }
        if (importJobResource.getFormat() != null) {
            String format = importJobResource.getFormat();
            awsJsonWriter.name("Format");
            awsJsonWriter.value(format);
        }
        if (importJobResource.getRegisterEndpoints() != null) {
            Boolean registerEndpoints = importJobResource.getRegisterEndpoints();
            awsJsonWriter.name("RegisterEndpoints");
            awsJsonWriter.value(registerEndpoints.booleanValue());
        }
        if (importJobResource.getRoleArn() != null) {
            String roleArn = importJobResource.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        if (importJobResource.getS3Url() != null) {
            String s3Url = importJobResource.getS3Url();
            awsJsonWriter.name("S3Url");
            awsJsonWriter.value(s3Url);
        }
        if (importJobResource.getSegmentId() != null) {
            String segmentId = importJobResource.getSegmentId();
            awsJsonWriter.name("SegmentId");
            awsJsonWriter.value(segmentId);
        }
        if (importJobResource.getSegmentName() != null) {
            String segmentName = importJobResource.getSegmentName();
            awsJsonWriter.name("SegmentName");
            awsJsonWriter.value(segmentName);
        }
        awsJsonWriter.endObject();
    }

    public static ImportJobResourceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ImportJobResourceJsonMarshaller();
        }
        return instance;
    }
}
