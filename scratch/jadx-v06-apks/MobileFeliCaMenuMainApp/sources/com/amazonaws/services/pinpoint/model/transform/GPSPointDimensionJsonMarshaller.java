package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GPSCoordinates;
import com.amazonaws.services.pinpoint.model.GPSPointDimension;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class GPSPointDimensionJsonMarshaller {
    private static GPSPointDimensionJsonMarshaller instance;

    GPSPointDimensionJsonMarshaller() {
    }

    public void marshall(GPSPointDimension gPSPointDimension, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (gPSPointDimension.getCoordinates() != null) {
            GPSCoordinates coordinates = gPSPointDimension.getCoordinates();
            awsJsonWriter.name("Coordinates");
            GPSCoordinatesJsonMarshaller.getInstance().marshall(coordinates, awsJsonWriter);
        }
        if (gPSPointDimension.getRangeInKilometers() != null) {
            Double rangeInKilometers = gPSPointDimension.getRangeInKilometers();
            awsJsonWriter.name("RangeInKilometers");
            awsJsonWriter.value(rangeInKilometers);
        }
        awsJsonWriter.endObject();
    }

    public static GPSPointDimensionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new GPSPointDimensionJsonMarshaller();
        }
        return instance;
    }
}
