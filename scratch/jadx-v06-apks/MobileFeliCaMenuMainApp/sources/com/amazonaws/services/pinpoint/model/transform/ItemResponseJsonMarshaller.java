package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.EndpointItemResponse;
import com.amazonaws.services.pinpoint.model.EventItemResponse;
import com.amazonaws.services.pinpoint.model.ItemResponse;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class ItemResponseJsonMarshaller {
    private static ItemResponseJsonMarshaller instance;

    ItemResponseJsonMarshaller() {
    }

    public void marshall(ItemResponse itemResponse, AwsJsonWriter awsJsonWriter) throws Exception {
        awsJsonWriter.beginObject();
        if (itemResponse.getEndpointItemResponse() != null) {
            EndpointItemResponse endpointItemResponse = itemResponse.getEndpointItemResponse();
            awsJsonWriter.name("EndpointItemResponse");
            EndpointItemResponseJsonMarshaller.getInstance().marshall(endpointItemResponse, awsJsonWriter);
        }
        if (itemResponse.getEventsItemResponse() != null) {
            Map<String, EventItemResponse> eventsItemResponse = itemResponse.getEventsItemResponse();
            awsJsonWriter.name("EventsItemResponse");
            awsJsonWriter.beginObject();
            for (Map.Entry<String, EventItemResponse> entry : eventsItemResponse.entrySet()) {
                EventItemResponse value = entry.getValue();
                if (value != null) {
                    awsJsonWriter.name(entry.getKey());
                    EventItemResponseJsonMarshaller.getInstance().marshall(value, awsJsonWriter);
                }
            }
            awsJsonWriter.endObject();
        }
        awsJsonWriter.endObject();
    }

    public static ItemResponseJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ItemResponseJsonMarshaller();
        }
        return instance;
    }
}
