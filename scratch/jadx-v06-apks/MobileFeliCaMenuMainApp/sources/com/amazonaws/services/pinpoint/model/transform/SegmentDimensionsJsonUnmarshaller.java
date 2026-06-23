package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.http.HttpHeader;
import com.amazonaws.services.pinpoint.model.SegmentDimensions;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentDimensionsJsonUnmarshaller implements Unmarshaller<SegmentDimensions, JsonUnmarshallerContext> {
    private static SegmentDimensionsJsonUnmarshaller instance;

    SegmentDimensionsJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentDimensions unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentDimensions segmentDimensions = new SegmentDimensions();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Attributes")) {
                segmentDimensions.setAttributes(new MapUnmarshaller(AttributeDimensionJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Behavior")) {
                segmentDimensions.setBehavior(SegmentBehaviorsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Demographic")) {
                segmentDimensions.setDemographic(SegmentDemographicsJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals(HttpHeader.LOCATION)) {
                segmentDimensions.setLocation(SegmentLocationJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Metrics")) {
                segmentDimensions.setMetrics(new MapUnmarshaller(MetricDimensionJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("UserAttributes")) {
                segmentDimensions.setUserAttributes(new MapUnmarshaller(AttributeDimensionJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentDimensions;
    }

    public static SegmentDimensionsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentDimensionsJsonUnmarshaller();
        }
        return instance;
    }
}
