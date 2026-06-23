package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointItemResponse;
import com.amazonaws.util.json.AwsJsonWriter;

/* JADX INFO: loaded from: classes.dex */
class EndpointItemResponseJsonMarshaller {
    private static EndpointItemResponseJsonMarshaller instance;

    EndpointItemResponseJsonMarshaller() {
    }

    public void marshall(EndpointItemResponse endpointItemResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointItemResponse.getMessage() != null) {
            String message = endpointItemResponse.getMessage();
            awsJsonWriter.name("Message");
            awsJsonWriter.value(message);
        }
        if (endpointItemResponse.getStatusCode() != null) {
            Integer statusCode = endpointItemResponse.getStatusCode();
            awsJsonWriter.name("StatusCode");
            awsJsonWriter.value(statusCode);
        }
        awsJsonWriter.endObject();
    }

    public static EndpointItemResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointItemResponseJsonMarshaller();
        }
        return instance;
    }
}
