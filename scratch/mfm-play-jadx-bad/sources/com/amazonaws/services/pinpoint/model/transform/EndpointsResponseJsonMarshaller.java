package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointResponse;
import com.amazonaws.services.pinpoint.model.EndpointsResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class EndpointsResponseJsonMarshaller {
    private static EndpointsResponseJsonMarshaller instance;

    EndpointsResponseJsonMarshaller() {
    }

    public void marshall(EndpointsResponse endpointsResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (endpointsResponse.getItem() != null) {
            List<EndpointResponse> item = endpointsResponse.getItem();
            awsJsonWriter.name("Item");
            awsJsonWriter.beginArray();
            for (EndpointResponse endpointResponse : item) {
                if (endpointResponse != null) {
                    EndpointResponseJsonMarshaller.getInstance().marshall(endpointResponse, awsJsonWriter);
                }
            }
            awsJsonWriter.endArray();
        }
        awsJsonWriter.endObject();
    }

    public static EndpointsResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new EndpointsResponseJsonMarshaller();
        }
        return instance;
    }
}
