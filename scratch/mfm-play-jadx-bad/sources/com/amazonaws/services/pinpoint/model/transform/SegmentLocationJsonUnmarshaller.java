package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.SegmentLocation;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class SegmentLocationJsonUnmarshaller implements Unmarshaller<SegmentLocation, JsonUnmarshallerContext> {
    private static SegmentLocationJsonUnmarshaller instance;

    SegmentLocationJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public SegmentLocation unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        SegmentLocation segmentLocation = new SegmentLocation();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Country")) {
                segmentLocation.setCountry(SetDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("GPSPoint")) {
                segmentLocation.setGPSPoint(GPSPointDimensionJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return segmentLocation;
    }

    public static SegmentLocationJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentLocationJsonUnmarshaller();
        }
        return instance;
    }
}
