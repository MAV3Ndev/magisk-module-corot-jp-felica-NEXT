package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ItemResponse;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ItemResponseJsonUnmarshaller implements Unmarshaller<ItemResponse, JsonUnmarshallerContext> {
    private static ItemResponseJsonUnmarshaller instance;

    ItemResponseJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public ItemResponse unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ItemResponse itemResponse = new ItemResponse();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("EndpointItemResponse")) {
                itemResponse.setEndpointItemResponse(EndpointItemResponseJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("EventsItemResponse")) {
                itemResponse.setEventsItemResponse(new MapUnmarshaller(EventItemResponseJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return itemResponse;
    }

    public static ItemResponseJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ItemResponseJsonUnmarshaller();
        }
        return instance;
    }
}
