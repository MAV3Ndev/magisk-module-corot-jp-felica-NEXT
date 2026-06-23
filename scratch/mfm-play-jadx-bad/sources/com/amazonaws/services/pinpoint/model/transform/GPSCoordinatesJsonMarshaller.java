package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GPSCoordinates;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class GPSCoordinatesJsonMarshaller {
    private static GPSCoordinatesJsonMarshaller instance;

    GPSCoordinatesJsonMarshaller() {
    }

    public void marshall(GPSCoordinates gPSCoordinates, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (gPSCoordinates.getLatitude() != null) {
            Double latitude = gPSCoordinates.getLatitude();
            awsJsonWriter.name("Latitude");
            awsJsonWriter.value(latitude);
        }
        if (gPSCoordinates.getLongitude() != null) {
            Double longitude = gPSCoordinates.getLongitude();
            awsJsonWriter.name("Longitude");
            awsJsonWriter.value(longitude);
        }
        awsJsonWriter.endObject();
    }

    public static GPSCoordinatesJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GPSCoordinatesJsonMarshaller();
        }
        return instance;
    }
}
