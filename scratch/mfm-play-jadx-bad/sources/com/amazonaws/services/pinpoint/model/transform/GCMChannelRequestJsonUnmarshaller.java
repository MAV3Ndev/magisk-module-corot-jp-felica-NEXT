package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.GCMChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class GCMChannelRequestJsonUnmarshaller implements Unmarshaller<GCMChannelRequest, JsonUnmarshallerContext> {
    private static GCMChannelRequestJsonUnmarshaller instance;

    GCMChannelRequestJsonUnmarshaller() {
    }

    /* JADX DEBUG: Method merged with bridge method: unmarshall(Ljava/lang/Object;)Ljava/lang/Object; */
    @Override // com.amazonaws.transform.Unmarshaller
    public GCMChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        GCMChannelRequest gCMChannelRequest = new GCMChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ApiKey")) {
                gCMChannelRequest.setApiKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                gCMChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return gCMChannelRequest;
    }

    public static GCMChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new GCMChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
