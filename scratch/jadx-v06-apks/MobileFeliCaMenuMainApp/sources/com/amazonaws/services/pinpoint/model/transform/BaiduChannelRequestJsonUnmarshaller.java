package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.BaiduChannelRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class BaiduChannelRequestJsonUnmarshaller implements Unmarshaller<BaiduChannelRequest, JsonUnmarshallerContext> {
    private static BaiduChannelRequestJsonUnmarshaller instance;

    BaiduChannelRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public BaiduChannelRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        BaiduChannelRequest baiduChannelRequest = new BaiduChannelRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            String strNextName = reader.nextName();
            if (strNextName.equals("ApiKey")) {
                baiduChannelRequest.setApiKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("Enabled")) {
                baiduChannelRequest.setEnabled(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else if (strNextName.equals("SecretKey")) {
                baiduChannelRequest.setSecretKey(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return baiduChannelRequest;
    }

    public static BaiduChannelRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new BaiduChannelRequestJsonUnmarshaller();
        }
        return instance;
    }
}
