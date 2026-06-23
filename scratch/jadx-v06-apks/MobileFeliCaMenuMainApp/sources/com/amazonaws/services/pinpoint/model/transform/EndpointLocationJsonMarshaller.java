package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointLocation;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EndpointLocationJsonMarshaller {
    private static EndpointLocationJsonMarshaller instance;

    EndpointLocationJsonMarshaller() {
    }

    public void marshall(EndpointLocation endpointLocation, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointLocation.getCity() != null) {
            String city = endpointLocation.getCity();
            awsJsonWriter.name("City");
            awsJsonWriter.value(city);
        }
        if (endpointLocation.getCountry() != null) {
            String country = endpointLocation.getCountry();
            awsJsonWriter.name("Country");
            awsJsonWriter.value(country);
        }
        if (endpointLocation.getLatitude() != null) {
            Double latitude = endpointLocation.getLatitude();
            awsJsonWriter.name("Latitude");
            awsJsonWriter.value(latitude);
        }
        if (endpointLocation.getLongitude() != null) {
            Double longitude = endpointLocation.getLongitude();
            awsJsonWriter.name("Longitude");
            awsJsonWriter.value(longitude);
        }
        if (endpointLocation.getPostalCode() != null) {
            String postalCode = endpointLocation.getPostalCode();
            awsJsonWriter.name("PostalCode");
            awsJsonWriter.value(postalCode);
        }
        if (endpointLocation.getRegion() != null) {
            String region = endpointLocation.getRegion();
            awsJsonWriter.name("Region");
            awsJsonWriter.value(region);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointLocationJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointLocationJsonMarshaller();
        }
        return instance;
    }
}
