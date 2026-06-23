package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentBehaviors;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentBehaviorsJsonUnmarshaller implements Unmarshaller<SegmentBehaviors, JsonUnmarshallerContext> {
    private static SegmentBehaviorsJsonUnmarshaller instance;

    SegmentBehaviorsJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentBehaviors unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentBehaviors segmentBehaviors = new SegmentBehaviors();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Recency")) {
                segmentBehaviors.setRecency(RecencyDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentBehaviors;
    }

    public static SegmentBehaviorsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentBehaviorsJsonUnmarshaller();
        }
        return instance;
    }
}
