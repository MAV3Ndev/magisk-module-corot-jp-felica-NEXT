package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.ADMChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class ADMChannelRequestJsonUnmarshaller implements Unmarshaller<ADMChannelRequest, JsonUnmarshallerContext> {
    private static ADMChannelRequestJsonUnmarshaller instance;

    ADMChannelRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public ADMChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        ADMChannelRequest aDMChannelRequest = new ADMChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ClientId")) {
                aDMChannelRequest.setClientId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("ClientSecret")) {
                aDMChannelRequest.setClientSecret(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                aDMChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return aDMChannelRequest;
    }

    public static ADMChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ADMChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
