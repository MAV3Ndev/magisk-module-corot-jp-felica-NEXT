package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GPSCoordinates;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class GPSCoordinatesJsonUnmarshaller implements Unmarshaller<GPSCoordinates, JsonUnmarshallerContext> {
    private static GPSCoordinatesJsonUnmarshaller instance;

    GPSCoordinatesJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GPSCoordinates unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        GPSCoordinates gPSCoordinates = new GPSCoordinates();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Latitude")) {
                gPSCoordinates.setLatitude(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Longitude")) {
                gPSCoordinates.setLongitude(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return gPSCoordinates;
    }

    public static GPSCoordinatesJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GPSCoordinatesJsonUnmarshaller();
        }
        return instance;
    }
}
