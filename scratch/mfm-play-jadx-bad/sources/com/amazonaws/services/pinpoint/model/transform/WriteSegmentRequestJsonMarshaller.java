package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentDimensions;
import com.amazonaws.services.pinpoint.model.SegmentGroupList;
import com.amazonaws.services.pinpoint.model.WriteSegmentRequest;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class WriteSegmentRequestJsonMarshaller {
    private static WriteSegmentRequestJsonMarshaller instance;

    WriteSegmentRequestJsonMarshaller() {
    }

    public void marshall(WriteSegmentRequest writeSegmentRequest, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (writeSegmentRequest.getDimensions() != null) {
            SegmentDimensions dimensions = writeSegmentRequest.getDimensions();
            awsJsonWriter.name("Dimensions");
            SegmentDimensionsJsonMarshaller.getInstance().marshall(dimensions, awsJsonWriter);
        }
        if (writeSegmentRequest.getName() != null) {
            String name = writeSegmentRequest.getName();
            awsJsonWriter.name("Name");
            awsJsonWriter.value(name);
        }
        if (writeSegmentRequest.getSegmentGroups() != null) {
            SegmentGroupList segmentGroups = writeSegmentRequest.getSegmentGroups();
            awsJsonWriter.name("SegmentGroups");
            SegmentGroupListJsonMarshaller.getInstance().marshall(segmentGroups, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static WriteSegmentRequestJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new WriteSegmentRequestJsonMarshaller();
        }
        return instance;
    }
}
