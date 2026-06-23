package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GPSPointDimension;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class GPSPointDimensionJsonUnmarshaller implements Unmarshaller<GPSPointDimension, JsonUnmarshallerContext> {
    private static GPSPointDimensionJsonUnmarshaller instance;

    GPSPointDimensionJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GPSPointDimension unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        GPSPointDimension gPSPointDimension = new GPSPointDimension();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("Coordinates")) {
                gPSPointDimension.setCoordinates(GPSCoordinatesJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("RangeInKilometers")) {
                gPSPointDimension.setRangeInKilometers(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return gPSPointDimension;
    }

    public static GPSPointDimensionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GPSPointDimensionJsonUnmarshaller();
        }
        return instance;
    }
}
