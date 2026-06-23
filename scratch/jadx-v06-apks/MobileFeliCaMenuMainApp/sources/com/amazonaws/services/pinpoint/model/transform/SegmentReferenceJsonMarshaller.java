package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.auth.policy.internal.JsonDocumentFields;
import com.amazonaws.services.pinpoint.model.SegmentReference;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SegmentReferenceJsonMarshaller {
    private static SegmentReferenceJsonMarshaller instance;

    SegmentReferenceJsonMarshaller() {
    }

    public void marshall(SegmentReference segmentReference, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentReference.getId() != null) {
            String id = segmentReference.getId();
            awsJsonWriter.name(JsonDocumentFields.POLICY_ID);
            awsJsonWriter.value(id);
        }
        if (segmentReference.getVersion() != null) {
            Integer version = segmentReference.getVersion();
            awsJsonWriter.name(JsonDocumentFields.VERSION);
            awsJsonWriter.value(version);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentReferenceJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentReferenceJsonMarshaller();
        }
        return instance;
    }
}
