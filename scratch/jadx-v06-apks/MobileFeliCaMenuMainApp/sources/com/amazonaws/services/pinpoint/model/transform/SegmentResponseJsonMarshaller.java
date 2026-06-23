package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.SegmentDimensions;
import com.amazonaws.services.pinpoint.model.SegmentGroupList;
import com.amazonaws.services.pinpoint.model.SegmentImportResource;
import com.amazonaws.services.pinpoint.model.SegmentResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SegmentResponseJsonMarshaller {
    private static SegmentResponseJsonMarshaller instance;

    SegmentResponseJsonMarshaller() {
    }

    public void marshall(SegmentResponse segmentResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentResponse.getApplicationId() != null) {
            String applicationId = segmentResponse.getApplicationId();
            awsJsonWriter.name("ApplicationId");
            awsJsonWriter.value(applicationId);
        }
        if (segmentResponse.getCreationDate() != null) {
            String creationDate = segmentResponse.getCreationDate();
            awsJsonWriter.name("CreationDate");
            awsJsonWriter.value(creationDate);
        }
        if (segmentResponse.getDimensions() != null) {
            SegmentDimensions dimensions = segmentResponse.getDimensions();
            awsJsonWriter.name("Dimensions");
            SegmentDimensionsJsonMarshaller.getInstance().marshall(dimensions, awsJsonWriter);
        }
        if (segmentResponse.getId() != null) {
            String id = segmentResponse.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (segmentResponse.getImportDefinition() != null) {
            SegmentImportResource importDefinition = segmentResponse.getImportDefinition();
            awsJsonWriter.name("ImportDefinition");
            SegmentImportResourceJsonMarshaller.getInstance().marshall(importDefinition, awsJsonWriter);
        }
        if (segmentResponse.getLastModifiedDate() != null) {
            String lastModifiedDate = segmentResponse.getLastModifiedDate();
            awsJsonWriter.name("LastModifiedDate");
            awsJsonWriter.value(lastModifiedDate);
        }
        if (segmentResponse.getName() != null) {
            String name = segmentResponse.getName();
            awsJsonWriter.name("Name");
            awsJsonWriter.value(name);
        }
        if (segmentResponse.getSegmentGroups() != null) {
            SegmentGroupList segmentGroups = segmentResponse.getSegmentGroups();
            awsJsonWriter.name("SegmentGroups");
            SegmentGroupListJsonMarshaller.getInstance().marshall(segmentGroups, awsJsonWriter);
        }
        if (segmentResponse.getSegmentType() != null) {
            String segmentType = segmentResponse.getSegmentType();
            awsJsonWriter.name("SegmentType");
            awsJsonWriter.value(segmentType);
        }
        if (segmentResponse.getVersion() != null) {
            Integer version = segmentResponse.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentResponseJsonMarshaller();
        }
        return instance;
    }
}
