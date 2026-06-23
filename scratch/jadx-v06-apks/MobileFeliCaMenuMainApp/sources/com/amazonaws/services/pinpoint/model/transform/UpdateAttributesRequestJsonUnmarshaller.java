package com.amazonaws.services.pinpoint.model.transform;

import com.amazonaws.services.pinpoint.model.UpdateAttributesRequest;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

/* JADX INFO: loaded from: classes.dex */
class UpdateAttributesRequestJsonUnmarshaller implements Unmarshaller<UpdateAttributesRequest, JsonUnmarshallerContext> {
    private static UpdateAttributesRequestJsonUnmarshaller instance;

    UpdateAttributesRequestJsonUnmarshaller() {
    }

    @Override // com.amazonaws.transform.Unmarshaller
    public UpdateAttributesRequest unmarshall(JsonUnmarshallerContext jsonUnmarshallerContext) throws Exception {
        AwsJsonReader reader = jsonUnmarshallerContext.getReader();
        if (!reader.isContainer()) {
            reader.skipValue();
            return null;
        }
        UpdateAttributesRequest updateAttributesRequest = new UpdateAttributesRequest();
        reader.beginObject();
        while (reader.hasNext()) {
            if (reader.nextName().equals("Blacklist")) {
                updateAttributesRequest.setBlacklist(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(jsonUnmarshallerContext));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return updateAttributesRequest;
    }

    public static UpdateAttributesRequestJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new UpdateAttributesRequestJsonUnmarshaller();
        }
        return instance;
    }
}
