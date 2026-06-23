package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.RecencyDimension;
import com.amazonaws.services.pinpoint.model.SegmentBehaviors;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SegmentBehaviorsJsonMarshaller {
    private static SegmentBehaviorsJsonMarshaller instance;

    SegmentBehaviorsJsonMarshaller() {
    }

    public void marshall(SegmentBehaviors segmentBehaviors, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentBehaviors.getRecency() != null) {
            RecencyDimension recency = segmentBehaviors.getRecency();
            awsJsonWriter.name("Recency");
            RecencyDimensionJsonMarshaller.getInstance().marshall(recency, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentBehaviorsJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentBehaviorsJsonMarshaller();
        }
        return instance;
    }
}
