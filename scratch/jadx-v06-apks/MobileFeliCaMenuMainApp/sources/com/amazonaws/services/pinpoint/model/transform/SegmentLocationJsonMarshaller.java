package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GPSPointDimension;
import com.amazonaws.services.pinpoint.model.SegmentLocation;
import com.amazonaws.services.pinpoint.model.SetDimension;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class SegmentLocationJsonMarshaller {
    private static SegmentLocationJsonMarshaller instance;

    SegmentLocationJsonMarshaller() {
    }

    public void marshall(SegmentLocation segmentLocation, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (segmentLocation.getCountry() != null) {
            SetDimension country = segmentLocation.getCountry();
            awsJsonWriter.name("Country");
            SetDimensionJsonMarshaller.getInstance().marshall(country, awsJsonWriter);
        }
        if (segmentLocation.getGPSPoint() != null) {
            GPSPointDimension gPSPoint = segmentLocation.getGPSPoint();
            awsJsonWriter.name("GPSPoint");
            GPSPointDimensionJsonMarshaller.getInstance().marshall(gPSPoint, awsJsonWriter);
        }
        awsJsonWriter.endObject();
    }

    public static SegmentLocationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new SegmentLocationJsonMarshaller();
        }
        return instance;
    }
}
