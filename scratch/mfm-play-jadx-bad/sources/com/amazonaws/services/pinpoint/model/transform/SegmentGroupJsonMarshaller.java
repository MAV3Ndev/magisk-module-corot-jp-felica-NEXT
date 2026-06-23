package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentDimensions;
import com.amazonaws.services.pinpoint.model.SegmentGroup;
import com.amazonaws.services.pinpoint.model.SegmentReference;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class SegmentGroupJsonMarshaller {
    private static SegmentGroupJsonMarshaller instance;

    SegmentGroupJsonMarshaller() {
    }

    public void marshall(SegmentGroup segmentGroup, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentGroup.getDimensions() != null) {
            List<SegmentDimensions> dimensions = segmentGroup.getDimensions();
            awsJsonWriter.name("Dimensions");
            awsJsonWriter.beginArray();
            for (SegmentDimensions segmentDimensions : dimensions) {
                if (segmentDimensions != null) {
                    SegmentDimensionsJsonMarshaller.getInstance().marshall(segmentDimensions, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (segmentGroup.getSourceSegments() != null) {
            List<SegmentReference> sourceSegments = segmentGroup.getSourceSegments();
            awsJsonWriter.name("SourceSegments");
            awsJsonWriter.beginArray();
            for (SegmentReference segmentReference : sourceSegments) {
                if (segmentReference != null) {
                    SegmentReferenceJsonMarshaller.getInstance().marshall(segmentReference, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        if (segmentGroup.getSourceType() != null) {
            String sourceType = segmentGroup.getSourceType();
            awsJsonWriter.name("SourceType");
            awsJsonWriter.value(sourceType);
        }
        if (segmentGroup.getType() != null) {
            String type = segmentGroup.getType();
            awsJsonWriter.name("Type");
            awsJsonWriter.value(type);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentGroupJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentGroupJsonMarshaller();
        }
        return instance;
    }
}
