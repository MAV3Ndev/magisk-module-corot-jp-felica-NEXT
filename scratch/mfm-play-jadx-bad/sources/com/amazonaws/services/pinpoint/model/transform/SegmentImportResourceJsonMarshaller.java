package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentImportResource;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class SegmentImportResourceJsonMarshaller {
    private static SegmentImportResourceJsonMarshaller instance;

    SegmentImportResourceJsonMarshaller() {
    }

    public void marshall(SegmentImportResource segmentImportResource, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentImportResource.getChannelCounts() != null) {
            Map<String, Integer> channelCounts = segmentImportResource.getChannelCounts();
            awsJsonWriter.name("ChannelCounts");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, Integer> entry : channelCounts.entrySet()) {
                Integer value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    awsJsonWriter.value(value);
                }
            }
            awsJsonWriter.endObject();
        }
        if (segmentImportResource.getExternalId() != null) {
            String externalId = segmentImportResource.getExternalId();
            awsJsonWriter.name("ExternalId");
            awsJsonWriter.value(externalId);
        }
        if (segmentImportResource.getFormat() != null) {
            String format = segmentImportResource.getFormat();
            awsJsonWriter.name("Format");
            awsJsonWriter.value(format);
        }
        if (segmentImportResource.getRoleArn() != null) {
            String roleArn = segmentImportResource.getRoleArn();
            awsJsonWriter.name("RoleArn");
            awsJsonWriter.value(roleArn);
        }
        if (segmentImportResource.getS3Url() != null) {
            String s3Url = segmentImportResource.getS3Url();
            awsJsonWriter.name("S3Url");
            awsJsonWriter.value(s3Url);
        }
        if (segmentImportResource.getSize() != null) {
            Integer size = segmentImportResource.getSize();
            awsJsonWriter.name("Size");
            awsJsonWriter.value(size);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentImportResourceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentImportResourceJsonMarshaller();
        }
        return instance;
    }
}
